package com.shiyuan.bookstore.service;

import java.util.List;

import com.shiyuan.bookstore.entity.Book;

public interface BookService {
    List<Book> findAll();

    Book findById(int id);

    void save(Book book);

    void deleteById(int id);
}
