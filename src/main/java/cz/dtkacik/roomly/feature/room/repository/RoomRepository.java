package cz.dtkacik.roomly.feature.room.repository;

import cz.dtkacik.roomly.feature.room.domain.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("select r.id from Room r where r.active = true")
    Page<Long> findActiveRoomIds(Pageable pageable);

    @Query("""
           select distinct r
           from Room r
           left join fetch r.location l
           left join fetch r.equipment e
           left join fetch r.reservations res
           where r.id in :ids
           """)
    List<Room> findAllWithDetailsByIdIn(@Param("ids") List<Long> ids);

    @Query("""
    select r.id
    from Room r
    where r.active = true
      and r.id not in (
        select res.room.id
        from Reservation res
        where res.dateFrom < :to and res.dateTo > :from
      )
    """)
    Page<Long> findAvailableRoomIds(@Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            Pageable pageable);

    @Query("""
    select distinct r
    from Room r
    left join fetch r.location
    left join fetch r.reservations
    where r.id in :ids
""")
    List<Room> findAllByIdIn(@Param("ids") List<Long> ids);

}
