package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.ResponseDTO.SupplierRateContractResponseDTO;
import com.efitops.basesetup.dto.SupplierRateContractDTO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface SubContractService {

	Map<String, Object> createUpdateSupplierRateContract(SupplierRateContractDTO supplierRateContractDTO) throws ApplicationException;

	List<Map<String, Object>> getCustomerForSupplierRateContract(Long orgId, Long branch);

	List<Map<String, Object>> getServiceForSupplierRateContract(Long orgId, Long branch);

	SupplierRateContractResponseDTO getSupplierRateContractById(Long id) throws ApplicationException;

	List<SupplierRateContractResponseDTO> getSupplierRateContractByOrgIdAndBranch(Long orgId, Long branch) throws ApplicationException;

	String getSupplierRateContractDocId(Long orgId, String financialYear, String screenCode);

	List<Map<String, Object>> getSupplierRateContractItemDropdown(Long orgId, Long branch);



}
