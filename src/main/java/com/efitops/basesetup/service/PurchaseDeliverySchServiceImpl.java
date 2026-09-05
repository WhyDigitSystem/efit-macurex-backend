package com.efitops.basesetup.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.DepartmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.EmployeeDetailsDTO;
import com.efitops.basesetup.ResponseDTO.EmployeeDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.GSTRateMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.GSTStateResponseDTO;
import com.efitops.basesetup.ResponseDTO.GateInwardEntryResponseDTO;
import com.efitops.basesetup.ResponseDTO.InternalIndentDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.InternalIndentResponseDTO;
import com.efitops.basesetup.ResponseDTO.ItemResponse1DTO;
import com.efitops.basesetup.ResponseDTO.ListOfValuesDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.LocationMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.PhysicalStockReConcilationDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.PhysicalStockReConcilationResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseBillDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseBillResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseBillSupplierResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseBillTaxGridResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAttachmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseContractDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseContractResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseContractTaxDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseDeliveryScheduleDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseDeliveryScheduleLineResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseDeliveryScheduleResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.GateInwardEntryDTO;
import com.efitops.basesetup.dto.HsnResponseImageDTO;
import com.efitops.basesetup.dto.InternalIndentDTO;
import com.efitops.basesetup.dto.InternalIndentDetailsDTO;
import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;
import com.efitops.basesetup.dto.ListOfVlauesDetailsResponseDTO;
import com.efitops.basesetup.dto.PhysicalStockReConcilationDTO;
import com.efitops.basesetup.dto.PhysicalStockReConcilationDetailsDTO;
import com.efitops.basesetup.dto.PrimaryUnitImageDTO;
import com.efitops.basesetup.dto.PurchaseBillDTO;
import com.efitops.basesetup.dto.PurchaseBillDetailsDTO;
import com.efitops.basesetup.dto.PurchaseBillTaxGridDTO;
import com.efitops.basesetup.dto.PurchaseContractDTO;
import com.efitops.basesetup.dto.PurchaseContractDetailsDTO;
import com.efitops.basesetup.dto.PurchaseContractTaxDetailsDTO;
import com.efitops.basesetup.dto.PurchaseDeliveryScheduleDTO;
import com.efitops.basesetup.dto.PurchaseDeliveryScheduleDetailsDTO;
import com.efitops.basesetup.dto.PurchaseDeliveryScheduleLineDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CurrencyVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.GSTRateMasterVO;
import com.efitops.basesetup.entity.GSTStateMasterVO;
import com.efitops.basesetup.entity.GateInwardEntryVO;
import com.efitops.basesetup.entity.InternalIndentDetailsVO;
import com.efitops.basesetup.entity.InternalIndentVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.ListOfValuesDetailsVO;
import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.entity.PhysicalStockReConcilationDetailsVO;
import com.efitops.basesetup.entity.PhysicalStockReConcilationVO;
import com.efitops.basesetup.entity.PurchaseBillDetailsVO;
import com.efitops.basesetup.entity.PurchaseBillTaxGridVO;
import com.efitops.basesetup.entity.PurchaseBillVO;
import com.efitops.basesetup.entity.PurchaseContractAttachmentVO;
import com.efitops.basesetup.entity.PurchaseContractDetailsVO;
import com.efitops.basesetup.entity.PurchaseContractTaxDetailsVO;
import com.efitops.basesetup.entity.PurchaseContractVO;
import com.efitops.basesetup.entity.PurchaseDeliveryScheduleDetailsVO;
import com.efitops.basesetup.entity.PurchaseDeliveryScheduleLineVO;
import com.efitops.basesetup.entity.PurchaseDeliveryScheduleVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CurrencyRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.DepartmentRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.EmployeeMasterRepo;
import com.efitops.basesetup.repository.GSTStateMasterRepo;
import com.efitops.basesetup.repository.GateInwardEntryRepo;
import com.efitops.basesetup.repository.GstRateMasterRepo;
import com.efitops.basesetup.repository.InternalIndentDetailsRepo;
import com.efitops.basesetup.repository.InternalIndentRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.ListOfValuesDetailsRepo;
import com.efitops.basesetup.repository.LocationRepo;
import com.efitops.basesetup.repository.PhysicalStockReConcilationDetailsRepo;
import com.efitops.basesetup.repository.PhysicalStockReConcilationRepo;
import com.efitops.basesetup.repository.PurchaseBillDetailsRepo;
import com.efitops.basesetup.repository.PurchaseBillRepo;
import com.efitops.basesetup.repository.PurchaseBillTaxGridRepo;
import com.efitops.basesetup.repository.PurchaseContractRepo;
import com.efitops.basesetup.repository.PurchaseDeliveryScheduleDetailsRepo;
import com.efitops.basesetup.repository.PurchaseDeliveryScheduleLineRepo;
import com.efitops.basesetup.repository.PurchaseDeliveryScheduleRepo;
import com.efitops.basesetup.repository.UnitMasterRepo;

@Service
public class PurchaseDeliverySchServiceImpl implements PurchaseDeliverySchService {

	public static final Logger LOGGER = LoggerFactory.getLogger(PartyMasterServiceImpl.class);

	@Autowired
	PurchaseDeliveryScheduleRepo purchaseDeliveryScheduleRepo;

	@Autowired
	BranchRepo branchRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	PurchaseDeliveryScheduleDetailsRepo purchaseDeliveryScheduleDetailsRepo;

	@Autowired
	ItemMasterRepo itemRepo;

	@Autowired
	PurchaseDeliveryScheduleLineRepo purchaseDeliveryScheduleLineRepo;

	@Autowired
	GateInwardEntryRepo gateInwardEntryRepo;

	@Autowired
	PurchaseContractRepo purchaseContractRepo;

	@Autowired
	CurrencyRepo currencyRepo;

	@Autowired
	DepartmentRepo departmentRepo;

	@Autowired
	GSTStateMasterRepo gSTStateMasterRepo;

	@Autowired
	UnitMasterRepo unitMasterRepo;

	@Autowired
	PurchaseBillRepo purchaseBillRepo;

	@Autowired
	ListOfValuesDetailsRepo listOfValuesDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	EmployeeMasterRepo employeeRepo;

	@Autowired
	GstRateMasterRepo gstRateMasterRepo;

	@Autowired
	PurchaseBillDetailsRepo purchaseBillDetailsRepo;

	@Autowired
	PurchaseBillTaxGridRepo purchaseBillTaxGridRepo;

	@Autowired
	InternalIndentRepo internalIndentRepo;

	@Autowired
	InternalIndentDetailsRepo internalIndentDetailsRepo;

	@Autowired
	PhysicalStockReConcilationRepo physicalStockReConcilationRepo;

	@Autowired
	PhysicalStockReConcilationDetailsRepo physicalStockReConcilationDetailsRepo;

	@Autowired
	LocationRepo locationRepo;

	@Override
	@Transactional
	public Map<String, Object> updateCreatePurchaseDeliverySchedule(
			PurchaseDeliveryScheduleDTO purchaseDeliveryScheduleDTO) throws ApplicationException {
		String screenCode = "PDS";
		PurchaseDeliveryScheduleVO purchaseDeliveryScheduleVO = new PurchaseDeliveryScheduleVO();

		String message;

		if (ObjectUtils.isNotEmpty(purchaseDeliveryScheduleDTO.getId())) {

			purchaseDeliveryScheduleVO = purchaseDeliveryScheduleRepo.findById(purchaseDeliveryScheduleDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Purchase Delivery Schedule Details"));

			purchaseDeliveryScheduleVO.setUpdatedBy(purchaseDeliveryScheduleDTO.getCreatedBy());

			message = "Purchase Delivery Schedule Updated Successfully";

		} else {

			String docId = purchaseDeliveryScheduleRepo.getPurchaseDeliveryScheduleDocId(
					purchaseDeliveryScheduleDTO.getOrgId(), purchaseDeliveryScheduleDTO.getFinancialYear(), screenCode);

			purchaseDeliveryScheduleVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(purchaseDeliveryScheduleDTO.getOrgId(),
							purchaseDeliveryScheduleDTO.getFinancialYear(), screenCode);

			if (documentTypeMappingDetailsVO == null) {
				throw new ApplicationException("Document Type Mapping Details Not Found");
			}

			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			purchaseDeliveryScheduleVO.setCreatedBy(purchaseDeliveryScheduleDTO.getCreatedBy());

			purchaseDeliveryScheduleVO.setUpdatedBy(purchaseDeliveryScheduleDTO.getCreatedBy());

			message = "Purchase Delivery Schedule Created Successfully";
		}

		createUpdatePurchaseDeliveryScheduleVO(purchaseDeliveryScheduleDTO, purchaseDeliveryScheduleVO);

		PurchaseDeliveryScheduleVO savedVO = purchaseDeliveryScheduleRepo.save(purchaseDeliveryScheduleVO);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("purchaseDeliveryScheduleVO", purchaseDeliveryScheduleResponse(savedVO));

		return response;
	}

	private void createUpdatePurchaseDeliveryScheduleVO(PurchaseDeliveryScheduleDTO dto,
			PurchaseDeliveryScheduleVO purchaseDeliveryScheduleVO) throws ApplicationException {

		purchaseDeliveryScheduleVO.setBelongsTo(dto.getBelongsTo());
		purchaseDeliveryScheduleVO.setScheduleStartDate(dto.getScheduleStartDate());
		purchaseDeliveryScheduleVO.setScheduleEndDate(dto.getScheduleEndDate());
		purchaseDeliveryScheduleVO.setNote(dto.getNote());
		purchaseDeliveryScheduleVO.setFinancialYear(dto.getFinancialYear());
		purchaseDeliveryScheduleVO.setOrgId(dto.getOrgId());

		purchaseDeliveryScheduleVO.setActive(dto.isActive());
		purchaseDeliveryScheduleVO.setCancelRemarks(dto.getCancelRemarks());

		// =========================
		// Branch Mapping
		// =========================

		if (dto.getBranch() != null && dto.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			purchaseDeliveryScheduleVO.setBranch(branch);
		}

		if (dto.getPreparedBy() != null && dto.getPreparedBy() != 0) {

			EmployeeMasterVO branch = employeeRepo.findById(dto.getPreparedBy())
					.orElseThrow(() -> new ApplicationException("Employee Not Found"));

			purchaseDeliveryScheduleVO.setPreparedBy(branch);
			;
		}

		// =========================
		// Supplier Mapping
		// =========================

		if (dto.getSupplier() != null && dto.getSupplier() != 0) {

			CustomerVO supplier = customerRepo.findById(dto.getSupplier())
					.orElseThrow(() -> new ApplicationException("Supplier Not Found"));

			purchaseDeliveryScheduleVO.setSupplier(supplier);
		}

		// =========================
		// Purchase Order Mapping
		// =========================

		purchaseDeliveryScheduleVO.setPurchaseOrderNo(dto.getPurchaseOrderNo());
		purchaseDeliveryScheduleVO.setPurchaseOrderDate(dto.getPurchaseOrderDate());

		// ======================================
		// Delete Existing Details During Update
		// ======================================

		if (dto.getId() != null) {

			List<PurchaseDeliveryScheduleDetailsVO> oldDetails = purchaseDeliveryScheduleDetailsRepo
					.findByPurchaseDeliveryScheduleVO(purchaseDeliveryScheduleVO);

			for (PurchaseDeliveryScheduleDetailsVO detailVO : oldDetails) {

				List<PurchaseDeliveryScheduleLineVO> oldLines = purchaseDeliveryScheduleLineRepo
						.findByPurchaseDeliveryScheduleDetailsVO(detailVO);

				purchaseDeliveryScheduleLineRepo.deleteAll(oldLines);
			}

			purchaseDeliveryScheduleDetailsRepo.deleteAll(oldDetails);
		}

		// ======================================
		// Child Save - Details
		// ======================================
		List<PurchaseDeliveryScheduleDetailsVO> detailsList = new ArrayList<>();

		if (dto.getScheduleDetails() != null && !dto.getScheduleDetails().isEmpty()) {

			for (PurchaseDeliveryScheduleDetailsDTO detailDTO : dto.getScheduleDetails()) {

				PurchaseDeliveryScheduleDetailsVO detailVO = new PurchaseDeliveryScheduleDetailsVO();

				// Item Mapping
				if (detailDTO.getItem() != null && detailDTO.getItem() != 0) {

					ItemMasterVO item = itemRepo.findById(detailDTO.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));

					detailVO.setItem(item);

					if (item.getPrimaryUnit() != null) {
						detailVO.setPrimaryUnit(item.getPrimaryUnit());
					}
					if (item.getPurchaseUnit() != null) {
						detailVO.setPurchaseUnit(item.getPurchaseUnit());
					}
				}

				detailVO.setDemandQty(detailDTO.getDemandQty());
				detailVO.setAvailableStock(detailDTO.getAvailableStock());
				detailVO.setQty(detailDTO.getQty());
				detailVO.setTentativeQty(detailDTO.getTentativeQty());
				detailVO.setTentativeQtyNextMonth(detailDTO.getTentativeQtyNextMonth());
				detailVO.setRate(detailDTO.getRate());

				// Parent Mapping
				detailVO.setPurchaseDeliveryScheduleVO(purchaseDeliveryScheduleVO);

				// ======================================
				// Child Save - Schedule Line
				// ======================================
				List<PurchaseDeliveryScheduleLineVO> lineList = new ArrayList<>();

				if (detailDTO.getSchedule() != null && !detailDTO.getSchedule().isEmpty()) {

					for (PurchaseDeliveryScheduleLineDTO lineDTO : detailDTO.getSchedule()) {

						PurchaseDeliveryScheduleLineVO lineVO = new PurchaseDeliveryScheduleLineVO();

						lineVO.setPlanDate(lineDTO.getPlanDate());
						lineVO.setWeekNo(lineDTO.getWeekNo());
						lineVO.setScheduleQty(lineDTO.getScheduleQty());

						// Parent Mapping
						lineVO.setPurchaseDeliveryScheduleDetailsVO(detailVO);

						lineList.add(lineVO);
					}
				}

				detailVO.setPurchaseDeliveryScheduleLineVO(lineList);

				detailsList.add(detailVO);
			}

			purchaseDeliveryScheduleVO.setPurchaseDeliveryScheduleDetailsVO(detailsList);
		}
	}

	private PurchaseDeliveryScheduleResponseDTO purchaseDeliveryScheduleResponse(
			PurchaseDeliveryScheduleVO purchaseDeliveryScheduleVO) {

		PurchaseDeliveryScheduleResponseDTO responseDTO = new PurchaseDeliveryScheduleResponseDTO();

		responseDTO.setId(purchaseDeliveryScheduleVO.getId());
		responseDTO.setBelongsTo(purchaseDeliveryScheduleVO.getBelongsTo());
		responseDTO.setDocId(purchaseDeliveryScheduleVO.getDocId());
		responseDTO.setDocDate(purchaseDeliveryScheduleVO.getDocDate());
		responseDTO.setScheduleStartDate(purchaseDeliveryScheduleVO.getScheduleStartDate());
		responseDTO.setScheduleEndDate(purchaseDeliveryScheduleVO.getScheduleEndDate());

		responseDTO.setNote(purchaseDeliveryScheduleVO.getNote());
		responseDTO.setPurchaseOrderNo(purchaseDeliveryScheduleVO.getPurchaseOrderNo());
		responseDTO.setPurchaseOrderDate(purchaseDeliveryScheduleVO.getPurchaseOrderDate());
		responseDTO.setOrgId(purchaseDeliveryScheduleVO.getOrgId());
		responseDTO.setFinancialYear(purchaseDeliveryScheduleVO.getFinancialYear());
		responseDTO.setActive(purchaseDeliveryScheduleVO.getActive());
		responseDTO.setCancelRemarks(purchaseDeliveryScheduleVO.getCancelRemarks());
		responseDTO.setCreatedBy(purchaseDeliveryScheduleVO.getCreatedBy());
		responseDTO.setCreatedBy(purchaseDeliveryScheduleVO.getUpdatedBy());

		// =========================
		// Branch Response
		// =========================

		if (purchaseDeliveryScheduleVO.getBranch() != null) {

			BranchResponseDTO branchDTO = new BranchResponseDTO();

			branchDTO.setId(purchaseDeliveryScheduleVO.getBranch().getId());
			branchDTO.setBranchName(purchaseDeliveryScheduleVO.getBranch().getBranchName());

			responseDTO.setBranch(branchDTO);
		}

		if (purchaseDeliveryScheduleVO.getPreparedBy() != null) {

			EmployeeDetailsDTO branchDTO = new EmployeeDetailsDTO();

			branchDTO.setId(purchaseDeliveryScheduleVO.getPreparedBy().getId());
			branchDTO.setName(purchaseDeliveryScheduleVO.getPreparedBy().getEmployeeName());

			responseDTO.setPreparedBy(branchDTO);
		}

		// =========================
		// Supplier Response
		// =========================

		if (purchaseDeliveryScheduleVO.getSupplier() != null) {

			SupplierResponseDTO supplierDTO = new SupplierResponseDTO();

			supplierDTO.setId(purchaseDeliveryScheduleVO.getSupplier().getId());
			supplierDTO.setSupplierCode(purchaseDeliveryScheduleVO.getSupplier().getCustomerCode());
			supplierDTO.setSupplierName(purchaseDeliveryScheduleVO.getSupplier().getCustomerName());
			responseDTO.setSupplier(supplierDTO);
		}

		// =========================
		// Details Response
		// =========================

		List<PurchaseDeliveryScheduleDetailsResponseDTO> detailResponseList = new ArrayList<>();

		if (purchaseDeliveryScheduleVO.getPurchaseDeliveryScheduleDetailsVO() != null
				&& !purchaseDeliveryScheduleVO.getPurchaseDeliveryScheduleDetailsVO().isEmpty()) {

			for (PurchaseDeliveryScheduleDetailsVO detailVO : purchaseDeliveryScheduleVO
					.getPurchaseDeliveryScheduleDetailsVO()) {

				PurchaseDeliveryScheduleDetailsResponseDTO detailDTO = new PurchaseDeliveryScheduleDetailsResponseDTO();

				detailDTO.setId(detailVO.getId());

				// Item Response

				if (detailVO.getItem() != null) {

					ItemMasterResponseDetailsDTO itemDTO = new ItemMasterResponseDetailsDTO();

					itemDTO.setId(detailVO.getItem().getId());
					itemDTO.setItemCode(detailVO.getItem().getItemCode());
					itemDTO.setItemDescription(detailVO.getItem().getItemDescription());

					detailDTO.setItem(itemDTO);
				}

				// Primary Unit Response

				if (detailVO.getPrimaryUnit() != null) {

					UnitMasterResponseDTO unitDTO = new UnitMasterResponseDTO();

					unitDTO.setId(detailVO.getPrimaryUnit().getId());
					unitDTO.setUnitId(detailVO.getPrimaryUnit().getUnitId());
					unitDTO.setUnitDescription(detailVO.getPrimaryUnit().getDescription());

					detailDTO.setPrimaryUnit(unitDTO);
				}
				// purchase Unit Response

				if (detailVO.getPurchaseUnit() != null) {
					UnitMasterResponseDTO unitDTO = new UnitMasterResponseDTO();

					unitDTO.setId(detailVO.getPrimaryUnit().getId());
					unitDTO.setUnitId(detailVO.getPrimaryUnit().getUnitId());
					unitDTO.setUnitDescription(detailVO.getPrimaryUnit().getDescription());

					detailDTO.setPurchaseUnit(unitDTO);
					;
				}

				detailDTO.setDemandQty(detailVO.getDemandQty());
				detailDTO.setAvailableStock(detailVO.getAvailableStock());
				detailDTO.setQty(detailVO.getQty());
				detailDTO.setTentativeQty(detailVO.getTentativeQty());
				detailDTO.setTentativeQtyNextMonth(detailVO.getTentativeQtyNextMonth());
				detailDTO.setRate(detailVO.getRate());

				// =========================
				// Schedule Response
				// =========================

				List<PurchaseDeliveryScheduleLineResponseDTO> lineResponseList = new ArrayList<>();

				if (detailVO.getPurchaseDeliveryScheduleLineVO() != null
						&& !detailVO.getPurchaseDeliveryScheduleLineVO().isEmpty()) {

					for (PurchaseDeliveryScheduleLineVO lineVO : detailVO.getPurchaseDeliveryScheduleLineVO()) {

						PurchaseDeliveryScheduleLineResponseDTO lineDTO = new PurchaseDeliveryScheduleLineResponseDTO();

						lineDTO.setPlanDate(lineVO.getPlanDate());
						lineDTO.setWeekNo(lineVO.getWeekNo());
						lineDTO.setScheduleQty(lineVO.getScheduleQty());

						lineResponseList.add(lineDTO);
					}
				}

				detailDTO.setSchedule(lineResponseList);

				detailResponseList.add(detailDTO);
			}
		}

		responseDTO.setScheduleDetails(detailResponseList);

		return responseDTO;
	}

	@Override
	public PurchaseDeliveryScheduleResponseDTO getPurchaseDeliveryScheduleById(Long id) throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {
			throw new ApplicationException("Invalid Id");
		}

		PurchaseDeliveryScheduleVO purchaseDeliveryScheduleVO = purchaseDeliveryScheduleRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Purchase Delivery Schedule Not Found"));

		return purchaseDeliveryScheduleResponse(purchaseDeliveryScheduleVO);
	}

	@Override
	public List<PurchaseDeliveryScheduleResponseDTO> getPurchaseDeliveryScheduleByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<PurchaseDeliveryScheduleVO> purchaseDeliveryScheduleList = purchaseDeliveryScheduleRepo
				.getPurchaseDeliveryScheduleByOrgId(orgId, branch);

		if (purchaseDeliveryScheduleList.isEmpty()) {
			throw new ApplicationException("No Purchase Delivery Schedule Details Found");
		}

		List<PurchaseDeliveryScheduleResponseDTO> responseList = new ArrayList<>();

		for (PurchaseDeliveryScheduleVO purchaseDeliveryScheduleVO : purchaseDeliveryScheduleList) {

			responseList.add(purchaseDeliveryScheduleResponse(purchaseDeliveryScheduleVO));
		}

		return responseList;
	}

//	dropdown api for supllier

	@Override
	public Map<String, Object> getSupplierDropdownForPurchaseDeliverySchedule(Long branch, Long orgId)
			throws ApplicationException {

		Map<String, Object> responseMap = new HashMap<>();

		List<Object[]> supplierList = customerRepo.getSupplierDropdownForPurchaseDeliverySchedule(branch, orgId);

		List<SupplierResponseDTO> responseDTOList = new ArrayList<>();

		for (Object[] obj : supplierList) {

			SupplierResponseDTO dto = new SupplierResponseDTO();

			dto.setId(obj[0] != null ? ((Number) obj[0]).longValue() : 0L);

			dto.setSupplierCode(obj[1] != null ? (String) obj[1] : "");

			dto.setSupplierName(obj[2] != null ? (String) obj[2] : "");

			responseDTOList.add(dto);
		}

		responseMap.put("message", "Supplier List Fetched Successfully");

		responseMap.put("supplierList", responseDTOList);

		return responseMap;
	}

//	item dropdown for purchasedeliveryschedule 
	@Override
	public Map<String, Object> getItemsForPurchaseDeliverySchedule(String purchasecontractnumber, Long customer,
			Long branch, Long orgId) throws ApplicationException {

		Map<String, Object> responseMap = new HashMap<>();

		List<Object[]> itemList = purchaseDeliveryScheduleRepo
				.getItemsForPurchaseDeliverySchedule(purchasecontractnumber, customer, branch, orgId);

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : itemList) {

			Map<String, Object> itemMap = new HashMap<>();

			itemMap.put("item", obj[0] != null ? obj[0].toString() : "");

			itemMap.put("supplier", obj[1] != null ? ((Number) obj[1]).longValue() : null);

			itemMap.put("primaryUnit", obj[2] != null ? obj[2].toString() : "");

			itemMap.put("purchaseUnit", obj[2] != null ? obj[2].toString() : "");

			itemMap.put("itemCode", obj[3] != null ? obj[3].toString() : "");

			itemMap.put("itemDesc", obj[4] != null ? obj[4].toString() : "");

			itemMap.put("itemId", obj[5] != null ? ((Number) obj[5]).longValue() : null);

			responseList.add(itemMap);
		}

		responseMap.put("message", "Item List Fetched Successfully");
		responseMap.put("itemList", responseList);

		return responseMap;
	}

//	Purchasecontractnumber dropdown
	@Override
	public Map<String, Object> getPurchaseOrderNumberForPurchaseDeliverySchedule(Long customer, LocalDate docdt,
			Long branch, Long orgId) throws ApplicationException {

		Map<String, Object> responseMap = new HashMap<>();

		List<Object[]> contractList = purchaseDeliveryScheduleRepo
				.getPurchaseOrderNumberForPurchaseDeliverySchedule(customer, docdt, branch, orgId);

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : contractList) {

			Map<String, Object> contractMap = new HashMap<>();

			contractMap.put("id", obj[0] != null ? ((Number) obj[0]).longValue() : null);

			contractMap.put("purchaseorderno", obj[1] != null ? obj[1].toString() : "");

			contractMap.put("docDate", obj[2] != null ? obj[2] : null);

			contractMap.put("supplier", obj[3] != null ? ((Number) obj[3]).longValue() : null);

			responseList.add(contractMap);
		}

		responseMap.put("message", "Purchase Contract List Fetched Successfully");
		responseMap.put("purchaseContractList", responseList);

		return responseMap;
	}

	// GateInwardEntry
	@Override
	@Transactional
	public Map<String, Object> updateCreateGateInwardEntry(GateInwardEntryDTO gateInwardEntryDTO)
			throws ApplicationException {

		GateInwardEntryVO gateInwardEntryVO = new GateInwardEntryVO();

		String message;

		if (ObjectUtils.isNotEmpty(gateInwardEntryDTO.getId())) {

			gateInwardEntryVO = gateInwardEntryRepo.findById(gateInwardEntryDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Gate Inward Entry"));

			gateInwardEntryVO.setUpdated_By(gateInwardEntryDTO.getCreatedBy());

			message = "Gate Inward Entry Updated Successfully";

		} else {

			gateInwardEntryVO.setCreatedBy(gateInwardEntryDTO.getCreatedBy());

			gateInwardEntryVO.setUpdated_By(gateInwardEntryDTO.getCreatedBy());

			message = "Gate Inward Entry Created Successfully";
		}

		createUpdateGateInwardEntryVO(gateInwardEntryDTO, gateInwardEntryVO);

		GateInwardEntryVO savedVO = gateInwardEntryRepo.save(gateInwardEntryVO);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("gateInwardEntryVO", gateInwardEntryResponse(savedVO));

		return response;
	}

	private void createUpdateGateInwardEntryVO(GateInwardEntryDTO dto, GateInwardEntryVO vo)
			throws ApplicationException {

		if (dto.getBranch() != null) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			vo.setBranch(branch);
		}

		if (dto.getCustomer() != null) {

			CustomerVO customer = customerRepo.findById(dto.getCustomer())
					.orElseThrow(() -> new ApplicationException("Customer Not Found"));

			vo.setCustomer(customer);
		}

		vo.setAddress(dto.getAddress());

		vo.setDocType(dto.getDocType());

		vo.setModvatCopyReceived(dto.getModvatCopyReceived());

		vo.setSupplierInvoiceNumber(dto.getSupplierInvoiceNumber());

		vo.setSupplierInvoiceDate(dto.getSupplierInvoiceDate());

		vo.setInvoiceNumber(dto.getInvoiceNumber());

		vo.setTimeOfEntry(dto.getTimeOfEntry());

		vo.setActive(dto.isActive());

		vo.setOrgId(dto.getOrgId());

		vo.setCancelRemarks(dto.getCancelRemarks());

	}

	private GateInwardEntryResponseDTO gateInwardEntryResponse(GateInwardEntryVO vo) {

		GateInwardEntryResponseDTO dto = new GateInwardEntryResponseDTO();

		dto.setId(vo.getId());

		// Branch
		if (vo.getBranch() != null) {

			BranchResponseDTO branchDTO = new BranchResponseDTO();

			branchDTO.setId(vo.getBranch().getId());
			branchDTO.setBranchName(vo.getBranch().getBranchName()); // change according to your entity
			branchDTO.setBranchCode(vo.getBranch().getBranchCode()); // if available

			dto.setBranch(branchDTO);
		}

		// Customer
		if (vo.getCustomer() != null) {

			CustomerResponseDetailsDTO customerDTO = new CustomerResponseDetailsDTO();

			customerDTO.setId(vo.getCustomer().getId());
			customerDTO.setCustomerCode(vo.getCustomer().getCustomerCode());
			customerDTO.setCustomerName(vo.getCustomer().getCustomerName());

			dto.setCustomer(customerDTO);
		}

		dto.setAddress(vo.getAddress());

		dto.setDocType(vo.getDocType());

		dto.setModvatCopyReceived(vo.getModvatCopyReceived());

		dto.setSupplierInvoiceNumber(vo.getSupplierInvoiceNumber());

		dto.setSupplierInvoiceDate(vo.getSupplierInvoiceDate());

		dto.setInvoiceNumber(vo.getInvoiceNumber());

		dto.setTimeOfEntry(vo.getTimeOfEntry());

		dto.setActive(vo.getActive());

		dto.setOrgId(vo.getOrgId());

		dto.setCreatedBy(vo.getCreatedBy());

		dto.setCancelRemarks(vo.getCancelRemarks());

		return dto;
	}

	@Override
	public GateInwardEntryResponseDTO getGateInwardEntryById(Long id) throws ApplicationException {

		GateInwardEntryVO gateInwardEntryVO = gateInwardEntryRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Gate Inward Entry Not Found"));

		return gateInwardEntryResponse(gateInwardEntryVO);
	}

	@Override
	public List<GateInwardEntryResponseDTO> getGateInwardEntryByOrgId(Long branch, Long orgId)
			throws ApplicationException {

		List<GateInwardEntryVO> voList = gateInwardEntryRepo.findByBranchIdAndOrgIdAndCancelFalse(branch, orgId);

		List<GateInwardEntryResponseDTO> responseList = new ArrayList<>();

		for (GateInwardEntryVO vo : voList) {

			responseList.add(gateInwardEntryResponse(vo));
		}

		return responseList;
	}

//customerName dropdown
	@Override
	public List<Map<String, Object>> getCustomerNameDropdownForGateInwardEntry(Long branch, Long orgId)
			throws ApplicationException {

		List<Object[]> result = gateInwardEntryRepo.getCustomerNameDropdownForGateInwardEntry(branch, orgId);

		return getCustomerNameDropdown(result);
	}

	private List<Map<String, Object>> getCustomerNameDropdown(List<Object[]> result) {

		List<Map<String, Object>> details = new ArrayList<>();

		for (Object[] obj : result) {

			Map<String, Object> customer = new HashMap<>();

			customer.put("customerName", obj[0] != null ? obj[0].toString() : "");

			customer.put("address", obj[1] != null ? obj[1].toString() : "");

			customer.put("customerId", obj[2] != null ? ((Number) obj[2]).longValue() : null);

			customer.put("customerCode", obj[3] != null ? obj[3].toString() : "");

			details.add(customer);
		}

		return details;
	}

	// Purchase Contract
	@Override
	@Transactional
	public Map<String, Object> updateCreatePurchaseContract(PurchaseContractDTO purchaseContractDTO,
			MultipartFile[] files) throws ApplicationException {

		PurchaseContractVO purchaseContractVO = new PurchaseContractVO();

		String screenCode = "PC";

		String message;

		if (ObjectUtils.isNotEmpty(purchaseContractDTO.getId())) {

			purchaseContractVO = purchaseContractRepo.findById(purchaseContractDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Purchase Contract"));

			purchaseContractVO.setUpdatedBy(purchaseContractDTO.getCreatedBy());

			message = "Purchase Contract Updated Successfully";

		} else {

			purchaseContractVO.setCreatedBy(purchaseContractDTO.getCreatedBy());

			purchaseContractVO.setUpdatedBy(purchaseContractDTO.getCreatedBy());

			message = "Purchase Contract Created Successfully";
		}

		createUpdatePurchaseContractVO(purchaseContractDTO, purchaseContractVO);

		PurchaseContractVO savedVO = purchaseContractRepo.save(purchaseContractVO);

		if (files != null && files.length > 0) {
			saveAttachments(files, savedVO);
		}

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("purchaseContractVO", purchaseContractResponse(savedVO));

		return response;
	}

	private void createUpdatePurchaseContractVO(PurchaseContractDTO dto, PurchaseContractVO vo)
			throws ApplicationException {

		if (dto.getBranch() != null) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			vo.setBranch(branch);
		}

		if (dto.getDepartment() != null) {

			DepartmentVO department = departmentRepo.findById(dto.getDepartment())
					.orElseThrow(() -> new ApplicationException("Department Not Found"));

			vo.setDepartment(department);
		}

		if (dto.getSupplier() != null) {

			CustomerVO supplier = customerRepo.findById(dto.getSupplier())
					.orElseThrow(() -> new ApplicationException("Supplier Not Found"));

			vo.setSupplier(supplier);
		}

		if (dto.getGstState() != null) {

			GSTStateMasterVO gstState = gSTStateMasterRepo.findById(dto.getGstState())
					.orElseThrow(() -> new ApplicationException("GST State Not Found"));

			vo.setGSTState(gstState);
		}

		if (dto.getCurrency() != null) {

			CurrencyVO currency = currencyRepo.findById(dto.getCurrency())
					.orElseThrow(() -> new ApplicationException("Currency Not Found"));

			vo.setCurrency(currency);
		}

		vo.setIGSTAppl(dto.isIGSTAppl());

		vo.setValidFrom(dto.getValidFrom());

		vo.setValidTo(dto.getValidTo());

		vo.setPurchaseOrderType(dto.getPurchaseOrderType());

		vo.setModeOfDespatch(dto.getModeOfDespatch());

		vo.setPaymentTerms(dto.getPaymentTerms());

		vo.setDelivery(dto.getDelivery());

		vo.setFreightType(dto.getFreightType());

		vo.setPackingType(dto.getPackingType());

		vo.setInsuranceAmount(dto.getInsuranceAmount());

		vo.setBank(dto.getBank());

		vo.setAccounts(dto.getAccounts());

		vo.setSwiftCode(dto.getSwiftCode());

		vo.setCheckedBy(dto.getCheckedBy());

		vo.setPreparedBy(dto.getPreparedBy());

		vo.setAuthorisedBy(dto.getAuthorisedBy());

		vo.setFreightForwarder(dto.getFreightForwarder());

		vo.setNotes(dto.getNotes());

		vo.setTermsConditions(dto.getTermsConditions());

		vo.setOrgId(dto.getOrgId());

		vo.setFinancialYear(dto.getFinancialYear());

		vo.setActive(dto.isActive());

		vo.setCancelRemarks(dto.getCancelRemarks());

		vo.getPurchaseContractDetailsVO().clear();

		if (dto.getDetails() != null) {

			for (PurchaseContractDetailsDTO detailDTO : dto.getDetails()) {

				PurchaseContractDetailsVO detailVO = new PurchaseContractDetailsVO();

				if (detailDTO.getItem() != null) {

					ItemMasterVO item = itemRepo.findById(detailDTO.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));

					detailVO.setItem(item);
				}

				if (detailDTO.getUnit() != null) {

					UnitMasterVO unit = unitMasterRepo.findById(detailDTO.getUnit())
							.orElseThrow(() -> new ApplicationException("Unit Not Found"));

					detailVO.setUnit(unit);
				}

				detailVO.setHscCode(detailDTO.getHsnCode());

				detailVO.setTaxType(detailDTO.getTaxType());

				detailVO.setTaxPercentage(detailDTO.getTaxPercentage());

				detailVO.setRateInCurrency(detailDTO.getRateInCurrency());

				detailVO.setSgstRate(detailDTO.getSgstRate());

				detailVO.setSgstAmount(detailDTO.getSgstAmount());

				detailVO.setCgstRate(detailDTO.getCgstRate());

				detailVO.setCgstAmount(detailDTO.getCgstAmount());

				detailVO.setIgstRate(detailDTO.getIgstRate());

				detailVO.setIgstAmount(detailDTO.getIgstAmount());

				detailVO.setValidFrom(detailDTO.getValidFrom());

				detailVO.setValidTo(detailDTO.getValidTo());

				// -------------------------------------------------
				// GST CALCULATION
				// -------------------------------------------------

				BigDecimal taxableAmount = detailDTO.getRateInCurrency() != null ? detailDTO.getRateInCurrency()
						: BigDecimal.ZERO;

				BigDecimal cgstRate = detailDTO.getCgstRate() != null ? detailDTO.getCgstRate() : BigDecimal.ZERO;

				BigDecimal sgstRate = detailDTO.getSgstRate() != null ? detailDTO.getSgstRate() : BigDecimal.ZERO;

				BigDecimal igstRate = detailDTO.getIgstRate() != null ? detailDTO.getIgstRate() : BigDecimal.ZERO;

				// =================================================
				// =================================================
				// IGST
				// =================================================
				if (dto.isIGSTAppl()) {

					BigDecimal igstAmount = taxableAmount.multiply(igstRate).divide(BigDecimal.valueOf(100));

					detailVO.setIgstRate(igstRate);
					detailVO.setIgstAmount(igstAmount);

					detailVO.setCgstRate(BigDecimal.ZERO);
					detailVO.setCgstAmount(BigDecimal.ZERO);

					detailVO.setSgstRate(BigDecimal.ZERO);
					detailVO.setSgstAmount(BigDecimal.ZERO);
				}
				// =================================================
				// CGST + SGST
				// =================================================
				else {

					BigDecimal cgstAmount = taxableAmount.multiply(cgstRate).divide(BigDecimal.valueOf(100));

					BigDecimal sgstAmount = taxableAmount.multiply(sgstRate).divide(BigDecimal.valueOf(100));

					detailVO.setCgstRate(cgstRate);
					detailVO.setCgstAmount(cgstAmount);

					detailVO.setSgstRate(sgstRate);
					detailVO.setSgstAmount(sgstAmount);

					detailVO.setIgstRate(BigDecimal.ZERO);
					detailVO.setIgstAmount(BigDecimal.ZERO);
				}
				detailVO.setPurchaseContractVO(vo);

				vo.getPurchaseContractDetailsVO().add(detailVO);
			}
		}
		vo.getPurchaseContractTaxDetailsVO().clear();

		if (dto.getTaxDetails() != null) {

			for (PurchaseContractTaxDetailsDTO taxDTO : dto.getTaxDetails()) {

				PurchaseContractTaxDetailsVO taxVO = new PurchaseContractTaxDetailsVO();

				taxVO.setParticulars(taxDTO.getParticulars());

				taxVO.setTaxPercent(taxDTO.getTaxPercent());

				taxVO.setAmount(taxDTO.getAmount());

				taxVO.setPurchaseContractVO(vo);

				vo.getPurchaseContractTaxDetailsVO().add(taxVO);
			}
		}
	}

	@Value("${purchasecontract.upload.path}")
	private String uploadPath;

	private void saveAttachments(MultipartFile[] files, PurchaseContractVO purchaseContractVO)
			throws ApplicationException {

		if (files == null || files.length == 0) {
			return;
		}

		try {

			File directory = new File(uploadPath);

			if (!directory.exists()) {
				directory.mkdirs();
			}

			for (MultipartFile file : files) {

				if (file.isEmpty()) {
					continue;
				}

				String originalName = file.getOriginalFilename();

				String extension = "";

				if (originalName != null && originalName.contains(".")) {
					extension = originalName.substring(originalName.lastIndexOf("."));
				}

				String fileName = UUID.randomUUID().toString() + extension;

				Path path = Paths.get(uploadPath, fileName);

				try (InputStream inputStream = file.getInputStream()) {

					Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);

				}

				PurchaseContractAttachmentVO attachmentVO = new PurchaseContractAttachmentVO();

				attachmentVO.setName(originalName);

				attachmentVO.setFileName(fileName);

				attachmentVO.setFilePath(path.toString());

				attachmentVO.setFileSize(file.getSize());

				attachmentVO.setUploadOn(LocalDateTime.now());

				attachmentVO.setPurchaseContractVO(purchaseContractVO);

				purchaseContractVO.getPurchaseContractAttachmentVO().add(attachmentVO);
			}

			purchaseContractRepo.save(purchaseContractVO);

		} catch (IOException e) {

			throw new ApplicationException("Unable to Save Attachment");

		}
	}

	private PurchaseContractResponseDTO purchaseContractResponse(PurchaseContractVO vo) {

		PurchaseContractResponseDTO dto = new PurchaseContractResponseDTO();

		dto.setId(vo.getId());

		if (vo.getBranch() != null) {

			BranchResponseDTO branchDTO = new BranchResponseDTO();

			branchDTO.setId(vo.getBranch().getId());
			branchDTO.setBranchCode(vo.getBranch().getBranchCode());
			branchDTO.setBranchName(vo.getBranch().getBranchName());

			dto.setBranch(branchDTO);
		}

		if (vo.getDepartment() != null) {

			DepartmentResponseDTO departmentDTO = new DepartmentResponseDTO();

			departmentDTO.setId(vo.getDepartment().getId());
			departmentDTO.setDepartmentCode(vo.getDepartment().getDepartmentCode());
			departmentDTO.setDepartmentName(vo.getDepartment().getDepartmentName());

			dto.setDepartment(departmentDTO);
		}

		if (vo.getSupplier() != null) {

			CustomerResponseDetailsDTO customerDTO = new CustomerResponseDetailsDTO();

			customerDTO.setId(vo.getSupplier().getId());
			customerDTO.setCustomerCode(vo.getSupplier().getCustomerCode());
			customerDTO.setCustomerName(vo.getSupplier().getCustomerName());

			dto.setSupplier(customerDTO);
		}

		if (vo.getGSTState() != null) {

			GSTStateResponseDTO gstDTO = new GSTStateResponseDTO();

			gstDTO.setId(vo.getGSTState().getId());
			gstDTO.setStateCode(vo.getGSTState().getStateCode());
			gstDTO.setStateName(vo.getGSTState().getStateName());

			dto.setGstState(gstDTO);
		}

		dto.setValidFrom(vo.getValidFrom());

		dto.setValidTo(vo.getValidTo());

		dto.setIgstAppl(vo.isIGSTAppl());

		dto.setPurchaseOrderType(vo.getPurchaseOrderType());

		dto.setModeOfDespatch(vo.getModeOfDespatch());

		dto.setPaymentTerms(vo.getPaymentTerms());

		dto.setDelivery(vo.getDelivery());

		dto.setFreightType(vo.getFreightType());

		dto.setPackingType(vo.getPackingType());

		dto.setInsuranceAmount(vo.getInsuranceAmount());

		dto.setBank(vo.getBank());

		dto.setAccounts(vo.getAccounts());

		dto.setSwiftCode(vo.getSwiftCode());

		dto.setCheckedBy(vo.getCheckedBy());

		dto.setPreparedBy(vo.getPreparedBy());

		dto.setAuthorisedBy(vo.getAuthorisedBy());

		dto.setFreightForwarder(vo.getFreightForwarder());

		dto.setNotes(vo.getNotes());

		dto.setTermsConditions(vo.getTermsConditions());

		dto.setOrgId(vo.getOrgId());

		dto.setFinancialYear(vo.getFinancialYear());

		dto.setActive(vo.getActive());

		dto.setCreatedBy(vo.getCreatedBy());

		dto.setCancelRemarks(vo.getCancelRemarks());

		List<PurchaseContractDetailsResponseDTO> detailsList = new ArrayList<>();

		if (vo.getPurchaseContractDetailsVO() != null) {

			for (PurchaseContractDetailsVO detailVO : vo.getPurchaseContractDetailsVO()) {

				PurchaseContractDetailsResponseDTO detailDTO = new PurchaseContractDetailsResponseDTO();
				if (detailVO.getItem() != null) {

					ItemMasterResponseDetailsDTO itemDTO = new ItemMasterResponseDetailsDTO();

					itemDTO.setId(detailVO.getItem().getId());
					itemDTO.setItemCode(detailVO.getItem().getItemCode());
					itemDTO.setItemDescription(detailVO.getItem().getItemDescription());

					detailDTO.setItemCode(itemDTO);

				}
				detailDTO.setHsnCode(detailVO.getHscCode());

				detailDTO.setTaxType(detailVO.getTaxType());

				detailDTO.setTaxPercentage(detailVO.getTaxPercentage());

				if (detailVO.getUnit() != null) {

					PrimaryUnitImageDTO unitDTO = new PrimaryUnitImageDTO();

					unitDTO.setId(detailVO.getUnit().getId());
					unitDTO.setUnitId(detailVO.getUnit().getUnitId());
					unitDTO.setUnitDescription(detailVO.getUnit().getDescription());

					detailDTO.setUnit(unitDTO);

				}

				detailDTO.setRateInCurrency(detailVO.getRateInCurrency());

				detailDTO.setSgstRate(detailVO.getSgstRate());

				detailDTO.setSgstAmount(detailVO.getSgstAmount());

				detailDTO.setCgstRate(detailVO.getCgstRate());

				detailDTO.setCgstAmount(detailVO.getCgstAmount());

				detailDTO.setIgstRate(detailVO.getIgstRate());

				detailDTO.setIgstAmount(detailVO.getIgstAmount());

				detailDTO.setValidFrom(detailVO.getValidFrom());

				detailDTO.setValidTo(detailVO.getValidTo());

				detailsList.add(detailDTO);
			}
		}

		dto.setDetails(detailsList);

		List<PurchaseContractTaxDetailsResponseDTO> taxList = new ArrayList<>();

		if (vo.getPurchaseContractTaxDetailsVO() != null) {

			for (PurchaseContractTaxDetailsVO taxVO : vo.getPurchaseContractTaxDetailsVO()) {

				PurchaseContractTaxDetailsResponseDTO taxDTO = new PurchaseContractTaxDetailsResponseDTO();

				taxDTO.setId(taxVO.getId());

				taxDTO.setParticulars(taxVO.getParticulars());

				taxDTO.setTaxPercent(taxVO.getTaxPercent());

				taxDTO.setAmount(taxVO.getAmount());

				taxList.add(taxDTO);
			}
		}

		dto.setTaxDetails(taxList);

		List<PurchaseContractAttachmentResponseDTO> attachmentList = new ArrayList<>();

		if (vo.getPurchaseContractAttachmentVO() != null) {

			for (PurchaseContractAttachmentVO attachmentVO : vo.getPurchaseContractAttachmentVO()) {

				PurchaseContractAttachmentResponseDTO attachmentDTO = new PurchaseContractAttachmentResponseDTO();

				attachmentDTO.setId(attachmentVO.getId());

				attachmentDTO.setName(attachmentVO.getName());

				attachmentDTO.setFileName(attachmentVO.getFileName());

				attachmentDTO.setFilePath(attachmentVO.getFilePath());

				attachmentList.add(attachmentDTO);
			}
		}

		dto.setAttachments(attachmentList);

		return dto;
	}

	@Override
	public PurchaseContractResponseDTO getPurchaseContractById(Long id) throws ApplicationException {

		PurchaseContractVO purchaseContractVO = purchaseContractRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Purchase Contract Not Found"));

		return purchaseContractResponse(purchaseContractVO);
	}

	@Override
	public List<PurchaseContractResponseDTO> getPurchaseContractByOrgId(Long branch, Long orgId)
			throws ApplicationException {

		List<PurchaseContractVO> voList = purchaseContractRepo.findByBranchIdAndOrgIdAndCancelFalse(branch, orgId);

		List<PurchaseContractResponseDTO> responseList = new ArrayList<>();

		for (PurchaseContractVO vo : voList) {

			responseList.add(purchaseContractResponse(vo));
		}

		return responseList;
	}

//	supplier dropdown for Purchase contract
	@Override
	public List<Map<String, Object>> getSupplierDropdownForPurchaseContract(Long branch, Long orgId) {

		List<Object[]> result = purchaseContractRepo.getSupplierDropdownForPurchaseContract(branch, orgId);

		return getSupplierDropdownForPurchaseContract(result);
	}

	private List<Map<String, Object>> getSupplierDropdownForPurchaseContract(List<Object[]> result) {

		List<Map<String, Object>> details = new ArrayList<>();

		for (Object[] obj : result) {

			Map<String, Object> supplier = new HashMap<>();

			supplier.put("supplierId", obj[0] != null ? ((Number) obj[0]).longValue() : null);

			supplier.put("supplierCode", obj[1] != null ? obj[1].toString() : "");

			supplier.put("supplierName", obj[2] != null ? obj[2].toString() : "");

			details.add(supplier);
		}

		return details;
	}
//	dropdown for authorizedby , checkedby and preparedby

	@Override
	public List<Map<String, Object>> getEmployeeDropdownPurchaseContract(Long branch, Long orgId) {

		List<Object[]> result = purchaseContractRepo.getEmployeeDropdownPurchaseContract(branch, orgId);

		return getEmployeeDropdownPurchaseContract(result);
	}

	private List<Map<String, Object>> getEmployeeDropdownPurchaseContract(List<Object[]> result) {

		List<Map<String, Object>> details = new ArrayList<>();

		for (Object[] obj : result) {

			Map<String, Object> employee = new HashMap<>();

			employee.put("employeeId", obj[0] != null ? ((Number) obj[0]).longValue() : null);

			employee.put("employeeCode", obj[1] != null ? obj[1].toString() : "");

			employee.put("employeeName", obj[2] != null ? obj[2].toString() : "");

			details.add(employee);
		}

		return details;
	}

//	dropdown for items
	@Override
	public Map<String, Object> getPurchaseContractItems(Long supplier, Long branch, Long orgId)
			throws ApplicationException {

		List<Object[]> result = purchaseContractRepo.getPurchaseContractItems(supplier, branch, orgId);

		Map<String, Object> response = new HashMap<>();
		response.put("itemList", getPurchaseContractItemDetails(result));

		return response;
	}

	private List<Map<String, Object>> getPurchaseContractItemDetails(List<Object[]> result) {

		List<Map<String, Object>> itemList = new ArrayList<>();

		for (Object[] obj : result) {

			Map<String, Object> item = new HashMap<>();

			item.put("id", obj[0] != null ? ((Number) obj[0]).longValue() : null);
			item.put("itemCode", obj[1] != null ? obj[1].toString() : null);
			item.put("itemDescription", obj[2] != null ? obj[2].toString() : null);

			item.put("unitId", obj[3] != null ? ((Number) obj[3]).longValue() : null);
			item.put("unitCode", obj[4] != null ? obj[4].toString() : null);

			item.put("hsnId", obj[5] != null ? ((Number) obj[5]).longValue() : null);
			item.put("hsnCode", obj[6] != null ? obj[6].toString() : null);
			item.put("hsnDescription", obj[7] != null ? obj[7].toString() : null);

			itemList.add(item);
		}

		return itemList;
	}

//	purchase bill 

	@Override
	@Transactional
	public Map<String, Object> createUpdatePurchaseBill(PurchaseBillDTO purchaseBillDTO) throws ApplicationException {

		PurchaseBillVO purchaseBillVO = new PurchaseBillVO();

		String message;

		// ======================================================
		// Update
		// ======================================================

		if (ObjectUtils.isNotEmpty(purchaseBillDTO.getId())) {

			purchaseBillVO = purchaseBillRepo.findById(purchaseBillDTO.getId())
					.orElseThrow(() -> new ApplicationException("Purchase Bill Not Found"));

			purchaseBillVO.setUpdatedBy(purchaseBillDTO.getUpdatedBy() != null ? purchaseBillDTO.getUpdatedBy()
					: purchaseBillDTO.getCreatedBy());

			message = "Purchase Bill Updated Successfully";

		} else {

			purchaseBillVO.setCreatedBy(purchaseBillDTO.getCreatedBy());

			purchaseBillVO.setUpdatedBy(purchaseBillDTO.getUpdatedBy() != null ? purchaseBillDTO.getUpdatedBy()
					: purchaseBillDTO.getCreatedBy());

			message = "Purchase Bill Created Successfully";
		}

		// ======================================================
		// Header Mapping
		// ======================================================

		createUpdatePurchaseBillVO(purchaseBillDTO, purchaseBillVO);

		// ======================================================
		// Save Header
		// ======================================================

		purchaseBillVO = purchaseBillRepo.save(purchaseBillVO);

		// ======================================================
		// Purchase Bill Details
		// ======================================================

		if (purchaseBillDTO.getPurchaseDetails() != null) {

			createUpdatePurchaseBillDetails(purchaseBillDTO.getPurchaseDetails(), purchaseBillVO);
		}

		// ======================================================
		// Purchase Bill Tax Details
		// ======================================================

		if (purchaseBillDTO.getTaxGrid() != null) {

			createUpdatePurchaseBillTaxDetails(purchaseBillDTO.getTaxGrid(), purchaseBillVO);
		}
		// ======================================================
		// Build Response
		// ======================================================

		PurchaseBillResponseDTO responseDTO = purchaseBillResponse(purchaseBillVO);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);
		response.put("purchaseBillVO", responseDTO);

		return response;
	}

	private void createUpdatePurchaseBillVO(PurchaseBillDTO dto, PurchaseBillVO vo) throws ApplicationException {

		// ======================================================
		// Basic Header
		// ======================================================

		vo.setBelongsTo(dto.getBelongsTo());

		vo.setDocDate(dto.getDocDate());

		vo.setGrnNo(dto.getGrnNo());

		vo.setGrnDate(dto.getGrnDate());

		vo.setExcisable(dto.getExcisable());

		vo.setVendorDcNo(dto.getVendorDcNo());

		vo.setExchangeRate(dto.getExchangeRate());

		vo.setPurchaseorderType(dto.getPurchaseorderType());

		vo.setIsReverseChrg(dto.getIsReverseChrg());

		vo.setVoucherPostingDate(dto.getVoucherPostingDate());

		vo.setDate(dto.getDate());

		vo.setDutyPerUnit(dto.getDutyPerUnit());

		vo.setModvatCopyReceived(dto.getModvatCopyReceived());

		vo.setSupplierDcInvNo(dto.getSupplierDcInvNo());

		vo.setSupplierDcInvDate(dto.getSupplierDcInvDate());

		// ======================================================
		// Charges Summary
		// ======================================================

		vo.setTotalFreight(dto.getTotalFreight());

		vo.setTotalQty(dto.getTotalQty());

		vo.setBasicValue(dto.getBasicValue());

		vo.setTotalAmount(dto.getTotalAmount());

		vo.setAmountInWords(dto.getAmountInWords());

		vo.setEntryTaxApplicable(dto.getEntryTaxApplicable());

		vo.setNarration(dto.getNarration());

		vo.setPaymentTerms(dto.getPaymentTerms());

		// ======================================================
		// Organization
		// ======================================================

		vo.setOrgId(dto.getOrgId());

		vo.setFinancialYear(dto.getFinancialYear());

		vo.setActive(dto.isActive());

		vo.setCancelRemarks(dto.getCancelRemarks());

		// ======================================================
		// Branch
		// ======================================================

		if (dto.getBranch() != null && dto.getBranch() > 0) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			vo.setBranch(branch);
		}

		// ======================================================
		// Supplier
		// ======================================================

		if (dto.getSupplier() != null && dto.getSupplier() > 0) {

			CustomerVO supplier = customerRepo.findById(dto.getSupplier())
					.orElseThrow(() -> new ApplicationException("Supplier Not Found"));

			vo.setSupplier(supplier);
		}

		// ======================================================
		// Dealer Type
		// ======================================================

		// ======================================================
		// Posting Category
		// ======================================================

//		if (dto.getPostingCategory() != null && dto.getPostingCategory() > 0) {
//
//			ListOfValuesDetailsVO postingCategory = listOfValuesDetailsRepo.findById(dto.getPostingCategory())
//					.orElseThrow(() -> new ApplicationException("Posting Category Not Found"));
//
//			vo.setPostingCategory(postingCategory);
//		}

		// ======================================================
		// ECC Type
		// ======================================================

//	    if (dto.getEccType() != null
//	            && dto.getEccType() > 0) {
//
//	        CustomerVO eccType =
//	                customerRepo
//	                        .findById(dto.getEccType())
//	                        .orElseThrow(() ->
//	                                new ApplicationException(
//	                                        "ECC Type Not Found"));
//
//	        vo.setEccType(eccType);
//	    }
	}

	private void createUpdatePurchaseBillDetails(List<PurchaseBillDetailsDTO> detailsDTOList,
			PurchaseBillVO purchaseBillVO) throws ApplicationException {

		if (detailsDTOList == null || detailsDTOList.isEmpty()) {
			return;
		}

		for (PurchaseBillDetailsDTO detailsDTO : detailsDTOList) {

			PurchaseBillDetailsVO detailsVO;

			// ======================================================
			// Update / Create
			// ======================================================

			if (detailsDTO.getId() != null) {

				detailsVO = purchaseBillDetailsRepo.findById(detailsDTO.getId())
						.orElseThrow(() -> new ApplicationException("Purchase Bill Details Not Found"));

			} else {

				detailsVO = new PurchaseBillDetailsVO();
			}

			// ======================================================
			// Basic Details
			// ======================================================

			detailsVO.setTaxType(detailsDTO.getTaxType());
			detailsVO.setTaxPercent(detailsDTO.getTaxPercent());
			detailsVO.setExciseToPost(detailsDTO.getExciseToPost());

			detailsVO.setChallanQty(detailsDTO.getChallanQty());
			detailsVO.setGrnReceivedQty(detailsDTO.getGrnReceivedQty());
			detailsVO.setAcceptedQty(detailsDTO.getAcceptedQty());
			detailsVO.setRejectedQty(detailsDTO.getRejectedQty());
			detailsVO.setShortageQty(detailsDTO.getShortageQty());

			detailsVO.setPurchaseorderRate(detailsDTO.getPurchaseorderRate());

			// ======================================================
			// Rate In Selected Currency
			// ======================================================

			BigDecimal rateInSelectedCurrency = detailsDTO.getRateInSelectedCurrency();

			if (rateInSelectedCurrency == null) {
				rateInSelectedCurrency = BigDecimal.ZERO;
			}

			detailsVO.setRateInSelectedCurrency(rateInSelectedCurrency);

			// ======================================================
			// Rate In INR
			//
			// Rate In INR = Rate In Selected Currency
			// * Exchange Rate
			// ======================================================

			BigDecimal exchangeRate = purchaseBillVO.getExchangeRate();

			if (exchangeRate == null) {
				exchangeRate = BigDecimal.ONE;
			}

			BigDecimal rateInInr = rateInSelectedCurrency.multiply(exchangeRate);

			detailsVO.setRateInInr(rateInInr);

			// ======================================================
			// Quantity
			// ======================================================

			BigDecimal acceptedQty = detailsDTO.getAcceptedQty();

			if (acceptedQty == null) {
				acceptedQty = BigDecimal.ZERO;
			}

			BigDecimal receivedQty = detailsDTO.getGrnReceivedQty();

			if (receivedQty == null) {
				receivedQty = BigDecimal.ZERO;
			}

			// ======================================================
			// Amount In Selected Currency
			//
			// Amount In Selected Currency
			// = Rate In Selected Currency * Accepted Qty
			// ======================================================

			BigDecimal amountInSelectedCurrency = rateInSelectedCurrency.multiply(acceptedQty);

			detailsVO.setAmountInSelectedCurrency(amountInSelectedCurrency);

			// ======================================================
			// Amount In INR
			//
			// Amount In INR
			// = Rate In INR * Accepted Qty
			// ======================================================

			BigDecimal amountInInr = rateInInr.multiply(acceptedQty);

			detailsVO.setAmountInInr(amountInInr);

			// ======================================================
			// Amount
			// ======================================================
			//
			// If your "amount" field represents the item amount,
			// use the selected-currency amount here.
			//
			// If your business logic uses INR amount for "amount",
			// change this to amountInInr.
			// ======================================================

			BigDecimal amount = detailsDTO.getAmount();

			if (amount == null) {
				amount = amountInSelectedCurrency;
			}

			detailsVO.setAmount(amount);

			// ======================================================
			// Apportioned Cost
			//
			// Apportioned Cost =
			// (Total Freight * Item Amount) / Total Item Amount
			// ======================================================

			BigDecimal totalFreight = purchaseBillVO.getTotalFreight();

			BigDecimal totalItemAmount = purchaseBillVO.getBasicValue();

			BigDecimal apportionedCost = BigDecimal.ZERO;

			if (totalFreight != null && amount != null && totalItemAmount != null
					&& totalItemAmount.compareTo(BigDecimal.ZERO) > 0) {

				apportionedCost = totalFreight.multiply(amount).divide(totalItemAmount, 2, RoundingMode.HALF_UP);
			}

			detailsVO.setApportionedCost(apportionedCost);

			// ======================================================
			// Landed Cost Rate
			//
			// Landed Cost Rate = Amount / Received Qty
			// ======================================================

			BigDecimal landedCostRate = BigDecimal.ZERO;

			if (amount != null && receivedQty.compareTo(BigDecimal.ZERO) > 0) {

				landedCostRate = amount.divide(receivedQty, 2, RoundingMode.HALF_UP);
			}

			detailsVO.setLandedCostRate(landedCostRate);

			// ======================================================
			// Additional Duty
			// ======================================================

			detailsVO.setAdditionalDuty(detailsDTO.getAdditionalDuty());

			// ======================================================
			// SGST
			// ======================================================

			detailsVO.setSgstRate(detailsDTO.getSgstRate());

			detailsVO.setSgstAmount(detailsDTO.getSgstAmount());

			// ======================================================
			// CGST
			// ======================================================

			detailsVO.setCgstRate(detailsDTO.getCgstRate());

			detailsVO.setCgstAmount(detailsDTO.getCgstAmount());

			// ======================================================
			// IGST
			// ======================================================

			detailsVO.setIgstRate(detailsDTO.getIgstRate());

			detailsVO.setIgstAmount(detailsDTO.getIgstAmount());

			// ======================================================
			// Item
			// ======================================================

			if (detailsDTO.getItem() != null && detailsDTO.getItem() > 0) {

				ItemMasterVO item = itemRepo.findById(detailsDTO.getItem())
						.orElseThrow(() -> new ApplicationException("Item Not Found"));

				detailsVO.setItem(item);
			}

			// ======================================================
			// GST Rate
			// ======================================================

			if (detailsDTO.getGstRate() != null && detailsDTO.getGstRate() > 0) {

				GSTRateMasterVO gstRate = gstRateMasterRepo.findById(detailsDTO.getGstRate())
						.orElseThrow(() -> new ApplicationException("GST Rate Not Found"));

				detailsVO.setGstRate(gstRate);
			}

			// ======================================================
			// HSN
			// ======================================================

			/*
			 * if (detailsDTO.getHsnCode() != null && detailsDTO.getHsnCode() > 0) {
			 * 
			 * HsnVO hsn = hsnRepo.findById(detailsDTO.getHsnCode()) .orElseThrow(() -> new
			 * ApplicationException( "HSN Code Not Found"));
			 * 
			 * detailsVO.setHsnCode(hsn); }
			 */

			// ======================================================
			// Unit
			// ======================================================

			if (detailsDTO.getUnit() != null && detailsDTO.getUnit() > 0) {

				UnitMasterVO unit = unitMasterRepo.findById(detailsDTO.getUnit())
						.orElseThrow(() -> new ApplicationException("Unit Not Found"));

				detailsVO.setUnit(unit);
			}

			// ======================================================
			// Parent
			// ======================================================

			detailsVO.setPurchaseBillVO(purchaseBillVO);

			// ======================================================
			// Add Details To Parent
			// ======================================================

			if (!purchaseBillVO.getPurchaseBillDetailsVO().contains(detailsVO)) {

				purchaseBillVO.getPurchaseBillDetailsVO().add(detailsVO);
			}
		}
	}

	private void createUpdatePurchaseBillTaxDetails(List<PurchaseBillTaxGridDTO> taxDTOList,
			PurchaseBillVO purchaseBillVO) throws ApplicationException {

		if (taxDTOList == null || taxDTOList.isEmpty()) {
			return;
		}

		for (PurchaseBillTaxGridDTO taxDTO : taxDTOList) {

			PurchaseBillTaxGridVO taxVO;

			// ======================================================
			// Update
			// ======================================================

			if (taxDTO.getId() != null) {

				taxVO = purchaseBillTaxGridRepo.findById(taxDTO.getId())
						.orElseThrow(() -> new ApplicationException("Purchase Bill Tax Details Not Found"));

			} else {

				// ==================================================
				// Create
				// ==================================================

				taxVO = new PurchaseBillTaxGridVO();
			}

			// ======================================================
			// Basic Tax Details
			// ======================================================

			taxVO.setParticulars(taxDTO.getParticulars());
			taxVO.setTaxPercent(taxDTO.getTaxPercent());
			taxVO.setAcceptedQtyAmount(taxDTO.getAcceptedQtyAmount());
			taxVO.setRevisedAmount(taxDTO.getRevisedAmount());

			taxVO.setDebitbCredit(taxDTO.getDebitCredit());

			taxVO.setDebitAmount(taxDTO.getDebitAmount());
			taxVO.setCreditAmount(taxDTO.getCreditAmount());

			taxVO.setPostToFinanceAc(taxDTO.getPostToFinanceAc());

			// ======================================================
			// Ledger Account
			// ======================================================

			if (taxDTO.getLedgerAccount() != null && taxDTO.getLedgerAccount() > 0) {

				ListOfValuesDetailsVO ledgerAccount = listOfValuesDetailsRepo.findById(taxDTO.getLedgerAccount())
						.orElseThrow(() -> new ApplicationException("Ledger Account Not Found"));

				taxVO.setLedgerAccount(ledgerAccount);
			}

			// ======================================================
			// Parent
			// ======================================================

			taxVO.setPurchaseBillVO(purchaseBillVO);

			// ======================================================
			// Add to Parent
			// ======================================================

			if (!purchaseBillVO.getPurchaseBillTaxGridVO().contains(taxVO)) {

				purchaseBillVO.getPurchaseBillTaxGridVO().add(taxVO);
			}
		}
	}

	private PurchaseBillResponseDTO purchaseBillResponse(PurchaseBillVO vo) {

		PurchaseBillResponseDTO dto = new PurchaseBillResponseDTO();

		// ======================================================
		// Basic Header
		// ======================================================

		dto.setId(vo.getId());

//	    dto.setDocId(vo.getDocId());

		dto.setBelongsTo(vo.getBelongsTo());

//	    dto.setDocDate(vo.getDocDate());

		dto.setGrnNo(vo.getGrnNo());

		dto.setGrnDate(vo.getGrnDate());

		dto.setExcisable(vo.getExcisable());

		dto.setVendorDcNo(vo.getVendorDcNo());

		dto.setExchangeRate(vo.getExchangeRate());

		dto.setPurchaseorderType(vo.getPurchaseorderType());

		dto.setPurchaseorderNo(vo.getPurchaseorderNumber());

		dto.setPurchaseorderDate(vo.getPurchaseorderDate());

		dto.setIsReverseChrg(vo.getIsReverseChrg());

		dto.setVoucherPostingDate(vo.getVoucherPostingDate());

		dto.setDate(vo.getDate());

		dto.setDutyPerUnit(vo.getDutyPerUnit());

		dto.setModvatCopyReceived(vo.getModvatCopyReceived());

		dto.setSupplierDcInvNo(vo.getSupplierDcInvNo());

		dto.setSupplierDcInvDate(vo.getSupplierDcInvDate());

		// ======================================================
		// Charges Summary
		// ======================================================

		dto.setTotalFreight(vo.getTotalFreight());

		dto.setTotalQty(vo.getTotalQty());

		dto.setBasicValue(vo.getBasicValue());

		dto.setTotalAmount(vo.getTotalAmount());

		dto.setAmountInWords(vo.getAmountInWords());

		dto.setEntryTaxApplicable(vo.getEntryTaxApplicable());

		dto.setNarration(vo.getNarration());

		dto.setPaymentTerms(vo.getPaymentTerms());

		// ======================================================
		// Organization / Audit
		// ======================================================

		dto.setOrgId(vo.getOrgId());

		dto.setFinancialYear(vo.getFinancialYear());

		dto.setActive(vo.getActive());

		dto.setCancelRemarks(vo.getCancelRemarks());

		if (vo.getCreatedBy() != null) {
			dto.setCreatedBy(vo.getCreatedBy());
		}

		if (vo.getUpdatedBy() != null) {
			dto.setUpdatedBy(vo.getUpdatedBy());
		}

		// ======================================================
		// Branch
		// ======================================================

		if (vo.getBranch() != null) {

			BranchResponseDTO branchDTO = new BranchResponseDTO();

			branchDTO.setId(vo.getBranch().getId());

			branchDTO.setBranchCode(vo.getBranch().getBranchCode());

			branchDTO.setBranchName(vo.getBranch().getBranchName());

			dto.setBranch(branchDTO);
		}

		// ======================================================
		// Supplier
		// ======================================================
		if (vo.getSupplier() != null) {

			PurchaseBillSupplierResponseDTO supplierDTO = new PurchaseBillSupplierResponseDTO();

			supplierDTO.setId(vo.getSupplier().getId());

			supplierDTO.setSupplierCode(vo.getSupplier().getCustomerCode());

			supplierDTO.setSupplierName(vo.getSupplier().getCustomerName());

			supplierDTO.setDealerType(vo.getSupplier().isRegistered());

			// GST State
			if (vo.getSupplier().getGstState() != null) {

				GSTStateResponseDTO gstStateDTO = new GSTStateResponseDTO();

				gstStateDTO.setId(vo.getSupplier().getGstState().getId());

				gstStateDTO.setStateCode(vo.getSupplier().getGstState().getStateCode());

				gstStateDTO.setStateName(vo.getSupplier().getGstState().getStateName());

				supplierDTO.setGstState(gstStateDTO);
			}

			// GST Number
			supplierDTO.setGstNNo(vo.getSupplier().getGstNo());

			// ECC Type
			supplierDTO.setEccType(vo.getSupplier().getEccType());

			dto.setSupplier(supplierDTO);
		}

//		if (vo.getPostingCategory() != null) {
//
//			ListOfVlauesDetailsResponseDTO postingCategoryDTO = new ListOfVlauesDetailsResponseDTO();
//
//			postingCategoryDTO.setId(vo.getPostingCategory().getId());
//
//			postingCategoryDTO.setValueCode(vo.getPostingCategory().getValueCode());
//
//			dto.setPostingCategory(postingCategoryDTO);
//		}

		// ======================================================
		// ECC Type
		// ======================================================

//	    if (vo.getEccType() != null) {
//
//	        ListOfVlauesDetailsResponseDTO eccTypeDTO =
//	                new ListOfVlauesDetailsResponseDTO();
//
//	        eccTypeDTO.setId(
//	                vo.getEccType().getId());
//
//	        eccTypeDTO.setValueCode(vo.getEccType().getValueCode());
//	        dto.setEccType(eccTypeDTO);
//	    }
		// ======================================================
		// Purchase Bill Details
		// ======================================================

		if (vo.getPurchaseBillDetailsVO() != null) {

			List<PurchaseBillDetailsResponseDTO> detailsList = new ArrayList<>();

			for (PurchaseBillDetailsVO detailsVO : vo.getPurchaseBillDetailsVO()) {

				PurchaseBillDetailsResponseDTO detailsDTO = purchaseBillDetailsResponse(detailsVO);

				detailsList.add(detailsDTO);
			}

			dto.setPurchaseDetails(detailsList);
		}

		// ======================================================
		// Purchase Bill Tax Grid
		// ======================================================

		if (vo.getPurchaseBillTaxGridVO() != null) {

			List<PurchaseBillTaxGridResponseDTO> taxList = new ArrayList<>();

			for (PurchaseBillTaxGridVO taxVO : vo.getPurchaseBillTaxGridVO()) {

				PurchaseBillTaxGridResponseDTO taxDTO = purchaseBillTaxGridResponse(taxVO);

				taxList.add(taxDTO);
			}

			dto.setTaxGrid(taxList);
		}

		return dto;

	}

	private PurchaseBillDetailsResponseDTO purchaseBillDetailsResponse(PurchaseBillDetailsVO vo) {

		PurchaseBillDetailsResponseDTO dto = new PurchaseBillDetailsResponseDTO();

		dto.setId(vo.getId());

		dto.setTaxType(vo.getTaxType());
		dto.setTaxPercent(vo.getTaxPercent());
//		dto.setTariffNo(vo.getTariffNo());
		dto.setExciseToPost(vo.getExciseToPost());
		dto.setChallanQty(vo.getChallanQty());

		dto.setGrnReceivedQty(vo.getGrnReceivedQty());
		dto.setAcceptedQty(vo.getAcceptedQty());
		dto.setRejectedQty(vo.getRejectedQty());
		dto.setShortageQty(vo.getShortageQty());

		dto.setPurchaseorderRate(vo.getPurchaseorderRate());
		dto.setRateInInr(vo.getRateInInr());
		dto.setRateInSelectedCurrency(vo.getRateInSelectedCurrency());

		dto.setApportionedCost(vo.getApportionedCost());
		dto.setLandedCostRate(vo.getLandedCostRate());

		dto.setAmount(vo.getAmount());
		dto.setAmountInSelectedCurrency(vo.getAmountInSelectedCurrency());

		dto.setAdditionalDuty(vo.getAdditionalDuty());
		dto.setAmountInInr(vo.getAmountInInr());

		dto.setSgstRate(vo.getSgstRate());
		dto.setSgstAmount(vo.getSgstAmount());

		dto.setCgstRate(vo.getCgstRate());
		dto.setCgstAmount(vo.getCgstAmount());

		dto.setIgstRate(vo.getIgstRate());
		dto.setIgstAmount(vo.getIgstAmount());

		// ======================================================
		// Item
		// ======================================================

		if (vo.getItem() != null) {

			ItemMasterResponseDetailsDTO itemDTO = new ItemMasterResponseDetailsDTO();

			itemDTO.setId(vo.getItem().getId());
			// Add the remaining Item fields based on your DTO

			dto.setItem(itemDTO);
		}

		// ======================================================
		// GST Rate
		// ======================================================

		if (vo.getGstRate() != null) {

			GSTRateMasterResponseDTO gstDTO = new GSTRateMasterResponseDTO();

			gstDTO.setId(vo.getGstRate().getId());

			// Add remaining GST fields if required

			dto.setGstRate(gstDTO);
		}

		// ======================================================
		// HSN
		// ======================================================

		if (vo.getHsnCode() != null) {

			HsnResponseImageDTO hsnDTO = new HsnResponseImageDTO();

			hsnDTO.setId(vo.getHsnCode().getId());

			// Add remaining HSN fields if required

			dto.setHsnCode(hsnDTO);
		}

		// ======================================================
		// Unit
		// ======================================================

		if (vo.getUnit() != null) {

			PrimaryUnitImageDTO unitDTO = new PrimaryUnitImageDTO();

			unitDTO.setId(vo.getUnit().getId());

			// Add remaining Unit fields if required

			dto.setUnit(unitDTO);
		}

		return dto;
	}

	private PurchaseBillTaxGridResponseDTO purchaseBillTaxGridResponse(PurchaseBillTaxGridVO vo) {

		PurchaseBillTaxGridResponseDTO dto = new PurchaseBillTaxGridResponseDTO();

		dto.setId(vo.getId());
		dto.setParticulars(vo.getParticulars());
		dto.setTaxPercent(vo.getTaxPercent());
		dto.setAcceptedQtyAmount(vo.getAcceptedQtyAmount());
		dto.setRevisedAmount(vo.getRevisedAmount());

		dto.setDebitCredit(vo.getDebitbCredit());

		dto.setDebitAmount(vo.getDebitAmount());
		dto.setCreditAmount(vo.getCreditAmount());

		dto.setPostToFinanceAc(vo.getPostToFinanceAc());

		// ======================================================
		// Ledger Account
		// ======================================================

		if (vo.getLedgerAccount() != null) {

			ListOfVlauesDetailsResponseDTO ledgerDTO = new ListOfVlauesDetailsResponseDTO();

			ledgerDTO.setId(vo.getLedgerAccount().getId());
			ledgerDTO.setValueCode(vo.getLedgerAccount().getValueCode());

			dto.setLedgerAccount(ledgerDTO);
		}

		return dto;
	}

//	Supplier dropdown for purchase bill
	@Override
	public Map<String, Object> getSuppliersForPurchaseBill(Long orgId, Long branch) throws ApplicationException {

		List<Object[]> supplierList = purchaseBillRepo.getSuppliersForPurchaseBill(orgId, branch);

		List<Map<String, Object>> responseList = new ArrayList<>();

		if (supplierList != null && !supplierList.isEmpty()) {

			for (Object[] obj : supplierList) {

				Map<String, Object> supplierMap = new HashMap<>();

				supplierMap.put("supplierName", obj[0] != null ? obj[0].toString() : null);

				supplierMap.put("supplierCode", obj[1] != null ? obj[1].toString() : null);

				supplierMap.put("supplierId", obj[2] != null ? ((Number) obj[2]).longValue() : null);

				supplierMap.put("eccType", obj[3] != null ? obj[3].toString() : null);

				supplierMap.put("isGstApplicable", obj[4] != null ? obj[4] : null);

				supplierMap.put("gstNo", obj[5] != null ? obj[5].toString() : null);

				supplierMap.put("gstType", obj[6] != null ? obj[6].toString() : null);

				supplierMap.put("gstStateId", obj[7] != null ? ((Number) obj[7]).longValue() : null);

				supplierMap.put("stateCode", obj[8] != null ? obj[8].toString() : null);

				supplierMap.put("stateName", obj[9] != null ? obj[9].toString() : null);

				supplierMap.put("isRegistered", obj[10] != null ? ((Boolean) obj[10]) : null);

				responseList.add(supplierMap);
			}
		}

		Map<String, Object> response = new HashMap<>();

		response.put("supplierList", responseList);

		return response;
	}

	@Override
	public PurchaseBillResponseDTO getPurchaseBillById(Long id) throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {
			throw new ApplicationException("Invalid Id");
		}

		PurchaseBillVO purchaseBillVO = purchaseBillRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Purchase Bill Not Found"));

		return purchaseBillResponse(purchaseBillVO);
	}

	@Override
	public List<PurchaseBillResponseDTO> getPurchaseBillByOrgId(Long orgId, Long branch) throws ApplicationException {

		List<PurchaseBillVO> purchaseBillList = purchaseBillRepo.getPurchaseBillByOrgId(orgId, branch);

		if (purchaseBillList.isEmpty()) {
			throw new ApplicationException("No Purchase Bill Details Found");
		}

		List<PurchaseBillResponseDTO> responseList = new ArrayList<>();

		for (PurchaseBillVO purchaseBillVO : purchaseBillList) {

			responseList.add(purchaseBillResponse(purchaseBillVO));
		}

		return responseList;
	}

	@Override
	public String getPurchaseDeliveryScheduleDocId(Long orgId, String financialYear) {
		String screenCode1 = "PDS";
		String result = purchaseDeliveryScheduleRepo.getPurchaseDeliveryScheduleDocId(orgId, financialYear,
				screenCode1);
		return result;
	}

//	 grnno dropdown
	@Override
	public Map<String, Object> getGrnNoDropdownforPurchaseBill(Long orgId, Long branch, Long supplier)
			throws ApplicationException {

		String methodName = "getGrnNoDropdownforPurchaseBill()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> response = new HashMap<>();

		try {

			List<Object[]> result = purchaseBillRepo.GrnNoDropdownforPurchaseBill(orgId, branch, supplier);

			List<Map<String, Object>> dropdownList = new ArrayList<>();

			for (Object[] obj : result) {

				Map<String, Object> data = new LinkedHashMap<>();

				data.put("id", obj[0] != null ? ((Number) obj[0]).longValue() : null);

				data.put("grnNo", obj[1] != null ? obj[1].toString() : null);

				data.put("grnDate", obj[2] != null ? obj[2] : null);

				data.put("currency", obj[3] != null ? ((Number) obj[3]).longValue() : null);

				data.put("exchangeRate", obj[4] != null ? obj[4] : null);

				data.put("poNo", obj[5] != null ? obj[5].toString() : null);

				data.put("vendorDcNo", obj[6] != null ? obj[6].toString() : null);

				data.put("vendorDcDate", obj[7] != null ? obj[7] : null);

				data.put("poType", obj[8] != null ? obj[8].toString() : null);

				if (obj[9] != null) {

					if (obj[9] instanceof Boolean) {
						data.put("modvat", obj[9]);
					} else if (obj[9] instanceof Number) {
						data.put("modvat", ((Number) obj[9]).intValue() == 1);
					} else {
						data.put("modvat", obj[9]);
					}

				} else {
					data.put("modvat", null);
				}

				data.put("supplierDcInvNo", obj[10] != null ? obj[10].toString() : null);

				data.put("supplierDcInvDate", obj[11] != null ? obj[11] : null);

				dropdownList.add(data);
			}

			response.put("data", dropdownList);
			response.put("count", dropdownList.size());

			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

			return response;

		} catch (Exception e) {

			LOGGER.error(CommonConstant.EXCEPTION, methodName, e.getMessage(), e);

			throw new ApplicationException(CommonConstant.INTERNAL_SERVER_ERROR);
		}
	}

//	item dropdown for purchasebill
	@Override
	public Map<String, Object> getItemDropDownForPurchaseBill(Long orgId, Long branch, Long supplier, String grnNo)
			throws ApplicationException {

		String methodName = "getItemDropDownForPurchaseBill()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> response = new HashMap<>();

		try {

			List<Object[]> result = purchaseBillRepo.GetItemDropDownForPurchaseBill(orgId, branch, supplier, grnNo);

			List<Map<String, Object>> itemList = new ArrayList<>();

			for (Object[] obj : result) {

				Map<String, Object> data = new LinkedHashMap<>();

				// Item
				data.put("item", obj[0] != null ? ((Number) obj[0]).longValue() : null);

				data.put("itemdesc", obj[1] != null ? obj[1].toString() : null);

				// HSN
				Map<String, Object> hsnMap = new LinkedHashMap<>();

				hsnMap.put("id", obj[2] != null ? ((Number) obj[2]).longValue() : null);

				hsnMap.put("value", obj[3] != null ? obj[3].toString() : null);

				data.put("hsnCode", hsnMap);

				// Unit
				Map<String, Object> unitMap = new LinkedHashMap<>();

				unitMap.put("id", obj[4] != null ? ((Number) obj[4]).longValue() : null);

				unitMap.put("value", obj[5] != null ? obj[5].toString() : null);

				data.put("unit", unitMap);

				// Challan Quantity
				data.put("challan_qty", obj[6] != null ? obj[6] : null);

				// Received Quantity
				data.put("received_qty", obj[7] != null ? obj[7] : null);

				// Challan Quantity
				BigDecimal challanQty = obj[6] != null ? (BigDecimal) obj[6] : BigDecimal.ZERO;

				data.put("challan_qty", challanQty);

				// Received Quantity
				BigDecimal receivedQty = obj[7] != null ? (BigDecimal) obj[7] : BigDecimal.ZERO;

				data.put("received_qty", receivedQty);

				// Shortage Quantity
				BigDecimal shortageQty = challanQty.subtract(receivedQty);

				if (shortageQty.compareTo(BigDecimal.ZERO) < 0) {
					shortageQty = BigDecimal.ZERO;
				}

				data.put("shortage_qty", shortageQty);

				// Accepted Quantity
				data.put("accepted_qty", obj[8] != null ? obj[8] : null);

				// Rejected Quantity
				data.put("rejected_qty", obj[9] != null ? obj[9] : null);

				// PO Rate
				data.put("po_rate", obj[10] != null ? obj[10] : null);

				// GST Rate
				data.put("gst_rate", obj[11] != null ? obj[11] : null);

				// CGST
				data.put("cgst_rate", obj[12] != null ? obj[12] : null);

				data.put("cgst_amount", obj[13] != null ? obj[13] : null);

				// SGST
				data.put("sgst_rate", obj[14] != null ? obj[14] : null);

				data.put("sgst_amount", obj[15] != null ? obj[15] : null);

				// IGST
				data.put("igst_rate", obj[16] != null ? obj[16] : null);

				data.put("igst_amount", obj[17] != null ? obj[17] : null);

				// Tax Type
				data.put("tax_type", obj[18] != null ? obj[18].toString() : null);

				itemList.add(data);
			}

			response.put("data", itemList);
			response.put("count", itemList.size());

			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

			return response;

		} catch (Exception e) {

			LOGGER.error(CommonConstant.EXCEPTION, methodName, e.getMessage(), e);

			throw new ApplicationException(CommonConstant.INTERNAL_SERVER_ERROR);
		}
	}

//	internal indent
	@Override
	@Transactional
	public Map<String, Object> updateCreateInternalIndent(InternalIndentDTO internalIndentDTO)
			throws ApplicationException {

		String screenCode = "INTI";

		InternalIndentVO internalIndentVO = new InternalIndentVO();

		String message;

		if (ObjectUtils.isNotEmpty(internalIndentDTO.getId())) {

			internalIndentVO = internalIndentRepo.findById(internalIndentDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Internal Indent Details"));

			internalIndentVO.setUpdatedBy(internalIndentDTO.getCreatedBy());

			message = "Internal Indent Updated Successfully";

		} else {

			String docId = internalIndentRepo.getInternalIndentDocId(internalIndentDTO.getOrgId(),
					internalIndentDTO.getFinancialYear(), screenCode);

			internalIndentVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(internalIndentDTO.getOrgId(),
							internalIndentDTO.getFinancialYear(), screenCode);

			if (documentTypeMappingDetailsVO == null) {

				throw new ApplicationException("Document Type Mapping Details Not Found");
			}

			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			internalIndentVO.setCreatedBy(internalIndentDTO.getCreatedBy());

			internalIndentVO.setUpdatedBy(internalIndentDTO.getCreatedBy());

			message = "Internal Indent Created Successfully";
		}

		createUpdateInternalIndentVO(internalIndentDTO, internalIndentVO);

		InternalIndentVO savedVO = internalIndentRepo.save(internalIndentVO);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("internalIndentVO", internalIndentResponse(savedVO));

		return response;
	}

	private void createUpdateInternalIndentVO(InternalIndentDTO dto, InternalIndentVO internalIndentVO)
			throws ApplicationException {

		internalIndentVO.setBelongTo(dto.getBelongTo());

		internalIndentVO.setDocDate(dto.getDocDate());

		internalIndentVO.setTimeOfIndent(dto.getTimeOfIndent());

		internalIndentVO.setApprovedByPM(dto.getApprovedByPM());

		internalIndentVO.setRemarks(dto.getRemarks());

		internalIndentVO.setOrgId(dto.getOrgId());

		internalIndentVO.setFinancialYear(dto.getFinancialYear());

		internalIndentVO.setActive(dto.isActive());

		internalIndentVO.setCancelRemarks(dto.getCancelRemarks());

		// =========================
		// Branch Mapping
		// =========================

		if (dto.getBranch() != null && dto.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			internalIndentVO.setBranch(branch);
		}

		// =========================
		// Department Mapping
		// =========================

		if (dto.getDepartment() != null && dto.getDepartment() != 0) {

			DepartmentVO department = departmentRepo.findById(dto.getDepartment())
					.orElseThrow(() -> new ApplicationException("Department Not Found"));

			internalIndentVO.setDepartment(department);
		}

		// =========================
		// Prepared By Mapping
		// =========================

		if (dto.getPreparedBy() != null && dto.getPreparedBy() != 0) {

			EmployeeMasterVO preparedBy = employeeRepo.findById(dto.getPreparedBy())
					.orElseThrow(() -> new ApplicationException("Prepared By Not Found"));

			internalIndentVO.setPreparedBy(preparedBy);
		}

		// =========================
		// Authorized By Mapping
		// =========================

		if (dto.getAuthorizedBy() != null && dto.getAuthorizedBy() != 0) {

			EmployeeMasterVO authorizedBy = employeeRepo.findById(dto.getAuthorizedBy())
					.orElseThrow(() -> new ApplicationException("Authorized By Not Found"));

			internalIndentVO.setAuthorizedBy(authorizedBy);
		}

		// ======================================
		// Delete Existing Details During Update
		// ======================================

		if (dto.getId() != null) {

			List<InternalIndentDetailsVO> oldDetails = internalIndentDetailsRepo
					.findByInternalIndentVO(internalIndentVO);

			internalIndentDetailsRepo.deleteAll(oldDetails);
		}

		// ======================================
		// Child Save - Details
		// ======================================

		List<InternalIndentDetailsVO> detailsList = new ArrayList<>();

		if (dto.getInternalIndentDetailsDTO() != null && !dto.getInternalIndentDetailsDTO().isEmpty()) {

			for (InternalIndentDetailsDTO detailDTO : dto.getInternalIndentDetailsDTO()) {

				InternalIndentDetailsVO detailVO = new InternalIndentDetailsVO();

				// =========================
				// Item Mapping
				// =========================

				if (detailDTO.getItem() != null && detailDTO.getItem() != 0) {

					ItemMasterVO item = itemRepo.findById(detailDTO.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));

					detailVO.setItem(item);
				}

				detailVO.setRequiredQty(detailDTO.getRequiredQty());

				detailVO.setPurpose(detailDTO.getPurpose());

				// =========================
				// Parent Mapping
				// =========================

				detailVO.setInternalIndentVO(internalIndentVO);

				detailsList.add(detailVO);
			}

			internalIndentVO.setInternalIndentDetailsVO(detailsList);
		}

	}

	private InternalIndentResponseDTO internalIndentResponse(InternalIndentVO internalIndentVO) {

		InternalIndentResponseDTO responseDTO = new InternalIndentResponseDTO();

		responseDTO.setId(internalIndentVO.getId());

		responseDTO.setBelongTo(internalIndentVO.getBelongTo());

		responseDTO.setDocId(internalIndentVO.getDocId());

		responseDTO.setDocDate(internalIndentVO.getDocDate());

		responseDTO.setTimeOfIndent(internalIndentVO.getTimeOfIndent());

		responseDTO.setApprovedByPM(internalIndentVO.getApprovedByPM());

		responseDTO.setRemarks(internalIndentVO.getRemarks());

		responseDTO.setOrgId(internalIndentVO.getOrgId());

		responseDTO.setActive(internalIndentVO.getActive());

		responseDTO.setCancelRemarks(internalIndentVO.getCancelRemarks());

		responseDTO.setCreatedBy(internalIndentVO.getCreatedBy());

		// =========================
		// Branch Response
		// =========================

		if (internalIndentVO.getBranch() != null) {

			BranchResponseDTO branchDTO = new BranchResponseDTO();

			branchDTO.setId(internalIndentVO.getBranch().getId());

			branchDTO.setBranchName(internalIndentVO.getBranch().getBranchName());

			responseDTO.setBranch(branchDTO);
		}

		// =========================
		// Department Response
		// =========================

		if (internalIndentVO.getDepartment() != null) {

			DepartmentResponseDTO departmentDTO = new DepartmentResponseDTO();

			departmentDTO.setId(internalIndentVO.getDepartment().getId());

			departmentDTO.setDepartmentName(internalIndentVO.getDepartment().getDepartmentName());

			responseDTO.setDepartment(departmentDTO);
		}

		// =========================
		// Prepared By Response
		// =========================

		if (internalIndentVO.getPreparedBy() != null) {

			EmployeeDropdownResponseDTO preparedByDTO = new EmployeeDropdownResponseDTO();

			preparedByDTO.setEmployeeId(internalIndentVO.getPreparedBy().getId());

			preparedByDTO.setEmployeeName(internalIndentVO.getPreparedBy().getEmployeeName());

			responseDTO.setPreparedBy(preparedByDTO);
		}

		// =========================
		// Authorized By Response
		// =========================

		if (internalIndentVO.getAuthorizedBy() != null) {

			EmployeeDropdownResponseDTO authorizedByDTO = new EmployeeDropdownResponseDTO();

			authorizedByDTO.setEmployeeId(internalIndentVO.getAuthorizedBy().getId());

			authorizedByDTO.setEmployeeName(internalIndentVO.getAuthorizedBy().getEmployeeName());

			responseDTO.setAuthorizedBy(authorizedByDTO);
		}

		// =========================
		// Details Response
		// =========================

		List<InternalIndentDetailsResponseDTO> detailResponseList = new ArrayList<>();

		if (internalIndentVO.getInternalIndentDetailsVO() != null
				&& !internalIndentVO.getInternalIndentDetailsVO().isEmpty()) {

			for (InternalIndentDetailsVO detailVO : internalIndentVO.getInternalIndentDetailsVO()) {

				InternalIndentDetailsResponseDTO detailDTO = new InternalIndentDetailsResponseDTO();

				// =========================
				// Item Response
				// =========================

				if (detailVO.getItem() != null) {

					ItemResponse1DTO itemDTO = new ItemResponse1DTO();

					itemDTO.setId(detailVO.getItem().getId());

					itemDTO.setItemCode(detailVO.getItem().getItemCode());

					itemDTO.setItemDescription(detailVO.getItem().getItemDescription());

					detailDTO.setItem(itemDTO);
				}

				detailDTO.setRequiredQty(detailVO.getRequiredQty());

				detailDTO.setPurpose(detailVO.getPurpose());

				detailResponseList.add(detailDTO);
			}
		}

		responseDTO.setInternalIndentDetailsResponseDTO(detailResponseList);

		return responseDTO;
	}

	@Override
	public String getInternalIndentDocId(Long orgId, String financialYear) {

		String screenCode = "INTI";

		String result = internalIndentRepo.getInternalIndentDocId(orgId, financialYear, screenCode);

		return result;
	}

//	item dropdown for internal indent
	@Override
	public List<Map<String, Object>> getItemDropdownForInternalIndent(Long branch, Long orgId)
			throws ApplicationException {

		List<Object[]> itemList = internalIndentRepo.getItemDropdownForInternalIndent(branch, orgId);

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : itemList) {

			Map<String, Object> map = new HashMap<>();

			map.put("id", obj[0]);
			map.put("itemCode", obj[1]);
			map.put("itemDescription", obj[2]);
			map.put("unit", obj[3]);

			responseList.add(map);
		}

		return responseList;
	}

	@Override
	public InternalIndentResponseDTO getInternalIndentById(Long id) throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {

			throw new ApplicationException("Invalid Id");
		}

		InternalIndentVO internalIndentVO = internalIndentRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Internal Indent Not Found"));

		return internalIndentResponse(internalIndentVO);
	}

	@Override
	public List<InternalIndentResponseDTO> getInternalIndentByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<InternalIndentVO> internalIndentList = internalIndentRepo.getInternalIndentByOrgId(orgId, branch);

		if (internalIndentList.isEmpty()) {

			throw new ApplicationException("No Internal Indent Details Found");
		}

		List<InternalIndentResponseDTO> responseList = new ArrayList<>();

		for (InternalIndentVO internalIndentVO : internalIndentList) {

			responseList.add(internalIndentResponse(internalIndentVO));
		}

		return responseList;
	}

//	Physical Stock reconcilation 

	@Override
	@Transactional
	public Map<String, Object> updateCreatePhysicalStockReConcilation(
			PhysicalStockReConcilationDTO physicalStockReConcilationDTO) throws ApplicationException {

		String screenCode = "PSRC";

		PhysicalStockReConcilationVO physicalStockReConcilationVO = new PhysicalStockReConcilationVO();

		String message;

		if (ObjectUtils.isNotEmpty(physicalStockReConcilationDTO.getId())) {

			physicalStockReConcilationVO = physicalStockReConcilationRepo
					.findById(physicalStockReConcilationDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Physical Stock Reconciliation Details"));

			physicalStockReConcilationVO.setUpdatedBy(physicalStockReConcilationDTO.getCreatedBy());

			message = "Physical Stock Reconciliation Updated Successfully";

		} else {

			// =========================
			// Generate Document ID
			// =========================

			String docId = physicalStockReConcilationRepo.getPhysicalStockReConcilationDocId(
					physicalStockReConcilationDTO.getOrgId(), physicalStockReConcilationDTO.getFinancialYear(),
					screenCode);

			physicalStockReConcilationVO.setDocId(docId);

			// =========================
			// Document Type Mapping
			// =========================

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(physicalStockReConcilationDTO.getOrgId(),
							physicalStockReConcilationDTO.getFinancialYear(), screenCode);

			if (documentTypeMappingDetailsVO == null) {

				throw new ApplicationException("Document Type Mapping Details Not Found");
			}

			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			physicalStockReConcilationVO.setCreatedBy(physicalStockReConcilationDTO.getCreatedBy());

			physicalStockReConcilationVO.setUpdatedBy(physicalStockReConcilationDTO.getCreatedBy());

			message = "Physical Stock Reconciliation Created Successfully";
		}

		createUpdatePhysicalStockReConcilationVO(physicalStockReConcilationDTO, physicalStockReConcilationVO);

		PhysicalStockReConcilationVO savedVO = physicalStockReConcilationRepo.save(physicalStockReConcilationVO);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("physicalStockReConcilationVO", physicalStockReConcilationResponse(savedVO));

		return response;
	}

	private void createUpdatePhysicalStockReConcilationVO(PhysicalStockReConcilationDTO dto,
			PhysicalStockReConcilationVO physicalStockReConcilationVO) throws ApplicationException {

		// =========================
		// Basic Mapping
		// =========================

		if (dto.getBranch() != null && dto.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("branch  Not Found"));

			physicalStockReConcilationVO.setBranch(branch);
		}

//		physicalStockReConcilationVO.setDocDate(dto.getDocDate());

		physicalStockReConcilationVO.setTime(dto.getTime());

		physicalStockReConcilationVO.setRefNo(dto.getRefNo());

		physicalStockReConcilationVO.setRefDate(dto.getRefDate());

		physicalStockReConcilationVO.setBelongsTo(dto.getBelongsTo());

		physicalStockReConcilationVO.setNarration(dto.getNarration());

		physicalStockReConcilationVO.setApprovedByPM(dto.getApprovedByPM());

		physicalStockReConcilationVO.setOrgId(dto.getOrgId());

		physicalStockReConcilationVO.setActive(dto.isActive());

		physicalStockReConcilationVO.setCancelRemarks(dto.getCancelRemarks());

		physicalStockReConcilationVO.setScreenCode("PSRC");

		physicalStockReConcilationVO.setScreenName("PHYSICAL STOCK RECONCILATION ");

		// =========================
		// Location Type
		// =========================

		if (dto.getLocationType() != null && dto.getLocationType() != 0) {

			ListOfValuesDetailsVO locationType = listOfValuesDetailsRepo.findById(dto.getLocationType())
					.orElseThrow(() -> new ApplicationException("Location Type Not Found"));

			physicalStockReConcilationVO.setLocationType(locationType);
		}

		// =========================
		// Location
		// =========================

		if (dto.getLocation() != null && dto.getLocation() != 0) {

			LocationVO location = locationRepo.findById(dto.getLocation())
					.orElseThrow(() -> new ApplicationException("Location Not Found"));

			physicalStockReConcilationVO.setLocation(location);
		}

		// =========================
		// Prepared By
		// =========================

		if (dto.getPreparedBy() != null && dto.getPreparedBy() != 0) {

			EmployeeMasterVO preparedBy = employeeRepo.findById(dto.getPreparedBy())
					.orElseThrow(() -> new ApplicationException("Prepared By Not Found"));

			physicalStockReConcilationVO.setPreparedBy(preparedBy);
		}

		// ======================================
		// Delete Existing Details During Update
		// ======================================

		if (dto.getId() != null) {

			List<PhysicalStockReConcilationDetailsVO> oldDetails = physicalStockReConcilationDetailsRepo
					.findByPhysicalStockReConcilationVO(physicalStockReConcilationVO);

			physicalStockReConcilationDetailsRepo.deleteAll(oldDetails);
		}

		// =========================
		// Details Mapping
		// =========================

		List<PhysicalStockReConcilationDetailsVO> detailsList = new ArrayList<>();

		if (dto.getPhysicalStockReConcilationDetailsDTO() != null
				&& !dto.getPhysicalStockReConcilationDetailsDTO().isEmpty()) {

			for (PhysicalStockReConcilationDetailsDTO detailDTO : dto.getPhysicalStockReConcilationDetailsDTO()) {

				PhysicalStockReConcilationDetailsVO detailVO = new PhysicalStockReConcilationDetailsVO();

				// =========================
				// Item
				// =========================

				if (detailDTO.getItem() != null && detailDTO.getItem() != 0) {

					ItemMasterVO item = itemRepo.findById(detailDTO.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));

					detailVO.setItem(item);
				}

				detailVO.setBookStock(detailDTO.getBookStock());

				detailVO.setActualQty(detailDTO.getActualQty());

				BigDecimal difference = BigDecimal.ZERO;
				// difference
				if (detailDTO.getActualQty() != null && detailDTO.getBookStock() != null) {

					difference = detailDTO.getActualQty().subtract(detailDTO.getBookStock()).abs();
					detailVO.setDifference(difference);
				}

				detailVO.setLcRate(detailDTO.getLcRate());

				detailVO.setRate(detailDTO.getRate());

				detailVO.setReasonCode(detailDTO.getReasonCode());

				// Amount = Rate × Difference

				if (detailDTO.getRate() != null) {

					BigDecimal amount = detailDTO.getRate().multiply(difference).setScale(2, RoundingMode.HALF_UP);

					detailVO.setAmount(amount);
				}
				// =========================
				// Parent Mapping
				// =========================

				detailVO.setPhysicalStockReConcilationVO(physicalStockReConcilationVO);

				detailsList.add(detailVO);
			}

			physicalStockReConcilationVO.setPhysicalStockReConcilationDetailsVO(detailsList);
		}
	}

	private PhysicalStockReConcilationResponseDTO physicalStockReConcilationResponse(
			PhysicalStockReConcilationVO physicalStockReConcilationVO) {

		PhysicalStockReConcilationResponseDTO responseDTO = new PhysicalStockReConcilationResponseDTO();

		responseDTO.setId(physicalStockReConcilationVO.getId());

		responseDTO.setDocId(physicalStockReConcilationVO.getDocId());

		responseDTO.setDocDate(physicalStockReConcilationVO.getDocDate());

		responseDTO.setTime(physicalStockReConcilationVO.getTime());

		responseDTO.setRefNo(physicalStockReConcilationVO.getRefNo());

		responseDTO.setRefDate(physicalStockReConcilationVO.getRefDate());

		responseDTO.setBelongsTo(physicalStockReConcilationVO.getBelongsTo());

		responseDTO.setNarration(physicalStockReConcilationVO.getNarration());

		responseDTO.setApprovedByPM(physicalStockReConcilationVO.getApprovedByPM());

		responseDTO.setOrgId(physicalStockReConcilationVO.getOrgId());

		responseDTO.setActive(physicalStockReConcilationVO.getActive());

		responseDTO.setCancelRemarks(physicalStockReConcilationVO.getCancelRemarks());

		responseDTO.setCreatedBy(physicalStockReConcilationVO.getCreatedBy());

		// =========================
		// Branch Response
		// =========================

		if (physicalStockReConcilationVO.getBranch() != null) {

			BranchResponseDTO branchDTO = new BranchResponseDTO();

			branchDTO.setId(physicalStockReConcilationVO.getBranch().getId());

			branchDTO.setBranchName(physicalStockReConcilationVO.getBranch().getBranchName());

			responseDTO.setBranch(branchDTO);
		}

		// =========================
		// Location Type Response
		// =========================

		if (physicalStockReConcilationVO.getLocationType() != null) {

			ListOfValuesDetailsResponseDTO locationTypeDTO = new ListOfValuesDetailsResponseDTO();

			locationTypeDTO.setId(physicalStockReConcilationVO.getLocationType().getId());

			locationTypeDTO.setCode(physicalStockReConcilationVO.getLocationType().getValueCode());
			locationTypeDTO.setDescription(physicalStockReConcilationVO.getLocationType().getValueDescription());

			responseDTO.setLocationType(locationTypeDTO);
		}

		// =========================
		// Location Response
		// =========================

		if (physicalStockReConcilationVO.getLocation() != null) {

			LocationMasterResponseDTO locationDTO = new LocationMasterResponseDTO();

			locationDTO.setId(physicalStockReConcilationVO.getLocation().getId());

			locationDTO.setLocationName(physicalStockReConcilationVO.getLocation().getLocationName());

			responseDTO.setLocation(locationDTO);
		}

		// =========================
		// Prepared By Response
		// =========================

		if (physicalStockReConcilationVO.getPreparedBy() != null) {

			EmployeeDropdownResponseDTO preparedByDTO = new EmployeeDropdownResponseDTO();

			preparedByDTO.setEmployeeId(physicalStockReConcilationVO.getPreparedBy().getId());

			preparedByDTO.setEmployeeName(physicalStockReConcilationVO.getPreparedBy().getEmployeeName());

			responseDTO.setPreparedBy(preparedByDTO);
		}

		// =========================
		// Details Response
		// =========================

		List<PhysicalStockReConcilationDetailsResponseDTO> detailResponseList = new ArrayList<>();

		if (physicalStockReConcilationVO.getPhysicalStockReConcilationDetailsVO() != null
				&& !physicalStockReConcilationVO.getPhysicalStockReConcilationDetailsVO().isEmpty()) {

			for (PhysicalStockReConcilationDetailsVO detailVO : physicalStockReConcilationVO
					.getPhysicalStockReConcilationDetailsVO()) {

				PhysicalStockReConcilationDetailsResponseDTO detailDTO = new PhysicalStockReConcilationDetailsResponseDTO();

				// =========================
				// Item Response
				// =========================

				if (detailVO.getItem() != null) {

					ItemResponse1DTO itemDTO = new ItemResponse1DTO();

					itemDTO.setId(detailVO.getItem().getId());

					itemDTO.setItemCode(detailVO.getItem().getItemCode());

					itemDTO.setItemDescription(detailVO.getItem().getItemDescription());

					detailDTO.setItem(itemDTO);
				}

				detailDTO.setBookStock(detailVO.getBookStock());

				detailDTO.setActualQty(detailVO.getActualQty());

				detailDTO.setDifference(detailVO.getDifference());

				detailDTO.setLcRate(detailVO.getLcRate());

				detailDTO.setRate(detailVO.getRate());

				detailDTO.setReasonCode(detailVO.getReasonCode());

				detailDTO.setAmount(detailVO.getAmount());

				detailResponseList.add(detailDTO);
			}
		}

		responseDTO.setPhysicalStockReConcilationDetailsResponseDTO(detailResponseList);

		return responseDTO;
	}

	@Override
	public PhysicalStockReConcilationResponseDTO getPhysicalStockReConcilationById(Long id)
			throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {

			throw new ApplicationException("Invalid Id");
		}

		PhysicalStockReConcilationVO physicalStockReConcilationVO = physicalStockReConcilationRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Physical Stock Reconciliation Not Found"));

		return physicalStockReConcilationResponse(physicalStockReConcilationVO);
	}

	@Override
	public List<PhysicalStockReConcilationResponseDTO> getPhysicalStockReConcilationByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<PhysicalStockReConcilationVO> physicalStockReConcilationList = physicalStockReConcilationRepo
				.getPhysicalStockReConcilationByOrgId(orgId, branch);

		if (physicalStockReConcilationList.isEmpty()) {

			throw new ApplicationException("No Physical Stock Reconciliation Details Found");
		}

		List<PhysicalStockReConcilationResponseDTO> responseList = new ArrayList<>();

		for (PhysicalStockReConcilationVO physicalStockReConcilationVO : physicalStockReConcilationList) {

			responseList.add(physicalStockReConcilationResponse(physicalStockReConcilationVO));
		}

		return responseList;
	}

//	 location dropdown for physical stock reconcilation
	@Override
	public List<Map<String, Object>> getLocationDropdownForPhysicalStockReConcilation(Long locationType, Long branch,
			Long orgId) throws ApplicationException {

		List<Object[]> locationList = physicalStockReConcilationRepo
				.getLocationDropdownForPhysicalStockReConcilation(locationType, branch, orgId);

		if (locationList.isEmpty()) {

			throw new ApplicationException("No Location Details Found");
		}

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : locationList) {

			Map<String, Object> map = new HashMap<>();

			map.put("id", obj[0]);
			map.put("locationId", obj[1]);
			map.put("locationName", obj[2]);

			responseList.add(map);
		}

		return responseList;
	}

//	docid for physicalstockreconcilation
	@Override
	public String getPhysicalStockReConcilationDocId(Long orgId, String financialYear) {

		String screenCode = "PSRC";

		String result = physicalStockReConcilationRepo.getPhysicalStockReConcilationDocId(orgId, financialYear,
				screenCode);

		return result;
	}


}
