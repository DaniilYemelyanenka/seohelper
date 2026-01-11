package by.lykianova.seohelper.repository;

import by.lykianova.seohelper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserByEmail(String email);

    boolean existsByEmail(String email);
}
