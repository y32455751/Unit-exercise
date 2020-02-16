package com.mypro.service2.dao;

import com.mypro.userInfoService.bean.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoDao {

    List<UserInfo> getUserInfoList();

    UserInfo getUserInfo(String objuid);

    void addUserInfo(UserInfo userInfo);

    void deleteUserInfo(String objuid);

}
