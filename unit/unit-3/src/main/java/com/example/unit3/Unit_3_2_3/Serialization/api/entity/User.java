package com.example.unit3.Unit_3_2_3.Serialization.api.entity;

import com.example.unit3.Unit_3_2_3.Serialization.api.ISerializable;
import com.example.unit3.Unit_3_2_3.Serialization.api.impl.Serializable;

public class User implements java.io.Serializable, Cloneable {

    private String name;
    private int age;
    private String sex;
    private Job job;
    private transient String address;
    public static int longer = 18;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", job=" + job.getJobName() +
                ", longer=" + longer +
                '}';
    }

    /**
     * 浅克隆不会克隆原对象中的引用类型，仅仅拷贝了引用类型的指向，所以修改克隆后的对象的引用属性，原型也会跟着改变
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public User clone() throws CloneNotSupportedException {
        // 浅克隆，实现Cloneable接口
        return (User)super.clone();
    }

    /**
     * 深克隆，这种克隆会直接隔绝其中的对象类型字段，当克隆后的对象修改对象类型字段，不会改变原型的值
     * @return
     */
    public User deepClone(){
        ISerializable serializable = new Serializable();
        byte[] bytes = serializable.serializer(this);
        return serializable.deSerializer(bytes,this.getClass());
    }

}
