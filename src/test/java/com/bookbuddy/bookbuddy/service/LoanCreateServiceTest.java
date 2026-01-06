package com.bookbuddy.bookbuddy.service;

import com.bookbuddy.bookbuddy.dto.loan.CreateLoanRequest;
import com.bookbuddy.bookbuddy.entity.BookCopy;
import com.bookbuddy.bookbuddy.entity.User;
import com.bookbuddy.bookbuddy.repository.BookCopyRepository;
import com.bookbuddy.bookbuddy.repository.LoanRepository;
import com.bookbuddy.bookbuddy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanCreateServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final BookCopyRepository bookCopyRepository = mock(BookCopyRepository.class);
    private final LoanRepository loanRepository = mock(LoanRepository.class);

    private final LoanCreateService service =
            new LoanCreateService(userRepository, bookCopyRepository, loanRepository);

    @Test
    void create_shouldThrow_whenCopyNotAvailable() {
        User user = new User();
        user.setId(1L);

        BookCopy copy = new BookCopy();
        copy.setId(10L);
        copy.setAvailable(false);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookCopyRepository.findById(10L)).thenReturn(Optional.of(copy));

        CreateLoanRequest request = new CreateLoanRequest(1L, 10L, LocalDate.now().plusDays(7));

        BusinessException ex = assertThrows(BusinessException.class, () -> service.create(request));
        assertEquals("Exemplarul nu este disponibil", ex.getMessage());

        verify(loanRepository, never()).save(any());
    }

    @Test
    void create_shouldSaveLoan_andMarkCopyUnavailable_whenAvailable() {
        User user = new User();
        user.setId(1L);

        BookCopy copy = new BookCopy();
        copy.setId(10L);
        copy.setAvailable(true);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookCopyRepository.findById(10L)).thenReturn(Optional.of(copy));

        CreateLoanRequest request = new CreateLoanRequest(1L, 10L, LocalDate.now().plusDays(7));

        service.create(request);

        assertFalse(copy.isAvailable());
        verify(bookCopyRepository).save(copy);

        ArgumentCaptor<com.bookbuddy.bookbuddy.entity.Loan> captor =
                ArgumentCaptor.forClass(com.bookbuddy.bookbuddy.entity.Loan.class);

        verify(loanRepository).save(captor.capture());
        assertEquals(10L, captor.getValue().getBookCopyId());
        assertEquals(user, captor.getValue().getUser());
        assertNotNull(captor.getValue().getStartDate());
        assertEquals(request.dueDate(), captor.getValue().getDueDate());
    }
}
