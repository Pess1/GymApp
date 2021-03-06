package com.haagahelia.GymApp.web;

import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.haagahelia.GymApp.domain.GymLift;
import com.haagahelia.GymApp.domain.GymLiftRepository;
import com.haagahelia.GymApp.domain.LiftForm;
import com.haagahelia.GymApp.domain.Workout;
import com.haagahelia.GymApp.domain.WorkoutRepository;

@Controller
public class LiftController {
	
	private final AtomicLong counter = new AtomicLong();
	
	@Autowired
	private GymLiftRepository liftRepo;
	
	@Autowired
	private WorkoutRepository workoutRepo;
	
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
	
	//Controller for deleting a lift
	@RequestMapping(value = "/deletelift/{id}/{workoutId}", method = RequestMethod.GET)
	public String deleteLift(@PathVariable("id") Long id, @PathVariable("workoutId") Long workoutId, Model model) {
		liftRepo.deleteById(id);
		return "redirect:../../workout/" + workoutId;
	}

}
