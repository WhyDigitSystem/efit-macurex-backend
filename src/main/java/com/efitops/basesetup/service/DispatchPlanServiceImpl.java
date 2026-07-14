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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.DispatchPlanDTO;
import com.efitops.basesetup.dto.DispatchPlanDetailsDTO;
import com.efitops.basesetup.entity.DispatchPlanDetailsVO;
import com.efitops.basesetup.entity.DispatchPlanVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.PutawayVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DispatchPlanDetailsRepo;
import com.efitops.basesetup.repo.DispatchPlanRepo;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;

@Service
public class DispatchPlanServiceImpl implements DispatchPlanService {

	@Autowired
	DispatchPlanRepo dispatchPlanRepo;

	@Autowired
	DispatchPlanDetailsRepo dispatchPlanDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	private CommonNotificationService commonNotificationService;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<DispatchPlanVO> getDispatchPlanByOrgId(Long orgId) {
		List<DispatchPlanVO> dispatchPlanVO = new ArrayList<>();
		dispatchPlanVO = dispatchPlanRepo.getDispatchPlanByOrgId(orgId);

		return dispatchPlanVO;
	}

	@Override
	public DispatchPlanVO getDispatchPlanById(Long id) {
		DispatchPlanVO dispatchPlanVO = new DispatchPlanVO();

		dispatchPlanVO = dispatchPlanRepo.getDispatchPlanById(id);

		return dispatchPlanVO;
	}

	@Override
	public Map<String, Object> updateCreateDispatchPlan(@Valid DispatchPlanDTO dispatchPlanDTO)
			throws ApplicationException {
		String message;
		String screenCode = "DP";
		DispatchPlanVO oldDispatchPlan = null;

		DispatchPlanVO dispatchPlanVO = new DispatchPlanVO();

		if (dispatchPlanDTO.getId() != null) {
			oldDispatchPlan = dispatchPlanRepo.findById(dispatchPlanDTO.getId())
					.orElseThrow(() -> new ApplicationException("DispatchPlan not found"));

			oldDispatchPlan.getDispatchPlanDetailsVO().size(); // load

			entityManager.detach(oldDispatchPlan); // detach snapshot
			// Fetch existing ItemVO for update
			dispatchPlanVO = dispatchPlanRepo.findById(dispatchPlanDTO.getId())
					.orElseThrow(() -> new ApplicationException("DispatchPlan master not found"));
			dispatchPlanVO.setUpdatedBy(dispatchPlanDTO.getCreatedBy());

			createUpdateDispatchPlanVOByDispatchPlanDTO(dispatchPlanDTO, dispatchPlanVO);
			message = "DispatchPlan Master Updated Successfully";

			List<DispatchPlanDetailsVO> dispatchPlanDetailsVOs = dispatchPlanDetailsRepo
					.findByDispatchPlanVO(dispatchPlanVO);
			dispatchPlanDetailsRepo.deleteAll(dispatchPlanDetailsVOs);

		} else {

			// GETDOCID API
			String docId = dispatchPlanRepo.getDispatchPlanByDocId(dispatchPlanDTO.getOrgId(), screenCode);

			dispatchPlanVO.setDocId(docId);

//        							// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(dispatchPlanDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			// Create new ItemVO
			dispatchPlanVO.setCreatedBy(dispatchPlanDTO.getCreatedBy());
			dispatchPlanVO.setUpdatedBy(dispatchPlanDTO.getCreatedBy());
			createUpdateDispatchPlanVOByDispatchPlanDTO(dispatchPlanDTO, dispatchPlanVO);
			message = "DispatchPlan Master Created Successfully";
		}

		// Save the ItemVO
		dispatchPlanRepo.save(dispatchPlanVO);
		commonNotificationService.generateNotification(dispatchPlanVO.getScreenCode(), dispatchPlanVO.getId(),
				oldDispatchPlan, dispatchPlanVO);

		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("dispatchPlanVO", dispatchPlanVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateDispatchPlanVOByDispatchPlanDTO(@Valid DispatchPlanDTO dispatchPlanDTO,
			DispatchPlanVO dispatchPlanVO) throws ApplicationException {
		dispatchPlanVO.setRouteCardEntry(dispatchPlanDTO.getRouteCardEntry());
		dispatchPlanVO.setCustomerName(dispatchPlanDTO.getCustomerName());
		dispatchPlanVO.setCustomerCode(dispatchPlanDTO.getCustomerCode());
		dispatchPlanVO.setWorkOrderNo(dispatchPlanDTO.getWorkOrderNo());
		dispatchPlanVO.setScheduleDispatchDate(dispatchPlanDTO.getScheduleDispatchDate());
		dispatchPlanVO.setDispatchType(dispatchPlanDTO.getDispatchType());
		dispatchPlanVO.setNarration(dispatchPlanDTO.getNarration());
		dispatchPlanVO.setOrgId(dispatchPlanDTO.getOrgId());

		// Handling ItemInventoryVO

		List<DispatchPlanDetailsVO> dispatchPlanDetailsVOs = new ArrayList<>();

		// Map to track total delivery qty per item
		Map<String, BigDecimal> deliverySumMap = new HashMap<>();

		for (DispatchPlanDetailsDTO dispatchPlanDetailsDTO : dispatchPlanDTO.getDispatchPlanDetailsDTO()) {

		    DispatchPlanDetailsVO dispatchPlanDetailsVO = new DispatchPlanDetailsVO();

		    dispatchPlanDetailsVO.setItem(dispatchPlanDetailsDTO.getItem());
		    dispatchPlanDetailsVO.setItemDesc(dispatchPlanDetailsDTO.getItemDesc());
		    dispatchPlanDetailsVO.setUnit(dispatchPlanDetailsDTO.getUnit());

		    // Order Qty validation
		    validateQty(dispatchPlanDetailsDTO.getOrderQty(), "Order Qty");
		    dispatchPlanDetailsVO.setOrderQty(dispatchPlanDetailsDTO.getOrderQty());

		    Set<Object[]> itemDetails = dispatchPlanRepo.findItemDetailsForDispatchPlan(
		            dispatchPlanDTO.getOrgId(),
		            dispatchPlanDTO.getWorkOrderNo()
		    );

		    for (Object s[] : itemDetails) {

		        // Assuming s[0] = itemCode (adjust index if needed)
		        if (s[0].toString().equalsIgnoreCase(dispatchPlanDetailsDTO.getItem())) {

		            // Delivery Qty validation
		            validateQty(dispatchPlanDetailsDTO.getDeliveryQty(), "Delivery Qty");

		            // ---- SUM LOGIC ----
		            String itemKey = dispatchPlanDetailsDTO.getItem();

		            BigDecimal existingQty = deliverySumMap.getOrDefault(itemKey, BigDecimal.ZERO);
		            BigDecimal newTotal = existingQty.add(dispatchPlanDetailsDTO.getDeliveryQty());

		            BigDecimal orderQty = dispatchPlanDetailsDTO.getOrderQty();

		            // ---- CHECK ----
		            if (newTotal.compareTo(orderQty) > 0) {
		                throw new ApplicationException(
		                        "Total Delivery Qty for item " + itemKey +
		                        " exceeds Order Qty (" + orderQty + ")"
		                );
		            }

		            // store updated total
		            deliverySumMap.put(itemKey, newTotal);

		            dispatchPlanDetailsVO.setDeliveryQty(dispatchPlanDetailsDTO.getDeliveryQty());
		        }
		    }

		    dispatchPlanDetailsVO.setRemarks(dispatchPlanDetailsDTO.getRemarks());
		    dispatchPlanDetailsVO.setDispatchPlanVO(dispatchPlanVO);

		    dispatchPlanDetailsVOs.add(dispatchPlanDetailsVO);
		}

		dispatchPlanVO.setDispatchPlanDetailsVO(dispatchPlanDetailsVOs);
	}

	private void validateQty(BigDecimal qty, String fieldName) throws ApplicationException {
		if (qty == null || qty.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ApplicationException(fieldName + " must be greater than zero.");
		}
	}

	@Override
	public String getDispatchPlanDocId(Long orgId) {
		String screenCode = "DP";
		String result = dispatchPlanRepo.getDispatchPlanByDocId(orgId, screenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getRouteCardDetailsForDispatchPlan(Long orgId) {
		Set<Object[]> routeCardDetails = dispatchPlanRepo.findRouteCardDetailsForDispatchPlan(orgId);
		return getRouteCardDetailsForDispatchPlan(routeCardDetails);
	}

	private List<Map<String, Object>> getRouteCardDetailsForDispatchPlan(Set<Object[]> routeCardDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : routeCardDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("routeCardNo", ch[0] != null ? ch[0].toString() : "");
			map.put("customerCode", ch[1] != null ? ch[1].toString() : "");
			map.put("customerName", ch[2] != null ? ch[2].toString() : "");
			map.put("workOrderNo", ch[3] != null ? ch[3].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getItemDetailsForDispatchPlan(Long orgId, String workOrderNo) {
		Set<Object[]> itemDetails = dispatchPlanRepo.findItemDetailsForDispatchPlan(orgId, workOrderNo);
		return getItemDetailsForDispatchPlan(itemDetails);
	}

	private List<Map<String, Object>> getItemDetailsForDispatchPlan(Set<Object[]> itemDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : itemDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemCode", ch[0] != null ? ch[0].toString() : "");
			map.put("itemName", ch[1] != null ? ch[1].toString() : "");
			map.put("unit", ch[2] != null ? ch[2].toString() : "");
			map.put("ordqty", ch[3] != null ? ch[3].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	// Report APIs

	@Override
	public List<Map<String, Object>> getDispatchPlanReport(Long orgId, String fromDate, String toDate,
			String routeCardEntry) {

		Set<Object[]> reportData = dispatchPlanRepo.getDispatchPlanReport(orgId, fromDate, toDate, routeCardEntry);

		return mapDispatchPlanReport(reportData);
	}

	private List<Map<String, Object>> mapDispatchPlanReport(Set<Object[]> reportData) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : reportData) {

			Map<String, Object> map = new HashMap<>();
			map.put("dispatchPlanId", ch[0] != null ? ch[0].toString() : "");
			map.put("docId", ch[1] != null ? ch[1].toString() : "");
			map.put("docDate", ch[2] != null ? ch[2].toString() : "");
			map.put("routeCardNo", ch[3] != null ? ch[3].toString() : "");
			map.put("customerCode", ch[4] != null ? ch[4].toString() : "");
			map.put("customerName", ch[5] != null ? ch[5].toString() : "");
			map.put("workOrderNo", ch[6] != null ? ch[6].toString() : "");
			map.put("scheduleDispatchDate", ch[7] != null ? ch[7].toString() : "");
			map.put("dispatchType", ch[8] != null ? ch[8].toString() : "");
			map.put("narration", ch[9] != null ? ch[9].toString() : "");
			map.put("itemCode", ch[10] != null ? ch[10].toString() : "");
			map.put("itemName", ch[11] != null ? ch[11].toString() : "");
			map.put("unit", ch[12] != null ? ch[12].toString() : "");
			map.put("orderedQty", ch[13] != null ? ch[13].toString() : "");
			map.put("deliveryQty", ch[14] != null ? ch[14].toString() : "");
			map.put("remarks", ch[15] != null ? ch[15].toString() : "");

			list.add(map);
		}

		return list;
	}

}
