package com.bookbuddy.bookbuddy.service;

import com.bookbuddy.bookbuddy.dto.user.CreateUserRequest;
import com.bookbuddy.bookbuddy.dto.user.UserResponse;
import com.bookbuddy.bookbuddy.entity.Loan;
import com.bookbuddy.bookbuddy.entity.User;
import com.bookbuddy.bookbuddy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserService service = new UserService(userRepository);

    @Test
    void create_shouldSaveUserWithNameAndEmail() {
        CreateUserRequest request = new CreateUserRequest("Ana", "ana@example.com");

        service.create(request);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());

        assertEquals("Ana", captor.getValue().getName());
        assertEquals("ana@example.com", captor.getValue().getEmail());
    }

    @Test
    void listWithLoans_shouldMapUsersToResponse() {
        User user = new User();
        user.setId(1L);
        user.setName("Ana");
        user.setEmail("ana@example.com");

        Loan loan = new Loan();
        loan.setId(10L);
        loan.setBookCopyId(5L);
        loan.setStartDate(LocalDate.of(2026, 1, 6));
        loan.setDueDate(LocalDate.of(2026, 1, 20));
        loan.setReturnDate(null);

        user.setLoans(List.of(loan));

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserResponse> result = service.listWithLoans();

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).id());
        assertEquals(1, result.get(0).loans().size());
        assertEquals(10L, result.get(0).loans().get(0).id());
        assertEquals(5L, result.get(0).loans().get(0).bookCopyId());

        verify(userRepository).findAll();
    }
}
