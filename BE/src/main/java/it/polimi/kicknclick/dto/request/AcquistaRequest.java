package it.polimi.kicknclick.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AcquistaRequest {

    private Long scarpaId;
    private Long utenteId;
}
