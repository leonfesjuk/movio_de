package de.upteams.tasktracker.event.persistence;

import de.upteams.tasktracker.event.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    @Query("SELECT e FROM Event e WHERE e.datetime >= CURRENT_TIMESTAMP ORDER BY e.datetime ASC")
    Page<Event> findUpcomingEvents(Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.datetime >= CURRENT_TIMESTAMP AND e.cinema.id = :cinemaId ORDER BY e.datetime ASC")
    Page<Event> findUpcomingEventsByCinema(@Param("cinemaId") UUID cinemaId, Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.datetime >= CURRENT_TIMESTAMP AND e.cinema.organizationId = :orgId ORDER BY e.datetime ASC")
    Page<Event> findByOrganizationIdWithDateCheck(@Param("orgId") UUID organizationId, Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.datetime >= CURRENT_TIMESTAMP AND e.cinema.geonameId = :cityGeonameId ORDER BY e.datetime ASC")
    Page<Event> findUpcomingEventsByCity(@Param("cityGeonameId") Long cityGeonameId, Pageable pageable);

    List<Event> findByCinemaId(UUID cinemaId);

    boolean existsByCinemaId(UUID cinemaId);

    boolean existsByCinemaIdAndDatetimeAfter(UUID cinemaId, LocalDateTime dateTime);
}
