package com.luka.bookinfoapp.controllers;

import java.util.List;

import javax.validation.Valid;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luka.bookinfoapp.models.Book;
import com.luka.bookinfoapp.models.Comment;
import com.luka.bookinfoapp.service.BookService;
import com.luka.bookinfoapp.service.CommentService;
import com.luka.bookinfoapp.service.SearchService;


@Controller
public class BookController {
	
	private BookService bookService;
	private CommentService commentService;
	private SearchService searchService;
		
	public BookController(BookService bookService, CommentService commentService, SearchService searchService) {
		this.bookService = bookService;
		this.commentService = commentService;
		this.searchService = searchService;
	}
	

	@GetMapping("/books")
	public String getBooks(Model model) {
		List<Book> books = bookService.getAllBooks();
		model.addAttribute("books", books);
		return "/books";
	}
	@GetMapping("/addBook")
	public String addBookPage(Book book,Comment comment,Model model) {
		model.addAttribute("book", book);
		model.addAttribute("comment", comment);
		return "/addBook";
	}
	
	@PostMapping("/addBook")
	public String addBook(@Valid @ModelAttribute("book") Book book,
						BindingResult bindingResult,
						@ModelAttribute("comment") Comment comment) {
		if(bookService.checkIfTitleExists(book.getTitle())){
			bindingResult.addError(new FieldError("book", "title", "Title already exists"));
		}
		if (bindingResult.hasErrors()) {
			return "/addBook";
		}
		Book savedBook = bookService.addBook(book);
		if(!comment.isCommentTextEmpty(comment.getCommentText())) {
			//setting id of the book that was added/saved to comment
			comment.setBook(savedBook);
			commentService.addComment(comment);
		}
		return "redirect:/addBook?bookAdded=true";
	}
	
	@GetMapping("/editBook")
	public String editBookPage(Book book,Model model, @RequestParam Long bookId) {
		book = bookService.getBookById(bookId);
		model.addAttribute("book", book);
		return "/editBook";
	}
	
	@PostMapping("/editBook")
	public String editBook(@Valid @ModelAttribute Book book, BindingResult bindingResult,@RequestParam Long bookId ) {
		
		if (bindingResult.hasErrors()) {
			return "/editBook";
		}
		bookService.updateBook(book, bookId);
		return "redirect:/editBook?bookEditSuccess&bookId=" + bookId;
	}
	
	@PostMapping("/removeBook")
	public String removeBook(@RequestParam Long bookId) {
		bookService.deleteBook(bookId);
		return "redirect:/profile?bookRemovedSuccess";
	}
	
	@GetMapping("/search")
	public String searchBooksBasedOnBookTitleAndAuthor(String word, Model model) {
		List<Book> books = searchService.getBookBasedOnWord(word);
		model.addAttribute("books", books);
		return "/searchResults";
	}
}
