package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListOfVlauesDetailsResponseDTO {
	private Long id;
	private String valueCode;
	private String valueDescription;
}
