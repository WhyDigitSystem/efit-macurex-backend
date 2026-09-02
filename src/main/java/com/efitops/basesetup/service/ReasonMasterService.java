package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import com.efitops.basesetup.ResponseDTO.ReasonMasterResponseDTO;
import com.efitops.basesetup.dto.ReasonMasterDTO;
import com.efitops.basesetup.exception.ApplicationException;

public interface ReasonMasterService {

	Map<String, Object> createUpdateReasonMaster(ReasonMasterDTO reasonMasterDTO) throws ApplicationException;

	ReasonMasterResponseDTO getReasonMasterById(Long id) throws ApplicationException;

	List<ReasonMasterResponseDTO> getReasonMasterByOrgId(Long orgId) throws ApplicationException;

}
