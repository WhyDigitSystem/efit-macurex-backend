package com.efitops.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListOfImageResponseDTO {
	private Long id;
	private String listCode;
	private String listDescription;
	
	List<ListOfImageResponseDetailsDTO>listOfImageResponseDetailsDTO;

}
