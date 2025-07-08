package cz.dtkacik.roomly.feature.room.mapper;

import cz.dtkacik.roomly.feature.room.domain.Location;
import cz.dtkacik.roomly.feature.room.dto.LocationDto;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

    public Location toEntity(LocationDto dto) {
        if (dto == null) {
            return null;
        }

        Location location = new Location();
        location.setCountry(dto.country());
        location.setCity(dto.city());
        location.setStreet(dto.street());
        location.setStreetNumber(dto.streetNumber());
        return location;
    }

    public LocationDto toDto(Location location) {
        if (location == null) {
            return null;
        }

        return new LocationDto(
                location.getId(),
                location.getCountry(),
                location.getCity(),
                location.getStreet(),
                location.getStreetNumber()
        );
    }
}
