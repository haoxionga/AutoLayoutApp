package com.example.administrator.autolayoutapplication.proxy;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by Shinelon on 2017/9/9.
 */
public class Client {
    @Test
    public void main() {
        //代理对象
        Hello helloImpl = new HelloImpl();
        //将需要代理的对象传进去，最后是需要该对象调用其方法的
        InvocationHandler handler = new DynamicProxy(helloImpl);
        /*
        * 通过Proxy的newProxyInstance方法来创建我们的代理对象，我们来看看其三个参数
        * 第一个参数 handler.getClass().getClassLoader() ，我们这里使用handler这个类的ClassLoader对象来加载我们的代理对象
        * 第二个参数realSubject.getClass().getInterfaces()，我们这里为代理对象提供的接口是真实对象所实行的接口，表示我要代理的是该真实对象，这样我就能调用这组接口中的方法了
        * 第三个参数handler， 我们这里将这个代理对象关联到了上方的 InvocationHandler 这个对象上
        */
        Hello helloProxy = (Hello) Proxy.newProxyInstance(handler.getClass().getClassLoader(), helloImpl
                .getClass().getInterfaces(), handler);
        //看看这个代理对象的真实面目
        System.out.println(helloProxy.getClass().getName());
        helloProxy.helloCat();
        helloProxy.helloDog("小白");
    }
}