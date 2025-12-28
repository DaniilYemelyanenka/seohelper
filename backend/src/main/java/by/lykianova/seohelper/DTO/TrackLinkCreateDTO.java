package by.lykianova.seohelper.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TrackLinkCreateDTO {

    @NotBlank(message = "URL can't be blank")
    @Size(max = 2048,message = "URL is to long")
    @Pattern(
            regexp = "^(https?://).+",
            message = "URL is uncorrected"
    )
    private String originalUrl;
}
