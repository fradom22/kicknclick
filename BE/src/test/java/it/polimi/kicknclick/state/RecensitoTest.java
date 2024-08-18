package it.polimi.kicknclick.state;

import it.polimi.kicknclick.model.Prodotto;
import it.polimi.kicknclick.state.implementation.Recensito;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class RecensitoTest {

    @InjectMocks
    Recensito recensito;

    @Test
    void vendutoTest() {
        Prodotto p = new Prodotto();
        recensito = new Recensito(p);
        assertAll(() -> recensito.venduto());
    }

    @Test
    void recensitoTest() {
        Prodotto p = new Prodotto();
        recensito = new Recensito(p);
        assertAll(() -> recensito.recensito());
    }

    @Test
    void disponibileTest() {
        Prodotto p = new Prodotto();
        recensito = new Recensito(p);
        assertAll(() -> recensito.disponibile());
    }
}
