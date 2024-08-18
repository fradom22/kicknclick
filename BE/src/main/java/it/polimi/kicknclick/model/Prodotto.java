package it.polimi.kicknclick.model;

import it.polimi.kicknclick.builder.ProdottoBuilder;
import it.polimi.kicknclick.state.StateProdotto;
import it.polimi.kicknclick.state.implementation.Disponibile;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

/**
 * Classe che rappresenta l'entità Prodotto.
 */
@Data
@Entity(name = "Prodotto")
@Table(name = "prodotto")
public class Prodotto {

    @Id
    @SequenceGenerator(
            name = "prodotto_sequence",
            sequenceName = "prodotto_sequence",
            allocationSize = 1)
    @GeneratedValue(
            generator = "prodotto_sequence",
            strategy = GenerationType.SEQUENCE)
    @Column(name = "prodotto_id", nullable = false, updatable = false)
    private Long prodottoId;

    @Column(name = "marca", nullable = false, columnDefinition = "VARCHAR(50)")
    private String marca;

    @Column(name = "modello", nullable = false, columnDefinition = "VARCHAR(50)")
    private String modello;

    @Column(name = "prezzo", nullable = false, columnDefinition = "VARCHAR(50)")
    private Double prezzo;

    @Column(name = "misura", nullable = false)
    private Double misura;

    @Column
    private String nomeImmagine;

    @Column
    private String tipoImmagine;

    @Column
    private String percorsoImmagine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Utente utente; // Seller che ha caricato il prodotto

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prodotto_id", referencedColumnName = "acquisto_id")
    private Acquisto acquisto; // Acquisto associato al prodotto

    // Stato del prodotto. Viene utilizzato il pattern State.
    @Transient
    private StateProdotto stato;

    /**
     * Costruttore della classe Prodotto. Viene utilizzato il pattern Builder.
     * @param builder Oggetto di tipo ProdottoBuilder con i dati necessari per la costruzione dell'oggetto.
     */
    public Prodotto(@NonNull ProdottoBuilder builder) {
        this.prodottoId = builder.getProdottoId();
        this.marca = builder.getMarca();
        this.modello = builder.getModello();
        this.prezzo = builder.getPrezzo();
        this.misura = builder.getMisura();
        this.nomeImmagine = builder.getNomeImmagine();
        this.tipoImmagine = builder.getTipoImmagine();
        this.percorsoImmagine = builder.getPercorsoImmagine();
        this.utente = builder.getUtente();
        this.acquisto = builder.getAcquisto();
    }

    /**
     * Costruttore vuoto della classe Prodotto.
     * Viene inizializzato lo stato del prodotto come Disponibile.
     * Viene utilizzato il pattern State.
     */
    public Prodotto() {
        this.stato = new Disponibile(this);
    }

    /**
     * Metodo che permette di cambiare lo stato del prodotto.
     * @param stato Nuovo stato del prodotto.
     */
    public void cambiaStato(StateProdotto stato) {
        this.stato = stato;
    }

    /**
     * Metodo che permette di ottenere lo stato del prodotto.
     * @return Stato del prodotto.
     */
    @SuppressWarnings("Pattern state")
    public StateProdotto getStato() {
        return this.stato;
    }
}
