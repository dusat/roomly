package cz.dtkacik.roomly.feature.reservation.dto;

import java.time.LocalDateTime;

public record UpdateReservationDto(
        Long roomId,
        LocalDateTime dateFrom,
        LocalDateTime dateTo
) {}
