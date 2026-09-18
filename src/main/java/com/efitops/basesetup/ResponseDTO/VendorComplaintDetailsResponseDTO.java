package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorComplaintDetailsResponseDTO {
	
	private ItemResponse1DTO item;
	
	private String reason;
	

}
