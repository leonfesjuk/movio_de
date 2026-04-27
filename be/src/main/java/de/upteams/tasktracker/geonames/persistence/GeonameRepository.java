package de.upteams.tasktracker.geonames.persistence;

import de.upteams.tasktracker.geonames.entity.GeonameEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeonameRepository extends JpaRepository<GeonameEntity, Long> {

    @Query("SELECT g FROM GeonameEntity g " +
           "WHERE g.isActive = true " +
           "AND (LOWER(g.name) LIKE LOWER(CONCAT(:q, '%')) " +
           "OR LOWER(g.asciiName) LIKE LOWER(CONCAT(:q, '%'))) " +
           "ORDER BY g.population DESC")
    List<GeonameEntity> searchAll(@Param("q") String query, Pageable pageable);

    @Query("SELECT DISTINCT g FROM GeonameEntity g " +
           "JOIN Cinema c ON c.geonameId = g.id " +
           "WHERE g.isActive = true " +
           "AND (LOWER(g.name) LIKE LOWER(CONCAT(:q, '%')) " +
           "OR LOWER(g.asciiName) LIKE LOWER(CONCAT(:q, '%'))) " +
           "ORDER BY g.population DESC")
    List<GeonameEntity> searchWithCinemas(@Param("q") String query, Pageable pageable);
}
