package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.DespatchInstructionResponseDTO;
import com.efitops.basesetup.ResponseDTO.DocketInvoiceResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractAmdResponseDTO;
import com.efitops.basesetup.ResponseDTO.StockTransferChallanResponseDTO;
import com.efitops.basesetup.dto.CustomerComplaintDTO;
import com.efitops.basesetup.dto.CustomerComplaintResponseDTO;
import com.efitops.basesetup.dto.DespatchInstructionDTO;
import com.efitops.basesetup.dto.DocketInvoiceDTO;
import com.efitops.basesetup.dto.SalesContractAmendmentDTO;
import com.efitops.basesetup.dto.StockTransferChallanDTO;
import com.efitops.basesetup.exception.ApplicationException;


public interface TransportMasterService {

	Map<String, Object> updateCreateCustomerComplaint(
	        CustomerComplaintDTO customerComplaintDTO,
	        MultipartFile[] images)
	        throws ApplicationException;

	CustomerComplaintResponseDTO getCustomerComplaintById(Long id) throws ApplicationException;

	List<CustomerComplaintResponseDTO> getCustomerComplaintByOrgId(Long orgId, Long branch) throws ApplicationException;


	// dropdown for preparedby
	Map<String, Object> getPreparedBy(Long departmentId, Long branch, Long departmentId2)
	        throws ApplicationException;

	//dropdown for item	
//	Map<String, Object> getItem() throws ApplicationException;

	Map<String, Object> getCustomerComplaintItemDetails(Long itemId, Long branch) throws ApplicationException;
   
	//dropdown for branch
	Map<String, Object> getAllBranch(Long orgId) throws ApplicationException;
	
	//customer dropdown
	Map<String, Object> getCustomerDetails(Long orgId , Long branch) throws ApplicationException;

	
	//SalesContractAmendment
//	Map<String, Object> updateCreateSalesContractAmendment(
//			SalesContractAmendmentDTO salesContractAmendmentDTO)
//			throws ApplicationException;
//
//	SalesContractAmdResponseDTO getSalesContractAmendmentById(Long id)
//			throws ApplicationException;
//
//	List<SalesContractAmdResponseDTO> getSalesContractAmendmentByOrgId(Long orgId,Long branch)
//			throws ApplicationException;
//
//	//dropdown for contrcatno
//	Map<String, Object> getContractNo() throws ApplicationException;
//	
	//despatchinstruction



	Map<String, Object> updateCreateDespatchInstruction(DespatchInstructionDTO despatchInstructionDTO)
			throws ApplicationException;

	DespatchInstructionResponseDTO getDespatchInstructionById(Long id) throws ApplicationException;

	List<DespatchInstructionResponseDTO> getDespatchInstructionByOrgId(Long orgId, Long branch)
			throws ApplicationException;
	
	

	//StockTransferChallan
	Map<String, Object> updateCreateStockTransferChallan(StockTransferChallanDTO stockTransferChallanDTO) throws ApplicationException;

	StockTransferChallanResponseDTO getStockTransferChallanById(Long id) throws ApplicationException;

	List<StockTransferChallanResponseDTO> getStockTransferChallanByOrgId(Long orgId, Long branch) throws ApplicationException;

	//stock transfercustomer
	Map<String, Object> getStockTransferCustomer() throws ApplicationException;

	

	//despatch instruction schedule no dropdown

	Map<String, Object> getDespatchCustomer(Long branch, Long orgId) throws ApplicationException;


	Map<String, Object> getDespatchSalesContract(Long customerId, Long branch, Long orgId) throws ApplicationException;

	Map<String, Object> getDespatchItems(Long branch, Long orgId) throws ApplicationException;

	Map<String, Object> getDespatchScheduleMonth(Long itemId, Long branch, Long orgId) throws ApplicationException;

	Map<String, Object> getDespatchPlannedQty(Long itemId, Long branch, Long orgId) throws ApplicationException;

	Map<String, Object> getScheduleNoDropdownForDespatchInstruction(Long customer, String monthYear,Long branch, Long orgId)
			throws ApplicationException;


//	Map<String, Object> getDespatchPendingQty(Long itemId, String month, Long branch, Long orgId, Long customerId)
//			throws ApplicationException;




	
	 


}
