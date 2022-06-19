package com.knowledgesharing.ms.service;

import com.knowledgesharing.ms.entities.UserAccessDetails;
import com.knowledgesharing.ms.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(
                userRepository
        );
    }

    @Test
    void getUserDetails() {
        String uuid = "UUID1";
        UserAccessDetails userAccessDetails = UserAccessDetails.builder()
                .userId("UUID1")
                .name("user")
                .role("ADMIN")
                .build();

        Optional<UserAccessDetails> userAccessDetailsOptional = Optional.of(userAccessDetails);
        when(userRepository.findById(uuid))
                .thenReturn(userAccessDetailsOptional);
        UserDetails actual = userService.loadUserByUsername(uuid);
        assertNotNull(actual);

    }
}
