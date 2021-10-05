package com.luka.bookinfoapp.controllers;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.luka.bookinfoapp.models.Book;
import com.luka.bookinfoapp.models.Comment;
import com.luka.bookinfoapp.models.User;
import com.luka.bookinfoapp.service.BookService;
import com.luka.bookinfoapp.service.CommentService;
import com.luka.bookinfoapp.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.assertj.core.util.Lists;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest extends ViewResolverConfig{

	@Mock
	UserService userService;
	@Mock
	BookService bookService;
	@Mock
	CommentService commentService;
	@InjectMocks
	AccountController accountController;
	
	MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(accountController).setViewResolvers(super.getViewResolver()).build();
	}
	
	@Test
	void testLogin() throws Exception {
		mockMvc.perform(get("/login"))
			.andExpect(status().isOk())
			.andExpect(view().name("loginPage"));
	}

	@Test
	void testRegister() throws Exception {
		mockMvc.perform(get("/register"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("user"))
			.andExpect(view().name("register"));
	}

	@Test
	void testShowProfile() throws Exception {
		User userExpected = new User();
		userExpected.setBooks(Lists.newArrayList(new Book(),new Book()));
		userExpected.setComments(Lists.newArrayList(new Comment(),new Comment()));
		
		when(userService.getCurrentLoggedInUser()).thenReturn(userExpected);
		when(bookService.findAllBooksByUser(userExpected)).thenReturn(userExpected.getBooks());
		when(commentService.findAllCommentsByUser(userExpected)).thenReturn(userExpected.getComments());
		
		mockMvc.perform(get("/profile"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("user","books","userComments"))
			.andExpect(view().name("profile"));
		
		
		
	}

}
