package by.lykianova.seohelper.response;

import lombok.Data;

@Data
public class SeoAnalyseResult {
    private String url;
    private String recommendations;
    private int Score;
}
