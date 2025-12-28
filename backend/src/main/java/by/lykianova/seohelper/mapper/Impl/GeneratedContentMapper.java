package by.lykianova.seohelper.mapper.Impl;

import by.lykianova.seohelper.DTO.GenerateContentDTO;
import by.lykianova.seohelper.entity.GeneratedContent;
import by.lykianova.seohelper.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = BaseMapper.class)
public interface GeneratedContentMapper {

    @Mapping(target = "createdAt",expression = "java(java.time.LocalDateTime.now())")
    GeneratedContent toEntity(GenerateContentDTO generateContentDTO);

    @Mapping(target = "userId",source = "user.id")
    GenerateContentDTO toDto(GeneratedContent generatedContent);

    @Mapping(target = "userId",source = "user.id")
    List<GenerateContentDTO> toDtos(Iterable<GeneratedContent> generatedContents);
}
