package com.haagahelia.GymApp.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface WorkoutRepository extends CrudRepository<Workout, Long> {
	List<Workout> findWorkoutsByDate(String date);
	
	List<Workout> findWorkoutsByUser(User user);
	
	Workout findWorkoutById(long id);

}
