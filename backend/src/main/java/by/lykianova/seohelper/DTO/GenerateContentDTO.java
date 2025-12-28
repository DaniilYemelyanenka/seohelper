package by.lykianova.seohelper.DTO;

import by.lykianova.seohelper.enums.ContentType;
import by.lykianova.seohelper.enums.Platform;
import lombok.Data;

@Data
public class GenerateContentDTO {

    private ContentType contentType;

    private Platform platform;

    private String topic;

    private String content;

    private Long userId;
}
