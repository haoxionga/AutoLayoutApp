package com.example.administrator.autolayoutapplication.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Shinelon on 2017/9/9.
 */

public class DynamicProxy implements InvocationHandler {
    private Hello hello;
    public DynamicProxy(Hello hello){
        this.hello = hello;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        //before
        System.out.println("before say hello");
        //当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
        Object object = method.invoke(hello, args);
        //after
        System.out.println("after say hello");
        return object;
    }
}