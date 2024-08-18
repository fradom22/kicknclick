package it.polimi.kicknclick.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class ExceptionTest {

    @InjectMocks
    ExceptionHandlerController controller;

    @Test
    void exceptionTest() {
        Exception e = new Exception("message");
        ExceptionModel exceptionModel = new ExceptionModel("message", HttpStatus.OK);
        String message = exceptionModel.getMessage();
        HttpStatus status = exceptionModel.getStatus();
        assertAll(() -> controller.handleException(e));
    }
}
