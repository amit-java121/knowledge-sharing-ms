package com.knowledgesharing.ms.integration.repository;

import com.knowledgesharing.ms.Application;
import com.knowledgesharing.ms.repository.KnowledgeSharingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.knowledgesharing.ms.utility.TestData.dataKnowledgeSharingTable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@SpringBootTest(classes = Application.class)
public class KnowledgeSharingRepositoryTest {

    @Autowired
    KnowledgeSharingRepository knowledgeSharingRepository;

    @BeforeEach
    void beforeEach() {
//        knowledgeSharingRepository.deleteAll();
    }

    @Nested
    @TestInstance(PER_CLASS)
    class fetchDetails {
        @Test
        void shouldInsertRecord() {

        }
    }
}
