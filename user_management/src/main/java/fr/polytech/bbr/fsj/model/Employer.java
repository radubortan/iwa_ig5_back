package fr.polytech.bbr.fsj.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@ToString
public class Employer {
    @Id
    private String id;
    private String companyName;
    private String address;
    private String phoneNumber;

    public Employer(String id, String companyName, String address, String phoneNumber) {
        this.id = id;
        this.companyName = companyName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
