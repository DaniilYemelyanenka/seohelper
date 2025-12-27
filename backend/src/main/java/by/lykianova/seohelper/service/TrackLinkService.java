package by.lykianova.seohelper.service;

import by.lykianova.seohelper.DTO.TrackLinkCreateDTO;
import by.lykianova.seohelper.DTO.TrackLinkDTO;
import by.lykianova.seohelper.config.Base62;
import by.lykianova.seohelper.entity.TrackedLink;
import by.lykianova.seohelper.mapper.Impl.TrackLinkMapper;
import by.lykianova.seohelper.repository.TrackLinkRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TrackLinkService{

    @Autowired
    private TrackLinkRepository trackLinkRepository;

    @Autowired
    private TrackLinkMapper trackLinkMapper;

    public String getTrackLink(TrackLinkCreateDTO trackLinkCreateDTO){

        TrackLinkDTO trackLinkDTO = trackLinkMapper.fromCreateToDTO(trackLinkCreateDTO);
        TrackedLink trackedLink = trackLinkMapper.toEntity(trackLinkDTO);
        TrackedLink savedTrackLink  = trackLinkRepository.save(trackedLink);
        //TODO Change savedTrackLink from entity to DTO

        String token = Base62.encode(savedTrackLink.getId());
        savedTrackLink.setShort_code(token);
        trackLinkRepository.save(savedTrackLink);
        return "api/v1/api/v1/track-link/" + token;
    }

    public void getTrackInfo(HttpServletRequest request){

    }
}
