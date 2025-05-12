package vn.edu.hust.nmcnpm_20242_n3.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.hust.nmcnpm_20242_n3.entity.BookLoan;
import vn.edu.hust.nmcnpm_20242_n3.entity.BookRequest;
import vn.edu.hust.nmcnpm_20242_n3.repository.BookRequestRepository;
import vn.edu.hust.nmcnpm_20242_n3.constant.BookLoanStatusEnum;
import vn.edu.hust.nmcnpm_20242_n3.constant.BookRequestStatusEnum;
import vn.edu.hust.nmcnpm_20242_n3.constant.BookRequestTypeEnum;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookRequestService {

    private final BookRequestRepository bookRequestRepository;
    private final BookLoanService bookLoanService;

    @Autowired
    public BookRequestService(BookRequestRepository bookRequestRepository, BookLoanService bookLoanService) {
        this.bookRequestRepository = bookRequestRepository;
        this.bookLoanService = bookLoanService;
    }

    public List<BookRequest> getAllPendingRequests() {
        return bookRequestRepository.findByStatusOrderByCreatedAtAsc(BookRequestStatusEnum.PENDING);
    }

    @Transactional
    public BookRequest processRequest(Integer requestId, boolean approve) {
        BookRequest request = bookRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found with ID: " + requestId));

        BookLoan bookLoan = request.getBookLoan();
        if (bookLoan == null) {
            throw new IllegalStateException("Associated book loan not found for request ID: " + requestId);
        }

        if (approve) {
            request.setStatus(BookRequestStatusEnum.ACCEPTED);
            if (request.getType() == BookRequestTypeEnum.BORROWING) {
                bookLoan.setStatus(BookLoanStatusEnum.BORROWED);
            } else if (request.getType() == BookRequestTypeEnum.RETURNING) {
                bookLoan.setStatus(BookLoanStatusEnum.RETURNED);
                bookLoan.setActualReturnDate(LocalDateTime.now());
            }
        } else {
            request.setStatus(BookRequestStatusEnum.DENIED);
            if (request.getType() == BookRequestTypeEnum.BORROWING) {
                bookLoan.setStatus(BookLoanStatusEnum.REJECTED);
            } else if (request.getType() == BookRequestTypeEnum.RETURNING) {
                bookLoan.setStatus(BookLoanStatusEnum.NONRETURNABLE);
            }
        }

        request.setUpdatedAt(LocalDateTime.now());
        bookLoan.setUpdatedAt(LocalDateTime.now());
        bookLoan.setCurrentBookRequestId(null);

        bookLoanService.save(bookLoan);
        return bookRequestRepository.save(request);
    }
}