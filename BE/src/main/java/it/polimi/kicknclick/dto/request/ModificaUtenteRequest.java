package it.polimi.kicknclick.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModificaUtenteRequest {

    private Long id;
    private String username;
    private String nome;
    private String cognome;
    private String vecchiaPassword;
    private String nuovaPassword;
}
