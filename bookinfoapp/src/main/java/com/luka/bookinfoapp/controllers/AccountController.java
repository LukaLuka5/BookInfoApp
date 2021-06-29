package com.luka.bookinfoapp.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.luka.bookinfoapp.models.Book;
import com.luka.bookinfoapp.models.Comment;
import com.luka.bookinfoapp.models.User;
import com.luka.bookinfoapp.service.BookService;
import com.luka.bookinfoapp.service.CommentService;
import com.luka.bookinfoapp.service.UserService;

@Controller
public class AccountController {
	
	private UserService userService;
	private BookService bookService;
	private CommentService commentService;
	
	public AccountController(UserService userService, BookService bookService, CommentService commentService) {
		this.userService = userService;
		this.bookService = bookService;
		this.commentService = commentService;
	}
	
	
	@GetMapping("/login")
	public String login() {
		return "loginPage";
	}
	
	@GetMapping("/register")
	public String register(User user, Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@GetMapping("/profile")
	public String showProfile(Model model) {
		User user = userService.getCurrentLoggedInUser();
		model.addAttribute("user", user);
		
		List<Book> books = bookService.findAllBooksByUser(user);
		model.addAttribute("books", books);
		
		List<Comment> userComments = commentService.findAllCommentsByUser(user);
		model.addAttribute("userComments", userComments);
		return "profile";
	}
}
