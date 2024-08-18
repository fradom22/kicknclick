package it.polimi.kicknclick.controller;

import it.polimi.kicknclick.dto.request.AcquistaRequest;
import it.polimi.kicknclick.dto.request.RecensioneRequest;
import it.polimi.kicknclick.dto.response.MessaggioResponse;
import it.polimi.kicknclick.dto.response.RecensioneResponse;
import it.polimi.kicknclick.service.AcquistoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Controller per la gestione delle richieste relative agli acquisti e alle recensioni.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/acquisto")
public class AcquistoController {

    private final AcquistoService acquistoService;

    /**
     * Metodo per effettuare un acquisto.
     *
     * @param request DTO contenente i dati dell'acquisto.
     * @return Messaggio di conferma.
     */
    @PostMapping("/acquista/")
    public ResponseEntity<MessaggioResponse> acquista(@RequestBody AcquistaRequest request) {

        acquistoService.acquista(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessaggioResponse("Acquisto completato con successo!"));
    }

    /**
     * Metodo per pubblicare una recensione.
     *
     * @param prodottoId      Id del prodotto recensito.
     * @param customerId      Id del cliente che ha pubblicato la recensione.
     * @param testoRecensione Testo della recensione.
     * @param votoRecensione  Voto assegnato alla recensione.
     * @param immagine        Immagine allegata alla recensione.
     * @return Messaggio di conferma.
     * @throws IOException Eccezione lanciata in caso di errore nella lettura dell'immagine.
     */
    @PostMapping("/recensione/")
    public ResponseEntity<MessaggioResponse> recensione(
            @RequestParam("prodottoId") Long prodottoId,
            @RequestParam("customerId") Long customerId,
            @RequestParam("testoRecensione") String testoRecensione,
            @RequestParam("votoRecensione") Integer votoRecensione,
            @RequestParam("immagine") MultipartFile immagine
    ) throws IOException {
        RecensioneRequest request = new RecensioneRequest(prodottoId, customerId, testoRecensione, votoRecensione);

        acquistoService.recensione(request, immagine);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessaggioResponse("Recensione pubblicata con successo!"));
    }

    /**
     * Metodo per visualizzare una recensione.
     *
     * @param prodottoId Id del prodotto di cui si vuole visualizzare la recensione.
     * @return Recensione del prodotto.
     * @throws IOException Eccezione lanciata in caso di errore nella lettura dell'immagine.
     */
    @GetMapping("visualizzaRecensione/{prodottoId}")
    public ResponseEntity<RecensioneResponse> visualizzaRecensione(@PathVariable Long prodottoId) throws IOException {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(acquistoService.visualizzaRecensione(prodottoId));
    }
}
