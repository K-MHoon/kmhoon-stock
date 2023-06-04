package com.kmhoon.licensingservice.service;

import com.kmhoon.licensingservice.configuration.ServiceConfig;
import com.kmhoon.licensingservice.model.dto.license.LicenseDto;
import com.kmhoon.licensingservice.model.dto.organization.Organization;
import com.kmhoon.licensingservice.model.entity.License;
import com.kmhoon.licensingservice.repository.LicenseRepository;
import com.kmhoon.licensingservice.service.client.OrganizationDiscoveryClient;
import com.kmhoon.licensingservice.service.client.OrganizationFeignClient;
import com.kmhoon.licensingservice.service.client.OrganizationRestTemplateClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
@Slf4j
public class LicenseService {

    private final MessageSource messageSource;
    private final LicenseRepository licenseRepository;
    private final ServiceConfig serviceConfig;
    private final OrganizationFeignClient organizationFeignClient;
    private final OrganizationRestTemplateClient organizationRestTemplateClient;
    private final OrganizationDiscoveryClient organizationDiscoveryClient;

    @Transactional
    public License getLicense(String licenseId, String organizationId, String clientType) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId)
                .orElseThrow(() -> new IllegalArgumentException(String.format(messageSource.getMessage("license.search.error.message", null, null), licenseId, organizationId)));
        Organization organization = retrieveOrganizationInfo(organizationId, clientType);
        if(organization != null) {
            license.updateOrganization(organization);
        }
        return license.withComment(serviceConfig.getProperty());
    }

    private Organization retrieveOrganizationInfo(String organizationId, String clientType) {
        return switch (clientType) {
            case "feign" -> organizationFeignClient.getOrganization(organizationId);
            case "rest" -> organizationRestTemplateClient.getOrganization(organizationId);
            case "discovery" -> organizationDiscoveryClient.getOrganization(organizationId);
            default -> organizationRestTemplateClient.getOrganization(organizationId);
        };
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

    @Transactional(readOnly = true)
    @CircuitBreaker(name = "licenseService")
    public List<License> getLicensesByOrganization(String organizationId) throws TimeoutException {
        randomRunLong();
        return licenseRepository.findByOrganizationId(organizationId);
    }

    private void randomRunLong() throws TimeoutException {
        Random random = new Random();
        int randomNum = random.nextInt(3) + 1;
        if (randomNum == 3) sleep();
    }

    private void sleep() throws TimeoutException {
        try {
            Thread.sleep(1000);
            throw new TimeoutException();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}
