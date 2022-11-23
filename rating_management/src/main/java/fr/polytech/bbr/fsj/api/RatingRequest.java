package fr.polytech.bbr.fsj.api;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class RatingRequest {
    private final String id;
    private final int value;
    private final String comment;
    private final String idReceiver;

    public RatingRequest(String id, int value, String comment, String idReceiver) {
        this.id = id;
        this.value = value;
        this.comment = comment;
        this.idReceiver = idReceiver;
    }
}
