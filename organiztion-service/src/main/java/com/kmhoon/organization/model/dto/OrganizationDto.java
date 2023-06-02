package com.kmhoon.organization.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrganizationDto {

    private String id;
    private String name;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
}
