package it.polimi.kicknclick.dto;

import it.polimi.kicknclick.dto.response.MessaggioResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MessaggioResponseTest {

    @Test
    void messaggioResponseTest() {
        MessaggioResponse response = new MessaggioResponse("messaggio");
        String messaggio = response.getMessaggio();
    }
}
