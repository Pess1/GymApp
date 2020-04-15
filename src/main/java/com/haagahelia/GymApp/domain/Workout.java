package com.haagahelia.GymApp.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Workout {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String date;
	
	@ManyToOne
	@JoinColumn(name = "userid")
	private User user;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="workout")
	@JsonIgnore
	private List<GymLift> lifts;
	
	public Workout() {
		
	}
	
	public Workout(String date, User user) {
		this.setDate(date);
		this.setUser(user);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<GymLift> getLifts() {
		return lifts;
	}

	public void setLifts(List<GymLift> lifts) {
		this.lifts = lifts;
	}
	
	public String toString() {
		return this.getDate() + " " + this.getUser().toString();
	}
	

}
