package com.haagahelia.GymApp.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface GymLiftRepository extends CrudRepository<GymLift, Long> {
	//Method for finding lifts in a workout
	List<GymLift> findLiftsByWorkout(Workout workout);
	
	//Method for finding lifts by the user of a workout
	List<GymLift> findLiftsByWorkoutUser(User user);
	
	//Method for finding lifts by their type
	List<GymLift> findLiftsByLiftType(String liftType);
	
	//Method that returns a gymlift object by id instead of an optional<GymLift>
	GymLift findLiftById(long id);

}
