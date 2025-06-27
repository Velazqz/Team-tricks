package com.devs.api.repository

import com.devs.api.domain.Book
import com.devs.api.domain.Author
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Repository
class BookRepository {

  private final Map<String, Book> books = new ConcurrentHashMap<>()
  private final AtomicLong counter = new AtomicLong()

  BookRepository() {

    Author jorge = new Author(id: '1', firstName: 'Jorge', lastName: 'Luis Borges')
    Author gabriel = new Author(id: '2', firstName: 'Gabriel', lastName: 'García Márquez')

    books.put('1', new Book(id: '1', title: 'Ficciones', isbn: '978-0307951794', author: jorge))
    books.put('2', new Book(id: '2', title: 'Cien años de soledad', isbn: '978-0060883287', author: gabriel))
    books.put('3', new Book(id: '3', title: 'El Aleph', isbn: '978-0140183515', author: jorge))

    jorge.books.add(books.get('1'))
    jorge.books.add(books.get('3'))
    gabriel.books.add(books.get('2'))
  }

  List<Book> findAll() {
    new ArrayList<>(books.values())
  }

  Optional<Book> findById(String id) {
    Optional.ofNullable(books.get(id))
  }

  Book save(Book book) {
    if (!book.id) {
      book.id = String.valueOf(counter.incrementAndGet())
    }
    books.put(book.id, book)
    book
  }

  void deleteById(String id) {
    books.remove(id)
  }
}