package com.efitops.basesetup.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialTypeDTO {
	private Long id;
	private String materialType;
	private String itemGroup;
	private Long orgId;
	private String createdBy;
//	private boolean active;
	
  List<MaterialTypeDetailsDTO>materialTypeDetailDTO;
}
