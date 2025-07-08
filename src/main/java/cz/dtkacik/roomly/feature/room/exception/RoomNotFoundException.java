package cz.dtkacik.roomly.feature.room.exception;

public class RoomNotFoundException extends RuntimeException {

    public RoomNotFoundException(Long id) {
        super("Room with ID " + id + " not found.");
    }

    public RoomNotFoundException(String message) {
        super(message);
    }
}
