package org.example.library.web;


import jakarta.validation.Valid;
import org.example.library.models.dtos.AuthorDTO;
import org.example.library.models.dtos.BookDTO;
import org.example.library.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/authors")
public class AuthorController
{

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        Long authorId = authorService.createAuthor(authorDTO);
        if (authorId != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors(){
        List<AuthorDTO> authorDTOS = authorService.getAllAuthors();
        if(authorDTOS.isEmpty()){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(authorDTOS);
        }
    }
}