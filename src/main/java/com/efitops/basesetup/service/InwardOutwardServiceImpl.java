package com.efitops.basesetup.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.GateInwardEntryDTO;
import com.efitops.basesetup.dto.GateInwardEntryDetailsDTO;
import com.efitops.basesetup.dto.GateOutwardEntryDTO;
import com.efitops.basesetup.dto.GateOutwardEntryDetailsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.EnquiryDetailsVO;
import com.efitops.basesetup.entity.EnquirySummaryVO;
import com.efitops.basesetup.entity.GateInwardEntryDetailsVO;
import com.efitops.basesetup.entity.GateInwardEntryVO;
import com.efitops.basesetup.entity.GateOutwardEntryDetailsVO;
import com.efitops.basesetup.entity.GateOutwardEntryVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.GateInwardEntryDetailsRepo;
import com.efitops.basesetup.repo.GateInwardEntryRepo;
import com.efitops.basesetup.repo.GateOutwardEntryDetailsRepo;
import com.efitops.basesetup.repo.GateOutwardEntryRepo;

@Service
public class InwardOutwardServiceImpl implements InwardOutwardService{

	public static final Logger LOGGER = LoggerFactory.getLogger(InwardOutwardServiceImpl.class);

	@Autowired
	GateInwardEntryRepo gateInwardEntryRepo;
	
	@Autowired
	GateInwardEntryDetailsRepo gateInwardEntryDetailsRepo;
	
	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;
	
	@Autowired
	GateOutwardEntryRepo gateOutwardEntryRepo;
	
	@Autowired
	GateOutwardEntryDetailsRepo gateOutwardEntryDetailsRepo;
	
	@Autowired
	private CommonNotificationService commonNotificationService;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<GateInwardEntryVO> getGateInwardEntryByOrgId(Long orgId,String finYear,String branchCode) {
		List<GateInwardEntryVO> gateInwardEntryVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received GateInwardEntry BY OrgId : {}", orgId);
			gateInwardEntryVO = gateInwardEntryRepo.findGateInwardEntryByOrgId(orgId,finYear,branchCode);
		}
		return gateInwardEntryVO;
	}
	
	@Override
	public List<GateInwardEntryVO> getGateInwardEntryById(Long id) {
		List<GateInwardEntryVO> gateInwardEntryVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received GateInwardEntry BY Id : {}", id);
			gateInwardEntryVO = gateInwardEntryRepo.findgetGateInwardEntryById(id);
		}
		return gateInwardEntryVO;
	}
	
	@Override
	public Map<String, Object> updateCreateGateInwardEntry(@Valid GateInwardEntryDTO gateInwardEntryDTO) throws ApplicationException {
		String message;
        String screenCode="GIE";
		GateInwardEntryVO gateInwardEntryVO = new GateInwardEntryVO();
		GateInwardEntryVO oldInward = null;
		if (gateInwardEntryDTO.getId() != null) {
			
			oldInward = gateInwardEntryRepo.findById(gateInwardEntryDTO.getId())
					.orElseThrow(() -> new ApplicationException("GateInwardEntry not found"));

			oldInward.getGateInwardEntryDetailsVO().size(); // load

			entityManager.detach(oldInward); // detach snapshot
			
			// Fetch existing ItemVO for update
			gateInwardEntryVO = gateInwardEntryRepo.findById(gateInwardEntryDTO.getId())
					.orElseThrow(() -> new ApplicationException("GateInwardEntry not found"));
			gateInwardEntryVO.setUpdatedBy(gateInwardEntryDTO.getCreatedBy());
			createUpdateGateInwardEntryVOByGateInwardEntryDTO(gateInwardEntryDTO, gateInwardEntryVO);
			message = "GateInwardEntry Updated Successfully";

			List<GateInwardEntryDetailsVO> gateInwardEntryDetailsVOs = gateInwardEntryDetailsRepo.findByGateInwardEntryVO(gateInwardEntryVO);
			gateInwardEntryDetailsRepo.deleteAll(gateInwardEntryDetailsVOs);

			
		} else {
			
			// GETDOCID API
						String docId = gateInwardEntryRepo.getGateInwardEntryDocId(gateInwardEntryDTO.getOrgId(),gateInwardEntryDTO.getFinYear(),gateInwardEntryDTO.getBranchCode(),
								screenCode);

						gateInwardEntryVO.setDocId(docId);

//			        							// GETDOCID LASTNO +1
						DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
								.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(gateInwardEntryDTO.getOrgId(),gateInwardEntryDTO.getFinYear(),gateInwardEntryDTO.getBranchCode(), screenCode);
						documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
						documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			
			
			// Create new ItemVO
			gateInwardEntryVO.setCreatedBy(gateInwardEntryDTO.getCreatedBy());
			gateInwardEntryVO.setUpdatedBy(gateInwardEntryDTO.getCreatedBy());
			createUpdateGateInwardEntryVOByGateInwardEntryDTO(gateInwardEntryDTO, gateInwardEntryVO);
			message = "GateInwardEntry Created Successfully";
		}

		// Save the ItemVO
		gateInwardEntryRepo.save(gateInwardEntryVO);
		commonNotificationService.generateNotification(gateInwardEntryVO.getScreenCode(), gateInwardEntryVO.getId(), oldInward, gateInwardEntryVO);
		
		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("gateInwardEntryVO", gateInwardEntryVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateGateInwardEntryVOByGateInwardEntryDTO(@Valid GateInwardEntryDTO gateInwardEntryDTO, GateInwardEntryVO gateInwardEntryVO) {
		gateInwardEntryVO.setSupplierName(gateInwardEntryDTO.getSupplierName());
		gateInwardEntryVO.setSupplierCode(gateInwardEntryDTO.getSupplierCode());
		gateInwardEntryVO.setPoNumber(gateInwardEntryDTO.getPoNumber());
		gateInwardEntryVO.setInvoiceNo(gateInwardEntryDTO.getInvoiceNo());
		gateInwardEntryVO.setInvoiceDate(gateInwardEntryDTO.getInvoiceDate());
		gateInwardEntryVO.setVehicleNo(gateInwardEntryDTO.getVehicleNo());
		gateInwardEntryVO.setCourierNo(gateInwardEntryDTO.getCourierNo());
		gateInwardEntryVO.setCourierName(gateInwardEntryDTO.getCourierName());
		gateInwardEntryVO.setNarration(gateInwardEntryDTO.getNarration());
		gateInwardEntryVO.setOrgId(gateInwardEntryDTO.getOrgId());
		gateInwardEntryVO.setBranch(gateInwardEntryDTO.getBranch());
		gateInwardEntryVO.setBranchCode(gateInwardEntryDTO.getBranchCode());
		gateInwardEntryVO.setFinYear(gateInwardEntryDTO.getFinYear());

		// Handling ItemInventoryVO
		List<GateInwardEntryDetailsVO> gateInwardEntryDetailsVOs = new ArrayList<>();
		for (GateInwardEntryDetailsDTO gateInwardEntryDetailsDTO : gateInwardEntryDTO.getGateInwardEntryDetailsDTO()) {
			GateInwardEntryDetailsVO gateInwardEntryDetailsVO = new GateInwardEntryDetailsVO();
			gateInwardEntryDetailsVO.setItemName(gateInwardEntryDetailsDTO.getItemName());
			gateInwardEntryDetailsVO.setItemDesc(gateInwardEntryDetailsDTO.getItemDesc());
			gateInwardEntryDetailsVO.setUom(gateInwardEntryDetailsDTO.getUom());
			gateInwardEntryDetailsVO.setPoQty(gateInwardEntryDetailsDTO.getPoQty());
			gateInwardEntryDetailsVO.setInvoiceQty(gateInwardEntryDetailsDTO.getInvoiceQty());
			gateInwardEntryDetailsVO.setInwardQty(gateInwardEntryDetailsDTO.getInwardQty());
			gateInwardEntryDetailsVO.setPoBalanceQty(gateInwardEntryDetailsDTO.getPoBalanceQty());
			BigDecimal balanceQty = gateInwardEntryDetailsDTO.getPoQty().subtract(gateInwardEntryDetailsDTO.getInwardQty());

			// Check if balanceQty is non-negative or negative
			if (balanceQty.compareTo(BigDecimal.ZERO) >= 0) {
			    // No excess, balance is positive or zero
			    gateInwardEntryDetailsVO.setBalanceQty(balanceQty.intValue()); 
			    gateInwardEntryDetailsVO.setExcessQty(0); 
			} else {
			    // Excess exists (negative balanceQty indicates inward exceeds PO)
			    gateInwardEntryDetailsVO.setExcessQty(balanceQty.intValue()); 
			    gateInwardEntryDetailsVO.setBalanceQty(0); 
			}

			gateInwardEntryDetailsVO.setGateInwardEntryVO(gateInwardEntryVO); // Set the reference in child entity
			gateInwardEntryDetailsVOs.add(gateInwardEntryDetailsVO);
		}
		gateInwardEntryVO.setGateInwardEntryDetailsVO(gateInwardEntryDetailsVOs);

	}
	
	
	@Override
	public String getGateInwardEntryDocId(Long orgId,String finYear,String branchCode) {
		String ScreenCode = "GIE";
		String result = gateInwardEntryRepo.getGateInwardEntryDocId(orgId,finYear,branchCode, ScreenCode);
		return result;
	}
	
	@Override
	public List<Map<String, Object>> getPurchaseOrderNoForGateInward(Long orgId, String supplierCode) {
		Set<Object[]> purchaseOrderNo = gateInwardEntryRepo.findPurchaseOrderNoForGateInward(orgId, supplierCode);
		return getPurchaseOrderNoForGateInward(purchaseOrderNo);
	}

	private List<Map<String, Object>> getPurchaseOrderNoForGateInward(Set<Object[]> purchaseOrderNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : purchaseOrderNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("purchaseOrderNo", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;

	}
	
	@Override
	public List<Map<String, Object>> getItemDetailsForGateInwardEntry(Long orgId, String purchaseOrderNo) {
		Set<Object[]> itemDetails = gateInwardEntryRepo.findItemDetailsForGateInwardEntry(orgId, purchaseOrderNo);
		return getItemDetailsForGateInwardEntry(itemDetails);
	}

	private List<Map<String, Object>> getItemDetailsForGateInwardEntry(Set<Object[]> itemDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : itemDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("item", ch[0] != null ? ch[0].toString() : "");
			map.put("itemDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("uom", ch[2] != null ? ch[2].toString() : "");
			map.put("qty", ch[3] != null ? ch[3].toString() : "");
			map.put("inwardQty", ch[4] != null ? ch[4].toString() : "");
			map.put("poBalanceQty", ch[5] != null ? ch[5].toString() : "");
			List1.add(map);
		}
		return List1;

	}
	
	
	//GateOutWard
	
	@Override
	public List<GateOutwardEntryVO> getAllGateOutwardEntryByOrgId(Long orgId,String finYear,String branchCode) {

			return gateOutwardEntryRepo.getAllGateOutwardEntryByOrgId(orgId,finYear,branchCode);

	}
	
	@Override
	public GateOutwardEntryVO getGateOutwardEntryById(Long id) {
	
			return  gateOutwardEntryRepo.getGateOutwardEntryById(id);

	}
	
	@Override
	public Map<String, Object> updateCreateGateOutwardEntry(@Valid GateOutwardEntryDTO gateOutwardEntryDTO) throws ApplicationException {
		GateOutwardEntryVO gateOutwardEntryVO = new GateOutwardEntryVO();
		String message;
		String screenCode = "GOE";
		GateOutwardEntryVO oldOutward =null;
		if (ObjectUtils.isNotEmpty(gateOutwardEntryDTO.getId())) {
			
			oldOutward = gateOutwardEntryRepo.findById(gateOutwardEntryDTO.getId())
					.orElseThrow(() -> new ApplicationException("GateOutwardEntry not found"));

			oldOutward.getGateOutwardEntryDetailsVO().size(); // load

			entityManager.detach(oldOutward); // detach snapshot
			
			gateOutwardEntryVO = gateOutwardEntryRepo.findById(gateOutwardEntryDTO.getId())
					.orElseThrow(() -> new ApplicationException("GateOutwardEntry Enquiry details"));
			message = "GateOutwardEntry Updated Successfully";
			gateOutwardEntryVO.setUpdatedBy(gateOutwardEntryDTO.getCreatedBy());

		} else {

			String docId = gateOutwardEntryRepo.getGateOutwardEntryDocId(gateOutwardEntryDTO.getOrgId(), gateOutwardEntryDTO.getFinYear(),
					gateOutwardEntryDTO.getBranchCode(), screenCode);
			gateOutwardEntryVO.setDocId(docId);

			
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(gateOutwardEntryDTO.getOrgId(), gateOutwardEntryDTO.getFinYear(),
							gateOutwardEntryDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			gateOutwardEntryVO.setCreatedBy(gateOutwardEntryDTO.getCreatedBy());
			gateOutwardEntryVO.setUpdatedBy(gateOutwardEntryDTO.getCreatedBy());

			message = "GateOutwardEntry Created Successfully";
		}
		
		createUpdatedGateOutwardEntryVOFromGateOutwardEntryDTO(gateOutwardEntryDTO, gateOutwardEntryVO);
		gateOutwardEntryRepo.save(gateOutwardEntryVO);
		commonNotificationService.generateNotification(gateOutwardEntryVO.getScreenCode(), gateOutwardEntryVO.getId(), oldOutward, gateOutwardEntryVO);

		Map<String, Object> response = new HashMap<>();
		response.put("gateOutwardEntryVO", gateOutwardEntryVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedGateOutwardEntryVOFromGateOutwardEntryDTO(@Valid GateOutwardEntryDTO gateOutwardEntryDTO, GateOutwardEntryVO gateOutwardEntryVO) {
		gateOutwardEntryVO.setCustomerNo(gateOutwardEntryDTO.getCustomerNo());
		gateOutwardEntryVO.setType(gateOutwardEntryDTO.getType());
		gateOutwardEntryVO.setDeliveryChallanNo(gateOutwardEntryDTO.getDeliveryChallanNo());
		gateOutwardEntryVO.setDeliveryChallanDate(gateOutwardEntryDTO.getDeliveryChallanDate());
		gateOutwardEntryVO.setInvoiceNo(gateOutwardEntryDTO.getInvoiceNo());
		gateOutwardEntryVO.setInvoiceDate(gateOutwardEntryDTO.getInvoiceDate());
		gateOutwardEntryVO.setModeOfShipment(gateOutwardEntryDTO.getModeOfShipment());
		gateOutwardEntryVO.setVehicleNo(gateOutwardEntryDTO.getVehicleNo());
		gateOutwardEntryVO.setNarration(gateOutwardEntryDTO.getNarration());
		gateOutwardEntryVO.setOrgId(gateOutwardEntryDTO.getOrgId());
		gateOutwardEntryVO.setBranch(gateOutwardEntryDTO.getBranch());
		gateOutwardEntryVO.setCustomerName(gateOutwardEntryDTO.getCustomerName());
		gateOutwardEntryVO.setBranchCode(gateOutwardEntryDTO.getBranchCode());
		gateOutwardEntryVO.setFinYear(gateOutwardEntryDTO.getFinYear());
		

		if (ObjectUtils.isNotEmpty(gateOutwardEntryDTO.getId())) {
			List<GateOutwardEntryDetailsVO> gateOutwardEntryDetailsVO1 = gateOutwardEntryDetailsRepo.findByGateOutwardEntryVO(gateOutwardEntryVO);
			gateOutwardEntryDetailsRepo.deleteAll(gateOutwardEntryDetailsVO1);
		}

		List<GateOutwardEntryDetailsVO> gateOutwardEntryDetailsVOs = new ArrayList<>();
		for (GateOutwardEntryDetailsDTO gateOutwardEntryDetailsDTO : gateOutwardEntryDTO.getGateOutwardEntryDetailsDTO()) {
			GateOutwardEntryDetailsVO gateOutwardEntryDetailsVO = new GateOutwardEntryDetailsVO();
			gateOutwardEntryDetailsVO.setItem(gateOutwardEntryDetailsDTO.getItem());
			gateOutwardEntryDetailsVO.setItemDesc(gateOutwardEntryDetailsDTO.getItemDesc());
			gateOutwardEntryDetailsVO.setUom(gateOutwardEntryDetailsDTO.getUom());
			gateOutwardEntryDetailsVO.setQty(gateOutwardEntryDetailsDTO.getQty());

			gateOutwardEntryDetailsVO.setGateOutwardEntryVO(gateOutwardEntryVO); 
			gateOutwardEntryDetailsVOs.add(gateOutwardEntryDetailsVO);
		}
		gateOutwardEntryVO.setGateOutwardEntryDetailsVO(gateOutwardEntryDetailsVOs);
	}
	
	
	@Override
	public String getGateOutwardEntryDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "GOE";
		return  gateOutwardEntryRepo.getGateOutwardEntryDocId(orgId, finYear, branchCode, ScreenCode);
		
	}
	
	
	@Override
	public List<Map<String, Object>> getCustomerNameAndCodeFromGateOutwardEntry(Long orgId) {
		Set<Object[]> customerDetails = gateOutwardEntryRepo.findCustomerNameAndCodeFromGateOutwardEntry(orgId);
		return getCustomerNameAndCodeFromGateOutwardEntry(customerDetails);
	}

	private List<Map<String, Object>> getCustomerNameAndCodeFromGateOutwardEntry(Set<Object[]> customerDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : customerDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("customer", ch[0] != null ? ch[0].toString() : "");
			map.put("customerCode", ch[1] != null ? ch[1].toString() : "");
			
			List1.add(map);
		}
		return List1;
	}
	
	@Override
	public List<Map<String, Object>> getEmployeeNameDetails(Long orgId,String branchCode) {
		Set<Object[]> customerDetails = gateOutwardEntryRepo.getEmployeeNameDetails(orgId,branchCode);
		return getEmployeeNameDetails(customerDetails);
	}

	private List<Map<String, Object>> getEmployeeNameDetails(Set<Object[]> customerDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : customerDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("employeeName", ch[0] != null ? ch[0].toString() : "");
			map.put("employeeCode", ch[1] != null ? ch[1].toString() : "");
			
			List1.add(map);
		}
		return List1;
	}
	
	
	@Override
	public List<Map<String, Object>> getDeliveryChallanNoForGateOutwardEntry(Long orgId,String customerName,String type) {
		Set<Object[]> deliveryChallanNo = gateOutwardEntryRepo.findDeliveryChallanNoForGateOutwardEntry(orgId,customerName,type);
		return getDeliveryChallanNoForGateOutwardEntry(deliveryChallanNo);
	}

	private List<Map<String, Object>> getDeliveryChallanNoForGateOutwardEntry(Set<Object[]> deliveryChallanNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : deliveryChallanNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("deliveryChallanNo", ch[0] != null ? ch[0].toString() : "");
			map.put("deliveryChallanDate", ch[1] != null ? ch[1].toString() : "");
			
			List1.add(map);
		}
		return List1;
	}
	
	
	@Override
	public List<Map<String, Object>> getInvoiceNoForGateOutwardEntry(Long orgId,String customerName,String type) {
		Set<Object[]> invoiceNo = gateOutwardEntryRepo.findInvoiceNoForGateOutwardEntry(orgId,customerName,type);
		return getInvoiceNoForGateOutwardEntry(invoiceNo);
	}

	private List<Map<String, Object>> getInvoiceNoForGateOutwardEntry(Set<Object[]> invoiceNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : invoiceNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("invoiceNo", ch[0] != null ? ch[0].toString() : "");
			map.put("invoiceDate", ch[1] != null ? ch[1].toString() : "");
			map.put("woNo", ch[2] != null ? ch[2].toString() : "");

			
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getItemDetailsForGateOutwardEntry(Long orgId,String invNo) {
		Set<Object[]> itemDetails = gateOutwardEntryRepo.findItemDetailsForGateOutwardEntry(orgId,invNo);
		return getItemDetailsForGateOutwardEntry(itemDetails);
	}

	private List<Map<String, Object>> getItemDetailsForGateOutwardEntry(Set<Object[]> itemDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : itemDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("item", ch[0] != null ? ch[0].toString() : "");
			map.put("itemDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("qty", ch[2] != null ? ch[2].toString() : "");
			map.put("uom", ch[3] != null ? ch[3].toString() : "");
			
			List1.add(map);
		}
		return List1;
	}
	
	@Override
	public List<Map<String, Object>> getDeliveryChallanDetails(Long orgId, String branchCode,String type) {
		Set<Object[]> itemDetails = gateOutwardEntryRepo.getDeliveryChallanDetails( orgId,  branchCode, type);
		return getDocIdDetails(itemDetails);
	}

	private List<Map<String, Object>> getDocIdDetails(Set<Object[]> itemDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : itemDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("docid", ch[0] != null ? ch[0].toString() : "");
			map.put("docdate", ch[1] != null ? ch[1].toString() : "");			
			List1.add(map);
		}
		return List1;
	}
	
	@Override
	public List<Map<String, Object>> getInvoiceDetails(Long orgId, String branchCode,String deliveryChallanNo) {
		Set<Object[]> itemDetails = gateOutwardEntryRepo.getInvoiceDetails( orgId,  branchCode, deliveryChallanNo);
		return getInvoiceDetails(itemDetails);
	}

	private List<Map<String, Object>> getInvoiceDetails(Set<Object[]> itemDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : itemDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("docid", ch[0] != null ? ch[0].toString() : "");
			map.put("docdate", ch[1] != null ? ch[1].toString() : "");	
			map.put("qty", ch[2] != null ? ch[2].toString() : "");
			map.put("item", ch[3] != null ? ch[3].toString() : "");
			map.put("itemdesc", ch[4] != null ? ch[4].toString() : "");
			map.put("units", ch[5] != null ? ch[5].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	
	//Report gateoutwardentry
	
	@Override
	public List<Map<String, Object>> getGateOutwardEntryReport(Long orgId, String fromDate,
			String toDate) {
		Set<Object[]> gateOutwardEntry = gateOutwardEntryRepo.getGateOutwardEntryReport(orgId, fromDate, toDate);
		return getGateOutwardEntryReport(gateOutwardEntry);
	}
	

	private List<Map<String, Object>> getGateOutwardEntryReport(Set<Object[]> gateOutwardEntry) {

	    List<Map<String, Object>> list = new ArrayList<>();

	    for (Object[] ch : gateOutwardEntry) {

	        Map<String, Object> map = new HashMap<>();

	        map.put("gateOutwardId", ch[0] != null ? ch[0].toString() : "");
	        map.put("customerCode", ch[1] != null ? ch[1].toString() : "");
	        map.put("dcDate", ch[2] != null ? ch[2].toString() : "");
	        map.put("dcNo", ch[3] != null ? ch[3].toString() : "");
	        map.put("invoiceDate", ch[4] != null ? ch[4].toString() : "");
	        map.put("invoiceNo", ch[5] != null ? ch[5].toString() : "");
	        map.put("shipmentMode", ch[6] != null ? ch[6].toString() : "");
	        map.put("narration", ch[7] != null ? ch[7].toString() : "");
	        map.put("orgId", ch[8] != null ? ch[8].toString() : "");
	        map.put("type", ch[9] != null ? ch[9].toString() : "");
	        map.put("vehicleNo", ch[10] != null ? ch[10].toString() : "");
	        map.put("branch", ch[11] != null ? ch[11].toString() : "");
	        map.put("branchCode", ch[12] != null ? ch[12].toString() : "");
	        map.put("finYear", ch[13] != null ? ch[13].toString() : "");
	        map.put("docDate", ch[14] != null ? ch[14].toString() : "");
	        map.put("docId", ch[15] != null ? ch[15].toString() : "");
	        map.put("customerName", ch[16] != null ? ch[16].toString() : "");

	        list.add(map);
	    }

	    return list;
	}
	
	
	@Override
	public List<Map<String, Object>> getGateInwardReport(Long orgId,String branchCode, String supplierName, String fromDate, String toDate) {
		Set<Object[]> chType = gateInwardEntryRepo.getGateInwardReport(orgId,branchCode, supplierName, fromDate, toDate);
		return getGateInwardReport(chType);
	}

	private List<Map<String, Object>> getGateInwardReport(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : ""); // 0
			map.put("docDate", ch[1] != null ? ch[1].toString() : ""); // 1
			map.put("courierName", ch[2] != null ? ch[2].toString() : ""); // 2
			map.put("courierNo", ch[3] != null ? ch[3].toString() : ""); // 3
			map.put("invoiceNo", ch[4] != null ? ch[4].toString() : ""); // 4
			map.put("invoiceDate", ch[5] != null ? ch[5].toString() : ""); // 5
			map.put("toLocation", ch[6] != null ? ch[6].toString() : ""); // 6
			map.put("supplierName", ch[7] != null ? ch[7].toString() : ""); // 8
			map.put("supplierCode", ch[8] != null ? ch[8].toString() : ""); // 9
			map.put("vehicleNo", ch[9] != null ? ch[9].toString() : ""); // 5
			map.put("itemName", ch[10] != null ? ch[10].toString() : ""); // 6
			map.put("itemDesc", ch[11] != null ? ch[11].toString() : ""); // 7
			map.put("uom", ch[12] != null ? ch[12].toString() : ""); 
			map.put("invoiceQty", ch[13] != null ? new BigDecimal(ch[13].toString()) : BigDecimal.ZERO); // 10
			map.put("gateInwardentryId",ch[14] != null ? ch[14].toString() : "");

			List1.add(map);
		}
		return List1;
	}
  

}
