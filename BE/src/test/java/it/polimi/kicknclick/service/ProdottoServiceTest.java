package it.polimi.kicknclick.service;

import it.polimi.kicknclick.dto.request.ModificaProdottoRequest;
import it.polimi.kicknclick.dto.request.PubblicaProdottoRequest;
import it.polimi.kicknclick.dto.response.ProdottoResponse;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProdottoServiceTest {

    @Mock
    UtenteRepository utenteRepository;
    @Mock
    ProdottoRepository prodottoRepository;
    @Mock
    AcquistoRepository acquistoRepository;
    @InjectMocks
    ProdottoService prodottoService;

    @Test
    void pubblicaThrowsImmagineNonPresente() {
        PubblicaProdottoRequest request = new PubblicaProdottoRequest("marca", "modello", 1.0, 1.0);
        assertThrows(Exception.class, () -> prodottoService.pubblica(request, null, 1L));
    }

    @Test
    void pubblicaThrowsUtenteNonTrovato() {
        PubblicaProdottoRequest request = new PubblicaProdottoRequest("marca", "modello", 1.0, 1.0);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("immagine", "filename.png", "image/png", "immagine".getBytes());
        when(utenteRepository.findByUtenteId(1L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> prodottoService.pubblica(request, mockMultipartFile, 1L));
    }

    @Test
    void pubblicaSuccessful() {
        PubblicaProdottoRequest request = new PubblicaProdottoRequest("marca", "modello", 1.0, 1.0);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("immagine", "filename.png", "image/png", "immagine".getBytes());
        when(utenteRepository.findByUtenteId(1L)).thenReturn(Optional.of(new Utente()));
        assertAll(() -> prodottoService.pubblica(request, mockMultipartFile, 1L));
    }

    @Test
    void getAllNonVenduteSuccessful() throws IOException {
        Prodotto prodotto = new Prodotto();
        prodotto.setProdottoId(1L);
        prodotto.setMarca("marca");
        prodotto.setModello("modello");
        prodotto.setPrezzo(1.0);
        prodotto.setMisura(1.0);
        File tempFile = File.createTempFile("tempFile", ".txt");
        prodotto.setPercorsoImmagine(tempFile.getAbsolutePath());
        List<Prodotto> prodotti = new ArrayList<>();
        prodotti.add(prodotto);
        when(prodottoRepository.findAll()).thenReturn(prodotti);
        List<ProdottoResponse> result = prodottoService.getAllNonVendute();
        assertEquals(1, result.size());
        ProdottoResponse response = result.get(0);
        assertAll(
                () -> assertEquals(prodotto.getProdottoId(), response.getProdottoId()),
                () -> assertEquals(prodotto.getMarca(), response.getMarca()),
                () -> assertEquals(prodotto.getModello(), response.getModello()),
                () -> assertEquals(prodotto.getPrezzo(), response.getPrezzo()),
                () -> assertEquals(prodotto.getMisura(), response.getMisura())
        );
    }

    @Test
    void getThrowsProdottoNonTrovato() {
        assertThrows(Exception.class, () -> prodottoService.get(1L));
    }

    @Test
    void getSuccessful() throws IOException {
        Prodotto prodotto = new Prodotto();
        prodotto.setProdottoId(1L);
        prodotto.setMarca("marca");
        prodotto.setModello("modello");
        prodotto.setPrezzo(1.0);
        prodotto.setMisura(1.0);
        File tempFile = File.createTempFile("tempFile", ".txt");
        prodotto.setPercorsoImmagine(tempFile.getAbsolutePath());
        when(prodottoRepository.findByProdottoId(any())).thenReturn(Optional.of(prodotto));
        ProdottoResponse result = prodottoService.get(1L);
        assertAll(
                () -> assertEquals(prodotto.getProdottoId(), result.getProdottoId()),
                () -> assertEquals(prodotto.getMarca(), result.getMarca()),
                () -> assertEquals(prodotto.getModello(), result.getModello()),
                () -> assertEquals(prodotto.getPrezzo(), result.getPrezzo()),
                () -> assertEquals(prodotto.getMisura(), result.getMisura())
        );
    }

    @Test
    void getAllPubblicateThrowsUtenteNonTrovato() {
        when(utenteRepository.findByUtenteId(any())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> prodottoService.getAllPubblicate(1L));
    }

    @Test
    void getAllPubblicateSuccessful() throws IOException {
        Utente utente = new Utente();
        utente.setUtenteId(1L);
        Prodotto prodotto = new Prodotto();
        prodotto.setProdottoId(1L);
        prodotto.setMarca("marca");
        prodotto.setModello("modello");
        prodotto.setPrezzo(1.0);
        prodotto.setMisura(1.0);
        File tempFile = File.createTempFile("tempFile", ".txt");
        prodotto.setPercorsoImmagine(tempFile.getAbsolutePath());
        List<Prodotto> prodotti = new ArrayList<>();
        prodotti.add(prodotto);
        when(utenteRepository.findByUtenteId(any())).thenReturn(Optional.of(utente));
        when(prodottoRepository.findAllByUtente(utente)).thenReturn(prodotti);
        List<ProdottoResponse> result = prodottoService.getAllPubblicate(1L);
        assertEquals(1, result.size());
        ProdottoResponse response = result.get(0);
        assertAll(
                () -> assertEquals(prodotto.getProdottoId(), response.getProdottoId()),
                () -> assertEquals(prodotto.getMarca(), response.getMarca()),
                () -> assertEquals(prodotto.getModello(), response.getModello()),
                () -> assertEquals(prodotto.getPrezzo(), response.getPrezzo()),
                () -> assertEquals(prodotto.getMisura(), response.getMisura())
        );
    }

    @Test
    void eliminaThrowsProdottoNonTrovato() {
        when(prodottoRepository.findByProdottoId(any())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> prodottoService.elimina(1L));
    }

    @Test
    void eliminaSuccessful() {
        when(prodottoRepository.findByProdottoId(any())).thenReturn(Optional.of(new Prodotto()));
        assertAll(() -> prodottoService.elimina(1L));
    }

    @Test
    void modificaThrowsProdottoNonTrovato() {
        ModificaProdottoRequest request = new ModificaProdottoRequest("marca", "modello", 1.0, 1.0, 1L);
        when(prodottoRepository.findByProdottoId(any())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> prodottoService.modifica(request));
    }

    @Test
    void modificaSuccessful() {
        ModificaProdottoRequest request = new ModificaProdottoRequest("marca", "modello", 1.0, 1.0, 1L);
        when(prodottoRepository.findByProdottoId(any())).thenReturn(Optional.of(new Prodotto()));
        assertAll(() -> prodottoService.modifica(request));
    }

    @Test
    void getByCustomerThrowsUtenteNonTrovato() {
        when(utenteRepository.findByUtenteId(any())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> prodottoService.getByCustomer(1L));
    }

    @Test
    void getByCustomerSuccessful() throws IOException {
        Utente utente = new Utente();
        utente.setUtenteId(1L);
        Prodotto prodotto = new Prodotto();
        prodotto.setProdottoId(1L);
        prodotto.setMarca("marca");
        prodotto.setModello("modello");
        prodotto.setPrezzo(1.0);
        prodotto.setMisura(1.0);
        File tempFile = File.createTempFile("tempFile", ".txt");
        prodotto.setPercorsoImmagine(tempFile.getAbsolutePath());
        Acquisto acquisto = new Acquisto();
        acquisto.setProdotto(prodotto);
        acquisto.setUtente(utente);
        List<Acquisto> acquisti = new ArrayList<>();
        acquisti.add(acquisto);
        when(utenteRepository.findByUtenteId(any())).thenReturn(Optional.of(utente));
        when(acquistoRepository.findAllByUtente(utente)).thenReturn(acquisti);
        List<ProdottoResponse> result = prodottoService.getByCustomer(1L);
        assertEquals(1, result.size());
        ProdottoResponse response = result.get(0);
        assertAll(
                () -> assertEquals(prodotto.getProdottoId(), response.getProdottoId()),
                () -> assertEquals(prodotto.getMarca(), response.getMarca()),
                () -> assertEquals(prodotto.getModello(), response.getModello()),
                () -> assertEquals(prodotto.getPrezzo(), response.getPrezzo()),
                () -> assertEquals(prodotto.getMisura(), response.getMisura())
        );
    }

    @Test
    void getAllSuccessful() throws IOException {
        List<Prodotto> prodotti = new ArrayList<>();
        Prodotto prodotto = new Prodotto();
        prodotto.setProdottoId(1L);
        prodotto.setMarca("marca");
        prodotto.setModello("modello");
        prodotto.setPrezzo(1.0);
        prodotto.setMisura(1.0);
        File tempFile = File.createTempFile("tempFile", ".txt");
        prodotto.setPercorsoImmagine(tempFile.getAbsolutePath());
        prodotti.add(prodotto);
        when(prodottoRepository.findAll()).thenReturn(prodotti);
        List<ProdottoResponse> result = prodottoService.getAll();
        assertEquals(1, result.size());
        ProdottoResponse response = result.get(0);
        assertAll(
                () -> assertEquals(prodotto.getProdottoId(), response.getProdottoId()),
                () -> assertEquals(prodotto.getMarca(), response.getMarca()),
                () -> assertEquals(prodotto.getModello(), response.getModello()),
                () -> assertEquals(prodotto.getPrezzo(), response.getPrezzo()),
                () -> assertEquals(prodotto.getMisura(), response.getMisura())
        );
    }
}
