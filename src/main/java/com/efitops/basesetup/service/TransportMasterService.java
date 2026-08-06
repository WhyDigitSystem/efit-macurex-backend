package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.DespatchInstructionResponseDTO;
import com.efitops.basesetup.ResponseDTO.DocketInvoiceResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractAmdResponseDTO;
import com.efitops.basesetup.dto.CustomerComplaintDTO;
import com.efitops.basesetup.dto.CustomerComplaintResponseDTO;
import com.efitops.basesetup.dto.DailyExchangeRateDTO;
import com.efitops.basesetup.dto.DespatchInstructionDTO;
import com.efitops.basesetup.dto.DocketInvoiceDTO;
import com.efitops.basesetup.dto.SalesContractAmendmentDTO;
import com.efitops.basesetup.entity.CustomerComplaintEntryVO;
import com.efitops.basesetup.entity.DailyExchangeRateVO;
import com.efitops.basesetup.exception.ApplicationException;


public interface TransportMasterService {

	Map<String, Object> updateCreateCustomerComplaint(
	        CustomerComplaintDTO customerComplaintDTO,
	        MultipartFile[] images)
	        throws ApplicationException;

	CustomerComplaintResponseDTO getCustomerComplaintById(Long id) throws ApplicationException;

	List<CustomerComplaintResponseDTO> getCustomerComplaintByOrgId(Long orgId, Long branch) throws ApplicationException;


	// dropdown for preparedby
	Map<String, Object> getPreparedBy(Long departmentId)
	        throws ApplicationException;

	//dropdown for item	

	Map<String, Object> getItemDetailsforCustomerComplaint(Long orgId, Long branch) throws ApplicationException;
   
	//dropdown for branch
	Map<String, Object> getBranch() throws ApplicationException;

	Map<String, Object> getTypeDropdown() throws ApplicationException;
	
	//department dropdown
	Map<String, Object> getDepartment() throws ApplicationException;

	//customer dropdown
	Map<String, Object> getCustomerDetails(String customerId) throws ApplicationException;

	Map<String, Object> getCustomer() throws ApplicationException;
	
	//SalesContractAmendment
	Map<String, Object> updateCreateSalesContractAmendment(
			SalesContractAmendmentDTO salesContractAmendmentDTO)
			throws ApplicationException;

	SalesContractAmdResponseDTO getSalesContractAmendmentById(Long id)
			throws ApplicationException;

	List<SalesContractAmdResponseDTO> getSalesContractAmendmentByOrgId(Long orgId,Long branch)
			throws ApplicationException;

	//dropdown for contrcatno
	Map<String, Object> getContractNo() throws ApplicationException;
	
	//despatchinstruction



	Map<String, Object> updateCreateDespatchInstruction(DespatchInstructionDTO despatchInstructionDTO)
			throws ApplicationException;

	DespatchInstructionResponseDTO getDespatchInstructionById(Long id) throws ApplicationException;

	List<DespatchInstructionResponseDTO> getDespatchInstructionByOrgId(Long orgId, Long branch)
			throws ApplicationException;
	
	//Docket Invoice
	Map<String, Object> updateCreateDocketInvoice(DocketInvoiceDTO docketInvoiceDTO) throws ApplicationException;

	DocketInvoiceResponseDTO getDocketInvoiceById(Long id) throws ApplicationException;

	List<DocketInvoiceResponseDTO> getDocketInvoiceByOrgId(Long orgId, Long branch) throws ApplicationException;

	


	
	 


}
