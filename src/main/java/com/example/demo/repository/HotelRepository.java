package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer>{

	@Query(value = "SELECT "
			+ "DISTINCT ON ( "
			+ "ht.id ) "
			+ "ht.id, "
			+ "ht.photo photo, "
			+ "ht.name, "
			+ "pl.price, "
			+ "ht.prefecture, "
			+ "ht.city "
			+ "FROM hotels ht "
			+ "JOIN plans pl "
			+ "ON pl.hotel_id = ht.id "
			+ "WHERE (?1 IS NULL OR ht.prefecture = ?1) "
			+ "AND (?2 IS NULL OR pl.price >= ?2) "
			+ "AND (?3 IS NULL OR pl.price <= ?3) "
			+ "AND (?4 IS NULL OR ht.name LIKE concat('%',?4,'%') "
			+ "OR ht.city LIKE concat('%',?4,'%')) ",nativeQuery = true)
	List<Hotel> findByhotelList(String prefecture,Integer minPrice,Integer maxPrice,String keyword);
	
}
