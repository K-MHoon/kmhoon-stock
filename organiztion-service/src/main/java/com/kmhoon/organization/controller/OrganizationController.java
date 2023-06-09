package com.kmhoon.organization.controller;

import com.kmhoon.organization.model.dto.OrganizationDto;
import com.kmhoon.organization.model.entity.Organization;
import com.kmhoon.organization.service.OrganizationService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService service;

    @GetMapping("/{organizationId}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<Organization> getOrganization(@PathVariable("organizationId") String organizationId) {
        return ResponseEntity.ok(service.findById(organizationId));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed({"ADMIN", "USER"})
    public void updateOrganization(@RequestBody OrganizationDto organizationDto) {
        service.update(organizationDto);
    }

    @PostMapping
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<Organization> saveOrganization(@RequestBody OrganizationDto organizationDto) {
        return ResponseEntity.ok(service.create(organizationDto));
    }

    @DeleteMapping("/{organizationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RolesAllowed("ADMIN")
    public void deleteOrganization(@PathVariable("organizationId") String organizationId) {
        service.delete(organizationId);
    }

}
