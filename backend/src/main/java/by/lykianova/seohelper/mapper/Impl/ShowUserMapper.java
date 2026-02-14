package by.lykianova.seohelper.mapper.Impl;

import by.lykianova.seohelper.DTO.ShowUserDTO;
import by.lykianova.seohelper.entity.User;
import by.lykianova.seohelper.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapper.class)
public interface ShowUserMapper {

     ShowUserDTO toDto(User user);
}
