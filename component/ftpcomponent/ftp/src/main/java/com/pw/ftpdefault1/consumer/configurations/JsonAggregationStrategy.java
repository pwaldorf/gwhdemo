package com.pw.ftpdefault1.consumer.configurations;

public class JsonAggregationStrategy {

    //@Override
    public String aggregate(String oldExchange, String newExchange) {

        if (oldExchange == null) {

            return newExchange;
        }

        String tempNewExchange = null;
        String tempOldExchange = null;

        tempNewExchange = newExchange.replace("[", "");
        tempNewExchange = tempNewExchange.replace("]", "");
        tempOldExchange = oldExchange.replace("[", "");
        tempOldExchange = tempOldExchange.replace("]", "");

        return "[" + tempOldExchange + "," + tempNewExchange + "]";

    }

}
