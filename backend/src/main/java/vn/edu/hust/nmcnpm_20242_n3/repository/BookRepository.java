package vn.edu.hust.nmcnpm_20242_n3.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hust.nmcnpm_20242_n3.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
    Book findByBookId(int id);
    void deleteByBookId(int id);
    List<Book> findByTitleContaining(String title);
    List<Book> findByAuthors_NameContaining(String authorName);
    List<Book> findByPublisher_NameContaining(String publisher);
    List<Book> findByCategories_NameContaining(String category);
}
