package com.opensales.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.opensales.app.domain.model.User;
import com.opensales.app.domain.model.UserProfile;

@Repository
public interface UserProfileRepository  extends JpaRepository<UserProfile,Long> {
    UserProfile findByUserProfileId(User profileId); 
}
