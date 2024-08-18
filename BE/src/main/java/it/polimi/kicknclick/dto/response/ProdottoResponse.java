package it.polimi.kicknclick.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProdottoResponse {

    private Long prodottoId;
    private String marca;
    private String modello;
    private Double prezzo;
    private Double misura;
    private byte[] immagine;
    private String stato;
}
