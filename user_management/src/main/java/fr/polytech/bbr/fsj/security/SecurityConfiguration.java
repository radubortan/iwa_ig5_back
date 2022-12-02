package fr.polytech.bbr.fsj.security;

import fr.polytech.bbr.fsj.filter.CustomAuthenticationFilter;
import fr.polytech.bbr.fsj.filter.CustomAuthorisationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //setting the route for the login page
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //routes that can be accessed without being logged in
        http.authorizeRequests().antMatchers("/api/login/**", "/api/registration/**", "/api/users/candidates").permitAll();

        //all authenticated users can view employers and candidates
        http.authorizeRequests().antMatchers(GET, "/api/users/employer/**", "/api/users/candidate/**").authenticated();

        //for employer and candidates to get their own ids
        http.authorizeRequests().antMatchers(GET, "/api/users/employer/id").hasAuthority("ROLE_EMPLOYER");
        http.authorizeRequests().antMatchers(GET, "/api/users/candidate/id").hasAuthority("ROLE_CANDIDATE");

        //updating an employer is only accessible to an employer
        http.authorizeRequests().antMatchers(PUT, "/api/users/employer/{id}/update").hasAuthority("ROLE_EMPLOYER");

        //updating a candidate is only accessible to a candidate
        http.authorizeRequests().antMatchers(PUT, "/api/users/candidate/{id}/update").hasAuthority("ROLE_CANDIDATE");

        //updating CV link and CV keywords
        http.authorizeRequests().antMatchers(PUT, "api/users/candidate/{idCandidate}/cv_link", "api/users/candidate/{idCandidate}/cv_keywords").hasAuthority("ROLE_CANDIDATE");

        //all other requests require user to be authenticated
        http.authorizeRequests().anyRequest().authenticated();

        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorisationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}


