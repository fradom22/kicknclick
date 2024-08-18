package it.polimi.kicknclick.dto.response;

import it.polimi.kicknclick.model.Ruolo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private Long utenteId;
    private String nome;
    private String cognome;
    private String username;
    private Ruolo ruolo;
    private String jwt;
}
