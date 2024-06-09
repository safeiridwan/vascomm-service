package com.vascomm.util;

import com.vascomm.entity.User;
import com.vascomm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationFacade {
    private final UserRepository userRepository;
    public String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }

        return null;
    }

    public String getRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            String userId = ((UserDetails) authentication.getPrincipal()).getUsername();
            User user = userRepository.findByUserId(userId);
            if (user == null) {
                return null;
            }

            return user.getRole();
        }

        return null;
    }
}
