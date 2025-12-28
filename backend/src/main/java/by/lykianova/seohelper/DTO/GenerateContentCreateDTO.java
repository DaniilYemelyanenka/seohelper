package by.lykianova.seohelper.DTO;

import by.lykianova.seohelper.enums.ContentType;
import by.lykianova.seohelper.enums.Platform;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class GenerateContentCreateDTO {

    @NotNull(message = "content type cant be null")
    private ContentType contentType;

    @NotNull(message = "platform cant be null")
    private Platform platform;

    @NotBlank(message = "topic is required")
    @Size(min = 3, max = 255, message = "topic must be between 3 and 255 characters")
    private String topic;
}
