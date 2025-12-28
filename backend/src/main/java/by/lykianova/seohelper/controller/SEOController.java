package by.lykianova.seohelper.controller;


import by.lykianova.seohelper.DTO.SeoReportCreateDTO;
import by.lykianova.seohelper.entity.SeoAnalyseResult;
import by.lykianova.seohelper.response.CustomApiResponse;
import by.lykianova.seohelper.service.SEOService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/seo/")
@Validated
public class SEOController{

    @Autowired
    private SEOService seoService;

    @PostMapping("analyze")
    public ResponseEntity<CustomApiResponse<SeoAnalyseResult>> getSiteAnalyze(@Valid @RequestBody SeoReportCreateDTO seoReportCreateDTO){
        SeoAnalyseResult seoAnalyseResult = seoService.getAnalyze(seoReportCreateDTO,1L);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomApiResponse<>(true,seoAnalyseResult));
    }

    @GetMapping("history")
    public ResponseEntity<List<SeoAnalyseResult>> getSEOHistory(){
        List<SeoAnalyseResult> results = seoService.getAllAnylysesById(1L);
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

    @GetMapping("{id}/analyze-information")
    public ResponseEntity<SeoAnalyseResult> getAnalyzeInformation(@PathVariable @Min(value = 1,message = "Id must be greater than 0") Long id){
        SeoAnalyseResult seoAnalyseResult = seoService.getAnalyzeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(seoAnalyseResult);
    }

}
