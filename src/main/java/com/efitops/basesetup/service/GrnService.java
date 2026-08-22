package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.GrnResponseDTO;
import com.efitops.basesetup.dto.GrnDTO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface GrnService {

	GrnResponseDTO getGrnById(Long id) throws ApplicationException;

	List<GrnResponseDTO> getGrnByOrgId(Long orgId, Long branch) throws ApplicationException;

	Map<String, Object> createUpdateGrn(GrnDTO grnDTO, MultipartFile[] files) throws ApplicationException;

	ResponseEntity<byte[]> viewGrnFile(HttpServletRequest request) throws IOException;

	List<Map<String, Object>> getSupplierDetailsForGrn(Long orgId, Long branch);

	String getGrnDocId(Long orgId, String financialYear, String screenCode);

	List<Map<String, Object>> getGatePassDocId(Long orgId, Long branch, Long supplierCode);

	List<Map<String, Object>> getPurchaseOrderNoBasedDocId(Long orgId, Long branch, Long supplierCode, String gatePass);

	List<Map<String, Object>> getScheduleDocId(Long orgId, String purchaseOrderNo, String date, String gatePass);

	List<Map<String, Object>> getPoNmberBasedItemDetails(Long orgId, Long branch, String purchaseOrderNo);

}
