package com.CompanyService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CompanyService.Model.CompanyJobApplication;

public interface ApplicationRepo extends JpaRepository<CompanyJobApplication,Long> {
    List<CompanyJobApplication> findByJobIdIn(List<Long> jobIds);

}
