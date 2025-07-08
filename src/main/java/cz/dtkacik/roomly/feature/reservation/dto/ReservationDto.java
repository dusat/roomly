package cz.dtkacik.roomly.feature.reservation.dto;

import java.time.LocalDateTime;

public record ReservationDto(
        Long id,
        Long roomId,
        LocalDateTime dateFrom,
        LocalDateTime dateTo
) {}