package com.luka.bookinfoapp.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Entity
@Indexed(index = "book_index")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message="Title can't be empty")
	@Field
	private String title;
	@NotBlank(message="Author name can't be empty")
	@Field
	private String author;
	@OneToMany(mappedBy = "book", cascade=CascadeType.ALL)
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	
	public Book(String title, String author, List<Comment> comments, User userWhoAddedTheBook) {
		this.title = title;
		this.author = author;
		this.comments = comments;
		this.userWhoAddedTheBook = userWhoAddedTheBook;
	}
	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", comments=" + comments + ", user=" + userWhoAddedTheBook
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



	
	
	
	
	
	
	
	
	
}
