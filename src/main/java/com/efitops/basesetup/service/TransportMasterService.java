package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.efitops.basesetup.dto.CustomerComplaintDTO;
import com.efitops.basesetup.dto.CustomerComplaintResponseDTO;
import com.efitops.basesetup.dto.DailyExchangeRateDTO;
import com.efitops.basesetup.entity.CustomerComplaintEntryVO;
import com.efitops.basesetup.entity.DailyExchangeRateVO;
import com.efitops.basesetup.exception.ApplicationException;


public interface TransportMasterService {

	Map<String, Object> updateCreateCustomerComplaint(CustomerComplaintDTO customerComplaintDTO) throws ApplicationException;

	CustomerComplaintResponseDTO getCustomerComplaintById(Long id) throws ApplicationException;

	List<CustomerComplaintResponseDTO> getCustomerComplaintByOrgId(Long orgId, Long branch) throws ApplicationException;


	// dropdown for preparedby
	Map<String, Object> getPreparedBy(Long departmentId)
	        throws ApplicationException;

	//dropdown for item	
	Map<String, Object> getItem() throws ApplicationException;

	Map<String, Object> getItemDetails(Long itemId) throws ApplicationException;
   
	//dropdown for branch
	Map<String, Object> getBranch() throws ApplicationException;

	Map<String, Object> getTypeDropdown() throws ApplicationException;
	
	//department dropdown
	Map<String, Object> getDepartment() throws ApplicationException;

	//customer dropdown
	Map<String, Object> getCustomerDetails(String customerId) throws ApplicationException;

	Map<String, Object> getCustomer() throws ApplicationException;
	
	
	
	 


}
