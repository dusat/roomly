package cz.dtkacik.roomly.feature.reservation.controller;

import cz.dtkacik.roomly.feature.reservation.dto.CreateReservationDto;
import cz.dtkacik.roomly.feature.reservation.dto.ReservationDto;
import cz.dtkacik.roomly.feature.reservation.dto.UpdateReservationDto;
import cz.dtkacik.roomly.feature.reservation.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationDto> create(@RequestBody CreateReservationDto dto) {
        ReservationDto created = reservationService.create(dto);
        return ResponseEntity
                .created(URI.create("/api/v1/reservations/" + created.id()))
                .body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDto> update(
            @PathVariable Long id,
            @RequestBody UpdateReservationDto dto) {

        return ResponseEntity.ok(reservationService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
