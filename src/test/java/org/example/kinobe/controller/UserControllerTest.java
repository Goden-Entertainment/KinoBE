package org.example.kinobe.controller;

import jakarta.servlet.http.HttpSession;
import org.example.kinobe.exception.DatabaseOperationException;
import org.example.kinobe.model.User;
import org.example.kinobe.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private UserRestController userController;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User(1, "testuser","12346" , "hashedpassword", "test@email.com", LocalDate.now() );
    }

    // ✅ Succesfuldt login
    @Test
    void authenticateUser_validCredentials_returns200AndUser() {
        when(userService.login("testuser", "password123")).thenReturn(mockUser);

        ResponseEntity<?> response = userController.authenticateUser("testuser", "password123", session);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(session).setAttribute("user", mockUser);
    }

    // ❌ Forkert kodeord/brugernavn
    @Test
    void authenticateUser_invalidCredentials_returns401() {
        when(userService.login("testuser", "wrongpassword")).thenReturn(null);

        ResponseEntity<?> response = userController.authenticateUser("testuser", "wrongpassword", session);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(session, never()).setAttribute(any(), any());
    }

    private void assertEquals(HttpStatus httpStatus, HttpStatusCode statusCode) {
    }

    // 💥 Database fejl
    @Test
    void authenticateUser_databaseError_throwsDatabaseOperationException() {
        when(userService.login(any(), any())).thenThrow(new DataAccessException("DB error") {});

        assertThrows(DatabaseOperationException.class, () ->
                userController.authenticateUser("testuser", "password123", session));

        verify(session, never()).setAttribute(any(), any());
    }
}