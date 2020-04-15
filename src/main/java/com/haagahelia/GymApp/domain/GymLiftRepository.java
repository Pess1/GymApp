package com.haagahelia.GymApp.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface GymLiftRepository extends CrudRepository<GymLift, Long> {
	List<GymLift> findLiftsByWorkout(Workout workout);
	
	List<GymLift> findLiftsByLiftType(String liftType);
	
	GymLift findLiftById(long id);

}
