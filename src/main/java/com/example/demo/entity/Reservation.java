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

import org.hibernate.annotations.CreationTimestamp;

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
	
	@Column(name = "check_in")
	private LocalDate checkIn;
	
	@Column(name = "check_out")
	private LocalDate checkOut;
	
	@Column(name = "total_price")
	private Integer totalPrice;
	
	@Column(name = "pay")
	private String pay;
	
	private boolean cancelled = false;
	
	@Column(name = "created_at")
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	public Reservation() {
		
	}
	
	public Reservation(Integer id, User user,Plan plan, Integer numberOfPeople, LocalDate checkIn,
	LocalDate checkOut, Integer totalPrice, String pay, LocalDateTime createdAt) {
		this.id = id;
		this.user = user;
		this.plan = plan;
		this.numberOfPeople = numberOfPeople;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.totalPrice = totalPrice;
		this.pay = pay;
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
	
	public LocalDate getCheckIn() {
		return checkIn;
	}
	
	public void setCheckIn(LocalDate checkIn) {
		this.checkIn = checkIn;
	}
	
	public LocalDate getCheckOut() {
		return checkOut;
	}
	
	public void setCheckOut(LocalDate checkOut) {
		this.checkOut = checkOut;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public Integer getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getPay() {
		return pay;
	}

	public void setPay(String pay) {
		this.pay = pay;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

}
