package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.efitops.basesetup.ResponseDTO.CustomerResponseDTO;
import com.efitops.basesetup.dto.CustomerDTO;
import com.efitops.basesetup.exception.ApplicationException;

public interface PartyMasterService {


//	Object getPartyMasterByOrgId(Long orgId, Long branch);
//
//	PartyMasterVO getPartyMasterById(Long id);
//

	Map<String, Object> createUpdateCustomer(@Valid CustomerDTO customerDTO) throws ApplicationException;

	CustomerResponseDTO getCustomerById(Long id) throws ApplicationException;

	List<CustomerResponseDTO> getCustomerByOrgIdAndBranch(Long orgId, Long branch) throws ApplicationException;

}
