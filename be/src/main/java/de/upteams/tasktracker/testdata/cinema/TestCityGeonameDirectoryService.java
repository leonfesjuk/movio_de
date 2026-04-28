package de.upteams.tasktracker.testdata.cinema;

import de.upteams.tasktracker.geonames.entity.GeonameEntity;
import de.upteams.tasktracker.geonames.persistence.GeonameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestCityGeonameDirectoryService {

    private final GeonameRepository geonameRepository;

    public List<Long> getCityGeonameIds(int poolLimit) {
        return geonameRepository.searchAll("", PageRequest.of(0, poolLimit)).stream()
                .filter(g -> Boolean.TRUE.equals(g.getIsActive()))
                .map(GeonameEntity::getId)
                .collect(Collectors.toList());
    }
}
