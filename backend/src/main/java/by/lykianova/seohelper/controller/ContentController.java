package by.lykianova.seohelper.controller;

import by.lykianova.seohelper.DTO.GenerateContentCreateDTO;
import by.lykianova.seohelper.DTO.GenerateContentDTO;
import by.lykianova.seohelper.response.CustomApiResponse;
import by.lykianova.seohelper.service.GenerateContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/content")
public class ContentController {

    @Autowired
    private GenerateContentService generateContentService;

    @PostMapping("generate")
    public ResponseEntity<CustomApiResponse<GenerateContentDTO>> getGeneratedSiteDescriptionContent(@RequestBody GenerateContentCreateDTO generateContentCreateDTO){
        GenerateContentDTO generateContentDTO = generateContentService.generateContent(generateContentCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomApiResponse<>(true,generateContentDTO));
    }

    @GetMapping("history")
    public ResponseEntity<String> getGeneratedContent(){
        //TODO here must be getting history
        return ResponseEntity.status(HttpStatus.CREATED).body("history");
    }

}
