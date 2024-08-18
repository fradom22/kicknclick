package it.polimi.kicknclick.builder;

import it.polimi.kicknclick.model.Acquisto;
import it.polimi.kicknclick.model.Prodotto;
import it.polimi.kicknclick.model.Ruolo;
import it.polimi.kicknclick.model.Utente;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Classe builder per l'oggetto Utente.
 * Permette di costruire un oggetto Utente passando i parametri desiderati.
 * I parametri non obbligatori possono essere omessi.
 */
@Getter
@NoArgsConstructor
public class UtenteBuilder {

    private Long utenteId;
    private String nome;
    private String cognome;
    private String username;
    private String password;
    private Ruolo ruolo;
    private List<Acquisto> acquisti;
    private List<Prodotto> prodottiPubblicati;

    public UtenteBuilder utenteId(Long utenteId) {
        this.utenteId = utenteId;
        return this;
    }

    public UtenteBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    public UtenteBuilder cognome(String cognome) {
        this.cognome = cognome;
        return this;
    }

    public UtenteBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UtenteBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UtenteBuilder ruolo(Ruolo ruolo) {
        this.ruolo = ruolo;
        return this;
    }

    public UtenteBuilder acquisti(List<Acquisto> acquisti) {
        this.acquisti = acquisti;
        return this;
    }

    public UtenteBuilder prodottiPubblicati(List<Prodotto> prodottiPubblicati) {
        this.prodottiPubblicati = prodottiPubblicati;
        return this;
    }

    /**
     * Costruisce un oggetto Utente con i parametri passati al builder.
     *
     * @return un oggetto Utente
     */
    public Utente build() {
        return new Utente(this);
    }
}
