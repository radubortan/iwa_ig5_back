package fr.polytech.brr.fsj.recommendation_management.job_offer_recommendation.models;

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
    private Long id;
    private String lastName;
    private String firstName;
    private LocalDate birthday;
    private String phoneNumber;
    private String linkCv = "";
    private String cvKeywords = "";

    public Candidate(Long id, String lastName, String firstName, LocalDate birthday, String phoneNumber) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
    }
}
