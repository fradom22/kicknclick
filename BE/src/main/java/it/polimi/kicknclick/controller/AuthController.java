package it.polimi.kicknclick.controller;

import it.polimi.kicknclick.dto.request.LoginRequest;
import it.polimi.kicknclick.dto.request.RegisterRequest;
import it.polimi.kicknclick.dto.response.LoginResponse;
import it.polimi.kicknclick.dto.response.MessaggioResponse;
import it.polimi.kicknclick.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller per la gestione delle richieste di autenticazione.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authenticationService;

    /**
     * Metodo per la registrazione.
     *
     * @param request DTO con i dati per la registrazione.
     * @return Messaggio di avvenuta registrazione.
     */
    @PostMapping("/register")
    public ResponseEntity<MessaggioResponse> register(@RequestBody RegisterRequest request) {

        authenticationService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessaggioResponse("Registrazione completata!"));
    }

    /**
     * Metodo per il login.
     *
     * @param request DTO con i dati per il login.
     * @return DTO con l'utente autenticato e un messaggio di avvenuta registrazione.
     * Nell'header la stringa jwt.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest request) {

        final LoginResponse response = authenticationService.login(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(authenticationService.putJwtInHttpHeaders(response.getJwt()))
                .body(response);
    }
}