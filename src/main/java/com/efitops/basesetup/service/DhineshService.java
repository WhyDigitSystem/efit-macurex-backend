package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import com.efitops.basesetup.ResponseDTO.CustomerDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.QuotationDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractItemDropdownResponseDTO;
import com.efitops.basesetup.dto.SalesContractDTO;
import com.efitops.basesetup.exception.ApplicationException;

public interface DhineshService {

	Map<String, Object> createUpdateSalesContract(SalesContractDTO salesContractDTO) throws ApplicationException;

	List<SalesContractItemDropdownResponseDTO> getFinishedGoodsItems(Long orgId, Long branch) throws ApplicationException;

	List<QuotationDropdownResponseDTO> getQuotationDropdown(String customerCode, String ctype, Long orgId, Long branch,
			String oldQuotationNo, Long recId) throws ApplicationException;

	List<CustomerDropdownResponseDTO> getCustomerDropdown(String ctype, Long orgId, Long branch) throws ApplicationException;

}
