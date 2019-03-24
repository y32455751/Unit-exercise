package com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Server.registerCenter;


public interface IRegisterCenter {

    /**
     * 注册服务
     * @param serviceName 服务名称
     * @param serviceAddress 服务地址
     */
    public void register(String serviceName,String serviceAddress);

}
