package by.lykianova.seohelper.DTO;

import by.lykianova.seohelper.enums.ContentType;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class GenerateContentCreateDTO {

    private String contentType;

    private String platform;

    private String topic;
}
