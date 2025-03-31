package vn.edu.hust.nmcnpm_20242_n3.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.hust.nmcnpm_20242_n3.entity.Book;
import vn.edu.hust.nmcnpm_20242_n3.repository.BookRepository;


@RestController
@RequestMapping("/api/books")
public class BookController {
    BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    ResponseEntity<Iterable<Book>> getAllBooks() {
        return ResponseEntity.ok(bookRepository.findAll());
    }
}
