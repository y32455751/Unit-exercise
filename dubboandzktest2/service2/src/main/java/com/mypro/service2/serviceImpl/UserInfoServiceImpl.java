package com.mypro.service2.serviceImpl;

import com.mypro.service2.dao.UserInfoDao;
import com.mypro.service2.zookeeperLock.ZookeeperLockDemo;
import com.mypro.userInfoService.bean.UserInfo;
import com.mypro.userInfoService.service.UserInfoService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserInfoDao userInfoDao;


    @Override
    public List<UserInfo> getUserInfoList() {

        ZookeeperLockDemo zkLock = new ZookeeperLockDemo(Thread.currentThread() .getStackTrace()[1].getMethodName());
        List<UserInfo> list = null;
        try{
            zkLock.lock();
            list = userInfoDao.getUserInfoList();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            zkLock.unlock();
        }

        return list;
    }

    @Override
    public UserInfo getUserInfo(String objuid) {
        return null;
    }

    @Override
    public void addUserInfo(UserInfo userInfo) {

    }

    @Override
    public void deleteUserInfo(String objuid) {

    }
}
