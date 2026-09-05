package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.ToolMasterResponseDTO;
import com.efitops.basesetup.dto.EngineeringChangeRecordDTO;
import com.efitops.basesetup.dto.EngineeringDeviationRequestDTO;
import com.efitops.basesetup.dto.ToolMasterDTO;
import com.efitops.basesetup.exception.ApplicationException;

public interface ToolMasterService {

	Map<String, Object> updateCreateToolMaster(ToolMasterDTO toolMasterDTO, MultipartFile[] files)
			throws ApplicationException;

	ToolMasterResponseDTO getToolMasterById(Long id) throws ApplicationException;

	List<ToolMasterResponseDTO> getToolMasterByOrgId(Long orgId, Long branch) throws ApplicationException;

	List<Map<String, Object>> getLocationForToolMaster(Long orgId, Long branchId) throws ApplicationException;

//	engineering change recod
	Map<String, Object> updateCreateEngineeringChangeRecord(EngineeringChangeRecordDTO engineeringChangeRecordDTO,
			MultipartFile[] files) throws ApplicationException;

	Map<String, Object> getEngineeringChangeRecordById(Long id) throws ApplicationException;

	

	Map<String, Object> getEngineeringChangeRecordByOrgId(Long orgId, Long branch) throws ApplicationException;

	String getEngineeringChangeRecordDocId(Long orgId, String financialYear);

//engineering deviation request
	
	Map<String, Object> updateCreateEngineeringDeviation(EngineeringDeviationRequestDTO engineeringDeviationRequestDTO,
			MultipartFile[] files) throws Exception;

}
