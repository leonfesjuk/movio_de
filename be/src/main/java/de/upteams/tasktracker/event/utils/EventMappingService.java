package de.upteams.tasktracker.event.utils;

import de.upteams.tasktracker.cinema.utils.CinemaMappingService;
import de.upteams.tasktracker.event.dto.request.EventCreateDto;
import de.upteams.tasktracker.event.dto.request.EventUpdateDto;
import de.upteams.tasktracker.event.dto.response.EventResponseDto;
import de.upteams.tasktracker.event.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CinemaMappingService.class, TimeFlagMappingService.class}
)
public interface EventMappingService {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cinema", ignore = true)
    Event mapDtoToEntity(EventCreateDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cinema", ignore = true)
    Event mapDtoToEntity(EventUpdateDto dto);

    @Mapping(target = "cinema", ignore = true)
    @Mapping(target = "timeFlags", ignore = true)
    EventResponseDto mapEntityToDto(Event entity);
}
