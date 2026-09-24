package com.efitops.basesetup.ResponseDTO;


import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScrapNoteReasonDetailsResponseDTO {
    private Long id;
    private String reasonCode;
    private String reasonDescription;
    private BigDecimal rejQty;
}