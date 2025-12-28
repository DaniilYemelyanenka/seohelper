package by.lykianova.seohelper.entity;


import by.lykianova.seohelper.enums.ContentType;
import by.lykianova.seohelper.enums.Platform;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "generated_content")
@Data
public class GeneratedContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;


    @Enumerated(value = EnumType.STRING)
    @Column(name = "content_type")
    private ContentType contentType;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "platform")
    private Platform platform;

    private String topic;

    @Column(length = 1000)
    private String content;
}
