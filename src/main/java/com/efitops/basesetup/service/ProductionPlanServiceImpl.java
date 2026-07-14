package com.efitops.basesetup.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.ProductionPlanDTO;
import com.efitops.basesetup.dto.ProductionPlanDetailsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.ProductionPlanDetailsVO;
import com.efitops.basesetup.entity.ProductionPlanVO;
import com.efitops.basesetup.entity.RouteCardEntryVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.ProductionPlanDetailsRepo;
import com.efitops.basesetup.repo.ProductionPlanRepo;

@Service
public class ProductionPlanServiceImpl implements ProductionPlanService {

	public static final Logger LOGGER = LoggerFactory.getLogger(ProductionPlanServiceImpl.class);

	@Autowired
	ProductionPlanRepo productionPlanRepo;

	@Autowired
	ProductionPlanDetailsRepo productionPlanDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;
	
	@Autowired
	private CommonNotificationService commonNotificationService;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<ProductionPlanVO> getAllProductionPlanByOrgId(Long orgId) {
		List<ProductionPlanVO> productionPlanVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  ProductionPlan BY OrgId : {}", orgId);
			productionPlanVO = productionPlanRepo.getAllProductionPlanByOrgId(orgId);
		}
		return productionPlanVO;
	}

	@Override
	public List<ProductionPlanVO> getProductionPlanById(Long id) {
		List<ProductionPlanVO> productionPlanVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received ProductionPlan BY Id : {}", id);
			productionPlanVO = productionPlanRepo.getAllProductionPlanById(id);
		}
		return productionPlanVO;
	}

//	@Override
//	public Map<String, Object> createUpdateProductionPlan(ProductionPlanDTO productionPlanDTO)
//			throws ApplicationException {
//		String screenCode = "PP";
//		ProductionPlanVO productionPlanVO = new ProductionPlanVO();
//		String message;
//		if (ObjectUtils.isNotEmpty(productionPlanDTO.getId())) {
//			productionPlanVO = productionPlanRepo.findById(productionPlanDTO.getId())
//					.orElseThrow(() -> new ApplicationException("Production Plan not found"));
//
//			productionPlanVO.setModifiedBy(productionPlanDTO.getCreatedBy());
//			createUpdateProductionPlanVOByProductionPlanDTO(productionPlanDTO, productionPlanVO);
//			message = "ProductionPlan Updated Successfully";
//		} else {
//			// GETDOCID API
//			String docId = productionPlanRepo.getProductionPlanDocId(productionPlanDTO.getOrgId(), screenCode);
//			productionPlanVO.setDocId(docId);
//
//			// GETDOCID LASTNO +1
//			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
//					.findByOrgIdAndScreenCode(productionPlanDTO.getOrgId(),
//							 screenCode);
//			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
//			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
//			
//			productionPlanVO.setCreatedBy(productionPlanDTO.getCreatedBy());
//			productionPlanVO.setModifiedBy(productionPlanDTO.getCreatedBy());
//			createUpdateProductionPlanVOByProductionPlanDTO(productionPlanDTO, productionPlanVO);
//			message = "ProductionPlan Created Successfully";
//		}
//
//		productionPlanRepo.save(productionPlanVO);
//		Map<String, Object> response = new HashMap<>();
//		response.put("productionPlanVO", productionPlanVO);
//		response.put("message", message);
//		return response;
//	}
//
//	private void createUpdateProductionPlanVOByProductionPlanDTO(ProductionPlanDTO productionPlanDTO,
//			ProductionPlanVO productionPlanVO) { 
//
//		// Map fields from DTO to VO
//		productionPlanVO.setOrgId(productionPlanDTO.getOrgId());
////		productionPlanVO.setBranch(productionPlanDTO.getBranch());
////		productionPlanVO.setBranchCode(productionPlanDTO.getBranchCode());
////		productionPlanVO.setFinYear(productionPlanDTO.getFinYear());
//		productionPlanVO.setCreatedBy(productionPlanDTO.getCreatedBy());
//		productionPlanVO.setRouteCardNo(productionPlanDTO.getRouteCardNo());
//		productionPlanVO.setWoSoNo(productionPlanDTO.getWoSoNo());
//		productionPlanVO.setWoSoDate(productionPlanDTO.getWoSoDate());
//		productionPlanVO.setCustomerName(productionPlanDTO.getCustomerName());
//		productionPlanVO.setPart(productionPlanDTO.getPart());
//		productionPlanVO.setPartDesc(productionPlanDTO.getPartDesc());
//		productionPlanVO.setProductionQty(productionPlanDTO.getProductionQty());
//		productionPlanVO.setProductionStartDate(productionPlanDTO.getProductionStartDate());
//		productionPlanVO.setProductionEndDate(productionPlanDTO.getProductionEndDate());
//		productionPlanVO.setRawMaterial(productionPlanDTO.getRawMaterial());
//		productionPlanVO.setRawMaterialDesc(productionPlanDTO.getRawMaterialDesc());
//		productionPlanVO.setNarration(productionPlanDTO.getNarration());
//
//		if (ObjectUtils.isNotEmpty(productionPlanVO.getId())) {
//			List<ProductionPlanDetailsVO> productionPlanDetailsVO1 = productionPlanDetailsRepo
//					.findByProductionPlanVO(productionPlanVO);
//			productionPlanDetailsRepo.deleteAll(productionPlanDetailsVO1);
//		}
//
//		List<ProductionPlanDetailsVO> productionPlanDetailsVOs = new ArrayList<>();
//		for (ProductionPlanDetailsDTO productionPlanDetailsDTO : productionPlanDTO.getProductionPlanDetailsDTO()) {
//
//			ProductionPlanDetailsVO productionPlanDetailsVO = new ProductionPlanDetailsVO();
//			productionPlanDetailsVO.setProcess(productionPlanDetailsDTO.getProcess());
//			productionPlanDetailsVO.setQty(productionPlanDetailsDTO.getQty());
//			productionPlanDetailsVO.setFromDate(productionPlanDetailsDTO.getFromDate());
//			productionPlanDetailsVO.setToDate(productionPlanDetailsDTO.getToDate());
//			productionPlanDetailsVO.setMachineName(productionPlanDetailsDTO.getMachineName());
//			productionPlanDetailsVO.setMachineNo(productionPlanDetailsDTO.getMachineNo());
//			productionPlanDetailsVO.setTimeTakenInSec(productionPlanDetailsDTO.getTimeTakenInSec());
//			productionPlanDetailsVO.setTotalTimeTaken(productionPlanDetailsDTO.getTotalTimeTaken());
//			productionPlanDetailsVO.setTimeTakenInHours(productionPlanDetailsDTO.getTimeTakenInHours());
//			productionPlanDetailsVO.setQtyPerHr(productionPlanDetailsDTO.getQtyPerHr());
//			productionPlanDetailsVO.setExpMaxProd(productionPlanDetailsDTO.getExpMaxProd());
//			productionPlanDetailsVO.setExpMinProd(productionPlanDetailsDTO.getExpMinProd());
//			productionPlanDetailsVO.setStatus(productionPlanDetailsDTO.getStatus());
//			productionPlanDetailsVO.setProductionPlanVO(productionPlanVO);
//			productionPlanDetailsVOs.add(productionPlanDetailsVO);
//
//		}
//		productionPlanVO.setProductionPlanDetailsVO(productionPlanDetailsVOs);
//	}

	

	public Map<String, Object> createUpdateProductionPlan(ProductionPlanDTO dto)
			throws ApplicationException {
		String screenCode = "PP";
		ProductionPlanVO oldProductionPlan = null;
		
		ProductionPlanVO vo = new ProductionPlanVO();
		String message;
		if (ObjectUtils.isNotEmpty(dto.getId())) {
			oldProductionPlan = productionPlanRepo.findById(dto.getId())
		            .orElseThrow(() -> new ApplicationException("ProductionPlan not found"));

			oldProductionPlan.getProductionPlanDetailsVO().size(); // load
			
			entityManager.detach(oldProductionPlan); // detach snapshot
			
			
			vo = productionPlanRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Production Plan not found"));
	       
	        vo.setModifiedBy(dto.getCreatedBy());

	        validateAllDetails(dto);

	        createUpdateProductionPlanVOByProductionPlanDTO(dto, vo);

	        message = "ProductionPlan Updated Successfully";

	    } else {

	        String docId = productionPlanRepo.getProductionPlanDocId(dto.getOrgId(), screenCode);
	        vo.setDocId(docId);

	        DocumentTypeMappingDetailsVO doc =
	                documentTypeMappingDetailsRepo.findByOrgIdAndScreenCode(dto.getOrgId(), screenCode);

	        doc.setLastno(doc.getLastno() + 1);
	        documentTypeMappingDetailsRepo.save(doc);

	        vo.setCreatedBy(dto.getCreatedBy());
	        vo.setModifiedBy(dto.getCreatedBy());

	        validateAllDetails(dto);

	        createUpdateProductionPlanVOByProductionPlanDTO(dto, vo);

	        message = "ProductionPlan Created Successfully";
	    }

	    productionPlanRepo.save(vo);
	    commonNotificationService.generateNotification(vo.getScreenCode(), vo.getId(), oldProductionPlan,
				vo);
	    Map<String, Object> response = new HashMap<>();
	    response.put("productionPlanVO", vo);
	    response.put("message", message);

	    return response;
	}
	
	private void createUpdateProductionPlanVOByProductionPlanDTO(
	        ProductionPlanDTO dto,
	        ProductionPlanVO vo) {

	    vo.setOrgId(dto.getOrgId());
	    vo.setCreatedBy(dto.getCreatedBy());
	    vo.setRouteCardNo(dto.getRouteCardNo());
	    vo.setWoSoNo(dto.getWoSoNo());
	    vo.setWoSoDate(dto.getWoSoDate());
	    vo.setCustomerName(dto.getCustomerName());
	    vo.setPart(dto.getPart());
	    vo.setPartDesc(dto.getPartDesc());
	    vo.setProductionQty(dto.getProductionQty());
	    vo.setProductionStartDate(dto.getProductionStartDate());
	    vo.setProductionEndDate(dto.getProductionEndDate());
	    vo.setRawMaterial(dto.getRawMaterial());
	    vo.setRawMaterialDesc(dto.getRawMaterialDesc());
	    vo.setNarration(dto.getNarration());

	    // DELETE OLD DETAILS (for update)
	    if (vo.getId() != null) {
	        List<ProductionPlanDetailsVO> oldList =
	                productionPlanDetailsRepo.findByProductionPlanVO(vo);
	        productionPlanDetailsRepo.deleteAll(oldList);
	    }

	    List<ProductionPlanDetailsVO> detailsList = new ArrayList<>();

	    for (ProductionPlanDetailsDTO d : dto.getProductionPlanDetailsDTO()) {

	        ProductionPlanDetailsVO detail = new ProductionPlanDetailsVO();

	        detail.setProcess(d.getProcess());
	        detail.setQty(d.getQty());
	        detail.setFromDate(d.getFromDate());
	        detail.setToDate(d.getToDate());
	        detail.setMachineName(d.getMachineName());
	        detail.setMachineNo(d.getMachineNo());
	        detail.setTimeTakenInSec(d.getTimeTakenInSec());
	        detail.setTotalTimeTaken(d.getTotalTimeTaken());
	        detail.setTimeTakenInHours(d.getTimeTakenInHours());
	        detail.setQtyPerHr(d.getQtyPerHr());
	        detail.setExpMaxProd(d.getExpMaxProd());
	        detail.setExpMinProd(d.getExpMinProd());
	        detail.setStatus(d.getStatus());
	        detail.setProductionPlanVO(vo);

	        detailsList.add(detail);
	    }

	    vo.setProductionPlanDetailsVO(detailsList);
	}
	
	private void validateAllDetails(ProductionPlanDTO dto) throws ApplicationException {

	    if (dto.getProductionPlanDetailsDTO() == null ||
	        dto.getProductionPlanDetailsDTO().isEmpty()) {
	        return;
	    }

	    for (ProductionPlanDetailsDTO detail : dto.getProductionPlanDetailsDTO()) {

	        if (detail.getFromDate() == null || detail.getToDate() == null) {
	            throw new ApplicationException("From Date and To Date are required");
	        }

	        LocalDateTime from = parseStrictDateTime(detail.getFromDate());
	        LocalDateTime to = parseStrictDateTime(detail.getToDate());

	        if (to.isBefore(from)) {
	            throw new ApplicationException("To Date cannot be earlier than From Date");
	        }

	        validateMachineOverlap(detail, dto.getOrgId(), dto.getId());
	    }
	}
	
	private LocalDateTime parseStrictDateTime(String dateStr) throws ApplicationException {
	    try {
	        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        return LocalDateTime.parse(dateStr, dtf);
	    } catch (Exception e) {
	        throw new ApplicationException(
	            "Invalid date format: " + dateStr + " (Use yyyy-MM-dd HH:mm:ss)"
	        );
	    }
	}
	
	private LocalDateTime parseFlexibleFromDate(String dateStr) throws ApplicationException {
	    try {
	        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        return LocalDateTime.parse(dateStr, dtf);
	    } catch (Exception e) {
	        try {
	            return LocalDate.parse(dateStr).atStartOfDay(); // 00:00:00
	        } catch (Exception ex) {
	            throw new ApplicationException("Invalid date format: " + dateStr);
	        }
	    }}
		
	private LocalDateTime parseFlexibleToDate(String dateStr) throws ApplicationException {
	    try {
	        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        return LocalDateTime.parse(dateStr, dtf);
	    } catch (Exception e) {
	        try {
	            return LocalDate.parse(dateStr).atTime(23, 59, 59); // FULL DAY END
	        } catch (Exception ex) {
	            throw new ApplicationException("Invalid date format: " + dateStr);
	        }
	    }
	}
	
	private void validateMachineOverlap(ProductionPlanDetailsDTO dto,
            Long orgId,
            Long planId) throws ApplicationException {

	LocalDateTime newFrom = parseStrictDateTime(dto.getFromDate());
	LocalDateTime newTo = parseStrictDateTime(dto.getToDate());
	
	String machineName = dto.getMachineName().trim().toLowerCase();
	
	List<ProductionPlanDetailsVO> existingList =
	productionPlanDetailsRepo.findByMachineNameIgnoreCase(
	machineName, orgId);
	
	System.out.println("Size: " + existingList.size());
	
	if(existingList.isEmpty()) {
	    System.out.println("No records found");
	} else {
	    System.out.println("Records found: " + existingList.size());
	}
	
	// DEBUG (remove later)
	System.out.println("Existing records found: " + existingList.size());
	
	for (ProductionPlanDetailsVO existing : existingList) {
	
	// Ignore same record during update
	if (planId != null &&
	existing.getProductionPlanVO().getId().equals(planId)) {
	continue;
	}
	
	LocalDateTime existingFrom = parseFlexibleFromDate(existing.getFromDate());
	LocalDateTime existingTo   = parseFlexibleToDate(existing.getToDate());
	
	 System.out.println("------ CHECK ------");
	    System.out.println("New From: " + newFrom);
	    System.out.println("New To  : " + newTo);

	    System.out.println("Existing From: " + existingFrom);
	    System.out.println("Existing To  : " + existingTo);
	
	boolean isOverlap =
	!newFrom.isAfter(existingTo) && !newTo.isBefore(existingFrom);
	
	if (isOverlap) {
	throw new ApplicationException(
	"Machine " + dto.getMachineName() +
	" already booked from " + existing.getFromDate() +
	" to " + existing.getToDate()
	);
	}
	}
}
	
	
	@Override
	public String getProductionPlanDocId(Long orgId) {
		String screenCode = "PP";
		return productionPlanRepo.getProductionPlanDocId(orgId, screenCode);
	}

	@Override
	public List<Map<String, Object>> getRouteCardNoAndDetails(Long orgId) {
		Set<Object[]> routeCard = productionPlanRepo.getRouteCardNo(orgId);
		return getRouteCardNo(routeCard);
	}

	private List<Map<String, Object>> getRouteCardNo(Set<Object[]> route) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : route) {
			Map<String, Object> map = new HashMap<>();
			map.put("routeCardNo", ch[0] != null ? ch[0].toString() : "");
			map.put("woSoNo", ch[1] != null ? ch[1].toString() : "");
			map.put("woSoDate", ch[2] != null ? ch[2].toString() : "");
			map.put("customerName", ch[3] != null ? ch[3].toString() : "");
			map.put("partyCode", ch[4] != null ? ch[4].toString() : "");
			map.put("partyName", ch[5] != null ? ch[5].toString() : "");
			map.put("productionQty", ch[6] != null ? ch[6].toString() : "");
			map.put("partName", ch[7] != null ? ch[7].toString() : "");
			map.put("partDesc", ch[8] != null ? ch[8].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getRawMaterialDetails(Long orgId) {
		Set<Object[]> rawMaterial = productionPlanRepo.getRawMaterialDetails(orgId);
		return getRawMaterialDetails(rawMaterial);
	}

	private List<Map<String, Object>> getRawMaterialDetails(Set<Object[]> route) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : route) {
			Map<String, Object> map = new HashMap<>();
			map.put("rawMaterail", ch[0] != null ? ch[0].toString() : "");
			map.put("rawMaterailDesc", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getProcessName(Long orgId, String item) {
		Set<Object[]> process = productionPlanRepo.getProcessName(orgId, item);
		return getProcessDetails(process);
	}

	private List<Map<String, Object>> getProcessDetails(Set<Object[]> route) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : route) {
			Map<String, Object> map = new HashMap<>();
			map.put("processName", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getMachineName(Long orgId, String fromDate, Long id, String docId) {
		Set<Object[]> machine = productionPlanRepo.getMachineName(orgId, fromDate, id, docId);
		return getMachineDetails(machine);
	}

	private List<Map<String, Object>> getMachineDetails(Set<Object[]> route) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : route) {
			Map<String, Object> map = new HashMap<>();
			map.put("machineNo", ch[0] != null ? ch[0].toString() : "");
			map.put("machineName", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;
	}
	
	
	@Override
	public List<Map<String, Object>> getProductionPlanReport(Long orgId, String customerName, String routeCardNo) {

	    List<Object[]> routeCard =
	            productionPlanRepo.getProductionPlanReport(orgId, customerName, routeCardNo);

	    return buildProductionPlanReport(routeCard);
	}


	private List<Map<String, Object>> buildProductionPlanReport(List<Object[]> route) {

	    List<Map<String, Object>> list = new ArrayList<>();

	    for (Object[] ch : route) {

	        Map<String, Object> map = new LinkedHashMap<>();

	        map.put("customerName", safeStr(ch[0]));
	        map.put("docDate", ch[1]); // keep date as date
	        map.put("docId", safeStr(ch[2]));
	        map.put("narration", safeStr(ch[3]));
	        map.put("orgId", ch[4]);

	        map.put("part", safeStr(ch[5]));
	        map.put("partDesc", safeStr(ch[6]));

	        map.put("productionEndDate", ch[7]);
	        map.put("productionQty", ch[8]);
	        map.put("productionStartDate", ch[9]);

	        map.put("rawMaterial", safeStr(ch[10]));
	        map.put("rawMaterialDesc", safeStr(ch[11]));

	        map.put("routeCardNo", safeStr(ch[12]));
	        map.put("woSoDate", ch[13]);
	        map.put("woSoNo", safeStr(ch[14]));

	        list.add(map);
	    }

	    return list;
	}

	private String safeStr(Object obj) {
	    return obj != null ? obj.toString() : " ";
	}


}