package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.FgIssueToPackingDTO;
import com.efitops.basesetup.dto.FinalFgPartStockUpdateDTO;
import com.efitops.basesetup.entity.FgIssueToPackingVO;
import com.efitops.basesetup.entity.FinalFgPartStockUpdateVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface AssemblyService {

	List<FinalFgPartStockUpdateVO> getAllFgPartStockUpdateVOByOrgId(Long orgId);

	FinalFgPartStockUpdateVO getFgPartStockUpdateVOById(Long id);

	Map<String, Object> updateCreateFgPartStockUpdate(FinalFgPartStockUpdateDTO finalFgPartStockUpdateDTO) throws ApplicationException;

	List<Map<String, Object>> getRouteCardEntryNoFromFgPartStockUpdate(Long orgId);

	List<Map<String, Object>> getRouteCardEntryDetailsFromFgPartStockUpdate(Long orgId, String routeCardEntryNo);

	List<Map<String, Object>> getItemDetailsFromFgPartStockUpdate(Long orgId, String fgPartName);
	
	//FgIssueToPacking
	
	List<FgIssueToPackingVO> getAllFgIssueToPackingVOByOrgId(Long orgId);

	FgIssueToPackingVO getFgIssueToPackingVOById(Long id);

	String getFgIssueToPackingDocId(Long orgId);

	Map<String, Object> updateCreateFgIssueToPacking(FgIssueToPackingDTO fgIssueToPackingDTO) throws ApplicationException;

	List<Map<String, Object>> getDeptfromFgIssueToPacking(Long orgId);

	List<Map<String, Object>> getRouteCardEntryNoFromFgIssueToPacking(Long orgId);

	List<Map<String, Object>> getItemDetailsFromFgIssueToPacking(Long orgId, String routeCardEntryNo);

	List<Map<String, Object>> getPriceDetails(Long orgId, String itemName);

	String getFgPartStockUpdateDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getPartNameAndDesc(Long orgId);

	List<Map<String, Object>> getFinalFgPartStockUpdateReport(Long orgId, String fromDate, String toDate,
			String partName);

	List<Map<String, Object>> getFgIssueToPackingReport(Long orgId, String fromDate, String toDate, String routeCardNo);

	


}
