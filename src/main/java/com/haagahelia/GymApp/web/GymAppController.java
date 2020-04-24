package com.haagahelia.GymApp.web;

import java.text.DecimalFormat;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.haagahelia.GymApp.domain.UserRepository;
import com.haagahelia.GymApp.domain.WorkoutForm;
import com.haagahelia.GymApp.domain.WorkoutRepository;
import com.haagahelia.GymApp.domain.GymLift;
import com.haagahelia.GymApp.domain.GymLiftRepository;
import com.haagahelia.GymApp.domain.NewsRepository;
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
	
	@Autowired
	private NewsRepository newsRepo;
	
	private static DecimalFormat df = new DecimalFormat("0.00");
	
	//Controller for login page
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	//Front page controller
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String homePage(Model model, Authentication auth) {
		System.out.println(auth.getName());
		model.addAttribute("news", newsRepo.findAll());
		return "home";
	}
	
	//Profile page controller also controls adding a new workout
	@RequestMapping(value = "/profile/{username}", method = RequestMethod.GET)
	public String profileData(@PathVariable("username") String username, Model model, Authentication authentication) {
		User user = userRepo.findUserByUsername(username);
		
		if (authentication.getName().equals(user.getUsername())) {
			WorkoutForm workoutForm = new WorkoutForm();
			
			List<GymLift> lifts = liftRepo.findLiftsByWorkoutUser(user);
			GymLift squat = new GymLift();
			GymLift bench = new GymLift();
			GymLift deadlift = new GymLift();
			
			squat.setWeight(0);
			bench.setWeight(0);
			deadlift.setWeight(0);
			
			
			for (GymLift lift : lifts) {
				if(lift.getLiftType().equals("Squat")) {
					if(lift.getWeight() >= squat.getWeight()) {
						squat.setWeight(lift.getWeight());
						squat.setReps(lift.getReps());
						squat.setNotes(lift.getNotes());
					}
				}
				
				if(lift.getLiftType().equals("Bench")) {
					if(lift.getWeight() >= bench.getWeight()) {
						bench.setWeight(lift.getWeight());
						bench.setReps(lift.getReps());
						bench.setNotes(lift.getNotes());
					}
				}
				
				if(lift.getLiftType().equals("Deadlift")) {
					if(lift.getWeight() >= squat.getWeight()) {
						deadlift.setWeight(lift.getWeight());
						deadlift.setReps(lift.getReps());
						deadlift.setNotes(lift.getNotes());
					}
				}
			}
			
			int uW = user.getWeight();
			int total = squat.getWeight() + bench.getWeight() + deadlift.getWeight();
			Double wilks = 0.00;
			
			if(user.getGender().equals("Male")) {
				wilks = (total) * 500 / (-216.0475144 + (16.2606339 * uW) + (-0.002388645 * Math.pow(uW, 2)) + (-0.00113732 * Math.pow(uW, 3)) + 
						(7.01863E-06 * Math.pow(uW, 4)) + (-1.291E-08 * Math.pow(uW, 5)));
			} else {
				wilks = (total) * 500 / (594.31747775582 + (-27.23842536447 * uW) + (0.82112226871 * Math.pow(uW, 2)) + (-0.00930733913 * Math.pow(uW, 3)) + 
						(4.731582E-05 * Math.pow(uW, 4)) + (-9.054E-08 * Math.pow(uW, 5)));
			}
			workoutForm.setUser(user);
			
			model.addAttribute("workoutform", workoutForm);
			model.addAttribute("workouts", workoutRepo.findWorkoutsByUser(user));
			model.addAttribute("user", user);
			model.addAttribute("bestsquat", squat);
			model.addAttribute("bestbench", bench);
			model.addAttribute("bestdl", deadlift);
			model.addAttribute("wilks", df.format(wilks));
			model.addAttribute("total", total);
			System.out.println(username);
			
			return "profile";
			
		} else {
			return "redirect:../invalidprofile";
		}
	}

		
	
	//Controller for all users page for admins
	@RequestMapping(value = "/allusers", method = RequestMethod.GET)
	public String allUsers(Model model) {
		model.addAttribute("users", userRepo.findAll());
		return "allusers";
	}
	
	//Controller for the page displayed if an user tries to access profile of another user
	@RequestMapping(value = "/invalidprofile", method = RequestMethod.GET)
	public String invalidProfile() {
		return "invalidprofile";
	}

}
