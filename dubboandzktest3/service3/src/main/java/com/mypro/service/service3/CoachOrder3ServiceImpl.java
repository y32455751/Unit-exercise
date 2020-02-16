package com.mypro.service.service3;

import com.mypro.service.dao.CoachOrder3Dao;
import com.mypro.service.zookeeper.ZookeeperLockDemo;
import com.mypro.userInfoService.bean.UserInfo;
import com.mypro.userInfoService.service.UserInfoService;
import com.mypro.www.bean.CoachOrder3;
import com.mypro.www.service.CoachOrder3Service;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CoachOrder3ServiceImpl implements CoachOrder3Service {

    @Autowired
    CoachOrder3Dao coachOrder3Dao;

    @Reference
    UserInfoService userInfoService;

    @Override
    public CoachOrder3 addOrder(String name) {

        ZookeeperLockDemo zkLock = new ZookeeperLockDemo(Thread.currentThread() .getStackTrace()[1].getMethodName());
        CoachOrder3 order3 = new CoachOrder3();
        try {
            zkLock.lock();
            order3.setName(name);
            order3.setOrderCode(name+ UUID.randomUUID());
            order3.setPayTime(new Date());
            order3.setPrice(12345.5f);
            coachOrder3Dao.addCoachOrder(order3);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            zkLock.unlock();
        }

        return order3;
    }

    @Override
    public void delOrder(int id) {

    }

    @Override
    public List<CoachOrder3> getOrderList() {
        ZookeeperLockDemo zkLock = new ZookeeperLockDemo(Thread.currentThread() .getStackTrace()[1].getMethodName());
        List<CoachOrder3> list = null;
        try {
            zkLock.lock();
            list = coachOrder3Dao.getCoachOrderList();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            zkLock.unlock();
        }
        return list;
    }

    @Override
    public CoachOrder3 getOrderById(int id) {
        return null;
    }

    @Override
    public List<UserInfo> getUserInfoList_() {
        ZookeeperLockDemo zkLock = new ZookeeperLockDemo(Thread.currentThread() .getStackTrace()[1].getMethodName());
        List<UserInfo> list = null;
        try {
            zkLock.lock();
            list = userInfoService.getUserInfoList();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            zkLock.unlock();
        }
        return list;
    }
}
