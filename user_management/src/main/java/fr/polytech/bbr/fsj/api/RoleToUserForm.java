package fr.polytech.bbr.fsj.api;

import lombok.Data;

@Data
class RoleToUserForm {
    private String email;
    private String roleName;
}