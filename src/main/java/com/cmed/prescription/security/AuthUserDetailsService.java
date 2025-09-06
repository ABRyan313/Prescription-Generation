package com.cmed.prescription.security;

import com.cmed.prescription.model.dto.auth.AuthRequest;
import com.cmed.prescription.persistence.entity.UserEntity;
import com.cmed.prescription.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(username);
        userEntityOptional.orElseThrow(() -> {
            log.debug("username not found {}", username);
            return new UsernameNotFoundException("User not found with username: " + username);
        });
        UserEntity userEntity = userEntityOptional.get();
        return new AuthUser(userEntity);
    }

    public boolean registerUser(AuthRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Username already exists");
        }

        UserEntity user = new UserEntity();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));

        return userRepository.save(user);
    }
}
