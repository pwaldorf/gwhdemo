package com.pw.ftpdefault1.consumer.configurations;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

// @Component("jsonAggregationStrategy")
public class JsonAggregationStrategy { //} implements AggregationStrategy {

    //@Override
    public String aggregate(String oldExchange, String newExchange) {

        if (oldExchange == null) {

            return newExchange;
        }

        // String oldBody = oldExchange.getIn().getBody(String.class);
        // String newBody = newExchange.getIn().getBody(String.class);
        String tempNewExchange = null;
        String tempOldExchange = null;

        tempNewExchange = newExchange.replace("[", "");
        tempNewExchange = tempNewExchange.replace("]", "");
        tempOldExchange = oldExchange.replace("[", "");
        tempOldExchange = tempOldExchange.replace("]", "");

        return "[" + tempOldExchange + "," + tempNewExchange + "]";

    }

}
