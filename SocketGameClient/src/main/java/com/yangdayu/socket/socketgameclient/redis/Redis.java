package com.yangdayu.socket.socketgameclient.redis;


import com.yangdayu.socket.socketgameclient.entity.UserInfoEntity;
import com.yangdayu.socket.socketgameclient.serializer.SerializerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

public class Redis<T> {

    public Redis(){

    }

    private static Jedis jedis = new Jedis("127.0.0.1");

    public synchronized static Jedis getInstance(){
        if(jedis==null){
            jedis = new Jedis("127.0.0.1");
        }
        return jedis;
    }

    public <T> List<Class<T>> getList(String key,Class<T> type){
        Jedis redis = Redis.getInstance();
        Set<byte[]> keySet = redis.keys("*".getBytes());
        byte[][] keys = keySet.toArray(new byte[keySet.size()][]);
        // 获取所有value
        byte[][] values = redis.mget(keys).toArray(new byte[keySet.size()][]);

        // 打印key-value对
        for (int i = 0; i < keySet.size(); ++i) {
            System.out.println(byte2hex(keys[i]) + " --- " + byte2hex(values[i]));
        }
        return null;
    }


    private static String byte2hex(byte[] buffer) {
        String h = "0x";

        for (byte aBuffer : buffer) {
            String temp = Integer.toHexString(aBuffer & 0xFF);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            h = h + " " + temp;
        }

        return h;

    }

}
