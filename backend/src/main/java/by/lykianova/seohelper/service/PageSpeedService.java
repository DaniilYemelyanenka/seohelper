package by.lykianova.seohelper.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;


@Service
public class PageSpeedService {

    private static final String PAGE_API_URL  = "https://www.googleapis.com/pagespeedonline/v5/runPagespeed";

    @Value("${google.pagespeed.api.key}")
    private String API_KEY;

    private final RestTemplate restTemplate = new RestTemplate();

    public int getSpeedScore(String url){

        Map lighthouse = (Map) getLighthouseResult(url).get("categories");

        Map performance = (Map) lighthouse.get("performance");

        Double score = (Double) performance.get("score") * 100;

        return score.intValue();
    }

    public int getMobileScore(String url){

        int score = 0;

        int max = 3;

        Map lighthouse = getLighthouseResult(url);

        Map audits = (Map) lighthouse.get("audits");

        score += isAuditPassed(audits,"viewport-insight") ? 1:0;
        score += isAuditPassed(audits,"cumulative-layout-shift") ? 1:0;
        score += isAuditPassed(audits,"tap-targets") ? 1:0;

        System.out.println("mpbile-score " + ((score / (double) max) * 100));

        return (int) ((score / (double) max) * 100);
    }

    private Map getLighthouseResult(String url){

        String requestUrl = UriComponentsBuilder.fromUriString(PAGE_API_URL)
                .queryParam("url",url)
                .queryParam("strategy","mobile")
                .queryParam("key",API_KEY)
                .queryParam(
                "fields",
                "lighthouseResult(categories/performance/score,audits)"
                )
                .toUriString();

        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(requestUrl,Map.class);

        Map lighthouse = (Map) responseEntity.getBody().get("lighthouseResult");

        return lighthouse;
    }

    private boolean isAuditPassed(Map audits,String key){
        if (audits == null || !audits.containsKey(key)) return false;

        Map audit = (Map) audits.get(key);
        if (audit == null) return false;

        Object scoreObj = audit.get("score");
        if (scoreObj == null) return false;

        if (scoreObj instanceof Number) {
            double score = ((Number) scoreObj).doubleValue();
            return score >= 1.0;
        }

        return false;
    }
}
