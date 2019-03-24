package com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Server.tool.banalce;

import java.util.List;
import java.util.Random;

public class RandomLoadBanalce extends AbstarctLoadBanalce {

    /**
     * 实现负载均衡算法，这里是随机负载
     * @param nodelist 负载均衡的集合
     * @return
     */
    @Override
    protected String doSelect(List<String> nodelist) {
        int len = nodelist.size();
        Random random = new Random();
        return nodelist.get(random.nextInt(len));
    }
}
