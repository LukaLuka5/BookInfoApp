package com.luka.bookinfoapp.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luka.bookinfoapp.models.Book;

@Service
public class SearchService {
	
	  private final EntityManager entityManager;
	  
	  public SearchService(EntityManager entityManager) {
		  this.entityManager = entityManager;
	  }
	  
	  @SuppressWarnings("unchecked")
	  @Transactional
	  public List<Book> getBookBasedOnWord(String word){
	    FullTextEntityManager fullTextEntityManager = 
	      Search.getFullTextEntityManager(entityManager);

	    QueryBuilder bookQb = fullTextEntityManager
	      .getSearchFactory()
	      .buildQueryBuilder()
	      .forEntity(Book.class)
	      .get();

	    Query bookQuery = bookQb.keyword()
	      .onFields("title","author")
	      .matching(word)
	      .createQuery();

	    List<Book> books = null;
	    
	    
	    FullTextQuery fullTextQuery = fullTextEntityManager
	      .createFullTextQuery(bookQuery, Book.class);
	    
	    books = fullTextQuery.getResultList();
	    
	    return books;
	  }
}
