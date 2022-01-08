package com.example.springbootreact.profile;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер для модели {@link UserProfile}
 */
@RestController
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("api/user-profile")
    public List<UserProfile> getUserProfiles() {
        return userProfileService.getUserProfiles();
    }

    @PostMapping(path = "api/user-profile/{userUuid}/image/upload",
                 consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public void uploadUserProfileImage(@PathVariable("userUuid") UUID userUuid,
                                       @RequestParam("file") MultipartFile file) {
        userProfileService.uploadUserProfileImage(userUuid, file);
    }

    @GetMapping("api/user-profile/{userUuid}/image/download")
    public byte[] downloadUserProfileImage(@PathVariable("userUuid") UUID userUuid ) {
        return userProfileService.downloadUserProfileImage(userUuid);
    }

}
