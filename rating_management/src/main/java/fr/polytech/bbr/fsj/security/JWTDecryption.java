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

    public String getRole() {
        String role = jwt.getClaim("roles").asArray(String.class)[0];
        return role;
    }

    public String getEmail() {
        return jwt.getSubject();
    }

    public String getClaim(String claim) {
        return jwt.getClaim(claim).toString();
    }

    public String getAccountId() {
        return jwt.getClaim("accountId").asString();
    }
}
