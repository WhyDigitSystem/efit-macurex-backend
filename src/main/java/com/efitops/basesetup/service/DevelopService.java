package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.DocumentTypeMappingDTO;
import com.efitops.basesetup.dto.EnquiryDTO;
import com.efitops.basesetup.dto.EnquiryResponseDTO;
import com.efitops.basesetup.dto.SalesZoneMasterDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingVO;
import com.efitops.basesetup.entity.EnquiryVO;
import com.efitops.basesetup.entity.SalesZoneMasterVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface DevelopService {


	EnquiryVO getEnquiryById(Long id) throws ApplicationException;

	List<EnquiryVO> getEnquiryByOrgId(Long orgId, Long branchId) throws ApplicationException;

	Map<String, Object> uploadEnquiryAttachment(Long enquiryId, MultipartFile file) throws ApplicationException;

	ResponseEntity<byte[]> viewEnquiryAttachment(Long attachmentId) throws ApplicationException;

	Map<String, Object> updateCreateEnquiry(EnquiryDTO enquiryDTO) throws ApplicationException;
	
	//enquiry
	
	
	
	
	
	
}
