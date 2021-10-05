package com.luka.bookinfoapp.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private UserDetailsService myUserDetailsService;
	
	@Autowired
    public SecurityConfig(UserDetailsService myUserDetailsService) {
		this.myUserDetailsService = myUserDetailsService;
	}

	@Override
    /*This tells spring security to ignore any request which path starts with specified pattern*/
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
            .antMatchers("/resources/**", "/static/**","/webjars/**");
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/index").permitAll()
			.antMatchers("/books").permitAll()
			.antMatchers("/register").permitAll()
			.antMatchers("/createUser").permitAll()
			.antMatchers("/login").permitAll()
			.antMatchers("/about").permitAll()
			.antMatchers("/comments").permitAll()
			.antMatchers("/search").permitAll()
			.antMatchers("/").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/login").defaultSuccessUrl("/index",true).permitAll()
				.and().logout().logoutSuccessUrl("/login?logout");		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService).passwordEncoder(getPasswordEncoder());
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	




}
