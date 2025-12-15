package by.lykianova.seohelper.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/content")
public class ContentController {

    @PostMapping("site-description")
    public ResponseEntity<String> getGeneratedSiteDescriptionContent(){
        //TODO here must be generation of site description
        return ResponseEntity.status(HttpStatus.CREATED).body("site description");
    }

    @PostMapping("social-post")
    public ResponseEntity<String> getGeneratedSocialPostContent(){
        //TODO here must be generation of social-post content
        return ResponseEntity.status(HttpStatus.CREATED).body("social-post");
    }

    @GetMapping("history")
    public ResponseEntity<String> getGeneratedContent(){
        //TODO here must be getting history
        return ResponseEntity.status(HttpStatus.CREATED).body("history");
    }

}
