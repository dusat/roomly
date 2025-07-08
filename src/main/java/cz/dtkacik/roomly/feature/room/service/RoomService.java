package cz.dtkacik.roomly.feature.room.service;

import cz.dtkacik.roomly.feature.room.dto.CreateRoomDto;
import cz.dtkacik.roomly.feature.room.dto.RoomDto;
import cz.dtkacik.roomly.feature.room.dto.UpdateRoomDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface RoomService {
    RoomDto create(CreateRoomDto dto);
    RoomDto findById(Long id);
    Page<RoomDto> findAllActive(Pageable pageable);
    RoomDto update(Long id, UpdateRoomDto dto);
    void changeActivation(Long id, Boolean active);
    void delete(Long id);
    Page<RoomDto> findAvailableRooms(LocalDateTime from, LocalDateTime to, Pageable pageable);
}
