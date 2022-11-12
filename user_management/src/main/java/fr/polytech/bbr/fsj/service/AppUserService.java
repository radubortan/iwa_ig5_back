package fr.polytech.bbr.fsj.service;

import fr.polytech.bbr.fsj.model.AppUser;
import fr.polytech.bbr.fsj.model.Role;
import fr.polytech.bbr.fsj.registration.token.ConfirmationToken;
import fr.polytech.bbr.fsj.registration.token.ConfirmationTokenService;
import fr.polytech.bbr.fsj.repository.AppUserRepo;
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
public class AppUserService implements UserDetailsService {
    private final AppUserRepo appUserRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    //logs in a user
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = appUserRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found in the database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new User(user.getEmail(), user.getPassword(), authorities);
    }

    //adds user to the database and generates the activation token
    public String saveUser(AppUser user, String role) {
        //checks if email isn't taken
        boolean userExists = appUserRepo.findByEmail(user.getEmail()) != null;

        //if email taken, throw exception
        if (userExists) {
            throw new IllegalStateException("Email already taken");
        }

        //encode the password and set it
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        //save user to the database
        appUserRepo.save(user);
        addRoleToAppUser(user.getEmail(), role);

        //generate activate token
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user
        );

        //save token to the database
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        //return the token
        return token;
    }

    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    public void addRoleToAppUser(String email, String roleName) {
        AppUser user = appUserRepo.findByEmail(email);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    public AppUser getAppUser(String email) {
        return appUserRepo.findByEmail(email);
    }

    public List<AppUser> getAppUsers() {
        return appUserRepo.findAll();
    }

    //activates the account when the link in the activation link is clicked
    public int enableAppUser(String email) {
        return appUserRepo.enableAppUser(email);
    }
}
