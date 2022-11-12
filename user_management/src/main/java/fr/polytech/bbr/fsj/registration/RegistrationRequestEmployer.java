package fr.polytech.bbr.fsj.registration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode
@ToString
public class RegistrationRequestEmployer {
    private final String email;
    private final String password;
    private final String companyName;
    private final String address;
    private final String phoneNumber;

    public RegistrationRequestEmployer(String email, String password, String companyName, String address, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.companyName = companyName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
