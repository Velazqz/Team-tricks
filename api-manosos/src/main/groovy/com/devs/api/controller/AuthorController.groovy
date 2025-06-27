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
class AuthorController {

  @Autowired
  AuthorRepository authorRepository

  @Autowired
  BookRepository bookRepository

  @QueryMapping
  Author authorById(@Argument String id) {
    authorRepository.findById(id).orElse(null)
  }

  @QueryMapping
  List<Author> authors() {
    authorRepository.findAll()
  }

  @MutationMapping
  Author addAuthor(@Argument String firstName, @Argument String lastName) {
    Author newAuthor = new Author(firstName: firstName, lastName: lastName)
    authorRepository.save(newAuthor)
    newAuthor
  }

  @MutationMapping
  Author updateAuthor(@Argument String id, @Argument String firstName, @Argument String lastName) {
    Author existingAuthor = authorRepository.findById(id)
      .orElseThrow({ new IllegalArgumentException("Author with ID ${id} not found") })

    if (firstName) {
      existingAuthor.firstName = firstName
    }
    if (lastName) {
      existingAuthor.lastName = lastName
    }
    authorRepository.save(existingAuthor)
  }

  @MutationMapping
  Boolean deleteAuthor(@Argument String id) {
    Author authorToDelete = authorRepository.findById(id).orElse(null)
    if (authorToDelete) {
      authorToDelete.books.clear()
      authorRepository.deleteById(id)
      return true
    }
    false
  }

  @SchemaMapping(typeName = "Author")
  List<Book> books(Author author) {
    bookRepository.findAll().stream()
      .filter({ book -> book.author?.id == author.id })
      .collect(Collectors.toList())
  }
}