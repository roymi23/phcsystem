package com.phc.identity.repository;

import com.phc.identity.entity.UserIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserIdentityRepository extends JpaRepository<UserIdentity, Integer> {

    Optional<UserIdentity> findByUserName(String userName);
}
