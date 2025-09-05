package com.CompanyService.service;

import org.springframework.data.jpa.domain.Specification;

import com.CompanyService.Model.JobPosting;

public class JobPostingSpecification {

    public static Specification<JobPosting> hasCompanyName(String companyName) {
        return (root, query, cb) -> {
            if (companyName == null || companyName.trim().isEmpty()) {
                return null;
            }
            return cb.equal(root.get("company").get("name"), companyName);
        };
    }

    public static Specification<JobPosting> hasDomain(String domain) {
        return (root, query, cb) -> {
            if (domain == null || domain.trim().isEmpty()) {
                return null;
            }
            return cb.equal(root.get("domain"), domain);
        };
    }

    public static Specification<JobPosting> hasLocation(String location) {
        return (root, query, cb) -> {
            if (location == null || location.trim().isEmpty()) {
                return null;
            }
            return cb.equal(root.get("location"), location);
        };
    }

    public static Specification<JobPosting> salaryGreaterThanOrEqual(Double salary) {
        return (root, query, cb) -> {
            if (salary == null) {
                return null;
            }
            return cb.greaterThanOrEqualTo(root.get("salary"), salary);
        };
    }
}
