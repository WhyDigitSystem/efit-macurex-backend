package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import com.efitops.basesetup.ResponseDTO.DailyInspectionCumRejectionDataResponseDTO;
import com.efitops.basesetup.ResponseDTO.InstrumentCalibrationResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierResponseEntryResponseDTO;
import com.efitops.basesetup.ResponseDTO.VendorComplaintEntryResponseDTO;
import com.efitops.basesetup.dto.DailyInspectionCumRejectionDataDTO;
import com.efitops.basesetup.dto.InstrumentCalibrationDTO;
import com.efitops.basesetup.dto.SetUpApprovalDTO;
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

}
