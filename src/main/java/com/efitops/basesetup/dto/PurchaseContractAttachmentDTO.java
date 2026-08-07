package com.efitops.basesetup.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  (Purchase Contract Attachment) row shown back to the client after upload.
 * name/fileName/filePath/fileSize/uploadOn are all [SYSTEM SET] on upload — the client only supplies
 * the raw MultipartFile[] on the multipart request (see controller), never these fields directly.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseContractAttachmentDTO {


    private String name;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private LocalDateTime uploadOn;
}