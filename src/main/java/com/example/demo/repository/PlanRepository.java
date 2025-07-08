package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Plan;

public interface PlanRepository extends JpaRepository<Plan, Integer>{
	
	@Query(value  = "SELECT * "
			+ "FROM plans pl "
			+ "WHERE pl.hotel_id = ?1",nativeQuery = true)
	List<Plan> findPlanByHotelId(Integer id);

}
