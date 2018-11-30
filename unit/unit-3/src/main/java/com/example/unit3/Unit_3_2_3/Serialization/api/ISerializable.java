package com.example.unit3.Unit_3_2_3.Serialization.api;

/**
 * 这是一个接口，定义了序列化和反序列化的方法。
 */
public interface ISerializable {

    /**
     * 序列化，其中T代表着进行序列化的class的类型
     * @param obj 进行序列化的对象
     * @param <T> 序列化的类型
     * @return 序列化后的字符序列
     */
    <T> byte[] serializer(Object obj);

    /**
     * 反序列化，T同上
     * @param bytes 进行反序列化的字符序列
     * @param type 字符序列本身的类型
     * @param <T> 序列化的类型
     * @return 反序列化后的对象
     */
    <T> T deSerializer(byte[] bytes,Class<T> type);

}
