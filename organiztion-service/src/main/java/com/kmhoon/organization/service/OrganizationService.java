package com.kmhoon.organization.service;

import com.kmhoon.organization.events.source.SimpleSourceBean;
import com.kmhoon.organization.model.dto.OrganizationDto;
import com.kmhoon.organization.model.entity.Organization;
import com.kmhoon.organization.repository.OrganizationRepository;
import com.kmhoon.organization.utils.ActionEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository repository;

    private final SimpleSourceBean sourceBean;


    @Transactional(readOnly = true)
    public Organization findById(String organizationId) {
        Organization organization = repository.findById(organizationId).orElse(null);
        sourceBean.publishOrganizationChange(ActionEnum.GET, organizationId);
        return organization;
    }

    @Transactional
    public Organization create(OrganizationDto organizationDto) {
        Organization organization = Organization.builder()
                .id(UUID.randomUUID().toString())
                .name(organizationDto.getName())
                .contactName(organizationDto.getContactName())
                .contactPhone(organizationDto.getContactPhone())
                .contactEmail(organizationDto.getContactEmail())
                .build();
        Organization savedOrganization = repository.save(organization);
        sourceBean.publishOrganizationChange(ActionEnum.CREATED, savedOrganization.getId());
        return savedOrganization;
    }

    @Transactional
    public void update(OrganizationDto organizationDto) {
        Organization organization = Organization.builder()
                .id(organizationDto.getId())
                .name(organizationDto.getName())
                .contactName(organizationDto.getContactName())
                .contactPhone(organizationDto.getContactPhone())
                .contactEmail(organizationDto.getContactEmail())
                .build();
        sourceBean.publishOrganizationChange(ActionEnum.UPDATED, organization.getId());
        repository.save(organization);
    }

    @Transactional
    public void delete(String organizationId) {
        repository.deleteById(organizationId);
        sourceBean.publishOrganizationChange(ActionEnum.DELETED, organizationId);
    }
}
