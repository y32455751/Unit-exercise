package com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Server.tool;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 这里是定义了一个注解，
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME) //注解的生命周期，表示生命周期是整个运行时
public @interface RpcAnnotation {

    Class<?> value();

    //String getVersion();

}
