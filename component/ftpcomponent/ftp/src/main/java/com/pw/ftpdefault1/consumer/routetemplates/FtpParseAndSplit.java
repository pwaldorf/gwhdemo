package com.pw.ftpdefault1.consumer.routetemplates;

import org.apache.camel.Exchange;
import org.apache.camel.builder.AggregationStrategies;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.DataFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.ftpdefault1.consumer.configurations.JsonAggregationStrategy;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.ftp.default1.consumer.enabled", havingValue = "true", matchIfMissing = false)
public class FtpParseAndSplit extends RouteBuilder {

    @Value("${gwh.dataformat.name:ftpDataFormat}")
    String ftpDataFormat;

    @Override
    public void configure() throws Exception {

        routeTemplate("fileparserandgroup")
            .templateParameter("directNameIn", "fileparserandgroup")
            .templateParameter("directNameOut")
            .templateParameter("groupCount", "50")
            .templateParameter("splittoken", "\n")
            .templateParameter("formatName", "customers-100.csv")
            .templateBean("defaultDataFormat")
                .typeClass(DataFormat.class)
                .property("formatName", "{{formatName}}")
            .end()
            .from("direct:{{directNameIn}}")
                .setHeader("GWHBatchCount", constant(0))
                .setHeader("GWHGroupCount", constant("{{groupCount}}"))
                .split(body().tokenize("\n"))
                    .streaming()
                    .stopOnException()
                    .unmarshal(ftpDataFormat)
                    .bean("testBean")
                    .choice()
                    .when(simple("${body.getErrorCount} > 0"))
                        .log("Parsing error occured: simple(${body.getErrors})")
                        .throwException(new RuntimeException("simple(${body.getErrors})"))
                    .end()
                    .marshal().json()
                    .convertBodyTo(String.class)
                    .aggregate(constant(true), AggregationStrategies.bean(JsonAggregationStrategy.class, "aggregate")).eagerCheckCompletion()
                        .completionSize("{{groupCount}}")
                        .completionTimeout(5000) // This is a safety stop
                        .completionPredicate(exchangeProperty(Exchange.SPLIT_COMPLETE).isEqualTo(true))
                    .bean("gwhWrapMessage", "wrapMessage")
                    .to("direct:{{directNameOut}}")
                .end();


    }

}
