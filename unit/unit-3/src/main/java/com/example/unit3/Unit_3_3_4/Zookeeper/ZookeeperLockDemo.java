package com.example.unit3.Unit_3_3_4.Zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 当前类是一个分布式锁机制，解决的问题是：
 *      当多个客户端同时要使用一个节点的时候，会发生争抢，羊群效应。
 *      解决方案是让他们排队，用完一个走一个，排队使用。
 *      现实模型映射：银行业务排队
 * 代码实现的逻辑是：
 *      调用一个节点时，先在此节点下创建一个子节点用于排队
 *      -》进入大厅，先叫号排队
 *      获得当前节点下所有子节点，并且进行排序
 *      -》看看自己的号码，记下自己的号码
 *      判断当前子节点的顺序队列中的第一个，是不是自己的节点
 *      -》看看银行窗口叫的号是不是自己的号
 *      如果不是，则要获得自己节点前面队列中，最后一个人的节点名称，进行监听
 *      -》如果叫的不是自己，那么自己只要看自己前面的那个人的业务办理状况就可以了
 *      当前面的一个节点发生了事件变动，离开了队列，则要再次判断自己节点是不是队列第一个
 *      -》当自己前面那个人离开了，要看银行是否叫到了自己的号
 *      （因为前面一个节点可能在排队的中途离开，所以还要重新判断自己在队列中的情况）
 *      如果不是第一个，则再次监听自己节点前面队列中的最后一个
 *      -》如果不是自己的号，则要在看自己前面的号的业务办理状况。
 *      直到自己获得节点权限，或者中途退出
 */
public class ZookeeperLockDemo implements Lock,Watcher {

    private ZooKeeper zk = null;
    private String ROOT_LOCK = "/lock";
    private String WAIT_NODE;
    private String CURRENT_NODE;

    private CountDownLatch countDownLatch;


    /**
     * 构造方法
     * 构造方法连接了zookeeper，然后判断了主要节点是否存在，不存在则创建。
     */
    public ZookeeperLockDemo() {
        try {
            // 这里创建的CountDownLatch，是用来等待zookeeper连接成功阻塞。
            CountDownLatch count = new CountDownLatch(1);
            // 连接zookeeper。默认的zookeeper端口是2181，这里要确认Linux的2181端口是否开放
            // watcher在zk有了动作之后就会回调进行通知。
            zk = new ZooKeeper("192.168.8.56:2181",4000,watchedEvent -> {
                // 判断回调通知时候的状态是connected，表示已经连接
                if(Event.KeeperState.SyncConnected == watchedEvent.getState()){
                    // 此时count进行countDown，放开阻塞
                    count.countDown();
                }
            });
            // 在这里进行阻塞，等待这个count执行countDown，当这个count执行countDown时，这里的阻塞就会放开，执行下面的代码
            count.await();
            // 这里的exists是获得一个节点的状态，当节点不存在的时候stat会为空。
            Stat stat = zk.exists(ROOT_LOCK,false);
            // 当stat为空的时候，说明这个节点不存在，则进行创建
            if(stat == null){
               // create方法，（节点路径，节点初始化内容/值，节点acl策略/权限，节点类型）
               zk.create(ROOT_LOCK,"0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void lock() {
        if(this.tryLock()){
            System.out.println(Thread.currentThread().getName() + ":" + CURRENT_NODE + "获得锁成功");
        }else{
            waitNodeCommit(WAIT_NODE);
        }
    }

    /**
     * 这个是用来判断当前节点前面的一个节点是否失效或者删除，从队列中离开。
     *
     * 疑问1：当一个节点执行countDown时候是不是所有节点都会执行countDown
     * 答案：不会，因为这个方法传入了一个要被监听的对象，这个被监听的对象监听的只是前面的一个节点
     *       当一个节点退出队列，只有他后面的一个节点会触发countDown，因为只有他后面的节点监听了那个退出的节点
     *       其余节点监听的节点并没有变化，所以不会触发countDown
     *
     * @param node 要被监听的节点
     */
    private void waitNodeCommit(String node){
        try {
            // 先看此节点是否存在，这里的watcher设置成true是因为
            // 本身当前类就已经实现了watcher方法，所以当这个节点发生变化的时候，会直接调用当前类的process方法
            Stat stat = zk.exists(node,true);
            // 当节点存在
            if(stat != null){
                System.out.println(Thread.currentThread().getName() + ":等待节点 " + node + " 释放");
                // 这是一个全局的countDownLatch，
                countDownLatch = new CountDownLatch(1);
                // 在这里开始等待节点被释放，当这个阻塞放开了之后，当前节点就可以获得锁。
                countDownLatch.await();
                // 这里不能直接提示获得锁成功，也有可能前面的一个节点在排队中途离开，必须要再次尝试获得锁
                this.lock();
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    /**
     * 尝试获得锁的方法
     *
     * 这个锁机制采用的方法是使用排序的方法，
     * 首先创建一个临时有序的子节点，
     * 获得根节点下面的所有子节点，判断当前我创建的节点是不是最小的，或者说是当前根节点下面的第一个子节点
     * 如果是第一个子节点，那么表示我有权获得当权根节点的锁，
     * 如果不是，那么我要监听我前面的一个节点，看他什么时候会有动作，
     * 当他有动作的时候，我要看我是不是当前最小的，
     * 等到我前面的子节点失效或者删除时，我变成了最小的节点后，我就可以获得当前根节点的锁。
     *
     * 类似业务窗口排序，医院、银行窗口
     *
     * @return 是否可以获得锁
     */
    @Override
    public boolean tryLock() {
        try {
            // 创建当前用来排序的节点（节点路径，节点初始化内容/值，节点acl策略/权限，节点类型）
            /*
                节点类型有：
                （1）PERSISTENT：持久；
                （2）PERSISTENT_SEQUENTIAL：持久顺序；
                （3）EPHEMERAL：临时；
                （4）EPHEMERAL_SEQUENTIAL：临时顺序。
             */
            CURRENT_NODE = zk.create(ROOT_LOCK+"/","0".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(Thread.currentThread().getName() + ":" + CURRENT_NODE + "节点进入队列");
            // 查询当前根节点下面有多少子节点，即队列中有多少人正在排队
            List<String> childrens = zk.getChildren(ROOT_LOCK,false);
            // 创建一个可以排序的集合/要知道排队的人的顺序
            SortedSet<String> nodeList = new TreeSet<>();
            // 对排队的人进行排序
            for(String children:childrens){
                nodeList.add(ROOT_LOCK + "/" + children);
            }
            // 获得当前集合中第一个节点/找到队列最前面的人
            String minNode = nodeList.first();
            // 获得当前节点之前的所有节点/看我前面都是谁
            SortedSet<String> lessThenMe = ((TreeSet<String>) nodeList).headSet(CURRENT_NODE);
            // 判断当前节点是不是子节点中的第一个
            if(CURRENT_NODE.equals(minNode)){
                return true;
            }
            // 如果我不是第一个，而且我前面还有人，则我要一直看着我前面的那个人。
            if(!lessThenMe.isEmpty()){
                WAIT_NODE = lessThenMe.last();//这里获得的是我前面所有人的最后一个，就是我前面的第一个人
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit){
        return false;
    }

    /**
     * 放开锁
     * 这里把节点删除后，关闭zk
     */
    @Override
    public void unlock() {
        try {
            // 删除一个节点，-1表示不管是否能删除我都要删除
            zk.delete(CURRENT_NODE,-1);
            CURRENT_NODE = null;
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    /**
     * 当监听的节点发生改变，即会调用这个方法。
     * @param watchedEvent
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        if(this.countDownLatch != null){
            // 放开阻塞
            this.countDownLatch.countDown();
        }
    }
}
