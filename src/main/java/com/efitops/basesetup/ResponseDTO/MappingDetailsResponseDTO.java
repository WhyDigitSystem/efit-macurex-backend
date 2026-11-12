package com.efitops.basesetup.ResponseDTO;

import com.efitops.basesetup.dto.PartyResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MappingDetailsResponseDTO {

    private Long id;

    private PartyResponseDTO party;

    private String accountName;

}