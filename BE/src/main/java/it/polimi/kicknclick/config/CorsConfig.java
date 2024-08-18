package it.polimi.kicknclick.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

/**
 * Classe di configurazione dei CORS.
 * I CORS (Cross-Origin Resource Sharing) sono un meccanismo che permette di definire quali risorse possono essere
 * richieste da un dominio differente da quello che ha servito la pagina.
 */
@Configuration
public class CorsConfig {

    /**
     * Configurazione base dei CORS.
     *
     * @return Oggetto CorsFilter configurato.
     */
    @Bean
    public CorsFilter corsFilter() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); //Configurazione CORS basata su url.
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(false); //Disattiva la richiesta di credenziali.
        config.addAllowedOrigin("*"); //Consente l'accesso da qualsiasi origine.
        config.addAllowedMethod("*"); //Consente tutti i metodi HTTP.
        config.addAllowedHeader("*"); //Consente tutti gli header.

        source.registerCorsConfiguration("/**", config); //Registra la configurazione precedente.

        return new CorsFilter(source);
    }

    /**
     * Configurazione avanzata dei CORS.
     *
     * @return Oggetto FilterRegistrationBean configurato.
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); //Configurazione CORS basata su url.
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(false); //Disattiva la richiesta di credenziali.
        config.setAllowedOrigins(Arrays.asList("http://localhost:8080", "*")); //Consente l'accesso solo dalla porta locale 8080.
        config.setAllowedMethods(Collections.singletonList("*")); //Qualsiasi metodo è consentito.
        config.setAllowedHeaders(Collections.singletonList("*")); //Qualsiasi header è consentito.
        source.registerCorsConfiguration("/**", config); //Registro la configurazione precedente.

        //Registro la configurazione precedente.
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        //Imposta l'ordine di esecuzione del filtro al valore più alto.
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return bean;
    }
}
