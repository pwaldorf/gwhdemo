package com.pw.ftpdefault1.consumer.routetemplates;

import org.apache.camel.Exchange;
import org.apache.camel.builder.AggregationStrategies;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.dataformat1.transform.GwhNewRouteFileAggregator;


@Component
@ConditionalOnProperty(value = "gwh.framework.component.ftp.default1.consumer.enabled", havingValue = "true", matchIfMissing = false)
public class FtpParseAndSplit2 extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        routeTemplate("fileparserandgroup2")
            .templateParameter("directNameIn", "fileparserandgroup2")
            .templateParameter("directNameOut")
            .templateParameter("groupCount", "5")
            .templateParameter("splittoken", "\n")
            .templateParameter("formatName", "test-ftp2.txt")
            // .templateBean("defaultDataFormat")
            //     .typeClass(DataFormat.class)
            //     .property("formatName", "{{formatName}}")
            // .end()
            .from("direct:{{directNameIn}}")
                .setHeader("GWHBatchCount", constant(0))
                .setHeader("GWHGroupCount", constant("{{groupCount}}"))
                .convertBodyTo(String.class)
                .split(body().tokenize("\n"))
                    .streaming()
                    .stopOnException()
                    .bean("gwhNewRouteFileDataFormat", "parse")
                    .aggregate(constant(true), new GwhNewRouteFileAggregator()).eagerCheckCompletion()
                        .completionSize("{{groupCount}}")
                        //.completionTimeout(5000) // This is a safety stop
                        .completionPredicate(exchangeProperty(Exchange.SPLIT_COMPLETE).isEqualTo(true))
                    .bean("gwhNewRouteFileDataFormat", "format")
                    .marshal().json()
                    .to("direct:{{directNameOut}}")
                .end();


    }

}
