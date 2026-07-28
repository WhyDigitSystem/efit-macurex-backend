package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.efitops.basesetup.dto.FinancialYearDTO;
import com.efitops.basesetup.dto.HolidayMasterDTO;
import com.efitops.basesetup.dto.LMEDTO;
import com.efitops.basesetup.dto.LocationDTO;
import com.efitops.basesetup.dto.TSBankDTO;
import com.efitops.basesetup.dto.TaxDefinitionDTO;
import com.efitops.basesetup.entity.FinancialYearVO;
import com.efitops.basesetup.entity.HolidayMasterVO;
import com.efitops.basesetup.entity.LMEVO;
import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.entity.TSBankVO;
import com.efitops.basesetup.entity.TaxDefinitionVO;
import com.efitops.basesetup.exception.ApplicationException;

public interface TransportMasterService {

	//TS bank
	Map<String, Object> createUpdateBankMaster(TSBankDTO tSBankDTO) throws ApplicationException;

	TSBankVO getBankMasterById(Long id) throws ApplicationException;

	List<TSBankVO> getBankMasterByOrgId(Long orgId) throws ApplicationException;
	
	//Tax Definition
	

	TaxDefinitionVO getTaxDefinitionById(Long id) throws ApplicationException;

	List<TaxDefinitionVO> getTaxDefinitionByOrgId(Long orgId,Long branch) throws ApplicationException;

	Map<String, Object> updateCreateTaxDefinition(TaxDefinitionDTO taxDefinitionDTO) throws ApplicationException;

	//Holiday Master
	
	Map<String, Object> updateCreateHolidayMaster(HolidayMasterDTO holidayMasterDTO) throws ApplicationException;

	HolidayMasterVO getHolidayMasterById(Long id);

	List<HolidayMasterVO> getHolidayMasterByOrgId(Long orgId, Long branchId);


	

	
	

	
	

	
	 


}
