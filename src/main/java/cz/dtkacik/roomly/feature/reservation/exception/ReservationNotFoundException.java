package cz.dtkacik.roomly.feature.reservation.exception;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(Long id) {
        super("Reservation with ID " + id + " not found.");
    }

    public ReservationNotFoundException(String message) {
        super(message);
    }
}

