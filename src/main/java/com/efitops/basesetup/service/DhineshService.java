package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.CustomerDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.QuotationDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.QuotationItemDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractItemDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractResponseDTO;
import com.efitops.basesetup.dto.SalesContractDTO;
import com.efitops.basesetup.exception.ApplicationException;

public interface DhineshService {

	Map<String, Object> createUpdateSalesContract(SalesContractDTO salesContractDTO, MultipartFile[] files) throws ApplicationException;

	List<SalesContractItemDropdownResponseDTO> getFinishedGoodsItems(Long orgId, Long branch) throws ApplicationException;

	List<QuotationDropdownResponseDTO> getQuotationDropdown(String customerCode, String ctype, Long orgId, Long branch,
			String oldQuotationNo, Long recId) throws ApplicationException;

	List<CustomerDropdownResponseDTO> getCustomerDropdown(String ctype, Long orgId, Long branch) throws ApplicationException;

	List<QuotationItemDropdownResponseDTO> getQuotationItemDropdown(String quotationNo, Long orgId, Long branch) throws ApplicationException;

	SalesContractResponseDTO getSalesContractById(Long id) throws ApplicationException;

	List<SalesContractResponseDTO> getSalesContractByOrgIdAndBranch(Long orgId, Long branch) throws ApplicationException;

	String getSalesContractDocId(Long orgId, String financialYear, String screenCode);

}
