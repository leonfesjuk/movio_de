package de.upteams.tasktracker.event.persistence;

import de.upteams.tasktracker.event.entity.TimeFlag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TimeFlagRepository extends JpaRepository<TimeFlag, UUID> {

    Optional<TimeFlag> findByEventId(UUID eventId);
}
