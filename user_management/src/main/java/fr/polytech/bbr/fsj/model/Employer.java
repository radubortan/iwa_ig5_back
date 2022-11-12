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
public class Employer {
    @Id
    private String email;
    private String companyName;
    private String address;
    private String phoneNumber;
}
