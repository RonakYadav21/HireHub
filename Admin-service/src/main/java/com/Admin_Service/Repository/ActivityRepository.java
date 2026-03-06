package com.Admin_Service.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Admin_Service.Model.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findTop10ByOrderByTimeDesc();

}