package com.CompanyService.Repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.CompanyService.Model.Company;
import com.CompanyService.Model.JobPosting;

public interface JobPostingRepository extends JpaRepository<JobPosting,Long>, JpaSpecificationExecutor<JobPosting> {

	List<JobPosting> findAll(Specification<JobPosting> spec);

    List<JobPosting> findByCompany(Company company);

	List<JobPosting> findByCompanyId(Long companyId);

}
