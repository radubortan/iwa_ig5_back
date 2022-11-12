package fr.polytech.bbr.fsj.service;

import fr.polytech.bbr.fsj.model.AppUser;
import fr.polytech.bbr.fsj.model.Candidate;
import fr.polytech.bbr.fsj.model.Role;
import fr.polytech.bbr.fsj.registration.token.ConfirmationToken;
import fr.polytech.bbr.fsj.registration.token.ConfirmationTokenService;
import fr.polytech.bbr.fsj.repository.AppUserRepo;
import fr.polytech.bbr.fsj.repository.CandidateRepo;
import fr.polytech.bbr.fsj.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CandidateService {
    private final CandidateRepo candidateRepo;

    //adds user to the database and generates the activation token
    public void saveCandidate(Candidate candidate) {
        candidateRepo.save(candidate);
    }

    public Candidate getCandidate(String email) {
        return candidateRepo.findByEmail(email);
    }

    public List<Candidate> getCandidates() {
        return candidateRepo.findAll();
    }
}
