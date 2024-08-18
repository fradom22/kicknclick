package it.polimi.kicknclick.builder;

import it.polimi.kicknclick.model.Acquisto;
import it.polimi.kicknclick.model.Prodotto;
import it.polimi.kicknclick.model.Utente;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Classe builder per l'oggetto Acquisto.
 * Permette di costruire un oggetto Acquisto passando i parametri desiderati.
 * I parametri non obbligatori possono essere omessi.
 */
@Getter
@NoArgsConstructor
public class AcquistoBuilder {

    private Long acquistoId;
    private LocalDateTime data;
    private String testoRecensione;
    private Integer votoRecensione;
    private String nomeImmagine;
    private String tipoImmagine;
    private String percorsoImmagine;
    private Utente utente;
    private Prodotto prodotto;

    public AcquistoBuilder acquistoId(Long acquistoId) {
        this.acquistoId = acquistoId;
        return this;
    }

    public AcquistoBuilder data(LocalDateTime data) {
        this.data = data;
        return this;
    }

    public AcquistoBuilder testoRecensione(String testoRecensione) {
        this.testoRecensione = testoRecensione;
        return this;
    }

    public AcquistoBuilder votoRecensione(Integer votoRecensione) {
        this.votoRecensione = votoRecensione;
        return this;
    }

    public AcquistoBuilder nomeImmagine(String nomeImmagine) {
        this.nomeImmagine = nomeImmagine;
        return this;
    }

    public AcquistoBuilder tipoImmagine(String tipoImmagine) {
        this.tipoImmagine = tipoImmagine;
        return this;
    }

    public AcquistoBuilder percorsoImmagine(String percorsoImmagine) {
        this.percorsoImmagine = percorsoImmagine;
        return this;
    }

    public AcquistoBuilder utente(Utente utente) {
        this.utente = utente;
        return this;
    }

    public AcquistoBuilder prodotto(Prodotto prodotto) {
        this.prodotto = prodotto;
        return this;
    }

    /**
     * Metodo che costruisce l'oggetto Acquisto con i parametri passati.
     *
     * @return l'oggetto Acquisto costruito
     */
    public Acquisto build() {
        return new Acquisto(this);
    }
}
