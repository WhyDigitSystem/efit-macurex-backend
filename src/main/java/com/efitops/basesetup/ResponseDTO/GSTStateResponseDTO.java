package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GSTStateResponseDTO {

	private Long id;
	private String stateCode;
	private String stateName;
	private String gstStateId;

}
