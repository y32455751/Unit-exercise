package com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Public;

import java.io.Serializable;

/**
 * 映射请求的实体类
 */
public class RmiRequest implements Serializable {

    // 实现序列化的ID
    private static final long serialVersionUID = -7715591962721514355L;
    // 要被远程调用的class名称
    private String className;
    // 要呗远程调用的方法名称
    private String methodName;
    // 方法参数
    private Object[] args;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
