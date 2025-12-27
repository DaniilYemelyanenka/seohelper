package by.lykianova.seohelper.controller;

import by.lykianova.seohelper.DTO.TrackLinkCreateDTO;
import by.lykianova.seohelper.service.TrackLinkService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/track-link")
public class TrackerController {

    @Autowired
    private TrackLinkService trackLinkService;

    @GetMapping()
    public ResponseEntity<String> getTrackLink(@RequestBody TrackLinkCreateDTO trackLinkCreateDTO){
        String trackLink = trackLinkService.getTrackLink(trackLinkCreateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(trackLink);
    }

    @GetMapping("/{tackLink}")
    public ResponseEntity<String> postTrackLink(@PathVariable String trackLink){
        System.out.println("tracklink: " + trackLink);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @GetMapping("/info")
    public ResponseEntity<String> getTrackLinkInformation(@RequestParam(value = "id") Long id){
        System.out.println("id:" + id);
        return ResponseEntity.status(HttpStatus.OK).body("info");
    }
}
