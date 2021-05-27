package com.luka.bookinfoapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luka.bookinfoapp.models.User;

public interface UserDao extends JpaRepository<User, Long>{

}
