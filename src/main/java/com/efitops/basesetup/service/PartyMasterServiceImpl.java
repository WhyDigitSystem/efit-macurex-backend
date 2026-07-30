package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.ResponseDTO.DocumentTypeMappingBranchResponseDTO;
import com.efitops.basesetup.ResponseDTO.CityResponseDTO;
import com.efitops.basesetup.ResponseDTO.CountryResponseDTO;
import com.efitops.basesetup.ResponseDTO.CustomerContactDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.CustomerItemDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.CustomerResponseDTO;
import com.efitops.basesetup.ResponseDTO.CustomerShippingDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.DepartmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.GSTStateResponseDTO;
import com.efitops.basesetup.ResponseDTO.ItemResponseDTO;
import com.efitops.basesetup.ResponseDTO.PartyCategoryResponseDTO;
import com.efitops.basesetup.ResponseDTO.StateResponseDTO;
import com.efitops.basesetup.ResponseDTO.UnitResponseDTO;
import com.efitops.basesetup.dto.CustomerContactDetailsDTO;
import com.efitops.basesetup.dto.CustomerDTO;
import com.efitops.basesetup.dto.CustomerItemDetailsDTO;
import com.efitops.basesetup.dto.CustomerShippingDetailsDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CityVO;
import com.efitops.basesetup.entity.CountryVO;
import com.efitops.basesetup.entity.CustomerContactDetailsVO;
import com.efitops.basesetup.entity.CustomerItemDetailsVO;
import com.efitops.basesetup.entity.CustomerShippingDetailsVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.GSTStateMasterVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.ListOfValuesVO;
import com.efitops.basesetup.entity.StateVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CityRepo;
import com.efitops.basesetup.repository.CountryRepo;
import com.efitops.basesetup.repository.CustomerContactDetailsRepo;
import com.efitops.basesetup.repository.CustomerItemDetailsRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.CustomerShippingDetailsRepo;
import com.efitops.basesetup.repository.DepartmentRepo;
import com.efitops.basesetup.repository.GSTStateMasterRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.ListOfValuesRepo;
import com.efitops.basesetup.repository.StateRepo;

@Service
public class PartyMasterServiceImpl implements PartyMasterService {

	public static final Logger LOGGER = LoggerFactory.getLogger(PartyMasterServiceImpl.class);

	@Autowired
	CustomerRepo customerRepo;
//	
	@Autowired
	ListOfValuesRepo listOfValuesRepo;
	
	@Autowired
	BranchRepo branchRepo;
	
	@Autowired
	private GSTStateMasterRepo gstStateRepo;
	
	@Autowired
	CityRepo cityRepo;
	
	@Autowired
	StateRepo stateRepo;
	
	@Autowired
	CountryRepo countryRepo;
//
	@Autowired
	DepartmentRepo departmentRepo;
	
	@Autowired
	CustomerContactDetailsRepo customerContactDetailsRepo;
	
	@Autowired
	CustomerShippingDetailsRepo customerShippingDetailsRepo;
	
	@Autowired
	CustomerItemDetailsRepo customerItemDetailsRepo;
	
	@Autowired
	ItemMasterRepo itemMasterRepo;
	
//	@Autowired
//	TransportRepo transportMasterRepo;
//	

	@Override
	@Transactional
	public Map<String, Object> createUpdateCustomer(CustomerDTO customerDTO) throws ApplicationException {

	    CustomerVO customerVO = new CustomerVO();
	    String message;

	    if (customerDTO.getId() != null) {

	        customerVO = customerRepo.findById(customerDTO.getId())
	                .orElseThrow(() -> new ApplicationException("Invalid Customer Details"));

	        customerVO.setUpdatedBy(customerDTO.getCreatedBy());

	        if (!customerVO.getCustomerName().equalsIgnoreCase(customerDTO.getCustomerName())) {

	            if (customerRepo.existsByCustomerNameAndOrgId(
	                    customerDTO.getCustomerName(),
	                    customerDTO.getOrgId())) {

	                throw new ApplicationException(
	                        "Customer Name : " + customerDTO.getCustomerName() + " already exists.");
	            }
	        }

	        if (customerDTO.getGstNo() != null
	                && !customerDTO.getGstNo().equalsIgnoreCase(customerVO.getGstNo())) {

	            if (customerRepo.existsByGstNoAndOrgId(
	                    customerDTO.getGstNo(),
	                    customerDTO.getOrgId())) {

	                throw new ApplicationException(
	                        "GST No : " + customerDTO.getGstNo() + " already exists.");
	            }
	        }

	        message = "Customer Updated Successfully";

	    } else {

	        if (customerRepo.existsByCustomerNameAndOrgId(
	                customerDTO.getCustomerName(),
	                customerDTO.getOrgId())) {

	            throw new ApplicationException(
	                    "Customer Name : " + customerDTO.getCustomerName() + " already exists.");
	        }

	        if (customerDTO.getGstNo() != null
	                && customerRepo.existsByGstNoAndOrgId(
	                        customerDTO.getGstNo(),
	                        customerDTO.getOrgId())) {

	            throw new ApplicationException(
	                    "GST No : " + customerDTO.getGstNo() + " already exists.");
	        }

	        customerVO.setCreatedBy(customerDTO.getCreatedBy());
	        customerVO.setUpdatedBy(customerDTO.getCreatedBy());

	        message = "Customer Created Successfully";
	    }

	    createUpdateCustomerVO(customerDTO, customerVO);

	    customerRepo.save(customerVO);

	    CustomerResponseDTO responseDTO = convertToResponse(customerVO);

	    Map<String, Object> response = new HashMap<>();
	    response.put("customer", responseDTO);
	    response.put("message", message);

	    return response;
	}
	
	
	private void createUpdateCustomerVO(CustomerDTO dto, CustomerVO customerVO)
	        throws ApplicationException {

	    // Load Masters
		ListOfValuesVO customerCategory = listOfValuesRepo
		        .findById(dto.getCustomerCategory())
		        .orElseThrow(() -> new ApplicationException("Customer Category Not Found"));

		ListOfValuesVO customerCategory1 = listOfValuesRepo
		        .findById(dto.getCustomerCategory1())
		        .orElseThrow(() -> new ApplicationException("Customer Category1 Not Found"));

		ListOfValuesVO customerCategory2 = listOfValuesRepo
		        .findById(dto.getCustomerCategory2())
		        .orElseThrow(() -> new ApplicationException("Customer Category2 Not Found"));

		ListOfValuesVO supplierType = listOfValuesRepo
		        .findById(dto.getSupplierType())
		        .orElseThrow(() -> new ApplicationException("Supplier Type Not Found"));

		BranchVO branch = branchRepo
		        .findById(dto.getBranch())
		        .orElseThrow(() -> new ApplicationException("Branch Not Found"));

		GSTStateMasterVO gstState = gstStateRepo
		        .findById(dto.getGstState())
		        .orElseThrow(() -> new ApplicationException("GST State Not Found"));

		CityVO city = cityRepo
		        .findById(dto.getCity())
		        .orElseThrow(() -> new ApplicationException("City Not Found"));

		StateVO state = stateRepo
		        .findById(dto.getState())
		        .orElseThrow(() -> new ApplicationException("State Not Found"));

		CountryVO country = countryRepo
		        .findById(dto.getCountry())
		        .orElseThrow(() -> new ApplicationException("Country Not Found"));
		customerVO.setCustomerCategory(customerCategory);
		customerVO.setCustomerCategory1(customerCategory1);
		customerVO.setCustomerCategory2(customerCategory2);
		customerVO.setSupplierType(supplierType);
		
	    customerVO.setBranch(branch);
	    customerVO.setGstState(gstState);
	    customerVO.setCity(city);
	    customerVO.setState(state);
	    customerVO.setCountry(country);

	    // Set all remaining fields
	    customerVO.setDocId(dto.getDocId());
	    customerVO.setDocDate(dto.getDocDate());
	    customerVO.setSalutation(dto.getSalutation());
	    customerVO.setCustomerType(dto.getCustomerType());
	    customerVO.setAccountName(dto.getAccountName());
	    customerVO.setCustomerName(dto.getCustomerName());
	    customerVO.setCustomerLegalName(dto.getCustomerLegalName());
	    customerVO.setTradeName(dto.getTradeName());
	    customerVO.setGroupCompany(dto.isGroupCompany());
	    customerVO.setZone(dto.getZone());
	    customerVO.setVendorCode(dto.getVendorCode());
	    customerVO.setGroupName(dto.getGroupName());
	    customerVO.setRegistered(dto.isRegistered());
	    customerVO.setExcisable(dto.isExcisable());
	    customerVO.setPartyCreditLimit(dto.getPartyCreditLimit());
	    customerVO.setPartyCreditPeriod(dto.getPartyCreditPeriod());
	    customerVO.setGstType(dto.getGstType());
	    customerVO.setGstNo(dto.getGstNo());
	    customerVO.setGstApplicable(dto.isGstApplicable());
	    customerVO.setAddress(dto.getAddress());
	    customerVO.setPincode(dto.getPincode());
	    customerVO.setEmail(dto.getEmail());
	    customerVO.setWebAddress(dto.getWebAddress());
	    customerVO.setCinNo(dto.getCinNo());
	    customerVO.setOverDueInterest(dto.getOverDueInterest());
	    customerVO.setIntroducedBy(dto.getIntroducedBy());
	    customerVO.setCstNo(dto.getCstNo());
	    customerVO.setEccNo(dto.getEccNo());
	    customerVO.setEccType(dto.getEccType());
	    customerVO.setKstNo(dto.getKstNo());
	    customerVO.setPhone(dto.getPhone());
	    customerVO.setContactPerson(dto.getContactPerson());
	    customerVO.setEffectiveFrom(dto.getEffectiveFrom());
	    customerVO.setRange(dto.getRange());
	    customerVO.setRemarks(dto.getRemarks());
	    customerVO.setDateOfApproval(dto.getDateOfApproval());
	    customerVO.setIsoStatus(dto.getIsoStatus());
	    customerVO.setTypeExtentOfControl(dto.getTypeExtentOfControl());
	    customerVO.setReAssessmentDate(dto.getReAssessmentDate());
	    customerVO.setCreditPeriod(dto.getCreditPeriod());
	    customerVO.setApproved(dto.isApproved());
	    customerVO.setScopeOfSupply(dto.getScopeOfSupply());
	    customerVO.setBasisOfApproval(dto.getBasisOfApproval());
	    customerVO.setBelongsTo(dto.getBelongsTo());
	    customerVO.setBankName(dto.getBankName());
	    customerVO.setBankAccountNo(dto.getBankAccountNo());
	    customerVO.setPaymentMode(dto.getPaymentMode());
	    customerVO.setIfscCode(dto.getIfscCode());
	    customerVO.setOrgId(dto.getOrgId());
	    customerVO.setFinancialYear(dto.getFinancialYear());
	    customerVO.setCancelRemarks(dto.getCancelRemarks());
	    customerVO.setActive(dto.isActive());
	    
	    List<CustomerContactDetailsVO> contactDetails = new ArrayList<>();

	 // Only for Update
	 
	 if (dto.getId() != null) {

		 List<CustomerContactDetailsVO> oldContacts =
	             customerContactDetailsRepo.findByCustomerVO(customerVO);

		 customerContactDetailsRepo.deleteAll(oldContacts);
	    }

	 // Common for Create & Update
	     for (CustomerContactDetailsDTO cusdto : dto.getCustomerContactDetails()) {

	         CustomerContactDetailsVO vo = new CustomerContactDetailsVO();

	         if (cusdto.getPurpose() != null) {
	             DepartmentVO department = departmentRepo.findById(cusdto.getPurpose())
	                     .orElseThrow(() -> new ApplicationException("Department not found"));
	             vo.setPurpose(department);
	         }

	         vo.setContactName(cusdto.getContactName());
	         vo.setDesignation(cusdto.getDesignation());
	         vo.setPhone(cusdto.getPhone());
	         vo.setEmail(cusdto.getEmail());
	         vo.setWebsite(cusdto.getWebsite());
	         vo.setCustomerVO(customerVO);

	         contactDetails.add(vo);
	     }
	 

	 customerVO.setCustomerContactDetails(contactDetails);
	 
	 List<CustomerShippingDetailsVO> shippingDetails = new ArrayList<>();

	// Only for Update
	if (dto.getId() != null) {

	    List<CustomerShippingDetailsVO> oldShippingDetails =
	            customerShippingDetailsRepo.findByCustomerVO(customerVO);

	    customerShippingDetailsRepo.deleteAll(oldShippingDetails);
	}

	// Common for Create & Update
	for (CustomerShippingDetailsDTO shippingDTO : dto.getCustomerShippingDetails()) {

	    CustomerShippingDetailsVO vo = new CustomerShippingDetailsVO();

	    vo.setShippingAddressType(shippingDTO.getShippingAddressType());
	    vo.setShippingAddress(shippingDTO.getShippingAddress());

	    if (shippingDTO.getShippingCity() != null) {
	        CityVO city1 = cityRepo.findById(shippingDTO.getShippingCity())
	                .orElseThrow(() -> new ApplicationException("City not found"));
	        vo.setShippingCity(city1);
	    }

	    if (shippingDTO.getShippingState() != null) {
	        StateVO state1 = stateRepo.findById(shippingDTO.getShippingState())
	                .orElseThrow(() -> new ApplicationException("State not found"));
	        vo.setShippingState(state1);
	    }

	    if (shippingDTO.getShippingCountry() != null) {
	        CountryVO country1 = countryRepo.findById(shippingDTO.getShippingCountry())
	                .orElseThrow(() -> new ApplicationException("Country not found"));
	        vo.setShippingCountry(country1);
	    }

	    vo.setShippingPincode(shippingDTO.getShippingPincode());
	    vo.setCustomerVO(customerVO);

	    shippingDetails.add(vo);
	}

	customerVO.setCustomerShippingDetails(shippingDetails);
	
	List<CustomerItemDetailsVO> itemDetails = new ArrayList<>();

	// Only for Update
	if (dto.getId() != null) {

	    List<CustomerItemDetailsVO> oldItems =
	            customerItemDetailsRepo.findByCustomerVO(customerVO);

	    customerItemDetailsRepo.deleteAll(oldItems);
	}

	// Common for Create & Update
	if (dto.getCustomerItemDetailsDTO() != null) {

	    for (CustomerItemDetailsDTO itemDTO : dto.getCustomerItemDetailsDTO()) {

	        CustomerItemDetailsVO vo = new CustomerItemDetailsVO();

	        ItemMasterVO itemMaster = itemMasterRepo.findById(itemDTO.getItemId())
	                .orElseThrow(() -> new ApplicationException("Item not found"));

	        vo.setItem(itemMaster);
	        vo.setCustomerVO(customerVO);

	        itemDetails.add(vo);
	    }
	}

	customerVO.setCustomerItemDetailsVO(itemDetails);
}
	
	
	private CustomerResponseDTO convertToResponse(CustomerVO customerVO) {

	    CustomerResponseDTO dto = new CustomerResponseDTO();

	    dto.setId(customerVO.getId());

	    // Customer Categories
	    if (customerVO.getCustomerCategory() != null) {
	        dto.setCustomerCategory(new PartyCategoryResponseDTO(
	                customerVO.getCustomerCategory().getId(),
	                customerVO.getCustomerCategory().getListCode(),
	                customerVO.getCustomerCategory().getListDescription()));
	    }

	    if (customerVO.getCustomerCategory1() != null) {
	        dto.setCustomerCategory1(new PartyCategoryResponseDTO(
	                customerVO.getCustomerCategory1().getId(),
	                customerVO.getCustomerCategory1().getListCode(),
	                customerVO.getCustomerCategory1().getListDescription()));
	    }

	    if (customerVO.getCustomerCategory2() != null) {
	        dto.setCustomerCategory2(new PartyCategoryResponseDTO(
	                customerVO.getCustomerCategory2().getId(),
	                customerVO.getCustomerCategory2().getListCode(),
	                customerVO.getCustomerCategory2().getListDescription()));
	    }

	    if (customerVO.getSupplierType() != null) {
	        dto.setSupplierType(new PartyCategoryResponseDTO(
	                customerVO.getSupplierType().getId(),
	                customerVO.getSupplierType().getListCode(),
	                customerVO.getSupplierType().getListDescription()));
	    }

	    // Branch
	    if (customerVO.getBranch() != null) {
	        dto.setBranch(new DocumentTypeMappingBranchResponseDTO(
	                customerVO.getBranch().getId(),
	                customerVO.getBranch().getBranchCode(),
	                customerVO.getBranch().getBranchName()));
	    }

	    // GST State
	    if (customerVO.getGstState() != null) {
	        dto.setGstState(new GSTStateResponseDTO(
	                customerVO.getGstState().getId(),
	                customerVO.getGstState().getStateCode(),
	                customerVO.getGstState().getStateName(),
	                customerVO.getGstState().getGstStateId()));
	    }

	    // City
	    if (customerVO.getCity() != null) {
	        dto.setCity(new CityResponseDTO(
	                customerVO.getCity().getId(),
	                customerVO.getCity().getCityCode(),
	                customerVO.getCity().getCityName()));
	    }

	    // State
	    if (customerVO.getState() != null) {
	        dto.setState(new StateResponseDTO(
	                customerVO.getState().getId(),
	                customerVO.getState().getStateCode(),
	                customerVO.getState().getStateName()));
	    }

	    // Country
	    if (customerVO.getCountry() != null) {
	        dto.setCountry(new CountryResponseDTO(
	                customerVO.getCountry().getId(),
	                customerVO.getCountry().getCountryCode(),
	                customerVO.getCountry().getCountryName()));
	    }

	    // Basic Details
	    dto.setDocId(customerVO.getDocId());
	    dto.setDocDate(customerVO.getDocDate());
	    dto.setSalutation(customerVO.getSalutation());
	    dto.setCustomerType(customerVO.getCustomerType());
	    dto.setAccountName(customerVO.getAccountName());
	    dto.setCustomerName(customerVO.getCustomerName());
	    dto.setCustomerLegalName(customerVO.getCustomerLegalName());
	    dto.setTradeName(customerVO.getTradeName());
	    dto.setBelongsTo(customerVO.getBelongsTo());

	    dto.setGroupCompany(customerVO.isGroupCompany());

	    dto.setZone(customerVO.getZone());
	    dto.setVendorCode(customerVO.getVendorCode());
	    dto.setGroupName(customerVO.getGroupName());

	    dto.setRegistered(customerVO.isRegistered());
	    dto.setExcisable(customerVO.isExcisable());

	    dto.setPartyCreditLimit(customerVO.getPartyCreditLimit());
	    dto.setPartyCreditPeriod(customerVO.getPartyCreditPeriod());

	    // GST
	    dto.setGstType(customerVO.getGstType());
	    dto.setGstNo(customerVO.getGstNo());
	    dto.setGstApplicable(customerVO.isGstApplicable());

	    // Address
	    dto.setAddress(customerVO.getAddress());
	    dto.setPincode(customerVO.getPincode());

	    // Contact
	    dto.setEmail(customerVO.getEmail());
	    dto.setWebAddress(customerVO.getWebAddress());
	    dto.setPhone(customerVO.getPhone());
	    dto.setContactPerson(customerVO.getContactPerson());

	    // Company Details
	    dto.setCinNo(customerVO.getCinNo());
	    dto.setOverDueInterest(customerVO.getOverDueInterest());
	    dto.setIntroducedBy(customerVO.getIntroducedBy());
	    dto.setCstNo(customerVO.getCstNo());
	    dto.setEccNo(customerVO.getEccNo());
	    dto.setEccType(customerVO.getEccType());
	    dto.setKstNo(customerVO.getKstNo());

	    // Dates
	    dto.setEffectiveFrom(customerVO.getEffectiveFrom());
	    dto.setDateOfApproval(customerVO.getDateOfApproval());
	    dto.setReAssessmentDate(customerVO.getReAssessmentDate());

	    // Other Details
	    dto.setRange(customerVO.getRange());
	    dto.setRemarks(customerVO.getRemarks());
	    dto.setIsoStatus(customerVO.getIsoStatus());
	    dto.setTypeExtentOfControl(customerVO.getTypeExtentOfControl());

	    dto.setCreditPeriod(customerVO.getCreditPeriod());
	    dto.setApproved(customerVO.isApproved());

	    dto.setScopeOfSupply(customerVO.getScopeOfSupply());
	    dto.setBasisOfApproval(customerVO.getBasisOfApproval());

	    // Bank Details
	    dto.setBankName(customerVO.getBankName());
	    dto.setBankAccountNo(customerVO.getBankAccountNo());
	    dto.setPaymentMode(customerVO.getPaymentMode());
	    dto.setIfscCode(customerVO.getIfscCode());

	    // Audit Details
	    dto.setOrgId(customerVO.getOrgId());
	    dto.setCreatedBy(customerVO.getCreatedBy());
	    dto.setCancelRemarks(customerVO.getCancelRemarks());
	    dto.setActive(customerVO.getActive());
	    dto.setFinancialYear(customerVO.getFinancialYear());
	    
	    List<CustomerContactDetailsResponseDTO> contactResponseList = new ArrayList<>();

	    if (customerVO.getCustomerContactDetails() != null) {

	        for (CustomerContactDetailsVO contactVO : customerVO.getCustomerContactDetails()) {

	            CustomerContactDetailsResponseDTO contactDTO = new CustomerContactDetailsResponseDTO();

	            contactDTO.setId(contactVO.getId());

	            if (contactVO.getPurpose() != null) {

	                DepartmentResponseDTO departmentDTO = new DepartmentResponseDTO();

	                departmentDTO.setId(contactVO.getPurpose().getId());
	                departmentDTO.setDepartmentCode(contactVO.getPurpose().getDepartmentCode());
	                departmentDTO.setDepartmentName(contactVO.getPurpose().getDepartmentName());

	                contactDTO.setPurpose(departmentDTO);
	            }

	            contactDTO.setContactName(contactVO.getContactName());
	            contactDTO.setDesignation(contactVO.getDesignation());
	            contactDTO.setPhone(contactVO.getPhone());
	            contactDTO.setEmail(contactVO.getEmail());
	            contactDTO.setWebsite(contactVO.getWebsite());

	            contactResponseList.add(contactDTO);
	        }
	    }

	    dto.setCustomerContactDetails(contactResponseList);
	    
	    List<CustomerShippingDetailsResponseDTO> shippingResponseList = new ArrayList<>();

	    if (customerVO.getCustomerShippingDetails() != null) {

	        for (CustomerShippingDetailsVO shippingVO : customerVO.getCustomerShippingDetails()) {

	            CustomerShippingDetailsResponseDTO shippingDTO = new CustomerShippingDetailsResponseDTO();

	            shippingDTO.setId(shippingVO.getId());

	            shippingDTO.setShippingAddressType(shippingVO.getShippingAddressType());
	            shippingDTO.setShippingAddress(shippingVO.getShippingAddress());

	            if (shippingVO.getShippingCity() != null) {
	                shippingDTO.setShippingCity(new CityResponseDTO(
	                        shippingVO.getShippingCity().getId(),
	                        shippingVO.getShippingCity().getCityCode(),
	                        shippingVO.getShippingCity().getCityName()));
	            }

	            if (shippingVO.getShippingState() != null) {
	                shippingDTO.setShippingState(new StateResponseDTO(
	                        shippingVO.getShippingState().getId(),
	                        shippingVO.getShippingState().getStateCode(),
	                        shippingVO.getShippingState().getStateName()));
	            }

	            if (shippingVO.getShippingCountry() != null) {
	                shippingDTO.setShippingCountry(new CountryResponseDTO(
	                        shippingVO.getShippingCountry().getId(),
	                        shippingVO.getShippingCountry().getCountryCode(),
	                        shippingVO.getShippingCountry().getCountryName()));
	            }

	            shippingDTO.setShippingPincode(shippingVO.getShippingPincode());

	            shippingResponseList.add(shippingDTO);
	        }
	    }

	    dto.setCustomerShippingDetails(shippingResponseList);
	    
	    List<CustomerItemDetailsResponseDTO> itemResponseList = new ArrayList<>();

	    if (customerVO.getCustomerItemDetailsVO() != null) {

	        for (CustomerItemDetailsVO itemVO : customerVO.getCustomerItemDetailsVO()) {

	            CustomerItemDetailsResponseDTO itemResponse =
	                    new CustomerItemDetailsResponseDTO();

	            itemResponse.setId(itemVO.getId());

	            if (itemVO.getItem() != null) {

	                ItemMasterVO itemMaster = itemVO.getItem();

	                ItemResponseDTO itemDTO = new ItemResponseDTO();

	                itemDTO.setId(itemMaster.getId());
	                itemDTO.setItemCode(itemMaster.getItemCode());
	                itemDTO.setItemDescription(itemMaster.getItemDescription());

	                if (itemMaster.getPrimaryUnit() != null) {

	                    UnitResponseDTO unitDTO = new UnitResponseDTO();

	                    unitDTO.setId(itemMaster.getPrimaryUnit().getId());
	                    unitDTO.setUnitId(itemMaster.getPrimaryUnit().getUnitId());

	                    itemDTO.setUnit(unitDTO);
	                }

	                itemResponse.setItem(itemDTO);
	            }

	            itemResponseList.add(itemResponse);
	        }
	    }

	    dto.setCustomerItemDetails(itemResponseList);

	    return dto;
	}

	
	@Override
	public CustomerResponseDTO getCustomerById(Long id) throws ApplicationException {

	    CustomerVO customerVO = customerRepo.findById(id)
	            .orElseThrow(() -> new ApplicationException("Customer not found"));

	    return convertToResponse(customerVO);
	}
	
	@Override
	public List<CustomerResponseDTO> getCustomerByOrgIdAndBranch(Long orgId, Long branch)
	        throws ApplicationException {

	    List<CustomerVO> customerList =
	            customerRepo.findByOrgIdAndBranch(orgId, branch);

	    List<CustomerResponseDTO> response = new ArrayList<>();

	    for (CustomerVO customerVO : customerList) {
	        response.add(convertToResponse(customerVO));
	    }

	    return response;
	}
	
}
