package com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Server.tool.banalce;

import java.util.List;

/**
 * 负载均衡抽象类
 *
 * 因为doselect有好几种实现方法
 * 随机负载，权重负载，轮询负载
 *
 */
public abstract class AbstarctLoadBanalce implements ILoadBanalce {

    /**
     * 选择节点
     *
     * 当节点集合没有的时候，返回空
     * 当节点只有一个时候直接返回
     * 当节点有两个或者两个以上的时候进行负载均衡计算
     *
     * @param list 需要被负载均衡计算的节点集合
     * @return 负载均衡算法后的节点
     */
    @Override
    public String selectHost(List<String> list){
        if(list==null || list.size()==0){
            return null;
        }else if(list.size()==1){
            return list.get(0);
        }
        return doSelect(list);
    }

    /**
     * 抽象的负载均衡算法
     * @param nodelist 负载均衡的集合
     * @return 负载均衡后的节点
     */
    protected abstract String doSelect(List<String> nodelist);

}
