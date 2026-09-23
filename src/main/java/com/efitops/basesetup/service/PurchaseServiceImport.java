package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.BillOfMaterialResponseDTO;
import com.efitops.basesetup.ResponseDTO.ConsumptionEntryResponseDTO;
import com.efitops.basesetup.ResponseDTO.DirectPurchaseResponseDTO;
import com.efitops.basesetup.ResponseDTO.FgTransferSlipResponseDTO;
import com.efitops.basesetup.ResponseDTO.MaterialIndentForProductionResponseDTO;
import com.efitops.basesetup.ResponseDTO.MaterialTransferReturnNoteResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProductionBulkIssueResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProductionIssueResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProductionSchOrderShortCloseResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProductionScheduleOrderResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProductionTransferSlipResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseOrderDeliveryScheduleShortCloseResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseOrderResponseDTO;
import com.efitops.basesetup.ResponseDTO.ScrapNoteResponseDTO;
import com.efitops.basesetup.ResponseDTO.StockTransferResponseDTO;
import com.efitops.basesetup.dto.BillOfMaterialDTO;
import com.efitops.basesetup.dto.ConsumptionEntryDTO;
import com.efitops.basesetup.dto.DirectPurchaseDTO;
import com.efitops.basesetup.dto.FgTransferSlipDTO;
import com.efitops.basesetup.dto.MaterialIndentForProductionDTO;
import com.efitops.basesetup.dto.MaterialTransferReturnNoteDTO;
import com.efitops.basesetup.dto.PoType;
import com.efitops.basesetup.dto.ProductionBulkIssueDTO;
import com.efitops.basesetup.dto.ProductionIssueDTO;
import com.efitops.basesetup.dto.ProductionSchOrderShortCloseDTO;
import com.efitops.basesetup.dto.ProductionScheduleOrderDTO;
import com.efitops.basesetup.dto.ProductionTransferSlipDTO;
import com.efitops.basesetup.dto.PurchaseOrderDTO;
import com.efitops.basesetup.dto.PurchaseOrderDeliveryScheduleShortCloseDTO;
import com.efitops.basesetup.dto.ScrapNoteDTO;
import com.efitops.basesetup.dto.StockTransferDTO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface PurchaseServiceImport {

	Map<String, Object> createUpdatePurchaseOrder(PurchaseOrderDTO purchaseOrderDTO, MultipartFile[] files)
			throws ApplicationException;

	ResponseEntity<byte[]> viewPurchaseOrderFile(HttpServletRequest request) throws IOException;

	List<PurchaseOrderResponseDTO> getPurchaseOrderByOrgId(Long orgId, Long branch) throws ApplicationException;

	List<Map<String, Object>> getItemDetailsResponsePurchaseLocal(Long orgId, Long branch);

	List<Map<String, Object>> getItemDetailsResponsePurchaseImport(Long orgId, Long branch);

	List<Map<String, Object>> getSupplierDetails(Long orgId, Long branch);

	String getPurchaseOrderDocId(Long orgId, String financialYear, String screenCode, PoType type);

	List<Map<String, Object>> getExchangeRateDetails(Long orgId, Long branch, Long currency);

	List<Map<String, Object>> getMutipleFactorAmount(Long orgId, Long primaryUnit, Long purchaseUnit);

	PurchaseOrderResponseDTO getPurchaseOrderById(Long id, PoType type) throws ApplicationException;

	List<Map<String, Object>> getIndentNoBasedLocal(Long orgId, String belongsTo, String type);

	List<Map<String, Object>> getIndentNoBasedImport(Long orgId, String type);

	List<Map<String, Object>> getHsnCodeDetails(Long orgId, Long branch, Long item, String type);

	PurchaseOrderDeliveryScheduleShortCloseResponseDTO getPurchaseOrderDeliveryScheduleShortCloseById(Long id)
			throws ApplicationException;

	List<PurchaseOrderDeliveryScheduleShortCloseResponseDTO> getPurchaseOrderDeliveryScheduleShortCloseByOrgId(
			Long orgId, Long branch) throws ApplicationException;

	Map<String, Object> createUpdatePurchaseOrderDeliveryScheduleShortClose(
			PurchaseOrderDeliveryScheduleShortCloseDTO purchaseOrderDeliveryScheduleShortCloseDTO)
			throws ApplicationException;

	List<Map<String, Object>> getSupplierDetailsShortClose(Long orgId, Long branch);

	List<Map<String, Object>> getPurchaseOrderNobasedSchedule(Long orgId, Long branch, Long supplier);

	List<Map<String, Object>> getPurchaseOrderNobasedScheduleDetails(Long orgId, Long branch, Long supplier,
			String purchaseOrderNo);

	String getPurchaseOrderDeliveryScheduleShortCloseDocId(Long orgId, String financialYear);

	// Direction

	DirectPurchaseResponseDTO getDirectPurchaseById(Long id) throws ApplicationException;

	List<DirectPurchaseResponseDTO> getDirectPurchaseByOrgId(Long orgId, Long branch) throws ApplicationException;

	Map<String, Object> createUpdateDirectPurchase(DirectPurchaseDTO directPurchaseDTO, MultipartFile[] files)
			throws ApplicationException;

	String getDirectPurchaseDocId(Long orgId, String financialYear);

	ResponseEntity<byte[]> viewDirectPurchaseFile(HttpServletRequest request) throws IOException;

	List<Map<String, Object>> getIssueTo(Long orgId, Long branch);

	List<Map<String, Object>> getItemType(Long orgId, Long branch, Long itemType);

	// Applic

	StockTransferResponseDTO getStockTransferById(Long id) throws ApplicationException;

	List<StockTransferResponseDTO> getStockTransferByOrgId(Long orgId, Long branch) throws ApplicationException;

	Map<String, Object> createUpdateStockTransfer(StockTransferDTO stockTransferDTO) throws ApplicationException;

	String getStockTransferDocId(Long orgId, String financialYear);

	List<Map<String, Object>> getStockTransferItemDetails(Long orgId, Long branch);

	// Production

	String getProductionScheduleOrderDocId(Long orgId, String financialYear);

	Map<String, Object> createUpdateProductionScheduleOrder(ProductionScheduleOrderDTO productionScheduleOrderDTO)
			throws ApplicationException;

	ProductionScheduleOrderResponseDTO getProductionScheduleOrderById(Long id) throws ApplicationException;

	List<ProductionScheduleOrderResponseDTO> getProductionScheduleOrderByOrgId(Long orgId, Long branch)
			throws ApplicationException;

	// BOM

	Map<String, Object> createUpdateBillOfMaterial(BillOfMaterialDTO billOfMaterialDTO) throws ApplicationException;

	String getBillOfMaterialDocId(Long orgId, String financialYear);

	BillOfMaterialResponseDTO getBillOfMaterialById(Long id) throws ApplicationException;

	List<BillOfMaterialResponseDTO> getBillOfMaterialByOrgId(Long orgId, Long branch) throws ApplicationException;

	List<Map<String, Object>> getFgAndSfgItemDetails(Long orgId, Long branch, String type);

	List<Map<String, Object>> getGridDetailsFromBom(Long orgId, Long branch);

	List<Map<String, Object>> getFillDetailsOf(Long orgId, Long branch, Long fgItem);

	List<Map<String, Object>> getFgAndSfgItemDetailsFromProduction(Long orgId, Long branch);

	List<Map<String, Object>> getFgAndSfgItemDetailsFromProductionDetails(Long orgId, Long branch, Long bom);

	// Material

	Map<String, Object> createUpdateMaterialIndentForProduction(MaterialIndentForProductionDTO dto)
			throws ApplicationException;

	String getMaterialIndentForProductionDocId(Long orgId, String financialYear);

	MaterialIndentForProductionResponseDTO getMaterialIndentForProductionById(Long id) throws ApplicationException;

	List<MaterialIndentForProductionResponseDTO> getMaterialIndentForProductionByOrgId(Long orgId, Long branch)
			throws ApplicationException;

	List<Map<String, Object>> getFgAndSfgItemDetailsFromMaterial(Long orgId, Long branch);

	List<Map<String, Object>> getFgAndSfgItemDetailsFromMaterialDetails(Long orgId, Long branch, Long fgItem);

	// Production

	Map<String, Object> createUpdateProductionTransferSlip(ProductionTransferSlipDTO dto) throws ApplicationException;

	String getProductionTransferSlipDocId(Long orgId, String financialYear);

	ProductionTransferSlipResponseDTO getProductionTransferSlipById(Long id) throws ApplicationException;

	List<ProductionTransferSlipResponseDTO> getProductionTransferSlipByOrgId(Long orgId, Long branch)
			throws ApplicationException;

	List<Map<String, Object>> getScrapDetailsItem(Long orgId, Long branch);

	List<Map<String, Object>> getSfGDocIdAndDetails(Long orgId, Long branch);

	// slip

	Map<String, Object> createUpdateFgTransferSlip(FgTransferSlipDTO dto) throws ApplicationException;

	String getFgTransferSlipDocId(Long orgId, String financialYear);

	FgTransferSlipResponseDTO getFgTransferSlipById(Long id) throws ApplicationException;

	List<FgTransferSlipResponseDTO> getFgTransferSlipByOrgId(Long orgId, Long branch) throws ApplicationException;

	List<Map<String, Object>> getFgPartNoDetails(Long orgId, Long branch);

	List<Map<String, Object>> getSfgPartNoDetails(Long orgId, Long branch);

	List<Map<String, Object>> getSchNoFromTransferSlip(Long orgId, Long branch, Long fgItem, Long sfgItem);

	List<Map<String, Object>> getBomNoFromTransferSlip(Long orgId, Long branch, Long fgItem, Long sfgItem);

	List<Map<String, Object>> getBomNoFromTransferSlipDetails(Long orgId, Long branch, Long bom);

	List<Map<String, Object>> getBomFromFgTransferSlip(Long orgId, Long branch, Long fgItem);

	List<Map<String, Object>> getSchNoFromFgTransferSlip(Long orgId, Long branch);

	List<Map<String, Object>> getCustomersDetailsFromTransferSlip(Long orgId, Long branch);

	List<Map<String, Object>> getBomDetailsFromFgTransferSlip(Long orgId, Long branch, Long bom);

	// Consum

	Map<String, Object> createUpdateConsumptionEntry(ConsumptionEntryDTO dto) throws ApplicationException;

	String getConsumptionEntryDocId(Long orgId, String financialYear);

	ConsumptionEntryResponseDTO getConsumptionEntryById(Long id) throws ApplicationException;

	List<ConsumptionEntryResponseDTO> getConsumptionEntryByOrgId(Long orgId, Long branch) throws ApplicationException;

	List<Map<String, Object>> getFgAndSfgItemDetailsConsumptionEntry(Long orgId, Long branch);

	List<Map<String, Object>> getRawMaterialConsumptionEntry(Long orgId, Long branch, Long fgItem);

	// material

	Map<String, Object> createUpdateMaterialTransferReturnNote(MaterialTransferReturnNoteDTO dto)
			throws ApplicationException;

	String getMaterialTransferReturnNoteDocId(Long orgId, String financialYear) throws ApplicationException;

	MaterialTransferReturnNoteResponseDTO getMaterialTransferReturnNoteById(Long id) throws ApplicationException;

	List<MaterialTransferReturnNoteResponseDTO> getMaterialTransferReturnNoteByOrgId(Long orgId, Long branch)
			throws ApplicationException;

	List<Map<String, Object>> getFgAndSfgFromMaterialTransferReturnNote(Long orgId, Long branch);

	List<Map<String, Object>> getSchNoFromMaterialTransferReturnNote(Long orgId, Long branch);

	List<Map<String, Object>> getSchNoItemDetailsFromMaterialTransferReturnNote(Long orgId, Long branch, String schNo);

	// Note

	Map<String, Object> createUpdateScrapNote(ScrapNoteDTO dto) throws ApplicationException;

	String getScrapNoteDocId(Long orgId, String financialYear) throws ApplicationException;

	ScrapNoteResponseDTO getScrapNoteById(Long id) throws ApplicationException;

	List<ScrapNoteResponseDTO> getScrapNoteByOrgId(Long orgId, Long branch) throws ApplicationException;

	List<Map<String, Object>> getSchNoFromScrapNote(Long orgId, Long branch);

	List<Map<String, Object>> getBomNoFromScrapNote(Long orgId, Long branch);

	List<Map<String, Object>> getScrapPartNo(Long orgId, Long branch);

	List<Map<String, Object>> getScrapNoteItemDetails(Long orgId, Long branch, Long bom);

	// ShortClose

	Map<String, Object> createUpdateProductionSchOrderShortClose(ProductionSchOrderShortCloseDTO dto)
			throws ApplicationException;

	String getProductionSchOrderShortCloseDocId(Long orgId, String financialYear) throws ApplicationException;

	ProductionSchOrderShortCloseResponseDTO getProductionSchOrderShortCloseById(Long id) throws ApplicationException;

	List<ProductionSchOrderShortCloseResponseDTO> getProductionSchOrderShortCloseByOrgId(Long orgId, Long branch)
			throws ApplicationException;

	List<Map<String, Object>> getItemDetailsFromProductionShortClose(Long orgId, Long branch);

	List<Map<String, Object>> getSchOrderNoProductionShortClose(Long orgId, Long branch);

	// Production

	Map<String, Object> createUpdateProductionIssue(ProductionIssueDTO dto) throws ApplicationException;

	String getProductionIssueDocId(Long orgId, String financialYear) throws ApplicationException;

	ProductionIssueResponseDTO getProductionIssueById(Long id) throws ApplicationException;

	List<ProductionIssueResponseDTO> getProductionIssueByOrgId(Long orgId, Long branch) throws ApplicationException;

	// Bulk

	Map<String, Object> createUpdateProductionBulkIssue(ProductionBulkIssueDTO dto) throws ApplicationException;

	String getProductionBulkIssueDocId(Long orgId, String financialYear) throws ApplicationException;

	ProductionBulkIssueResponseDTO getProductionBulkIssueById(Long id) throws ApplicationException;

	List<ProductionBulkIssueResponseDTO> getProductionBulkIssueByOrgId(Long orgId, Long branch)
			throws ApplicationException;

}
