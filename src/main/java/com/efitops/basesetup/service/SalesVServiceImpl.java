package com.efitops.basesetup.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.SalesDTO;
import com.efitops.basesetup.dto.SalesInvoiceExportDTO;
import com.efitops.basesetup.dto.SalesInvoiceExportDetailsDTO;
import com.efitops.basesetup.dto.SalesInvoiceExportTermsDTO;
import com.efitops.basesetup.dto.SalesItemParticularsDTO;
import com.efitops.basesetup.dto.SalesOrderTermsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.PendingWorkOrderDetailsVO;
import com.efitops.basesetup.entity.SalesInvoiceExportDetailsVO;
import com.efitops.basesetup.entity.SalesInvoiceExportTermsVO;
import com.efitops.basesetup.entity.SalesInvoiceExportVO;
import com.efitops.basesetup.entity.SalesItemParticularsVO;
import com.efitops.basesetup.entity.SalesOrderTermsVO;
import com.efitops.basesetup.entity.SalesVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.PendingWorkOrderDetailsRepo;
import com.efitops.basesetup.repo.SalesInvoiceExportDetailsRepo;
import com.efitops.basesetup.repo.SalesInvoiceExportRepo;
import com.efitops.basesetup.repo.SalesInvoiceExportTermsRepo;
import com.efitops.basesetup.repo.SalesItemParticularsRepo;
import com.efitops.basesetup.repo.SalesOrderTermsRepo;
import com.efitops.basesetup.repo.SalesRepo;

@Service
public class SalesVServiceImpl implements SalesVService {

	@Autowired
	SalesRepo salesRepo;

	@Autowired
	SalesItemParticularsRepo salesItemParticularsRepo;

	@Autowired
	SalesOrderTermsRepo salesOrderTermsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	SalesInvoiceExportRepo salesInvoiceExportRepo;

	@Autowired
	SalesInvoiceExportTermsRepo salesInvoiceExportTermsRepo;

	@Autowired
	AmountInWordsConverterService amountInWordsConverterService;

	@Autowired
	SalesInvoiceExportDetailsRepo salesInvoiceExportDetailsRepo;

	@Autowired
	PendingWorkOrderDetailsRepo pendingWorkOrderDetailsRepo;
	
	@Autowired
	private CommonNotificationService commonNotificationService;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Map<String, Object> updateCreateSalesOrder(SalesDTO salesDTO) throws ApplicationException {
		SalesVO salesVO = new SalesVO();
		String message;
		String screenCode = "SO";
		SalesVO oldSales   = null;

		if (ObjectUtils.isNotEmpty(salesDTO.getId())) {
			oldSales = salesRepo.findById(salesDTO.getId())
		            .orElseThrow(() -> new ApplicationException("sales not found"));

			oldSales.getSalesItemParticularsVO().size(); // load
			oldSales.getSalesOrderTermsVO().size(); // load
			
		    entityManager.detach(oldSales); // detach snapshot
			salesVO = salesRepo.findById(salesDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Sales details"));
			salesVO.setUpdatedBy(salesDTO.getCreatedBy());
			getSalesVOFromsalesDTO(salesDTO, salesVO);
			message = "Sales Updated Successfully";
		} else {

			String docId = salesRepo.getSalesDocId(salesDTO.getOrgId(), salesDTO.getFinYear(), salesDTO.getBranchCode(),
					screenCode);
			salesVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(salesDTO.getOrgId(), salesDTO.getFinYear(),
							salesDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			salesVO.setCreatedBy(salesDTO.getCreatedBy());
			salesVO.setUpdatedBy(salesDTO.getCreatedBy());
			getSalesVOFromsalesDTO(salesDTO, salesVO);
			message = "Sales Created Successfully";
		}

		SalesVO savedSales = salesRepo.save(salesVO);
		commonNotificationService.generateNotification(salesVO.getScreenCode(), salesVO.getId(), oldSales, salesVO);

		List<SalesItemParticularsVO> salesItemParticularsVOs = savedSales.getSalesItemParticularsVO();

		Long sourceId = savedSales.getId();
		if (sourceId != null) { // Null check for safety
			List<PendingWorkOrderDetailsVO> pendingWorkOrderDetailsVOList = pendingWorkOrderDetailsRepo
					.findBySourceId(sourceId);

			if (!pendingWorkOrderDetailsVOList.isEmpty()) { // Check if records exist
				pendingWorkOrderDetailsRepo.deleteAll(pendingWorkOrderDetailsVOList);
			}
		}

		for (SalesItemParticularsVO detailsVO : salesItemParticularsVOs) {
			PendingWorkOrderDetailsVO pendingWorkOrder = new PendingWorkOrderDetailsVO();
			pendingWorkOrder.setOrgId(savedSales.getOrgId());
			pendingWorkOrder.setRefDate(savedSales.getDocDate());
			pendingWorkOrder.setRefNo(savedSales.getId());
			pendingWorkOrder.setPlusOrMinus("m");
			pendingWorkOrder.setSourceScreenCode(savedSales.getScreenCode());
			pendingWorkOrder.setSourceScreenName(savedSales.getScreenName());
			pendingWorkOrder.setQty(detailsVO.getQtyOfferd().multiply(BigDecimal.valueOf(-1)));
			pendingWorkOrder.setPartno(detailsVO.getPartNo());
			pendingWorkOrder.setPartDesc(detailsVO.getPartDesc());
			pendingWorkOrder.setCreatedBy(savedSales.getCreatedBy());
			pendingWorkOrder.setUpdatedBy(savedSales.getUpdatedBy());
			pendingWorkOrder.setSourceId(savedSales.getId());
			pendingWorkOrder.setWorkOrderNo(detailsVO.getWorkOrderNo());
			pendingWorkOrder.setWorkorderdate(savedSales.getDocDate());
			pendingWorkOrder.setCustomerName(savedSales.getCustomerName());
			pendingWorkOrder.setCustomerPoNo(savedSales.getCustomerPoNo());
			pendingWorkOrder.setCustomerCode(savedSales.getCustomerCode());
			pendingWorkOrder.setSalesOrderNo(savedSales.getDocId());
			pendingWorkOrder.setSalesOrderDate(savedSales.getDocDate());
			pendingWorkOrderDetailsRepo.save(pendingWorkOrder);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("salesVO", salesVO);
		response.put("message", message);
		return response;
	}

	private void getSalesVOFromsalesDTO(SalesDTO salesDTO, SalesVO salesVO) {
		salesVO.setCustomerName(salesDTO.getCustomerName());
		salesVO.setCustomerCode(salesDTO.getCustomerCode());
		salesVO.setCurrency(salesDTO.getCurrency());
		salesVO.setExChangeRate(salesDTO.getExChangeRate());
		salesVO.setCustomerPoNo(salesDTO.getCustomerPoNo());
		salesVO.setWorkOrderNo(salesDTO.getWorkOrderNo());
		salesVO.setShippingAddress(salesDTO.getShippingAddress());
		salesVO.setBillingAddress(salesDTO.getBillingAddress());
		salesVO.setContactPerson(salesDTO.getContactPerson());
		salesVO.setCustomerMail(salesDTO.getCustomerMail());
		salesVO.setPlaceOfSupply(salesDTO.getPlaceOfSupply());
		salesVO.setTaxType(salesDTO.getTaxType());
		salesVO.setNarration(salesDTO.getNarration());
		salesVO.setInvoiceType(salesDTO.getInvoiceType());
		salesVO.setDueDate(salesDTO.getDueDate());
		salesVO.setCreatedBy(salesDTO.getCreatedBy());
		salesVO.setOrgId(salesDTO.getOrgId());
		salesVO.setBranch(salesDTO.getBranch());
		salesVO.setBranchCode(salesDTO.getBranchCode());
		salesVO.setFinYear(salesDTO.getFinYear());

		BigDecimal totalTaxAmount = BigDecimal.ZERO;
		BigDecimal grossAmount = BigDecimal.ZERO;
		BigDecimal netAmount = BigDecimal.ZERO;

		if (salesDTO.getId() != null) {
			List<SalesItemParticularsVO> salesItemParticularsVO1 = salesItemParticularsRepo.findBySalesVO(salesVO);
			salesItemParticularsRepo.deleteAll(salesItemParticularsVO1);

			List<SalesOrderTermsVO> salesOrderTermsVO1 = salesOrderTermsRepo.findBySalesVO(salesVO);
			salesOrderTermsRepo.deleteAll(salesOrderTermsVO1);
		}

		List<SalesItemParticularsVO> salesItemParticularsVOs = new ArrayList<>();
		for (SalesItemParticularsDTO salesItemParticularsDTO : salesDTO.getSalesItemParticularsDTO()) {
			SalesItemParticularsVO salesItemParticularsVO = new SalesItemParticularsVO();
			salesItemParticularsVO.setPartNo(salesItemParticularsDTO.getPartNo());
			salesItemParticularsVO.setPartDesc(salesItemParticularsDTO.getPartDesc());
			salesItemParticularsVO.setWorkOrderNo(salesItemParticularsDTO.getWorkOrderNo());
			salesItemParticularsVO.setCustomerPoNo(salesItemParticularsDTO.getCustomerPoNo());
			salesItemParticularsVO.setDueDate(salesItemParticularsDTO.getDueDate());
			salesItemParticularsVO.setUnitPrice(salesItemParticularsDTO.getUnitPrice());
			salesItemParticularsVO.setQtyOfferd(salesItemParticularsDTO.getQtyOfferd());
			salesItemParticularsVO.setDiscount(salesItemParticularsDTO.getDiscount());
			salesItemParticularsVO.setTaxCode(salesItemParticularsDTO.getTaxCode());
			salesItemParticularsVO.setExRate(salesItemParticularsDTO.getExRate());
			BigDecimal discountAmount = BigDecimal.ZERO;
			BigDecimal taxAmountIn = BigDecimal.ZERO;
			BigDecimal amount = BigDecimal.ZERO;
			BigDecimal grossAmountIn = BigDecimal.ZERO;

			BigDecimal amountSet = salesItemParticularsDTO.getUnitPrice()
					.multiply(salesItemParticularsDTO.getQtyOfferd());
			salesItemParticularsVO.setBasicAmount(amountSet);

			discountAmount = salesItemParticularsVO.getBasicAmount().multiply(salesItemParticularsDTO.getDiscount())
					.divide(BigDecimal.valueOf(100));

			grossAmountIn = salesItemParticularsVO.getBasicAmount().subtract(discountAmount);

			salesItemParticularsVO.setTaxableAmount(grossAmountIn);
			grossAmount = grossAmount.add(salesItemParticularsVO.getTaxableAmount());

			if (salesVO.getTaxType() == null || salesVO.getTaxType().isEmpty()
					|| !salesVO.getTaxType().equalsIgnoreCase("INTER")
							&& !salesVO.getTaxType().equalsIgnoreCase("INTRA")) {

				salesItemParticularsVO.setSgst(BigDecimal.ZERO);
				salesItemParticularsVO.setCgst(BigDecimal.ZERO);
				salesItemParticularsVO.setIgst(BigDecimal.ZERO);
				salesItemParticularsVO.setTaxAmount(BigDecimal.ZERO);
			} else {
				if (salesVO.getTaxType().equalsIgnoreCase("INTER")) {

					salesItemParticularsVO.setIgst(salesItemParticularsDTO.getIgst());
					BigDecimal igstAmount = salesItemParticularsVO.getTaxableAmount()
							.multiply(salesItemParticularsDTO.getIgst()).divide(BigDecimal.valueOf(100));
					salesItemParticularsVO.setCgst(BigDecimal.ZERO);
					salesItemParticularsVO.setSgst(BigDecimal.ZERO);
					taxAmountIn = igstAmount;
					salesItemParticularsVO.setTaxAmount(taxAmountIn);
				} else if (salesVO.getTaxType().equalsIgnoreCase("INTRA")) {
					salesItemParticularsVO.setCgst(salesItemParticularsDTO.getCgst());
					salesItemParticularsVO.setSgst(salesItemParticularsDTO.getSgst());

					BigDecimal cgstAmount = salesItemParticularsDTO.getCgst()
							.multiply(salesItemParticularsVO.getTaxableAmount()).divide(BigDecimal.valueOf(100));
					BigDecimal sgstAmount = salesItemParticularsDTO.getSgst()
							.multiply(salesItemParticularsVO.getTaxableAmount()).divide(BigDecimal.valueOf(100));

					salesItemParticularsVO.setIgst(BigDecimal.ZERO);
					taxAmountIn = cgstAmount.add(sgstAmount);
					salesItemParticularsVO.setTaxAmount(taxAmountIn);
				}
			}

			totalTaxAmount = totalTaxAmount.add(salesItemParticularsVO.getTaxAmount());

			amount = salesItemParticularsVO.getTaxableAmount().add(salesItemParticularsVO.getTaxAmount());
			salesItemParticularsVO.setAmount(amount);
			netAmount = netAmount.add(salesItemParticularsVO.getAmount());

			salesItemParticularsVO.setSalesVO(salesVO);
			salesItemParticularsVOs.add(salesItemParticularsVO);
		}

		salesVO.setGrossAmount(netAmount);
		salesVO.setTotalTaxAmount(totalTaxAmount);
		salesVO.setNetAmount(grossAmount);
		salesVO.setAmountInWords(amountInWordsConverterService.convert(salesVO.getNetAmount().longValue()));
		salesVO.setSalesItemParticularsVO(salesItemParticularsVOs);

		List<SalesOrderTermsVO> salesOrderTermsVOs = new ArrayList<>();
		for (SalesOrderTermsDTO salesOrderTermsDTO : salesDTO.getSalesOrderTermsDTO()) {
			SalesOrderTermsVO salesOrderTermsVO = new SalesOrderTermsVO();
			salesOrderTermsVO.setTerms(salesOrderTermsDTO.getTerms());
			salesOrderTermsVO.setDescription(salesOrderTermsDTO.getDescription());

			salesOrderTermsVO.setSalesVO(salesVO);
			salesOrderTermsVOs.add(salesOrderTermsVO);
		}
		salesVO.setSalesOrderTermsVO(salesOrderTermsVOs);
	}

	@Override
	public String getSalesDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "SO";
		String result = salesRepo.getSalesDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	public SalesVO getSalesById(Long id) {

		return salesRepo.getSalesById(id);
	}

	@Override
	public List<SalesVO> getAllSalesByOrgId(Long orgId, String finYear, String branchCode) {

		return salesRepo.getAllSalesByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public List<Map<String, Object>> findByCustomerNameFromPartyMasterSalesOrder(Long orgId, String finYear,
			String branchCode) {
		Set<Object[]> chType = salesRepo.findByCustomerNameFromPartyMasterSalesOrder(orgId, finYear, branchCode);
		return findByCustomerNameFromPartyMasterSales(chType);
	}

	private List<Map<String, Object>> findByCustomerNameFromPartyMasterSales(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("customerName", ch[0] != null ? ch[0].toString() : "");
			map.put("customerCode", ch[1] != null ? ch[1].toString() : "");
			map.put("currency", ch[2] != null ? ch[2].toString() : "");
//			map.put("taxType", ch[3] != null ? ch[3].toString() : "");
			map.put("billingAddress", ch[3] != null ? ch[3].toString() : "");
			map.put("contactPerson", ch[4] != null ? ch[4].toString() : "");
			map.put("email", ch[5] != null ? ch[5].toString() : "");
			map.put("sellingPrice", ch[6] != null ? ch[6].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> findByShippingAddressFromPartyMaster(Long orgId, String finYear, String branchCode,
			String customerName) {
		Set<Object[]> chType = salesRepo.findByShippingAddressFromPartyMaster(orgId, finYear, branchCode, customerName);
		return findByShippingAddress(chType);
	}

	private List<Map<String, Object>> findByShippingAddress(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("shippingAddress", ch[0] != null ? ch[0].toString() : "");
			map.put("placeOfSupply", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> findByCustomerPoNoFromWorkOrder(Long orgId, String finYear, String branchCode,
			String customerName) {
		Set<Object[]> chType = salesRepo.findByCustomerPoNoFromWorkOrder(orgId, finYear, branchCode, customerName);
		return findByCustomerPoNo(chType);
	}

	private List<Map<String, Object>> findByCustomerPoNo(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("customerPoNumber", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

//	@Override
//	public List<Map<String, Object>> findByWorkOrderNo(Long orgId,String finYear,String branchCode, String customerPoNo) {
//		Set<Object[]> chType = salesRepo.findByWorkOrderNo(orgId,finYear,branchCode, customerPoNo);
//		return findByWorkOrder(chType);
//	}
//
//	private List<Map<String, Object>> findByWorkOrder(Set<Object[]> chType) {
//		List<Map<String, Object>> List1 = new ArrayList<>();
//		for (Object[] ch : chType) {
//			Map<String, Object> map = new HashMap<>();
//			map.put("workOrderNo", ch[0] != null ? ch[0].toString() : "");
//			map.put("dueDate", ch[1] != null ? ch[1].toString() : "");
//			List1.add(map);
//		}
//		return List1;
//	}

	@Override
	public List<Map<String, Object>> findByWorkOrderNo(Long orgId, String finYear, String branchCode,
			String customerPoNoCsv) {

		List<String> customerPoList = Arrays.stream(customerPoNoCsv.split(",")).map(String::trim)
				.filter(s -> !s.isEmpty()).collect(Collectors.toList());

		Set<Object[]> chType = salesRepo.findByWorkOrderNo(orgId, finYear, branchCode, customerPoList);

		return findByWorkOrder(chType);
	}

	private List<Map<String, Object>> findByWorkOrder(Set<Object[]> chType) {
		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("workOrderNo", ch[0] != null ? ch[0].toString() : "");
			map.put("dueDate", ch[1] != null ? ch[1].toString() : "");
			list.add(map);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> findByContactPersonFromPartyMaster(Long orgId, String finYear, String branchCode,
			String customerCode) {
		Set<Object[]> chType = salesRepo.findByContactPersonFromPartyMaster(orgId, finYear, branchCode, customerCode);
		return findByContactPerson(chType);
	}

	private List<Map<String, Object>> findByContactPerson(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("contactPerson", ch[0] != null ? ch[0].toString() : "");
			map.put("placeOfSupply", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> findByInvoiceType(Long orgId, String finYear, String branchCode,
			String customerCode, String currency) {
		Set<Object[]> chType = salesRepo.findByInvoiceType(orgId, finYear, branchCode, customerCode, currency);
		return findByInvoice(chType);
	}

	private List<Map<String, Object>> findByInvoice(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("invoiceType", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> findByTaxType(Long orgId, String branchCode, String customerCode,
			String partyType) {
		Set<Object[]> chType = salesRepo.findByTaxType(orgId, branchCode, customerCode, partyType);
		return findByTaxType(chType);
	}

	private List<Map<String, Object>> findByTaxType(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("taxType", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> findByGstForSalesOrder(Long orgId, String currency, String item, String taxType) {
		Set<Object[]> chType = salesRepo.findByGstForSalesOrder(orgId, currency, item, taxType);
		return findByGstForSalesOrder(chType);
	}

	private List<Map<String, Object>> findByGstForSalesOrder(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemName", ch[0] != null ? ch[0].toString() : "");
			map.put("taxSlab", ch[1] != null ? ch[1].toString() : "");
			map.put("gstPercentage", ch[2] != null ? ch[2].toString() : "");
			map.put("cgstPercentage", ch[3] != null ? ch[3].toString() : "");
			map.put("sgstPercentage", ch[4] != null ? ch[4].toString() : "");
			map.put("igstPercentage", ch[5] != null ? ch[5].toString() : "");

			List1.add(map);
		}
		return List1;
	}

//	@Override
//	public List<Map<String, Object>> findByPartNoAndDescFromWorkOrder(Long orgId,String finYear,String branchCode,String workOrderNo) {
//		Set<Object[]> chType = salesRepo.findByPartNoAndDescFromWorkOrder(orgId,finYear,branchCode,workOrderNo);
//		return findByPartNo(chType);
//	}
//
//	private List<Map<String, Object>> findByPartNo(Set<Object[]> chType) {
//		List<Map<String, Object>> List1 = new ArrayList<>();
//		for (Object[] ch : chType) {
//			Map<String, Object> map = new HashMap<>();
//			map.put("partNo", ch[0] != null ? ch[0].toString() : "");
//			map.put("partDesc", ch[1] != null ? ch[1].toString() : "");
//			map.put("workOrderNo", ch[2] != null ? ch[2].toString() : "");
//			map.put("dueDate", ch[3] != null ? ch[3].toString() : "");
//			map.put("qtyOffered", ch[4] != null ? ch[4].toString() : "");
//			map.put("price", ch[5] != null ? ch[5].toString() : "");
//			List1.add(map);
//		}
//		return List1;
//	}

	@Override
	public List<Map<String, Object>> findByPartNoAndDescFromWorkOrder(Long orgId, String finYear, String branchCode,
			String workOrderNoCsv) {

		List<String> workOrderList = Arrays.stream(workOrderNoCsv.split(",")).map(String::trim)
				.filter(s -> !s.isEmpty()).collect(Collectors.toList());

		Set<Object[]> chType = salesRepo.findByPartNoAndDescFromWorkOrder(orgId, finYear, branchCode, workOrderList);

		return findByPartNo(chType);
	}

	private List<Map<String, Object>> findByPartNo(Set<Object[]> chType) {
		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("partNo", ch[0] != null ? ch[0].toString() : "");
			map.put("partDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("workOrderNo", ch[2] != null ? ch[2].toString() : "");
			map.put("dueDate", ch[3] != null ? ch[3].toString() : "");
			map.put("qtyOffered", ch[4] != null ? ch[4].toString() : "");
			map.put("price", ch[5] != null ? ch[5].toString() : "");
			map.put("customerPoNo", ch[6] != null ? ch[6].toString() : "");
			list.add(map);
		}
		return list;
	}

	// SALESINVOICEEXPORT

	@Override
	public Map<String, Object> updateCreateSalesInvoiceExport(SalesInvoiceExportDTO salesInvoiceExportDTO)
			throws ApplicationException {
		SalesInvoiceExportVO salesInvoiceExportVO = new SalesInvoiceExportVO();
		String message;
		String screenCode = "SIE";
		if (ObjectUtils.isNotEmpty(salesInvoiceExportDTO.getId())) {
			salesInvoiceExportVO = salesInvoiceExportRepo.findById(salesInvoiceExportDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid SalesInvoiceExport details"));
			message = "SalesInvoiceExport Updated Successfully";
			salesInvoiceExportVO.setUpdatedBy(salesInvoiceExportDTO.getCreatedBy());

		} else {

			String docId = salesInvoiceExportRepo.getSalesInvoiceExportDocId(salesInvoiceExportDTO.getOrgId(),
					salesInvoiceExportDTO.getFinYear(), salesInvoiceExportDTO.getBranchCode(), screenCode);
			salesInvoiceExportVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(salesInvoiceExportDTO.getOrgId(),
							salesInvoiceExportDTO.getFinYear(), salesInvoiceExportDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			salesInvoiceExportVO.setCreatedBy(salesInvoiceExportDTO.getCreatedBy());
			salesInvoiceExportVO.setUpdatedBy(salesInvoiceExportDTO.getCreatedBy());
			message = "SalesInvoiceExport Created Successfully";
		}
		getSalesInvoiceExportVOFromSalesInvoiceExportDTO(salesInvoiceExportDTO, salesInvoiceExportVO);
		salesInvoiceExportRepo.save(salesInvoiceExportVO);
		Map<String, Object> response = new HashMap<>();
		response.put("salesInvoiceExportVO", salesInvoiceExportVO);
		response.put("message", message);
		return response;
	}

	private void getSalesInvoiceExportVOFromSalesInvoiceExportDTO(SalesInvoiceExportDTO salesInvoiceExportDTO,
			SalesInvoiceExportVO salesInvoiceExportVO) {
		salesInvoiceExportVO.setSalesOrderNo(salesInvoiceExportDTO.getSalesOrderNo());
		salesInvoiceExportVO.setExportPackingNo(salesInvoiceExportDTO.getExportPackingNo());
		salesInvoiceExportVO.setCurrency(salesInvoiceExportDTO.getCurrency());
		salesInvoiceExportVO.setExchangeRate(salesInvoiceExportDTO.getExchangeRate());
		salesInvoiceExportVO.setLocation(salesInvoiceExportDTO.getLocation());
		salesInvoiceExportVO.setBillingAddress(salesInvoiceExportDTO.getBillingAddress());
		salesInvoiceExportVO.setShippingAddress(salesInvoiceExportDTO.getShippingAddress());
		salesInvoiceExportVO.setOrgId(salesInvoiceExportDTO.getOrgId());
		salesInvoiceExportVO.setBranch(salesInvoiceExportDTO.getBranch());
		salesInvoiceExportVO.setBranchCode(salesInvoiceExportDTO.getBranchCode());
		salesInvoiceExportVO.setFinYear(salesInvoiceExportDTO.getFinYear());
		salesInvoiceExportVO.setCreatedBy(salesInvoiceExportDTO.getCreatedBy());
		salesInvoiceExportVO.setRemarks(salesInvoiceExportDTO.getRemarks());
		salesInvoiceExportVO.setCustomerName(salesInvoiceExportDTO.getCustomerName());

		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal totalGrossAmount = BigDecimal.ZERO;
		BigDecimal totalDiscountAmount = BigDecimal.ZERO;
		BigDecimal totalQty = BigDecimal.ZERO;

		if (salesInvoiceExportDTO.getId() != null) {
			List<SalesInvoiceExportDetailsVO> salesInvoiceExportDetailsVO1 = salesInvoiceExportDetailsRepo
					.findBySalesInvoiceExportVO(salesInvoiceExportVO);
			salesInvoiceExportDetailsRepo.deleteAll(salesInvoiceExportDetailsVO1);
			List<SalesInvoiceExportTermsVO> salesInvoiceExportTermsVO1 = salesInvoiceExportTermsRepo
					.findBySalesInvoiceExportVO(salesInvoiceExportVO);
			salesInvoiceExportTermsRepo.deleteAll(salesInvoiceExportTermsVO1);
		}

		List<SalesInvoiceExportDetailsVO> salesInvoiceExportDetailsVOs = new ArrayList<>();
		for (SalesInvoiceExportDetailsDTO salesInvoiceExportDetailsDTO : salesInvoiceExportDTO
				.getSalesInvoiceExportDetailsDTO()) {
			SalesInvoiceExportDetailsVO salesInvoiceExportDetailsVO = new SalesInvoiceExportDetailsVO();
			salesInvoiceExportDetailsVO.setItem(salesInvoiceExportDetailsDTO.getItem());
			salesInvoiceExportDetailsVO.setItemDesc(salesInvoiceExportDetailsDTO.getItemDesc());
			salesInvoiceExportDetailsVO.setUnits(salesInvoiceExportDetailsDTO.getUnits());
			salesInvoiceExportDetailsVO.setQty(salesInvoiceExportDetailsDTO.getQty());
			salesInvoiceExportDetailsVO.setRate(salesInvoiceExportDetailsDTO.getRate());
			salesInvoiceExportDetailsVO.setDiscount(salesInvoiceExportDetailsDTO.getDiscount());
			totalQty = totalQty.add(salesInvoiceExportDetailsDTO.getQty());

			BigDecimal grossAmount = BigDecimal.ZERO;
			BigDecimal discountAmount = BigDecimal.ZERO;
			BigDecimal netAmount = BigDecimal.ZERO;

			grossAmount = salesInvoiceExportDetailsDTO.getQty().multiply(salesInvoiceExportDetailsDTO.getRate());
			salesInvoiceExportDetailsVO.setGrossAmount(grossAmount);
			totalGrossAmount = totalGrossAmount.add(salesInvoiceExportDetailsVO.getGrossAmount());
			discountAmount = salesInvoiceExportDetailsDTO.getDiscount()
					.multiply(salesInvoiceExportDetailsVO.getGrossAmount()).divide(BigDecimal.valueOf(100));
			salesInvoiceExportDetailsVO.setDiscountAmount(discountAmount);
			totalDiscountAmount = totalDiscountAmount.add(salesInvoiceExportDetailsVO.getDiscountAmount());
			netAmount = salesInvoiceExportDetailsVO.getGrossAmount()
					.subtract(salesInvoiceExportDetailsVO.getDiscountAmount());
			salesInvoiceExportDetailsVO.setNetAmount(netAmount);
			totalAmount = totalAmount.add(salesInvoiceExportDetailsVO.getNetAmount());

			salesInvoiceExportDetailsVO.setSalesInvoiceExportVO(salesInvoiceExportVO);
			salesInvoiceExportDetailsVOs.add(salesInvoiceExportDetailsVO);
		}
		salesInvoiceExportVO.setTotalQty(totalQty);
		salesInvoiceExportVO.setTotalGrossAmount(totalGrossAmount);
		salesInvoiceExportVO.setTotalDiscountAmount(totalDiscountAmount);
		salesInvoiceExportVO.setTotalAmount(totalAmount);

		salesInvoiceExportVO.setTotalAmountInWords(
				amountInWordsConverterService.convert(salesInvoiceExportVO.getTotalAmount().longValue()));
		salesInvoiceExportVO.setSalesInvoiceExportDetailsVO(salesInvoiceExportDetailsVOs);

		List<SalesInvoiceExportTermsVO> salesInvoiceExportTermsVOs = new ArrayList<>();
		for (SalesInvoiceExportTermsDTO salesInvoiceExportTermsDTO : salesInvoiceExportDTO
				.getSalesInvoiceExportTermsDTO()) {
			SalesInvoiceExportTermsVO salesInvoiceExportTermsVO = new SalesInvoiceExportTermsVO();
			salesInvoiceExportTermsVO.setTerms(salesInvoiceExportTermsDTO.getTerms());
			salesInvoiceExportTermsVO.setDescriptions(salesInvoiceExportTermsDTO.getDescriptions());
			salesInvoiceExportTermsVO.setSalesInvoiceExportVO(salesInvoiceExportVO);
			salesInvoiceExportTermsVOs.add(salesInvoiceExportTermsVO);
			salesInvoiceExportVO.setSalesInvoiceExportTermsVO(salesInvoiceExportTermsVOs);
		}
		salesInvoiceExportVO.setSalesInvoiceExportTermsVO(salesInvoiceExportTermsVOs);
	}

	@Override
	public String getSalesInvoiceExportDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "SIE";
		String result = salesInvoiceExportRepo.getSalesInvoiceExportDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	public SalesInvoiceExportVO getSalesInvoiceExportById(Long id) {

		return salesInvoiceExportRepo.getSalesInvoiceExportById(id);
	}

	@Override
	public List<SalesInvoiceExportVO> getAllSalesInvoiceExport(Long orgId, String finYear, String branchCode) {
		return salesInvoiceExportRepo.getAllSalesInvoiceExport(orgId, finYear, branchCode);
	}

	@Override
	public List<Map<String, Object>> findByCustomerNameFromPartyMasterSalesInvoiceExport(Long orgId, String partyName) {
		Set<Object[]> chType = salesInvoiceExportRepo.findByCustomerNameFromPartyMasterSalesInvoiceExport(orgId,
				partyName);
		return findByCustomerNameFromPartyMaster(chType);
	}

	private List<Map<String, Object>> findByCustomerNameFromPartyMaster(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("customerName", ch[0] != null ? ch[0].toString() : "");
			map.put("currency", ch[1] != null ? ch[1].toString() : "");
			map.put("city", ch[2] != null ? ch[2].toString() : "");
			map.put("billingAddress", ch[3] != null ? ch[3].toString() : "");
			map.put("exchangeRate", ch[4] != null ? ch[4].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> findByShippingFromPartySalesInvoiceExp(Long orgId, String customerName) {
		Set<Object[]> chType = salesInvoiceExportRepo.findByShippingFromPartySalesInvoiceExp(orgId, customerName);
		return findByShipping(chType);
	}

	private List<Map<String, Object>> findByShipping(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("shippingAddress", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getSalesOrderNumber(Long orgId, String customerName) {
		Set<Object[]> chType = salesInvoiceExportRepo.getSalesOrderNumber(orgId, customerName);
		return getSalesOrder(chType);
	}

	private List<Map<String, Object>> getSalesOrder(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("saleOrderNumber", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getexportpackinglistNumber(Long orgId, String customerName, String salesOrderNo) {
		Set<Object[]> chType = salesInvoiceExportRepo.getexportpackinglistNumber(orgId, customerName, salesOrderNo);
		return getexportpackinglist(chType);
	}

	private List<Map<String, Object>> getexportpackinglist(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("exportPackingList", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPartNoFromexportpackinglist(Long orgId, String salesOrderNo,
			String exportPackingListNo) {
		Set<Object[]> chType = salesInvoiceExportRepo.getPartNoFromexportpackinglist(orgId, salesOrderNo,
				exportPackingListNo);
		return getPartNoFromexport(chType);
	}

	private List<Map<String, Object>> getPartNoFromexport(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("partNo", ch[0] != null ? ch[0].toString() : "");
			map.put("partDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("units", ch[2] != null ? ch[2].toString() : "");
			map.put("qty", ch[3] != null ? ch[3].toString() : "");
			map.put("rate", ch[4] != null ? ch[4].toString() : "");
			List1.add(map);
		}
		return List1;
	}

//	@Override
//	public List<Map<String, Object>> getSalesInvoiceExportsReport(Long orgId, String fromDate, String toDate,
//			String customername) {
//		Set<Object[]> repType = salesInvoiceExportRepo.getSalesInvoiceExportsReport(orgId, fromDate, toDate,
//				customername);
//		return getSalesInvoiceExportsReport(repType);
//	}
//
//	private List<Map<String, Object>> getSalesInvoiceExportsReport(Set<Object[]> repType) {
//		List<Map<String, Object>> List = new ArrayList<>();
//		for (Object[] re : repType) {
//			Map<String, Object> map = new HashMap<>();
//
//			map.put("docid", re[0] != null ? re[0].toString() : "");
//			map.put("docdate", re[1] != null ? re[1].toString() : "");
//			map.put("customername", re[2] != null ? re[2].toString() : "");
//			map.put("salesorderno", re[3] != null ? re[3].toString() : "");
//			map.put("exportpackingno", re[4] != null ? re[4].toString() : "");
//			map.put("currency", re[5] != null ? re[5].toString() : "");
//			map.put("exchangerate", re[6] != null ? re[6].toString() : "");
//			map.put("location", re[7] != null ? re[7].toString() : "");
//			map.put("billingaddress", re[8] != null ? re[8].toString() : "");
//			map.put("shippingaddress", re[9] != null ? re[9].toString() : "");
//			map.put("salesInvoiceExportId", re[10] != null ? re[10].toString() : "");
//			List.add(map);
//		}
//		return List;
//	}

	@Override
	public List<Map<String, Object>> getSalesOrderDetails(Long orgId, String customerName, String fromDate,
			String toDate, String branchCode) {
		Set<Object[]> chType = salesRepo.getSalesOrderDetails(orgId, customerName, fromDate, toDate, branchCode);
		return getSalesOrderDetails(chType);
	}

	private List<Map<String, Object>> getSalesOrderDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("salesId", ch[0] != null ? ch[0].toString() : ""); // 0
			map.put("docId", ch[1] != null ? ch[1].toString() : ""); // 1
			map.put("docDate", ch[2] != null ? ch[2].toString() : ""); // 2
			map.put("contactPerson", ch[3] != null ? ch[3].toString() : ""); // 3
			map.put("currency", ch[4] != null ? ch[4].toString() : ""); // 4
			map.put("customerCode", ch[5] != null ? ch[5].toString() : ""); // 5
			map.put("customerMail", ch[6] != null ? ch[6].toString() : ""); // 6
			map.put("customerName", ch[7] != null ? ch[7].toString() : ""); // 7
			map.put("customerPoNo", ch[8] != null ? ch[8].toString() : ""); // 8
			map.put("billingAddress", ch[9] != null ? ch[9].toString() : ""); // 9
			map.put("placeOfSupply", ch[10] != null ? ch[10].toString() : ""); // 10
			map.put("shippingAddress", ch[11] != null ? ch[11].toString() : ""); // 11
			map.put("workOrderNo", ch[12] != null ? ch[12].toString() : ""); // 12
			map.put("partNo", ch[13] != null ? ch[13].toString() : ""); // 13
			map.put("partDesc", ch[14] != null ? ch[14].toString() : ""); // 14

			map.put("unitPrice", ch[15] != null ? new BigDecimal(ch[15].toString()) : BigDecimal.ZERO); // 15
			map.put("qtyOffered", ch[16] != null ? new BigDecimal(ch[16].toString()) : BigDecimal.ZERO); // 16
			map.put("basicAmount", ch[17] != null ? new BigDecimal(ch[17].toString()) : BigDecimal.ZERO); // 17
			map.put("igst", ch[18] != null ? new BigDecimal(ch[18].toString()) : BigDecimal.ZERO); // 18
			map.put("cgst", ch[19] != null ? new BigDecimal(ch[19].toString()) : BigDecimal.ZERO); // 19
			map.put("sgst", ch[20] != null ? new BigDecimal(ch[20].toString()) : BigDecimal.ZERO); // 20
			map.put("taxAmount", ch[21] != null ? new BigDecimal(ch[21].toString()) : BigDecimal.ZERO); // 21
			map.put("discount", ch[22] != null ? new BigDecimal(ch[22].toString()) : BigDecimal.ZERO); // 22
			map.put("discountAmount", ch[23] != null ? new BigDecimal(ch[23].toString()) : BigDecimal.ZERO); // 23
			map.put("totalAmount", ch[24] != null ? new BigDecimal(ch[24].toString()) : BigDecimal.ZERO); // 24

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getSalesOrderSummaryDetails(Long orgId, String customerName, String fromDate,
			String toDate, String branchCode) {
		Set<Object[]> chType = salesRepo.getSalesOrderSummaryDetails(orgId, customerName, fromDate, toDate, branchCode);
		return getSalesOrderSummaryDetails(chType);
	}

	private List<Map<String, Object>> getSalesOrderSummaryDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("salesId", ch[0] != null ? ch[0].toString() : ""); // 0
			map.put("docId", ch[1] != null ? ch[1].toString() : ""); // 1
			map.put("docDate", ch[2] != null ? ch[2].toString() : ""); // 2
			map.put("billingAddress", ch[3] != null ? ch[3].toString() : ""); // 3
			map.put("currency", ch[4] != null ? ch[4].toString() : ""); // 4
			map.put("customerCode", ch[5] != null ? ch[5].toString() : ""); // 5
			map.put("customerName", ch[6] != null ? ch[6].toString() : ""); // 6
			map.put("customerMail", ch[7] != null ? ch[7].toString() : ""); // 7
			map.put("customerPoNo", ch[8] != null ? ch[8].toString() : ""); // 8
			map.put("invoiceType", ch[9] != null ? ch[9].toString() : ""); // 9
			map.put("placeOfSupply", ch[10] != null ? ch[10].toString() : ""); // 10
			map.put("shippingAddress", ch[11] != null ? ch[11].toString() : ""); // 11
			map.put("taxType", ch[12] != null ? ch[12].toString() : ""); // 12

			map.put("grossAmount", ch[13] != null ? new BigDecimal(ch[13].toString()) : BigDecimal.ZERO); // 13
			map.put("totalTaxAmount", ch[14] != null ? new BigDecimal(ch[14].toString()) : BigDecimal.ZERO); // 14
			map.put("netAmount", ch[15] != null ? new BigDecimal(ch[15].toString()) : BigDecimal.ZERO); // 15
			map.put("igst", ch[16] != null ? new BigDecimal(ch[16].toString()) : BigDecimal.ZERO); // 16
			map.put("cgst", ch[17] != null ? new BigDecimal(ch[17].toString()) : BigDecimal.ZERO); // 17
			map.put("sgst", ch[18] != null ? new BigDecimal(ch[18].toString()) : BigDecimal.ZERO); // 18
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getSalesInvoiceExportDetails(Long orgId, String customerName, String fromDate,
			String toDate, String branchCode) {
		Set<Object[]> chType = salesInvoiceExportRepo.getSalesInvoiceExportDetails(orgId, customerName, fromDate,
				toDate, branchCode);
		return getSalesInvoiceExportDetails(chType);
	}

	private List<Map<String, Object>> getSalesInvoiceExportDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("salesInvoiceExportId", ch[0] != null ? ch[0].toString() : "");
			map.put("docId", ch[1] != null ? ch[1].toString() : "");
			map.put("docDate", ch[2] != null ? ch[2].toString() : ""); // 2
			map.put("billingAddress", ch[3] != null ? ch[3].toString() : ""); // 3
			map.put("currency", ch[4] != null ? ch[4].toString() : ""); // 4
			map.put("customerName", ch[5] != null ? ch[5].toString() : ""); // 5
			map.put("exchangeRate", ch[6] != null ? new BigDecimal(ch[6].toString()) : BigDecimal.ZERO); // 6
			map.put("location", ch[7] != null ? ch[7].toString() : ""); // 7
			map.put("salesOrderNo", ch[8] != null ? ch[8].toString() : ""); // 8
			map.put("exportPackingNo", ch[9] != null ? ch[9].toString() : ""); // 9
			map.put("item", ch[10] != null ? ch[10].toString() : ""); // 10
			map.put("itemDesc", ch[11] != null ? ch[11].toString() : ""); // 11

			map.put("rate", ch[12] != null ? new BigDecimal(ch[12].toString()) : BigDecimal.ZERO); // 12
			map.put("qty", ch[13] != null ? new BigDecimal(ch[13].toString()) : BigDecimal.ZERO); // 13
			map.put("grossAmount", ch[14] != null ? new BigDecimal(ch[14].toString()) : BigDecimal.ZERO); // 14
			map.put("discount", ch[15] != null ? new BigDecimal(ch[15].toString()) : BigDecimal.ZERO); // 15
			map.put("discountAmount", ch[16] != null ? new BigDecimal(ch[16].toString()) : BigDecimal.ZERO); // 16
			map.put("netAmount", ch[17] != null ? new BigDecimal(ch[17].toString()) : BigDecimal.ZERO); // 17

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getSalesInvoiceExportSummaryDetails(Long orgId, String customerName,
			String fromDate, String toDate, String branchCode) {
		Set<Object[]> chType = salesInvoiceExportRepo.getSalesInvoiceExportSummaryDetails(orgId, customerName, fromDate,
				toDate, branchCode);
		return getSalesInvoiceExportSummaryDetails(chType);
	}

	private List<Map<String, Object>> getSalesInvoiceExportSummaryDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("salesInvoiceExportId", ch[0] != null ? ch[0].toString() : ""); // 0
			map.put("docId", ch[1] != null ? ch[1].toString() : ""); // 1
			map.put("docDate", ch[2] != null ? ch[2].toString() : ""); // 2
			map.put("billingAddress", ch[3] != null ? ch[3].toString() : ""); // 3
			map.put("currency", ch[4] != null ? ch[4].toString() : ""); // 4

			map.put("exchangeRate", ch[5] != null ? new BigDecimal(ch[5].toString()) : BigDecimal.ZERO); // 5
			map.put("customerName", ch[6] != null ? ch[6].toString() : ""); // 6
			map.put("exportPackingNo", ch[7] != null ? ch[7].toString() : ""); // 7
			map.put("location", ch[8] != null ? ch[8].toString() : ""); // 8
			map.put("salesOrderNo", ch[9] != null ? ch[9].toString() : ""); // 9

			map.put("totalQty", ch[10] != null ? new BigDecimal(ch[10].toString()) : BigDecimal.ZERO); // 10
			map.put("totalGrossAmount", ch[11] != null ? new BigDecimal(ch[11].toString()) : BigDecimal.ZERO); // 11
			map.put("totalDiscountAmount", ch[12] != null ? new BigDecimal(ch[12].toString()) : BigDecimal.ZERO); // 12
			map.put("totalAmount", ch[13] != null ? new BigDecimal(ch[13].toString()) : BigDecimal.ZERO); // 13

			List1.add(map);
		}
		return List1;
	}

}
