package cz.dtkacik.roomly.feature.reservation.repository;

import cz.dtkacik.roomly.feature.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
        select count(r) > 0
        from Reservation r
        where r.room.id = :roomId
          and (:reservationId is null or r.id <> :reservationId)
          and r.dateFrom < :newDateTo
          and r.dateTo   > :newDateFrom
    """)
    boolean existsOverlappingReservation(
            @Param("roomId") Long roomId,
            @Param("newDateFrom") LocalDateTime from,
            @Param("newDateTo") LocalDateTime to,
            @Param("reservationId") Long selfId);
}