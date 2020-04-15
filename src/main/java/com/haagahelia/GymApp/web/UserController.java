package com.haagahelia.GymApp.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.haagahelia.GymApp.domain.SignupForm;
import com.haagahelia.GymApp.domain.User;
import com.haagahelia.GymApp.domain.UserRepository;

@Controller
public class UserController {
	@Autowired
	private UserRepository userRepo;
	
	@RequestMapping(value = "/signup")
	public String signup(Model model) {
		model.addAttribute("signupform", new SignupForm());
		return "signup";
	}
	
	
	/**
	*@param signupForm
	*@param bindingResult
	*@return
	*/
	
	@RequestMapping(value = "/saveuser", method = RequestMethod.POST)
	public String saveUser(@Valid @ModelAttribute("signupform") SignupForm signupForm, BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) {
			String password = signupForm.getPassword();
			BCryptPasswordEncoder bcEncoder = new BCryptPasswordEncoder();
			String hashPass = bcEncoder.encode(password);
			
			User newUser = new User();
			newUser.setPasswordHash(hashPass);
			newUser.setUsername(signupForm.getUsername());
			newUser.setRole(signupForm.getRole());
			newUser.setEmail(signupForm.getEmail());
			newUser.setHeight(signupForm.getHeight());
			newUser.setWeight(signupForm.getWeight());
			
			if (userRepo.findUserByUsername(signupForm.getUsername()) == null) {
				userRepo.save(newUser);
			} else {
				bindingResult.rejectValue("username", "err.username", "Username already exists");
				return "signup";
			}
			
		} else {
			return "signup";
		}
		
		return "redirect:/login";
	}
	
}
