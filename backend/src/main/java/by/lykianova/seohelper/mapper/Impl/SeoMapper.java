package by.lykianova.seohelper.mapper.Impl;

import by.lykianova.seohelper.DTO.SeoReportDTO;
import by.lykianova.seohelper.entity.SeoReports;
import by.lykianova.seohelper.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = BaseMapper.class)
public interface SeoMapper  {

    @Mapping(target = "userId",source = "user.id")
    SeoReportDTO toDTO(SeoReports seoReports);

    SeoReports toEntity(SeoReportDTO seoReportDTO);

    List<SeoReportDTO> toDtos(Iterable<SeoReports> seoReports);

    List<SeoReports> toEntities(Iterable<SeoReportDTO> seoReportDTOS);

    SeoReports merge(@MappingTarget SeoReports seoReports, SeoReportDTO seoReportDTO);

}
