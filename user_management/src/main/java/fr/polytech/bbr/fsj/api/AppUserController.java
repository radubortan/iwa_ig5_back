package fr.polytech.bbr.fsj.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import fr.polytech.bbr.fsj.model.Candidate;
import fr.polytech.bbr.fsj.model.Employer;
import fr.polytech.bbr.fsj.model.Role;
import fr.polytech.bbr.fsj.service.AppUserService;
import fr.polytech.bbr.fsj.service.CandidateService;
import fr.polytech.bbr.fsj.service.EmployerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AppUserController {
    private final AppUserService appUserService;
    private final CandidateService candidateService;
    private final EmployerService employerService;

    //for an employer to update their profile info
    @PutMapping("/users/employer/{id}/update")
    public ResponseEntity<Employer> updateEmployerInfo(@PathVariable Long id, @RequestBody Employer employer, @RequestHeader(AUTHORIZATION) String jwt) {
        try {
            String token = jwt.substring("Bearer ".length());
            Algorithm algorithm = Algorithm.HMAC256("fsj-Secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            String email = decodedJWT.getSubject();
            String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

            //we get the id corresponding to the email stored in the jwt, so we get the identity of the user
            Long requesterId = appUserService.getAppUser(email).getId();

            //we make sure that the user that needs to be updated has the same id as the person trying to do the update
            if (requesterId.equals(id) &&  roles[0].equals("ROLE_EMPLOYER")) {
                return ResponseEntity.ok().body(employerService.updateEmployer(employer));
            }
            throw new Exception();
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(null);
        }
    }

    //for a candidate to update their profile info
    @PutMapping("/users/candidate/{id}/update")
    public ResponseEntity<Candidate> updateCandidateInfo(@PathVariable Long id, @RequestBody Candidate candidate, @RequestHeader(AUTHORIZATION) String jwt) {
        try {
            String token = jwt.substring("Bearer ".length());
            Algorithm algorithm = Algorithm.HMAC256("fsj-Secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            String email = decodedJWT.getSubject();
            String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

            //we get the id corresponding to the email stored in the jwt, so we get the identity of the user
            Long requesterId = appUserService.getAppUser(email).getId();

            //we make sure that the user that needs to be updated has the same id as the person trying to do the update
            if (requesterId.equals(id) && roles[0].equals("ROLE_CANDIDATE")) {
                return ResponseEntity.ok().body(candidateService.updateCandidate(candidate));
            }
            throw new Exception();
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(null);
        }
    }

    //for an employer to get their id
    @GetMapping("/users/employer/id")
    public ResponseEntity<String> getEmployerId(@RequestHeader(AUTHORIZATION) String jwt) {
        try {
            String token = jwt.substring("Bearer ".length());
            Algorithm algorithm = Algorithm.HMAC256("fsj-Secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            String email = decodedJWT.getSubject();
            String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

            //id corresponding to the employer
            Long id = appUserService.getAppUser(email).getId();

            //we make sure that the requester is an employer
            if (roles[0].equals("ROLE_EMPLOYER")) {
                return ResponseEntity.ok().body(id.toString());
            }
            throw new Exception();
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(null);
        }
    }

    //for a candidate to get their id
    @GetMapping("/users/candidate/id")
    public ResponseEntity<String> getCandidateId(@RequestHeader(AUTHORIZATION) String jwt) {
        try {
            String token = jwt.substring("Bearer ".length());
            Algorithm algorithm = Algorithm.HMAC256("fsj-Secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            String email = decodedJWT.getSubject();
            String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

            //id corresponding to the candidate
            Long id = appUserService.getAppUser(email).getId();

            //we make sure that the requester is a candidate
            if (roles[0].equals("ROLE_CANDIDATE")) {
                return ResponseEntity.ok().body(id.toString());
            }
            throw new Exception();
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(null);
        }
    }

    //get info of an employer by their id
    @GetMapping("/users/employer/{id}")
    public ResponseEntity<Employer> getEmployerById(@PathVariable Long id) {
        return ResponseEntity.ok().body(employerService.getEmployerById(id));
    }

    //get info about candidate by their id
    @GetMapping("/users/candidate/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable Long id) {
        return ResponseEntity.ok().body(candidateService.getCandidateById(id));
    }

    //get the role of a user by their id
    @GetMapping("/users/{id}/role")
    public ResponseEntity<Role> getEmployerRole(@PathVariable Long id) {
        return ResponseEntity.ok().body(appUserService.getRole(id));
    }
}

