package com.pw.ftpdefaultcomponent.routetemplates;


import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.GroupedBodyAggregationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.ftpdefaultcomponent.configurations.GwhDataFormat;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.ftp.reader.enabled", havingValue = "true", matchIfMissing = false)
public class FtpParseAndSplit extends RouteBuilder {

    @Autowired(required = false)
    GwhDataFormat gwhDataFormat;

    @Override
    public void configure() throws Exception {

        routeTemplate("fileparserandgroup")
            .templateParameter("directNameIn", "fileparserandgroup")
            .templateParameter("directNameOut")
            .templateParameter("groupCount", "50")
            .from("direct:{{directNameIn}}")
                .convertBodyTo(String.class)
                // .unmarshal(gwhDataFormat.geDataFormat("${file:onlyname}"))
                .unmarshal("gwhDataFormat")
                .choice()
                .when(simple("${body.getErrorCount} > 0"))
                    .log("Parsing error occured: simple(${body.getErrors})")
                    .throwException(new RuntimeException("simple(${body.getErrors})"))
                .end()
                .split().body()
                .aggregate(new GroupedBodyAggregationStrategy()).constant(true).eagerCheckCompletion()
                    .completionSize("{{groupCount}}")
                    .completionTimeout(5000) // This is a safety stop
                    .completionPredicate(exchangeProperty("CamelSplitComplete").isEqualTo(true))
                    .marshal().json()
                    .to("direct:{{directNameOut}}")
                .end();

    }

}
