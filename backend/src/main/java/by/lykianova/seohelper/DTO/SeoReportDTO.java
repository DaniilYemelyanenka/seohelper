package by.lykianova.seohelper.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SeoReportDTO {

    private Long id;

    private String url;

    private String metaDescription;

    private String h1;

    private Boolean hasSs1;

    private Integer speedScore;

    private Integer mobileScore;

    private LocalDateTime createdAt;

    private Long userId;
}
