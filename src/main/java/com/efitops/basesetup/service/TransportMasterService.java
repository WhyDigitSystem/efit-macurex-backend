package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.efitops.basesetup.dto.FinancialYearDTO;
import com.efitops.basesetup.dto.HolidayMasterDTO;
import com.efitops.basesetup.dto.LMEDTO;
import com.efitops.basesetup.dto.LocationDTO;
import com.efitops.basesetup.dto.MappingOfPartyToAccDTO;
import com.efitops.basesetup.dto.TSBankDTO;
import com.efitops.basesetup.dto.TaxDefinitionDTO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.FinancialYearVO;
import com.efitops.basesetup.entity.HolidayMasterVO;
import com.efitops.basesetup.entity.LMEVO;
import com.efitops.basesetup.entity.ListOfValuesVO;
import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.entity.MappingOfPartyToAccVO;
import com.efitops.basesetup.entity.TSBankVO;
import com.efitops.basesetup.entity.TaxDefinitionVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.PartyProjection;

public interface TransportMasterService {

	//TS bank
	Map<String, Object> createUpdateBankMaster(TSBankDTO tSBankDTO) throws ApplicationException;

	TSBankVO getBankMasterById(Long id) throws ApplicationException;

	List<TSBankVO> getBankMasterByOrgId(Long orgId) throws ApplicationException;
	
	//Tax Definition
	

	TaxDefinitionVO getTaxDefinitionById(Long id) throws ApplicationException;

	List<TaxDefinitionVO> 
	getTaxDefinitionByOrgId(Long orgId,Long branch) throws ApplicationException;

	Map<String, Object> updateCreateTaxDefinition(TaxDefinitionDTO taxDefinitionDTO) throws ApplicationException;

	//Holiday Master
	
	Map<String, Object> updateCreateHolidayMaster(HolidayMasterDTO holidayMasterDTO) throws ApplicationException;

	HolidayMasterVO getHolidayMasterById(Long id);

	List<HolidayMasterVO> getHolidayMasterByOrgId(Long orgId, Long branchId);

	//Mapping of party to account
	
	Map<String, Object> updateCreateMappingOfPartyToAcc(MappingOfPartyToAccDTO mappingOfPartyToAccDTO) throws ApplicationException;

	MappingOfPartyToAccVO getMappingOfPartyToAccById(Long id);

	List<MappingOfPartyToAccVO> getMappingOfPartyToAccByOrgId(Long orgId, Long branch);
	
	Map<String, Object> getCustomerCategory(Long orgId)
	        throws ApplicationException;
	
	Map<String, Object> getParty(Long category,
            Long orgId,
            Long branch)
throws ApplicationException;

	

	

	
	

	
	

	
	 


}
