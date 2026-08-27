package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DirectPurchaseFileUploadDetailsResponseDTO {

    private Long id;
    private String fileName;
    private String filePath;
    private String fileType;
    private Long fileSize;
//    private String uploadedBy;
}