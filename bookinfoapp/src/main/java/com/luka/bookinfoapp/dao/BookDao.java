package com.luka.bookinfoapp.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luka.bookinfoapp.models.Book;
import com.luka.bookinfoapp.models.User;

public interface BookDao extends JpaRepository<Book, Long> {
	public Optional<Book> findBookByTitle(String title);
	//find all books that user added to database
	public List<Book> findBooksByUserWhoAddedTheBook(User user);
}
