package com.phc.identity.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phc.identity.dto.ChangePasswordRequest;
import com.phc.identity.entity.UserIdentity;
import com.phc.identity.exception.UserNotFoundException;
import com.phc.identity.repository.UserIdentityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class UserIdentityServiceImpl implements UserIdentityService{

    @Autowired
    private UserIdentityRepository userIdentityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserIdentity createUserIdentity(UserIdentity userIdentity) {
        return userIdentityRepository.save(userIdentity);
    }

    @Override
    public List<UserIdentity> getAllIdentities() {
        return userIdentityRepository.findAll();
    }

    @Override
    public UserIdentity getById(int id) throws UserNotFoundException {
        return userIdentityRepository.findById(Integer.valueOf(id)).orElseThrow();
    }

    @Override
    public long loadIdentitiesFromJson() {
        System.out.println("Entered loadIdentitiesFromJson");
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<UserIdentity>> typeReference = new TypeReference<List<UserIdentity>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/identities.json");
        List<UserIdentity> users = null;
        long identitiesCount = 0;

        try {
            users = objectMapper.readValue(inputStream, typeReference);
            identitiesCount = users.size();
            System.out.println("No. of identities in the list: " + identitiesCount);
            for(UserIdentity identity: users) {
                identity.setPassword(passwordEncoder.encode(identity.getPassword()));
            }
            userIdentityRepository.saveAll(users);
            System.out.println("Identities Saved!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Finished loadIdentitiesFromJson");
        return identitiesCount;
    }

    /**
     * @param changePasswordRequest
     * @return
     */
    @Override
    public Optional<UserIdentity> changePassword(ChangePasswordRequest changePasswordRequest) {

        UserIdentity existingIdentity = userIdentityRepository.findByUserName(changePasswordRequest.getUserName())
                .orElseThrow(()-> new UsernameNotFoundException("userName not found" + changePasswordRequest.getUserName()));
        //validate old password
        existingIdentity.setPassword(changePasswordRequest.getNewPassword());
        final UserIdentity updatedIdentity = userIdentityRepository.saveAndFlush(existingIdentity);
        return Optional.ofNullable(updatedIdentity);
    }

    /**
     * @param userIdentity
     * @return
     */
    @Override
    public Optional<UserIdentity> updateIdentity(UserIdentity userIdentity) {
        UserIdentity existingIdentity = userIdentityRepository.findByUserName(userIdentity.getUserName())
                .orElseThrow(()-> new UsernameNotFoundException("userName not found" + userIdentity.getUserName()));
        existingIdentity.setActive(userIdentity.isActive());
        existingIdentity.setEmail(userIdentity.getEmail());
        final UserIdentity updatedIdentity = userIdentityRepository.saveAndFlush(existingIdentity);
        return Optional.ofNullable(updatedIdentity);
    }

    /**
     * @param userIdentity
     * @return
     */
    @Override
    public Boolean deleteIdentity(UserIdentity userIdentity) {
        UserIdentity existingIdentity = userIdentityRepository.findByUserName(userIdentity.getUserName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found for " + userIdentity.getUserName()));
        userIdentityRepository.delete(existingIdentity);
        return Boolean.TRUE;
    }
}
