package com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Client.discover.impl;

import com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Client.discover.IServiceDiscover;
import com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Public.ZKConfig;
import com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Server.tool.banalce.ILoadBanalce;
import com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Server.tool.banalce.RandomLoadBanalce;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现服务类
 * 连接上zookeeper之后，调用服务的时候先去查找服务
 * 发现服务之后对服务进行监听。
 */
public class ServiceDiscover implements IServiceDiscover {

    private CuratorFramework curatorFramework;
    List<String> nodeList = new ArrayList<>();


    public ServiceDiscover(String zk_) {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(zk_)
                .sessionTimeoutMs(40000)
                .retryPolicy(new ExponentialBackoffRetry(1000,10))
                .build();
        curatorFramework.start();
    }

    /**
     * 发现服务方法
     *
     * 获得要被发现的节点路径
     * 获得节点的子节点，这里的子节点都是拥有此服务的地址
     * 监听节点
     * 进行负载均衡
     *
     * @param serviceName 要被发现的服务
     * @return 发现的服务，可能下面有好几个服务器的连接，返回的是经过负载均衡之后的地址
     */
    @Override
    public String discover(String serviceName){
        String path = ZKConfig.REGISTER_NODE + "/" + serviceName;
        try {
            nodeList = curatorFramework.getChildren().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        listenNode(path);
        ILoadBanalce banalce = new RandomLoadBanalce();
        return banalce.selectHost(nodeList);
    }

    /**
     * 监听节点
     *
     * 监听节点
     * 然后启动监听
     *
     * @param path
     */
    public void listenNode(String path){
        PathChildrenCache childrenCache = new PathChildrenCache(curatorFramework,path,true);
        PathChildrenCacheListener pathChildrenCacheListener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                nodeList = curatorFramework.getChildren().forPath(path);
            }
        };
        childrenCache.getListenable().addListener(pathChildrenCacheListener);
        try {
            childrenCache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
