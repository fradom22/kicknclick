package it.polimi.kicknclick.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UtentiGlobaliResponse {

    private Long utenteId;
    private String nome;
    private String cognome;
    private String ruolo;
}
