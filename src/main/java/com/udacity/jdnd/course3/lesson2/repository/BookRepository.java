package com.udacity.jdnd.course3.lesson2.repository;

import com.udacity.jdnd.course3.lesson2.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

    Optional<Book> findByTitle(String bookTitle);
}
