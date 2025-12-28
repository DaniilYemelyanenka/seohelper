package by.lykianova.seohelper.mapper.Impl;

import by.lykianova.seohelper.DTO.CreateUserDTO;
import by.lykianova.seohelper.entity.User;
import by.lykianova.seohelper.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapper.class)
public interface UserMapper {

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isActive", constant = "true")
    User createDtoToEntity(CreateUserDTO createUserDTO);

}
