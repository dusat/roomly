package cz.dtkacik.roomly.feature.reservation.domain;

import cz.dtkacik.roomly.feature.room.domain.Room;
import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservations",
        indexes = @Index(name = "ix_reservation_room_from_to",
                columnList = "room_id, date_from, date_to"))
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_seq")
    @SequenceGenerator(
            name = "reservation_seq",
            sequenceName = "reservation_seq",
            allocationSize = 50
    )
    private Long id;

    @Column(name = "date_from", nullable = false)
    private LocalDateTime dateFrom;

    @Column(name = "date_to", nullable = false)
    private LocalDateTime dateTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    protected Reservation() { }

    public Reservation(LocalDateTime dateFrom,
            LocalDateTime dateTo,
            Room room) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }
    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }
    public void setDateTo(LocalDateTime dateTo) {
        this.dateTo = dateTo;
    }

    public Room getRoom() {
        return room;
    }
    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        String roomInfo = "null";
        if (room != null && Hibernate.isInitialized(room)) {
            roomInfo = "Room(id=" + room.getId() + ")";
        } else if (room != null) {
            roomInfo = "Room(uninitialized proxy)";
        }

        return "Reservation{" +
                "id=" + id +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", room=" + roomInfo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Reservation other = (Reservation) o;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id != null
                ? id.hashCode()
                : System.identityHashCode(this);
    }
}
