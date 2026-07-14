package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.ProductionPlanDTO;
import com.efitops.basesetup.entity.ProductionPlanVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface ProductionPlanService {

	List<ProductionPlanVO> getAllProductionPlanByOrgId(Long orgId);

	List<ProductionPlanVO> getProductionPlanById(Long productionPlanId);

	Map<String, Object> createUpdateProductionPlan(ProductionPlanDTO productionPlanDTO) throws ApplicationException;

	String getProductionPlanDocId(Long orgId);

	List<Map<String, Object>> getRouteCardNoAndDetails(Long orgId);

	List<Map<String, Object>> getRawMaterialDetails(Long orgId);

	List<Map<String, Object>> getProcessName(Long orgId, String item);

	List<Map<String, Object>> getMachineName(Long orgId, String fromDate, Long id, String docid);

	List<Map<String, Object>> getProductionPlanReport(Long orgId, String customerName, String routeCardNo);

}
