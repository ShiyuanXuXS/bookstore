package com.shiyuan.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shiyuan.bookstore.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

    public List<Book> findAllByOrderByTitleAsc();
}
