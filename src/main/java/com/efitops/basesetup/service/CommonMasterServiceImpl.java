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
import com.efitops.basesetup.dto.CityDTO;
import com.efitops.basesetup.dto.CompanyDTO;
import com.efitops.basesetup.dto.CountryDTO;
import com.efitops.basesetup.dto.CurrencyDTO;
import com.efitops.basesetup.dto.FinScreenDTO;
import com.efitops.basesetup.dto.FinancialYearDTO;
import com.efitops.basesetup.dto.RegionDTO;
import com.efitops.basesetup.dto.Role;
import com.efitops.basesetup.dto.ScreenNamesDTO;
import com.efitops.basesetup.dto.StateDTO;
import com.efitops.basesetup.dto.TransportMasterDTO;
import com.efitops.basesetup.entity.BankDetailsVO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CityVO;
import com.efitops.basesetup.entity.CompanyVO;
import com.efitops.basesetup.entity.CountryVO;
import com.efitops.basesetup.entity.CurrencyVO;
import com.efitops.basesetup.entity.EmployeeVO;
import com.efitops.basesetup.entity.FinancialYearVO;
import com.efitops.basesetup.entity.RegionVO;
import com.efitops.basesetup.entity.ScreenNamesVO;
import com.efitops.basesetup.entity.StateVO;
import com.efitops.basesetup.entity.TransportMasterVO;
import com.efitops.basesetup.entity.UserVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BankDetailsRepo;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CityRepo;
import com.efitops.basesetup.repository.CompanyRepo;
import com.efitops.basesetup.repository.CountryRepo;
import com.efitops.basesetup.repository.CurrencyRepo;
import com.efitops.basesetup.repository.EmployeeRepo;
import com.efitops.basesetup.repository.FinScreenRepo;
import com.efitops.basesetup.repository.FinancialYearRepo;
import com.efitops.basesetup.repository.RegionRepo;
import com.efitops.basesetup.repository.ResponsibilitiesRepo;
import com.efitops.basesetup.repository.ScreenNamesRepo;
import com.efitops.basesetup.repository.StateRepo;
import com.efitops.basesetup.repository.TransportRepo;
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
	EmployeeRepo employeeRepo;
	
	@Autowired
	BranchRepo branchRepo;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	TransportRepo transportRepo;

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
	public CompanyResponseDTO  createCompany(CompanyDTO companyDTO) throws Exception {

		validateCreateCompany(companyDTO);

		CompanyVO companyVO = new CompanyVO();
		mapCreateCompanyDTOToVO(companyVO, companyDTO);
		companyRepo.save(companyVO);

		EmployeeVO employeeVO = new EmployeeVO();
		employeeVO.setEmployeeName(companyVO.getAdminName());
//		employeeVO.setEmployeeCode(companyVO.getCompanyCode());
		employeeVO.setActive(true);
		employeeVO.setOrgId(companyVO.getId());
		employeeRepo.save(employeeVO);

		UserVO userVO = new UserVO();
		userVO.setUserName(companyVO.getAdminName());
		userVO.setEmployeeName(companyVO.getAdminName());
//		userVO.setEmployeeCode(companyVO.getCompanyCode());
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
		    userVO.setPassword(
		        encoder.encode(
		            CryptoUtils.getDecrypt(companyDTO.getPassword())
		        )
		    );
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
	public CompanyResponseDTO  updateCompany(CompanyDTO companyDTO) throws Exception {

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
			if (!countryVO.getCountryCode().equalsIgnoreCase(countryDTO.getCountryCode())) {
				if (countryRepo.existsByCountryCodeAndOrgId(countryDTO.getCountryCode(), countryDTO.getOrgId())) {
					String errorMessage = String.format("The CountryCode: %s already exists This Organization.",
							countryDTO.getCountryCode());
					throw new ApplicationException(errorMessage);
				}
				countryVO.setCountryCode(countryDTO.getCountryCode().toUpperCase());
			}
			if (!countryVO.getCountryName().equalsIgnoreCase(countryDTO.getCountryName())) {
				if (countryRepo.existsByCountryNameAndOrgId(countryDTO.getCountryName(), countryDTO.getOrgId())) {
					String errorMessage = String.format("The CountryName: %s already exists This Organization.",
							countryDTO.getCountryName());
					throw new ApplicationException(errorMessage);
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
	public List<StateVO> getStatesByCountry(Long orgid, String country) {

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

		CountryVO countryVO = countryRepo.findById(stateDTO.getCountryId())
				.orElseThrow(() -> new ApplicationException("Country not found with id : " + stateDTO.getCountryId()));

		stateVO.setCountry(countryVO);

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
	public List<CityVO> getAllCitiesByState(Long orgid, String state) {

		return cityRepo.findAll(orgid, state);
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
		CountryVO countryVO = countryRepo.findById(cityDTO.getCountryId())
				.orElseThrow(() -> new ApplicationException("Country not found with id : " + cityDTO.getCountryId()));

		StateVO stateVO = stateRepo.findById(cityDTO.getStateId())
				.orElseThrow(() -> new ApplicationException("State not found with id : " + cityDTO.getStateId()));

		cityVO.setCountry(countryVO);
		cityVO.setState(stateVO);
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
	@Override
	public Map<String, Object> createUpdateFinYear(FinancialYearDTO financialYearDTO) throws ApplicationException {
		FinancialYearVO financialYearVO = null;
		String message;

		if (ObjectUtils.isEmpty(financialYearDTO.getId())) {
			if (financialYearRepo.existsByFinYearAndOrgId(financialYearDTO.getFinYear(), financialYearDTO.getOrgId())) {
				String errorMessage = String.format("ThiS finyear:%s Already Exists This Organization .",
						financialYearDTO.getFinYear());
				throw new ApplicationException(errorMessage);
			}

			if (financialYearRepo.existsByFinYearIdentifierAndOrgId(financialYearDTO.getFinYearIdentifier(),
					financialYearDTO.getOrgId())) {
				String errorMessage = String.format("ThiS finyearidentifier:%s Already Exists This Organization .",
						financialYearDTO.getFinYearIdentifier());
				throw new ApplicationException(errorMessage);
			}

			if (financialYearRepo.existsByFinYearIdAndOrgId(financialYearDTO.getFinYearId(),
					financialYearDTO.getOrgId())) {
				String errorMessage = String.format("ThiS finyearid:%s Already Exists This Organization .",
						financialYearDTO.getFinYearId());
				throw new ApplicationException(errorMessage);
			}

			financialYearVO = new FinancialYearVO();
			financialYearVO.setCreatedBy(financialYearDTO.getCreatedBy());
			financialYearVO.setUpdatedBy(financialYearDTO.getCreatedBy());
			message = "Financial Year Creation Successfully";

		} else {
			financialYearVO = financialYearRepo.findById(financialYearDTO.getId())
					.orElseThrow(() -> new ApplicationException(String
							.format("This Id Is Not Found Any Information, Invalid Id: %s", financialYearDTO.getId())));

			if (financialYearVO.getFinYear() != financialYearDTO.getFinYear()) {
				if (financialYearRepo.existsByFinYearAndOrgId(financialYearDTO.getFinYear(),
						financialYearDTO.getOrgId())) {
					String errorMessage = String.format("This finyear: %s already exists for this organization.",
							financialYearDTO.getFinYear());
					throw new ApplicationException(errorMessage);
				}
				financialYearVO.setFinYear(financialYearDTO.getFinYear());
			}

			if (!financialYearVO.getFinYearIdentifier().equals(financialYearDTO.getFinYearIdentifier())) {
				if (financialYearRepo.existsByFinYearIdentifierAndOrgId(financialYearDTO.getFinYearIdentifier(),
						financialYearDTO.getOrgId())) {
					String errorMessage = String.format(
							"This finyearIdentifier: %s already exists for this organization.",
							financialYearDTO.getFinYearIdentifier());
					throw new ApplicationException(errorMessage);
				}
				financialYearVO.setFinYearIdentifier(financialYearDTO.getFinYearIdentifier());
			}

			if (financialYearVO.getFinYearId() != financialYearDTO.getFinYearId()) {
				if (financialYearRepo.existsByFinYearIdAndOrgId(financialYearDTO.getFinYearId(),
						financialYearDTO.getOrgId())) {
					String errorMessage = String.format("This finyearId: %s already exists for this organization.",
							financialYearDTO.getFinYearId());
					throw new ApplicationException(errorMessage);
				}
				financialYearVO.setFinYearId(financialYearDTO.getFinYearId());
			}

			financialYearVO.setUpdatedBy(financialYearDTO.getCreatedBy());
			message = "Financial Year Updation Successfully";

		}
		getFinancialYearVOFromFinancialYearDTO(financialYearVO, financialYearDTO);
		financialYearRepo.save(financialYearVO);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("financialYearVO", financialYearVO);
		response.put("message", response);
		return response;

	}

	private void getFinancialYearVOFromFinancialYearDTO(FinancialYearVO financialYearVO,
			FinancialYearDTO financialYearDTO) {
		financialYearVO.setFinYear(financialYearDTO.getFinYear());
		financialYearVO.setFinYearId(financialYearDTO.getFinYearId());
		financialYearVO.setFinYearIdentifier(financialYearDTO.getFinYearIdentifier());
		financialYearVO.setStartDate(financialYearDTO.getStartDate());
		financialYearVO.setEndDate(financialYearDTO.getEndDate());
		financialYearVO.setCurrentFinYear(financialYearDTO.isCurrentFinYear());
		financialYearVO.setClosed(financialYearDTO.isClosed());
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
	
	
	
	//branch
	
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
	                throw new ApplicationException(
	                        "Branch Name : " + branchDTO.getBranchName() + " already exists.");
	            }
	        }

	        if (!branchVO.getBranchCode().equalsIgnoreCase(branchDTO.getBranchCode())) {

	            if (branchRepo.existsByBranchCodeAndOrgId(branchDTO.getBranchCode(), branchDTO.getOrgId())) {
	                throw new ApplicationException(
	                        "Branch Code : " + branchDTO.getBranchCode() + " already exists.");
	            }
	        }


		    branchVO.setCreatedBy(branchDTO.getCreatedBy());
	        message = "Branch Updated Successfully";

	    } else {

	        if (branchRepo.existsByBranchNameAndOrgId(branchDTO.getBranchName(), branchDTO.getOrgId())) {
	            throw new ApplicationException(
	                    "Branch Name : " + branchDTO.getBranchName() + " already exists.");
	        }

	        if (branchRepo.existsByBranchCodeAndOrgId(branchDTO.getBranchCode(), branchDTO.getOrgId())) {
	            throw new ApplicationException(
	                    "Branch Code : " + branchDTO.getBranchCode() + " already exists.");
	        }

	        branchVO.setCreatedBy(branchDTO.getCreatedBy());
	        branchVO.setUpdatedBy(branchDTO.getCreatedBy());

	        message = "Branch Created Successfully";
	    }

	    createUpdateBranchVO(branchDTO, branchVO);

	    branchRepo.save(branchVO);

	    // Delete old Bank Details while updating
	    if (branchDTO.getId() != null) {

	        List<BankDetailsVO> bankList = bankDetailsRepo.findByBranchVO(branchVO);

	        bankDetailsRepo.deleteAll(bankList);
	    }

	    // Save Bank Details
	    if (branchDTO.getBankDetails() != null && !branchDTO.getBankDetails().isEmpty()) {

	        for (BankDetailsDTO bankDTO : branchDTO.getBankDetails()) {

	            BankDetailsVO bankVO = new BankDetailsVO();

	            bankVO.setBankName(bankDTO.getBankName());
	            bankVO.setIfscCode(bankDTO.getIfscCode());
	            bankVO.setAccountNo(bankDTO.getAccountNo());
	            bankVO.setBankBranch(bankDTO.getBankBranch());

	            bankVO.setBranchVO(branchVO);

	            bankDetailsRepo.save(bankVO);
	        }
	    }

	    Map<String, Object> response = new HashMap<>();
	    response.put("branchVO", branchVO);
	    response.put("message", message);

	    return response;
	}
	
	private void createUpdateBranchVO(BranchDTO dto, BranchVO branchVO)
	        throws ApplicationException {

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

	    List<BranchVO> branchList =
	            branchRepo.getBranchByOrgId(orgId);

	    if (branchList.isEmpty()) {
	        throw new ApplicationException("No branch Details Found");
	    }

	    return branchList;
	}
		
	  @Override
	  @Transactional
	  public Map<String, Object> updateCreateTransportMaster(@Valid TransportMasterDTO transportMasterDTO)
	          throws ApplicationException {

	      TransportMasterVO transportMasterVO = new TransportMasterVO();
	      String message;

	      if (ObjectUtils.isNotEmpty(transportMasterDTO.getId())) {

	          transportMasterVO = transportRepo.findById(transportMasterDTO.getId())
	                  .orElseThrow(() -> new ApplicationException("Invalid Transport Details"));

	          if (!transportMasterVO.getTransportName()
	                  .equalsIgnoreCase(transportMasterDTO.getTransportName())) {

	              if (transportRepo.existsByTransportNameAndOrgId(
	                      transportMasterDTO.getTransportName(),
	                      transportMasterDTO.getOrgId())) {

	                  throw new ApplicationException(
	                          "The Transport : " + transportMasterDTO.getTransportName()
	                                  + " already exists in this Organization.");
	              }
	          }

	          createUpdateTransportMasterVOByTransportMasterDTO(transportMasterDTO, transportMasterVO);

	          transportMasterVO.setUpdated_By(transportMasterDTO.getCreatedBy());

	          message = "Transport Updated Successfully";

	      } else {

	          if (transportRepo.existsByTransportNameAndOrgId(
	                  transportMasterDTO.getTransportName(),
	                  transportMasterDTO.getOrgId())) {

	              throw new ApplicationException(
	                      "The Transport : " + transportMasterDTO.getTransportName()
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
	  
	  private void createUpdateTransportMasterVOByTransportMasterDTO(
		        TransportMasterDTO transportMasterDTO,
		        TransportMasterVO transportMasterVO) {

		    transportMasterVO.setTransportName(transportMasterDTO.getTransportName().toUpperCase());
		    transportMasterVO.setAddress(transportMasterDTO.getAddress());
		    transportMasterVO.setOrgId(transportMasterDTO.getOrgId());
		    transportMasterVO.setBranchCode(transportMasterDTO.getBranchCode());
		    transportMasterVO.setActive(transportMasterDTO.getActive());
		    transportMasterVO.setCancelRemarks(transportMasterDTO.getCancelRemarks());
		    transportMasterVO.setBranch(transportMasterDTO.getBranch());

		}


		@Override
		public TransportMasterVO getTransportNameById(Long id) throws ApplicationException {

		    return transportRepo.findById(id)
		            .orElseThrow(() -> new ApplicationException("Invalid Transport Details"));
		}

		@Override
		public List<TransportMasterVO> getTransportNameByOrgId(Long orgId,String branchCode) throws ApplicationException {

		    List<TransportMasterVO> transportList =
		            transportRepo.findByOrgIdAndBranch(orgId,branchCode);

		    if (transportList.isEmpty()) {
		        throw new ApplicationException("No Transport Details Found");
		    }

		    return transportList;
		}

		
}