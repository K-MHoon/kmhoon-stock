package com.kmhoon.licensingservice.model;


import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@ToString
@Builder
public class License extends RepresentationModel<License> {
// RepresentationModel = 모델 클래스에 링크 추가 가능하게 한다.
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
