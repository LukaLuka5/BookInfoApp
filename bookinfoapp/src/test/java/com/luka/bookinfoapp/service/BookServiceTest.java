package com.luka.bookinfoapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.luka.bookinfoapp.dao.BookDao;
import com.luka.bookinfoapp.models.Book;
import com.luka.bookinfoapp.models.User;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
	
	@Mock
	BookDao bookDao;
	@Mock
	UserService userService;
	@InjectMocks
	BookService bookService;
	
	Book book;
	
	@BeforeEach
	void setUp() {
		book = new Book();
		book.setId(1l);
	}
	
	@Test
	void testAddBook() {
		User user = new User();

		when(userService.getCurrentLoggedInUser()).thenReturn(user);
		when(bookDao.save(any(Book.class))).thenReturn(book);
		
		Book returnedBook = bookService.addBook(book);
		
		assertEquals(book.getId(), returnedBook.getId(),"The book id that was returned doesnt match actual book id");
		assertEquals(book.getUserWhoAddedTheBook(), returnedBook.getUserWhoAddedTheBook(),"Expected user object doesnt match returned user object");
	}

	@Test
	void testGetBookById() {
		when(bookDao.findById(1l)).thenReturn(Optional.of(book));
		Book returnedBook = bookService.getBookById(1l);
		
		assertEquals(book, returnedBook);
	}

	@Test
	void testGetAllBooks() {
		when(bookDao.findAll()).thenReturn(Lists.newArrayList(new Book("title1", "author1", null, null),new Book("title2", "author2", null, null)));
		
		List<Book> books = bookService.getAllBooks();
		
		assertEquals(2, books.size() ,"Size of the list books doesnt match the specified size");
		
	}

	@Test
	void testUpdateBook() {
		Book updatedBook = new Book();
		updatedBook.setTitle("Updated Title");
		
		when(bookDao.findById(1l)).thenReturn(Optional.of(book));
		
		Book returnedBook = bookService.updateBook(updatedBook, 1l);
		
		assertEquals("Updated Title", returnedBook.getTitle());
		verify(bookDao).save(any(Book.class));
	}
	
	@Test
	void testDeleteBook() {
		String result = bookService.deleteBook(1l);
		assertEquals("Deleted Book with id = 1", result);
	}

	@Test
	void testCheckIfTitleExists() {
		when(bookDao.findBookByTitle("Test title")).thenReturn(Optional.of(book));
		
		Boolean result = bookService.checkIfTitleExists("Test title");
		
		assertTrue(result);
	}

	@Test
	void testFindAllBooksByUser() {
		User user = new User();
		
		when(bookDao.findBooksByUserWhoAddedTheBook(user)).thenReturn(Lists.newArrayList(new Book(),new Book()));
		
		List<Book> books = bookService.findAllBooksByUser(user);
		
		assertEquals(2, books.size(),"Size of the list books doesnt match the specified size");
	}

}
