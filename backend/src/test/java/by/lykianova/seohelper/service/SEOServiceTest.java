package by.lykianova.seohelper.service;

import by.lykianova.seohelper.DTO.SeoReportCreateDTO;
import by.lykianova.seohelper.DTO.SeoReportDTO;
import by.lykianova.seohelper.entity.SeoReports;
import by.lykianova.seohelper.entity.User;
import by.lykianova.seohelper.error.UserNotFoundException;
import by.lykianova.seohelper.mapper.Impl.SeoMapper;
import by.lykianova.seohelper.repository.SeoRepository;
import by.lykianova.seohelper.response.SeoAnalyseResult;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SEOServiceTest {

    @Mock
    private SeoParserService seoParserService;

    @Mock
    private UserService userService;

    @Mock
    private SeoRepository seoRepository;

    @Mock
    private SeoMapper seoMapper;

    @Mock
    private SeoAnalyserService seoAnalyserService;

    @InjectMocks
    private SEOService seoService;


    @Test
    void successGetAnalyzeTest() {

        SeoReportCreateDTO createDTO = new SeoReportCreateDTO();
        createDTO.setUrl("https://example.com");

        SeoReportDTO seoReportDTO = new SeoReportDTO();
        SeoReports seoReports = new SeoReports();
        SeoReports saved = new SeoReports();
        SeoReportDTO savedDTO = new SeoReportDTO();

        User user = new User();

        SeoAnalyseResult seoAnalyseExpectedResult = new SeoAnalyseResult();
        seoAnalyseExpectedResult.setRecommendations("recs");

        Mockito.when(seoParserService.parse(createDTO.getUrl())).thenReturn(seoReportDTO);
        Mockito.when(seoMapper.toEntity(seoReportDTO)).thenReturn(seoReports);
        Mockito.when(userService.getUserById(1L)).thenReturn(user);
        Mockito.when(seoRepository.save(seoReports)).thenReturn(saved);
        Mockito.when(seoMapper.toDTO(saved)).thenReturn(savedDTO);
        Mockito.when(seoAnalyserService.analyseSite(savedDTO)).thenReturn(seoAnalyseExpectedResult);

        SeoAnalyseResult result = seoService.getAnalyze(createDTO,1L);

        assertEquals(seoAnalyseExpectedResult.getRecommendations(),result.getRecommendations());

        Mockito.verify(seoParserService).parse(createDTO.getUrl());
        Mockito.verify(userService).getUserById(1L);
        Mockito.verify(seoRepository).save(seoReports);
        Mockito.verify(seoAnalyserService).analyseSite(savedDTO);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundTest(){

        SeoReportCreateDTO createDTO = new SeoReportCreateDTO();
        createDTO.setUrl("https://example.com");

        Mockito.when(userService.getUserById(1L)).thenThrow(new UserNotFoundException(1L));

        assertThrows(UserNotFoundException.class,() -> seoService.getAnalyze(createDTO,1L));

        Mockito.verify(userService).getUserById(1L);
        Mockito.verifyNoInteractions(seoRepository);
    }

    @Test
    void parserFailsTest(){
        SeoReportCreateDTO createDTO = new SeoReportCreateDTO();
        createDTO.setUrl("https://example.com");

        Mockito.when(seoParserService.parse(createDTO.getUrl())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class,() -> seoService.getAnalyze(createDTO,1L));

        Mockito.verify(seoParserService).parse(createDTO.getUrl());
        Mockito.verifyNoInteractions(seoRepository);
    }

    @Test
    void successGetAnalyzeByIdTest() {

        SeoReports seoReports = new SeoReports();
        SeoReportDTO seoReportDTO = new SeoReportDTO();
        SeoAnalyseResult expextedSeoAnalyseResult = new SeoAnalyseResult();
        expextedSeoAnalyseResult.setRecommendations("recs");

        Mockito.when(seoRepository.findById(1L)).thenReturn(Optional.of(seoReports));
        Mockito.when(seoMapper.toDTO(seoReports)).thenReturn(seoReportDTO);
        Mockito.when(seoAnalyserService.analyseSite(seoReportDTO)).thenReturn(expextedSeoAnalyseResult);

        SeoAnalyseResult result = seoService.getAnalyzeById(1L);

        assertEquals(expextedSeoAnalyseResult.getRecommendations(),result.getRecommendations());

        Mockito.verify(seoRepository).findById(1L);
        Mockito.verify(seoAnalyserService).analyseSite(seoReportDTO);
    }

    @Test
    void shouldThrowExceptionWhenSeoAnalyzeNotFound(){

        Mockito.when(seoRepository.findById(1L))
                .thenThrow(new EntityNotFoundException("seo analyze with this id is not found"));


        assertThrows(EntityNotFoundException.class,() -> seoService.getAnalyzeById(1L));

        Mockito.verify(seoRepository).findById(1L);
        Mockito.verifyNoInteractions(seoMapper);
        Mockito.verifyNoInteractions(seoAnalyserService);
    }

    @Test
    void successGetAllAnylysesById() {

        List<SeoReports> seoReportsList = List.of(new SeoReports(),new SeoReports());
        List<SeoReportDTO> seoReportDTOList = List.of(new SeoReportDTO(),new SeoReportDTO());
        SeoAnalyseResult expectedSeoAnalyseResult = new SeoAnalyseResult();
        expectedSeoAnalyseResult.setRecommendations("recs");

        List<SeoAnalyseResult> expectedSeoAnalyzeResultList = List.of(expectedSeoAnalyseResult);

        Mockito.when(seoRepository.findAllByUserId(1L)).thenReturn(seoReportsList);
        Mockito.when(seoMapper.toDtos(seoReportsList)).thenReturn(seoReportDTOList);
        Mockito.when(seoAnalyserService.analyseSite(Mockito.any())).thenReturn(expectedSeoAnalyseResult);

        List<SeoAnalyseResult> result = seoService.getAllAnylysesById(1L);

        assertEquals(expectedSeoAnalyzeResultList.get(0).getRecommendations(),result.get(0).getRecommendations());
    }

    @Test
    void shouldReturnEmptyListWhenSeoAnalyzesNotFound(){
        List<SeoReports> seoReportsList = Collections.emptyList();
        List<SeoReportDTO> seoReportDTOList = Collections.emptyList();
        List<SeoAnalyseResult> expectedResult = Collections.emptyList();

        Mockito.when(seoRepository.findAllByUserId(1L)).thenReturn(seoReportsList);
        Mockito.when(seoMapper.toDtos(seoReportsList)).thenReturn(seoReportDTOList);

        List<SeoAnalyseResult> result = seoService.getAllAnylysesById(1L);

        assertEquals(expectedResult.isEmpty(),result.isEmpty());

    }
}