package by.lykianova.seohelper.service;

import by.lykianova.seohelper.config.UserPrincipals;
import by.lykianova.seohelper.error.EmailNotFoundException;
import by.lykianova.seohelper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws EmailNotFoundException {
        return userRepository
                .findUserByEmail(email)
                .map(UserPrincipals::new)
                .orElseThrow(() -> new EmailNotFoundException(String.format("Пользоавтель с email: %s не найден",email)));
    }

}
