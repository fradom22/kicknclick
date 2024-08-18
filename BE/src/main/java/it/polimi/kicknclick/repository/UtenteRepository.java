package it.polimi.kicknclick.repository;

import it.polimi.kicknclick.model.Ruolo;
import it.polimi.kicknclick.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaccia che estende JpaRepository per la gestione dei dati degli utenti.
 */
public interface UtenteRepository extends JpaRepository<Utente, Long> {

    /**
     * Metodo per la ricerca di un utente tramite username.
     *
     * @param username username dell'utente da cercare
     * @return utente trovato
     */
    Optional<Utente> findByUsername(String username);

    /**
     * Metodo per la ricerca di un utente tramite id.
     *
     * @param utenteId id dell'utente da cercare
     * @return utente trovato
     */
    Optional<Utente> findByUtenteId(Long utenteId);

    /**
     * Metodo per la ricerca di tutti gli utenti che non appartengono ad un determinato ruolo.
     *
     * @param ruoli ruoli degli utenti da escludere
     * @return lista di utenti trovati
     */
    List<Utente> findAllByRuoloIsNotIn(List<Ruolo> ruoli);
}
