package org.example.library.seeders;

import org.example.library.models.entities.Author;
import org.example.library.models.entities.Book;
import org.example.library.models.entities.Genre;
import org.example.library.models.enums.EGenre;
import org.example.library.repositories.AuthorRepository;
import org.example.library.repositories.BookRepository;
import org.example.library.repositories.GenreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class DBSeeder implements CommandLineRunner {

    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;


    public DBSeeder(GenreRepository genreRepository, AuthorRepository authorRepository, BookRepository bookRepository) {
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (bookRepository.count() == 0 && authorRepository.count() == 0) {
            seedGenres();
            initAuthor();
        }

        for (Book book : bookRepository.findAll()) {
            System.out.println(book.getGenre());
        }
    }
    public void initAuthor() {
        List<Book> allBooks = new ArrayList<>();
        List<Book> allBooks1 = new ArrayList<>();

        // Вземане на всички жанрове от енумерацията
        Genre actionGenre = genreRepository.findByGenreType(EGenre.ACTION)
                .orElseThrow(() -> new RuntimeException("Genre ACTION not found"));
        Genre crimeGenre = genreRepository.findByGenreType(EGenre.CRIME)
                .orElseThrow(() -> new RuntimeException("Genre CRIME not found"));
        Genre dramaGenre = genreRepository.findByGenreType(EGenre.DRAMAS)
                .orElseThrow(() -> new RuntimeException("Genre DRAMAS not found"));
        Genre horrorGenre = genreRepository.findByGenreType(EGenre.HORROR)
                .orElseThrow(() -> new RuntimeException("Genre HORROR not found"));
        Genre fantasyGenre = genreRepository.findByGenreType(EGenre.FANTASY)
                .orElseThrow(() -> new RuntimeException("Genre FANTASY not found"));
        Genre sportsGenre = genreRepository.findByGenreType(EGenre.SPORTS)
                .orElseThrow(() -> new RuntimeException("Genre SPORTS not found"));

        // Създаване на първия автор
        Author author1 = new Author();
        author1.setName("George Orwell");
        author1.setDateOfBirth(LocalDate.of(1965, 5, 18));
        author1.setCountry("United Kingdom");
        author1.setRating(4.8f);

        // Добавяне на книги към първия автор
        // Добавяне на книги към първия автор
        String[] booksForAuthor1 = {"1984", "Animal Farm", "Homage to Catalonia"};
        int[] publicationYears1 = {1949, 1945, 1938};// Години на публикуване за всяка книга
        BigDecimal[] pricesForAuthor1 = {BigDecimal.valueOf(19.99), BigDecimal.valueOf(15.49), BigDecimal.valueOf(22.75)};

        for (int i = 0; i < booksForAuthor1.length; i++) {
            Book book = new Book();

            book.setAuthor(author1);
            book.setTitle(booksForAuthor1[i]);

            // Задаване на фиксирана година
            book.setPublicationYear(publicationYears1[i]);
            book.setPrice(pricesForAuthor1[i]);
            book.setIsbn(UUID.randomUUID().toString());

            // Добавяне на различни жанрове
            book.getGenre().add(actionGenre); // Добавяне на жанр "ACTION"
            book.getGenre().add(crimeGenre);  // Добавяне на жанр "CRIME"
            allBooks.add(book);
        }

        author1.setBooks(allBooks);

        // Създаване на втория автор
        Author author2 = new Author();
        author2.setName("J.K. Rowling");
        author2.setDateOfBirth(LocalDate.of(1924, 8, 20));
        author2.setCountry("United Kingdom");
        author2.setRating(4.9f);

        // Добавяне на книги към втория автор
        String[] booksForAuthor2 = {"Harry Potter and the Philosopher's Stone", "Harry Potter and the Chamber of Secrets", "The Running Grave"};
        int[] publicationYears = {1997, 1998, 2023}; // Години на публикуване за всяка книга
        BigDecimal[] pricesForAuthor2 = {BigDecimal.valueOf(29.99), BigDecimal.valueOf(24.99), BigDecimal.valueOf(34.99)};
        for (int i = 0; i < booksForAuthor2.length; i++) {
            Book book1 = new Book();

            book1.setAuthor(author2);
            book1.setTitle(booksForAuthor2[i]);

            // Задаване на фиксирана година
            book1.setPublicationYear(publicationYears[i]);
            book1.setPrice(pricesForAuthor2[i]);
            book1.setIsbn(UUID.randomUUID().toString());

            book1.getGenre().add(fantasyGenre);  // Добавяне на жанр "FANTASY"
            book1.getGenre().add(sportsGenre);   // Добавяне на жанр "SPORTS"

            allBooks.add(book1);
        }
        author2.setBooks(allBooks);

        // Запазване на авторите и книгите в базата данни
        authorRepository.save(author1);
        authorRepository.save(author2);
        bookRepository.saveAll(allBooks);
    }
    private void seedGenres() {
        if (genreRepository.count() == 0) {
            for (EGenre value : EGenre.values()) {
                Genre genre = new Genre();
                genre.setGenreType(value);
                genreRepository.save(genre);
            }
        }
    }
}