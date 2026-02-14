package by.lykianova.seohelper.response;

import lombok.Data;

@Data
public class SeoAnalyseResult {
    private Long id;
    private String url;
    private String recommendations;
    private int Score;
}
