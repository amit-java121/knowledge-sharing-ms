package com.knowledgesharing.ms.service;

import com.knowledgesharing.ms.datatransfer.KnowledgeSharingDto;
import com.knowledgesharing.ms.datatransfer.KnowledgeSharingModelView;
import com.knowledgesharing.ms.entities.KnowledgeSharing;
import com.knowledgesharing.ms.exception.NotFoundException;
import com.knowledgesharing.ms.mapper.KnowledgeSharingMapper;
import com.knowledgesharing.ms.repository.KnowledgeSharingRepository;
import com.knowledgesharing.ms.repository.specification.KnowledgeSharingSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KnowledgeSharingServiceTest {

    private final KnowledgeSharingRepository knowledgeSharingRepository = mock(KnowledgeSharingRepository.class);
    private final KnowledgeSharingMapper knowledgeSharingMapper = mock(KnowledgeSharingMapper.class);

    private final KnowledgeSharingSpecification knowledgeSharingSpecification = mock(KnowledgeSharingSpecification.class);

    private KnowledgeSharingService knowledgeSharingService;

    Specification<KnowledgeSharing> mockSpec;
    @Mock(extraInterfaces = Serializable.class)
    Root<KnowledgeSharing> root;
    @Mock(extraInterfaces = Serializable.class)
    CriteriaQuery<?> query;
    @Mock(extraInterfaces = Serializable.class)
    CriteriaBuilder builder;

    @Mock(extraInterfaces = Serializable.class)
    Predicate predicate;

    @BeforeEach
    void setUp() {
        knowledgeSharingService = new KnowledgeSharingService(
                knowledgeSharingRepository,
                knowledgeSharingMapper, knowledgeSharingSpecification
        );

        mockSpec = (Specification<KnowledgeSharing>) mock(Specification.class, withSettings().serializable());
        when(mockSpec.toPredicate(root, query, builder)).thenReturn(predicate);
    }

    @Nested
    class GetDetails {

        @Test
        void shouldFetchDetails() {
            String author = "some-author";
            String title = "some-title";
            Long views = 100L;
            Long likes = 100L;

            KnowledgeSharing knowledgeSharing = KnowledgeSharing.builder()
                    .id(1L)
                    .title("some-title")
                    .author("some-author")
                    .views(100L)
                    .likes(100L)
                    .link("some-link")
                    .build();

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

            when(knowledgeSharingSpecification.getKnowledgeSharing(author, title, likes, views)).thenReturn(mockSpec);
            when(knowledgeSharingRepository.findAll(mockSpec))
                    .thenReturn(List.of(knowledgeSharing));
            when(knowledgeSharingMapper.convertToDto(List.of(knowledgeSharing))).thenReturn(List.of(knowledgeSharingDto));
            KnowledgeSharingModelView actual = knowledgeSharingService.fetchDetails(author, title, likes, views);
            assertEquals(actual, expected);
        }
    }

    @Nested
    class InsertDetails {

        @Test
        void shouldInsertDetails() {
            Long id = 100L;

            KnowledgeSharing knowledgeSharing = KnowledgeSharing.builder()
                    .id(id)
                    .title("some-title")
                    .author("some-author")
                    .views(100L)
                    .likes(100L)
                    .link("some-link")
                    .build();

            KnowledgeSharingDto knowledgeSharingDto = KnowledgeSharingDto
                    .builder()
                    .title("some-title")
                    .author("some-author")
                    .views(100L)
                    .likes(100L)
                    .link("some-link")
                    .build();

            when(knowledgeSharingMapper.convertToEntityForInsert(knowledgeSharingDto)).thenReturn(knowledgeSharing);
            when(knowledgeSharingRepository.save(knowledgeSharing)).thenReturn(knowledgeSharing);
            Long expected = knowledgeSharingService.insertDetails(knowledgeSharingDto);
            assertEquals(id, expected);
        }
    }

    @Nested
    class ModifyDetails {

        @Test
        void shouldModifyDetails() {
            Long id = 100L;

            Optional<KnowledgeSharing> knowledgeSharing = Optional.of(KnowledgeSharing.builder()
                    .id(id)
                    .title("some-title")
                    .author("some-author")
                    .views(100L)
                    .likes(100L)
                    .link("some-link")
                    .build());

            KnowledgeSharing knowledgeSharingModified = KnowledgeSharing.builder()
                    .id(id)
                    .title("some-title-modify")
                    .author("some-author-modify")
                    .views(100L)
                    .likes(100L)
                    .link("some-link")
                    .build();

            KnowledgeSharingDto knowledgeSharingDto = KnowledgeSharingDto
                    .builder()
                    .title("some-title-modify")
                    .author("some-author-modify")
                    .views(100L)
                    .likes(100L)
                    .link("some-link")
                    .date("some-date")
                    .build();

            when(knowledgeSharingRepository.findById(id)).thenReturn(knowledgeSharing);
            when(knowledgeSharingRepository.save(knowledgeSharingModified)).thenReturn(knowledgeSharingModified);
            when(knowledgeSharingService.modifyDetails(id, knowledgeSharingDto)).thenReturn(id);
            assertNotNull(id);
        }

        @Test
        void shouldThrowExceptionWhenModifying() {
            Long id = 1L;
            when(knowledgeSharingRepository.findById(id)).thenReturn(Optional.empty());
            NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> knowledgeSharingService.modifyDetails(1L, null));
            assertEquals("The required ID your searching is not found on the DB 1", notFoundException.getMessage());

        }
    }
}
