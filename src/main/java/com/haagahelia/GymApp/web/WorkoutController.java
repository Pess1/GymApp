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

import com.haagahelia.GymApp.domain.GymLiftRepository;
import com.haagahelia.GymApp.domain.Workout;
import com.haagahelia.GymApp.domain.WorkoutForm;
import com.haagahelia.GymApp.domain.WorkoutRepository;

@Controller
public class WorkoutController {
	
	private final AtomicLong counter = new AtomicLong();
	
	@Autowired
	private GymLiftRepository liftRepo;
	
	@Autowired
	private WorkoutRepository workoutRepo;
	
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
	
	//Controller for deleting a workout
	@RequestMapping(value = "/deleteworkout/{id}", method = RequestMethod.GET)
	public String deleteWorkout(@PathVariable("id") Long id) {
		Workout workout = workoutRepo.findWorkoutById(id);
		String username = workout.getUser().getUsername();
			
		workoutRepo.deleteById(id);
			
		return "redirect:../profile/" + username;
	}
	
}
