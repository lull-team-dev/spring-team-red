package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer>{
	//ユーザーIDとそれに紐づくプランとホテルを取得
	@Query("SELECT r FROM Reservation r JOIN FETCH r.plan p JOIN FETCH p.hotel WHERE r.user.id = :userId")
	List<Reservation> findByUserIdFetchPlanAndHotel(@Param("userId") Integer userId);
	//最新の予約1件」を取得
    Reservation findTopByUserIdOrderByPlanIdDesc(Integer userId);

}
