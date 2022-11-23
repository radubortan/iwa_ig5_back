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
public class Rating {
    @Id
    private String id;
    private int value;
    private LocalDate date;
    private String comment;
    private String idSender;
    private String idReceiver;

    public Rating(String id, int value, String comment, String idSender, String idReceiver) {
        this.id = id;
        this.value = value;
        this.comment = comment;
        this.idSender = idSender;
        this.idReceiver = idReceiver;
        this.date = LocalDate.now();
    }
}
