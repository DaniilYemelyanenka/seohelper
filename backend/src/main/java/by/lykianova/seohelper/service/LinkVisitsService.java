package by.lykianova.seohelper.service;

import by.lykianova.seohelper.DTO.LinkVisitDTO;
import by.lykianova.seohelper.mapper.Impl.LinkVisitMapper;
import by.lykianova.seohelper.repository.LinkVisitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkVisitsService {

    @Autowired
    private LinkVisitsRepository linkVisitsRepository;

    @Autowired
    private LinkVisitMapper linkVisitMapper;

    public void addLinkVisit(LinkVisitDTO linkVisitDTO){
        linkVisitsRepository.save(linkVisitMapper.toEntity(linkVisitDTO));
    }

}
