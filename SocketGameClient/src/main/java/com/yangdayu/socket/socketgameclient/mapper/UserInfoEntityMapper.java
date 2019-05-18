package com.yangdayu.socket.socketgameclient.mapper;

import java.util.List;

import com.yangdayu.socket.socketgameclient.entity.UserInfoEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoEntityMapper {

    void addUserinfoentity(UserInfoEntity userinfoentity);

    int deleteUserinfoentity(String objuid);

    int updateUserinfoentity(UserInfoEntity userinfoentity);

    UserInfoEntity findUserinfoentityByObjuid(String objuid);

    List<UserInfoEntity> findAllUserinfoentity();

    List<UserInfoEntity> findUserinfoentityByWhere(UserInfoEntity userinfoentity);

    List<UserInfoEntity> findUserinfoentityByPage(UserInfoEntity userinfoentity);

}