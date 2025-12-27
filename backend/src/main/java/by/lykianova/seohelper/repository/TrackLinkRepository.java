package by.lykianova.seohelper.repository;

import by.lykianova.seohelper.DTO.TrackLinkDTO;
import by.lykianova.seohelper.entity.TrackedLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrackLinkRepository extends JpaRepository<TrackedLink,Long> {
    Optional<TrackedLink> findByShortCode(String shortCode);

    Optional<TrackedLink> findByOriginalUrl(String originalUrl);
}
