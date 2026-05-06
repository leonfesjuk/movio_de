package de.upteams.tasktracker.cinema.persistence;

import de.upteams.tasktracker.cinema.entity.Cinema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, UUID> {

    @EntityGraph(attributePaths = "geoname")
    Page<Cinema> findByOrganizationId(UUID organizationId, Pageable pageable);

    List<Cinema> findByOrganizationId(UUID organizationId);

    boolean existsByOrganizationId(UUID organizationId);
}
