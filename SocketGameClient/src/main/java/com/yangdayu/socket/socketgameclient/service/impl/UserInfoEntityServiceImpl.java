package com.yangdayu.socket.socketgameclient.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.yangdayu.socket.socketgameclient.entity.UserInfoEntity;
import com.yangdayu.socket.socketgameclient.mapper.UserInfoEntityMapper;
import com.yangdayu.socket.socketgameclient.service.UserInfoEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangdayu.socket.socketgameclient.unit.YUUID;

@Service
@Transactional
public class UserInfoEntityServiceImpl implements UserInfoEntityService {

    @Resource
    private UserInfoEntityMapper mapper;

    @Override
    public UserInfoEntity addUserinfoentity(UserInfoEntity userinfoentity) {
        String objuid = YUUID.getUUID();
        userinfoentity.setObjuid(objuid);
        mapper.addUserinfoentity(userinfoentity);
        userinfoentity = new UserInfoEntity();
        userinfoentity = this.findUserinfoentityByObjuid(objuid);
        return userinfoentity;
    }

    @Override
    public int deleteUserinfoentity(String objuid) {
        return mapper.deleteUserinfoentity(objuid);
    }

    @Override
    public UserInfoEntity updateUserinfoentity(UserInfoEntity userinfoentity) {
        int num = mapper.updateUserinfoentity(userinfoentity);
        if(num>0){
            String objuid = userinfoentity.getObjuid();
            userinfoentity = new UserInfoEntity();
            userinfoentity = this.findUserinfoentityByObjuid(objuid);
            return userinfoentity;
        }else{
            return null;
        }
    }

    @Override
    public UserInfoEntity findUserinfoentityByObjuid(String objuid) {
        return mapper.findUserinfoentityByObjuid(objuid);
    }

    @Override
    public List<UserInfoEntity> findAllUserinfoentity() {
        return mapper.findAllUserinfoentity();
    }

    @Override
    public List<UserInfoEntity> findUserinfoentityByWhere(UserInfoEntity userinfoentity) {
        return mapper.findUserinfoentityByWhere(userinfoentity);
    }

    @Override
    public List<UserInfoEntity> findUserinfoentityByPage(UserInfoEntity userinfoentity) {
        return mapper.findUserinfoentityByPage(userinfoentity);	}

    @Override
    public UserInfoEntity findByLoginName(UserInfoEntity userinfoentity) {
        UserInfoEntity entity = new UserInfoEntity();
        entity.setLoginname(userinfoentity.getLoginname());
        List<UserInfoEntity> list = this.findUserinfoentityByWhere(entity);
        if(null != list && list.size()==1){
            return list.get(0);
        }
        return null;
    }

    @Override
    public UserInfoEntity findByUserName(UserInfoEntity userinfoentity) {
        UserInfoEntity entity = new UserInfoEntity();
        entity.setUsername(userinfoentity.getUsername());
        List<UserInfoEntity> list = this.findUserinfoentityByWhere(entity);
        if(null != list && list.size()==1){
            return list.get(0);
        }
        return null;
    }

    @Override
    public UserInfoEntity findByUserName(String username) {
        UserInfoEntity entity = new UserInfoEntity();
        entity.setUsername(username);
        return this.findByUserName(entity);
    }

    @Override
    public UserInfoEntity login(UserInfoEntity userinfoentity) {
        List<UserInfoEntity> list = this.findUserinfoentityByWhere(userinfoentity);
        if(null != list && list.size()==1){
            return list.get(0);
        }
        return null;
    }

}