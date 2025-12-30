package by.lykianova.seohelper.service;

import by.lykianova.seohelper.DTO.SeoReportCreateDTO;
import by.lykianova.seohelper.DTO.SeoReportDTO;
import by.lykianova.seohelper.response.SeoAnalyseResult;
import by.lykianova.seohelper.entity.SeoReports;
import by.lykianova.seohelper.mapper.Impl.SeoMapper;
import by.lykianova.seohelper.repository.SeoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SEOService {

    @Autowired
    private SeoParserService seoParserService;

    @Autowired
    private SeoAnalyserService seoAnalyserService;

    @Autowired
    private UserService userService;

    @Autowired
    private SeoRepository seoRepository;

    @Autowired
    private SeoMapper seoMapper;

    public SeoAnalyseResult getAnalyze(SeoReportCreateDTO seoReportCreateDTO,Long userId){

        SeoReportDTO seoReportDTO = seoParserService.parse(seoReportCreateDTO.getUrl());

        SeoReports seoReports = seoMapper.toEntity(seoReportDTO);

        seoReports.setUser(userService.getUserById(userId));

        SeoReports saved = seoRepository.save(seoReports);

        SeoReportDTO savedDTO = seoMapper.toDTO(saved);

        return seoAnalyserService.analyseSite(savedDTO);
    }

    public SeoAnalyseResult getAnalyzeById(Long id){
        SeoReports seoReports = seoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("seo analyze with this id is not found"));
        SeoReportDTO seoReportDTO = seoMapper.toDTO(seoReports);
        return seoAnalyserService.analyseSite(seoReportDTO);
    }

    public List<SeoAnalyseResult> getAllAnylysesById(Long id){
        List<SeoAnalyseResult> seoAnalyseResults = new ArrayList<>();
        List<SeoReports> seoReports = seoRepository.findAllByUserId(id);
        List<SeoReportDTO> seoReportDTOS = seoMapper.toDtos(seoReports);
        seoReportDTOS.forEach(el -> seoAnalyseResults.add(seoAnalyserService.analyseSite(el)));
        return seoAnalyseResults;
    }

}
