package com.efitops.basesetup.ResponseDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettingApprovalResponseDTO {

	private String routeCardNo;
	private String partNo;
	private String partName;
	private String drgNo;
	private String operation;
	List<SettingApprovalDetailsResponseDTO> settingApprovalDetailsResponseDTO;

}
