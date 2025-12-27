package by.lykianova.seohelper.service;

import by.lykianova.seohelper.DTO.LinkVisitDTO;
import by.lykianova.seohelper.entity.VisitLinksAnalyze;
import by.lykianova.seohelper.mapper.Impl.LinkVisitMapper;
import by.lykianova.seohelper.repository.LinkVisitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LinkVisitsService {

    @Autowired
    private LinkVisitsRepository linkVisitsRepository;

    @Autowired
    private LinkVisitMapper linkVisitMapper;

    public void addLinkVisit(LinkVisitDTO linkVisitDTO){
        linkVisitsRepository.save(linkVisitMapper.toEntity(linkVisitDTO));
    }

    public VisitLinksAnalyze makeVisitLinkAnalyze(Long trackLinkId){
        VisitLinksAnalyze visitLinksAnalyze = new VisitLinksAnalyze();
        visitLinksAnalyze.setVisitsCount(linkVisitsRepository.countByTrackedLinkId(trackLinkId));

        List<Object[]> countryList = linkVisitsRepository.countByCountry(trackLinkId);
        List<Object[]> browserList = linkVisitsRepository.countByBrowser(trackLinkId);
        List<Object[]> deviceList = linkVisitsRepository.countByDevice(trackLinkId);

        visitLinksAnalyze.setCountry(toMap(countryList));
        visitLinksAnalyze.setBrowsers(toMap(browserList));
        visitLinksAnalyze.setDevises(toMap(deviceList));

        return visitLinksAnalyze;
    }

    private Map<String,Integer> toMap(List<Object[]> rows){
        return rows.stream().collect(Collectors.toMap(
                row -> (String) row[0],
                row -> ((Long) row[1]).intValue()
        ));
    }

}
