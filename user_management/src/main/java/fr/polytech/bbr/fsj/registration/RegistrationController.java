package fr.polytech.bbr.fsj.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/registration")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping("/employer")
    public String registerEmployer(@RequestBody RegistrationRequestEmployer request) {
        return registrationService.registerEmployer(request);
    }

    @PostMapping("/candidate")
    public String registerCandidate(@RequestBody RegistrationRequestCandidate request) {
        return registrationService.registerCandidate(request);
    }
}
