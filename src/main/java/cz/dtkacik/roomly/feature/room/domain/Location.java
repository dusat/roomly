package cz.dtkacik.roomly.feature.room.domain;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

@Entity
@Table(name = "locations",
        uniqueConstraints = @UniqueConstraint(columnNames = {
                "country", "city", "street", "street_number"
        }))
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_seq")
    @SequenceGenerator(
            name = "location_seq",
            sequenceName = "location_seq",
            allocationSize = 50
    )
    private Long id;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "street_number", nullable = false)
    private String streetNumber;

    public Location() { }

    public Location(String country,
            String city,
            String street,
            String streetNumber) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
    }

    public Long getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }
    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Location other = (Location) o;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id != null
                ? id.hashCode()
                : System.identityHashCode(this);
    }
}

