package de.upteams.tasktracker.geonames.utils;

import de.upteams.tasktracker.geonames.dto.GeonameAlternateNameDto;
import de.upteams.tasktracker.geonames.dto.GeonameDetailsDto;
import de.upteams.tasktracker.geonames.dto.GeonameResponseDto;
import de.upteams.tasktracker.geonames.dto.GeonameSearchListResponseDto;
import de.upteams.tasktracker.geonames.entity.GeonameAlternateNameEntity;
import de.upteams.tasktracker.geonames.entity.GeonameEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface GeonameMapper {

    @Mapping(source = "geonameId", target = "geonameId")
    GeonameResponseDto toGeonameResponseDto(GeonameEntity geoname);

    @Mapping(source = "geonameId", target = "geonameId")
    GeonameDetailsDto toGeonameDetailsDto(GeonameEntity geoname);

    // Mapping for alternate names
    // Map from DB column 'isolanguage' to DTO field 'code'
    @Mapping(source = "isoLanguage", target = "code")
    GeonameAlternateNameDto toGeonameAlternateNameDto(GeonameAlternateNameEntity alternateName);

    // Helper method to map a list of entities to a list of DTOs
    List<GeonameResponseDto> toGeonameResponseDtoList(List<GeonameEntity> geonames);
    List<GeonameDetailsDto> toGeonameDetailsDtoList(List<GeonameEntity> geonames);
    List<GeonameAlternateNameDto> toGeonameAlternateNameDtoList(List<GeonameAlternateNameEntity> alternateNames);

    // Метод для створення обгортки GeonameSearchListResponseDto
    default GeonameSearchListResponseDto toGeonameSearchListResponseDto(List<GeonameResponseDto> items) {
        return GeonameSearchListResponseDto.builder()
                .items(items)
                .build();
    }
}
