package com.haagahelia.GymApp.domain;

import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity

public class User {
	//@Column(name = "userid", nullable = false, updatable = false)
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userid;
	
	@Column(name = "username", nullable = false, unique = true)
	private String username;
	
	@Column(name = "password", nullable = false)
	private String passwordHash;
	
	@Column(name = "email", nullable=false)
	private String email;

	@Column(name = "role", nullable = false)
	private String role;
	
	@Column(name = "weight", nullable = false)
	private int weight;
	
	@Column(name = "height", nullable = false)
	private int height;
	
	@Column(name = "gender", nullable = false)
	private String gender;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="user")
	@JsonIgnore
	private List<Workout> workouts;

	public User() {
		
	}
	
	public User(String username, String passwordHash, String email, String role, int weight, int height, String gender) {
		this.username = username;
		this.passwordHash = passwordHash;
		this.email = email;
		this.role = role;
		this.weight = weight;
		this.height = height;
		this.gender = gender;
	}

	public long getUserId() {
		return userid;
	}

	public void setUserId(long userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public List<Workout> getWorkouts() {
		return workouts;
	}

	public void setLifts(List<Workout> workouts) {
		this.workouts = workouts;
	}
	
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String toString() {
		return this.getUsername() + " " + this.getEmail() + " " + this.getRole() + " " + this.getWeight() + " " + this.getHeight();
	}
	
}
