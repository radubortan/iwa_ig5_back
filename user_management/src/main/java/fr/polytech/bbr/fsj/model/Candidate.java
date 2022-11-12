package fr.polytech.bbr.fsj.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {
    @Id
    private String email;
    private String lastName;
    private String firstName;
    private LocalDate birthday;
    private String phoneNumber;
    private String linkCv = "";
    private String cvKeywords = "";

    public Candidate(String email, String lastName, String firstName, LocalDate birthday, String phoneNumber) {
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
    }
}
