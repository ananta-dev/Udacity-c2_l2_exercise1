package com.udacity.jdnd.course3.lesson2;

import com.udacity.jdnd.course3.lesson2.entity.Book;
import com.udacity.jdnd.course3.lesson2.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

@SpringBootApplication
@EnableJpaRepositories
public class BookTest {

    private static final String PERSISTENCE_UNIT_NAME = "Order";

    private static final Logger log = LoggerFactory.getLogger(BookTest.class);

    public static void main(String[] args) {
        SpringApplication.run(BookTest.class, args);
    }

    @Bean
    public CommandLineRunner bookDemo(BookRepository repository) {
        return (args) -> {


            // STEP 1: Create a factory for the persistence unit
            EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

            // STEP 2: Create an EntityManager
            EntityManager em = factory.createEntityManager();

            // STEP 3: Start a transaction
            em.getTransaction().begin();

            // STEP 4: Create an order (entity is in Transient state)
            Book book = new Book();
            book.setTitle("Pride and Prejudice");
            book.setIsbn("9781986431484");

            book = repository.save(book);
            System.err.println("Saved Book ID: " + book.getBookId());

            Optional<Book> bookRead = repository.findByTitle("Pride and Prejudice");
            // bookRead.ifPresent(value -> System.err.println("Found by Title - Book: " + value));
        };
    }
}