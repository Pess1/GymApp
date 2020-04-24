package com.haagahelia.GymApp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.haagahelia.GymApp.domain.GymLift;
import com.haagahelia.GymApp.domain.GymLiftRepository;
import com.haagahelia.GymApp.domain.User;
import com.haagahelia.GymApp.domain.UserRepository;
import com.haagahelia.GymApp.domain.Workout;
import com.haagahelia.GymApp.domain.WorkoutRepository;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LiftRepoTest {
	
	@Autowired
	private GymLiftRepository lRepo;
	
	@Autowired 
	private WorkoutRepository wRepo;
	
	@Autowired
	private UserRepository uRepo;
	
	//Testing for finding by workout
	@Test
	public void findLiftsByWorkoutShouldReturnListLifts() {
		List<Workout> workouts = wRepo.findWorkoutsByDate("11.04.2020");
		Workout workout = workouts.get(0);
		
		int liftsInWorkout = workout.getLifts().size();
		
		List<GymLift> lifts = lRepo.findLiftsByWorkout(workout);
		
		assertThat(lifts.size()).isEqualTo(liftsInWorkout);
	}
	
	//Testing for finding by user
	@Test
	public void findLiftsByWorkoutUserShouldReturnListLifts() {
		User user = uRepo.findUserByUsername("admin");
		
		List<GymLift> lifts = lRepo.findLiftsByWorkoutUser(user);
		
		assertThat(lifts.get(0).getWorkout().getUser().getUsername()).isEqualTo("admin");
	}
	
	//Testing for finding by lift type
	@Test
	public void findLiftsByLiftTypeShouldReturnListLifts() {
		List<GymLift> lifts = lRepo.findLiftsByLiftType("Squat");
		
		assertThat(lifts.get(0).getLiftType()).isEqualTo("Squat");
	}
	
	//Testing saving a new lift
	@Test
	public void createNewLiftTest() {
		List<Workout> workouts = wRepo.findWorkoutsByDate("11.04.2020");
		
		GymLift lift = new GymLift("Squat", "High bar", 3, 8, 90, workouts.get(0));
		
		lRepo.save(lift);
		
		GymLift testLift = lRepo.findLiftById(lift.getId());
		
		assertThat(testLift.getNotes()).isEqualTo("High bar");
	}
	
	//Testing deleting an old lift
	@Test
	public void deleteLiftTest() {
		List<Workout> workouts = wRepo.findWorkoutsByDate("11.04.2020");
		
		GymLift lift = new GymLift("Squat", "High bar", 3, 8, 90, workouts.get(0));
		
		lRepo.save(lift);
		
		lRepo.deleteById(lift.getId());
		
		GymLift testLift = lRepo.findLiftById(lift.getId());
		
		assertThat(testLift).isNull();
	}

}
