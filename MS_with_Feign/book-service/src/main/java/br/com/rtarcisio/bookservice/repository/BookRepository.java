package br.com.rtarcisio.bookservice.repository;

import br.com.rtarcisio.bookservice.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
