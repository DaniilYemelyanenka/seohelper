package by.lykianova.seohelper.controller;


import by.lykianova.seohelper.DTO.SeoReportCreateDTO;
import by.lykianova.seohelper.entity.SeoAnalyseResult;
import by.lykianova.seohelper.response.CustomApiResponse;
import by.lykianova.seohelper.service.SEOService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/seo/")
@Validated
public class SEOController{

    @Autowired
    private SEOService seoService;

    @PostMapping("analyze")
    public ResponseEntity<CustomApiResponse<SeoAnalyseResult>> getSiteAnalyze(@Valid @RequestBody SeoReportCreateDTO seoReportCreateDTO){
        SeoAnalyseResult seoAnalyseResult = seoService.getAnalyze(seoReportCreateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomApiResponse<>(true,seoAnalyseResult));
    }

    @GetMapping("history")
    public ResponseEntity<String> getSEOHistory(){
        //TODO get analyze history
        return ResponseEntity.status(HttpStatus.OK).body("history");
    }

    @GetMapping("{id}")
    public ResponseEntity<String> getAnalyzeInformation(@RequestParam(value = "id") Long id){
        //TODO get analyze information
        return ResponseEntity.status(HttpStatus.OK).body("by id");
    }

}
