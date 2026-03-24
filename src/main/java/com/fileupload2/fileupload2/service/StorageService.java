package com.fileupload2.fileupload2.service;
import com.fileupload2.fileupload2.dto.PresignResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.model.*;


import java.util.UUID;
import java.time.Duration;

@Service


public class StorageService {
    
    private final S3Presigner s3Presigner;

    private final String bucket;

    public StorageService(S3Presigner s3Presigner, @Value("${storage.bucket}") String bucket) {
        this.s3Presigner = s3Presigner;
        this.bucket = bucket;
    }
    public PresignResponse generateUploadUrl(String fileName, String contentType) {
        String key = UUID.randomUUID() + "-" + fileName;
        PutObjectRequest req = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                .build();
        // create presign request with 5 min expiry time 
        PutObjectPresignRequest presignReq = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(5))
                .putObjectRequest(req)
                .build();
        PresignedPutObjectRequest presignedReq = s3Presigner.presignPutObject(presignReq);
        return new PresignResponse(presignedReq.url().toString(), key);
    }

    // download presign url 
    public String generateDownloadUrl(String key) {

        GetObjectRequest getReq = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(5))
                .getObjectRequest(getReq)
                .build();

        PresignedGetObjectRequest presigned = s3Presigner.presignGetObject(presignRequest);

        return presigned.url().toString();
    }
    
}
