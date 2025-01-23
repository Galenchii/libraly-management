package org.example.library.web;


import com.sun.source.tree.LambdaExpressionTree;
import jakarta.validation.Valid;
import org.example.library.models.dtos.BookDTO;
import org.example.library.models.entities.Book;
import org.example.library.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBook(@PathVariable("id") Long bookId, @RequestBody @Valid BookDTO bookDTO) {
        Long updateBookId = bookService.updateBook(bookId, bookDTO);

        if (updateBookId == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok("Book with id + " + updateBookId + "was updated successful.");
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        System.out.println("Fetching book with ID: " + id);
        Book book = bookService.findById(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else {
            return ResponseEntity.ok(books);
        }
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<BookDTO> getBookByTitle(@PathVariable("title") String title) {
        BookDTO bookDTO = bookService.findByTittle(title);
        if (bookDTO == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else {
            return ResponseEntity.ok(bookDTO);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<?> createBook(@RequestBody BookDTO bookDTO,
                                              UriComponentsBuilder uriComponentsBuilder) {

        Long newBookId = bookService.createBook(bookDTO);

        if (newBookId == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Author is not found"));

        } else {
            return ResponseEntity.created(uriComponentsBuilder
                            .path("/api/books/{id}")
                            .buildAndExpand(newBookId)
                            .toUri())
                    .body(bookDTO);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<BookDTO> deleteBookById(@PathVariable("id") Long bookId) {
        bookService.deleteBookById(bookId);

        return ResponseEntity.noContent().build();
    }
    }