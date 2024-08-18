package it.polimi.kicknclick.config;

import it.polimi.kicknclick.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Classe di configurazione dell'applicazione.
 * Contiene i bean per la gestione dell'autenticazione e dell'utente in sessione.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UtenteRepository utenteRepository;

    /**
     * Bean che specifica come gestire l'utente in sessione.
     *
     * @return Un'istanza dell'UserDetailsService.
     */
    @Bean
    public UserDetailsService userDetailsService() {

        return username -> utenteRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato"));
    }

    /**
     * Bean che specifica come gestire l'autenticazione.
     *
     * @return Un'istanza dell'AuthenticationProvider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    /**
     * Bean che specifica come gestire l'autenticazione.
     *
     * @param config Configurazione dell'autenticazione.
     * @return Un'istanza dell'AuthenticationManager.
     * @throws Exception Se non è possibile ottenere l'AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();
    }

    /**
     * Bean che specifica come codificare la password.
     *
     * @return Un'istanza del PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
