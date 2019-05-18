package com.yangdayu.socket.socketgameclient.serializer;

/**
 * 这是一个序列化操作的接口文件
 */
public interface ISerializer {
    /**
     * 这个方法是用来把对象进行序列化的
     * @param obj 将要被序列化的对象
     * @param <T> 将要被序列化的对象的类型
     * @return 序列化后的字节数组
     */
    <T> byte[] serializer(T obj);

    /**
     * 这个方法是用来反序列化的，
     * @param data 序列化后的字节数组
     * @param type 序列化内容的类型
     * @param <T> 序列化内容类型的Class
     * @return 反序列化之后的对象
     */
    <T> T deSerializer(byte[] data, Class<T> type);
}
