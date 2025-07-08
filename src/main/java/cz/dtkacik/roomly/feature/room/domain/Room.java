package cz.dtkacik.roomly.feature.room.domain;

import cz.dtkacik.roomly.feature.reservation.domain.Reservation;
import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_seq")
    @SequenceGenerator(
            name = "room_seq",
            sequenceName = "room_seq",
            allocationSize = 50
    )
    private Long id;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "person_capacity", nullable = false)
    private Integer personCapacity;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "room_equipment",
            joinColumns = @JoinColumn(name = "room_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "equipment")
    private Set<EquipmentType> equipment = new HashSet<>();

    @OneToMany(mappedBy = "room",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Reservation> reservations = new HashSet<>();

    protected Room() {}

    public Room(Location location,
            Boolean active,
            Integer personCapacity,
            Set<EquipmentType> equipment) {
        this.location = location;
        this.active = active;
        this.personCapacity = personCapacity;
        this.equipment = equipment;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void addReservation(Reservation reservation) {
        reservation.setRoom(this);
        reservations.add(reservation);
    }

    public void removeReservation(Reservation reservation) {
        reservation.setRoom(null);
        reservations.remove(reservation);
    }

    public Long getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer getPersonCapacity() {
        return personCapacity;
    }

    public void setPersonCapacity(Integer capacity) {
        this.personCapacity = capacity;
    }

    public Set<EquipmentType> getEquipment() {
        return equipment;
    }

    public void setEquipment(Set<EquipmentType> equipment) {
        this.equipment = equipment;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    @Override
    public String toString() {
        String locationInfo = "null";
        if (location != null && Hibernate.isInitialized(location)) {
            locationInfo = "Location(id=" + location.getId() + ")";
        } else if (location != null) {
            locationInfo = "Location(uninitialized proxy)";
        }

        String equipmentInfo = Hibernate.isInitialized(equipment)
                ? equipment.stream()
                .map(Enum::name)
                .collect(Collectors.joining(", ", "[", "]"))
                : "[] (uninitialized)";

        String reservationsInfo = Hibernate.isInitialized(reservations)
                ? reservations.stream()
                .map(r -> "Reservation(id=" + r.getId() + ")")
                .collect(Collectors.joining(", ", "[", "]"))
                : "[] (uninitialized)";

        return "Room{" +
                "id=" + id +
                ", active=" + active +
                ", personCapacity=" + personCapacity +
                ", location=" + locationInfo +
                ", equipment=" + equipmentInfo +
                ", reservations=" + reservationsInfo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Room other = (Room) o;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id != null
                ? id.hashCode()
                : System.identityHashCode(this);
    }
}
