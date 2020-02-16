package com.mypro.userInfoService.service;

import com.mypro.userInfoService.bean.UserInfo;

import java.util.List;

public interface UserInfoService {

    List<UserInfo> getUserInfoList();

    UserInfo getUserInfo(String objuid);

    void addUserInfo(UserInfo userInfo);

    void deleteUserInfo(String objuid);

}
