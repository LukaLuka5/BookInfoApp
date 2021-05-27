package com.luka.bookinfoapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luka.bookinfoapp.models.Book;

public interface BookDao extends JpaRepository<Book, Long> {

}
