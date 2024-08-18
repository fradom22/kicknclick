package it.polimi.kicknclick.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PubblicaProdottoRequest {

    private String marca;
    private String modello;
    private Double prezzo;
    private Double misura;
}
