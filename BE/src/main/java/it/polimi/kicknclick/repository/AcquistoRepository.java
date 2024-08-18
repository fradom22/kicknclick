package it.polimi.kicknclick.repository;

import it.polimi.kicknclick.model.Acquisto;
import it.polimi.kicknclick.model.Prodotto;
import it.polimi.kicknclick.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaccia che permette di definire le operazioni che possono essere effettuate sul database per la gestione degli acquisti.
 */
public interface AcquistoRepository extends JpaRepository<Acquisto, Long> {

    /**
     * Metodo che permette di recuperare tutti gli acquisti effettuati da un determinato utente.
     *
     * @param utente utente di cui si vogliono recuperare gli acquisti
     * @return lista di acquisti effettuati dall'utente
     */
    List<Acquisto> findAllByUtente(Utente utente);

    /**
     * Metodo che permette di recuperare un acquisto effettuato da un determinato utente per un determinato prodotto.
     *
     * @param prodotto prodotto acquistato
     * @param utente   utente che ha effettuato l'acquisto
     * @return acquisto effettuato dall'utente per il prodotto
     */
    Optional<Acquisto> findByProdottoAndUtente(Prodotto prodotto, Utente utente);

    /**
     * Metodo che permette di recuperare un acquisto effettuato per un determinato prodotto.
     *
     * @param prodotto prodotto acquistato
     * @return acquisto effettuato per il prodotto
     */
    Optional<Acquisto> findByProdotto(Prodotto prodotto);
}
