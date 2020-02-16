package com.mypro.service.dao;

import com.mypro.www.bean.CoachOrder3;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoachOrder3Dao {

    CoachOrder3 getCoachOrderById(@Param("id") int id);


    List<CoachOrder3> getCoachOrderList();


    void deleteCoachOrder(@Param("id") int id);


    void addCoachOrder(CoachOrder3 coachOrder);

}
