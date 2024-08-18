package it.polimi.kicknclick.model;

import it.polimi.kicknclick.builder.UtenteBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UtenteTest {

    @Test
    void utenteTest() {
        Utente u = new UtenteBuilder()
                .ruolo(Ruolo.CUSTOMER)
                .acquisti(List.of(new Acquisto()))
                .prodottiPubblicati(List.of(new Prodotto()))
                .build();
        List<Acquisto> acquisti = u.getAcquisti();
        List<Prodotto> prodottiPubblicati = u.getProdottiPubblicati();
        u.getAuthorities();
        u.isAccountNonExpired();
        u.isAccountNonLocked();
        u.isCredentialsNonExpired();
        u.isEnabled();
    }
}
