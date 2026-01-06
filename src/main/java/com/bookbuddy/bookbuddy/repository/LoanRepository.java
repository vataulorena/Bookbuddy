package com.bookbuddy.bookbuddy.repository;

import com.bookbuddy.bookbuddy.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
