package by.lykianova.seohelper.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "link_visits")
public class LinkVisits{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tracked_link_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TrackedLink trackedLink;

    private String ip_address;

    private String country;

    private String device;

    private String browser;

    private LocalDateTime visitedAt;
}
