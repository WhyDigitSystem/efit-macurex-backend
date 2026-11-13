package com.efitops.basesetup.ResponseDTO;

import com.efitops.basesetup.dto.ListOfVlauesDetailsResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LocationIssuesResponseDTO {
	
	 private Long id;
	
	 private String locationId;
	 
	 private String locationName;
	 
	 private ListOfVlauesDetailsResponseDTO locationTypeId;


}
