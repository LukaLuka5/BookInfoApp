package com.luka.bookinfoapp.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Book {
	
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String author;
	@OneToMany(mappedBy = "book")
	private List<Comment> comments;
	
	/** User who added a book on the web app */
	@ManyToOne
	@JoinColumn(name="user_id", nullable = false)
	private User userWhoAddedTheBook;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public User getUserWhoAddedTheBook() {
		return userWhoAddedTheBook;
	}
	public void setUserWhoAddedTheBook(User userWhoAddedTheBook) {
		this.userWhoAddedTheBook = userWhoAddedTheBook;
	}
	
	public Book() {

	}
	
	public Book(String name, String author, List<Comment> comments, User userWhoAddedTheBook) {
		this.name = name;
		this.author = author;
		this.comments = comments;
		this.userWhoAddedTheBook = userWhoAddedTheBook;
	}
	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", author=" + author + ", comments=" + comments + ", user=" + userWhoAddedTheBook
				+ "]";
	}
	
	

	
	
	
	
	
	
	
	
	
}
