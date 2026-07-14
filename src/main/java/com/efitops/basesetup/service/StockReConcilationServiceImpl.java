package com.efitops.basesetup.service;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.PurchaseShortCloseDTO;
import com.efitops.basesetup.dto.PurchaseShortCloseDetailsDTO;
import com.efitops.basesetup.dto.StockReConcilationDTO;
import com.efitops.basesetup.dto.StockReConcilationDetailsDTO;
import com.efitops.basesetup.dto.WorkOrderShortCloseDTO;
import com.efitops.basesetup.dto.WorkOrderShortCloseDetailsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.PurchaseShortCloseDetailsVO;
import com.efitops.basesetup.entity.PurchaseShortCloseVO;
import com.efitops.basesetup.entity.StockDetailsVO;
import com.efitops.basesetup.entity.StockReConcilationDetailsVO;
import com.efitops.basesetup.entity.StockReConcilationVO;
import com.efitops.basesetup.entity.WorkOrderShortCloseDetailsVO;
import com.efitops.basesetup.entity.WorkOrderShortCloseVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.PurchaseShortCloseDetailsRepo;
import com.efitops.basesetup.repo.PurchaseShortCloseRepo;
import com.efitops.basesetup.repo.StockDetailsRepo;
import com.efitops.basesetup.repo.StockReConcilationDetailsRepo;
import com.efitops.basesetup.repo.StockReConcilationRepo;
import com.efitops.basesetup.repo.WorkOrderShortCloseDetailsRepo;
import com.efitops.basesetup.repo.WorkOrderShortCloseRepo;

@Service
public class StockReConcilationServiceImpl implements StockReConcilationService {

	public static final Logger LOGGER = LoggerFactory.getLogger(StockReConcilationServiceImpl.class);

	@Autowired
	StockReConcilationRepo stockReConcilationRepo;

	@Autowired
	StockReConcilationDetailsRepo stockReConcilationDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	StockDetailsRepo stockDetailsRepo;

	@Autowired
	PurchaseShortCloseRepo purchaseShortCloseRepo;

	@Autowired
	PurchaseShortCloseDetailsRepo purchaseShortCloseDetailsRepo;

	@Autowired
	WorkOrderShortCloseRepo workOrderShortCloseRepo;

	@Autowired
	WorkOrderShortCloseDetailsRepo workOrderShortCloseDetailsRepo;

	@Override
	public StockReConcilationVO getStockReConcilationById(Long id) {

		return stockReConcilationRepo.getStockReConcilationById(id);

	}

	@Override
	public List<StockReConcilationVO> getAllStockReConcilationByOrgId(Long orgId, String finYear, String branchCode) {

		return stockReConcilationRepo.getAllStockReConcilationByOrgId(orgId, finYear, branchCode);

	}

	@Override
	public Map<String, Object> updateCreateStockReConcilation(@Valid StockReConcilationDTO stockReConcilationDTO)
			throws ApplicationException {

		String message;
		String screenCode = "SRC";
		StockReConcilationVO stockReConcilationVO;
		StockReConcilationVO savedPicked;

		if (stockReConcilationDTO.getId() != null) {

			stockReConcilationVO = stockReConcilationRepo.findById(stockReConcilationDTO.getId())
					.orElseThrow(() -> new ApplicationException("StockReConcilation not found"));

			stockReConcilationVO.setUpdatedBy(stockReConcilationDTO.getCreatedBy());

			createUpdateStockReConcilationVOByStockReConcilationDTO(stockReConcilationDTO, stockReConcilationVO);

			savedPicked = stockReConcilationRepo.save(stockReConcilationVO);
			message = "StockReConcilation Updated Successfully";

		} else {

			stockReConcilationVO = new StockReConcilationVO();

			// GET DOC ID
			String docId = stockReConcilationRepo.getStockReConcilationDocId(stockReConcilationDTO.getOrgId(),
					stockReConcilationDTO.getFinYear(), stockReConcilationDTO.getBranchCode(), screenCode);

			stockReConcilationVO.setDocId(docId);
			stockReConcilationVO.setCreatedBy(stockReConcilationDTO.getCreatedBy());
			stockReConcilationVO.setUpdatedBy(stockReConcilationDTO.getCreatedBy());

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(stockReConcilationDTO.getOrgId(),
							stockReConcilationDTO.getFinYear(), stockReConcilationDTO.getBranchCode(), screenCode);

			if (documentTypeMappingDetailsVO == null) {
				throw new ApplicationException("Document Type Mapping not found");
			}

			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			createUpdateStockReConcilationVOByStockReConcilationDTO(stockReConcilationDTO, stockReConcilationVO);

			savedPicked = stockReConcilationRepo.save(stockReConcilationVO);
			message = "StockReConcilation Created Successfully";
		}

		for (StockReConcilationDetailsVO detail : savedPicked.getStockReConcilationDetailsVO()) {
			StockDetailsVO rackStock = new StockDetailsVO();
			rackStock.setOrgId(savedPicked.getOrgId());
			rackStock.setStockDate(savedPicked.getDocDate());
			rackStock.setDocId(savedPicked.getDocId());
			rackStock.setDocDate(savedPicked.getDocDate());
			rackStock.setRefDate(savedPicked.getDocDate());
			rackStock.setRefNo(savedPicked.getId());
			rackStock.setSourceId(savedPicked.getId());
			rackStock.setSourceScreenCode(savedPicked.getScreenCode());
			rackStock.setSourceScreenName(savedPicked.getScreenName());
			rackStock.setLocation(savedPicked.getLocation());

			BigDecimal diff = detail.getDifference();

			rackStock.setQty(diff);
			rackStock.setPlusOrMinus(diff.signum() < 0 ? "m" : "p");

			rackStock.setPartno(detail.getItemCode());
			rackStock.setPartDesc(detail.getItemDesc());
			rackStock.setActive(true);
			rackStock.setCancel(false);
			rackStock.setCreatedBy(savedPicked.getCreatedBy());
			rackStock.setUpdatedBy(savedPicked.getUpdatedBy());
			rackStock.setBranch(savedPicked.getBranch());
			rackStock.setAmount(detail.getAmount());
			rackStock.setBranchCode(savedPicked.getBranchCode());
			rackStock.setFinYear(savedPicked.getFinYear());

			stockDetailsRepo.save(rackStock);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("stockReConcilationVO", savedPicked);
		response.put("message", message);
		return response;
	}

	private void createUpdateStockReConcilationVOByStockReConcilationDTO(
			@Valid StockReConcilationDTO stockReConcilationDTO, StockReConcilationVO stockReConcilationVO) {

		stockReConcilationVO.setLocation(stockReConcilationDTO.getLocation());
		stockReConcilationVO.setPreparedBy(stockReConcilationDTO.getPreparedBy());
		stockReConcilationVO.setOrgId(stockReConcilationDTO.getOrgId());
		stockReConcilationVO.setBranch(stockReConcilationDTO.getBranch());
		stockReConcilationVO.setBranchCode(stockReConcilationDTO.getBranchCode());
		stockReConcilationVO.setFinYear(stockReConcilationDTO.getFinYear());
		stockReConcilationVO.setNarration(stockReConcilationDTO.getNarration());

		if (stockReConcilationVO.getId() != null) {
			List<StockReConcilationDetailsVO> oldList = stockReConcilationDetailsRepo
					.findByStockReConcilationVO(stockReConcilationVO);
			stockReConcilationDetailsRepo.deleteAll(oldList);
		}

		BigDecimal totalAmount = BigDecimal.ZERO;
		List<StockReConcilationDetailsVO> detailsList = new ArrayList<>();

		for (StockReConcilationDetailsDTO dto : stockReConcilationDTO.getStockReConcilationDetailsDTO()) {

			StockReConcilationDetailsVO vo = new StockReConcilationDetailsVO();

			vo.setItemCode(dto.getItemCode());
			vo.setItemDesc(dto.getItemDesc());
			vo.setUnit(dto.getUnit());
			vo.setBookstock(dto.getBookstock());
			vo.setActualQty(dto.getActualQty());
			vo.setRate(dto.getRate());

			BigDecimal difference = dto.getActualQty().subtract(dto.getBookstock());
			vo.setDifference(difference);
			BigDecimal amount = difference.multiply(dto.getRate());
			vo.setAmount(amount);

			totalAmount = totalAmount.add(amount);

			vo.setStockReConcilationVO(stockReConcilationVO);
			detailsList.add(vo);
		}

		stockReConcilationVO.setTotalAmount(totalAmount);
		stockReConcilationVO.setStockReConcilationDetailsVO(detailsList);
	}

	@Override
	public String getStockReConcilationDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "SRC";
		return stockReConcilationRepo.getStockReConcilationDocId(orgId, finYear, branchCode, screenCode);
	}

	@Override
	public List<Map<String, Object>> getItemNameAndDesc(Long orgId) {
		Set<Object[]> chType = stockReConcilationRepo.getItemNameAndDesc(orgId);
		return getPickListReport(chType);
	}

	private List<Map<String, Object>> getPickListReport(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemName", ch[0] != null ? ch[0].toString() : "");
			map.put("itemDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("price", ch[2] != null ? ch[2].toString() : "");
			map.put("uom", ch[3] != null ? ch[3].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	// purchaseStockClose

	@Override
	public List<PurchaseShortCloseVO> getPurchaseShortCloseByOrgId(Long orgId, String finYear, String branchCode) {
		return purchaseShortCloseRepo.getPurchaseShortCloseByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public PurchaseShortCloseVO getPurchaseShortCloseById(Long id) {
		return purchaseShortCloseRepo.getPurchaseShortCloseById(id);
	}

	@Override
	public Map<String, Object> updateCreatePurchaseShortClose(PurchaseShortCloseDTO purchaseShortCloseDTO)
			throws ApplicationException {
		PurchaseShortCloseVO purchaseShortCloseVO = new PurchaseShortCloseVO();
		String message;
		String screenCode = "PSC";
		if (ObjectUtils.isNotEmpty(purchaseShortCloseDTO.getId())) {
			purchaseShortCloseVO = purchaseShortCloseRepo.findById(purchaseShortCloseDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid PO details"));
			message = "PurchaseShortClose Updated Successfully";
			purchaseShortCloseVO.setUpdatedBy(purchaseShortCloseDTO.getCreatedBy());

		} else {

			String docId = purchaseShortCloseRepo.getPurchaseShortCloseDocId(purchaseShortCloseDTO.getOrgId(),
					purchaseShortCloseDTO.getFinYear(), purchaseShortCloseDTO.getBranchCode(), screenCode);
			purchaseShortCloseVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(purchaseShortCloseDTO.getOrgId(),
							purchaseShortCloseDTO.getFinYear(), purchaseShortCloseDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			purchaseShortCloseVO.setCreatedBy(purchaseShortCloseDTO.getCreatedBy());
			purchaseShortCloseVO.setUpdatedBy(purchaseShortCloseDTO.getCreatedBy());

			message = "PurchaseShortClose Successfully";

		}
		createUpdatePurchaseShortCloseVOByPurchaseShortCloseDTO(purchaseShortCloseDTO, purchaseShortCloseVO);
		purchaseShortCloseRepo.save(purchaseShortCloseVO);
		Map<String, Object> response = new HashMap<>();
		response.put("purchaseShortCloseVO", purchaseShortCloseVO);
		response.put("message", message);
		return response;

	}

	private void createUpdatePurchaseShortCloseVOByPurchaseShortCloseDTO(
			@Valid PurchaseShortCloseDTO purchaseShortCloseDTO, PurchaseShortCloseVO purchaseShortCloseVO)
			throws ApplicationException {
		purchaseShortCloseVO.setCustomerName(purchaseShortCloseDTO.getCustomerName());
		purchaseShortCloseVO.setCustomerCode(purchaseShortCloseDTO.getCustomerCode());
		purchaseShortCloseVO.setSupplierName(purchaseShortCloseDTO.getSupplierName());
		purchaseShortCloseVO.setSupplierCode(purchaseShortCloseDTO.getSupplierCode());
		purchaseShortCloseVO.setPoNumber(purchaseShortCloseDTO.getPoNumber());
		purchaseShortCloseVO.setPoDate(purchaseShortCloseDTO.getPoDate());
		purchaseShortCloseVO.setContactPerson(purchaseShortCloseDTO.getContactPerson());
		purchaseShortCloseVO.setMobileNo(purchaseShortCloseDTO.getMobileNo());
		purchaseShortCloseVO.setEmail(purchaseShortCloseDTO.getEmail());
		purchaseShortCloseVO.setCity(purchaseShortCloseDTO.getCity());
		purchaseShortCloseVO.setState(purchaseShortCloseDTO.getState());
		purchaseShortCloseVO.setCountry(purchaseShortCloseDTO.getCountry());
		purchaseShortCloseVO.setAddress(purchaseShortCloseDTO.getAddress());
		purchaseShortCloseVO.setRemarks(purchaseShortCloseDTO.getRemarks());
		purchaseShortCloseVO.setOrgId(purchaseShortCloseDTO.getOrgId());
		purchaseShortCloseVO.setBranch(purchaseShortCloseDTO.getBranch());
		purchaseShortCloseVO.setBranchCode(purchaseShortCloseDTO.getBranchCode());
		purchaseShortCloseVO.setFinYear(purchaseShortCloseDTO.getFinYear());
		purchaseShortCloseVO.setCreatedBy(purchaseShortCloseDTO.getCreatedBy());

		if (ObjectUtils.isNotEmpty(purchaseShortCloseVO.getId())) {
			List<PurchaseShortCloseDetailsVO> byPurchaseShortCloseVO = purchaseShortCloseDetailsRepo
					.findByPurchaseShortCloseVO(purchaseShortCloseVO);
			purchaseShortCloseDetailsRepo.deleteAll(byPurchaseShortCloseVO);
		}

		List<PurchaseShortCloseDetailsVO> purchaseShortCloseDetailsVOs = new ArrayList<>();
		for (PurchaseShortCloseDetailsDTO purchaseShortCloseDetailsDTO : purchaseShortCloseDTO
				.getPurchaseShortCloseDetailsDTO()) {
			PurchaseShortCloseDetailsVO purchaseShortCloseDetailsVO = new PurchaseShortCloseDetailsVO();
			purchaseShortCloseDetailsVO.setItem(purchaseShortCloseDetailsDTO.getItem());
			purchaseShortCloseDetailsVO.setItemDesc(purchaseShortCloseDetailsDTO.getItemDesc());
			purchaseShortCloseDetailsVO.setUom(purchaseShortCloseDetailsDTO.getUom());
			purchaseShortCloseDetailsVO.setQty(purchaseShortCloseDetailsDTO.getQty());
			purchaseShortCloseDetailsVO.setRate(purchaseShortCloseDetailsDTO.getRate());
			purchaseShortCloseDetailsVO
					.setAmount(purchaseShortCloseDetailsDTO.getRate().multiply(purchaseShortCloseDetailsDTO.getQty()));
			purchaseShortCloseDetailsVO.setReceivedQty(purchaseShortCloseDetailsDTO.getReceivedQty());
			BigDecimal qty = purchaseShortCloseDetailsDTO.getQty();
			BigDecimal receivedQty = purchaseShortCloseDetailsDTO.getReceivedQty();
			BigDecimal shortageQty = purchaseShortCloseDetailsDTO.getShortageQty();
			BigDecimal remainingQty = qty.subtract(receivedQty);

			if (shortageQty.compareTo(remainingQty) <= 0) {
				purchaseShortCloseDetailsVO.setShortageQty(shortageQty);
			} else {
				throw new ApplicationException(
						"Invalid Shortage Qty" + remainingQty + ", but entered Shortage Qty = " + shortageQty);

			}

			purchaseShortCloseDetailsVO.setPurchaseShortCloseVO(purchaseShortCloseVO);
			purchaseShortCloseDetailsVOs.add(purchaseShortCloseDetailsVO);
		}

		purchaseShortCloseVO.setPurchaseShortCloseDetailsVO(purchaseShortCloseDetailsVOs);

	}

	@Override
	public String getPurchaseShortCloseDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "PSC";
		String result = purchaseShortCloseRepo.getPurchaseShortCloseDocId(orgId, finYear, branchCode, screenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getPurchaseOrderDetails(Long orgId, String poNo) {
		Set<Object[]> chType = purchaseShortCloseRepo.getPurchaseOrderDetails(orgId, poNo);
		return getPurchaseOrderDetails(chType);
	}

	private List<Map<String, Object>> getPurchaseOrderDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			map.put("docDate", ch[1] != null ? ch[1].toString() : "");
			map.put("customerName", ch[2] != null ? ch[2].toString() : "");
			map.put("customerCode", ch[3] != null ? ch[3].toString() : "");
			map.put("supplierName", ch[4] != null ? ch[4].toString() : "");
			map.put("supplierCode", ch[5] != null ? ch[5].toString() : "");
			map.put("country", ch[6] != null ? ch[6].toString() : "");
			map.put("address", ch[7] != null ? ch[7].toString() : "");
			map.put("contactPerson", ch[8] != null ? ch[8].toString() : "");
			map.put("city", ch[9] != null ? ch[9].toString() : "");
			map.put("state", ch[10] != null ? ch[10].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getItemDetailsFromPurchaseOrderDetails(Long orgId, String branchCode,
			String poNo) {
		Set<Object[]> chType = purchaseShortCloseRepo.getItemDetailsFromPurchaseOrderDetails(orgId, branchCode, poNo);
		return getItemDetailsFromPurchaseOrderDetails(chType);
	}

	private List<Map<String, Object>> getItemDetailsFromPurchaseOrderDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("item", ch[0] != null ? ch[0].toString() : "");
			map.put("itemDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("uom", ch[2] != null ? ch[2].toString() : "");
			map.put("qty", ch[3] != null ? ch[3].toString() : "");
			map.put("price", ch[4] != null ? ch[4].toString() : "");
			map.put("acceptQty", ch[5] != null ? ch[5].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPurchaseOrderDocId(Long orgId) {
		Set<Object[]> chType = purchaseShortCloseRepo.getPurchaseOrderDocId(orgId);
		return getPurchaseOrderDocId(chType);
	}

	private List<Map<String, Object>> getPurchaseOrderDocId(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	// WorkOrderStockCloseDetails

	@Override
	public List<WorkOrderShortCloseVO> getAllWorkOrderShortCloseByOrgId(Long orgId, String finYear, String branchCode) {
		return workOrderShortCloseRepo.getAllWorkOrderShortCloseByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public WorkOrderShortCloseVO getWorkOrderShortCloseById(Long id) {
		return workOrderShortCloseRepo.getWorkOrderShortCloseById(id);
	}

	@Override
	public Map<String, Object> createUpdateWorkOrderShortClose(WorkOrderShortCloseDTO workOrderShortCloseDTO)
			throws ApplicationException {
		WorkOrderShortCloseVO workOrderShortCloseVO = new WorkOrderShortCloseVO();
		String message;
		String screenCode = "WOSC";
		if (ObjectUtils.isNotEmpty(workOrderShortCloseDTO.getId())) {
			workOrderShortCloseVO = workOrderShortCloseRepo.findById(workOrderShortCloseDTO.getId())
					.orElseThrow(() -> new ApplicationException("WorkOrder Enquiry details"));
			workOrderShortCloseVO.setUpdatedBy(workOrderShortCloseDTO.getCreatedBy());
			createUpdatedWorkOrderShortCloseVOFromWorkOrderShortCloseDTO(workOrderShortCloseDTO, workOrderShortCloseVO);
			message = "WorkOrder Updated Successfully";

		} else {

			createUpdatedWorkOrderShortCloseVOFromWorkOrderShortCloseDTO(workOrderShortCloseDTO, workOrderShortCloseVO);

			String docId = workOrderShortCloseRepo.getWorkOrderShortCloseDocId(workOrderShortCloseDTO.getOrgId(),
					workOrderShortCloseDTO.getFinYear(), workOrderShortCloseDTO.getBranchCode(), screenCode);
			workOrderShortCloseVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(workOrderShortCloseDTO.getOrgId(),
							workOrderShortCloseDTO.getFinYear(), workOrderShortCloseDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			workOrderShortCloseVO.setCreatedBy(workOrderShortCloseDTO.getCreatedBy());
			workOrderShortCloseVO.setUpdatedBy(workOrderShortCloseDTO.getCreatedBy());

			message = "WorkOrder Created Successfully";
		}

		workOrderShortCloseRepo.save(workOrderShortCloseVO);
		Map<String, Object> response = new HashMap<>();
		response.put("workOrderShortCloseVO", workOrderShortCloseVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedWorkOrderShortCloseVOFromWorkOrderShortCloseDTO(
			WorkOrderShortCloseDTO workOrderShortCloseDTO, WorkOrderShortCloseVO workOrderShortCloseVO)
			throws ApplicationException {
		workOrderShortCloseVO.setCustomerName(workOrderShortCloseDTO.getCustomerName());
		workOrderShortCloseVO.setCustomerCode(workOrderShortCloseDTO.getCustomerCode());
		workOrderShortCloseVO.setCustomerPoNo(workOrderShortCloseDTO.getCustomerPoNo());
		workOrderShortCloseVO.setProductionMgr(workOrderShortCloseDTO.getProductionMgr());
		workOrderShortCloseVO.setWorkOrderNumber(workOrderShortCloseDTO.getWorkOrderNumber());
		workOrderShortCloseVO.setCreatedBy(workOrderShortCloseDTO.getCreatedBy());
		workOrderShortCloseVO.setOrgId(workOrderShortCloseDTO.getOrgId());
		workOrderShortCloseVO.setBranch(workOrderShortCloseDTO.getBranch());
		workOrderShortCloseVO.setBranchCode(workOrderShortCloseDTO.getBranchCode());
		workOrderShortCloseVO.setCurrency(workOrderShortCloseDTO.getCurrency());
		workOrderShortCloseVO.setFinYear(workOrderShortCloseDTO.getFinYear());
		if (ObjectUtils.isNotEmpty(workOrderShortCloseDTO.getId())) {
			List<WorkOrderShortCloseDetailsVO> workOrderShortCloseDetailsVO1 = workOrderShortCloseDetailsRepo
					.findByWorkOrderShortCloseVO(workOrderShortCloseVO);
			workOrderShortCloseDetailsRepo.deleteAll(workOrderShortCloseDetailsVO1);
		}

		List<WorkOrderShortCloseDetailsVO> workOrderShortCloseDetailsVOs = new ArrayList<>();
		for (WorkOrderShortCloseDetailsDTO workOrderShortCloseDetailsDTO : workOrderShortCloseDTO
				.getWorkOrderShortCloseDetailsDTO()) {
			WorkOrderShortCloseDetailsVO workOrderShortCloseDetailsVO = new WorkOrderShortCloseDetailsVO();
			workOrderShortCloseDetailsVO.setPartNo(workOrderShortCloseDetailsDTO.getPartNo());
			workOrderShortCloseDetailsVO.setPartName(workOrderShortCloseDetailsDTO.getPartName());
			workOrderShortCloseDetailsVO.setDrawingNo(workOrderShortCloseDetailsDTO.getDrawingNo());
			workOrderShortCloseDetailsVO.setRevisionNo(workOrderShortCloseDetailsDTO.getRevisionNo());
			workOrderShortCloseDetailsVO.setUom(workOrderShortCloseDetailsDTO.getUom());
			workOrderShortCloseDetailsVO.setOrderQty(workOrderShortCloseDetailsDTO.getOrderQty());
			if (workOrderShortCloseDetailsDTO.getShortageQty()
					.compareTo(workOrderShortCloseDetailsDTO.getOrderQty()) <= 0) {
				workOrderShortCloseDetailsVO.setShortageQty(workOrderShortCloseDetailsDTO.getShortageQty());

			} else {
				throw new ApplicationException("Shortage Qty cannot be greater than Order Qty. " + "Order Qty: "
						+ workOrderShortCloseDetailsDTO.getOrderQty() + ", Shortage Qty: "
						+ workOrderShortCloseDetailsDTO.getShortageQty());
			}

			workOrderShortCloseDetailsVO.setWorkOrderShortCloseVO(workOrderShortCloseVO);
			workOrderShortCloseDetailsVOs.add(workOrderShortCloseDetailsVO);
		}
		workOrderShortCloseVO.setWorkOrderShortCloseDetailsVO(workOrderShortCloseDetailsVOs);

	}

	@Override
	public String getWorkOrderShortCloseDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "WOSC";
		String result = workOrderShortCloseRepo.getWorkOrderShortCloseDocId(orgId, finYear, branchCode, screenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getWorkOrderNumber(Long orgId, String branchCode, String workOrderNo) {
		Set<Object[]> chType = workOrderShortCloseRepo.getWorkOrderNumber(orgId, branchCode, workOrderNo);
		return getWorkOrderNumber(chType);
	}

	private List<Map<String, Object>> getWorkOrderNumber(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			map.put("docDate", ch[1] != null ? ch[1].toString() : "");
			map.put("customerName", ch[2] != null ? ch[2].toString() : "");
			map.put("customerCode", ch[3] != null ? ch[3].toString() : "");
			map.put("customerPoNo", ch[4] != null ? ch[4].toString() : "");
			map.put("currency", ch[5] != null ? ch[5].toString() : "");
			map.put("productionManager", ch[6] != null ? ch[6].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getWorkOrderDetails(Long orgId, String branchCode, String workOrderNo) {
		Set<Object[]> chType = workOrderShortCloseRepo.getWorkOrderDetails(orgId, branchCode, workOrderNo);
		return getWorkOrderDetails(chType);
	}

	private List<Map<String, Object>> getWorkOrderDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("partno", ch[0] != null ? ch[0].toString() : "");
			map.put("partName", ch[1] != null ? ch[1].toString() : "");
			map.put("drawingNo", ch[2] != null ? ch[2].toString() : "");
			map.put("revisionNo", ch[3] != null ? ch[3].toString() : "");
			map.put("uom", ch[4] != null ? ch[4].toString() : "");
			map.put("orderQty", ch[5] != null ? ch[5].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getWorkOrderDocId(Long orgId) {
		Set<Object[]> chType = workOrderShortCloseRepo.getWorkOrderDocId(orgId);
		return getWorkOrderDocId(chType);
	}

	private List<Map<String, Object>> getWorkOrderDocId(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public WorkOrderShortCloseVO approveWorkOrderShortClose(Long orgId, Long id, String docId, String action,
			String actionBy) throws ApplicationException {

		WorkOrderShortCloseVO workOrderShortCloseVO = workOrderShortCloseRepo.findByOrgIdAndIdAndDocId(orgId, id,
				docId);

		if (workOrderShortCloseVO == null) {
			throw new ApplicationException("WorkOrderShortClose details not found");
		}

		if ("Approved".equalsIgnoreCase(workOrderShortCloseVO.getApproveStatus())) {
			throw new ApplicationException("This WorkOrderShortClose is already Approved.");
		} else if ("Rejected".equalsIgnoreCase(workOrderShortCloseVO.getApproveStatus())) {
			throw new ApplicationException("This WorkOrderShortClose is already Rejected.");
		}

		workOrderShortCloseVO.setApproveStatus(action);
		workOrderShortCloseVO.setShortCloseDate(LocalDate.now());
		workOrderShortCloseVO.setApproveBy(actionBy);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
		workOrderShortCloseVO.setApproveOn(LocalDateTime.now().format(formatter).toUpperCase());

		return workOrderShortCloseRepo.save(workOrderShortCloseVO);
	}

	@Override
	public PurchaseShortCloseVO approvePurchaseShortClose(Long orgId, Long id, String docId, String action,
			String actionBy) throws ApplicationException {

		PurchaseShortCloseVO purchaseShortCloseVO = purchaseShortCloseRepo.findByOrgIdAndIdAndDocId(orgId, id, docId);

		if (purchaseShortCloseVO == null) {
			throw new ApplicationException("PurchaseShortClose details not found");
		}

		if ("Approved".equalsIgnoreCase(purchaseShortCloseVO.getApproveStatus())) {
			throw new ApplicationException("This PurchaseShortClose is already Approved.");
		} else if ("Rejected".equalsIgnoreCase(purchaseShortCloseVO.getApproveStatus())) {
			throw new ApplicationException("This PurchaseShortClose is already Rejected.");
		}

		purchaseShortCloseVO.setApproveStatus(action);
		purchaseShortCloseVO.setPurchaseClosedDate(LocalDate.now());
		purchaseShortCloseVO.setApproveBy(actionBy);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
		purchaseShortCloseVO.setApproveOn(LocalDateTime.now().format(formatter).toUpperCase());

		return purchaseShortCloseRepo.save(purchaseShortCloseVO);
	}
	
	
	@Override
	public List<Map<String, Object>> getWorkOrderShortCloseReport(Long orgId,String branchCode,String fromDate,String toDate) {
		Set<Object[]> chType = workOrderShortCloseRepo.getWorkOrderShortCloseReport( orgId, branchCode, fromDate, toDate);
		return getWorkOrderShortCloseReport(chType);
	}

	private List<Map<String, Object>> getWorkOrderShortCloseReport(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : ""); // 0
			map.put("docDate", ch[1] != null ? ch[1].toString() : ""); // 1
			map.put("customerName", ch[2] != null ? ch[2].toString() : ""); // 2
			map.put("customerCode", ch[3] != null ? ch[3].toString() : ""); // 3
			map.put("customerPoNo", ch[4] != null ? ch[4].toString() : ""); // 4
			map.put("productionMgr", ch[5] != null ? ch[5].toString() : ""); // 5
			map.put("workOrderNumber", ch[6] != null ? ch[6].toString() : ""); // 6
			map.put("partNo", ch[7] != null ? ch[7].toString() : ""); // 7
			map.put("partName", ch[8] != null ? ch[8].toString() : ""); // 8
			map.put("drawingNo", ch[9] != null ? ch[9].toString() : ""); // 9
			map.put("revisionNo", ch[10] != null ? ch[10].toString() : ""); // 10
			map.put("uom", ch[11] != null ? ch[11].toString() : ""); // 11
			map.put("orderQty", ch[12] != null ? new BigDecimal(ch[12].toString()) : BigDecimal.ZERO); // 12
			map.put("shortageQty", ch[13] != null ? new BigDecimal(ch[13].toString()) : BigDecimal.ZERO); // 13

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPurchaseShortCloseReport(Long orgId,String branchCode,String fromDate,String toDate) {
		Set<Object[]> chType = purchaseShortCloseRepo.getPurchaseShortCloseReport(orgId, branchCode, fromDate, toDate);
		return getPurchaseShortCloseReport(chType);
	}

	private List<Map<String, Object>> getPurchaseShortCloseReport(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("address", ch[0] != null ? ch[0].toString() : ""); // 0
			map.put("contactPerson", ch[1] != null ? ch[1].toString() : ""); // 1
			map.put("country", ch[2] != null ? ch[2].toString() : ""); // 2
			map.put("customerName", ch[3] != null ? ch[3].toString() : ""); // 3
			map.put("customerCode", ch[4] != null ? ch[4].toString() : ""); // 4
			map.put("docId", ch[5] != null ? ch[5].toString() : ""); // 5
			map.put("docDate", ch[6] != null ? ch[6].toString() : ""); // 6
			map.put("poNumber", ch[7] != null ? ch[7].toString() : ""); // 7
			map.put("purchaseClosedDate", ch[8] != null ? ch[8].toString() : ""); // 8
			map.put("supplierCode", ch[9] != null ? ch[9].toString() : ""); // 9
			map.put("supplierName", ch[10] != null ? ch[10].toString() : ""); // 10
			map.put("item", ch[11] != null ? ch[11].toString() : ""); // 11
			map.put("itemDesc", ch[12] != null ? ch[12].toString() : ""); // 12

			map.put("qty", ch[13] != null ? new BigDecimal(ch[13].toString()) : BigDecimal.ZERO); // 13
			map.put("rate", ch[14] != null ? new BigDecimal(ch[14].toString()) : BigDecimal.ZERO); // 14
			map.put("receivedQty", ch[15] != null ? new BigDecimal(ch[15].toString()) : BigDecimal.ZERO); // 15
			map.put("shortageQty", ch[16] != null ? new BigDecimal(ch[16].toString()) : BigDecimal.ZERO); // 16
			map.put("uom", ch[17] != null ? ch[17].toString() : ""); // 17
			map.put("amount", ch[18] != null ? new BigDecimal(ch[18].toString()) : BigDecimal.ZERO); // 18


			List1.add(map);
		}
		return List1;
	}

}
