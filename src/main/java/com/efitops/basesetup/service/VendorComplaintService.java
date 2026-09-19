package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.CategoryMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.CauseMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.DailyInspectionCumRejectionDataResponseDTO;
import com.efitops.basesetup.ResponseDTO.FlashNCReportResponseDTO;
import com.efitops.basesetup.ResponseDTO.InstrumentCalibrationResponseDTO;
import com.efitops.basesetup.ResponseDTO.SetUpApprovalResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierChangeRequestResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierResponseEntryResponseDTO;
import com.efitops.basesetup.ResponseDTO.VendorComplaintEntryResponseDTO;
import com.efitops.basesetup.dto.CategoryMasterDTO;
import com.efitops.basesetup.dto.CauseMasterDTO;
import com.efitops.basesetup.dto.DailyInspectionCumRejectionDataDTO;
import com.efitops.basesetup.dto.FlashNCReportDTO;
import com.efitops.basesetup.dto.InstrumentCalibrationDTO;
import com.efitops.basesetup.dto.SetUpApprovalDTO;
import com.efitops.basesetup.dto.SupplierChangeRequestDTO;
import com.efitops.basesetup.dto.SupplierResponseEntryDTO;
import com.efitops.basesetup.dto.VendorComplaintEntryDTO;
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


}
