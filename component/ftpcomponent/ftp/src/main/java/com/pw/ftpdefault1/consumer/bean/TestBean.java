package com.pw.ftpdefault1.consumer.bean;

import org.springframework.stereotype.Component;

@Component
public class TestBean {


    public void wrapMessage(String exchange) {


        System.out.println("TestBean.wrapMessage()");

    }
}
