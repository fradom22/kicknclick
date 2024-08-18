package it.polimi.kicknclick.service;

import it.polimi.kicknclick.builder.UtenteBuilder;
import it.polimi.kicknclick.dto.request.ModificaUtenteRequest;
import it.polimi.kicknclick.exception.Exception;
import it.polimi.kicknclick.model.Ruolo;
import it.polimi.kicknclick.model.Utente;
import it.polimi.kicknclick.repository.UtenteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UtenteServiceTest {

    @Mock
    UtenteRepository utenteRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    UtenteService utenteService;

    @Test
    void getThrowsUtenteNonPresente() {
        when(utenteRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> utenteService.get(1L));
    }

    @Test
    void getSuccessful() {
        when(utenteRepository.findById(any())).thenReturn(Optional.of(new UtenteBuilder().username("username").nome("nome").cognome("cognome").build()));
        assertAll(() -> utenteService.get(1L));
    }

    @Test
    void modificaThrowsUtenteNonPresente() {
        ModificaUtenteRequest request = new ModificaUtenteRequest(1L, "username", "nome", "cognome", "vecchiaPassword", "nuovaPassword");
        when(utenteRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> utenteService.modifica(request));
    }

    @Test
    void modificaThrowsUsernameGiaRegistrato() {
        ModificaUtenteRequest request = new ModificaUtenteRequest(1L, "username", "nome", "cognome", "vecchiaPassword", "nuovaPassword");
        Utente utente = new UtenteBuilder().nome("iok").cognome("ok").username("uu").build();
        when(utenteRepository.findById(any())).thenReturn(Optional.of(utente));
        when(utenteRepository.findByUsername(any())).thenReturn(Optional.of(new Utente()));
        assertThrows(Exception.class, () -> utenteService.modifica(request));
    }

    @Test
    void modificaThrowsPasswordErrata() {
        ModificaUtenteRequest request = new ModificaUtenteRequest(1L, "username", "nome", "cognome", "vecchiaPassword", "nuovaPassword");
        Utente utente = new UtenteBuilder().nome("iok").cognome("ok").username("uu").build();
        when(utenteRepository.findById(any())).thenReturn(Optional.of(utente));
        when(utenteRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(passwordEncoder.matches(any(), any())).thenReturn(false);
        assertThrows(Exception.class, () -> utenteService.modifica(request));
    }

    @Test
    void modificaThrowsPasswordUguali() {
        ModificaUtenteRequest request = new ModificaUtenteRequest(1L, "username", "nome", "cognome", "password", "password");
        Utente utente = new UtenteBuilder().nome("iok").cognome("ok").username("uu").build();
        when(utenteRepository.findById(any())).thenReturn(Optional.of(utente));
        when(utenteRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        assertThrows(Exception.class, () -> utenteService.modifica(request));
    }

    @Test
    void modificaSuccessful() {
        ModificaUtenteRequest request = new ModificaUtenteRequest(1L, "username", "nome", "cognome", "vecchiaPassword", "nuovaPassword");
        Utente utente = new UtenteBuilder().nome("iok").cognome("ok").username("uu").build();
        when(utenteRepository.findById(any())).thenReturn(Optional.of(utente));
        when(utenteRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        assertAll(() -> utenteService.modifica(request));
    }

    @Test
    void eliminaThrowsUtenteNonPresente() {
        when(utenteRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> utenteService.elimina(1L));
    }

    @Test
    void eliminaSuccessful() {
        when(utenteRepository.findById(any())).thenReturn(Optional.of(new UtenteBuilder().build()));
        when(utenteRepository.findByUtenteId(any())).thenReturn(Optional.of(new Utente()));
        assertThrows(Exception.class, () -> utenteService.elimina(1L));
    }

    @Test
    void getAllSuccessful() {
        when(utenteRepository.findAllByRuoloIsNotIn(any())).thenReturn(List.of(new UtenteBuilder().utenteId(1L).nome("nome").cognome("cognome").ruolo(Ruolo.SELLER).build()));
        assertAll(() -> utenteService.getAll());
    }
}
