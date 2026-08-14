package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.ResponseDTO.ProformaInvoiceResponseDTO;
import com.efitops.basesetup.ResponseDTO.RejectionInvoiceResponseDTO;
import com.efitops.basesetup.dto.ProformaInvoiceDTO;
import com.efitops.basesetup.dto.RejectionInvoiceDTO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface RejectionInvoiceService {

	RejectionInvoiceResponseDTO getRejectionInvoiceById(Long id) throws ApplicationException;

	List<RejectionInvoiceResponseDTO> getRejectionInvoiceByOrgId(Long orgId, Long branch) throws ApplicationException;

	Map<String, Object> createUpdateRejectionInvoice(RejectionInvoiceDTO rejectionInvoiceDTO)
			throws ApplicationException;

	String getRejectionInvoiceDocId(Long orgId, String screenCode);

	
	//ProfomaInvoice

	ProformaInvoiceResponseDTO getProformaInvoiceById(Long id) throws ApplicationException;

	List<ProformaInvoiceResponseDTO> getProformaInvoiceByOrgId(Long orgId, Long branch) throws ApplicationException;

	Map<String, Object> createUpdateProformaInvoice(ProformaInvoiceDTO proformaInvoiceDTO) throws ApplicationException;

	String getProformaInvoiceDocId(Long orgId, String screenCode);

	List<Map<String, Object>> getTaxValue(Long orgId, Long hsn);

	List<Map<String, Object>> getItemDetailsResponse(Long orgId, Long branch);

//	List<Map<String, Object>> getGstState(Long orgId, Long customer);

}
