package com.knowledgesharing.ms.mapper;

import com.knowledgesharing.ms.datatransfer.KnowledgeSharingDto;
import com.knowledgesharing.ms.entities.KnowledgeSharing;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.knowledgesharing.ms.utility.TestData.dataKnowledgeSharingTable;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mapstruct.factory.Mappers.getMapper;

public class KnowledgeSharingMapperTest {

    private KnowledgeSharingMapper knowledgeSharingMapper = getMapper(KnowledgeSharingMapper.class);

    @Test
    void shouldToConvertToEntity() {

        KnowledgeSharingDto knowledgeSharingDto = KnowledgeSharingDto
                .builder()
                .title("some-title")
                .author("some-author")
                .views(100L)
                .date(LocalDate.now())
                .likes(100L)
                .link("some-link")
                .build();

        KnowledgeSharing actual = knowledgeSharingMapper.convertToEntityForInsert(knowledgeSharingDto);
        assertNotNull(actual);
    }

    @Test
    void shouldToConvertToEntityReturnNull() {
        KnowledgeSharing actual = knowledgeSharingMapper.convertToEntityForInsert(null);
        assertNull(actual);
    }

    @Test
    void shouldConvertToDto() {
        List<KnowledgeSharingDto> actual = knowledgeSharingMapper.convertToDto(List.of(dataKnowledgeSharingTable()));
        assertNotNull(actual);
    }

    @Test
    void shouldConvertToDtoReturnNull() {
        List<KnowledgeSharingDto> actual = knowledgeSharingMapper.convertToDto(null);
        assertNull(actual);
    }

    @Test
    void getDateTime() {
        LocalDateTime actual = knowledgeSharingMapper.getDateTime("");
        assertNotNull(actual);
    }
}
