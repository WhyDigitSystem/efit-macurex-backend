package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTypeMappingDetailsResponseDTO {

    private Long id;

    private String screenName;

    private String screenCode;

    private String docCode;

    private String prefix;

    private int lastNo;

    private String active;
}