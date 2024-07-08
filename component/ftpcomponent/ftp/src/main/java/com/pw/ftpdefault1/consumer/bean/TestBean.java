package com.pw.ftpdefault1.consumer.bean;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class TestBean {


    public void wrapMessage(Exchange exchange) {


        System.out.println("TestBean.wrapMessage()");
        System.out.println(exchange.getProperty(Exchange.BATCH_INDEX));
        System.out.println(exchange.getProperty(Exchange.BATCH_SIZE));
        System.out.println(exchange.getProperty(Exchange.SPLIT_INDEX));
        System.out.println(exchange.getProperty(Exchange.SPLIT_SIZE));

    }
}
