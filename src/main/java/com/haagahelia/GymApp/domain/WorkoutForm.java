package com.haagahelia.GymApp.domain;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

//Form for saving new workout or modifying existing ones
public class WorkoutForm {
	
	private long id;
	
	@NotEmpty
	@Pattern(regexp = "^(([0-9]{2})\\.([0-9]{2})\\.([0-9]{4}))$")
	private String date = "";
	
	private List<GymLift> lifts;
	
	private User user;

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

	public List<GymLift> getLifts() {
		return lifts;
	}

	public void setLifts(List<GymLift> lifts) {
		this.lifts = lifts;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
