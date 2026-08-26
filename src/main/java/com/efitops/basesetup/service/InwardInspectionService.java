package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.InwardInspectionResponseDTO;
import com.efitops.basesetup.dto.InwardInspectionDTO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface InwardInspectionService {

	InwardInspectionResponseDTO getInwardInspectionById(Long id) throws ApplicationException;

	List<InwardInspectionResponseDTO> getInwardInspectionByOrgId(Long orgId, Long branch) throws ApplicationException;

	Map<String, Object> createUpdateInwardInspection(InwardInspectionDTO inwardInspectionDTO, MultipartFile[] files)
			throws ApplicationException;

	ResponseEntity<byte[]> viewInwardInspectionFile(HttpServletRequest request) throws IOException;

	String getInwardInspectionDocId(Long orgId, String financialYear);

	List<Map<String, Object>> getMirnGrnNo(Long orgId, Long branch, Long supplierCode);

	List<Map<String, Object>> getMirnGrnNoItemDetails(Long orgId, Long branch, Long supplierCode,
			String purchaseOrderNo);

}
