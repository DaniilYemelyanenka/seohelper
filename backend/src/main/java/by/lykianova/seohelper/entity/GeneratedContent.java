package by.lykianova.seohelper.entity;


import by.lykianova.seohelper.enums.ContentType;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "generated_content")
public class GeneratedContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private User user;


    @Enumerated(value = EnumType.STRING)
    @Column(name = "content_type")
    private ContentType contentType;

    private String platform;

    private String topic;

    private String content;
}
