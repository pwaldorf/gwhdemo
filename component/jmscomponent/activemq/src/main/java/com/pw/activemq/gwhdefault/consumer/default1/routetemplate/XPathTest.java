package com.pw.activemq.gwhdefault.consumer.default1.routetemplate;

import org.apache.camel.CamelContext;
import org.apache.camel.language.xpath.XPathBuilder;
import org.springframework.stereotype.Component;

@Component
public class XPathTest {

    public void test(CamelContext camelContext, String message){
        System.out.println("In XPathTest:"+message);
        String value = XPathBuilder.xpath("foo/bar/text()").evaluate(camelContext, message, String.class);
        System.out.println("value:"+value);
    }
}
