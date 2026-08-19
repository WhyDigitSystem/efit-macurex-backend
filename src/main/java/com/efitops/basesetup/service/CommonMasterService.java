package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.CompanyResponseDTO;
import com.efitops.basesetup.ResponseDTO.DocumentTypeMappingResponseDTO;
import com.efitops.basesetup.ResponseDTO.GSTRateMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.MappingOfPartyToAccResponseDTO;
import com.efitops.basesetup.dto.BranchDTO;
import com.efitops.basesetup.dto.CityDTO;
import com.efitops.basesetup.dto.CompanyDTO;
import com.efitops.basesetup.dto.CountryDTO;
import com.efitops.basesetup.dto.CurrencyDTO;
import com.efitops.basesetup.dto.DailyExchangeRateDTO;
import com.efitops.basesetup.dto.DocumentTypeMappingDTO;
import com.efitops.basesetup.dto.DocumentTypeMasterDTO;
import com.efitops.basesetup.dto.FinScreenDTO;
import com.efitops.basesetup.dto.FinancialYearDTO;
import com.efitops.basesetup.dto.GSTRateMasterDTO;
import com.efitops.basesetup.dto.GSTStateMasterDTO;
import com.efitops.basesetup.dto.GradeMasterDTO;
import com.efitops.basesetup.dto.HolidayMasterDTO;
import com.efitops.basesetup.dto.HsnDTO;
import com.efitops.basesetup.dto.LMEDTO;
import com.efitops.basesetup.dto.ListOfValuesDTO;
import com.efitops.basesetup.dto.LocationDTO;
import com.efitops.basesetup.dto.LocationResponseDTO;
import com.efitops.basesetup.dto.MappingOfPartyToAccDTO;
import com.efitops.basesetup.dto.RegionDTO;
import com.efitops.basesetup.dto.SalesZoneMasterDTO;
import com.efitops.basesetup.dto.ScreenNamesDTO;
import com.efitops.basesetup.dto.ServiceAccMasterDTO;
import com.efitops.basesetup.dto.ServiceAccMasterResponseDTO;
import com.efitops.basesetup.dto.StateDTO;
import com.efitops.basesetup.dto.TSBankDTO;
import com.efitops.basesetup.dto.TaxDefinitionDTO;
import com.efitops.basesetup.dto.TaxDefinitionDetailsResponseDTO;
import com.efitops.basesetup.dto.TaxDefinitionMasterResponseDTO;
import com.efitops.basesetup.dto.TransportMasterDTO;
import com.efitops.basesetup.dto.UnitMasterDTO;
import com.efitops.basesetup.dto.UomConversionDTO;
import com.efitops.basesetup.dto.UomConversionResponseDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CityVO;
import com.efitops.basesetup.entity.CompanyVO;
import com.efitops.basesetup.entity.CountryVO;
import com.efitops.basesetup.entity.CurrencyVO;
import com.efitops.basesetup.entity.DailyExchangeRateVO;
import com.efitops.basesetup.entity.DocumentTypeMappingVO;
import com.efitops.basesetup.entity.DocumentTypeMasterVO;
import com.efitops.basesetup.entity.FinancialYearVO;
import com.efitops.basesetup.entity.GSTStateMasterVO;
import com.efitops.basesetup.entity.GradeMasterVO;
import com.efitops.basesetup.entity.HolidayMasterVO;
import com.efitops.basesetup.entity.HsnVO;
import com.efitops.basesetup.entity.LMEVO;
import com.efitops.basesetup.entity.ListOfValuesVO;
import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.entity.RegionVO;
import com.efitops.basesetup.entity.SalesZoneMasterVO;
import com.efitops.basesetup.entity.ScreenNamesVO;
import com.efitops.basesetup.entity.StateVO;
import com.efitops.basesetup.entity.TSBankVO;
import com.efitops.basesetup.entity.TaxDefinitionVO;
import com.efitops.basesetup.entity.TransportMasterVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.entity.UomConversionVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface CommonMasterService {

	// Country

	List<CountryVO> getAllCountry(Long orgid); // Method names should be in camelCase

	Optional<CountryVO> getCountryById(Long countryid);

	Map<String, Object> createUpdateCountry(CountryDTO countryDTO) throws ApplicationException; // Return the created
																								// entity

	void deleteCountry(Long countryid);

	// State

	List<StateVO> getAllgetAllStates(Long orgid);

	Optional<StateVO> getStateById(Long stateid);

	List<StateVO> getStatesByCountry(Long orgid, Long country);

	Map<String, Object> createUpdateState(StateDTO stateDTO) throws ApplicationException;

	void deleteState(Long stateid);

	// city

	List<CityVO> getAllgetAllCities(Long orgid);

	List<CityVO> getAllCitiesByState(Long orgid, Long state);

	Optional<CityVO> getCityById(Long cityid);

	Map<String, Object> createUpdateCity(CityDTO cityDTO) throws ApplicationException;

	void deleteCity(Long cityid);

	// Currency

	List<CurrencyVO> getAllCurrency(Long orgid);

	Optional<CurrencyVO> getCurrencyById(Long currencyid);

	Map<String, Object> createUpdateCurrency(CurrencyDTO currencyDTO) throws ApplicationException;

	void deleteCurrency(Long currencyid);

	// region

	List<RegionVO> getAllRegios();

	List<RegionVO> getAllRegionsByOrgId(Long orgId);

	Optional<RegionVO> getRegionById(Long regionid);

	Map<String, Object> createUpdateRegion(RegionDTO regionDTO) throws ApplicationException;

	void deleteRegion(Long regionid);

	// Company

	List<CompanyVO> getAllCompany();

	List<CompanyVO> getCompanyById(Long companyid);

	CompanyResponseDTO createCompany(CompanyDTO companyDTO) throws Exception;

	CompanyResponseDTO updateCompany(CompanyDTO companyDTO) throws ApplicationException, Exception;

	void deleteCompany(Long companyid);

//	List<Map<String, Object>> getCompanyByOrgId(Long orgId);

	// FINANCIAL YEAR

//	Map<String, Object> createUpdateFinYear(FinancialYearDTO financialYearDTO) throws ApplicationException;

	List<FinancialYearVO> getAllActiveFInYear(Long orgId);

	List<FinancialYearVO> getAllFInYearByOrgId(Long orgId);

	Optional<FinancialYearVO> getAllFInYearById(Long id);

//	FinScreen
	List<ScreenNamesVO> getFinScreenById(Long id);

	ScreenNamesVO updateCreateFinScreen(@Valid FinScreenDTO finScreenDTO) throws ApplicationException;

	List<Map<String, Object>> getAllScreenCode(Long orgId);

	// Screen Names
	Map<String, Object> createUpdateScreenNames(ScreenNamesDTO screenNamesDTO) throws ApplicationException;

	List<ScreenNamesVO> getAllScreenNames();

	ScreenNamesVO getScreenNamesById(Long id) throws ApplicationException;

	List<Map<String, Object>> getAllCurrencyForExRate(Long orgId);

	CompanyVO uploadCompanyLogoInBloob(MultipartFile file, Long id) throws IOException;

	// branch

	Map<String, Object> createUpdateBranch(BranchDTO branchDTO) throws ApplicationException;

	BranchVO getBranchById(Long id) throws ApplicationException;

	List<BranchVO> getBranchByOrgId(Long orgId) throws ApplicationException;

	// transport

	Map<String, Object> updateCreateTransportMaster(TransportMasterDTO transportMasterDTO) throws ApplicationException;

	TransportMasterVO getTransportNameById(Long id) throws ApplicationException;

	List<TransportMasterVO> getTransportNameByOrgId(Long orgId, Long branchCode) throws ApplicationException;

	// listofvalues

	Map<String, Object> updateCreateListOfValues(@Valid ListOfValuesDTO dto) throws ApplicationException;

	ListOfValuesVO getListOfValuesById(Long id);

	List<ListOfValuesVO> getListOfValuesByOrgId(Long orgId, Long branchCode);

	List<Map<String, Object>> getBudgetGroup(Long orgId, String name) throws ApplicationException;

	// gstratemaster

	Map<String, Object> updateCreateGSTRateMaster(@Valid GSTRateMasterDTO gSTRateMasterDTO) throws ApplicationException;

	GSTRateMasterResponseDTO getGSTRateMasterById(Long id) throws ApplicationException;

	List<GSTRateMasterResponseDTO> getGSTRateByOrgId(Long orgId, Long branchId) throws ApplicationException;

	// serviceacc

	Map<String, Object> updateCreateServiceAccMaster(@Valid ServiceAccMasterDTO serviceAccMasterDTO)
			throws ApplicationException;

	// locationmaster

	Map<String, Object> updateCreateLocationMaster(LocationDTO locationDTO) throws ApplicationException;

	LocationResponseDTO getLocationById(Long id) throws ApplicationException;

	List<LocationResponseDTO> getLocationByOrgId(Long orgId, Long branch) throws ApplicationException;

	// LME

	Map<String, Object> updateCreateLMEMaster(LMEDTO lMEDTO) throws ApplicationException;

	LMEVO getLMEById(Long id) throws ApplicationException;

	List<LMEVO> getLMEByOrgId(Long orgId, Long branch) throws ApplicationException;

	// FIN YEAR
	Map<String, Object> createUpdateFinancialYear(FinancialYearDTO financialYearDTO) throws ApplicationException;

	List<FinancialYearVO> getFinancialYearByOrgId(Long orgId) throws ApplicationException;

	FinancialYearVO getFinancialYearById(Long id) throws ApplicationException;

	// HSN

	List<HsnVO> getHsnByOrgId(Long orgId, Long branch);

	Optional<HsnVO> getHSNById(Long hsnId);

	Map<String, Object> createUpdateHSN(HsnDTO hsnDTO) throws ApplicationException;

	// Unit Master

	List<UnitMasterVO> getUnitMasterByOrgId(Long orgId, Long branch);

	Optional<UnitMasterVO> getUnitMasterById(Long id);

	Map<String, Object> createUpdateUnitMaster(UnitMasterDTO unitMasterDTO) throws ApplicationException;

	// uom

	Map<String, Object> createUpdateUomConversion(UomConversionDTO uomConversionDTO) throws ApplicationException;

//	List<UomConversionResponseDTO> getUomConversionByOrgId(Long orgId, Long branch);

	// grademaster

	Map<String, Object> createUpdateGradeMaster(GradeMasterDTO gradeMasterDTO) throws ApplicationException;

	List<GradeMasterVO> getGradeMasterByOrgId(Long orgId, Long branch);

	Optional<GradeMasterVO> getGradeMasterById(Long id);

	// GSTStateMaster

	List<GSTStateMasterVO> getGSTStateMasterByOrgId(Long orgId, Long branch);

	Optional<GSTStateMasterVO> getGSTStateMasterById(Long id);

	Map<String, Object> createUpdateGSTStateMaster(GSTStateMasterDTO gstStateMasterDTO) throws ApplicationException;

	// DocumentTypeMaster

//	List<DocumentTypeMasterVO> getDocumentTypeMasterByOrgId(Long orgId, Long branch);

	DocumentTypeMasterVO getDocumentTypeMasterById(Long id) throws ApplicationException;

	Map<String, Object> createUpdateDocumentTypeMaster(DocumentTypeMasterDTO documentTypeMasterDTO)
			throws ApplicationException;

	// TS bank
	Map<String, Object> createUpdateBankMaster(TSBankDTO tSBankDTO) throws ApplicationException;

	TSBankVO getBankMasterById(Long id) throws ApplicationException;

	List<TSBankVO> getBankMasterByOrgId(Long orgId) throws ApplicationException;

	// Tax Definition

	TaxDefinitionMasterResponseDTO getTaxDefinitionById(Long id) throws ApplicationException;

	List<TaxDefinitionMasterResponseDTO> getTaxDefinitionByOrgId(Long orgId, Long branch) throws ApplicationException;

	Map<String, Object> updateCreateTaxDefinition(TaxDefinitionDTO taxDefinitionDTO) throws ApplicationException;

	// Holiday Master

	Map<String, Object> updateCreateHolidayMaster(HolidayMasterDTO holidayMasterDTO) throws ApplicationException;

	HolidayMasterVO getHolidayMasterById(Long id);

	List<HolidayMasterVO> getHolidayMasterByOrgId(Long orgId, Long branchId);

	// Mapping of party to account

	Map<String, Object> updateCreateMappingOfPartyToAcc(MappingOfPartyToAccDTO mappingOfPartyToAccDTO)
			throws ApplicationException;

	MappingOfPartyToAccResponseDTO getMappingOfPartyToAccById(Long id);

	List<MappingOfPartyToAccResponseDTO> getMappingOfPartyToAccByOrgId(Long orgId, Long branch);

	Map<String, Object> getCustomerCategory(Long orgId) throws ApplicationException;

	Map<String, Object> getParty(Long category, Long orgId, Long branch) throws ApplicationException;

	List<ServiceAccMasterResponseDTO> getServiceAccMasterByOrgId(Long orgId, Long branchId) throws ApplicationException;

	ServiceAccMasterResponseDTO getServiceAccMasterById(Long id) throws ApplicationException;

	// dailyexrate
	Map<String, Object> updateCreateDailyExRate(@Valid DailyExchangeRateDTO dailyExchangeRateDTO)
			throws ApplicationException;

	DailyExchangeRateVO getDailyExRateById(Long id) throws ApplicationException;

	List<DailyExchangeRateVO> getDailyExRateByOrgId(Long orgId, Long branch) throws ApplicationException;

	Map<String, Object> getCurrency(Long orgId) throws ApplicationException;

	// documenttypemapping

	Map<String, Object> updateCreateDocumentTypeMapping(DocumentTypeMappingDTO documentTypeMappingDTO)
			throws ApplicationException;

	DocumentTypeMappingResponseDTO getDocumentTypeMappingById(Long id) throws ApplicationException;

//	List<DocumentTypeMappingResponseDTO> getDocumnentTypeMappingByOrgId(Long orgId, Long branch) throws ApplicationException;

	// saleszonemaster

	Map<String, Object> createUpdateSalesZoneMaster(SalesZoneMasterDTO salesZoneMasterDTO) throws ApplicationException;

	Optional<SalesZoneMasterVO> getSalesZoneMasterById(Long id);

	List<SalesZoneMasterVO> getSalesZoneMasterByOrgId(Long orgId, Long branch);

	UomConversionResponseDTO getUomConversionById(Long id) throws ApplicationException;

	List<UomConversionResponseDTO> getUomConversionByOrgId(Long orgId, Long branchId) throws ApplicationException;

	List<DocumentTypeMasterVO> getAllDocumentTypeMasterByOrgId(Long orgId);

	List<DocumentTypeMappingResponseDTO> getDocumentTypeMappingByOrgId(Long orgId, Long branch)
			throws ApplicationException;

	List<Map<String, Object>> getPendingDocumentTypeMapping(Long orgId, String branch, String branchCode,
			String finYear, String finYearIdentifier);

}
