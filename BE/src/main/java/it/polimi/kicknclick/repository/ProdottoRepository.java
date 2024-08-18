package it.polimi.kicknclick.repository;

import it.polimi.kicknclick.model.Prodotto;
import it.polimi.kicknclick.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaccia che estende JpaRepository per la gestione dei dati relativi ai prodotti.
 */
public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {

    /**
     * Metodo per trovare un prodotto tramite il suo id.
     *
     * @param prodottoId id del prodotto da trovare
     * @return prodotto trovato
     */
    Optional<Prodotto> findByProdottoId(Long prodottoId);

    /**
     * Metodo per trovare tutti i prodotti di un utente.
     *
     * @param utente utente di cui trovare i prodotti
     * @return lista dei prodotti dell'utente
     */
    List<Prodotto> findAllByUtente(Utente utente);
}
