package vn.edu.hust.nmcnpm_20242_n3.entity;

import jakarta.persistence.*;
import vn.edu.hust.nmcnpm_20242_n3.constant.BookLoanStatusEnum;

import java.time.LocalDateTime;

@Entity
@Table(name = "book_loans")
public class BookLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "book_copy_id")
    private BookCopy bookCopy;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private LocalDateTime loanDate;

    @Column(nullable = false)
    private LocalDateTime returnDate;

    private LocalDateTime actualReturnDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookLoanStatusEnum status;

    private Integer currentBookRequestId;

    @Column(nullable = false)
    private LocalDateTime loanedAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public BookLoan() {
        this.loanedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public BookCopy getBookCopy() { return bookCopy; }
    public void setBookCopy(BookCopy bookCopy) { this.bookCopy = bookCopy; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDateTime getLoanDate() { return loanDate; }
    public void setLoanDate(LocalDateTime loanDate) { this.loanDate = loanDate; }

    public LocalDateTime getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDateTime returnDate) { this.returnDate = returnDate; }

    public LocalDateTime getActualReturnDate() { return actualReturnDate; }
    public void setActualReturnDate(LocalDateTime actualReturnDate) { this.actualReturnDate = actualReturnDate; }

    public BookLoanStatusEnum getStatus() { return status; }
    public void setStatus(BookLoanStatusEnum status) { this.status = status; }

    public Integer getCurrentBookRequestId() { return currentBookRequestId; }
    public void setCurrentBookRequestId(Integer currentBookRequestId) { this.currentBookRequestId = currentBookRequestId; }

    public LocalDateTime getLoanedAt() { return loanedAt; }
    public void setLoanedAt(LocalDateTime loanedAt) { this.loanedAt = loanedAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}