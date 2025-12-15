package by.lykianova.seohelper.service;

import by.lykianova.seohelper.DTO.SeoReportDTO;
import by.lykianova.seohelper.error.SiteException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class SeoParserService {

    @Autowired
    private PageSpeedService pageSpeedService;

    public SeoReportDTO parse(String url) {

        if(!isSiteValid(url)){
           throw new SiteException("Something aws wrong at connection to this url... Please check URL!");
        }

        SeoReportDTO seoReportDTO = new SeoReportDTO();
        seoReportDTO.setUrl(url);
        seoReportDTO.setCreatedAt(LocalDateTime.now());

        try {
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10_000)
                    .get();


            String metaDescription = document.select("meta[name = description]")
                                             .attr("content");

            String h1 = document.select("h1").text();

            Boolean hasSsl = url.startsWith("https");

            seoReportDTO.setMetaDescription(metaDescription.isEmpty()? null : metaDescription);
            seoReportDTO.setH1(h1.isEmpty()? null : h1);
            seoReportDTO.setHasSs1(hasSsl);

            CompletableFuture<Integer> mobile = CompletableFuture.supplyAsync(() ->  pageSpeedService.getMobileScore(url));
            CompletableFuture<Integer> speed = CompletableFuture.supplyAsync(() -> pageSpeedService.getSpeedScore(url));

            CompletableFuture.allOf(mobile,speed).join();

            seoReportDTO.setMobileScore(mobile.get());
            seoReportDTO.setSpeedScore(speed.get());

        } catch (IOException | InterruptedException | ExecutionException ioException) {
            System.out.println(ioException.getMessage());
        }


        return seoReportDTO;
    }

    private boolean isSiteValid(String url) {
        try{
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestMethod("HEAD");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);

            int responseCode = httpURLConnection.getResponseCode();
            return responseCode < 400;

        }catch(IOException exception){
            return false;
        }
    }
}
