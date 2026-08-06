package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.QuotationDTO;
import com.efitops.basesetup.dto.QuotationResponseDTO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface QuotationService {

//	Map<String, Object> updateCreateQuotation(QuotationDTO quotationDTO) throws ApplicationException;

//	Map<String, Object> createUpdateQuotationImages(MultipartFile[] files, String docId, String screenName,
//			String module, List<String> fileNames) throws ApplicationException, IOException;
//
//	ResponseEntity<byte[]> viewQuotationImages(HttpServletRequest request) throws IOException;

	QuotationResponseDTO getQuotationById(Long id) throws ApplicationException;

	List<QuotationResponseDTO> getQuotationByOrgId(Long orgId, Long branchId) throws ApplicationException;

	Map<String, Object> createUpdateQuotation(QuotationDTO quotationDTO, MultipartFile[] files) throws ApplicationException;

}
