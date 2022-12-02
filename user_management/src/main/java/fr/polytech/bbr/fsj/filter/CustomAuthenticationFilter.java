package fr.polytech.bbr.fsj.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.polytech.bbr.fsj.model.AppUser;
import fr.polytech.bbr.fsj.service.AppUserService;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private AppUserService appUserService;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            //get the body of the request
            String receivedBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

            //transform the body into JSON
            JSONObject jsonObject = new JSONObject(receivedBody);

            //extract email and password out of the body
            String email = jsonObject.get("email").toString();
            String password = jsonObject.get("password").toString();

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
            return authenticationManager.authenticate(authenticationToken);

        }
        catch (Exception err){
            System.out.println(err);
        }
        throw null;
    }

    @Override
    //authenticates user by sending the jwt access token and refresh token
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User)authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("fsj-Secret".getBytes());

        //injecting the user service
        if(appUserService == null) {
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            appUserService = webApplicationContext.getBean(AppUserService.class);
        }

        //getting the user id
        String email = user.getUsername();
        AppUser appUser = appUserService.getAppUser(email);

        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                //make the token last 7 days
                .withExpiresAt(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withClaim("accountId", appUser.getId())
                .sign(algorithm);

        Map<String, String> data = new HashMap<>();




        data.put("accessToken", accessToken);
        data.put("accountType", user.getAuthorities().iterator().next().toString());

        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), data);
    }
}
