package it.polimi.kicknclick.builder;

import it.polimi.kicknclick.model.Acquisto;
import it.polimi.kicknclick.model.Prodotto;
import it.polimi.kicknclick.model.Utente;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Classe builder per l'oggetto Prodotto.
 * Permette di costruire un oggetto Prodotto passando i parametri desiderati.
 * I parametri non obbligatori possono essere omessi.
 */
@Getter
@NoArgsConstructor
public class ProdottoBuilder {

    private Long prodottoId;
    private String marca;
    private String modello;
    private Double prezzo;
    private Double misura;
    private String nomeImmagine;
    private String tipoImmagine;
    private String percorsoImmagine;
    private Utente utente;
    private Acquisto acquisto;

    public ProdottoBuilder prodottoId(Long prodottoId) {
        this.prodottoId = prodottoId;
        return this;
    }

    public ProdottoBuilder marca(String marca) {
        this.marca = marca;
        return this;
    }

    public ProdottoBuilder modello(String modello) {
        this.modello = modello;
        return this;
    }

    public ProdottoBuilder prezzo(Double prezzo) {
        this.prezzo = prezzo;
        return this;
    }

    public ProdottoBuilder misura(Double misura) {
        this.misura = misura;
        return this;
    }

    public ProdottoBuilder nomeImmagine(String nomeImmagine) {
        this.nomeImmagine = nomeImmagine;
        return this;
    }

    public ProdottoBuilder tipoImmagine(String tipoImmagine) {
        this.tipoImmagine = tipoImmagine;
        return this;
    }

    public ProdottoBuilder percorsoImmagine(String percorsoImmagine) {
        this.percorsoImmagine = percorsoImmagine;
        return this;
    }

    public ProdottoBuilder utente(Utente utente) {
        this.utente = utente;
        return this;
    }

    public ProdottoBuilder acquisto(Acquisto acquisto) {
        this.acquisto = acquisto;
        return this;
    }

    /**
     * Costruisce un oggetto Prodotto con i parametri passati al builder.
     *
     * @return un oggetto Prodotto
     */
    public Prodotto build() {
        return new Prodotto(this);
    }
}
