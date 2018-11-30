package com.example.unit3.Unit_3_2_3.Serialization.api;

import com.example.unit3.Unit_3_2_3.Serialization.api.entity.Job;
import com.example.unit3.Unit_3_2_3.Serialization.api.entity.User;
import com.example.unit3.Unit_3_2_3.Serialization.api.impl.Serializable;

public class Test {
    static User user;

    public static void main(String[] args) {
        initData();
        testSerializer();
    }

    /**
     * 克隆测试方法
     */
    public static void testClone(){
        User user1 = new User();
        User user2 = new User();
        try {
            user1 = user.clone();//浅克隆
            user1.getJob().setJobName("厨师");//修改对应引用属性值
            System.out.println(user1.toString());
            System.out.println(user.toString());//这里会改变user的属性值
            user2 = user.deepClone();//深克隆
            user2.getJob().setJobName("司机");//修改对应引用属性值
            System.out.println(user2.toString());
            System.out.println(user.toString());//这里不会改变user的属性值
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 序列化测试方法
     */
    public static void testSerializer(){
        ISerializable serializable = new Serializable();
        byte[] bytes = serializable.serializer(user);//序列化
        System.out.println(bytes);
        user.setAge(19);//修改值
        User.longer = 10;//修改静态变量值
        User user1 = serializable.deSerializer(bytes,User.class);//反序列化
        //age未被修改，longer被修改，因此证明，静态变量不会参与序列化,address不会参与序列化，因为已经被transient修饰
        System.out.println(user1.toString());
    }

    public static void initData(){
        user = new User();
        Job job = new Job();
        job.setJobName("程序员");
        user.setName("杨大宇");
        user.setAge(22);
        user.setSex("男");
        user.setJob(job);
        user.setAddress("白山");
    }

}
