package com.kmhoon.organization.events.source;

import com.kmhoon.organization.events.model.OrganizationChangeModel;
import com.kmhoon.organization.utils.ActionEnum;
import com.kmhoon.organization.utils.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SimpleSourceBean {

    private final StreamBridge streamBridge;

    public void publishOrganizationChange(ActionEnum action, String organizationId) {
        log.debug("Kafka Message = {}, Organization Id = {} ", action, organizationId);
        OrganizationChangeModel change = OrganizationChangeModel.builder()
                .type(OrganizationChangeModel.class.getTypeName())
                .action(action.toString())
                .organizationId(organizationId)
                .correlationId(UserContext.getCorrelationId())
                .build();
        streamBridge.send("output-out-0", MessageBuilder.withPayload(change).build());
    }


}
