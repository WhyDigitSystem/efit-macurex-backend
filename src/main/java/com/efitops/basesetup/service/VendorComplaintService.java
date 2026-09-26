package com.efitops.basesetup.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.ActivitiesCarriedOutResponseDTO;
import com.efitops.basesetup.ResponseDTO.AuthorizationForBreakdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.CategoryMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.CauseMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.DailyInspectionCumRejectionDataResponseDTO;
import com.efitops.basesetup.ResponseDTO.FlashNCReportResponseDTO;
import com.efitops.basesetup.ResponseDTO.InstrumentCalibrationResponseDTO;
import com.efitops.basesetup.ResponseDTO.MachineToolBreakdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.MachineToolsScrapNoteResponseDTO;
import com.efitops.basesetup.ResponseDTO.PMCheckListMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.QualityScrapNoteResponseDTO;
import com.efitops.basesetup.ResponseDTO.ScrapMaterialReturnRejectionResponseDTO;
import com.efitops.basesetup.ResponseDTO.SetUpApprovalResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierChangeRequestResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierResponseEntryResponseDTO;
import com.efitops.basesetup.ResponseDTO.VendorComplaintEntryResponseDTO;
import com.efitops.basesetup.dto.ActivitiesCarriedOutDTO;
import com.efitops.basesetup.dto.AuthorizationForBreakdownDTO;
import com.efitops.basesetup.dto.CategoryMasterDTO;
import com.efitops.basesetup.dto.CauseMasterDTO;
import com.efitops.basesetup.dto.DailyInspectionCumRejectionDataDTO;
import com.efitops.basesetup.dto.FlashNCReportDTO;
import com.efitops.basesetup.dto.InstrumentCalibrationDTO;
import com.efitops.basesetup.dto.MachineToolBreakdownDTO;
import com.efitops.basesetup.dto.MachineToolRectificationDTO;
import com.efitops.basesetup.dto.MachineToolsScrapNoteDTO;
import com.efitops.basesetup.dto.PMCheckListMasterDTO;
import com.efitops.basesetup.dto.QualityScrapNoteDTO;
import com.efitops.basesetup.dto.ScrapMaterialReturnRejectionDTO;
import com.efitops.basesetup.dto.SetUpApprovalDTO;
import com.efitops.basesetup.dto.SupplierChangeRequestDTO;
import com.efitops.basesetup.dto.SupplierResponseEntryDTO;
import com.efitops.basesetup.dto.VendorComplaintEntryDTO;
import com.efitops.basesetup.entity.MachineToolRectificationResponseDTO;
import com.efitops.basesetup.exception.ApplicationException;

public interface VendorComplaintService {

	Map<String, Object> updateCreateVendorComplaintEntry(VendorComplaintEntryDTO vendorComplaintEntryDTO)
			throws ApplicationException;

	VendorComplaintEntryResponseDTO getVendorComplaintEntryById(Long id) throws ApplicationException;

	List<VendorComplaintEntryResponseDTO> getVendorComplaintEntryByOrgId(Long orgId) throws ApplicationException;

	String getVendorComplaintEntryDocId(Long orgId, String financialYear);

	Map<String, Object> getFgItemDropdownForVendorComplaintEntry(Long branch, Long orgId) throws ApplicationException;

	Map<String, Object> getItemDropdownForVendorComplaintEntry(Long supplier, Long branch, Long orgId)
			throws ApplicationException;

	Map<String, Object> updateCreateSupplierResponseEntry(SupplierResponseEntryDTO supplierResponseEntryDTO)
			throws ApplicationException;

	SupplierResponseEntryResponseDTO getSupplierResponseEntryById(Long id) throws ApplicationException;

	List<SupplierResponseEntryResponseDTO> getSupplierResponseEntryByOrgId(Long orgId) throws ApplicationException;

	List<Map<String, Object>> getComplaintNoDropdownForSupplierResponseEntry(Long orgId);

	String getSupplierResponseEntryDocId(Long orgId, String financialYear);

	List<Map<String, Object>> getItemDropDownForSupplierResponseEntry(Long supplierId, Long orgId, Long branch)
			throws ApplicationException;

	Map<String, Object> updateCreateInstrumentCalibration(InstrumentCalibrationDTO instrumentCalibrationDTO)
			throws ApplicationException;


	List<InstrumentCalibrationResponseDTO> getInstrumentCalibrationByOrgId(Long orgId, Long branch)
			throws ApplicationException;

	InstrumentCalibrationResponseDTO getInstrumentCalibrationById(Long id) throws ApplicationException;

	List<Map<String, Object>> getMachineNoForInstrumentCalibration(Long machineCategory, Long branch, Long orgId)
			throws ApplicationException;

	Map<String, Object> updateCreateDailyInspectionCumRejectionData(
			DailyInspectionCumRejectionDataDTO dailyInspectionCumRejectionDataDTO) throws ApplicationException;

	List<DailyInspectionCumRejectionDataResponseDTO> getDailyInspectionCumRejectionDataByOrgId(Long orgId, Long branch)
			throws ApplicationException;

	DailyInspectionCumRejectionDataResponseDTO getDailyInspectionCumRejectionDataById(Long id)
			throws ApplicationException;

	List<Map<String, Object>> getFromLocationDropdownForDailyInspectionCumRejection(Long orgId, Long branch)
			throws ApplicationException;

	List<Map<String, Object>> getReworkLocationDropdownForDailyInspectionCumRejection(Long branch, Long orgId);

	List<Map<String, Object>> getRejectionLocationDropdownForDailyInspectionCumRejection(Long branch, Long orgId);

	List<Map<String, Object>> getScrapLocationDropdownForDailyInspectionCumRejection(Long branch, Long orgId);

	String getDailyInspectionCumRejectionDataDocId(Long orgId, String financialYear) throws ApplicationException;

//	setupapproval
	Map<String, Object> updateCreateSetUpApproval(SetUpApprovalDTO setUpApprovalDTO) throws ApplicationException;

	SetUpApprovalResponseDTO getSetUpApprovalById(Long id) throws ApplicationException;

	List<SetUpApprovalResponseDTO> getSetUpApprovalByOrgId(Long orgId, Long branch) throws ApplicationException;

	List<Map<String, Object>> getFgSfgItemDropdownForSetUpApproval(Long orgId, Long branch) throws ApplicationException;

	List<Map<String, Object>> getProcessSheetNoForSetUpApproval(Long item, Long orgId, Long branch)
			throws ApplicationException;

	List<Map<String, Object>> getControlPlanDetailsForSetUpApproval(Long item, String processSheetNo, Long orgId,
			Long branch) throws ApplicationException;

	String getSetUpApprovalDocId(Long orgId, String financialYear) throws ApplicationException;

//	FlashNC Report

	List<FlashNCReportResponseDTO> getFlashNCReportByOrgId(Long orgId, Long branch) throws ApplicationException;

	FlashNCReportResponseDTO getFlashNCReportById(Long id) throws ApplicationException;

	String getFlashNCReportDocId(Long orgId, String financialYear) throws ApplicationException;

	List<Map<String, Object>> getQualityEmployeesForFlashNCReport(Long orgId, Long branch) throws ApplicationException;

	
	ResponseEntity<byte[]> viewFlashNCReportFile(HttpServletRequest request) throws IOException;

	Map<String, Object> updateCreateFlashNCReport(FlashNCReportDTO flashNCReportDTO, MultipartFile[] files,
			MultipartFile[] images) throws ApplicationException;

	List<Map<String, Object>> getFromDeptDropdownForFlashNCReport(Long listOfValuesId) throws ApplicationException;

	List<Map<String, Object>> getToDepartmentDropdownForFlashNCReport(Long listOfValuesId, Long fromDept)
			throws ApplicationException;

	List<Map<String, Object>> getMRINGRNDropdownForFlashNCReport(Long orgId, Long branch) throws ApplicationException;

//	SupplierChangeRequest
	Map<String, Object> updateCreateSupplierChangeRequest(SupplierChangeRequestDTO supplierChangeRequestDTO)
			throws ApplicationException;

	String getSupplierChangeRequestDocId(Long orgId, String financialYear, String screenCode)
			throws ApplicationException;

	SupplierChangeRequestResponseDTO getSupplierChangeRequestById(Long id) throws ApplicationException;


	List<SupplierChangeRequestResponseDTO> getSupplierChangeRequestByOrgId(Long orgId, Long branch)
			throws ApplicationException;

	List<Map<String, Object>> getVendorCodeDropdownForSupplierChangeRequest(Long orgId, Long branch)
			throws ApplicationException;

	List<Map<String, Object>> getPurchaseEmployeesDropdownForSupplierChangeRequest(Long orgId, Long branch)
			throws ApplicationException;
	
	List<Map<String, Object>> getTDCEmployeesDropdownForSupplierChangeRequest(Long orgId, Long branch)
			throws ApplicationException;

	List<Map<String, Object>> getProductionEmployeesDropdownSupplierChangeRequest(Long orgId, Long branch)
			throws ApplicationException;

//	category master
	Map<String, Object> updateCreateCategoryMaster(CategoryMasterDTO categoryMasterDTO) throws ApplicationException;

	CategoryMasterResponseDTO getCategoryMasterById(Long id) throws ApplicationException;

	List<CategoryMasterResponseDTO> getCategoryMasterByOrgId(Long orgId) throws ApplicationException;

//	cause master
	Map<String, Object> updateCreateCauseMaster(CauseMasterDTO causeMasterDTO) throws ApplicationException;

	CauseMasterResponseDTO getCauseMasterById(Long id) throws ApplicationException;

	List<CauseMasterResponseDTO> getCauseMasterByOrgId(Long orgId) throws ApplicationException;

//pmcheck list master
	Map<String, Object> updateCreatePMCheckListMaster(PMCheckListMasterDTO dto) throws ApplicationException;

	PMCheckListMasterResponseDTO getPMCheckListMasterById(Long id) throws ApplicationException;

	List<PMCheckListMasterResponseDTO> getPMCheckListMasterByOrgId(Long orgId, Long branch) throws ApplicationException;


	List<Map<String, Object>> getActivityForPMCheckListMaster(Long department, Long orgId) throws ApplicationException;

	List<Map<String, Object>> getToolMachineCategoryForPMCheckListMaster(Long orgId, String pmCheckListFor)
			throws ApplicationException;

//MachineTool breakdown
	Map<String, Object> updateCreateMachineToolBreakdown(MachineToolBreakdownDTO machineToolBreakdownDTO,
			MultipartFile[] files, MultipartFile[] images) throws ApplicationException;

	ResponseEntity<byte[]> viewMachineToolBreakdownFile(HttpServletRequest request) throws IOException;

	String getMachineToolBreakdownDocId(Long orgId, String financialYear) throws ApplicationException;

	MachineToolBreakdownResponseDTO getMachineToolBreakdownById(Long id) throws ApplicationException;

	List<MachineToolBreakdownResponseDTO> getMachineToolBreakdownByOrgId(Long orgId, Long branch)
			throws ApplicationException;

	
	List<Map<String, Object>> getMachineToolForBreakdown(Long toolCategoryId, Long orgId, Long branch)
			throws ApplicationException;

	//MACHINE TOOL RECTIFICATION
	
	Map<String, Object> updateCreateMachineToolRectification(MachineToolRectificationDTO dto)
			throws ApplicationException;

	String getMachineToolRectificationDocId(Long orgId, String financialYear) throws ApplicationException;

	List<MachineToolRectificationResponseDTO> getMachineToolRectificationByOrgId(Long orgId, Long branch)
			throws ApplicationException;

	MachineToolRectificationResponseDTO getMachineToolRectificationById(Long id) throws ApplicationException;

	List<Map<String, Object>> getBreakdownDetailsForRectification(Long orgId, Long branch) throws ApplicationException;

	List<Map<String, Object>> getPrepareByForMachineToolRectification(Long orgId, Long branch, Long department)
			throws ApplicationException;

//Authorization For Breakdown
	Map<String, Object> updateCreateAuthorizationForBreakdown(AuthorizationForBreakdownDTO dto)
			throws ApplicationException;

	String getAuthorizationForBreakdownDocId(Long orgId, String financialYear) throws ApplicationException;

	AuthorizationForBreakdownResponseDTO getAuthorizationForBreakdownById(Long id) throws ApplicationException;

	List<AuthorizationForBreakdownResponseDTO> getAuthorizationForBreakdownByOrgId(Long orgId, Long branch)
			throws ApplicationException;

	List<Map<String, Object>> getMachineToolRectificationDetailsForAuthorizationBreakdown(Long branch, Long orgId)
			throws ApplicationException;

	String getPMCheckListMasterDocId(Long orgId, String financialYear) throws ApplicationException;

// machinetoolsscarpnote

	ResponseEntity<byte[]> viewMachineToolsScrapNoteFile(HttpServletRequest request) throws IOException;

	Map<String, Object> updateCreateMachineToolsScrapNote(MachineToolsScrapNoteDTO machineToolsScrapNoteDTO,
			MultipartFile[] files) throws ApplicationException;

	String getMachineToolsScrapNoteDocId(Long orgId, String financialYear) throws ApplicationException;

	Map<String, Object> getMachineToolsScrapNoteById(Long id) throws ApplicationException;


	List<MachineToolsScrapNoteResponseDTO> getMachineToolsScrapNoteByOrgId(Long orgId, Long branch)
			throws ApplicationException;

	Map<String, Object> updateCreateActivitiesCarriedOut(ActivitiesCarriedOutDTO dto) throws ApplicationException;

//	List<ActivitiesCarriedOutResponseDTO> getActivitiesCarriedOutByOrgId(Long orgId) throws ApplicationException;

	Map<String, Object> getActivitiesCarriedOutById(Long id) throws ApplicationException;

	List<ActivitiesCarriedOutResponseDTO> getActivitiesCarriedOutByOrgId(Long orgId, Long branch)
			throws ApplicationException;

	//quality scrap note
	
	Map<String, Object> updateCreateQualityScrapNote(QualityScrapNoteDTO dto) throws ApplicationException;

	Map<String, Object> getQualityScrapNoteById(Long id) throws ApplicationException;

	List<QualityScrapNoteResponseDTO> getQualityScrapNoteByOrgId(Long orgId, Long branch) throws ApplicationException;

	Map<String, Object> getQualityScrapNoteDocId(Long orgId, String financialYear) throws ApplicationException;

	Map<String, Object> getActivitiesCarriedOutDocId(Long orgId, String financialYear) throws ApplicationException;

//	scrapt material return rejection 
	Map<String, Object> updateCreateScrapMaterialReturnRejection(
			ScrapMaterialReturnRejectionDTO scrapMaterialReturnRejectionDTO) throws ApplicationException;

	List<ScrapMaterialReturnRejectionResponseDTO> getScrapMaterialReturnRejectionByOrgId(Long orgId,Long branch)
			throws ApplicationException;

	Map<String, Object> getScrapMaterialReturnRejectionById(Long id) throws ApplicationException;

	Map<String, Object> getScrapMaterialReturnRejectionDocId(Long orgId, String financialYear)
			throws ApplicationException;


	Map<String, Object> getOrderAcceptanceForGstApprovalReport(String fromDate, String toDate, Long orgId, Long branch)
			throws ApplicationException;

	

	Map<String, Object> getSalesContractForApproval(Long branch, Long orgId, String fromDate, String toDate)
			throws ApplicationException;

	



}
