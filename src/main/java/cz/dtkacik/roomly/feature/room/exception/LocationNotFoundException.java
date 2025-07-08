package cz.dtkacik.roomly.feature.room.exception;

public class LocationNotFoundException extends RuntimeException {

    public LocationNotFoundException(Long id) {
        super("Location with ID " + id + " not found.");
    }

    public LocationNotFoundException(String message) {
        super(message);
    }

}
