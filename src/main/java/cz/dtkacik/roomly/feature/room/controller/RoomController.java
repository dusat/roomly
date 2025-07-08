package cz.dtkacik.roomly.feature.room.controller;

import cz.dtkacik.roomly.feature.room.dto.CreateRoomDto;
import cz.dtkacik.roomly.feature.room.dto.RoomDto;
import cz.dtkacik.roomly.feature.room.dto.UpdateRoomDto;
import cz.dtkacik.roomly.feature.room.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<RoomDto> create(@RequestBody CreateRoomDto dto) {
        RoomDto created = roomService.create(dto);
        return ResponseEntity
                .created(URI.create("/api/v1/rooms/" + created.id()))
                .body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> getById(@PathVariable Long id) {
        RoomDto dto = roomService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/active")
    public ResponseEntity<Page<RoomDto>> getAllActive(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(roomService.findAllActive(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDto> update(
            @PathVariable Long id,
            @RequestBody UpdateRoomDto dto) {
        RoomDto updated = roomService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/activation")
    public ResponseEntity<Void> changeActivation(
            @PathVariable Long id,
            @RequestParam boolean active) {
        roomService.changeActivation(id, active);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available")
    public ResponseEntity<Page<RoomDto>> findAvailableRooms(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {

        Page<RoomDto> page = roomService.findAvailableRooms(from, to, pageable);
        return ResponseEntity.ok(page);
    }

}
