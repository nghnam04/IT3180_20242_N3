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
import java.util.stream.Collectors;
import java.util.Comparator;

@Service
public class BookRequestService {

    private final BookRequestRepository bookRequestRepository;
    private final BookLoanService bookLoanService;

    @Autowired
    public BookRequestService(BookRequestRepository bookRequestRepository, BookLoanService bookLoanService) {
        this.bookRequestRepository = bookRequestRepository;
        this.bookLoanService = bookLoanService;
    }

    public List<BookRequest> getAllRequests() {
        List<BookRequest> requests = (List<BookRequest>) bookRequestRepository.findAll();
        return requests.stream()
            .sorted(Comparator.comparing((BookRequest br) -> br.getStatus() == BookRequestStatusEnum.PENDING ? 0 : 1)
                .thenComparing(BookRequest::getCreatedAt))
            .collect(Collectors.toList());
    }

    @Transactional
    public BookRequest processRequest(String requestId, boolean approve) {
        BookRequest request = bookRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found with ID: " + requestId));

        if (request.getStatus() != BookRequestStatusEnum.PENDING) {
            throw new IllegalStateException("Only PENDING requests can be processed");
        }

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

        bookLoan.setCurrentBookRequestId(null);

        bookLoanService.save(bookLoan);
        return bookRequestRepository.save(request);
    }
}