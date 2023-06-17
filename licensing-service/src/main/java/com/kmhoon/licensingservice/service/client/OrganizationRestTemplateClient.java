package com.kmhoon.licensingservice.service.client;

import com.kmhoon.licensingservice.model.dto.organization.Organization;
import com.kmhoon.licensingservice.repository.OrganizationRedisRepository;
import com.kmhoon.licensingservice.utils.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrganizationRestTemplateClient {

    private final RestTemplate restTemplate;

    private final OrganizationRedisRepository organizationRedisRepository;

    public Organization getOrganization(String organizationId) {
        log.debug("In Licensing Service.getOrganization: {}", UserContext.getCorrelationId());

        Organization organization = checkRedisCache(organizationId);

        if(organization != null) {
            log.debug("successfully retrieved an organization {} from the redis cache: {}", organizationId, organization);
            return organization;
        }

        log.debug("Unable to locate organization from the redis cache: {}.", organizationId);

        ResponseEntity<Organization> restExchange = restTemplate.exchange("http://organization-service/v1/organization/{organizationId}",
                HttpMethod.GET,
                null,
                Organization.class,
                organizationId);

        organization = restExchange.getBody();

        if(organization != null) {
            cacheOrganizationObject(organization);
        }

        return organization;
    }

    private Organization checkRedisCache(String organizationId) {
        try {
            return organizationRedisRepository.findById(organizationId).orElse(null);
        } catch (Exception ex) {
            log.error("Error encountered while trying to retrieve organization {} check Redis Cache. Exception {}", organizationId, ex);
            return null;
        }
    }

    private void cacheOrganizationObject(Organization organization) {
        try {
            organizationRedisRepository.save(organization);
        } catch (Exception ex) {
            log.error("Unable to cache organization {} in Redis. Exception {}", organization.getId(), ex);
        }
    }
}
