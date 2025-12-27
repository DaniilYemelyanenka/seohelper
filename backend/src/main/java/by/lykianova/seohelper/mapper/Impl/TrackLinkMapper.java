package by.lykianova.seohelper.mapper.Impl;

import by.lykianova.seohelper.DTO.TrackLinkCreateDTO;
import by.lykianova.seohelper.DTO.TrackLinkDTO;
import by.lykianova.seohelper.entity.TrackedLink;
import by.lykianova.seohelper.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = BaseMapper.class)
public interface TrackLinkMapper {

    TrackLinkDTO fromCreateToDTO(TrackLinkCreateDTO trackLinkCreateDTO);


    TrackLinkDTO toDTO(TrackedLink trackedLink);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    TrackedLink toEntity(TrackLinkDTO trackLinkDTO);

    List<TrackLinkDTO> toDtos(Iterable<TrackedLink> trackedLinks);

    List<TrackedLink> toEntities(Iterable<TrackLinkDTO> trackLinkDTOS);

    TrackedLink merge(@MappingTarget TrackedLink TrackedLink, TrackLinkDTO trackLinkDTO);
}
