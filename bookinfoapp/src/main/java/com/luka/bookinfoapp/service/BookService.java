package com.luka.bookinfoapp.service;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luka.bookinfoapp.dao.BookDao;
import com.luka.bookinfoapp.models.Book;
import com.luka.bookinfoapp.models.User;

@Service
public class BookService {
	private BookDao bookDao;
	private UserService userService;

	public BookService(BookDao bookDao, UserService userService) {
		this.bookDao = bookDao;
		this.userService = userService;
	}
	
	public Book addBook(Book book) {
		//Get current user that is logged in and set it in the book entity.
		//This represents user who added the book to database.
		User user = userService.getCurrentLoggedInUser();
		book.setUserWhoAddedTheBook(user);

		return bookDao.save(book);
	}
	@Transactional(readOnly = true)
	public Book getBookById(Long id) {
		return bookDao.findById(id).orElse(null);
	}
	@Transactional(readOnly = true)
	public List<Book> getAllBooks(){
		return bookDao.findAll();
	}
	
	public Book updateBook(Book updatedBook, Long id) {
		Optional<Book> bookOpt = bookDao.findById(id);
		Book book = bookOpt.get();
		
		book.setTitle(updatedBook.getTitle());
		book.setAuthor(updatedBook.getAuthor());
		
		bookDao.save(book);
		return book;
		
	}
	public String deleteBook(Long id) {
		bookDao.deleteById(id);
		return "Deleted Book with id = " + id;
	}
	@Transactional(readOnly = true)
	public boolean checkIfTitleExists(String title) {
		return bookDao.findBookByTitle(title).isPresent();
	}
	@Transactional(readOnly = true)
	//find all books that user added to database
	public List<Book> findAllBooksByUser(User user){
		return bookDao.findBooksByUserWhoAddedTheBook(user);
	}
}
