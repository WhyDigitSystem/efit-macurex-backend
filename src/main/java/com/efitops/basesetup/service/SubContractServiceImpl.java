package com.efitops.basesetup.service;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.BomResponseDTO;
import com.efitops.basesetup.ResponseDTO.CustomerDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.DeliveryChallanSubcontractingDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.DeliveryChallanSubcontractingResponseDTO;
import com.efitops.basesetup.ResponseDTO.DepartmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.EmployeeDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.GSTStateMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.HsnResponseDTO;
import com.efitops.basesetup.ResponseDTO.ItemResponseDTO;
import com.efitops.basesetup.ResponseDTO.JobOrderAmendmentDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.JobOrderAmendmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.JobOrderAttachmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.JobOrderDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.JobOrderResponseDTO;
import com.efitops.basesetup.ResponseDTO.JobOrderTaxDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.LocationMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.ServiceAccMasterResponse1DTO;
import com.efitops.basesetup.ResponseDTO.SubContractSupplyScheduleDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.SubContractSupplyScheduleItemDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.SubContractSupplyScheduleResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierRateContractAmendmentItemDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierRateContractAmendmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierRateContractItemDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierRateContractResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierRateContractTaxDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.TransportResponseDTO;
import com.efitops.basesetup.ResponseDTO.UnitResponseDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.DeliveryChallanSubcontractingDTO;
import com.efitops.basesetup.dto.DeliveryChallanSubcontractingDetailsDTO;
import com.efitops.basesetup.dto.EmployeeResponseDTO;
import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;
import com.efitops.basesetup.dto.JobOrderAmendmentDTO;
import com.efitops.basesetup.dto.JobOrderAmendmentDetailsDTO;
import com.efitops.basesetup.dto.JobOrderDTO;
import com.efitops.basesetup.dto.JobOrderDetailsDTO;
import com.efitops.basesetup.dto.JobOrderTaxDetailsDTO;
import com.efitops.basesetup.dto.SubContractSupplyScheduleDTO;
import com.efitops.basesetup.dto.SubContractSupplyScheduleDetailsDTO;
import com.efitops.basesetup.dto.SubContractSupplyScheduleItemDetailsDTO;
import com.efitops.basesetup.dto.SupplierRateContractAmendmentDTO;
import com.efitops.basesetup.dto.SupplierRateContractAmendmentItemDetailsDTO;
import com.efitops.basesetup.dto.SupplierRateContractDTO;
import com.efitops.basesetup.dto.SupplierRateContractItemDetailsDTO;
import com.efitops.basesetup.dto.SupplierRateContractTaxDetailsDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;
import com.efitops.basesetup.entity.BomVO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.DeliveryChallanSubcontractingDetailsVO;
import com.efitops.basesetup.entity.DeliveryChallanSubcontractingVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.GSTStateMasterVO;
import com.efitops.basesetup.entity.HsnVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.JobOrderAmendmentDetailsVO;
import com.efitops.basesetup.entity.JobOrderAmendmentVO;
import com.efitops.basesetup.entity.JobOrderAttachmentVO;
import com.efitops.basesetup.entity.JobOrderDetailsVO;
import com.efitops.basesetup.entity.JobOrderTaxDetailsVO;
import com.efitops.basesetup.entity.JobOrderVO;
import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.entity.ServiceAccMasterVO;
import com.efitops.basesetup.entity.SubContractSupplyScheduleDetailsVO;
import com.efitops.basesetup.entity.SubContractSupplyScheduleItemDetailsVO;
import com.efitops.basesetup.entity.SubContractSupplyScheduleVO;
import com.efitops.basesetup.entity.SupplierRateContractAmendmentItemDetailsVO;
import com.efitops.basesetup.entity.SupplierRateContractAmendmentVO;
import com.efitops.basesetup.entity.SupplierRateContractItemDetailsVO;
import com.efitops.basesetup.entity.SupplierRateContractTaxDetailsVO;
import com.efitops.basesetup.entity.SupplierRateContractVO;
import com.efitops.basesetup.entity.TransportMasterVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BomRepo;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.DeliveryChallanSubcontractingDetailsRepo;
import com.efitops.basesetup.repository.DeliveryChallanSubcontractingRepo;
import com.efitops.basesetup.repository.DepartmentRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.EmployeeMasterRepo;
import com.efitops.basesetup.repository.GSTStateMasterRepo;
import com.efitops.basesetup.repository.HsnRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.JobOrderAmendmentDetailsRepo;
import com.efitops.basesetup.repository.JobOrderAmendmentRepo;
import com.efitops.basesetup.repository.JobOrderAttachmentRepo;
import com.efitops.basesetup.repository.JobOrderDetailsRepo;
import com.efitops.basesetup.repository.JobOrderRepo;
import com.efitops.basesetup.repository.JobOrderTaxDetailsRepo;
import com.efitops.basesetup.repository.ListOfValuesDetailsRepo;
import com.efitops.basesetup.repository.LocationRepo;
import com.efitops.basesetup.repository.ServiceAccMasterRepo;
import com.efitops.basesetup.repository.SubContractSupplyScheduleDetailsRepo;
import com.efitops.basesetup.repository.SubContractSupplyScheduleItemDetailsRepo;
import com.efitops.basesetup.repository.SubContractSupplyScheduleRepo;
import com.efitops.basesetup.repository.SupplierRateContractAmendmentItemDetailsRepo;
import com.efitops.basesetup.repository.SupplierRateContractAmendmentRepo;
import com.efitops.basesetup.repository.SupplierRateContractItemDetailsRepo;
import com.efitops.basesetup.repository.SupplierRateContractRepo;
import com.efitops.basesetup.repository.SupplierRateContractTaxDetailsRepo;
import com.efitops.basesetup.repository.TransportRepo;
import com.efitops.basesetup.repository.UnitMasterRepo;

import io.jsonwebtoken.io.IOException;
@Service
public class SubContractServiceImpl implements SubContractService {

	public static final Logger LOGGER = LoggerFactory.getLogger(RejectionInvoiceServiceImpl.class);

	@Autowired
	SupplierRateContractRepo supplierRateContractRepo;

	@Autowired
	SupplierRateContractItemDetailsRepo supplierRateContractItemDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	SupplierRateContractTaxDetailsRepo supplierRateContractTaxDetailsRepo;

	@Autowired
	private BranchRepo branchRepo;

	@Autowired
	private DepartmentRepo departmentRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private GSTStateMasterRepo gstStateMasterRepo;

	@Autowired
	private ItemMasterRepo itemMasterRepo;

	@Autowired
	private UnitMasterRepo unitMasterRepo;

	@Autowired
	private HsnRepo hsnRepo;

	@Autowired
	private ListOfValuesDetailsRepo listOfValuesDetailsRepo;

	@Autowired
	private EmployeeMasterRepo employeeMasterRepo;

	@Autowired
	ServiceAccMasterRepo serviceAccMasterRepo;
	
    @Autowired
    JobOrderRepo jobOrderRepo;
    
    @Autowired
    JobOrderDetailsRepo jobOrderDetailsRepo;

    @Autowired
    JobOrderTaxDetailsRepo jobOrderTaxDetailsRepo;
    
    @Autowired
    BomRepo bomRepo;
    
    @Autowired
    JobOrderAttachmentRepo jobOrderAttachmentRepo;
    
    @Value("${server.base-url}")
    private String serverBaseUrl;
    

	@Value("${joborder.upload.path}")
	private String joborderUploadPath;
	
	@Autowired
	JobOrderAmendmentRepo jobOrderAmendmentRepo;
    
	@Autowired
	JobOrderAmendmentDetailsRepo jobOrderAmendmentDetailsRepo;
	
	@Autowired
	DeliveryChallanSubcontractingRepo deliveryChallanSubcontractingRepo;
	
	@Autowired
	DeliveryChallanSubcontractingDetailsRepo deliveryChallanSubcontractingDetailsRepo;
	
	@Autowired
	LocationRepo locationRepo;
	
	@Autowired
	TransportRepo transportRepo;
	
	@Autowired
	SubContractSupplyScheduleRepo subContractSupplyScheduleRepo;
	
	@Autowired
	SubContractSupplyScheduleItemDetailsRepo subContractSupplyScheduleItemDetailsRepo;
	
	@Autowired
	SubContractSupplyScheduleDetailsRepo subContractSupplyScheduleDetailsRepo;
	
	@Autowired
	SupplierRateContractAmendmentRepo supplierRateContractAmendmentRepo;
	
	@Autowired
	SupplierRateContractAmendmentItemDetailsRepo supplierRateContractAmendmentItemDetailsRepo;
	
	@Override
	@Transactional
	public Map<String, Object> createUpdateSupplierRateContract(SupplierRateContractDTO dto)
			throws ApplicationException {

		String screenCode = "SRC";

		Map<String, Object> response = new HashMap<>();

		String message;

		SupplierRateContractVO supplierRateContractVO;

		// =========================================================
		// CREATE
		// =========================================================

		if (ObjectUtils.isEmpty(dto.getId())) {

			supplierRateContractVO = new SupplierRateContractVO();

			String docId = supplierRateContractRepo.getSupplierRateContractDocId(dto.getOrgId(), dto.getFinancialYear(),
					screenCode);

			supplierRateContractVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(dto.getOrgId(), dto.getFinancialYear(), screenCode);

			if (documentTypeMappingDetailsVO != null) {

				documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

				documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO
	            );
	        }

			supplierRateContractVO.setCreatedBy(dto.getCreatedBy());
			supplierRateContractVO.setUpdatedBy(dto.getCreatedBy());

			message = "Supplier Rate Contract Created Successfully";

		}

		// =========================================================
		// UPDATE
		// =========================================================

		else {

			supplierRateContractVO = supplierRateContractRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Supplier Rate Contract Not Found"));

			/*
			 * Delete existing child records.
			 *
			 * We are replacing old item/tax details with the details coming from the
			 * request.
			 */

			supplierRateContractItemDetailsRepo.deleteAll(
					supplierRateContractItemDetailsRepo.findBySupplierRateContractVO(supplierRateContractVO));

			supplierRateContractTaxDetailsRepo
					.deleteAll(supplierRateContractTaxDetailsRepo.findBySupplierRateContractVO(supplierRateContractVO));

			supplierRateContractVO.getSupplierRateContractItemDetailsVO().clear();

			supplierRateContractVO.getSupplierRateContractTaxDetailsVO().clear();

			supplierRateContractVO.setUpdatedBy(dto.getCreatedBy());

			message = "Supplier Rate Contract Updated Successfully";
		}

		// =========================================================
		// HEADER + CHILD MAPPING
		// =========================================================

		getSupplierRateContractVOFromDTO(dto, supplierRateContractVO);

		// =========================================================
		// SAVE HEADER + CHILDREN
		// =========================================================

		supplierRateContractVO = supplierRateContractRepo.saveAndFlush(supplierRateContractVO);

		// =========================================================
		// RESPONSE
		// =========================================================

		SupplierRateContractResponseDTO responseDTO = convertToResponse(supplierRateContractVO);

		response.put("message", message);

		response.put("supplierRateContract", responseDTO);

		return response;
	}

	private void getSupplierRateContractVOFromDTO(SupplierRateContractDTO dto,
			SupplierRateContractVO supplierRateContractVO) throws ApplicationException {

		// =========================================================
		// HEADER MASTER MAPPING
		// =========================================================

		BranchVO branch = branchRepo.findById(dto.getBranch())
				.orElseThrow(() -> new ApplicationException("Branch Not Found"));

		DepartmentVO department = departmentRepo.findById(dto.getDepartment())
				.orElseThrow(() -> new ApplicationException("Department Not Found"));

		CustomerVO customer = customerRepo.findById(dto.getCustomer())
				.orElseThrow(() -> new ApplicationException("Customer Not Found"));

		GSTStateMasterVO gstState = gstStateMasterRepo.findById(dto.getGstState())
				.orElseThrow(() -> new ApplicationException("GST State Not Found"));

		ServiceAccMasterVO serviceName = serviceAccMasterRepo.findById(dto.getServiceName())
				.orElseThrow(() -> new ApplicationException("Service Name Not Found"));

		System.out.println("1. HSN ID = " + dto.getHsnSacCode());

		HsnVO hsnSacCode = hsnRepo.findByHsn_Id(dto.getHsnSacCode())
		        .orElseThrow(() -> new ApplicationException("HSN/SAC Code Not Found"));
		
		System.out.println("2. HSN FOUND = " + hsnSacCode.getId());

		supplierRateContractVO.setHsnSacCode(hsnSacCode);

		System.out.println("3. HSN SET SUCCESS");

		EmployeeMasterVO preparedBy = employeeMasterRepo.findById(dto.getPreparedBy())
				.orElseThrow(() -> new ApplicationException("Prepared By Employee Not Found"));

		EmployeeMasterVO authoriedBy = employeeMasterRepo.findById(dto.getAuthoriedBy())
				.orElseThrow(() -> new ApplicationException("Authoried By Employee Not Found"));

		// =========================================================
		// SET HEADER
		// =========================================================

		supplierRateContractVO.setBranch(branch);

		supplierRateContractVO.setDepartment(department);

		supplierRateContractVO.setBelongsTo(dto.getBelongsTo());

		supplierRateContractVO.setValidFrom(dto.getValidFrom());

		supplierRateContractVO.setValidTo(dto.getValidTo());

		supplierRateContractVO.setCustomer(customer);

		supplierRateContractVO.setContractFor(dto.getContractFor());

		supplierRateContractVO.setGstState(gstState);

		supplierRateContractVO.setIgstApplicable(dto.isIgstApplicable());

		supplierRateContractVO.setDeliveryDate(dto.getDeliveryDate());

		supplierRateContractVO.setTaxType(dto.getTaxType());

		supplierRateContractVO.setServiceName(serviceName);

//		supplierRateContractVO.setHsnSacCode(hsnSacCode);

		supplierRateContractVO.setScrap(dto.isScrap());

		supplierRateContractVO.setTaxPercentage(dto.getTaxPercentage());

		supplierRateContractVO.setDiscount(dto.getDiscount());

		supplierRateContractVO.setPaymentsTerms(dto.getPaymentsTerms());

		supplierRateContractVO.setDeliveryTerms(dto.getDeliveryTerms());

		supplierRateContractVO.setFreight(dto.getFreight());

		supplierRateContractVO.setFreightType(dto.getFreightType());

		supplierRateContractVO.setPackingType(dto.getPackingType());

		supplierRateContractVO.setInsurance(dto.getInsurance());

		supplierRateContractVO.setModeOfDespatch(dto.getModeOfDespatch());

		supplierRateContractVO.setInlandCharge(dto.getInlandCharge());

		supplierRateContractVO.setPreparedBy(preparedBy);

		supplierRateContractVO.setAuthoriedBy(authoriedBy);

		supplierRateContractVO.setNarration(dto.getNarration());
		
		supplierRateContractVO.setFreightType(dto.getFreightType());

		supplierRateContractVO.setPackingType(dto.getPackingType());


		// =========================================================
		// COMMON FIELDS
		// =========================================================

		supplierRateContractVO.setOrgId(dto.getOrgId());

		supplierRateContractVO.setFinancialYear(dto.getFinancialYear());

		supplierRateContractVO.setCreatedBy(dto.getCreatedBy());

		supplierRateContractVO.setCancelRemarks(dto.getCancelRemarks());

		supplierRateContractVO.setActive(dto.isActive());

		// =========================================================
		// ITEM DETAILS
		// =========================================================

		List<SupplierRateContractItemDetailsVO> itemDetailList = new ArrayList<>();

		if (dto.getSupplierRateContractItemDetailsDTO() != null
				&& !dto.getSupplierRateContractItemDetailsDTO().isEmpty()) {

			for (SupplierRateContractItemDetailsDTO childDTO : dto.getSupplierRateContractItemDetailsDTO()) {

				SupplierRateContractItemDetailsVO itemDetailVO = new SupplierRateContractItemDetailsVO();

				// -------------------------------------------------
				// Incoming Item
				// -------------------------------------------------

				ItemMasterVO incomingItemCode = itemMasterRepo.findById(childDTO.getIncomingItemCode())
						.orElseThrow(() -> new ApplicationException("Incoming Item Not Found"));

				// -------------------------------------------------
				// Purchase Unit
				// -------------------------------------------------

				UnitMasterVO purchaseUnit = unitMasterRepo.findById(childDTO.getPurchaseUnit())
						.orElseThrow(() -> new ApplicationException("Purchase Unit Not Found"));

				// -------------------------------------------------
				// Child Mapping
				// -------------------------------------------------

				itemDetailVO.setIncomingItemCode(incomingItemCode);

				itemDetailVO.setPurchaseUnit(purchaseUnit);

				itemDetailVO.setPlatingType(childDTO.getPlatingType());

				itemDetailVO.setThickness(childDTO.getThickness());

				itemDetailVO.setRate(childDTO.getRate());

				BigDecimal rate = childDTO.getRate() != null ? childDTO.getRate() : BigDecimal.ZERO;

				BigDecimal igstRate = childDTO.getIgstRate() != null ? childDTO.getIgstRate() : BigDecimal.ZERO;

				BigDecimal cgstRate = childDTO.getCgstRate() != null ? childDTO.getCgstRate() : BigDecimal.ZERO;

				BigDecimal sgstRate = childDTO.getSgstRate() != null ? childDTO.getSgstRate() : BigDecimal.ZERO;

				// =====================================================
				// IGST
				// =====================================================

				if (dto.isIgstApplicable()) {

					BigDecimal igstAmount = rate.multiply(igstRate).divide(BigDecimal.valueOf(100), 2,
							RoundingMode.HALF_UP);

					itemDetailVO.setIgstRate(igstRate);
					itemDetailVO.setIgstAmount(igstAmount);

					itemDetailVO.setCgstRate(BigDecimal.ZERO);
					itemDetailVO.setCgstAmount(BigDecimal.ZERO);

					itemDetailVO.setSgstRate(BigDecimal.ZERO);
					itemDetailVO.setSgstAmount(BigDecimal.ZERO);
				}

				// =====================================================
				// CGST + SGST
				// =====================================================

				else {

					BigDecimal cgstAmount = rate.multiply(cgstRate).divide(BigDecimal.valueOf(100), 2,
							RoundingMode.HALF_UP);

					BigDecimal sgstAmount = rate.multiply(sgstRate).divide(BigDecimal.valueOf(100), 2,
							RoundingMode.HALF_UP);

					itemDetailVO.setCgstRate(cgstRate);
					itemDetailVO.setCgstAmount(cgstAmount);

					itemDetailVO.setSgstRate(sgstRate);
					itemDetailVO.setSgstAmount(sgstAmount);

					itemDetailVO.setIgstRate(BigDecimal.ZERO);
					itemDetailVO.setIgstAmount(BigDecimal.ZERO);
				}
				itemDetailVO.setValidFrom(childDTO.getValidFrom());

				itemDetailVO.setValidTo(childDTO.getValidTo());

				BigDecimal toolAmortizationAmount = itemDetailVO.getIgstAmount().add(itemDetailVO.getCgstAmount())
						.add(itemDetailVO.getSgstAmount());

				itemDetailVO.setToolAmortizationRate(toolAmortizationAmount);
				// -------------------------------------------------
				// Header Mapping
				// -------------------------------------------------

				itemDetailVO.setSupplierRateContractVO(supplierRateContractVO);

				itemDetailList.add(itemDetailVO);
			}
		}

		// =========================================================
		// ADD ITEM DETAILS TO HEADER
		// =========================================================

		supplierRateContractVO.getSupplierRateContractItemDetailsVO().clear();

		for (SupplierRateContractItemDetailsVO itemDetail : itemDetailList) {

			itemDetail.setSupplierRateContractVO(supplierRateContractVO);

			supplierRateContractVO.getSupplierRateContractItemDetailsVO().add(itemDetail);
		}

		// =========================================================
		// TAX DETAILS
		// =========================================================

		List<SupplierRateContractTaxDetailsVO> taxDetailList = new ArrayList<>();

		if (dto.getSupplierRateContractTaxDetailsDTO() != null
				&& !dto.getSupplierRateContractTaxDetailsDTO().isEmpty()) {

			for (SupplierRateContractTaxDetailsDTO taxDTO : dto.getSupplierRateContractTaxDetailsDTO()) {

				SupplierRateContractTaxDetailsVO taxVO = new SupplierRateContractTaxDetailsVO();

				taxVO.setParticulars(taxDTO.getParticulars());

				taxVO.setAmount(taxDTO.getAmount());

				// -------------------------------------------------
				// Header Mapping
				// -------------------------------------------------

				taxVO.setSupplierRateContractVO(supplierRateContractVO);

				taxDetailList.add(taxVO);
			}
		}

		// =========================================================
		// ADD TAX DETAILS TO HEADER
		// =========================================================

		supplierRateContractVO.getSupplierRateContractTaxDetailsVO().clear();

		for (SupplierRateContractTaxDetailsVO taxDetail : taxDetailList) {

			taxDetail.setSupplierRateContractVO(supplierRateContractVO);

			supplierRateContractVO.getSupplierRateContractTaxDetailsVO().add(taxDetail);
		}
	}

	private SupplierRateContractResponseDTO convertToResponse(SupplierRateContractVO vo) {

		SupplierRateContractResponseDTO dto = new SupplierRateContractResponseDTO();

		// ===================== HEADER MAPPING =====================

		dto.setId(vo.getId());
		dto.setDocId(vo.getDocId());
		dto.setDocDate(vo.getDocDate());
		dto.setBelongsTo(vo.getBelongsTo());
		dto.setValidFrom(vo.getValidFrom());
		dto.setValidTo(vo.getValidTo());
		dto.setContractFor(vo.getContractFor());
		dto.setIgstApplicable(vo.isIgstApplicable());
		dto.setDeliveryDate(vo.getDeliveryDate());
		dto.setTaxType(vo.getTaxType());
		dto.setScrap(vo.isScrap());
		dto.setTaxPercentage(vo.getTaxPercentage());
		dto.setDiscount(vo.getDiscount());
		dto.setPaymentsTerms(vo.getPaymentsTerms());
		dto.setDeliveryTerms(vo.getDeliveryTerms());
		dto.setFreight(vo.getFreight());
		dto.setInsurance(vo.getInsurance());
		dto.setModeOfDespatch(vo.getModeOfDespatch());
		dto.setInlandCharge(vo.getInlandCharge());
		dto.setNarration(vo.getNarration());

		// ===================== COMMON FIELDS =====================

		dto.setOrgId(vo.getOrgId());
		dto.setFinancialYear(vo.getFinancialYear());
		dto.setCreatedBy(vo.getCreatedBy());
		dto.setUpdatedBy(vo.getUpdatedBy());
		dto.setCancelRemarks(vo.getCancelRemarks());
		dto.setActive(vo.getActive());

		// ===================== BRANCH MAPPING =====================

		if (vo.getBranch() != null) {

			dto.setBranch(new BranchResponseDTO(vo.getBranch().getId(), vo.getBranch().getBranchCode(),
					vo.getBranch().getBranchName()));
		}

		// ===================== DEPARTMENT MAPPING =====================

		if (vo.getDepartment() != null) {

			dto.setDepartment(new DepartmentResponseDTO(vo.getDepartment().getId(),
					vo.getDepartment().getDepartmentCode(), vo.getDepartment().getDepartmentName()));
		}

		// ===================== CUSTOMER MAPPING =====================

		if (vo.getCustomer() != null) {

			CustomerDropdownResponseDTO customerDTO = new CustomerDropdownResponseDTO();

			customerDTO.setCustomerId(vo.getCustomer().getId());

			customerDTO.setCustomerCode(vo.getCustomer().getCustomerCode());

			customerDTO.setCustomerName(vo.getCustomer().getCustomerName());

			customerDTO.setAddress(vo.getCustomer().getAddress());

			customerDTO.setGstState(
					vo.getCustomer().getGstState() != null ? vo.getCustomer().getGstState().getStateCode() : null);

			customerDTO.setGstNo(vo.getCustomer().getGstNo());

			customerDTO.setIgstApplicable(vo.getCustomer().isGstApplicable());

			customerDTO.setGstType(vo.getCustomer().getGstType());

			dto.setCustomer(customerDTO);
		}

		// ===================== GST STATE MAPPING =====================

		if (vo.getGstState() != null) {

			GSTStateMasterResponseDTO gstStateDTO = new GSTStateMasterResponseDTO();

			gstStateDTO.setId(vo.getGstState().getId());

			gstStateDTO.setGstState(vo.getGstState().getStateName());

			gstStateDTO.setGstStateCode(vo.getGstState().getStateCode());

			dto.setGstState(gstStateDTO);
		}

		// ===================== SERVICE NAME MAPPING =====================

		if (vo.getServiceName() != null) {

			ServiceAccMasterResponse1DTO serviceDTO = new ServiceAccMasterResponse1DTO();

			serviceDTO.setId(vo.getServiceName().getId());

			serviceDTO.setServiceName(vo.getServiceName().getServiceName());

			serviceDTO.setServiceDescription(vo.getServiceName().getServiceDescription());

			dto.setServiceName(serviceDTO);
		}

		// ===================== HSN / SAC MAPPING =====================

		if (vo.getHsnSacCode() != null) {

			HsnResponseDTO hsnDTO = new HsnResponseDTO();

			hsnDTO.setId(vo.getHsnSacCode().getId());

			hsnDTO.setHsn(vo.getHsnSacCode().getHsn());

			hsnDTO.setDescription(vo.getHsnSacCode().getDescription());

			dto.setHsnSacCode(hsnDTO);
		}

		// ===================== FREIGHT TYPE MAPPING =====================

//		if (vo.getFreightType() != null) {
//
//			ListOfValuesDetailsResponseDTO freightTypeDTO = new ListOfValuesDetailsResponseDTO();
//
//			freightTypeDTO.setId(vo.getFreightType().getId());
//
//			freightTypeDTO.setCode(vo.getFreightType().getValueCode());
//
//			freightTypeDTO.setDescription(vo.getFreightType().getValueDescription());
//
//			dto.setFreightType(freightTypeDTO);
//		}

		// ===================== PACKING TYPE MAPPING =====================

//		if (vo.getPackingType() != null) {
//
//			ListOfValuesDetailsResponseDTO packingTypeDTO = new ListOfValuesDetailsResponseDTO();
//
//			packingTypeDTO.setId(vo.getPackingType().getId());
//
//			packingTypeDTO.setCode(vo.getPackingType().getValueCode());
//
//			packingTypeDTO.setDescription(vo.getPackingType().
//());
//
//			dto.setPackingType(packingTypeDTO);
//		}

		// ===================== PREPARED BY MAPPING =====================

		if (vo.getPreparedBy() != null) {

			EmployeeDropdownResponseDTO preparedByDTO = new EmployeeDropdownResponseDTO();

			preparedByDTO.setEmployeeId(vo.getPreparedBy().getId());

			preparedByDTO.setEmployeeCode(vo.getPreparedBy().getEmployeeId());

			preparedByDTO.setEmployeeName(vo.getPreparedBy().getEmployeeName());

			preparedByDTO.setEmail(vo.getPreparedBy().getEmail());

			dto.setPreparedBy(preparedByDTO);
		}

		// ===================== AUTHORISED BY MAPPING =====================

		if (vo.getAuthoriedBy() != null) {

			EmployeeDropdownResponseDTO authoriedByDTO = new EmployeeDropdownResponseDTO();

			authoriedByDTO.setEmployeeId(vo.getAuthoriedBy().getId());

			authoriedByDTO.setEmployeeCode(vo.getAuthoriedBy().getEmployeeId());

			authoriedByDTO.setEmployeeName(vo.getAuthoriedBy().getEmployeeName());

			authoriedByDTO.setEmail(vo.getAuthoriedBy().getEmail());

			dto.setAuthoriedBy(authoriedByDTO);
		}

		// ===================== ITEM DETAILS MAPPING =====================

		List<SupplierRateContractItemDetailsResponseDTO> itemResponse = new ArrayList<>();

		if (vo.getSupplierRateContractItemDetailsVO() != null) {

			for (SupplierRateContractItemDetailsVO detail : vo.getSupplierRateContractItemDetailsVO()) {

				SupplierRateContractItemDetailsResponseDTO detailDTO = new SupplierRateContractItemDetailsResponseDTO();

				detailDTO.setId(detail.getId());

				// ===================== ITEM MAPPING =====================

				if (detail.getIncomingItemCode() != null) {

					ItemMasterResponseDetailsDTO itemDTO = new ItemMasterResponseDetailsDTO();

					itemDTO.setId(detail.getIncomingItemCode().getId());

					itemDTO.setItemCode(detail.getIncomingItemCode().getItemCode());

					itemDTO.setItemDescription(detail.getIncomingItemCode().getItemDescription());

					// ===================== UNIT =====================

					if (detail.getIncomingItemCode().getPrimaryUnit() != null) {

						UnitMasterResponseDTO unitDTO = new UnitMasterResponseDTO();

						unitDTO.setId(detail.getIncomingItemCode().getPrimaryUnit().getId());

						unitDTO.setUnitId(detail.getIncomingItemCode().getPrimaryUnit().getUnitId());

						unitDTO.setUnitDescription(detail.getIncomingItemCode().getPrimaryUnit().getDescription());

						itemDTO.setUnit(unitDTO);
					}

					// ===================== HSN =====================

					if (detail.getIncomingItemCode().getHsnCode() != null) {

						HsnResponseDTO hsnDTO = new HsnResponseDTO();

						hsnDTO.setId(detail.getIncomingItemCode().getHsnCode().getId());

						hsnDTO.setHsn(detail.getIncomingItemCode().getHsnCode().getHsn());

						hsnDTO.setDescription(detail.getIncomingItemCode().getHsnCode().getDescription());

						itemDTO.setHsn(hsnDTO);
					}

					detailDTO.setIncomingItemCode(itemDTO);
				}

				// ===================== ITEM FIELDS =====================

				detailDTO.setPlatingType(detail.getPlatingType());

				detailDTO.setThickness(detail.getThickness());

				detailDTO.setRate(detail.getRate());

				detailDTO.setSgstRate(detail.getSgstRate());

				detailDTO.setSgstAmount(detail.getSgstAmount());

				detailDTO.setCgstRate(detail.getCgstRate());

				detailDTO.setCgstAmount(detail.getCgstAmount());

				detailDTO.setIgstRate(detail.getIgstRate());

				detailDTO.setIgstAmount(detail.getIgstAmount());

				detailDTO.setValidFrom(detail.getValidFrom());

				detailDTO.setValidTo(detail.getValidTo());

				detailDTO.setToolAmortizationRate(detail.getToolAmortizationRate());

				itemResponse.add(detailDTO);
			}
		}

		dto.setSupplierRateContractItemDetailsDTO(itemResponse);

		// ===================== TAX DETAILS MAPPING =====================

		List<SupplierRateContractTaxDetailsResponseDTO> taxResponse = new ArrayList<>();

		if (vo.getSupplierRateContractTaxDetailsVO() != null) {

			for (SupplierRateContractTaxDetailsVO tax : vo.getSupplierRateContractTaxDetailsVO()) {

				SupplierRateContractTaxDetailsResponseDTO taxDTO = new SupplierRateContractTaxDetailsResponseDTO();

				taxDTO.setId(tax.getId());

				taxDTO.setParticulars(tax.getParticulars());

				taxDTO.setAmount(tax.getAmount());

				taxResponse.add(taxDTO);
			}
		}

		dto.setSupplierRateContractTaxDetailsDTO(taxResponse);

		return dto;
	}

	@Override
	public List<Map<String, Object>> getCustomerForSupplierRateContract(Long orgId, Long branch) {

		Set<Object[]> result = customerRepo.getCustomerForSupplierRateContract(orgId, branch);

		return getCustomerForSupplierRateContractDetails(result);
	}

	private List<Map<String, Object>> getCustomerForSupplierRateContractDetails(
	        Set<Object[]> result) {

	    List<Map<String, Object>> details = new ArrayList<>();

	    for (Object[] fs : result) {

	        Map<String, Object> part = new HashMap<>();

	        part.put("customerId",
	                fs[0] != null ? ((Number) fs[0]).longValue() : null);

	        part.put("customerCode",
	                fs[1] != null ? fs[1].toString() : null);

	        part.put("customerName",
	                fs[2] != null ? fs[2].toString() : null);

	        part.put("address",
	                fs[3] != null ? fs[3].toString() : null);

	        part.put("gstState",
	                fs[4] != null ? fs[4].toString() : null);

	        part.put("gstNo",
	                fs[5] != null ? fs[5].toString() : null);

	        // is_gst_applicable is Boolean
	        part.put("igstApplicable",
	                fs[6] != null ? (Boolean) fs[6] : false);

	        part.put("gstType",
	                fs[7] != null ? fs[7].toString() : null);

	        part.put("gstStateId",
	                fs[8] != null ? ((Number) fs[8]).longValue() : null);
	        
	        details.add(part);
	    }

	    return details;
	}
	
	@Override
	public List<Map<String, Object>> getServiceForSupplierRateContract(
	        Long orgId, Long branch) {

	    Set<Object[]> result =
	            serviceAccMasterRepo.getServiceForSupplierRateContract(
	                    orgId, branch);

	    return getServiceForSupplierRateContractDetails(result);
	}
	
	private List<Map<String, Object>> getServiceForSupplierRateContractDetails(
	        Set<Object[]> result) {

	    List<Map<String, Object>> details = new ArrayList<>();

	    for (Object[] fs : result) {

	        Map<String, Object> part = new HashMap<>();

	        part.put("serviceId",
	                fs[0] != null
	                        ? ((Number) fs[0]).longValue()
	                        : null);

	        part.put("serviceName",
	                fs[1] != null
	                        ? fs[1].toString()
	                        : null);

	        part.put("serviceDescription",
	                fs[2] != null
	                        ? fs[2].toString()
	                        : null);

	        part.put("hsn",
	                fs[3] != null
	                        ? fs[3].toString()
	                        : null);

	        part.put("igstRate",
	                fs[4] != null
	                        ? new BigDecimal(fs[4].toString())
	                        : BigDecimal.ZERO);

	        part.put("cgstRate",
	                fs[5] != null
	                        ? new BigDecimal(fs[5].toString())
	                        : BigDecimal.ZERO);

	        part.put("sgstRate",
	                fs[6] != null
	                        ? new BigDecimal(fs[6].toString())
	                        : BigDecimal.ZERO);

	        part.put("rate",
	                fs[7] != null
	                        ? new BigDecimal(fs[7].toString())
	                        : BigDecimal.ZERO);
	        
	        part.put("hsnId",
	                fs[8] != null
	                        ? ((Number) fs[8]).longValue()
	                        : null);

	        details.add(part);
	    }

	    return details;
	}
	
	@Override
	public SupplierRateContractResponseDTO getSupplierRateContractById(
	        Long id) throws ApplicationException {

	    SupplierRateContractVO supplierRateContractVO =
	            supplierRateContractRepo.findById(id)
	                    .orElseThrow(() ->
	                            new ApplicationException(
	                                    "Supplier Rate Contract Not Found"));

	    return convertToResponse(supplierRateContractVO);
	}
	
	@Override
	public List<SupplierRateContractResponseDTO>
	getSupplierRateContractByOrgIdAndBranch(
	        Long orgId,
	        Long branch) throws ApplicationException {

	    List<SupplierRateContractVO> supplierRateContracts =
	            supplierRateContractRepo
	                    .findByOrgIdAndBranch(orgId, branch);

	    List<SupplierRateContractResponseDTO> responseList =
	            new ArrayList<>();

	    for (SupplierRateContractVO vo : supplierRateContracts) {

	        responseList.add(
	                convertToResponse(vo));
	    }

	    return responseList;
	}
	
	@Override
	public String getSupplierRateContractDocId(
	        Long orgId,
	        String financialYear) {

	    String screenCode1 = "SRC";

	    String result =
	            supplierRateContractRepo
	                    .getSupplierRateContractDocId(
	                            orgId,
	                            financialYear,
	                            screenCode1);

	    return result;
	}
	
	@Override
	public List<Map<String, Object>> getSupplierRateContractItemDropdown(
	        Long orgId, Long branch) {

	    List<Object[]> result =
	            itemMasterRepo.getSupplierRateContractItemDropdown(orgId, branch);

	    return getSupplierRateContractItemDropdownDetails(result);
	}

	private List<Map<String, Object>> getSupplierRateContractItemDropdownDetails(
	        List<Object[]> result) {

	    List<Map<String, Object>> details = new ArrayList<>();

	    for (Object[] fs : result) {

	        Map<String, Object> part = new HashMap<>();

	        part.put("item", fs[0] != null ? fs[0].toString() : null);
	        part.put("itemDesc", fs[1] != null ? fs[1].toString() : null);
	        part.put("itemId", fs[2] != null ? ((Number) fs[2]).longValue() : null);

	        part.put("unitmasterId",
	                fs[3] != null ? ((Number) fs[3]).longValue() : null);

	        part.put("unitId",
	                fs[4] != null ? fs[4].toString() : null);

	        part.put("unitDescription",
	                fs[5] != null ? fs[5].toString() : null);

	        details.add(part);
	    }

	    return details;
	}
	
	//JobOrder
	
	@Override
	@Transactional
	public Map<String, Object> createUpdateJobOrder(JobOrderDTO jobOrderDTO, MultipartFile[] files)
	        throws ApplicationException {

	    String screenCode = "JO";

	    JobOrderVO jobOrderVO;
	    String message;

	    if (ObjectUtils.isNotEmpty(jobOrderDTO.getId())) {

	        jobOrderVO = jobOrderRepo.findById(jobOrderDTO.getId())
	                .orElseThrow(() -> new ApplicationException("Job Order Not Found"));

	        jobOrderVO.setUpdatedBy(jobOrderDTO.getCreatedBy());

	        message = "Job Order Updated Successfully";
	        

	        
	    } else {

	        jobOrderVO = new JobOrderVO();

	        String docId = jobOrderRepo.getJobOrderDocId(jobOrderDTO.getOrgId(), jobOrderDTO.getFinancialYear(),
	                screenCode);

	        jobOrderVO.setDocId(docId);

	        DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
	                .findByOrgIdAndFinYearAndScreenCode(jobOrderDTO.getOrgId(), jobOrderDTO.getFinancialYear(),
	                        screenCode);
	        documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
	        documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

	        jobOrderVO.setCreatedBy(jobOrderDTO.getCreatedBy());
	        jobOrderVO.setUpdatedBy(jobOrderDTO.getCreatedBy());

	        message = "Job Order Created Successfully";
	    }

	    // Header + Child Mapping
	    createUpdateJobOrderVOByJobOrderDTO(jobOrderDTO, jobOrderVO);

	    // Save Header
	    jobOrderVO = jobOrderRepo.save(jobOrderVO);

	    // Save Attachments
	    try {
			saveJobOrderAttachments(files, jobOrderVO);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.io.IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    // Response
	    JobOrderResponseDTO responseDTO = buildJobOrderResponse(jobOrderVO);

	    Map<String, Object> response = new HashMap<>();
	    response.put("message", message);
	    response.put("jobOrderVO", responseDTO);

	    return response;
	}

	private void createUpdateJobOrderVOByJobOrderDTO(JobOrderDTO jobOrderDTO, JobOrderVO jobOrderVO)
	        throws ApplicationException {

	    // Branch
	    if (jobOrderDTO.getBranch() != null && jobOrderDTO.getBranch() > 0) {
	        BranchVO branch = branchRepo.findById(jobOrderDTO.getBranch())
	                .orElseThrow(() -> new ApplicationException("Branch Not Found"));
	        jobOrderVO.setBranch(branch);
	    }

	    // Department
	    if (jobOrderDTO.getDepartment() != null && jobOrderDTO.getDepartment() > 0) {
	        DepartmentVO department = departmentRepo.findById(jobOrderDTO.getDepartment())
	                .orElseThrow(() -> new ApplicationException("Department Not Found"));
	        jobOrderVO.setDepartment(department);
	    }

	    jobOrderVO.setBelongsTo(jobOrderDTO.getBelongsTo());

	    // Vendor
	    if (jobOrderDTO.getVendor() != null && jobOrderDTO.getVendor() > 0) {
	        CustomerVO vendor = customerRepo.findById(jobOrderDTO.getVendor())
	                .orElseThrow(() -> new ApplicationException("Vendor Not Found"));
	        jobOrderVO.setVendor(vendor);
	    }

	    // GST State
	    if (jobOrderDTO.getGstState() != null && jobOrderDTO.getGstState() > 0) {
	        GSTStateMasterVO gstState = gstStateMasterRepo.findById(jobOrderDTO.getGstState())
	                .orElseThrow(() -> new ApplicationException("GST State Not Found"));
	        jobOrderVO.setGstState(gstState);
	    }

	    jobOrderVO.setJobOrderFor(jobOrderDTO.getJobOrderFor());
	    jobOrderVO.setIgstAppl(jobOrderDTO.isIgstAppl());
	    jobOrderVO.setContractNo(jobOrderDTO.getContractNo());

	    // Service Name
	    if (jobOrderDTO.getServiceName() != null && jobOrderDTO.getServiceName() > 0) {
	        ServiceAccMasterVO serviceName = serviceAccMasterRepo.findById(jobOrderDTO.getServiceName())
	                .orElseThrow(() -> new ApplicationException("Service Not Found"));
	        jobOrderVO.setServiceName(serviceName);
	    }

	    jobOrderVO.setIndentTime(jobOrderDTO.getIndentTime());

	 // HSN/SAC
	    if (jobOrderDTO.getHsnSacCode() != null && jobOrderDTO.getHsnSacCode() > 0) {
	        HsnVO hsn = hsnRepo.findById(jobOrderDTO.getHsnSacCode())
	                .orElseThrow(() -> new ApplicationException("HSN/SAC Not Found"));
	        jobOrderVO.setHsnSacCode(hsn);
	    }
	    
	    jobOrderVO.setTaxType(jobOrderDTO.getTaxType());
	    jobOrderVO.setTaxPercentage(jobOrderDTO.getTaxPercentage());

	    jobOrderVO.setPaymentTerms(jobOrderDTO.getPaymentTerms());
	    jobOrderVO.setDeliveryDate(jobOrderDTO.getDeliveryDate());

	    jobOrderVO.setNarration(jobOrderDTO.getNarration());
	    jobOrderVO.setNote(jobOrderDTO.getNote());

	    jobOrderVO.setOrgId(jobOrderDTO.getOrgId());
	    jobOrderVO.setFinancialYear(jobOrderDTO.getFinancialYear());
	    jobOrderVO.setActive(jobOrderDTO.isActive());
	    jobOrderVO.setCancelRemarks(jobOrderDTO.getCancelRemarks());

	    // Clear existing children on update
	    if (ObjectUtils.isNotEmpty(jobOrderVO.getId())) {

	        List<JobOrderDetailsVO> existingDetails = jobOrderDetailsRepo.findByJobOrder(jobOrderVO);
	        jobOrderDetailsRepo.deleteAll(existingDetails);

	        List<JobOrderTaxDetailsVO> existingTaxDetails = jobOrderTaxDetailsRepo.findByJobOrder(jobOrderVO);
	        jobOrderTaxDetailsRepo.deleteAll(existingTaxDetails);
	    }

	    BigDecimal totalAmount = BigDecimal.ZERO;

	    // Job Order Details (child items)
	    List<JobOrderDetailsVO> detailsList = new ArrayList<>();

	    if (jobOrderDTO.getJobOrderDetails() != null) {

	        for (JobOrderDetailsDTO dto : jobOrderDTO.getJobOrderDetails()) {

	            JobOrderDetailsVO detailVO = new JobOrderDetailsVO();

	            if (dto.getIncomingItem() != null && dto.getIncomingItem() != 0) {
	                ItemMasterVO item = itemMasterRepo.findById(dto.getIncomingItem())
	                        .orElseThrow(() -> new ApplicationException("Item Not Found"));
	                detailVO.setIncomingItem(item);
	            }

//	            if (dto.getBom() != null && dto.getBom() != 0) {
//	                BomVO bom = bomRepo.findById(dto.getBom())
//	                        .orElseThrow(() -> new ApplicationException("BOM Not Found"));
//	                detailVO.setBom(bom);
//	            }

	            if (dto.getUnit() != null && dto.getUnit() != 0) {
	                UnitMasterVO unit = unitMasterRepo.findById(dto.getUnit())
	                        .orElseThrow(() -> new ApplicationException("Unit Not Found"));
	                detailVO.setUnit(unit);
	            }

	            detailVO.setIncomingType(dto.getIncomingType());
	           
	            BigDecimal amount = BigDecimal.ZERO;

	            if (dto.getOrderQty() != null && dto.getRate() != null) {
	                amount = dto.getOrderQty().multiply(dto.getRate());
	            }

	            detailVO.setBom(dto.getBom());
	            detailVO.setOrderQty(dto.getOrderQty());
	            detailVO.setRate(dto.getRate());
	            detailVO.setAmount(amount);


	            // =========================
	            // TAX CALCULATION
	            // =========================

	            if (jobOrderVO.isIgstAppl()) {

	                // IGST
	                BigDecimal igstAmount = amount
	                        .multiply(dto.getIgstRate() != null ? dto.getIgstRate() : BigDecimal.ZERO)
	                        .divide(BigDecimal.valueOf(100));

	                detailVO.setIgstRate(
	                        dto.getIgstRate() != null ? dto.getIgstRate() : BigDecimal.ZERO
	                );

	                detailVO.setCgstRate(BigDecimal.ZERO);
	                detailVO.setSgstRate(BigDecimal.ZERO);

	                detailVO.setIgstAmount(igstAmount);
	                detailVO.setCgstAmount(BigDecimal.ZERO);
	                detailVO.setSgstAmount(BigDecimal.ZERO);

	            } else {

	                // CGST
	                BigDecimal cgstAmount = amount
	                        .multiply(dto.getCgstRate() != null ? dto.getCgstRate() : BigDecimal.ZERO)
	                        .divide(BigDecimal.valueOf(100));

	                // SGST
	                BigDecimal sgstAmount = amount
	                        .multiply(dto.getSgstRate() != null ? dto.getSgstRate() : BigDecimal.ZERO)
	                        .divide(BigDecimal.valueOf(100));

	                detailVO.setCgstRate(
	                        dto.getCgstRate() != null ? dto.getCgstRate() : BigDecimal.ZERO
	                );

	                detailVO.setSgstRate(
	                        dto.getSgstRate() != null ? dto.getSgstRate() : BigDecimal.ZERO
	                );

	                detailVO.setIgstRate(BigDecimal.ZERO);

	                detailVO.setCgstAmount(cgstAmount);
	                detailVO.setSgstAmount(sgstAmount);
	                detailVO.setIgstAmount(BigDecimal.ZERO);
	            }
	            detailVO.setSentFor(dto.getSentFor());

	            detailVO.setJobOrder(jobOrderVO);

	            if (dto.getAmount() != null) {
	                totalAmount = totalAmount.add(dto.getAmount());
	            }

	            detailsList.add(detailVO);
	        }
	    }

	    jobOrderVO.setJobOrderDetails(detailsList);

	    // Job Order Tax Details
	    List<JobOrderTaxDetailsVO> taxDetailsList = new ArrayList<>();

	    if (jobOrderDTO.getJobOrderTaxDetails() != null) {

	        for (JobOrderTaxDetailsDTO dto : jobOrderDTO.getJobOrderTaxDetails()) {

	            JobOrderTaxDetailsVO taxVO = new JobOrderTaxDetailsVO();

	            taxVO.setParticulars(dto.getParticulars());
	            taxVO.setAmount(dto.getAmount());
	            taxVO.setJobOrder(jobOrderVO);

	            taxDetailsList.add(taxVO);
	        }
	    }

	    jobOrderVO.setJobOrderTaxDetails(taxDetailsList);

	    jobOrderVO.setAmount(totalAmount);
	}


	private void saveJobOrderAttachments(MultipartFile[] files, JobOrderVO jobOrderVO) throws ApplicationException, java.io.IOException {

	    // Delete old attachments (DB rows + physical files) on update, same as jobOrderDetails/taxDetails
	    List<JobOrderAttachmentVO> existingAttachments = jobOrderAttachmentRepo.findByJobOrderVO(jobOrderVO);

	    if (existingAttachments != null && !existingAttachments.isEmpty()) {

	        for (JobOrderAttachmentVO oldAttachment : existingAttachments) {

	            try {
	                Path oldPath = Paths.get(oldAttachment.getFilePath());
	                Files.deleteIfExists(oldPath);
	            } catch (IOException e) {
	                // log and continue — don't block the update if a stray file is already missing
	                System.err.println("Could not delete old file: " + oldAttachment.getFilePath() + " - " + e.getMessage());
	            }
	        }

	        jobOrderAttachmentRepo.deleteAll(existingAttachments);
	    }

	    // If no new files were sent, we're done — old ones are already cleared above
	    if (files == null || files.length == 0) {
	        jobOrderVO.setAttachments(new ArrayList<>());
	        return;
	    }

	    try {

	        File folder = new File(joborderUploadPath);

	        if (!folder.exists()) {
	            folder.mkdirs();
	        }

	        List<JobOrderAttachmentVO> attachmentList = new ArrayList<>();

	        for (MultipartFile file : files) {

	            if (file == null || file.isEmpty()) {
	                continue;
	            }

	            String originalFileName = file.getOriginalFilename();
	            String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;

	            Path path = Paths.get(joborderUploadPath, uniqueFileName);

	            try (InputStream inputStream = file.getInputStream()) {
	                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
	            }

	            JobOrderAttachmentVO attachment = JobOrderAttachmentVO.builder()
	                    .jobOrderVO(jobOrderVO)
	                    .name(originalFileName)
	                    .fileName(uniqueFileName)
	                    .filePath(path.toString())
	                    .fileSize(file.getSize())
	                    .contentType(file.getContentType())
	                    .uploadOn(LocalDateTime.now())
	                    .build();

	            attachmentList.add(attachment);
	        }

	        List<JobOrderAttachmentVO> savedAttachments = jobOrderAttachmentRepo.saveAll(attachmentList);

	        jobOrderVO.setAttachments(savedAttachments);

	    } catch (IOException e) {
	        throw new ApplicationException("File Upload Failed : " + e.getMessage());
	    }
	}

	private JobOrderResponseDTO buildJobOrderResponse(JobOrderVO jobOrderVO) {

	    JobOrderResponseDTO responseDTO = new JobOrderResponseDTO();

	    responseDTO.setId(jobOrderVO.getId());
	    responseDTO.setDocId(jobOrderVO.getDocId());
	    responseDTO.setDocDate(jobOrderVO.getDocDate());
	    responseDTO.setBelongsTo(jobOrderVO.getBelongsTo());
	    responseDTO.setJobOrderFor(jobOrderVO.getJobOrderFor());
	    responseDTO.setIgstAppl(jobOrderVO.isIgstAppl());
	    responseDTO.setContractNo(jobOrderVO.getContractNo());
	    responseDTO.setIndentTime(jobOrderVO.getIndentTime());
	    responseDTO.setTaxType(jobOrderVO.getTaxType());
	    responseDTO.setTaxPercentage(jobOrderVO.getTaxPercentage());
	    responseDTO.setPaymentTerms(jobOrderVO.getPaymentTerms());
	    responseDTO.setDeliveryDate(jobOrderVO.getDeliveryDate());
	    responseDTO.setAmount(jobOrderVO.getAmount());
	    responseDTO.setNarration(jobOrderVO.getNarration());
	    responseDTO.setNote(jobOrderVO.getNote());
	    responseDTO.setOrgId(jobOrderVO.getOrgId());
	    responseDTO.setFinancialYear(jobOrderVO.getFinancialYear());
	    responseDTO.setCreatedBy(jobOrderVO.getCreatedBy());
	    responseDTO.setUpdatedBy(jobOrderVO.getUpdatedBy());
	    responseDTO.setActive(jobOrderVO.getActive());
	    responseDTO.setCancel(jobOrderVO.getCancel());
	    responseDTO.setCancelRemarks(jobOrderVO.getCancelRemarks());
	    responseDTO.setScreenCode(jobOrderVO.getScreenCode());
	    responseDTO.setScreenName(jobOrderVO.getScreenName());
	    responseDTO.setJobOrderFor(jobOrderVO.getJobOrderFor());

	    if (jobOrderVO.getBranch() != null) {
	        BranchResponseDTO branchDTO = new BranchResponseDTO();
	        branchDTO.setId(jobOrderVO.getBranch().getId());
	        branchDTO.setBranchCode(jobOrderVO.getBranch().getBranchCode());
	        branchDTO.setBranchName(jobOrderVO.getBranch().getBranchName());
	        responseDTO.setBranch(branchDTO);
	    }

	    if (jobOrderVO.getVendor() != null) {

	        CustomerDropdownResponseDTO vendorDTO = new CustomerDropdownResponseDTO();

	        vendorDTO.setCustomerId(jobOrderVO.getVendor().getId());
	        vendorDTO.setCustomerCode(jobOrderVO.getVendor().getCustomerCode());
	        vendorDTO.setCustomerName(jobOrderVO.getVendor().getCustomerName());
	        vendorDTO.setAddress(jobOrderVO.getVendor().getAddress());
//	        vendorDTO.setGstState(jobOrderVO.getVendor().getGstState().);
	        vendorDTO.setGstNo(jobOrderVO.getVendor().getGstNo());
	        vendorDTO.setIgstApplicable(jobOrderVO.getVendor().isGstApplicable());
	        vendorDTO.setGstType(jobOrderVO.getVendor().getGstType());

	        responseDTO.setVendor(vendorDTO);
	    }
	    
	    if (jobOrderVO.getHsnSacCode() != null) {
	        HsnResponseDTO hsnDTO = new HsnResponseDTO();
	        hsnDTO.setId(jobOrderVO.getHsnSacCode().getId());
	        hsnDTO.setHsn(jobOrderVO.getHsnSacCode().getHsn());   // adjust to actual HsnVO field names
	        hsnDTO.setDescription(jobOrderVO.getHsnSacCode().getDescription());
	        responseDTO.setHsnSacCode(hsnDTO);
	    }
	    
	    if (jobOrderVO.getDepartment() != null) {

	        DepartmentResponseDTO departmentDTO = new DepartmentResponseDTO();

	        departmentDTO.setId(jobOrderVO.getDepartment().getId());
	        departmentDTO.setDepartmentCode(jobOrderVO.getDepartment().getDepartmentCode());
	        departmentDTO.setDepartmentName(jobOrderVO.getDepartment().getDepartmentName());

	        responseDTO.setDepartment(departmentDTO);
	    }
	    
	    if (jobOrderVO.getServiceName() != null) {

	        ServiceAccMasterResponse1DTO serviceDTO = new ServiceAccMasterResponse1DTO();

	        serviceDTO.setId(jobOrderVO.getServiceName().getId());
	        serviceDTO.setServiceDescription(jobOrderVO.getServiceName().getServiceDescription());
	        serviceDTO.setServiceName(jobOrderVO.getServiceName().getServiceName());

	        responseDTO.setServiceName(serviceDTO);
	    }
	    
	    if (jobOrderVO.getGstState() != null) {

	        GSTStateMasterResponseDTO gstStateDTO = new GSTStateMasterResponseDTO();

	        gstStateDTO.setId(jobOrderVO.getGstState().getId());
	        gstStateDTO.setGstState(jobOrderVO.getGstState().getStateName());
	        gstStateDTO.setGstStateCode(jobOrderVO.getGstState().getStateCode());

	        responseDTO.setGstState(gstStateDTO);
	    }
	    
	    

	    // Details
	    List<JobOrderDetailsResponseDTO> detailsList = new ArrayList<>();

	    if (jobOrderVO.getJobOrderDetails() != null) {

	        for (JobOrderDetailsVO detailVO : jobOrderVO.getJobOrderDetails()) {

	            JobOrderDetailsResponseDTO detailDTO = new JobOrderDetailsResponseDTO();

	            detailDTO.setId(detailVO.getId());
	            detailDTO.setIncomingType(detailVO.getIncomingType());
	            detailDTO.setOrderQty(detailVO.getOrderQty());
	            detailDTO.setRate(detailVO.getRate());
	            detailDTO.setAmount(detailVO.getAmount());
	            detailDTO.setSgstRate(detailVO.getSgstRate());
	            detailDTO.setSgstAmount(detailVO.getSgstAmount());
	            detailDTO.setCgstRate(detailVO.getCgstRate());
	            detailDTO.setCgstAmount(detailVO.getCgstAmount());
	            detailDTO.setIgstRate(detailVO.getIgstRate());
	            detailDTO.setIgstAmount(detailVO.getIgstAmount());
	            detailDTO.setSentFor(detailVO.getSentFor());
	            detailDTO.setBom(detailVO.getBom());

	            if (detailVO.getIncomingItem() != null) {
	                ItemMasterResponseDetailsDTO itemDTO = new ItemMasterResponseDetailsDTO();
	                itemDTO.setId(detailVO.getIncomingItem().getId());
	                itemDTO.setItemCode(detailVO.getIncomingItem().getItemCode());
	                itemDTO.setItemDescription(detailVO.getIncomingItem().getItemDescription());
	                detailDTO.setIncomingItem(itemDTO);
	            }
//	            if (detailVO.getBom() != null) {
//	                BomResponseDTO bomDTO = new BomResponseDTO();
//	                bomDTO.setId(detailVO.getBom().getId());
//	                bomDTO.setProductType(detailVO.getBom().getProductType());
//	                bomDTO.setProductCode(detailVO.getBom().getProductCode());
//	                bomDTO.setProductName(detailVO.getBom().getProductName());
//	                bomDTO.setUom(detailVO.getBom().getUom());          // adjust getter name if BomVO stores it differently
//	                bomDTO.setQty(detailVO.getBom().getQty());
//	                detailDTO.setBom(bomDTO);
//	            }

	            if (detailVO.getUnit() != null) {
	                UnitMasterResponseDTO unitDTO = new UnitMasterResponseDTO();
	                unitDTO.setId(detailVO.getUnit().getId());
	                unitDTO.setUnitId(detailVO.getUnit().getUnitId());
	                unitDTO.setUnitDescription(detailVO.getUnit().getDescription());
	                detailDTO.setUnit(unitDTO);
	            }

	            detailsList.add(detailDTO);
	        }
	    }

	    responseDTO.setJobOrderDetails(detailsList);

	    // Tax Details
	    List<JobOrderTaxDetailsResponseDTO> taxDetailsList = new ArrayList<>();

	    if (jobOrderVO.getJobOrderTaxDetails() != null) {

	        for (JobOrderTaxDetailsVO taxVO : jobOrderVO.getJobOrderTaxDetails()) {

	            JobOrderTaxDetailsResponseDTO taxDTO = new JobOrderTaxDetailsResponseDTO();
	            taxDTO.setId(taxVO.getId());
	            taxDTO.setParticulars(taxVO.getParticulars());
	            taxDTO.setAmount(taxVO.getAmount());

	            taxDetailsList.add(taxDTO);
	        }
	    }

	    responseDTO.setJobOrderTaxDetails(taxDetailsList);

	    // Attachments
	    List<JobOrderAttachmentResponseDTO> attachmentList = new ArrayList<>();

	    if (jobOrderVO.getAttachments() != null) {

	        for (JobOrderAttachmentVO fileVO : jobOrderVO.getAttachments()) {

	            JobOrderAttachmentResponseDTO fileDTO = new JobOrderAttachmentResponseDTO();
	            fileDTO.setId(fileVO.getId());
	            fileDTO.setName(fileVO.getName());
	            fileDTO.setFileName(fileVO.getFileName());
	            fileDTO.setFileSize(fileVO.getFileSize());
	            fileDTO.setContentType(fileVO.getContentType());
	            fileDTO.setUploadOn(fileVO.getUploadOn());

	            String urlPath = joborderUploadPath.replace("C:/", "/").replace("\\", "/");
	            fileDTO.setFilePath(serverBaseUrl + urlPath + fileVO.getFileName());

	            attachmentList.add(fileDTO);
	        }
	    }

	    responseDTO.setAttachments(attachmentList);

	    return responseDTO;
	}
	
	@Override
	public List<Map<String, Object>> getSupplierRateContractDropdown(
	        Long customer, Long orgId, Long branch) {

	    Set<Object[]> result =
	            supplierRateContractRepo.getSupplierRateContractDropdown(
	                    customer, orgId, branch);

	    return getSupplierRateContractDropdownDetails(result);
	}

	private List<Map<String, Object>> getSupplierRateContractDropdownDetails(
	        Set<Object[]> result) {

	    List<Map<String, Object>> details = new ArrayList<>();

	    for (Object[] fs : result) {

	        Map<String, Object> part = new HashMap<>();

	        part.put("jobOrderFor",
	                fs[2] != null ? fs[2].toString() : null);

	        part.put("docId",
	                fs[0] != null ? fs[0].toString() : null);

	        part.put("docDate",
	                fs[1] != null ? fs[1] : null);

	        part.put("taxPercentage",
	                fs[8] != null
	                        ? new BigDecimal(fs[8].toString())
	                        : BigDecimal.ZERO);

	        part.put("igstRate",
	                fs[9] != null
	                        ? new BigDecimal(fs[9].toString())
	                        : BigDecimal.ZERO);

	        part.put("cgstRate",
	                fs[10] != null
	                        ? new BigDecimal(fs[10].toString())
	                        : BigDecimal.ZERO);

	        part.put("sgstRate",
	                fs[11] != null
	                        ? new BigDecimal(fs[11].toString())
	                        : BigDecimal.ZERO);

	        // Service
	        Map<String, Object> service = new HashMap<>();

	        service.put("id",
	                fs[3] != null
	                        ? Long.valueOf(fs[3].toString())
	                        : null);

	        service.put("name",
	                fs[4] != null
	                        ? fs[4].toString()
	                        : null);

	        part.put("serviceName", service);

	        // HSN
	        Map<String, Object> hsn = new HashMap<>();

	        hsn.put("id",
	                fs[5] != null
	                        ? Long.valueOf(fs[5].toString())
	                        : null);

	        hsn.put("code",
	                fs[6] != null
	                        ? fs[6].toString()
	                        : null);

	        hsn.put("description",
	                fs[7] != null
	                        ? fs[7].toString()
	                        : null);

	        part.put("hsnSacCode", hsn);

	        details.add(part);
	    }

	    return details;
	}
	
	@Override
	public List<Map<String, Object>> getSupplierRateContractItemDetailsForJobOrder(
			String docId, Long orgId, Long branch) {

		Set<Object[]> result = supplierRateContractRepo
				.getSupplierRateContractItemDetails(docId, orgId, branch);

		return getSupplierRateContractItemDetails(result);
	}

	private List<Map<String, Object>> getSupplierRateContractItemDetails(Set<Object[]> result) {

		List<Map<String, Object>> details1 = new ArrayList<>();

		for (Object[] fs : result) {

			Map<String, Object> part = new HashMap<>();

			part.put("id", fs[0] != null ? Long.valueOf(fs[0].toString()) : null);
			part.put("incomingItemId", fs[1] != null ? Long.valueOf(fs[1].toString()) : null);
			part.put("itemCode", fs[2] != null ? fs[2].toString() : null);
			part.put("itemDescription", fs[3] != null ? fs[3].toString() : null);
			part.put("unitId", fs[4] != null ? Long.valueOf(fs[4].toString()) : null);
			part.put("unit", fs[5] != null ? fs[5].toString() : null);
			part.put("unitDescription", fs[6] != null ? fs[6].toString() : null);
			part.put("rate", fs[7] != null ? new BigDecimal(fs[7].toString()) : BigDecimal.ZERO);

			details1.add(part);
		}

		return details1;
	}
	
	@Override
	public JobOrderResponseDTO getJobOrderById(
	        Long id) throws ApplicationException {

	    JobOrderVO jobOrderVO =
	            jobOrderRepo.findById(id)
	                    .orElseThrow(() ->
	                            new ApplicationException(
	                                    "Job Order Not Found"));

	    return buildJobOrderResponse(jobOrderVO);
	}
	
	
	@Override
	public List<JobOrderResponseDTO> getJobOrderByOrgIdAndBranch(
	        Long orgId,
	        Long branch) throws ApplicationException {

	    List<JobOrderVO> jobOrders =
	            jobOrderRepo.findByOrgIdAndBranch(
	                    orgId,
	                    branch);

	    List<JobOrderResponseDTO> responseList =
	            new ArrayList<>();

	    for (JobOrderVO vo : jobOrders) {

	        responseList.add(
	        		buildJobOrderResponse(vo));
	    }

	    return responseList;
	}
	
	@Override
	public String getJobOrderDocId(
	        Long orgId,
	        String financialYear) {

	    String screenCode1 = "JO";

	    String result =
	            jobOrderRepo.getJobOrderDocId(
	                    orgId,
	                    financialYear,
	                    screenCode1);

	    return result;
	}
	
	
	@Override
	@Transactional
	public Map<String, Object> createUpdateJobOrderAmendment(
	        JobOrderAmendmentDTO jobOrderAmendmentDTO) throws ApplicationException {

	    String screenCode = "JOA";

	    Map<String, Object> response = new HashMap<>();

	    String message;

	    JobOrderAmendmentVO jobOrderAmendmentVO;

	    if (ObjectUtils.isEmpty(jobOrderAmendmentDTO.getId())) {

	        jobOrderAmendmentVO = new JobOrderAmendmentVO();

	        String docId = jobOrderAmendmentRepo.getJobOrderAmendmentDocId(
	                jobOrderAmendmentDTO.getOrgId(),
	                jobOrderAmendmentDTO.getFinancialYear(),
	                screenCode);

	        jobOrderAmendmentVO.setDocId(docId);

	        DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO =
	                documentTypeMappingDetailsRepo
	                        .findByOrgIdAndFinYearAndScreenCode(
	                                jobOrderAmendmentDTO.getOrgId(),
	                                jobOrderAmendmentDTO.getFinancialYear(),
	                                screenCode);

	        documentTypeMappingDetailsVO.setLastNo(
	                documentTypeMappingDetailsVO.getLastNo() + 1);

	        documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

	        jobOrderAmendmentVO.setCreatedBy(
	                jobOrderAmendmentDTO.getCreatedBy());

	        jobOrderAmendmentVO.setUpdatedBy(
	                jobOrderAmendmentDTO.getCreatedBy());

	        message = "Job Order Amendment Created Successfully";

	    } else {

	        jobOrderAmendmentVO = jobOrderAmendmentRepo
	                .findById(jobOrderAmendmentDTO.getId())
	                .orElseThrow(() ->
	                        new ApplicationException(
	                                "Job Order Amendment Not Found"));

	        jobOrderAmendmentVO.setUpdatedBy(
	                jobOrderAmendmentDTO.getCreatedBy());

	       

	        message = "Job Order Amendment Updated Successfully";
	    }

	    createUpdateJobOrderAmendmentVOByDTO(
	            jobOrderAmendmentDTO,
	            jobOrderAmendmentVO);

	    // Cascade saves everything
	    jobOrderAmendmentVO =
	            jobOrderAmendmentRepo.save(jobOrderAmendmentVO);

	    JobOrderAmendmentResponseDTO responseDTO =
	            buildJobOrderAmendmentResponse(jobOrderAmendmentVO);

	    response.put("message", message);
	    response.put("jobOrderAmendment", responseDTO);

	    return response;
	}
	
	private void createUpdateJobOrderAmendmentVOByDTO(
	        JobOrderAmendmentDTO dto,
	        JobOrderAmendmentVO jobOrderAmendmentVO) throws ApplicationException {

	    jobOrderAmendmentVO.setJobOrderNo(dto.getJobOrderNo());

	    jobOrderAmendmentVO.setJobOrderDate(dto.getJobOrderDate());

	    jobOrderAmendmentVO.setRevisionNo(dto.getRevisionNo());

	    jobOrderAmendmentVO.setOldDeliveryDate(dto.getOldDeliveryDate());

	    jobOrderAmendmentVO.setNewDeliveryDate(dto.getNewDeliveryDate());

	    jobOrderAmendmentVO.setRemarks(dto.getRemarks());

	    jobOrderAmendmentVO.setOrgId(dto.getOrgId());

	    jobOrderAmendmentVO.setFinancialYear(dto.getFinancialYear());

	    jobOrderAmendmentVO.setActive(dto.isActive());

	    jobOrderAmendmentVO.setCancelRemarks(dto.getCancelRemarks());

	    if (dto.getBranch() != null && dto.getBranch() != 0) {

	        BranchVO branch = branchRepo.findById(dto.getBranch())
	                .orElseThrow(() ->
	                        new ApplicationException("Branch Not Found"));

	        jobOrderAmendmentVO.setBranch(branch);
	    }

	    // ----------------------------------------------------
	    // Party / Customer
	    // ----------------------------------------------------

	    if (dto.getCustomer() != null && dto.getCustomer() != 0) {

	        CustomerVO customer = customerRepo.findById(dto.getCustomer())
	                .orElseThrow(() ->
	                        new ApplicationException("Customer Not Found"));

	        jobOrderAmendmentVO.setCustomer(customer);
	    }

	    // ----------------------------------------------------
	    // Delete old child records while updating
	    // ----------------------------------------------------

	    if (dto.getId() != null) {

	        List<JobOrderAmendmentDetailsVO> oldList =
	                jobOrderAmendmentDetailsRepo
	                        .findByJobOrderAmendment(
	                                jobOrderAmendmentVO);

	        jobOrderAmendmentDetailsRepo.deleteAll(oldList);
	    }

	    // ----------------------------------------------------
	    // Child Details
	    // ----------------------------------------------------

	    List<JobOrderAmendmentDetailsVO> detailList =
	            new ArrayList<>();

	    if (dto.getJobOrderAmendmentDetails() != null
	            && !dto.getJobOrderAmendmentDetails().isEmpty()) {

	        for (JobOrderAmendmentDetailsDTO detailDTO :
	                dto.getJobOrderAmendmentDetails()) {

	            JobOrderAmendmentDetailsVO detailVO =
	                    new JobOrderAmendmentDetailsVO();

	            // Item

	            if (detailDTO.getItem() != null
	                    && detailDTO.getItem() != 0) {

	                ItemMasterVO item =
	                        itemMasterRepo.findById(detailDTO.getItem())
	                                .orElseThrow(() ->
	                                        new ApplicationException(
	                                                "Item Not Found"));

	                detailVO.setItem(item);
	            }

	            // Unit

	            if (detailDTO.getUnit() != null
	                    && detailDTO.getUnit() != 0) {

	                UnitMasterVO unit =
	                        unitMasterRepo.findById(detailDTO.getUnit())
	                                .orElseThrow(() ->
	                                        new ApplicationException(
	                                                "Unit Not Found"));

	                detailVO.setUnit(unit);
	            }

	            detailVO.setOldQty(detailDTO.getOldQty());

	            detailVO.setNewQty(detailDTO.getNewQty());

	            // Parent Mapping

	            detailVO.setJobOrderAmendment(
	                    jobOrderAmendmentVO);

	            detailList.add(detailVO);
	        }

	        // Set child list to parent

	        jobOrderAmendmentVO.setJobOrderAmendmentDetails(
	                detailList);
	    }
	}
	
	private JobOrderAmendmentResponseDTO buildJobOrderAmendmentResponse(
	        JobOrderAmendmentVO vo) {

	    JobOrderAmendmentResponseDTO response =
	            new JobOrderAmendmentResponseDTO();

	    // ================= Header =================

	    response.setId(vo.getId());

	    response.setDocId(vo.getDocId());

	    response.setDocDate(vo.getDocDate());

	    response.setJobOrderNo(vo.getJobOrderNo());

	    response.setJobOrderDate(vo.getJobOrderDate());

	    response.setRevisionNo(vo.getRevisionNo());

	    response.setOldDeliveryDate(vo.getOldDeliveryDate());

	    response.setNewDeliveryDate(vo.getNewDeliveryDate());

	    response.setRemarks(vo.getRemarks());

	    response.setOrgId(vo.getOrgId());

	    response.setFinancialYear(vo.getFinancialYear());

	    response.setCreatedBy(vo.getCreatedBy());

	    response.setUpdatedBy(vo.getUpdatedBy());

	    response.setCancelRemarks(vo.getCancelRemarks());

	    response.setActive(vo.getActive());

	    response.setCancel(vo.getCancel());

	    response.setScreenCode(vo.getScreenCode());

	    response.setScreenName(vo.getScreenName());

	    // ================= Branch =================

	    if (vo.getBranch() != null) {

	        BranchResponseDTO branch = new BranchResponseDTO();

	        branch.setId(vo.getBranch().getId());

	        branch.setBranchCode(
	                vo.getBranch().getBranchCode());

	        branch.setBranchName(
	                vo.getBranch().getBranchName());

	        response.setBranch(branch);
	    }

	    // ================= Customer =================

	    if (vo.getCustomer() != null) {

	        CustomerDropdownResponseDTO customer =
	                new CustomerDropdownResponseDTO();

	        customer.setCustomerId(
	                vo.getCustomer().getId());

	        customer.setCustomerCode(
	                vo.getCustomer().getCustomerCode());

	        customer.setCustomerName(
	                vo.getCustomer().getCustomerName());

	        response.setCustomer(customer);
	    }

	    // ================= Details =================

	    List<JobOrderAmendmentDetailsResponseDTO> detailsResponse =
	            new ArrayList<>();

	    if (vo.getJobOrderAmendmentDetails() != null) {

	        for (JobOrderAmendmentDetailsVO detailVO :
	                vo.getJobOrderAmendmentDetails()) {

	            JobOrderAmendmentDetailsResponseDTO detailResponse =
	                    new JobOrderAmendmentDetailsResponseDTO();

	            detailResponse.setId(detailVO.getId());

	            detailResponse.setOldQty(
	                    detailVO.getOldQty());

	            detailResponse.setNewQty(
	                    detailVO.getNewQty());

	            // ================= Item =================

	            if (detailVO.getItem() != null) {

	                ItemResponseDTO item = new ItemResponseDTO();

	                item.setId(detailVO.getItem().getId());

	                item.setItemCode(
	                        detailVO.getItem().getItemCode());

	                item.setItemDescription(
	                        detailVO.getItem().getItemDescription());

	                detailResponse.setItem(item);
	            }

	            // ================= Unit =================

	            if (detailVO.getUnit() != null) {

	                UnitResponseDTO unit = new UnitResponseDTO();

	                unit.setId(detailVO.getUnit().getId());

	                unit.setUnitId(
	                        detailVO.getUnit().getDescription());

	                detailResponse.setUnit(unit);
	            }

	            detailsResponse.add(detailResponse);
	        }
	    }

	    response.setJobOrderAmendmentDetails(detailsResponse);

	    return response;
	}
	
	@Override
	public List<Map<String, Object>> getJobOrderNoAndDateForJobOrderAmd(
	        Long branch, Long orgId, Long customer) {

	    Set<Object[]> result =
	            jobOrderRepo.getJobOrderNoAndDateForJobOrderAmd(branch, orgId, customer);

	    return getJobOrderNoAndDate(result);
	}

	private List<Map<String, Object>> getJobOrderNoAndDate(
	        Set<Object[]> result) {

	    List<Map<String, Object>> details = new ArrayList<>();

	    for (Object[] fs : result) {

	        Map<String, Object> part = new HashMap<>();

	        part.put("id", fs[0] != null ? fs[0] : null);
	        part.put("jobOrderNo", fs[1] != null ? fs[1] : null);
	        part.put("jobOrderDate", fs[2] != null ? fs[2] : null);

	        details.add(part);
	    }

	    return details;
	}
	
	@Override
	public Integer getNextRevisionNoForJobOrderAmd(
	        String jobOrderNo,
	        Long branch,
	        Long orgId) {

	    Integer revisionNo =
	            jobOrderAmendmentRepo.getNextRevisionNoForJobOrderAmd(
	                    jobOrderNo, branch, orgId);

	    return revisionNo != null ? revisionNo : 1;
	}
	
	@Override
	public List<Map<String, Object>> getJobOrderItemDetailsForJobOrderAmd(
	        String jobOrderNo, Long branch, Long orgId, Long customer) {

	    Set<Object[]> result =
	            jobOrderRepo.getJobOrderItemDetailsForJobOrderAmd(
	                    jobOrderNo, branch, orgId, customer);

	    return getJobOrderItemDetailsForJobOrderAmd(result);
	}

	private List<Map<String, Object>> getJobOrderItemDetailsForJobOrderAmd(
	        Set<Object[]> result) {

	    List<Map<String, Object>> details1 = new ArrayList<>();

	    for (Object[] fs : result) {

	        Map<String, Object> part = new HashMap<>();

	        part.put("id", fs[0] != null ? fs[0] : null);
	        part.put("item", fs[1] != null ? fs[1] : null);
	        part.put("itemCode", fs[2] != null ? fs[2] : null);
	        part.put("itemDescription", fs[3] != null ? fs[3] : null);
	        part.put("unit", fs[4] != null ? fs[4] : null);
	        part.put("unitDescription", fs[5] != null ? fs[5] : null);
	        part.put("rate", fs[6] != null ? fs[6] : null);

	        part.put("bom", fs[7] != null ? fs[7] : null);


	        part.put("deliveryDate", fs[8] != null ? fs[8] : null);

	        details1.add(part);
	    }

	    return details1;
	}
	
	@Override
	public JobOrderAmendmentResponseDTO getJobOrderAmendmentById(Long id)
	        throws ApplicationException {

	    JobOrderAmendmentVO jobOrderAmendmentVO =
	            jobOrderAmendmentRepo.findById(id)
	                    .orElseThrow(() ->
	                            new ApplicationException(
	                                    "Job Order Amendment Not Found"));

	    return buildJobOrderAmendmentResponse(jobOrderAmendmentVO);
	}
	
	@Override
	public List<JobOrderAmendmentResponseDTO> getJobOrderAmendmentByOrgIdAndBranch(
	        Long orgId, Long branch) throws ApplicationException {

	    List<JobOrderAmendmentVO> jobOrderAmendments =
	            jobOrderAmendmentRepo.findByOrgIdAndBranch(
	                    orgId, branch);

	    List<JobOrderAmendmentResponseDTO> responseList =
	            new ArrayList<>();

	    for (JobOrderAmendmentVO vo : jobOrderAmendments) {

	        responseList.add(
	                buildJobOrderAmendmentResponse(vo));
	    }

	    return responseList;
	}
	
	@Override
	public String getJobOrderAmendmentDocId(
	        Long orgId,
	        String financialYear) {

	    String screenCode1 = "JOA";

	    String result =
	            jobOrderAmendmentRepo.getJobOrderAmendmentDocId(
	                    orgId,
	                    financialYear,
	                    screenCode1);

	    return result;
	}
	
	//DeliveryChallanSubcontracting
	
	@Override
	@Transactional
	public Map<String, Object> createUpdateDeliveryChallanSubcontracting(
	        DeliveryChallanSubcontractingDTO deliveryChallanSubcontractingDTO)
	        throws ApplicationException {

	    String screenCode = "SCDC";

	    Map<String, Object> response = new HashMap<>();

	    String message;

	    DeliveryChallanSubcontractingVO deliveryChallanSubcontractingVO;

	    if (ObjectUtils.isEmpty(deliveryChallanSubcontractingDTO.getId())) {

	        deliveryChallanSubcontractingVO =
	                new DeliveryChallanSubcontractingVO();

	        String docId =
	                deliveryChallanSubcontractingRepo
	                        .getDeliveryChallanSubcontractingDocId(
	                                deliveryChallanSubcontractingDTO.getOrgId(),
	                                deliveryChallanSubcontractingDTO.getFinancialYear(),
	                                screenCode);

	        deliveryChallanSubcontractingVO.setDocId(docId);

	        DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO =
	                documentTypeMappingDetailsRepo
	                        .findByOrgIdAndFinYearAndScreenCode(
	                                deliveryChallanSubcontractingDTO.getOrgId(),
	                                deliveryChallanSubcontractingDTO.getFinancialYear(),
	                                screenCode);

	        documentTypeMappingDetailsVO.setLastNo(
	                documentTypeMappingDetailsVO.getLastNo() + 1);

	        documentTypeMappingDetailsRepo.save(
	                documentTypeMappingDetailsVO);

	        deliveryChallanSubcontractingVO.setCreatedBy(
	                deliveryChallanSubcontractingDTO.getCreatedBy());

	        deliveryChallanSubcontractingVO.setUpdatedBy(
	                deliveryChallanSubcontractingDTO.getCreatedBy());

	        message =
	                "Delivery Challan For Sub Contracting Created Successfully";

	    } else {

	        deliveryChallanSubcontractingVO =
	                deliveryChallanSubcontractingRepo
	                        .findById(
	                                deliveryChallanSubcontractingDTO.getId())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Delivery Challan For Sub Contracting Not Found"));

	        deliveryChallanSubcontractingVO.setUpdatedBy(
	                deliveryChallanSubcontractingDTO.getCreatedBy());

	        message =
	                "Delivery Challan For Sub Contracting Updated Successfully";
	    }

	    createUpdateDeliveryChallanSubcontractingVOByDTO(
	            deliveryChallanSubcontractingDTO,
	            deliveryChallanSubcontractingVO);

	    // Cascade saves everything
	    deliveryChallanSubcontractingVO =
	            deliveryChallanSubcontractingRepo.save(
	                    deliveryChallanSubcontractingVO);

	    DeliveryChallanSubcontractingResponseDTO responseDTO =
	            buildDeliveryChallanSubcontractingResponse(
	                    deliveryChallanSubcontractingVO);

	    response.put("message", message);

	    response.put(
	            "deliveryChallanSubcontracting",
	            responseDTO);

	    return response;
	}
	
	private void createUpdateDeliveryChallanSubcontractingVOByDTO(
	        DeliveryChallanSubcontractingDTO dto,
	        DeliveryChallanSubcontractingVO deliveryChallanSubcontractingVO)
	        throws ApplicationException {

	    // ============================================================
	    // Header Fields
	    // ============================================================

	    deliveryChallanSubcontractingVO.setBelongsTo(
	            dto.getBelongsTo());

	    deliveryChallanSubcontractingVO.setJobOrderNo(
	            dto.getJobOrderNo());

	    deliveryChallanSubcontractingVO.setVehicleNo(
	            dto.getVehicleNo());

	    deliveryChallanSubcontractingVO.setQty(
	            dto.getQty());

	    deliveryChallanSubcontractingVO.setTimeOfIssue(
	            dto.getTimeOfIssue());

	    deliveryChallanSubcontractingVO.setDcType(
	            dto.getDcType());

	    deliveryChallanSubcontractingVO.setApprovalByStores(
	            dto.getApprovalByStores());

	    deliveryChallanSubcontractingVO.setRemarks(
	            dto.getRemarks());

	    deliveryChallanSubcontractingVO.setOrgId(
	            dto.getOrgId());

	    deliveryChallanSubcontractingVO.setFinancialYear(
	            dto.getFinancialYear());

	    deliveryChallanSubcontractingVO.setActive(
	            dto.isActive());

	    deliveryChallanSubcontractingVO.setCancelRemarks(
	            dto.getCancelRemarks());


	    // ============================================================
	    // Branch
	    // ============================================================

	    if (dto.getBranch() != null
	            && dto.getBranch() != 0) {

	        BranchVO branch =
	                branchRepo.findById(dto.getBranch())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Branch Not Found"));

	        deliveryChallanSubcontractingVO.setBranch(branch);
	    }


	    // ============================================================
	    // Department
	    // ============================================================

	    if (dto.getDepartment() != null
	            && dto.getDepartment() != 0) {

	        DepartmentVO department =
	                departmentRepo.findById(dto.getDepartment())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Department Not Found"));

	        deliveryChallanSubcontractingVO.setDepartment(
	                department);
	    }


	    // ============================================================
	    // Vendor
	    // ============================================================

	    if (dto.getVendor() != null
	            && dto.getVendor() != 0) {

	        CustomerVO vendor =
	                customerRepo.findById(dto.getVendor())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Vendor Not Found"));

	        deliveryChallanSubcontractingVO.setVendor(vendor);
	    }


	    // ============================================================
	    // Party Location
	    // ============================================================

	    if (dto.getPartyLocation() != null
	            && dto.getPartyLocation() != 0) {

	        LocationVO partyLocation =
	                locationRepo.findById(
	                        dto.getPartyLocation())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Party Location Not Found"));

	        deliveryChallanSubcontractingVO.setPartyLocation(
	                partyLocation);
	    }


	    // ============================================================
	    // Incoming Item
	    // ============================================================

	    if (dto.getIncomingItem() != null
	            && dto.getIncomingItem() != 0) {

	        ItemMasterVO incomingItem =
	                itemMasterRepo.findById(
	                        dto.getIncomingItem())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Incoming Item Not Found"));

	        deliveryChallanSubcontractingVO.setIncomingItem(
	                incomingItem);
	    }


	    // ============================================================
	    // Transport
	    // ============================================================

	    if (dto.getTransportName() != null
	            && dto.getTransportName() != 0) {

	        TransportMasterVO transport =
	                transportRepo.findById(
	                        dto.getTransportName())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Transport Not Found"));

	        deliveryChallanSubcontractingVO.setTransportName(
	                transport);
	    }


	    // ============================================================
	    // SFG BOM
	    // ============================================================

	    if (dto.getSfgBomId() != null
	            && dto.getSfgBomId() != 0) {

	        BomVO bom =
	                bomRepo.findById(dto.getSfgBomId())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "BOM Not Found"));

	        deliveryChallanSubcontractingVO.setSfgBomId(bom);
	    }


	    // ============================================================
	    // Prepared By
	    // ============================================================

	    if (dto.getPreparedBy() != null
	            && dto.getPreparedBy() != 0) {

	        EmployeeMasterVO preparedBy =
	                employeeMasterRepo.findById(
	                        dto.getPreparedBy())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Prepared By Employee Not Found"));

	        deliveryChallanSubcontractingVO.setPreparedBy(
	                preparedBy);
	    }


	    // ============================================================
	    // Approved By
	    // ============================================================

	    if (dto.getApprovedBy() != null
	            && dto.getApprovedBy() != 0) {

	        EmployeeMasterVO approvedBy =
	                employeeMasterRepo.findById(
	                        dto.getApprovedBy())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Approved By Employee Not Found"));

	        deliveryChallanSubcontractingVO.setApprovedBy(
	                approvedBy);
	    }


	    // ============================================================
	    // Delete Old Details While Updating
	    // ============================================================

	    if (dto.getId() != null) {

	        List<DeliveryChallanSubcontractingDetailsVO> oldList =
	                deliveryChallanSubcontractingDetailsRepo
	                        .findByDeliveryChallanSubcontracting(
	                                deliveryChallanSubcontractingVO);

	        deliveryChallanSubcontractingDetailsRepo.deleteAll(
	                oldList);
	    }


	    // ============================================================
	    // Child Details
	    // ============================================================

	    List<DeliveryChallanSubcontractingDetailsVO> detailList =
	            new ArrayList<>();

	    if (dto.getDetails() != null
	            && !dto.getDetails().isEmpty()) {

	        for (DeliveryChallanSubcontractingDetailsDTO detailDTO :
	                dto.getDetails()) {

	            DeliveryChallanSubcontractingDetailsVO detailVO =
	                    new DeliveryChallanSubcontractingDetailsVO();


	            // ====================================================
	            // Outgoing Item
	            // ====================================================

	            if (detailDTO.getOutgoingItem() != null
	                    && detailDTO.getOutgoingItem() != 0) {

	                ItemMasterVO outgoingItem =
	                        itemMasterRepo.findById(
	                                detailDTO.getOutgoingItem())
	                                .orElseThrow(() ->
	                                        new ApplicationException(
	                                                "Outgoing Item Not Found"));

	                detailVO.setOutgoingItem(
	                        outgoingItem);
	            }


	            // ====================================================
	            // Unit
	            // ====================================================

	            if (detailDTO.getUnit() != null
	                    && detailDTO.getUnit() != 0) {

	                UnitMasterVO unit =
	                        unitMasterRepo.findById(
	                                detailDTO.getUnit())
	                                .orElseThrow(() ->
	                                        new ApplicationException(
	                                                "Unit Not Found"));

	                detailVO.setUnit(unit);
	            }


	            // ====================================================
	            // From Location
	            // ====================================================

	            if (detailDTO.getFromLocation() != null
	                    && detailDTO.getFromLocation() != 0) {

	                LocationVO fromLocation =
	                        locationRepo.findById(
	                                detailDTO.getFromLocation())
	                                .orElseThrow(() ->
	                                        new ApplicationException(
	                                                "From Location Not Found"));

	                detailVO.setFromLocation(
	                        fromLocation);
	            }


	            // ====================================================
	            // Other Detail Fields
	            // ====================================================

	            detailVO.setStock(
	                    detailDTO.getStock());

	            detailVO.setAvailableStock(
	                    detailDTO.getAvailableStock());

	            detailVO.setIssueQty(
	                    detailDTO.getIssueQty());

	            detailVO.setUnitRate(
	                    detailDTO.getUnitRate());

	            BigDecimal amount = BigDecimal.ZERO;

	            if (detailDTO.getIssueQty() != null
	                    && detailDTO.getUnitRate() != null) {

	                amount = detailDTO.getIssueQty()
	                        .multiply(detailDTO.getUnitRate());
	            }

	            detailVO.setAmount(amount);

	            detailVO.setRemarks(
	                    detailDTO.getRemarks());


	            // ====================================================
	            // Parent Mapping
	            // ====================================================

	            detailVO.setDeliveryChallanSubcontracting(
	                    deliveryChallanSubcontractingVO);

	            detailList.add(detailVO);
	        }


	        // Set child list to parent

	        deliveryChallanSubcontractingVO.setDetails(
	                detailList);
	    }
	}
	
	private DeliveryChallanSubcontractingResponseDTO
	buildDeliveryChallanSubcontractingResponse(
	        DeliveryChallanSubcontractingVO vo) {

	    DeliveryChallanSubcontractingResponseDTO response =
	            new DeliveryChallanSubcontractingResponseDTO();

	    // ============================================================
	    // Header
	    // ============================================================

	    response.setId(vo.getId());

	    response.setDocId(vo.getDocId());

	    response.setDocDate(vo.getDocDate());

	    response.setBelongsTo(vo.getBelongsTo());

	    response.setJobOrderNo(vo.getJobOrderNo());

	    response.setVehicleNo(vo.getVehicleNo());

	    response.setQty(vo.getQty());

	    response.setTimeOfIssue(vo.getTimeOfIssue());

	    response.setDcType(vo.getDcType());

	    response.setApprovalByStores(
	            vo.getApprovalByStores());

	    response.setRemarks(vo.getRemarks());

	    response.setOrgId(vo.getOrgId());

	    response.setFinancialYear(
	            vo.getFinancialYear());

	    response.setCreatedBy(
	            vo.getCreatedBy());

	    response.setUpdatedBy(
	            vo.getUpdatedBy());

	    response.setCancelRemarks(
	            vo.getCancelRemarks());

	    response.setActive(
	            vo.getActive());

	    response.setCancel(
	            vo.getCancel());

	    response.setScreenCode(
	            vo.getScreenCode());

	    response.setScreenName(
	            vo.getScreenName());


	    // ============================================================
	    // Branch
	    // ============================================================

	    if (vo.getBranch() != null) {

	        BranchResponseDTO branch =
	                new BranchResponseDTO();

	        branch.setId(
	                vo.getBranch().getId());

	        branch.setBranchCode(
	                vo.getBranch().getBranchCode());

	        branch.setBranchName(
	                vo.getBranch().getBranchName());

	        response.setBranch(branch);
	    }


	    // ============================================================
	    // Department
	    // ============================================================

	    if (vo.getDepartment() != null) {

	        DepartmentResponseDTO department =
	                new DepartmentResponseDTO();

	        department.setId(
	                vo.getDepartment().getId());

	        department.setDepartmentCode(
	                vo.getDepartment().getDepartmentCode());

	        department.setDepartmentName(
	                vo.getDepartment().getDepartmentName());

	        response.setDepartment(department);
	    }


	    // ============================================================
	    // Vendor
	    // ============================================================

	    if (vo.getVendor() != null) {

	        CustomerDropdownResponseDTO vendor =
	                new CustomerDropdownResponseDTO();

	        vendor.setCustomerId(
	                vo.getVendor().getId());

	        vendor.setCustomerCode(
	                vo.getVendor().getCustomerCode());

	        vendor.setCustomerName(
	                vo.getVendor().getCustomerName());

	        response.setVendor(vendor);
	    }


	    // ============================================================
	    // Party Location
	    // ============================================================

	    if (vo.getPartyLocation() != null) {

	        LocationMasterResponseDTO location =
	                new LocationMasterResponseDTO();

	        location.setId(
	                vo.getPartyLocation().getId());

	        location.setLocationName(
	                vo.getPartyLocation().getLocationName());

	        response.setPartyLocation(location);
	    }


	    // ============================================================
	    // Incoming Item
	    // ============================================================

	    if (vo.getIncomingItem() != null) {

	        ItemResponseDTO item =
	                new ItemResponseDTO();

	        item.setId(
	                vo.getIncomingItem().getId());

	        item.setItemCode(
	                vo.getIncomingItem().getItemCode());

	        item.setItemDescription(
	                vo.getIncomingItem().getItemDescription());

	        // If ItemMasterVO has Unit
	        if (vo.getIncomingItem().getPrimaryUnit() != null) {

	            UnitResponseDTO unit =
	                    new UnitResponseDTO();

	            unit.setId(
	                    vo.getIncomingItem().getPrimaryUnit().getId());

	            unit.setUnitId(
	                    vo.getIncomingItem().getPrimaryUnit().getDescription());

	            item.setUnit(unit);
	        }

	        response.setIncomingItem(item);
	    }


	    // ============================================================
	    // Transport
	    // ============================================================

	    if (vo.getTransportName() != null) {

	        TransportResponseDTO transport =
	                new TransportResponseDTO();

	        transport.setId(
	                vo.getTransportName().getId());

	        transport.setTransportName(
	                vo.getTransportName().getTransportName());

	        response.setTransportName(transport);
	    }


	    // ============================================================
	    // SFG BOM
	    // ============================================================

	    if (vo.getSfgBomId() != null) {

	        BomResponseDTO bom =
	                new BomResponseDTO();

	        bom.setId(
	                vo.getSfgBomId().getId());

	        bom.setProductType(
	                vo.getSfgBomId().getProductType());

	        bom.setProductCode(
	                vo.getSfgBomId().getProductCode());

	        bom.setProductName(
	                vo.getSfgBomId().getProductName());

	        bom.setUom(
	                vo.getSfgBomId().getUom());

	        bom.setQty(
	                vo.getSfgBomId().getQty());

	        response.setSfgBomId(bom);
	    }

	    // ============================================================
	    // Prepared By
	    // ============================================================

	    if (vo.getPreparedBy() != null) {

	        EmployeeResponseDTO employee =
	                new EmployeeResponseDTO();

	        employee.setId(
	                vo.getPreparedBy().getId());

	        employee.setEmployeeName(
	                vo.getPreparedBy().getEmployeeName());

	        response.setPreparedBy(employee);
	    }


	    // ============================================================
	    // Approved By
	    // ============================================================

	    if (vo.getApprovedBy() != null) {

	        EmployeeResponseDTO employee =
	                new EmployeeResponseDTO();

	        employee.setId(
	                vo.getApprovedBy().getId());

	        employee.setEmployeeName(
	                vo.getApprovedBy().getEmployeeName());

	        response.setApprovedBy(employee);
	    }


	    // ============================================================
	    // Details
	    // ============================================================

	    List<DeliveryChallanSubcontractingDetailsResponseDTO>
	            detailsResponse = new ArrayList<>();

	    if (vo.getDetails() != null) {

	        for (DeliveryChallanSubcontractingDetailsVO detailVO :
	                vo.getDetails()) {

	            DeliveryChallanSubcontractingDetailsResponseDTO
	                    detailResponse =
	                    new DeliveryChallanSubcontractingDetailsResponseDTO();


	            // ====================================================
	            // Detail ID
	            // ====================================================

	            detailResponse.setId(
	                    detailVO.getId());


	            // ====================================================
	            // Stock
	            // ====================================================

	            detailResponse.setStock(
	                    detailVO.getStock());


	            // ====================================================
	            // Available Stock
	            // ====================================================

	            detailResponse.setAvailableStock(
	                    detailVO.getAvailableStock());


	            // ====================================================
	            // Issue Qty
	            // ====================================================

	            detailResponse.setIssueQty(
	                    detailVO.getIssueQty());


	            // ====================================================
	            // Unit Rate
	            // ====================================================

	            detailResponse.setUnitRate(
	                    detailVO.getUnitRate());


	            // ====================================================
	            // Amount
	            // ====================================================

	            detailResponse.setAmount(
	                    detailVO.getAmount());


	            // ====================================================
	            // Remarks
	            // ====================================================

	            detailResponse.setRemarks(
	                    detailVO.getRemarks());


	            // ====================================================
	            // Outgoing Item
	            // ====================================================

	            if (detailVO.getOutgoingItem() != null) {

	                ItemResponseDTO item =
	                        new ItemResponseDTO();

	                item.setId(
	                        detailVO.getOutgoingItem().getId());

	                item.setItemCode(
	                        detailVO.getOutgoingItem().getItemCode());

	                item.setItemDescription(
	                        detailVO.getOutgoingItem()
	                                .getItemDescription());

	                // Item Unit
	                if (detailVO.getOutgoingItem().getPrimaryUnit() != null) {

	                    UnitResponseDTO itemUnit =
	                            new UnitResponseDTO();

	                    itemUnit.setId(
	                            detailVO.getOutgoingItem()
	                                    .getPrimaryUnit()
	                                    .getId());

	                    itemUnit.setUnitId(
	                            detailVO.getOutgoingItem()
	                                    .getPrimaryUnit()
	                                    .getDescription());

	                    item.setUnit(itemUnit);
	                }

	                detailResponse.setOutgoingItem(item);
	            }


	            // ====================================================
	            // Unit
	            // ====================================================

	            if (detailVO.getUnit() != null) {

	                UnitResponseDTO unit =
	                        new UnitResponseDTO();

	                unit.setId(
	                        detailVO.getUnit().getId());

	                unit.setUnitId(
	                        detailVO.getUnit().getDescription());

	                detailResponse.setUnit(unit);
	            }


	            // ====================================================
	            // From Location
	            // ====================================================

	            if (detailVO.getFromLocation() != null) {

	                LocationMasterResponseDTO location =
	                        new LocationMasterResponseDTO();

	                location.setId(
	                        detailVO.getFromLocation().getId());

	                location.setLocationName(
	                        detailVO.getFromLocation()
	                                .getLocationName());

	                detailResponse.setFromLocation(
	                        location);
	            }


	            detailsResponse.add(detailResponse);
	        }
	    }

	    response.setDetails(detailsResponse);

	    return response;
	}
	
	@Override
	public List<Map<String, Object>> getLocationForDeliverChallanSubContract(
	        Long orgId, Long branch) {

	    Set<Object[]> result =
	            locationRepo.getLocationForDeliverChallanSubContract(
	                    orgId, branch);

	    return getSubContractLocationDropdown(result);
	}

	private List<Map<String, Object>> getSubContractLocationDropdown(
	        Set<Object[]> result) {

	    List<Map<String, Object>> details1 =
	            new ArrayList<>();

	    for (Object[] fs : result) {

	        Map<String, Object> part =
	                new HashMap<>();

	        part.put("id",
	                fs[0] != null ? fs[0] : null);

	        part.put("locationId",
	                fs[1] != null ? fs[1] : null);

	        part.put("locationName",
	                fs[2] != null ? fs[2] : null);

	        details1.add(part);
	    }

	    return details1;
	}
	
	
	@Override
	public List<Map<String, Object>> getItemDetailsforDeliveryChallanSubContract(
	        String jobOrderNo,
	        Long branch,
	        Long orgId,
	        Long vendor) {

	    Set<Object[]> result =
	            jobOrderRepo.getItemDetailsforDeliveryChallanSubContract(
	                    jobOrderNo,
	                    branch,
	                    orgId,
	                    vendor);

	    return getItemDetailsforDeliveryChallanSubContract(result);
	}

	private List<Map<String, Object>> getItemDetailsforDeliveryChallanSubContract(
	        Set<Object[]> result) {

	    List<Map<String, Object>> details1 = new ArrayList<>();

	    for (Object[] fs : result) {

	        Map<String, Object> part = new HashMap<>();

	        part.put("id", fs[0] != null ? fs[0] : null);
	        part.put("jobOrderFor", fs[1] != null ? fs[1] : null);
	        part.put("contractNo", fs[2] != null ? fs[2] : null);
	        part.put("outgoingItem", fs[3] != null ? fs[3] : null);
	        part.put("itemCode", fs[4] != null ? fs[4] : null);
	        part.put("itemDescription", fs[5] != null ? fs[5] : null);
	        part.put("unit", fs[6] != null ? fs[6] : null);
	        part.put("unitDescription", fs[7] != null ? fs[7] : null);
	        part.put("rate", fs[8] != null ? fs[8] : null);

	        details1.add(part);
	    }

	    return details1;
	}
	
	
	@Override
	@Transactional
	public Map<String, Object> createUpdateSubContractSupplySchedule(
	        SubContractSupplyScheduleDTO dto) throws ApplicationException {

	    String screenCode = "SCSS";

	    Map<String, Object> response = new HashMap<>();

	    String message;

	    SubContractSupplyScheduleVO subContractSupplyScheduleVO;


	    // =========================================================
	    // CREATE
	    // =========================================================

	    if (ObjectUtils.isEmpty(dto.getId())) {

	        subContractSupplyScheduleVO = new SubContractSupplyScheduleVO();

	        // Generate Document ID
//	        String docId = subContractSupplyScheduleRepo.getSubContractSupplyScheduleDocId(
//	                dto.getOrgId(),
//	                dto.getFinancialYear(),
//	                screenCode);
//
//	        subContractSupplyScheduleVO.setDocId(docId);
//
//
//	        // Update Document Last Number
//	        DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO =
//	                documentTypeMappingDetailsRepo.findByOrgIdAndFinYearAndScreenCode(
//	                        dto.getOrgId(),
//	                        dto.getFinancialYear(),
//	                        screenCode);
//
//	        if (documentTypeMappingDetailsVO != null) {
//
//	            documentTypeMappingDetailsVO.setLastNo(
//	                    documentTypeMappingDetailsVO.getLastNo() + 1);
//
//	            documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
//	        }


	        subContractSupplyScheduleVO.setCreatedBy(dto.getCreatedBy());
	        subContractSupplyScheduleVO.setUpdatedBy(dto.getCreatedBy());

	        message = "Sub Contract Supplier Schedule Created Successfully";
	    }


	    // =========================================================
	    // UPDATE
	    // =========================================================

	    else {

	        subContractSupplyScheduleVO =
	                subContractSupplyScheduleRepo.findById(dto.getId())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Sub Contract Supplier Schedule Not Found"));


	     // Delete Old Item Details

	        if (subContractSupplyScheduleVO.getItemDetails() != null) {

	            // Delete Child of Child - Schedule Details
	            for (SubContractSupplyScheduleItemDetailsVO itemDetails :
	                    subContractSupplyScheduleVO.getItemDetails()) {

	                if (itemDetails.getScheduleDetails() != null) {
	                    subContractSupplyScheduleDetailsRepo
	                            .deleteAll(itemDetails.getScheduleDetails());

	                    itemDetails.getScheduleDetails().clear();
	                }
	            }

	            // Delete Child - Item Details
	            subContractSupplyScheduleItemDetailsRepo
	                    .deleteAll(subContractSupplyScheduleVO.getItemDetails());

	            subContractSupplyScheduleVO.getItemDetails().clear();
	        }


	        subContractSupplyScheduleVO.setUpdatedBy(dto.getCreatedBy());

	        message = "Sub Contract Supplier Schedule Updated Successfully";
	    }


	    // =========================================================
	    // HEADER MAPPING
	    // =========================================================

	    getSubContractSupplyScheduleVOFromDTO(
	            dto,
	            subContractSupplyScheduleVO);


	    // =========================================================
	    // SAVE
	    // =========================================================

	    subContractSupplyScheduleVO =
	            subContractSupplyScheduleRepo.saveAndFlush(
	                    subContractSupplyScheduleVO);


	    // =========================================================
	    // RESPONSE
	    // =========================================================

	    response.put("message", message);

	    response.put(
	            "subContractSupplyScheduleVO",
	            convertToSubContractSupplyScheduleResponse(subContractSupplyScheduleVO));

	    return response;
	}
	
	private void getSubContractSupplyScheduleVOFromDTO(
	        SubContractSupplyScheduleDTO dto,
	        SubContractSupplyScheduleVO vo)
	        throws ApplicationException {


	    // =========================================================
	    // Branch
	    // =========================================================

	    if (dto.getBranch() != null) {

	        BranchVO branch =
	                branchRepo.findById(dto.getBranch())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Branch Not Found"));

	        vo.setBranch(branch);
	    }


	    // =========================================================
	    // Customer
	    // =========================================================

	    if (dto.getCustomer() != null) {

	        CustomerVO customer =
	                customerRepo.findById(dto.getCustomer())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Customer Not Found"));

	        vo.setCustomer(customer);
	    }


	    // =========================================================
	    // Prepared By
	    // =========================================================

	    if (dto.getPreparedBy() != null) {

	        EmployeeMasterVO preparedBy =
	                employeeMasterRepo.findById(dto.getPreparedBy())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Prepared By Employee Not Found"));

	        vo.setPreparedBy(preparedBy);
	    }


	    // =========================================================
	    // Authorised By
	    // =========================================================

	    if (dto.getAuthorisedBy() != null) {

	        EmployeeMasterVO authorisedBy =
	                employeeMasterRepo.findById(dto.getAuthorisedBy())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Authorised By Employee Not Found"));

	        vo.setAuthorisedBy(authorisedBy);
	    }


	    // =========================================================
	    // Header Fields
	    // =========================================================

	    vo.setBelongsTo(dto.getBelongsTo());

	    vo.setSchStartDate(dto.getSchStartDate());

	    vo.setSchEndDate(dto.getSchEndDate());

	    vo.setContractNo(dto.getContractNo());

	    vo.setContractDate(dto.getContractDate());

	    vo.setJobOrderNo(dto.getJobOrderNo());

	    vo.setRemarks(dto.getRemarks());

	    vo.setOrgId(dto.getOrgId());

	    vo.setFinancialYear(dto.getFinancialYear());

	    vo.setActive(dto.isActive());

	    vo.setCancelRemarks(dto.getCancelRemarks());

	    // =========================================================
	    // Item Details
	    // =========================================================

	    List<SubContractSupplyScheduleItemDetailsVO> itemDetailsList =
	            new ArrayList<>();


	    if (dto.getItemDetails() != null) {

	        for (SubContractSupplyScheduleItemDetailsDTO itemDTO :
	                dto.getItemDetails()) {


	            SubContractSupplyScheduleItemDetailsVO itemDetails =
	                    new SubContractSupplyScheduleItemDetailsVO();


	            // =================================================
	            // Item
	            // =================================================

	            if (itemDTO.getItem() != null) {

	                ItemMasterVO itemCode =
	                        itemMasterRepo.findById(itemDTO.getItem())
	                                .orElseThrow(() ->
	                                        new ApplicationException(
	                                                "Item Not Found"));

	                itemDetails.setItem(itemCode);
	            }


	            // =================================================
	            // Unit
	            // =================================================

	            if (itemDTO.getUnit() != null) {

	                UnitMasterVO unit =
	                        unitMasterRepo.findById(itemDTO.getUnit())
	                                .orElseThrow(() ->
	                                        new ApplicationException(
	                                                "Unit Not Found"));

	                itemDetails.setUnit(unit);
	            }


	            // =================================================
	            // Item Fields
	            // =================================================

	            itemDetails.setStock(itemDTO.getStock());

	            itemDetails.setQty(itemDTO.getQty());

	            itemDetails.setRate(itemDTO.getRate());


	            // =================================================
	            // Parent Mapping
	            // =================================================

	            itemDetails.setSubContractSupplyScheduleVO(
	                    vo);


	            // =================================================
	            // Schedule Details
	            // =================================================

	            List<SubContractSupplyScheduleDetailsVO> scheduleDetailsList =
	                    new ArrayList<>();


	            if (itemDTO.getScheduleDetails() != null) {

	                for (SubContractSupplyScheduleDetailsDTO scheduleDTO :
	                        itemDTO.getScheduleDetails()) {


	                    SubContractSupplyScheduleDetailsVO scheduleDetails =
	                            new SubContractSupplyScheduleDetailsVO();


	                    scheduleDetails.setPlanDate(
	                            scheduleDTO.getPlanDate());

	                    scheduleDetails.setScheduleQty(
	                            scheduleDTO.getScheduleQty());


	                    // Parent Item Mapping
	                    scheduleDetails.setItemDetails(
	                            itemDetails);


	                    scheduleDetailsList.add(
	                            scheduleDetails);
	                }
	            }


	            itemDetails.setScheduleDetails(
	                    scheduleDetailsList);


	            itemDetailsList.add(
	                    itemDetails);
	        }
	    }


	    // =========================================================
	    // Set Item Details to Header
	    // =========================================================

	    vo.setItemDetails(itemDetailsList);
	}
	
	private SubContractSupplyScheduleResponseDTO convertToSubContractSupplyScheduleResponse(
	        SubContractSupplyScheduleVO vo) {

	    SubContractSupplyScheduleResponseDTO response =
	            new SubContractSupplyScheduleResponseDTO();

	    // ============================================================
	    // Header
	    // ============================================================

	    response.setId(vo.getId());

	    response.setDocId(vo.getDocId());

	    response.setSchStartDate(vo.getSchStartDate());

	    response.setDocDate(vo.getDocDate());

	    response.setSchEndDate(vo.getSchEndDate());

	    response.setBelongsTo(vo.getBelongsTo());

	    response.setContractNo(vo.getContractNo());

	    response.setContractDate(vo.getContractDate());

	    response.setJobOrderNo(vo.getJobOrderNo());

	    response.setRemarks(vo.getRemarks());

	    response.setOrgId(vo.getOrgId());

	    response.setFinancialYear(
	            vo.getFinancialYear());

	    response.setCreatedBy(
	            vo.getCreatedBy());

	    response.setUpdatedBy(
	            vo.getUpdatedBy());

	    response.setCancelRemarks(
	            vo.getCancelRemarks());

	    response.setActive(
	            vo.getActive());

	    response.setCancel(
	            vo.getCancel());

	    response.setScreenCode(
	            vo.getScreenCode());

	    response.setScreenName(
	            vo.getScreenName());


	    // ============================================================
	    // Branch
	    // ============================================================

	    if (vo.getBranch() != null) {

	        BranchResponseDTO branch =
	                new BranchResponseDTO();

	        branch.setId(
	                vo.getBranch().getId());

	        branch.setBranchCode(
	                vo.getBranch().getBranchCode());

	        branch.setBranchName(
	                vo.getBranch().getBranchName());

	        response.setBranch(branch);
	    }


	    // ============================================================
	    // Customer
	    // ============================================================

	    if (vo.getCustomer() != null) {

	        CustomerDropdownResponseDTO customer =
	                new CustomerDropdownResponseDTO();

	        customer.setCustomerId(
	                vo.getCustomer().getId());

	        customer.setCustomerCode(
	                vo.getCustomer().getCustomerCode());

	        customer.setCustomerName(
	                vo.getCustomer().getCustomerName());

	        response.setCustomer(customer);
	    }


	    // ============================================================
	    // Prepared By
	    // ============================================================

	    if (vo.getPreparedBy() != null) {

	        EmployeeResponseDTO employee =
	                new EmployeeResponseDTO();

	        employee.setId(
	                vo.getPreparedBy().getId());

	        employee.setEmployeeName(
	                vo.getPreparedBy().getEmployeeName());

	        response.setPreparedBy(employee);
	    }


	    // ============================================================
	    // Authorised By
	    // ============================================================

	    if (vo.getAuthorisedBy() != null) {

	        EmployeeResponseDTO employee =
	                new EmployeeResponseDTO();

	        employee.setId(
	                vo.getAuthorisedBy().getId());

	        employee.setEmployeeName(
	                vo.getAuthorisedBy().getEmployeeName());

	        response.setAuthorisedBy(employee);
	    }


	    // ============================================================
	    // Item Details
	    // ============================================================

	    List<SubContractSupplyScheduleItemDetailsResponseDTO>
	            itemDetailsResponse = new ArrayList<>();

	    if (vo.getItemDetails() != null) {

	        for (SubContractSupplyScheduleItemDetailsVO itemVO :
	                vo.getItemDetails()) {

	            SubContractSupplyScheduleItemDetailsResponseDTO
	                    itemResponse =
	                    new SubContractSupplyScheduleItemDetailsResponseDTO();


	            // ====================================================
	            // Item Detail ID
	            // ====================================================

	            itemResponse.setId(
	                    itemVO.getId());


	            // ====================================================
	            // Item Code
	            // ====================================================

	            if (itemVO.getItem() != null) {

	                ItemMasterResponseDetailsDTO item =
	                        new ItemMasterResponseDetailsDTO();

	                item.setId(
	                        itemVO.getItem().getId());

	                item.setItemCode(
	                        itemVO.getItem().getItemCode());

	                item.setItemDescription(
	                        itemVO.getItem().getItemDescription());

	                // Item Primary Unit
	                if (itemVO.getItem().getPrimaryUnit() != null) {

	                    UnitMasterResponseDTO unit =
	                            new UnitMasterResponseDTO();

	                    unit.setId(
	                            itemVO.getItem()
	                                    .getPrimaryUnit()
	                                    .getId());

	                    unit.setUnitId(
	                            itemVO.getItem()
	                                    .getPrimaryUnit()
	                                    .getUnitId());

	                    
	                    unit.setUnitId(
	                            itemVO.getItem()
	                                    .getPrimaryUnit()
	                                    .getDescription());

	                    item.setUnit(unit);
	                }

	                itemResponse.setItemCode(item);
	            }


	            // ====================================================
	            // Unit
	            // ====================================================

//	            if (itemVO.getUnit() != null) {
//
//	                UnitMasterResponseDTO unit =
//	                        new UnitMasterResponseDTO();
//
//	                unit.setId(
//	                        itemVO.getUnit().getId());
//
//	                unit.setUnitId(
//	                        itemVO.getUnit().getDescription());
//
//	                itemResponse.setUnit(unit);
//	            }


	            // ====================================================
	            // Stock
	            // ====================================================

	            itemResponse.setStock(
	                    itemVO.getStock());


	            // ====================================================
	            // Qty
	            // ====================================================

	            itemResponse.setQty(
	                    itemVO.getQty());


	            // ====================================================
	            // Rate
	            // ====================================================

	            itemResponse.setRate(
	                    itemVO.getRate());


	            // ====================================================
	            // Schedule Details
	            // ====================================================

	            List<SubContractSupplyScheduleDetailsResponseDTO>
	                    scheduleDetailsResponse = new ArrayList<>();

	            if (itemVO.getScheduleDetails() != null) {

	                for (SubContractSupplyScheduleDetailsVO scheduleVO :
	                        itemVO.getScheduleDetails()) {

	                    SubContractSupplyScheduleDetailsResponseDTO
	                            scheduleResponse =
	                            new SubContractSupplyScheduleDetailsResponseDTO();


	                    // ================================================
	                    // Schedule Detail ID
	                    // ================================================

	                    scheduleResponse.setId(
	                            scheduleVO.getId());


	                    // ================================================
	                    // Plan Date
	                    // ================================================

	                    scheduleResponse.setPlanDate(
	                            scheduleVO.getPlanDate());


	                    // ================================================
	                    // Schedule Qty
	                    // ================================================

	                    scheduleResponse.setScheduleQty(
	                            scheduleVO.getScheduleQty());


	                    scheduleDetailsResponse.add(
	                            scheduleResponse);
	                }
	            }

	            itemResponse.setScheduleDetails(
	                    scheduleDetailsResponse);

	            itemDetailsResponse.add(
	                    itemResponse);
	        }
	    }

	    response.setItemDetails(
	            itemDetailsResponse);

	    return response;
	}
	
	@Override
	public List<Map<String, Object>> getJobOrderNoAndDateForSubContractSupplySch(
	        Long branch, Long orgId, String contractNo) {

	    Set<Object[]> result =
	            jobOrderRepo.getJobOrderNoAndDateForSubContractSupplySch(branch, orgId, contractNo);

	    return getJobOrderNoAndDateForSubContractSupplySch(result);
	}

	private List<Map<String, Object>> getJobOrderNoAndDateForSubContractSupplySch(
	        Set<Object[]> result) {

	    List<Map<String, Object>> details = new ArrayList<>();

	    for (Object[] fs : result) {

	        Map<String, Object> part = new HashMap<>();

	        part.put("id", fs[0] != null ? fs[0] : null);
	        part.put("jobOrderNo", fs[1] != null ? fs[1] : null);
	        part.put("jobOrderDate", fs[2] != null ? fs[2] : null);

	        details.add(part);
	    }

	    return details;
	}
	
	@Override
	public SubContractSupplyScheduleResponseDTO getSubContractSupplyScheduleById(
	        Long id) throws ApplicationException {

	    SubContractSupplyScheduleVO subContractSupplyScheduleVO =
	            subContractSupplyScheduleRepo.findById(id)
	                    .orElseThrow(() ->
	                            new ApplicationException(
	                                    "Sub Contract Supply Schedule Not Found"));

	    return convertToSubContractSupplyScheduleResponse(
	            subContractSupplyScheduleVO);
	}

	@Override
	public List<SubContractSupplyScheduleResponseDTO>
	getSubContractSupplyScheduleByOrgIdAndBranch(
	        Long orgId,
	        Long branch) throws ApplicationException {

	    List<SubContractSupplyScheduleVO> subContractSupplySchedules =
	            subContractSupplyScheduleRepo
	                    .findByOrgIdAndBranch(orgId, branch);

	    List<SubContractSupplyScheduleResponseDTO> responseList =
	            new ArrayList<>();

	    for (SubContractSupplyScheduleVO vo : subContractSupplySchedules) {

	        responseList.add(
	                convertToSubContractSupplyScheduleResponse(vo));
	    }

	    return responseList;
	}

	@Override
	public String getSubContractSupplyScheduleDocId(
	        Long orgId,
	        String financialYear) {

	    String screenCode1 = "SCSS";

	    String result =
	            subContractSupplyScheduleRepo
	                    .getSubContractSupplyScheduleDocId(
	                            orgId,
	                            financialYear,
	                            screenCode1);

	    return result;
	}
	
	
	@Override
	@Transactional
	public Map<String, Object> createUpdateSupplierRateContractAmendment(
	        SupplierRateContractAmendmentDTO dto)
	        throws ApplicationException {

	    String screenCode = "SRCA";

	    Map<String, Object> response = new HashMap<>();

	    String message;

	    SupplierRateContractAmendmentVO
	            supplierRateContractAmendmentVO;

	    // ============================================================
	    // Create
	    // ============================================================

	    if (ObjectUtils.isEmpty(dto.getId())) {

	        supplierRateContractAmendmentVO =
	                new SupplierRateContractAmendmentVO();

	        // ========================================================
	        // Generate Doc Id
	        // ========================================================

	        String docId =
	                supplierRateContractAmendmentRepo
	                        .getSupplierRateContractAmendmentDocId(
	                                dto.getOrgId(),
	                                dto.getFinancialYear(),
	                                screenCode);

	        supplierRateContractAmendmentVO.setDocId(docId);

	        supplierRateContractAmendmentVO
	                .setCreatedBy(dto.getCreatedBy());

	        supplierRateContractAmendmentVO
	                .setUpdatedBy(dto.getCreatedBy());

	        message =
	                "Supplier Rate Contract Amendment Created Successfully";

	    } else {

	        // ========================================================
	        // Update
	        // ========================================================

	        supplierRateContractAmendmentVO =
	                supplierRateContractAmendmentRepo
	                        .findById(dto.getId())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Supplier Rate Contract Amendment Not Found"));

	        // ========================================================
	        // Delete Old Item Details
	        // ========================================================

	        if (supplierRateContractAmendmentVO.getItemDetails()
	                != null) {

	            supplierRateContractAmendmentItemDetailsRepo
	                    .deleteAll(
	                            supplierRateContractAmendmentVO
	                                    .getItemDetails());

	            supplierRateContractAmendmentVO
	                    .getItemDetails()
	                    .clear();
	        }

	        supplierRateContractAmendmentVO
	                .setUpdatedBy(dto.getCreatedBy());

	        message =
	                "Supplier Rate Contract Amendment Updated Successfully";
	    }

	    // ============================================================
	    // Set Header And Item Details
	    // ============================================================

	    getSupplierRateContractAmendmentVOFromDTO(
	            dto,
	            supplierRateContractAmendmentVO);

	    // ============================================================
	    // Save
	    // ============================================================

	    supplierRateContractAmendmentVO =
	            supplierRateContractAmendmentRepo
	                    .saveAndFlush(
	                            supplierRateContractAmendmentVO);

	    // ============================================================
	    // Response
	    // ============================================================

	    response.put("message", message);

	    response.put(
	            "supplierRateContractAmendmentVO",
	            convertToSupplierRateContractAmendmentResponse(
	                    supplierRateContractAmendmentVO));

	    return response;
	}
	
	private void getSupplierRateContractAmendmentVOFromDTO(
	        SupplierRateContractAmendmentDTO dto,
	        SupplierRateContractAmendmentVO vo)
	        throws ApplicationException {

	    // ============================================================
	    // Branch
	    // ============================================================

	    if (dto.getBranch() != null) {

	        BranchVO branch =
	                branchRepo.findById(dto.getBranch())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Branch Not Found"));

	        vo.setBranch(branch);
	    }

	    // ============================================================
	    // Customer
	    // ============================================================

	    if (dto.getCustomer() != null) {

	        CustomerVO customer =
	                customerRepo.findById(dto.getCustomer())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Customer Not Found"));

	        vo.setCustomer(customer);
	    }

	    // ============================================================
	    // Prepared By
	    // ============================================================

	    if (dto.getPreparedBy() != null) {

	        EmployeeMasterVO preparedBy =
	                employeeMasterRepo.findById(dto.getPreparedBy())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Prepared By Employee Not Found"));

	        vo.setPreparedBy(preparedBy);
	    }

	    // ============================================================
	    // Authorised By
	    // ============================================================

	    if (dto.getAuthorisedBy() != null) {

	        EmployeeMasterVO authorisedBy =
	                employeeMasterRepo.findById(dto.getAuthorisedBy())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Authorised By Employee Not Found"));

	        vo.setAuthorisedBy(authorisedBy);
	    }

	    // ============================================================
	    // Header Details
	    // ============================================================


	    vo.setBelongsTo(dto.getBelongsTo());

	    vo.setContractDate(dto.getContractDate());

	    vo.setContractNo(dto.getContractNo());

	    vo.setValidFrom(dto.getValidFrom());

	    vo.setNewValidFrom(dto.getNewValidFrom());

	    vo.setValidTo(dto.getValidTo());

	    vo.setNewValidTo(dto.getNewValidTo());

	    vo.setRevisionNo(dto.getRevisionNo());

	    vo.setFreightType(dto.getFreightType());

	    vo.setPackingType(dto.getPackingType());

	    vo.setInsuranceAmount(dto.getInsuranceAmount());

	    vo.setModeOfDespatch(dto.getModeOfDespatch());

	    vo.setTaxDescription(dto.getTaxDescription());

	    vo.setRemarks(dto.getRemarks());

	    vo.setOrgId(dto.getOrgId());

	    vo.setFinancialYear(dto.getFinancialYear());

	    vo.setActive(dto.isActive());

	    vo.setCancelRemarks(dto.getCancelRemarks());

	    // ============================================================
	    // Item Details
	    // ============================================================

	    List<SupplierRateContractAmendmentItemDetailsVO>
	            itemDetailsList = new ArrayList<>();

	    if (dto.getItemDetails() != null) {

	        for (SupplierRateContractAmendmentItemDetailsDTO itemDTO :
	                dto.getItemDetails()) {

	            SupplierRateContractAmendmentItemDetailsVO
	                    itemDetails =
	                    new SupplierRateContractAmendmentItemDetailsVO();

	            // ====================================================
	            // Item
	            // ====================================================

	            if (itemDTO.getItem() != null) {

	                ItemMasterVO item =
	                        itemMasterRepo
	                                .findById(itemDTO.getItem())
	                                .orElseThrow(() ->
	                                        new ApplicationException(
	                                                "Item Not Found"));

	                itemDetails.setItem(item);
	            }

	            // ====================================================
	            // Unit
	            // ====================================================

	            if (itemDTO.getUnit() != null) {

	                UnitMasterVO unit =
	                        unitMasterRepo
	                                .findById(itemDTO.getUnit())
	                                .orElseThrow(() ->
	                                        new ApplicationException(
	                                                "Unit Not Found"));

	                itemDetails.setUnit(unit);
	            }

	            // ====================================================
	            // Rate Details
	            // ====================================================

	            itemDetails.setOldRate(
	                    itemDTO.getOldRate());

	            itemDetails.setNewRate(
	                    itemDTO.getNewRate());

	            // ====================================================
	            // Header Reference
	            // ====================================================

	            itemDetails.setSupplierRateContractAmendmentVO(vo);

	            itemDetailsList.add(itemDetails);
	        }
	    }

	    vo.setItemDetails(itemDetailsList);
	}
	
	
	
	private SupplierRateContractAmendmentResponseDTO
	convertToSupplierRateContractAmendmentResponse(
	        SupplierRateContractAmendmentVO vo) {

	    SupplierRateContractAmendmentResponseDTO response =
	            new SupplierRateContractAmendmentResponseDTO();

	    // ============================================================
	    // Header Details
	    // ============================================================

	    response.setId(vo.getId());
	    response.setDocId(vo.getDocId());
	    response.setDocDate(vo.getDocDate());
	    response.setBelongsTo(vo.getBelongsTo());
	    response.setContractDate(vo.getContractDate());
	    response.setContractNo(vo.getContractNo());
	    response.setValidFrom(vo.getValidFrom());
	    response.setNewValidFrom(vo.getNewValidFrom());
	    response.setValidTo(vo.getValidTo());
	    response.setNewValidTo(vo.getNewValidTo());
	    response.setRevisionNo(vo.getRevisionNo());
	    response.setFreightType(vo.getFreightType());
	    response.setPackingType(vo.getPackingType());
	    response.setInsuranceAmount(vo.getInsuranceAmount());
	    response.setModeOfDespatch(vo.getModeOfDespatch());
	    response.setTaxDescription(vo.getTaxDescription());
	    response.setRemarks(vo.getRemarks());
	    response.setOrgId(vo.getOrgId());
	    response.setFinancialYear(vo.getFinancialYear());
	    response.setCreatedBy(vo.getCreatedBy());
	    response.setUpdatedBy(vo.getUpdatedBy());
	    response.setCancelRemarks(vo.getCancelRemarks());
	    response.setActive(vo.getActive());
	    response.setCancel(vo.getCancel());
	    response.setScreenCode(vo.getScreenCode());
	    response.setScreenName(vo.getScreenName());

	    // ============================================================
	    // Branch
	    // ============================================================

	    if (vo.getBranch() != null) {

	        BranchResponseDTO branch =
	                new BranchResponseDTO();

	        branch.setId(
	                vo.getBranch().getId());

	        branch.setBranchCode(
	                vo.getBranch().getBranchCode());

	        branch.setBranchName(
	                vo.getBranch().getBranchName());

	        response.setBranch(branch);
	    }

	    // ============================================================
	    // Customer
	    // ============================================================

	    if (vo.getCustomer() != null) {

	        CustomerDropdownResponseDTO customer =
	                new CustomerDropdownResponseDTO();

	        customer.setCustomerId(
	                vo.getCustomer().getId());

	        customer.setCustomerCode(
	                vo.getCustomer().getCustomerCode());

	        customer.setCustomerName(
	                vo.getCustomer().getCustomerName());

	        response.setCustomer(customer);
	    }

	    // ============================================================
	    // Prepared By
	    // ============================================================

	    if (vo.getPreparedBy() != null) {

	        EmployeeResponseDTO employee =
	                new EmployeeResponseDTO();

	        employee.setId(
	                vo.getPreparedBy().getId());

	        employee.setEmployeeName(
	                vo.getPreparedBy().getEmployeeName());

	        response.setPreparedBy(employee);
	    }

	    // ============================================================
	    // Authorised By
	    // ============================================================

	    if (vo.getAuthorisedBy() != null) {

	        EmployeeResponseDTO employee =
	                new EmployeeResponseDTO();

	        employee.setId(
	                vo.getAuthorisedBy().getId());

	        employee.setEmployeeName(
	                vo.getAuthorisedBy().getEmployeeName());

	        response.setAuthorisedBy(employee);
	    }

	    // ============================================================
	    // Item Details
	    // ============================================================

	    List<SupplierRateContractAmendmentItemDetailsResponseDTO>
	            itemDetailsResponse = new ArrayList<>();

	    if (vo.getItemDetails() != null) {

	        for (SupplierRateContractAmendmentItemDetailsVO itemVO :
	                vo.getItemDetails()) {

	            SupplierRateContractAmendmentItemDetailsResponseDTO
	                    itemResponse =
	                    new SupplierRateContractAmendmentItemDetailsResponseDTO();

	            itemResponse.setId(
	                    itemVO.getId());

	            // ====================================================
	            // Item
	            // ====================================================

	            if (itemVO.getItem() != null) {

	                ItemMasterResponseDetailsDTO item =
	                        new ItemMasterResponseDetailsDTO();

	                item.setId(
	                        itemVO.getItem().getId());

	                item.setItemCode(
	                        itemVO.getItem().getItemCode());

	                item.setItemDescription(
	                        itemVO.getItem().getItemDescription());

	                // =================================================
	                // Primary Unit
	                // =================================================

	                if (itemVO.getItem().getPrimaryUnit() != null) {

	                    UnitMasterResponseDTO unit =
	                            new UnitMasterResponseDTO();

	                    unit.setId(
	                            itemVO.getItem()
	                                    .getPrimaryUnit()
	                                    .getId());

	                    unit.setUnitId(
	                            itemVO.getItem()
	                                    .getPrimaryUnit()
	                                    .getDescription());

	                    item.setUnit(unit);
	                }

	                itemResponse.setItemCode(item);
	            }

	            // ====================================================
	            // Unit
	            // ====================================================

	            if (itemVO.getUnit() != null) {

	                UnitMasterResponseDTO unit =
	                        new UnitMasterResponseDTO();

	                unit.setId(
	                        itemVO.getUnit().getId());

	                unit.setUnitId(
	                        itemVO.getUnit().getDescription());

	                itemResponse.setUnit(unit);
	            }

	            // ====================================================
	            // Rate Details
	            // ====================================================

	            itemResponse.setOldRate(
	                    itemVO.getOldRate());

	            itemResponse.setNewRate(
	                    itemVO.getNewRate());

	            itemDetailsResponse.add(itemResponse);
	        }
	    }

	    response.setItemDetails(itemDetailsResponse);

	    return response;
	}
	
	
	
	
	
	
	
	
	
	
	@Override
	public SupplierRateContractAmendmentResponseDTO
	getSupplierRateContractAmendmentById(
	        Long id) throws ApplicationException {

	    SupplierRateContractAmendmentVO
	            supplierRateContractAmendmentVO =
	            supplierRateContractAmendmentRepo
	                    .findById(id)
	                    .orElseThrow(() ->
	                            new ApplicationException(
	                                    "Supplier Rate Contract Amendment Not Found"));

	    return convertToSupplierRateContractAmendmentResponse(
	            supplierRateContractAmendmentVO);
	}
	
	@Override
	public List<SupplierRateContractAmendmentResponseDTO>
	getSupplierRateContractAmendmentByOrgIdAndBranch(
	        Long orgId,
	        Long branch) throws ApplicationException {

	    List<SupplierRateContractAmendmentVO>
	            supplierRateContractAmendments =
	            supplierRateContractAmendmentRepo
	                    .findByOrgIdAndBranch(
	                            orgId,
	                            branch);

	    List<SupplierRateContractAmendmentResponseDTO>
	            responseList = new ArrayList<>();

	    for (SupplierRateContractAmendmentVO vo :
	            supplierRateContractAmendments) {

	        responseList.add(
	                convertToSupplierRateContractAmendmentResponse(vo));
	    }

	    return responseList;
	}
	
	
	@Override
	public String getSupplierRateContractAmendmentDocId(
	        Long orgId,
	        String financialYear) {

	    String screenCode1 = "SRCA";

	    String result =
	            supplierRateContractAmendmentRepo
	                    .getSupplierRateContractAmendmentDocId(
	                            orgId,
	                            financialYear,
	                            screenCode1);

	    return result;
	}
	
	
	@Override
	public List<Map<String, Object>> getRevisionNoDetailsForSupplierRateContractAmd(
	        String contractNo,
	        Long orgId,
	        Long branch) {

	    List<Object[]> result =
	            supplierRateContractAmendmentRepo
	                    .getRevisionNoDetailsForSupplierRateContractAmd(
	                            contractNo,
	                            orgId,
	                            branch);

	    List<Map<String, Object>> details =
	            new ArrayList<>();

	    if (result == null || result.isEmpty()) {

	        Map<String, Object> part =
	                new HashMap<>();

	        part.put("newValidFrom", null);
	        part.put("newValidTo", null);
	        part.put("revisionNo", 1);

	        details.add(part);

	    } else {

	        for (Object[] fs : result) {

	            Map<String, Object> part =
	                    new HashMap<>();

	            part.put("newValidFrom",
	                    fs[0] != null ? fs[0] : null);

	            part.put("newValidTo",
	                    fs[1] != null ? fs[1] : null);

	            part.put("revisionNo",
	                    fs[2] != null ? fs[2] : 1);

	            details.add(part);
	        }
	    }

	    return details;
	}
	
	@Override
	public List<Map<String, Object>> getSupplierRateContractItemDetailsForSRCAmd(
	        String docId,
	        Long orgId,
	        Long branch) {

	    List<Object[]> result =
	            supplierRateContractAmendmentRepo
	                    .getSupplierRateContractItemDetailsForSRCAmd(
	                            docId,
	                            orgId,
	                            branch);

	    return getSupplierRateContractAmendmentItemDetails(result);
	}
	
	private List<Map<String, Object>> getSupplierRateContractAmendmentItemDetails(
	        List<Object[]> result) {

	    List<Map<String, Object>> details =
	            new ArrayList<>();

	    for (Object[] fs : result) {

	        Map<String, Object> part =
	                new HashMap<>();

	        part.put("id",
	                fs[0] != null ? fs[0] : null);

	        part.put("incomingItem",
	                fs[1] != null ? fs[1] : null);

	        part.put("itemCode",
	                fs[2] != null ? fs[2] : null);

	        part.put("itemDescription",
	                fs[3] != null ? fs[3] : null);

	        part.put("unit",
	                fs[4] != null ? fs[4] : null);

	        part.put("unitId",
	                fs[5] != null ? fs[5] : null);

	        part.put("description",
	                fs[6] != null ? fs[6] : null);

	        part.put("oldRate",
	                fs[7] != null ? fs[7] : null);

	        details.add(part);
	    }

	    return details;
	}
	
}
