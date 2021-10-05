package com.luka.bookinfoapp.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@ExtendWith(MockitoExtension.class)
class MainControllerTest extends ViewResolverConfig{

	@Mock
	MainController mainController;
	
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp() {

		mockMvc = MockMvcBuilders.standaloneSetup(mainController).setViewResolvers(super.getViewResolver()).build();
	}
	
	@Test
	void testIndex() throws Exception {
		mockMvc.perform(get("/index"))
			.andExpect(status().isOk())
			.andExpect(view().name("index"));
	}

}
