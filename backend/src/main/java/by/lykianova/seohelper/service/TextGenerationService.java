package by.lykianova.seohelper.service;

import by.lykianova.seohelper.DTO.GenerateContentDTO;
import by.lykianova.seohelper.enums.ContentType;
import by.lykianova.seohelper.enums.Platform;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Service
public class TextGenerationService {

    @Value("${huggingface.api.key}")
    private String API_KEY;

    @Value("${huggingface.api.url}")
    private String API_URL;


    private final RestTemplate restTemplate = new RestTemplate();

    public GenerateContentDTO generateText(String prompt, String topic, Platform platform){

        System.out.println(prompt);

        Map<String,Object> payloads = Map.of(
                "model", "llama3.2:1b",
                "prompt", prompt,
                "stream", false
        );


        ResponseEntity<Map> response = restTemplate.postForEntity(API_URL,payloads,Map.class);

        String text = (String) response.getBody().get("response");

        GenerateContentDTO contentDTO = new GenerateContentDTO();

        contentDTO.setContent(text);
        contentDTO.setTopic(topic);
        contentDTO.setPlatform(platform);
        contentDTO.setContentType(ContentType.POST);

        return contentDTO;
    }
}
