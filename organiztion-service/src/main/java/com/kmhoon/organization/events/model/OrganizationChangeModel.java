package com.kmhoon.organization.events.model;

import lombok.*;

@Getter
@Builder
public class OrganizationChangeModel {

    private String type;
    private String action;
    private String organizationId;
    private String correlationId;

}
