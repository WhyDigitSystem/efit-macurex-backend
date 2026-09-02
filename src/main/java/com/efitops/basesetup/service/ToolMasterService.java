package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.ToolMasterResponseDTO;
import com.efitops.basesetup.dto.ToolMasterDTO;
import com.efitops.basesetup.exception.ApplicationException;

public interface ToolMasterService {

	Map<String, Object> updateCreateToolMaster(ToolMasterDTO toolMasterDTO, MultipartFile[] files)
			throws ApplicationException;

	ToolMasterResponseDTO getToolMasterById(Long id) throws ApplicationException;

	List<ToolMasterResponseDTO> getToolMasterByOrgId(Long orgId, Long branch) throws ApplicationException;

	List<Map<String, Object>> getLocationForToolMaster(Long orgId, Long branchId) throws ApplicationException;

}
