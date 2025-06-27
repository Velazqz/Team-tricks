package com.devs.api.repository

import com.devs.api.domain.Author
import com.devs.api.domain.Book
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Repository
class AuthorRepository {

  private final Map<String, Author> authors = new ConcurrentHashMap<>()
  private final AtomicLong counter = new AtomicLong()

  AuthorRepository() {

    Author jorge = new Author(id: '1', firstName: 'Jorge', lastName: 'Luis Borges')
    Author gabriel = new Author(id: '2', firstName: 'Gabriel', lastName: 'García Márquez')

    authors.put('1', jorge)
    authors.put('2', gabriel)

  }

  List<Author> findAll() {
    new ArrayList<>(authors.values())
  }

  Optional<Author> findById(String id) {
    Optional.ofNullable(authors.get(id))
  }

  Author save(Author author) {
    if (!author.id) {
      author.id = String.valueOf(counter.incrementAndGet())
    }
    authors.put(author.id, author)
    author
  }

  void deleteById(String id) {
    authors.remove(id)
  }
}