package by.lykianova.seohelper.service;

import by.lykianova.seohelper.DTO.CreateUserDTO;
import by.lykianova.seohelper.entity.User;
import by.lykianova.seohelper.enums.Role;
import by.lykianova.seohelper.error.UserNotFoundException;
import by.lykianova.seohelper.mapper.Impl.UserMapper;
import by.lykianova.seohelper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public void addUser(CreateUserDTO createUserDTO){
        User user = userMapper.createDtoToEntity(createUserDTO);
        user.setRoles(Set.of(Role.ROLE_ADMIN,Role.ROLE_USER));
        userRepository.save(user);
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
