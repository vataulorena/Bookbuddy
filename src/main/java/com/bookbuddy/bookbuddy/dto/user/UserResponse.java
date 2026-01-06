package com.bookbuddy.bookbuddy.dto.user;

import java.util.List;

public record UserResponse(
        Long id,
        String name,
        String email,
        List<LoanSummary> loans
) {
    public record LoanSummary(
            Long id,
            Long bookCopyId,
            String startDate,
            String dueDate,
            String returnDate
    ) {}
}
