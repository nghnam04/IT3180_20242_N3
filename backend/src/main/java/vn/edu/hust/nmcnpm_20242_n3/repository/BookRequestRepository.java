package vn.edu.hust.nmcnpm_20242_n3.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hust.nmcnpm_20242_n3.entity.BookRequest;
import vn.edu.hust.nmcnpm_20242_n3.constant.BookRequestStatusEnum;

import java.util.List;

@Repository
public interface BookRequestRepository extends CrudRepository<BookRequest, Integer> {
    List<BookRequest> findByStatusOrderByCreatedAtAsc(BookRequestStatusEnum status);
}