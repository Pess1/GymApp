package com.haagahelia.GymApp.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface WorkoutRepository extends CrudRepository<Workout, Long> {
	//Method for finding workouts of a certain date
	List<Workout> findWorkoutsByDate(String date);
	
	//Method for finding workouts by their owner
	List<Workout> findWorkoutsByUser(User user);
	
	//Method that returns workout instead of optional<workout>
	Workout findWorkoutById(long id);

}
