package cz.dtkacik.roomly.feature.reservation.exception;

import java.time.LocalDateTime;

public class ReservationConflictException extends RuntimeException {

    public ReservationConflictException(Long roomId,
            LocalDateTime from,
            LocalDateTime to) {
        super("Reservation conflict in room " + roomId +
                " for interval [" + from + ", " + to + ']');
    }

}
