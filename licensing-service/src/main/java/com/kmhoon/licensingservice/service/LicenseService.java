package com.kmhoon.licensingservice.service;

import com.kmhoon.licensingservice.configuration.ServiceConfig;
import com.kmhoon.licensingservice.model.dto.LicenseDto;
import com.kmhoon.licensingservice.model.entity.License;
import com.kmhoon.licensingservice.repository.LicenseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LicenseService {

    private final MessageSource messageSource;
    private final LicenseRepository licenseRepository;
    private final ServiceConfig serviceConfig;

    @Transactional
    public License getLicense(String licenseId, String organizationId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId)
                .orElseThrow(() -> new IllegalArgumentException(String.format(messageSource.getMessage("license.search.error.message", null, null), licenseId, organizationId)));
        return license.withComment(serviceConfig.getProperty());
    }

    @Transactional
    public License createLicense(LicenseDto request) {
        License license = License.builder()
                .licenseId(UUID.randomUUID().toString())
                .description(request.getDescription())
                .organizationId(request.getOrganizationId())
                .productName(request.getProductName())
                .licenseType(request.getLicenseType())
                .build();

        licenseRepository.save(license);
        return license.withComment(serviceConfig.getProperty());
    }

    @Transactional
    public License updateLicense(LicenseDto request) {
        License license = License.builder()
                .licenseId(request.getLicenseId())
                .description(request.getDescription())
                .organizationId(request.getOrganizationId())
                .productName(request.getProductName())
                .licenseType(request.getLicenseType())
                .build();

        licenseRepository.save(license);
        return license.withComment(serviceConfig.getProperty());
    }

    @Transactional
    public String deleteLicense(String licenseId) {
        License license = licenseRepository.findById(licenseId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 License ID 입니다."));
        licenseRepository.delete(license);
        return String.format(messageSource.getMessage("license.delete.message", null, null), licenseId);
    }
}
