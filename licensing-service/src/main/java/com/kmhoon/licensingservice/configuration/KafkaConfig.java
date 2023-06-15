package com.kmhoon.licensingservice.configuration;

import com.kmhoon.licensingservice.events.model.OrganizationChangeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class KafkaConfig {

    @Bean
    public Consumer<OrganizationChangeModel> loggerSink() {
        return orgChange -> log.info("Received an {} event for organization Id {}", orgChange.getAction(), orgChange.getOrganizationId());
    }
}
