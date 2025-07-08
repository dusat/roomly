package cz.dtkacik.roomly.feature.room.service;

import cz.dtkacik.roomly.feature.reservation.domain.Reservation;
import cz.dtkacik.roomly.feature.reservation.repository.ReservationRepository;
import cz.dtkacik.roomly.feature.room.domain.Location;
import cz.dtkacik.roomly.feature.room.domain.Room;
import cz.dtkacik.roomly.feature.room.dto.CreateRoomDto;
import cz.dtkacik.roomly.feature.room.dto.RoomDto;
import cz.dtkacik.roomly.feature.room.dto.UpdateRoomDto;
import cz.dtkacik.roomly.feature.room.exception.LocationNotFoundException;
import cz.dtkacik.roomly.feature.room.exception.RoomNotFoundException;
import cz.dtkacik.roomly.feature.room.mapper.RoomMapper;
import cz.dtkacik.roomly.feature.room.repository.LocationRepository;
import cz.dtkacik.roomly.feature.room.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final LocationRepository locationRepository;
    private final ReservationRepository reservationRepository;
    private final RoomMapper roomMapper;

    public RoomServiceImpl(RoomRepository roomRepository,
            RoomMapper roomMapper,
            LocationRepository locationRepository,
            ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
        this.locationRepository = locationRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    @Transactional
    public RoomDto create(CreateRoomDto dto) {
        Room room = roomMapper.toEntity(dto);
        Room saved = roomRepository.save(room);
        return roomMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public RoomDto findById(Long id) {
        Room room = roomRepository.findById(id)
                .filter(Room::getActive)
                .orElseThrow(() -> new RoomNotFoundException(id));
        return roomMapper.toDto(room);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoomDto> findAllActive(Pageable pageable) {

        Page<Long> idPage = roomRepository.findActiveRoomIds(pageable);

        List<Room> rooms = idPage.isEmpty()
                ? List.of()
                : roomRepository.findAllWithDetailsByIdIn(idPage.getContent());

        Map<Long, Room> byId = rooms.stream()
                .collect(Collectors.toMap(Room::getId, Function.identity()));

        List<RoomDto> content = idPage.getContent()
                .stream()
                .map(byId::get)
                .map(roomMapper::toDto)
                .toList();

        return new PageImpl<>(content, pageable, idPage.getTotalElements());
    }

    @Override
    @Transactional
    public RoomDto update(Long id, UpdateRoomDto dto) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));

        applyUpdateDto(room, dto);
        roomRepository.save(room);
        return roomMapper.toDto(room);
    }

    @Override
    @Transactional
    public void changeActivation(Long id, Boolean active) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
        room.setActive(active);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));

        roomRepository.delete(room);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoomDto> findAvailableRooms(LocalDateTime from, LocalDateTime to, Pageable pageable) {

        Page<Long> idPage = roomRepository.findAvailableRoomIds(from, to, pageable);

        List<Room> rooms = idPage.isEmpty()
                ? List.of()
                : roomRepository.findAllByIdIn(idPage.getContent());

        Map<Long, Room> byId = rooms.stream()
                .collect(Collectors.toMap(Room::getId, Function.identity()));

        List<RoomDto> content = idPage.getContent().stream()
                .map(byId::get)
                .map(roomMapper::toDto)
                .toList();

        return new PageImpl<>(content, pageable, idPage.getTotalElements());
    }

    private void applyUpdateDto(Room room, UpdateRoomDto dto) {

        if (dto.location() != null && dto.location().id() != null) {
            Location loc = locationRepository.findById(dto.location().id())
                    .orElseThrow(() -> new LocationNotFoundException(dto.location().id()));
            room.setLocation(loc);
        }

        if (dto.personCapacity() != null) {
            room.setPersonCapacity(dto.personCapacity());
        }

        if (dto.equipment() != null) {
            room.getEquipment().clear();
            room.getEquipment().addAll(dto.equipment());
        }
    }

}
