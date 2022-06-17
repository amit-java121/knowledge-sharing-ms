package com.knowledgesharing.ms.service;

import com.knowledgesharing.ms.datatransfer.KnowledgeSharingDto;
import com.knowledgesharing.ms.datatransfer.KnowledgeSharingModelView;
import com.knowledgesharing.ms.entities.KnowledgeSharing;
import com.knowledgesharing.ms.exception.NotFoundException;
import com.knowledgesharing.ms.mapper.KnowledgeSharingMapper;
import com.knowledgesharing.ms.repository.KnowledgeSharingRepository;
import com.knowledgesharing.ms.repository.specification.KnowledgeSharingSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class KnowledgeSharingService {

    private final KnowledgeSharingRepository knowledgeSharingRepository;
    private final KnowledgeSharingMapper knowledgeSharingMapper;

    private final KnowledgeSharingSpecification knowledgeSharingSpecification;

    /**
     * Get the details based on the search parameters that are being send as a request parameters to the service
     *
     * @param author
     * @param title
     * @param likes
     * @param views
     * @return
     */
    public KnowledgeSharingModelView fetchDetails(String author, String title, Long likes, Long views) {
        List<KnowledgeSharingDto> knowledgeSharingDtoList = knowledgeSharingMapper.convertToDto(knowledgeSharingRepository.findAll(
                knowledgeSharingSpecification.getKnowledgeSharing(author, title, likes, views)));
        return KnowledgeSharingModelView.builder().knowledgeSharingDtoList(knowledgeSharingDtoList).build();
    }

    /**
     * Inserts new knowledge sharing details that is received from the FE or External provider
     *
     * @param knowledgeSharingDto
     * @return
     */
    public Long insertDetails(KnowledgeSharingDto knowledgeSharingDto) {
        KnowledgeSharing knowledgeSharing = knowledgeSharingRepository.save(knowledgeSharingMapper.convertToEntityForInsert(knowledgeSharingDto));
        return knowledgeSharing.getId();
    }

    /**
     * Modifies the existing details on the DB based on the ID that is passed and the variables that are needs to be changed
     *
     * @param id
     * @param knowledgeSharingDto
     * @return
     */
    public Long modifyDetails(Long id, KnowledgeSharingDto knowledgeSharingDto) {
        KnowledgeSharing knowledgeSharing = knowledgeSharingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("The required ID your searching is not found on the DB " + id));
        KnowledgeSharing knowledgeSharingNew = mergeWithNewValues(knowledgeSharingDto, id, knowledgeSharing);
        knowledgeSharingRepository.save(knowledgeSharingNew);
        return id;
    }

    /**
     * This method transforms the Entity object to a new Entity for perform the update
     * @param knowledgeSharingDto
     * @param id
     * @param knowledgeSharing
     * @return
     */
    private KnowledgeSharing mergeWithNewValues(KnowledgeSharingDto knowledgeSharingDto, Long id, KnowledgeSharing knowledgeSharing) {
        knowledgeSharing.setId(id);
        if (StringUtils.hasLength(knowledgeSharingDto.getTitle())) {
            knowledgeSharing.setTitle(knowledgeSharingDto.getTitle());
        }
        if (nonNull(knowledgeSharingDto.getLikes())) {
            knowledgeSharing.setLikes(knowledgeSharingDto.getLikes());
        }
        if (StringUtils.hasLength(knowledgeSharingDto.getAuthor())) {
            knowledgeSharing.setAuthor(knowledgeSharingDto.getAuthor());
        }
        if (nonNull(knowledgeSharingDto.getViews())) {
            knowledgeSharing.setViews(knowledgeSharingDto.getViews());
        }
        if (nonNull(knowledgeSharingDto.getDate())) {
            knowledgeSharing.setDate(knowledgeSharingDto.getDate());
        }
        if (StringUtils.hasLength(knowledgeSharingDto.getLink())) {
            knowledgeSharing.setLink(knowledgeSharingDto.getLink());
        }
        knowledgeSharing.setUpdatedBy("some-user");
        knowledgeSharing.setUpdatedAt(LocalDateTime.now());
        return knowledgeSharing;
    }

    /**
     * Deletes the element from the table by taking the ID as an input from the service
     *
     * @param id
     */
    public void deleteDetails(Long id) {
        knowledgeSharingRepository.deleteById(id);
    }
}
