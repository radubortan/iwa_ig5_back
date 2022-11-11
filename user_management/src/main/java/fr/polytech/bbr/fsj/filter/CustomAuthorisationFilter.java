package fr.polytech.bbr.fsj.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomAuthorisationFilter extends OncePerRequestFilter {
    @Override
    //makes sure that only requests that provide a jwt are allowed
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //if we're on these routes, no need to provide a jwt
        if (request.getServletPath().equals("/api/login") || request.getServletPath().equals("/api/token/refresh") || request.getServletPath().equals("/api/registration")) {
            filterChain.doFilter(request, response);
        } else {
            //get the authorization header
            String authorisationHeader = request.getHeader(AUTHORIZATION);
            //check that it isn't null and that it starts correctly
            if (authorisationHeader != null && authorisationHeader.startsWith("Bearer ")) {
                try {
                    //extract the jwt
                    String token = authorisationHeader.substring("Bearer ".length());
                    //setting up the signature algorithm
                    Algorithm algorithm = Algorithm.HMAC256("fsj-Secret".getBytes());
                    //setting up the decoder
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    //decoding the token
                    DecodedJWT decodedJWT = verifier.verify(token);
                    //extracting the email
                    String email = decodedJWT.getSubject();
                    //extracting the roles
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role));
                    });
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    response.setHeader("error", e.getMessage());
                    response.setStatus(FORBIDDEN.value());

                    Map<String, String> error = new HashMap<>();
                    error.put("errorMessage", e.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
