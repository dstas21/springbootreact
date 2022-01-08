package com.example.springbootreact.profile;

import com.example.springbootreact.bucket.BucketName;
import com.example.springbootreact.filestore.FileStore;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.apache.http.entity.ContentType.IMAGE_GIF;
import static org.apache.http.entity.ContentType.IMAGE_JPEG;
import static org.apache.http.entity.ContentType.IMAGE_PNG;

/**
 * Сервис для модели {@link UserProfile}
 */
@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final FileStore fileStore;

    public UserProfileService(UserProfileRepository userProfileRepository,
                              FileStore fileStore) {
        this.userProfileRepository = userProfileRepository;
        this.fileStore = fileStore;
    }

    public List<UserProfile> getUserProfiles() {
        return userProfileRepository.getUserProfiles();
    }

    public void uploadUserProfileImage(UUID userUuid,
                                       MultipartFile file) {
        String contentType = file.getContentType();

        isFileEmpty(file);

        isImage(contentType);

        UserProfile user = checkUser(userUuid);

        Map<String, String> metadata = extractMetadata(file, contentType);

        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUuid());
        String fileName = String.format("%s-s%", file.getOriginalFilename(), UUID.randomUUID());

        try {
            fileStore.save(path, fileName, Optional.of(metadata), file.getInputStream());
            user.setImageLink(fileName);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public byte[] downloadUserProfileImage(UUID userUuid) {
        UserProfile user = checkUser(userUuid);
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUuid());
        return user.getImageLink().map(key -> fileStore.download(path, key)).orElse(new byte[0]);
    }

    private Map<String, String> extractMetadata(MultipartFile file,
                                                String contentType) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", contentType);
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    private UserProfile checkUser(UUID userUuid) {
        return userProfileRepository.getUserProfiles()
                                    .stream()
                                    .filter(userProfile -> userProfile.getUuid().equals(userUuid))
                                    .findFirst()
                                    .orElseThrow(() -> new IllegalStateException(
                                            String.format("Пользователь с %s не найден", userUuid)));
    }

    private void isImage(String contentType) {
        if (!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType(), IMAGE_GIF.getMimeType())
                   .contains(contentType)) {
            throw new IllegalStateException("Изображение должно быть формата JPEG, PNG или GIF");
        }
    }

    private void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Невозможно загрузить пустой файл");
        }
    }
}
