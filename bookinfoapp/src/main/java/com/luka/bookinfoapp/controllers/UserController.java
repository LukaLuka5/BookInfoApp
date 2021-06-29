package com.luka.bookinfoapp.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luka.bookinfoapp.models.User;
import com.luka.bookinfoapp.service.UserService;

@Controller
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
		dataBinder.setAllowedFields("username","password","email");
	}
	
	
	@PostMapping("/createUser")
	public String createUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,RedirectAttributes attributes) {
		if(userService.checkIfEmailExists(user.getEmail())){
			bindingResult.addError(new FieldError("user", "email", "Email already in use"));
		}
		if(userService.checkIfUsernameExists(user.getUsername())){
			bindingResult.addError(new FieldError("user", "username", "Username already in use"));
		}
		
		if (bindingResult.hasErrors()) {
			return "register";
		}
		
		userService.saveUser(user);
		attributes.addFlashAttribute("success", "You have registered successfully");
		return "redirect:/login";
	}
	@GetMapping("/users")
	public List<User> findAllUsers() {
		return userService.getAllUsers();
	}
	@GetMapping("/user/{id}")
	public User findUserById(@PathVariable Long id) {
		return userService.getUserById(id);
	}
	
	
}
