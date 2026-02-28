package com.fileupload2.fileupload2.controller;

import com.fileupload2.fileupload2.service.StorageService;
import com.fileupload2.fileupload2.dto.PresignResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;


@RestController
@RequestMapping("/presign")

public class PresignController {
    private final StorageService storageService;

    public PresignController(StorageService storageService) {
        this.storageService = storageService;
    }
    
    public record UploadRequest(String fileName, String contentType) {
    }

    @PostMapping("/upload")
    public PresignResponse presignUpload(@RequestBody UploadRequest req) {
        return storageService.generateUploadUrl(req.fileName(), req.contentType());
    }

    @GetMapping("/download/{key}")
    public String presignDownload(@PathVariable String key) {
        return storageService.generateDownloadUrl(key);
    }
    
}
