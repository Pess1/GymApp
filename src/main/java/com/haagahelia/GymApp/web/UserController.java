package com.haagahelia.GymApp.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.haagahelia.GymApp.domain.EditRoleForm;
import com.haagahelia.GymApp.domain.EditUserForm;
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
	
	@RequestMapping(value = "/edituser/{username}")
	public String editUser(@PathVariable("username") String username, Model model) {
		User user = userRepo.findUserByUsername(username);
		EditUserForm editUserForm = new EditUserForm();
		
		editUserForm.setEmail(user.getEmail());
		editUserForm.setUsername(user.getUsername());
		editUserForm.setHeight(user.getHeight());
		editUserForm.setWeight(user.getWeight());
		editUserForm.setRole(user.getRole());
		
		model.addAttribute("edituserform", editUserForm);
		
		return "edituser";
	}
	
	@RequestMapping(value = "/edituserrole/{id}")
	public String editUserRole(@PathVariable("id") Long id, Model model) {
		User user = userRepo.findUserByUserid(id);
		EditRoleForm editRoleForm = new EditRoleForm();
		
		editRoleForm.setRole(user.getRole());
		editRoleForm.setId(user.getUserId());
		editRoleForm.setUsername(user.getUsername());

		model.addAttribute("editroleform", editRoleForm);
		
		return "edituserrole";
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
			newUser.setGender(signupForm.getGender());
			
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
	
	@RequestMapping(value = "/saveuseredit/{username}", method = RequestMethod.POST)
	public String saveEditedUser(@Valid @ModelAttribute("edituserform") EditUserForm editUserForm, BindingResult bindingResult, 
		@PathVariable("username") String username) {
		
		if (!bindingResult.hasErrors()) {
			User user = userRepo.findUserByUsername(username);
			
			BCryptPasswordEncoder bcEncoder = new BCryptPasswordEncoder();
			
			String password = editUserForm.getPassword();
			boolean passwordValid = BCrypt.checkpw(password, user.getPasswordHash());
			password = "";
			
			System.out.println(passwordValid);
			
			if(passwordValid == true) {
				String newPassword = editUserForm.getNewPassword();
				String newHashedPass = bcEncoder.encode(newPassword);
				
				if(newPassword.length() < 4 && (newPassword.isEmpty()) == false) {
					bindingResult.rejectValue("newPassword", "err.newPassword", "New Password too short. Must be at least 4 characters");
					return "redirect:../edituser/" + user.getUsername();
				}
				
				user.setEmail(editUserForm.getEmail());
				user.setHeight(editUserForm.getHeight());
				user.setWeight(editUserForm.getWeight());
				user.setRole(editUserForm.getRole());
				
				if (newPassword.isEmpty() || newPassword.isBlank()) {
					userRepo.save(user);
				} else {
					user.setPasswordHash(newHashedPass);
					userRepo.save(user);
				}
			} else {
				bindingResult.rejectValue("password", "err.password", "Password does not match");
				return "redirect:../edituser/" + user.getUsername();
			}

		}
		return ("redirect:../profile/" + username);
	}
	
	@RequestMapping(value = "/saveroleedit/{id}", method = RequestMethod.POST)
	public String saveRoleEdit(@Valid @ModelAttribute("editroleform") EditRoleForm editRoleForm, BindingResult bindingResult, @PathVariable("id") Long id) {
		if(!bindingResult.hasErrors()) {
			User user = userRepo.findUserByUserid(id);
			
			user.setRole(editRoleForm.getRole());
			
			userRepo.save(user);
			
		} else {
			return "redirect:../edituserrole/" + id;
		}
		
		return "redirect:../allusers";
	}
	
	@RequestMapping(value = "/deleteuser/{id}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable("id") Long id) {
		userRepo.deleteById(id);
		
		return "redirect:../allusers";
	}
	
}
