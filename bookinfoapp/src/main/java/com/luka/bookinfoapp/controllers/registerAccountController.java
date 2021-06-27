package com.luka.bookinfoapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class registerAccountController {
	@RequestMapping("/register")
	public String register() {
		return "register";
	}
}
