package com.kmhoon.licensingservice.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Entity
@Table(name = "licenses")
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class License extends RepresentationModel<License> {
// RepresentationModel = 모델 클래스에 링크 추가 가능하게 한다.

    @Id
    @Column(name = "license_id", nullable = false)
    private Long id;
    private String licenseId;
    private String description;
    @Column(name = "organization_id", nullable = false)
    private String organizationId;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "license_type", nullable = false)
    private String licenseType;
    @Column(name = "comment")
    private String comment;

    public void updateOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public License withComment(String comment) {
        this.comment = comment;
        return this;
    }
}
