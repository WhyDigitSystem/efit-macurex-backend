package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.efitops.basesetup.ResponseDTO.CityResponseDTO;
import com.efitops.basesetup.ResponseDTO.CountryResponseDTO;
import com.efitops.basesetup.ResponseDTO.CustomerContactDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.CustomerDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.CustomerItemDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.CustomerResponseDTO;
import com.efitops.basesetup.ResponseDTO.CustomerShippingDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.DepartmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.DocumentTypeMappingBranchResponseDTO;
import com.efitops.basesetup.ResponseDTO.EmployeeDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.GSTStateResponseDTO;
import com.efitops.basesetup.ResponseDTO.ItemResponseDTO;
import com.efitops.basesetup.ResponseDTO.PartyCategoryResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesZoneResponseDTO;
import com.efitops.basesetup.ResponseDTO.StateResponseDTO;
import com.efitops.basesetup.ResponseDTO.UnitResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.CustomerContactDetailsDTO;
import com.efitops.basesetup.dto.CustomerDTO;
import com.efitops.basesetup.dto.CustomerItemDetailsDTO;
import com.efitops.basesetup.dto.CustomerShippingDetailsDTO;
import com.efitops.basesetup.dto.EmployeeResponseDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CityVO;
import com.efitops.basesetup.entity.CountryVO;
import com.efitops.basesetup.entity.CustomerContactDetailsVO;
import com.efitops.basesetup.entity.CustomerItemDetailsVO;
import com.efitops.basesetup.entity.CustomerShippingDetailsVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.GSTStateMasterVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.ListOfValuesDetailsVO;
import com.efitops.basesetup.entity.SalesZoneMasterVO;
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
import com.efitops.basesetup.repository.EmployeeMasterRepo;
import com.efitops.basesetup.repository.EmployeeRepo;
import com.efitops.basesetup.repository.GSTStateMasterRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.ListOfValuesDetailsRepo;
import com.efitops.basesetup.repository.ListOfValuesRepo;
import com.efitops.basesetup.repository.SalesZoneMasterRepo;
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
	ListOfValuesDetailsRepo listOfValuesDetailsRepo;
	
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
	
	@Autowired
	SalesZoneMasterRepo salesZoneMasterRepo;
	
	@Autowired
	EmployeeMasterRepo employeeMasterRepo;
	
	@Autowired
	EmployeeRepo employeeRepo;
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

	    System.out.println("Before save");
	    customerRepo.save(customerVO);
	    System.out.println("After save");

	    System.out.println("Before convert");
	    CustomerResponseDTO responseDTO = convertToResponse(customerVO);
	    System.out.println("After convert");
	    
	    Map<String, Object> response = new HashMap<>();
	    response.put("customer", responseDTO);
	    response.put("message", message);

	    return response;
	}
	
	
	private void createUpdateCustomerVO(CustomerDTO dto, CustomerVO customerVO)
	        throws ApplicationException {

	    // Load Masters
		ListOfValuesDetailsVO customerCategory = null;
		if (dto.getCustomerCategory() != null) {
		    customerCategory = listOfValuesDetailsRepo.findById(dto.getCustomerCategory())
		            .orElseThrow(() -> new ApplicationException("Customer Category Not Found"));
		}

		ListOfValuesDetailsVO customerCategory1 = null;
		if (dto.getCustomerCategory1() != null) {
		    customerCategory1 = listOfValuesDetailsRepo.findById(dto.getCustomerCategory1())
		            .orElseThrow(() -> new ApplicationException("Customer Category1 Not Found"));
		}

		ListOfValuesDetailsVO customerCategory2 = null;
		if (dto.getCustomerCategory2() != null) {
		    customerCategory2 = listOfValuesDetailsRepo.findById(dto.getCustomerCategory2())
		            .orElseThrow(() -> new ApplicationException("Customer Category2 Not Found"));
		}

		ListOfValuesDetailsVO supplierType = null;
		if (dto.getSupplierType() != null) {
		    supplierType = listOfValuesDetailsRepo.findById(dto.getSupplierType())
		            .orElseThrow(() -> new ApplicationException("Supplier Type Not Found"));
		}

		BranchVO branch = null;
		if (dto.getBranch() != null) {
		    branch = branchRepo.findById(dto.getBranch())
		            .orElseThrow(() -> new ApplicationException("Branch Not Found"));
		}

		GSTStateMasterVO gstState = null;
		if (dto.getGstState() != null) {
		    gstState = gstStateRepo.findById(dto.getGstState())
		            .orElseThrow(() -> new ApplicationException("GST State Not Found"));
		}

		CityVO city = null;
		if (dto.getCity() != null) {
		    city = cityRepo.getCityById(dto.getCity());

		    if (city == null) {
		        throw new ApplicationException("City Not Found");
		    }
		}

		StateVO state = null;
		if (dto.getState() != null) {
		    state = stateRepo.findById(dto.getState())
		            .orElseThrow(() -> new ApplicationException("State Not Found"));
		}

		CountryVO country = null;
		if (dto.getCountry() != null) {
		    country = countryRepo.findById(dto.getCountry())
		            .orElseThrow(() -> new ApplicationException("Country Not Found"));
		}
		
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
	    
	    customerVO.setPanNo(dto.getPanNo());
	    customerVO.setEsiNo(dto.getEsiNo());
	    customerVO.setTinNo(dto.getTinNo());

	    EmployeeMasterVO buyerName = null;

	    if (dto.getBuyerName() != null) {

	        buyerName = employeeMasterRepo.findById(dto.getBuyerName())
	                .orElseThrow(() -> new ApplicationException("Buyer Name Not Found"));

	    }

	    customerVO.setBuyerName(buyerName);
	    
	    
	    customerVO.setCustomerLegalName(dto.getCustomerLegalName());
	    customerVO.setTradeName(dto.getTradeName());
	    customerVO.setGroupCompany(dto.isGroupCompany());
	    
	    SalesZoneMasterVO zone = null;

	    if (dto.getZone() != null) {

	        zone = salesZoneMasterRepo.findById(dto.getZone())
	                .orElseThrow(() -> new ApplicationException("Sales Zone Not Found"));

	    }

	    customerVO.setZone(zone);	  
	    
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
	    ListOfValuesDetailsVO belongsTo = null;

	    if (dto.getBelongsTo() != null) {

	        belongsTo = listOfValuesDetailsRepo.findById(dto.getBelongsTo())
	                .orElseThrow(() -> new ApplicationException("Belongs To Not Found"));

	    }

	    customerVO.setBelongsTo(belongsTo);
	    
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
	                .orElseThrow(() -> new ApplicationException("Shipping City not found"));
	        vo.setShippingCity(city1);
	    }

	    if (shippingDTO.getShippingState() != null) {
	        StateVO state1 = stateRepo.findById(shippingDTO.getShippingState())
	                .orElseThrow(() -> new ApplicationException("Shipping State not found"));
	        vo.setShippingState(state1);
	    }

	    if (shippingDTO.getShippingCountry() != null) {
	        CountryVO country1 = countryRepo.findById(shippingDTO.getShippingCountry())
	                .orElseThrow(() -> new ApplicationException("Shipping Country not found"));
	        vo.setShippingCountry(country1);
	    }

	    vo.setShippingPincode(shippingDTO.getShippingPincode());
	    vo.setName(shippingDTO.getName());
	    vo.setFax(shippingDTO.getFax());
	    vo.setEmail(shippingDTO.getEmail());
	    vo.setPhoneNo(shippingDTO.getPhoneNo());

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

	        ItemMasterVO itemMaster = itemMasterRepo.getItemById(itemDTO.getItemId());

	        if (itemMaster == null) {
	            throw new ApplicationException("Item not found");
	        }	        
	                
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
	                customerVO.getCustomerCategory().getValueCode(),
	                customerVO.getCustomerCategory().getValueDescription()));
	    }

	    if (customerVO.getCustomerCategory1() != null) {
	        dto.setCustomerCategory1(new PartyCategoryResponseDTO(
	                customerVO.getCustomerCategory1().getId(),
	                customerVO.getCustomerCategory1().getValueCode(),
	                customerVO.getCustomerCategory1().getValueDescription()));
	    }

	    if (customerVO.getCustomerCategory2() != null) {
	        dto.setCustomerCategory2(new PartyCategoryResponseDTO(
	                customerVO.getCustomerCategory2().getId(),
	                customerVO.getCustomerCategory2().getValueCode(),
	                customerVO.getCustomerCategory2().getValueDescription()));
	    }

	    if (customerVO.getSupplierType() != null) {
	        dto.setSupplierType(new PartyCategoryResponseDTO(
	                customerVO.getSupplierType().getId(),
	                customerVO.getSupplierType().getValueCode(),
	                customerVO.getSupplierType().getValueDescription()));
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
	    if (customerVO.getBelongsTo() != null) {
	        dto.setBelongsTo(new PartyCategoryResponseDTO(
	                customerVO.getBelongsTo().getId(),
	                customerVO.getBelongsTo().getValueCode(),
	                customerVO.getBelongsTo().getValueDescription()
	        ));
	    }
	    dto.setGroupCompany(customerVO.isGroupCompany());

	    if (customerVO.getZone() != null) {
	        dto.setZone(new SalesZoneResponseDTO(
	                customerVO.getZone().getId(),
	                customerVO.getZone().getZonedescription()
	        ));
	    }	
	    
	    if (customerVO.getBuyerName() != null) {
	        dto.setBuyerName(new EmployeeResponseDTO(
	                customerVO.getBuyerName().getId(),
	                customerVO.getBuyerName().getEmployeeName()
	        ));
	    }
	    
	    dto.setVendorCode(customerVO.getVendorCode());
	    dto.setGroupName(customerVO.getGroupName());

	    dto.setRegistered(customerVO.isRegistered());
	    dto.setExcisable(customerVO.isExcisable());

	    dto.setPartyCreditLimit(customerVO.getPartyCreditLimit());
	    dto.setPartyCreditPeriod(customerVO.getPartyCreditPeriod());
	    dto.setPanNo(customerVO.getPanNo());
	    dto.setEsiNo(customerVO.getEsiNo());
	    dto.setTinNo(customerVO.getTinNo());
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
	            shippingDTO.setName(shippingVO.getName());
	            shippingDTO.setFax(shippingVO.getFax());
	            shippingDTO.setEmail(shippingVO.getEmail());
	            shippingDTO.setPhoneNo(shippingVO.getPhoneNo());
	            
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
	                
	                System.out.println("Item Id : " + itemMaster.getId());
	                System.out.println("Item Code : " + itemMaster.getItemCode());
	                System.out.println("Primary Unit : " +
	                        (itemMaster.getPrimaryUnit() == null ? "NULL" : itemMaster.getPrimaryUnit().getId()));
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
	
	//dropdown wmployee
	
	@Override
	public List<EmployeeDropdownResponseDTO> getPurchaseEmployees(Long orgId, Long branch)
	        throws ApplicationException {

	    List<Object[]> list = employeeRepo.getPurchaseEmployees(orgId, branch);

	    return convertToEmployeeDropdownDTO(list);
	}
	
	private List<EmployeeDropdownResponseDTO> convertToEmployeeDropdownDTO(List<Object[]> list) {

	    List<EmployeeDropdownResponseDTO> responseList = new ArrayList<>();

	    for (Object[] obj : list) {

	        EmployeeDropdownResponseDTO dto = new EmployeeDropdownResponseDTO();

	        dto.setEmployeeId(obj[0] != null ? ((Number) obj[0]).longValue() : null);
	        dto.setEmployeeCode(obj[1] != null ? obj[1].toString() : null);
	        dto.setEmployeeName(obj[2] != null ? obj[2].toString() : null);

	        responseList.add(dto);
	    }

	    return responseList;
	}
	
	
	
}
