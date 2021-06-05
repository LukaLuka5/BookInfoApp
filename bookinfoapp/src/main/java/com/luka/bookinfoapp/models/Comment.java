package com.luka.bookinfoapp.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Comment {
	@Id
	@GeneratedValue
	private Long id;
	private String commentText;
	@ManyToOne
	@JoinColumn(name="user_id", nullable = false)
	private User user;
	@ManyToOne
	@JoinColumn(name="book_id", nullable = false)
	private Book book;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	
	public Comment() {	
		
	}
	
	public Comment(String commentText, User user, Book book) {
		this.commentText = commentText;
		this.user = user;
		this.book = book;
	}
	@Override
	public String toString() {
		return "Comment [id=" + id + ", commentText=" + commentText + ", user=" + user + ", book=" + book + "]";
	}
	
	
	
	
	
	
}
