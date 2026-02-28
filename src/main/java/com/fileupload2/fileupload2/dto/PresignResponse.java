package com.fileupload2.fileupload2.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Data


public class PresignResponse {
    private String url;
    private String key;

    }
