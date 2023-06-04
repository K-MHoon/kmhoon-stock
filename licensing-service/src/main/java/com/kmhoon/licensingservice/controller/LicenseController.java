package com.kmhoon.licensingservice.controller;

import com.kmhoon.licensingservice.model.dto.license.LicenseDto;
import com.kmhoon.licensingservice.model.entity.License;
import com.kmhoon.licensingservice.service.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/organization/{organizationId}/license")
@RequiredArgsConstructor
public class LicenseController {

    private final LicenseService licenseService;

    @GetMapping("/{licenseId}")
    @ResponseStatus(HttpStatus.OK)
    public License getLicense(@PathVariable("organizationId") String organizationId, @PathVariable("licenseId") String licenseId) {
        License license = licenseService.getLicense(licenseId, organizationId, "");
        license.add(
                linkTo(methodOn(LicenseController.class) // linkTo = 루트 매핑, methodOn = 대상 메서드 더미 호출
                        .getLicense(organizationId, license.getLicenseId()))
                        .withSelfRel(),
                linkTo(methodOn(LicenseController.class)
                        .createLicense(LicenseDto.entityToDto(license)))
                        .withRel("createLicense"),
                linkTo(methodOn(LicenseController.class)
                        .updateLicense(LicenseDto.entityToDto(license)))
                        .withRel("updateLicense"),
                linkTo(methodOn(LicenseController.class)
                        .deleteLicense(license.getLicenseId()))
                        .withRel("deleteLicense"));

        return license;
    }

    @GetMapping("/{licenseId}/{clientType}")
    public License getLicenseWithClient(@PathVariable("organizationId") String organizationId,
                                        @PathVariable("licenseId") String licenseId,
                                        @PathVariable("clientType") String clientType) {

        return licenseService.getLicense(licenseId, organizationId, clientType);
    }

    @PostMapping
    public ResponseEntity<License> createLicense(@RequestBody @Validated LicenseDto request) {
        return ResponseEntity.ok(licenseService.createLicense(request));
    }

    @PutMapping
    public ResponseEntity<License> updateLicense(@RequestBody @Validated LicenseDto request) {
        return ResponseEntity.ok(licenseService.updateLicense(request));
    }

    @DeleteMapping("/{licenseId}")
    public ResponseEntity<String> deleteLicense(@PathVariable("licenseId") String licenseId) {
        return ResponseEntity.ok(licenseService.deleteLicense(licenseId));
    }

    @GetMapping
    public List<License> getLicenses(@PathVariable("organizationId") String organizationId) throws TimeoutException {
        return licenseService.getLicensesByOrganization(organizationId);
    }
}
