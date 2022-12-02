package fr.polytech.bbr.fsj.service;

import fr.polytech.bbr.fsj.model.AppUser;
import fr.polytech.bbr.fsj.model.Role;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AppUserService implements UserDetailsService {
    private final AppUserRepo appUserRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

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

    /**
     * Saves user to database
     * @param user the user
     * @param role the user role
     */
    public void saveUser(AppUser user, String role) {
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
    }

    /**
     * Saves a user role to the database
     * @param role the role
     * @return the role
     */
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    /**
     * Get a role
     * @param id id of the role
     * @return the role
     */
    public Role getRole(String id) {
        AppUser appUser = appUserRepo.findById(id).orElse(null);
        return appUser.getRoles().iterator().next();
    }

    /**
     * Adds a role to a user
     * @param email the email of the user
     * @param roleName the name of the role
     */
    public void addRoleToAppUser(String email, String roleName) {
        AppUser user = appUserRepo.findByEmail(email);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    /**
     * Gets a user by their email
     * @param email the email of the user
     * @return the user
     */
    public AppUser getAppUser(String email) {
        return appUserRepo.findByEmail(email);
    }

    /**
     * Get all app users
     * @return a list of all the users
     */
    public List<AppUser> getAppUsers() {
        return appUserRepo.findAll();
    }
}
