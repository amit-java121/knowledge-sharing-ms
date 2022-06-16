package com.knowledgesharing.ms.repository.specification;

import com.knowledgesharing.ms.entities.KnowledgeSharing;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class KnowledgeSharingSpecification {

    public Specification<KnowledgeSharing> getKnowledgeSharing(String author, String title, Long likes, Long views) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasLength(title)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }
            if (StringUtils.hasLength(author)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), "%" + author.toLowerCase() + "%"));
            }
            if (!ObjectUtils.isEmpty(likes)) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("likes"), likes));
            }
            if (!ObjectUtils.isEmpty(views)) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("views"), views));
            }
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), LocalDate.now()));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
