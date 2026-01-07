package com.bookbuddy.bookbuddy.dto.loan;

import java.time.LocalDate;

public record LoanResponse(
        Long id,
        Long userId,
        Long bookCopyId,
        LocalDate startDate,
        LocalDate dueDate,
        LocalDate returnDate
) {
}
