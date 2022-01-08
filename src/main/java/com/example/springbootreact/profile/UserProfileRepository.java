package com.example.springbootreact.profile;

import com.example.springbootreact.datastore.FakeUserProfileData;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для модели {@link UserProfile}
 */
@Repository
public class UserProfileRepository {

    private final FakeUserProfileData fakeUserProfileData;

    public UserProfileRepository(FakeUserProfileData fakeUserProfileData) {
        this.fakeUserProfileData = fakeUserProfileData;
    }

     public List<UserProfile> getUserProfiles() {
        return fakeUserProfileData.getUserProfiles();
     }
}
