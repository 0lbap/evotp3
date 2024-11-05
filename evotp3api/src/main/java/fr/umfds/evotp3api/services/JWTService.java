package fr.umfds.evotp3api.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import fr.umfds.evotp3api.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class JWTService {

    @Value("security.jwt.issuer")
    private String issuer;
    @Value("security.jwt.secret")
    private String securityHmac;

    private final Algorithm algorithm = Algorithm.HMAC256(getSecurityHmac());
    private final JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer(getIssuer())
            .build();

    /**
     * Créer un {@link JWT} pour l'utilisateur donnée valide pendant 1 mois.
     *
     * @param user l'user
     * @return le JWT en String
     */
    public String issue(User user) {
        return JWT.create()
                .withIssuer(getIssuer())
                .withSubject("" + user.getId())
                .withClaim("username", user.getEmail())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(31, ChronoUnit.DAYS))
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);
    }

    /**
     * Extrait l'ID utilisateur à partir du JWT afin d'assurer l'authentification.
     *
     * @param jwt le JWT
     * @return l'user ID (optionnel)
     */
    public Optional<String> extractUsername(String jwt) {
        try {
            return Optional.of(verifier.verify(jwt).getClaim("username").asString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public String getIssuer() {
        if (issuer == null)
            return "default";
        return issuer;
    }

    public String getSecurityHmac() {
        if (securityHmac == null)
            return "default";
        return securityHmac;
    }
}
