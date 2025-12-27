package by.lykianova.seohelper.repository;

import by.lykianova.seohelper.DTO.TrackLinkDTO;
import by.lykianova.seohelper.entity.TrackedLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrackLinkRepository extends JpaRepository<TrackedLink,Long> {
}
