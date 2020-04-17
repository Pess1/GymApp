package com.haagahelia.GymApp.web;

import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.haagahelia.GymApp.domain.UserRepository;
import com.haagahelia.GymApp.domain.Workout;
import com.haagahelia.GymApp.domain.WorkoutForm;
import com.haagahelia.GymApp.domain.WorkoutRepository;
import com.haagahelia.GymApp.domain.GymLift;
import com.haagahelia.GymApp.domain.GymLiftRepository;
import com.haagahelia.GymApp.domain.LiftForm;
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
	
	//Controller for login page
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	//Profile page controller also controls adding a new workout
	@RequestMapping(value = "/profile/{username}", method = RequestMethod.GET)
	public String profileData(@PathVariable("username") String username, Model model) {
		User user = userRepo.findUserByUsername(username);
		WorkoutForm workoutForm = new WorkoutForm();
		
		workoutForm.setUser(user);
		
		model.addAttribute("workoutform", workoutForm);
		model.addAttribute("workouts", workoutRepo.findWorkoutsByUser(user));
		model.addAttribute("user", user);
		System.out.println(username);
		return "profile";
	}
	
	//Front page controller
	@RequestMapping(value = "/alllifts", method = RequestMethod.GET)
	public String allLifts(Model model) {
		model.addAttribute("lifts", liftRepo.findAll());
		return "alllifts";
	}
	
	//Controller for all users page for admins
	@RequestMapping(value = "/allusers", method = RequestMethod.GET)
	public String allUsers(Model model) {
		model.addAttribute("users", userRepo.findAll());
		return "allusers";
	}
	
	//Controller for adding a lift to an existing workout
	@RequestMapping(value = "/addlift/{id}", method = RequestMethod.GET)
	public String addLift(@PathVariable("id") Long id, Model model) {
		LiftForm liftForm = new LiftForm();
		Workout workout = workoutRepo.findWorkoutById(id);
		liftForm.setWorkout(workout);
		model.addAttribute("liftform", liftForm);
		
		return "addlift";
	}
	
	//Controller for saving a lift
	@RequestMapping(value = "/saveLift/{id}", method = RequestMethod.POST)
	public String saveLift(@Valid @ModelAttribute("liftform") LiftForm liftForm, BindingResult bindingResult, @PathVariable("id") Long id) {
		if(!bindingResult.hasErrors()) {
			GymLift lift = new GymLift();
			
			lift.setLiftType(liftForm.getLiftType());
			lift.setNotes(liftForm.getNotes());
			lift.setReps(liftForm.getReps());
			lift.setSets(liftForm.getSets());
			lift.setWeight(liftForm.getWeight());
			lift.setWorkout(liftForm.getWorkout());
				
			liftRepo.save(lift);
		} else {
			return ("redirect:../addlift/" + id);
		}
		
		return "redirect:../workout/" + id;
	}
	
	//Controller for saving the workout
	@RequestMapping(value = "/saveworkout", method = RequestMethod.POST)
	public String saveWorkout(@Valid @ModelAttribute("workoutform") WorkoutForm workoutForm, BindingResult bindingResult) {
		if(!bindingResult.hasErrors()) {
			Workout workout = new Workout();
			
			workout.setDate(workoutForm.getDate());
			workout.setUser(workoutForm.getUser());
			
			workoutRepo.save(workout);
			
		} else {
			return "redirect:profile/" + workoutForm.getUser().getUsername();
		}
		
		return "redirect:profile/" + workoutForm.getUser().getUsername();
	}
	
	//Controller for editing a lift
	@RequestMapping(value="/editlift/{id}")
	public String editLift(@PathVariable("id") Long id, Model model) {
		GymLift lift = liftRepo.findLiftById(id);
		LiftForm liftForm = new LiftForm();
		
		liftForm.setId(lift.getId());
		liftForm.setLiftType(lift.getLiftType());
		liftForm.setNotes(lift.getNotes());
		liftForm.setReps(lift.getReps());
		liftForm.setSets(lift.getSets());
		liftForm.setWeight(lift.getWeight());
		liftForm.setWorkout(lift.getWorkout());
		
		model.addAttribute("liftform", liftForm);
		return "editlift";
	}
	
	//Controller for saving an edited lift
	@RequestMapping(value="/save_edited_lift/{id}/{workoutId}", method = RequestMethod.POST)
	public String saveEditedLift(@Valid @ModelAttribute("liftform") LiftForm liftForm, BindingResult bindingResult, 
		@PathVariable("id") Long id, @PathVariable("workoutId") Long workoutId) {
		
		if(!bindingResult.hasErrors()) {
			GymLift lift = liftRepo.findLiftById(id);
			
			lift.setLiftType(liftForm.getLiftType());
			lift.setNotes(liftForm.getNotes());
			lift.setReps(liftForm.getReps());
			lift.setSets(liftForm.getSets());
			lift.setWeight(liftForm.getWeight());
			lift.setWorkout(liftForm.getWorkout());
			
			liftRepo.save(lift);
		} else {
			return "redirect:../../editlift/" + id;
		}

		return "redirect:../../workout/" + workoutId;
	}
	
	//Controller for showing lifts in a workout and editing workout date
	@RequestMapping(value="/workout/{id}")
	public String editWorkout(@PathVariable("id") Long id, Model model, Authentication authentication) {
		Workout workout = workoutRepo.findWorkoutById(id);
		
		WorkoutForm workoutForm = new WorkoutForm();
		workoutForm.setDate(workout.getDate());
		
		model.addAttribute("workoutform", workoutForm);
		model.addAttribute("workout", workout);
		model.addAttribute("lifts", liftRepo.findLiftsByWorkout(workout));
		return "workout";
	}
	
	//Controller for saving editedDate for workout
	@RequestMapping(value="/saveworkoutedit/{id}", method = RequestMethod.POST)
	public String saveWorkoutEdit(@Valid @ModelAttribute("workoutform") WorkoutForm workoutForm, BindingResult bindingResult,
		@PathVariable("id") Long id) {
		
		if(!bindingResult.hasErrors()) {
			Workout workout = workoutRepo.findWorkoutById(id);
			
			workout.setDate(workoutForm.getDate());
			
			workoutRepo.save(workout);
		} else {
			return "redirect:../workout/" + id;
		}
		
		return "redirect:../workout/" + id;
	}
	
	//Controller for deleting a lift
	@RequestMapping(value = "deletelift/{id}/{workoutId}", method = RequestMethod.GET)
	public String deleteLift(@PathVariable("id") Long id, @PathVariable("workoutId") Long workoutId, Model model) {
		liftRepo.deleteById(id);
		return "redirect:../../workout/" + workoutId;
	}
	
	//Controller for deleting a workout
	@RequestMapping(value = "deleteworkout/{id}", method = RequestMethod.GET)
	public String deleteWorkout(@PathVariable("id") Long id) {
		Workout workout = workoutRepo.findWorkoutById(id);
		String username = workout.getUser().getUsername();
		
		workoutRepo.deleteById(id);
		
		return "redirect:../profile/" + username;
	}

}
