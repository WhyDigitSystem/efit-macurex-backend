package com.efitops.basesetup.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.efitops.basesetup.ResponseDTO.GSTStateResponseDTO;
import com.efitops.basesetup.ResponseDTO.GateInwardEntryResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseBillResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseBillSupplierResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAttachmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseContractDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseContractResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseContractTaxDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseDeliveryScheduleDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseDeliveryScheduleLineResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseDeliveryScheduleResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierResponseDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.GateInwardEntryDTO;
import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;
import com.efitops.basesetup.dto.ListOfVlauesDetailsResponseDTO;
import com.efitops.basesetup.dto.PrimaryUnitImageDTO;
import com.efitops.basesetup.dto.PurchaseBillDTO;
import com.efitops.basesetup.dto.PurchaseContractDTO;
import com.efitops.basesetup.dto.PurchaseContractDetailsDTO;
import com.efitops.basesetup.dto.PurchaseContractTaxDetailsDTO;
import com.efitops.basesetup.dto.PurchaseDeliveryScheduleDTO;
import com.efitops.basesetup.dto.PurchaseDeliveryScheduleDetailsDTO;
import com.efitops.basesetup.dto.PurchaseDeliveryScheduleLineDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CurrencyVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.GSTStateMasterVO;
import com.efitops.basesetup.entity.GateInwardEntryVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.ListOfValuesDetailsVO;
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
import com.efitops.basesetup.repository.GSTStateMasterRepo;
import com.efitops.basesetup.repository.GateInwardEntryRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.ListOfValuesDetailsRepo;
import com.efitops.basesetup.repository.PurchaseBillRepo;
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
	
	

	@Override
	@Transactional
	public Map<String, Object> updateCreatePurchaseDeliverySchedule(
			PurchaseDeliveryScheduleDTO purchaseDeliveryScheduleDTO) throws ApplicationException {

		PurchaseDeliveryScheduleVO purchaseDeliveryScheduleVO = new PurchaseDeliveryScheduleVO();

		String message;

		if (ObjectUtils.isNotEmpty(purchaseDeliveryScheduleDTO.getId())) {

			purchaseDeliveryScheduleVO = purchaseDeliveryScheduleRepo.findById(purchaseDeliveryScheduleDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Purchase Delivery Schedule Details"));

			purchaseDeliveryScheduleVO.setUpdatedBy(purchaseDeliveryScheduleDTO.getCreatedBy());

			message = "Purchase Delivery Schedule Updated Successfully";

		} else {

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
		purchaseDeliveryScheduleVO.setScheduleStartDate(dto.getScheduleEndDate());

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
//		responseDTO.setDocId(purchaseDeliveryScheduleVO.getDocId());
//		responseDTO.setDocDate(purchaseDeliveryScheduleVO.getDocDate());
		responseDTO.setScheduleStartDate(purchaseDeliveryScheduleVO.getScheduleStartDate());
		responseDTO.setScheduleEndDate(purchaseDeliveryScheduleVO.getScheduleEndDate());
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
	public Map<String, Object> getItemsForPurchaseDeliverySchedule(
	        String purchasecontractnumber,
	        Long customer,
	        Long branch,
	        Long orgId) throws ApplicationException {

	    Map<String, Object> responseMap = new HashMap<>();

	    List<Object[]> itemList = purchaseDeliveryScheduleRepo
	            .getItemsForPurchaseDeliverySchedule(purchasecontractnumber, customer,branch,orgId);

	    List<Map<String, Object>> responseList = new ArrayList<>();

	    for (Object[] obj : itemList) {

	        Map<String, Object> itemMap = new HashMap<>();

	        itemMap.put("item",
	                obj[0] != null ? obj[0].toString() : "");

	        itemMap.put("supplier",
	                obj[1] != null ? ((Number) obj[1]).longValue() : null);

	        itemMap.put("primaryUnit",
	                obj[2] != null ? ((Number) obj[2]).longValue() : null);

	        itemMap.put("purchaseUnit",
	                obj[3] != null ? ((Number) obj[3]).longValue() : null);

	        responseList.add(itemMap);
	    }

	    responseMap.put("message", "Item List Fetched Successfully");
	    responseMap.put("itemList", responseList);

	    return responseMap;
	}
	
//	Purchasecontractnumber dropdown
	@Override
	public Map<String, Object> getPurchaseOrderNumberForPurchaseDeliverySchedule(
	        Long customer,
	        LocalDate docdt,
	        Long branch,
	        Long orgId) throws ApplicationException {

	    Map<String, Object> responseMap = new HashMap<>();

	    List<Object[]> contractList =
	            purchaseDeliveryScheduleRepo
	                    .getPurchaseOrderNumberForPurchaseDeliverySchedule(
	                            customer, docdt, branch, orgId);

	    List<Map<String, Object>> responseList = new ArrayList<>();

	    for (Object[] obj : contractList) {

	        Map<String, Object> contractMap = new HashMap<>();

	        contractMap.put("id",
	                obj[0] != null ? ((Number) obj[0]).longValue() : null);

	        contractMap.put("purchaseorderno",
	                obj[1] != null ? obj[1].toString() : "");

	        contractMap.put("docDate",
	                obj[2] != null ? obj[2] : null);

	        contractMap.put("supplier",
	                obj[3] != null ? ((Number) obj[3]).longValue() : null);

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

		if (dto.getDealerType() != null && dto.getDealerType() > 0) {

			ListOfValuesDetailsVO dealerType = listOfValuesDetailsRepo.findById(dto.getDealerType())
					.orElseThrow(() -> new ApplicationException("Dealer Type Not Found"));

			vo.setDealerType(dealerType);
		}

		// ======================================================
		// Posting Category
		// ======================================================

		if (dto.getPostingCategory() != null && dto.getPostingCategory() > 0) {

			ListOfValuesDetailsVO postingCategory = listOfValuesDetailsRepo.findById(dto.getPostingCategory())
					.orElseThrow(() -> new ApplicationException("Posting Category Not Found"));

			vo.setPostingCategory(postingCategory);
		}

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

		    PurchaseBillSupplierResponseDTO supplierDTO =
		            new PurchaseBillSupplierResponseDTO();

		    supplierDTO.setId(vo.getSupplier().getId());

		    supplierDTO.setSupplierCode(
		            vo.getSupplier().getCustomerCode());

		    supplierDTO.setSupplierName(
		            vo.getSupplier().getCustomerName());

		    // GST State
		    if (vo.getSupplier().getGstState() != null) {

		        GSTStateResponseDTO gstStateDTO =
		                new GSTStateResponseDTO();

		        gstStateDTO.setId(
		                vo.getSupplier().getGstState().getId());

		        gstStateDTO.setStateCode(
		                vo.getSupplier().getGstState().getStateCode());

		        gstStateDTO.setStateName(
		                vo.getSupplier().getGstState().getStateName());

		        supplierDTO.setGstState(gstStateDTO);
		    }

		    // GST Number
		    supplierDTO.setGstNNo(
		            vo.getSupplier().getGstNo());

		    // ECC Type
		    supplierDTO.setEccType(
		            vo.getSupplier().getEccType());

		    dto.setSupplier(supplierDTO);
		}

		// ======================================================
		// Dealer Type
		// ======================================================

		if (vo.getDealerType() != null) {

			ListOfVlauesDetailsResponseDTO dealerTypeDTO = new ListOfVlauesDetailsResponseDTO();

			dealerTypeDTO.setId(vo.getDealerType().getId());

			dealerTypeDTO.setValueCode(vo.getDealerType().getValueCode());

			dto.setDealerType(dealerTypeDTO);
		}

		// ======================================================
		// Posting Category
		// ======================================================

		if (vo.getPostingCategory() != null) {

			ListOfVlauesDetailsResponseDTO postingCategoryDTO = new ListOfVlauesDetailsResponseDTO();

			postingCategoryDTO.setId(vo.getPostingCategory().getId());

			postingCategoryDTO.setValueCode(vo.getPostingCategory().getValueCode());

			dto.setPostingCategory(postingCategoryDTO);
		}

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

}
