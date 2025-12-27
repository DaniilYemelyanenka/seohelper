package by.lykianova.seohelper.DTO;

import by.lykianova.seohelper.enums.ContentType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class GenerateContentDTO {

    private ContentType contentType;

    private String platform;

    private String topic;

    private String content;

    private LocalDateTime createdAt;
}
