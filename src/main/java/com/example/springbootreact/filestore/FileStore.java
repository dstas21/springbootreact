package com.example.springbootreact.filestore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

/**
 * Сервис для сохранения файлов и картинок
 */
@Service
public class FileStore {

    private final AmazonS3 amazonS3;

    public FileStore(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    /**
     * Сохранение картинки или файла
     *
     * @param path             имя корзины в котору необходимо сохранить
     * @param fileName         имя файла
     * @param optionalMetadata для различных данных файла, такие размер, разрешение, но не является обязательным
     * @param inputStream      сам файл или картинка
     */
    public void save(String path, String fileName,
                     Optional<Map<String, String>> optionalMetadata,
                     InputStream inputStream) {

        ObjectMetadata metadata = new ObjectMetadata();
        optionalMetadata.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(metadata::addUserMetadata);
            }
        });

        try {
            amazonS3.putObject(path, fileName, inputStream, metadata);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Ошибка при сохранении файла", e);
        }
    }

    public byte[] download(String path, String key) {
        try {
            S3Object object = amazonS3.getObject(path, key);
            return IOUtils.toByteArray(object.getObjectContent());
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Ошибка загрузки с s3", e);
        }
    }
}
