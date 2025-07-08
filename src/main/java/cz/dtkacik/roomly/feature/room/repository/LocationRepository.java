package cz.dtkacik.roomly.feature.room.repository;

import cz.dtkacik.roomly.feature.room.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
