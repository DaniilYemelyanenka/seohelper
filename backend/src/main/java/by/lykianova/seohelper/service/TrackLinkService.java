package by.lykianova.seohelper.service;

import by.lykianova.seohelper.DTO.TrackLinkCreateDTO;
import by.lykianova.seohelper.DTO.TrackLinkDTO;
import by.lykianova.seohelper.config.Base62;
import by.lykianova.seohelper.entity.LinkVisits;
import by.lykianova.seohelper.entity.TrackedLink;
import by.lykianova.seohelper.entity.VisitLinksAnalyze;
import by.lykianova.seohelper.mapper.Impl.LinkVisitMapper;
import by.lykianova.seohelper.mapper.Impl.TrackLinkMapper;
import by.lykianova.seohelper.repository.TrackLinkRepository;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TrackLinkService{

    @Autowired
    private TrackLinkRepository trackLinkRepository;

    @Autowired
    private LinkVisitsService linkVisitsService;

    @Autowired
    private TrackLinkMapper trackLinkMapper;

    @Autowired
    private LinkVisitMapper linkVisitMapper;

    public String getTrackLink(TrackLinkCreateDTO trackLinkCreateDTO){

        TrackLinkDTO trackLinkDTO = trackLinkMapper.fromCreateToDTO(trackLinkCreateDTO);
        TrackedLink trackedLink = trackLinkMapper.toEntity(trackLinkDTO);
        TrackedLink savedTrackLink  = trackLinkRepository.save(trackedLink);
        //TODO Change savedTrackLink from entity to DTO

        String shortCode = Base62.encode(savedTrackLink.getId());

        trackLinkRepository.save(savedTrackLink);
        return "api/v1/api/v1/track-link/" + shortCode;
    }

    public String analyzeTrackLinkClick(HttpServletRequest httpServletRequest, String shortCode){
        TrackedLink trackedLink = trackLinkRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new EntityNotFoundException("Track link with this short code not found"));

        LinkVisits linkVisits = setTrackInfo(httpServletRequest);
        linkVisits.setTrackedLink(trackedLink);

        linkVisitsService.addLinkVisit(linkVisitMapper.toDTO(linkVisits));

        return trackedLink.getOriginalUrl();
    }

    private LinkVisits setTrackInfo(HttpServletRequest request){
        LinkVisits linkVisits = new LinkVisits();
        linkVisits.setIpAddress(request.getRemoteAddr());
        linkVisits.setVisitedAt(LocalDateTime.now());

        String userAgentString = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);

        linkVisits.setBrowser(userAgent.getBrowser().getName());
        linkVisits.setDevice(userAgent.getOperatingSystem().getDeviceType().getName());


        //TODO ADD CLOUDFLARE?
        linkVisits.setCountry("BY");

        return linkVisits;
    }

    public VisitLinksAnalyze getTrackLinkAnalyze(Long id){

        if(trackLinkRepository.existsById(id)){
            return linkVisitsService.makeVisitLinkAnalyze(id);
        }else
            throw new EntityNotFoundException("Track link with this id not found");

    }

}
