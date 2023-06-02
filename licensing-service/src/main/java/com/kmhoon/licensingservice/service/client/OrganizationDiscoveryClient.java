package com.kmhoon.licensingservice.service.client;

import com.kmhoon.licensingservice.model.dto.organization.Organization;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrganizationDiscoveryClient {

    /**
     * 해당 클래스는 스프링 클라우드 로드 밸런서와 상호 작용한다.
     */
    private final DiscoveryClient discoveryClient;

    public Organization getOrganization(String organizationId) {
        RestTemplate restTemplate = new RestTemplate();
        List<ServiceInstance> instances = discoveryClient.getInstances("organization-service");

        if(instances.isEmpty()) return null;
        String serviceUri = String.format("%s/v1/organization/%s", instances.get(0).getUri().toString(), organizationId);

        ResponseEntity<Organization> restExchange = restTemplate.exchange(serviceUri, HttpMethod.GET, null, Organization.class, organizationId);
        return restExchange.getBody();
    }


}
