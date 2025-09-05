package com.PlacementService.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PlacementService.Model.PlacementOfficer;

public interface PlacementOfficerRepository extends JpaRepository<PlacementOfficer, Long> {
    Optional<PlacementOfficer> findByEmail(String email);
}