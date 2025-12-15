package by.lykianova.seohelper.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/track-link")
public class TrackerController {

    @PostMapping()
    public ResponseEntity<String> getTrackLink(){
        return ResponseEntity.status(HttpStatus.OK).body("track-link");
    }

    @GetMapping("info")
    public ResponseEntity<String> getTrackLinkInformation(@RequestParam(value = "id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body("info");
    }
}
