package de.upteams.tasktracker.event.utils;

import de.upteams.tasktracker.event.dto.request.EventUpdateDto;
import de.upteams.tasktracker.event.dto.response.EventResponseDto;
import de.upteams.tasktracker.event.entity.TimeFlag;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TimeFlagMappingService {

    TimeFlag mapUpdateDtoToEntity(EventUpdateDto.TimeFlagDto dto, java.util.UUID eventId);

    EventResponseDto.TimeFlagDto mapEntityToDto(TimeFlag entity);
}
