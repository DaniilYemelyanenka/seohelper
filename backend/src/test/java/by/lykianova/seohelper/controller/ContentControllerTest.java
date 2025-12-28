package by.lykianova.seohelper.controller;

import by.lykianova.seohelper.DTO.GenerateContentCreateDTO;
import by.lykianova.seohelper.DTO.GenerateContentDTO;
import by.lykianova.seohelper.entity.User;
import by.lykianova.seohelper.enums.ContentType;
import by.lykianova.seohelper.enums.Platform;
import by.lykianova.seohelper.error.UserNotFoundException;
import by.lykianova.seohelper.service.GenerateContentService;
import by.lykianova.seohelper.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContentController.class)
class ContentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    //отправляет запросы без поднятия сервера


    @Autowired
    private ObjectMapper objectMapper;
    // преобразование из object в JSON

    @MockBean
    private UserService userService;

    @MockBean
    private GenerateContentService generateContentService;


    @Test
    void  successGeneratedSiteDescriptionContentTest() throws Exception {

        GenerateContentCreateDTO generateContentCreateDTO = new GenerateContentCreateDTO();
        generateContentCreateDTO.setContentType(ContentType.POST);
        generateContentCreateDTO.setPlatform(Platform.TELEGRAM);
        generateContentCreateDTO.setTopic("cats");

        GenerateContentDTO generateContentDTO = new GenerateContentDTO();
        generateContentDTO.setContentType(ContentType.POST);
        generateContentDTO.setPlatform(Platform.TELEGRAM);
        generateContentDTO.setTopic("cats");
        generateContentDTO.setContent("generated content");
        generateContentDTO.setUserId(1L);

        User user = new User();
        user.setId(1L);

        Mockito.when(userService.getUserById(1l)).thenReturn(user);
        Mockito.when(generateContentService.generateContent(generateContentCreateDTO,user)).thenReturn(generateContentDTO);


        mockMvc.perform(post("/api/v1/content/generate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(generateContentCreateDTO)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.success").value(true))
                        .andExpect(jsonPath("$.data.content").value("generated content"));

        Mockito.verify(userService).getUserById(1L);
        Mockito.verify(generateContentService).generateContent(generateContentCreateDTO,user);

    }

    @Test
    void successGetHistoryTest() throws Exception{

        GenerateContentDTO generateContentDTO1 = new GenerateContentDTO();
        generateContentDTO1.setUserId(1L);
        generateContentDTO1.setContentType(ContentType.POST);
        generateContentDTO1.setPlatform(Platform.TELEGRAM);
        generateContentDTO1.setTopic("cats");
        generateContentDTO1.setContent("some content");

        GenerateContentDTO generateContentDTO2 = new GenerateContentDTO();
        generateContentDTO2.setUserId(1L);
        generateContentDTO2.setContentType(ContentType.POST);
        generateContentDTO2.setPlatform(Platform.TELEGRAM);
        generateContentDTO2.setTopic("dog");
        generateContentDTO2.setContent("some content dog");

        List<GenerateContentDTO> historyList = List.of(generateContentDTO1,generateContentDTO2);

        Mockito.when(generateContentService.getHistory(1L)).thenReturn(historyList);


        mockMvc.perform(get("/api/v1/content/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].topic").value("cats"))
                .andExpect(jsonPath("$[1].topic").value("dog"));

        Mockito.verify(generateContentService).getHistory(1L);
    }

    @Test
    void userNotFoundHistoryTest() throws Exception{

        Mockito.when(generateContentService.getHistory(1L)).thenThrow(new UserNotFoundException(1L));

        mockMvc.perform(get("/api/v1/content/history"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("User with id 1 not found"));
    }
    @Test
    void successGetEmptyHistoryTest() throws Exception{

        Mockito.when(generateContentService.getHistory(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/content/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void isBlankValidationTest() throws Exception {

        GenerateContentCreateDTO generateContentCreateDTO = new GenerateContentCreateDTO();
        generateContentCreateDTO.setTopic("");

        mockMvc.perform(post("/api/v1/content/generate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(generateContentCreateDTO)))
                .andExpect(status().isBadRequest());

        Mockito.verifyNoInteractions(generateContentService);

    }
}