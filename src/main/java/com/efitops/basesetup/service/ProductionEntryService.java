package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import com.efitops.basesetup.ResponseDTO.PreDeliveryInspectionResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProductionEntryResponseDTO;
import com.efitops.basesetup.dto.PreDeliveryInspectionDTO;
import com.efitops.basesetup.dto.ProductionEntryDTO;
import com.efitops.basesetup.exception.ApplicationException;

public interface ProductionEntryService {

	Map<String, Object> createUpdateProductionEntry(ProductionEntryDTO dto) throws ApplicationException;

	String getProductionEntryDocId(Long orgId, String financialYear);

	ProductionEntryResponseDTO getProductionEntryById(Long id) throws ApplicationException;

	List<ProductionEntryResponseDTO> getProductionEntryByOrgId(Long orgId, Long branch) throws ApplicationException;

	List<Map<String, Object>> getSchNoFromProductionEntry(Long orgId, Long branch, Long fgItem);

	List<Map<String, Object>> getBomNoFromProductionEntry(Long orgId, Long branch);

	// Pre order

	Map<String, Object> createUpdatePreDeliveryInspection(PreDeliveryInspectionDTO dto) throws ApplicationException;

	String getPreDeliveryInspectionDocId(Long orgId, String financialYear) throws ApplicationException;

	PreDeliveryInspectionResponseDTO getPreDeliveryInspectionById(Long id) throws ApplicationException;

	List<PreDeliveryInspectionResponseDTO> getPreDeliveryInspectionByOrgId(Long orgId, Long branch)
			throws ApplicationException;

	List<Map<String, Object>> getFgTransferSlipNo(Long orgId, Long branch);

	List<Map<String, Object>> getItemDetailsFromFgTransferSlipNo(Long orgId, Long branch, String transferSlipNo);

	List<Map<String, Object>> getInitialPlanningNo(Long orgId, Long item);

	List<Map<String, Object>> getInspectionDetailsFromFgTransferSlipNo(Long orgId, Long item, String transferSlipNo);
}
