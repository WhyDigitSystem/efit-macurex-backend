package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import com.efitops.basesetup.ResponseDTO.SupplierResponseEntryResponseDTO;
import com.efitops.basesetup.ResponseDTO.VendorComplaintEntryResponseDTO;
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

}
