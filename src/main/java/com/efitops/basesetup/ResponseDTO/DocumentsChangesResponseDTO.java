package com.efitops.basesetup.ResponseDTO;


import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentsChangesResponseDTO {
    private Long id;
    private String sopNo;
    private String stationNo;
    private LocalDate completionDate;
    private String remarks;
}
