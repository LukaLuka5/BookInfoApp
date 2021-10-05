package com.luka.bookinfoapp.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


import com.luka.bookinfoapp.models.User;

public interface UserDao extends JpaRepository<User, Long>{	
	public Optional<User> findUserByEmail(String email);
	@EntityGraph(attributePaths = {"roles"})
	public Optional<User> findUserByUsername(String username);
}
