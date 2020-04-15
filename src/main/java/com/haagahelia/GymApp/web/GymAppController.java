package com.haagahelia.GymApp.web;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.haagahelia.GymApp.domain.UserRepository;
import com.haagahelia.GymApp.domain.Workout;
import com.haagahelia.GymApp.domain.WorkoutRepository;
import com.haagahelia.GymApp.domain.GymLift;
import com.haagahelia.GymApp.domain.GymLiftRepository;
import com.haagahelia.GymApp.domain.User;

@Controller
public class GymAppController {
	
	private final AtomicLong counter = new AtomicLong();
	
	@Autowired
	private GymLiftRepository liftRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private WorkoutRepository workoutRepo;
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "/profile/{username}", method = RequestMethod.GET)
	public String profileData(@PathVariable("username") String username, Model model) {
		User user = userRepo.findUserByUsername(username);
		model.addAttribute("workouts", workoutRepo.findWorkoutsByUser(user));
		System.out.println(username);
		return "profile";
	}
	
	@RequestMapping(value = "/alllifts", method = RequestMethod.GET)
	public String profileData(Model model) {
		model.addAttribute("lifts", liftRepo.findAll());
		return "alllifts";
	}
	
	@RequestMapping(value = "/addlift/{id}", method = RequestMethod.GET)
	public String addLift(@PathVariable("id") Long id, Model model) {
		GymLift lift = new GymLift();
		Workout workout = workoutRepo.findWorkoutById(id);
		lift.setWorkout(workout);
		model.addAttribute("lift", lift);
		
		return "addlift";
	}
	
	@RequestMapping(value = "/addWorkout", method = RequestMethod.GET)
	public String addWorkout(Model model, Authentication authentication) {
		Workout workout = new Workout();
		User user = userRepo.findUserByUsername(authentication.getName());
		workout.setUser(user);
		model.addAttribute("workout", workout);
		
		return "addworkout";
	}
	
	@RequestMapping(value = "/saveLift/{id}", method = RequestMethod.POST)
	public String saveLift(@PathVariable("id") Long id, GymLift lift) {
		liftRepo.save(lift);
		return "redirect:../workout/" + id;
	}
	
	@RequestMapping(value = "/saveWorkout", method = RequestMethod.POST)
	public String saveWorkout(Workout workout) {
		workoutRepo.save(workout);
		User user = workout.getUser();
		String username = user.getUsername();
		return "redirect:profile/" + username;
	}
	
	@RequestMapping(value="/editlift/{id}")
	public String editLift(@PathVariable("id") Long id, Model model) {
		GymLift lift = liftRepo.findLiftById(id);
		model.addAttribute("workout", workoutRepo.findWorkoutById(lift.getWorkout().getId()));
		model.addAttribute("lift", lift);
		return "editlift";
	}
	
	@RequestMapping(value="/workout/{id}")
	public String editWorkout(@PathVariable("id") Long id, Model model, Authentication authentication) {
		Workout workout = workoutRepo.findWorkoutById(id);
		model.addAttribute("workout", workout);
		model.addAttribute("lifts", liftRepo.findLiftsByWorkout(workout));
		return "workout";
	}
	
	@RequestMapping(value = "deletelift/{id}/{workoutId}", method = RequestMethod.GET)
	public String deleteLift(@PathVariable("id") Long id, @PathVariable("workoutId") Long workoutId, Model model) {
		liftRepo.deleteById(id);
		return "redirect:../../workout/" + workoutId;
	}

}
