package com.efitops.basesetup.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.ResponseDTO.CustomerDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.DepartmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.EmployeeDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.GSTStateMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.HsnResponseDTO;
import com.efitops.basesetup.ResponseDTO.ListOfValuesDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierRateContractItemDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierRateContractResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierRateContractTaxDetailsResponseDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;
import com.efitops.basesetup.dto.SupplierRateContractDTO;
import com.efitops.basesetup.dto.SupplierRateContractItemDetailsDTO;
import com.efitops.basesetup.dto.SupplierRateContractTaxDetailsDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.GSTStateMasterVO;
import com.efitops.basesetup.entity.HsnVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.ListOfValuesDetailsVO;
import com.efitops.basesetup.entity.SupplierRateContractItemDetailsVO;
import com.efitops.basesetup.entity.SupplierRateContractTaxDetailsVO;
import com.efitops.basesetup.entity.SupplierRateContractVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.DepartmentRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.EmployeeMasterRepo;
import com.efitops.basesetup.repository.GSTStateMasterRepo;
import com.efitops.basesetup.repository.HsnRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.ListOfValuesDetailsRepo;
import com.efitops.basesetup.repository.ServiceAccMasterRepo;
import com.efitops.basesetup.repository.SupplierRateContractItemDetailsRepo;
import com.efitops.basesetup.repository.SupplierRateContractRepo;
import com.efitops.basesetup.repository.SupplierRateContractTaxDetailsRepo;
import com.efitops.basesetup.repository.UnitMasterRepo;

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

		CustomerVO serviceName = customerRepo.findById(dto.getServiceName())
				.orElseThrow(() -> new ApplicationException("Service Name Not Found"));

		HsnVO hsnSacCode = hsnRepo.findById(dto.getHsnSacCode())
				.orElseThrow(() -> new ApplicationException("HSN/SAC Code Not Found"));

		ListOfValuesDetailsVO freightType = listOfValuesDetailsRepo.findById(dto.getFreightType())
				.orElseThrow(() -> new ApplicationException("Freight Type Not Found"));

		ListOfValuesDetailsVO packingType = listOfValuesDetailsRepo.findById(dto.getPackingType())
				.orElseThrow(() -> new ApplicationException("Packing Type Not Found"));

		EmployeeMasterVO preparedBy = employeeMasterRepo.findById(dto.getPreparedBy())
				.orElseThrow(() -> new ApplicationException("Prepared By Employee Not Found"));

		EmployeeMasterVO authoriedBy = employeeMasterRepo.findById(dto.getAuthoriedBy())
				.orElseThrow(() -> new ApplicationException("Authoried By Employee Not Found"));

		// =========================================================
		// SET HEADER
		// =========================================================

		supplierRateContractVO.setBranch(branch);

		supplierRateContractVO.setDepartment(department);

		supplierRateContractVO.setDocId(supplierRateContractVO.getDocId());

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

		supplierRateContractVO.setHsnSacCode(hsnSacCode);

		supplierRateContractVO.setScrap(dto.isScrap());

		supplierRateContractVO.setTaxPercentage(dto.getTaxPercentage());

		supplierRateContractVO.setDiscount(dto.getDiscount());

		supplierRateContractVO.setPaymentsTerms(dto.getPaymentsTerms());

		supplierRateContractVO.setDeliveryTerms(dto.getDeliveryTerms());

		supplierRateContractVO.setFreight(dto.getFreight());

		supplierRateContractVO.setFreightType(freightType);

		supplierRateContractVO.setPackingType(packingType);

		supplierRateContractVO.setInsurance(dto.getInsurance());

		supplierRateContractVO.setModeOfDespatch(dto.getModeOfDespatch());

		supplierRateContractVO.setInlandCharge(dto.getInlandCharge());

		supplierRateContractVO.setPreparedBy(preparedBy);

		supplierRateContractVO.setAuthoriedBy(authoriedBy);

		supplierRateContractVO.setNarration(dto.getNarration());

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

			gstStateDTO.setGstSate(vo.getGstState().getStateName());

			gstStateDTO.setGstStateCode(vo.getGstState().getStateCode());

			dto.setGstState(gstStateDTO);
		}

		// ===================== SERVICE NAME MAPPING =====================

		if (vo.getServiceName() != null) {

			CustomerDropdownResponseDTO serviceDTO = new CustomerDropdownResponseDTO();

			serviceDTO.setCustomerId(vo.getServiceName().getId());

			serviceDTO.setCustomerCode(vo.getServiceName().getCustomerCode());

			serviceDTO.setCustomerName(vo.getServiceName().getCustomerName());

			serviceDTO.setAddress(vo.getServiceName().getAddress());

			serviceDTO.setGstState(
					vo.getCustomer().getGstState() != null ? vo.getCustomer().getGstState().getStateCode() : null);
			serviceDTO.setGstNo(vo.getServiceName().getGstNo());

			serviceDTO.setIgstApplicable(vo.getServiceName().isGstApplicable());

			serviceDTO.setGstType(vo.getServiceName().getGstType());

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

		if (vo.getFreightType() != null) {

			ListOfValuesDetailsResponseDTO freightTypeDTO = new ListOfValuesDetailsResponseDTO();

			freightTypeDTO.setId(vo.getFreightType().getId());

			freightTypeDTO.setCode(vo.getFreightType().getValueCode());

			freightTypeDTO.setDescription(vo.getFreightType().getValueDescription());

			dto.setFreightType(freightTypeDTO);
		}

		// ===================== PACKING TYPE MAPPING =====================

		if (vo.getPackingType() != null) {

			ListOfValuesDetailsResponseDTO packingTypeDTO = new ListOfValuesDetailsResponseDTO();

			packingTypeDTO.setId(vo.getPackingType().getId());

			packingTypeDTO.setCode(vo.getPackingType().getValueCode());

			packingTypeDTO.setDescription(vo.getPackingType().getValueDescription());

			dto.setPackingType(packingTypeDTO);
		}

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
	        String financialYear,
	        String screenCode) {

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

	        part.put("idem", fs[0] != null ? fs[0].toString() : null);
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

}
