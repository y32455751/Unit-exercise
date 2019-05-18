package com.yangdayu.socket.socketgameclient.service;

import java.util.List;

import com.yangdayu.socket.socketgameclient.entity.UserInfoEntity;

public interface UserInfoEntityService {

    UserInfoEntity addUserinfoentity(UserInfoEntity userinfoentity);

    int deleteUserinfoentity(String objuid);

    UserInfoEntity updateUserinfoentity(UserInfoEntity userinfoentity);

    UserInfoEntity findUserinfoentityByObjuid(String objuid);

    List<UserInfoEntity> findAllUserinfoentity();

    List<UserInfoEntity> findUserinfoentityByWhere(UserInfoEntity userinfoentity);

    List<UserInfoEntity> findUserinfoentityByPage(UserInfoEntity userinfoentity);

    UserInfoEntity findByLoginName(UserInfoEntity userinfoentity);

    UserInfoEntity findByUserName(UserInfoEntity userinfoentity);

    UserInfoEntity findByUserName(String username);

    UserInfoEntity login(UserInfoEntity userinfoentity);

}