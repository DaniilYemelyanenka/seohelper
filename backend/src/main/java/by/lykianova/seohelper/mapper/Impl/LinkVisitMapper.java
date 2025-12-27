package by.lykianova.seohelper.mapper.Impl;

import by.lykianova.seohelper.DTO.LinkVisitDTO;
import by.lykianova.seohelper.entity.LinkVisits;
import by.lykianova.seohelper.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = BaseMapper.class)
public interface LinkVisitMapper {

    LinkVisitDTO toDTO(LinkVisits linkVisits);

    LinkVisits toEntity(LinkVisitDTO linkVisitDTO);

    List<LinkVisitDTO> toDtos(Iterable<LinkVisits> linkVisits);

    List<LinkVisits> toEntities(Iterable<LinkVisitDTO> linkVisitDTOS);

    LinkVisits merge(@MappingTarget LinkVisits linkVisits, LinkVisitDTO linkVisitDTO);
}
