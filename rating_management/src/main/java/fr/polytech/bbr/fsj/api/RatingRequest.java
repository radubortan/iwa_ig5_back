package fr.polytech.bbr.fsj.api;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class RatingRequest {
    private final int value;
    private final String comment;
    private final Long idReceiver;

    public RatingRequest(int value, String comment, Long idReceiver) {
        this.value = value;
        this.comment = comment;
        this.idReceiver = idReceiver;
    }
}
