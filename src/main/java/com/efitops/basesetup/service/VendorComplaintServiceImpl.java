package com.efitops.basesetup.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.efitops.basesetup.ResponseDTO.ActivitiesCarriedOutComponentDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.ActivitiesCarriedOutDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.ActivitiesCarriedOutResponseDTO;
import com.efitops.basesetup.ResponseDTO.ActivityResponseDTO;
import com.efitops.basesetup.ResponseDTO.AuthorizationForBreakdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.CategoryMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.CauseMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.CustomerResponse1DTO;
import com.efitops.basesetup.ResponseDTO.DailyInspectionCumRejectionDataResponseDTO;
import com.efitops.basesetup.ResponseDTO.DailyInspectionCumRejectionDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.DepartmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.EmployeeDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.EmployeeMasterResponseDetailsDTO;
import com.efitops.basesetup.ResponseDTO.FlashNCReportAttachmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.FlashNCReportResponseDTO;
import com.efitops.basesetup.ResponseDTO.InstrumentCalibrationDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.InstrumentCalibrationResponseDTO;
import com.efitops.basesetup.ResponseDTO.ItemResponse1DTO;
import com.efitops.basesetup.ResponseDTO.ListOfValuesDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.LocationMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.MachineMasterResponse1DTO;
import com.efitops.basesetup.ResponseDTO.MachineToolBreakdownAttchmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.MachineToolBreakdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.MachineToolScrapNoteAttachmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.MachineToolsScrapNoteDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.MachineToolsScrapNoteResponseDTO;
import com.efitops.basesetup.ResponseDTO.PMCheckListDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.PMCheckListMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.PMCheckListResponseDTO;
import com.efitops.basesetup.ResponseDTO.QualityScrapNoteDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.QualityScrapNoteResponseDTO;
import com.efitops.basesetup.ResponseDTO.ScrapMaterialReturnRejectionDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.ScrapMaterialReturnRejectionResponseDTO;
import com.efitops.basesetup.ResponseDTO.SetUpApprovalDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.SetUpApprovalParametersDetailsResponeDTO;
import com.efitops.basesetup.ResponseDTO.SetUpApprovalResponseDTO;
import com.efitops.basesetup.ResponseDTO.ShiftResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierChangeRequestResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierResponseEntryDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierResponseEntryResponseDTO;
import com.efitops.basesetup.ResponseDTO.ToolCategoryDetailRepo;
import com.efitops.basesetup.ResponseDTO.ToolCategoryDetailResponseDTO;
import com.efitops.basesetup.ResponseDTO.ToolCategoryResponse1DTO;
import com.efitops.basesetup.ResponseDTO.UnitResponseDTO;
import com.efitops.basesetup.ResponseDTO.VendorComplaintDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.VendorComplaintEntryResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.dto.ActivitiesCarriedOutComponentDetailsDTO;
import com.efitops.basesetup.dto.ActivitiesCarriedOutDTO;
import com.efitops.basesetup.dto.ActivitiesCarriedOutDetailsDTO;
import com.efitops.basesetup.dto.AuthorizationForBreakdownDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.CategoryMasterDTO;
import com.efitops.basesetup.dto.CauseMasterDTO;
import com.efitops.basesetup.dto.DailyInspectionCumRejectionDataDTO;
import com.efitops.basesetup.dto.DailyInspectionCumRejectionDataDetailsDTO;
import com.efitops.basesetup.dto.EmployeeMasterDetailsReponseDTO;
import com.efitops.basesetup.dto.EmployeeResponseDTO;
import com.efitops.basesetup.dto.FlashNCReportDTO;
import com.efitops.basesetup.dto.InstrumentCalibrationDTO;
import com.efitops.basesetup.dto.InstrumentCalibrationDetailsDTO;
import com.efitops.basesetup.dto.MachineToolBreakdownDTO;
import com.efitops.basesetup.dto.MachineToolRectificationDTO;
import com.efitops.basesetup.dto.MachineToolsScrapNoteDTO;
import com.efitops.basesetup.dto.MachineToolsScrapNoteDetailsDTO;
import com.efitops.basesetup.dto.PMCheckListDetailsDTO;
import com.efitops.basesetup.dto.PMCheckListMasterDTO;
import com.efitops.basesetup.dto.QualityScrapNoteDTO;
import com.efitops.basesetup.dto.QualityScrapNoteDetailsDTO;
import com.efitops.basesetup.dto.ScrapMaterialReturnRejectionDTO;
import com.efitops.basesetup.dto.ScrapMaterialReturnRejectionDetailsDTO;
import com.efitops.basesetup.dto.SetUpApprovalDTO;
import com.efitops.basesetup.dto.SetUpApprovalDetailsDTO;
import com.efitops.basesetup.dto.SetUpApprovalParametersDetailsDTO;
import com.efitops.basesetup.dto.SupplierChangeRequestDTO;
import com.efitops.basesetup.dto.SupplierResponseEntryDTO;
import com.efitops.basesetup.dto.SupplierResponseEntryDetailsDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;
import com.efitops.basesetup.dto.VendorComplaintDetailsDTO;
import com.efitops.basesetup.dto.VendorComplaintEntryDTO;
import com.efitops.basesetup.entity.ActivitiesCarriedOutComponentDetailsVO;
import com.efitops.basesetup.entity.ActivitiesCarriedOutDetailsVO;
import com.efitops.basesetup.entity.ActivitiesCarriedOutVO;
import com.efitops.basesetup.entity.ActivityMasterVO;
import com.efitops.basesetup.entity.AuthorizationForBreakdownVO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CategoryMasterVO;
import com.efitops.basesetup.entity.CauseMasterVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.DailyInspectionCumRejectionDataVO;
import com.efitops.basesetup.entity.DailyInspectionCumRejectionDetailsVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.FlashNCReportAttachmentVO;
import com.efitops.basesetup.entity.FlashNCReportVO;
import com.efitops.basesetup.entity.InstrumentCalibrationDetailsVO;
import com.efitops.basesetup.entity.InstrumentCalibrationVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.ListOfValuesDetailsVO;
import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.entity.MachineMasterVO;
import com.efitops.basesetup.entity.MachineToolBreakdownAttachmentVO;
import com.efitops.basesetup.entity.MachineToolBreakdownVO;
import com.efitops.basesetup.entity.MachineToolRectificationResponseDTO;
import com.efitops.basesetup.entity.MachineToolRectificationVO;
import com.efitops.basesetup.entity.MachineToolScrapNoteAttachmentVO;
import com.efitops.basesetup.entity.MachineToolsScrapNoteDetailsVO;
import com.efitops.basesetup.entity.MachineToolsScrapNoteVO;
import com.efitops.basesetup.entity.PMCheckListDetailsVO;
import com.efitops.basesetup.entity.PMCheckListMasterVO;
import com.efitops.basesetup.entity.QualityScrapNoteDetailsVO;
import com.efitops.basesetup.entity.QualityScrapNoteVO;
import com.efitops.basesetup.entity.ScrapMaterialReturnRejectionDetailsVO;
import com.efitops.basesetup.entity.ScrapMaterialReturnRejectionVO;
import com.efitops.basesetup.entity.SetUpApprovalDetailsVO;
import com.efitops.basesetup.entity.SetUpApprovalParametersDetailsVO;
import com.efitops.basesetup.entity.SetUpApprovalVO;
import com.efitops.basesetup.entity.ShiftVO;
import com.efitops.basesetup.entity.SupplierChangeRequestVO;
import com.efitops.basesetup.entity.SupplierResponseEntryDetailsVO;
import com.efitops.basesetup.entity.SupplierResponseEntryVO;
import com.efitops.basesetup.entity.ToolCategoryDetailVO;
import com.efitops.basesetup.entity.ToolCategoryVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.entity.VendorComplaintDetailsVO;
import com.efitops.basesetup.entity.VendorComplaintEntryVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.ActivitiesCarriedOutComponentDetailsRepo;
import com.efitops.basesetup.repository.ActivitiesCarriedOutDetailsRepo;
import com.efitops.basesetup.repository.ActivitiesCarriedOutRepo;
import com.efitops.basesetup.repository.ActivityMasterRepo;
import com.efitops.basesetup.repository.AuthorizationForBreakdownRepo;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CategoryMasterRepo;
import com.efitops.basesetup.repository.CauseMasterRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.DailyInspectionCumRejectionDataRepo;
import com.efitops.basesetup.repository.DailyInspectionCumRejectionDetailsRepo;
import com.efitops.basesetup.repository.DepartmentRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.EmployeeMasterRepo;
import com.efitops.basesetup.repository.FlashNCReportAttachmentRepo;
import com.efitops.basesetup.repository.FlashNCReportRepo;
import com.efitops.basesetup.repository.InstrumentCalibrationDetailsRepo;
import com.efitops.basesetup.repository.InstrumentCalibrationRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.ListOfValuesDetailsRepo;
import com.efitops.basesetup.repository.LocationRepo;
import com.efitops.basesetup.repository.MachineMasterRepo;
import com.efitops.basesetup.repository.MachineToolBreakdownAttachementRepo;
import com.efitops.basesetup.repository.MachineToolBreakdownRepo;
import com.efitops.basesetup.repository.MachineToolRectificationRepo;
import com.efitops.basesetup.repository.MachineToolsScrapNoteAttachmentRepo;
import com.efitops.basesetup.repository.MachineToolsScrapNoteDetailsRepo;
import com.efitops.basesetup.repository.MachineToolsScrapNoteRepo;
import com.efitops.basesetup.repository.OrderAcceptanceRepo;
import com.efitops.basesetup.repository.PMCheckListMasterRepo;
import com.efitops.basesetup.repository.QualityScrapNoteDetailsRepo;
import com.efitops.basesetup.repository.QualityScrapNoteRepo;
import com.efitops.basesetup.repository.SalesContractRepo;
import com.efitops.basesetup.repository.ScrapMaterialReturnRejectionDetailsRepo;
import com.efitops.basesetup.repository.ScrapMaterialReturnRejectionRepo;
import com.efitops.basesetup.repository.SetUpApprovalDetailsRepo;
import com.efitops.basesetup.repository.SetUpApprovalParametersDetailsRepo;
import com.efitops.basesetup.repository.SetUpApprovalRepo;
import com.efitops.basesetup.repository.ShiftRepo;
import com.efitops.basesetup.repository.SupplierChangeRequestRepo;
import com.efitops.basesetup.repository.SupplierResponseEntryDetailsRepo;
import com.efitops.basesetup.repository.SupplierResponseEntryRepo;
import com.efitops.basesetup.repository.ToolCategoryRepo;
import com.efitops.basesetup.repository.UnitMasterRepo;
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

	@Autowired
	private FlashNCReportRepo flashNCReportRepo;

	@Autowired
	private FlashNCReportAttachmentRepo flashNCReportAttachmentRepo;

	@Autowired
	SupplierChangeRequestRepo supplierChangeRequestRepo;

	@Autowired
	private CategoryMasterRepo categoryMasterRepo;

	@Autowired
	private CauseMasterRepo causeMasterRepo;

	@Autowired
	private PMCheckListMasterRepo pmCheckListMasterRepo;

	@Autowired
	private ToolCategoryDetailRepo toolCategoryDetailRepo;

	@Autowired
	private ActivityMasterRepo activityMasterRepo;

	@Autowired
	private MachineToolBreakdownRepo machineToolBreakdownRepo;

	@Autowired
	private MachineToolBreakdownAttachementRepo machineToolBreakdownAttachementRepo;

	@Autowired
	private ToolCategoryRepo toolCategoryRepo;

	@Autowired
	private MachineToolRectificationRepo machineToolRectificationRepo;

	@Autowired
	private AuthorizationForBreakdownRepo authorizationForBreakdownRepo;

	@Autowired
	private MachineToolsScrapNoteRepo machineToolsScrapNoteRepo;

	@Autowired
	private MachineToolsScrapNoteDetailsRepo machineToolsScrapNoteDetailsRepo;

	@Autowired
	private MachineToolsScrapNoteAttachmentRepo machineToolScrapNoteAttachmentRepo;

	@Autowired
	private ActivitiesCarriedOutRepo activitiesCarriedOutRepo;

	@Autowired
	private ActivitiesCarriedOutDetailsRepo activitiesCarriedOutDetailsRepo;

	@Autowired
	private ActivitiesCarriedOutComponentDetailsRepo activitiesCarriedOutComponentDetailsRepo;

	@Autowired
	private QualityScrapNoteRepo qualityScrapNoteRepo;

	@Autowired
	private QualityScrapNoteDetailsRepo qualityScrapNoteDetailsRepo;

	@Autowired
	private ScrapMaterialReturnRejectionRepo scrapMaterialReturnRejectionRepo;

	@Autowired
	private ScrapMaterialReturnRejectionDetailsRepo scrapMaterialReturnRejectionDetailsRepo;

	@Autowired
	private UnitMasterRepo unitMasterRepo;

	@Autowired
	private OrderAcceptanceRepo orderAcceptanceRepo;
	
	@Autowired
	private SalesContractRepo salesContractRepo;

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

//			String docId = setUpApprovalRepo.getSetUpApprovalDocId(setUpApprovalDTO.getOrgId(),
//					setUpApprovalDTO.getFinancialYear(), screenCode);
//
//			if (StringUtils.isBlank(docId)) {
//
//				throw new ApplicationException("Set Up Approval DocId Not Found");
//			}
//
//			setUpApprovalVO.setDocId(docId);

			/*
			 * Document Type Mapping
			 */
//			var documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo.findByOrgIdAndFinYearAndScreenCode(
//					setUpApprovalDTO.getOrgId(), setUpApprovalDTO.getFinancialYear(), screenCode);
//
//			if (documentTypeMappingDetailsVO == null) {
//
//				throw new ApplicationException("Document Type Mapping Details Not Found");
//			}
//
//			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
//
//			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

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

		responseDTO.setActive(vo.getActive());

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

	@Override
	public SetUpApprovalResponseDTO getSetUpApprovalById(Long id) throws ApplicationException {

		SetUpApprovalVO setUpApprovalVO = setUpApprovalRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Set Up Approval Details Not Found"));

		return setUpApprovalResponse(setUpApprovalVO);
	}

	@Override
	public List<SetUpApprovalResponseDTO> getSetUpApprovalByOrgId(Long orgId, Long branch) throws ApplicationException {

		List<SetUpApprovalVO> setUpApprovalVOList = setUpApprovalRepo.getSetUpApprovalByOrgId(orgId, branch);

		if (setUpApprovalVOList == null || setUpApprovalVOList.isEmpty()) {

			throw new ApplicationException("No Set Up Approval Details Found");
		}

		List<SetUpApprovalResponseDTO> responseList = new ArrayList<>();

		for (SetUpApprovalVO setUpApprovalVO : setUpApprovalVOList) {

			responseList.add(setUpApprovalResponse(setUpApprovalVO));
		}

		return responseList;
	}

	@Override
	public List<Map<String, Object>> getFgSfgItemDropdownForSetUpApproval(Long orgId, Long branch)
			throws ApplicationException {

		List<Object[]> itemList = setUpApprovalRepo.getFgSfgItemDropdownForSetUpApproval(orgId, branch);

		if (itemList == null || itemList.isEmpty()) {
			throw new ApplicationException("No FG or SFG Item Details Found");
		}

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : itemList) {

			Map<String, Object> itemMap = new HashMap<>();

			itemMap.put("itemId", obj[0]);
			itemMap.put("itemCode", obj[1]);
			itemMap.put("itemDescription", obj[2]);
			itemMap.put("customerPartNo", obj[3]);
			itemMap.put("itemType", obj[4]);
			itemMap.put("drawingNo", obj[5]);

			responseList.add(itemMap);
		}

		return responseList;
	}

	@Override
	public List<Map<String, Object>> getProcessSheetNoForSetUpApproval(Long item, Long orgId, Long branch)
			throws ApplicationException {

		List<Object[]> processSheetList = setUpApprovalRepo.getProcessSheetNoForSetUpApproval(item, orgId, branch);

		if (processSheetList == null || processSheetList.isEmpty()) {
			throw new ApplicationException("No Process Sheet Details Found For Selected Item");
		}

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : processSheetList) {

			Map<String, Object> processSheetMap = new HashMap<>();

			processSheetMap.put("id", obj[0]);
			processSheetMap.put("processSheetNo", obj[1]);

			responseList.add(processSheetMap);
		}

		return responseList;
	}

	@Override
	public List<Map<String, Object>> getControlPlanDetailsForSetUpApproval(Long item, String processSheetNo, Long orgId,
			Long branch) throws ApplicationException {

		List<Object[]> controlPlanList = setUpApprovalRepo.getControlPlanDetailsForSetUpApproval(item, processSheetNo,
				orgId, branch);

		if (controlPlanList == null || controlPlanList.isEmpty()) {
			throw new ApplicationException("No Control Plan Details Found For Selected Item And Process Sheet No");
		}

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : controlPlanList) {

			Map<String, Object> controlPlanMap = new HashMap<>();

			controlPlanMap.put("controlPlanId", obj[0]);
			controlPlanMap.put("controlPlanNo", obj[1]);
			controlPlanMap.put("controlPlanDetailId", obj[2]);
			controlPlanMap.put("operationNo", obj[3]);
			controlPlanMap.put("description", obj[4]);
			controlPlanMap.put("specification", obj[5]);

			responseList.add(controlPlanMap);
		}

		return responseList;
	}

	@Override
	public String getSetUpApprovalDocId(Long orgId, String financialYear) throws ApplicationException {

		String screenCode = "SUA";

		String docId = setUpApprovalRepo.getSetUpApprovalDocId(orgId, financialYear, screenCode);

		if (StringUtils.isBlank(docId)) {
			throw new ApplicationException("Set Up Approval DocId Not Found");
		}

		return docId;
	}

//FlashNC Report

	@Override
	@Transactional
	public Map<String, Object> updateCreateFlashNCReport(FlashNCReportDTO flashNCReportDTO, MultipartFile[] files,
			MultipartFile[] images) throws ApplicationException {

		FlashNCReportVO flashNCReportVO;
		String message;

		if (ObjectUtils.isNotEmpty(flashNCReportDTO.getId())) {

			flashNCReportVO = flashNCReportRepo.findById(flashNCReportDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Flash NC Report"));

			flashNCReportVO.setUpdatedBy(flashNCReportDTO.getCreatedBy());

			message = "Flash NC Report Updated Successfully";

		} else {

			flashNCReportVO = new FlashNCReportVO();

			String screenCode = "FNR";

			String docId = flashNCReportRepo.getFlashNCReportDocId(flashNCReportDTO.getOrgId(),
					flashNCReportDTO.getFinancialYear(), screenCode);

			if (StringUtils.isBlank(docId)) {
				throw new ApplicationException("Flash NC Report DocId Not Found");
			}

			flashNCReportVO.setDocId(docId);

			DocumentTypeMappingDetailsVO mapping = documentTypeMappingDetailsRepo.findByOrgIdAndFinYearAndScreenCode(
					flashNCReportDTO.getOrgId(), flashNCReportDTO.getFinancialYear(), screenCode);

			if (mapping == null) {
				throw new ApplicationException("Document Type Mapping Details Not Found");
			}

			mapping.setLastNo(mapping.getLastNo() + 1);
			documentTypeMappingDetailsRepo.save(mapping);

			flashNCReportVO.setCreatedBy(flashNCReportDTO.getCreatedBy());

			flashNCReportVO.setUpdatedBy(flashNCReportDTO.getCreatedBy());

			message = "Flash NC Report Created Successfully";
		}

		/*
		 * Set Flash NC Report values
		 */
		createUpdateFlashNCReportVO(flashNCReportDTO, flashNCReportVO);

		/*
		 * Save Main VO first
		 */
		flashNCReportVO = flashNCReportRepo.save(flashNCReportVO);

		// Save header image
		saveFlashNCReportImage(images, flashNCReportVO);

		/*
		 * Save Attachments
		 */
		saveFlashNCReportAttachments(files, flashNCReportVO);

		/*
		 * Response
		 */
		FlashNCReportResponseDTO responseDTO = flashNCReportResponse(flashNCReportVO);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);
		response.put("flashNCReportVO", responseDTO);

		return response;
	}

	private void createUpdateFlashNCReportVO(FlashNCReportDTO dto, FlashNCReportVO vo) throws ApplicationException {

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

			ListOfValuesDetailsVO belongsTo = listOfValuesDetailsRepo.findById(dto.getBelongsTo())
					.orElseThrow(() -> new ApplicationException("Belongs To Not Found"));

			vo.setBelongsTo(belongsTo);
		}

		/*
		 * Reference
		 */
		if (dto.getReference() != null) {

			ListOfValuesDetailsVO reference = listOfValuesDetailsRepo.findById(dto.getReference())
					.orElseThrow(() -> new ApplicationException("Reference Not Found"));

			vo.setReference(reference);
		}

		/*
		 * From Department
		 */
		if (dto.getFromDept() != null) {

			ListOfValuesDetailsVO fromDept = listOfValuesDetailsRepo.findById(dto.getFromDept())
					.orElseThrow(() -> new ApplicationException("From Department Not Found"));

			vo.setFromDept(fromDept);
		}

		/*
		 * To Department
		 */
		if (dto.getToDept() != null) {

			ListOfValuesDetailsVO toDept = listOfValuesDetailsRepo.findById(dto.getToDept())
					.orElseThrow(() -> new ApplicationException("To Department Not Found"));

			vo.setToDept(toDept);
		}

		/*
		 * Supplier
		 */
		if (dto.getSupplier() != null) {

			CustomerVO supplier = customerRepo.findById(dto.getSupplier())
					.orElseThrow(() -> new ApplicationException("Supplier Not Found"));

			vo.setSupplier(supplier);
		}

		/*
		 * Item
		 */
		if (dto.getItem() != null) {

			ItemMasterVO item = itemRepo.findById(dto.getItem())
					.orElseThrow(() -> new ApplicationException("Item Not Found"));

			vo.setItem(item);
		}

		/*
		 * Disposal
		 */
		if (dto.getDisposal() != null) {

			ListOfValuesDetailsVO disposal = listOfValuesDetailsRepo.findById(dto.getDisposal())
					.orElseThrow(() -> new ApplicationException("Disposal Not Found"));

			vo.setDisposal(disposal);
		}

		/*
		 * Inspected By
		 */
		if (dto.getInspectedBy() != null) {

			EmployeeMasterVO inspectedBy = employeeMasterRepo.findById(dto.getInspectedBy())
					.orElseThrow(() -> new ApplicationException("Inspected By Employee Not Found"));

			vo.setInspectedBy(inspectedBy);
		}

		/*
		 * Status
		 */
		if (dto.getStatus() != null) {

			ListOfValuesDetailsVO status = listOfValuesDetailsRepo.findById(dto.getStatus())
					.orElseThrow(() -> new ApplicationException("Status Not Found"));

			vo.setStatus(status);
		}

		/*
		 * Normal Fields
		 */
		vo.setDescription(dto.getDescription());

		vo.setDrawingNo(dto.getDrawingNo());

		vo.setMrinSCGRNNO(dto.getMrinSCGRNNO());

		vo.setMrinDate(dto.getMrinDate());

		vo.setOccPercentage(dto.getOccPercentage());

		vo.setInvoiceNo(dto.getInvoiceNo());

		vo.setPoNo(dto.getPoNo());

		vo.setOperationNo(dto.getOperationNo());

		vo.setLotQty(dto.getLotQty());

		vo.setSampleQty(dto.getSampleQty());

		vo.setNcQty(dto.getNcQty());

		vo.setDefectSeen(dto.getDefectSeen());

		vo.setProblemStatus(dto.getProblemStatus());

		vo.setActionOnDefectiveLot(dto.getActionOnDefectiveLot());

		vo.setNarration(dto.getNarration());

		vo.setOrgId(dto.getOrgId());

		vo.setFinancialYear(dto.getFinancialYear());

		vo.setActive(dto.isActive());

		vo.setCancelRemarks(dto.getCancelRemarks());
	}

	private void saveFlashNCReportImage(MultipartFile[] images, FlashNCReportVO flashNCReportVO)
			throws ApplicationException {

		if (images == null || images.length == 0) {
			return;
		}

		try {

			Path flashNCReportFolder = Paths.get(flashNCReportUploadPath, "flash-nc-report",
					flashNCReportVO.getId().toString());

			createDirectory(flashNCReportFolder);

			for (MultipartFile image : images) {

				if (image == null || image.isEmpty()) {
					continue;
				}

				String originalName = image.getOriginalFilename();

				if (originalName == null) {
					originalName = "image";
				}

				originalName = originalName.replaceAll("\\s+", "_");

				String extension = "";

				if (originalName.contains(".")) {

					extension = originalName.substring(originalName.lastIndexOf("."));

					originalName = originalName.substring(0, originalName.lastIndexOf("."));
				}

				String fileName = originalName + "_" + flashNCReportVO.getId() + extension;

				Path filePath = flashNCReportFolder.resolve(fileName);

				try (InputStream inputStream = image.getInputStream()) {

					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				}

				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/api/vendorComplaintEntry/viewFile/").toUriString();

				String relativePath = "flash-nc-report/" + flashNCReportVO.getId() + "/" + fileName;

				String publicUrl = baseUrl + relativePath;

				// Save image URL/path in parent table
				flashNCReportVO.setFlashNCImageName(publicUrl);

				flashNCReportRepo.save(flashNCReportVO);
			}

		} catch (IOException e) {

			throw new ApplicationException("Image Upload Failed : " + e.getMessage());
		}
	}

	private FlashNCReportResponseDTO flashNCReportResponse(FlashNCReportVO vo) {

		FlashNCReportResponseDTO response = new FlashNCReportResponseDTO();

		response.setId(vo.getId());

		/*
		 * Branch Response
		 */
		if (vo.getBranch() != null) {

			BranchResponseDTO branchResponse = new BranchResponseDTO();

			branchResponse.setId(vo.getBranch().getId());

			branchResponse.setBranchCode(vo.getBranch().getBranchCode());

			branchResponse.setBranchName(vo.getBranch().getBranchName());

			response.setBranch(branchResponse);
		}

		/*
		 * Belongs To
		 */
		if (vo.getBelongsTo() != null) {

			ListOfValuesDetailsResponseDTO belongsTo = new ListOfValuesDetailsResponseDTO();

			belongsTo.setId(vo.getBelongsTo().getId());

			belongsTo.setCode(vo.getBelongsTo().getValueCode());

			belongsTo.setDescription(vo.getBelongsTo().getValueDescription());

			response.setBelongsTo(belongsTo);

		}

		/*
		 * Reference
		 */
		if (vo.getReference() != null) {

			ListOfValuesDetailsResponseDTO reference = new ListOfValuesDetailsResponseDTO();

			reference.setId(vo.getReference().getId());

			reference.setCode(vo.getReference().getValueCode());

			reference.setDescription(vo.getReference().getValueDescription());

			response.setReference(reference);
		}

		/*
		 * From Department
		 */
		if (vo.getFromDept() != null) {

			ListOfValuesDetailsResponseDTO fromDept = new ListOfValuesDetailsResponseDTO();

			fromDept.setId(vo.getFromDept().getId());

			fromDept.setCode(vo.getFromDept().getValueCode());

			fromDept.setDescription(vo.getFromDept().getValueDescription());

			response.setFromDept(fromDept);
		}

		/*
		 * To Department
		 */
		if (vo.getToDept() != null) {

			ListOfValuesDetailsResponseDTO toDept = new ListOfValuesDetailsResponseDTO();

			toDept.setId(vo.getToDept().getId());

			toDept.setCode(vo.getToDept().getValueCode());

			toDept.setDescription(vo.getToDept().getValueDescription());

			response.setToDept(toDept);
		}

		/*
		 * Supplier
		 */
		if (vo.getSupplier() != null) {

			CustomerResponse1DTO supplier = new CustomerResponse1DTO();

			supplier.setId(vo.getSupplier().getId());

			supplier.setCustomerName(vo.getSupplier().getCustomerName());

			response.setSupplier(supplier);
		}

		/*
		 * Item
		 */
		if (vo.getItem() != null) {

			ItemResponse1DTO item = new ItemResponse1DTO();

			item.setId(vo.getItem().getId());

			item.setItemCode(vo.getItem().getItemCode());

			item.setItemDescription(vo.getItem().getItemDescription());

			response.setItem(item);
		}

		/*
		 * Disposal
		 */
		if (vo.getDisposal() != null) {

			ListOfValuesDetailsResponseDTO disposal = new ListOfValuesDetailsResponseDTO();

			disposal.setId(vo.getDisposal().getId());

			disposal.setCode(vo.getDisposal().getValueCode());

			disposal.setDescription(vo.getDisposal().getValueDescription());

			response.setDisposal(disposal);
		}

		/*
		 * Inspected By
		 */
		if (vo.getInspectedBy() != null) {

			EmployeeDropdownResponseDTO inspectedBy = new EmployeeDropdownResponseDTO();

			inspectedBy.setEmployeeId(vo.getInspectedBy().getId());

			inspectedBy.setEmployeeName(vo.getInspectedBy().getEmployeeName());

			response.setInspectedBy(inspectedBy);
		}

		/*
		 * Status
		 */
		if (vo.getStatus() != null) {

			ListOfValuesDetailsResponseDTO status = new ListOfValuesDetailsResponseDTO();

			status.setId(vo.getStatus().getId());

			status.setCode(vo.getStatus().getValueCode());

			status.setDescription(vo.getStatus().getValueDescription());

			response.setStatus(status);
		}

		/*
		 * Normal Fields
		 */
		response.setDescription(vo.getDescription());

		response.setDrawingNo(vo.getDrawingNo());

		response.setMrinSCGRNNO(vo.getMrinSCGRNNO());

		response.setMrinDate(vo.getMrinDate());

		response.setOccPercentage(vo.getOccPercentage());

		response.setInvoiceNo(vo.getInvoiceNo());

		response.setPoNo(vo.getPoNo());

		response.setOperationNo(vo.getOperationNo());

		response.setLotQty(vo.getLotQty());

		response.setSampleQty(vo.getSampleQty());

		response.setNcQty(vo.getNcQty());

		response.setDefectSeen(vo.getDefectSeen());

		response.setProblemStatus(vo.getProblemStatus());

		response.setActionOnDefectiveLot(vo.getActionOnDefectiveLot());

		response.setNarration(vo.getNarration());

		response.setOrgId(vo.getOrgId());

		response.setFinancialYear(vo.getFinancialYear());

		response.setActive(vo.getActive());

		response.setCancelRemarks(vo.getCancelRemarks());

		response.setCreatedBy(vo.getCreatedBy());

		response.setFlashNCImageName(vo.getFlashNCImageName());

		/*
		 * Attachment Response
		 */
		List<FlashNCReportAttachmentResponseDTO> attachmentList = new ArrayList<>();

		if (vo.getFlashNCReportAttachmentVO() != null && !vo.getFlashNCReportAttachmentVO().isEmpty()) {

			for (FlashNCReportAttachmentVO attachmentVO : vo.getFlashNCReportAttachmentVO()) {

				FlashNCReportAttachmentResponseDTO attachmentDTO = new FlashNCReportAttachmentResponseDTO();

				attachmentDTO.setId(attachmentVO.getId());

				attachmentDTO.setName(attachmentVO.getName());

				attachmentDTO.setFileName(attachmentVO.getFileName());

				attachmentDTO.setFilePath(attachmentVO.getFilePath());

				attachmentDTO.setFileSize(attachmentVO.getFileSize());

				attachmentDTO.setContentType(attachmentVO.getContentType());

				attachmentDTO.setUploadOn(attachmentVO.getUploadOn());

				attachmentList.add(attachmentDTO);
			}
		}

		response.setFlashNCReportAttachmentResponseDTO(attachmentList);

		return response;
	}

	@Value("${flash.nc.report.upload.path}")
	private String flashNCReportUploadPath;

	private void saveFlashNCReportAttachments(MultipartFile[] files, FlashNCReportVO flashNCReportVO)
			throws ApplicationException {

		if (files == null || files.length == 0) {
			return;
		}

		try {

			// 1. Create folder
			Path flashNCReportFolder = Paths.get(flashNCReportUploadPath, "flash-nc-report",
					flashNCReportVO.getId().toString());

			createDirectory(flashNCReportFolder);

			List<FlashNCReportAttachmentVO> attachmentList = new ArrayList<>();

			for (MultipartFile file : files) {

				if (file == null || file.isEmpty()) {
					continue;
				}

				// 2. Get original file name
				String originalName = file.getOriginalFilename();

				if (originalName == null) {
					originalName = "file";
				}

				originalName = originalName.replaceAll("\\s+", "_");

				String extension = "";

				if (originalName.contains(".")) {

					extension = originalName.substring(originalName.lastIndexOf("."));

					originalName = originalName.substring(0, originalName.lastIndexOf("."));
				}

				// 3. Create unique file name
				String fileName = originalName + "_" + flashNCReportVO.getId() + extension;

				// 4. Actual physical file path
				Path filePath = flashNCReportFolder.resolve(fileName);

				// 5. Save image to disk
				try (InputStream inputStream = file.getInputStream()) {

					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				}

				// 6. Create URL for viewing image
				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/api/vendorComplaintEntry/viewFile/").toUriString();

				String relativePath = "flash-nc-report/" + flashNCReportVO.getId() + "/" + fileName;

				String publicUrl = baseUrl + relativePath;

				// 7. Save attachment details in DB
				FlashNCReportAttachmentVO attachment = new FlashNCReportAttachmentVO();

				attachment.setFlashNCReportVO(flashNCReportVO);
				attachment.setName(file.getOriginalFilename());
				attachment.setFileName(fileName);
				attachment.setFilePath(publicUrl);
				attachment.setFileSize(file.getSize());
				attachment.setContentType(file.getContentType());
				attachment.setUploadOn(LocalDateTime.now());

				attachmentList.add(attachment);
			}

			// 8. Save attachment records
			if (!attachmentList.isEmpty()) {

				List<FlashNCReportAttachmentVO> saved = flashNCReportAttachmentRepo.saveAll(attachmentList);

				flashNCReportVO.setFlashNCReportAttachmentVO(saved);
			}

		} catch (IOException e) {

			throw new ApplicationException("File Upload Failed : " + e.getMessage());
		}
	}

	private void createDirectory(Path path) throws IOException {

		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
	}

	@Override
	public ResponseEntity<byte[]> viewFlashNCReportFile(HttpServletRequest request) throws IOException {

		return serveFile(request, "/api/vendorComplaintEntry/viewFile/", flashNCReportUploadPath);
	}

	private ResponseEntity<byte[]> serveFile(HttpServletRequest request, String apiPrefix, String uploadBasePath)
			throws IOException {

		String uri = request.getRequestURI();

		String relativePath = uri.replace(apiPrefix, "");

		relativePath = URLDecoder.decode(relativePath, StandardCharsets.UTF_8);

		if (relativePath.startsWith("uploads/")) {
			relativePath = relativePath.substring("uploads/".length());
		}

		Path baseDir = Paths.get(uploadBasePath).toAbsolutePath().normalize();

		Path filePath = baseDir.resolve(relativePath).normalize();

		if (!filePath.startsWith(baseDir)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		if (!Files.exists(filePath)) {
			return ResponseEntity.notFound().build();
		}

		String contentType = Files.probeContentType(filePath);

		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		byte[] data = Files.readAllBytes(filePath);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline").body(data);
	}

	@Override
	public FlashNCReportResponseDTO getFlashNCReportById(Long id) throws ApplicationException {

		FlashNCReportVO flashNCReportVO = flashNCReportRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Flash NC Report Details Not Found"));

		return flashNCReportResponse(flashNCReportVO);
	}

	@Override
	public List<FlashNCReportResponseDTO> getFlashNCReportByOrgId(Long orgId, Long branch) throws ApplicationException {

		List<FlashNCReportVO> flashNCReportVOList = flashNCReportRepo.getFlashNCReportByOrgId(orgId, branch);

		if (flashNCReportVOList == null || flashNCReportVOList.isEmpty()) {

			throw new ApplicationException("No Flash NC Report Details Found");
		}

		List<FlashNCReportResponseDTO> responseList = new ArrayList<>();

		for (FlashNCReportVO flashNCReportVO : flashNCReportVOList) {

			responseList.add(flashNCReportResponse(flashNCReportVO));
		}

		return responseList;
	}

	@Override
	public String getFlashNCReportDocId(Long orgId, String financialYear) throws ApplicationException {

		String screenCode = "FNR";

		String docId = flashNCReportRepo.getFlashNCReportDocId(orgId, financialYear, screenCode);

		if (StringUtils.isBlank(docId)) {
			throw new ApplicationException("Flash NC Report DocId Not Found");
		}

		return docId;
	}

	@Override
	public List<Map<String, Object>> getQualityEmployeesForFlashNCReport(Long orgId, Long branch)
			throws ApplicationException {

		List<Object[]> employeeList = flashNCReportRepo.getQualityEmployeesForFlashNCReport(orgId, branch);

		if (employeeList == null || employeeList.isEmpty()) {
			throw new ApplicationException("No Quality Department Employees Found");
		}

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : employeeList) {

			Map<String, Object> employeeMap = new HashMap<>();

			employeeMap.put("employeeId", obj[0]);
			employeeMap.put("employeeCode", obj[1]);
			employeeMap.put("employeeName", obj[2]);

			responseList.add(employeeMap);
		}

		return responseList;
	}

	@Override
	public List<Map<String, Object>> getFromDeptDropdownForFlashNCReport(Long listOfValuesId)
			throws ApplicationException {

		List<Object[]> departmentList = flashNCReportRepo.getFromDeptDropdownForFlashNCReport(listOfValuesId);

		if (departmentList == null || departmentList.isEmpty()) {

			throw new ApplicationException("No From Department Found");
		}

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : departmentList) {

			Map<String, Object> departmentMap = new HashMap<>();

			departmentMap.put("id", obj[0]);
			departmentMap.put("valueCode", obj[1]);
			departmentMap.put("valueDescription", obj[2]);

			responseList.add(departmentMap);
		}

		return responseList;
	}

	@Override
	public List<Map<String, Object>> getToDepartmentDropdownForFlashNCReport(Long listOfValuesId, Long fromDept)
			throws ApplicationException {

		List<Object[]> departmentList = flashNCReportRepo.getToDepartmentDropdownForFlashNCReport(listOfValuesId,
				fromDept);

		if (departmentList == null || departmentList.isEmpty()) {

			throw new ApplicationException("No To Department Found");
		}

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : departmentList) {

			Map<String, Object> departmentMap = new HashMap<>();

			departmentMap.put("id", obj[0]);
			departmentMap.put("valueCode", obj[1]);
			departmentMap.put("valueDescription", obj[2]);

			responseList.add(departmentMap);
		}

		return responseList;
	}

	@Override
	public List<Map<String, Object>> getMRINGRNDropdownForFlashNCReport(Long orgId, Long branch)
			throws ApplicationException {

		List<Object[]> mrinGrnList = flashNCReportRepo.getMRINGRNDropdownForFlashNCReport(orgId, branch);

		if (mrinGrnList == null || mrinGrnList.isEmpty()) {

			throw new ApplicationException("No MRIN/GRN Details Found");
		}

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : mrinGrnList) {

			Map<String, Object> mrinGrnMap = new HashMap<>();

			mrinGrnMap.put("mrinGrnNo", obj[0]);
			mrinGrnMap.put("supplierCode", obj[1]);
			mrinGrnMap.put("supplierName", obj[2]);
			mrinGrnMap.put("invoiceNo", obj[3]);
			mrinGrnMap.put("item", obj[4]);
			mrinGrnMap.put("itemDescription", obj[5]);
			mrinGrnMap.put("mrinGrnDate", obj[6]);
			mrinGrnMap.put("poNo", obj[7]);
			mrinGrnMap.put("qty", obj[8]);
			mrinGrnMap.put("sourceType", obj[9]);

			responseList.add(mrinGrnMap);
		}

		return responseList;
	}

	@Override
	@Transactional
	public Map<String, Object> updateCreateSupplierChangeRequest(SupplierChangeRequestDTO supplierChangeRequestDTO)
			throws ApplicationException {

		SupplierChangeRequestVO supplierChangeRequestVO = new SupplierChangeRequestVO();

		String message;

		String screenCode = "SCR";

		if (ObjectUtils.isEmpty(supplierChangeRequestDTO.getId())) {

			String docId = supplierChangeRequestRepo.getSupplierChangeRequestDocId(supplierChangeRequestDTO.getOrgId(),
					supplierChangeRequestDTO.getFinancialYear(), screenCode);

			if (StringUtils.isBlank(docId)) {

				throw new ApplicationException("Supplier Change Request DocId Not Found");
			}

			supplierChangeRequestVO.setDocId(docId);

			DocumentTypeMappingDetailsVO mapping = documentTypeMappingDetailsRepo.findByOrgIdAndFinYearAndScreenCode(
					supplierChangeRequestDTO.getOrgId(), supplierChangeRequestDTO.getFinancialYear(), screenCode);

			if (mapping == null) {

				throw new ApplicationException("Document Type Mapping Details Not Found");
			}

			mapping.setLastNo(mapping.getLastNo() + 1);

			documentTypeMappingDetailsRepo.save(mapping);

			supplierChangeRequestVO.setCreatedBy(supplierChangeRequestDTO.getCreatedBy());

			supplierChangeRequestVO.setUpdatedBy(supplierChangeRequestDTO.getCreatedBy());

			message = "Supplier Change Request Created Successfully";

		} else {

			supplierChangeRequestVO = supplierChangeRequestRepo.findById(supplierChangeRequestDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Supplier Change Request Details"));

			supplierChangeRequestVO.setUpdatedBy(supplierChangeRequestDTO.getCreatedBy());

			message = "Supplier Change Request Updated Successfully";
		}

		createUpdateSupplierChangeRequestVO(supplierChangeRequestDTO, supplierChangeRequestVO);

		SupplierChangeRequestVO savedVO = supplierChangeRequestRepo.save(supplierChangeRequestVO);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("supplierChangeRequestVO", supplierChangeRequestResponse(savedVO));

		return response;
	}

	private void createUpdateSupplierChangeRequestVO(SupplierChangeRequestDTO dto, SupplierChangeRequestVO vo)
			throws ApplicationException {

		if (dto.getBranch() != null) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Invalid Branch"));

			vo.setBranch(branch);
		}

		if (dto.getVendorCode() != null) {

			CustomerVO customer = customerRepo.findById(dto.getVendorCode())
					.orElseThrow(() -> new ApplicationException("Invalid Vendor"));

			vo.setVendorCode(customer);
		}

		if (dto.getBuyerName() != null) {

			EmployeeMasterVO employee = employeeMasterRepo.findById(dto.getBuyerName())
					.orElseThrow(() -> new ApplicationException("Invalid Buyer"));

			vo.setBuyerName(employee);
		}

		if (dto.getSourceTriggeredBy() != null) {

			EmployeeMasterVO employee = employeeMasterRepo.findById(dto.getSourceTriggeredBy())
					.orElseThrow(() -> new ApplicationException("Invalid Source Triggered By"));

			vo.setSourceTriggeredBy(employee);
		}

		// Basic Details

		vo.setPartNo(dto.getPartNo());
		vo.setPartDescription(dto.getPartDescription());
		vo.setSupplierContact(dto.getSupplierContact());
		vo.setSupplierPhoneNo(dto.getSupplierPhoneNo());
		vo.setSupplierEmailId(dto.getSupplierEmailId());

		vo.setBuyerPhoneNo(dto.getBuyerPhoneNo());
		vo.setBuyerEmailId(dto.getBuyerEmailId());

		vo.setSourcePhoneNo(dto.getSourcePhoneNo());
		vo.setSourceEmailId(dto.getSourceEmailId());

		// Reason for Change

		vo.setCapacityIssueWithExisitingSupplier(dto.getCapacityIssueWithExisitingSupplier());

		vo.setCustomerRequirementDemandIncreased(dto.getCustomerRequirementDemandIncreased());

		vo.setAlternativeRMSourceorAdditionalRMSource(dto.getAlternativeRMSourceorAdditionalRMSource());

		vo.setInternalCapacityIssue(dto.getInternalCapacityIssue());

		vo.setChangeInSupplierBaseQualityIssueinExisitingSupplier(
				dto.getChangeInSupplierBaseQualityIssueinExisitingSupplier());

		vo.setSupplierCommercialIssue(dto.getSupplierCommercialIssue());

		vo.setCustomeApprovedSource(dto.getCustomeApprovedSource());

		vo.setOthers(dto.getOthers());

		vo.setChangeDescriptionInDetails(dto.getChangeDescriptionInDetails());

		vo.setDetailOfProposedProcessOfOutSourced(dto.getDetailOfProposedProcessOfOutSourced());

		// Impact of Change

		vo.setQualityImprovement(dto.getQualityImprovement());

		vo.setReducedLeadTime(dto.getReducedLeadTime());

		vo.setCostReduction(dto.getCostReduction());

		vo.setIncreaseManufacturingEfficiency(dto.getIncreaseManufacturingEfficiency());

		vo.setOthersPleaseSpecify(dto.getOthersPleaseSpecify());

		vo.setEffectOfChanges(dto.getEffectOfChanges());

		vo.setRiskAssessment(dto.getRiskAssessment());

		vo.setProposedIntroductionImplementationDate(dto.getProposedIntroductionImplementationDate());

		vo.setSupplierEvaluationReport(dto.getSupplierEvaluationReport());

		vo.setReliabilityFunctionalReportFromTDC(dto.getReliabilityFunctionalReportFromTDC());

		vo.setCustomerApproval(dto.getCustomerApproval());

		vo.setOnJobTrainingReportFromMfg(dto.getOnJobTrainingReportFromMfg());

		vo.setProcessAuditReport(dto.getProcessAuditReport());

		vo.setSupplierRegistrationFrom(dto.getSupplierRegistrationFrom());

		vo.setPpapIsirRequired(dto.getPpapIsirRequired());

		vo.setChangeRequestApproval(dto.getChangeRequestApproval());

		// Authorized Signatures

		if (dto.getSignByPurchase() != null) {

			EmployeeMasterVO employee = employeeMasterRepo.findById(dto.getSignByPurchase())
					.orElseThrow(() -> new ApplicationException("Invalid Purchase Employee"));

			vo.setSignByPurchase(employee);
		}

		vo.setPurchaseDisposition(dto.getPurchaseDisposition());

		if (dto.getSignByTDC() != null) {

			EmployeeMasterVO employee = employeeMasterRepo.findById(dto.getSignByTDC())
					.orElseThrow(() -> new ApplicationException("Invalid TDC Employee"));

			vo.setSignByTDC(employee);
		}

		vo.setTdcDisposition(dto.getTdcDisposition());

		if (dto.getSignByProduction() != null) {

			EmployeeMasterVO employee = employeeMasterRepo.findById(dto.getSignByProduction())
					.orElseThrow(() -> new ApplicationException("Invalid Production Employee"));

			vo.setSignByProduction(employee);
		}

		vo.setProductionDisposition(dto.getProductionDisposition());

		if (dto.getSignByQuality() != null) {

			EmployeeMasterVO employee = employeeMasterRepo.findById(dto.getSignByQuality())
					.orElseThrow(() -> new ApplicationException("Invalid Quality Employee"));

			vo.setSignByQuality(employee);
		}

		vo.setQualityDisposition(dto.getQualityDisposition());

		vo.setNote(dto.getNote());

		// Common fields

		vo.setActive(dto.isActive());

		vo.setOrgId(dto.getOrgId());

		vo.setFinancialYear(dto.getFinancialYear());

	}

	private SupplierChangeRequestResponseDTO supplierChangeRequestResponse(SupplierChangeRequestVO vo) {

		SupplierChangeRequestResponseDTO response = new SupplierChangeRequestResponseDTO();

		response.setId(vo.getId());

		response.setDocId(vo.getDocId());

		response.setDocDate(vo.getDocDate());

		// Branch

		if (vo.getBranch() != null) {

			BranchResponseDTO branchResponse = new BranchResponseDTO();

			branchResponse.setId(vo.getBranch().getId());

			branchResponse.setBranchCode(vo.getBranch().getBranchCode());

			branchResponse.setBranchName(vo.getBranch().getBranchName());

			response.setBranch(branchResponse);
		}

		// Vendor

		if (vo.getVendorCode() != null) {

			CustomerResponse1DTO customerResponse = new CustomerResponse1DTO();

			customerResponse.setId(vo.getVendorCode().getId());

			customerResponse.setCustomerName(vo.getVendorCode().getCustomerName());

			response.setVendorCode(customerResponse);
		}

		// Basic Details

		response.setPartNo(vo.getPartNo());

		response.setPartDescription(vo.getPartDescription());

		response.setSupplierContact(vo.getSupplierContact());

		response.setSupplierPhoneNo(vo.getSupplierPhoneNo());

		response.setSupplierEmailId(vo.getSupplierEmailId());

		// Buyer

		if (vo.getBuyerName() != null) {

			EmployeeDropdownResponseDTO employeeResponse = new EmployeeDropdownResponseDTO();

			employeeResponse.setEmployeeId(vo.getBuyerName().getId());

			employeeResponse.setEmployeeName(vo.getBuyerName().getEmployeeName());

			response.setBuyerName(employeeResponse);
		}

		response.setBuyerPhoneNo(vo.getBuyerPhoneNo());

		response.setBuyerEmailId(vo.getBuyerEmailId());

		// Source Triggered By

		if (vo.getSourceTriggeredBy() != null) {

			EmployeeDropdownResponseDTO employeeResponse = new EmployeeDropdownResponseDTO();

			employeeResponse.setEmployeeId(vo.getSourceTriggeredBy().getId());

			employeeResponse.setEmployeeName(vo.getSourceTriggeredBy().getEmployeeName());

			response.setSourceTriggeredBy(employeeResponse);
		}

		response.setSourcePhoneNo(vo.getSourcePhoneNo());

		response.setSourceEmailId(vo.getSourceEmailId());

		// Reason for Change

		response.setCapacityIssueWithExisitingSupplier(vo.getCapacityIssueWithExisitingSupplier());

		response.setCustomerRequirementDemandIncreased(vo.getCustomerRequirementDemandIncreased());

		response.setAlternativeRMSourceorAdditionalRMSource(vo.getAlternativeRMSourceorAdditionalRMSource());

		response.setInternalCapacityIssue(vo.getInternalCapacityIssue());

		response.setChangeInSupplierBaseQualityIssueinExisitingSupplier(
				vo.getChangeInSupplierBaseQualityIssueinExisitingSupplier());

		response.setSupplierCommercialIssue(vo.getSupplierCommercialIssue());

		response.setCustomeApprovedSource(vo.getCustomeApprovedSource());

		response.setOthers(vo.getOthers());

		response.setChangeDescriptionInDetails(vo.getChangeDescriptionInDetails());

		response.setDetailOfProposedProcessOfOutSourced(vo.getDetailOfProposedProcessOfOutSourced());

		// Impact of Change

		response.setQualityImprovement(vo.getQualityImprovement());

		response.setReducedLeadTime(vo.getReducedLeadTime());

		response.setCostReduction(vo.getCostReduction());

		response.setIncreaseManufacturingEfficiency(vo.getIncreaseManufacturingEfficiency());

		response.setOthersPleaseSpecify(vo.getOthersPleaseSpecify());

		response.setEffectOfChanges(vo.getEffectOfChanges());

		response.setRiskAssessment(vo.getRiskAssessment());

		response.setProposedIntroductionImplementationDate(vo.getProposedIntroductionImplementationDate());

		response.setSupplierEvaluationReport(vo.getSupplierEvaluationReport());

		response.setReliabilityFunctionalReportFromTDC(vo.getReliabilityFunctionalReportFromTDC());

		response.setCustomerApproval(vo.getCustomerApproval());

		response.setOnJobTrainingReportFromMfg(vo.getOnJobTrainingReportFromMfg());

		response.setProcessAuditReport(vo.getProcessAuditReport());

		response.setSupplierRegistrationFrom(vo.getSupplierRegistrationFrom());

		response.setPpapIsirRequired(vo.getPpapIsirRequired());

		response.setChangeRequestApproval(vo.getChangeRequestApproval());

		// Sign By Purchase

		if (vo.getSignByPurchase() != null) {

			EmployeeDropdownResponseDTO employeeResponse = new EmployeeDropdownResponseDTO();

			employeeResponse.setEmployeeId(vo.getSignByPurchase().getId());

			employeeResponse.setEmployeeName(vo.getSignByPurchase().getEmployeeName());

			response.setSignByPurchase(employeeResponse);
		}

		response.setPurchaseDisposition(vo.getPurchaseDisposition());

		// Sign By TDC

		if (vo.getSignByTDC() != null) {

			EmployeeDropdownResponseDTO employeeResponse = new EmployeeDropdownResponseDTO();

			employeeResponse.setEmployeeId(vo.getSignByTDC().getId());

			employeeResponse.setEmployeeName(vo.getSignByTDC().getEmployeeName());

			response.setSignByTDC(employeeResponse);
		}

		response.setTdcDisposition(vo.getTdcDisposition());

		// Sign By Production

		if (vo.getSignByProduction() != null) {

			EmployeeDropdownResponseDTO employeeResponse = new EmployeeDropdownResponseDTO();

			employeeResponse.setEmployeeId(vo.getSignByProduction().getId());

			employeeResponse.setEmployeeName(vo.getSignByProduction().getEmployeeName());

			response.setSignByProduction(employeeResponse);
		}

		response.setProductionDisposition(vo.getProductionDisposition());

		// Sign By Quality

		if (vo.getSignByQuality() != null) {

			EmployeeDropdownResponseDTO employeeResponse = new EmployeeDropdownResponseDTO();

			employeeResponse.setEmployeeId(vo.getSignByQuality().getId());

			employeeResponse.setEmployeeName(vo.getSignByQuality().getEmployeeName());

			response.setSignByQuality(employeeResponse);
		}

		response.setQualityDisposition(vo.getQualityDisposition());

		response.setNote(vo.getNote());

		// Common fields

		response.setActive(vo.isActive() ? "Active" : "In-Active");

		response.setOrgId(vo.getOrgId());

		response.setFinancialYear(vo.getFinancialYear());

		response.setCreatedBy(vo.getCreatedBy());

		return response;
	}

	@Override
	public String getSupplierChangeRequestDocId(Long orgId, String financialYear, String screenCode)
			throws ApplicationException {

		String docId = supplierChangeRequestRepo.getSupplierChangeRequestDocId(orgId, financialYear, screenCode);

		if (StringUtils.isBlank(docId)) {
			throw new ApplicationException("Supplier Change Request DocId Not Found");
		}

		return docId;
	}

	@Override
	public SupplierChangeRequestResponseDTO getSupplierChangeRequestById(Long id) throws ApplicationException {

		SupplierChangeRequestVO supplierChangeRequestVO = supplierChangeRequestRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Invalid Supplier Change Request Details"));

		return supplierChangeRequestResponse(supplierChangeRequestVO);
	}

	@Override
	public List<SupplierChangeRequestResponseDTO> getSupplierChangeRequestByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<SupplierChangeRequestVO> supplierChangeRequestList = supplierChangeRequestRepo
				.getSupplierChangeRequestByOrgId(orgId, branch);

		if (supplierChangeRequestList == null || supplierChangeRequestList.isEmpty()) {

			throw new ApplicationException("No Supplier Change Request Details Found");
		}

		List<SupplierChangeRequestResponseDTO> responseList = new ArrayList<>();

		for (SupplierChangeRequestVO vo : supplierChangeRequestList) {

			responseList.add(supplierChangeRequestResponse(vo));
		}

		return responseList;
	}

	@Override
	public List<Map<String, Object>> getVendorCodeDropdownForSupplierChangeRequest(Long orgId, Long branch)
			throws ApplicationException {

		List<Object[]> vendorList = supplierChangeRequestRepo.getVendorCodeDropdownForSupplierChangeRequest(orgId,
				branch);

		if (vendorList == null || vendorList.isEmpty()) {
			throw new ApplicationException("No Vendor Found");
		}

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : vendorList) {

			Map<String, Object> vendorMap = new HashMap<>();

			vendorMap.put("id", obj[0]);
			vendorMap.put("vendorCode", obj[1]);
			vendorMap.put("supplierName", obj[2]);

			responseList.add(vendorMap);
		}

		return responseList;
	}

	@Override
	public List<Map<String, Object>> getPurchaseEmployeesDropdownForSupplierChangeRequest(Long orgId, Long branch)
			throws ApplicationException {

		List<Object[]> employeeList = supplierChangeRequestRepo
				.getPurchaseEmployeesDropdownForSupplierChangeRequest(orgId, branch);

		if (employeeList == null || employeeList.isEmpty()) {
			throw new ApplicationException("No Purchase Employee Found");
		}

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : employeeList) {

			Map<String, Object> employeeMap = new HashMap<>();

			employeeMap.put("employeeId", obj[0]);
			employeeMap.put("employeeCode", obj[1]);
			employeeMap.put("employeeName", obj[2]);

			responseList.add(employeeMap);
		}

		return responseList;
	}

	@Override
	public List<Map<String, Object>> getTDCEmployeesDropdownForSupplierChangeRequest(Long orgId, Long branch)
			throws ApplicationException {

		List<Object[]> employeeList = supplierChangeRequestRepo.getTDCEmployeesDropdownForSupplierChangeRequest(orgId,
				branch);

		if (employeeList == null || employeeList.isEmpty()) {
			throw new ApplicationException("No TDC Employee Found");
		}

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : employeeList) {

			Map<String, Object> employeeMap = new HashMap<>();

			employeeMap.put("employeeId", obj[0]);
			employeeMap.put("employeeCode", obj[1]);
			employeeMap.put("employeeName", obj[2]);

			responseList.add(employeeMap);
		}

		return responseList;
	}

	@Override
	public List<Map<String, Object>> getProductionEmployeesDropdownSupplierChangeRequest(Long orgId, Long branch)
			throws ApplicationException {

		List<Object[]> employeeList = supplierChangeRequestRepo
				.getProductionEmployeesDropdownForSupplierChangeRequest(orgId, branch);

		if (employeeList == null || employeeList.isEmpty()) {
			throw new ApplicationException("No Production Employee Found");
		}

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : employeeList) {

			Map<String, Object> employeeMap = new HashMap<>();

			employeeMap.put("employeeId", obj[0]);
			employeeMap.put("employeeCode", obj[1]);
			employeeMap.put("employeeName", obj[2]);

			responseList.add(employeeMap);
		}

		return responseList;
	}

	@Override
	@Transactional
	public Map<String, Object> updateCreateCategoryMaster(CategoryMasterDTO categoryMasterDTO)
			throws ApplicationException {

		CategoryMasterVO categoryMasterVO = new CategoryMasterVO();

		String message;

		if (ObjectUtils.isEmpty(categoryMasterDTO.getId())) {

			categoryMasterVO.setCreatedBy(categoryMasterDTO.getCreatedBy());

			message = "Category Master Created Successfully";

		} else {

			categoryMasterVO = categoryMasterRepo.findById(categoryMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Category Master Details"));

			categoryMasterVO.setUpdatedBy(categoryMasterDTO.getCreatedBy());

			message = "Category Master Updated Successfully";
		}

		createUpdateCategoryMasterVO(categoryMasterDTO, categoryMasterVO);

		CategoryMasterVO savedVO = categoryMasterRepo.save(categoryMasterVO);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("categoryMasterVO", categoryMasterResponse(savedVO));

		return response;
	}

	private void createUpdateCategoryMasterVO(CategoryMasterDTO dto, CategoryMasterVO vo) throws ApplicationException {

		// Applicable For
		if (dto.getApplicableFor() != null) {

			ListOfValuesDetailsVO applicableFor = listOfValuesDetailsRepo.findById(dto.getApplicableFor())
					.orElseThrow(() -> new ApplicationException("Invalid Applicable For"));

			vo.setApplicableFor(applicableFor);
		}

		// Basic Details
		vo.setCategory(dto.getCategory());

		// Common Fields
		vo.setActive(dto.isActive());

		vo.setOrgId(dto.getOrgId());

		vo.setFinancialYear(dto.getFinancialYear());

		vo.setCancelRemarks(dto.getCancelRemarks());
	}

	private CategoryMasterResponseDTO categoryMasterResponse(CategoryMasterVO vo) {

		CategoryMasterResponseDTO response = new CategoryMasterResponseDTO();

		response.setId(vo.getId());

		// Applicable For
		if (vo.getApplicableFor() != null) {

			ListOfValuesDetailsResponseDTO applicableForResponse = new ListOfValuesDetailsResponseDTO();

			applicableForResponse.setId(vo.getApplicableFor().getId());

			applicableForResponse.setCode(vo.getApplicableFor().getValueCode());

			applicableForResponse.setDescription(vo.getApplicableFor().getValueDescription());

			response.setApplicableFor(applicableForResponse);
		}

		response.setCategory(vo.getCategory());

		response.setActive(vo.isActive() ? "Active" : "In-Active");

		response.setOrgId(vo.getOrgId());

		response.setCreatedBy(vo.getCreatedBy());

		response.setFinancialYear(vo.getFinancialYear());

		response.setCancelRemarks(vo.getCancelRemarks());

		return response;
	}

	@Override
	public CategoryMasterResponseDTO getCategoryMasterById(Long id) throws ApplicationException {

		CategoryMasterVO categoryMasterVO = categoryMasterRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Invalid Category Master Details"));

		return categoryMasterResponse(categoryMasterVO);
	}

	@Override
	public List<CategoryMasterResponseDTO> getCategoryMasterByOrgId(Long orgId) throws ApplicationException {

		List<CategoryMasterVO> categoryMasterList = categoryMasterRepo.getCategoryMasterByOrgId(orgId);

		if (categoryMasterList == null || categoryMasterList.isEmpty()) {

			throw new ApplicationException("No Category Master Details Found");
		}

		List<CategoryMasterResponseDTO> responseList = new ArrayList<>();

		for (CategoryMasterVO vo : categoryMasterList) {

			responseList.add(categoryMasterResponse(vo));
		}

		return responseList;
	}

//	cause Master

	@Override
	@Transactional
	public Map<String, Object> updateCreateCauseMaster(CauseMasterDTO causeMasterDTO) throws ApplicationException {

		CauseMasterVO causeMasterVO = new CauseMasterVO();

		String message;

		if (ObjectUtils.isEmpty(causeMasterDTO.getId())) {

			causeMasterVO.setCreatedBy(causeMasterDTO.getCreatedBy());

			message = "Cause Master Created Successfully";

		} else {

			causeMasterVO = causeMasterRepo.findById(causeMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Cause Master Details"));

			causeMasterVO.setUpdatedBy(causeMasterDTO.getCreatedBy());

			message = "Cause Master Updated Successfully";
		}

		createUpdateCauseMasterVO(causeMasterDTO, causeMasterVO);

		CauseMasterVO savedCauseMasterVO = causeMasterRepo.save(causeMasterVO);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);
		response.put("causeMasterVO", causeMasterResponse(savedCauseMasterVO));

		return response;
	}

	private void createUpdateCauseMasterVO(CauseMasterDTO causeMasterDTO, CauseMasterVO causeMasterVO)
			throws ApplicationException {

		if (causeMasterDTO.getDepartment() != null) {

			DepartmentVO departmentVO = departmentRepo.findById(causeMasterDTO.getDepartment())
					.orElseThrow(() -> new ApplicationException("Invalid Department Details"));

			causeMasterVO.setDepartment(departmentVO);
		}

		if (causeMasterDTO.getMaintenanceType() != null) {

			ListOfValuesDetailsVO maintenanceTypeVO = listOfValuesDetailsRepo
					.findById(causeMasterDTO.getMaintenanceType())
					.orElseThrow(() -> new ApplicationException("Invalid Maintenance Type Details"));

			causeMasterVO.setMaintenanceType(maintenanceTypeVO);
		}

		causeMasterVO.setCauseCode(causeMasterDTO.getCauseCode());
		causeMasterVO.setCause(causeMasterDTO.getCause());
		causeMasterVO.setActive(causeMasterDTO.isActive());
		causeMasterVO.setOrgId(causeMasterDTO.getOrgId());
		causeMasterVO.setFinancialYear(causeMasterDTO.getFinancialYear());
		causeMasterVO.setCancelRemarks(causeMasterDTO.getCancelRemarks());
	}

	private CauseMasterResponseDTO causeMasterResponse(CauseMasterVO causeMasterVO) {

		CauseMasterResponseDTO response = new CauseMasterResponseDTO();

		response.setId(causeMasterVO.getId());

		if (causeMasterVO.getDepartment() != null) {

			DepartmentResponseDTO departmentResponseDTO = new DepartmentResponseDTO();

			departmentResponseDTO.setId(causeMasterVO.getDepartment().getId());

			departmentResponseDTO.setDepartmentCode(causeMasterVO.getDepartment().getDepartmentCode());

			departmentResponseDTO.setDepartmentName(causeMasterVO.getDepartment().getDepartmentName());

			response.setDepartment(departmentResponseDTO);
		}

		if (causeMasterVO.getMaintenanceType() != null) {

			ListOfValuesDetailsResponseDTO maintenanceTypeResponseDTO = new ListOfValuesDetailsResponseDTO();

			maintenanceTypeResponseDTO.setId(causeMasterVO.getMaintenanceType().getId());

			maintenanceTypeResponseDTO.setCode(causeMasterVO.getMaintenanceType().getValueCode());

			maintenanceTypeResponseDTO.setDescription(causeMasterVO.getMaintenanceType().getValueDescription());

			response.setMaintenanceType(maintenanceTypeResponseDTO);
		}

		response.setCauseCode(causeMasterVO.getCauseCode());
		response.setCause(causeMasterVO.getCause());
		response.setActive(causeMasterVO.isActive());
		response.setOrgId(causeMasterVO.getOrgId());
		response.setCreatedBy(causeMasterVO.getCreatedBy());
		response.setFinancialYear(causeMasterVO.getFinancialYear());
		response.setCancelRemarks(causeMasterVO.getCancelRemarks());

		return response;
	}

	@Override
	public CauseMasterResponseDTO getCauseMasterById(Long id) throws ApplicationException {

		CauseMasterVO causeMasterVO = causeMasterRepo.findById(id).orElse(null);

		if (causeMasterVO == null) {
			throw new ApplicationException("Invalid Cause Master Details");
		}

		return causeMasterResponse(causeMasterVO);
	}

	@Override
	public List<CauseMasterResponseDTO> getCauseMasterByOrgId(Long orgId) throws ApplicationException {

		List<CauseMasterVO> causeMasterList = causeMasterRepo.getCauseMasterByOrgId(orgId);

		if (causeMasterList == null || causeMasterList.isEmpty()) {
			throw new ApplicationException("No Cause Master Details Found");
		}

		List<CauseMasterResponseDTO> responseList = new ArrayList<>();

		for (CauseMasterVO causeMasterVO : causeMasterList) {

			CauseMasterResponseDTO response = causeMasterResponse(causeMasterVO);

			responseList.add(response);
		}

		return responseList;
	}

//	pm check list master

	@Override
	@Transactional
	public Map<String, Object> updateCreatePMCheckListMaster(PMCheckListMasterDTO dto) throws ApplicationException {

		PMCheckListMasterVO pmCheckListMasterVO = new PMCheckListMasterVO();

		String message;

		String screenCode = "PMCLM";

		/*
		 * CREATE
		 */
		if (ObjectUtils.isEmpty(dto.getId())) {

			String docId = pmCheckListMasterRepo.getPMCheckListMasterDocId(dto.getOrgId(), dto.getFinancialYear(),
					screenCode);

			if (docId == null || docId.isEmpty()) {
				throw new ApplicationException("PM Check List Master DocId Not Found");
			}

			pmCheckListMasterVO.setDocId(docId);

			/*
			 * Update document number
			 */
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(dto.getOrgId(), dto.getFinancialYear(), screenCode);

			if (documentTypeMappingDetailsVO == null) {
				throw new ApplicationException("Document Type Mapping Details Not Found");
			}

			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			pmCheckListMasterVO.setCreatedBy(dto.getCreatedBy());

			pmCheckListMasterVO.setUpdatedBy(dto.getCreatedBy());

			message = "PM Check List Master Created Successfully";

		} else {

			/*
			 * UPDATE
			 */
			pmCheckListMasterVO = pmCheckListMasterRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Invalid PM Check List Master Details"));

			pmCheckListMasterVO.setUpdatedBy(dto.getCreatedBy());

			message = "PM Check List Master Updated Successfully";
		}

		/*
		 * Set Master Details
		 */
		createUpdatePMCheckListMasterVO(dto, pmCheckListMasterVO);

		/*
		 * Save Master
		 */
		PMCheckListMasterVO savedVO = pmCheckListMasterRepo.save(pmCheckListMasterVO);

		/*
		 * Response
		 */
		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("pmCheckListMasterVO", pmCheckListMasterResponse(savedVO));

		return response;
	}

	private void createUpdatePMCheckListMasterVO(PMCheckListMasterDTO dto, PMCheckListMasterVO pmCheckListMasterVO)
			throws ApplicationException {

		/*
		 * Branch
		 */
		if (dto.getBranch() != null) {

			BranchVO branchVO = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Invalid Branch Details"));

			pmCheckListMasterVO.setBranch(branchVO);
		}

		/*
		 * Department
		 */
		if (dto.getDepartment() != null) {

			DepartmentVO departmentVO = departmentRepo.findById(dto.getDepartment())
					.orElseThrow(() -> new ApplicationException("Invalid Department Details"));

			pmCheckListMasterVO.setDepartment(departmentVO);
		}

		/*
		 * PM Check List For
		 */

		/*
		 * Tool Category
		 */
		if (dto.getToolCategory() != null) {

			ToolCategoryDetailVO toolCategoryVO = toolCategoryDetailRepo.findById(dto.getToolCategory())
					.orElseThrow(() -> new ApplicationException("Invalid Tool Category Details"));

			pmCheckListMasterVO.setToolCategory(toolCategoryVO);
		}

		/*
		 * Prepared By
		 */
		if (dto.getPreparedBy() != null) {

			EmployeeMasterVO preparedByVO = employeeMasterRepo.findById(dto.getPreparedBy())
					.orElseThrow(() -> new ApplicationException("Invalid Prepared By Details"));

			pmCheckListMasterVO.setPreparedBy(preparedByVO);
		}

		/*
		 * Approved By
		 */
		if (dto.getApprovedBy() != null) {

			EmployeeMasterVO approvedByVO = employeeMasterRepo.findById(dto.getApprovedBy())
					.orElseThrow(() -> new ApplicationException("Invalid Approved By Details"));

			pmCheckListMasterVO.setApprovedBy(approvedByVO);
		}

		/*
		 * Normal Fields
		 */
		pmCheckListMasterVO.setPmCheckListNo(dto.getPmCheckListNo());

		if (dto.getActive() != null) {
			pmCheckListMasterVO.setActive(dto.getActive());
		}

		pmCheckListMasterVO.setPmCheckListFor(dto.getPmCheckListFor());

		pmCheckListMasterVO.setOrgId(dto.getOrgId());

		pmCheckListMasterVO.setFinancialYear(dto.getFinancialYear());

		pmCheckListMasterVO.setCancelRemarks(dto.getCancelRemarks());

		/*
		 * Child Details
		 */
		if (dto.getPmCheckListDetailsDTO() != null) {

			List<PMCheckListDetailsVO> detailsList = new ArrayList<>();

			for (PMCheckListDetailsDTO detailsDTO : dto.getPmCheckListDetailsDTO()) {

				PMCheckListDetailsVO detailsVO = new PMCheckListDetailsVO();

				/*
				 * Category
				 */
				if (detailsDTO.getCategory() != null) {

					ListOfValuesDetailsVO categoryVO = listOfValuesDetailsRepo.findById(detailsDTO.getCategory())
							.orElseThrow(() -> new ApplicationException("Invalid Category Details"));

					detailsVO.setCategory(categoryVO);
				}

				/*
				 * Activity
				 */
				if (detailsDTO.getActivity() != null) {

					ActivityMasterVO activityVO = activityMasterRepo.findById(detailsDTO.getActivity())
							.orElseThrow(() -> new ApplicationException("Invalid Activity Details"));

					detailsVO.setActivity(activityVO);
				}

				/*
				 * Child Normal Fields
				 */
				detailsVO.setCheckingPoints(detailsDTO.getCheckingPoints());

				detailsVO.setParameter(detailsDTO.getParameter());

				detailsVO.setSpecification(detailsDTO.getSpecification());

				detailsVO.setGeneralDevObs(detailsDTO.getGeneralDevObs());

				detailsVO.setRemediesRemarks(detailsDTO.getRemediesRemarks());

				detailsVO.setNoOfHrs(detailsDTO.getNoOfHrs());

				detailsVO.setFrequency(detailsDTO.getFrequency());

				/*
				 * Set Parent
				 */
				detailsVO.setPmCheckListMasterVO(pmCheckListMasterVO);

				detailsList.add(detailsVO);
			}

			/*
			 * Set Child List
			 */
			pmCheckListMasterVO.setPmCheckListDetailsVO(detailsList);
		}
	}

	private PMCheckListMasterResponseDTO pmCheckListMasterResponse(PMCheckListMasterVO vo) {

		PMCheckListMasterResponseDTO response = new PMCheckListMasterResponseDTO();

		response.setId(vo.getId());

		/*
		 * Branch
		 */
		if (vo.getBranch() != null) {

			BranchResponseDTO branchResponseDTO = new BranchResponseDTO();

			branchResponseDTO.setId(vo.getBranch().getId());

			branchResponseDTO.setBranchCode(vo.getBranch().getBranchCode());

			branchResponseDTO.setBranchName(vo.getBranch().getBranchName());

			response.setBranch(branchResponseDTO);
		}

		/*
		 * Department
		 */
		if (vo.getDepartment() != null) {

			DepartmentResponseDTO departmentResponseDTO = new DepartmentResponseDTO();

			departmentResponseDTO.setId(vo.getDepartment().getId());

			departmentResponseDTO.setDepartmentCode(vo.getDepartment().getDepartmentCode());

			departmentResponseDTO.setDepartmentName(vo.getDepartment().getDepartmentName());

			response.setDepartment(departmentResponseDTO);
		}

		/*
		 * PM Check List For
		 */

		/*
		 * Tool Category
		 */
		if (vo.getToolCategory() != null) {

			ToolCategoryDetailResponseDTO toolCategoryResponseDTO = new ToolCategoryDetailResponseDTO();

			toolCategoryResponseDTO.setId(vo.getToolCategory().getId());

			toolCategoryResponseDTO.setCategory(vo.getToolCategory().getCategory());

			response.setToolCategory(toolCategoryResponseDTO);
		}

		/*
		 * Prepared By
		 */
		if (vo.getPreparedBy() != null) {

			EmployeeMasterDetailsReponseDTO preparedByResponseDTO = new EmployeeMasterDetailsReponseDTO();

			preparedByResponseDTO.setId(vo.getPreparedBy().getId());

			preparedByResponseDTO.setEmployeeName(vo.getPreparedBy().getEmployeeName());

			response.setPreparedBy(preparedByResponseDTO);
		}

		/*
		 * Approved By
		 */
		if (vo.getApprovedBy() != null) {

			EmployeeMasterDetailsReponseDTO approvedByResponseDTO = new EmployeeMasterDetailsReponseDTO();

			approvedByResponseDTO.setId(vo.getApprovedBy().getId());

			approvedByResponseDTO.setEmployeeName(vo.getApprovedBy().getEmployeeName());

			response.setApprovedBy(approvedByResponseDTO);
		}

		/*
		 * Normal Fields
		 */

		response.setPmCheckListFor(vo.getPmCheckListFor());

		response.setPmCheckListNo(vo.getPmCheckListNo());

		response.setActive(vo.isActive());

		response.setOrgId(vo.getOrgId());

		response.setCreatedBy(vo.getCreatedBy());

		response.setFinancialYear(vo.getFinancialYear());

		response.setCancelRemarks(vo.getCancelRemarks());

		/*
		 * Child Details Response
		 */
		if (vo.getPmCheckListDetailsVO() != null) {

			List<PMCheckListDetailsResponseDTO> detailsResponseList = new ArrayList<>();

			for (PMCheckListDetailsVO detailsVO : vo.getPmCheckListDetailsVO()) {

				PMCheckListDetailsResponseDTO detailsResponse = new PMCheckListDetailsResponseDTO();

				/*
				 * Category
				 */
				if (detailsVO.getCategory() != null) {

					ListOfValuesDetailsResponseDTO categoryResponse = new ListOfValuesDetailsResponseDTO();

					categoryResponse.setId(detailsVO.getCategory().getId());

					categoryResponse.setCode(detailsVO.getCategory().getValueCode());

					categoryResponse.setDescription(detailsVO.getCategory().getValueDescription());

					detailsResponse.setCategory(categoryResponse);
				}

				/*
				 * Activity
				 */
				if (detailsVO.getActivity() != null) {

					ActivityResponseDTO activityResponse = new ActivityResponseDTO();

					activityResponse.setId(detailsVO.getActivity().getId());

					activityResponse.setActivity(detailsVO.getActivity().getActivity());
					detailsResponse.setActivity(activityResponse);
				}

				detailsResponse.setCheckingPoints(detailsVO.getCheckingPoints());

				detailsResponse.setParameter(detailsVO.getParameter());

				detailsResponse.setSpecification(detailsVO.getSpecification());

				detailsResponse.setGeneralDevObs(detailsVO.getGeneralDevObs());

				detailsResponse.setRemediesRemarks(detailsVO.getRemediesRemarks());

				detailsResponse.setNoOfHrs(detailsVO.getNoOfHrs());

				detailsResponse.setFrequency(detailsVO.getFrequency());

				detailsResponseList.add(detailsResponse);
			}

			response.setPmCheckListDetailsResponseDTO(detailsResponseList);
		}

		return response;
	}

	@Override
	public PMCheckListMasterResponseDTO getPMCheckListMasterById(Long id) throws ApplicationException {

		PMCheckListMasterVO pmCheckListMasterVO = pmCheckListMasterRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Invalid PM Check List Master Details"));

		return pmCheckListMasterResponse(pmCheckListMasterVO);
	}

	@Override
	public List<PMCheckListMasterResponseDTO> getPMCheckListMasterByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<PMCheckListMasterVO> pmCheckListMasterList = pmCheckListMasterRepo.getPMCheckListMasterByOrgId(orgId,
				branch);

		if (pmCheckListMasterList == null || pmCheckListMasterList.isEmpty()) {
			throw new ApplicationException("No PM Check List Master Found");
		}

		List<PMCheckListMasterResponseDTO> responseList = new ArrayList<>();

		for (PMCheckListMasterVO pmCheckListMasterVO : pmCheckListMasterList) {

			PMCheckListMasterResponseDTO response = pmCheckListMasterResponse(pmCheckListMasterVO);

			responseList.add(response);
		}

		return responseList;
	}

	@Override
	public List<Map<String, Object>> getToolMachineCategoryForPMCheckListMaster(Long orgId, String pmCheckListFor)
			throws ApplicationException {

		List<Object[]> toolCategoryList = pmCheckListMasterRepo.getToolMachineCategoryForPMCheckListMaster(orgId,
				pmCheckListFor);

		if (toolCategoryList == null || toolCategoryList.isEmpty()) {
			throw new ApplicationException("No Tool Machine Category Found");
		}

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : toolCategoryList) {

			Map<String, Object> toolCategoryMap = new HashMap<>();

			toolCategoryMap.put("id", obj[0]);
			toolCategoryMap.put("name", obj[1]);

			responseList.add(toolCategoryMap);
		}

		return responseList;
	}

	@Override
	public List<Map<String, Object>> getActivityForPMCheckListMaster(Long department, Long orgId)
			throws ApplicationException {

		List<Object[]> activityList = pmCheckListMasterRepo.getActivityForPMCheckListMaster(department, orgId);

		if (activityList == null || activityList.isEmpty()) {
			throw new ApplicationException("No Activity Found");
		}

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : activityList) {

			Map<String, Object> activityMap = new HashMap<>();

			activityMap.put("id", obj[0]);
			activityMap.put("name", obj[1]);

			responseList.add(activityMap);
		}

		return responseList;
	}

	@Override
	@Transactional
	public Map<String, Object> updateCreateMachineToolBreakdown(MachineToolBreakdownDTO machineToolBreakdownDTO,
			MultipartFile[] files, MultipartFile[] images) throws ApplicationException {

		MachineToolBreakdownVO machineToolBreakdownVO;
		String message;

		/*
		 * Update
		 */
		if (ObjectUtils.isNotEmpty(machineToolBreakdownDTO.getId())) {

			machineToolBreakdownVO = machineToolBreakdownRepo.findById(machineToolBreakdownDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Machine Tool Breakdown"));

			machineToolBreakdownVO.setUpdatedBy(machineToolBreakdownDTO.getCreatedBy());

			message = "Machine Tool Breakdown Updated Successfully";

		}

		/*
		 * Create
		 */
		else {

			machineToolBreakdownVO = new MachineToolBreakdownVO();

			String screenCode = "MTB";

			String docId = machineToolBreakdownRepo.getMachineToolBreakdownDocId(machineToolBreakdownDTO.getOrgId(),
					machineToolBreakdownDTO.getFinancialYear(), screenCode);

			if (StringUtils.isBlank(docId)) {

				throw new ApplicationException("Machine Tool Breakdown DocId Not Found");
			}

			machineToolBreakdownVO.setDocId(docId);

			DocumentTypeMappingDetailsVO mapping = documentTypeMappingDetailsRepo.findByOrgIdAndFinYearAndScreenCode(
					machineToolBreakdownDTO.getOrgId(), machineToolBreakdownDTO.getFinancialYear(), screenCode);

			if (mapping == null) {

				throw new ApplicationException("Document Type Mapping Details Not Found");
			}

			mapping.setLastNo(mapping.getLastNo() + 1);

			documentTypeMappingDetailsRepo.save(mapping);

			machineToolBreakdownVO.setCreatedBy(machineToolBreakdownDTO.getCreatedBy());

			machineToolBreakdownVO.setUpdatedBy(machineToolBreakdownDTO.getCreatedBy());

			message = "Machine Tool Breakdown Created Successfully";
		}

		/*
		 * Set Machine Tool Breakdown values
		 */
		createUpdateMachineToolBreakdownVO(machineToolBreakdownDTO, machineToolBreakdownVO);

		/*
		 * Save Main VO first
		 */
		machineToolBreakdownVO = machineToolBreakdownRepo.save(machineToolBreakdownVO);

		/*
		 * Save Header Image
		 */
		saveMachineToolBreakdownImage(images, machineToolBreakdownVO);

		/*
		 * Save Attachments
		 */
		saveMachineToolBreakdownAttachments(files, machineToolBreakdownVO);

		/*
		 * Response
		 */
		MachineToolBreakdownResponseDTO responseDTO = machineToolBreakdownResponse(machineToolBreakdownVO);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);
		response.put("machineToolBreakdownVO", responseDTO);

		return response;
	}

	private void createUpdateMachineToolBreakdownVO(MachineToolBreakdownDTO dto, MachineToolBreakdownVO vo)
			throws ApplicationException {

		/*
		 * Branch
		 */
		if (dto.getBranch() != null) {

			BranchVO branchVO = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			vo.setBranch(branchVO);
		}

		/*
		 * Department
		 */
		if (dto.getDepartment() != null) {

			DepartmentVO departmentVO = departmentRepo.findById(dto.getDepartment())
					.orElseThrow(() -> new ApplicationException("Department Not Found"));

			vo.setDepartment(departmentVO);
		}

		/*
		 * Select Machine Tool Instrument
		 */
		if (dto.getSelectMachineToolInst() != null) {

			ToolCategoryVO selectMachineToolInst = toolCategoryRepo.findById(dto.getSelectMachineToolInst())
					.orElseThrow(() -> new ApplicationException("Select Machine Tool Instrument Not Found"));

			vo.setSelectMachineToolInst(selectMachineToolInst);
		}

		/*
		 * PM Check List No
		 */
		if (dto.getPmCheckListNo() != null) {

			PMCheckListMasterVO pmCheckListNo = pmCheckListMasterRepo.findById(dto.getPmCheckListNo())
					.orElseThrow(() -> new ApplicationException("PM Check List Not Found"));

			vo.setPmCheckListNo(pmCheckListNo);
		}

		/*
		 * Operator Name
		 */
		if (dto.getOperatorName() != null) {

			EmployeeMasterVO operatorName = employeeMasterRepo.findById(dto.getOperatorName())
					.orElseThrow(() -> new ApplicationException("Operator Employee Not Found"));

			vo.setOperatorName(operatorName);
		}

		/*
		 * Maintenance Type
		 */
		if (dto.getMaintenanceType() != null) {

			ListOfValuesDetailsVO maintenanceType = listOfValuesDetailsRepo.findById(dto.getMaintenanceType())
					.orElseThrow(() -> new ApplicationException("Maintenance Type Not Found"));

			vo.setMaintenanceType(maintenanceType);
		}

		/*
		 * Nature Of Breakdown
		 */
		if (dto.getNatureOfBreakdown() != null) {

			ListOfValuesDetailsVO natureOfBreakdown = listOfValuesDetailsRepo.findById(dto.getNatureOfBreakdown())
					.orElseThrow(() -> new ApplicationException("Nature Of Breakdown Not Found"));

			vo.setNatureOfBreakdown(natureOfBreakdown);
		}

		/*
		 * Breakdown Type
		 */
		if (dto.getBreakdownType() != null) {

			ListOfValuesDetailsVO breakdownType = listOfValuesDetailsRepo.findById(dto.getBreakdownType())
					.orElseThrow(() -> new ApplicationException("Breakdown Type Not Found"));

			vo.setBreakdownType(breakdownType);
		}

		/*
		 * Normal Fields
		 */
		vo.setDocDate(dto.getId() == null ? vo.getDocDate() : vo.getDocDate());

		vo.setMachineToolIdInst(dto.getMachineToolIdInst());

		vo.setMachineName(dto.getMachineName());

		vo.setLocation(dto.getLocation());

		vo.setBreakdownTime(dto.getBreakdownTime());

		vo.setReportedTime(dto.getReportedTime());

		vo.setReportedDate(dto.getReportedDate());

		vo.setNatureOfProblem(dto.getNatureOfProblem());

		vo.setEstimatedTime(dto.getEstimatedTime());

		vo.setRemarks(dto.getRemarks());

		vo.setOrgId(dto.getOrgId());

		vo.setFinancialYear(dto.getFinancialYear());

		vo.setActive(dto.isActive());

		vo.setCancelRemarks(dto.getCancelRemarks());

		/*
		 * Image field from DTO
		 */
		if (dto.getImage() != null) {
			vo.setImage(dto.getImage());
		}
	}

	private void saveMachineToolBreakdownImage(MultipartFile[] images, MachineToolBreakdownVO machineToolBreakdownVO)
			throws ApplicationException {

		if (images == null || images.length == 0) {
			return;
		}

		try {

			Path machineToolBreakdownFolder = Paths.get(machineToolBreakdownUploadPath, "machine-tool-breakdown",
					machineToolBreakdownVO.getId().toString());

			createDirectory(machineToolBreakdownFolder);

			for (MultipartFile image : images) {

				if (image == null || image.isEmpty()) {
					continue;
				}

				String originalName = image.getOriginalFilename();

				if (originalName == null) {
					originalName = "image";
				}

				originalName = originalName.replaceAll("\\s+", "_");

				String extension = "";

				if (originalName.contains(".")) {

					extension = originalName.substring(originalName.lastIndexOf("."));

					originalName = originalName.substring(0, originalName.lastIndexOf("."));
				}

				String fileName = originalName + "_" + machineToolBreakdownVO.getId() + extension;

				Path filePath = machineToolBreakdownFolder.resolve(fileName);

				try (InputStream inputStream = image.getInputStream()) {

					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				}

				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/api/vendorComplaintEntry/viewFile/").toUriString();

				String relativePath = "machine-tool-breakdown/" + machineToolBreakdownVO.getId() + "/" + fileName;

				String publicUrl = baseUrl + relativePath;

				/*
				 * Save image URL/path in parent table
				 */
				machineToolBreakdownVO.setImage(publicUrl);

				machineToolBreakdownRepo.save(machineToolBreakdownVO);
			}

		} catch (IOException e) {

			throw new ApplicationException("Image Upload Failed : " + e.getMessage());
		}
	}

	private void saveMachineToolBreakdownAttachments(MultipartFile[] files,
			MachineToolBreakdownVO machineToolBreakdownVO) throws ApplicationException {

		if (files == null || files.length == 0) {
			return;
		}

		try {

			/*
			 * Create folder
			 */
			Path machineToolBreakdownFolder = Paths.get(machineToolBreakdownUploadPath, "machine-tool-breakdown",
					machineToolBreakdownVO.getId().toString());

			createDirectory(machineToolBreakdownFolder);

			List<MachineToolBreakdownAttachmentVO> attachmentList = new ArrayList<>();

			for (MultipartFile file : files) {

				if (file == null || file.isEmpty()) {
					continue;
				}

				/*
				 * Get original file name
				 */
				String originalName = file.getOriginalFilename();

				if (originalName == null) {
					originalName = "file";
				}

				originalName = originalName.replaceAll("\\s+", "_");

				String extension = "";

				if (originalName.contains(".")) {

					extension = originalName.substring(originalName.lastIndexOf("."));

					originalName = originalName.substring(0, originalName.lastIndexOf("."));
				}

				/*
				 * Create unique file name
				 */
				String fileName = originalName + "_" + machineToolBreakdownVO.getId() + extension;

				/*
				 * Actual physical file path
				 */
				Path filePath = machineToolBreakdownFolder.resolve(fileName);

				/*
				 * Save file to disk
				 */
				try (InputStream inputStream = file.getInputStream()) {

					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				}

				/*
				 * Create URL
				 */
				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/api/vendorComplaintEntry/machineToolBreakdown/viewFile/").toUriString();

				String relativePath = "machine-tool-breakdown/" + machineToolBreakdownVO.getId() + "/" + fileName;

				String publicUrl = baseUrl + relativePath;

				/*
				 * Save attachment details
				 */
				MachineToolBreakdownAttachmentVO attachment = new MachineToolBreakdownAttachmentVO();

				attachment.setMachineToolBreakdownVO(machineToolBreakdownVO);

				attachment.setName(file.getOriginalFilename());

				attachment.setFileName(fileName);

				attachment.setFilePath(publicUrl);

				attachment.setFileSize(file.getSize());

				attachment.setContentType(file.getContentType());

				attachment.setUploadOn(LocalDateTime.now());

				attachmentList.add(attachment);
			}

			/*
			 * Save attachment records
			 */
			if (!attachmentList.isEmpty()) {

				List<MachineToolBreakdownAttachmentVO> saved = machineToolBreakdownAttachementRepo
						.saveAll(attachmentList);

				machineToolBreakdownVO.setMachineToolBreakdownAttachmentVO(saved);
			}

		} catch (IOException e) {

			throw new ApplicationException("File Upload Failed : " + e.getMessage());
		}
	}

	private MachineToolBreakdownResponseDTO machineToolBreakdownResponse(MachineToolBreakdownVO vo) {

		MachineToolBreakdownResponseDTO response = new MachineToolBreakdownResponseDTO();

		response.setId(vo.getId());

		/*
		 * Branch Response
		 */
		if (vo.getBranch() != null) {

			BranchResponseDTO branchResponse = new BranchResponseDTO();

			branchResponse.setId(vo.getBranch().getId());

			branchResponse.setBranchCode(vo.getBranch().getBranchCode());

			branchResponse.setBranchName(vo.getBranch().getBranchName());

			response.setBranch(branchResponse);
		}

		/*
		 * Department Response
		 */
		if (vo.getDepartment() != null) {

			DepartmentResponseDTO departmentResponse = new DepartmentResponseDTO();

			departmentResponse.setId(vo.getDepartment().getId());

			departmentResponse.setDepartmentCode(vo.getDepartment().getDepartmentCode());

			departmentResponse.setDepartmentName(vo.getDepartment().getDepartmentName());

			response.setDepartment(departmentResponse);
		}

		/*
		 * Select Machine Tool Instrument
		 */
		if (vo.getSelectMachineToolInst() != null) {

			ToolCategoryResponse1DTO selectMachineToolInst = new ToolCategoryResponse1DTO();

			selectMachineToolInst.setId(vo.getSelectMachineToolInst().getId());

			selectMachineToolInst.setApplicableFor(vo.getSelectMachineToolInst().getApllicableFor());

			response.setSelectMachineToolInst(selectMachineToolInst);
		}

		/*
		 * PM Check List
		 */
		if (vo.getPmCheckListNo() != null) {

			PMCheckListResponseDTO pmCheckListResponse = new PMCheckListResponseDTO();

			pmCheckListResponse.setId(vo.getPmCheckListNo().getId());
			pmCheckListResponse.setPmCheckListNo(vo.getPmCheckListNo().getPmCheckListNo());

			/*
			 * Add your PM Check List fields here according to your PMCheckListResponseDTO
			 */

			response.setPmCheckListNo(pmCheckListResponse);
		}

		/*
		 * Operator Name
		 */
		if (vo.getOperatorName() != null) {

			EmployeeDropdownResponseDTO operatorName = new EmployeeDropdownResponseDTO();

			operatorName.setEmployeeId(vo.getOperatorName().getId());

			operatorName.setEmployeeName(vo.getOperatorName().getEmployeeName());

			response.setOperatorName(operatorName);
		}

		/*
		 * Maintenance Type
		 */
		if (vo.getMaintenanceType() != null) {

			ListOfValuesDetailsResponseDTO maintenanceType = new ListOfValuesDetailsResponseDTO();

			maintenanceType.setId(vo.getMaintenanceType().getId());

			maintenanceType.setCode(vo.getMaintenanceType().getValueCode());

			maintenanceType.setDescription(vo.getMaintenanceType().getValueDescription());

			response.setMaintenanceType(maintenanceType);
		}

		/*
		 * Nature Of Breakdown
		 */
		if (vo.getNatureOfBreakdown() != null) {

			ListOfValuesDetailsResponseDTO natureOfBreakdown = new ListOfValuesDetailsResponseDTO();

			natureOfBreakdown.setId(vo.getNatureOfBreakdown().getId());

			natureOfBreakdown.setCode(vo.getNatureOfBreakdown().getValueCode());

			natureOfBreakdown.setDescription(vo.getNatureOfBreakdown().getValueDescription());

			response.setNatureOfBreakdown(natureOfBreakdown);
		}

		/*
		 * Breakdown Type
		 */
		if (vo.getBreakdownType() != null) {

			ListOfValuesDetailsResponseDTO breakdownType = new ListOfValuesDetailsResponseDTO();

			breakdownType.setId(vo.getBreakdownType().getId());

			breakdownType.setCode(vo.getBreakdownType().getValueCode());

			breakdownType.setDescription(vo.getBreakdownType().getValueDescription());

			response.setBreakdownType(breakdownType);
		}

		/*
		 * Normal Fields
		 */
		response.setMachineToolIdInst(vo.getMachineToolIdInst());

		response.setMachineName(vo.getMachineName());

		response.setLocation(vo.getLocation());

		response.setBreakdownTime(vo.getBreakdownTime());

		response.setReportedTime(vo.getReportedTime());

		response.setReportedDate(vo.getReportedDate());

		response.setNatureOfProblem(vo.getNatureOfProblem());

		response.setEstimatedTime(vo.getEstimatedTime());

		response.setRemarks(vo.getRemarks());

		response.setActive(vo.isActive());

		response.setOrgId(vo.getOrgId());

		response.setCreatedBy(vo.getCreatedBy());

		response.setFinancialYear(vo.getFinancialYear());

		response.setImage(vo.getImage());

		/*
		 * Attachment Response
		 */
		List<MachineToolBreakdownAttchmentResponseDTO> attachmentList = new ArrayList<>();

		if (vo.getMachineToolBreakdownAttachmentVO() != null && !vo.getMachineToolBreakdownAttachmentVO().isEmpty()) {

			for (MachineToolBreakdownAttachmentVO attachmentVO : vo.getMachineToolBreakdownAttachmentVO()) {

				MachineToolBreakdownAttchmentResponseDTO attachmentDTO = new MachineToolBreakdownAttchmentResponseDTO();

				attachmentDTO.setId(attachmentVO.getId());

				attachmentDTO.setName(attachmentVO.getName());

				attachmentDTO.setFileName(attachmentVO.getFileName());

				attachmentDTO.setFilePath(attachmentVO.getFilePath());

				attachmentDTO.setFileSize(attachmentVO.getFileSize());

				attachmentDTO.setContentType(attachmentVO.getContentType());

				attachmentDTO.setUploadOn(attachmentVO.getUploadOn());

				attachmentList.add(attachmentDTO);
			}
		}

		response.setMachineToolBreakdownAttchmentResponseDTO(attachmentList);

		return response;
	}

	@Value("${machine.tool.breakdown.upload.path}")
	private String machineToolBreakdownUploadPath;

	@Override
	public ResponseEntity<byte[]> viewMachineToolBreakdownFile(HttpServletRequest request) throws IOException {

		return serveMachineToolBreakdownFile(request, "/api/vendorComplaintEntry/machineToolBreakdown/viewFile/",
				machineToolBreakdownUploadPath);
	}

	private ResponseEntity<byte[]> serveMachineToolBreakdownFile(HttpServletRequest request, String apiPrefix,
			String uploadBasePath) throws IOException {

		String uri = request.getRequestURI();

		String relativePath = uri.replace(apiPrefix, "");

		relativePath = URLDecoder.decode(relativePath, StandardCharsets.UTF_8);

		if (relativePath.startsWith("uploads/")) {
			relativePath = relativePath.substring("uploads/".length());
		}

		Path baseDir = Paths.get(uploadBasePath).toAbsolutePath().normalize();

		Path filePath = baseDir.resolve(relativePath).normalize();

		/*
		 * Prevent path traversal
		 */
		if (!filePath.startsWith(baseDir)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		/*
		 * File not found
		 */
		if (!Files.exists(filePath)) {
			return ResponseEntity.notFound().build();
		}

		String contentType = Files.probeContentType(filePath);

		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		byte[] data = Files.readAllBytes(filePath);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline").body(data);
	}

	private ResponseEntity<byte[]> serveFile1(HttpServletRequest request, String apiPrefix, String uploadBasePath)
			throws IOException {

		String uri = request.getRequestURI();

		String relativePath = uri.replace(apiPrefix, "");

		relativePath = URLDecoder.decode(relativePath, StandardCharsets.UTF_8);

		if (relativePath.startsWith("uploads/")) {

			relativePath = relativePath.substring("uploads/".length());
		}

		Path baseDir = Paths.get(uploadBasePath).toAbsolutePath().normalize();

		Path filePath = baseDir.resolve(relativePath).normalize();

		/*
		 * Prevent path traversal
		 */
		if (!filePath.startsWith(baseDir)) {

			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		/*
		 * File not found
		 */
		if (!Files.exists(filePath)) {

			return ResponseEntity.notFound().build();
		}

		String contentType = Files.probeContentType(filePath);

		if (contentType == null) {

			contentType = "application/octet-stream";
		}

		byte[] data = Files.readAllBytes(filePath);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline").body(data);
	}

	private void createDirectory1(Path path) throws IOException {

		if (!Files.exists(path)) {

			Files.createDirectories(path);
		}
	}

	@Override
	public MachineToolBreakdownResponseDTO getMachineToolBreakdownById(Long id) throws ApplicationException {

		MachineToolBreakdownVO machineToolBreakdownVO = machineToolBreakdownRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Machine Tool Breakdown Not Found"));

		return machineToolBreakdownResponse(machineToolBreakdownVO);
	}

	@Override
	public List<MachineToolBreakdownResponseDTO> getMachineToolBreakdownByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<MachineToolBreakdownVO> machineToolBreakdownList = machineToolBreakdownRepo.findByOrgIdAndBranchId(orgId,
				branch);

		List<MachineToolBreakdownResponseDTO> responseList = new ArrayList<>();

		for (MachineToolBreakdownVO machineToolBreakdownVO : machineToolBreakdownList) {

			MachineToolBreakdownResponseDTO response = machineToolBreakdownResponse(machineToolBreakdownVO);

			responseList.add(response);
		}

		return responseList;
	}

	@Override
	public String getMachineToolBreakdownDocId(Long orgId, String financialYear) throws ApplicationException {

		String screenCode = "MTB";

		String docId = machineToolBreakdownRepo.getMachineToolBreakdownDocId(orgId, financialYear, screenCode);

		if (StringUtils.isBlank(docId)) {

			throw new ApplicationException("Machine Tool Breakdown DocId Not Found");
		}

		return docId;
	}

	@Override
	public List<Map<String, Object>> getMachineToolForBreakdown(Long toolCategoryId, Long orgId, Long branch)
			throws ApplicationException {

		List<Object[]> machineToolList = machineToolBreakdownRepo.getMachineToolForBreakdown(toolCategoryId, orgId,
				branch);

		if (machineToolList == null || machineToolList.isEmpty()) {
			throw new ApplicationException("No Machine/Tool Found");
		}

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : machineToolList) {

			Map<String, Object> map = new HashMap<>();

			map.put("name", obj[0]);
			map.put("number", obj[1]);
			map.put("location", obj[2]);

			responseList.add(map);
		}

		return responseList;
	}

	@Override
	@Transactional
	public Map<String, Object> updateCreateMachineToolRectification(MachineToolRectificationDTO dto)
			throws ApplicationException {

		String screenCode = "MTR";

		MachineToolRectificationVO machineToolRectificationVO;
		String message;

		if (dto.getId() != null) {

			machineToolRectificationVO = machineToolRectificationRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Machine Tool Rectification Details"));

			machineToolRectificationVO.setUpdatedBy(dto.getCreatedBy());

			message = "Machine Tool Rectification Updated Successfully";

		} else {

			machineToolRectificationVO = new MachineToolRectificationVO();

			String docId = machineToolRectificationRepo.getMachineToolRectificationDocId(dto.getOrgId(),
					dto.getFinancialYear(), screenCode);

			if (StringUtils.isBlank(docId)) {
				throw new ApplicationException("Machine Tool Rectification DocId Not Found");
			}

			machineToolRectificationVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdScreenCode(dto.getOrgId(), screenCode);

			if (documentTypeMappingDetailsVO == null) {
				throw new ApplicationException("Document Type Mapping Details Not Found");
			}

			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			machineToolRectificationVO.setCreatedBy(dto.getCreatedBy());

			machineToolRectificationVO.setUpdatedBy(dto.getCreatedBy());

			message = "Machine Tool Rectification Created Successfully";
		}

		createUpdateMachineToolRectificationVO(machineToolRectificationVO, dto);

		machineToolRectificationVO = machineToolRectificationRepo.save(machineToolRectificationVO);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		responseObjectsMap.put(CommonConstant.STRING_MESSAGE, message);

		responseObjectsMap.put("machineToolRectificationVO",
				machineToolRectificationResponse(machineToolRectificationVO));

		return responseObjectsMap;
	}

	private void createUpdateMachineToolRectificationVO(MachineToolRectificationVO machineToolRectificationVO,
			MachineToolRectificationDTO dto) throws ApplicationException {

		if (dto.getBranch() != null) {

			BranchVO branchVO = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Invalid Branch Details"));

			machineToolRectificationVO.setBranch(branchVO);
		}

		if (dto.getDepartment() != null) {

			DepartmentVO departmentVO = departmentRepo.findById(dto.getDepartment())
					.orElseThrow(() -> new ApplicationException("Invalid Department Details"));

			machineToolRectificationVO.setDepartment(departmentVO);
		}

		if (dto.getAttendBy() != null) {

			EmployeeMasterVO employeeVO = employeeMasterRepo.findById(dto.getAttendBy())
					.orElseThrow(() -> new ApplicationException("Invalid Attend By Employee Details"));

			machineToolRectificationVO.setAttendBy(employeeVO);
		}

		if (dto.getCarriedOutBy() != null) {

			EmployeeMasterVO employeeVO = employeeMasterRepo.findById(dto.getCarriedOutBy())
					.orElseThrow(() -> new ApplicationException("Invalid Carried Out By Employee Details"));

			machineToolRectificationVO.setCarriedOutBy(employeeVO);
		}

		if (dto.getPreparedBy() != null) {

			EmployeeMasterVO employeeVO = employeeMasterRepo.findById(dto.getPreparedBy())
					.orElseThrow(() -> new ApplicationException("Invalid Prepared By Employee Details"));

			machineToolRectificationVO.setPreparedBy(employeeVO);
		}

		if (dto.getApprovedBy() != null) {

			EmployeeMasterVO employeeVO = employeeMasterRepo.findById(dto.getApprovedBy())
					.orElseThrow(() -> new ApplicationException("Invalid Approved By Employee Details"));

			machineToolRectificationVO.setApprovedBy(employeeVO);
		}

		machineToolRectificationVO.setBreakdownNo(dto.getBreakdownNo());
		machineToolRectificationVO.setBreakdownDate(dto.getBreakdownDate());
		machineToolRectificationVO.setTime(dto.getTime());
		machineToolRectificationVO.setMachineToolNo(dto.getMachineToolNo());
		machineToolRectificationVO.setRectificationTime(dto.getRectificationTime());
		machineToolRectificationVO.setDescription(dto.getDescription());
		machineToolRectificationVO.setCause(dto.getCause());
		machineToolRectificationVO.setMaintenanceType(dto.getMaintenanceType());
		machineToolRectificationVO.setActionTaken(dto.getActionTaken());
		machineToolRectificationVO.setNatureOfProblem(dto.getNatureOfProblem());
		machineToolRectificationVO.setTimeTakenForRectification(dto.getTimeTakenForRectification());
		machineToolRectificationVO.setLocation(dto.getLocation());
		machineToolRectificationVO.setSparesUsed(dto.getSparesUsed());
		machineToolRectificationVO.setRemarks(dto.getRemarks());
		machineToolRectificationVO.setOrgId(dto.getOrgId());
		machineToolRectificationVO.setFinancialYear(dto.getFinancialYear());
		machineToolRectificationVO.setActive(dto.isActive());
		machineToolRectificationVO.setCancelRemarks(dto.getCancelRemarks());
	}

	private MachineToolRectificationResponseDTO machineToolRectificationResponse(MachineToolRectificationVO vo) {

		MachineToolRectificationResponseDTO response = new MachineToolRectificationResponseDTO();

		response.setId(vo.getId());

		if (vo.getBranch() != null) {

			BranchResponseDTO branchResponseDTO = new BranchResponseDTO();

			branchResponseDTO.setId(vo.getBranch().getId());
			branchResponseDTO.setBranchCode(vo.getBranch().getBranchCode());
			branchResponseDTO.setBranchName(vo.getBranch().getBranchName());

			response.setBranch(branchResponseDTO);
		}

		if (vo.getDepartment() != null) {

			DepartmentResponseDTO departmentResponseDTO = new DepartmentResponseDTO();

			departmentResponseDTO.setId(vo.getDepartment().getId());
			departmentResponseDTO.setDepartmentCode(vo.getDepartment().getDepartmentCode());
			departmentResponseDTO.setDepartmentName(vo.getDepartment().getDepartmentName());

			response.setDepartment(departmentResponseDTO);
		}

		if (vo.getAttendBy() != null) {

			EmployeeMasterDetailsReponseDTO employeeResponse = new EmployeeMasterDetailsReponseDTO();

			employeeResponse.setId(vo.getAttendBy().getId());
			employeeResponse.setEmployeeName(vo.getAttendBy().getEmployeeName());

			response.setAttendBy(employeeResponse);
		}

		if (vo.getCarriedOutBy() != null) {

			EmployeeMasterDetailsReponseDTO employeeResponse = new EmployeeMasterDetailsReponseDTO();

			employeeResponse.setId(vo.getCarriedOutBy().getId());
			employeeResponse.setEmployeeName(vo.getCarriedOutBy().getEmployeeName());

			response.setCarriedOutBy(employeeResponse);
		}

		if (vo.getPreparedBy() != null) {

			EmployeeMasterDetailsReponseDTO employeeResponse = new EmployeeMasterDetailsReponseDTO();

			employeeResponse.setId(vo.getPreparedBy().getId());
			employeeResponse.setEmployeeName(vo.getPreparedBy().getEmployeeName());

			response.setPreparedBy(employeeResponse);
		}

		if (vo.getApprovedBy() != null) {

			EmployeeMasterDetailsReponseDTO employeeResponse = new EmployeeMasterDetailsReponseDTO();

			employeeResponse.setId(vo.getApprovedBy().getId());

			employeeResponse.setEmployeeName(vo.getApprovedBy().getEmployeeName());

			response.setApprovedBy(employeeResponse);
		}

		response.setBreakdownNo(vo.getBreakdownNo());
		response.setBreakdownDate(vo.getBreakdownDate());
		response.setTime(vo.getTime());
		response.setMachineToolNo(vo.getMachineToolNo());
		response.setRectificationTime(vo.getRectificationTime());
		response.setDescription(vo.getDescription());
		response.setCause(vo.getCause());
		response.setMaintenanceType(vo.getMaintenanceType());
		response.setActionTaken(vo.getActionTaken());
		response.setNatureOfProblem(vo.getNatureOfProblem());
		response.setTimeTakenForRectification(vo.getTimeTakenForRectification());
		response.setLocation(vo.getLocation());
		response.setSparesUsed(vo.getSparesUsed());
		response.setRemarks(vo.getRemarks());
		response.setOrgId(vo.getOrgId());
		response.setFinancialYear(vo.getFinancialYear());
		response.setActive(vo.getActive());
		response.setCancelRemarks(vo.getCancelRemarks());
		response.setCreatedBy(vo.getCreatedBy());

		return response;
	}

	@Override
	public String getMachineToolRectificationDocId(Long orgId, String financialYear) throws ApplicationException {

		String screenCode = "MTR";

		String docId = machineToolRectificationRepo.getMachineToolRectificationDocId(orgId, financialYear, screenCode);

		if (StringUtils.isBlank(docId)) {
			throw new ApplicationException("Machine Tool Rectification DocId Not Found");
		}

		return docId;
	}

	@Override
	@Transactional(readOnly = true)
	public MachineToolRectificationResponseDTO getMachineToolRectificationById(Long id) throws ApplicationException {

		MachineToolRectificationVO machineToolRectificationVO = machineToolRectificationRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Invalid Machine Tool Rectification Details"));

		return machineToolRectificationResponse(machineToolRectificationVO);
	}

	@Override
	@Transactional
	public List<MachineToolRectificationResponseDTO> getMachineToolRectificationByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<MachineToolRectificationVO> machineToolRectificationList = machineToolRectificationRepo
				.getMachineToolRectificationByOrgId(orgId, branch);

		if (machineToolRectificationList == null || machineToolRectificationList.isEmpty()) {

			throw new ApplicationException("No Machine Tool Rectification Details Found");
		}

		List<MachineToolRectificationResponseDTO> responseList = new ArrayList<>();

		for (MachineToolRectificationVO machineToolRectificationVO : machineToolRectificationList) {

			MachineToolRectificationResponseDTO response = machineToolRectificationResponse(machineToolRectificationVO);

			responseList.add(response);
		}

		return responseList;
	}

	@Override
	public List<Map<String, Object>> getBreakdownDetailsForRectification(Long orgId, Long branch)
			throws ApplicationException {

		List<Object[]> breakdownList = machineToolRectificationRepo.getBreakdownDetailsForRectification(orgId, branch);

		if (breakdownList == null || breakdownList.isEmpty()) {
			throw new ApplicationException("No Breakdown Details Found");
		}

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : breakdownList) {

			Map<String, Object> breakdownMap = new HashMap<>();

			breakdownMap.put("id", obj[0]);
			breakdownMap.put("breakdownNo", obj[1]);
			breakdownMap.put("breakdownDate", obj[2]);
			breakdownMap.put("time", obj[3]);
			breakdownMap.put("machineToolNo", obj[4]);
			breakdownMap.put("description", obj[5]);
			breakdownMap.put("maintenanceType", obj[6]);
			breakdownMap.put("natureOfProblem", obj[7]);
			breakdownMap.put("timeTakenForRectification", obj[8]);
			breakdownMap.put("location", obj[9]);

			responseList.add(breakdownMap);
		}

		return responseList;
	}

	@Override
	public List<Map<String, Object>> getPrepareByForMachineToolRectification(Long orgId, Long branch, Long department)
			throws ApplicationException {

		List<Object[]> employeeList = machineToolRectificationRepo.getPrepareByForMachineToolRectification(orgId,
				branch, department);

		if (employeeList == null || employeeList.isEmpty()) {
			throw new ApplicationException("No Prepare By Employee Found");
		}

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : employeeList) {

			Map<String, Object> employeeMap = new HashMap<>();

			employeeMap.put("id", obj[0]);
			employeeMap.put("employeeId", obj[1]);
			employeeMap.put("name", obj[2]);

			responseList.add(employeeMap);
		}

		return responseList;
	}

//	Authorization for breakdown

	@Override
	@Transactional
	public Map<String, Object> updateCreateAuthorizationForBreakdown(AuthorizationForBreakdownDTO dto)
			throws ApplicationException {

		String screenCode = "AUFBR";

		AuthorizationForBreakdownVO authorizationForBreakdownVO;
		String message;

		if (dto.getId() != null) {

			authorizationForBreakdownVO = authorizationForBreakdownRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Authorization For Breakdown Details"));

			authorizationForBreakdownVO.setUpdatedBy(dto.getCreatedBy());

			message = "Authorization For Breakdown Updated Successfully";

		} else {

			authorizationForBreakdownVO = new AuthorizationForBreakdownVO();

			String docId = authorizationForBreakdownRepo.getAuthorizationForBreakdownDocId(dto.getOrgId(),
					dto.getFinancialYear(), screenCode);

			if (StringUtils.isBlank(docId)) {
				throw new ApplicationException("Authorization For Breakdown DocId Not Found");
			}

			authorizationForBreakdownVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdScreenCode(dto.getOrgId(), screenCode);

			if (documentTypeMappingDetailsVO == null) {
				throw new ApplicationException("Document Type Mapping Details Not Found");
			}

			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			authorizationForBreakdownVO.setCreatedBy(dto.getCreatedBy());

			authorizationForBreakdownVO.setUpdatedBy(dto.getCreatedBy());

			message = "Authorization For Breakdown Created Successfully";
		}

		createUpdateAuthorizationForBreakdownVO(authorizationForBreakdownVO, dto);

		authorizationForBreakdownVO = authorizationForBreakdownRepo.save(authorizationForBreakdownVO);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		responseObjectsMap.put(CommonConstant.STRING_MESSAGE, message);

		responseObjectsMap.put("docId", authorizationForBreakdownVO.getDocId());

		responseObjectsMap.put("docDate", authorizationForBreakdownVO.getDocDate());

		responseObjectsMap.put("authorizationForBreakdownVO",
				authorizationForBreakdownResponse(authorizationForBreakdownVO));

		return responseObjectsMap;
	}

	private void createUpdateAuthorizationForBreakdownVO(AuthorizationForBreakdownVO authorizationForBreakdownVO,
			AuthorizationForBreakdownDTO dto) throws ApplicationException {

		if (dto.getBranch() != null) {

			BranchVO branchVO = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Invalid Branch Details"));

			authorizationForBreakdownVO.setBranch(branchVO);
		}

		if (dto.getDepartment() != null) {

			DepartmentVO departmentVO = departmentRepo.findById(dto.getDepartment())
					.orElseThrow(() -> new ApplicationException("Invalid Department Details"));

			authorizationForBreakdownVO.setDepartment(departmentVO);
		}

		if (dto.getAuthorizedBy() != null) {

			EmployeeMasterVO employeeVO = employeeMasterRepo.findById(dto.getAuthorizedBy())
					.orElseThrow(() -> new ApplicationException("Invalid Authorized By Employee Details"));

			authorizationForBreakdownVO.setAuthorizedBy(employeeVO);
		}

		authorizationForBreakdownVO.setRectificationNo(dto.getRectificationNo());

		authorizationForBreakdownVO.setRectificationDate(dto.getRectificationDate());

		authorizationForBreakdownVO.setBreakdownNo(dto.getBreakdownNo());

		authorizationForBreakdownVO.setBreakdownDate(dto.getBreakdownDate());

		authorizationForBreakdownVO.setWorking(dto.getWorking());

		authorizationForBreakdownVO.setProblem(dto.getProblem());

		authorizationForBreakdownVO.setSolution(dto.getSolution());

		authorizationForBreakdownVO.setMachineNo(dto.getMachineNo());

		authorizationForBreakdownVO.setRectifiedTime(dto.getRectifiedTime());

		authorizationForBreakdownVO.setReasonIfNo(dto.getReasonIfNo());

		authorizationForBreakdownVO.setOrgId(dto.getOrgId());

		authorizationForBreakdownVO.setFinancialYear(dto.getFinancialYear());

		authorizationForBreakdownVO.setActive(dto.isActive());

		authorizationForBreakdownVO.setCancelRemarks(dto.getCancelRemarks());
	}

	private AuthorizationForBreakdownResponseDTO authorizationForBreakdownResponse(AuthorizationForBreakdownVO vo) {

		AuthorizationForBreakdownResponseDTO response = new AuthorizationForBreakdownResponseDTO();

		response.setId(vo.getId());

		if (vo.getBranch() != null) {

			BranchResponseDTO branchResponseDTO = new BranchResponseDTO();

			branchResponseDTO.setId(vo.getBranch().getId());

			branchResponseDTO.setBranchCode(vo.getBranch().getBranchCode());

			branchResponseDTO.setBranchName(vo.getBranch().getBranchName());

			response.setBranch(branchResponseDTO);
		}

		if (vo.getDepartment() != null) {

			DepartmentResponseDTO departmentResponseDTO = new DepartmentResponseDTO();

			departmentResponseDTO.setId(vo.getDepartment().getId());

			departmentResponseDTO.setDepartmentCode(vo.getDepartment().getDepartmentCode());

			departmentResponseDTO.setDepartmentName(vo.getDepartment().getDepartmentName());

			response.setDepartment(departmentResponseDTO);
		}

		if (vo.getAuthorizedBy() != null) {

			EmployeeResponseDTO employeeResponse = new EmployeeResponseDTO();

			employeeResponse.setId(vo.getAuthorizedBy().getId());
			employeeResponse.setEmployeeName(vo.getAuthorizedBy().getEmployeeName());

			response.setAuthorizedBy(employeeResponse);
		}

		response.setRectificationNo(vo.getRectificationNo());

		response.setRectificationDate(vo.getRectificationDate());

		response.setBreakdownNo(vo.getBreakdownNo());

		response.setBreakdownDate(vo.getBreakdownDate());

		response.setWorking(vo.getWorking());

		response.setProblem(vo.getProblem());

		response.setSolution(vo.getSolution());

		response.setMachineNo(vo.getMachineNo());

		response.setRectifiedTime(vo.getRectifiedTime());

		response.setReasonIfNo(vo.getReasonIfNo());

		response.setOrgId(vo.getOrgId());

		response.setFinancialYear(vo.getFinancialYear());

		response.setActive(vo.getActive());

		response.setCancelRemarks(vo.getCancelRemarks());

		response.setCreatedBy(vo.getCreatedBy());

		return response;
	}

	@Override
	public String getAuthorizationForBreakdownDocId(Long orgId, String financialYear) throws ApplicationException {

		String screenCode = "AUFBR";

		String docId = authorizationForBreakdownRepo.getAuthorizationForBreakdownDocId(orgId, financialYear,
				screenCode);

		if (StringUtils.isBlank(docId)) {
			throw new ApplicationException("Authorization For Breakdown DocId Not Found");
		}

		return docId;
	}

	@Override
	public AuthorizationForBreakdownResponseDTO getAuthorizationForBreakdownById(Long id) throws ApplicationException {

		AuthorizationForBreakdownVO authorizationForBreakdownVO = authorizationForBreakdownRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Invalid Authorization For Breakdown Details"));

		return authorizationForBreakdownResponse(authorizationForBreakdownVO);
	}

	@Override
	public List<AuthorizationForBreakdownResponseDTO> getAuthorizationForBreakdownByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<AuthorizationForBreakdownVO> authorizationForBreakdownList = authorizationForBreakdownRepo
				.findByOrgIdAndBranchId(orgId, branch);

		List<AuthorizationForBreakdownResponseDTO> responseList = new ArrayList<>();

		for (AuthorizationForBreakdownVO authorizationForBreakdownVO : authorizationForBreakdownList) {

			responseList.add(authorizationForBreakdownResponse(authorizationForBreakdownVO));
		}

		return responseList;
	}

	@Override
	public List<Map<String, Object>> getMachineToolRectificationDetailsForAuthorizationBreakdown(Long branch,
			Long orgId) throws ApplicationException {

		List<Object[]> machineToolRectificationList = authorizationForBreakdownRepo
				.getMachineToolRectificationDetails(branch, orgId);

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] row : machineToolRectificationList) {

			Map<String, Object> response = new HashMap<>();

			response.put("docId", row[0]);
			response.put("docDate", row[1]);
			response.put("breakdownNo", row[2]);
			response.put("breakdownDate", row[3]);
			response.put("natureOfProblem", row[4]);
			response.put("actionTaken", row[5]);
			response.put("rectificationTime", row[6]);
			response.put("machineToolNo", row[7]);

			responseList.add(response);
		}

		return responseList;
	}

	@Override
	public String getPMCheckListMasterDocId(Long orgId, String financialYear) throws ApplicationException {

		String screenCode = "PMCLM";

		String docId = pmCheckListMasterRepo.getPMCheckListMasterDocId(orgId, financialYear, screenCode);

		if (StringUtils.isBlank(docId)) {
			throw new ApplicationException("PM Check List Master DocId Not Found");
		}

		return docId;
	}

//	MachineToolsScrapNote 

	@Override
	@Transactional
	public Map<String, Object> updateCreateMachineToolsScrapNote(MachineToolsScrapNoteDTO machineToolsScrapNoteDTO,
			MultipartFile[] files) throws ApplicationException {

		MachineToolsScrapNoteVO machineToolsScrapNoteVO;
		String message;

		/*
		 * Update
		 */
		if (ObjectUtils.isNotEmpty(machineToolsScrapNoteDTO.getId())) {

			machineToolsScrapNoteVO = machineToolsScrapNoteRepo.findById(machineToolsScrapNoteDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Machine Tools Scrap Note"));

			machineToolsScrapNoteVO.setUpdatedBy(machineToolsScrapNoteDTO.getCreatedBy());

			message = "Machine Tools Scrap Note Updated Successfully";
		}

		/*
		 * Create
		 */
		else {

			machineToolsScrapNoteVO = new MachineToolsScrapNoteVO();

			String screenCode = "MTSN";

			String docId = machineToolsScrapNoteRepo.getMachineToolsScrapNoteDocId(machineToolsScrapNoteDTO.getOrgId(),
					machineToolsScrapNoteDTO.getFinancialYear(), screenCode);

			if (StringUtils.isBlank(docId)) {

				throw new ApplicationException("Machine Tools Scrap Note DocId Not Found");
			}

			machineToolsScrapNoteVO.setDocId(docId);

			DocumentTypeMappingDetailsVO mapping = documentTypeMappingDetailsRepo.findByOrgIdAndFinYearAndScreenCode(
					machineToolsScrapNoteDTO.getOrgId(), machineToolsScrapNoteDTO.getFinancialYear(), screenCode);

			if (mapping == null) {

				throw new ApplicationException("Document Type Mapping Details Not Found");
			}

			mapping.setLastNo(mapping.getLastNo() + 1);

			documentTypeMappingDetailsRepo.save(mapping);

			machineToolsScrapNoteVO.setCreatedBy(machineToolsScrapNoteDTO.getCreatedBy());

			machineToolsScrapNoteVO.setUpdatedBy(machineToolsScrapNoteDTO.getCreatedBy());

			message = "Machine Tools Scrap Note Created Successfully";
		}

		/*
		 * Set Machine Tools Scrap Note values
		 */
		createUpdateMachineToolsScrapNoteVO(machineToolsScrapNoteDTO, machineToolsScrapNoteVO);

		/*
		 * Save Main VO first
		 */
		machineToolsScrapNoteVO = machineToolsScrapNoteRepo.save(machineToolsScrapNoteVO);

		/*
		 * Save Details
		 */
		saveMachineToolsScrapNoteDetails(machineToolsScrapNoteDTO.getMachineToolsScrapNoteDetailsDTO(),
				machineToolsScrapNoteVO);

		/*
		 * Save Attachments
		 */
		saveMachineToolsScrapNoteAttachments(files, machineToolsScrapNoteVO);

		/*
		 * Response
		 */
		MachineToolsScrapNoteResponseDTO responseDTO = machineToolsScrapNoteResponse(machineToolsScrapNoteVO);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("machineToolsScrapNoteVO", responseDTO);

		return response;
	}

	/*
	 * ============================================================ SET MACHINE
	 * TOOLS SCRAP NOTE VALUES
	 * ============================================================
	 */

	private void createUpdateMachineToolsScrapNoteVO(MachineToolsScrapNoteDTO dto, MachineToolsScrapNoteVO vo)
			throws ApplicationException {

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

			ListOfValuesDetailsVO belongsTo = listOfValuesDetailsRepo.findById(dto.getBelongsTo())
					.orElseThrow(() -> new ApplicationException("Belongs To Not Found"));

			vo.setBelongsTo(belongsTo);
		}

		/*
		 * Department
		 */
		if (dto.getDepartement() != null) {

			DepartmentVO departmentVO = departmentRepo.findById(dto.getDepartement())
					.orElseThrow(() -> new ApplicationException("Department Not Found"));

			vo.setDepartement(departmentVO);
		}

		/*
		 * From Location
		 */
		if (dto.getFromLocation() != null) {

			LocationVO fromLocation = locationRepo.findById(dto.getFromLocation())
					.orElseThrow(() -> new ApplicationException("From Location Not Found"));

			vo.setFromLocation(fromLocation);
		}

		/*
		 * To Location
		 */
		if (dto.getToLocation() != null) {

			LocationVO toLocation = locationRepo.findById(dto.getToLocation())
					.orElseThrow(() -> new ApplicationException("To Location Not Found"));

			vo.setToLocation(toLocation);
		}

		/*
		 * Prepared By
		 */
		if (dto.getPreparedBy() != null) {

			EmployeeMasterVO preparedBy = employeeMasterRepo.findById(dto.getPreparedBy())
					.orElseThrow(() -> new ApplicationException("Prepared By Employee Not Found"));

			vo.setPreparedBy(preparedBy);
		}

		/*
		 * Authorized By
		 */
		if (dto.getAuthorizedBy() != null) {

			EmployeeMasterVO authorizedBy = employeeMasterRepo.findById(dto.getAuthorizedBy())
					.orElseThrow(() -> new ApplicationException("Authorized By Employee Not Found"));

			vo.setAuthorizedBy(authorizedBy);
		}

		/*
		 * Normal Fields
		 */
		vo.setTime(dto.getTime());

		vo.setProductionApproval(dto.getProductionApproval());

		vo.setQualityApproval(dto.getQualityApproval());

		vo.setStoreApproval(dto.getStoreApproval());

		vo.setNarration(dto.getNarration());

		vo.setOrgId(dto.getOrgId());

		vo.setFinancialYear(dto.getFinancialYear());

		vo.setActive(dto.isActive());

		vo.setCancelRemarks(dto.getCancelRemarks());
	}

	/*
	 * ============================================================ SAVE DETAILS
	 * ============================================================
	 */

	private void saveMachineToolsScrapNoteDetails(List<MachineToolsScrapNoteDetailsDTO> detailsDTOList,
			MachineToolsScrapNoteVO machineToolsScrapNoteVO) throws ApplicationException {

		if (detailsDTOList == null || detailsDTOList.isEmpty()) {

			return;
		}

		List<MachineToolsScrapNoteDetailsVO> detailsList = new ArrayList<>();

		for (MachineToolsScrapNoteDetailsDTO dto : detailsDTOList) {

			MachineToolsScrapNoteDetailsVO detailsVO = new MachineToolsScrapNoteDetailsVO();

			/*
			 * Item
			 */
			if (dto.getItem() != null) {

				ItemMasterVO itemVO = itemRepo.findById(dto.getItem())
						.orElseThrow(() -> new ApplicationException("Item Not Found"));

				detailsVO.setItem(itemVO);
			}

			/*
			 * Normal Fields
			 */
			detailsVO.setStock(dto.getStock());

			detailsVO.setQuantity(dto.getQuantity());

			detailsVO.setRate(dto.getRate());

			// Quantity * Rate = Value
			if (detailsVO.getQuantity() != null && detailsVO.getRate() != null) {

				BigDecimal value = detailsVO.getQuantity().multiply(detailsVO.getRate());

				detailsVO.setValue(value);

			} else {

				detailsVO.setValue(BigDecimal.ZERO);
			}

			/*
			 * Parent
			 */
			detailsVO.setMachineToolsScrapNoteVO(machineToolsScrapNoteVO);

			detailsList.add(detailsVO);
		}

		/*
		 * Save Details
		 */
		machineToolsScrapNoteDetailsRepo.saveAll(detailsList);

		machineToolsScrapNoteVO.setMachineToolsScrapNoteDetailsVO(detailsList);
	}

	/*
	 * ============================================================ SAVE CHILD
	 * ATTACHMENTS ============================================================
	 */

	@Value("${machine.tools.scrap.note.upload.path}")
	private String machineToolsScrapNoteUploadPath;

	private void saveMachineToolsScrapNoteAttachments(MultipartFile[] files,
			MachineToolsScrapNoteVO machineToolsScrapNoteVO) throws ApplicationException {

		if (files == null || files.length == 0) {
			return;
		}

		try {

			/*
			 * Create folder
			 */
			Path machineToolsScrapNoteFolder = Paths.get(machineToolsScrapNoteUploadPath, "machine-tools-scrap-note",
					machineToolsScrapNoteVO.getId().toString());

			createDirectory(machineToolsScrapNoteFolder);

			List<MachineToolScrapNoteAttachmentVO> attachmentList = new ArrayList<>();

			for (MultipartFile file : files) {

				if (file == null || file.isEmpty()) {
					continue;
				}

				/*
				 * Get original file name
				 */
				String originalName = file.getOriginalFilename();

				if (originalName == null) {
					originalName = "file";
				}

				originalName = originalName.replaceAll("\\s+", "_");

				String extension = "";

				if (originalName.contains(".")) {

					extension = originalName.substring(originalName.lastIndexOf("."));

					originalName = originalName.substring(0, originalName.lastIndexOf("."));
				}

				/*
				 * Create unique file name
				 */
				String fileName = originalName + "_" + machineToolsScrapNoteVO.getId() + extension;

				/*
				 * Actual physical file path
				 */
				Path filePath = machineToolsScrapNoteFolder.resolve(fileName);

				/*
				 * Save file to disk
				 */
				try (InputStream inputStream = file.getInputStream()) {

					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				}

				/*
				 * Create URL
				 */
				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/api/vendorComplaintEntry/machineToolsScrapNote/viewFile/").toUriString();

				String relativePath = "machine-tools-scrap-note/" + machineToolsScrapNoteVO.getId() + "/" + fileName;

				String publicUrl = baseUrl + relativePath;

				/*
				 * Save Attachment Details
				 */
				MachineToolScrapNoteAttachmentVO attachment = new MachineToolScrapNoteAttachmentVO();

				attachment.setMachineToolsScrapNoteVO(machineToolsScrapNoteVO);

				attachment.setName(file.getOriginalFilename());

				attachment.setFileName(fileName);

				attachment.setFilePath(publicUrl);

				attachment.setFileSize(file.getSize());

				attachment.setContentType(file.getContentType());

				attachment.setUploadOn(LocalDateTime.now());

				attachmentList.add(attachment);
			}

			/*
			 * Save Attachment Records
			 */
			if (!attachmentList.isEmpty()) {

				List<MachineToolScrapNoteAttachmentVO> saved = machineToolScrapNoteAttachmentRepo
						.saveAll(attachmentList);

				machineToolsScrapNoteVO.setMachineToolScrapNoteAttachmentVO(saved);
			}

		} catch (IOException e) {

			throw new ApplicationException("File Upload Failed : " + e.getMessage());
		}
	}

	/*
	 * ============================================================ RESPONSE
	 * ============================================================
	 */

	private MachineToolsScrapNoteResponseDTO machineToolsScrapNoteResponse(MachineToolsScrapNoteVO vo) {

		MachineToolsScrapNoteResponseDTO response = new MachineToolsScrapNoteResponseDTO();

		response.setId(vo.getId());

		// Branch Response
		if (vo.getBranch() != null) {
			BranchResponseDTO branchResponse = new BranchResponseDTO();

			branchResponse.setId(vo.getBranch().getId());
			branchResponse.setBranchCode(vo.getBranch().getBranchCode());
			branchResponse.setBranchName(vo.getBranch().getBranchName());

			response.setBranch(branchResponse);
		}

		// Belongs To Response
		if (vo.getBelongsTo() != null) {
			ListOfValuesDetailsResponseDTO belongsTo = new ListOfValuesDetailsResponseDTO();

			belongsTo.setId(vo.getBelongsTo().getId());
			belongsTo.setCode(vo.getBelongsTo().getValueCode());
			belongsTo.setDescription(vo.getBelongsTo().getValueDescription());

			response.setBelongsTo(belongsTo);
		}

		// Department Response
		if (vo.getDepartement() != null) {
			DepartmentResponseDTO departmentResponse = new DepartmentResponseDTO();

			departmentResponse.setId(vo.getDepartement().getId());
			departmentResponse.setDepartmentCode(vo.getDepartement().getDepartmentCode());
			departmentResponse.setDepartmentName(vo.getDepartement().getDepartmentName());

			response.setDepartement(departmentResponse);
		}

		// From Location Response
		if (vo.getFromLocation() != null) {
			LocationMasterResponseDTO fromLocation = new LocationMasterResponseDTO();

			fromLocation.setId(vo.getFromLocation().getId());
			fromLocation.setLocationName(vo.getFromLocation().getLocationName());

			response.setFromLocation(fromLocation);
		}

		// To Location Response
		if (vo.getToLocation() != null) {
			LocationMasterResponseDTO toLocation = new LocationMasterResponseDTO();

			toLocation.setId(vo.getToLocation().getId());
			toLocation.setLocationName(vo.getToLocation().getLocationName());

			response.setToLocation(toLocation);
		}

		// Prepared By Response
		if (vo.getPreparedBy() != null) {
			EmployeeMasterResponseDetailsDTO preparedBy = new EmployeeMasterResponseDetailsDTO();

			preparedBy.setId(vo.getPreparedBy().getId());
			preparedBy.setEmployeeName(vo.getPreparedBy().getEmployeeName());

			response.setPreparedBy(preparedBy);
		}

		// Authorized By Response
		if (vo.getAuthorizedBy() != null) {
			EmployeeMasterResponseDetailsDTO authorizedBy = new EmployeeMasterResponseDetailsDTO();

			authorizedBy.setId(vo.getAuthorizedBy().getId());
			authorizedBy.setEmployeeName(vo.getAuthorizedBy().getEmployeeName());

			response.setAuthorizedBy(authorizedBy);
		}

		// Normal Fields
		response.setTime(vo.getTime());
		response.setProductionApproval(vo.getProductionApproval());
		response.setQualityApproval(vo.getQualityApproval());
		response.setStoreApproval(vo.getStoreApproval());
		response.setNarration(vo.getNarration());
		response.setOrgId(vo.getOrgId());
		response.setFinancialYear(vo.getFinancialYear());
		response.setActive(vo.getActive());
		response.setCancelRemarks(vo.getCancelRemarks());
		response.setCreatedBy(vo.getCreatedBy());

		// Details Response
		List<MachineToolsScrapNoteDetailsResponseDTO> detailsList = new ArrayList<>();

		if (vo.getMachineToolsScrapNoteDetailsVO() != null && !vo.getMachineToolsScrapNoteDetailsVO().isEmpty()) {

			for (MachineToolsScrapNoteDetailsVO detailsVO : vo.getMachineToolsScrapNoteDetailsVO()) {

				MachineToolsScrapNoteDetailsResponseDTO detailsResponse = new MachineToolsScrapNoteDetailsResponseDTO();

				if (detailsVO.getItem() != null) {

					ItemResponse1DTO itemResponse = new ItemResponse1DTO();

					itemResponse.setId(detailsVO.getItem().getId());
					itemResponse.setItemCode(detailsVO.getItem().getItemCode());
					itemResponse.setItemDescription(detailsVO.getItem().getItemDescription());

					detailsResponse.setItem(itemResponse);
				}

				detailsResponse.setStock(detailsVO.getStock());
				detailsResponse.setQuantity(detailsVO.getQuantity());
				detailsResponse.setRate(detailsVO.getRate());
				detailsResponse.setValue(detailsVO.getValue());

				detailsList.add(detailsResponse);
			}
		}

		response.setMachineToolsScrapNoteDetailsResponseDTO(detailsList);

		// Attachment Response
		List<MachineToolScrapNoteAttachmentResponseDTO> attachmentList = new ArrayList<>();

		if (vo.getMachineToolScrapNoteAttachmentVO() != null && !vo.getMachineToolScrapNoteAttachmentVO().isEmpty()) {

			for (MachineToolScrapNoteAttachmentVO attachmentVO : vo.getMachineToolScrapNoteAttachmentVO()) {

				MachineToolScrapNoteAttachmentResponseDTO attachmentDTO = new MachineToolScrapNoteAttachmentResponseDTO();

				attachmentDTO.setId(attachmentVO.getId());
				attachmentDTO.setName(attachmentVO.getName());
				attachmentDTO.setFileName(attachmentVO.getFileName());
				attachmentDTO.setFilePath(attachmentVO.getFilePath());
				attachmentDTO.setFileSize(attachmentVO.getFileSize());
				attachmentDTO.setContentType(attachmentVO.getContentType());
				attachmentDTO.setUploadOn(attachmentVO.getUploadOn());

				attachmentList.add(attachmentDTO);
			}
		}

		response.setMachineToolsScrapNoteAttachmentResponseDTO(attachmentList);

		return response;
	}

	/*
	 * / ============================================================ VIEW FILE
	 * ============================================================
	 */

	@Override
	public ResponseEntity<byte[]> viewMachineToolsScrapNoteFile(HttpServletRequest request) throws IOException {

		return serveMachineToolsScrapNoteFile(request, "/api/vendorComplaintEntry/machineToolsScrapNote/viewFile/",
				machineToolsScrapNoteUploadPath);
	}

	private ResponseEntity<byte[]> serveMachineToolsScrapNoteFile(HttpServletRequest request, String apiPrefix,
			String uploadBasePath) throws IOException {

		String uri = request.getRequestURI();

		String relativePath = uri.replace(apiPrefix, "");

		relativePath = URLDecoder.decode(relativePath, StandardCharsets.UTF_8);

		if (relativePath.startsWith("uploads/")) {

			relativePath = relativePath.substring("uploads/".length());
		}

		Path baseDir = Paths.get(uploadBasePath).toAbsolutePath().normalize();

		Path filePath = baseDir.resolve(relativePath).normalize();

		/*
		 * Prevent path traversal
		 */
		if (!filePath.startsWith(baseDir)) {

			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		/*
		 * File not found
		 */
		if (!Files.exists(filePath)) {

			return ResponseEntity.notFound().build();
		}

		String contentType = Files.probeContentType(filePath);

		if (contentType == null) {

			contentType = "application/octet-stream";
		}

		byte[] data = Files.readAllBytes(filePath);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline").body(data);
	}

	/*
	 * ============================================================ CREATE DIRECTORY
	 * ============================================================
	 */

	private void createDirectory11(Path path) throws IOException {

		if (!Files.exists(path)) {

			Files.createDirectories(path);
		}
	}

	@Override
	public String getMachineToolsScrapNoteDocId(Long orgId, String financialYear) throws ApplicationException {

		String screenCode = "MTSN";

		String docId = machineToolsScrapNoteRepo.getMachineToolsScrapNoteDocId(orgId, financialYear, screenCode);

		if (StringUtils.isBlank(docId)) {

			throw new ApplicationException("Machine Tools Scrap Note DocId Not Found");
		}

		return docId;
	}

	@Override
	public Map<String, Object> getMachineToolsScrapNoteById(Long id) throws ApplicationException {

		MachineToolsScrapNoteVO machineToolsScrapNoteVO = machineToolsScrapNoteRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Invalid Machine Tools Scrap Note Details"));

		Map<String, Object> responseObjectsMap = new HashMap<>();

		responseObjectsMap.put("machineToolsScrapNoteVO", machineToolsScrapNoteResponse(machineToolsScrapNoteVO));

		return responseObjectsMap;
	}

	@Override
	public List<MachineToolsScrapNoteResponseDTO> getMachineToolsScrapNoteByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<MachineToolsScrapNoteVO> machineToolsScrapNoteList = machineToolsScrapNoteRepo
				.getMachineToolsScrapNoteByOrgId(orgId, branch);

		List<MachineToolsScrapNoteResponseDTO> responseList = new ArrayList<>();

		for (MachineToolsScrapNoteVO machineToolsScrapNoteVO : machineToolsScrapNoteList) {

			responseList.add(machineToolsScrapNoteResponse(machineToolsScrapNoteVO));
		}

		return responseList;
	}

//	Activities carried out

	@Override
	@Transactional
	public Map<String, Object> updateCreateActivitiesCarriedOut(ActivitiesCarriedOutDTO dto)
			throws ApplicationException {

		String screenCode = "ACO";

		ActivitiesCarriedOutVO activitiesCarriedOutVO;
		String message;

		if (dto.getId() != null) {

			activitiesCarriedOutVO = activitiesCarriedOutRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Activities Carried Out Details"));

			activitiesCarriedOutVO.setUpdatedBy(dto.getCreatedBy());

			message = "Activities Carried Out Updated Successfully";

		} else {

			activitiesCarriedOutVO = new ActivitiesCarriedOutVO();

			String docId = activitiesCarriedOutRepo.getActivitiesCarriedOutDocId(dto.getOrgId(), dto.getFinancialYear(),
					screenCode);

			if (StringUtils.isBlank(docId)) {
				throw new ApplicationException("Activities Carried Out DocId Not Found");
			}

			activitiesCarriedOutVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdScreenCode(dto.getOrgId(), screenCode);

			if (documentTypeMappingDetailsVO == null) {
				throw new ApplicationException("Document Type Mapping Details Not Found");
			}

			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			activitiesCarriedOutVO.setCreatedBy(dto.getCreatedBy());
			activitiesCarriedOutVO.setUpdatedBy(dto.getCreatedBy());

			message = "Activities Carried Out Created Successfully";
		}

		createUpdateActivitiesCarriedOutVO(activitiesCarriedOutVO, dto);

		activitiesCarriedOutVO = activitiesCarriedOutRepo.save(activitiesCarriedOutVO);

		saveActivitiesCarriedOutDetails(activitiesCarriedOutVO, dto.getActivitiesCarriedOutDetailsDTO());

		saveActivitiesCarriedOutComponentDetails(activitiesCarriedOutVO,
				dto.getActivitiesCarriedOutComponentDetailsDTO());

		Map<String, Object> responseObjectsMap = new HashMap<>();

		responseObjectsMap.put(CommonConstant.STRING_MESSAGE, message);

		responseObjectsMap.put("activitiesCarriedOutVO", activitiesCarriedOutResponse(activitiesCarriedOutVO));

		responseObjectsMap.put("docId", activitiesCarriedOutVO.getDocId());

		responseObjectsMap.put("docDate", activitiesCarriedOutVO.getDocDate());

		return responseObjectsMap;
	}

	private void createUpdateActivitiesCarriedOutVO(ActivitiesCarriedOutVO vo, ActivitiesCarriedOutDTO dto)
			throws ApplicationException {

		if (dto.getBranch() != null) {

			BranchVO branchVO = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Invalid Branch Details"));

			vo.setBranch(branchVO);
		}

		if (dto.getDepartment() != null) {

			DepartmentVO departmentVO = departmentRepo.findById(dto.getDepartment())
					.orElseThrow(() -> new ApplicationException("Invalid Department Details"));

			vo.setDepartment(departmentVO);
		}

		if (dto.getCheckedBy() != null) {

			EmployeeMasterVO employeeMasterVO = employeeMasterRepo.findById(dto.getCheckedBy())
					.orElseThrow(() -> new ApplicationException("Invalid Checked By Details"));

			vo.setCheckedBy(employeeMasterVO);
		}

		if (dto.getSelectMachineToolInst() != null) {

			ToolCategoryVO toolCategoryVO = toolCategoryRepo.findById(dto.getSelectMachineToolInst())
					.orElseThrow(() -> new ApplicationException("Invalid Machine Tool Instrument Details"));

			vo.setSelectMachineToolInst(toolCategoryVO);
		}

		vo.setMachineToolInstNo(dto.getMachineToolInstNo());

		vo.setLocation(dto.getLocation());

		if (dto.getPmCheckListNo() != null) {

			PMCheckListMasterVO pmCheckListMasterVO = pmCheckListMasterRepo.findById(dto.getPmCheckListNo())
					.orElseThrow(() -> new ApplicationException("Invalid PM Check List Details"));

			vo.setPmCheckListNo(pmCheckListMasterVO);
		}

		if (dto.getMaintenanceType() != null) {

			ListOfValuesDetailsVO maintenanceTypeVO = listOfValuesDetailsRepo.findById(dto.getMaintenanceType())
					.orElseThrow(() -> new ApplicationException("Invalid Maintenance Type Details"));

			vo.setMaintenanceType(maintenanceTypeVO);
		}

		if (dto.getFromLocation() != null) {

			LocationVO locationVO = locationRepo.findById(dto.getFromLocation())
					.orElseThrow(() -> new ApplicationException("Invalid From Location Details"));

			vo.setFromLocation(locationVO);
		}

		vo.setOrgId(dto.getOrgId());
		vo.setFinancialYear(dto.getFinancialYear());
		vo.setActive(dto.isActive());
		vo.setCancelRemarks(dto.getCancelRemarks());
	}

	private void saveActivitiesCarriedOutDetails(ActivitiesCarriedOutVO activitiesCarriedOutVO,
			List<ActivitiesCarriedOutDetailsDTO> detailsDTOList) throws ApplicationException {

		if (detailsDTOList == null || detailsDTOList.isEmpty()) {
			return;
		}

		List<ActivitiesCarriedOutDetailsVO> detailsList = new ArrayList<>();

		for (ActivitiesCarriedOutDetailsDTO detailsDTO : detailsDTOList) {

			ActivitiesCarriedOutDetailsVO detailsVO = new ActivitiesCarriedOutDetailsVO();

			detailsVO.setScheduledActivity(detailsDTO.getScheduledActivity());

			if (detailsDTO.getItem() != null) {

				ItemMasterVO itemVO = itemRepo.findById(detailsDTO.getItem())
						.orElseThrow(() -> new ApplicationException("Invalid Item Details"));

				detailsVO.setItem(itemVO);
			}

			detailsVO.setFromTime(detailsDTO.getFromTime());
			detailsVO.setToTime(detailsDTO.getToTime());

			detailsVO.setCheckingPoints(detailsDTO.getCheckingPoints());

			detailsVO.setParameter(detailsDTO.getParameter());

			detailsVO.setActivitiesCarriedOut(detailsDTO.getActivitiesCarriedOut());

			detailsVO.setStatus(detailsDTO.getStatus());

			detailsVO.setDate(detailsDTO.getDate());

			detailsVO.setNextActivity(detailsDTO.getNextActivity());

			/*
			 * Calculate No Of Hours To Time - From Time
			 */
			if (detailsDTO.getFromTime() != null && detailsDTO.getToTime() != null) {

				LocalTime fromTime = detailsDTO.getFromTime();

				LocalTime toTime = detailsDTO.getToTime();

				long minutes = Duration.between(fromTime, toTime).toMinutes();

				/*
				 * Handles activity crossing midnight
				 */
				if (minutes < 0) {
					minutes = minutes + (24 * 60);
				}

				BigDecimal noOfHours = BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60), 2,
						RoundingMode.HALF_UP);

				detailsVO.setNoOfHrs(noOfHours);

			} else {

				detailsVO.setNoOfHrs(BigDecimal.ZERO);
			}

			detailsVO.setFrequency(detailsDTO.getFrequency());

			detailsVO.setNextScheduleDate(detailsDTO.getNextScheduleDate());

			detailsVO.setActivitiesCarriedOutVO(activitiesCarriedOutVO);

			detailsList.add(detailsVO);
		}

		activitiesCarriedOutDetailsRepo.saveAll(detailsList);

		activitiesCarriedOutVO.setActivitiesCarriedOutDetailsVO(detailsList);
	}

	private void saveActivitiesCarriedOutComponentDetails(ActivitiesCarriedOutVO activitiesCarriedOutVO,
			List<ActivitiesCarriedOutComponentDetailsDTO> componentDTOList) throws ApplicationException {

		if (componentDTOList == null || componentDTOList.isEmpty()) {
			return;
		}

		List<ActivitiesCarriedOutComponentDetailsVO> componentList = new ArrayList<>();

		for (ActivitiesCarriedOutComponentDetailsDTO componentDTO : componentDTOList) {

			ActivitiesCarriedOutComponentDetailsVO componentVO = new ActivitiesCarriedOutComponentDetailsVO();

			if (componentDTO.getItem() != null) {

				ItemMasterVO itemVO = itemRepo.findById(componentDTO.getItem())
						.orElseThrow(() -> new ApplicationException("Invalid Item Details"));

				componentVO.setItem(itemVO);
			}

			componentVO.setReqQty(componentDTO.getReqQty());

			componentVO.setRate(componentDTO.getRate());

			/*
			 * Calculate Amount Req Qty * Rate
			 */
			if (componentDTO.getReqQty() != null && componentDTO.getRate() != null) {

				BigDecimal amount = componentDTO.getReqQty().multiply(componentDTO.getRate());

				componentVO.setAmount(amount);

			} else {

				componentVO.setAmount(BigDecimal.ZERO);
			}

			componentVO.setRemarks(componentDTO.getRemarks());

			componentVO.setActivitiesCarriedOutVO(activitiesCarriedOutVO);

			componentList.add(componentVO);
		}

		activitiesCarriedOutComponentDetailsRepo.saveAll(componentList);

		activitiesCarriedOutVO.setActivitiesCarriedOutComponentDetailsVO(componentList);
	}

	private ActivitiesCarriedOutResponseDTO activitiesCarriedOutResponse(ActivitiesCarriedOutVO vo) {

		ActivitiesCarriedOutResponseDTO response = new ActivitiesCarriedOutResponseDTO();

		response.setId(vo.getId());

		// Branch
		if (vo.getBranch() != null) {

			BranchResponseDTO branchResponse = new BranchResponseDTO();

			branchResponse.setId(vo.getBranch().getId());

			branchResponse.setBranchCode(vo.getBranch().getBranchCode());

			branchResponse.setBranchName(vo.getBranch().getBranchName());

			response.setBranch(branchResponse);
		}

		// Department
		if (vo.getDepartment() != null) {

			DepartmentResponseDTO departmentResponse = new DepartmentResponseDTO();

			departmentResponse.setId(vo.getDepartment().getId());

			departmentResponse.setDepartmentCode(vo.getDepartment().getDepartmentCode());

			departmentResponse.setDepartmentName(vo.getDepartment().getDepartmentName());

			response.setDepartment(departmentResponse);
		}

		// Checked By
		if (vo.getCheckedBy() != null) {

			EmployeeMasterResponseDetailsDTO checkedBy = new EmployeeMasterResponseDetailsDTO();

			checkedBy.setId(vo.getCheckedBy().getId());

			checkedBy.setEmployeeName(vo.getCheckedBy().getEmployeeName());

			response.setCheckedBy(checkedBy);
		}

		// Machine Tool Instrument
		if (vo.getSelectMachineToolInst() != null) {

			ToolCategoryResponse1DTO toolCategoryResponse = new ToolCategoryResponse1DTO();

			toolCategoryResponse.setId(vo.getSelectMachineToolInst().getId());

			toolCategoryResponse.setApplicableFor(vo.getSelectMachineToolInst().getApllicableFor());

			response.setSelectMachineToolInst(toolCategoryResponse);
		}

		response.setMachineToolInstNo(vo.getMachineToolInstNo());

		response.setLocation(vo.getLocation());

		// PM Check List
		if (vo.getPmCheckListNo() != null) {

			PMCheckListResponseDTO pmCheckListResponse = new PMCheckListResponseDTO();

			pmCheckListResponse.setId(vo.getPmCheckListNo().getId());

			pmCheckListResponse.setPmCheckListNo(vo.getPmCheckListNo()
					.getPmCheckListNo()); /*
											 * Add the fields available in your PMCheckListResponseDTO here.
											 */

			response.setPmCheckListNo(pmCheckListResponse);
		}

		// Maintenance Type
		if (vo.getMaintenanceType() != null) {

			ListOfValuesDetailsResponseDTO maintenanceType = new ListOfValuesDetailsResponseDTO();

			maintenanceType.setId(vo.getMaintenanceType().getId());

			maintenanceType.setCode(vo.getMaintenanceType().getValueCode());

			maintenanceType.setDescription(vo.getMaintenanceType().getValueDescription());

			response.setMaintenanceType(maintenanceType);
		}

		// From Location
		if (vo.getFromLocation() != null) {

			LocationMasterResponseDTO fromLocation = new LocationMasterResponseDTO();

			fromLocation.setId(vo.getFromLocation().getId());

			fromLocation.setLocationName(vo.getFromLocation().getLocationName());

			response.setFromLocation(fromLocation);
		}

		response.setOrgId(vo.getOrgId());
		response.setFinancialYear(vo.getFinancialYear());
		response.setActive(vo.getActive());
		response.setCancelRemarks(vo.getCancelRemarks());
		response.setCreatedBy(vo.getCreatedBy());

		// Details Response
		List<ActivitiesCarriedOutDetailsResponseDTO> detailsResponseList = new ArrayList<>();

		if (vo.getActivitiesCarriedOutDetailsVO() != null && !vo.getActivitiesCarriedOutDetailsVO().isEmpty()) {

			for (ActivitiesCarriedOutDetailsVO detailsVO : vo.getActivitiesCarriedOutDetailsVO()) {

				ActivitiesCarriedOutDetailsResponseDTO detailsResponse = new ActivitiesCarriedOutDetailsResponseDTO();

				detailsResponse.setScheduledActivity(detailsVO.getScheduledActivity());

				if (detailsVO.getItem() != null) {

					ItemResponse1DTO itemResponse = new ItemResponse1DTO();

					itemResponse.setId(detailsVO.getItem().getId());

					itemResponse.setItemCode(detailsVO.getItem().getItemCode());

					itemResponse.setItemDescription(detailsVO.getItem().getItemDescription());

					if (detailsVO.getItem().getPrimaryUnit() != null) {

						UnitMasterResponseDTO unitResponse = new UnitMasterResponseDTO();

						unitResponse.setId(detailsVO.getItem().getPrimaryUnit().getId());

						unitResponse.setUnitId(detailsVO.getItem().getPrimaryUnit().getUnitId());

						unitResponse.setUnitDescription(detailsVO.getItem().getPrimaryUnit().getDescription());

						itemResponse.setUnit(unitResponse);
					}

					detailsResponse.setItem(itemResponse);
				}
				detailsResponse.setFromTime(detailsVO.getFromTime());

				detailsResponse.setToTime(detailsVO.getToTime());

				detailsResponse.setCheckingPoints(detailsVO.getCheckingPoints());

				detailsResponse.setParameter(detailsVO.getParameter());

				detailsResponse.setActivitiesCarriedOut(detailsVO.getActivitiesCarriedOut());

				detailsResponse.setStatus(detailsVO.getStatus());

				detailsResponse.setDate(detailsVO.getDate());

				detailsResponse.setNextActivity(detailsVO.getNextActivity());

				detailsResponse.setNoOfHrs(detailsVO.getNoOfHrs());

				detailsResponse.setFrequency(detailsVO.getFrequency());

				detailsResponse.setNextScheduleDate(detailsVO.getNextScheduleDate());

				detailsResponseList.add(detailsResponse);
			}
		}

		response.setActivitiesCarriedOutDetailsResponseDTO(detailsResponseList);

		// Component Details Response
		List<ActivitiesCarriedOutComponentDetailsResponseDTO> componentResponseList = new ArrayList<>();

		if (vo.getActivitiesCarriedOutComponentDetailsVO() != null
				&& !vo.getActivitiesCarriedOutComponentDetailsVO().isEmpty()) {

			for (ActivitiesCarriedOutComponentDetailsVO componentVO : vo.getActivitiesCarriedOutComponentDetailsVO()) {

				ActivitiesCarriedOutComponentDetailsResponseDTO componentResponse = new ActivitiesCarriedOutComponentDetailsResponseDTO();

				if (componentVO.getItem() != null) {

					ItemResponse1DTO itemResponse = new ItemResponse1DTO();

					itemResponse.setId(componentVO.getItem().getId());

					itemResponse.setItemCode(componentVO.getItem().getItemCode());

					itemResponse.setItemDescription(componentVO.getItem().getItemDescription());

					if (componentVO.getItem().getPrimaryUnit() != null) {

						UnitMasterResponseDTO unitResponse = new UnitMasterResponseDTO();

						unitResponse.setId(componentVO.getItem().getPrimaryUnit().getId());

						unitResponse.setUnitId(componentVO.getItem().getPrimaryUnit().getUnitId());

						unitResponse.setUnitDescription(componentVO.getItem().getPrimaryUnit().getDescription());

						itemResponse.setUnit(unitResponse);
					}

					componentResponse.setItem(itemResponse);
				}
				componentResponse.setReqQty(componentVO.getReqQty());

				componentResponse.setRate(componentVO.getRate());

				componentResponse.setAmount(componentVO.getAmount());

				componentResponse.setRemarks(componentVO.getRemarks());

				componentResponseList.add(componentResponse);
			}
		}

		response.setActivitiesCarriedOutComponentDetailsResponseDTO(componentResponseList);

		return response;
	}

	@Override
	public Map<String, Object> getActivitiesCarriedOutById(Long id) throws ApplicationException {

		ActivitiesCarriedOutVO activitiesCarriedOutVO = activitiesCarriedOutRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Invalid Activities Carried Out Details"));

		Map<String, Object> responseObjectsMap = new HashMap<>();

		responseObjectsMap.put("activitiesCarriedOutVO", activitiesCarriedOutResponse(activitiesCarriedOutVO));

		return responseObjectsMap;
	}

	@Override
	public List<ActivitiesCarriedOutResponseDTO> getActivitiesCarriedOutByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<ActivitiesCarriedOutVO> activitiesCarriedOutList = activitiesCarriedOutRepo.findByOrgId(orgId, branch);

		List<ActivitiesCarriedOutResponseDTO> responseList = new ArrayList<>();

		for (ActivitiesCarriedOutVO vo : activitiesCarriedOutList) {

			responseList.add(activitiesCarriedOutResponse(vo));
		}

		return responseList;
	}

	@Override
	public Map<String, Object> getActivitiesCarriedOutDocId(Long orgId, String financialYear)
			throws ApplicationException {

		String screenCode = "ACO";

		String docId = activitiesCarriedOutRepo.getActivitiesCarriedOutDocId(orgId, financialYear, screenCode);

		if (StringUtils.isBlank(docId)) {
			throw new ApplicationException("Activities Carried Out DocId Not Found");
		}

		Map<String, Object> responseObjectsMap = new HashMap<>();

		responseObjectsMap.put("docId", docId);

		return responseObjectsMap;
	}

//	quality scrap note
	@Override
	@Transactional
	public Map<String, Object> updateCreateQualityScrapNote(QualityScrapNoteDTO qualityScrapNoteDTO)
			throws ApplicationException {

		String screenCode = "QSN";

		QualityScrapNoteVO qualityScrapNoteVO;
		String message;

		if (qualityScrapNoteDTO.getId() != null) {

			qualityScrapNoteVO = qualityScrapNoteRepo.findById(qualityScrapNoteDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Quality Scrap Note Details"));

			qualityScrapNoteVO.setUpdatedBy(qualityScrapNoteDTO.getCreatedBy());

			message = "Quality Scrap Note Updated Successfully";

		} else {

			qualityScrapNoteVO = new QualityScrapNoteVO();

			String docId = qualityScrapNoteRepo.getQualityScrapNoteDocId(qualityScrapNoteDTO.getOrgId(),
					qualityScrapNoteDTO.getFinancialYear(), screenCode);

			if (StringUtils.isBlank(docId)) {
				throw new ApplicationException("Quality Scrap Note DocId Not Found");
			}

			qualityScrapNoteVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdScreenCode(qualityScrapNoteDTO.getOrgId(), screenCode);

			if (documentTypeMappingDetailsVO == null) {
				throw new ApplicationException("Document Type Mapping Details Not Found");
			}

			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			qualityScrapNoteVO.setCreatedBy(qualityScrapNoteDTO.getCreatedBy());

			qualityScrapNoteVO.setUpdatedBy(qualityScrapNoteDTO.getCreatedBy());

			message = "Quality Scrap Note Created Successfully";
		}

		createUpdateQualityScrapNoteVO(qualityScrapNoteVO, qualityScrapNoteDTO);

		qualityScrapNoteVO = qualityScrapNoteRepo.save(qualityScrapNoteVO);

		/*
		 * Save Details
		 */
		if (qualityScrapNoteDTO.getQualityScrapNoteDetailsDTO() != null) {

			saveQualityScrapNoteDetails(qualityScrapNoteVO, qualityScrapNoteDTO.getQualityScrapNoteDetailsDTO());
		}

		/*
		 * Reload parent with saved details
		 */
		qualityScrapNoteVO = qualityScrapNoteRepo.findById(qualityScrapNoteVO.getId())
				.orElseThrow(() -> new ApplicationException("Quality Scrap Note Not Found"));

		Map<String, Object> responseObjectsMap = new HashMap<>();

		responseObjectsMap.put(CommonConstant.STRING_MESSAGE, message);

		responseObjectsMap.put("qualityScrapNoteVO", qualityScrapNoteResponse(qualityScrapNoteVO));
		responseObjectsMap.put("docId", qualityScrapNoteVO.getDocId());

		responseObjectsMap.put("docDate", qualityScrapNoteVO.getDocDate());

		return responseObjectsMap;
	}

	private void createUpdateQualityScrapNoteVO(QualityScrapNoteVO qualityScrapNoteVO,
			QualityScrapNoteDTO qualityScrapNoteDTO) throws ApplicationException {

		if (qualityScrapNoteDTO.getBranch() != null) {

			BranchVO branchVO = branchRepo.findById(qualityScrapNoteDTO.getBranch())
					.orElseThrow(() -> new ApplicationException("Invalid Branch"));

			qualityScrapNoteVO.setBranch(branchVO);
		}

		if (qualityScrapNoteDTO.getBelongsTo() != null) {

			ListOfValuesDetailsVO belongsTo = listOfValuesDetailsRepo.findById(qualityScrapNoteDTO.getBelongsTo())
					.orElseThrow(() -> new ApplicationException("Invalid Belongs To"));

			qualityScrapNoteVO.setBelongsTo(belongsTo);
		}

		if (qualityScrapNoteDTO.getDepartment() != null) {

			DepartmentVO departmentVO = departmentRepo.findById(qualityScrapNoteDTO.getDepartment())
					.orElseThrow(() -> new ApplicationException("Invalid Department"));

			qualityScrapNoteVO.setDepartment(departmentVO);
		}

		if (qualityScrapNoteDTO.getFromLocation() != null) {

			LocationVO fromLocation = locationRepo.findById(qualityScrapNoteDTO.getFromLocation())
					.orElseThrow(() -> new ApplicationException("Invalid From Location"));

			qualityScrapNoteVO.setFromLocation(fromLocation);
		}

		if (qualityScrapNoteDTO.getToLocation() != null) {

			LocationVO toLocation = locationRepo.findById(qualityScrapNoteDTO.getToLocation())
					.orElseThrow(() -> new ApplicationException("Invalid To Location"));

			qualityScrapNoteVO.setToLocation(toLocation);
		}

		if (qualityScrapNoteDTO.getPreparedBy() != null) {

			EmployeeMasterVO preparedBy = employeeMasterRepo.findById(qualityScrapNoteDTO.getPreparedBy())
					.orElseThrow(() -> new ApplicationException("Invalid Prepared By"));

			qualityScrapNoteVO.setPreparedBy(preparedBy);
		}

		if (qualityScrapNoteDTO.getAuthorizedBy() != null) {

			EmployeeMasterVO authorizedBy = employeeMasterRepo.findById(qualityScrapNoteDTO.getAuthorizedBy())
					.orElseThrow(() -> new ApplicationException("Invalid Authorized By"));

			qualityScrapNoteVO.setAuthorizedBy(authorizedBy);
		}

		qualityScrapNoteVO.setTime(qualityScrapNoteDTO.getTime());

		// DO NOT SET totalScrapValue FROM DTO
		// It will be calculated from quantity × rate
		// qualityScrapNoteVO.setTotalScrapValue(
		// qualityScrapNoteDTO.getTotalScrapValue());

		qualityScrapNoteVO.setQualityApproval(qualityScrapNoteDTO.getQualityApproval());

		qualityScrapNoteVO.setNarration(qualityScrapNoteDTO.getNarration());

		qualityScrapNoteVO.setOrgId(qualityScrapNoteDTO.getOrgId());

		qualityScrapNoteVO.setFinancialYear(qualityScrapNoteDTO.getFinancialYear());

		qualityScrapNoteVO.setActive(qualityScrapNoteDTO.isActive());

		qualityScrapNoteVO.setCancelRemarks(qualityScrapNoteDTO.getCancelRemarks());

		qualityScrapNoteVO.setScreenCode("QSN");

		qualityScrapNoteVO.setScreenName("QUALITY SCRAP NOTE");
	}

	private void saveQualityScrapNoteDetails(QualityScrapNoteVO qualityScrapNoteVO,
			List<QualityScrapNoteDetailsDTO> detailsDTOList) throws ApplicationException {

		List<QualityScrapNoteDetailsVO> detailsList = new ArrayList<>();

		BigDecimal totalScrapValue = BigDecimal.ZERO;

		for (QualityScrapNoteDetailsDTO detailsDTO : detailsDTOList) {

			QualityScrapNoteDetailsVO detailsVO = new QualityScrapNoteDetailsVO();

			if (detailsDTO.getItem() != null) {

				ItemMasterVO itemVO = itemRepo.findById(detailsDTO.getItem())
						.orElseThrow(() -> new ApplicationException("Invalid Item"));

				detailsVO.setItem(itemVO);
			}

			detailsVO.setStock(detailsDTO.getStock());

			detailsVO.setQuantity(detailsDTO.getQuantity());

			detailsVO.setRate(detailsDTO.getRate());

			/*
			 * Value = Quantity × Rate
			 */
			BigDecimal value = BigDecimal.ZERO;

			if (detailsDTO.getQuantity() != null && detailsDTO.getRate() != null) {

				value = detailsDTO.getQuantity().multiply(detailsDTO.getRate());
			}

			detailsVO.setValue(value);

			/*
			 * Add child value to header total
			 */
			totalScrapValue = totalScrapValue.add(value);

			detailsVO.setQualityScrapNoteVO(qualityScrapNoteVO);

			detailsList.add(detailsVO);
		}

		/*
		 * Set calculated total value in header
		 */
		qualityScrapNoteVO.setTotalScrapValue(totalScrapValue);

		/*
		 * Set details to parent
		 */
		qualityScrapNoteVO.setQualityScrapNoteDetailsVO(detailsList);
	}

	private QualityScrapNoteResponseDTO qualityScrapNoteResponse(QualityScrapNoteVO vo) {

		QualityScrapNoteResponseDTO response = new QualityScrapNoteResponseDTO();

		response.setId(vo.getId());

		// Branch
		if (vo.getBranch() != null) {

			BranchResponseDTO branchResponse = new BranchResponseDTO();

			branchResponse.setId(vo.getBranch().getId());
			branchResponse.setBranchCode(vo.getBranch().getBranchCode());
			branchResponse.setBranchName(vo.getBranch().getBranchName());

			response.setBranch(branchResponse);
		}

		response.setTime(vo.getTime());

		// Belongs To
		if (vo.getBelongsTo() != null) {

			ListOfValuesDetailsResponseDTO belongsTo = new ListOfValuesDetailsResponseDTO();

			belongsTo.setId(vo.getBelongsTo().getId());

			belongsTo.setCode(vo.getBelongsTo().getValueCode());

			belongsTo.setDescription(vo.getBelongsTo().getValueDescription());

			response.setBelongsTo(belongsTo);
		}

		// Department
		if (vo.getDepartment() != null) {

			DepartmentResponseDTO department = new DepartmentResponseDTO();

			department.setId(vo.getDepartment().getId());

			department.setDepartmentCode(vo.getDepartment().getDepartmentCode());

			department.setDepartmentName(vo.getDepartment().getDepartmentName());

			response.setDepartment(department);
		}

		// From Location
		if (vo.getFromLocation() != null) {

			LocationMasterResponseDTO fromLocation = new LocationMasterResponseDTO();

			fromLocation.setId(vo.getFromLocation().getId());

//			fromLocation.setLocationCode(vo.getFromLocation().getLocationCode());

			fromLocation.setLocationName(vo.getFromLocation().getLocationName());

			response.setFromLocation(fromLocation);
		}

		// To Location
		if (vo.getToLocation() != null) {

			LocationMasterResponseDTO toLocation = new LocationMasterResponseDTO();

			toLocation.setId(vo.getToLocation().getId());

//			toLocation.setLocationCode(vo.getToLocation().getLocationCode());

			toLocation.setLocationName(vo.getToLocation().getLocationName());

			response.setToLocation(toLocation);
		}

		// Prepared By
		if (vo.getPreparedBy() != null) {

			EmployeeMasterDetailsReponseDTO preparedBy = new EmployeeMasterDetailsReponseDTO();

			preparedBy.setId(vo.getPreparedBy().getId());

//			spreparedBy.setEmployeeCode(vo.getPreparedBy().getEmployeeCode());

			preparedBy.setEmployeeName(vo.getPreparedBy().getEmployeeName());

			response.setPreparedBy(preparedBy);
		}

		// Authorized By
		if (vo.getAuthorizedBy() != null) {

			EmployeeMasterDetailsReponseDTO authorizedBy = new EmployeeMasterDetailsReponseDTO();

			authorizedBy.setId(vo.getAuthorizedBy().getId());

//			authorizedBy.setEmployeeCode(vo.getAuthorizedBy().getEmployeeCode());

			authorizedBy.setEmployeeName(vo.getAuthorizedBy().getEmployeeName());

			response.setAuthorizedBy(authorizedBy);
		}

		response.setTotalScrapValue(vo.getTotalScrapValue());

		response.setQualityApproval(vo.getQualityApproval());

		response.setNarration(vo.getNarration());

		response.setOrgId(vo.getOrgId());

		response.setFinancialYear(vo.getFinancialYear());

		response.setActive(vo.getActive());

		response.setCancelRemarks(vo.getCancelRemarks());

		response.setCreatedBy(vo.getCreatedBy());

		// Details
		List<QualityScrapNoteDetailsResponseDTO> detailsResponseList = new ArrayList<>();

		if (vo.getQualityScrapNoteDetailsVO() != null && !vo.getQualityScrapNoteDetailsVO().isEmpty()) {

			for (QualityScrapNoteDetailsVO detailsVO : vo.getQualityScrapNoteDetailsVO()) {

				QualityScrapNoteDetailsResponseDTO detailsResponse = new QualityScrapNoteDetailsResponseDTO();

				// Item
				if (detailsVO.getItem() != null) {

					ItemResponse1DTO itemResponse = new ItemResponse1DTO();

					itemResponse.setId(detailsVO.getItem().getId());

					itemResponse.setItemCode(detailsVO.getItem().getItemCode());

					itemResponse.setItemDescription(detailsVO.getItem().getItemDescription());

					// Unit
					if (detailsVO.getItem().getPrimaryUnit() != null) {

						UnitMasterResponseDTO unitResponse = new UnitMasterResponseDTO();

						unitResponse.setId(detailsVO.getItem().getPrimaryUnit().getId());

						unitResponse.setUnitId(detailsVO.getItem().getPrimaryUnit().getUnitId());

						unitResponse.setUnitDescription(detailsVO.getItem().getPrimaryUnit().getDescription());

						itemResponse.setUnit(unitResponse);
					}

					detailsResponse.setItem(itemResponse);
				}

				detailsResponse.setStock(detailsVO.getStock());

				detailsResponse.setQuantity(detailsVO.getQuantity());

				detailsResponse.setRate(detailsVO.getRate());

				detailsResponse.setValue(detailsVO.getValue());

				detailsResponseList.add(detailsResponse);
			}
		}

		response.setQualityScrapNoteDetailsResponseDTO(detailsResponseList);

		return response;
	}

	@Override
	public Map<String, Object> getQualityScrapNoteById(Long id) throws ApplicationException {

		QualityScrapNoteVO qualityScrapNoteVO = qualityScrapNoteRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Invalid Quality Scrap Note Details"));

		Map<String, Object> responseObjectsMap = new HashMap<>();

		responseObjectsMap.put("qualityScrapNoteVO", qualityScrapNoteResponse(qualityScrapNoteVO));

		responseObjectsMap.put("docId", qualityScrapNoteVO.getDocId());

		responseObjectsMap.put("docDate", qualityScrapNoteVO.getDocDate());

		return responseObjectsMap;
	}

	@Override
	public List<QualityScrapNoteResponseDTO> getQualityScrapNoteByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<QualityScrapNoteVO> qualityScrapNoteList = qualityScrapNoteRepo.findByOrgId(orgId, branch);

		List<QualityScrapNoteResponseDTO> responseList = new ArrayList<>();

		for (QualityScrapNoteVO vo : qualityScrapNoteList) {

			responseList.add(qualityScrapNoteResponse(vo));
		}

		return responseList;
	}

	@Override
	public Map<String, Object> getQualityScrapNoteDocId(Long orgId, String financialYear) throws ApplicationException {

		String screenCode = "QSN";

		String docId = qualityScrapNoteRepo.getQualityScrapNoteDocId(orgId, financialYear, screenCode);

		if (StringUtils.isBlank(docId)) {
			throw new ApplicationException("Quality Scrap Note DocId Not Found");
		}

		Map<String, Object> responseObjectsMap = new HashMap<>();

		responseObjectsMap.put("docId", docId);

		return responseObjectsMap;
	}

//	Scrap material return rejecttion

	@Override
	@Transactional
	public Map<String, Object> updateCreateScrapMaterialReturnRejection(
			ScrapMaterialReturnRejectionDTO scrapMaterialReturnRejectionDTO) throws ApplicationException {

		String screenCode = "SMRR";

		ScrapMaterialReturnRejectionVO scrapMaterialReturnRejectionVO;
		String message;

		if (scrapMaterialReturnRejectionDTO.getId() != null) {

			scrapMaterialReturnRejectionVO = scrapMaterialReturnRejectionRepo
					.findById(scrapMaterialReturnRejectionDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Scrap Material Return Rejection Details"));

			scrapMaterialReturnRejectionVO.setUpdatedBy(scrapMaterialReturnRejectionDTO.getCreatedBy());

			message = "Scrap Material Return Rejection Updated Successfully";

		} else {

			scrapMaterialReturnRejectionVO = new ScrapMaterialReturnRejectionVO();

			String docId = scrapMaterialReturnRejectionRepo.getScrapMaterialReturnRejectionDocId(
					scrapMaterialReturnRejectionDTO.getOrgId(), scrapMaterialReturnRejectionDTO.getFinancialYear(),
					screenCode);

			if (StringUtils.isBlank(docId)) {

				throw new ApplicationException("Scrap Material Return Rejection DocId Not Found");
			}

			scrapMaterialReturnRejectionVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdScreenCode(scrapMaterialReturnRejectionDTO.getOrgId(), screenCode);

			if (documentTypeMappingDetailsVO == null) {

				throw new ApplicationException("Document Type Mapping Details Not Found");
			}

			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			scrapMaterialReturnRejectionVO.setCreatedBy(scrapMaterialReturnRejectionDTO.getCreatedBy());

			scrapMaterialReturnRejectionVO.setUpdatedBy(scrapMaterialReturnRejectionDTO.getCreatedBy());

			message = "Scrap Material Return Rejection Created Successfully";
		}

		createUpdateScrapMaterialReturnRejectionVO(scrapMaterialReturnRejectionVO, scrapMaterialReturnRejectionDTO);

		if (scrapMaterialReturnRejectionDTO.getScrapMaterialReturnRejectionDetailsDTO() != null
				&& !scrapMaterialReturnRejectionDTO.getScrapMaterialReturnRejectionDetailsDTO().isEmpty()) {

			saveScrapMaterialReturnRejectionDetails(scrapMaterialReturnRejectionVO,
					scrapMaterialReturnRejectionDTO.getScrapMaterialReturnRejectionDetailsDTO());
		}

		scrapMaterialReturnRejectionVO = scrapMaterialReturnRejectionRepo.save(scrapMaterialReturnRejectionVO);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		responseObjectsMap.put(CommonConstant.STRING_MESSAGE, message);

		responseObjectsMap.put("scrapMaterialReturnRejectionVO",
				scrapMaterialReturnRejectionResponse(scrapMaterialReturnRejectionVO));

		responseObjectsMap.put("docId", scrapMaterialReturnRejectionVO.getDocId());

		responseObjectsMap.put("docDate", scrapMaterialReturnRejectionVO.getDocDate());

		return responseObjectsMap;
	}

	private void createUpdateScrapMaterialReturnRejectionVO(
			ScrapMaterialReturnRejectionVO scrapMaterialReturnRejectionVO,
			ScrapMaterialReturnRejectionDTO scrapMaterialReturnRejectionDTO) throws ApplicationException {

		if (scrapMaterialReturnRejectionDTO.getBranch() != null) {

			BranchVO branchVO = branchRepo.findById(scrapMaterialReturnRejectionDTO.getBranch())
					.orElseThrow(() -> new ApplicationException("Invalid Branch"));

			scrapMaterialReturnRejectionVO.setBranch(branchVO);
		}

		if (scrapMaterialReturnRejectionDTO.getEntryFor() != null) {

			ListOfValuesDetailsVO entryFor = listOfValuesDetailsRepo
					.findById(scrapMaterialReturnRejectionDTO.getEntryFor())
					.orElseThrow(() -> new ApplicationException("Invalid Entry For"));

			scrapMaterialReturnRejectionVO.setEntryFor(entryFor);
		}

		if (scrapMaterialReturnRejectionDTO.getVendorId() != null) {

			CustomerVO vendor = customerRepo.findById(scrapMaterialReturnRejectionDTO.getVendorId())
					.orElseThrow(() -> new ApplicationException("Invalid Vendor"));

			scrapMaterialReturnRejectionVO.setVendorId(vendor);
		}

		if (scrapMaterialReturnRejectionDTO.getToLocation() != null) {

			LocationVO toLocation = locationRepo.findById(scrapMaterialReturnRejectionDTO.getToLocation())
					.orElseThrow(() -> new ApplicationException("Invalid To Location"));

			scrapMaterialReturnRejectionVO.setToLocation(toLocation);
		}

		if (scrapMaterialReturnRejectionDTO.getVendorLocation() != null) {

			LocationVO vendorLocation = locationRepo.findById(scrapMaterialReturnRejectionDTO.getVendorLocation())
					.orElseThrow(() -> new ApplicationException("Invalid Vendor Location"));

			scrapMaterialReturnRejectionVO.setVendorLocation(vendorLocation);
		}

		scrapMaterialReturnRejectionVO.setEntryType(scrapMaterialReturnRejectionDTO.getEntryType());

		scrapMaterialReturnRejectionVO.setDocNo(scrapMaterialReturnRejectionDTO.getDocNo());

		scrapMaterialReturnRejectionVO.setDocumentDate(scrapMaterialReturnRejectionDTO.getDocumentDate());

		scrapMaterialReturnRejectionVO.setApprovalByQc(scrapMaterialReturnRejectionDTO.getApprovalByQc());

		scrapMaterialReturnRejectionVO.setReasonForRejection(scrapMaterialReturnRejectionDTO.getReasonForRejection());

		scrapMaterialReturnRejectionVO.setApprovalByPurchase(scrapMaterialReturnRejectionDTO.getApprovalByPurchase());

		scrapMaterialReturnRejectionVO.setOrgId(scrapMaterialReturnRejectionDTO.getOrgId());

		scrapMaterialReturnRejectionVO.setFinancialYear(scrapMaterialReturnRejectionDTO.getFinancialYear());

		scrapMaterialReturnRejectionVO.setActive(scrapMaterialReturnRejectionDTO.isActive());

		scrapMaterialReturnRejectionVO.setCancelRemarks(scrapMaterialReturnRejectionDTO.getCancelRemarks());

		scrapMaterialReturnRejectionVO.setScreenCode("SMRR");

		scrapMaterialReturnRejectionVO.setScreenName("SCRAP MATERIAL RETURN REJECTION");
	}

	private void saveScrapMaterialReturnRejectionDetails(ScrapMaterialReturnRejectionVO scrapMaterialReturnRejectionVO,
			List<ScrapMaterialReturnRejectionDetailsDTO> detailsDTOList) throws ApplicationException {

		List<ScrapMaterialReturnRejectionDetailsVO> detailsList = new ArrayList<>();

		for (ScrapMaterialReturnRejectionDetailsDTO detailsDTO : detailsDTOList) {

			ScrapMaterialReturnRejectionDetailsVO detailsVO = new ScrapMaterialReturnRejectionDetailsVO();

			if (detailsDTO.getItem() != null) {

				ItemMasterVO itemVO = itemRepo.findById(detailsDTO.getItem())
						.orElseThrow(() -> new ApplicationException("Invalid Item"));

				detailsVO.setItem(itemVO);
			}

			if (detailsDTO.getUnit() != null) {

				UnitMasterVO unitVO = unitMasterRepo.findById(detailsDTO.getUnit())
						.orElseThrow(() -> new ApplicationException("Invalid Unit"));

				detailsVO.setUnit(unitVO);
			}

			detailsVO.setAvailableStock(detailsDTO.getAvailableStock());

			detailsVO.setRecQty(detailsDTO.getRecQty());

			detailsVO.setCostRate(detailsDTO.getCostRate());

			/*
			 * Amount = Received Quantity × Cost Rate
			 */
			BigDecimal amount = BigDecimal.ZERO;

			if (detailsDTO.getRecQty() != null && detailsDTO.getCostRate() != null) {

				amount = detailsDTO.getRecQty().multiply(detailsDTO.getCostRate());
			}

			detailsVO.setAmount(amount);

			detailsVO.setNote(detailsDTO.getNote());

			detailsVO.setScrapMaterialReturnRejectionVO(scrapMaterialReturnRejectionVO);

			detailsList.add(detailsVO);
		}

		scrapMaterialReturnRejectionVO.setScrapMaterialReturnRejectionDetailsVO(detailsList);
	}

	private ScrapMaterialReturnRejectionResponseDTO scrapMaterialReturnRejectionResponse(
			ScrapMaterialReturnRejectionVO vo) {

		ScrapMaterialReturnRejectionResponseDTO response = new ScrapMaterialReturnRejectionResponseDTO();

		response.setId(vo.getId());

// Branch
		if (vo.getBranch() != null) {

			BranchResponseDTO branchResponse = new BranchResponseDTO();

			branchResponse.setId(vo.getBranch().getId());

			branchResponse.setBranchCode(vo.getBranch().getBranchCode());

			branchResponse.setBranchName(vo.getBranch().getBranchName());

			response.setBranch(branchResponse);
		}

// Entry For
		if (vo.getEntryFor() != null) {

			ListOfValuesDetailsResponseDTO entryFor = new ListOfValuesDetailsResponseDTO();

			entryFor.setId(vo.getEntryFor().getId());

			entryFor.setCode(vo.getEntryFor().getValueCode());

			entryFor.setDescription(vo.getEntryFor().getValueDescription());

			response.setEntryFor(entryFor);
		}

// Vendor
		if (vo.getVendorId() != null) {

			CustomerResponse1DTO vendor = new CustomerResponse1DTO();

			vendor.setId(vo.getVendorId().getId());

//			vendor.setCustomerCode(vo.getVendorId().getCustomerCode());

			vendor.setCustomerName(vo.getVendorId().getCustomerName());

			response.setVendorId(vendor);
		}

// To Location
		if (vo.getToLocation() != null) {

			LocationMasterResponseDTO toLocation = new LocationMasterResponseDTO();

			toLocation.setId(vo.getToLocation().getId());

			toLocation.setLocationName(vo.getToLocation().getLocationName());

			response.setToLocation(toLocation);
		}

// Vendor Location
		if (vo.getVendorLocation() != null) {

			LocationMasterResponseDTO vendorLocation = new LocationMasterResponseDTO();

			vendorLocation.setId(vo.getVendorLocation().getId());

			vendorLocation.setLocationName(vo.getVendorLocation().getLocationName());

			response.setVendorLocation(vendorLocation);
		}

		response.setEntryType(vo.getEntryType());

		response.setDocNo(vo.getDocNo());

		response.setDocumentDate(vo.getDocumentDate());

		response.setApprovalByQc(vo.getApprovalByQc());

		response.setReasonForRejection(vo.getReasonForRejection());

		response.setApprovalByPurchase(vo.getApprovalByPurchase());

		response.setOrgId(vo.getOrgId());

		response.setFinancialYear(vo.getFinancialYear());

		response.setActive(vo.getActive());

		response.setCancelRemarks(vo.getCancelRemarks());

		response.setCreatedBy(vo.getCreatedBy());

		/*
		 * Details
		 */
		List<ScrapMaterialReturnRejectionDetailsResponseDTO> detailsResponseList = new ArrayList<>();

		if (vo.getScrapMaterialReturnRejectionDetailsVO() != null
				&& !vo.getScrapMaterialReturnRejectionDetailsVO().isEmpty()) {

			for (ScrapMaterialReturnRejectionDetailsVO detailsVO : vo.getScrapMaterialReturnRejectionDetailsVO()) {

				ScrapMaterialReturnRejectionDetailsResponseDTO detailsResponse = new ScrapMaterialReturnRejectionDetailsResponseDTO();

				detailsResponse.setId(detailsVO.getId());

				// Item
				if (detailsVO.getItem() != null) {

					ItemResponse1DTO itemResponse = new ItemResponse1DTO();

					itemResponse.setId(detailsVO.getItem().getId());

					itemResponse.setItemCode(detailsVO.getItem().getItemCode());

					itemResponse.setItemDescription(detailsVO.getItem().getItemDescription());

					if (detailsVO.getItem().getPrimaryUnit() != null) {

						UnitMasterResponseDTO unitResponse = new UnitMasterResponseDTO();

						unitResponse.setId(detailsVO.getItem().getPrimaryUnit().getId());

						unitResponse.setUnitId(detailsVO.getItem().getPrimaryUnit().getUnitId());

						unitResponse.setUnitDescription(detailsVO.getItem().getPrimaryUnit().getDescription());

						itemResponse.setUnit(unitResponse);
					}

					detailsResponse.setItem(itemResponse);
				}

				// Unit
				if (detailsVO.getUnit() != null) {

					UnitResponseDTO unitResponse = new UnitResponseDTO();

					unitResponse.setId(detailsVO.getUnit().getId());

					unitResponse.setUnitId(detailsVO.getUnit().getUnitId());

//					unitResponse.setDescription(detailsVO.getUnit().getDescription());

					detailsResponse.setUnit(unitResponse);
				}

				detailsResponse.setAvailableStock(detailsVO.getAvailableStock());

				detailsResponse.setRecQty(detailsVO.getRecQty());

				detailsResponse.setCostRate(detailsVO.getCostRate());

				detailsResponse.setAmount(detailsVO.getAmount());

				detailsResponse.setNote(detailsVO.getNote());

				detailsResponseList.add(detailsResponse);
			}
		}

		response.setScrapMaterialReturnRejectionDetailsResponseDTO(detailsResponseList);

		return response;
	}

	@Override
	public Map<String, Object> getScrapMaterialReturnRejectionById(Long id) throws ApplicationException {

		ScrapMaterialReturnRejectionVO scrapMaterialReturnRejectionVO = scrapMaterialReturnRejectionRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Scrap Material Return Rejection Not Found"));

		Map<String, Object> responseObjectsMap = new HashMap<>();

		responseObjectsMap.put("scrapMaterialReturnRejectionVO",
				scrapMaterialReturnRejectionResponse(scrapMaterialReturnRejectionVO));

		return responseObjectsMap;
	}

	@Override
	public List<ScrapMaterialReturnRejectionResponseDTO> getScrapMaterialReturnRejectionByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<ScrapMaterialReturnRejectionVO> scrapMaterialReturnRejectionList = scrapMaterialReturnRejectionRepo
				.findByOrgIdAndBranch(orgId, branch);

		List<ScrapMaterialReturnRejectionResponseDTO> responseList = new ArrayList<>();

		for (ScrapMaterialReturnRejectionVO vo : scrapMaterialReturnRejectionList) {

			responseList.add(scrapMaterialReturnRejectionResponse(vo));
		}

		return responseList;
	}

	@Override
	public Map<String, Object> getScrapMaterialReturnRejectionDocId(Long orgId, String financialYear)
			throws ApplicationException {

		String screenCode = "SMRR";

		String docId = scrapMaterialReturnRejectionRepo.getScrapMaterialReturnRejectionDocId(orgId, financialYear,
				screenCode);

		if (StringUtils.isBlank(docId)) {

			throw new ApplicationException("Scrap Material Return Rejection DocId Not Found");
		}

		Map<String, Object> responseObjectsMap = new HashMap<>();

		responseObjectsMap.put("docId", docId);

		return responseObjectsMap;
	}

//	OrderAcceptanceForapprovalreports
	@Override
	public Map<String, Object> getOrderAcceptanceForGstApprovalReport(String fromDate, String toDate, Long orgId,
			Long branch) throws ApplicationException {

		List<Object[]> resultList = orderAcceptanceRepo.getOrderAcceptanceForGstApproval(fromDate, toDate, orgId,
				branch);

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : resultList) {

			Map<String, Object> data = new HashMap<>();

			data.put("docId", obj[0]);
			data.put("docDate", obj[1]);
			data.put("customerId", obj[2]);
			data.put("customerName", obj[3]);
			data.put("Approved", obj[4]);
			data.put("orderAcceptanceBasicId", obj[5]);
			data.put("note", obj[6]);

			responseList.add(data);
		}

		Map<String, Object> responseObjectsMap = new HashMap<>();

		responseObjectsMap.put("orderAcceptanceList", responseList);

		return responseObjectsMap;
	}

//	SalesContractApprovalReport

	@Override
	public Map<String, Object> getSalesContractForApproval(Long branch, Long orgId, String fromDate,
			String toDate) throws ApplicationException {

		List<Object[]> resultList = salesContractRepo.getSalesContractForApproval(branch, orgId, fromDate, toDate);

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : resultList) {

			Map<String, Object> data = new HashMap<>();

			data.put("docId", obj[0]);
			data.put("docDate", obj[1]);
			data.put("customerPurchaseOrderDate", obj[2]);
			data.put("customerId", obj[3]);
			data.put("customerName", obj[4]);
			data.put("approval", obj[5]);
			data.put("salesContractId", obj[6]);
			data.put("notes", obj[7]);
			data.put("customerontractNo",obj[8]);

			responseList.add(data);
		}

		Map<String, Object> responseObjectsMap = new HashMap<>();

		responseObjectsMap.put("salesContractList", responseList);

		return responseObjectsMap;
	}

}
