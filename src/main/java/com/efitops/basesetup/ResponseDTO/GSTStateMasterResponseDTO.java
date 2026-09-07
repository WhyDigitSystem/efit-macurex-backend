package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GSTStateMasterResponseDTO {
	private Long id;
	private String gstState;
	private String gstStateCode;
}
