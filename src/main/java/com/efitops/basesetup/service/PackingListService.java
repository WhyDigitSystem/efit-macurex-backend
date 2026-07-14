package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.PackingListDTO;
import com.efitops.basesetup.entity.PackingListVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface PackingListService {

	// PackingList

	List<PackingListVO> getAllPackingListByOrgId(Long orgId, String finYear, String branchCode);

	PackingListVO getPackingListById(Long packingListId);

	Map<String, Object> createUpdatePackingList(PackingListDTO packingListDTO) throws ApplicationException;

	String getPackingListDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getCustomerNameFromPartyMasterPacking(Long orgId);

	List<Map<String, Object>> getDocIdFromSalesOrderNo(Long orgId, String customerName);

	List<Map<String, Object>> getPartNoFromSalesOrder(Long orgId, String salesOrderNo);

	List<Map<String, Object>> getPackingListDetails(Long orgId, String fromdate, String todate, String customername,
			String salesorderno);

	
}
