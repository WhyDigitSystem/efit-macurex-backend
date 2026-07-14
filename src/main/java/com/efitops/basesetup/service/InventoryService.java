package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.ImageResponseDTO;
import com.efitops.basesetup.dto.ItemIssueToProductionDTO;
import com.efitops.basesetup.dto.PickListDTO;
import com.efitops.basesetup.dto.PutawayDTO;
import com.efitops.basesetup.dto.RouteCardEntryDTO;
import com.efitops.basesetup.entity.ItemIssueToProductionVO;
import com.efitops.basesetup.entity.PickListVO;
import com.efitops.basesetup.entity.PutawayVO;
import com.efitops.basesetup.entity.RouteCardEntryVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface InventoryService {

	// Putaway
	Map<String, Object> updateCreatePutaway(@Valid PutawayDTO putawayDTO) throws ApplicationException;

	List<PutawayVO> getPutawayByOrgId(Long orgId, String finYear, String branchCode);

	List<PutawayVO> getPutawayById(Long id);

	String getPutawayDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getGrnDetailsForPutaway(Long orgId);

	List<Map<String, Object>> getLocationCodeForPutaway(Long orgId);

	List<Map<String, Object>> getFillGridForPutaway(Long orgId, String grnNo);

	List<Map<String, Object>> getRackNoForPutaway(Long orgId);

	// routeCardEntry

	Map<String, Object> updateCreateRouteCardEntry(@Valid RouteCardEntryDTO routeCardEntryDTO)
			throws ApplicationException;

	List<RouteCardEntryVO> getRouteCardEntryByOrgId(Long orgId, String finYear, String branchCode);

	List<RouteCardEntryVO> getRouteCardEntryById(Long id);

	String getRouteCardEntryDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getCustomerNameAndCodeFromRouteCardEntry(Long orgId);

	List<Map<String, Object>> getWorkOrderNoFromRouteCardEntry(Long orgId, String customerCode);

	List<Map<String, Object>> getFgPartNameAndDescAndQtyFromRouteCardEntry(Long orgId, String workOrderNo);

	List<Map<String, Object>> getOptrSignFromRouteCardEntry(Long orgId);

	List<Map<String, Object>> getPreparedByFromRouteCardEntry(Long orgId);

	List<Map<String, Object>> getApprovedByFromRouteCardEntry(Long orgId);

	List<Map<String, Object>> getQAManagerSignFromRouteCardEntry(Long orgId);

	List<Map<String, Object>> getPlantManagerSignFromRouteCardEntry(Long orgId);

	RouteCardEntryVO uploadFileForRouteCardEntry(MultipartFile file, Long id) throws IOException;

	// PickList
	Map<String, Object> updateCreatePickList(@Valid PickListDTO pickListDTO) throws ApplicationException;

	List<PickListVO> getPickListByOrgId(Long orgId, String finYear, String branchCode);

	List<PickListVO> getPickListById(Long id);

	String getPickListDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getItemIssueToProductionDetailsfromPickList(Long orgId, String itemIssueToProduction);

	List<Map<String, Object>> getRouteCardEntryNoForPickList(Long orgId, String customerCode);

	// ItemIssueToProduction

	Map<String, Object> updateCreateItemIssToProd(@Valid ItemIssueToProductionDTO itemIssueToProductionDTO)
			throws ApplicationException;

	List<ItemIssueToProductionVO> getItemIssueToProductionByOrgId(Long orgId, String finYear, String branchCode);

	List<ItemIssueToProductionVO> getItemIssToProdById(Long id);

	String getItemIssueToProductionDocId(Long orgId, String finYear, String branchCode);

//	List<Map<String, Object>> getRouteCardEntryNoForItemIssueToProduction(Long orgId, String customerCode);
	
	List<Map<String, Object>> getRouteCardEntryNoForItemIssueToProduction(Long orgId);


	List<Map<String, Object>> getRouteCardEntryDetailsForItemIssueToProduction(Long orgId, String routeCardNo);

	List<Map<String, Object>> getItemIssueToProductionDetailsfromBom(Long orgId, String fgItemId);

	List<Map<String, Object>> getItemIssueToProductionNofromPickList(Long orgId, String routeCardEntryNo);

	List<Map<String, Object>> getItemIssueQty(Long orgId, String routeCardNo, String workorder, String item);

	List<Map<String, Object>> getRackDetails(Long orgId, String item);

	List<Map<String, Object>> getRackNoForRackDetails(Long orgId, String branchCode, String itemCode);
	
	//Report
	
	List<Map<String, Object>> getRouteCardEntryReport(
	        Long orgId, String status
	);


	List<Map<String, Object>> getPutAwayDetails(Long orgId, String supplierName, String fromDate, String toDate,
			String branchCode,String grnNo);

	List<Map<String, Object>> getItemIssueToProductionNoforPickList(Long orgId, String routeCardEntryNo,String branchCode);

	List<Map<String, Object>> getPickListReport(Long orgId, String itemIssueToProductionNo, String branchCode, String routeCardEntryNo);

	List<Map<String, Object>> getItemIssuedProductionDetails(Long orgId, String routecardno);

	List<Map<String, Object>> getRouteCardNoAndItemIssueNumber(Long orgId);

	ResponseEntity<byte[]> viewFileRouteCard(HttpServletRequest request) throws IOException, IOException;

	Map<String, Object> createUpdateRouteCardEntry(MultipartFile[] files, String docId, String screenName,
			String module) throws ApplicationException, IOException;

	List<ImageResponseDTO> getRouteCardEntryImages(Long id) throws Exception;


}
