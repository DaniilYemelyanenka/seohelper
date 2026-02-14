package by.lykianova.seohelper.service;

import by.lykianova.seohelper.DTO.GenerateContentCreateDTO;
import by.lykianova.seohelper.DTO.GenerateContentDTO;
import by.lykianova.seohelper.entity.GeneratedContent;
import by.lykianova.seohelper.entity.User;
import by.lykianova.seohelper.enums.Platform;
import by.lykianova.seohelper.mapper.Impl.GeneratedContentMapper;
import by.lykianova.seohelper.repository.GenerationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenerateContentService {

    @Autowired
    private TextGenerationService textGenerationService;

    @Autowired
    private GenerationRepository generationRepository;

    @Autowired
    private GeneratedContentMapper generatedContentMapper;

    public GenerateContentDTO generateContent(GenerateContentCreateDTO generateContentCreateDTO, User user){

        //TODO add logik for translating russin to english before request LLM 

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
                        "Generate the response **in its entirety**, do not cut it off. The text length should be about 50-100 words.\n" +
                        "</user>",generateContentCreateDTO.getPlatform(),generateContentCreateDTO.getTopic()),
                generateContentCreateDTO.getTopic(), generateContentCreateDTO.getPlatform());

        GeneratedContent generatedContent = generatedContentMapper.toEntity(contentDTO);

        generatedContent.setUser(user);

        return generatedContentMapper.toDto(generationRepository.save(generatedContent));
    }

    public List<GenerateContentDTO> getHistory(Long userId){
        return generatedContentMapper.toDtos(generationRepository.findAllByUserId(userId));
    }

    private String getPlatform(Platform platform){
        if(platform.equals(Platform.TELEGRAM)) return "telegram";
        if(platform.equals(Platform.SITE)) return "site";
        return "";
    }

}
