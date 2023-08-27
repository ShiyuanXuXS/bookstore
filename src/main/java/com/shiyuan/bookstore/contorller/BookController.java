package com.shiyuan.bookstore.contorller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shiyuan.bookstore.entity.Book;
import com.shiyuan.bookstore.service.BookService;

@Controller
@RequestMapping("books")
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String getAllBooks(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "books-list";
    }

    @GetMapping("/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "book-form";
    }

    @GetMapping("/update")
    public String updateBook(Model model, @RequestParam("bookId") int bookId) {
        Book book = bookService.findById(bookId);
        model.addAttribute("book", book);
        return "book-form";
    }

    @PostMapping("/save")
    public String addBook(@ModelAttribute("book") Book book) {
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam("bookId") int bookId) {
        bookService.deleteById(bookId);
        return "redirect:/books";
    }

}
