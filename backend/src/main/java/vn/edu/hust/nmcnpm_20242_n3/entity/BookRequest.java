package vn.edu.hust.nmcnpm_20242_n3.entity;

import jakarta.persistence.*;
import vn.edu.hust.nmcnpm_20242_n3.constant.BookRequestStatusEnum;
import vn.edu.hust.nmcnpm_20242_n3.constant.BookRequestTypeEnum;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_requests")
public class BookRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_loan_id")
    private BookLoan bookLoan;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookRequestStatusEnum status;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookRequestTypeEnum type;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public BookLoan getBookLoan() { return bookLoan; }
    public void setBookLoan(BookLoan bookLoan) { this.bookLoan = bookLoan; }

    public BookRequestStatusEnum getStatus() { return status; }
    public void setStatus(BookRequestStatusEnum status) { this.status = status; }

    public BookRequestTypeEnum getType() { return type; }
    public void setType(BookRequestTypeEnum type) { this.type = type; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}