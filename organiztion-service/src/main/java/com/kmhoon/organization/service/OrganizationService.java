package com.kmhoon.organization.service;

import com.kmhoon.organization.model.dto.OrganizationDto;
import com.kmhoon.organization.model.entity.Organization;
import com.kmhoon.organization.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository repository;


    @Transactional(readOnly = true)
    public Organization findById(String organizationId) {
        return repository.findById(organizationId).orElse(null);
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
        return repository.save(organization);
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
        repository.save(organization);
    }

    @Transactional
    public void delete(String organizationId) {
        repository.deleteById(organizationId);
    }
}
