package com.knowledgesharing.ms.service;

import com.knowledgesharing.ms.entities.UserAccessDetails;
import com.knowledgesharing.ms.repository.UserRepository;
import com.knowledgesharing.ms.service.authentication.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccessDetails> user = userRepository.findById(username);
        return user.map(AuthenticatedUser::new).orElse(null);
    }
}
