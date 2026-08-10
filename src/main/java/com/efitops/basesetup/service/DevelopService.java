package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.CustomerDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractItemDropdownResponseDTO;
import com.efitops.basesetup.dto.EnquiryDTO;
import com.efitops.basesetup.dto.EnquiryResponseDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleResponseDTO;
import com.efitops.basesetup.dto.SalesReturnDTO;
import com.efitops.basesetup.dto.SalesReturnResponseDTO;
import com.efitops.basesetup.entity.EnquiryVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface DevelopService {


	EnquiryResponseDTO getEnquiryById(Long id) throws ApplicationException;

	List<EnquiryResponseDTO> getEnquiryByOrgId(Long orgId, Long branchId) throws ApplicationException;

//	Map<String, Object> uploadEnquiryAttachment(Long enquiryId, MultipartFile file) throws ApplicationException;
//
//	ResponseEntity<byte[]> viewEnquiryAttachment(Long attachmentId) throws ApplicationException;

	Map<String, Object> updateCreateEnquiry(EnquiryDTO enquiryDTO, MultipartFile[] files) throws ApplicationException;
	

	
	//SALES RETURN

	Map<String, Object> createUpdateSalesReturn(SalesReturnDTO salesReturnDTO) throws ApplicationException;

	SalesReturnResponseDTO getSalesReturnById(Long id)  throws ApplicationException;


	List<SalesReturnResponseDTO> getAllSalesReturn(Long orgId, Long branch)
	        throws ApplicationException;
	
	
	
	
	

	
}
	
	
	
	
	
	

