package cz.dtkacik.roomly.feature.reservation.service;

import cz.dtkacik.roomly.feature.reservation.domain.Reservation;
import cz.dtkacik.roomly.feature.reservation.dto.CreateReservationDto;
import cz.dtkacik.roomly.feature.reservation.dto.ReservationDto;
import cz.dtkacik.roomly.feature.reservation.dto.UpdateReservationDto;
import cz.dtkacik.roomly.feature.reservation.exception.ReservationConflictException;
import cz.dtkacik.roomly.feature.reservation.exception.ReservationNotFoundException;
import cz.dtkacik.roomly.feature.reservation.mapper.ReservationMapper;
import cz.dtkacik.roomly.feature.reservation.repository.ReservationRepository;
import cz.dtkacik.roomly.feature.room.domain.Room;
import cz.dtkacik.roomly.feature.room.exception.RoomNotFoundException;
import cz.dtkacik.roomly.feature.room.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final RoomRepository roomRepository;

    public ReservationServiceImpl(
            ReservationRepository reservationRepository,
            ReservationMapper reservationMapper,
            RoomRepository roomRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.roomRepository = roomRepository;
    }

    @Override
    @Transactional
    public ReservationDto create(CreateReservationDto dto) {
        checkNoOverlap(dto.roomId(), dto.dateFrom(), dto.dateTo(), null);

        Room room = roomRepository.findById(dto.roomId())
                .orElseThrow(() -> new RoomNotFoundException(dto.roomId()));

        Reservation reservation = reservationMapper.toEntity(dto, room);
        Reservation saved       = reservationRepository.save(reservation);

        return reservationMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationDto findById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        return reservationMapper.toDto(reservation);
    }

    @Override
    @Transactional
    public ReservationDto update(Long id, UpdateReservationDto dto) {

        // 1. Načteme existující rezervaci
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        // 2. Zjistíme finální hodnoty, které budou po updatu platit
        LocalDateTime newDateFrom = dto.dateFrom() != null
                ? dto.dateFrom()
                : reservation.getDateFrom();

        LocalDateTime newDateTo = dto.dateTo() != null
                ? dto.dateTo()
                : reservation.getDateTo();

        // 3. Určíme cílovou místnost (může zůstat původní)
        Room targetRoom;
        if (dto.roomId() != null && !dto.roomId().equals(reservation.getRoom().getId())) {
            targetRoom = roomRepository.findById(dto.roomId())
                    .orElseThrow(() -> new RoomNotFoundException(dto.roomId()));
        } else {
            targetRoom = reservation.getRoom();
        }

        // 4. Ověříme, že nový stav nekoliduje s jinými rezervacemi
        checkNoOverlap(targetRoom.getId(), newDateFrom, newDateTo, id);

        // 5. Až poté mutujeme entitu — JPA dirty-checking vše uloží
        reservation.setDateFrom(newDateFrom);
        reservation.setDateTo(newDateTo);
        reservation.setRoom(targetRoom);

        return reservationMapper.toDto(reservation);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        reservationRepository.delete(reservation);
    }

    private void checkNoOverlap(Long roomId,
            LocalDateTime from,
            LocalDateTime to,
            Long selfId) {
        boolean conflict = reservationRepository
                .existsOverlappingReservation(roomId, from, to, selfId);

        if (conflict) {
            throw new ReservationConflictException(roomId, from, to);
        }
    }

}


