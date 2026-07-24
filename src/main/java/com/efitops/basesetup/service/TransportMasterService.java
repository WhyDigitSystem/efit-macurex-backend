package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.efitops.basesetup.dto.GSTRateMasterDTO;
import com.efitops.basesetup.dto.ListOfValuesDTO;
import com.efitops.basesetup.dto.ServiceAccMasterDTO;
import com.efitops.basesetup.entity.GSTRateMasterVO;
import com.efitops.basesetup.entity.ListOfValuesVO;
import com.efitops.basesetup.entity.ServiceAccMasterVO;
import com.efitops.basesetup.exception.ApplicationException;

public interface TransportMasterService {

	Map<String, Object> updateCreateListOfValues(@Valid ListOfValuesDTO dto) throws ApplicationException;

	ListOfValuesVO getListOfValuesById(Long id);

	List<ListOfValuesVO> getListOfValuesByOrgId(Long orgId, Long branchCode);

	Map<String, Object> updateCreateGSTRateMaster(@Valid GSTRateMasterDTO gSTRateMasterDTO) throws ApplicationException;

	GSTRateMasterVO getGSTRateMasterById(Long id) throws ApplicationException;

	List<GSTRateMasterVO> getGSTRateByOrgId(Long orgId, Long branchId) throws ApplicationException;

	Map<String, Object> updateCreateServiceAccMaster(@Valid ServiceAccMasterDTO serviceAccMasterDTO)
			throws ApplicationException;

	ServiceAccMasterVO getServiceNameById(Long id) throws ApplicationException;

	List<ServiceAccMasterVO> getServiceNameByOrgId(Long orgId, Long branchId) throws ApplicationException;



	 


}
