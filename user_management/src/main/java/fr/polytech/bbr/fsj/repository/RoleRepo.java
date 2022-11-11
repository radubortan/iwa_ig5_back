package fr.polytech.bbr.fsj.repository;

import fr.polytech.bbr.fsj.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
        Role findByName(String name);
}
