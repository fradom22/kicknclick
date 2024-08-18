package it.polimi.kicknclick.dto.response;

import it.polimi.kicknclick.model.Ruolo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModificaUtenteResponse {

    private Long utenteId;
    private String nome;
    private String cognome;
    private String username;
    private String ruolo;
}
