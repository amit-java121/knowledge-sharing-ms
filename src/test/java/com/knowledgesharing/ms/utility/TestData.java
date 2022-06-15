package com.knowledgesharing.ms.utility;

import com.knowledgesharing.ms.entities.KnowledgeSharing;
import com.knowledgesharing.ms.repository.KnowledgeSharingRepository;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.time.LocalDateTime;

public class TestData implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static void flushDatabase() {
        KnowledgeSharingRepository repo = getBean(KnowledgeSharingRepository.class);
        repo.deleteAll();
        repo.flush();
    }

    public static <T> T getBean(Class<T> clazz) {
        if (applicationContext == null) {
            throw new RuntimeException("Application Context in Test builder is not initialized.");
        }
        return applicationContext.getBean(clazz);
    }

    public static KnowledgeSharing dataKnowledgeSharingTable() {
        return KnowledgeSharing.builder()
                .id(1L)
                .title("some-title")
                .author("some-author")
                .views(100L)
                .likes(100L)
                .link("some-link")
                .date("some-date")
                .createdAt(LocalDateTime.now())
                .createdBy("some-user")
                .updatedAt(LocalDateTime.now())
                .updatedBy("some-user")
                .build();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext = applicationContext;
    }
}
