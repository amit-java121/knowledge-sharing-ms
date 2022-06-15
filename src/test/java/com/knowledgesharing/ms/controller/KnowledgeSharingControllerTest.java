package com.knowledgesharing.ms.controller;

import com.knowledgesharing.ms.datatransfer.KnowledgeSharingDto;
import com.knowledgesharing.ms.datatransfer.KnowledgeSharingModelView;
import com.knowledgesharing.ms.service.KnowledgeSharingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class KnowledgeSharingControllerTest {

    private final KnowledgeSharingService knowledgeSharingService = mock(KnowledgeSharingService.class);

    private KnowledgeSharingController knowledgeSharingController;

    @BeforeEach
    void setUp() {
        knowledgeSharingController = new KnowledgeSharingController(knowledgeSharingService);
    }

    @Test
    void fetchDetails() {
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

        when(knowledgeSharingService.fetchDetails(author, title, views, likes)).thenReturn(expected);
        KnowledgeSharingModelView actual = knowledgeSharingController.fetchDetails(author, title, views, likes);
        assertEquals(actual, expected);
    }

    @Test
    void insertDetails() {
        Long id = 1L;

        KnowledgeSharingDto knowledgeSharingDto = KnowledgeSharingDto
                .builder()
                .title("some-title")
                .author("some-author")
                .views(100L)
                .likes(100L)
                .link("some-link")
                .build();

        when(knowledgeSharingService.insertDetails(knowledgeSharingDto)).thenReturn(id);
        ResponseEntity<Long> actual = knowledgeSharingController.insertDetails(knowledgeSharingDto);
        assertEquals(id, actual.getBody());
    }

    @Test
    void modifyDetails() {
        Long id = 1L;

        KnowledgeSharingDto knowledgeSharingDto = KnowledgeSharingDto
                .builder()
                .title("some-title")
                .author("some-author")
                .views(100L)
                .likes(100L)
                .link("some-link")
                .build();

        when(knowledgeSharingService.modifyDetails(id, knowledgeSharingDto)).thenReturn(id);
        ResponseEntity<Long> actual = knowledgeSharingController.modifyDetails(id, knowledgeSharingDto);
        assertEquals(id, actual.getBody());
    }

    @Test
    void deleteDetails() {
        Long id = 1L;
        doNothing().when(knowledgeSharingService).deleteDetails(id);
        knowledgeSharingController.deleteDetails(id);
        assertNotNull(id);
    }
}
