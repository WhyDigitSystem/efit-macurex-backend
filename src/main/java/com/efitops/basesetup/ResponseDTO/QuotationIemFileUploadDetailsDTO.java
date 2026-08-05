package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class QuotationIemFileUploadDetailsDTO {

    private Long id;

    private String name;

    private String fileName;

    private String filePath;

//    private String fileUrl;

    private Long fileSize;

    private LocalDateTime uploadOn;
}
