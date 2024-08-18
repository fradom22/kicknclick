package it.polimi.kicknclick.service;

import it.polimi.kicknclick.builder.AcquistoBuilder;
import it.polimi.kicknclick.dto.request.AcquistaRequest;
import it.polimi.kicknclick.dto.request.RecensioneRequest;
import it.polimi.kicknclick.dto.response.RecensioneResponse;
import it.polimi.kicknclick.exception.Exception;
import it.polimi.kicknclick.model.Acquisto;
import it.polimi.kicknclick.model.Prodotto;
import it.polimi.kicknclick.model.Utente;
import it.polimi.kicknclick.repository.AcquistoRepository;
import it.polimi.kicknclick.repository.ProdottoRepository;
import it.polimi.kicknclick.repository.UtenteRepository;
import it.polimi.kicknclick.util.ControlloDati;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Questa classe si occupa di gestire le operazioni di acquisto e recensione di un prodotto.
 */
@Service
@RequiredArgsConstructor
public class AcquistoService {


    private static final String FOLDER_PATH = "/Users/Fra_Dommy/Desktop/kicknclick/BE/src/main/resources/imgs/recensioni/";

    private final AcquistoRepository acquistoRepository;
    private final ProdottoRepository prodottoRepository;
    private final UtenteRepository utenteRepository;

    /**
     * Questo metodo si occupa di effettuare l'acquisto di un prodotto.
     *
     * @param request DTO contenente l'id del prodotto e l'id dell'utente che effettua l'acquisto.
     */
    public void acquista(AcquistaRequest request) {

        ControlloDati.controlloNumeri(List.of(request.getScarpaId(), request.getUtenteId()));

        // Controllo se la scarpa esiste
        Optional<Prodotto> prodottoExists = prodottoRepository.findByProdottoId(request.getScarpaId());
        if (prodottoExists.isEmpty()) {
            throw new Exception("La scarpa non esiste!");
        }

        // Controllo se l'utente esiste
        Optional<Utente> customerExists = utenteRepository.findByUtenteId(request.getUtenteId());
        if (customerExists.isEmpty()) {
            throw new Exception("Il cliente non esiste!");
        }

        //Costruisco l'acquisto con il pattern builder
        Acquisto acquisto = new AcquistoBuilder()
                .data(LocalDateTime.now())
                .utente(customerExists.get())
                .prodotto(prodottoExists.get())
                .build();

        //Salvo l'acquisto nel database
        acquistoRepository.save(acquisto);
    }

    /**
     * Questo metodo si occupa di effettuare la recensione di un prodotto.
     *
     * @param request  DTO contenente l'id del prodotto, l'id dell'utente che effettua la recensione, il testo della recensione e il voto.
     * @param immagine immagine della recensione.
     * @throws IOException se l'immagine non è presente.
     */
    public void recensione(RecensioneRequest request, MultipartFile immagine) throws IOException {

        ControlloDati.controlloNumeri(List.of(request.getVotoRecensione(), request.getCustomerId(), request.getProdottoId()));
        ControlloDati.controlloStringhe(List.of(request.getTestoRecensione()));

        // Controllo se l'immagine è presente
        if (immagine == null) {
            throw new Exception("Immagine non presente");
        }

        // Controllo se l'utente esiste
        Optional<Utente> utenteExists = utenteRepository.findByUtenteId(request.getCustomerId());
        if (utenteExists.isEmpty()) {
            throw new Exception("Utente non trovato");
        }

        // Controllo se il prodotto esiste
        Optional<Prodotto> prodottoExists = prodottoRepository.findByProdottoId(request.getProdottoId());
        if (prodottoExists.isEmpty()) {
            throw new Exception("Prodotto non trovato");
        }

        // Controllo se l'utente ha acquistato il prodotto
        Optional<Acquisto> acquisto = acquistoRepository.findByProdottoAndUtente(prodottoExists.get(), utenteExists.get());
        if (acquisto.isEmpty()) {
            throw new Exception("Acquisto non trovato");
        }

        acquisto.get().setTestoRecensione(request.getTestoRecensione());
        acquisto.get().setVotoRecensione(request.getVotoRecensione());
        acquisto.get().setNomeImmagine(immagine.getOriginalFilename());
        acquisto.get().setTipoImmagine(immagine.getContentType());
        acquisto.get().setPercorsoImmagine(FOLDER_PATH + immagine.getOriginalFilename());

        acquistoRepository.save(acquisto.get());

        //Creo l'immagine e la salvo nel server
        File file = new File(FOLDER_PATH + immagine.getOriginalFilename());
        immagine.transferTo(file);
    }

    /**
     * Questo metodo si occupa di visualizzare la recensione di un prodotto.
     *
     * @param prodottoId id del prodotto.
     * @return DTO contenente il voto, il testo e l'immagine della recensione.
     * @throws IOException se l'immagine non è presente.
     */
    public RecensioneResponse visualizzaRecensione(Long prodottoId) throws IOException {

        // Controllo se il prodotto esiste
        Optional<Prodotto> prodottoExists = prodottoRepository.findByProdottoId(prodottoId);
        if (prodottoExists.isEmpty()) {
            throw new Exception("Prodotto non trovato");
        }

        // Controllo se il prodotto è stato acquistato
        Optional<Acquisto> acquisto = acquistoRepository.findByProdotto(prodottoExists.get());
        if (acquisto.isEmpty()) {
            throw new Exception("Acquisto non trovato");
        }

        return new RecensioneResponse(
                acquisto.get().getVotoRecensione(),
                acquisto.get().getTestoRecensione(),
                //Leggo l'immagine dal server
                Files.readAllBytes(new File(acquisto.get().getPercorsoImmagine()).toPath())
        );
    }
}
