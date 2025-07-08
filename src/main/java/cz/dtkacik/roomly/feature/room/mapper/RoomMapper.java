package cz.dtkacik.roomly.feature.room.mapper;

import cz.dtkacik.roomly.feature.reservation.mapper.ReservationMapper;
import cz.dtkacik.roomly.feature.room.domain.EquipmentType;
import cz.dtkacik.roomly.feature.room.domain.Room;
import cz.dtkacik.roomly.feature.room.dto.CreateRoomDto;
import cz.dtkacik.roomly.feature.room.dto.RoomDto;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoomMapper {

    private final LocationMapper locationMapper;
    private final ReservationMapper reservationMapper;

    public RoomMapper(LocationMapper locationMapper,
            ReservationMapper reservationMapper) {
        this.locationMapper = locationMapper;
        this.reservationMapper = reservationMapper;
    }

    public Room toEntity(CreateRoomDto dto) {
        return new Room(
                locationMapper.toEntity(dto.location()),
                dto.active(),
                dto.personCapacity(),
                dto.equipment() != null ? dto.equipment() : Set.of()
        );
    }

    public RoomDto toDto(Room room) {
        Set<EquipmentType> equipment = room.getEquipment() == null
                ? Set.of()
                : new HashSet<>(room.getEquipment()); // kopie ⇒ už neproxies

        return new RoomDto(
                room.getId(),
                room.getActive(),
                locationMapper.toDto(room.getLocation()),
                room.getPersonCapacity(),
                equipment,
                room.getReservations().stream()
                        .map(reservationMapper::toDto)
                        .collect(Collectors.toSet())
        );
    }
}