package by.lykianova.seohelper.service;

import by.lykianova.seohelper.DTO.SeoReportCreateDTO;
import by.lykianova.seohelper.DTO.SeoReportDTO;
import by.lykianova.seohelper.entity.SeoAnalyseResult;
import by.lykianova.seohelper.entity.SeoReports;
import by.lykianova.seohelper.mapper.Impl.SeoMapper;
import by.lykianova.seohelper.repository.SeoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SEOService {

    @Autowired
    private SeoParserService seoParserService;

    @Autowired
    private SeoAnalyserService seoAnalyserService;

    @Autowired
    private SeoRepository seoRepository;

    @Autowired
    private SeoMapper seoMapper;

    public SeoAnalyseResult getAnalyze(SeoReportCreateDTO seoReportCreateDTO){
        SeoReportDTO seoReportDTO = seoParserService.parse(seoReportCreateDTO.getUrl());
        SeoReports seoReports = seoMapper.toEntity(seoReportDTO);
        seoRepository.save(seoReports);
        return seoAnalyserService.analyseSite(seoReportDTO);
    }

}
