package fr.polytech.bbr.fsj.registration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode
@ToString
public class RegistrationRequestCandidate{
    private final String email;
    private final String password;
    private final String lastName;
    private final String firstName;
    private final LocalDate birthday;
    private final String phoneNumber;

    public RegistrationRequestCandidate(String email, String password, String lastName, String firstName, LocalDate birthday, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
    }
}
