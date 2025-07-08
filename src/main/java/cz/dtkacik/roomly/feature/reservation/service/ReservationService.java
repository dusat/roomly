package cz.dtkacik.roomly.feature.reservation.service;

import cz.dtkacik.roomly.feature.reservation.dto.CreateReservationDto;
import cz.dtkacik.roomly.feature.reservation.dto.ReservationDto;
import cz.dtkacik.roomly.feature.reservation.dto.UpdateReservationDto;

public interface ReservationService {
    ReservationDto create(CreateReservationDto createReservationDto);
    ReservationDto findById(Long id);
    ReservationDto update(Long id, UpdateReservationDto dto);
    void delete(Long id);
}
