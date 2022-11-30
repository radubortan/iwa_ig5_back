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

    /**
     * For an employer to update their profile information
     * @param id the employer id
     * @param employer an employer object with the new information
     * @param jwt token for authorisation
     * @return the updated employer
     */
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


    /**
     * For a candidate to update their profile information
     * @param id the candidate id
     * @param candidate a candidate object with the new information
     * @param jwt token for authorisation
     * @return the updated candidate
     */
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

    /**
     * For an employer to get their id
     * @param jwt token for authorisation
     * @return the id of the employer
     */
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

    /**
     * For a candidate to get their id
     * @param jwt token for authorisation
     * @return the id of the candidate
     */
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

    /**
     * Get information about an employer by their id
     * @param id the id of the employer
     * @return the employer
     */
    @GetMapping("/users/employer/{id}")
    public ResponseEntity<Employer> getEmployerById(@PathVariable String id) {
        return ResponseEntity.ok().body(employerService.getEmployerById(id));
    }

    /**
     * Get information about a candidate by their id
     * @param id the id of the candidate
     * @return the candidate
     */
    @GetMapping("/users/candidate/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable String id) {
        return ResponseEntity.ok().body(candidateService.getCandidateById(id));
    }

    /**
     * Get the role of a user
     * @param id the id of the employer
     * @return the role
     */
    @GetMapping("/users/{id}/role")
    public ResponseEntity<Role> getRole(@PathVariable String id) {
        return ResponseEntity.ok().body(appUserService.getRole(id));
    }

    /**
     * Get all candidates
     * @return A list of all the candidates
     */
    @GetMapping("/users/candidates")
    public ResponseEntity<List<Candidate>> getCandidates() {
        return ResponseEntity.ok().body(candidateService.getCandidates());
    }

    /**
     * Get the CV link of a candidate given their id
     * @param idCandidate the id of the candidate
     * @return the CV link
     */
    @GetMapping("/users/candidate/{idCandidate}/cv_link")
    public ResponseEntity<String> getCvLink(@PathVariable String idCandidate) {
        try {
            return ResponseEntity.ok().body(candidateService.getCvLink(idCandidate));
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(NOT_FOUND).body("");
        }
    }

    /**
     * Get the CV keywords of a candidate given their id
     * @param idCandidate the id of the candidate
     * @return the CV keywords
     */
    @GetMapping("/users/candidate/{idCandidate}/cv_keywords")
    public ResponseEntity<String> getCvKeywords(@PathVariable String idCandidate) {
        try {
            return ResponseEntity.ok().body(candidateService.getCvKeyboards(idCandidate));
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(NOT_FOUND).body("");
        }
    }

    /**
     * Update the CV link of a candidate
     * @param idCandidate id of the candidate whose CV link to update
     * @param cvLink the new CV link
     * @param jwt token for authorisation
     * @return the new CV link
     */
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

    /**
     * Update the CV keywords of a candidate
     * @param idCandidate id of the candidate whose CV keywords to update
     * @param cvKeywords the new CV keywords
     * @param jwt token for authorisation
     * @return the new CV keywords
     */
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

