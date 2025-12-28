package by.lykianova.seohelper.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "seo_reports")
@Data
public class SeoReports {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(length = 2048)
    private String url;

    @Column(length = 1000)
    private String metaDescription;

    @Column(length = 500)
    private String h1;

    private Boolean hasSs1;

    private Integer speedScore;

    private Integer mobileScore;

    private LocalDateTime createdAt;
}
