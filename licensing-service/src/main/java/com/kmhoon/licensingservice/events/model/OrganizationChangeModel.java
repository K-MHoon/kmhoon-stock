package com.kmhoon.licensingservice.events.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrganizationChangeModel {

    private String type;
    private String action;
    private String organizationId;
    private String correlationId;

}
