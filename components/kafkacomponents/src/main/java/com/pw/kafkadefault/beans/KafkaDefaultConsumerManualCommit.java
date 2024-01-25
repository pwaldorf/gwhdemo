package com.pw.kafkadefault.beans;

import org.apache.camel.Exchange;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.component.kafka.consumer.KafkaManualCommit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.kafka.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaDefaultConsumerManualCommit {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDefaultConsumerManualCommit.class);
    
    public void process(Exchange exchange) {
        KafkaManualCommit manual =
            exchange.getIn().getHeader(KafkaConstants.MANUAL_COMMIT, KafkaManualCommit.class);                    
        manual.commit();

        LOGGER.info("Committing offset {}", exchange.getIn().getHeader(KafkaConstants.OFFSET));
    }
    
}
