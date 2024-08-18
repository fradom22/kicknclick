package it.polimi.kicknclick.controller;

import it.polimi.kicknclick.dto.request.LoginRequest;
import it.polimi.kicknclick.dto.request.RegisterRequest;
import it.polimi.kicknclick.dto.response.LoginResponse;
import it.polimi.kicknclick.model.Ruolo;
import it.polimi.kicknclick.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    AuthService authenticationService;
    @InjectMocks
    AuthController authController;

    @Test
    void register() {
        RegisterRequest request = new RegisterRequest("nome", "cognome", "username", "password", "ruolo");
        assertAll(() -> authController.register(request));
    }

    @Test
    void authenticate() {
        LoginRequest request = new LoginRequest("username", "password");
        when(authenticationService.login(request)).thenReturn(new LoginResponse(1L, "nome", "cognome", "username", Ruolo.SELLER, "jwt"));
        assertAll(() -> authController.authenticate(request));
    }
}
