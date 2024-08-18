package it.polimi.kicknclick.controller;

import it.polimi.kicknclick.dto.request.ModificaProdottoRequest;
import it.polimi.kicknclick.dto.request.PubblicaProdottoRequest;
import it.polimi.kicknclick.dto.response.MessaggioResponse;
import it.polimi.kicknclick.dto.response.ProdottoResponse;
import it.polimi.kicknclick.service.ProdottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Questa classe gestisce le richieste relative ai prodotti.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/prodotto")
public class ProdottoController {

    private final ProdottoService prodottoService;

    /**
     * Questo metodo permette di pubblicare un prodotto.
     *
     * @param marca    Marca del prodotto.
     * @param modello  Modello del prodotto.
     * @param prezzo   Prezzo del prodotto.
     * @param misura   Misura del prodotto.
     * @param immagine Immagine del prodotto.
     * @param sellerId Id del venditore.
     * @return Messaggio di conferma.
     * @throws IOException Eccezione in caso di errore nella lettura dell'immagine.
     */
    @PostMapping("/pubblica/{sellerId}")
    public ResponseEntity<MessaggioResponse> pubblica(
            @RequestParam("marca") String marca,
            @RequestParam("modello") String modello,
            @RequestParam("prezzo") Double prezzo,
            @RequestParam("misura") Double misura,
            @RequestParam("immagine") MultipartFile immagine,
            @PathVariable("sellerId") Long sellerId
    ) throws IOException {
        PubblicaProdottoRequest request = new PubblicaProdottoRequest(marca, modello, prezzo, misura);

        prodottoService.pubblica(request, immagine, sellerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessaggioResponse("Prodotto pubblicato con successo!"));
    }

    /**
     * Questo metodo permette di ottenere tutti i prodotti non venduti.
     *
     * @return Lista di prodotti non venduti.
     * @throws IOException Eccezione in caso di errore nella lettura dell'immagine.
     */
    @GetMapping("/getAllNonVendute/")
    public ResponseEntity<List<ProdottoResponse>> getAllNonVendute() throws IOException {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(prodottoService.getAllNonVendute());
    }

    /**
     * Questo metodo permette di prendere i dati di un prodotto.
     * @param prodottoId Id del prodotto.
     * @return DTO con i dati del prodotto.
     */
    @GetMapping("/get/{prodottoId}")
    public ResponseEntity<ProdottoResponse> get(@PathVariable String prodottoId) throws IOException {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(prodottoService.get(Long.parseLong(prodottoId)));
    }

    /**
     * Questo metodo permette di ottenere tutti i prodotti pubblicati da un venditore.
     * @param sellerId Id del venditore.
     * @return Lista di prodotti pubblicati.
     * @throws IOException Eccezione in caso di errore nella lettura dell'immagine.
     */
    @GetMapping("/getAllPubblicate/{sellerId}")
    public ResponseEntity<List<ProdottoResponse>> getAllPubblicate(@PathVariable String sellerId) throws IOException {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(prodottoService.getAllPubblicate(Long.parseLong(sellerId)));
    }

    /**
     * Questo metodo permette di eliminare un prodotto.
     * @param prodottoId Id del prodotto.
     * @return Messaggio di conferma.
     */
    @DeleteMapping("/elimina/{prodottoId}")
    public ResponseEntity<MessaggioResponse> elimina(@PathVariable String prodottoId) {

        prodottoService.elimina(Long.parseLong(prodottoId));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessaggioResponse("Prodotto eliminato con successo!"));
    }

    /**
     * Questo metodo permette di modificare un prodotto.
     * @param prodotto DTO con i dati aggiornati del prodotto.
     * @return Messaggio di conferma.
     */
    @PutMapping("/modifica/")
    public ResponseEntity<MessaggioResponse> modifica(@RequestBody ModificaProdottoRequest prodotto) {

        prodottoService.modifica(prodotto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessaggioResponse("Prodotto modificato con successo!"));
    }

    /**
     * Questo metodo permette di prendere i prodotti acquistati da un cliente.
     * @param customerId Id del cliente.
     * @return Lista di prodotti acquistati.
     */
    @GetMapping("/getByCustomer/{customerId}")
    public ResponseEntity<List<ProdottoResponse>> getByCustomer(@PathVariable String customerId) throws IOException {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(prodottoService.getByCustomer(Long.parseLong(customerId)));
    }

    /**
     * Questo metodo permette di prendere tutti i prodotti.
     * @return Lista di tutti i prodotti.
     */
    @GetMapping("/getAll/")
    public ResponseEntity<List<ProdottoResponse>> getAll() throws IOException {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(prodottoService.getAll());
    }
}
