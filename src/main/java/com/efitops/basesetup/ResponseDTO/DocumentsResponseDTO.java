package com.efitops.basesetup.ResponseDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentsResponseDTO {
    private Long id;
    private String drawing;
    private String partNo;
    private String issue;
    private String remarks;
}