package com.example.administrator.autolayoutapplication.proxy;

/**
 * Created by Shinelon on 2017/9/9.
 */

public class HelloImpl implements Hello {
    @Override
    public void helloCat() {
        System.out.println("hello Cat !");
    }
    @Override
    public void helloDog(String dog) {
        System.out.println("hello " + dog + "!");
    }
}