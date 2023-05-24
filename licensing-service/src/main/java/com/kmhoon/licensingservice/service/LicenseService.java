package com.kmhoon.licensingservice.service;

import com.kmhoon.licensingservice.model.License;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class LicenseService {

    public License getLicense(String licenseId, String organizationId) {
        return License.builder()
                .id(new Random().nextLong(1000))
                .licenseId(licenseId)
                .organizationId(organizationId)
                .description("Product")
                .productName("Stock")
                .licenseType("full")
                .build();
    }

    public String createLicense(License license, String organizationId) {
        String responseMessage = null;
        if (license != null) {
            license.updateOrganizationId(organizationId);
            responseMessage = String.format("create license = %s", license);
        }
        return responseMessage;
    }

    public String updateLicense(License license, String organizationId) {
        String responseMessage = null;
        if (license != null) {
            license.updateOrganizationId(organizationId);
            responseMessage = String.format("update license = %s", license);
        }
        return responseMessage;
    }

    public String deleteLicense(String licenseId, String organizationId) {
        String responseMessage = null;
        responseMessage = String.format("delete license = %s, organization = %s", licenseId, organizationId);
        return responseMessage;
    }
}
