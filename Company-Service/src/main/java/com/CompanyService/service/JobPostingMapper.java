package com.CompanyService.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.CompanyService.Model.JobPosting;
import com.CompanyService.Model.JobPostingDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JobPostingMapper {
    @Mapping(source = "company.name", target = "companyName")
    @Mapping(source = "domain", target = "domain")
    @Mapping(source = "cgpa", target = "cgpa")

    JobPostingDTO toDto(JobPosting job);
    List<JobPostingDTO> toDtoList(List<JobPosting> jobs);
}
