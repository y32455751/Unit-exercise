package com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Server.registerCenter;

import com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Public.ZKConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * 注册服务类
 */
public class RegisterCenter implements IRegisterCenter {

    private CuratorFramework curatorFramework;

    public RegisterCenter() {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(ZKConfig.ZK_CONNECTION_STR)
                .sessionTimeoutMs(40000)
                .retryPolicy(new ExponentialBackoffRetry(1000,10))
                .build();
        curatorFramework.start();
    }

    /**
     * 注册服务
     *
     * 检查服务是否已经被注册
     * 未注册即进行创建并且注册
     * 在服务节点下创建临时的地址：端口节点
     *
     * @param serviceName 服务名称
     * @param serviceAddress 服务地址
     */
    @Override
    public void register(String serviceName, String serviceAddress) {
        String path = ZKConfig.REGISTER_NODE + "/" + serviceName;

        try {
            if(curatorFramework.checkExists().forPath(path) == null){
                curatorFramework.create().creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT).forPath(path);
            }
            String addressPath = path + "/" + serviceAddress;
            curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(addressPath,"0".getBytes());
            System.out.println(addressPath + "服务注册成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
