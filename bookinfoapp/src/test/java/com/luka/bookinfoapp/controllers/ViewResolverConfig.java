package com.luka.bookinfoapp.controllers;

import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class ViewResolverConfig {

	private InternalResourceViewResolver viewResolver;
	
	public InternalResourceViewResolver getViewResolver() {
        viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:/templates/");
        viewResolver.setSuffix(".html");
		return viewResolver;
	}



}
