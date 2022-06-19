package com.knowledgesharing.ms.repository;

import com.knowledgesharing.ms.entities.UserAccessDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserAccessDetails, String> {

}
