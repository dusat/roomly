package cz.dtkacik.roomly.feature.room.dto;

import cz.dtkacik.roomly.feature.reservation.dto.ReservationDto;
import cz.dtkacik.roomly.feature.room.domain.EquipmentType;

import java.util.Set;

public record RoomDto(
        Long id,
        Boolean active,
        LocationDto location,
        Integer personCapacity,
        Set<EquipmentType> equipment,
        Set<ReservationDto> reservations
) { }
