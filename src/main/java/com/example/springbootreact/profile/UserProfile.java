package com.example.springbootreact.profile;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Модель профиля пользователя
 */
public class UserProfile {

    private UUID uuid;
    private String userName;
    /**
     * Ссылка на изображения пользователя в s3, не обязательное поле
     */
    private String imageLink;

    public UserProfile(UUID uuid,
                       String userName,
                       String imageLink) {
        this.uuid = uuid;
        this.userName = userName;
        this.imageLink = imageLink;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Optional<String> getImageLink() {
        return Optional.ofNullable(imageLink);
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(uuid, that.uuid) &&
               Objects.equals(userName, that.userName) &&
               Objects.equals(imageLink, that.imageLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, userName, imageLink);
    }
}
