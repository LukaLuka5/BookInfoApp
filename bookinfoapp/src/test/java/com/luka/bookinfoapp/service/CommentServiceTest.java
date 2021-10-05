package com.luka.bookinfoapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.luka.bookinfoapp.dao.CommentDao;
import com.luka.bookinfoapp.models.Book;
import com.luka.bookinfoapp.models.Comment;
import com.luka.bookinfoapp.models.User;

import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
	
	@Mock
	CommentDao commentDao;
	@Mock
	UserService userService;
	@Mock
	BookService bookService;
	@InjectMocks
	CommentService commentService;
	
	Comment comment;
	
	@BeforeEach
	void setUp() {
		comment = new Comment();
		comment.setId(1l);
	}

	@Test
	void testAddComment() {
		User user = new User();
		
		when(userService.getCurrentLoggedInUser()).thenReturn(user);
		when(commentDao.save(any(Comment.class))).thenReturn(comment);
		
		Comment returnedComment = commentService.addComment(comment);
		
		assertEquals(comment, returnedComment,"Comment objects dont match");
		assertEquals(user, returnedComment.getUser(),"Users dont match");
	}

	@Test
	void testGetCommentById() {
		when(commentDao.findById(any(Long.class))).thenReturn(Optional.of(comment));
		
		Comment result = commentService.getCommentById(any(Long.class));
		
		assertEquals(comment.getId(), result.getId(),"Id's dont match");
		
	}
	
	@Test
	void testGetAllCommentsForBook() {
		Book book = new Book();
		book.setComments(Lists.newArrayList(new Comment(),new Comment()));
		
		when(bookService.getBookById(any(Long.class))).thenReturn(book);
		when(commentDao.findCommentsByBook(any(Book.class))).thenReturn(book.getComments());
		
		List<Comment> commentsResult = commentService.getAllCommentsForBook(1l);
		
		assertEquals(2, commentsResult.size(),"Size of list doesnt match the expected size");
	}

	@Test
	@DisplayName("Test findAll comments posted by user")
	void testFindAllCommentsByUser() {
		User user = new User();
		user.setComments(Lists.newArrayList(new Comment(),new Comment()));
		
		when(commentDao.findCommentsByUser(user)).thenReturn(user.getComments());
		
		List<Comment> commentsResult = commentService.findAllCommentsByUser(user);
		
		assertEquals(2, commentsResult.size(),"Size of list doesnt match the expected size");
	}

	@Test
	void testUpdateComment() {
		Comment updatedComment = new Comment();
		updatedComment.setCommentText("Changed the text of a comment");
		
		when(commentDao.findById(1l)).thenReturn(Optional.of(comment));

		Comment result = commentService.updateComment(updatedComment,1l);
		
		assertEquals("Changed the text of a comment", result.getCommentText(),"Text inside comments dont match");
		verify(commentDao).save(comment);
	}

	@Test
	void testDeleteComment() {
		String result = commentService.deleteComment(1l);
		assertEquals("Deleted Comment with id = 1", result);
	}

}
