package it.polimi.kicknclick.state;

import it.polimi.kicknclick.model.Prodotto;
import it.polimi.kicknclick.state.implementation.Disponibile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class DisponibileTest {

    @InjectMocks
    Disponibile disponibile;

    @Test
    void vendutoTest() {
        Prodotto p = new Prodotto();
        disponibile = new Disponibile(p);
        assertAll(() -> disponibile.venduto());
    }

    @Test
    void disponibileTest() {
        Prodotto p = new Prodotto();
        disponibile = new Disponibile(p);
        assertAll(() -> disponibile.disponibile());
    }

    @Test
    void recensitoTest() {
        Prodotto p = new Prodotto();
        disponibile = new Disponibile(p);
        assertAll(() -> disponibile.recensito());
    }
}
