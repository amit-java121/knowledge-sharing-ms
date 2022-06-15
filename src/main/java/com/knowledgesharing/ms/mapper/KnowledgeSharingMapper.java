package com.knowledgesharing.ms.mapper;

import com.knowledgesharing.ms.datatransfer.KnowledgeSharingDto;
import com.knowledgesharing.ms.entities.KnowledgeSharing;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface KnowledgeSharingMapper {

    @Mapping(constant = "some-user", target = "updatedBy")
    @Mapping(target = "updatedAt", qualifiedByName = "getDateTime", constant = "")
    @Mapping(constant = "some-user", target = "createdBy")
    @Mapping(target = "createdAt", qualifiedByName = "getDateTime", constant = "")
//    @Mapping(target = "id", source = "id")
    KnowledgeSharing convertToEntityForInsert(KnowledgeSharingDto knowledgeSharingDto);

    @Mapping(source = "knowledgeSharing", target = "knowledgeSharingDtoList")
    List<KnowledgeSharingDto> convertToDto(List<KnowledgeSharing> knowledgeSharing);

    @Named("getDateTime")
    default LocalDateTime getDateTime(String value) {
        return LocalDateTime.now();
    }
}