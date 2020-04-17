package com.haagahelia.GymApp;

import org.springframework.boot.CommandLineRunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.haagahelia.GymApp.domain.User;
import com.haagahelia.GymApp.domain.UserRepository;
import com.haagahelia.GymApp.domain.Workout;
import com.haagahelia.GymApp.domain.WorkoutRepository;
import com.haagahelia.GymApp.domain.GymLift;
import com.haagahelia.GymApp.domain.GymLiftRepository;

@SpringBootApplication
public class GymAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymAppApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner book(GymLiftRepository liftRepo, UserRepository userRepo, WorkoutRepository workoutRepo) {
		return (args) -> {
	
			User user1 = new User("admin", "$2y$12$Vh1mFFnzjglTzkuVWjfXhOC1FW5HUes2mrJbPMLcuZHxIJqplFgrG", "admin@email.com", "ADMIN", 112, 185);
			userRepo.save(user1);
			
			User user2 = new User("testuser", "$2y$12$lNfQ16Dx1BxAfrVPfq5rzOjeY4hMGBZHfiOee4wVCP7.b.jkohw8K", "test@email.com", "USER", 90, 180);
			userRepo.save(user2);
			
			Workout workout1 = new Workout("30.03.2020", userRepo.findUserByUsername("admin"));
			Workout workout2 = new Workout("01.04.2020", userRepo.findUserByUsername("admin"));
			
			workoutRepo.save(workout1);
			workoutRepo.save(workout2);
			
			//String liftType, String notes, int sets, int reps, int weight, Workout
			liftRepo.save(new GymLift("Squat", "High-bar, no belt, knee sleeves", 3, 8, 90, workoutRepo.findWorkoutById(workout1.getId())));
			liftRepo.save(new GymLift("Deadlift", "Conventional, with belt, no straps", 5, 3, 160, workoutRepo.findWorkoutById(workout2.getId())));
			liftRepo.save(new GymLift("Bench press", "With pause, normal grip width", 4, 6, 80, workoutRepo.findWorkoutById(workout1.getId())));
			liftRepo.save(new GymLift("OHP", "Strict, raw", 3, 8, 50, workoutRepo.findWorkoutById(workout2.getId())));
		};
	}

}
