package it.polimi.kicknclick.model;

import it.polimi.kicknclick.builder.AcquistoBuilder;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

/**
 * Classe che rappresenta l'entità Acquisto.
 */
@Data
@NoArgsConstructor
@Entity(name = "Acquisto")
@Table(name = "acquisto")
public class Acquisto {

    @Id
    @SequenceGenerator(
            name = "acquisto_sequence",
            sequenceName = "acquisto_sequence",
            allocationSize = 1)
    @GeneratedValue(
            generator = "acquisto_sequence",
            strategy = GenerationType.SEQUENCE)
    @Column(name = "acquisto_id", nullable = false, updatable = false)
    private Long acquistoId;

    @Column
    private LocalDateTime data;

    @Column
    private String testoRecensione;

    @Column
    private Integer votoRecensione;

    @Column
    private String nomeImmagine;

    @Column
    private String tipoImmagine;

    @Column
    private String percorsoImmagine;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Utente utente; // Utente che ha effettuato l'acquisto

    @OneToOne(mappedBy = "acquisto")
    private Prodotto prodotto; // Prodotto acquistato

    /**
     * Costruttore della classe Acquisto. Viene utilizzato il pattern Builder.
     * @param builder Oggetto di tipo AcquistoBuilder con i dati necessari per la costruzione dell'oggetto.
     */
    public Acquisto(@NonNull AcquistoBuilder builder) {
        this.acquistoId = builder.getAcquistoId();
        this.data = builder.getData();
        this.testoRecensione = builder.getTestoRecensione();
        this.votoRecensione = builder.getVotoRecensione();
        this.nomeImmagine = builder.getNomeImmagine();
        this.tipoImmagine = builder.getTipoImmagine();
        this.percorsoImmagine = builder.getPercorsoImmagine();
        this.utente = builder.getUtente();
        this.prodotto = builder.getProdotto();
    }
}
