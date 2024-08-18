package it.polimi.kicknclick.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequest {

    private String nome;
    private String cognome;
    private String username;
    private String password;
    private String ruolo;
}
