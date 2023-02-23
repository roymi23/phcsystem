package com.phc.identity.security;

import com.phc.identity.entity.UserIdentity;
import com.phc.identity.repository.UserIdentityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

public class DbUserIdentityManager implements UserDetailsManager {

    @Autowired
    private UserIdentityRepository userIdentityRepository;

    @Override
    public void createUser(UserDetails user) {


    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<UserIdentity> userIdentity = userIdentityRepository.findByUserName(userName);
        return userIdentity.map(UserIdentityDetail::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found" + userName));
    }
}
