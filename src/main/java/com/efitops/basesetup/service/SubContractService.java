package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.AdvForStoresResponseDTO;
import com.efitops.basesetup.ResponseDTO.BomCorrectionRequestNoteResponseDTO;
import com.efitops.basesetup.ResponseDTO.DeliveryChallanCapitalItemsResponseDTO;
import com.efitops.basesetup.ResponseDTO.DeliveryChallanCumGatePassResponseDTO;
import com.efitops.basesetup.ResponseDTO.DeliveryChallanSubcontractingResponseDTO;
import com.efitops.basesetup.ResponseDTO.InspectionRequisitionNoteResponseDTO;
import com.efitops.basesetup.ResponseDTO.JobOrderAmendmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.JobOrderResponseDTO;
import com.efitops.basesetup.ResponseDTO.JobOrderShortCloseResponseDTO;
import com.efitops.basesetup.ResponseDTO.MaterialPlanningResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProductionScheduleForNextThreeMonthResponseDTO;
import com.efitops.basesetup.ResponseDTO.SubContractSupplyScheduleResponseDTO;
import com.efitops.basesetup.ResponseDTO.SubContractingGRNResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierRateContractAmendmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierRateContractResponseDTO;
import com.efitops.basesetup.dto.AdvForStoresDTO;
import com.efitops.basesetup.dto.BomCorrectionRequestNoteDTO;
import com.efitops.basesetup.dto.DeliveryChallanCapitalItemsDTO;
import com.efitops.basesetup.dto.DeliveryChallanCumGatePassDTO;
import com.efitops.basesetup.dto.DeliveryChallanSubcontractingDTO;
import com.efitops.basesetup.dto.InspectionRequisitionNoteDTO;
import com.efitops.basesetup.dto.JobOrderAmendmentDTO;
import com.efitops.basesetup.dto.JobOrderDTO;
import com.efitops.basesetup.dto.JobOrderShortCloseDTO;
import com.efitops.basesetup.dto.MaterialPlanningDTO;
import com.efitops.basesetup.dto.ProductionScheduleForNextThreeMonthDTO;
import com.efitops.basesetup.dto.SubContractSupplyScheduleDTO;
import com.efitops.basesetup.dto.SubContractingGRNDTO;
import com.efitops.basesetup.dto.SupplierRateContractAmendmentDTO;
import com.efitops.basesetup.dto.SupplierRateContractDTO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface SubContractService {

	Map<String, Object> createUpdateSupplierRateContract(SupplierRateContractDTO supplierRateContractDTO)
			throws ApplicationException;

	List<Map<String, Object>> getCustomerForSupplierRateContract(Long orgId, Long branch);

	List<Map<String, Object>> getServiceForSupplierRateContract(Long orgId, Long branch);

	SupplierRateContractResponseDTO getSupplierRateContractById(Long id) throws ApplicationException;

	List<SupplierRateContractResponseDTO> getSupplierRateContractByOrgIdAndBranch(Long orgId, Long branch)
			throws ApplicationException;

	String getSupplierRateContractDocId(Long orgId, String financialYear);

	List<Map<String, Object>> getSupplierRateContractItemDropdown(Long orgId, Long branch);

	Map<String, Object> createUpdateJobOrder(JobOrderDTO jobOrderDTO, MultipartFile[] files)
			throws ApplicationException;

	List<Map<String, Object>> getSupplierRateContractDropdown(Long customer, Long orgId, Long branch);

	List<Map<String, Object>> getSupplierRateContractItemDetailsForJobOrder(String docId, Long orgId, Long branch);

	String getJobOrderDocId(Long orgId, String financialYear);

	List<JobOrderResponseDTO> getJobOrderByOrgIdAndBranch(Long orgId, Long branch) throws ApplicationException;

	JobOrderResponseDTO getJobOrderById(Long id) throws ApplicationException;

	Map<String, Object> createUpdateJobOrderAmendment(JobOrderAmendmentDTO jobOrderAmendmentDTO)
			throws ApplicationException;

	List<Map<String, Object>> getJobOrderNoAndDateForJobOrderAmd(Long branch, Long orgId, Long customer);

	Integer getNextRevisionNoForJobOrderAmd(String jobOrderNo, Long branch, Long orgId);

	List<Map<String, Object>> getJobOrderItemDetailsForJobOrderAmd(String jobOrderNo, Long branch, Long orgId,
			Long customer);

	JobOrderAmendmentResponseDTO getJobOrderAmendmentById(Long id) throws ApplicationException;

	List<JobOrderAmendmentResponseDTO> getJobOrderAmendmentByOrgIdAndBranch(Long orgId, Long branch)
			throws ApplicationException;

	String getJobOrderAmendmentDocId(Long orgId, String financialYear);

	Map<String, Object> createUpdateDeliveryChallanSubcontracting(
			DeliveryChallanSubcontractingDTO deliveryChallanSubcontractingDTO) throws ApplicationException;

	List<Map<String, Object>> getLocationForDeliverChallanSubContract(Long orgId, Long branch);

	List<Map<String, Object>> getItemDetailsforDeliveryChallanSubContract(String jobOrderNo, Long branch, Long orgId,
			Long vendor);

	List<DeliveryChallanSubcontractingResponseDTO> getAllDeliveryChallanSubcontractingByOrgIdAndBranch(Long orgId,
			Long branch) throws ApplicationException;

	DeliveryChallanSubcontractingResponseDTO getDeliveryChallanSubcontractingById(Long id) throws ApplicationException;

	String getDeliveryChallanSubcontractingDocId(Long orgId, String financialYear) throws ApplicationException;

	// SubContractSupplySchedule
	Map<String, Object> createUpdateSubContractSupplySchedule(SubContractSupplyScheduleDTO subContractSupplyScheduleDTO)
			throws ApplicationException;

	List<Map<String, Object>> getJobOrderNoAndDateForSubContractSupplySch(Long branch, Long orgId, String contractNo);

	SubContractSupplyScheduleResponseDTO getSubContractSupplyScheduleById(Long id) throws ApplicationException;

	List<SubContractSupplyScheduleResponseDTO> getSubContractSupplyScheduleByOrgIdAndBranch(Long orgId, Long branch)
			throws ApplicationException;

	String getSubContractSupplyScheduleDocId(Long orgId, String financialYear);

	// SupplierRateContractAmd
	Map<String, Object> createUpdateSupplierRateContractAmendment(
			SupplierRateContractAmendmentDTO supplierRateContractAmendmentDTO) throws ApplicationException;

	String getSupplierRateContractAmendmentDocId(Long orgId, String financialYear);

	SupplierRateContractAmendmentResponseDTO getSupplierRateContractAmendmentById(Long id) throws ApplicationException;

	List<SupplierRateContractAmendmentResponseDTO> getSupplierRateContractAmendmentByOrgIdAndBranch(Long orgId,
			Long branch) throws ApplicationException;

	List<Map<String, Object>> getRevisionNoDetailsForSupplierRateContractAmd(String contractNo, Long orgId,
			Long branch);

	List<Map<String, Object>> getSupplierRateContractItemDetailsForSRCAmd(String contractNo, Long orgId, Long branch);

	Map<String, Object> createUpdateProductionScheduleForNextThreeMonth(
			ProductionScheduleForNextThreeMonthDTO productionScheduleDTO) throws ApplicationException;

	List<ProductionScheduleForNextThreeMonthResponseDTO> getAllProductionScheduleForNextThreeMonthByOrgIdAndBranch(
			Long orgId, Long branch) throws ApplicationException;

	ProductionScheduleForNextThreeMonthResponseDTO getProductionScheduleForNextThreeMonthById(Long id) throws ApplicationException;

	//DeliveryChallanCumGatePass
	Map<String, Object> createUpdateDeliveryChallanCumGatePass(
			DeliveryChallanCumGatePassDTO deliveryChallanCumGatePassDTO) throws ApplicationException;

	List<Map<String, Object>> getDeliveryChallanCumGatePassDetails(String jobOrderNo, Long branch, Long orgId,
			Long customer);

	DeliveryChallanCumGatePassResponseDTO getDeliveryChallanCumGatePassById(Long id) throws ApplicationException;

	List<DeliveryChallanCumGatePassResponseDTO> getDeliveryChallanCumGatePassByOrgIdAndBranch(Long orgId, Long branch) throws ApplicationException;

	String getDeliveryChallanCumGatePassDocId(Long orgId, String financialYear);

	Map<String, Object> createUpdateAdvForStores(AdvForStoresDTO advForStoresDTO) throws ApplicationException;

	List<Map<String, Object>> getLatestBomDropdown(Long itemId, Long orgId, Long branch) throws ApplicationException;

//	List<Map<String, Object>> getLatestBomDetailsByProductCode(String productCode, Long orgId, String branch) throws ApplicationException;

	List<Map<String, Object>> getFGAndSFGItems(Long orgId, Long branch) throws ApplicationException;

	List<Map<String, Object>> getBomDetailsByDocId(String docId, Long orgId, Long branch) throws ApplicationException;

	AdvForStoresResponseDTO getAdvForStoresById(Long id) throws ApplicationException;

	List<AdvForStoresResponseDTO> getAdvForStoresByOrgIdAndBranch(Long orgId, Long branch) throws ApplicationException;

	String getAdvForStoresDocId(Long orgId, String financialYear);

	Map<String, Object> createUpdateJobOrderShortClose(JobOrderShortCloseDTO jobOrderShortCloseDTO) throws ApplicationException;

	List<Map<String, Object>> getTotalSuppliedQtyforJobOrderClose(Long orgId, Long branch, String jobOrderNo,
			Long item);

	JobOrderShortCloseResponseDTO getJobOrderShortCloseById(Long id) throws ApplicationException;

	List<JobOrderShortCloseResponseDTO> getJobOrderShortCloseByOrgIdAndBranch(Long orgId, Long branch) throws ApplicationException;

	String getJobOrderShortCloseDocId(Long orgId, String financialYear);

	Map<String, Object> createUpdateDeliveryChallanCapitalItems(DeliveryChallanCapitalItemsDTO dto)
			throws ApplicationException;

	DeliveryChallanCapitalItemsResponseDTO getDeliveryChallanCapitalItemsById(Long id) throws ApplicationException;

	String getDeliveryChallanCapitalItemsDocId(Long orgId, String financialYear);

	List<DeliveryChallanCapitalItemsResponseDTO> getDeliveryChallanCapitalItemsByOrgIdAndBranch(Long orgId,
			Long branch) throws ApplicationException;

	Map<String, Object> createUpdateSubContractingGRN(SubContractingGRNDTO dto) throws ApplicationException;

	List<Map<String, Object>> getGateInwardEntryDropdown(Long orgId, Long branch, Long customer);

	List<Map<String, Object>> getSubcontractSupplyScheduleforSubContractingGRN(Long orgId, Long branch, Long customer);

	List<Map<String, Object>> getItemDetailsForSubContractingGRN(String scheduleNo, Long orgId, Long branch,
			Long customer);

	List<Map<String, Object>> getBomItemDetailsforSubContractingGRN(Long orgId, Long branch, Long itemId);

	SubContractingGRNResponseDTO getSubContractingGRNById(Long id) throws ApplicationException;

	List<SubContractingGRNResponseDTO> getSubContractingGRNByOrgIdAndBranch(Long orgId, Long branch) throws ApplicationException;

	String getSubContractingGRNDocId(Long orgId, String financialYear);

	Map<String, Object> createUpdateMaterialPlanning(MaterialPlanningDTO materialPlanningDTO) throws ApplicationException;

	MaterialPlanningResponseDTO getMaterialPlanningById(Long id) throws ApplicationException;

	List<MaterialPlanningResponseDTO> getMaterialPlanningByOrgIdAndBranch(Long orgId, Long branch) throws ApplicationException;

	String getMaterialPlanningDocId(Long orgId, String financialYear);

	Map<String, Object> createUpdateBomCorrectionRequestNote(BomCorrectionRequestNoteDTO dto) throws ApplicationException;

	BomCorrectionRequestNoteResponseDTO getBomCorrectionRequestNoteById(Long id) throws ApplicationException;

	List<BomCorrectionRequestNoteResponseDTO> getBomCorrectionRequestNoteByOrgIdAndBranch(Long orgId, Long branch) throws ApplicationException;

	String getBomCorrectionRequestNoteDocId(Long orgId, String financialYear);

	List<Map<String, Object>> getFGItemsforBOMCorrectionRequestNote(Long orgId, Long branch) throws ApplicationException;

	List<Map<String, Object>> getAllItemsNotFGforBOMCorrectionRequestNote(Long orgId, Long branch) throws ApplicationException;

	List<Map<String, Object>> getEmployeesByDepartmentforBOMCorrectionRequestNote(Long orgId, Long branch,
			String department);

	Map<String, Object> createUpdateInspectionRequisitionNote(
			InspectionRequisitionNoteDTO inspectionRequisitionNoteDTO) throws ApplicationException;

	InspectionRequisitionNoteResponseDTO getInspectionRequisitionNoteById(Long id) throws ApplicationException;

	List<InspectionRequisitionNoteResponseDTO> getInspectionRequisitionNoteByOrgIdAndBranch(Long orgId, Long branch) throws ApplicationException;

}
