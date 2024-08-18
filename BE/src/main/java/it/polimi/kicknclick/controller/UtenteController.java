package it.polimi.kicknclick.controller;

import it.polimi.kicknclick.dto.request.ModificaUtenteRequest;
import it.polimi.kicknclick.dto.response.MessaggioResponse;
import it.polimi.kicknclick.dto.response.UtenteResponse;
import it.polimi.kicknclick.dto.response.UtentiGlobaliResponse;
import it.polimi.kicknclick.service.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller per la gestione delle richieste relative agli utenti.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/utente")
public class UtenteController {

    private final UtenteService utenteService;

    /**
     * Metodo per la creazione di un nuovo utente.
     *
     * @param utenteId id dell'utente
     * @return DTO contenente i dati dell'utente richiesto
     */
    @GetMapping("/get/{utenteId}")
    public ResponseEntity<UtenteResponse> get(@PathVariable String utenteId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(utenteService.get(Long.parseLong(utenteId)));
    }

    /**
     * Metodo per la modifica di un utente.
     *
     * @param request DTO contenente i dati dell'utente da modificare
     * @return DTO contenente un messaggio di conferma
     */
    @PutMapping("/modifica/")
    public ResponseEntity<MessaggioResponse> modifica(@RequestBody ModificaUtenteRequest request) {

        utenteService.modifica(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessaggioResponse("Utente modificato con successo!"));
    }

    /**
     * Metodo per l'eliminazione di un utente.
     *
     * @param utenteId id dell'utente
     * @return DTO contenente un messaggio di conferma
     */
    @DeleteMapping("/elimina/{utenteId}")
    public ResponseEntity<MessaggioResponse> elimina(@PathVariable String utenteId) {

        utenteService.elimina(Long.parseLong(utenteId));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessaggioResponse("Utente eliminato con successo!"));
    }

    /**
     * Metodo per ottenere la lista di tutti gli utenti.
     *
     * @return DTO contenente la lista di tutti gli utenti
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<UtentiGlobaliResponse>> getAll() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(utenteService.getAll());
    }
}
