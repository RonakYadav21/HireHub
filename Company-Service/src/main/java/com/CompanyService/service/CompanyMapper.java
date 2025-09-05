package com.CompanyService.service;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.CompanyService.Model.Company;
import com.CompanyService.Model.CompanyDTO;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    CompanyDTO companyToCompanyDto(Company company);
}