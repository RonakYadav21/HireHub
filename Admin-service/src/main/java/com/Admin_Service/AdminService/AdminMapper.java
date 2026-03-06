package com.Admin_Service.AdminService;

import org.mapstruct.Mapper;

import com.Admin_Service.Model.Admin;
import com.Admin_Service.Model.AdminDTO;

@Mapper(componentModel = "spring")
public interface AdminMapper {

	AdminDTO adminToAdminDto(Admin admin);

}
