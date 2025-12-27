package by.lykianova.seohelper.DTO;

import by.lykianova.seohelper.entity.TrackedLink;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LinkVisitDTO {

    private TrackedLink trackedLink;

    private String ipAddress;

    private String country;

    private String device;

    private String browser;

    private LocalDateTime visitedAt;
}
