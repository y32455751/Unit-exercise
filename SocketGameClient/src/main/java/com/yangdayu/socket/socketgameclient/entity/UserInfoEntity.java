package com.yangdayu.socket.socketgameclient.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * 用户信息
 */
public class UserInfoEntity implements Serializable {

    private static final long serialVersionUID = 3969438177161438988L;

    /**
     * 主键
     */
    protected String objuid;
    /**
     * 昵称
     */
    protected String username;
    /**
     * 登陆账号
     */
    protected String loginname;
    /**
     * 密码
     */
    protected String password;
    /**
     * 创建时间
     */
    protected Date createtime;
    /**
     * 修改时间
     */
    protected Date updatetime;

    protected UserEntity status;

    public String getObjuid() {
        return objuid;
    }

    public void setObjuid(String objuid) {
        this.objuid = objuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public UserEntity getStatus() {
        return status;
    }

    public void setStatus(UserEntity status) {
        this.status = status;
    }

    public void setInitUserStatus(){
        UserEntity entity = new UserEntity(3,new ArrayList<AircraftEntity>());
        this.status = entity;
    }
}
