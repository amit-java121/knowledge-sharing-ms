package com.knowledgesharing.ms.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowledgesharing.ms.Application;
import com.knowledgesharing.ms.controller.KnowledgeSharingController;
import com.knowledgesharing.ms.datatransfer.KnowledgeSharingDto;
import com.knowledgesharing.ms.datatransfer.KnowledgeSharingModelView;
import com.knowledgesharing.ms.service.KnowledgeSharingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {Application.class})
@WebMvcTest(KnowledgeSharingController.class)
public class KnowSharingControllerApiTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KnowledgeSharingService knowledgeSharingService;

    @BeforeEach
    void beforeEach() {
        reset(knowledgeSharingService);
    }

    @Nested
    @TestInstance(PER_CLASS)
    class FetchDetails {
        @Test
        void shouldFetchDetailsAPI() throws Exception {
            String author = "some-author";
            String title = "some-title";
            Long views = 100L;
            Long likes = 100L;

            KnowledgeSharingDto knowledgeSharingDto = KnowledgeSharingDto
                    .builder()
                    .title("some-title")
                    .author("some-author")
                    .views(100L)
                    .likes(100L)
                    .link("some-link")
                    .build();

            KnowledgeSharingModelView expected = KnowledgeSharingModelView.builder()
                    .knowledgeSharingDtoList(List.of(knowledgeSharingDto)).build();

            String jsonResponse = objectMapper.writeValueAsString(expected);
            when(knowledgeSharingService.fetchDetails(author, title, views, likes)).thenReturn(expected);
            mockMvc.perform(get("/knowledge-sharing")
                            .queryParam("author", author)
                            .queryParam("title", title)
                            .queryParam("likes", "100")
                            .queryParam("views", "100"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(jsonResponse));
        }
    }

    @Nested
    @TestInstance(PER_CLASS)
    class InsertDetails {
        @Test
        void shouldInsertRecords() throws Exception {
            KnowledgeSharingDto knowledgeSharingDto = KnowledgeSharingDto
                    .builder()
                    .title("some-title")
                    .author("some-author")
                    .views(100L)
                    .likes(100L)
                    .link("some-link")
                    .build();

            String requestBody = objectMapper.writeValueAsString(knowledgeSharingDto);

            when(knowledgeSharingService.insertDetails(knowledgeSharingDto)).thenReturn(2L);

            mockMvc.perform(MockMvcRequestBuilders.post("/knowledge-sharing")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(requestBody))
                    .andExpect(status().is(SC_CREATED));

        }
    }

    @Nested
    @TestInstance(PER_CLASS)
    class modifyDetails {
        @Test
        void shouldModifyRecords() throws Exception {
            KnowledgeSharingDto knowledgeSharingDto = KnowledgeSharingDto
                    .builder()
                    .title("some-title")
                    .author("some-author")
                    .views(100L)
                    .likes(100L)
                    .link("some-link")
                    .build();

            String requestBody = objectMapper.writeValueAsString(knowledgeSharingDto);

            when(knowledgeSharingService.modifyDetails(1L, knowledgeSharingDto)).thenReturn(1L);
            mockMvc.perform(MockMvcRequestBuilders.put("/knowledge-sharing/1")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(requestBody))
                    .andExpect(status().is(SC_OK));

        }
    }

    @Nested
    @TestInstance(PER_CLASS)
    class deleteDetails {
        @Test
        void shouldModifyRecords() throws Exception {
            doNothing().when(knowledgeSharingService).deleteDetails(1L);
            mockMvc.perform(MockMvcRequestBuilders.delete("/knowledge-sharing/1")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().is(SC_NO_CONTENT));
        }
    }

}
