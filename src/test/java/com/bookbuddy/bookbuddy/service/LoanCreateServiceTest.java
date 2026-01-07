package com.bookbuddy.bookbuddy.service;

import com.bookbuddy.bookbuddy.dto.loan.CreateLoanRequest;
import com.bookbuddy.bookbuddy.entity.BookCopy;
import com.bookbuddy.bookbuddy.entity.Loan;
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
    void create_shouldThrow_whenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        CreateLoanRequest request = new CreateLoanRequest(1L, 10L, LocalDate.now().plusDays(7));

        BusinessException ex = assertThrows(BusinessException.class, () -> service.create(request));
        assertEquals("User inexistent", ex.getMessage());

        verify(bookCopyRepository, never()).save(any());
        verify(loanRepository, never()).save(any());
    }

    @Test
    void create_shouldThrow_whenCopyNotFound() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookCopyRepository.findById(10L)).thenReturn(Optional.empty());

        CreateLoanRequest request = new CreateLoanRequest(1L, 10L, LocalDate.now().plusDays(7));

        BusinessException ex = assertThrows(BusinessException.class, () -> service.create(request));
        assertEquals("Exemplar inexistent", ex.getMessage());

        verify(loanRepository, never()).save(any());
    }

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
        verify(bookCopyRepository, never()).save(any(BookCopy.class)); // nu trebuie sa salveze
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

        // IMPORTANT: fara asta save() returneaza null si pica testul
        when(loanRepository.save(any(Loan.class))).thenAnswer(inv -> {
            Loan l = inv.getArgument(0);
            l.setId(1L);
            return l;
        });

        CreateLoanRequest request = new CreateLoanRequest(1L, 10L, LocalDate.now().plusDays(7));

        service.create(request);

        assertFalse(copy.isAvailable());
        verify(bookCopyRepository).save(copy);

        ArgumentCaptor<Loan> captor = ArgumentCaptor.forClass(Loan.class);
        verify(loanRepository).save(captor.capture());

        Loan savedLoan = captor.getValue();
        assertEquals(user, savedLoan.getUser());
        assertEquals(10L, savedLoan.getBookCopyId());
        assertNotNull(savedLoan.getStartDate());
        assertEquals(request.dueDate(), savedLoan.getDueDate());
        assertNull(savedLoan.getReturnDate());
    }

}
