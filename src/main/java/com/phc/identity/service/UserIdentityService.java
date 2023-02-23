package com.phc.identity.service;

import com.phc.identity.dto.ChangePasswordRequest;
import com.phc.identity.entity.UserIdentity;
import com.phc.identity.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserIdentityService {

    UserIdentity createUserIdentity(UserIdentity userIdentity);

    List<UserIdentity> getAllIdentities();

    UserIdentity getById(int id) throws UserNotFoundException;

    long loadIdentitiesFromJson();

    Optional<UserIdentity> changePassword(ChangePasswordRequest changePasswordRequest);

    Optional<UserIdentity> updateIdentity(UserIdentity userIdentity);

    Boolean deleteIdentity(UserIdentity userIdentity);
}
