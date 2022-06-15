package com.knowledgesharing.ms.repository;

import com.knowledgesharing.ms.entities.KnowledgeSharing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface KnowledgeSharingRepository extends JpaRepository<KnowledgeSharing, Long>, JpaSpecificationExecutor<KnowledgeSharing> {

}
