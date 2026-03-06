package com.CompanyService.service;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.CompanyService.Model.Company;
import com.CompanyService.Model.CompanyDTO;

@Mapper(componentModel = "spring")
public interface CompanyMapper {


    CompanyDTO companyToCompanyDto(Company company);

	void updateCompanyFromDto(CompanyDTO dto,@MappingTarget Company company);
}