package by.lykianova.seohelper.entity;

import lombok.Data;

import java.util.Map;

@Data
public class VisitLinksAnalyze {

    private Long visitsCount;

    private Map<String,Integer> country;

    private Map<String,Integer> browsers;

    private Map<String,Integer> devises;
}
