package com.haagahelia.GymApp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.haagahelia.GymApp.domain.User;
import com.haagahelia.GymApp.domain.UserRepository;
import com.haagahelia.GymApp.domain.Workout;
import com.haagahelia.GymApp.domain.WorkoutRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
final public class WorkoutRepoTest {
	
	@Autowired
	private WorkoutRepository wRepo;
	
	@Autowired
	private UserRepository uRepo;
	
	//Testing for finding with date
	@Test
	public void findWorkoutsByDateShouldReturnListWorkouts() {
		List<Workout> workouts = wRepo.findWorkoutsByDate("11.04.2020");
		
		assertThat(workouts.get(0).getDate()).isEqualTo("11.04.2020");
	}
	
	//Testing for finding with user
	@Test
	public void findWorkoutsByUserShouldReturnListWorkouts() {
		User user = uRepo.findUserByUsername("admin");
		
		List<Workout> workouts = wRepo.findWorkoutsByUser(user);
		
		assertThat(workouts).isNotEmpty();
	}
	
	//Testing for finding with id which returns Workout instead of Optional
	@Test
	public void findWorkoutByidShouldReturnWorkout() {
		User user = uRepo.findUserByUsername("admin");
		
		Workout workout = wRepo.findWorkoutById(wRepo.findWorkoutsByUser(user).get(0).getId());
		
		assertThat(workout.getDate()).isEqualTo("11.04.2020");
	}
	
	//Testing for creating a workout
	@Test
	public void createWorkoutTest() {
		User user = uRepo.findUserByUsername("admin");
		
		Workout workout = new Workout("22.04.2020", user);
		
		wRepo.save(workout);
		
		Workout testWorkout = wRepo.findWorkoutById(workout.getId());
		
		assertThat(testWorkout.getDate()).isEqualTo("22.04.2020");
	}
	
	//Testing for deletion
	@Test
	public void deleteWorkoutTest() {
		User user = uRepo.findUserByUsername("admin");
		
		Workout workout = new Workout("22.04.2020", user);
		
		wRepo.save(workout);
		
		wRepo.deleteById(workout.getId());
		
		Workout testWorkout = wRepo.findWorkoutById(workout.getId());
		
		assertThat(testWorkout).isNull();
	}
}
