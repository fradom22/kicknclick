package it.polimi.kicknclick.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UtenteResponse {

    private String username;
    private String nome;
    private String cognome;
}
