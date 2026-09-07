package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobOrderAttachmentResponseDTO {

    private Long id;

    private String name;

    private String fileName;

    private String filePath;

    private Long fileSize;

    private String contentType;

    private LocalDateTime uploadOn;
}
