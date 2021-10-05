package com.luka.bookinfoapp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luka.bookinfoapp.models.Book;
import com.luka.bookinfoapp.models.Comment;
import com.luka.bookinfoapp.models.User;

public interface CommentDao extends JpaRepository<Comment, Long> {
	public List<Comment> findCommentsByBook(Book book);
	public Optional<Comment> findCommentByBook(Book book);  
	public List<Comment> findCommentsByUser(User user);
}
