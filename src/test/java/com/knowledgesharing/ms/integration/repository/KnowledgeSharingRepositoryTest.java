package com.knowledgesharing.ms.integration.repository;

import com.knowledgesharing.ms.Application;
import com.knowledgesharing.ms.entities.KnowledgeSharing;
import com.knowledgesharing.ms.repository.KnowledgeSharingRepository;
import com.knowledgesharing.ms.repository.specification.KnowledgeSharingSpecification;
import com.knowledgesharing.ms.utility.TestData;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@SpringBootTest(classes = Application.class)
public class KnowledgeSharingRepositoryTest {

    @Autowired
    KnowledgeSharingRepository knowledgeSharingRepository;

    @Autowired
    KnowledgeSharingSpecification knowledgeSharingSpecification;

    @Nested
    @TestInstance(PER_CLASS)
    class insertDetails {
        @Test
        void shouldInsertRecord() {
            KnowledgeSharing knowledgeSharing = TestData.dataKnowledgeSharingTable();
            knowledgeSharing.setId(null);
            knowledgeSharingRepository.save(knowledgeSharing);
        }
    }

    @Nested
    @TestInstance(PER_CLASS)
    class fetchDetails {
        @Test
        void shouldInsertRecord() {
            String author = "Climate action needs new frontline leadership";
            String title = "Ozawa Bineshi Albert";
            Long views = 12000L;
            Long likes = 404000L;
            Specification<KnowledgeSharing> spec = knowledgeSharingSpecification.getKnowledgeSharing(author, title, likes, views);
            List<KnowledgeSharing> list = knowledgeSharingRepository.findAll(spec);
            assertNotNull(list);
        }
    }

    @Nested
    @TestInstance(PER_CLASS)
    class modifyDetails {
        @Test
        void shouldModifyRecord() {
            Long id = 20L;
            Optional<KnowledgeSharing> knowledgeSharing = knowledgeSharingRepository.findById(id);
            KnowledgeSharing entityBean = knowledgeSharing.get();
            entityBean.setLink("new link");
            KnowledgeSharing newInstance = knowledgeSharingRepository.save(entityBean);
            assertEquals(newInstance.getLink(), "new link");
        }
    }

    @Nested
    @TestInstance(PER_CLASS)
    class deleteDetails {
        @Test
        void shouldDeleteRecord() {
            Long id = 30L;
            knowledgeSharingRepository.deleteById(id);
            Optional<KnowledgeSharing> knowledgeSharingNewOp = knowledgeSharingRepository.findById(30L);
            boolean returnBeanAfterDelete = knowledgeSharingNewOp.isPresent();
            assertFalse(returnBeanAfterDelete);
        }
    }
}
