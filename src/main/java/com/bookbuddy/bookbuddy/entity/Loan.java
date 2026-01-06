package com.bookbuddy.bookbuddy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookCopyId;

    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    @ManyToOne
    private User user;
}
