package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer>{

	// ホテル取得(通常)
	@Query(value = "SELECT "
			+ "ht.id, "
			+ "ht.name, "
			+ "ht.photo, "
			+ "ht.prefecture, "
			+ "ht.city, "
			+ "pl.name AS planName, "
			+ "pl.price AS price "
			+ "FROM hotels ht "
			+ "JOIN plans pl "
			+ "ON pl.hotel_id = ht.id "
			+ "WHERE (?1 IS NULL OR ?1 = '' OR ht.prefecture = ?1) "
			+ "AND (?2 IS NULL OR pl.price >= ?2) "
			+ "AND (?3 IS NULL OR pl.price <= ?3) "
			+ "AND (?4 IS NULL OR ht.name LIKE concat('%',?4,'%') "
			+ "OR ht.city LIKE concat('%',?4,'%') "
			+ "OR pl.name LIKE concat('%',?4,'%')) "
			+ "ORDER BY ht.id "
			+ "LIMIT ?5 "
			+ "OFFSET ?6 ",nativeQuery = true)
	List<Object[]> findHotelList(String prefecture,Integer minPrice,Integer maxPrice,String keyword,int limit,int offset);
	
	// ホテル取得(昇順)
	@Query(value = "SELECT "
			+ "ht.id, "
			+ "ht.name, "
			+ "ht.photo, "
			+ "ht.prefecture, "
			+ "ht.city, "
			+ "pl.name AS planName, "
			+ "pl.price AS price "
			+ "FROM hotels ht "
			+ "JOIN plans pl "
			+ "ON pl.hotel_id = ht.id "
			+ "WHERE (?1 IS NULL OR ?1 = '' OR ht.prefecture = ?1) "
			+ "AND (?2 IS NULL OR pl.price >= ?2) "
			+ "AND (?3 IS NULL OR pl.price <= ?3) "
			+ "AND (?4 IS NULL OR ht.name LIKE concat('%',?4,'%') "
			+ "OR ht.city LIKE concat('%',?4,'%') "
			+ "OR pl.name LIKE concat('%',?4,'%')) "
			+ "ORDER BY pl.price ASC "
			+ "LIMIT ?5 "
			+ "OFFSET ?6 ",nativeQuery = true)
	List<Object[]> findHotelListOrderByPriceAsc(String prefecture,Integer minPrice,Integer maxPrice,String keyword,int limit,int offset);
	
	// ホテル取得(降順)
	@Query(value = "SELECT "
			+ "ht.id, "
			+ "ht.name, "
			+ "ht.photo, "
			+ "ht.prefecture, "
			+ "ht.city, "
			+ "pl.name AS planName, "
			+ "pl.price AS price "
			+ "FROM hotels ht "
			+ "JOIN plans pl "
			+ "ON pl.hotel_id = ht.id "
			+ "WHERE (?1 IS NULL OR ?1 = '' OR ht.prefecture = ?1) "
			+ "AND (?2 IS NULL OR pl.price >= ?2) "
			+ "AND (?3 IS NULL OR pl.price <= ?3) "
			+ "AND (?4 IS NULL OR ht.name LIKE concat('%',?4,'%') "
			+ "OR ht.city LIKE concat('%',?4,'%') "
			+ "OR pl.name LIKE concat('%',?4,'%')) "
			+ "ORDER BY pl.price DESC "
			+ "LIMIT ?5 "
			+ "OFFSET ?6 ",nativeQuery = true)
	List<Object[]> findHotelListOrderByPriceDesc(String prefecture,Integer minPrice,Integer maxPrice,String keyword,int limit,int offset);
	
	// ホテル件数の取得
	@Query(value = "SELECT "
			+ "COUNT (*) "
			+ "FROM hotels ht "
			+ "JOIN plans pl "
			+ "ON pl.hotel_id = ht.id "
			+ "WHERE (?1 IS NULL OR ?1 = '' OR ht.prefecture = ?1) "
			+ "AND (?2 IS NULL OR pl.price >= ?2) "
			+ "AND (?3 IS NULL OR pl.price <= ?3) "
			+ "AND (?4 IS NULL OR ht.name LIKE concat('%',?4,'%') "
			+ "OR ht.city LIKE concat('%',?4,'%') "
			+ "OR pl.name LIKE concat('%',?4,'%')) " ,nativeQuery = true)
	int countByHotelList(String prefecture,Integer minPrice,Integer maxPrice,String keyword);
		
}
