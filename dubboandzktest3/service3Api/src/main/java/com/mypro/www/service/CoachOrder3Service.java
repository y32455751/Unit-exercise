package com.mypro.www.service;

import com.mypro.userInfoService.bean.UserInfo;
import com.mypro.www.bean.CoachOrder3;

import java.util.List;

public interface CoachOrder3Service {

    /**
     * 新增订单信息
     * @param name
     * @return
     */
    public CoachOrder3 addOrder(String name);

    public void delOrder(int id);

    public List<CoachOrder3> getOrderList();

    public CoachOrder3 getOrderById(int id);

    public List<UserInfo> getUserInfoList_();
}
