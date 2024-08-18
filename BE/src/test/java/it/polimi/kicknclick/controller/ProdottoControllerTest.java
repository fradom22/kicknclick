package it.polimi.kicknclick.controller;

import it.polimi.kicknclick.service.ProdottoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class ProdottoControllerTest {

    @Mock
    ProdottoService prodottoService;
    @InjectMocks
    ProdottoController prodottoController;

    @Test
    void pubblica() {
        assertAll(() -> prodottoController.pubblica(null, null, null, null, null, null));
    }

    @Test
    void getAllNonVendute() {
        assertAll(() -> prodottoController.getAllNonVendute());
    }

    @Test
    void get() {
        assertAll(() -> prodottoController.get("2"));
    }

    @Test
    void getAllPubblicate() {
        assertAll(() -> prodottoController.getAllPubblicate("1"));
    }

    @Test
    void elimina() {
        assertAll(() -> prodottoController.elimina("2"));
    }

    @Test
    void modifica() {
        assertAll(() -> prodottoController.modifica(null));
    }

    @Test
    void getByCustomer() {
        assertAll(() -> prodottoController.getByCustomer("1"));
    }

    @Test
    void getAll() {
        assertAll(() -> prodottoController.getAll());
    }
}
