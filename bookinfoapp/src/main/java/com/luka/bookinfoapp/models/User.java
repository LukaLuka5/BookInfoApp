package com.luka.bookinfoapp.models;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.persistence.JoinColumn;


@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 100,unique = true)
	@NotBlank(message="Username can't be blank")
	private String username;
    @NotBlank(message="Password can't be empty")
    @Size(min = 4, message = "Password size must be at least 4 characters")
	private String password;
    @NotBlank(message="Email must not be empty")
    @Column(length = 100,unique = true)
	@Email(message="must be a valid Email adress")
	private String email;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "User_Role", 
            joinColumns = { @JoinColumn(name = "user_id",referencedColumnName="id") }, 
            inverseJoinColumns = { @JoinColumn(name = "role_id",referencedColumnName="id") }
        )
    private Set<Role> roles;
    //casscade types?
	@OneToMany(mappedBy = "user")
	private List<Comment> comments;
	@OneToMany(mappedBy = "userWhoAddedTheBook")
	private List<Book> books;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;	
	}	
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	public User() {
		super();
	}
	public User(String username, String password, String email,Set<Role> roles, List<Comment> comments, List<Book> books) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.roles = roles;
		this.comments = comments;
		this.books = books;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+"roles=" + roles +", comments=" + comments + ", books=" + books + "]";
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
