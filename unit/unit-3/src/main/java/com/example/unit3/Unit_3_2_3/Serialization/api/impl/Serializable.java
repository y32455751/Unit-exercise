package com.example.unit3.Unit_3_2_3.Serialization.api.impl;

import com.example.unit3.Unit_3_2_3.Serialization.api.ISerializable;

import java.io.*;

/**
 * 序列化接口的实现类
 */
public class Serializable implements ISerializable {
    /**
     * 实现了序列化方法
     * @param obj 进行序列化的对象
     * @param <T>
     * @return
     */
    @Override
    public <T> byte[] serializer(Object obj) {
        //字符序列输出流
        ByteArrayOutputStream byteArrayOutputStream = null;
        //对象输出流，用来将对象写出
        ObjectOutputStream objectOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            //这里放入byteArrayOutputStream 是要告诉对象写出流用什么方式写出对象，这里采用字符序列方式
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(byteArrayOutputStream != null) {
                    byteArrayOutputStream.flush();
                    byteArrayOutputStream.close();
                }
                if(objectOutputStream != null){
                    objectOutputStream.flush();
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 实现了反序列化的方法
     * @param bytes 进行反序列化的字符序列
     * @param type 字符序列本身的类型
     * @param <T>
     * @return
     */
    @Override
    public <T> T deSerializer(byte[] bytes, Class<T> type) {
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            //这里放入需要反序列化的内容
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            //告诉对象输入流使用何种方式进行输入
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (T)objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                if(byteArrayInputStream != null){
                    byteArrayInputStream.close();
                }
                if(objectInputStream != null){
                    objectInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
