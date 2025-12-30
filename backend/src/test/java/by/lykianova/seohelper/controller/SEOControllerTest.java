package by.lykianova.seohelper.controller;

import by.lykianova.seohelper.DTO.SeoReportCreateDTO;
import by.lykianova.seohelper.response.SeoAnalyseResult;
import by.lykianova.seohelper.service.SEOService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(SEOController.class)
class SEOControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SEOService seoService;

    @Test
    void successGetSiteAnalyzeTest() throws Exception{
        SeoReportCreateDTO seoReportCreateDTO = new SeoReportCreateDTO();
        seoReportCreateDTO.setUrl("https://mgkct.minskedu.gov.by");

        SeoAnalyseResult seoAnalyseResult = new SeoAnalyseResult();
        seoAnalyseResult.setRecommendations("some recs");

        Mockito.when(seoService.getAnalyze(seoReportCreateDTO,1L)).thenReturn(seoAnalyseResult);

        mockMvc.perform(post("/api/v1/seo/analyze")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(seoReportCreateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.recommendations").value("some recs"));

        Mockito.verify(seoService).getAnalyze(seoReportCreateDTO,1L);
    }

    @Test
    void validationRequestTest() throws Exception{

        SeoReportCreateDTO seoReportCreateDTO = new SeoReportCreateDTO();
        seoReportCreateDTO.setUrl("mgkct.minskedu.gov.by");

        mockMvc.perform(post("/api/v1/seo/analyze")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(seoReportCreateDTO)))
                .andExpect(status().isBadRequest());

        Mockito.verifyNoInteractions(seoService);
    }

    @Test
    void successGetSEOHistoryTest() throws Exception{

        SeoAnalyseResult seoAnalyseResult1 = new SeoAnalyseResult();
        seoAnalyseResult1.setRecommendations("recs1");

        SeoAnalyseResult seoAnalyseResult2 = new SeoAnalyseResult();
        seoAnalyseResult2.setRecommendations("recs2");

        List<SeoAnalyseResult> resultList = List.of(seoAnalyseResult1,seoAnalyseResult2);

        Mockito.when(seoService.getAllAnylysesById(1L)).thenReturn(resultList);

        mockMvc.perform(get("/api/v1/seo/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].recommendations").value("recs1"))
                .andExpect(jsonPath("$[1].recommendations").value("recs2"));


        Mockito.verify(seoService).getAllAnylysesById(1L);

    }

    @Test
    void GetEmptySeoHistoryTest() throws Exception{

        Mockito.when(seoService.getAllAnylysesById(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/seo/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

    }

    @Test
    void successGetAnalyzeInformationTest() throws Exception{

        SeoAnalyseResult seoAnalyseResult = new SeoAnalyseResult();
        seoAnalyseResult.setRecommendations("recs");

        Mockito.when(seoService.getAnalyzeById(1L)).thenReturn(seoAnalyseResult);

        mockMvc.perform(get("/api/v1/seo/1/analyze-information"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recommendations").value("recs"));
    }

    @Test
    void userNotFoundAtGetAnalyzeInformationTest() throws Exception{

        Mockito.when(seoService.getAnalyzeById(1l))
                .thenThrow(new EntityNotFoundException("seo analyze with this id is not found"));

        mockMvc.perform(get("/api/v1/seo/1/analyze-information"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("seo analyze with this id is not found"));


    }

    @Test
    void validationGetAnalyzeInformationTest() throws Exception{


        mockMvc.perform(get("/api/v1/seo/0/analyze-information"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Id must be greater than 0"));

        Mockito.verifyNoInteractions(seoService);
    }
}