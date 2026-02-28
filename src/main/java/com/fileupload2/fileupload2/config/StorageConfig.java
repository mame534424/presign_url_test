package com.fileupload2.fileupload2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;


import java.net.URI;

@Configuration
public class StorageConfig {
    @Value("${storage.s3.endpoint}")
    private String endpoint;

    @Value("${storage.s3.region}")
    private String region;

    @Value("${storage.s3.accessKey}")
    private String accessKey;

    @Value("${storage.s3.secretKey}")
    private String secretKey;

    @Bean
    public S3Presigner s3Presigner(){
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        S3Configuration serviceConfiguration = S3Configuration.builder()
                .pathStyleAccessEnabled(true)
                .build();
        return S3Presigner.builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .serviceConfiguration(serviceConfiguration)
                .build();
    }

}
