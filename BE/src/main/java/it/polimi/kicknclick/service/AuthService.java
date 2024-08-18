package it.polimi.kicknclick.service;

import it.polimi.kicknclick.builder.UtenteBuilder;
import it.polimi.kicknclick.dto.request.LoginRequest;
import it.polimi.kicknclick.dto.request.RegisterRequest;
import it.polimi.kicknclick.dto.response.LoginResponse;
import it.polimi.kicknclick.exception.Exception;
import it.polimi.kicknclick.model.Ruolo;
import it.polimi.kicknclick.model.Utente;
import it.polimi.kicknclick.repository.UtenteRepository;
import it.polimi.kicknclick.security.JwtService;
import it.polimi.kicknclick.util.ControlloDati;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Questa classe si occupa della registrazione e del login degli utenti.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Questo metodo si occupa della registrazione di un utente.
     * @param request DTO con i dati dell'utente da registrare.
     * @throws RuntimeException se l'utente è già registrato o se i dati non sono validi.
     */
    public void register(@NonNull RegisterRequest request) throws RuntimeException {

        //Controllo se esiste già un utente con questo username sul database.
        Optional<Utente> userAlreadyRegistered = utenteRepository.findByUsername(request.getUsername());

        //Se esiste, lancio un eccezione.
        if (userAlreadyRegistered.isPresent()) {
            throw new Exception("Username già registrato");
        }

        //Assegno a delle variabili i dati della richiesta.
        final String nome = request.getNome().trim();
        final String cognome = request.getCognome().trim();
        final String username = request.getUsername().trim().toLowerCase();
        final String password = request.getPassword();

        //Controllo che tutti i campi non siano vuoti.
        ControlloDati.controlloStringhe(List.of(nome, cognome, username, password));

        //Controllo il ruolo
        final Ruolo ruolo = switch (request.getRuolo()) {
            case "CUSTOMER" -> Ruolo.CUSTOMER;
            case "SELLER" -> Ruolo.SELLER;
            default -> throw new Exception("Ruolo non valido");
        };

        //Costruisco l'oggetto utente con il pattern builder
        Utente utente = new UtenteBuilder()
                .nome(nome)
                .cognome(cognome)
                .username(username)
                //Password codificata.
                .password(passwordEncoder.encode(password))
                .ruolo(ruolo)
                .build();

        //Salvo l'utente sul database
        utenteRepository.save(utente);

        //Controllo se l'utente è presente sul database, se sì la registrazione è andata a buon fine.
        Optional<Utente> userRegistered = utenteRepository.findByUsername(username);

        //Se l'utente appena registrato non è presente sul database, lancio un'eccezione.
        if (userRegistered.isEmpty()) {
            throw new Exception("Registrazione non riuscita");
        }
    }

    /**
     * Questo metodo si occupa del login di un utente.
     * @param request DTO con i dati dell'utente che vuole fare il login.
     * @return DTO con i dati dell'utente loggato e il jwt.
     * @throws RuntimeException se i dati non sono validi.
     */
    public LoginResponse login(@NonNull LoginRequest request) {

        //Chiamo l'authenticationManager che si occuperà del login e delle eccezioni.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername().trim().toLowerCase(),
                        request.getPassword()
                )
        );

        //Prendo l'utente appena loggato dal database.
        Utente utente = utenteRepository.findByUsername(request.getUsername()).orElseThrow();

        //Costruisco il DTO con i dati dell'utente e lo ritorno.
        return new LoginResponse(
                utente.getUtenteId(),
                utente.getNome(),
                utente.getCognome(),
                utente.getUsername(),
                utente.getRuolo(),
                jwtService.generateToken(utente)
        );
    }

    /**
     * Questo metodo si occupa di inserire il jwt negli headers della risposta http.
     * @param jwt stringa da inserire negli headers.
     * @return header con il jwt.
     */
    public HttpHeaders putJwtInHttpHeaders(String jwt) {

        HttpHeaders headers = new HttpHeaders();

        /* Come da prassi, l'header viene chiamato "Authorization" e
        prima del jwt è presente la scritta "Bearer " (tipo di token) */
        headers.add("Authorization", "Bearer: " + jwt);

        return headers;
    }
}
