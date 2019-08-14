package com.mybatis;

import com.mybatis.handler.UserMapperInvocationHandler;

import java.lang.reflect.Proxy;

public class SqlSession {

    public static <T> T getUserMapper(Class clazz, String path) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new UserMapperInvocationHandler(path));
    }
}
