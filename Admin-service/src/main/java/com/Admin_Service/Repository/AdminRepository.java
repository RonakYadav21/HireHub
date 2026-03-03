package com.Admin_Service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Admin_Service.Model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {

	Admin save(Admin admin);

	
}
