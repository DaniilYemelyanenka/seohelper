package by.lykianova.seohelper.mapper.Impl;

import by.lykianova.seohelper.DTO.UserDTO;
import by.lykianova.seohelper.entity.User;
import by.lykianova.seohelper.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = BaseMapper.class)
public interface UpdateUserMapper {

    void updateUserFromDto(UserDTO userDTO, @MappingTarget User entity);

}
