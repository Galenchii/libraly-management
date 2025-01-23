package org.example.library.services;

import org.example.library.models.dtos.AuthorDTO;
import org.example.library.models.entities.Author;
import org.example.library.repositories.AuthorRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public AuthorService(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    public List<AuthorDTO> getAllAuthors() {
        return authorRepository
                .findAll()
                .stream()
                .map(author -> modelMapper.map(author, AuthorDTO.class))
                .collect(Collectors.toList());
    }

    //създаване на автор
    public Long createAuthor(AuthorDTO authorDTO) {

        Author author = modelMapper.map(authorDTO, Author.class);
        author.setBooks(new ArrayList<>());
        authorRepository.save(author);
      return author.getId();
    }


}
