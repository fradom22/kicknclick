package it.polimi.kicknclick.service;

import it.polimi.kicknclick.builder.ProdottoBuilder;
import it.polimi.kicknclick.dto.request.ModificaProdottoRequest;
import it.polimi.kicknclick.dto.request.PubblicaProdottoRequest;
import it.polimi.kicknclick.dto.response.ProdottoResponse;
import it.polimi.kicknclick.exception.Exception;
import it.polimi.kicknclick.model.Acquisto;
import it.polimi.kicknclick.model.Prodotto;
import it.polimi.kicknclick.model.Utente;
import it.polimi.kicknclick.repository.AcquistoRepository;
import it.polimi.kicknclick.repository.ProdottoRepository;
import it.polimi.kicknclick.repository.UtenteRepository;
import it.polimi.kicknclick.state.implementation.Disponibile;
import it.polimi.kicknclick.state.implementation.Recensito;
import it.polimi.kicknclick.state.implementation.Venduto;
import it.polimi.kicknclick.util.ControlloDati;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Questa classe contiene la logica di business per la gestione dei prodotti.
 */
@Service
@RequiredArgsConstructor
public class ProdottoService {

    private final UtenteRepository utenteRepository;
    private final ProdottoRepository prodottoRepository;
    private final AcquistoRepository acquistoRepository;

    private static final String FOLDER_PATH = "/Users/Fra_Dommy/Desktop/kicknclick/BE/src/main/resources/imgs/prodotti/";

    /**
     * Questo metodo permette di pubblicare un prodotto.
     *
     * @param request  contiene i dati del prodotto da pubblicare
     * @param immagine contiene l'immagine del prodotto
     * @param sellerId contiene l'id del venditore
     * @throws IOException se si verifica un errore durante la lettura/scrittura dell'immagine
     */
    public void pubblica(PubblicaProdottoRequest request, MultipartFile immagine, Long sellerId) throws IOException {

        ControlloDati.controlloNumeri(List.of(request.getPrezzo(), request.getMisura(), sellerId));
        ControlloDati.controlloStringhe(List.of(request.getModello(), request.getMarca()));

        //Controllo se l'immagine è presente
        if (immagine == null) {
            throw new Exception("Immagine non presente");
        }

        //Controllo se l'utente esiste
        Optional<Utente> utenteExists = utenteRepository.findByUtenteId(sellerId);
        if (utenteExists.isEmpty()) {
            throw new Exception("Utente non trovato");
        }

        //Costruisco il prodotto con il builder
        Prodotto prodotto = new ProdottoBuilder()
                .marca(request.getMarca())
                .modello(request.getModello())
                .prezzo(request.getPrezzo())
                .misura(request.getMisura())
                .utente(utenteExists.get())
                .nomeImmagine(immagine.getOriginalFilename())
                .tipoImmagine(immagine.getContentType())
                .percorsoImmagine(FOLDER_PATH + immagine.getOriginalFilename())
                .build();

        prodottoRepository.save(prodotto);

        //Salvo l'immagine nella cartella
        File file = new File(FOLDER_PATH + immagine.getOriginalFilename());
    }

    /**
     * Questo metodo permette di ottenere tutti i prodotti non venduti.
     *
     * @return la lista dei prodotti non venduti
     * @throws IOException se si verifica un errore durante la lettura dell'immagine
     */
    public List<ProdottoResponse> getAllNonVendute() throws IOException {

        List<Prodotto> prodotti = prodottoRepository.findAll();

        List<ProdottoResponse> response = new ArrayList<>();

        //Controllo se il prodotto è disponibile e lo aggiungo alla lista
        for (Prodotto p : prodotti) {
            if (checkStato(p).equals("DISPONIBILE")) {
                response.add(new ProdottoResponse(
                        p.getProdottoId(),
                        p.getMarca(),
                        p.getModello(),
                        p.getPrezzo(),
                        p.getMisura(),
                        Files.readAllBytes(new File(p.getPercorsoImmagine()).toPath()),
                        "DISPONIBILE"
                ));
            }
        }

        return response;
    }

    /**
     * Questo metodo permette di ottenere un prodotto tramite il suo id.
     *
     * @param prodottoId contiene l'id del prodotto
     * @return il prodotto
     * @throws IOException se si verifica un errore durante la lettura dell'immagine
     */
    public ProdottoResponse get(Long prodottoId) throws IOException {

        Optional<Prodotto> prodotto = prodottoRepository.findByProdottoId(prodottoId);

        if (prodotto.isEmpty()) {
            throw new Exception("Prodotto non trovato");
        }

        return new ProdottoResponse(
                prodotto.get().getProdottoId(),
                prodotto.get().getMarca(),
                prodotto.get().getModello(),
                prodotto.get().getPrezzo(),
                prodotto.get().getMisura(),
                //Prendo l'immagine dal percorso
                Files.readAllBytes(new File(prodotto.get().getPercorsoImmagine()).toPath()),
                //Prendo lo stato del prodotto
                checkStato(prodotto.get())
        );
    }

    /**
     * Questo metodo permette di ottenere tutti i prodotti pubblicati da un venditore.
     *
     * @param sellerId contiene l'id del venditore
     * @return la lista dei prodotti pubblicati
     * @throws IOException se si verifica un errore durante la lettura dell'immagine
     */
    public List<ProdottoResponse> getAllPubblicate(Long sellerId) throws IOException {

        ControlloDati.controlloNumeri(List.of(sellerId));

        Optional<Utente> utenteExists = utenteRepository.findByUtenteId(sellerId);

        if (utenteExists.isEmpty()) {
            throw new Exception("Utente non trovato");
        }

        List<Prodotto> prodotti = prodottoRepository.findAllByUtente(utenteExists.get());

        List<ProdottoResponse> response = new ArrayList<>();

        //Aggiungo alla lista tutti i prodotti del venditore
        for (Prodotto p : prodotti) {
            response.add(new ProdottoResponse(
                    p.getProdottoId(),
                    p.getMarca(),
                    p.getModello(),
                    p.getPrezzo(),
                    p.getMisura(),
                    //Prendo l'immagine dal percorso
                    Files.readAllBytes(new File(p.getPercorsoImmagine()).toPath()),
                    //Prendo lo stato del prodotto
                    checkStato(p)
            ));
        }

        return response;
    }

    /**
     * Questo metodo permette di eliminare un prodotto.
     *
     * @param prodottoId contiene l'id del prodotto
     */
    public void elimina(Long prodottoId) {

        ControlloDati.controlloNumeri(List.of(prodottoId));

        Optional<Prodotto> prodottoExists = prodottoRepository.findByProdottoId(prodottoId);

        //Per poter eliminare un prodotto deve esistere
        if (prodottoExists.isEmpty()) {
            throw new Exception("Prodotto non trovato");
        }

        prodottoRepository.delete(prodottoExists.get());
    }

    /**
     * Questo metodo permette di modificare un prodotto.
     *
     * @param prodotto contiene i dati del prodotto da modificare
     */
    public void modifica(ModificaProdottoRequest prodotto) {

        ControlloDati.controlloNumeri(List.of(prodotto.getId(), prodotto.getPrezzo(), prodotto.getMisura()));
        ControlloDati.controlloStringhe(List.of(prodotto.getMarca(), prodotto.getModello()));

        Optional<Prodotto> prodottoExists = prodottoRepository.findByProdottoId(prodotto.getId());

        if (prodottoExists.isEmpty()) {
            throw new Exception("Prodotto non trovato");
        }

        //Modifico i dati del prodotto
        prodottoExists.get().setMarca(prodotto.getMarca());
        prodottoExists.get().setModello(prodotto.getModello());
        prodottoExists.get().setPrezzo(prodotto.getPrezzo());
        prodottoExists.get().setMisura(prodotto.getMisura());

        //Salvo le modifiche
        prodottoRepository.save(prodottoExists.get());
    }

    /**
     * Questo metodo permette di ottenere i prodotti acquistati da un cliente.
     *
     * @param customerId contiene l'id del cliente
     * @return la lista dei prodotti acquistati
     * @throws IOException se si verifica un errore durante la lettura dell'immagine
     */
    public List<ProdottoResponse> getByCustomer(Long customerId) throws IOException {

        ControlloDati.controlloNumeri(List.of(customerId));

        Optional<Utente> customerExists = utenteRepository.findByUtenteId(customerId);

        if (customerExists.isEmpty()) {
            throw new Exception("Utente non trovato");
        }

        //Prendo tutti gli acquisti del cliente
        List<Acquisto> acquisti = acquistoRepository.findAllByUtente(customerExists.get());

        List<ProdottoResponse> response = new ArrayList<>();

        //Aggiungo alla lista tutti i prodotti acquistati
        for (Acquisto a : acquisti) {
            response.add(new ProdottoResponse(
                    a.getProdotto().getProdottoId(),
                    a.getProdotto().getMarca(),
                    a.getProdotto().getModello(),
                    a.getProdotto().getPrezzo(),
                    a.getProdotto().getMisura(),
                    //Prendo l'immagine dal percorso
                    Files.readAllBytes(new File(a.getProdotto().getPercorsoImmagine()).toPath()),
                    //Prendo lo stato del prodotto
                    checkStato(a.getProdotto())
            ));
        }

        return response;
    }

    /**
     * Questo metodo permette di ottenere tutti i prodotti.
     *
     * @return la lista di tutti i prodotti
     * @throws IOException se si verifica un errore durante la lettura dell'immagine
     */
    public List<ProdottoResponse> getAll() throws IOException {

        List<Prodotto> prodotti = prodottoRepository.findAll();

        List<ProdottoResponse> response = new ArrayList<>();

        //Aggiungo alla lista tutti i prodotti
        for (Prodotto p : prodotti) {
            response.add(new ProdottoResponse(
                    p.getProdottoId(),
                    p.getMarca(),
                    p.getModello(),
                    p.getPrezzo(),
                    p.getMisura(),
                    //Prendo l'immagine dal percorso
                    Files.readAllBytes(new File(p.getPercorsoImmagine()).toPath()),
                    //Prendo lo stato del prodotto
                    checkStato(p)
            ));
        }

        return response;
    }

    /**
     * Questo metodo permette di ottenere lo stato di uno specifico prodotto.
     *
     * @param prodotto contiene il prodotto di cui si vuole conoscere lo stato
     * @return lo stato del prodotto
     */
    private String checkStato(Prodotto prodotto) {

        Optional<Acquisto> acquisto = acquistoRepository.findByProdotto(prodotto);

        //Se non esiste un acquisto allora il prodotto è disponibile
        if (acquisto.isEmpty()) {
            //Setto lo stato tramite il pattern State
            prodotto.setStato(new Disponibile(prodotto));
            return "DISPONIBILE";
        }

        //Se esiste sia l'acquisto che la recensione allora il prodotto è recensito
        if (acquisto.get().getTestoRecensione() != null) {
            //Setto lo stato tramite il pattern State
            prodotto.setStato(new Recensito(prodotto));
            return "RECENSITO";
        }

        //Se esiste l'acquisto ma non la recensione allora il prodotto è venduto
        //Setto lo stato tramite il pattern State
        prodotto.setStato(new Venduto(prodotto));
        return "VENDUTO";
    }
}
