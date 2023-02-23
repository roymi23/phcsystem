package com.phc.identity.security;

import com.phc.identity.entity.UserIdentity;
import com.phc.identity.exception.UserNotFoundException;
import com.phc.identity.repository.UserIdentityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Component
public class DbUserDetailsService implements UserDetailsService {

    @Autowired
    private UserIdentityRepository userIdentityRepository;

    public DbUserDetailsService() {
    }

    public DbUserDetailsService(UserIdentityRepository userIdentityRepository) {
        this.userIdentityRepository = userIdentityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserIdentity> userIdentity = userIdentityRepository.findByUserName(username);
        return userIdentity.map(UserIdentityDetail::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found" + username));
    }
}
