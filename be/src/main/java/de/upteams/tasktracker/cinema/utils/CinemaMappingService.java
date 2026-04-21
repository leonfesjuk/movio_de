package de.upteams.tasktracker.cinema.utils;

import de.upteams.tasktracker.cinema.dto.response.CinemaResponseDto;
import de.upteams.tasktracker.cinema.entity.Cinema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CinemaMappingService {

    @Mapping(target = "cityName", ignore = true)
    CinemaResponseDto mapEntityToDto(Cinema entity);
}
