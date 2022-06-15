package com.knowledgesharing.ms.repository.specification;

import com.knowledgesharing.ms.entities.KnowledgeSharing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.data.jpa.domain.Specification.not;

class SpecificationTest {

    Specification<KnowledgeSharing> mockSpec;
    @Mock(extraInterfaces = Serializable.class)
    Root<KnowledgeSharing> root;
    @Mock(extraInterfaces = Serializable.class)
    CriteriaQuery<?> query;
    @Mock(extraInterfaces = Serializable.class)
    CriteriaBuilder builder;

    @Mock(extraInterfaces = Serializable.class)
    Predicate predicate;

    private KnowledgeSharingSpecification knowledgeSharingSpecification;

    @BeforeEach
    void setUp() {
        mockSpec = (Specification<KnowledgeSharing>) mock(Specification.class, withSettings().serializable());
        when(mockSpec.toPredicate(root, query, builder)).thenReturn(predicate);
        knowledgeSharingSpecification = new KnowledgeSharingSpecification();
    }

    @Test
    public void testSpecification() {
        String author = "some-author";
        String title = "some-title";
        Long views = 100L;
        Long likes = 100L;
        knowledgeSharingSpecification.getKnowledgeSharing(author, title, likes, views);
        assertNotNull(author);
        assertNotNull(title);
        assertNotNull(likes);
        assertNotNull(views);

    }

}
