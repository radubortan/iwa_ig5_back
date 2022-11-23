package fr.polytech.bbr.fsj.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {
    @Id
    private String id;
    private String lastName;
    private String firstName;
    private LocalDate birthday;
    private String phoneNumber;
    private String linkCv = "";
    private String cvKeywords = "";

    public Candidate(String id, String lastName, String firstName, LocalDate birthday, String phoneNumber) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
    }
}