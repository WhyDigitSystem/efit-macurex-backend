package com.efitops.basesetup.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RackMasterDTO {

	private Long id;
	private String rackLocation;
	private String rackNo; 
	private Long orgId;
	private String createdBy;
	private boolean active;
}
