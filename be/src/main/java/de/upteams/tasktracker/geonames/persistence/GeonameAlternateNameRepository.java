package de.upteams.tasktracker.geonames.persistence;

import de.upteams.tasktracker.geonames.entity.GeonameAlternateNameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeonameAlternateNameRepository extends JpaRepository<GeonameAlternateNameEntity, Long> {

    List<GeonameAlternateNameEntity> findByGeonameId(Long geonameId);
}