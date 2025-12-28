package by.lykianova.seohelper.controller;

import by.lykianova.seohelper.DTO.GenerateContentCreateDTO;
import by.lykianova.seohelper.DTO.GenerateContentDTO;
import by.lykianova.seohelper.entity.User;
import by.lykianova.seohelper.response.CustomApiResponse;
import by.lykianova.seohelper.service.GenerateContentService;
import by.lykianova.seohelper.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/content")
@Validated
public class ContentController {

    @Autowired
    private GenerateContentService generateContentService;


    @Autowired
    private UserService userService;

    @PostMapping("generate")
    public ResponseEntity<CustomApiResponse<GenerateContentDTO>> getGeneratedSiteDescriptionContent(@Valid @RequestBody GenerateContentCreateDTO generateContentCreateDTO){
        User user = userService.getUserById(1L);
        GenerateContentDTO generateContentDTO = generateContentService.generateContent(generateContentCreateDTO,user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomApiResponse<>(true,generateContentDTO));
    }

    @GetMapping("history")
    public ResponseEntity<List<GenerateContentDTO>> getGeneratedContent(){
        List<GenerateContentDTO> generateContentDTOList = generateContentService.getHistory(1L);
        return ResponseEntity.status(HttpStatus.OK).body(generateContentDTOList);
    }

}
