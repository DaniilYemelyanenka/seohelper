package by.lykianova.seohelper.entity;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;

public class LoginAttempts {

    private Long id;

    private String ipAddress;

    private String email;

    private Integer attempts;

    private LocalDateTime lastAttempt;

    private LocalDateTime BlockedUntil;
}
