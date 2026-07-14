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
import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestParam;

import com.efitops.basesetup.dto.DeliveryChalanForFgDTO;
import com.efitops.basesetup.dto.DeliveryChallanForFgDetailsDTO;
import com.efitops.basesetup.dto.SalesInvoiceLocalDTO;
import com.efitops.basesetup.dto.SalesInvoiceLocalDetailsDTO;
import com.efitops.basesetup.dto.SalesInvoiceLocalTermsDTO;
import com.efitops.basesetup.dto.SalesReturnExportDTO;
import com.efitops.basesetup.dto.SalesReturnExportDetailsDTO;
import com.efitops.basesetup.dto.SalesReturnExportTermsDTO;
import com.efitops.basesetup.dto.SalesReturnLocalDTO;
import com.efitops.basesetup.dto.SalesReturnLocalDetailsDTO;
import com.efitops.basesetup.entity.DeliveryChalanForFgVO;
import com.efitops.basesetup.entity.DeliveryChallanForFgDetailsVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.SalesInvoiceLocalDetailsVO;
import com.efitops.basesetup.entity.SalesInvoiceLocalTermsVO;
import com.efitops.basesetup.entity.SalesInvoiceLocalVO;
import com.efitops.basesetup.entity.SalesReturnExportDetailsVO;
import com.efitops.basesetup.entity.SalesReturnExportTermsVO;
import com.efitops.basesetup.entity.SalesReturnExportVO;
import com.efitops.basesetup.entity.SalesReturnLocalDetailsVO;
import com.efitops.basesetup.entity.SalesReturnLocalVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DeliveryChalanForFgRepo;
import com.efitops.basesetup.repo.DeliveryChallanForFgDetailsRepo;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.SalesInvoiceLocalDetailsRepo;
import com.efitops.basesetup.repo.SalesInvoiceLocalRepo;
import com.efitops.basesetup.repo.SalesInvoiceLocalTermsRepo;
import com.efitops.basesetup.repo.SalesReturnExportDetailsRepo;
import com.efitops.basesetup.repo.SalesReturnExportRepo;
import com.efitops.basesetup.repo.SalesReturnExportTermsRepo;
import com.efitops.basesetup.repo.SalesReturnLocalDetailsRepo;
import com.efitops.basesetup.repo.SalesReturnLocalRepo;

@Service
public class SalesServiceImpl implements SalesService {


	public static final Logger LOGGER = LoggerFactory.getLogger(SalesServiceImpl.class);

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	AmountInWordsConverterService amountInWordsConverterService;

	@Autowired
	DeliveryChalanForFgRepo deliveryChalanForFgRepo;

	@Autowired
	DeliveryChallanForFgDetailsRepo deliveryChallanForFgDetailsRepo;

	@Autowired
	SalesInvoiceLocalRepo salesInvoiceLocalRepo;

	@Autowired
	SalesInvoiceLocalDetailsRepo salesInvoiceLocalDetailsRepo;

	@Autowired
	SalesInvoiceLocalTermsRepo salesInvoiceLocalTermsRepo;

	@Autowired
	SalesReturnLocalRepo salesReturnLocalRepo;

	@Autowired
	SalesReturnLocalDetailsRepo salesReturnLocalDetailsRepo;

	@Autowired
	SalesReturnExportRepo salesReturnExportRepo;

	@Autowired
	SalesReturnExportDetailsRepo salesReturnExportDetailsRepo;

	@Autowired
	SalesReturnExportTermsRepo salesReturnExportTermsRepo;
	
	@Autowired
	private CommonNotificationService commonNotificationService;
	
	@PersistenceContext
	private EntityManager entityManager;


	// DeliveryChalanForFg

//	@Override
//	public Map<String, Object> createUpdateDeliveryChalanForFg(DeliveryChalanForFgDTO deliveryChalanForFgDTO)
//			throws ApplicationException {
//		DeliveryChalanForFgVO deliveryChalanForFgVO = new DeliveryChalanForFgVO();
//		String message;
//		String screenCode = "DCF";
//		if (ObjectUtils.isNotEmpty(deliveryChalanForFgDTO.getId())) {
//			deliveryChalanForFgVO = deliveryChalanForFgRepo.findById(deliveryChalanForFgDTO.getId())
//					.orElseThrow(() -> new ApplicationException("Invalid DeliveryChalanForFg details"));
//			message = "DeliveryChalanForFg Updated Successfully";
//			deliveryChalanForFgVO.setUpdatedBy(deliveryChalanForFgDTO.getCreatedBy());
//
//		} else {
//
//			String docId = deliveryChalanForFgRepo.getDeliveryChalanForFgDocId(deliveryChalanForFgDTO.getOrgId(), deliveryChalanForFgDTO.getFinYear(),
//					deliveryChalanForFgDTO.getBranchCode(), screenCode);
//			deliveryChalanForFgVO.setDocId(docId);
//
//			// GETDOCID LASTNO +1
//			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
//					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(deliveryChalanForFgDTO.getOrgId(), deliveryChalanForFgDTO.getFinYear(),
//							deliveryChalanForFgDTO.getBranchCode(), screenCode);
//			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
//			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
//
//			deliveryChalanForFgVO.setCreatedBy(deliveryChalanForFgDTO.getCreatedBy());
//			deliveryChalanForFgVO.setUpdatedBy(deliveryChalanForFgDTO.getCreatedBy());
//
//			message = "DeliveryChalanForFg Created Successfully";
//		}
//		createUpdatedDeliveryChalanForFgVOFromDeliveryChalanForFgDTO(deliveryChalanForFgDTO, deliveryChalanForFgVO);
//		deliveryChalanForFgRepo.save(deliveryChalanForFgVO);
//		Map<String, Object> response = new HashMap<>();
//		response.put("deliveryChalanForFgVO", deliveryChalanForFgVO);
//		response.put("message", message);
//		return response;
//	}
//
//	private void createUpdatedDeliveryChalanForFgVOFromDeliveryChalanForFgDTO(
//			DeliveryChalanForFgDTO deliveryChalanForFgDTO, DeliveryChalanForFgVO deliveryChalanForFgVO) {
//		deliveryChalanForFgVO.setCustomerName(deliveryChalanForFgDTO.getCustomerName());
//		deliveryChalanForFgVO.setCustomerAddress(deliveryChalanForFgDTO.getCustomerAddress());
//		deliveryChalanForFgVO.setSoNo(deliveryChalanForFgDTO.getSoNo());
//		deliveryChalanForFgVO.setSoDate(deliveryChalanForFgDTO.getSoDate());
//		deliveryChalanForFgVO.setDuDate(deliveryChalanForFgDTO.getDuDate());
//		deliveryChalanForFgVO.setVehicleType(deliveryChalanForFgDTO.getVehicleType());
//		deliveryChalanForFgVO.setVehicleNo(deliveryChalanForFgDTO.getVehicleNo());
//
//		// Summary
//		deliveryChalanForFgVO.setNaration(deliveryChalanForFgDTO.getNaration());
//
//		deliveryChalanForFgVO.setOrgId(deliveryChalanForFgDTO.getOrgId());
//		deliveryChalanForFgVO.setBranch(deliveryChalanForFgDTO.getBranch());
//		deliveryChalanForFgVO.setBranchCode(deliveryChalanForFgDTO.getBranchCode());
//		deliveryChalanForFgVO.setFinYear(deliveryChalanForFgDTO.getFinYear());
//		deliveryChalanForFgVO.setActive(deliveryChalanForFgDTO.isActive());
//		deliveryChalanForFgVO.setCreatedBy(deliveryChalanForFgDTO.getCreatedBy());
//
//		if (ObjectUtils.isNotEmpty(deliveryChalanForFgDTO.getId())) {
//			List<DeliveryChallanForFgDetailsVO> deliveryChallanForFgDetailsVO1 = deliveryChallanForFgDetailsRepo
//					.findByDeliveryChalanForFgVO(deliveryChalanForFgVO);
//			deliveryChallanForFgDetailsRepo.deleteAll(deliveryChallanForFgDetailsVO1);
//
//		}
//
//		List<DeliveryChallanForFgDetailsVO> deliveryChallanForFgDetailsVOs = new ArrayList<>();
//		for (DeliveryChallanForFgDetailsDTO deliveryChallanForFgDetailsDTO : deliveryChalanForFgDTO
//				.getDeliveryChallanForFgDetailsDTO()) {
//			DeliveryChallanForFgDetailsVO deliveryChallanForFgDetailsVO = new DeliveryChallanForFgDetailsVO();
//			deliveryChallanForFgDetailsVO.setItemNo(deliveryChallanForFgDetailsDTO.getItemNo());
//			deliveryChallanForFgDetailsVO.setItemDescription(deliveryChallanForFgDetailsDTO.getItemDescription());
//			deliveryChallanForFgDetailsVO.setQuantity(deliveryChallanForFgDetailsDTO.getQuantity());
//			deliveryChallanForFgDetailsVO.setUnit(deliveryChallanForFgDetailsDTO.getUnit());
//			deliveryChallanForFgDetailsVO.setWeight(deliveryChallanForFgDetailsDTO.getWeight());
//			deliveryChallanForFgDetailsVO.setRemarks(deliveryChallanForFgDetailsDTO.getRemarks());
//			deliveryChallanForFgDetailsVO.setDeliveryChalanForFgVO(deliveryChalanForFgVO);
//			deliveryChallanForFgDetailsVOs.add(deliveryChallanForFgDetailsVO);
//		}
//		deliveryChalanForFgVO.setDeliveryChallanForFgDetailsVO(deliveryChallanForFgDetailsVOs);
//	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateDeliveryChalanForFg(DeliveryChalanForFgDTO dto) throws ApplicationException {

		DeliveryChalanForFgVO vo = new DeliveryChalanForFgVO();
		String message;
		String screenCode = "DCFG";
		DeliveryChalanForFgVO oldDeliveryChalanForFg   = null;


		/* ===================== UPDATE ===================== */
		if (ObjectUtils.isNotEmpty(dto.getId())) {
			
			oldDeliveryChalanForFg = deliveryChalanForFgRepo.findById(dto.getId())
		            .orElseThrow(() -> new ApplicationException("Delivery Challan For FG not found"));

			oldDeliveryChalanForFg.getDeliveryChallanForFgDetailsVO().size(); // load
			
			
		    entityManager.detach(oldDeliveryChalanForFg); // detach snapshot

			vo = deliveryChalanForFgRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Delivery Challan For FG details"));

			vo.setUpdatedBy(dto.getCreatedBy());
			mapDtoToVo(dto, vo);

			message = "DeliveryChalanForFg Updated Successfully";
		}
		/* ===================== CREATE ===================== */
		else {

			String docId = deliveryChalanForFgRepo.getDeliveryChalanForFgDocId(dto.getOrgId(), dto.getFinYear(),
					dto.getBranchCode(), screenCode);

			if (docId == null) {
				throw new ApplicationException("Failed to generate Delivery Challan document number");
			}

			vo.setDocId(docId);

			DocumentTypeMappingDetailsVO docMap = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(dto.getOrgId(), dto.getFinYear(),
							dto.getBranchCode(), screenCode);

			if (docMap == null) {
				throw new ApplicationException("Document type mapping not found for DCF");
			}

			docMap.setLastno(docMap.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(docMap);

			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());

			mapDtoToVo(dto, vo);

			message = "DeliveryChalanForFg Created Successfully";
		}

		DeliveryChalanForFgVO savedVO = deliveryChalanForFgRepo.save(vo);
		commonNotificationService.generateNotification(vo.getScreenCode(), vo.getId(), oldDeliveryChalanForFg, vo);


		Map<String, Object> response = new HashMap<>();
		response.put("deliveryChalanForFgVO", savedVO);
		response.put("message", message);

		return response;
	}

	private void mapDtoToVo(DeliveryChalanForFgDTO dto, DeliveryChalanForFgVO vo) throws ApplicationException {

		vo.setCustomerName(dto.getCustomerName());
		vo.setCustomerAddress(dto.getCustomerAddress());
		vo.setSoNo(dto.getSoNo());
		vo.setSoDate(dto.getSoDate());
		vo.setDuDate(dto.getDuDate());
		vo.setVehicleType(dto.getVehicleType());
		vo.setVehicleNo(dto.getVehicleNo());
		vo.setNaration(dto.getNaration());

		vo.setOrgId(dto.getOrgId());
		vo.setBranch(dto.getBranch());
		vo.setBranchCode(dto.getBranchCode());
		vo.setFinYear(dto.getFinYear());
		vo.setActive(dto.isActive());
		vo.setCreatedBy(dto.getCreatedBy());

		/* ========== DELETE OLD DETAILS (UPDATE CASE) ========== */
		if (dto.getId() != null) {
			List<DeliveryChallanForFgDetailsVO> oldList = deliveryChallanForFgDetailsRepo
					.findByDeliveryChalanForFgVO(vo);
			deliveryChallanForFgDetailsRepo.deleteAll(oldList);
		}

		/* ========== ADD DETAILS ========== */
		if (CollectionUtils.isEmpty(dto.getDeliveryChallanForFgDetailsDTO())) {
			throw new ApplicationException("Delivery Challan details cannot be empty");
		}

		List<DeliveryChallanForFgDetailsVO> detailsList = new ArrayList<>();

		for (DeliveryChallanForFgDetailsDTO d : dto.getDeliveryChallanForFgDetailsDTO()) {

			DeliveryChallanForFgDetailsVO detailsVO = new DeliveryChallanForFgDetailsVO();

			detailsVO.setItemNo(d.getItemNo());
			detailsVO.setItemDescription(d.getItemDescription());
			detailsVO.setQuantity(d.getQuantity());
			detailsVO.setUnit(d.getUnit());
			detailsVO.setWeight(d.getWeight());
			detailsVO.setRemarks(d.getRemarks());
			detailsVO.setSalesOrderNo(d.getSalesOrderNo());
			detailsVO.setDeliveryChalanForFgVO(vo);

			detailsList.add(detailsVO);
		}

		vo.setDeliveryChallanForFgDetailsVO(detailsList);
	}

	@Override
	public List<DeliveryChalanForFgVO> getAllDeliveryChalanForFgByOrgId(Long orgId, String finYear, String branchCode) {

		return deliveryChalanForFgRepo.getAllDeliveryChalanForFgByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public DeliveryChalanForFgVO getDeliveryChalanForFgById(Long id) {

		return deliveryChalanForFgRepo.getDeliveryChalanForFgById(id);
	}

	@Override
	public String getDeliveryChalanForFgDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "DCFG";
		String result = deliveryChalanForFgRepo.getDeliveryChalanForFgDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getCustomerNameFromPartyMaster(Long orgId) {
		Set<Object[]> chType = deliveryChalanForFgRepo.getCustomerNameFromPartyMaster(orgId);
		return getgetCustomerName(chType);
	}

	private List<Map<String, Object>> getgetCustomerName(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("customerName", ch[0] != null ? ch[0].toString() : "");
			map.put("customerAddress", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getSoNoFromSaleOrder(Long orgId, String customerName) {
		Set<Object[]> chType = deliveryChalanForFgRepo.getSoNoFromSaleOrder(orgId, customerName);
		return getSoNo(chType);
	}

	private List<Map<String, Object>> getSoNo(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("soNo", ch[0] != null ? ch[0].toString() : "");
			map.put("soDate", ch[1] != null ? ch[1].toString() : "");
			map.put("dueDate", ch[2] != null ? ch[2].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getItemDetailsforDCFGFromSaleOrder(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String finYear, @RequestParam String salesOrderNo) {

		// split comma separated sales order numbers
		List<String> salesOrderNos = Arrays.stream(salesOrderNo.split(",")).map(String::trim).filter(s -> !s.isEmpty())
				.collect(Collectors.toList());

		Set<Object[]> chType = deliveryChalanForFgRepo.getItemDetailsforDCFGFromSaleOrder(orgId, branchCode, finYear,
				salesOrderNos);

		return getItemDetailsforDCFGFromSaleOrder(chType);
	}

	private List<Map<String, Object>> getItemDetailsforDCFGFromSaleOrder(Set<Object[]> chType) {
		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("partNo", ch[0] != null ? ch[0].toString() : "");
			map.put("partDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("qtyOffered", ch[2] != null ? ch[2].toString() : "");
			map.put("unitPrice", ch[3] != null ? ch[3].toString() : "");
			map.put("salesOrderNo", ch[4] != null ? ch[4].toString() : "");

			list.add(map);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getItemNameFromSaleOrder(String customerName, String customerCode) {
		Set<Object[]> chType = deliveryChalanForFgRepo.getItemNameFromSaleOrder(customerName, customerCode);
		return getItemName(chType);
	}

	private List<Map<String, Object>> getItemName(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemName", ch[0] != null ? ch[0].toString() : "");
			map.put("itemDescription", ch[1] != null ? ch[1].toString() : "");
			map.put("quantity", ch[2] != null ? ch[2].toString() : "");
			map.put("unit", ch[3] != null ? ch[3].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	// SalesInvoiceLocal

	@Override
	public Map<String, Object> createUpdateSalesInvoiceLocal(SalesInvoiceLocalDTO salesInvoiceLocalDTO)
			throws ApplicationException {
		SalesInvoiceLocalVO salesInvoiceLocalVO = new SalesInvoiceLocalVO();
		String message;
		String screenCode = "SIL";
		SalesInvoiceLocalVO oldSalesInvoiceLocal   = null;

		
		if (ObjectUtils.isNotEmpty(salesInvoiceLocalDTO.getId())) {
			oldSalesInvoiceLocal = salesInvoiceLocalRepo.findById(salesInvoiceLocalDTO.getId())
		            .orElseThrow(() -> new ApplicationException("SalesInvoiceLocal  not found"));

			oldSalesInvoiceLocal.getSalesInvoiceLocalDetailsVO().size(); // load
			
			
		    entityManager.detach(oldSalesInvoiceLocal); // detach snapshot
			
			
			salesInvoiceLocalVO = salesInvoiceLocalRepo.findById(salesInvoiceLocalDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid SalesInvoiceLocal details"));
			message = "SalesInvoiceLocal Updated Successfully";
			salesInvoiceLocalVO.setUpdatedBy(salesInvoiceLocalDTO.getCreatedBy());

		} else {

			String docId = salesInvoiceLocalRepo.getSalesInvoiceLocalDocId(salesInvoiceLocalDTO.getOrgId(),
					salesInvoiceLocalDTO.getFinYear(), salesInvoiceLocalDTO.getBranchCode(), screenCode);
			salesInvoiceLocalVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(salesInvoiceLocalDTO.getOrgId(),
							salesInvoiceLocalDTO.getFinYear(), salesInvoiceLocalDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			salesInvoiceLocalVO.setCreatedBy(salesInvoiceLocalDTO.getCreatedBy());
			salesInvoiceLocalVO.setUpdatedBy(salesInvoiceLocalDTO.getCreatedBy());

			message = "SalesInvoiceLocal Created Successfully";
		}
		createUpdatedSalesInvoiceLocalVOFromSalesInvoiceLocalDTO(salesInvoiceLocalDTO, salesInvoiceLocalVO);
		salesInvoiceLocalRepo.save(salesInvoiceLocalVO);
		commonNotificationService.generateNotification(salesInvoiceLocalVO.getScreenCode(), salesInvoiceLocalVO.getId(), oldSalesInvoiceLocal, salesInvoiceLocalVO);

		Map<String, Object> response = new HashMap<>();
		response.put("salesInvoiceLocalVO", salesInvoiceLocalVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedSalesInvoiceLocalVOFromSalesInvoiceLocalDTO(SalesInvoiceLocalDTO salesInvoiceLocalDTO,
			SalesInvoiceLocalVO salesInvoiceLocalVO) {
		salesInvoiceLocalVO.setCustomerName(salesInvoiceLocalDTO.getCustomerName());
		salesInvoiceLocalVO.setPackingListNo(salesInvoiceLocalDTO.getPackingListNo());
		salesInvoiceLocalVO.setSalesOrderNo(salesInvoiceLocalDTO.getSalesOrderNo());
		salesInvoiceLocalVO.setGstNo(salesInvoiceLocalDTO.getGstNo());
		salesInvoiceLocalVO.setCurrency(salesInvoiceLocalDTO.getCurrency());
		salesInvoiceLocalVO.setExchangeRate(salesInvoiceLocalDTO.getExchangeRate());
		salesInvoiceLocalVO.setLocation(salesInvoiceLocalDTO.getLocation());
		salesInvoiceLocalVO.setBillingAddress(salesInvoiceLocalDTO.getBillingAddress());
		salesInvoiceLocalVO.setShippingAddress(salesInvoiceLocalDTO.getShippingAddress());
		salesInvoiceLocalVO.setTaxType(salesInvoiceLocalDTO.getTaxType());
		salesInvoiceLocalVO.setRemarks(salesInvoiceLocalDTO.getRemarks());
		salesInvoiceLocalVO.setOrgId(salesInvoiceLocalDTO.getOrgId());
		salesInvoiceLocalVO.setBranch(salesInvoiceLocalDTO.getBranch());
		salesInvoiceLocalVO.setBranchCode(salesInvoiceLocalDTO.getBranchCode());
		salesInvoiceLocalVO.setFinYear(salesInvoiceLocalDTO.getFinYear());
		salesInvoiceLocalVO.setCreatedBy(salesInvoiceLocalDTO.getCreatedBy());
		salesInvoiceLocalVO.setTerms(salesInvoiceLocalDTO.getTerms());
		salesInvoiceLocalVO.setDescription(salesInvoiceLocalDTO.getDescription());

		BigDecimal grossAmount = BigDecimal.ZERO;
		BigDecimal totalTaxAmount = BigDecimal.ZERO;
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal totalTaxableamount = BigDecimal.ZERO;

		if (ObjectUtils.isNotEmpty(salesInvoiceLocalDTO.getId())) {
			List<SalesInvoiceLocalDetailsVO> salesInvoiceLocalDetailsVO1 = salesInvoiceLocalDetailsRepo
					.findBySalesInvoiceLocalVO(salesInvoiceLocalVO);
			salesInvoiceLocalDetailsRepo.deleteAll(salesInvoiceLocalDetailsVO1);

//			List<SalesInvoiceLocalTermsVO> salesInvoiceLocalTermsVO1 = salesInvoiceLocalTermsRepo
//					.findBySalesInvoiceLocalVO(salesInvoiceLocalVO);
//			salesInvoiceLocalTermsRepo.deleteAll(salesInvoiceLocalTermsVO1);
		}

		List<SalesInvoiceLocalDetailsVO> salesInvoiceLocalDetailsVOs = new ArrayList<>();
		for (SalesInvoiceLocalDetailsDTO salesInvoiceLocalDetailsDTO : salesInvoiceLocalDTO
				.getSalesInvoiceLocalDetailsDTO()) {
			SalesInvoiceLocalDetailsVO salesInvoiceLocalDetailsVO = new SalesInvoiceLocalDetailsVO();
			salesInvoiceLocalDetailsVO.setItem(salesInvoiceLocalDetailsDTO.getItem());
			salesInvoiceLocalDetailsVO.setItemDesc(salesInvoiceLocalDetailsDTO.getItemDesc());
			salesInvoiceLocalDetailsVO.setUnits(salesInvoiceLocalDetailsDTO.getUnits());
			salesInvoiceLocalDetailsVO.setAvlStkQty(salesInvoiceLocalDetailsDTO.getAvlStkQty());
			salesInvoiceLocalDetailsVO.setQty(salesInvoiceLocalDetailsDTO.getQty());
			salesInvoiceLocalDetailsVO.setRate(salesInvoiceLocalDetailsDTO.getRate());
			salesInvoiceLocalDetailsVO.setTaxCode(salesInvoiceLocalDetailsDTO.getTaxCode());
			salesInvoiceLocalDetailsVO.setDiscount(salesInvoiceLocalDetailsDTO.getDiscount());

			BigDecimal discountAmount = BigDecimal.ZERO;
			BigDecimal netAmount = BigDecimal.ZERO;
			BigDecimal taxAmountIn = BigDecimal.ZERO;
			BigDecimal landedValues = BigDecimal.ZERO;

			BigDecimal amountSet = salesInvoiceLocalDetailsDTO.getQty().multiply(salesInvoiceLocalDetailsDTO.getRate());
			salesInvoiceLocalDetailsVO.setBasicAmount(amountSet);
			grossAmount = grossAmount.add(salesInvoiceLocalDetailsVO.getBasicAmount());

			discountAmount = salesInvoiceLocalDetailsVO.getBasicAmount()
					.multiply(salesInvoiceLocalDetailsDTO.getDiscount()).divide(BigDecimal.valueOf(100));
			salesInvoiceLocalDetailsVO.setDiscountAmount(discountAmount);

			netAmount = salesInvoiceLocalDetailsVO.getBasicAmount()
					.subtract(salesInvoiceLocalDetailsVO.getDiscountAmount());

			totalTaxableamount = totalTaxableamount.add(netAmount);

			salesInvoiceLocalDetailsVO.setTaxableAmount(netAmount);

			if (salesInvoiceLocalVO.getTaxType() == null || salesInvoiceLocalVO.getTaxType().isEmpty()
					|| !salesInvoiceLocalVO.getTaxType().equalsIgnoreCase("INTER")
							&& !salesInvoiceLocalVO.getTaxType().equalsIgnoreCase("INTRA")) {

				salesInvoiceLocalDetailsVO.setSgst(BigDecimal.ZERO);
				salesInvoiceLocalDetailsVO.setCgst(BigDecimal.ZERO);
				salesInvoiceLocalDetailsVO.setIgst(BigDecimal.ZERO);
				salesInvoiceLocalDetailsVO.setTaxAmount(BigDecimal.ZERO);
			} else {
				if (salesInvoiceLocalVO.getTaxType().equalsIgnoreCase("INTER")) {

					salesInvoiceLocalDetailsVO.setIgst(salesInvoiceLocalDetailsDTO.getIgst());
					BigDecimal igstAmount = salesInvoiceLocalDetailsVO.getTaxableAmount()
							.multiply(salesInvoiceLocalDetailsDTO.getIgst()).divide(BigDecimal.valueOf(100));
					salesInvoiceLocalDetailsVO.setCgst(BigDecimal.ZERO);
					salesInvoiceLocalDetailsVO.setSgst(BigDecimal.ZERO);
					taxAmountIn = igstAmount;
					salesInvoiceLocalDetailsVO.setTaxAmount(taxAmountIn);
				} else if (salesInvoiceLocalVO.getTaxType().equalsIgnoreCase("INTRA")) {
					salesInvoiceLocalDetailsVO.setCgst(salesInvoiceLocalDetailsDTO.getCgst());
					salesInvoiceLocalDetailsVO.setSgst(salesInvoiceLocalDetailsDTO.getSgst());

					BigDecimal cgstAmount = salesInvoiceLocalDetailsDTO.getCgst()
							.multiply(salesInvoiceLocalDetailsVO.getTaxableAmount()).divide(BigDecimal.valueOf(100));
					BigDecimal sgstAmount = salesInvoiceLocalDetailsDTO.getSgst()
							.multiply(salesInvoiceLocalDetailsVO.getTaxableAmount()).divide(BigDecimal.valueOf(100));

					salesInvoiceLocalDetailsVO.setIgst(BigDecimal.ZERO);
					taxAmountIn = cgstAmount.add(sgstAmount);
					salesInvoiceLocalDetailsVO.setTaxAmount(taxAmountIn);
				}
			}

			totalTaxAmount = totalTaxAmount.add(salesInvoiceLocalDetailsVO.getTaxAmount());

			landedValues = salesInvoiceLocalDetailsVO.getTaxableAmount().add(salesInvoiceLocalDetailsVO.getTaxAmount());
			salesInvoiceLocalDetailsVO.setLandedValue(landedValues);
			totalAmount = totalAmount.add(salesInvoiceLocalDetailsVO.getLandedValue());

			salesInvoiceLocalDetailsVO.setSalesInvoiceLocalVO(salesInvoiceLocalVO);
			salesInvoiceLocalDetailsVOs.add(salesInvoiceLocalDetailsVO);
		}

		salesInvoiceLocalVO.setGrossAmount(grossAmount);
		salesInvoiceLocalVO.setTotalTaxAmount(totalTaxAmount);
		salesInvoiceLocalVO.setTotalAmount(totalAmount);
		salesInvoiceLocalVO.setTotalTaxableAmount(totalTaxableamount);
		salesInvoiceLocalVO.setTotalAmountInWords(
				amountInWordsConverterService.convert(salesInvoiceLocalVO.getTotalAmount().longValue()));
		salesInvoiceLocalVO.setSalesInvoiceLocalDetailsVO(salesInvoiceLocalDetailsVOs);
//
//		List<SalesInvoiceLocalTermsVO> salesInvoiceLocalTermsVOs = new ArrayList<>();
//		for (SalesInvoiceLocalTermsDTO salesInvoiceLocalTermsDTO : salesInvoiceLocalDTO
//				.getSalesInvoiceLocalTermsDTO()) {
//			SalesInvoiceLocalTermsVO salesInvoiceLocalTermsVO = new SalesInvoiceLocalTermsVO();
//			salesInvoiceLocalTermsVO.setTerms(salesInvoiceLocalTermsDTO.getTerms());
//			salesInvoiceLocalTermsVO.setDescription(salesInvoiceLocalTermsDTO.getDescription());
//
//			salesInvoiceLocalTermsVO.setSalesInvoiceLocalVO(salesInvoiceLocalVO);
//			salesInvoiceLocalTermsVOs.add(salesInvoiceLocalTermsVO);
//		}
//		salesInvoiceLocalVO.setSalesInvoiceLocalTermsVO(salesInvoiceLocalTermsVOs);
	}

	@Override
	public List<SalesInvoiceLocalVO> getAllSalesInvoiceLocalByOrgId(Long orgId, String finYear, String branchCode) {

		return salesInvoiceLocalRepo.getAllSalesInvoiceLocalByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public SalesInvoiceLocalVO getSalesInvoiceLocalById(Long id) {

		return salesInvoiceLocalRepo.getSalesInvoiceLocalById(id);
	}

	@Override
	public String getSalesInvoiceLocalDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "SIL";
		return salesInvoiceLocalRepo.getSalesInvoiceLocalDocId(orgId, finYear, branchCode, ScreenCode);
	}

	@Override
	public List<Map<String, Object>> getpartyNameFromPartyMaster(Long orgId) {
		Set<Object[]> chType = salesInvoiceLocalRepo.getpartyNameFromPartyMaster(orgId);
		return getpartyName(chType);
	}

	private List<Map<String, Object>> getpartyName(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("customerName", ch[0] != null ? ch[0].toString() : "");
			map.put("gstNo", ch[1] != null ? ch[1].toString() : "");
			map.put("taxType", ch[2] != null ? ch[2].toString() : "");
			map.put("currency", ch[3] != null ? ch[3].toString() : "");
			map.put("billingAddress", ch[4] != null ? ch[4].toString() : "");
			map.put("partyCode", ch[5] != null ? ch[5].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getShippingAddressFromPartyMaster(Long orgId) {
		Set<Object[]> chType = salesInvoiceLocalRepo.getShippingAddressFromPartyMaster(orgId);
		return getShippingAddress(chType);
	}

	private List<Map<String, Object>> getShippingAddress(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("shippingAddress", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getDocIdFromPackingList(Long orgId, String customerName) {
		Set<Object[]> chType = salesInvoiceLocalRepo.getDocIdFromPackingList(orgId, customerName);
		return getDocId(chType);
	}

	private List<Map<String, Object>> getDocId(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("packingListNo", ch[0] != null ? ch[0].toString() : "");
			map.put("salesOrderNo", ch[1] != null ? ch[1].toString() : "");
			map.put("location", ch[2] != null ? ch[2].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getItemNameFromPackingList(Long orgId, String packingListNo, String customerName) {
		Set<Object[]> chType = salesInvoiceLocalRepo.getItemNameFromPackingList(orgId, packingListNo, customerName);
		return getItemNameFromPacking(chType);
	}

	private List<Map<String, Object>> getItemNameFromPacking(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("item", ch[0] != null ? ch[0].toString() : "");
			map.put("itemDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("units", ch[2] != null ? ch[2].toString() : "");
			map.put("qty", ch[3] != null ? ch[3].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	// SalesReturnLocal

	@Override
	public Map<String, Object> createUpdateSalesReturnLocal(SalesReturnLocalDTO salesReturnLocalDTO)
			throws ApplicationException {
		SalesReturnLocalVO salesReturnLocalVO = new SalesReturnLocalVO();
		String message;
		String screenCode = "SRL";
		if (ObjectUtils.isNotEmpty(salesReturnLocalDTO.getId())) {
			salesReturnLocalVO = salesReturnLocalRepo.findById(salesReturnLocalDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid SalesReturnLocal details"));
			message = "SalesReturnLocal Updated Successfully";
			salesReturnLocalVO.setUpdatedBy(salesReturnLocalDTO.getCreatedBy());

		} else {

			String docId = salesReturnLocalRepo.getSalesReturnLocalDocId(salesReturnLocalDTO.getOrgId(),
					salesReturnLocalDTO.getFinYear(), salesReturnLocalDTO.getBranchCode(), screenCode);
			salesReturnLocalVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(salesReturnLocalDTO.getOrgId(),
							salesReturnLocalDTO.getFinYear(), salesReturnLocalDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			salesReturnLocalVO.setCreatedBy(salesReturnLocalDTO.getCreatedBy());
			salesReturnLocalVO.setUpdatedBy(salesReturnLocalDTO.getCreatedBy());

			message = "SalesReturnLocal Created Successfully";
		}
		createUpdatedSalesReturnLocalVOFromSalesReturnLocalDTO(salesReturnLocalDTO, salesReturnLocalVO);
		salesReturnLocalRepo.save(salesReturnLocalVO);
		Map<String, Object> response = new HashMap<>();
		response.put("salesReturnLocalVO", salesReturnLocalVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedSalesReturnLocalVOFromSalesReturnLocalDTO(SalesReturnLocalDTO salesReturnLocalDTO,
			SalesReturnLocalVO salesReturnLocalVO) {
		salesReturnLocalVO.setCustomerName(salesReturnLocalDTO.getCustomerName());
		salesReturnLocalVO.setPackingListNo(salesReturnLocalDTO.getPackingListNo());
		salesReturnLocalVO.setSalesOrderNo(salesReturnLocalDTO.getSalesOrderNo());
		salesReturnLocalVO.setGstNo(salesReturnLocalDTO.getGstNo());
		salesReturnLocalVO.setCurrency(salesReturnLocalDTO.getCurrency());
		salesReturnLocalVO.setExchangeRate(salesReturnLocalDTO.getExchangeRate());
		salesReturnLocalVO.setLocation(salesReturnLocalDTO.getLocation());
		salesReturnLocalVO.setBillingAddress(salesReturnLocalDTO.getBillingAddress());
		salesReturnLocalVO.setShippingAddress(salesReturnLocalDTO.getShippingAddress());
		salesReturnLocalVO.setSalesInvoiceLocalNo(salesReturnLocalDTO.getSalesInvoiceLocalNo());
		salesReturnLocalVO.setSalesInvoiceLocalDate(salesReturnLocalDTO.getSalesInvoiceLocalDate());
		salesReturnLocalVO.setTaxType(salesReturnLocalDTO.getTaxType());
		salesReturnLocalVO.setRemarks(salesReturnLocalDTO.getRemarks());
		salesReturnLocalVO.setOrgId(salesReturnLocalDTO.getOrgId());
		salesReturnLocalVO.setBranch(salesReturnLocalDTO.getBranch());
		salesReturnLocalVO.setBranchCode(salesReturnLocalDTO.getBranchCode());
		salesReturnLocalVO.setFinYear(salesReturnLocalDTO.getFinYear());
		salesReturnLocalVO.setCreatedBy(salesReturnLocalDTO.getCreatedBy());

		BigDecimal grossAmount = BigDecimal.ZERO;
		BigDecimal totalTaxAmount = BigDecimal.ZERO;
		BigDecimal totalAmount = BigDecimal.ZERO;

		if (ObjectUtils.isNotEmpty(salesReturnLocalDTO.getId())) {
			List<SalesReturnLocalDetailsVO> salesReturnLocalDetailsVO1 = salesReturnLocalDetailsRepo
					.findBySalesReturnLocalVO(salesReturnLocalVO);
			salesReturnLocalDetailsRepo.deleteAll(salesReturnLocalDetailsVO1);

		}

		List<SalesReturnLocalDetailsVO> SalesReturnLocalDetailsVOs = new ArrayList<>();
		for (SalesReturnLocalDetailsDTO salesReturnLocalDetailsDTO : salesReturnLocalDTO
				.getSalesReturnLocalDetailsDTO()) {
			SalesReturnLocalDetailsVO salesReturnLocalDetailsVO = new SalesReturnLocalDetailsVO();
			salesReturnLocalDetailsVO.setItem(salesReturnLocalDetailsDTO.getItem());
			salesReturnLocalDetailsVO.setItemDesc(salesReturnLocalDetailsDTO.getItemDesc());
			salesReturnLocalDetailsVO.setUnits(salesReturnLocalDetailsDTO.getUnits());
			salesReturnLocalDetailsVO.setAvlStkQty(salesReturnLocalDetailsDTO.getAvlStkQty());
			salesReturnLocalDetailsVO.setQty(salesReturnLocalDetailsDTO.getQty());
			salesReturnLocalDetailsVO.setRejectQty(salesReturnLocalDetailsDTO.getRejectQty());
			salesReturnLocalDetailsVO.setRate(salesReturnLocalDetailsDTO.getRate());
			salesReturnLocalDetailsVO.setTaxCode(salesReturnLocalDetailsDTO.getTaxCode());

			BigDecimal taxAmountIn = BigDecimal.ZERO;
			BigDecimal landedValues = BigDecimal.ZERO;

			BigDecimal amountSet = salesReturnLocalDetailsDTO.getRejectQty()
					.multiply(salesReturnLocalDetailsDTO.getRate());
			salesReturnLocalDetailsVO.setBasicAmount(amountSet);
			grossAmount = grossAmount.add(salesReturnLocalDetailsVO.getBasicAmount());

			if (salesReturnLocalVO.getTaxType() == null || salesReturnLocalVO.getTaxType().isEmpty()
					|| !salesReturnLocalVO.getTaxType().equalsIgnoreCase("INTER")
							&& !salesReturnLocalVO.getTaxType().equalsIgnoreCase("INTRA")) {

				salesReturnLocalDetailsVO.setSgst(BigDecimal.ZERO);
				salesReturnLocalDetailsVO.setCgst(BigDecimal.ZERO);
				salesReturnLocalDetailsVO.setIgst(BigDecimal.ZERO);
				salesReturnLocalDetailsVO.setTaxAmount(BigDecimal.ZERO);
			} else {
				if (salesReturnLocalVO.getTaxType().equalsIgnoreCase("INTER")) {

					salesReturnLocalDetailsVO.setIgst(salesReturnLocalDetailsDTO.getIgst());
					BigDecimal igstAmount = salesReturnLocalDetailsVO.getBasicAmount()
							.multiply(salesReturnLocalDetailsDTO.getIgst()).divide(BigDecimal.valueOf(100));
					salesReturnLocalDetailsVO.setCgst(BigDecimal.ZERO);
					salesReturnLocalDetailsVO.setSgst(BigDecimal.ZERO);
					taxAmountIn = igstAmount;
					salesReturnLocalDetailsVO.setTaxAmount(taxAmountIn);
				} else if (salesReturnLocalVO.getTaxType().equalsIgnoreCase("INTRA")) {
					salesReturnLocalDetailsVO.setCgst(salesReturnLocalDetailsDTO.getCgst());
					salesReturnLocalDetailsVO.setSgst(salesReturnLocalDetailsDTO.getSgst());

					BigDecimal cgstAmount = salesReturnLocalDetailsDTO.getCgst()
							.multiply(salesReturnLocalDetailsVO.getBasicAmount()).divide(BigDecimal.valueOf(100));
					BigDecimal sgstAmount = salesReturnLocalDetailsDTO.getSgst()
							.multiply(salesReturnLocalDetailsVO.getBasicAmount()).divide(BigDecimal.valueOf(100));
					salesReturnLocalDetailsVO.setIgst(BigDecimal.ZERO);
					taxAmountIn = cgstAmount.add(sgstAmount);
					salesReturnLocalDetailsVO.setTaxAmount(taxAmountIn);
				}
			}
			totalTaxAmount = totalTaxAmount.add(salesReturnLocalDetailsVO.getTaxAmount());

			landedValues = salesReturnLocalDetailsVO.getBasicAmount().add(salesReturnLocalDetailsVO.getTaxAmount());
			salesReturnLocalDetailsVO.setLandedValue(landedValues);
			totalAmount = totalAmount.add(salesReturnLocalDetailsVO.getLandedValue());
			salesReturnLocalDetailsVO.setSalesReturnLocalVO(salesReturnLocalVO);
			SalesReturnLocalDetailsVOs.add(salesReturnLocalDetailsVO);
		}
		salesReturnLocalVO.setGrossAmount(grossAmount);
		salesReturnLocalVO.setTotalTaxAmount(totalTaxAmount);
		salesReturnLocalVO.setTotalAmount(totalAmount);
		salesReturnLocalVO.setTotalAmountInWords(
				amountInWordsConverterService.convert(salesReturnLocalVO.getTotalAmount().longValue()));
		salesReturnLocalVO.setSalesReturnLocalDetailsVO(SalesReturnLocalDetailsVOs);

	}

	@Override
	public List<SalesReturnLocalVO> getAllSalesReturnLocalByOrgId(Long orgId, String finYear, String branchCode) {

		return salesReturnLocalRepo.getAllSalesReturnLocalByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public SalesReturnLocalVO getSalesReturnLocalById(Long id) {

		return salesReturnLocalRepo.getSalesReturnLocalById(id);
	}

	@Override
	public String getSalesReturnLocalDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "SRL";
		return salesReturnLocalRepo.getSalesReturnLocalDocId(orgId, finYear, branchCode, ScreenCode);
	}

	@Override
	public List<Map<String, Object>> getSalesInvoiceNoFromSalesInvoice(Long orgId, String customerName) {
		Set<Object[]> chType = salesReturnLocalRepo.getSalesInvoiceNoFromSalesInvoice(orgId, customerName);
		return getSalesInvoiceNo(chType);
	}

	private List<Map<String, Object>> getSalesInvoiceNo(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("salesInvoiceLocalNo", ch[0] != null ? ch[0].toString() : "");
			map.put("salesinvoicelocaldate", ch[1] != null ? ch[1].toString() : "");
			map.put("billingAddress", ch[2] != null ? ch[2].toString() : "");
			map.put("currency", ch[3] != null ? ch[3].toString() : "");
			map.put("customerName", ch[4] != null ? ch[4].toString() : "");
			map.put("exchangeRate", ch[5] != null ? ch[5].toString() : "");
			map.put("location", ch[6] != null ? ch[6].toString() : "");
			map.put("salesOrderNo", ch[7] != null ? ch[7].toString() : "");
			map.put("taxType", ch[8] != null ? ch[8].toString() : "");
			map.put("packingListNo", ch[9] != null ? ch[9].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getItemFromSalesInvoice(Long orgId, String customerName,
			String salesInvoiceLocalNo) {
		Set<Object[]> chType = salesReturnLocalRepo.getItemFromSalesInvoice(orgId, customerName, salesInvoiceLocalNo);
		return getItem(chType);
	}

	private List<Map<String, Object>> getItem(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("item", ch[0] != null ? ch[0].toString() : "");
			map.put("itemDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("rate", ch[2] != null ? ch[2].toString() : "");
			map.put("qty", ch[3] != null ? ch[3].toString() : "");
			map.put("taxCode", ch[4] != null ? ch[4].toString() : "");
			map.put("units", ch[5] != null ? ch[5].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	// SalesReturnExport

	@Override
	public Map<String, Object> createUpdateSalesReturnExport(SalesReturnExportDTO salesReturnExportDTO)
			throws ApplicationException {
		SalesReturnExportVO salesReturnExportVO = new SalesReturnExportVO();
		String message;
		String screenCode = "SRE";
		if (ObjectUtils.isNotEmpty(salesReturnExportDTO.getId())) {
			salesReturnExportVO = salesReturnExportRepo.findById(salesReturnExportDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid SalesReturnExport details"));
			message = "SalesReturnExport Updated Successfully";
			salesReturnExportVO.setUpdatedBy(salesReturnExportDTO.getCreatedBy());

		} else {
			String docId = salesReturnExportRepo.getSalesReturnExportDocId(salesReturnExportDTO.getOrgId(),
					salesReturnExportDTO.getFinYear(), salesReturnExportDTO.getBranchCode(), screenCode);
			salesReturnExportVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(salesReturnExportDTO.getOrgId(),
							salesReturnExportDTO.getFinYear(), salesReturnExportDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			salesReturnExportVO.setCreatedBy(salesReturnExportDTO.getCreatedBy());
			salesReturnExportVO.setUpdatedBy(salesReturnExportDTO.getCreatedBy());

			message = "SalesReturnExport Created Successfully";
		}
		createUpdatedSalesReturnExportVOFromSalesReturnExportDTO(salesReturnExportDTO, salesReturnExportVO);
		salesReturnExportRepo.save(salesReturnExportVO);
		Map<String, Object> response = new HashMap<>();
		response.put("salesReturnExportVO", salesReturnExportVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedSalesReturnExportVOFromSalesReturnExportDTO(SalesReturnExportDTO salesReturnExportDTO,
			SalesReturnExportVO salesReturnExportVO) {
		salesReturnExportVO.setCustomerName(salesReturnExportDTO.getCustomerName());
		salesReturnExportVO.setSalesOrderNo(salesReturnExportDTO.getSalesOrderNo());
		salesReturnExportVO.setSalesInvoiceExportNo(salesReturnExportDTO.getSalesInvoiceExportNo());
		salesReturnExportVO.setSalesInvoiceExportDate(salesReturnExportDTO.getSalesInvoiceExportDate());
		salesReturnExportVO.setCurrency(salesReturnExportDTO.getCurrency());
		salesReturnExportVO.setExchangeRate(salesReturnExportDTO.getExchangeRate());
		salesReturnExportVO.setLocation(salesReturnExportDTO.getLocation());
		salesReturnExportVO.setBillingAddress(salesReturnExportDTO.getBillingAddress());
		salesReturnExportVO.setShippingAddress(salesReturnExportDTO.getShippingAddress());
		salesReturnExportVO.setExportPackingNo(salesReturnExportDTO.getExportPackingNo());
		salesReturnExportVO.setRemarks(salesReturnExportDTO.getRemarks());
		salesReturnExportVO.setOrgId(salesReturnExportDTO.getOrgId());
		salesReturnExportVO.setBranch(salesReturnExportDTO.getBranch());
		salesReturnExportVO.setBranchCode(salesReturnExportDTO.getBranchCode());
		salesReturnExportVO.setFinYear(salesReturnExportDTO.getFinYear());
		salesReturnExportVO.setCreatedBy(salesReturnExportDTO.getCreatedBy());

		BigDecimal totalQuantity = BigDecimal.ZERO;
		BigDecimal totalAmountInExport = BigDecimal.ZERO;

		if (ObjectUtils.isNotEmpty(salesReturnExportDTO.getId())) {
			List<SalesReturnExportDetailsVO> salesReturnExportDetailsVO1 = salesReturnExportDetailsRepo
					.findBySalesReturnExportVO(salesReturnExportVO);
			salesReturnExportDetailsRepo.deleteAll(salesReturnExportDetailsVO1);

			List<SalesReturnExportTermsVO> salesReturnExportTermsVO1 = salesReturnExportTermsRepo
					.findBySalesReturnExportVO(salesReturnExportVO);
			salesReturnExportTermsRepo.deleteAll(salesReturnExportTermsVO1);
		}

		List<SalesReturnExportDetailsVO> salesReturnExportDetailsVOs = new ArrayList<>();
		for (SalesReturnExportDetailsDTO salesReturnExportDetailsDTO : salesReturnExportDTO
				.getSalesReturnExportDetailsDTO()) {
			SalesReturnExportDetailsVO salesReturnExportDetailsVO = new SalesReturnExportDetailsVO();
			salesReturnExportDetailsVO.setItem(salesReturnExportDetailsDTO.getItem());
			salesReturnExportDetailsVO.setItemDesc(salesReturnExportDetailsDTO.getItemDesc());
			salesReturnExportDetailsVO.setUnits(salesReturnExportDetailsDTO.getUnits());
			salesReturnExportDetailsVO.setRejectQty(salesReturnExportDetailsDTO.getRejectQty());
			salesReturnExportDetailsVO.setQty(salesReturnExportDetailsDTO.getQty());
			salesReturnExportDetailsVO.setRate(salesReturnExportDetailsDTO.getRate());
			salesReturnExportDetailsVO.setDiscount(salesReturnExportDetailsDTO.getDiscount());

			totalQuantity = totalQuantity.add(salesReturnExportDetailsDTO.getRejectQty());

			BigDecimal discountAmountInExport = BigDecimal.ZERO;
			BigDecimal amountInNet = BigDecimal.ZERO;

			BigDecimal grossAmount = salesReturnExportDetailsDTO.getRejectQty()
					.multiply(salesReturnExportDetailsDTO.getRate());
			salesReturnExportDetailsVO.setGrossAmount(grossAmount);

			discountAmountInExport = salesReturnExportDetailsVO.getGrossAmount()
					.multiply(salesReturnExportDetailsDTO.getDiscount()).divide(BigDecimal.valueOf(100));
			salesReturnExportDetailsVO.setDiscountAmount(discountAmountInExport);

			amountInNet = salesReturnExportDetailsVO.getGrossAmount()
					.subtract(salesReturnExportDetailsVO.getDiscountAmount());

			salesReturnExportDetailsVO.setNetAmount(amountInNet);

			totalAmountInExport = totalAmountInExport.add(salesReturnExportDetailsVO.getNetAmount());

			salesReturnExportDetailsVO.setSalesReturnExportVO(salesReturnExportVO);
			salesReturnExportDetailsVOs.add(salesReturnExportDetailsVO);
		}
		salesReturnExportVO.setTotalQty(totalQuantity);

		salesReturnExportVO.setTotalAmount(totalAmountInExport);
		salesReturnExportVO.setTotalAmountInWords(
				amountInWordsConverterService.convert(salesReturnExportVO.getTotalAmount().longValue()));
		salesReturnExportVO.setSalesReturnExportDetailsVO(salesReturnExportDetailsVOs);

		List<SalesReturnExportTermsVO> salesReturnExportTermsVOs = new ArrayList<>();
		for (SalesReturnExportTermsDTO salesReturnExportTermsDTO : salesReturnExportDTO
				.getSalesReturnExportTermsDTO()) {
			SalesReturnExportTermsVO salesReturnExportTermsVO = new SalesReturnExportTermsVO();
			salesReturnExportTermsVO.setTerms(salesReturnExportTermsDTO.getTerms());
			salesReturnExportTermsVO.setDescriptions(salesReturnExportTermsDTO.getDescriptions());

			salesReturnExportTermsVO.setSalesReturnExportVO(salesReturnExportVO);
			salesReturnExportTermsVOs.add(salesReturnExportTermsVO);
		}
		salesReturnExportVO.setSalesReturnExportTermsVO(salesReturnExportTermsVOs);
	}

	@Override
	public List<SalesReturnExportVO> getAllSalesReturnExportByOrgId(Long orgId, String finYear, String branchCode) {

		return salesReturnExportRepo.getAllSalesReturnExportByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public SalesReturnExportVO getSalesReturnExportById(Long id) {

		return salesReturnExportRepo.getSalesReturnExportById(id);
	}

	@Override
	public String getSalesReturnExportDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "SRE";
		return salesReturnExportRepo.getSalesReturnExportDocId(orgId, finYear, branchCode, ScreenCode);
	}

	@Override
	public List<Map<String, Object>> getCustomerNameFromPartyMasterhExport(Long orgId) {
		Set<Object[]> chType = salesReturnExportRepo.getCustomerNameFromPartyMasterhExport(orgId);
		return getCustomerName(chType);
	}

	private List<Map<String, Object>> getCustomerName(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("customerName", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getDocIdFromSalesInvoiceExport(Long orgId, String customerName) {
		Set<Object[]> chType = salesReturnExportRepo.getDocIdFromSalesInvoiceExport(orgId, customerName);
		return getDocIdFromSales(chType);
	}

	private List<Map<String, Object>> getDocIdFromSales(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("salesInvoiceExportNo", ch[0] != null ? ch[0].toString() : "");
			map.put("salesinvoiceExportdate", ch[1] != null ? ch[1].toString() : "");
			map.put("salesOrderNo", ch[2] != null ? ch[2].toString() : "");
			map.put("exportPackingNo", ch[3] != null ? ch[3].toString() : "");
			map.put("currency", ch[4] != null ? ch[4].toString() : "");
			map.put("exchangeRate", ch[5] != null ? ch[5].toString() : "");
			map.put("location", ch[6] != null ? ch[6].toString() : "");
			map.put("billingAddress", ch[7] != null ? ch[7].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getItemFromSalesInvoiceExport(Long orgId, String customerName,
			String salesInvoiceExportNo) {
		Set<Object[]> chType = salesReturnExportRepo.getItemFromSalesInvoiceExport(orgId, customerName,
				salesInvoiceExportNo);
		return getItemFromSalesInvoice(chType);
	}

	private List<Map<String, Object>> getItemFromSalesInvoice(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("item", ch[0] != null ? ch[0].toString() : "");
			map.put("itemDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("units", ch[2] != null ? ch[2].toString() : "");
			map.put("qty", ch[3] != null ? ch[3].toString() : "");
			map.put("rate", ch[4] != null ? ch[4].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPackListDetails(Long orgId, String branchCode, String customerName) {
		Set<Object[]> chType = salesInvoiceLocalRepo.getPackListDetails(orgId, branchCode, customerName);
		return getPackListDetails(chType);
	}

	private List<Map<String, Object>> getPackListDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("docid", ch[0] != null ? ch[0].toString() : "");
			map.put("docdate", ch[1] != null ? ch[1].toString() : "");
			map.put("salesOrderNo", ch[2] != null ? ch[2].toString() : "");
			map.put("currency", ch[3] != null ? ch[3].toString() : "");
			map.put("gstIn", ch[4] != null ? ch[4].toString() : "");
			map.put("exchangeRate", ch[5] != null ? ch[5].toString() : "");
			map.put("billingAddress", ch[6] != null ? ch[6].toString() : "");
			map.put("shippingAddress", ch[7] != null ? ch[7].toString() : "");
			map.put("taxType", ch[8] != null ? ch[8].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getItemPackListDetails(Long orgId, String branchCode, String customerName,
			String packlistNo) {
		Set<Object[]> chType = salesInvoiceLocalRepo.getItemPackListDetails(orgId, branchCode, customerName,
				packlistNo);
		return getItemPackListDetails(chType);
	}

	private List<Map<String, Object>> getItemPackListDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("partno", ch[0] != null ? ch[0].toString() : "");
			map.put("partdesc", ch[1] != null ? ch[1].toString() : "");
			map.put("units", ch[2] != null ? ch[2].toString() : "");
			map.put("qty", ch[3] != null ? ch[3].toString() : "");
			map.put("unitPrice", ch[4] != null ? ch[4].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	// Reports API

	@Override
	public List<Map<String, Object>> getDeliveryChallanForFGReport(Long orgId, String fromDate, String toDate,String saleOrderNo) {

		Set<Object[]> reportData = deliveryChalanForFgRepo.getDeliveryChallanForFGReport(orgId, fromDate, toDate,saleOrderNo);

		return mapDeliveryChallanForFGReport(reportData);
	}

	private List<Map<String, Object>> mapDeliveryChallanForFGReport(Set<Object[]> reportData) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : reportData) {

			Map<String, Object> map = new HashMap<>();

			map.put("deliveryChallanId", ch[0]);
			map.put("dcNo", ch[1]);
			map.put("dcDate", ch[2]);
			map.put("customerName", ch[3]);
			map.put("customerAddress", ch[4]);
			map.put("salesOrderNo", ch[5]);
			map.put("soDate", ch[6]);
			map.put("dueDate", ch[7]);
			map.put("vehicleType", ch[8]);
			map.put("vehicleNo", ch[9]);
			map.put("narration", ch[10]);

			map.put("itemNo", ch[11]);
			map.put("itemDescription", ch[12]);
			map.put("quantity", ch[13]);
			map.put("unit", ch[14]);
			map.put("weight", ch[15]);
			map.put("remarks", ch[16]);

			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getSalesInvoiceLocalDetails(Long orgId, String fromdate, String todate) {

		Set<Object[]> salesInvoiceLocalDetails = salesInvoiceLocalRepo.getSalesInvoiceLocalDetails(orgId, fromdate,
				todate);

		return getSalesInvoiceLocalDetails(salesInvoiceLocalDetails);
	}

	private List<Map<String, Object>> getSalesInvoiceLocalDetails(Set<Object[]> salesInvoiceLocalDetails) {

		List<Map<String, Object>> list1 = new ArrayList<>();

		for (Object[] ch : salesInvoiceLocalDetails) {

			Map<String, Object> map = new HashMap<>();

			map.put("orgid", ch[0] != null ? ch[0].toString() : "");
			map.put("docid", ch[1] != null ? ch[1].toString() : "");
			map.put("docdate", ch[2] != null ? ch[2].toString() : "");
			map.put("customername", ch[3] != null ? ch[3].toString() : "");
			map.put("packinglistno", ch[4] != null ? ch[4].toString() : "");
			map.put("salesorderno", ch[5] != null ? ch[5].toString() : "");
			map.put("item", ch[6] != null ? ch[6].toString() : "");
			map.put("itemdesc", ch[7] != null ? ch[7].toString() : "");
			map.put("unit", ch[8] != null ? ch[8].toString() : "");
			map.put("avlstkqty", ch[9] != null ? ch[9].toString() : "");
			map.put("rate", ch[10] != null ? ch[10].toString() : "");
			map.put("landedvalue", ch[11] != null ? ch[11].toString() : "");
			map.put("salesinvoicelocalid", ch[12] != null ? ch[12].toString() : "");

			list1.add(map);
		}

		return list1;
	}

}
