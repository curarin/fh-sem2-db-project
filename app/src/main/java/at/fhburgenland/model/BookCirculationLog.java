package at.fhburgenland.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "book_circulation_log")
public class BookCirculationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_circulation_log_id", nullable = false)
    private Integer bookCirculationLogId;

    @Column(name = "loan_starts_at_date", nullable = false)
    private LocalDate loanStartsAtDate;

    @Column(name = "loan_ends_at_date")
    private LocalDate loanEndsAtDate;

    @Column(name = "book_returned_at_date")
    private LocalDate bookReturnedAtDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_stockid")
    private BookStockLog fkStockid;


}