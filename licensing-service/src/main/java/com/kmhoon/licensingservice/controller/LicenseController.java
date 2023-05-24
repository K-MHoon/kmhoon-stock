package com.kmhoon.licensingservice.controller;

import com.kmhoon.licensingservice.model.License;
import com.kmhoon.licensingservice.service.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/v1/organization/{organizationId}/license")
@RequiredArgsConstructor
public class LicenseController {

    private final LicenseService licenseService;

    @GetMapping("/{licenseId}")
    @ResponseStatus(HttpStatus.OK)
    public License getLicense(@PathVariable("organizationId") String organizationId, @PathVariable("licenseId") String licenseId) {
        return licenseService.getLicense(licenseId, organizationId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String createLicense(@PathVariable("organizationId") String organizationId,
                                @RequestBody License request,
                                @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) Locale locale) {
        return licenseService.createLicense(request, organizationId, locale);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public String updateLicense(@PathVariable("organizationId") String organizationId, @RequestBody License request) {
        return licenseService.updateLicense(request, organizationId);
    }

    @DeleteMapping("/{licenseId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteLicense(@PathVariable("organizationId") String organizationId, @PathVariable("licenseId") String licenseId) {
        return licenseService.deleteLicense(licenseId, organizationId);
    }
}
