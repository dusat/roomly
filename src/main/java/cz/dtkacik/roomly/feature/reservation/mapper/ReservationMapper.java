package cz.dtkacik.roomly.feature.reservation.mapper;

import cz.dtkacik.roomly.feature.reservation.domain.Reservation;
import cz.dtkacik.roomly.feature.reservation.dto.CreateReservationDto;
import cz.dtkacik.roomly.feature.reservation.dto.ReservationDto;
import cz.dtkacik.roomly.feature.room.domain.Room;
import cz.dtkacik.roomly.feature.room.dto.CreateRoomDto;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ReservationMapper {

    public Reservation toEntity(CreateReservationDto dto, Room room) {
        if (dto == null || room == null) {
            return null;
        }

        return new Reservation(
                dto.dateFrom(),
                dto.dateTo(),
                room
        );
    }


    public ReservationDto toDto(Reservation reservation) {
        if (reservation == null) {
            return null;
        }

        return new ReservationDto(
                reservation.getId(),
                reservation.getRoom().getId(),
                reservation.getDateFrom(),
                reservation.getDateTo()
        );
    }

}
