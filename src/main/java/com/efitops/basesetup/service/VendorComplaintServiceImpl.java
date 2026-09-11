package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.efitops.basesetup.ResponseDTO.CustomerResponse1DTO;
import com.efitops.basesetup.ResponseDTO.DailyInspectionCumRejectionDataResponseDTO;
import com.efitops.basesetup.ResponseDTO.DailyInspectionCumRejectionDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.DepartmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.EmployeeDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.EmployeeMasterResponseDetailsDTO;
import com.efitops.basesetup.ResponseDTO.InstrumentCalibrationDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.InstrumentCalibrationResponseDTO;
import com.efitops.basesetup.ResponseDTO.ItemResponse1DTO;
import com.efitops.basesetup.ResponseDTO.ListOfValuesDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.LocationMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.MachineMasterResponse1DTO;
import com.efitops.basesetup.ResponseDTO.SetUpApprovalDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.SetUpApprovalParametersDetailsResponeDTO;
import com.efitops.basesetup.ResponseDTO.SetUpApprovalResponseDTO;
import com.efitops.basesetup.ResponseDTO.ShiftResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierResponseEntryDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierResponseEntryResponseDTO;
import com.efitops.basesetup.ResponseDTO.VendorComplaintDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.VendorComplaintEntryResponseDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.DailyInspectionCumRejectionDataDTO;
import com.efitops.basesetup.dto.DailyInspectionCumRejectionDataDetailsDTO;
import com.efitops.basesetup.dto.InstrumentCalibrationDTO;
import com.efitops.basesetup.dto.InstrumentCalibrationDetailsDTO;
import com.efitops.basesetup.dto.SetUpApprovalDTO;
import com.efitops.basesetup.dto.SetUpApprovalDetailsDTO;
import com.efitops.basesetup.dto.SetUpApprovalParametersDetailsDTO;
import com.efitops.basesetup.dto.SupplierResponseEntryDTO;
import com.efitops.basesetup.dto.SupplierResponseEntryDetailsDTO;
import com.efitops.basesetup.dto.VendorComplaintDetailsDTO;
import com.efitops.basesetup.dto.VendorComplaintEntryDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.DailyInspectionCumRejectionDataVO;
import com.efitops.basesetup.entity.DailyInspectionCumRejectionDetailsVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.InstrumentCalibrationDetailsVO;
import com.efitops.basesetup.entity.InstrumentCalibrationVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.ListOfValuesDetailsVO;
import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.entity.MachineMasterVO;
import com.efitops.basesetup.entity.SetUpApprovalDetailsVO;
import com.efitops.basesetup.entity.SetUpApprovalParametersDetailsVO;
import com.efitops.basesetup.entity.SetUpApprovalVO;
import com.efitops.basesetup.entity.ShiftVO;
import com.efitops.basesetup.entity.SupplierResponseEntryDetailsVO;
import com.efitops.basesetup.entity.SupplierResponseEntryVO;
import com.efitops.basesetup.entity.VendorComplaintDetailsVO;
import com.efitops.basesetup.entity.VendorComplaintEntryVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.DailyInspectionCumRejectionDataRepo;
import com.efitops.basesetup.repository.DailyInspectionCumRejectionDetailsRepo;
import com.efitops.basesetup.repository.DepartmentRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.EmployeeMasterRepo;
import com.efitops.basesetup.repository.InstrumentCalibrationDetailsRepo;
import com.efitops.basesetup.repository.InstrumentCalibrationRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.ListOfValuesDetailsRepo;
import com.efitops.basesetup.repository.LocationRepo;
import com.efitops.basesetup.repository.MachineMasterRepo;
import com.efitops.basesetup.repository.SetUpApprovalDetailsRepo;
import com.efitops.basesetup.repository.SetUpApprovalParametersDetailsRepo;
import com.efitops.basesetup.repository.SetUpApprovalRepo;
import com.efitops.basesetup.repository.ShiftRepo;
import com.efitops.basesetup.repository.SupplierResponseEntryDetailsRepo;
import com.efitops.basesetup.repository.SupplierResponseEntryRepo;
import com.efitops.basesetup.repository.VendorComplaintDetailsRepo;
import com.efitops.basesetup.repository.VendorComplaintEntryRepo;

@Service

public class VendorComplaintServiceImpl implements VendorComplaintService {

	@Autowired
	VendorComplaintEntryRepo vendorComplaintEntryRepo;

	@Autowired
	VendorComplaintDetailsRepo vendorComplaintDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	ItemMasterRepo itemRepo;

	@Autowired
	SupplierResponseEntryRepo supplierResponseEntryRepo;

	@Autowired
	SupplierResponseEntryDetailsRepo supplierResponseEntryDetailsRepo;

	@Autowired
	InstrumentCalibrationRepo instrumentCalibrationRepo;

	@Autowired
	ListOfValuesDetailsRepo listOfValuesDetailsRepo;

	@Autowired
	InstrumentCalibrationDetailsRepo instrumentCalibrationDetailsRepo;

	@Autowired
	EmployeeMasterRepo employeeMasterRepo;

	@Autowired
	BranchRepo branchRepo;

	@Autowired
	DepartmentRepo departmentRepo;

	@Autowired
	MachineMasterRepo machineMasterRepo;

	@Autowired
	LocationRepo locationRepo;

	@Autowired
	DailyInspectionCumRejectionDataRepo dailyInspectionCumRejectionDataRepo;

	@Autowired
	DailyInspectionCumRejectionDetailsRepo dailyInspectionCumRejectionDetailsRepo;

	@Autowired
	private SetUpApprovalRepo setUpApprovalRepo;

	@Autowired
	private SetUpApprovalDetailsRepo setUpApprovalDetailsRepo;

	@Autowired
	private SetUpApprovalParametersDetailsRepo setUpApprovalParametersDetailsRepo;

	@Autowired
	private ShiftRepo shiftRepo;

	@Override
	@Transactional
	public Map<String, Object> updateCreateVendorComplaintEntry(VendorComplaintEntryDTO vendorComplaintEntryDTO)
			throws ApplicationException {

		String screenCode = "VCE";
		VendorComplaintEntryVO vendorComplaintEntryVO = new VendorComplaintEntryVO();
		String message;

		if (ObjectUtils.isNotEmpty(vendorComplaintEntryDTO.getId())) {

			vendorComplaintEntryVO = vendorComplaintEntryRepo.findById(vendorComplaintEntryDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Vendor Complaint Entry Details"));

			vendorComplaintEntryVO.setUpdatedBy(vendorComplaintEntryDTO.getCreatedBy());

			message = "Vendor Complaint Entry Updated Successfully";

		} else {

			String docId = vendorComplaintEntryRepo.getVendorComplaintEntryDocId(vendorComplaintEntryDTO.getOrgId(),
					vendorComplaintEntryDTO.getFinancialYear(), screenCode);

			if (StringUtils.isBlank(docId)) {
				throw new ApplicationException("Vendor Complaint Entry DocId Not Found");
			}

			vendorComplaintEntryVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdScreenCode(vendorComplaintEntryDTO.getOrgId(), screenCode);

			if (documentTypeMappingDetailsVO == null) {
				throw new ApplicationException("Document Type Mapping Details Not Found");
			}

			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			vendorComplaintEntryVO.setCreatedBy(vendorComplaintEntryDTO.getCreatedBy());

			vendorComplaintEntryVO.setUpdatedBy(vendorComplaintEntryDTO.getCreatedBy());

			message = "Vendor Complaint Entry Created Successfully";
		}

		createUpdateVendorComplaintEntryVO(vendorComplaintEntryDTO, vendorComplaintEntryVO);

		VendorComplaintEntryVO savedVO = vendorComplaintEntryRepo.save(vendorComplaintEntryVO);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);
		response.put("vendorComplaintEntryVO", vendorComplaintEntryResponse(savedVO));

		return response;
	}

	private void createUpdateVendorComplaintEntryVO(VendorComplaintEntryDTO dto,
			VendorComplaintEntryVO vendorComplaintEntryVO) throws ApplicationException {

		vendorComplaintEntryVO.setDocDate(dto.getDocDate());
		vendorComplaintEntryVO.setRemarks(dto.getRemarks());
		vendorComplaintEntryVO.setActive(dto.isActive());
		vendorComplaintEntryVO.setOrgId(dto.getOrgId());
		vendorComplaintEntryVO.setCancelRemarks(dto.getCancelRemarks());
		vendorComplaintEntryVO.setFinancialYear(dto.getFinancialYear());

		if (dto.getFgItem() != null && dto.getFgItem() != 0) {

			ItemMasterVO fgItem = itemRepo.findById(dto.getFgItem())
					.orElseThrow(() -> new ApplicationException("FG Item Not Found"));

			vendorComplaintEntryVO.setFgItem(fgItem);
		}

		if (dto.getSupplier() != null && dto.getSupplier() != 0) {

			CustomerVO supplier = customerRepo.findById(dto.getSupplier())
					.orElseThrow(() -> new ApplicationException("Supplier Not Found"));

			vendorComplaintEntryVO.setSupplier(supplier);
		}

		if (dto.getId() != null) {

			List<VendorComplaintDetailsVO> oldDetails = vendorComplaintDetailsRepo
					.findByVendorComplaintEntryVO(vendorComplaintEntryVO);

			if (!oldDetails.isEmpty()) {
				vendorComplaintDetailsRepo.deleteAll(oldDetails);
			}
		}

		List<VendorComplaintDetailsVO> detailsList = new ArrayList<>();

		if (dto.getVendorComplaintDetailsDTO() != null && !dto.getVendorComplaintDetailsDTO().isEmpty()) {

			for (VendorComplaintDetailsDTO detailDTO : dto.getVendorComplaintDetailsDTO()) {

				VendorComplaintDetailsVO detailVO = new VendorComplaintDetailsVO();

				if (detailDTO.getItem() != null && detailDTO.getItem() != 0) {

					ItemMasterVO itemVO = itemRepo.findById(detailDTO.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));

					detailVO.setItem(itemVO);
				}

				detailVO.setReason(detailDTO.getReason());

				detailVO.setVendorComplaintEntryVO(vendorComplaintEntryVO);

				detailsList.add(detailVO);
			}
		}

		vendorComplaintEntryVO.setVendorComplaintDetailsVO(detailsList);
	}

	private VendorComplaintEntryResponseDTO vendorComplaintEntryResponse(
			VendorComplaintEntryVO vendorComplaintEntryVO) {

		VendorComplaintEntryResponseDTO responseDTO = new VendorComplaintEntryResponseDTO();

		responseDTO.setId(vendorComplaintEntryVO.getId());
		responseDTO.setDocId(vendorComplaintEntryVO.getDocId());
		responseDTO.setDocDate(vendorComplaintEntryVO.getDocDate());
		responseDTO.setActive(vendorComplaintEntryVO.isActive());
		responseDTO.setOrgId(vendorComplaintEntryVO.getOrgId());
		responseDTO.setCreatedBy(vendorComplaintEntryVO.getCreatedBy());
		responseDTO.setCancelRemarks(vendorComplaintEntryVO.getCancelRemarks());
		responseDTO.setRemarks(vendorComplaintEntryVO.getRemarks());
		responseDTO.setFinancialYear(vendorComplaintEntryVO.getFinancialYear());

		if (vendorComplaintEntryVO.getFgItem() != null) {

			ItemResponse1DTO itemDTO = new ItemResponse1DTO();

			itemDTO.setId(vendorComplaintEntryVO.getFgItem().getId());

			itemDTO.setItemCode(vendorComplaintEntryVO.getFgItem().getItemCode());

			itemDTO.setItemDescription(vendorComplaintEntryVO.getFgItem().getItemDescription());

			responseDTO.setFgItem(itemDTO);
		}

		if (vendorComplaintEntryVO.getSupplier() != null) {

			responseDTO.setSupplier(vendorComplaintEntryVO.getSupplier().getId());
		}

		List<VendorComplaintDetailsResponseDTO> detailsResponseList = new ArrayList<>();

		if (vendorComplaintEntryVO.getVendorComplaintDetailsVO() != null
				&& !vendorComplaintEntryVO.getVendorComplaintDetailsVO().isEmpty()) {

			for (VendorComplaintDetailsVO detailVO : vendorComplaintEntryVO.getVendorComplaintDetailsVO()) {

				VendorComplaintDetailsResponseDTO detailResponseDTO = new VendorComplaintDetailsResponseDTO();

				detailResponseDTO.setReason(detailVO.getReason());

				if (detailVO.getItem() != null) {

					ItemResponse1DTO itemDTO = new ItemResponse1DTO();

					itemDTO.setId(detailVO.getItem().getId());

					itemDTO.setItemCode(detailVO.getItem().getItemCode());

					itemDTO.setItemDescription(detailVO.getItem().getItemDescription());

					detailResponseDTO.setItem(itemDTO);
				}

				detailsResponseList.add(detailResponseDTO);
			}
		}

		responseDTO.setVendorComplaintDetailsResponseDTO(detailsResponseList);

		return responseDTO;
	}

	@Override
	public VendorComplaintEntryResponseDTO getVendorComplaintEntryById(Long id) throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {
			throw new ApplicationException("Invalid Id");
		}

		VendorComplaintEntryVO vendorComplaintEntryVO = vendorComplaintEntryRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Vendor Complaint Entry Not Found"));

		return vendorComplaintEntryResponse(vendorComplaintEntryVO);
	}

	@Override
	public List<VendorComplaintEntryResponseDTO> getVendorComplaintEntryByOrgId(Long orgId)
			throws ApplicationException {

		List<VendorComplaintEntryVO> vendorComplaintEntryList = vendorComplaintEntryRepo
				.getVendorComplaintEntryByOrgId(orgId);

		if (vendorComplaintEntryList.isEmpty()) {
			throw new ApplicationException("No Vendor Complaint Entry Details Found");
		}

		List<VendorComplaintEntryResponseDTO> responseList = new ArrayList<>();

		for (VendorComplaintEntryVO vendorComplaintEntryVO : vendorComplaintEntryList) {

			responseList.add(vendorComplaintEntryResponse(vendorComplaintEntryVO));
		}

		return responseList;
	}

	@Override
	public String getVendorComplaintEntryDocId(Long orgId, String financialYear) {

		String screenCode = "VCE";

		String result = vendorComplaintEntryRepo.getVendorComplaintEntryDocId(orgId, financialYear, screenCode);

		return result;
	}

	@Override
	public Map<String, Object> getFgItemDropdownForVendorComplaintEntry(Long branch, Long orgId)
			throws ApplicationException {

		List<Object[]> result = vendorComplaintEntryRepo.getFgItemDropdownForVendorComplaintEntry(branch, orgId);

		if (result.isEmpty()) {
			throw new ApplicationException("No FG Item Details Found");
		}

		List<Map<String, Object>> itemList = new ArrayList<>();

		for (Object[] obj : result) {

			Map<String, Object> itemMap = new HashMap<>();

			// =========================
			// Item Details
			// =========================

			itemMap.put("id", obj[0] != null ? ((Number) obj[0]).longValue() : null);

			itemMap.put("name", obj[1] != null ? obj[1].toString() : null);

			itemList.add(itemMap);
		}

		Map<String, Object> response = new HashMap<>();

		response.put("itemList", itemList);

		return response;
	}

//	itemdropdown for the vendor complaint entry
	@Override
	public Map<String, Object> getItemDropdownForVendorComplaintEntry(Long supplier, Long branch, Long orgId)
			throws ApplicationException {

		List<Object[]> result = vendorComplaintEntryRepo.getItemDropdownForVendorComplaintEntry(supplier, branch,
				orgId);

		if (result.isEmpty()) {
			throw new ApplicationException("No Item Details Found");
		}

		List<Map<String, Object>> itemList = new ArrayList<>();

		for (Object[] obj : result) {

			Map<String, Object> itemMap = new HashMap<>();

			// =========================
			// Item Details
			// =========================

			itemMap.put("id", obj[0] != null ? ((Number) obj[0]).longValue() : null);

			itemMap.put("itemCode", obj[1] != null ? obj[1].toString() : null);

			itemMap.put("itemDescription", obj[2] != null ? obj[2].toString() : null);

			itemMap.put("qty", obj[3] != null ? ((Number) obj[3]).doubleValue() : null);

			itemList.add(itemMap);
		}

		Map<String, Object> response = new HashMap<>();

		response.put("itemList", itemList);

		return response;
	}

//	Supplier Compliant entry

	@Override
	@Transactional
	public Map<String, Object> updateCreateSupplierResponseEntry(SupplierResponseEntryDTO supplierResponseEntryDTO)
			throws ApplicationException {

		SupplierResponseEntryVO supplierResponseEntryVO = new SupplierResponseEntryVO();

		String screenCode = "SRE";
		String message;

		// ========================================================
		// CREATE / UPDATE
		// ========================================================

		if (ObjectUtils.isNotEmpty(supplierResponseEntryDTO.getId())) {

			supplierResponseEntryVO = supplierResponseEntryRepo.findById(supplierResponseEntryDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Supplier Response Entry Details"));

			supplierResponseEntryVO.setUpdatedBy(supplierResponseEntryDTO.getCreatedBy());

			message = "Supplier Response Entry Updated Successfully";

		} else {

			String docId = supplierResponseEntryRepo.getSupplierResponseEntryDocId(supplierResponseEntryDTO.getOrgId(),
					supplierResponseEntryDTO.getFinancialYear(), screenCode);

			if (StringUtils.isBlank(docId)) {
				throw new ApplicationException("Supplier Response Entry DocId Not Found");
			}

			supplierResponseEntryVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(supplierResponseEntryDTO.getOrgId(),
							supplierResponseEntryDTO.getFinancialYear(), screenCode);

			if (documentTypeMappingDetailsVO == null) {
				throw new ApplicationException("Document Type Mapping Details Not Found");
			}

			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			supplierResponseEntryVO.setCreatedBy(supplierResponseEntryDTO.getCreatedBy());

			supplierResponseEntryVO.setUpdatedBy(supplierResponseEntryDTO.getCreatedBy());

			message = "Supplier Response Entry Created Successfully";
		}

		// ========================================================
		// DTO -> VO
		// ========================================================

		createUpdateSupplierResponseEntryVO(supplierResponseEntryDTO, supplierResponseEntryVO);

		// ========================================================
		// SAVE BASIC
		// ========================================================

		SupplierResponseEntryVO savedVO = supplierResponseEntryRepo.save(supplierResponseEntryVO);

		// ========================================================
		// RESPONSE
		// ========================================================

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("supplierResponseEntryVO", supplierResponseEntryResponse(savedVO));

		return response;
	}

	private void createUpdateSupplierResponseEntryVO(SupplierResponseEntryDTO dto,
			SupplierResponseEntryVO supplierResponseEntryVO) throws ApplicationException {

		supplierResponseEntryVO.setComplaintNo(dto.getComplaintNo());

		supplierResponseEntryVO.setComplaintDate(dto.getComplaintDate());

		supplierResponseEntryVO.setProductNo(dto.getProductNo());

		supplierResponseEntryVO.setProductName(dto.getProductName());

		supplierResponseEntryVO.setSupplierNo(dto.getSupplierNo());

		supplierResponseEntryVO.setSupplierName(dto.getSupplierName());

		supplierResponseEntryVO.setActive(dto.isActive());

		supplierResponseEntryVO.setOrgId(dto.getOrgId());

		supplierResponseEntryVO.setFinancialYear(dto.getFinancialYear());

		supplierResponseEntryVO.setCancelRemarks(dto.getCancelRemarks());

		supplierResponseEntryVO.setRemarks(dto.getRemarks());

		// ========================================================
		// DELETE OLD DETAILS
		// ========================================================

		if (dto.getId() != null) {

			List<SupplierResponseEntryDetailsVO> oldDetails = supplierResponseEntryDetailsRepo
					.findBySupplierResponseEntryVO(supplierResponseEntryVO);

			if (!oldDetails.isEmpty()) {
				supplierResponseEntryDetailsRepo.deleteAll(oldDetails);
			}
		}

		// ========================================================
		// CREATE DETAILS
		// ========================================================

		List<SupplierResponseEntryDetailsVO> detailsList = new ArrayList<>();

		if (dto.getSupplierResponseEntryDetailsDTO() != null && !dto.getSupplierResponseEntryDetailsDTO().isEmpty()) {

			for (SupplierResponseEntryDetailsDTO detailDTO : dto.getSupplierResponseEntryDetailsDTO()) {

				SupplierResponseEntryDetailsVO detailVO = new SupplierResponseEntryDetailsVO();

				if (detailDTO.getItem() != null && detailDTO.getItem() != 0) {

					ItemMasterVO itemVO = itemRepo.findById(detailDTO.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));

					detailVO.setItem(itemVO);
				}

				detailVO.setQty(detailDTO.getQty());

				detailVO.setResponseQty(detailDTO.getResponseQty());

				detailVO.setReason(detailDTO.getReason());

				detailVO.setSupplierResponseEntryVO(supplierResponseEntryVO);

				detailsList.add(detailVO);
			}
		}

		supplierResponseEntryVO.setSupplierResponseEntryDetailsVO(detailsList);
	}

	private SupplierResponseEntryResponseDTO supplierResponseEntryResponse(
			SupplierResponseEntryVO supplierResponseEntryVO) {

		SupplierResponseEntryResponseDTO responseDTO = new SupplierResponseEntryResponseDTO();

		responseDTO.setId(supplierResponseEntryVO.getId());

		responseDTO.setComplaintNo(supplierResponseEntryVO.getComplaintNo());

		responseDTO.setComplaintDate(supplierResponseEntryVO.getComplaintDate());

		responseDTO.setProductNo(supplierResponseEntryVO.getProductNo());

		responseDTO.setProductName(supplierResponseEntryVO.getProductName());

		responseDTO.setSupplierNo(supplierResponseEntryVO.getSupplierNo());

		responseDTO.setSupplierName(supplierResponseEntryVO.getSupplierName());

		responseDTO.setActive(supplierResponseEntryVO.isActive() ? "Active" : "In-Active");

		responseDTO.setOrgId(supplierResponseEntryVO.getOrgId());

		responseDTO.setCreatedBy(supplierResponseEntryVO.getCreatedBy());

		responseDTO.setFinancialYear(supplierResponseEntryVO.getFinancialYear());

		responseDTO.setCancelRemarks(supplierResponseEntryVO.getCancelRemarks());

		responseDTO.setRemarks(supplierResponseEntryVO.getRemarks());

		// ========================================================
		// DETAILS RESPONSE
		// ========================================================

		List<SupplierResponseEntryDetailsResponseDTO> detailsResponseList = new ArrayList<>();

		if (supplierResponseEntryVO.getSupplierResponseEntryDetailsVO() != null
				&& !supplierResponseEntryVO.getSupplierResponseEntryDetailsVO().isEmpty()) {

			for (SupplierResponseEntryDetailsVO detailVO : supplierResponseEntryVO
					.getSupplierResponseEntryDetailsVO()) {

				SupplierResponseEntryDetailsResponseDTO detailResponseDTO = new SupplierResponseEntryDetailsResponseDTO();

				if (detailVO.getItem() != null) {

					detailResponseDTO.setItem(detailVO.getItem().getId());
				}

				detailResponseDTO.setQty(detailVO.getQty());

				detailResponseDTO.setResponseQty(detailVO.getResponseQty());

				detailResponseDTO.setReason(detailVO.getReason());

				detailsResponseList.add(detailResponseDTO);
			}
		}

		responseDTO.setSupplierResponseEntryDetailsResponseDTO(detailsResponseList);

		return responseDTO;
	}

	@Override
	public SupplierResponseEntryResponseDTO getSupplierResponseEntryById(Long id) throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {
			throw new ApplicationException("Invalid Id");
		}

		SupplierResponseEntryVO supplierResponseEntryVO = supplierResponseEntryRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Supplier Response Entry Not Found"));

		return supplierResponseEntryResponse(supplierResponseEntryVO);
	}

	@Override
	public List<SupplierResponseEntryResponseDTO> getSupplierResponseEntryByOrgId(Long orgId)
			throws ApplicationException {

		List<SupplierResponseEntryVO> supplierResponseEntryList = supplierResponseEntryRepo
				.getSupplierResponseEntryByOrgId(orgId);

		if (supplierResponseEntryList.isEmpty()) {
			throw new ApplicationException("No Supplier Response Entry Details Found");
		}

		List<SupplierResponseEntryResponseDTO> responseList = new ArrayList<>();

		for (SupplierResponseEntryVO supplierResponseEntryVO : supplierResponseEntryList) {

			responseList.add(supplierResponseEntryResponse(supplierResponseEntryVO));
		}

		return responseList;
	}

	@Override
	public List<Map<String, Object>> getComplaintNoDropdownForSupplierResponseEntry(Long orgId) {

		List<Object[]> result = supplierResponseEntryRepo.getComplaintNoDropdownForSupplierResponseEntry(orgId);

		return getComplaintNoDropdownForSupplierResponseEntry(result);
	}

	private List<Map<String, Object>> getComplaintNoDropdownForSupplierResponseEntry(List<Object[]> result) {

		List<Map<String, Object>> details = new ArrayList<>();

		for (Object[] obj : result) {

			Map<String, Object> complaint = new HashMap<>();

			complaint.put("docId", obj[0] != null ? obj[0].toString() : "");

			complaint.put("docDate", obj[1] != null ? obj[1].toString() : "");

			complaint.put("productNo", obj[2] != null ? obj[2].toString() : "");

			complaint.put("productName", obj[3] != null ? obj[3].toString() : "");

			complaint.put("supplierId", obj[4] != null ? ((Number) obj[4]).longValue() : null);

			complaint.put("supplierNo", obj[5] != null ? obj[5].toString() : "");

			complaint.put("supplierName", obj[6] != null ? obj[6].toString() : "");

			details.add(complaint);
		}

		return details;
	}

	@Override
	public String getSupplierResponseEntryDocId(Long orgId, String financialYear) {

		String screenCode = "SRE";

		String result = supplierResponseEntryRepo.getSupplierResponseEntryDocId(orgId, financialYear, screenCode);

		return result;
	}

	@Override
	public List<Map<String, Object>> getItemDropDownForSupplierResponseEntry(Long supplierId, Long orgId, Long branch)
			throws ApplicationException {

		List<Object[]> result = supplierResponseEntryRepo.getItemDropdownForSupplierResponseEntry(supplierId, orgId,
				branch);

		if (result == null || result.isEmpty()) {
			throw new ApplicationException("No Items Found For Selected Supplier");
		}

		List<Map<String, Object>> response = new ArrayList<>();

		for (Object[] obj : result) {

			Map<String, Object> item = new HashMap<>();

			item.put("id", obj[0]);
			item.put("itemCode", obj[1]);
			item.put("itemDescription", obj[2]);

			response.add(item);
		}

		return response;
	}

	@Override
	@Transactional
	public Map<String, Object> updateCreateInstrumentCalibration(InstrumentCalibrationDTO instrumentCalibrationDTO)
			throws ApplicationException {

		InstrumentCalibrationVO instrumentCalibrationVO = new InstrumentCalibrationVO();

		String screenCode = "IC";
		String message;

		// ========================================================
		// CREATE / UPDATE
		// ========================================================

		if (ObjectUtils.isNotEmpty(instrumentCalibrationDTO.getId())) {

			instrumentCalibrationVO = instrumentCalibrationRepo.findById(instrumentCalibrationDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Instrument Calibration Details"));

			instrumentCalibrationVO.setUpdatedBy(instrumentCalibrationDTO.getCreatedBy());

			message = "Instrument Calibration Updated Successfully";

		} else {

			// ========================================================
			// DOC ID GENERATION
			// ========================================================

			String docId = instrumentCalibrationRepo.getInstrumentCalibrationDocId(instrumentCalibrationDTO.getOrgId(),
					instrumentCalibrationDTO.getFinancialYear(), screenCode);

			if (StringUtils.isBlank(docId)) {
				throw new ApplicationException("Instrument Calibration DocId Not Found");
			}

			instrumentCalibrationVO.setDocId(docId);

			// ========================================================
			// UPDATE LAST NUMBER
			// ========================================================

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(instrumentCalibrationDTO.getOrgId(),
							instrumentCalibrationDTO.getFinancialYear(), screenCode);

			if (documentTypeMappingDetailsVO == null) {
				throw new ApplicationException("Document Type Mapping Details Not Found");
			}

			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			// ========================================================
			// CREATED / UPDATED BY
			// ========================================================

			instrumentCalibrationVO.setCreatedBy(instrumentCalibrationDTO.getCreatedBy());

			instrumentCalibrationVO.setUpdatedBy(instrumentCalibrationDTO.getCreatedBy());

			message = "Instrument Calibration Created Successfully";
		}

		// ========================================================
		// DTO -> VO
		// ========================================================

		createUpdateInstrumentCalibrationVO(instrumentCalibrationDTO, instrumentCalibrationVO);

		// ========================================================
		// SAVE BASIC
		// ========================================================

		InstrumentCalibrationVO savedVO = instrumentCalibrationRepo.save(instrumentCalibrationVO);

		// ========================================================
		// RESPONSE
		// ========================================================

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("instrumentCalibrationVO", instrumentCalibrationResponse(savedVO));

		return response;
	}

	private void createUpdateInstrumentCalibrationVO(InstrumentCalibrationDTO dto,
			InstrumentCalibrationVO instrumentCalibrationVO) throws ApplicationException {

		// ========================================================
		// BASIC DETAILS
		// ========================================================

		if (dto.getBranch() != null) {

			BranchVO branchVO = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			instrumentCalibrationVO.setBranch(branchVO);
		}

		if (dto.getDepartment() != null) {

			DepartmentVO departmentVO = departmentRepo.findById(dto.getDepartment())
					.orElseThrow(() -> new ApplicationException("Department Not Found"));

			instrumentCalibrationVO.setDepartment(departmentVO);
		}
		if (dto.getCheckedBy() != null) {

			EmployeeMasterVO checkedByVO = employeeMasterRepo.findById(dto.getCheckedBy())
					.orElseThrow(() -> new ApplicationException("Checked By Employee Not Found"));

			instrumentCalibrationVO.setCheckedBy(checkedByVO);
		}

		instrumentCalibrationVO.setSelectMachineInstNo(dto.getSelectMachineInstNo());

		if (dto.getMachineInstNo() != null) {

			MachineMasterVO machineMasterVO = machineMasterRepo.findById(dto.getMachineInstNo())
					.orElseThrow(() -> new ApplicationException("Machine Master Not Found"));

			instrumentCalibrationVO.setMachineInstNo(machineMasterVO);
		}

		if (dto.getLocation() != null) {

			LocationVO locationVO = locationRepo.findById(dto.getLocation())
					.orElseThrow(() -> new ApplicationException("Location Master Not Found"));

			instrumentCalibrationVO.setLocation(locationVO);
		}

		if (dto.getCalibrationAgency() != null) {

			ListOfValuesDetailsVO listOfValuesDetailsVO = listOfValuesDetailsRepo.findById(dto.getCalibrationAgency())
					.orElseThrow(() -> new ApplicationException("calibration agency  Not Found"));

			instrumentCalibrationVO.setCalibrationAgency(listOfValuesDetailsVO);
		}

		instrumentCalibrationVO.setCertificateNo(dto.getCertificateNo());
		if (dto.getApprovedBy() != null) {

			EmployeeMasterVO approvedByVO = employeeMasterRepo.findById(dto.getApprovedBy())
					.orElseThrow(() -> new ApplicationException("Approved By Employee Not Found"));

			instrumentCalibrationVO.setApprovedBy(approvedByVO);
		}

		instrumentCalibrationVO.setOrgId(dto.getOrgId());

		instrumentCalibrationVO.setFinancialYear(dto.getFinancialYear());

		instrumentCalibrationVO.setActive(dto.isActive());

		instrumentCalibrationVO.setCancelRemarks(dto.getCancelRemarks());

		// ========================================================
		// DELETE OLD DETAILS
		// ========================================================

		if (dto.getId() != null) {

			List<InstrumentCalibrationDetailsVO> oldDetails = instrumentCalibrationDetailsRepo
					.findByInstrumentCalibrationVO(instrumentCalibrationVO);

			if (!oldDetails.isEmpty()) {
				instrumentCalibrationDetailsRepo.deleteAll(oldDetails);
			}
		}

		// ========================================================
		// CREATE DETAILS
		// ========================================================

		List<InstrumentCalibrationDetailsVO> detailsList = new ArrayList<>();

		if (dto.getInstrumentCalibrationDetailsDTO() != null && !dto.getInstrumentCalibrationDetailsDTO().isEmpty()) {

			for (InstrumentCalibrationDetailsDTO detailDTO : dto.getInstrumentCalibrationDetailsDTO()) {

				InstrumentCalibrationDetailsVO detailVO = new InstrumentCalibrationDetailsVO();

				detailVO.setDateOfCalibration(detailDTO.getDateOfCalibration());

				// ====================================================
				// FREQUENCY
				// ====================================================

				if (detailDTO.getFrequency() != null && detailDTO.getFrequency() != 0) {

					ListOfValuesDetailsVO frequencyVO = listOfValuesDetailsRepo.findById(detailDTO.getFrequency())
							.orElseThrow(() -> new ApplicationException("Frequency Not Found"));

					detailVO.setFrequency(frequencyVO);
				}

				detailVO.setNextScheduleDate(detailDTO.getNextScheduleDate());

				detailVO.setInstrumentCalibrationVO(instrumentCalibrationVO);

				detailsList.add(detailVO);
			}
		}

		instrumentCalibrationVO.setInstrumentCalibrationDetailsVO(detailsList);
	}

	private InstrumentCalibrationResponseDTO instrumentCalibrationResponse(
			InstrumentCalibrationVO instrumentCalibrationVO) {

		InstrumentCalibrationResponseDTO responseDTO = new InstrumentCalibrationResponseDTO();

		// ========================================================
		// BASIC RESPONSE
		// ========================================================

		responseDTO.setId(instrumentCalibrationVO.getId());

		if (instrumentCalibrationVO.getBranch() != null) {

			BranchResponseDTO branch = new BranchResponseDTO();

			branch.setId(instrumentCalibrationVO.getBranch().getId());

			branch.setBranchName(instrumentCalibrationVO.getBranch().getBranchName());

			responseDTO.setBranch(branch);
		}
		if (instrumentCalibrationVO.getDepartment() != null) {

			DepartmentResponseDTO department = new DepartmentResponseDTO();

			department.setId(instrumentCalibrationVO.getDepartment().getId());

			department.setDepartmentName(instrumentCalibrationVO.getDepartment().getDepartmentName());

			responseDTO.setDepartment(department);
		}

		// ========================================================
		// CHECKED BY
		// ========================================================

		if (instrumentCalibrationVO.getCheckedBy() != null) {

			EmployeeDropdownResponseDTO checkedBy = new EmployeeDropdownResponseDTO();

			checkedBy.setEmployeeId(instrumentCalibrationVO.getCheckedBy().getId());

			checkedBy.setEmployeeName(instrumentCalibrationVO.getCheckedBy().getEmployeeName());

			responseDTO.setCheckedBy(checkedBy);
		}

		responseDTO.setSelectMachineInstNo(instrumentCalibrationVO.getSelectMachineInstNo());

		// ========================================================
		// MACHINE
		// ========================================================

		if (instrumentCalibrationVO.getMachineInstNo() != null) {

			MachineMasterResponse1DTO machine = new MachineMasterResponse1DTO();

			machine.setId(instrumentCalibrationVO.getMachineInstNo().getId());

			machine.setMachineInstrumentNo(instrumentCalibrationVO.getMachineInstNo().getMachineInstrumentNo());

			machine.setMachineInstrumentName(instrumentCalibrationVO.getMachineInstNo().getMachineInstrumentName());

			responseDTO.setMachineInstNo(machine);
		}

		// ========================================================
		// LOCATION
		// ========================================================

		if (instrumentCalibrationVO.getLocation() != null) {

			LocationMasterResponseDTO location = new LocationMasterResponseDTO();

			location.setId(instrumentCalibrationVO.getLocation().getId());

			location.setLocationName(instrumentCalibrationVO.getLocation().getLocationName());

			responseDTO.setLocation(location);
		}
		if (instrumentCalibrationVO.getCalibrationAgency() != null) {

			ListOfValuesDetailsResponseDTO listOfValuesDetailsResponseDTO = new ListOfValuesDetailsResponseDTO();

			listOfValuesDetailsResponseDTO.setId(instrumentCalibrationVO.getCalibrationAgency().getId());

			listOfValuesDetailsResponseDTO.setCode(instrumentCalibrationVO.getCalibrationAgency().getValueCode());

			listOfValuesDetailsResponseDTO
					.setDescription(instrumentCalibrationVO.getCalibrationAgency().getValueDescription());

			responseDTO.setCalibrationAgency(listOfValuesDetailsResponseDTO);
		}
		responseDTO.setCertificateNo(instrumentCalibrationVO.getCertificateNo());

		// ========================================================
		// APPROVED BY
		// ========================================================

		if (instrumentCalibrationVO.getApprovedBy() != null) {

			EmployeeDropdownResponseDTO approvedBy = new EmployeeDropdownResponseDTO();

			approvedBy.setEmployeeId(instrumentCalibrationVO.getApprovedBy().getId());

			approvedBy.setEmployeeName(instrumentCalibrationVO.getApprovedBy().getEmployeeName());

			responseDTO.setApprovedBy(approvedBy);
		}

		responseDTO.setOrgId(instrumentCalibrationVO.getOrgId());

		responseDTO.setFinancialYear(instrumentCalibrationVO.getFinancialYear());

		responseDTO.setActive(instrumentCalibrationVO.getActive());

		responseDTO.setCancelRemarks(instrumentCalibrationVO.getCancelRemarks());

		responseDTO.setCreatedBy(instrumentCalibrationVO.getCreatedBy());

		// ========================================================
		// DETAILS RESPONSE
		// ========================================================

		List<InstrumentCalibrationDetailsResponseDTO> detailsResponseList = new ArrayList<>();

		if (instrumentCalibrationVO.getInstrumentCalibrationDetailsVO() != null
				&& !instrumentCalibrationVO.getInstrumentCalibrationDetailsVO().isEmpty()) {

			for (InstrumentCalibrationDetailsVO detailVO : instrumentCalibrationVO
					.getInstrumentCalibrationDetailsVO()) {

				InstrumentCalibrationDetailsResponseDTO detailResponseDTO = new InstrumentCalibrationDetailsResponseDTO();

				detailResponseDTO.setDateOfCalibration(detailVO.getDateOfCalibration());

				// ====================================================
				// FREQUENCY RESPONSE
				// ====================================================

				if (detailVO.getFrequency() != null) {

					ListOfValuesDetailsResponseDTO frequency = new ListOfValuesDetailsResponseDTO();

					frequency.setId(detailVO.getFrequency().getId());

					frequency.setCode(detailVO.getFrequency().getValueCode());

					frequency.setDescription(detailVO.getFrequency().getValueDescription());

					detailResponseDTO.setFrequency(frequency);
				}

				detailResponseDTO.setNextScheduleDate(detailVO.getNextScheduleDate());

				detailsResponseList.add(detailResponseDTO);
			}
		}

		responseDTO.setInstrumentCalibrationDetailsResponseDTO(detailsResponseList);

		return responseDTO;
	}

	@Override
	public List<InstrumentCalibrationResponseDTO> getInstrumentCalibrationByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<InstrumentCalibrationVO> instrumentCalibrationVOList = instrumentCalibrationRepo
				.getInstrumentCalibrationByOrgId(orgId, branch);

		if (instrumentCalibrationVOList == null || instrumentCalibrationVOList.isEmpty()) {

			throw new ApplicationException("No Instrument Calibration Details Found");
		}

		List<InstrumentCalibrationResponseDTO> responseList = new ArrayList<>();

		for (InstrumentCalibrationVO instrumentCalibrationVO : instrumentCalibrationVOList) {

			responseList.add(instrumentCalibrationResponse(instrumentCalibrationVO));
		}

		return responseList;
	}

	@Override
	public InstrumentCalibrationResponseDTO getInstrumentCalibrationById(Long id) throws ApplicationException {

		InstrumentCalibrationVO instrumentCalibrationVO = instrumentCalibrationRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Instrument Calibration Details Not Found"));

		return instrumentCalibrationResponse(instrumentCalibrationVO);
	}
//	machine no ddropdown

	@Override
	public List<Map<String, Object>> getMachineNoForInstrumentCalibration(Long machineId, Long branch, Long orgId) {

		List<Object[]> result = instrumentCalibrationRepo.getMachineNoForInstrumentCalibration(machineId, branch,
				orgId);

		return getMachineNoForInstrumentCalibration(result);
	}

	private List<Map<String, Object>> getMachineNoForInstrumentCalibration(List<Object[]> result) {

		List<Map<String, Object>> details = new ArrayList<>();

		for (Object[] obj : result) {

			Map<String, Object> machine = new HashMap<>();

			machine.put("machineMasterId", obj[0] != null ? ((Number) obj[0]).longValue() : null);

			machine.put("machineInstrumentNo", obj[1] != null ? obj[1].toString() : "");

			machine.put("locationId", obj[2] != null ? ((Number) obj[2]).longValue() : null);

			machine.put("locationName", obj[3] != null ? obj[3].toString() : "");

			details.add(machine);
		}

		return details;
	}

//	Daily Inspection cum 
	@Override
	@Transactional
	public Map<String, Object> updateCreateDailyInspectionCumRejectionData(
			DailyInspectionCumRejectionDataDTO dailyInspectionCumRejectionDataDTO) throws ApplicationException {

		DailyInspectionCumRejectionDataVO dailyInspectionCumRejectionDataVO = new DailyInspectionCumRejectionDataVO();

		String screenCode = "DICRD";
		String message;

		/*
		 * UPDATE
		 */
		if (ObjectUtils.isNotEmpty(dailyInspectionCumRejectionDataDTO.getId())) {

			dailyInspectionCumRejectionDataVO = dailyInspectionCumRejectionDataRepo
					.findById(dailyInspectionCumRejectionDataDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Daily Inspection Cum Rejection Data Details"));

			dailyInspectionCumRejectionDataVO.setUpdatedBy(dailyInspectionCumRejectionDataDTO.getCreatedBy());

			message = "Daily Inspection Cum Rejection Data Updated Successfully";

		}

		/*
		 * CREATE
		 */
		else {

			String docId = dailyInspectionCumRejectionDataRepo.getDailyInspectionCumRejectionDataDocId(
					dailyInspectionCumRejectionDataDTO.getOrgId(),
					dailyInspectionCumRejectionDataDTO.getFinancialYear(), screenCode);

			if (StringUtils.isBlank(docId)) {
				throw new ApplicationException("Daily Inspection Cum Rejection Data DocId Not Found");
			}

			dailyInspectionCumRejectionDataVO.setDocId(docId);

			/*
			 * Document Type Mapping
			 */
			var documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo.findByOrgIdAndFinYearAndScreenCode(
					dailyInspectionCumRejectionDataDTO.getOrgId(),
					dailyInspectionCumRejectionDataDTO.getFinancialYear(), screenCode);

			if (documentTypeMappingDetailsVO == null) {
				throw new ApplicationException("Document Type Mapping Details Not Found");
			}

			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			dailyInspectionCumRejectionDataVO.setCreatedBy(dailyInspectionCumRejectionDataDTO.getCreatedBy());

			dailyInspectionCumRejectionDataVO.setUpdatedBy(dailyInspectionCumRejectionDataDTO.getCreatedBy());

			message = "Daily Inspection Cum Rejection Data Created Successfully";
		}

		/*
		 * Set Basic Details
		 */
		createUpdateDailyInspectionCumRejectionDataVO(dailyInspectionCumRejectionDataDTO,
				dailyInspectionCumRejectionDataVO);

		/*
		 * Save
		 */
		DailyInspectionCumRejectionDataVO savedVO = dailyInspectionCumRejectionDataRepo
				.save(dailyInspectionCumRejectionDataVO);

		/*
		 * Response
		 */
		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("dailyInspectionCumRejectionDataVO", dailyInspectionCumRejectionDataResponse(savedVO));

		return response;
	}

	/*
	 * CREATE / UPDATE MAPPING
	 */
	private void createUpdateDailyInspectionCumRejectionDataVO(DailyInspectionCumRejectionDataDTO dto,
			DailyInspectionCumRejectionDataVO vo) throws ApplicationException {

		/*
		 * Branch
		 */
		if (dto.getBranch() != null) {

			BranchVO branchVO = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			vo.setBranch(branchVO);
		}

		/*
		 * Belongs To
		 */
		if (dto.getBelongsTo() != null) {

			ListOfValuesDetailsVO belongsToVO = listOfValuesDetailsRepo.findById(dto.getBelongsTo())
					.orElseThrow(() -> new ApplicationException("Belongs To Details Not Found"));

			vo.setBelongsTo(belongsToVO);
		}

		/*
		 * Prepared By
		 */
		if (dto.getPreparedBy() != null) {

			EmployeeMasterVO preparedByVO = employeeMasterRepo.findById(dto.getPreparedBy())
					.orElseThrow(() -> new ApplicationException("Prepared By Employee Not Found"));

			vo.setPreparedBy(preparedByVO);
		}

		/*
		 * From Location
		 */
		if (dto.getFromLocation() != null) {

			LocationVO fromLocationVO = locationRepo.findById(dto.getFromLocation())
					.orElseThrow(() -> new ApplicationException("From Location Not Found"));

			vo.setFromLocation(fromLocationVO);
		}

		/*
		 * Rework Location
		 */
		if (dto.getReworkLocation() != null) {

			LocationVO reworkLocationVO = locationRepo.findById(dto.getReworkLocation())
					.orElseThrow(() -> new ApplicationException("Rework Location Not Found"));

			vo.setReworkLocation(reworkLocationVO);
		}

		/*
		 * Rejection Location
		 */
		if (dto.getRejectionLocation() != null) {

			LocationVO rejectionLocationVO = locationRepo.findById(dto.getRejectionLocation())
					.orElseThrow(() -> new ApplicationException("Rejection Location Not Found"));

			vo.setRejectionLocation(rejectionLocationVO);
		}

		/*
		 * Scrap Location
		 */
		if (dto.getScrapLocation() != null) {

			LocationVO scrapLocationVO = locationRepo.findById(dto.getScrapLocation())
					.orElseThrow(() -> new ApplicationException("Scrap Location Not Found"));

			vo.setScrapLocation(scrapLocationVO);
		}

		/*
		 * To Location
		 */
		if (dto.getToLocation() != null) {

			LocationVO toLocationVO = locationRepo.findById(dto.getToLocation())
					.orElseThrow(() -> new ApplicationException("To Location Not Found"));

			vo.setToLocation(toLocationVO);
		}

		/*
		 * Basic Fields
		 */
		vo.setOrgId(dto.getOrgId());
		vo.setFinancialYear(dto.getFinancialYear());
		vo.setActive(dto.isActive());
		vo.setCancelRemarks(dto.getCancelRemarks());

		/*
		 * Details
		 */
		if (dto.getDailyInspectionCumRejectionDataDetailsDTO() != null) {

			/*
			 * Delete old details during update
			 */
			if (vo.getId() != null && vo.getDailyInspectionCumRejectionDetailsVO() != null) {

				dailyInspectionCumRejectionDetailsRepo.deleteAll(vo.getDailyInspectionCumRejectionDetailsVO());

				vo.getDailyInspectionCumRejectionDetailsVO().clear();
			}

			List<DailyInspectionCumRejectionDetailsVO> detailsList = new ArrayList<>();

			for (DailyInspectionCumRejectionDataDetailsDTO detailsDTO : dto
					.getDailyInspectionCumRejectionDataDetailsDTO()) {

				DailyInspectionCumRejectionDetailsVO detailsVO = new DailyInspectionCumRejectionDetailsVO();

				/*
				 * FG Item
				 */
				if (detailsDTO.getFgItem() != null) {

					ItemMasterVO fgItemVO = itemRepo.findById(detailsDTO.getFgItem())
							.orElseThrow(() -> new ApplicationException("FG Item Not Found"));

					detailsVO.setFgItem(fgItemVO);
				}

				/*
				 * Quantities
				 */
				detailsVO.setStock(detailsDTO.getStock());
				detailsVO.setRate(detailsDTO.getRate());
				detailsVO.setInspectionQty(detailsDTO.getInspectionQty());
				detailsVO.setAcceptedQty(detailsDTO.getAcceptedQty());
				detailsVO.setReworkQty(detailsDTO.getReworkQty());
				detailsVO.setRejectionQty(detailsDTO.getRejectionQty());
				detailsVO.setScrapQty(detailsDTO.getScrapQty());

				/*
				 * Parent
				 */
				detailsVO.setDailyInspectionCumRejectionDataVO(vo);

				detailsList.add(detailsVO);
			}

			vo.setDailyInspectionCumRejectionDetailsVO(detailsList);
		}
	}

	/*
	 * RESPONSE DTO
	 */
	private DailyInspectionCumRejectionDataResponseDTO dailyInspectionCumRejectionDataResponse(
			DailyInspectionCumRejectionDataVO vo) {

		DailyInspectionCumRejectionDataResponseDTO responseDTO = new DailyInspectionCumRejectionDataResponseDTO();

		responseDTO.setId(vo.getId());

		/*
		 * Branch
		 */
		if (vo.getBranch() != null) {
			responseDTO.setBranch(vo.getBranch().getId());
		}

		/*
		 * Belongs To
		 */
		if (vo.getBelongsTo() != null) {
			responseDTO.setBelongsTo(vo.getBelongsTo().getId());
		}

		/*
		 * Prepared By
		 */
		if (vo.getPreparedBy() != null) {
			responseDTO.setPreparedBy(vo.getPreparedBy().getId());
		}

		/*
		 * Locations
		 */
		if (vo.getFromLocation() != null) {
			responseDTO.setFromLocation(vo.getFromLocation().getId());
		}

		if (vo.getReworkLocation() != null) {
			responseDTO.setReworkLocation(vo.getReworkLocation().getId());
		}

		if (vo.getRejectionLocation() != null) {
			responseDTO.setRejectionLocation(vo.getRejectionLocation().getId());
		}

		if (vo.getScrapLocation() != null) {
			responseDTO.setScrapLocation(vo.getScrapLocation().getId());
		}

		if (vo.getToLocation() != null) {
			responseDTO.setToLocation(vo.getToLocation().getId());
		}

		responseDTO.setActive(vo.getActive());

		responseDTO.setOrgId(vo.getOrgId());
		responseDTO.setFinancialYear(vo.getFinancialYear());
		responseDTO.setCreatedBy(vo.getCreatedBy());
		responseDTO.setCancelRemarks(vo.getCancelRemarks());

		/*
		 * Details Response
		 */
		List<DailyInspectionCumRejectionDetailsResponseDTO> detailsResponseList = new ArrayList<>();

		if (vo.getDailyInspectionCumRejectionDetailsVO() != null) {

			for (DailyInspectionCumRejectionDetailsVO detailsVO : vo.getDailyInspectionCumRejectionDetailsVO()) {

				DailyInspectionCumRejectionDetailsResponseDTO detailsResponseDTO = new DailyInspectionCumRejectionDetailsResponseDTO();

				if (detailsVO.getFgItem() != null) {
					detailsResponseDTO.setFgItem(detailsVO.getFgItem().getId());
				}

				detailsResponseDTO.setStock(detailsVO.getStock());
				detailsResponseDTO.setRate(detailsVO.getRate());
				detailsResponseDTO.setInspectionQty(detailsVO.getInspectionQty());
				detailsResponseDTO.setAcceptedQty(detailsVO.getAcceptedQty());
				detailsResponseDTO.setReworkQty(detailsVO.getReworkQty());
				detailsResponseDTO.setRejectionQty(detailsVO.getRejectionQty());
				detailsResponseDTO.setScrapQty(detailsVO.getScrapQty());

				detailsResponseList.add(detailsResponseDTO);
			}
		}

		responseDTO.setDailyInspectionCumRejectionDetailsResponseDTO(detailsResponseList);

		return responseDTO;
	}

	@Override
	public List<DailyInspectionCumRejectionDataResponseDTO> getDailyInspectionCumRejectionDataByOrgId(Long orgId,
			Long branch) throws ApplicationException {

		List<DailyInspectionCumRejectionDataVO> dailyInspectionCumRejectionDataVOList = dailyInspectionCumRejectionDataRepo
				.getDailyInspectionCumRejectionDataByOrgId(orgId, branch);

		if (dailyInspectionCumRejectionDataVOList == null || dailyInspectionCumRejectionDataVOList.isEmpty()) {

			throw new ApplicationException("No Daily Inspection Cum Rejection Data Details Found");
		}

		List<DailyInspectionCumRejectionDataResponseDTO> responseList = new ArrayList<>();

		for (DailyInspectionCumRejectionDataVO dailyInspectionCumRejectionDataVO : dailyInspectionCumRejectionDataVOList) {

			responseList.add(dailyInspectionCumRejectionDataResponse(dailyInspectionCumRejectionDataVO));
		}

		return responseList;
	}

	@Override
	public DailyInspectionCumRejectionDataResponseDTO getDailyInspectionCumRejectionDataById(Long id)
			throws ApplicationException {

		DailyInspectionCumRejectionDataVO dailyInspectionCumRejectionDataVO = dailyInspectionCumRejectionDataRepo
				.findById(id)
				.orElseThrow(() -> new ApplicationException("Daily Inspection Cum Rejection Data Details Not Found"));

		return dailyInspectionCumRejectionDataResponse(dailyInspectionCumRejectionDataVO);
	}

	@Override
	public List<Map<String, Object>> getFromLocationDropdownForDailyInspectionCumRejection(Long orgId, Long branch)
			throws ApplicationException {

		List<Object[]> locationList = dailyInspectionCumRejectionDataRepo
				.getFromLocationDropdownForDailyInspectionCumRejection(orgId, branch);

		if (locationList == null || locationList.isEmpty()) {
			throw new ApplicationException("No From Location Details Found");
		}

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : locationList) {

			Map<String, Object> locationMap = new HashMap<>();

			locationMap.put("locationId", obj[0]);
			locationMap.put("locationName", obj[1]);

			responseList.add(locationMap);
		}

		return responseList;
	}

	@Override
	public List<Map<String, Object>> getReworkLocationDropdownForDailyInspectionCumRejection(Long branch, Long orgId) {

		List<Object[]> result = dailyInspectionCumRejectionDataRepo
				.getReworkLocationDropdownForDailyInspectionCumRejection(branch, orgId);

		return getReworkLocationDropdown(result);
	}

	private List<Map<String, Object>> getReworkLocationDropdown(List<Object[]> result) {

		List<Map<String, Object>> details = new ArrayList<>();

		for (Object[] obj : result) {

			Map<String, Object> location = new HashMap<>();

			location.put("id", obj[0] != null ? ((Number) obj[0]).longValue() : null);

			location.put("locationId", obj[1] != null ? obj[1].toString() : "");

			location.put("locationName", obj[2] != null ? obj[2].toString() : "");

			details.add(location);
		}

		return details;
	}

	@Override
	public List<Map<String, Object>> getRejectionLocationDropdownForDailyInspectionCumRejection(Long branch,
			Long orgId) {

		List<Object[]> result = dailyInspectionCumRejectionDataRepo
				.getRejectionLocationDropdownForDailyInspectionCumRejection(branch, orgId);

		return getReworkLocationDropdown(result);
	}

	private List<Map<String, Object>> getRejectionLocationDropdown(List<Object[]> result) {

		List<Map<String, Object>> details = new ArrayList<>();

		for (Object[] obj : result) {

			Map<String, Object> location = new HashMap<>();

			location.put("id", obj[0] != null ? ((Number) obj[0]).longValue() : null);

			location.put("locationId", obj[1] != null ? obj[1].toString() : "");

			location.put("locationName", obj[2] != null ? obj[2].toString() : "");

			details.add(location);
		}

		return details;
	}

	@Override
	public List<Map<String, Object>> getScrapLocationDropdownForDailyInspectionCumRejection(Long branch, Long orgId) {

		List<Object[]> result = dailyInspectionCumRejectionDataRepo
				.getScrapLocationDropdownForDailyInspectionCumRejection(branch, orgId);

		return getReworkLocationDropdown(result);
	}

	private List<Map<String, Object>> getScrapLocationDropdown(List<Object[]> result) {

		List<Map<String, Object>> details = new ArrayList<>();

		for (Object[] obj : result) {

			Map<String, Object> location = new HashMap<>();

			location.put("id", obj[0] != null ? ((Number) obj[0]).longValue() : null);

			location.put("locationId", obj[1] != null ? obj[1].toString() : "");

			location.put("locationName", obj[2] != null ? obj[2].toString() : "");

			details.add(location);
		}

		return details;
	}
//	getFgItemDropdownForVendorComplaintEntry --for fgitemfordailyinspectioncumrejectiondata

	@Override
	public String getDailyInspectionCumRejectionDataDocId(Long orgId, String financialYear)
			throws ApplicationException {

		String screenCode = "DICRD";

		String docId = dailyInspectionCumRejectionDataRepo.getDailyInspectionCumRejectionDataDocId(orgId, financialYear,
				screenCode);

		if (StringUtils.isBlank(docId)) {
			throw new ApplicationException("Daily Inspection Cum Rejection Data DocId Not Found");
		}

		return docId;
	}

//	setupapproval

	@Override
	@Transactional
	public Map<String, Object> updateCreateSetUpApproval(SetUpApprovalDTO setUpApprovalDTO)
			throws ApplicationException {

		SetUpApprovalVO setUpApprovalVO = new SetUpApprovalVO();

		String screenCode = "SUA";
		String message;

		/*
		 * UPDATE
		 */
		if (ObjectUtils.isNotEmpty(setUpApprovalDTO.getId())) {

			setUpApprovalVO = setUpApprovalRepo.findById(setUpApprovalDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Set Up Approval Details"));

			setUpApprovalVO.setUpdatedBy(setUpApprovalDTO.getCreatedBy());

			message = "Set Up Approval Updated Successfully";

		}

		/*
		 * CREATE
		 */
		else {

			String docId = setUpApprovalRepo.getSetUpApprovalDocId(setUpApprovalDTO.getOrgId(),
					setUpApprovalDTO.getFinancialYear(), screenCode);

			if (StringUtils.isBlank(docId)) {

				throw new ApplicationException("Set Up Approval DocId Not Found");
			}

			setUpApprovalVO.setDocId(docId);

			/*
			 * Document Type Mapping
			 */
			var documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo.findByOrgIdAndFinYearAndScreenCode(
					setUpApprovalDTO.getOrgId(), setUpApprovalDTO.getFinancialYear(), screenCode);

			if (documentTypeMappingDetailsVO == null) {

				throw new ApplicationException("Document Type Mapping Details Not Found");
			}

			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			setUpApprovalVO.setCreatedBy(setUpApprovalDTO.getCreatedBy());

			setUpApprovalVO.setUpdatedBy(setUpApprovalDTO.getCreatedBy());

			message = "Set Up Approval Created Successfully";
		}

		/*
		 * Basic Details Mapping
		 */
		createUpdateSetUpApprovalVO(setUpApprovalDTO, setUpApprovalVO);

		/*
		 * Save
		 */
		SetUpApprovalVO savedVO = setUpApprovalRepo.save(setUpApprovalVO);

		/*
		 * Response
		 */
		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("setUpApprovalVO", setUpApprovalResponse(savedVO));

		return response;
	}

	/*
	 * CREATE / UPDATE BASIC + DETAILS
	 */
	private void createUpdateSetUpApprovalVO(SetUpApprovalDTO dto, SetUpApprovalVO vo) throws ApplicationException {

		/*
		 * Branch
		 */
		if (dto.getBranch() != null) {

			BranchVO branchVO = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			vo.setBranch(branchVO);
		}

		/*
		 * Shift
		 */
		if (dto.getShift() != null) {

			ShiftVO shiftVO = shiftRepo.findById(dto.getShift())
					.orElseThrow(() -> new ApplicationException("Shift Not Found"));

			vo.setShift(shiftVO);
		}

		/*
		 * Item
		 */
		if (dto.getItem() != null) {

			ItemMasterVO itemVO = itemRepo.findById(dto.getItem())
					.orElseThrow(() -> new ApplicationException("Item Master Not Found"));

			vo.setItem(itemVO);
		}

		/*
		 * Customer
		 */
		if (dto.getCustomer() != null) {

			CustomerVO customerVO = customerRepo.findById(dto.getCustomer())
					.orElseThrow(() -> new ApplicationException("Customer Not Found"));

			vo.setCustomer(customerVO);
		}

		/*
		 * Checked By
		 */
		if (dto.getCheckedBy() != null) {

			EmployeeMasterVO checkedByVO = employeeMasterRepo.findById(dto.getCheckedBy())
					.orElseThrow(() -> new ApplicationException("Checked By Employee Not Found"));

			vo.setCheckedBy(checkedByVO);
		}

		/*
		 * Approved By
		 */
		if (dto.getApprovedBy() != null) {

			EmployeeMasterVO approvedByVO = employeeMasterRepo.findById(dto.getApprovedBy())
					.orElseThrow(() -> new ApplicationException("Approved By Employee Not Found"));

			vo.setApprovedBy(approvedByVO);
		}

		/*
		 * Basic Fields
		 */
		vo.setProcessSheetNo(dto.getProcessSheetNo());
		vo.setControlPlan(dto.getControlPlan());
		vo.setRecommendedForProduction(dto.getRecommendedForProduction());

		vo.setOrgId(dto.getOrgId());
		vo.setFinancialYear(dto.getFinancialYear());
		vo.setActive(dto.isActive());
		vo.setCancelRemarks(dto.getCancelRemarks());

		/*
		 * Set Up Approval Details
		 */
		if (dto.getSetUpApprovalDetailsDTO() != null) {

			/*
			 * Delete old details during update
			 */
			if (vo.getId() != null && vo.getSetUpApprovalDetailsVO() != null
					&& !vo.getSetUpApprovalDetailsVO().isEmpty()) {

				setUpApprovalDetailsRepo.deleteAll(vo.getSetUpApprovalDetailsVO());

				vo.getSetUpApprovalDetailsVO().clear();
			}

			List<SetUpApprovalDetailsVO> detailsList = new ArrayList<>();

			for (SetUpApprovalDetailsDTO detailsDTO : dto.getSetUpApprovalDetailsDTO()) {

				SetUpApprovalDetailsVO detailsVO = new SetUpApprovalDetailsVO();

				detailsVO.setOperationNo(detailsDTO.getOperationNo());

				detailsVO.setDescription(detailsDTO.getDescription());

				detailsVO.setSpecification(detailsDTO.getSpecification());

				detailsVO.setDetails1(detailsDTO.getDetails1());

				detailsVO.setDetails2(detailsDTO.getDetails2());

				detailsVO.setDetails3(detailsDTO.getDetails3());

				detailsVO.setDetails4(detailsDTO.getDetails4());

				detailsVO.setDetails5(detailsDTO.getDetails5());

				detailsVO.setDetails6(detailsDTO.getDetails6());

				detailsVO.setDetails7(detailsDTO.getDetails7());

				detailsVO.setDetails8(detailsDTO.getDetails8());

				detailsVO.setDetails9(detailsDTO.getDetails9());

				detailsVO.setDetails10(detailsDTO.getDetails10());

				detailsVO.setTime(detailsDTO.getTime());

				detailsVO.setRemarks(detailsDTO.getRemarks());

				/*
				 * Parent
				 */
				detailsVO.setSetUpApprovalVO(vo);

				detailsList.add(detailsVO);
			}

			vo.setSetUpApprovalDetailsVO(detailsList);
		}

		/*
		 * Set Up Approval Parameters Details
		 */
		if (dto.getSetUpApprovalParametersDetailsDTO() != null) {

			/*
			 * Delete old parameter details during update
			 */
			if (vo.getId() != null && vo.getSetUpApprovalParametersDetailsVO() != null
					&& !vo.getSetUpApprovalParametersDetailsVO().isEmpty()) {

				setUpApprovalParametersDetailsRepo.deleteAll(vo.getSetUpApprovalParametersDetailsVO());

				vo.getSetUpApprovalParametersDetailsVO().clear();
			}

			List<SetUpApprovalParametersDetailsVO> parametersDetailsList = new ArrayList<>();

			for (SetUpApprovalParametersDetailsDTO parametersDTO : dto.getSetUpApprovalParametersDetailsDTO()) {

				SetUpApprovalParametersDetailsVO parametersVO = new SetUpApprovalParametersDetailsVO();

				parametersVO.setParameters(parametersDTO.getParameters());

				parametersVO.setParameterType(parametersDTO.getParameterType());

				parametersVO.setTol(parametersDTO.getTol());

				/*
				 * Parent
				 */
				parametersVO.setSetUpApprovalVO(vo);

				parametersDetailsList.add(parametersVO);
			}

			vo.setSetUpApprovalParametersDetailsVO(parametersDetailsList);
		}
	}

	

//	RESPONSE DTO*/

	private SetUpApprovalResponseDTO setUpApprovalResponse(SetUpApprovalVO vo) {

		SetUpApprovalResponseDTO responseDTO = new SetUpApprovalResponseDTO();

		responseDTO.setId(vo.getId());

		/*
		 * Branch
		 */
		if (vo.getBranch() != null) {

			BranchResponseDTO branchResponseDTO = new BranchResponseDTO();

			branchResponseDTO.setId(vo.getBranch().getId());

			branchResponseDTO.setBranchCode(vo.getBranch().getBranchCode());

			branchResponseDTO.setBranchName(vo.getBranch().getBranchName());

			responseDTO.setBranch(branchResponseDTO);
		}

		/*
		 * Shift
		 */
		if (vo.getShift() != null) {

			ShiftResponseDTO shiftResponseDTO = new ShiftResponseDTO();

			shiftResponseDTO.setId(vo.getShift().getId());

			shiftResponseDTO.setShiftCode(vo.getShift().getShiftCode());

			shiftResponseDTO.setShiftName(vo.getShift().getShiftName());

			responseDTO.setShift(shiftResponseDTO);
		}

		/*
		 * Item
		 */
		if (vo.getItem() != null) {

			ItemResponse1DTO itemResponseDTO = new ItemResponse1DTO();

			itemResponseDTO.setId(vo.getItem().getId());

			itemResponseDTO.setItemCode(vo.getItem().getItemCode());

			itemResponseDTO.setItemDescription(vo.getItem().getItemDescription());

			

			responseDTO.setItem(itemResponseDTO);
		}

		/*
		 * Customer
		 */
		if (vo.getCustomer() != null) {

			CustomerResponse1DTO customerResponseDTO = new CustomerResponse1DTO();

			customerResponseDTO.setId(vo.getCustomer().getId());

		

			customerResponseDTO.setCustomerName(vo.getCustomer().getCustomerName());

			responseDTO.setCustomer(customerResponseDTO);
		}

		/*
		 * Checked By
		 */
		if (vo.getCheckedBy() != null) {

			EmployeeMasterResponseDetailsDTO checkedByResponseDTO = new EmployeeMasterResponseDetailsDTO();

			checkedByResponseDTO.setId(vo.getCheckedBy().getId());

			checkedByResponseDTO.setEmployeeCode(vo.getCheckedBy().getEmployeeId());

			checkedByResponseDTO.setEmployeeName(vo.getCheckedBy().getEmployeeName());

			responseDTO.setCheckedBy(checkedByResponseDTO);
		}

		/*
		 * Approved By
		 */
		if (vo.getApprovedBy() != null) {

			EmployeeMasterResponseDetailsDTO approvedByResponseDTO = new EmployeeMasterResponseDetailsDTO();

			approvedByResponseDTO.setId(vo.getApprovedBy().getId());

			approvedByResponseDTO.setEmployeeCode(vo.getApprovedBy().getEmployeeId());

			approvedByResponseDTO.setEmployeeName(vo.getApprovedBy().getEmployeeName());

			responseDTO.setApprovedBy(approvedByResponseDTO);
		}

		/*
		 * Basic Fields
		 */
		responseDTO.setProcessSheetNo(vo.getProcessSheetNo());

		responseDTO.setControlPlan(vo.getControlPlan());

		responseDTO.setRecommendedForProduction(vo.getRecommendedForProduction());

		responseDTO.setOrgId(vo.getOrgId());
		responseDTO.setFinancialYear(vo.getFinancialYear());

		responseDTO.setActive(vo.getActive() );

		responseDTO.setCancelRemarks(vo.getCancelRemarks());

		responseDTO.setCreatedBy(vo.getCreatedBy());

		/*
		 * Details Response
		 */
		List<SetUpApprovalDetailsResponseDTO> detailsResponseList = new ArrayList<>();

		if (vo.getSetUpApprovalDetailsVO() != null) {

			for (SetUpApprovalDetailsVO detailsVO : vo.getSetUpApprovalDetailsVO()) {

				SetUpApprovalDetailsResponseDTO detailsResponseDTO = new SetUpApprovalDetailsResponseDTO();

				detailsResponseDTO.setOperationNo(detailsVO.getOperationNo());

				detailsResponseDTO.setDescription(detailsVO.getDescription());

				detailsResponseDTO.setSpecification(detailsVO.getSpecification());

				detailsResponseDTO.setDetails1(detailsVO.getDetails1());

				detailsResponseDTO.setDetails2(detailsVO.getDetails2());

				detailsResponseDTO.setDetails3(detailsVO.getDetails3());

				detailsResponseDTO.setDetails4(detailsVO.getDetails4());

				detailsResponseDTO.setDetails5(detailsVO.getDetails5());

				detailsResponseDTO.setDetails6(detailsVO.getDetails6());

				detailsResponseDTO.setDetails7(detailsVO.getDetails7());

				detailsResponseDTO.setDetails8(detailsVO.getDetails8());

				detailsResponseDTO.setDetails9(detailsVO.getDetails9());

				detailsResponseDTO.setDetails10(detailsVO.getDetails10());

				detailsResponseDTO.setTime(detailsVO.getTime());

				detailsResponseDTO.setRemarks(detailsVO.getRemarks());

				detailsResponseList.add(detailsResponseDTO);
			}
		}

		responseDTO.setSetUpApprovalDetailsResponseDTO(detailsResponseList);

		/*
		 * Parameters Response
		 */
		List<SetUpApprovalParametersDetailsResponeDTO> parametersResponseList = new ArrayList<>();

		if (vo.getSetUpApprovalParametersDetailsVO() != null) {

			for (SetUpApprovalParametersDetailsVO parametersVO : vo.getSetUpApprovalParametersDetailsVO()) {

				SetUpApprovalParametersDetailsResponeDTO parametersResponseDTO = new SetUpApprovalParametersDetailsResponeDTO();

				parametersResponseDTO.setParameters(parametersVO.getParameters());

				parametersResponseDTO.setParameterType(parametersVO.getParameterType());

				parametersResponseDTO.setTol(parametersVO.getTol());

				parametersResponseList.add(parametersResponseDTO);
			}
		}

		responseDTO.setSetUpApprovalParametersDetailsResponeDTO(parametersResponseList);

		return responseDTO;
	}
}


