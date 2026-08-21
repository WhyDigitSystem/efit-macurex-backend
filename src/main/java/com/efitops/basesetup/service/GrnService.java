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

}
