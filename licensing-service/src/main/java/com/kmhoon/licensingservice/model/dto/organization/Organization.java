package com.kmhoon.licensingservice.model.dto.organization;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@ToString
public class Organization extends RepresentationModel<Organization> {

    private String id;
    private String name;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
}
