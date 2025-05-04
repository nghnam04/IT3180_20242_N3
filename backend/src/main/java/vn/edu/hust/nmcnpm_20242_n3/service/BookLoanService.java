package vn.edu.hust.nmcnpm_20242_n3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.hust.nmcnpm_20242_n3.entity.BookLoan;
import vn.edu.hust.nmcnpm_20242_n3.repository.BookLoanRepository;

@Service
public class BookLoanService {

    private final BookLoanRepository bookLoanRepository;

    @Autowired
    public BookLoanService(BookLoanRepository bookLoanRepository) {
        this.bookLoanRepository = bookLoanRepository;
    }

    public BookLoan findByRequestId(Integer requestId) {
        return bookLoanRepository.findByCurrentBookRequestId(requestId)
                .orElseThrow(() -> new IllegalArgumentException("BookLoan with request ID " + requestId + " not found"));
    }

    public BookLoan save(BookLoan bookLoan) {
        return bookLoanRepository.save(bookLoan);
    }
}