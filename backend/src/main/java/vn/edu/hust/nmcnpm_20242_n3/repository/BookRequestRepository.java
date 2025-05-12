package vn.edu.hust.nmcnpm_20242_n3.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hust.nmcnpm_20242_n3.entity.BookRequest;
import vn.edu.hust.nmcnpm_20242_n3.constant.BookRequestStatusEnum;

import java.util.List;

@Repository
public interface BookRequestRepository extends CrudRepository<BookRequest, Integer> {
    @Query("SELECT br FROM BookRequest br ORDER BY CASE WHEN br.status = 'PENDING' THEN 0 ELSE 1 END, br.createdAt ASC")
    List<BookRequest> findByStatusOrderByCreatedAtAsc(BookRequestStatusEnum status);
}