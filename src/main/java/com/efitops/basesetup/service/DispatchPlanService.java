package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.DispatchPlanDTO;
import com.efitops.basesetup.entity.DispatchPlanVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface DispatchPlanService {

	List<DispatchPlanVO> getDispatchPlanByOrgId(Long orgId);

	DispatchPlanVO getDispatchPlanById(Long id);

	Map<String, Object> updateCreateDispatchPlan(DispatchPlanDTO dispatchPlanDTO) throws ApplicationException;

	String getDispatchPlanDocId(Long orgId);

	List<Map<String, Object>> getRouteCardDetailsForDispatchPlan(Long orgId);

	List<Map<String, Object>> getItemDetailsForDispatchPlan(Long orgId, String workOrderNo);
	
	// Report APIs
	
	List<Map<String, Object>> getDispatchPlanReport(Long orgId, String fromDate, String toDate, String routeCardEntry);

}
