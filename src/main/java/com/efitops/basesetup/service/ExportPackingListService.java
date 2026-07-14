package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.ExportPackingListDTO;
import com.efitops.basesetup.entity.ExportPackingListVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface ExportPackingListService {

	List<ExportPackingListVO> getExportPackingListByOrgId(Long orgid, String finYear, String branchCode);

	ExportPackingListVO getExportPackingListById(Long id);

	String getExportPackingListDocId(Long orgId, String finYear, String branchCode);

	Map<String, Object> updateCreateExportPackingList(@Valid ExportPackingListDTO exportPackingListDTO) throws ApplicationException;

	List<Map<String, Object>> getCustomerNameAndCodeForExportPackingList(Long orgId);

	List<Map<String, Object>> getCustomerDetailsForExportPackingList(Long orgId, String customerCode);

	List<Map<String, Object>> getAllCountryForExportPackingList(Long orgId);

	List<Map<String, Object>> getSalesOrderNoForExportPackingList(Long orgId, String customerCode);

	List<Map<String, Object>> getSalesOrderDetailsForExportPackingList(Long orgId, String salesOrderNo);

	List<Map<String, Object>> getCustomerDetailsForExportPackingListReport(Long orgId, String CustomerCode);

	List<Map<String, Object>> getItemDetailsFromExportPackingListReport(Long orgId, String salesOrderNo,
			String exportPackingListDocId);

	List<Map<String, Object>> getExportPackingListReport(Long orgId, String fromdate, String todate,
			String customername, String salesorderno);  
  

	
}
