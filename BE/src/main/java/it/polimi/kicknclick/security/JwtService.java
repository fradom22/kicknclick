package it.polimi.kicknclick.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service per gestire tutti i metodi inerenti al jwt.
 */
@Service
public class JwtService {

    //Stringa che elabora una chiave segreta per codificare e decodificare il token.
    private static final String SECRET_KEY = "4D30457130736F38466A626A6251534F6D61366E31674F7356394B395031676A";

    /**
     * Estrae l'username dall'oggetto jwt.
     * @param token Stringa jwt.
     * @return L'username dell'utente.
     */
    public String extractUsername(String token) {

        //getSubject restituisce l'attributo univoco dell'utente.
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Estrae un singolo dato (generico) dall'insieme di tutti i dati codificati nel token.
     * @param token          Stringa jwt.
     * @param claimsResolver Funzione che estrae il dato desiderato.
     * @param <T>            Tipo generico del dato da estrarre.
     * @return Il dato desiderato.
     */
    public <T> T extractClaim(String token, @NonNull Function<Claims, T> claimsResolver) {

        //Estraggo tutti i dati codificati nel token.
        final Claims claims = extractAllClaims(token);

        //Estraggo il singolo dato (generico) dall'insieme di tutti i dati codificati nel token.
        return claimsResolver.apply(claims);
    }

    /**
     * Genera un token jwt a partire da un oggetto UserDetails.
     * @param userDetails Oggetto UserDetails dell'utente loggato.
     * @return Il jwt.
     */
    public String generateToken(UserDetails userDetails) {

        //Chiama il metodo sottostante passando un hashmap vuoto per altri parametri facoltativi.
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Genera un token jwt a partire da un oggetto UserDetails e da eventuali informazioni aggiuntive.
     * @param extraClaims Informazioni aggiuntive da codificare.
     * @param userDetails Oggetto UserDetails dell'utente loggato.
     * @return Il jwt.
     */
    public String generateToken(
            Map<String, Object> extraClaims,
            @NonNull UserDetails userDetails) {

        return Jwts
                .builder() //Design pattern builder.
                .setClaims(extraClaims) //Aggiungo eventuali informazioni aggiuntive da codificare.
                .setSubject(userDetails.getUsername()) //Aggiungo l'attributo univoco dell'utente.
                .setIssuedAt(new Date(System.currentTimeMillis())) //Data di creazione del token.
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) //Data di scadenza del token.
                .signWith(getSigninKey(), SignatureAlgorithm.HS256) //Codifico con la chiave segreta e specifico l'algoritmo.
                .compact(); //Genero il token.
    }

    /**
     * Controlla se il token è valido.
     * @param token       Stringa jwt.
     * @param userDetails Oggetto UserDetails dell'utente loggato.
     * @return "true" se il token è valido, "false" se il token non è valido.
     */
    public boolean isTokenValid(String token, @NonNull UserDetails userDetails) {

        //Estraggo l'username dal token.
        final String username = extractUsername(token);

        //Controllo se l'username dell'utente loggato è uguale a quello del token e poi controllo la scadenza.
        return ((username.equals(userDetails.getUsername())) && !isTokenExpired(token));
    }

    /**
     * Controlla se la data di scadenza del token è precedente a quella attuale.
     * @param token Stringa jwt.
     * @return "true" se il token è scaduto, "false" se il token non è scaduto.
     */
    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    /**
     * Dato un token, viene restituita la data di scadenza.
     * @param token Stringa jwt.
     * @return La data di scadenza del token.
     */
    private Date extractExpiration(String token) {

        //getExpiration ritorna la data di scadenza del token.
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Utilizza la classe Jwts della dependency io.jsonwebtoken per
     * estrarre i dati che sono stati codificati nel jwt.
     * @param token Stringa jwt.
     * @return Tutti i dati codificati nel jwt.
     */
    private Claims extractAllClaims(String token) {

        return Jwts
                .parserBuilder() //Parser per poter utilizzare il builder.
                .setSigningKey(getSigninKey()) //Setto la chiave segreta per codificare e decodificare il token.
                .build() //Applico il pattern builder, quindi "costruisco" l'oggetto.
                .parseClaimsJws(token) //Mando il token da decodificare.
                .getBody(); //Prendo tutti i dati codificati nel jwt.
    }

    /**
     * Decodifica la stringa in base 64 e la codifica secondo un algoritmo di hash, così da poter lavorare con il jwt.
     * @return La chiave codificata, con cui gestire il jwt.
     */
    private @NonNull Key getSigninKey() {

        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); //Decodifico la chiave segreta in base 64.

        /* Viene codificata la chiave secondo un tipo di algoritmo di hash a chiave costruito a partire
        dalla funzione di hash SHA-256 e utilizzato come Hash-based Message Authentication Code (HMAC). */
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
