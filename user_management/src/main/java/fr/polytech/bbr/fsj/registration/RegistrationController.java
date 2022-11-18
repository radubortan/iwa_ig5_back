package fr.polytech.bbr.fsj.registration;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/registration")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping("/employer")
    public ResponseEntity<String> registerEmployer(@RequestBody RegistrationRequestEmployer request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(registrationService.registerEmployer(request));
        }
        //if email isn't valid
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        //if email is already taken
        catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PostMapping("/candidate")
    public ResponseEntity<String> registerCandidate(@RequestBody RegistrationRequestCandidate request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(registrationService.registerCandidate(request));
        }
        //if email isn't valid
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        //if email is already taken
        catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }
}
