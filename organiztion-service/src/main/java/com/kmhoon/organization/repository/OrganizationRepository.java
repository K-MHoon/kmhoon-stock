package com.kmhoon.organization.repository;

import com.kmhoon.organization.model.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, String> {
}
