package it.polimi.kicknclick.service;

import it.polimi.kicknclick.dto.request.ModificaUtenteRequest;
import it.polimi.kicknclick.dto.response.UtenteResponse;
import it.polimi.kicknclick.dto.response.UtentiGlobaliResponse;
import it.polimi.kicknclick.exception.Exception;
import it.polimi.kicknclick.model.Ruolo;
import it.polimi.kicknclick.model.Utente;
import it.polimi.kicknclick.repository.UtenteRepository;
import it.polimi.kicknclick.util.ControlloDati;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Questa classe contiene i metodi per la gestione degli utenti.
 */
@Service
@RequiredArgsConstructor
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Questo metodo permette di ottenere i dati di un utente.
     * @param utenteId id dell'utente
     * @return i dati dell'utente
     */
    public UtenteResponse get(Long utenteId) {

        ControlloDati.controlloNumeri(List.of(utenteId));

        Optional<Utente> optionalUtente = utenteRepository.findById(utenteId);

        if (optionalUtente.isEmpty()) {
            throw new Exception("Utente non presente");
        }

        //Se l'utente esiste, restituisco i dati.
        return new UtenteResponse(
                optionalUtente.get().getUsername(),
                optionalUtente.get().getNome(),
                optionalUtente.get().getCognome()
        );
    }

    /**
     * Questo metodo permette di modificare i dati di un utente.
     * @param request dati dell'utente da modificare
     */
    public void modifica(ModificaUtenteRequest request) {

        ControlloDati.controlloNumeri(List.of(request.getId()));

        Optional<Utente> optionalUtente = utenteRepository.findById(request.getId());

        if (optionalUtente.isEmpty()) {
            throw new Exception("Utente non presente");
        }

        //Se l'utente esiste, lo assegno a una variabile
        Utente utente = optionalUtente.get();

        //Aggiorno i campi "nome" e "cognome" se non sono vuoti
        if (!request.getNome().isEmpty() && !request.getNome().isBlank()) {
            utente.setNome(request.getNome());
        }
        if (!request.getCognome().isEmpty() && !request.getCognome().isBlank()) {
            utente.setCognome(request.getCognome());
        }

        //Se "username" non è vuoto e diverso da quello attuale, controllo se esiste già un utente con quel nome
        if (!request.getUsername().isEmpty() &&
                !request.getUsername().isBlank() &&
                !request.getUsername().equals(utente.getUsername())) {

            Optional<Utente> utenteWithUsername = utenteRepository.findByUsername(request.getUsername());

            //Se esiste, lancio un'eccezione
            if (utenteWithUsername.isPresent()) {
                throw new Exception("Username già registrato");
            } else {
                //Se non esiste, assegno all'utente il nuovo username
                utente.setUsername(request.getUsername());
            }
        }

        //Se "vecchia password" e "nuova password" non sono vuoti, controllo se la vecchia password è corretta
        if (!request.getNuovaPassword().isEmpty() &&
                !request.getNuovaPassword().isBlank() &&
                !request.getVecchiaPassword().isEmpty() &&
                !request.getVecchiaPassword().isBlank()) {

            //Decodficazione della password attuale e controllo se è corretta
            if (!passwordEncoder.matches(request.getVecchiaPassword(), utente.getPassword())) {
                throw new Exception("Password errata");
            }

            //Se vecchia e nuova password sono uguali, lancio un'eccezione
            if (request.getVecchiaPassword().equals(request.getNuovaPassword())) {
                throw new Exception("Le password sono uguali");
            }

            //Codifico la nuova password e la assegno all'utente
            utente.setPassword(passwordEncoder.encode(request.getNuovaPassword()));
        }

        //Aggiorno l'utente nel database
        utenteRepository.save(utente);
    }

    //Metodo per eliminare un utente
    public void elimina(Long utenteId) {

        ControlloDati.controlloNumeri(List.of(utenteId));

        Optional<Utente> optionalUtente = utenteRepository.findById(utenteId);

        if (optionalUtente.isEmpty()) {
            throw new Exception("Utente non presente");
        }

        //Elimino l'utente dal database
        utenteRepository.delete(optionalUtente.get());

        //Controllo se esiste ancora l'utente con quell'id
        Optional<Utente> userDeleted = utenteRepository.findByUtenteId(utenteId);

        //Se non è stato eliminato, lancio un'eccezione
        if (userDeleted.isPresent()) {
            throw new Exception("Errore nell'eliminazione dell'utente");
        }
    }

    /**
     * Questo metodo permette di ottenere la lista di tutti gli utenti globali.
     * @return la lista degli utenti
     */
    public List<UtentiGlobaliResponse> getAll() {

        //Dato che è un metodo chiamato dall'admin, ritorno tutti gli utenti tranne sé stesso
        List<Utente> utenti = utenteRepository.findAllByRuoloIsNotIn(List.of(Ruolo.ADMIN));

        List<UtentiGlobaliResponse> response = new ArrayList<>();

        //Per ogni utente, aggiungo i dati alla lista
        for (Utente u : utenti) {
            response.add(new UtentiGlobaliResponse(
                    u.getUtenteId(),
                    u.getNome(),
                    u.getCognome(),
                    u.getRuolo().toString()
            ));
        }

        return response;
    }
}
