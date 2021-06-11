package com.luka.bookinfoapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luka.bookinfoapp.models.Comment;

public interface CommentDao extends JpaRepository<Comment, Long> {

}
