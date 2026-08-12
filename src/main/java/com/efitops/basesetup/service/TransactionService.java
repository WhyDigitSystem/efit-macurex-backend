package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.ResponseDTO.SalesContractAmdResponseDTO;
import com.efitops.basesetup.dto.SalesContractAmendmentDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleResponseDTO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface TransactionService {
	
	//salesdeliveryschedule
	
		Map<String, Object> createUpdateSalesDeliverySchedule(
		        SalesDeliveryScheduleDTO salesDeliveryScheduleDTO)
		        throws ApplicationException;

		SalesDeliveryScheduleResponseDTO getSalesDeliveryScheduleById(Long id)
		        throws ApplicationException;

		List<SalesDeliveryScheduleResponseDTO> getAllSalesDeliverySchedule(
		        Long orgId,
		        Long branchId)
		        throws ApplicationException;
		
		Map<String, Object> getContractNo(Long orgId, Long branch)
		        throws ApplicationException;

		Map<String, Object> getSalesDeliveryScheduleByItemDropdown(String docId, Long orgId, Long branch)
		        throws ApplicationException;


		Map<String, Object> getAllCustomerDetails(Long orgId, Long branch) throws ApplicationException;

//		Map<String, Object> getContractNo() throws ApplicationException;

		List<SalesContractAmdResponseDTO> getSalesContractAmendmentByOrgId(Long orgId, Long branch) throws ApplicationException;

		SalesContractAmdResponseDTO getSalesContractAmendmentById(Long id) throws ApplicationException;

		Map<String, Object> updateCreateSalesContractAmendment(SalesContractAmendmentDTO salesContractAmendmentDTO) throws ApplicationException;

		Map<String, Object> getSalesContractAmdContractNoDropdown(Long orgId, Long branch) throws ApplicationException;

		Map<String, Object> getSalesContractAmdItemDropdown(String salesContractNo, Long orgId, Long branch) throws ApplicationException;

		Map<String, Object> getSalesContractAmdRevisionNo(String salesContractNo, Long item, Long orgId, Long branch) throws ApplicationException;

		

}
