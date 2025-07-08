package cz.dtkacik.roomly.feature.room.dto;

import cz.dtkacik.roomly.feature.room.domain.EquipmentType;

import java.util.Set;

public record UpdateRoomDto(
        LocationDto location,
        Integer personCapacity,
        Set<EquipmentType> equipment
) {}
