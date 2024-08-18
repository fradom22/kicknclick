package it.polimi.kicknclick.service;

import it.polimi.kicknclick.dto.request.LoginRequest;
import it.polimi.kicknclick.dto.request.RegisterRequest;
import it.polimi.kicknclick.model.Utente;
import it.polimi.kicknclick.repository.UtenteRepository;
import it.polimi.kicknclick.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    UtenteRepository utenteRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtService jwtService;
    @Mock
    AuthenticationManager authenticationManager;
    @InjectMocks
    AuthService authService;

    @Test
    void registerThrowsUsernameGiaRegistrato() {
        RegisterRequest request = new RegisterRequest("nome", "cognome", "username", "password", "ADMIN");
        when(utenteRepository.findByUsername(any())).thenReturn(Optional.of(new Utente()));
        assertThrows(Exception.class, () -> authService.register(request));
    }

    @Test
    void registerThrowsRuoloNonValido() {
        RegisterRequest request = new RegisterRequest("nome", "cognome", "username", "password", "ADMIN");
        when(utenteRepository.findByUsername(any())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> authService.register(request));
    }

    @Test
    void registerThrowsRegistrazioneNonRiuscitaCustomer() {
        RegisterRequest request = new RegisterRequest("nome", "cognome", "username", "password", "CUSTOMER");
        when(utenteRepository.findByUsername(any())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> authService.register(request));
    }

    @Test
    void registerThrowsRegistrazioneNonRiuscitaSeller() {
        RegisterRequest request = new RegisterRequest("nome", "cognome", "username", "password", "SELLER");
        when(utenteRepository.findByUsername(any())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> authService.register(request));
    }

    @Test
    void loginSuccessful() {
        LoginRequest request = new LoginRequest("username", "password");
        when(utenteRepository.findByUsername(any())).thenReturn(Optional.of(new Utente()));
        PasswordEncoder passwordEncoderMock = mock(PasswordEncoder.class);
        ReflectionTestUtils.setField(authService, "passwordEncoder", passwordEncoderMock);
        assertAll(() -> authService.login(request));
    }

    @Test
    void putJwtInHttpHeadersSuccessful() {
        assertAll(() -> authService.putJwtInHttpHeaders("jwt"));
    }
}
