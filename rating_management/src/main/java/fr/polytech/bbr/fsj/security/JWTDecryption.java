package fr.polytech.bbr.fsj.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTDecryption {
    private DecodedJWT jwt;

    public JWTDecryption(String jwt) {
        String token = jwt.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("fsj-Secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();

        this.jwt = verifier.verify(token);
    }

    /**
     * Get the role out of the token
     * @return the role
     */
    public String getRole() {
        String role = jwt.getClaim("roles").asArray(String.class)[0];
        return role;
    }

    /**
     * Get the email out of the token
     * @return the email
     */
    public String getEmail() {
        return jwt.getSubject();
    }

    /**
     * Get a claim out of the token
     * @param claim name of the claim
     * @return the claim
     */
    public Object getClaim(String claim) {
        return jwt.getClaim(claim);
    }

    /**
     * Get the account if out of the token
     * @return the account id
     */
    public String getAccountId() {
        return jwt.getClaim("accountId").asString();
    }
}
