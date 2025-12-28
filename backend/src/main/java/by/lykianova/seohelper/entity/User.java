package by.lykianova.seohelper.entity;

import by.lykianova.seohelper.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String passwordHash;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles",
                    joinColumns = @JoinColumn(name = "user_id"))
    private Set<Role> roles = new HashSet<>();

    private Boolean isActive;

    private LocalDateTime createdAt;
}
