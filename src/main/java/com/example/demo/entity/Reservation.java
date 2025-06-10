package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservations")
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "plan_id", nullable = false)
	private Plan plan;
	
	@Column(name = "number_of_people")
	private Integer numberOfPeople;
	
	@Column(name = "reservation_date")
	private LocalDate reservationDate;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	public Reservation() {
		
	}
	
	public Reservation(Integer id, User user,Plan plan, Integer numberOfPeople, LocalDate reservationDate, LocalDateTime createdAt) {
		this.id = id;
		this.user = user;
		this.plan = plan;
		this.numberOfPeople = numberOfPeople;
		this.reservationDate = reservationDate;
		this.createdAt = createdAt;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Plan getPlan() {
		return plan;
	}
	
	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	
	public Integer getNumberOfPeople() {
		return numberOfPeople;
	}
	
	public void setNumberOfPeople(Integer numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}
	
	public LocalDate getReservationDate() {
		return reservationDate;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
