package com.kmhoon.licensingservice.model;


import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class License {

    private Long id;
    private String licenseId;
    private String description;
    private String organizationId;
    private String productName;
    private String licenseType;

    public void updateOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }
}
