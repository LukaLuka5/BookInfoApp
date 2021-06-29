package com.luka.bookinfoapp.service;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luka.bookinfoapp.dao.CommentDao;
import com.luka.bookinfoapp.models.Book;
import com.luka.bookinfoapp.models.Comment;
import com.luka.bookinfoapp.models.User;


@Service
public class CommentService {
	private CommentDao commentDao;
	private UserService userService;
	private BookService bookService;

	public CommentService(CommentDao commentDao, UserService userService,BookService bookService) {
		this.commentDao = commentDao;
		this.userService = userService;
		this.bookService = bookService;
	}
	
	public Comment addComment(Comment comment) {
		//Get current user that is logged in and set it in the comment entity.
		//This represents user who posted a comment.
		User user = userService.getCurrentLoggedInUser();
		comment.setUser(user);
		return commentDao.save(comment);
	}
	@Transactional(readOnly = true)
	public Comment getCommentById(Long id) {
		return commentDao.findById(id).orElse(null);
	}
	@Transactional(readOnly = true)
	public List<Comment> getAllCommentsForBook(Long bookId){
		Book book = bookService.getBookById(bookId);
		return commentDao.findCommentsByBook(book);
	}
	/* 
	//when editing a book, find comment 
	@Transactional(readOnly = true)
	public Comment findCommentObjectByBook(Book book) {
		Optional<Comment> commentOpt = commentDao.findCommentByBook(book);
		return commentOpt.orElseThrow(() -> new RuntimeException("Book id doesnt exists!"));
	}
	*/
	public List<Comment> findAllCommentsByUser(User user){
		return commentDao.findCommentsByUser(user);
	}
	
	public Comment updateComment(Comment updatedComment, Long id) {
		Optional<Comment> commentOpt = commentDao.findById(id);
		Comment comment = commentOpt.get();
		//setters
		comment.setCommentText(updatedComment.getCommentText());
		
		commentDao.save(comment);
		return comment;
		
	}
	public String deleteComment(Long id) {
		commentDao.deleteById(id);
		return "Deleted Comment with id = " + id;
	}
	
	
}
