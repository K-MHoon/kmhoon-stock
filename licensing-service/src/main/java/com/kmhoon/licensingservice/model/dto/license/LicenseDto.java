package com.kmhoon.licensingservice.model.dto.license;

import com.kmhoon.licensingservice.model.entity.License;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class LicenseDto {

    private String licenseId;
    private String description;
    @NotEmpty(message = "organization ID는 필수 값 입니다.")
    private String organizationId;
    @NotEmpty(message = "product 이름은 필수 값 입니다.")
    private String productName;
    @NotEmpty(message = "license 타입은 필수 값 입니다.")
    private String licenseType;
    private String comment;

    public static LicenseDto entityToDto(License license) {
        return LicenseDto.builder()
                .licenseId(license.getLicenseId())
                .description(license.getDescription())
                .organizationId(license.getOrganizationId())
                .productName(license.getProductName())
                .licenseType(license.getLicenseType())
                .comment(license.getComment())
                .build();
    }
}
