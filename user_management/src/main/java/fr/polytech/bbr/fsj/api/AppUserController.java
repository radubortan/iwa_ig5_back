package fr.polytech.bbr.fsj.api;

import fr.polytech.bbr.fsj.model.Candidate;
import fr.polytech.bbr.fsj.model.Employer;
import fr.polytech.bbr.fsj.model.Role;
import fr.polytech.bbr.fsj.security.JWTDecryption;
import fr.polytech.bbr.fsj.service.AppUserService;
import fr.polytech.bbr.fsj.service.CandidateService;
import fr.polytech.bbr.fsj.service.EmployerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AppUserController {
    private final AppUserService appUserService;
    private final CandidateService candidateService;
    private final EmployerService employerService;

    //for an employer to update their profile info
    @PutMapping("/users/employer/{id}/update")
    public ResponseEntity<Employer> updateEmployerInfo(@PathVariable String id, @RequestBody Employer employer, @RequestHeader(AUTHORIZATION) String jwt) {
        try {
            JWTDecryption jwtDecryption = new JWTDecryption(jwt);
            String email = jwtDecryption.getEmail();
            String role = jwtDecryption.getRole();

            //we get the id corresponding to the email stored in the jwt, so we get the identity of the user
            String requesterId = appUserService.getAppUser(email).getId();

            //we make sure that the user that needs to be updated has the same id as the person trying to do the update
            if (requesterId.equals(id) &&  role.equals("ROLE_EMPLOYER")) {
                return ResponseEntity.ok().body(employerService.updateEmployer(employer));
            }
            return ResponseEntity.status(FORBIDDEN).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(null);
        }
    }

    //for a candidate to update their profile info
    @PutMapping("/users/candidate/{id}/update")
    public ResponseEntity<Candidate> updateCandidateInfo(@PathVariable String id, @RequestBody Candidate candidate, @RequestHeader(AUTHORIZATION) String jwt) {
        try {
            JWTDecryption jwtDecryption = new JWTDecryption(jwt);
            String email = jwtDecryption.getEmail();
            String role = jwtDecryption.getRole();

            //we get the id corresponding to the email stored in the jwt, so we get the identity of the user
            String requesterId = appUserService.getAppUser(email).getId();

            //we make sure that the user that needs to be updated has the same id as the person trying to do the update
            if (requesterId.equals(id) && role.equals("ROLE_CANDIDATE")) {
                return ResponseEntity.ok().body(candidateService.updateCandidate(candidate));
            }
            return ResponseEntity.status(FORBIDDEN).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(null);
        }
    }

    //for an employer to get their id
    @GetMapping("/users/employer/id")
    public ResponseEntity<String> getEmployerId(@RequestHeader(AUTHORIZATION) String jwt) {
        try {
            JWTDecryption jwtDecryption = new JWTDecryption(jwt);
            String email = jwtDecryption.getEmail();
            String role = jwtDecryption.getRole();

            //id corresponding to the employer
            String id = appUserService.getAppUser(email).getId();

            //we make sure that the requester is an employer
            if (role.equals("ROLE_EMPLOYER")) {
                return ResponseEntity.ok().body(id.toString());
            }
            return ResponseEntity.status(FORBIDDEN).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(null);
        }
    }

    //for a candidate to get their id
    @GetMapping("/users/candidate/id")
    public ResponseEntity<String> getCandidateId(@RequestHeader(AUTHORIZATION) String jwt) {
        try {
            JWTDecryption jwtDecryption = new JWTDecryption(jwt);
            String email = jwtDecryption.getEmail();
            String role = jwtDecryption.getRole();

            //id corresponding to the candidate
            String id = appUserService.getAppUser(email).getId();

            //we make sure that the requester is a candidate
            if (role.equals("ROLE_CANDIDATE")) {
                return ResponseEntity.ok().body(id);
            }
            return ResponseEntity.status(FORBIDDEN).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(null);
        }
    }

    //get info of an employer by their id
    @GetMapping("/users/employer/{id}")
    public ResponseEntity<Employer> getEmployerById(@PathVariable String id) {
        return ResponseEntity.ok().body(employerService.getEmployerById(id));
    }

    //get info about candidate by their id
    @GetMapping("/users/candidate/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable String id) {
        return ResponseEntity.ok().body(candidateService.getCandidateById(id));
    }

    //get the role of a user by their id
    @GetMapping("/users/{id}/role")
    public ResponseEntity<Role> getEmployerRole(@PathVariable String id) {
        return ResponseEntity.ok().body(appUserService.getRole(id));
    }

    //get all candidates
    @GetMapping("/users/candidates")
    public ResponseEntity<List<Candidate>> getCandidates() {
        return ResponseEntity.ok().body(candidateService.getCandidates());
    }

    //get the cv link given a candidate id
    @GetMapping("/users/candidate/{idCandidate}/cv_link")
    public ResponseEntity<String> getCvLink(@PathVariable String idCandidate) {
        try {
            return ResponseEntity.ok().body(candidateService.getCvLink(idCandidate));
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(NOT_FOUND).body("");
        }
    }

    //get the cv keywords given a candidate id
    @GetMapping("/users/candidate/{idCandidate}/cv_keywords")
    public ResponseEntity<String> getCvKeywords(@PathVariable String idCandidate) {
        try {
            return ResponseEntity.ok().body(candidateService.getCvKeyboards(idCandidate));
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(NOT_FOUND).body("");
        }
    }

    //for a candidate to update their cv link
    @PutMapping("/users/candidate/{idCandidate}/cv_link")
    public ResponseEntity<String> updateCvLink(@PathVariable String idCandidate, @RequestBody String cvLink, @RequestHeader(AUTHORIZATION) String jwt) {
        JWTDecryption jwtDecryption = new JWTDecryption(jwt);
        String email = jwtDecryption.getEmail();
        String role = jwtDecryption.getRole();

        //id corresponding to the candidate
        String id = appUserService.getAppUser(email).getId();

        try {
            //making sure it's a candidate updating their own profile
            if(role.equals("ROLE_CANDIDATE") && idCandidate.equals(id)) {
                return ResponseEntity.ok().body(candidateService.updateCvLink(idCandidate, cvLink));
            }
            return ResponseEntity.status(FORBIDDEN).body(null);
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(NOT_FOUND).body(null);
        }

    }

    //for a candidate to update their cv keywords
    @PutMapping("/users/candidate/{idCandidate}/cv_keywords")
    public ResponseEntity<String> updateCvKeywords(@PathVariable String idCandidate, @RequestBody String cvKeywords, @RequestHeader(AUTHORIZATION) String jwt) {
        JWTDecryption jwtDecryption = new JWTDecryption(jwt);
        String email = jwtDecryption.getEmail();
        String role = jwtDecryption.getRole();

        //id corresponding to the candidate
        String id = appUserService.getAppUser(email).getId();

        try {
            //making sure it's a candidate updating their own profile
            if (role.equals("ROLE_CANDIDATE") && idCandidate.equals(id)) {
                return ResponseEntity.ok().body(candidateService.updateCvKeywords(idCandidate, cvKeywords));
            }
            return ResponseEntity.status(FORBIDDEN).body(null);
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(NOT_FOUND).body(null);
        }
    }
}

