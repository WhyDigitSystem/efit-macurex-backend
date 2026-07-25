package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import com.efitops.basesetup.dto.FinancialYearDTO;
import com.efitops.basesetup.dto.LMEDTO;
import com.efitops.basesetup.dto.LocationDTO;
import com.efitops.basesetup.entity.FinancialYearVO;
import com.efitops.basesetup.entity.LMEVO;
import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.exception.ApplicationException;

public interface TransportMasterService {

	Map<String, Object> updateCreateLocationMaster(LocationDTO locationDTO) throws ApplicationException;

	LocationVO getLocationById(Long id) throws ApplicationException;

	

	List<LocationVO> getLocationByOrgId(Long orgId, Long branch) throws ApplicationException;
	
	//LME

	Map<String, Object> updateCreateLMEMaster(LMEDTO lMEDTO) throws ApplicationException;

	LMEVO getLMEById(Long id) throws ApplicationException;

	List<LMEVO> getLMEByOrgId(Long orgId, Long branch) throws ApplicationException;

	//FIN YEAR
	Map<String, Object> createUpdateFinancialYear(FinancialYearDTO financialYearDTO) throws ApplicationException;


	List<FinancialYearVO> getFinancialYearByOrgId(Long orgId) throws ApplicationException;

	FinancialYearVO getFinancialYearById(Long id) throws ApplicationException;


	

	
	

	
	 


}
