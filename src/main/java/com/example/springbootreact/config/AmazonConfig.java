package com.example.springbootreact.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация для подключения к Amazon
 */
@Configuration
public class AmazonConfig {

    @Bean
    public AmazonS3 s3() {
        AWSCredentials awsCredentials = new BasicAWSCredentials("AKIAWORYAJENSQBKKYMT", "munU6YY2weefr4jdq1zq+E6C7VltukYZ+EYE0Bm+");

        return AmazonS3ClientBuilder.standard()
                                    .withRegion("eu-north-1")
                                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                                    .build();
    }
}
