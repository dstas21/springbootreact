package com.example.springbootreact.bucket;

/**
 * Для сохранения файлов в корзине Amazon,
 * можно использовать несколько корзин
 */
public enum BucketName {

    PROFILE_IMAGE("springbootreact");

    private final String bucketName;

    BucketName(String bucketName) {this.bucketName = bucketName;}

    public String getBucketName() {
        return bucketName;
    }
}
