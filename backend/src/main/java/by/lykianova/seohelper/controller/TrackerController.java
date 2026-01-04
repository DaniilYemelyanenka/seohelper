package by.lykianova.seohelper.controller;

import by.lykianova.seohelper.DTO.TrackLinkCreateDTO;
import by.lykianova.seohelper.entity.VisitLinksAnalyze;
import by.lykianova.seohelper.service.TrackLinkService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/track-link")
@Validated
public class TrackerController {

    @Autowired
    private TrackLinkService trackLinkService;

    @PostMapping()
    public ResponseEntity<String> getTrackLink(@Valid @RequestBody TrackLinkCreateDTO trackLinkCreateDTO){
        String trackLink = trackLinkService.getTrackLink(trackLinkCreateDTO,1L);
        return ResponseEntity.status(HttpStatus.OK).body(trackLink);
    }

    @GetMapping("/t/{shortCode}")
    public ResponseEntity<String> analyzeTrackLink(@PathVariable
                                                       @Pattern(regexp = "^[0-9a-zA-Z]{1,10}$",
                                                               message = "Short code must be 1-10 alphanumeric characters")
                                                       String shortCode, HttpServletRequest httpServletRequest){

        String redirectUrl = trackLinkService.analyzeTrackLinkClick(httpServletRequest, shortCode);

        return ResponseEntity.status(HttpStatus.OK).body(redirectUrl);
    }

    @GetMapping("/{id}/analyze")
    public ResponseEntity<VisitLinksAnalyze> getTrackLinkInformation(@PathVariable
                                                                         @Min(value = 1,message = "Id must be greater than 0")
                                                                         Long id){
        VisitLinksAnalyze visitLinksAnalyze = trackLinkService.getTrackLinkAnalyze(id);
        return ResponseEntity.status(HttpStatus.OK).body(visitLinksAnalyze);
    }
}
