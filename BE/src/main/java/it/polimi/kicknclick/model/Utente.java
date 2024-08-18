package it.polimi.kicknclick.model;

import it.polimi.kicknclick.builder.UtenteBuilder;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Questa classe rappresenta l'entità Utente.
 * Implementa l'interfaccia UserDetails di Spring Security per la gestione dell'autenticazione e autorizzazione.
 */
@Data
@NoArgsConstructor
@Entity(name = "Utente")
@Table(
        name = "utente",
        uniqueConstraints = @UniqueConstraint(
                name = "username_unique",
                columnNames = "username"))
public class Utente implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "utente_sequence",
            sequenceName = "utente_sequence",
            allocationSize = 1)
    @GeneratedValue(
            generator = "utente_sequence",
            strategy = GenerationType.SEQUENCE)
    @Column(name = "utente_id", nullable = false, updatable = false)
    private Long utenteId;

    @Column(name = "nome", nullable = false, columnDefinition = "VARCHAR(50)")
    private String nome;

    @Column(name = "cognome", nullable = false, columnDefinition = "VARCHAR(50)")
    private String cognome;

    @Column(name = "username", nullable = false, unique = true, columnDefinition = "VARCHAR(50)")
    private String username;

    @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(100)")
    private String password;

    @Column(name = "ruolo", nullable = false, updatable = false, columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;

    @OneToMany(mappedBy = "utente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Acquisto> acquisti; //Lista di prodotti acquistati dal customer.

    @OneToMany(mappedBy = "utente")
    private List<Prodotto> prodottiPubblicati; //Lista di prodotti pubblicati dal seller.

    /**
     * Costruttore della classe Utente.
     * Viene utilizzato il pattern Builder per la costruzione dell'oggetto.
     *
     * @param builder Il builder dell'utente con i relativi attributi settati.
     */
    public Utente(@NonNull UtenteBuilder builder) {
        this.utenteId = builder.getUtenteId();
        this.nome = builder.getNome();
        this.cognome = builder.getCognome();
        this.username = builder.getUsername();
        this.password = builder.getPassword();
        this.ruolo = builder.getRuolo();
        this.acquisti = builder.getAcquisti();
        this.prodottiPubblicati = builder.getProdottiPubblicati();
    }

    /**
     * In base al ruolo, viene generata una lista delle funzionalità concesse.
     *
     * @return I permessi dell'utente.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + ruolo.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Indica se l'account dell'utente è scaduto.
     *
     * @return "true" se l'account è valido, "false" se non è valido.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indica se l'account dell'utente è bloccato o meno.
     *
     * @return "true" se l'account non è bloccato, "false" se l'account è bloccato.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indica se la password dell'utente è scaduta
     *
     * @return "true" se la password è valida, "false" se non è più valida.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indica se l'utente è abilitato o disabilitato.
     *
     * @return "true" se l'utente è abilitato, "false" se l'utente non è abilitato.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
