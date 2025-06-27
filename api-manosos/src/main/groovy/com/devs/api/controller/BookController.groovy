package com.devs.api.controller

import com.devs.api.domain.Author
import com.devs.api.domain.Book
import com.devs.api.repository.AuthorRepository
import com.devs.api.repository.BookRepository
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class BookController {

  @Autowired
  AuthorRepository authorRepository

  @Autowired
  BookRepository bookRepository

  @QueryMapping
  Book bookById(@Argument String id) {
    bookRepository.findById(id).orElse(null)
  }

  @QueryMapping
  List<Book> books() {
    bookRepository.findAll()
  }

  @MutationMapping
  Book addBook(@Argument String title, @Argument String isbn, @Argument String authorId) {
    Author author = authorRepository.findById(authorId)
      .orElseThrow({ new IllegalArgumentException("Author with ID ${authorId} not found") })

    Book newBook = new Book(title: title, isbn: isbn, author: author)
    bookRepository.save(newBook)
    author.books.add(newBook)
    newBook
  }

  @MutationMapping
  Book updateBook(@Argument String id, @Argument String title, @Argument String isbn, @Argument String authorId) {
    Book existingBook = bookRepository.findById(id)
      .orElseThrow({ new IllegalArgumentException("Book with ID ${id} not found") })

    if (title) {
      existingBook.title = title
    }
    if (isbn) {
      existingBook.isbn = isbn
    }
    if (authorId) {
      Author newAuthor = authorRepository.findById(authorId)
        .orElseThrow({ new IllegalArgumentException("Author with ID ${authorId} not found") })

      if (existingBook.author != null && !existingBook.author.id.equals(newAuthor.id)) {
        existingBook.author.books.remove(existingBook)
      }
      existingBook.author = newAuthor
      newAuthor.books.add(existingBook)
    }
    bookRepository.save(existingBook)
  }

  @MutationMapping
  Boolean deleteBook(@Argument String id) {
    Book bookToDelete = bookRepository.findById(id).orElse(null)
    if (bookToDelete) {

      if (bookToDelete.author) {
        bookToDelete.author.books.remove(bookToDelete)
      }
      bookRepository.deleteById(id)
      return true
    }
    false
  }
}