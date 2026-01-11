package by.lykianova.seohelper.service;

import by.lykianova.seohelper.DTO.CreateUserDTO;
import by.lykianova.seohelper.DTO.ShowUserDTO;
import by.lykianova.seohelper.DTO.UserDTO;
import by.lykianova.seohelper.entity.User;
import by.lykianova.seohelper.enums.Role;
import by.lykianova.seohelper.error.AuthenticationException;
import by.lykianova.seohelper.error.EmailNotFoundException;
import by.lykianova.seohelper.error.UserExistsException;
import by.lykianova.seohelper.error.UserNotFoundException;
import by.lykianova.seohelper.mapper.Impl.CreateUserMapper;
import by.lykianova.seohelper.mapper.Impl.ShowUserMapper;
import by.lykianova.seohelper.mapper.Impl.UpdateUserMapper;
import by.lykianova.seohelper.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;

    private CreateUserMapper userMapper;

    private JwtService jwtService;

    private AuthenticationManager authenticationManager;

    private ShowUserMapper showUserMapper;

    private UpdateUserMapper updateUserMapper;

    private BCryptPasswordEncoder  encoder = new BCryptPasswordEncoder();

    public UserService(CreateUserMapper userMapper, UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager,ShowUserMapper showUserMapper,UpdateUserMapper updateUserMapper) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.showUserMapper = showUserMapper;
        this.updateUserMapper = updateUserMapper;
    }

    @Transactional
    public String addUser(CreateUserDTO createUserDTO){

        validateUserByExists(createUserDTO.getEmail());
        User user = userMapper.createDtoToEntity(createUserDTO);
        user.setPasswordHash(encoder.encode(user.getPasswordHash()));
        user.setRoles(Set.of(Role.ROLE_ADMIN,Role.ROLE_USER));
        userRepository.save(user);
        return authenticate(createUserDTO.getEmail(), createUserDTO.getPasswordHash());
    }

    @Transactional
    public String login(UserDTO userDTO){
        getUserByEmail(userDTO.getEmail());
        return authenticate(userDTO.getEmail(),userDTO.getPassword());
    }


    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public User getUserByEmail(String email){
        return  userRepository.findUserByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(
                        String.format("Ошибка авторизации пользователя с email: %s",email)
                ));
    }
    public Long getUserIdByEmail(String email){
        User user = getUserByEmail(email);
        return user.getId();
    }

    public ShowUserDTO getUserInfo(String email){
        User user = getUserByEmail(email);
        return showUserMapper.toDto(user);
    }

    @Transactional
    public void updateUserInformation(UserDTO updateUserDTO, String email){
        User user = getUserByEmail(email);
        updateUserMapper.updateUserFromDto(updateUserDTO,user);
    }

    @Transactional
    public void deleteUser(String email){
        User user = getUserByEmail(email);
        userRepository.delete(user);
    }

    private void validateUserByExists(String email){
        if(userRepository.existsByEmail(email)){
            throw new UserExistsException("Пользователь с таким именем уже существует");
        }
    }

    private String authenticate(String username,String password){
        String token = null;

        Authentication auth = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(username,password)
                );

        if(auth.isAuthenticated()){
            token = jwtService.generateToken(username);
        }else{
            throw new AuthenticationException("Что-то поло не так, попробуйте позже ");
        }

        return token;
    }
}
