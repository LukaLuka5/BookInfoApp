package com.luka.bookinfoapp.controllers;

import java.util.List;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luka.bookinfoapp.models.Book;
import com.luka.bookinfoapp.models.Comment;
import com.luka.bookinfoapp.service.BookService;
import com.luka.bookinfoapp.service.CommentService;

@Controller
public class CommentController {
	
	private CommentService commentService;
	private BookService bookService;
	
	public CommentController(CommentService commentService, BookService bookService) {
		this.commentService = commentService;
		this.bookService = bookService;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping("/addComment")
	public String addCommentPage(@RequestParam("bookId") Long bookId, Comment comment, Model model) {
		 
		Book book = bookService.getBookById(bookId);
		model.addAttribute("book", book);
		model.addAttribute("comment", comment);

		return "/addComment";
	}
	@PostMapping("/addComment")
	public String addComment(@RequestParam("bookId") Long bookId,@ModelAttribute Comment comment, Errors errors, RedirectAttributes redAttributes) {
		Book book = bookService.getBookById(bookId);
		comment.setBook(book);
		
		if(comment.getCommentText() == null) {
			redAttributes.addFlashAttribute("Error", "Comment text can not be empty");
			return "redirect:/addComment?bookId="+ bookId + "&error";
		}
		
		commentService.addComment(comment);
		return "redirect:/addComment?bookId="+ bookId + "&commentAdded=true";
	}
	@GetMapping("comments")
	public String showAllCommentsForBook(@RequestParam("bookId") Long bookId, Model model) {
		Book book = bookService.getBookById(bookId);
		model.addAttribute("book", book);
		List<Comment> comments =  commentService.getAllCommentsForBook(bookId);
		model.addAttribute("listOfComments", comments);
		return "/comments";
	}
	
	@GetMapping("/editComment")
	public String editCommentPage(Model model, @RequestParam() Long commentId) {
		
		Comment comment = commentService.getCommentById(commentId);		
		model.addAttribute("comment", comment);
		
		Book book = comment.getBook();
		model.addAttribute("book", book);
		return "/editComment";
	}
	
	@PostMapping("/editComment")
	public String editComment(@ModelAttribute Comment comment, @RequestParam Long commentId ) {
		
		if (comment.getCommentText() == null) {
			return "redirect:/editComment?error&commentId=" + commentId;
		}
		commentService.updateComment(comment, commentId);
		return "redirect:/editComment?commentEditSuccess&commentId="+commentId;
	}
	
	@PostMapping("/removeComment")
	public String removeComment(@RequestParam Long commentId) {
		commentService.deleteComment(commentId);
		return "redirect:/profile?commentRemovedSuccess";
	}
}
