package it.polimi.kicknclick.service;

import it.polimi.kicknclick.dto.request.AcquistaRequest;
import it.polimi.kicknclick.dto.request.RecensioneRequest;
import it.polimi.kicknclick.dto.response.RecensioneResponse;
import it.polimi.kicknclick.model.Acquisto;
import it.polimi.kicknclick.model.Prodotto;
import it.polimi.kicknclick.model.Utente;
import it.polimi.kicknclick.repository.AcquistoRepository;
import it.polimi.kicknclick.repository.ProdottoRepository;
import it.polimi.kicknclick.repository.UtenteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AcquistoServiceTest {

    @Mock
    AcquistoRepository acquistoRepository;

    @Mock
    ProdottoRepository prodottoRepository;

    @Mock
    UtenteRepository utenteRepository;

    @InjectMocks
    AcquistoService acquistoService;

    @Test
    void acquistaThrowsScarpaNonEsiste() {
        when(prodottoRepository.findByProdottoId(any())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> acquistoService.acquista(new AcquistaRequest(1L, 1L)));
    }

    @Test
    void acquistaThrowsClienteNonEsiste() {
        when(prodottoRepository.findByProdottoId(any())).thenReturn(Optional.of(new Prodotto()));
        when(utenteRepository.findByUtenteId(any())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> acquistoService.acquista(new AcquistaRequest(1L, 1L)));
    }

    @Test
    void acquistaSuccessful() {
        when(prodottoRepository.findByProdottoId(any())).thenReturn(Optional.of(new Prodotto()));
        when(utenteRepository.findByUtenteId(any())).thenReturn(Optional.of(new Utente()));
        assertAll(() -> acquistoService.acquista(new AcquistaRequest(1L, 1L)));
    }

    @Test
    void recensioneThrowsImmagineNonPresente() {
        RecensioneRequest request = new RecensioneRequest(1L, 1L, "testo", 4);
        assertThrows(Exception.class, () -> acquistoService.recensione(request, null));
    }

    @Test
    void recensioneThrowsUtenteNonTrovato() {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("immagine", "filename.png", "image/png", "immagine".getBytes());
        RecensioneRequest request = new RecensioneRequest(1L, 1L, "testo", 4);
        when(utenteRepository.findByUtenteId(any())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> acquistoService.recensione(request, mockMultipartFile));
    }

    @Test
    void recensioneThrowsProdottoNonTrovato() {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("immagine", "filename.png", "image/png", "immagine".getBytes());
        RecensioneRequest request = new RecensioneRequest(1L, 1L, "testo", 4);
        when(utenteRepository.findByUtenteId(any())).thenReturn(Optional.of(new Utente()));
        when(prodottoRepository.findByProdottoId(any())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> acquistoService.recensione(request, mockMultipartFile));
    }

    @Test
    void recensioneThrowsAcquistoNonTrovato() {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("immagine", "filename.png", "image/png", "immagine".getBytes());
        RecensioneRequest request = new RecensioneRequest(1L, 1L, "testo", 4);
        Prodotto prodotto = new Prodotto();
        Utente utente = new Utente();
        when(utenteRepository.findByUtenteId(any())).thenReturn(Optional.of(utente));
        when(prodottoRepository.findByProdottoId(any())).thenReturn(Optional.of(prodotto));
        when(acquistoRepository.findByProdottoAndUtente(prodotto, utente)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> acquistoService.recensione(request, mockMultipartFile));
    }

    @Test
    void recensioneSuccessful() {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("immagine", "filename.png", "image/png", "immagine".getBytes());
        RecensioneRequest request = new RecensioneRequest(1L, 1L, "testo", 4);
        Prodotto prodotto = new Prodotto();
        Utente utente = new Utente();
        when(utenteRepository.findByUtenteId(any())).thenReturn(Optional.of(utente));
        when(prodottoRepository.findByProdottoId(any())).thenReturn(Optional.of(prodotto));
        when(acquistoRepository.findByProdottoAndUtente(prodotto, utente)).thenReturn(Optional.of(new Acquisto()));
        assertAll(() -> acquistoService.recensione(request, mockMultipartFile));
    }

    @Test
    void visualizzaRecensioneThrowsProdottoNonTrovato() {
        when(prodottoRepository.findByProdottoId(any())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> acquistoService.visualizzaRecensione(1L));
    }

    @Test
    void visualizzaRecensioneThrowsAcquistoNonTrovato() {
        when(prodottoRepository.findByProdottoId(any())).thenReturn(Optional.of(new Prodotto()));
        when(acquistoRepository.findByProdotto(any())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> acquistoService.visualizzaRecensione(1L));
    }

    @Test
    void visualizzaRecensioneSuccessful() throws IOException {
        Prodotto prodotto = new Prodotto();
        prodotto.setProdottoId(1L);
        Acquisto acquisto = new Acquisto();
        acquisto.setProdotto(prodotto);
        acquisto.setVotoRecensione(5);
        acquisto.setTestoRecensione("Great product!");
        File tempFile = File.createTempFile("tempFile", ".txt");
        acquisto.setPercorsoImmagine(tempFile.getAbsolutePath());
        when(prodottoRepository.findByProdottoId(any())).thenReturn(Optional.of(prodotto));
        when(acquistoRepository.findByProdotto(prodotto)).thenReturn(Optional.of(acquisto));
        RecensioneResponse result = acquistoService.visualizzaRecensione(1L);
        assertAll(
                () -> assertEquals(acquisto.getVotoRecensione(), result.getVotoRecensione()),
                () -> assertEquals(acquisto.getTestoRecensione(), result.getTestoRecensione()),
                () -> assertArrayEquals(Files.readAllBytes(tempFile.toPath()), result.getImmagine())
        );
    }
}
