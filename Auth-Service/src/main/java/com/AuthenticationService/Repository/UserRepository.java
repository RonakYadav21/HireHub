package com.AuthenticationService.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AuthenticationService.Model.Users;
import java.util.List;


@Repository
public interface  UserRepository extends JpaRepository<Users, Long>{

	 Optional<Users> findByUsername(String username);
	  boolean  existsByUsername(String username);
}
