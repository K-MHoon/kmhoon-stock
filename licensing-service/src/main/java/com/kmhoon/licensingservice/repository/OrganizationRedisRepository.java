package com.kmhoon.licensingservice.repository;

import com.kmhoon.licensingservice.model.dto.organization.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRedisRepository extends CrudRepository<Organization, String> {
}
