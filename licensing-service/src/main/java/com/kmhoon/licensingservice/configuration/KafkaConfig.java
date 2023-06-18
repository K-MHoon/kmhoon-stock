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
        return orgChange -> {

            log.debug("Received a message of type " + orgChange.getType());

            switch (orgChange.getAction()) {
                case "GET" -> log.debug("Received a GET event from the organization service for organization id {}", orgChange.getOrganizationId());
                case "SAVE" -> log.debug("Received a SAVE event from the organization service for organization id {}", orgChange.getOrganizationId());
                case "UPDATE" -> log.debug("Received a UPDATE event from the organization service for organization id {}", orgChange.getOrganizationId());
                case "DELETE" -> log.debug("Received a DELETE event from the organization service for organization id {}", orgChange.getOrganizationId());
                default -> log.error("Received an UNKNOWN event from the organization service of type {}", orgChange.getType());
            }
        };
    }
}
