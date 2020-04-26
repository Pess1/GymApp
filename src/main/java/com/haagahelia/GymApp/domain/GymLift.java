package com.haagahelia.GymApp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

//Gym lift entity. Each lift belongs to one workout
@Entity
public class GymLift {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String liftType;
	private String notes;
	private int sets;
	private int reps;
	private double weight = 0.00;
	
	@ManyToOne
	@JoinColumn(name = "workoutid")
	private Workout workout;
	
	public GymLift() {
		
	}
	
	public GymLift(String liftType, String notes, int sets, int reps, double weight, Workout workout) {
		this.liftType = liftType;
		this.notes = notes;
		this.sets = sets;
		this.reps = reps;
		this.weight = weight;
		this.workout = workout;
	}

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
	
	public String toString() {
		return this.getLiftType() + " " + this.getNotes() + " " + this.getSets() + " " + this.getReps() + " " + this.getWeight() + " "
				+ this.getWorkout().getDate() + " " + this.getWorkout().getUser().getUsername();
		
	}
}
