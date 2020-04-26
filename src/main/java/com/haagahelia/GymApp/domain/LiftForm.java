package com.haagahelia.GymApp.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

//Form for saving or editing a lift
public class LiftForm {
	private long id;
	
	@NotEmpty
	@Size(min = 1, max = 30)
	private String liftType;
	
	@Size(min = 0, max = 50)
	private String notes;
	
	@Min(value = 1)
	private int sets;
	
	@Min(value = 1)
	private int reps;
	
	@Min(value = 0)
	private double weight = 0.00;
	
	private Workout workout;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLiftType() {
		return liftType;
	}

	public void setLiftType(String liftType) {
		this.liftType = liftType;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getSets() {
		return sets;
	}

	public void setSets(int sets) {
		this.sets = sets;
	}

	public int getReps() {
		return reps;
	}

	public void setReps(int reps) {
		this.reps = reps;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Workout getWorkout() {
		return workout;
	}

	public void setWorkout(Workout workout) {
		this.workout = workout;
	}
	
}
