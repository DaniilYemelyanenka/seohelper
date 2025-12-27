package by.lykianova.seohelper.service;

import by.lykianova.seohelper.DTO.GenerateContentCreateDTO;
import by.lykianova.seohelper.DTO.GenerateContentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenerateContentService {

    @Autowired
    private TextGenerationService textGenerationService;

    public GenerateContentDTO generateContent(GenerateContentCreateDTO generateContentCreateDTO){

        System.out.println(generateContentCreateDTO.getPlatform());
        System.out.println(generateContentCreateDTO.getTopic());

        GenerateContentDTO contentDTO = textGenerationService.generateText(String.format("<system>\n" +
                        "You are a professional  copywriter and SEO specialist. Your task is to write clear, commercial texts . You must:\n" +
                        "1. Always respond **completely and to the end**.\n" +
                        "2. Strictly adhere to the structure requested by the user.\n" +
                        "3. Bring every thought to a logical conclusion.\n" +
                        "</system>\n" +
                        "\n" +
                        "<user>\n" +
                        "Generate text for %s "+
                        "**Topic:** %s\n" +
                        "**Tone of voice:** Expert, confident, but without complex jargon. Trustworthy.\n" +
                        "Generate the response **in its entirety**, do not cut it off. The text length should be about 100-150 words.\n" +
                        "</user>",generateContentCreateDTO.getPlatform(),generateContentCreateDTO.getTopic()),
                generateContentCreateDTO.getTopic(),generateContentCreateDTO.getPlatform());

        //TODO Generate content by using prompt to II
        //TODO save this generation to database
        return contentDTO;
    }

}
