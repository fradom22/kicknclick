package it.polimi.kicknclick.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecensioneRequest {

    private Long prodottoId;
    private Long customerId;
    private String testoRecensione;
    private Integer votoRecensione;
}
