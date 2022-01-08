package com.example.springbootreact.datastore;

import com.example.springbootreact.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Необходимые для тестирования функционала пользователи
 */
@Repository
public class FakeUserProfileData {

    private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

    static {
        USER_PROFILES.add(new UserProfile(UUID.fromString("d4be1ed0-0e67-48ac-83e8-051064a93cff"), "junior", null));
        USER_PROFILES.add(new UserProfile(UUID.fromString("d878deed-5a1b-4e13-bf3b-cb78a6d25295"), "middle", null));
    }

    public List<UserProfile> getUserProfiles() {
        return USER_PROFILES;
    }
}
