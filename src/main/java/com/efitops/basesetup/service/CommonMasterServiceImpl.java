package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.CompanyResponseDTO;
import com.efitops.basesetup.dto.BankDetailsDTO;
import com.efitops.basesetup.dto.BranchDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.CityDTO;
import com.efitops.basesetup.dto.CompanyDTO;
import com.efitops.basesetup.dto.CountryDTO;
import com.efitops.basesetup.dto.CurrencyDTO;
import com.efitops.basesetup.dto.DocumentTypeMasterDTO;
import com.efitops.basesetup.dto.FinScreenDTO;
import com.efitops.basesetup.dto.FinancialYearDTO;
import com.efitops.basesetup.dto.GSTRateMasterDTO;
import com.efitops.basesetup.dto.GSTStateMasterDTO;
import com.efitops.basesetup.dto.GradeMasterDTO;
import com.efitops.basesetup.dto.HolidayMasterDTO;
import com.efitops.basesetup.dto.HolidayMasterDetailsDTO;
import com.efitops.basesetup.dto.HsnDTO;
import com.efitops.basesetup.dto.HsnResponseImageDTO;
import com.efitops.basesetup.dto.LMEDTO;
import com.efitops.basesetup.dto.ListOfValuesDTO;
import com.efitops.basesetup.dto.ListOfValuesDetailsDTO;
import com.efitops.basesetup.dto.LocationDTO;
import com.efitops.basesetup.dto.MappingDetailsDTO;
import com.efitops.basesetup.dto.MappingOfPartyToAccDTO;
import com.efitops.basesetup.dto.RegionDTO;
import com.efitops.basesetup.dto.Role;
import com.efitops.basesetup.dto.ScreenNamesDTO;
import com.efitops.basesetup.dto.ServiceAccMasterDTO;
import com.efitops.basesetup.dto.ServiceAccMasterResponseDTO;
import com.efitops.basesetup.dto.StateDTO;
import com.efitops.basesetup.dto.TSBankDTO;
import com.efitops.basesetup.dto.TaxDefinitionDTO;
import com.efitops.basesetup.dto.TaxDefinitionDetailsDTO;
import com.efitops.basesetup.dto.TransportMasterDTO;
import com.efitops.basesetup.dto.UnitMasterDTO;
import com.efitops.basesetup.dto.UomConversionDTO;
import com.efitops.basesetup.entity.BankDetailsVO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CityVO;
import com.efitops.basesetup.entity.CompanyVO;
import com.efitops.basesetup.entity.CountryVO;
import com.efitops.basesetup.entity.CurrencyVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.DocumentTypeMasterVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.FinancialYearVO;
import com.efitops.basesetup.entity.GSTRateMasterVO;
import com.efitops.basesetup.entity.GSTStateMasterVO;
import com.efitops.basesetup.entity.GradeMasterVO;
import com.efitops.basesetup.entity.HolidayMasterDetailsVO;
import com.efitops.basesetup.entity.HolidayMasterVO;
import com.efitops.basesetup.entity.HsnVO;
import com.efitops.basesetup.entity.LMEVO;
import com.efitops.basesetup.entity.ListOfValuesDetailsVO;
import com.efitops.basesetup.entity.ListOfValuesVO;
import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.entity.MappingDetailsVO;
import com.efitops.basesetup.entity.MappingOfPartyToAccVO;
import com.efitops.basesetup.entity.RegionVO;
import com.efitops.basesetup.entity.ScreenNamesVO;
import com.efitops.basesetup.entity.ServiceAccMasterVO;
import com.efitops.basesetup.entity.StateVO;
import com.efitops.basesetup.entity.TSBankVO;
import com.efitops.basesetup.entity.TaxDefinitionDetailsVO;
import com.efitops.basesetup.entity.TaxDefinitionVO;
import com.efitops.basesetup.entity.TransportMasterVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.entity.UomConversionVO;
import com.efitops.basesetup.entity.UserVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BankDetailsRepo;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CityRepo;
import com.efitops.basesetup.repository.CompanyRepo;
import com.efitops.basesetup.repository.CountryRepo;
import com.efitops.basesetup.repository.CurrencyRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.DocumentTypeMasterRepo;
import com.efitops.basesetup.repository.EmployeeMasterRepo;
import com.efitops.basesetup.repository.FinScreenRepo;
import com.efitops.basesetup.repository.FinancialYearRepo;
import com.efitops.basesetup.repository.GSTStateMasterRepo;
import com.efitops.basesetup.repository.GradeMasterRepo;
import com.efitops.basesetup.repository.GstRateMasterRepo;
import com.efitops.basesetup.repository.HolidayMasterDetailsRepo;
import com.efitops.basesetup.repository.HolidayMasterRepo;
import com.efitops.basesetup.repository.HsnRepo;
import com.efitops.basesetup.repository.LMERepo;
import com.efitops.basesetup.repository.ListOfValuesDetailsRepo;
import com.efitops.basesetup.repository.ListOfValuesRepo;
import com.efitops.basesetup.repository.LocationRepo;
import com.efitops.basesetup.repository.MappingDetailsRepo;
import com.efitops.basesetup.repository.MappingPartyToAccRepo;
import com.efitops.basesetup.repository.PartyProjection;
import com.efitops.basesetup.repository.RegionRepo;
import com.efitops.basesetup.repository.ResponsibilitiesRepo;
import com.efitops.basesetup.repository.ScreenNamesRepo;
import com.efitops.basesetup.repository.ServiceAccMasterRepo;
import com.efitops.basesetup.repository.StateRepo;
import com.efitops.basesetup.repository.TSBankRepo;
import com.efitops.basesetup.repository.TaxDefinitionDetailsRepo;
import com.efitops.basesetup.repository.TaxDefinitionRepo;
import com.efitops.basesetup.repository.TransportRepo;
import com.efitops.basesetup.repository.UnitMasterRepo;
import com.efitops.basesetup.repository.UomConversionRepo;
import com.efitops.basesetup.repository.UserRepo;
import com.efitops.basesetup.util.CryptoUtils;

@Service
public class CommonMasterServiceImpl implements CommonMasterService {

	public static final Logger LOGGER = LoggerFactory.getLogger(CommonMasterServiceImpl.class);

	@Autowired
	CountryRepo countryRepo;

	@Autowired
	CurrencyRepo currencyRepo;

	@Autowired
	StateRepo stateRepo;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	CityRepo cityRepo;

	@Autowired
	RegionRepo regionRepo;

	@Autowired
	CompanyRepo companyRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	FinancialYearRepo financialYearRepo;

	@Autowired
	ResponsibilitiesRepo responsibilitiesRepo;

	@Autowired
	FinScreenRepo finScreenRepo;

	@Autowired
	ScreenNamesRepo screenNamesRepo;

	@Autowired
	BankDetailsRepo bankDetailsRepo;

	@Autowired
	EmployeeMasterRepo employeeRepo;

	@Autowired
	BranchRepo branchRepo;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	TransportRepo transportRepo;

	@Autowired
	ListOfValuesRepo listOfValuesRepo;

	@Autowired
	ListOfValuesDetailsRepo listOfValuesDetailsRepo;

	@Autowired
	GstRateMasterRepo gstRateMasterRepo;

	@Autowired
	ServiceAccMasterRepo serviceAccMasterRepo;

	@Autowired
	LocationRepo locationRepo;

	@Autowired
	LMERepo lMERepo;

	@Autowired
	private HsnRepo hsnRepo;

	@Autowired
	private UnitMasterRepo unitMasterRepo;

	@Autowired
	private UomConversionRepo uomConversionRepo;

	@Autowired
	private GradeMasterRepo gradeMasterRepo;

	@Autowired
	private GSTStateMasterRepo gstStateMasterRepo;

	@Autowired
	private DocumentTypeMasterRepo documentTypeMasterRepo;

	@Autowired
	private DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	TSBankRepo tSBankRepo;

	@Autowired
	TaxDefinitionRepo taxDefinitionRepo;

	@Autowired
	TaxDefinitionDetailsRepo taxDefinitionDetailsRepo;

	@Autowired
	HolidayMasterRepo holidayMasterRepo;

	@Autowired
	HolidayMasterDetailsRepo holidayMaterDetailsRepo;

	@Autowired
	MappingPartyToAccRepo mappingPartyToAccRepo;

	@Autowired
	MappingDetailsRepo mappingDetailsRepo;

	@Autowired
	CustomerRepo customerRepo;

	// Company

	@Override
	public List<CompanyVO> getAllCompany() {
		return companyRepo.findAll();
	}

	@Override
	public List<CompanyVO> getCompanyById(Long companyid) {
		return companyRepo.findByCompany(companyid);
	}

	@Override
	@Transactional
	public CompanyResponseDTO createCompany(CompanyDTO companyDTO) throws Exception {

		validateCreateCompany(companyDTO);

		CompanyVO companyVO = new CompanyVO();
		mapCreateCompanyDTOToVO(companyVO, companyDTO);
		companyRepo.save(companyVO);

		EmployeeMasterVO employeeVO = new EmployeeMasterVO();
		employeeVO.setEmployeeName(companyVO.getAdminName());
//		employeeVO.setEmployeeCode(companyVO.getCompanyCode());
		employeeVO.setActive(true);
		employeeVO.setOrgId(companyVO.getId());
		employeeRepo.save(employeeVO);

		UserVO userVO = new UserVO();
		userVO.setUserName(companyVO.getAdminName());
		userVO.setEmployeeMaster(employeeVO);
		// userVO.setEmployeeCode(companyVO.getCompanyCode());
		userVO.setEmail(companyVO.getAdminEmail());
		userVO.setMobileNo(companyVO.getAdminMobileNo());
		userVO.setRole(Role.ROLE_USER);
		userVO.setUserType("ADMIN");
		userVO.setOrgId(companyVO.getId());
		userVO.setCreatedby(companyVO.getCreatedBy());
		userVO.setUpdatedby(companyVO.getCreatedBy());
		userVO.setActive(true);
		userVO.setLoginStatus(false);
		userVO.setCompanyVO(companyVO);

		try {
			userVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(companyDTO.getPassword())));
		} catch (Exception e) {
			throw new ApplicationContextException("Unable To Encode Password");
		}

		userRepo.save(userVO);

		return mapCompanyVOToResponseDTO(companyVO);
	}

	private void validateCreateCompany(CompanyDTO dto) throws ApplicationException {

		if (companyRepo.existsByCompanyCode(dto.getCompanyCode())) {
			throw new ApplicationException("Company Code : " + dto.getCompanyCode() + " already exists.");
		}

		if (companyRepo.existsByCompanyName(dto.getCompanyName())) {
			throw new ApplicationException("Company Name : " + dto.getCompanyName() + " already exists.");
		}

		if (companyRepo.existsByEmail(dto.getAdminEmail())) {
			throw new ApplicationException("Email : " + dto.getAdminEmail() + " already exists.");
		}

		if (companyRepo.existsByPhoneNo(dto.getAdminMobileNo())) {
			throw new ApplicationException("Phone Number : " + dto.getAdminMobileNo() + " already exists.");
		}
	}

	private void mapCreateCompanyDTOToVO(CompanyVO companyVO, CompanyDTO dto) throws ApplicationException {

		companyVO.setCompanyCode(dto.getCompanyCode());
		companyVO.setCompanyName(dto.getCompanyName());
		companyVO.setEmail(dto.getEmail());
		companyVO.setPhoneNo(dto.getPhoneNo());
		companyVO.setPanNo(dto.getPanNo());
		companyVO.setGst(dto.getGst());
		companyVO.setCin(dto.getCin());
		companyVO.setOfficialWebsite(dto.getOfficialWebsite());
		companyVO.setIndustryType(dto.getIndustryType());
		companyVO.setCompanySize(dto.getCompanySize());

		companyVO.setPincode(dto.getPincode());
		companyVO.setCeo(dto.getCeo());
		companyVO.setRegisteredAddress(dto.getRegisteredAddress());

		companyVO.setSelectPlan(dto.getSelectPlan());
		companyVO.setTrialPeriod(dto.getTrialPeriod());
		companyVO.setMaxUsers(dto.getMaxUsers());
		companyVO.setStorageLimit(dto.getStorageLimit());

		companyVO.setAdminName(dto.getAdminName());
		companyVO.setAdminEmail(dto.getAdminEmail());
		companyVO.setAdminMobileNo(dto.getAdminMobileNo());

		companyVO.setCreatedBy(dto.getCreatedBy());
		companyVO.setUpdatedBy(dto.getCreatedBy());

		companyVO.setActive(dto.isActive());

		companyVO.setTermsAndConditions(dto.getTermsAndConditions());

		if (dto.getCountryId() != null && dto.getCountryId() != 0) {
			CountryVO country = countryRepo.findById(dto.getCountryId())
					.orElseThrow(() -> new ApplicationException("Country Not Found"));
			companyVO.setCountry(country);
		}

		if (dto.getStateId() != null && dto.getStateId() != 0) {
			StateVO state = stateRepo.findById(dto.getStateId())
					.orElseThrow(() -> new ApplicationException("State Not Found"));
			companyVO.setState(state);
		}

		if (dto.getCityId() != null && dto.getCityId() != 0) {
			CityVO city = cityRepo.findById(dto.getCityId())
					.orElseThrow(() -> new ApplicationException("City Not Found"));
			companyVO.setCity(city);
		}

		try {
			companyVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(dto.getPassword())));
		} catch (Exception e) {
			throw new ApplicationException("Unable To Encode Password");
		}
	}

	private CompanyResponseDTO mapCompanyVOToResponseDTO(CompanyVO companyVO) {

		CompanyResponseDTO dto = new CompanyResponseDTO();

		dto.setId(companyVO.getId());
		dto.setCompanyCode(companyVO.getCompanyCode());
		dto.setCompanyName(companyVO.getCompanyName());
		dto.setEmail(companyVO.getEmail());
		dto.setPhoneNo(companyVO.getPhoneNo());
		dto.setPanNo(companyVO.getPanNo());
		dto.setGst(companyVO.getGst());
		dto.setCin(companyVO.getCin());
		dto.setOfficialWebsite(companyVO.getOfficialWebsite());
		dto.setIndustryType(companyVO.getIndustryType());
		dto.setCompanySize(companyVO.getCompanySize());

		if (companyVO.getCountry() != null) {
			dto.setCountryId(companyVO.getCountry().getId());
			dto.setCountryName(companyVO.getCountry().getCountryName());
		}

		if (companyVO.getState() != null) {
			dto.setStateId(companyVO.getState().getId());
			dto.setStateName(companyVO.getState().getStateName());
		}

		if (companyVO.getCity() != null) {
			dto.setCityId(companyVO.getCity().getId());
			dto.setCityName(companyVO.getCity().getCityName());
		}

		dto.setPincode(companyVO.getPincode());
		dto.setCeo(companyVO.getCeo());
		dto.setRegisteredAddress(companyVO.getRegisteredAddress());

		dto.setSelectPlan(companyVO.getSelectPlan());
		dto.setTrialPeriod(companyVO.getTrialPeriod());
		dto.setMaxUsers(companyVO.getMaxUsers());
		dto.setStorageLimit(companyVO.getStorageLimit());

		dto.setAdminName(companyVO.getAdminName());
		dto.setAdminEmail(companyVO.getAdminEmail());
		dto.setAdminMobileNo(companyVO.getAdminMobileNo());

		dto.setCompanyLogo(companyVO.getCompanyLogo());

		dto.setCreatedBy(companyVO.getCreatedBy());
		dto.setUpdatedBy(companyVO.getUpdatedBy());

		dto.setTermsAndConditions(companyVO.getTermsAndConditions());
		dto.setCancelRemarks(companyVO.getCancelRemarks());

		dto.setScreenCode(companyVO.getScreenCode());
		dto.setScreenName(companyVO.getScreenName());

		dto.setActive(companyVO.getActive());
		dto.setCancel(companyVO.getCancel());

		dto.setCommonDate(companyVO.getCommonDate());

		return dto;
	}

	@Override
	@Transactional
	public CompanyResponseDTO updateCompany(CompanyDTO companyDTO) throws Exception {

		CompanyVO companyVO = companyRepo.findById(companyDTO.getId())
				.orElseThrow(() -> new ApplicationException("Company Not Found"));

		validateUpdateCompany(companyDTO);

		mapUpdateCompanyDTOToVO(companyVO, companyDTO);

		CompanyVO updatedCompany = companyRepo.save(companyVO);
		return mapCompanyVOToResponseDTO(updatedCompany);
	}

	private void validateUpdateCompany(CompanyDTO dto) throws ApplicationException {

		if (companyRepo.existsByCompanyCodeAndIdNot(dto.getCompanyCode(), dto.getId())) {
			throw new ApplicationException("Company Code : " + dto.getCompanyCode() + " already exists.");
		}

		if (companyRepo.existsByCompanyNameAndIdNot(dto.getCompanyName(), dto.getId())) {
			throw new ApplicationException("Company Name : " + dto.getCompanyName() + " already exists.");
		}

		if (companyRepo.existsByAdminEmailAndIdNot(dto.getAdminEmail(), dto.getId())) {
			throw new ApplicationException("Email : " + dto.getAdminEmail() + " already exists.");
		}

		if (companyRepo.existsByAdminMobileNoAndIdNot(dto.getAdminMobileNo(), dto.getId())) {
			throw new ApplicationException("Phone Number : " + dto.getAdminMobileNo() + " already exists.");
		}
	}

	private void mapUpdateCompanyDTOToVO(CompanyVO companyVO, CompanyDTO dto) throws ApplicationException {

		companyVO.setCompanyCode(dto.getCompanyCode());
		companyVO.setCompanyName(dto.getCompanyName());

		companyVO.setEmail(dto.getEmail());
		companyVO.setPhoneNo(dto.getPhoneNo());

		companyVO.setPanNo(dto.getPanNo());
		companyVO.setGst(dto.getGst());
		companyVO.setCin(dto.getCin());

		companyVO.setOfficialWebsite(dto.getOfficialWebsite());
		companyVO.setIndustryType(dto.getIndustryType());
		companyVO.setCompanySize(dto.getCompanySize());

		companyVO.setPincode(dto.getPincode());
		companyVO.setCeo(dto.getCeo());
		companyVO.setRegisteredAddress(dto.getRegisteredAddress());

		companyVO.setSelectPlan(dto.getSelectPlan());
		companyVO.setTrialPeriod(dto.getTrialPeriod());
		companyVO.setMaxUsers(dto.getMaxUsers());
		companyVO.setStorageLimit(dto.getStorageLimit());

		companyVO.setAdminName(dto.getAdminName());
		companyVO.setAdminEmail(dto.getAdminEmail());
		companyVO.setAdminMobileNo(dto.getAdminMobileNo());

		companyVO.setUpdatedBy(dto.getCreatedBy());

		companyVO.setActive(dto.isActive());

		companyVO.setTermsAndConditions(dto.getTermsAndConditions());

		if (dto.getCountryId() != null && dto.getCountryId() != 0) {
			CountryVO country = countryRepo.findById(dto.getCountryId())
					.orElseThrow(() -> new ApplicationException("Country Not Found"));
			companyVO.setCountry(country);
		}

		if (dto.getStateId() != null && dto.getStateId() != 0) {
			StateVO state = stateRepo.findById(dto.getStateId())
					.orElseThrow(() -> new ApplicationException("State Not Found"));
			companyVO.setState(state);
		}

		if (dto.getCityId() != null && dto.getCityId() != 0) {
			CityVO city = cityRepo.findById(dto.getCityId())
					.orElseThrow(() -> new ApplicationException("City Not Found"));
			companyVO.setCity(city);
		}

		if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
			try {
				companyVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(dto.getPassword())));
			} catch (Exception e) {
				throw new ApplicationException("Unable To Encode Password");
			}
		}
	}

	@Override
	public void deleteCompany(Long companyid) {
		companyRepo.deleteById(companyid);
	}

//	@Override
//	public CompanyVO saveImage(MultipartFile file, @RequestParam Long id)
//			throws ApplicationException, java.io.IOException {
//
//		CompanyVO image = companyRepo.findById(id)
//				.orElseThrow(() -> new ApplicationException("Invalid company id" + id));
//
//		image.setImageName(file.getOriginalFilename());
//		image.setData(file.getBytes());
//		return companyRepo.save(image);
//
//	}

	// FinScreen-----------------------------------------------------------------------------------
	@Override
	public List<ScreenNamesVO> getFinScreenById(Long id) {
		List<ScreenNamesVO> finScreenVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received FinScreen BY Id : {}", id);
			finScreenVO = screenNamesRepo.findFinScreenById(id);
		} else {
			LOGGER.info("Successfully Received FinScreen For All Id.");
			finScreenVO = screenNamesRepo.findAll();
		}
		return finScreenVO;
	}

	@Override
	public ScreenNamesVO updateCreateFinScreen(@Valid FinScreenDTO finScreenDTO) throws ApplicationException {
		ScreenNamesVO finScreenVO = new ScreenNamesVO();
		boolean isUpdate = false;
		if (ObjectUtils.isNotEmpty(finScreenDTO.getId())) {
			isUpdate = true;
			finScreenVO = screenNamesRepo.findById(finScreenDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid FinScreen Details"));
			finScreenVO.setUpdatedBy(finScreenDTO.getCreatedBy());

		} else {
			if (screenNamesRepo.existsByScreenName(finScreenDTO.getScreenName())) {
				throw new ApplicationException("The given Screen name already exists.");
			}
			if (screenNamesRepo.existsByScreenCode(finScreenDTO.getScreenCode())) {
				throw new ApplicationException("The given Screen code already exists.");
			}
			finScreenVO.setUpdatedBy(finScreenDTO.getCreatedBy());
			finScreenVO.setCreatedBy(finScreenDTO.getCreatedBy());
		}

		// update check
		if (isUpdate) {
			ScreenNamesVO finScreen = screenNamesRepo.findById(finScreenDTO.getId()).orElse(null);
			if (!finScreen.getScreenName().equalsIgnoreCase(finScreenDTO.getScreenName())) {
				if (screenNamesRepo.existsByScreenName(finScreenDTO.getScreenName())) {
					throw new ApplicationException("The given Screen name already exists.");
				}
			}
			if (!finScreen.getScreenCode().equals(finScreenDTO.getScreenCode())) {
				if (screenNamesRepo.existsByScreenCode(finScreenDTO.getScreenCode())) {
					throw new ApplicationException("The given Screen code already exists");
				}
			}
		}
		getFinScreenVOFromFinScreenDTO(finScreenDTO, finScreenVO);
		return screenNamesRepo.save(finScreenVO);
	}

	private void getFinScreenVOFromFinScreenDTO(@Valid FinScreenDTO finScreenDTO, ScreenNamesVO finScreenVO)
			throws ApplicationException {

		finScreenVO.setActive(finScreenDTO.isActive());
		finScreenVO.setScreenCode(finScreenDTO.getScreenCode());
		finScreenVO.setScreenName(finScreenDTO.getScreenName());
	}

	@Override
	public List<Map<String, Object>> getAllScreenCode(Long orgId) {
		Set<Object[]> getFinScreen = screenNamesRepo.findAllScreenCode(orgId);
		return getScreen(getFinScreen);
	}

	private List<Map<String, Object>> getScreen(Set<Object[]> getFinScreen) {
		List<Map<String, Object>> finScreenList = new ArrayList<>();

		for (Object[] finScreen : getFinScreen) {
			Map<String, Object> branchMap = new HashMap<>();
			branchMap.put("screenCode", finScreen[0] != null ? finScreen[0].toString() : "");
			branchMap.put("screenName", finScreen[1] != null ? finScreen[1].toString() : "");
			finScreenList.add(branchMap);
		}
		return finScreenList;
	}

	// Country

	@Override
	public List<CountryVO> getAllCountry(Long orgid) {
		return countryRepo.findAll(orgid);
	}

	@Override
	public Optional<CountryVO> getCountryById(Long countryid) {
		return countryRepo.findById(countryid);
	}

	@Override
	public Map<String, Object> createUpdateCountry(CountryDTO countryDTO) throws ApplicationException {

		CountryVO countryVO;
		String message = null;

		if (ObjectUtils.isEmpty(countryDTO.getId())) {
			if (countryRepo.existsByCountryNameAndCountryCodeAndOrgId(countryDTO.getCountryName(),
					countryDTO.getCountryCode(), countryDTO.getOrgId())) {
				String errorMessage = String.format(
						"The CountryName: %s and CountryCode: %s already exists This Organization.",
						countryDTO.getCountryName(), countryDTO.getCountryCode());
				throw new ApplicationException(errorMessage);
			}

			if (countryRepo.existsByCountryNameAndOrgId(countryDTO.getCountryName(), countryDTO.getOrgId())) {
				String errorMessage = String.format("The CountryName: %s already exists This Organization.",
						countryDTO.getCountryName());
				throw new ApplicationException(errorMessage);
			}

			if (countryRepo.existsByCountryCodeAndOrgId(countryDTO.getCountryCode(), countryDTO.getOrgId())) {
				String errorMessage = String.format("The CountryCode: %s already exists This Organization.",
						countryDTO.getCountryCode());
				throw new ApplicationException(errorMessage);
			}
			// Create new branch
			countryVO = new CountryVO();
			countryVO.setCreatedBy(countryDTO.getCreatedBy());
			countryVO.setUpdatedBy(countryDTO.getCreatedBy());
			message = "Country Creation SuccessFully";
		} else {
			// Update existing branch
			countryVO = countryRepo.findById(countryDTO.getId())
					.orElseThrow(() -> new ApplicationException("Branch not found with id: " + countryDTO.getId()));
			countryVO.setUpdatedBy(countryDTO.getCreatedBy());
			if (countryDTO.getCountryCode() != null
					&& !countryVO.getCountryCode().equalsIgnoreCase(countryDTO.getCountryCode())) {

				if (countryRepo.existsByCountryCodeIgnoreCaseAndOrgIdAndIdNot(countryDTO.getCountryCode(),
						countryDTO.getOrgId(), countryVO.getId())) {

					throw new ApplicationException(String.format(
							"The CountryCode: %s already exists in this Organization.", countryDTO.getCountryCode()));
				}

				countryVO.setCountryCode(countryDTO.getCountryCode().toUpperCase());
			}

			if (countryDTO.getCountryName() != null
					&& !countryVO.getCountryName().equalsIgnoreCase(countryDTO.getCountryName())) {

				if (countryRepo.existsByCountryNameIgnoreCaseAndOrgIdAndIdNot(countryDTO.getCountryName(),
						countryDTO.getOrgId(), countryVO.getId())) {

					throw new ApplicationException(String.format(
							"The CountryName: %s already exists in this Organization.", countryDTO.getCountryName()));
				}

				countryVO.setCountryName(countryDTO.getCountryName().toUpperCase());
			}
			message = "Country Update Successfully";
		}

		getCountryVOFromCounytryDTO(countryVO, countryDTO);
		countryRepo.save(countryVO);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", message);
		response.put("countryVO", countryVO);
		return response;

	}

	private void getCountryVOFromCounytryDTO(CountryVO countryVO, CountryDTO countryDTO) {
		countryVO.setCountryName(countryDTO.getCountryName().toUpperCase());
		countryVO.setCountryCode(countryDTO.getCountryCode().toUpperCase());
		countryVO.setActive(countryDTO.isActive());
		countryVO.setOrgId(countryDTO.getOrgId());
		countryVO.setCancel(countryDTO.isCancel());

	}

	@Override
	public void deleteCountry(Long countryid) {

		countryRepo.deleteById(countryid);
	}

	// State

	@Override
	public List<StateVO> getAllgetAllStates(Long orgid) {
		return stateRepo.findAllByOrgId(orgid);
	}

	@Override
	public Optional<StateVO> getStateById(Long stateid) {
		return stateRepo.findById(stateid);
	}

	@Override
	public List<StateVO> getStatesByCountry(Long orgid, Long country) {

		return stateRepo.findByCountry(orgid, country);
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateState(StateDTO stateDTO) throws ApplicationException {
		StateVO stateVO;
		String message = null;

		if (ObjectUtils.isEmpty(stateDTO.getId())) {
			// Check for existing state by state code, state number, and state name
			if (stateRepo.existsByStateCodeAndOrgId(stateDTO.getStateCode(), stateDTO.getOrgId())) {
				String errorMessage = String.format("The StateCode: %s already exists in This Organization.",
						stateDTO.getStateCode());
				throw new ApplicationException(errorMessage);
			}
			if (stateRepo.existsByStateNumberAndOrgId(stateDTO.getStateNumber(), stateDTO.getOrgId())) {
				String errorMessage = String.format("The StateNumber: %s already exists in This Organization.",
						stateDTO.getStateNumber());
				throw new ApplicationException(errorMessage);
			}
			if (stateRepo.existsByStateNameAndOrgId(stateDTO.getStateName(), stateDTO.getOrgId())) {
				String errorMessage = String.format("The StateName: %s already exists in This Organization.",
						stateDTO.getStateName());
				throw new ApplicationException(errorMessage);
			}

			// Create new state
			stateVO = new StateVO();
			stateVO.setCreatedBy(stateDTO.getCreatedBy());
			stateVO.setUpdatedBy(stateDTO.getCreatedBy());
			message = "State Creation Successfully";
		} else {
			// Update existing state
			stateVO = stateRepo.findById(stateDTO.getId())
					.orElseThrow(() -> new ApplicationException("State not found with id: " + stateDTO.getId()));

			stateVO.setUpdatedBy(stateDTO.getCreatedBy());

			if (!stateVO.getStateCode().equalsIgnoreCase(stateDTO.getStateCode())) {
				if (stateRepo.existsByStateCodeAndOrgId(stateDTO.getStateCode(), stateDTO.getOrgId())) {
					String errorMessage = String.format("The StateCode: %s already exists in This Organization.",
							stateDTO.getStateCode());
					throw new ApplicationException(errorMessage);
				}
				stateVO.setStateCode(stateDTO.getStateCode().toUpperCase());
			}

			if (!stateVO.getStateName().equalsIgnoreCase(stateDTO.getStateName())) {
				if (stateRepo.existsByStateNameAndOrgId(stateDTO.getStateName(), stateDTO.getOrgId())) {
					String errorMessage = String.format("The StateName: %s already exists in This Organization.",
							stateDTO.getStateName());
					throw new ApplicationException(errorMessage);
				}
				stateVO.setStateName(stateDTO.getStateName().toUpperCase());
			}

			if (!stateVO.getStateNumber().equalsIgnoreCase(stateDTO.getStateNumber())) {
				if (stateRepo.existsByStateNumberAndOrgId(stateDTO.getStateNumber(), stateDTO.getOrgId())) {
					String errorMessage = String.format("The StateNumber: %s already exists in This Organization.",
							stateDTO.getStateNumber());
					throw new ApplicationException(errorMessage);
				}
				stateVO.setStateNumber(stateDTO.getStateNumber().toUpperCase());
			}

			message = "State Update Successfully";
		}

		// Map the remaining fields
		getStateVOFromStateDTO(stateVO, stateDTO);

		// Save the entity
		stateRepo.save(stateVO);

		// Prepare the response
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("stateVO", stateVO);
		return response;
	}

	private void getStateVOFromStateDTO(StateVO stateVO, StateDTO stateDTO) throws ApplicationException {
		stateVO.setStateCode(stateDTO.getStateCode().toUpperCase());
		stateVO.setStateName(stateDTO.getStateName().toUpperCase());
		stateVO.setStateNumber(stateDTO.getStateNumber().toUpperCase());

		if (stateDTO.getCountry() != null && stateDTO.getCountry() != 0) {

			CountryVO countryVO = countryRepo.findById(stateDTO.getCountry()).orElseThrow(
					() -> new ApplicationException("Country not found with id : " + stateDTO.getCountry()));

			stateVO.setCountry(countryVO);
		}
		stateVO.setRegion(stateDTO.getRegion().toUpperCase());
		stateVO.setActive(stateDTO.isActive());
		stateVO.setCancelRemarks(stateDTO.getCancelRemarks());
		stateVO.setOrgId(stateDTO.getOrgId());
		// stateVO.setDupchk(stateDTO.getOrgId() + stateDTO.getStateCode() +
		// stateDTO.getStateName());
	}

	@Override
	public void deleteState(Long countryid) {
		stateRepo.deleteById(countryid);
	}

	// City

	@Override
	public List<CityVO> getAllgetAllCities(Long orgid) {
		return cityRepo.findAll(orgid);
	}

	@Override
	public List<CityVO> getAllCitiesByState(Long orgid, Long state) {

		return cityRepo.getAllCitiesByState(orgid, state);
	}

	@Override
	public Optional<CityVO> getCityById(Long cityid) {
		return cityRepo.findById(cityid);
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateCity(CityDTO cityDTO) throws ApplicationException {
		CityVO cityVO;
		String message;

		if (ObjectUtils.isEmpty(cityDTO.getId())) {
			if (cityRepo.existsByCityCodeAndOrgId(cityDTO.getCityCode(), cityDTO.getOrgId())) {
				String errorMessage = String.format("The CityCode: %s already exists in this organization.",
						cityDTO.getCityCode());
				throw new ApplicationException(errorMessage);
			}
			if (cityRepo.existsByCityNameAndOrgId(cityDTO.getCityName(), cityDTO.getOrgId())) {
				String errorMessage = String.format("The CityName: %s already exists in this organization.",
						cityDTO.getCityName());
				throw new ApplicationException(errorMessage);
			}
			// Create new city
			cityVO = new CityVO();
			cityVO.setCreatedBy(cityDTO.getCreatedBy());
			cityVO.setUpdatedBy(cityDTO.getCreatedBy());
			message = "City Created Successfully";
		} else {
			// Update existing city
			cityVO = cityRepo.findById(cityDTO.getId())
					.orElseThrow(() -> new ApplicationException("City not found with id: " + cityDTO.getId()));
			cityVO.setUpdatedBy(cityDTO.getCreatedBy());

			if (!cityVO.getCityCode().equalsIgnoreCase(cityDTO.getCityCode())) {
				if (cityRepo.existsByCityCodeAndOrgId(cityDTO.getCityCode(), cityDTO.getOrgId())) {
					String errorMessage = String.format("The CityCode: %s already exists in this organization.",
							cityDTO.getCityCode());
					throw new ApplicationException(errorMessage);
				}
				cityVO.setCityCode(cityDTO.getCityCode().toUpperCase());
			}

			if (!cityVO.getCityName().equalsIgnoreCase(cityDTO.getCityName())) {
				if (cityRepo.existsByCityNameAndOrgId(cityDTO.getCityName(), cityDTO.getOrgId())) {
					String errorMessage = String.format("The CityName: %s already exists in this organization.",
							cityDTO.getCityName());
					throw new ApplicationException(errorMessage);
				}
				cityVO.setCityName(cityDTO.getCityName().toUpperCase());
			}
			message = "City Updated Successfully";
		}

		getCityVOFromCityDTO(cityVO, cityDTO);
		cityRepo.save(cityVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("cityVO", cityVO);
		return response;
	}

	private void getCityVOFromCityDTO(CityVO cityVO, CityDTO cityDTO) throws ApplicationException {
		cityVO.setCityCode(cityDTO.getCityCode().toUpperCase());
		cityVO.setCityName(cityDTO.getCityName().toUpperCase());
		if (cityDTO.getCountry() != null && cityDTO.getCountry() != 0) {

			CountryVO countryVO = countryRepo.findById(cityDTO.getCountry())
					.orElseThrow(() -> new ApplicationException("Country not found with id : " + cityDTO.getCountry()));

			cityVO.setCountry(countryVO);
		}

		if (cityDTO.getState() != null && cityDTO.getState() != 0) {

			StateVO stateVO = stateRepo.findById(cityDTO.getState())
					.orElseThrow(() -> new ApplicationException("State not found with id : " + cityDTO.getState()));

			cityVO.setState(stateVO);
		}

		cityVO.setActive(cityDTO.isActive());
		cityVO.setOrgId(cityDTO.getOrgId());
		cityVO.setCancelRemarks(cityDTO.getCancelRemarks());

	}

	@Override
	public void deleteCity(Long cityid) {
		cityRepo.deleteById(cityid);
	}

	// Region

	@Override
	public List<RegionVO> getAllRegios() {

		return regionRepo.findAll();
	}

	@Override
	public List<RegionVO> getAllRegionsByOrgId(Long orgId) {
		return regionRepo.findAll(orgId);
	}

	@Override
	public Optional<RegionVO> getRegionById(Long Regionid) {
		return regionRepo.findById(Regionid);
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateRegion(RegionDTO regionDTO) throws ApplicationException {
		RegionVO regionVO;
		String message;

		if (ObjectUtils.isEmpty(regionDTO.getId())) {
			if (regionRepo.existsByRegionNameAndOrgId(regionDTO.getRegionName(), regionDTO.getOrgId())) {
				String errorMessage = String.format("This RegionName:%s Already Exists in This Organization",
						regionDTO.getRegionName().toUpperCase());
				throw new ApplicationException(errorMessage);
			}
			if (regionRepo.existsByRegionCodeAndOrgId(regionDTO.getRegionCode(), regionDTO.getOrgId())) {
				String errorMessage = String.format("This RegionCode:%s Already Exists in This Organization",
						regionDTO.getRegionCode().toUpperCase());
				throw new ApplicationException(errorMessage);
			}
			// Create new region
			regionVO = new RegionVO();
			regionVO.setCreatedBy(regionDTO.getCreatedBy());
			regionVO.setUpdatedBy(regionDTO.getCreatedBy());
			message = "Region Created Successfully";
		} else {
			// Update existing region
			regionVO = regionRepo.findById(regionDTO.getId()).orElseThrow(
					() -> new ApplicationException("This Id Is Not Found Any Information: " + regionDTO.getId()));
			regionVO.setUpdatedBy(regionDTO.getCreatedBy());

			if (!regionVO.getRegionName().equalsIgnoreCase(regionDTO.getRegionName())) {
				if (regionRepo.existsByRegionNameAndOrgId(regionDTO.getRegionName(), regionDTO.getOrgId())) {
					String errorMessage = String.format("This RegionName:%s Already Exists in This Organization",
							regionDTO.getRegionName());
					throw new ApplicationException(errorMessage);
				}
				regionVO.setRegionName(regionDTO.getRegionName().toUpperCase());
			}

			if (!regionVO.getRegionCode().equalsIgnoreCase(regionDTO.getRegionCode())) {
				if (regionRepo.existsByRegionCodeAndOrgId(regionDTO.getRegionCode(), regionDTO.getOrgId())) {
					String errorMessage = String.format("This RegionCode:%s Already Exists in This Organization",
							regionDTO.getRegionCode());
					throw new ApplicationException(errorMessage);
				}
				regionVO.setRegionCode(regionDTO.getRegionCode().toUpperCase());
			}
			message = "Region Updated Successfully";
		}

		getRegionVOFromRegionDTO(regionVO, regionDTO);
		regionRepo.save(regionVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("regionVO", regionVO);
		return response;
	}

	private void getRegionVOFromRegionDTO(RegionVO regionVO, RegionDTO regionDTO) {
		regionVO.setActive(regionDTO.isActive());
		regionVO.setOrgId(regionDTO.getOrgId());
		regionVO.setCancel(regionDTO.isCancel());
		regionVO.setRegionCode(regionDTO.getRegionCode().toUpperCase());
		regionVO.setRegionName(regionDTO.getRegionName().toUpperCase());
	}

	@Override
	public void deleteRegion(Long regionid) {
		regionRepo.deleteById(regionid);
	}

	// Currency
	@Override
	public List<CurrencyVO> getAllCurrency(Long orgid) {

		return currencyRepo.findAll(orgid);
	}

	@Override
	public Optional<CurrencyVO> getCurrencyById(Long currencyid) {

		return currencyRepo.findById(currencyid);
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateCurrency(CurrencyDTO currencyDTO) throws ApplicationException {

		CurrencyVO currencyVO;
		String message = null;
		CurrencyVO oldCurrency = null;
		CountryVO countryVO = countryRepo.findById(currencyDTO.getCountryId()).orElseThrow(
				() -> new ApplicationException("Country not found with id : " + currencyDTO.getCountryId()));
		if (ObjectUtils.isEmpty(currencyDTO.getId())) {

			if (currencyRepo.existsByOrgIdAndCountryAndCurrencyIgnoreCase(currencyDTO.getOrgId(), countryVO,
					currencyDTO.getCurrency())) {
				String errorMessage = String.format("This Currency:%s Already Exists in This Organization.",
						currencyDTO.getCurrency());
				throw new ApplicationException(errorMessage);
			}
			if (currencyRepo.existsByOrgIdAndCountryAndCurrencyDescriptionIgnoreCase(currencyDTO.getOrgId(), countryVO,
					currencyDTO.getCurrencyDescription())) {
				String errorMessage = String.format("This CurrencyDescription:%s Already Exists in This Organization.",
						currencyDTO.getCurrencyDescription());
				throw new ApplicationException(errorMessage);
			}
			if (currencyRepo.existsByOrgIdAndCountryAndSubCurrencyIgnoreCase(currencyDTO.getOrgId(), countryVO,
					currencyDTO.getSubCurrency())) {
				String errorMessage = String.format("This SubCurrency:%s Already Exists in This Organization.",
						currencyDTO.getSubCurrency());
				throw new ApplicationException(errorMessage);
			}

			// Create new currency
			currencyVO = new CurrencyVO();
			currencyVO.setCreatedBy(currencyDTO.getCreatedBy());
			currencyVO.setUpdatedBy(currencyDTO.getCreatedBy());
			message = "Currency Created Successfully";
		} else {
			// Update existing currency

			oldCurrency = currencyRepo.findById(currencyDTO.getId())
					.orElseThrow(() -> new ApplicationException("Currency master not found"));

			entityManager.detach(oldCurrency); // detach snapshot

			currencyVO = currencyRepo.findById(currencyDTO.getId()).orElseThrow(
					() -> new ApplicationException("This Id Is Not Found Any Information: " + currencyDTO.getId()));
			currencyVO.setUpdatedBy(currencyDTO.getCreatedBy());

			if (!currencyVO.getCurrency().equalsIgnoreCase(currencyDTO.getCurrency())) {
				if (currencyRepo.existsByOrgIdAndCountryAndCurrencyIgnoreCase(currencyDTO.getOrgId(), countryVO,
						currencyDTO.getCurrency())) {
					String errorMessage = String.format("This Currency:%s Already Exists in This Organization.",
							currencyDTO.getCurrency());
					throw new ApplicationException(errorMessage);
				}
				currencyVO.setCurrency(currencyDTO.getCurrency().toUpperCase());
			}
			if (!currencyVO.getSubCurrency().equalsIgnoreCase(currencyDTO.getSubCurrency())) {
				if (currencyRepo.existsByOrgIdAndCountryAndSubCurrencyIgnoreCase(currencyDTO.getOrgId(), countryVO,
						currencyDTO.getSubCurrency())) {
					String errorMessage = String.format("This SubCurrency:%s Already Exists in This Organization.",
							currencyDTO.getSubCurrency());
					throw new ApplicationException(errorMessage);
				}
				if (currencyDTO.getSubCurrency() != null) {
					currencyVO.setSubCurrency(currencyDTO.getSubCurrency().toUpperCase());
				}
			}
			if (!currencyVO.getCurrencyDescription().equalsIgnoreCase(currencyDTO.getCurrencyDescription())) {
				if (currencyRepo.existsByOrgIdAndCountryAndCurrencyDescriptionIgnoreCase(currencyDTO.getOrgId(),
						countryVO, currencyDTO.getCurrencyDescription())) {
					String errorMessage = String.format(
							"This CurrencyDescription:%s Already Exists in This Organization.",
							currencyDTO.getCurrencyDescription());
					throw new ApplicationException(errorMessage);
				}
				if (currencyDTO.getCurrencyDescription() != null) {
					currencyVO.setCurrencyDescription(currencyDTO.getCurrencyDescription().toUpperCase());
				}
			}
			message = "Currency Updated Successfully";
		}

		getCurrencyVOFromCurrencyDTO(currencyVO, currencyDTO);

		currencyRepo.save(currencyVO);
//		commonNotificationService.generateNotification(currencyVO.getScreenCode(), currencyVO.getId(), oldCurrency, currencyVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("currencyVO", currencyVO);
		return response;
	}

	private void getCurrencyVOFromCurrencyDTO(CurrencyVO currencyVO, CurrencyDTO currencyDTO)
			throws ApplicationException {
		currencyVO.setCurrency(currencyDTO.getCurrency().toUpperCase());
		if (currencyDTO.getSubCurrency() != null) {
			currencyVO.setSubCurrency(currencyDTO.getSubCurrency().toUpperCase());
		}
		if (currencyDTO.getCurrencyDescription() != null) {
			currencyVO.setCurrencyDescription(currencyDTO.getCurrencyDescription().toUpperCase());
		}
		currencyVO.setActive(currencyDTO.isActive());

		CountryVO countryVO = countryRepo.findById(currencyDTO.getCountryId()).orElseThrow(
				() -> new ApplicationException("Country not found with id : " + currencyDTO.getCountryId()));

		currencyVO.setCountry(countryVO);
		currencyVO.setOrgId(currencyDTO.getOrgId());

		currencyVO.setMainCurrency(currencyDTO.getMainCurrency().toUpperCase());
		currencyVO.setMainCurrencySymbol(currencyDTO.getMainCurrencySymbol());
		currencyVO.setSubSymbol(currencyDTO.getSubSymbol());
		currencyVO.setCurrencyRepresentation(currencyDTO.getCurrencyRepresentation());
		currencyVO.setCurrencyInteger(currencyDTO.getCurrencyInteger());
		currencyVO.setCurrencyDecimal(currencyDTO.getCurrencyDecimal());
		currencyVO.setCancelRemarks(currencyDTO.getCancelRemarks());

	}

	@Override
	public void deleteCurrency(Long currencyid) {
		currencyRepo.deleteById(currencyid);

	}

	@Override
	public Map<String, Object> createUpdateScreenNames(ScreenNamesDTO screenNamesDTO) throws ApplicationException {
		ScreenNamesVO screenNamesVO = new ScreenNamesVO();
		String message = null;

		if (ObjectUtils.isEmpty(screenNamesDTO.getId())) {

			// Validate if responsibility already exists by responsibility name
			if (screenNamesRepo.existsByScreenName(screenNamesDTO.getScreenName())) {
				throw new ApplicationException("Screen Name already exists");
			}
			if (screenNamesRepo.existsByScreenCode(screenNamesDTO.getScreenCode())) {
				throw new ApplicationException("Screen Code already exists");
			}

			screenNamesVO.setCreatedBy(screenNamesDTO.getCreatedBy());
			screenNamesVO.setUpdatedBy(screenNamesDTO.getCreatedBy());
			screenNamesVO.setActive(screenNamesDTO.isActive());
			screenNamesVO.setScreenCode(screenNamesDTO.getScreenCode());
			screenNamesVO.setScreenName(screenNamesDTO.getScreenName());
			// Set the values from screenNamesDTO to responsibilityVO
			message = "ScreenName Created successfully";

		} else {

			// Retrieve the existing ResponsibilityVO from the repository
			screenNamesVO = screenNamesRepo.findById(screenNamesDTO.getId())
					.orElseThrow(() -> new ApplicationException("Screen Name not found"));

			// Validate and update unique fields if changed
			if (!screenNamesVO.getScreenName().equalsIgnoreCase(screenNamesDTO.getScreenName())) {
				if (screenNamesRepo.existsByScreenName(screenNamesDTO.getScreenName())) {
					throw new ApplicationException("Screen Name already exists");
				}
				screenNamesVO.setScreenName(screenNamesDTO.getScreenName());
			}
			if (!screenNamesVO.getScreenCode().equalsIgnoreCase(screenNamesDTO.getScreenCode())) {
				if (screenNamesRepo.existsByScreenCode(screenNamesDTO.getScreenCode())) {
					throw new ApplicationException("Screen Code already exists");
				}
				screenNamesVO.setScreenCode(screenNamesDTO.getScreenCode());
			}
			screenNamesVO.setActive(screenNamesDTO.isActive());
			screenNamesVO.setUpdatedBy(screenNamesDTO.getCreatedBy());
			// Update the remaining fields from screenNamesDTO to responsibilityVO
			message = "ScreenName Updated successfully";
		}

		screenNamesRepo.save(screenNamesVO);
		Map<String, Object> response = new HashMap<>();
		response.put("screenNamesVO", screenNamesVO);
		response.put("message", message);
		return response;
	}

	@Override
	public List<ScreenNamesVO> getAllScreenNames() {

		return screenNamesRepo.findAll();
	}

	@Override
	public ScreenNamesVO getScreenNamesById(Long id) throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {
			throw new ApplicationException("Invalid Id");
		}

		ScreenNamesVO screenNamesVO = screenNamesRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Screen Name not found for Id: " + id));

		return screenNamesVO;
	}

	// Financila Year
//	@Override
//	public Map<String, Object> createUpdateFinYear(FinancialYearDTO financialYearDTO) throws ApplicationException {
//		FinancialYearVO financialYearVO = null;
//		String message;
//
//		if (ObjectUtils.isEmpty(financialYearDTO.getId())) {
//			if (financialYearRepo.existsByFinYearAndOrgId(financialYearDTO.getFinYear(), financialYearDTO.getOrgId())) {
//				String errorMessage = String.format("ThiS finyear:%s Already Exists This Organization .",
//						financialYearDTO.getFinYear());
//				throw new ApplicationException(errorMessage);
//			}
//
//			if (financialYearRepo.existsByFinYearIdentifierAndOrgId(financialYearDTO.getFinYearIdentifier(),
//					financialYearDTO.getOrgId())) {
//				String errorMessage = String.format("ThiS finyearidentifier:%s Already Exists This Organization .",
//						financialYearDTO.getFinYearIdentifier());
//				throw new ApplicationException(errorMessage);
//			}
//
//			if (financialYearRepo.existsByFinYearAndOrgId(financialYearDTO.getFinYearId(),
//					financialYearDTO.getOrgId())) {
//				String errorMessage = String.format("ThiS finyearid:%s Already Exists This Organization .",
//						financialYearDTO.getFinYearId());
//				throw new ApplicationException(errorMessage);
//			}
//
//			financialYearVO = new FinancialYearVO();
//			financialYearVO.setCreatedBy(financialYearDTO.getCreatedBy());
//			financialYearVO.setUpdatedBy(financialYearDTO.getCreatedBy());
//			message = "Financial Year Creation Successfully";
//
//		} else {
//			financialYearVO = financialYearRepo.findById(financialYearDTO.getId())
//					.orElseThrow(() -> new ApplicationException(String
//							.format("This Id Is Not Found Any Information, Invalid Id: %s", financialYearDTO.getId())));
//
//			if (financialYearVO.getFinYear() != financialYearDTO.getFinYear()) {
//				if (financialYearRepo.existsByFinYearAndOrgId(financialYearDTO.getFinYear(),
//						financialYearDTO.getOrgId())) {
//					String errorMessage = String.format("This finyear: %s already exists for this organization.",
//							financialYearDTO.getFinYear());
//					throw new ApplicationException(errorMessage);
//				}
//				financialYearVO.setFinYear(financialYearDTO.getFinYear());
//			}
//
//			if (!financialYearVO.getFinYearIdentifier().equals(financialYearDTO.getFinYearIdentifier())) {
//				if (financialYearRepo.existsByFinYearIdentifierAndOrgId(financialYearDTO.getFinYearIdentifier(),
//						financialYearDTO.getOrgId())) {
//					String errorMessage = String.format(
//							"This finyearIdentifier: %s already exists for this organization.",
//							financialYearDTO.getFinYearIdentifier());
//					throw new ApplicationException(errorMessage);
//				}
//				financialYearVO.setFinYearIdentifier(financialYearDTO.getFinYearIdentifier());
//			}
//
//			if (financialYearVO.getFinYearId() != financialYearDTO.getFinYearId()) {
//				if (financialYearRepo.existsByFinYearAndOrgId(financialYearDTO.getFinYearId(),
//						financialYearDTO.getOrgId())) {
//					String errorMessage = String.format("This finyearId: %s already exists for this organization.",
//							financialYearDTO.getFinYearId());
//					throw new ApplicationException(errorMessage);
//				}
//				financialYearVO.setFinYearId(financialYearDTO.getFinYearId());
//			}
//
//			financialYearVO.setUpdatedBy(financialYearDTO.getCreatedBy());
//			message = "Financial Year Updation Successfully";
//
//		}
//		getFinancialYearVOFromFinancialYearDTO(financialYearVO, financialYearDTO);
//		financialYearRepo.save(financialYearVO);
//		Map<String, Object> response = new HashMap<String, Object>();
//		response.put("financialYearVO", financialYearVO);
//		response.put("message", response);
//		return response;
//
//	}

	private void getFinancialYearVOFromFinancialYearDTO(FinancialYearVO financialYearVO,
			FinancialYearDTO financialYearDTO) {
		financialYearVO.setFinYear(financialYearDTO.getFinYear());
//		financialYearVO.setFinYearId(financialYearDTO.getFinYearId());
//		financialYearVO.setFinYearIdentifier(financialYearDTO.getFinYearIdentifier());
		financialYearVO.setStartDate(financialYearDTO.getStartDate());
		financialYearVO.setEndDate(financialYearDTO.getEndDate());
//		financialYearVO.setCurrentFinYear(financialYearDTO.isCurrentFinYear());
//		financialYearVO.setClosed(financialYearDTO.isClosed());
		financialYearVO.setOrgId(financialYearDTO.getOrgId());
		financialYearVO.setActive(financialYearDTO.isActive());
	}

	@Override
	public List<FinancialYearVO> getAllActiveFInYear(Long orgId) {
		return financialYearRepo.findAllActiveFinYear(orgId);
	}

	@Override
	public List<FinancialYearVO> getAllFInYearByOrgId(Long orgId) {
		return financialYearRepo.findFinancialYearByOrgId(orgId);
	}

	@Override
	public Optional<FinancialYearVO> getAllFInYearById(Long id) {
		return financialYearRepo.findById(id);
	}

	@Override
	public List<Map<String, Object>> getAllCurrencyForExRate(Long orgId) {
		Set<Object[]> getFullGridCurrency = currencyRepo.findCurrencyForFullGrid(orgId);
		return getCurrency(getFullGridCurrency); // Returning a list of Map<String, Object>
	}

	private List<Map<String, Object>> getCurrency(Set<Object[]> getFullGridCurrency) {
		List<Map<String, Object>> currencyList = new ArrayList<>(); // Correct variable name

		for (Object[] currency : getFullGridCurrency) { // Iterating over getFullGridCurrency
			Map<String, Object> currencyMap = new HashMap<>();
			currencyMap.put("id", currency[0] != null ? Integer.parseInt(currency[0].toString()) : 0);
			currencyMap.put("currency", currency[1] != null ? currency[1].toString() : "");
			currencyMap.put("currencyDescription", currency[2] != null ? currency[2].toString() : "");

			currencyList.add(currencyMap); // Add the Map to the list
		}
		return currencyList;
	}

//	@Override
//	public List<Map<String, Object>> getCompanyByOrgId(Long orgId) {
//
//		Set<Object[]> getCompanyBankDetails = companyRepo.findCompanyBankDetails(orgId);
//		return getBank(getCompanyBankDetails); // Returning a list of Map<String, Object>
//	}
//
//	private List<Map<String, Object>> getBank(Set<Object[]> getCompanyBankDetails) {
//		List<Map<String, Object>> bankDetailsList = new ArrayList<>(); // Correct variable name
//
//		for (Object[] bank : getCompanyBankDetails) { // Iterating over getFullGridCurrency
//			Map<String, Object> bankMap = new HashMap<>();
//			bankMap.put("bankName", bank[0] != null ? bank[0].toString() : "");
//			bankMap.put("accountCode", bank[1] != null ? bank[1].toString() : "");
//			bankMap.put("accountNo", bank[2] != null ? bank[2].toString() : "");
//			bankMap.put("ifsc", bank[3] != null ? bank[3].toString() : "");
//			bankMap.put("accountType", bank[4] != null ? bank[4].toString() : "");
//			bankMap.put("beneficiaryName", bank[5] != null ? bank[5].toString() : "");
//			bankMap.put("branch", bank[6] != null ? bank[6].toString() : "");
//
//			bankDetailsList.add(bankMap); // Add the Map to the list
//		}
//		return bankDetailsList;
//	}

	public CompanyVO uploadCompanyLogoInBloob(MultipartFile file, Long id) throws IOException {
		CompanyVO companyVO = companyRepo.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
		companyVO.setCompanyLogo(file.getBytes()); // Store image as byte array
		return companyRepo.save(companyVO);
	}

	// branch

	@Override
	@Transactional
	public Map<String, Object> createUpdateBranch(BranchDTO branchDTO) throws ApplicationException {

		BranchVO branchVO = new BranchVO();
		String message;

		if (branchDTO.getId() != null) {

			branchVO = branchRepo.findById(branchDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Branch Details"));

			branchVO.setUpdatedBy(branchDTO.getCreatedBy());

			if (!branchVO.getBranchName().equalsIgnoreCase(branchDTO.getBranchName())) {

				if (branchRepo.existsByBranchNameAndOrgId(branchDTO.getBranchName(), branchDTO.getOrgId())) {
					throw new ApplicationException("Branch Name : " + branchDTO.getBranchName() + " already exists.");
				}
			}

			if (!branchVO.getBranchCode().equalsIgnoreCase(branchDTO.getBranchCode())) {

				if (branchRepo.existsByBranchCodeAndOrgId(branchDTO.getBranchCode(), branchDTO.getOrgId())) {
					throw new ApplicationException("Branch Code : " + branchDTO.getBranchCode() + " already exists.");
				}
			}

			message = "Branch Updated Successfully";

		} else {

			if (branchRepo.existsByBranchNameAndOrgId(branchDTO.getBranchName(), branchDTO.getOrgId())) {
				throw new ApplicationException("Branch Name : " + branchDTO.getBranchName() + " already exists.");
			}

			if (branchRepo.existsByBranchCodeAndOrgId(branchDTO.getBranchCode(), branchDTO.getOrgId())) {
				throw new ApplicationException("Branch Code : " + branchDTO.getBranchCode() + " already exists.");
			}

			branchVO.setCreatedBy(branchDTO.getCreatedBy());
			branchVO.setUpdatedBy(branchDTO.getCreatedBy());

			message = "Branch Created Successfully";
		}

		createUpdateBranchVO(branchDTO, branchVO);

		branchRepo.save(branchVO);
		Map<String, Object> response = new HashMap<>();
		response.put("branchVO", branchVO);
		response.put("message", message);

		return response;
	}

	private void createUpdateBranchVO(BranchDTO dto, BranchVO branchVO) throws ApplicationException {

		branchVO.setBranchCode(dto.getBranchCode().toUpperCase());
		branchVO.setBranchName(dto.getBranchName().toUpperCase());
		branchVO.setBranchIncharge(dto.getBranchIncharge());
		branchVO.setPhoneNo(dto.getPhoneNo());
		branchVO.setEmail(dto.getEmail());
		branchVO.setAddress(dto.getAddress());
		branchVO.setEccNo(dto.getEccNo());
		branchVO.setDivision(dto.getDivision());
		branchVO.setPincode(dto.getPincode());

		branchVO.setGstinNo(dto.getGstinNo());
		branchVO.setPanNo(dto.getPanNo());
		branchVO.setCinNo(dto.getCinNo());
		branchVO.setDunsNo(dto.getDunsNo());

		branchVO.setOrgId(dto.getOrgId());

		branchVO.setActive(dto.isActive());
		branchVO.setCancelRemarks(dto.getCancelRemarks());

		if (dto.getStateId() != null && dto.getStateId() != 0) {

			StateVO state = stateRepo.findById(dto.getStateId())
					.orElseThrow(() -> new ApplicationException("State Not Found"));

			branchVO.setState(state);
		}

		if (dto.getCityId() != null && dto.getCityId() != 0) {

			CityVO city = cityRepo.findById(dto.getCityId())
					.orElseThrow(() -> new ApplicationException("City Not Found"));

			branchVO.setCity(city);
		}

		// Delete old Bank Details while updating
		if (dto.getId() != null) {

			List<BankDetailsVO> bankList = bankDetailsRepo.findByBranchVO(branchVO);

			bankDetailsRepo.deleteAll(bankList);
		}

		List<BankDetailsVO> bankList = new ArrayList<>();

		if (dto.getBankDetails() != null && !dto.getBankDetails().isEmpty()) {

			for (BankDetailsDTO bankDTO : dto.getBankDetails()) {

				BankDetailsVO bankVO = new BankDetailsVO();

				bankVO.setBankName(bankDTO.getBankName());
				bankVO.setIfscCode(bankDTO.getIfscCode());
				bankVO.setAccountNo(bankDTO.getAccountNo());
				bankVO.setBankBranch(bankDTO.getBankBranch());

				// Parent Mapping
				bankVO.setBranchVO(branchVO);

				// ⭐ Add to list
				bankList.add(bankVO);
			}

			// Set child list to parent
			branchVO.setBankDetailsVO(bankList);
		}
	}

	@Override
	public BranchVO getBranchById(Long id) throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {
			throw new ApplicationException("Invalid Id");
		}

		BranchVO branchVO = branchRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Branch not found for Id: " + id));

		return branchVO;
	}

	@Override
	public List<BranchVO> getBranchByOrgId(Long orgId) throws ApplicationException {

		List<BranchVO> branchList = branchRepo.getBranchByOrgId(orgId);

		if (branchList.isEmpty()) {
			throw new ApplicationException("No branch Details Found");
		}

		return branchList;
	}

	// Transport Master

	@Override
	@Transactional
	public Map<String, Object> updateCreateTransportMaster(@Valid TransportMasterDTO transportMasterDTO)
			throws ApplicationException {

		TransportMasterVO transportMasterVO = new TransportMasterVO();
		String message;

		if (ObjectUtils.isNotEmpty(transportMasterDTO.getId())) {

			transportMasterVO = transportRepo.findById(transportMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Transport Details"));

			if (!transportMasterVO.getTransportName().equalsIgnoreCase(transportMasterDTO.getTransportName())) {

				if (transportRepo.existsByTransportNameAndOrgId(transportMasterDTO.getTransportName(),
						transportMasterDTO.getOrgId())) {

					throw new ApplicationException("The Transport : " + transportMasterDTO.getTransportName()
							+ " already exists in this Organization.");
				}
			}

			createUpdateTransportMasterVOByTransportMasterDTO(transportMasterDTO, transportMasterVO);

			transportMasterVO.setUpdated_By(transportMasterDTO.getCreatedBy());

			message = "Transport Updated Successfully";

		} else {

			if (transportRepo.existsByTransportNameAndOrgId(transportMasterDTO.getTransportName(),
					transportMasterDTO.getOrgId())) {

				throw new ApplicationException("The Transport : " + transportMasterDTO.getTransportName()
						+ " already exists in this Organization.");
			}

			createUpdateTransportMasterVOByTransportMasterDTO(transportMasterDTO, transportMasterVO);

			transportMasterVO.setCreatedBy(transportMasterDTO.getCreatedBy());
			transportMasterVO.setUpdated_By(transportMasterDTO.getCreatedBy());

			message = "Transport Created Successfully";
		}

		transportRepo.save(transportMasterVO);

		Map<String, Object> response = new HashMap<>();
		response.put("transportMasterVO", transportMasterVO);
		response.put("message", message);

		return response;
	}

	private void createUpdateTransportMasterVOByTransportMasterDTO(TransportMasterDTO transportMasterDTO,
			TransportMasterVO transportMasterVO) throws ApplicationException {

		transportMasterVO.setTransportName(transportMasterDTO.getTransportName().toUpperCase());
		transportMasterVO.setAddress(transportMasterDTO.getAddress());
		transportMasterVO.setOrgId(transportMasterDTO.getOrgId());
		transportMasterVO.setActive(transportMasterDTO.getActive());
		transportMasterVO.setCancelRemarks(transportMasterDTO.getCancelRemarks());
		if (transportMasterDTO.getBranch() != null && transportMasterDTO.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(transportMasterDTO.getBranch())
					.orElseThrow(() -> new ApplicationException("branch Not Found"));

			transportMasterVO.setBranch(branch);
		}
	}

	@Override
	public TransportMasterVO getTransportNameById(Long id) throws ApplicationException {

		return transportRepo.findById(id).orElseThrow(() -> new ApplicationException("Invalid Transport Details"));
	}

	@Override
	public List<TransportMasterVO> getTransportNameByOrgId(Long orgId, Long branchCode) throws ApplicationException {

		List<TransportMasterVO> transportList = transportRepo.findByOrgIdAndBranch(orgId, branchCode);

		if (transportList.isEmpty()) {
			throw new ApplicationException("No Transport Details Found");
		}

		return transportList;
	}

	@Override
	@Transactional
	public Map<String, Object> updateCreateListOfValues(@Valid ListOfValuesDTO dto) throws ApplicationException {

		ListOfValuesVO listVO = new ListOfValuesVO();
		String message;

		if (ObjectUtils.isNotEmpty(dto.getId())) {

			listVO = listOfValuesRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("List Of Values Not Found"));

			listVO.setUpdatedBy(dto.getCreatedBy());

			if (!listVO.getListCode().equalsIgnoreCase(dto.getListCode())) {

				if (listOfValuesRepo.existsByListCodeAndOrgId(dto.getListCode(), dto.getOrgId())) {

					throw new ApplicationException("List Code already exists.");
				}
			}

			createUpdateListVO(dto, listVO);

			message = "List Of Values Updated Successfully";

		} else {

			if (listOfValuesRepo.existsByListCodeAndOrgId(dto.getListCode(), dto.getOrgId())) {

				throw new ApplicationException("List Code already exists.");
			}

			listVO.setCreatedBy(dto.getCreatedBy());
			listVO.setUpdatedBy(dto.getCreatedBy());

			createUpdateListVO(dto, listVO);

			message = "List Of Values Created Successfully";
		}

		listVO = listOfValuesRepo.save(listVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("listOfValuesVO", listVO);

		return response;
	}

	private void createUpdateListVO(ListOfValuesDTO dto, ListOfValuesVO listVO) throws ApplicationException {

		listVO.setListCode(dto.getListCode().toUpperCase());
		listVO.setListDescription(dto.getListDescription().toUpperCase());
		listVO.setOrgId(dto.getOrgId());
		listVO.setActive(dto.isActive());
		listVO.setCancelRemarks(dto.getCancelRemarks());

		if (dto.getBranch() != null && dto.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			listVO.setBranch(branch);
		}

		if (dto.getId() != null) {

			List<ListOfValuesDetailsVO> oldDetails = listOfValuesDetailsRepo.findByListOfValuesVO(listVO);

			listOfValuesDetailsRepo.deleteAll(oldDetails);
		}

		List<ListOfValuesDetailsVO> detailList = new ArrayList<>();

		for (ListOfValuesDetailsDTO detailDTO : dto.getDetails()) {

			ListOfValuesDetailsVO detailVO = new ListOfValuesDetailsVO();

			detailVO.setValueCode(detailDTO.getValueCode());
			detailVO.setValueDescription(detailDTO.getValueDescription());
			detailVO.setActive(detailDTO.isActive());

			detailVO.setListOfValuesVO(listVO);

			detailList.add(detailVO);
		}

		listVO.setListOfValuesDetailsVO(detailList);
	}

	@Override
	public ListOfValuesVO getListOfValuesById(Long id) {

		return listOfValuesRepo.getListOfValuesById(id);

	}

	@Override
	public List<ListOfValuesVO> getListOfValuesByOrgId(Long orgId, Long branchId) {

		return listOfValuesRepo.getListOfValuesByOrgId(orgId, branchId);
	}

	@Override
	public List<Map<String, Object>> getBudgetGroup(Long orgId, String name) throws ApplicationException {

		Set<Object[]> obj = listOfValuesRepo.getListValuesDetailsForBudget(orgId, name);
		return ListofValue(obj);
	}

	private List<Map<String, Object>> ListofValue(Set<Object[]> obj) {
		List<Map<String, Object>> details = new ArrayList<>();
		for (Object[] det : obj) {
			Map<String, Object> mp = new HashMap<>();
			mp.put("id", det[0] != null ? det[0].toString() : "");
			mp.put("valuesDescription", det[1] != null ? det[1].toString() : "");
			details.add(mp);
		}
		return details;
	}

	// GST Rate Master

	@Override
	@Transactional
	public Map<String, Object> updateCreateGSTRateMaster(@Valid GSTRateMasterDTO dto) throws ApplicationException {

		GSTRateMasterVO vo = new GSTRateMasterVO();
		String message;

		if (ObjectUtils.isNotEmpty(dto.getId())) {

			vo = gstRateMasterRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Invalid GST Rate Master"));

			// Duplicate Check
			if (!vo.getCategory().getId().equals(dto.getCategory())) {

				if (gstRateMasterRepo.existsByCategoryIdAndOrgId(dto.getCategory(), dto.getOrgId())) {

					throw new ApplicationException("GST Rate Master already exists.");
				}
			}

			createUpdateGSTRateMasterVOByDTO(dto, vo);

			vo.setUpdatedBy(dto.getCreatedBy());

			message = "GST Rate Master Updated Successfully";

		} else {

			// Duplicate Check
			if (gstRateMasterRepo.existsByCategoryIdAndOrgId(dto.getCategory(), dto.getOrgId())) {

				throw new ApplicationException("GST Rate Master already exists.");
			}

			createUpdateGSTRateMasterVOByDTO(dto, vo);

			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());

			message = "GST Rate Master Created Successfully";
		}

		gstRateMasterRepo.save(vo);

		Map<String, Object> map = new HashMap<>();
		map.put("gSTRateMasterVO", vo);
		map.put("message", message);

		return map;
	}

	private void createUpdateGSTRateMasterVOByDTO(GSTRateMasterDTO dto, GSTRateMasterVO vo)
			throws ApplicationException {

		// Category
		if (dto.getCategory() != null) {

			ListOfValuesVO category = listOfValuesRepo.findById(dto.getCategory())
					.orElseThrow(() -> new ApplicationException("Category Not Found"));

			vo.setCategory(category);
		}

		// HSN SAC Code
		if (dto.getHsnSacCode() != null) {

			HsnVO hsn = hsnRepo.findById(dto.getHsnSacCode())
					.orElseThrow(() -> new ApplicationException("HSN/SAC Code Not Found"));

			vo.setHsnSacCode(hsn);
		}

		// Branch
		if (dto.getBranch() != null && dto.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			vo.setBranch(branch);
		}

		vo.setDescription(dto.getDescription());
		vo.setWef(dto.getWef());
		vo.setTaxable(dto.isTaxable());
		vo.setRate(dto.getRate());
		vo.setIgst(dto.getIgst());
		vo.setSgst(dto.getSgst());
		vo.setCgst(dto.getCgst());
		vo.setDuplicateCheck(dto.isDuplicateCheck());
		vo.setOrgId(dto.getOrgId());
		vo.setFinancialYear(dto.getFinancialYear());
		vo.setCancelRemarks(dto.getCancelRemarks());
		vo.setActive(dto.isActive());
	}

	@Override
	public GSTRateMasterVO getGSTRateMasterById(Long id) throws ApplicationException {

		return gstRateMasterRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Invalid GST Rate Master Details"));
	}

	@Override
	public List<GSTRateMasterVO> getGSTRateByOrgId(Long orgId, Long branchId) throws ApplicationException {

		List<GSTRateMasterVO> gSTRateMasterVO = gstRateMasterRepo.getGSTRateByOrgId(orgId, branchId);

		if (gSTRateMasterVO.isEmpty()) {
			throw new ApplicationException("No GST Rate Master Details Found");
		}

		return gSTRateMasterVO;
	}

	// ServiceAccMaster

	@Override
	@Transactional
	public Map<String, Object> updateCreateServiceAccMaster(ServiceAccMasterDTO serviceAccMasterDTO)
			throws ApplicationException {

		ServiceAccMasterVO serviceAccMasterVO = new ServiceAccMasterVO();
		String message;

		if (ObjectUtils.isNotEmpty(serviceAccMasterDTO.getId())) {

			serviceAccMasterVO = serviceAccMasterRepo.findById(serviceAccMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("Service Accounting Master Not Found"));

			if (!serviceAccMasterVO.getServiceName().equalsIgnoreCase(serviceAccMasterDTO.getServiceName())) {

				if (serviceAccMasterRepo.existsByServiceNameAndOrgId(serviceAccMasterDTO.getServiceName(),
						serviceAccMasterDTO.getOrgId())) {

					throw new ApplicationException("The Service Accounting Master : "
							+ serviceAccMasterDTO.getServiceName() + " already exists in this Organization.");
				}
			}

			serviceAccMasterVO.setUpdatedBy(serviceAccMasterDTO.getCreatedBy());

			createUpdateServiceAccMasterVOByServiceAccMasterDTO(serviceAccMasterDTO, serviceAccMasterVO);

			message = "Service Accounting Master Updated Successfully";

		} else {

			if (serviceAccMasterRepo.existsByServiceNameAndOrgId(serviceAccMasterDTO.getServiceName(),
					serviceAccMasterDTO.getOrgId())) {

				throw new ApplicationException("The Service Accounting Master : " + serviceAccMasterDTO.getServiceName()
						+ " already exists in this Organization.");
			}

			serviceAccMasterVO.setCreatedBy(serviceAccMasterDTO.getCreatedBy());
			serviceAccMasterVO.setUpdatedBy(serviceAccMasterDTO.getCreatedBy());

			createUpdateServiceAccMasterVOByServiceAccMasterDTO(serviceAccMasterDTO, serviceAccMasterVO);

			message = "Service Accounting Master Created Successfully";
		}

		ServiceAccMasterVO savedServiceMaster = serviceAccMasterRepo.save(serviceAccMasterVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("serviceAccMasterVO", buildServiceAccMasterResponse(savedServiceMaster));

		return response;
	}

	private ServiceAccMasterResponseDTO buildServiceAccMasterResponse(ServiceAccMasterVO serviceAccMasterVO) {

		ServiceAccMasterResponseDTO responseDTO = new ServiceAccMasterResponseDTO();

		responseDTO.setId(serviceAccMasterVO.getId());
		responseDTO.setServiceName(serviceAccMasterVO.getServiceName());
		responseDTO.setServiceDescription(serviceAccMasterVO.getServiceDescription());

		if (serviceAccMasterVO.getBranch() != null) {

			BranchResponseDTO branchDTO = new BranchResponseDTO();
			branchDTO.setId(serviceAccMasterVO.getBranch().getId());
			branchDTO.setBranchName(serviceAccMasterVO.getBranch().getBranchName());

			responseDTO.setBranch(branchDTO);
		}

		if (serviceAccMasterVO.getHsnCode() != null) {

			HsnResponseImageDTO hsnDTO = new HsnResponseImageDTO();
			hsnDTO.setId(serviceAccMasterVO.getHsnCode().getId());
			hsnDTO.setHsnCode(serviceAccMasterVO.getHsnCode().getHsn());

			responseDTO.setItemHsn(hsnDTO);
		}

		responseDTO.setOrgId(serviceAccMasterVO.getOrgId());

		responseDTO.setCreatedBy(serviceAccMasterVO.getCreatedBy());
		responseDTO.setUpdatedBy(serviceAccMasterVO.getUpdatedBy());

		responseDTO.setCancelRemarks(serviceAccMasterVO.getCancelRemarks());

		return responseDTO;
	}

	private void createUpdateServiceAccMasterVOByServiceAccMasterDTO(@Valid ServiceAccMasterDTO serviceAccMasterDTO,
			ServiceAccMasterVO serviceAccMasterVO) throws ApplicationException {

		serviceAccMasterVO.setServiceName(serviceAccMasterDTO.getServiceName().toUpperCase());
		serviceAccMasterVO.setServiceDescription(serviceAccMasterDTO.getServiceDescription());
		serviceAccMasterVO.setOrgId(serviceAccMasterDTO.getOrgId());

		serviceAccMasterVO.setActive(serviceAccMasterDTO.isActive());
		serviceAccMasterVO.setCancelRemarks(serviceAccMasterDTO.getCancelRemarks());

		if (serviceAccMasterDTO.getBranchId() != null && serviceAccMasterDTO.getBranchId() != 0) {

			BranchVO branch = branchRepo.findById(serviceAccMasterDTO.getBranchId())
					.orElseThrow(() -> new ApplicationException("branch Not Found"));

			serviceAccMasterVO.setBranch(branch);
		}

		if (serviceAccMasterDTO.getHsnId() != null && serviceAccMasterDTO.getHsnId() != 0) {

			HsnVO hsnVO = hsnRepo.findById(serviceAccMasterDTO.getBranchId())
					.orElseThrow(() -> new ApplicationException("HSN Not Found"));

			serviceAccMasterVO.setHsnCode(hsnVO);
		}

	}

	@Override
	public ServiceAccMasterResponseDTO getServiceAccMasterById(Long id) throws ApplicationException {

		ServiceAccMasterVO ServiceAccMasterVO = serviceAccMasterRepo.getServiceAccMasterById(id);

		if (ServiceAccMasterVO == null) {
			throw new ApplicationException("ServiceAccountingMaster  Not Found");
		}

		return buildServiceAccMasterResponse(ServiceAccMasterVO);
	}

	@Override
	public List<ServiceAccMasterResponseDTO> getServiceAccMasterByOrgId(Long orgId, Long branchId)
			throws ApplicationException {

		List<ServiceAccMasterVO> employeeList = serviceAccMasterRepo.getServiceAccMasterByOrgId(orgId, branchId);

		if (employeeList == null || employeeList.isEmpty()) {
			throw new ApplicationException("ServiceAccountingMaster Not Found");
		}

		List<ServiceAccMasterResponseDTO> responseList = new ArrayList<>();

		for (ServiceAccMasterVO employeeMasterVO : employeeList) {
			responseList.add(buildServiceAccMasterResponse(employeeMasterVO));
		}

		return responseList;
	}
	// locationmaster

	@Override
	@Transactional
	public Map<String, Object> updateCreateLocationMaster(@Valid LocationDTO locationDTO) throws ApplicationException {

		LocationVO locationVO = new LocationVO();
		String message;

		if (ObjectUtils.isNotEmpty(locationDTO.getId())) {

			locationVO = locationRepo.findById(locationDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Location Details"));

			if (!locationVO.getLocationId().equalsIgnoreCase(locationDTO.getLocationId())) {

				if (locationRepo.existsByLocationIdAndOrgId(locationDTO.getLocationId(), locationDTO.getOrgId())) {

					throw new ApplicationException(
							"The Location : " + locationDTO.getLocationId() + " already exists in this Organization.");
				}
			}

			createUpdateLocationVOByLocationDTO(locationDTO, locationVO);

			locationVO.setUpdatedBy(locationDTO.getCreatedBy());

			message = "Location Updated Successfully";

		} else {

			if (locationRepo.existsByLocationIdAndOrgId(locationDTO.getLocationId(), locationDTO.getOrgId())) {

				throw new ApplicationException(
						"The Location : " + locationDTO.getLocationId() + " already exists in this Organization.");
			}

			createUpdateLocationVOByLocationDTO(locationDTO, locationVO);

			locationVO.setCreatedBy(locationDTO.getCreatedBy());
			locationVO.setUpdatedBy(locationDTO.getCreatedBy());

			message = "Location Created Successfully";
		}

		locationRepo.save(locationVO);

		Map<String, Object> response = new HashMap<>();
		response.put("locationVO", locationVO);
		response.put("message", message);

		return response;
	}

	private void createUpdateLocationVOByLocationDTO(LocationDTO locationDTO, LocationVO locationVO)
			throws ApplicationException {

		locationVO.setLocationId(locationDTO.getLocationId().toUpperCase());
		locationVO.setOrgId(locationDTO.getOrgId());
		locationVO.setPhoneNo(locationDTO.getPhoneNo());
		locationVO.setFaxNo(locationDTO.getFaxNo());
		locationVO.setEmail(locationDTO.getEmail());
		locationVO.setConsiderMrp(locationDTO.getConsiderMrp());
		locationVO.setAddress(locationDTO.getAddress());
		locationVO.setPhoneNo(locationDTO.getPhoneNo());
		locationVO.setLocationName(locationDTO.getLocationName());

		locationVO.setCancelRemarks(locationDTO.getCancelRemarks());
		if (locationDTO.getBranch() != null && locationDTO.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(locationDTO.getBranch())
					.orElseThrow(() -> new ApplicationException("branch Not Found"));

			locationVO.setBranch(branch);
		}
		if (locationDTO.getLocationType() != null && locationDTO.getLocationType() != 0) {

			ListOfValuesVO listOfValues = listOfValuesRepo.findById(locationDTO.getLocationType())
					.orElseThrow(() -> new ApplicationException("location type Not Found"));

			locationVO.setLocationType(listOfValues);
		}
		if (locationDTO.getBelongsTo() != null && locationDTO.getBelongsTo() != 0) {

			ListOfValuesVO listOfValues = listOfValuesRepo.findById(locationDTO.getBelongsTo())
					.orElseThrow(() -> new ApplicationException(" BelongsTo Not Found"));

			locationVO.setBelongsTo(listOfValues);
		}

	}

	@Override
	public LocationVO getLocationById(Long id) throws ApplicationException {

		return locationRepo.findById(id).orElseThrow(() -> new ApplicationException("Invalid Location Details"));
	}

	@Override
	public List<LocationVO> getLocationByOrgId(Long orgId, Long branchCode) throws ApplicationException {

		List<LocationVO> transportList = locationRepo.findByOrgIdAndBranch(orgId, branchCode);

		if (transportList.isEmpty()) {
			throw new ApplicationException("No Location Details Found");
		}

		return transportList;
	}

	// LME
	@Override
	@Transactional
	public Map<String, Object> updateCreateLMEMaster(@Valid LMEDTO lMEDTO) throws ApplicationException {

		LMEVO lMEVO = new LMEVO();
		String message;

		if (ObjectUtils.isNotEmpty(lMEDTO.getId())) {

			lMEVO = lMERepo.findById(lMEDTO.getId()).orElseThrow(() -> new ApplicationException("Invalid LME Details"));

			if (!lMEVO.getId().equals(lMEDTO.getId())) {

				if (lMERepo.existsByIdAndOrgId(lMEDTO.getId(), lMEDTO.getOrgId())) {

					throw new ApplicationException(
							"The LME : " + lMEDTO.getId() + " already exists in this Organization.");
				}
			}

			createUpdateLMEVOByLMEDTO(lMEDTO, lMEVO);

			lMEVO.setUpdatedBy(lMEDTO.getCreatedBy());

			message = "LME Updated Successfully";

		} else {

			if (lMERepo.existsByIdAndOrgId(lMEDTO.getId(), lMEDTO.getOrgId())) {

				throw new ApplicationException("The LME : " + lMEDTO.getId() + " already exists in this Organization.");
			}

			createUpdateLMEVOByLMEDTO(lMEDTO, lMEVO);

			lMEVO.setCreatedBy(lMEDTO.getCreatedBy());
			lMEVO.setUpdatedBy(lMEDTO.getCreatedBy());

			message = "LME Created Successfully";
		}

		lMERepo.save(lMEVO);

		Map<String, Object> response = new HashMap<>();
		response.put("lMEVO", lMEVO);
		response.put("message", message);

		return response;
	}

	private void createUpdateLMEVOByLMEDTO(LMEDTO lMEDTO, LMEVO lMEVO) throws ApplicationException {

		lMEVO.setOrgId(lMEDTO.getOrgId());
		lMEVO.setLmeRate(lMEDTO.getLmeRate());
		lMEVO.setLmeDateFrom(lMEDTO.getLmeDateFrom());
		lMEVO.setElmeDateTo(lMEDTO.getElmeDateTo());
		lMEVO.setFinYear(lMEDTO.getFinyear());
		lMEVO.setActive(lMEDTO.getActive());
		lMEVO.setCreatedBy(lMEDTO.getCreatedBy());

		lMEVO.setCancelRemarks(lMEDTO.getCancelRemarks());
		if (lMEDTO.getBranch() != null && lMEDTO.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(lMEDTO.getBranch())
					.orElseThrow(() -> new ApplicationException("branch Not Found"));

			lMEVO.setBranch(branch);
		}
		if (lMEDTO.getCurrencyName() != null && lMEDTO.getCurrencyName() != 0) {

			CurrencyVO currencyVO = currencyRepo.findById(lMEDTO.getCurrencyName())
					.orElseThrow(() -> new ApplicationException(" Currency Name Not Found"));

			lMEVO.setCurrencyName(currencyVO);
		}

	}

	@Override
	public LMEVO getLMEById(Long id) throws ApplicationException {

		return lMERepo.findById(id).orElseThrow(() -> new ApplicationException("Invalid LMES Details"));
	}

	@Override
	public List<LMEVO> getLMEByOrgId(Long orgId, Long branchCode) throws ApplicationException {

		List<LMEVO> transportList = lMERepo.findByOrgIdAndBranch(orgId, branchCode);

		if (transportList.isEmpty()) {
			throw new ApplicationException("No LME Details Found");
		}

		return transportList;
	}

	// Financial Year
	@Override
	@Transactional
	public Map<String, Object> createUpdateFinancialYear(@Valid FinancialYearDTO financialYearDTO)
			throws ApplicationException {

		FinancialYearVO financialYearVO = new FinancialYearVO();
		String message;

		if (ObjectUtils.isNotEmpty(financialYearDTO.getId())) {

			financialYearVO = financialYearRepo.findById(financialYearDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Financial Year Details"));

			if (!financialYearVO.getId().equals(financialYearDTO.getId())) {

				if (financialYearRepo.existsByIdAndOrgId(financialYearDTO.getId(), financialYearDTO.getOrgId())) {

					throw new ApplicationException("The Financial Year : " + financialYearDTO.getId()
							+ " already exists in this Organization.");
				}
			}

			createUpdateFinancialYearVOByFinancialYearDTO(financialYearDTO, financialYearVO);

			financialYearVO.setUpdatedBy(financialYearDTO.getCreatedBy());

			message = "Financil Year Updated Successfully";

		} else {

			if (financialYearRepo.existsByIdAndOrgId(financialYearDTO.getId(), financialYearDTO.getOrgId())) {

				throw new ApplicationException(
						"The Financil Year  : " + financialYearDTO.getId() + " already exists in this Organization.");
			}

			createUpdateFinancialYearVOByFinancialYearDTO(financialYearDTO, financialYearVO);

			financialYearVO.setCreatedBy(financialYearDTO.getCreatedBy());
			financialYearVO.setUpdatedBy(financialYearDTO.getCreatedBy());

			message = "Financil Year Created Successfully";
		}

		financialYearRepo.save(financialYearVO);

		Map<String, Object> response = new HashMap<>();
		response.put("financialYearVO", financialYearVO);
		response.put("message", message);

		return response;
	}

	private void createUpdateFinancialYearVOByFinancialYearDTO(FinancialYearDTO financialYearDTO,
			FinancialYearVO financialYearVO) throws ApplicationException {

		financialYearVO.setOrgId(financialYearDTO.getOrgId());
		financialYearVO.setFinYear(financialYearDTO.getFinYear());
		financialYearVO.setStartDate(financialYearDTO.getStartDate());
		financialYearVO.setEndDate(financialYearDTO.getEndDate());
		financialYearVO.setCreatedBy(financialYearDTO.getCreatedBy());
		financialYearVO.setActive(financialYearDTO.isActive());
		financialYearVO.setCancelRemarks(financialYearDTO.getCancelRemarks());

	}

	@Override
	public FinancialYearVO getFinancialYearById(Long id) throws ApplicationException {

		return financialYearRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Invalid Financial Year Details"));
	}

	@Override
	public List<FinancialYearVO> getFinancialYearByOrgId(Long orgId) throws ApplicationException {

		List<FinancialYearVO> transportList = financialYearRepo.findFinancialYearByOrgId(orgId);

		if (transportList.isEmpty()) {
			throw new ApplicationException("No Financial Year Details Found");
		}

		return transportList;
	}

	// HSN

	@Override
	public List<HsnVO> getHsnByOrgId(Long orgId, Long branch) {
		return hsnRepo.findByOrgId(orgId, branch);
	}

	@Override
	public Optional<HsnVO> getHSNById(Long hsnId) {
		return hsnRepo.findById(hsnId);
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateHSN(HsnDTO hsnDTO) throws ApplicationException {

		HsnVO hsnVO;
		String message;
		HsnVO oldHSN = null;

		ListOfValuesVO category = listOfValuesRepo.findById(hsnDTO.getCategory())
				.orElseThrow(() -> new ApplicationException("Category not found with id : " + hsnDTO.getCategory()));

		if (ObjectUtils.isEmpty(hsnDTO.getId())) {

			if (hsnRepo.existsByOrgIdAndCategoryAndHsnIgnoreCase(hsnDTO.getOrgId(), category, hsnDTO.getHsn())) {

				throw new ApplicationException(
						"This HSN : " + hsnDTO.getHsn() + " Already Exists in This Organization.");
			}

			hsnVO = new HsnVO();
			hsnVO.setCreatedBy(hsnDTO.getCreatedBy());
			hsnVO.setUpdatedBy(hsnDTO.getCreatedBy());

			message = "HSN Created Successfully";

		} else {

			oldHSN = hsnRepo.findById(hsnDTO.getId())
					.orElseThrow(() -> new ApplicationException("HSN Master not found"));

			entityManager.detach(oldHSN);

			hsnVO = hsnRepo.findById(hsnDTO.getId())
					.orElseThrow(() -> new ApplicationException("This Id Is Not Found : " + hsnDTO.getId()));

			hsnVO.setUpdatedBy(hsnDTO.getCreatedBy());

			if (!hsnVO.getHsn().equalsIgnoreCase(hsnDTO.getHsn())) {

				if (hsnRepo.existsByOrgIdAndCategoryAndHsnIgnoreCase(hsnDTO.getOrgId(), category, hsnDTO.getHsn())) {

					throw new ApplicationException(
							"This HSN : " + hsnDTO.getHsn() + " Already Exists in This Organization.");
				}

				hsnVO.setHsn(hsnDTO.getHsn().toUpperCase());
			}

			if (hsnDTO.getDescription() != null) {

				if (hsnVO.getDescription() == null
						|| !hsnVO.getDescription().equalsIgnoreCase(hsnDTO.getDescription())) {

					hsnVO.setDescription(hsnDTO.getDescription().toUpperCase());
				}
			}

			message = "HSN Updated Successfully";
		}

		getHSNVOFromDTO(hsnVO, hsnDTO);

		hsnRepo.save(hsnVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("hsnVO", hsnVO);

		return response;
	}

	private void getHSNVOFromDTO(HsnVO hsnVO, HsnDTO hsnDTO) throws ApplicationException {

		hsnVO.setHsn(hsnDTO.getHsn().toUpperCase());

		if (hsnDTO.getDescription() != null) {
			hsnVO.setDescription(hsnDTO.getDescription().toUpperCase());
		}

		hsnVO.setActive(hsnDTO.isActive());
		hsnVO.setOrgId(hsnDTO.getOrgId());
		hsnVO.setCancelRemarks(hsnDTO.getCancelRemarks());

		if (hsnDTO.getCategory() != null && hsnDTO.getCategory() != 0) {

			ListOfValuesVO category = listOfValuesRepo.findById(hsnDTO.getCategory()).orElseThrow(
					() -> new ApplicationException("Category not found with id : " + hsnDTO.getCategory()));

			hsnVO.setCategory(category);
		}
		if (hsnDTO.getBranch() != null && hsnDTO.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(hsnDTO.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			hsnVO.setBranch(branch);
		}
	}

	// Unit Master

	@Override
	public List<UnitMasterVO> getUnitMasterByOrgId(Long orgId, Long branch) {
		return unitMasterRepo.findByOrgIdAndBranch(orgId, branch);
	}

	@Override
	public Optional<UnitMasterVO> getUnitMasterById(Long id) {
		return unitMasterRepo.findById(id);
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateUnitMaster(UnitMasterDTO unitMasterDTO) throws ApplicationException {

		UnitMasterVO unitMasterVO;
		String message;
		UnitMasterVO oldUnitMaster = null;

		if (ObjectUtils.isEmpty(unitMasterDTO.getId())) {

			if (unitMasterRepo.existsByOrgIdAndUnitIdIgnoreCase(unitMasterDTO.getOrgId(), unitMasterDTO.getUnitId())) {

				throw new ApplicationException(
						"This Unit Id : " + unitMasterDTO.getUnitId() + " Already Exists in This Organization.");
			}

			unitMasterVO = new UnitMasterVO();
			unitMasterVO.setCreatedBy(unitMasterDTO.getCreatedBy());
			unitMasterVO.setUpdatedBy(unitMasterDTO.getCreatedBy());

			message = "Unit Master Created Successfully";

		} else {

			oldUnitMaster = unitMasterRepo.findById(unitMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("Unit Master not found"));

			entityManager.detach(oldUnitMaster);

			unitMasterVO = unitMasterRepo.findById(unitMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("This Id Is Not Found : " + unitMasterDTO.getId()));

			unitMasterVO.setUpdatedBy(unitMasterDTO.getCreatedBy());

			if (!unitMasterVO.getUnitId().equalsIgnoreCase(unitMasterDTO.getUnitId())) {

				if (unitMasterRepo.existsByOrgIdAndUnitIdIgnoreCase(unitMasterDTO.getOrgId(),
						unitMasterDTO.getUnitId())) {

					throw new ApplicationException(
							"This Unit Id : " + unitMasterDTO.getUnitId() + " Already Exists in This Organization.");
				}

				unitMasterVO.setUnitId(unitMasterDTO.getUnitId().toUpperCase());
			}

			message = "Unit Master Updated Successfully";
		}

		getUnitMasterVOFromDTO(unitMasterVO, unitMasterDTO);

		unitMasterRepo.save(unitMasterVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("unitMasterVO", unitMasterVO);

		return response;
	}

	private void getUnitMasterVOFromDTO(UnitMasterVO unitMasterVO, UnitMasterDTO unitMasterDTO)
			throws ApplicationException {

		unitMasterVO.setUnitId(unitMasterDTO.getUnitId().toUpperCase());
		unitMasterVO.setOrgId(unitMasterDTO.getOrgId());
		unitMasterVO.setActive(unitMasterDTO.isActive());
		unitMasterVO.setCancelRemarks(unitMasterDTO.getCancelRemarks());

		if (unitMasterDTO.getBranch() != null && unitMasterDTO.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(unitMasterDTO.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			unitMasterVO.setBranch(branch);
		}
	}
	// Uom Conversion

	@Override
	public List<UomConversionVO> getUomConversionByOrgId(Long orgId, Long branch) {
		return uomConversionRepo.findByOrgIdAndBranch(orgId, branch);
	}

	@Override
	public Optional<UomConversionVO> getUomConversionById(Long id) {
		return uomConversionRepo.findById(id);
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateUomConversion(UomConversionDTO uomConversionDTO)
			throws ApplicationException {

		UomConversionVO uomConversionVO;
		String message = null;
		UomConversionVO oldUomConversion = null;

		if (ObjectUtils.isEmpty(uomConversionDTO.getId())) {

			if (uomConversionRepo.existsByOrgIdAndFromUnitAndToUnit(uomConversionDTO.getOrgId(),
					uomConversionDTO.getFromUnit(), uomConversionDTO.getToUnit())) {

				String errorMessage = String.format("This Conversion Already Exists in This Organization.");

				throw new ApplicationException(errorMessage);
			}

			uomConversionVO = new UomConversionVO();
			uomConversionVO.setCreatedBy(uomConversionDTO.getCreatedBy());
			uomConversionVO.setUpdatedBy(uomConversionDTO.getCreatedBy());

			message = "UOM Conversion Created Successfully";

		} else {

			oldUomConversion = uomConversionRepo.findById(uomConversionDTO.getId())
					.orElseThrow(() -> new ApplicationException("UOM Conversion not found"));

			entityManager.detach(oldUomConversion);

			uomConversionVO = uomConversionRepo.findById(uomConversionDTO.getId())
					.orElseThrow(() -> new ApplicationException("This Id Is Not Found : " + uomConversionDTO.getId()));

			uomConversionVO.setUpdatedBy(uomConversionDTO.getCreatedBy());

			if (!uomConversionVO.getFromUnit().equals(uomConversionDTO.getFromUnit())
					|| !uomConversionVO.getToUnit().equals(uomConversionDTO.getToUnit())) {

				if (uomConversionRepo.existsByOrgIdAndFromUnitAndToUnit(uomConversionDTO.getOrgId(),
						uomConversionDTO.getFromUnit(), uomConversionDTO.getToUnit())) {

					String errorMessage = "This Conversion Already Exists in This Organization.";
					throw new ApplicationException(errorMessage);
				}

				uomConversionVO.setFromUnit(uomConversionDTO.getFromUnit());
				uomConversionVO.setToUnit(uomConversionDTO.getToUnit());
			}

			uomConversionVO.setMultiplicationFactor(uomConversionDTO.getMultiplicationFactor());

			message = "UOM Conversion Updated Successfully";
		}

		getUomConversionVOFromDTO(uomConversionVO, uomConversionDTO);

		uomConversionRepo.save(uomConversionVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("uomConversionVO", uomConversionVO);

		return response;
	}

	private void getUomConversionVOFromDTO(UomConversionVO uomConversionVO, UomConversionDTO uomConversionDTO)
			throws ApplicationException {

		uomConversionVO.setFromUnit(uomConversionDTO.getFromUnit());
		uomConversionVO.setToUnit(uomConversionDTO.getToUnit());
		uomConversionVO.setMultiplicationFactor(uomConversionDTO.getMultiplicationFactor());

		uomConversionVO.setOrgId(uomConversionDTO.getOrgId());
		uomConversionVO.setActive(uomConversionDTO.isActive());
		uomConversionVO.setCancelRemarks(uomConversionDTO.getCancelRemarks());

		if (uomConversionDTO.getBranch() != null && uomConversionDTO.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(uomConversionDTO.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			uomConversionVO.setBranch(branch);
		}

	}

	// Grade Master

	@Override
	public List<GradeMasterVO> getGradeMasterByOrgId(Long orgId, Long branch) {
		return gradeMasterRepo.findByOrgIdAndBranch(orgId, branch);
	}

	@Override
	public Optional<GradeMasterVO> getGradeMasterById(Long id) {
		return gradeMasterRepo.findById(id);
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateGradeMaster(GradeMasterDTO gradeMasterDTO) throws ApplicationException {

		GradeMasterVO gradeMasterVO;
		String message = null;
		GradeMasterVO oldGradeMaster = null;

		if (ObjectUtils.isEmpty(gradeMasterDTO.getId())) {

			if (gradeMasterRepo.existsByOrgIdAndGradeCodeIgnoreCase(gradeMasterDTO.getOrgId(),
					gradeMasterDTO.getGradeCode())) {

				String errorMessage = String.format("This Grade Code : %s Already Exists in This Organization.",
						gradeMasterDTO.getGradeCode());

				throw new ApplicationException(errorMessage);
			}

			gradeMasterVO = new GradeMasterVO();
			gradeMasterVO.setCreatedBy(gradeMasterDTO.getCreatedBy());
			gradeMasterVO.setUpdatedBy(gradeMasterDTO.getCreatedBy());

			message = "Grade Master Created Successfully";

		} else {

			oldGradeMaster = gradeMasterRepo.findById(gradeMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("Grade Master not found"));

			entityManager.detach(oldGradeMaster);

			gradeMasterVO = gradeMasterRepo.findById(gradeMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("This Id Is Not Found : " + gradeMasterDTO.getId()));

			gradeMasterVO.setUpdatedBy(gradeMasterDTO.getCreatedBy());

			if (!gradeMasterVO.getGradeCode().equalsIgnoreCase(gradeMasterDTO.getGradeCode())) {

				if (gradeMasterRepo.existsByOrgIdAndGradeCodeIgnoreCase(gradeMasterDTO.getOrgId(),
						gradeMasterDTO.getGradeCode())) {

					String errorMessage = String.format("This Grade Code : %s Already Exists in This Organization.",
							gradeMasterDTO.getGradeCode());

					throw new ApplicationException(errorMessage);
				}

				gradeMasterVO.setGradeCode(gradeMasterDTO.getGradeCode().toUpperCase());
			}

			if (!gradeMasterVO.getGradeDescription().equalsIgnoreCase(gradeMasterDTO.getGradeDescription())) {

				gradeMasterVO.setGradeDescription(gradeMasterDTO.getGradeDescription().toUpperCase());
			}

			if (gradeMasterDTO.getRemarks() != null) {
				gradeMasterVO.setRemarks(gradeMasterDTO.getRemarks().toUpperCase());
			}

			message = "Grade Master Updated Successfully";
		}

		getGradeMasterVOFromDTO(gradeMasterVO, gradeMasterDTO);

		gradeMasterRepo.save(gradeMasterVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("gradeMasterVO", gradeMasterVO);

		return response;
	}

	private void getGradeMasterVOFromDTO(GradeMasterVO gradeMasterVO, GradeMasterDTO gradeMasterDTO)
			throws ApplicationException {

		gradeMasterVO.setGradeCode(gradeMasterDTO.getGradeCode().toUpperCase());
		gradeMasterVO.setGradeDescription(gradeMasterDTO.getGradeDescription().toUpperCase());

		if (gradeMasterDTO.getRemarks() != null) {
			gradeMasterVO.setRemarks(gradeMasterDTO.getRemarks().toUpperCase());
		}

		gradeMasterVO.setActive(gradeMasterDTO.isActive());
		gradeMasterVO.setOrgId(gradeMasterDTO.getOrgId());
		gradeMasterVO.setCancelRemarks(gradeMasterDTO.getCancelRemarks());

		if (gradeMasterDTO.getBranch() != null && gradeMasterDTO.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(gradeMasterDTO.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			gradeMasterVO.setBranch(branch);
		}
	}

	// GSTStateMaster

	@Override
	public List<GSTStateMasterVO> getGSTStateMasterByOrgId(Long orgId, Long branch) {
		return gstStateMasterRepo.findByGSTStateMasterByOrgId(orgId, branch);
	}

	@Override
	public Optional<GSTStateMasterVO> getGSTStateMasterById(Long id) {
		return gstStateMasterRepo.findById(id);
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateGSTStateMaster(GSTStateMasterDTO gstStateMasterDTO)
			throws ApplicationException {

		GSTStateMasterVO gstStateMasterVO;
		String message;
		GSTStateMasterVO oldGSTStateMaster = null;

		if (ObjectUtils.isEmpty(gstStateMasterDTO.getId())) {

			if (gstStateMasterRepo.existsByOrgIdAndStateCodeIgnoreCase(gstStateMasterDTO.getOrgId(),
					gstStateMasterDTO.getStateCode())) {

				String errorMessage = String.format("This State Code : %s Already Exists in This Organization.",
						gstStateMasterDTO.getStateCode());

				throw new ApplicationException(errorMessage);
			}

			gstStateMasterVO = new GSTStateMasterVO();
			gstStateMasterVO.setCreatedBy(gstStateMasterDTO.getCreatedBy());
			gstStateMasterVO.setUpdatedBy(gstStateMasterDTO.getCreatedBy());

			message = "GST State Master Created Successfully";

		} else {

			oldGSTStateMaster = gstStateMasterRepo.findById(gstStateMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("GST State Master not found"));

			entityManager.detach(oldGSTStateMaster);

			gstStateMasterVO = gstStateMasterRepo.findById(gstStateMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("This Id Is Not Found : " + gstStateMasterDTO.getId()));

			gstStateMasterVO.setUpdatedBy(gstStateMasterDTO.getCreatedBy());

			if (!gstStateMasterVO.getStateCode().equalsIgnoreCase(gstStateMasterDTO.getStateCode())) {

				if (gstStateMasterRepo.existsByOrgIdAndStateCodeIgnoreCase(gstStateMasterDTO.getOrgId(),
						gstStateMasterDTO.getStateCode())) {

					String errorMessage = String.format("This State Code : %s Already Exists in This Organization.",
							gstStateMasterDTO.getStateCode());

					throw new ApplicationException(errorMessage);
				}

				gstStateMasterVO.setStateCode(gstStateMasterDTO.getStateCode().toUpperCase());
			}

			if (!gstStateMasterVO.getStateName().equalsIgnoreCase(gstStateMasterDTO.getStateName())) {

				gstStateMasterVO.setStateName(gstStateMasterDTO.getStateName().toUpperCase());
			}

			if (!gstStateMasterVO.getGstStateId().equalsIgnoreCase(gstStateMasterDTO.getGstStateId())) {

				gstStateMasterVO.setGstStateId(gstStateMasterDTO.getGstStateId().toUpperCase());
			}

			message = "GST State Master Updated Successfully";
		}

		getGSTStateMasterVOFromDTO(gstStateMasterVO, gstStateMasterDTO);

		gstStateMasterRepo.save(gstStateMasterVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("gstStateMasterVO", gstStateMasterVO);

		return response;
	}

	private void getGSTStateMasterVOFromDTO(GSTStateMasterVO gstStateMasterVO, GSTStateMasterDTO gstStateMasterDTO)
			throws ApplicationException {

		gstStateMasterVO.setStateCode(gstStateMasterDTO.getStateCode().toUpperCase());

		gstStateMasterVO.setStateName(gstStateMasterDTO.getStateName().toUpperCase());

		gstStateMasterVO.setGstStateId(gstStateMasterDTO.getGstStateId().toUpperCase());

		gstStateMasterVO.setOrgId(gstStateMasterDTO.getOrgId());
		gstStateMasterVO.setActive(gstStateMasterDTO.isActive());
		gstStateMasterVO.setCancelRemarks(gstStateMasterDTO.getCancelRemarks());

		if (gstStateMasterDTO.getBranch() != null && gstStateMasterDTO.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(gstStateMasterDTO.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			gstStateMasterVO.setBranch(branch);
		}
	}

	// DocumentTypeMaster

	@Override
	public List<DocumentTypeMasterVO> getDocumentTypeMasterByOrgId(Long orgId, Long branch) {
		return documentTypeMasterRepo.findByOrgIdAndBranch(orgId, branch);
	}

	@Override
	public Optional<DocumentTypeMasterVO> getDocumentTypeMasterById(Long id) {
		return documentTypeMasterRepo.findById(id);
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateDocumentTypeMaster(DocumentTypeMasterDTO documentTypeMasterDTO)
			throws ApplicationException {

		DocumentTypeMasterVO documentTypeMasterVO;
		String message;
		DocumentTypeMasterVO oldDocumentTypeMaster = null;

		if (ObjectUtils.isEmpty(documentTypeMasterDTO.getId())) {

			if (documentTypeMasterRepo.existsByOrgIdAndCodeIgnoreCase(documentTypeMasterDTO.getOrgId(),
					documentTypeMasterDTO.getCode())) {

				throw new ApplicationException("Document Type Code : " + documentTypeMasterDTO.getCode()
						+ " Already Exists in This Organization.");
			}

			documentTypeMasterVO = new DocumentTypeMasterVO();
			documentTypeMasterVO.setCreatedBy(documentTypeMasterDTO.getCreatedBy());
			documentTypeMasterVO.setUpdatedBy(documentTypeMasterDTO.getCreatedBy());

			message = "Document Type Master Created Successfully";

		} else {

			oldDocumentTypeMaster = documentTypeMasterRepo.findById(documentTypeMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("Document Type Master Not Found"));

			entityManager.detach(oldDocumentTypeMaster);

			documentTypeMasterVO = documentTypeMasterRepo.findById(documentTypeMasterDTO.getId()).orElseThrow(
					() -> new ApplicationException("This Id Is Not Found : " + documentTypeMasterDTO.getId()));

			documentTypeMasterVO.setUpdatedBy(documentTypeMasterDTO.getCreatedBy());

			if (!documentTypeMasterVO.getCode().equalsIgnoreCase(documentTypeMasterDTO.getCode())) {

				if (documentTypeMasterRepo.existsByOrgIdAndCodeIgnoreCase(documentTypeMasterDTO.getOrgId(),
						documentTypeMasterDTO.getCode())) {

					throw new ApplicationException("Document Type Code : " + documentTypeMasterDTO.getCode()
							+ " Already Exists in This Organization.");
				}

				documentTypeMasterVO.setCode(documentTypeMasterDTO.getCode().toUpperCase());
			}

			message = "Document Type Master Updated Successfully";
		}

		getDocumentTypeMasterVOFromDTO(documentTypeMasterVO, documentTypeMasterDTO);

		documentTypeMasterRepo.save(documentTypeMasterVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("documentTypeMasterVO", documentTypeMasterVO);

		return response;
	}

	private void getDocumentTypeMasterVOFromDTO(DocumentTypeMasterVO documentTypeMasterVO,
			DocumentTypeMasterDTO documentTypeMasterDTO) throws ApplicationException {

		documentTypeMasterVO.setCode(documentTypeMasterDTO.getCode().toUpperCase());

		if (documentTypeMasterDTO.getName() != null) {
			documentTypeMasterVO.setName(documentTypeMasterDTO.getName().toUpperCase());
		}

		if (documentTypeMasterDTO.getDes() != null) {
			documentTypeMasterVO.setDescription(documentTypeMasterDTO.getDescription().toUpperCase());
		}

		if (documentTypeMasterDTO.getDocCode() != null) {
			documentTypeMasterVO.setDocCode(documentTypeMasterDTO.getDocCode().toUpperCase());
		}

		documentTypeMasterVO.setOrgId(documentTypeMasterDTO.getOrgId());
		documentTypeMasterVO.setActive(documentTypeMasterDTO.isActive());
		documentTypeMasterVO.setCancelRemarks(documentTypeMasterDTO.getCancelRemarks());

		if (documentTypeMasterDTO.getBranch() != null && documentTypeMasterDTO.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(documentTypeMasterDTO.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			documentTypeMasterVO.setBranch(branch);
		}
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateBankMaster(@Valid TSBankDTO tSBankDTO) throws ApplicationException {

		TSBankVO tSBankVO = new TSBankVO();
		String message;

		if (ObjectUtils.isNotEmpty(tSBankDTO.getId())) {

			tSBankVO = tSBankRepo.findById(tSBankDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Bank Rate Master Details"));

//								          if (!tSBankVO.getbank()
//								                  .equalsIgnoreCase(tSBankDTO.getBank())) {
			//
//								              if (tSBankRepo.existsByBankAndOrgId(
//								            		  tSBankDTO.getBank(),
//								            		  tSBankDTO.getOrgId())) {
			//
//								                  throw new ApplicationException(
//								                          "The Bank  Master : " + tSBankDTO.getBank()
//								                                  + " already exists in this Organization.");
//								              }
//								          }

			createUpdateTSBankVOByTSBankDTO(tSBankDTO, tSBankDTO);

			tSBankVO.setUpdatedBy(tSBankDTO.getCreatedBy());

			message = "Bank master Updated Successfully";

		} else {

			if (tSBankRepo.existsByBankAndOrgId(tSBankDTO.getBank(), tSBankDTO.getOrgId())) {

				throw new ApplicationException(
						"The Bank  Master : " + tSBankDTO.getBank() + " already exists in this Organization.");
			}

			createUpdateTSBankVOByTSBankDTO(tSBankDTO, tSBankDTO);

			tSBankVO.setCreatedBy(tSBankDTO.getCreatedBy());
			tSBankVO.setUpdatedBy(tSBankDTO.getCreatedBy());
			tSBankVO.setBeneficiary(tSBankDTO.getBeneficiary());
			tSBankVO.setBank(tSBankDTO.getBank());
			tSBankVO.setAcno(tSBankDTO.getAcno());
			tSBankVO.setIfscCode(tSBankDTO.getIfscCode());
			tSBankVO.setBranch(tSBankDTO.getBranch());
			tSBankVO.setCancelRemarks(tSBankDTO.getCancelRemarks());
			tSBankVO.setActive(tSBankDTO.isActive());
			tSBankVO.setOrgId(tSBankDTO.getOrgId());

			message = "Bank Master Created Successfully";
		}

		tSBankRepo.save(tSBankVO);

		Map<String, Object> response = new HashMap<>();
		response.put("tSBankVO", tSBankVO);
		response.put("message", message);

		return response;
	}

	private void createUpdateTSBankVOByTSBankDTO(@Valid TSBankDTO tSBankDTO, @Valid TSBankDTO tSBankDTO2)
			throws ApplicationException {

		tSBankDTO2.setBank(tSBankDTO.getBank().toUpperCase());
		tSBankDTO2.setBeneficiary(tSBankDTO.getBeneficiary());
		tSBankDTO2.setOrgId(tSBankDTO.getOrgId());
		tSBankDTO2.setAcno(tSBankDTO.getAcno());
		tSBankDTO2.setBranch(tSBankDTO.getBranch());
		tSBankDTO2.setIfscCode(tSBankDTO.getIfscCode());
		tSBankDTO2.setCreatedBy(tSBankDTO.getCreatedBy());
		tSBankDTO2.setCancelRemarks(tSBankDTO.getCancelRemarks());
		tSBankDTO2.setActive(tSBankDTO.isActive());

	}

	@Override
	public TSBankVO getBankMasterById(Long id) throws ApplicationException {

		return tSBankRepo.findById(id).orElseThrow(() -> new ApplicationException("Invalid Bank  Master Details"));
	}

	@Override
	public List<TSBankVO> getBankMasterByOrgId(Long orgId) throws ApplicationException {

		List<TSBankVO> tSBankVO = tSBankRepo.getBankMasterByOrgId(orgId);

		if (tSBankVO.isEmpty()) {
			throw new ApplicationException("No Bank Master Details Found");
		}

		return tSBankVO;
	}

	// TAX Definition
	@Override
	@Transactional
	public Map<String, Object> updateCreateTaxDefinition(@Valid TaxDefinitionDTO dto) throws ApplicationException {

		TaxDefinitionVO taxDefinitionVO = new TaxDefinitionVO();
		String message;

		if (ObjectUtils.isNotEmpty(dto.getId()) && dto.getId() != 0) {

			taxDefinitionVO = taxDefinitionRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Tax Definition"));

			taxDefinitionVO.setUpdatedBy(dto.getCreatedBy());

			if (!taxDefinitionVO.getTaxNo().equals(dto.getTaxNo())) {

				if (taxDefinitionRepo.existsByTaxNoAndOrgId(dto.getTaxNo(), dto.getOrgId())) {

					throw new ApplicationException("Tax No already exists.");
				}
			}

			createUpdateTaxDefinitionVO(dto, taxDefinitionVO);

			message = "Tax Definition Updated Successfully";

		} else {

			if (taxDefinitionRepo.existsByTaxNoAndOrgId(dto.getTaxNo(), dto.getOrgId())) {

				throw new ApplicationException("Tax No already exists.");
			}

			createUpdateTaxDefinitionVO(dto, taxDefinitionVO);

			message = "Tax Definition Created Successfully";
		}

		taxDefinitionRepo.save(taxDefinitionVO);

		Map<String, Object> response = new HashMap<>();
		response.put("taxDefinitionVO", taxDefinitionVO);
		response.put("message", message);

		return response;
	}

	private void createUpdateTaxDefinitionVO(TaxDefinitionDTO dto, TaxDefinitionVO taxDefinitionVO) {

		if (dto.getBranch() != null) {

			BranchVO lov = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new RuntimeException("Branch Not Found"));

			taxDefinitionVO.setBranch(lov);
		}
		// Parent Mapping
		if (dto.getModule() != null) {

			ListOfValuesVO lov = listOfValuesRepo.findById(dto.getModule())
					.orElseThrow(() -> new RuntimeException("Module Not Found"));

			taxDefinitionVO.setModule(lov);
		}

		taxDefinitionVO.setTaxNo(dto.getTaxNo());
		taxDefinitionVO.setTaxDescription(dto.getTaxDescription());
		taxDefinitionVO.setDocDate(dto.getDocDate());
		taxDefinitionVO.setEffectiveDate(dto.getEffectiveDate());
		taxDefinitionVO.setFillCopyOF(dto.getFillCopyOF());
		taxDefinitionVO.setPrintName(dto.getPrintName());
		taxDefinitionVO.setActive(dto.isActive());
		taxDefinitionVO.setCancelRemarks(dto.getCancelRemarks());
		taxDefinitionVO.setOrgId(dto.getOrgId());

		// Remove old details during update
		if (taxDefinitionVO.getId() != null) {

			List<TaxDefinitionDetailsVO> oldDetails = taxDefinitionDetailsRepo.findByTaxDefinitionVO(taxDefinitionVO);

			taxDefinitionDetailsRepo.deleteAll(oldDetails);
		}

		// New Details
		List<TaxDefinitionDetailsVO> detailsList = new ArrayList<>();

		if (dto.getDetails() != null && !dto.getDetails().isEmpty()) {

			for (TaxDefinitionDetailsDTO detailDTO : dto.getDetails()) {

				TaxDefinitionDetailsVO detailVO = new TaxDefinitionDetailsVO();

				if (detailDTO.getTaxType() != null) {

					ListOfValuesVO detailLov = listOfValuesRepo.findById(detailDTO.getTaxType())
							.orElseThrow(() -> new RuntimeException("List Of Value Not Found"));

					detailVO.setTaxType(detailLov);
				}
				if (detailDTO.getTaxName() != null) {

					ListOfValuesVO detailLov = listOfValuesRepo.findById(detailDTO.getTaxName())
							.orElseThrow(() -> new RuntimeException("List Of Value Not Found"));

					detailVO.setTaxName(detailLov);
				}

				detailVO.setAddLess(detailDTO.getAddLess());
				detailVO.setTaxPercent(detailDTO.getTaxPercent());
				detailVO.setTaxId(detailDTO.getTaxId());
				detailVO.setFormula(detailDTO.getFormula());
				detailVO.setPostToFinance(detailDTO.getPostToFinance());
				detailVO.setDbCr(detailDTO.getDbCr());
				detailVO.setGlAccountName(detailDTO.getGlAccountName());
				detailVO.setPrint(detailDTO.getPrint());
				detailVO.setTaxPost(detailDTO.getTaxPost());

				// Parent Mapping
				detailVO.setTaxDefinitionVO(taxDefinitionVO);

				detailsList.add(detailVO);
			}
		}

		taxDefinitionVO.setTaxDefinitionDetailsVO(detailsList);
	}

	@Override
	public TaxDefinitionVO getTaxDefinitionById(Long id) throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {
			throw new ApplicationException("Invalid Id");
		}

		return taxDefinitionRepo.findById(id).orElseThrow(() -> new ApplicationException("Tax Definition Not Found"));
	}

	@Override
	public List<TaxDefinitionVO> getTaxDefinitionByOrgId(Long orgId, Long branch) throws ApplicationException {

		List<TaxDefinitionVO> list = taxDefinitionRepo.getTaxDefinitionByOrgId(orgId, branch);

		if (list.isEmpty()) {
			throw new ApplicationException("No Tax Definition Found");
		}

		return list;
	}

	// Holliday Master

	@Override
	@Transactional
	public Map<String, Object> updateCreateHolidayMaster(@Valid HolidayMasterDTO dto) throws ApplicationException {

		HolidayMasterVO holidayMasterVO = new HolidayMasterVO();
		String message;

		if (ObjectUtils.isNotEmpty(dto.getId())) {

			holidayMasterVO = holidayMasterRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Holiday Master Not Found"));

			holidayMasterVO.setUpdatedBy(dto.getCreatedBy());

			createUpdateholidayMasterVO(dto, holidayMasterVO);

			message = "Holiday MAster Updated Successfully";

		} else {

			holidayMasterVO.setCreatedBy(dto.getCreatedBy());
			holidayMasterVO.setUpdatedBy(dto.getCreatedBy());

			createUpdateholidayMasterVO(dto, holidayMasterVO);

			message = "List Of Values Created Successfully";
		}

		holidayMasterVO = holidayMasterRepo.save(holidayMasterVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("holidayMasterVO", holidayMasterVO);

		return response;
	}

	private void createUpdateholidayMasterVO(HolidayMasterDTO dto, HolidayMasterVO holidayMasterVO)
			throws ApplicationException {

		holidayMasterVO.setDate(dto.getDate());
		holidayMasterVO.setOrgId(dto.getOrgId());
		holidayMasterVO.setActive(dto.getActive());
		holidayMasterVO.setCancelRemarks(dto.getCancelRemarks());

		if (dto.getBranch() != null && dto.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			holidayMasterVO.setBranch(branch);
		}

		if (dto.getId() != null) {

			List<HolidayMasterDetailsVO> oldDetails = holidayMaterDetailsRepo.findByHolidayMasterVO(holidayMasterVO);

			holidayMaterDetailsRepo.deleteAll(oldDetails);
		}

		List<HolidayMasterDetailsVO> detailList = new ArrayList<>();

		for (HolidayMasterDetailsDTO detailDTO : dto.getDetails()) {

			HolidayMasterDetailsVO detailVO = new HolidayMasterDetailsVO();

			detailVO.setHolidayDate(detailDTO.getHolidayDate());
			detailVO.setDay(detailDTO.getDay());
			detailVO.setHolidayType(detailDTO.getHolidayType());
			detailVO.setRemarks(detailDTO.getRemarks());
			detailVO.setCompensatory(detailDTO.getCompensatory());
			detailVO.setCompensatoryDate(detailDTO.getCompensatoryDate());

			detailVO.setHolidayMasterVO(holidayMasterVO);

			detailList.add(detailVO);
		}

		holidayMasterVO.setHolidayMasterDetailsVO(detailList);
	}

	@Override
	public HolidayMasterVO getHolidayMasterById(Long id) {

		return holidayMasterRepo.getHolidayMasterById(id);

	}

	@Override
	public List<HolidayMasterVO> getHolidayMasterByOrgId(Long orgId, Long branch) {

		return holidayMasterRepo.getHolidayMasterByOrgId(orgId, branch);
	}

	// Mapping Party to Account

	@Override
	@Transactional
	public Map<String, Object> updateCreateMappingOfPartyToAcc(@Valid MappingOfPartyToAccDTO dto)
			throws ApplicationException {

		MappingOfPartyToAccVO mappingOfPartyToAccVO = new MappingOfPartyToAccVO();
		String message;

		if (ObjectUtils.isNotEmpty(dto.getId())) {

			mappingOfPartyToAccVO = mappingPartyToAccRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Mapping  Not Found"));

			mappingOfPartyToAccVO.setUpdatedBy(dto.getCreatedBy());

			createUpdatemappingOfPartyToAccVO(dto, mappingOfPartyToAccVO);

			message = "Mapping  Updated Successfully";

		} else {

			mappingOfPartyToAccVO.setCreatedBy(dto.getCreatedBy());
			mappingOfPartyToAccVO.setUpdatedBy(dto.getCreatedBy());

			createUpdatemappingOfPartyToAccVO(dto, mappingOfPartyToAccVO);

			message = "Mapping Created Successfully";
		}

		mappingOfPartyToAccVO = mappingPartyToAccRepo.save(mappingOfPartyToAccVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("mappingOfPartyToAccVO", mappingOfPartyToAccVO);

		return response;
	}

	private void createUpdatemappingOfPartyToAccVO(MappingOfPartyToAccDTO dto,
			MappingOfPartyToAccVO mappingOfPartyToAccVO) throws ApplicationException {

		mappingOfPartyToAccVO.setDocDate(dto.getDocDate());
		mappingOfPartyToAccVO.setAsOnDate(dto.getAsOnDate());
		mappingOfPartyToAccVO.setOrgId(dto.getOrgId());
		mappingOfPartyToAccVO.setActive(dto.isActive());
		mappingOfPartyToAccVO.setCancelRemarks(dto.getCancelRemarks());

		if (dto.getBranch() != null && dto.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			mappingOfPartyToAccVO.setBranch(branch);
		}
		if (dto.getCategory() != null && dto.getCategory() != 0) {

			ListOfValuesVO category = listOfValuesRepo.findById(dto.getCategory())
					.orElseThrow(() -> new ApplicationException("Category Not Found"));

			mappingOfPartyToAccVO.setCategory(category);
		}

		if (dto.getId() != null) {

			List<MappingDetailsVO> oldDetails = mappingDetailsRepo.findByMappingOfPartyToAccVO(mappingOfPartyToAccVO);

			mappingDetailsRepo.deleteAll(oldDetails);
		}

		List<MappingDetailsVO> detailList = new ArrayList<>();

		for (MappingDetailsDTO detailDTO : dto.getDetails()) {

			MappingDetailsVO detailVO = new MappingDetailsVO();

			if (detailDTO.getPartyId() != null) {

				CustomerVO detailLov = customerRepo.findById(detailDTO.getPartyId())
						.orElseThrow(() -> new RuntimeException("Party details Not Found"));

				detailVO.setPartId(detailLov);
			}
			detailVO.setAccountName(detailDTO.getAccountName());

			detailVO.setMappingOfPartyToAccVO(mappingOfPartyToAccVO);

			detailList.add(detailVO);
		}

		mappingOfPartyToAccVO.setMappingDetailsVO(detailList);
	}

	@Override
	public MappingOfPartyToAccVO getMappingOfPartyToAccById(Long id) {

		return mappingPartyToAccRepo.getMappingOfPartyToAccById(id);

	}

	@Override
	public List<MappingOfPartyToAccVO> getMappingOfPartyToAccByOrgId(Long orgId, Long branch) {

		return mappingPartyToAccRepo.getMappingOfPartyToAccByOrgId(orgId, branch);
	}

	// dropdown api for category

//											@Override
//											public Map<String, Object> getCustomerCategory(Long orgId)
//											        throws ApplicationException {
	//
//											    Map<String, Object> response = new HashMap<>();
	//
//											    List<ListOfValuesVO> customerCategory =
//											            listOfValuesRepo.getCustomerCategory(orgId);
	//
//											    if (customerCategory.isEmpty()) {
//											        throw new ApplicationException("No Customer Category Found");
//											    }
	//
//											    response.put("customerCategory", customerCategory);
	//
//											    return response;
//											}

	@Override
	public Map<String, Object> getCustomerCategory(Long orgId) throws ApplicationException {

		List<Object[]> customerCategory = listOfValuesRepo.getCustomerCategory(orgId);

		if (customerCategory.isEmpty()) {
			throw new ApplicationException("No Customer Category Found");
		}

		return getCustomerCategoryResponse(customerCategory);
	}

	private Map<String, Object> getCustomerCategoryResponse(List<Object[]> customerCategory) {

		Map<String, Object> response = new HashMap<>();

		List<Map<String, Object>> categoryList = new ArrayList<>();

		for (Object[] category : customerCategory) {

			Map<String, Object> map = new HashMap<>();
			map.put("id", category[0]);
			map.put("listCode", category[1]);
			map.put("listDescription", category[2]);

			categoryList.add(map);
		}

		response.put("customerCategory", categoryList);

		return response;
	}

	// Dropdown for Party Id
	@Override
	public Map<String, Object> getParty(Long category, Long orgId, Long branch) throws ApplicationException {

		Map<String, Object> response = new HashMap<>();

		List<PartyProjection> partyList = customerRepo.getParty(category, orgId, branch);

		if (partyList.isEmpty()) {
			throw new ApplicationException("No Party Found");
		}

		response.put("partyList", partyList);

		return response;
	}

}