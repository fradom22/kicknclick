package it.polimi.kicknclick.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecensioneResponse {

    private Integer votoRecensione;
    private String testoRecensione;
    private byte[] immagine;
}
