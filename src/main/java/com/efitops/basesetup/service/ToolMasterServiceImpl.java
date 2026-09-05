package com.efitops.basesetup.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.CustomerResponse1DTO;
import com.efitops.basesetup.ResponseDTO.DepartmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.EmployeeDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.EngineeringChangeRecordResponseDTO;
import com.efitops.basesetup.ResponseDTO.EngineeringDeviationRequestResponseDTO;
import com.efitops.basesetup.ResponseDTO.ItemResponse1DTO;
import com.efitops.basesetup.ResponseDTO.ListOfValuesDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.LocationMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.ToolMasterAttachementResponseDTO;
import com.efitops.basesetup.ResponseDTO.ToolMasterComponentOutPutDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.ToolMasterMachineHistoryDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.ToolMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.ToolMasterSpareDetailsResponseDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.EngineeringChangeRecordAttachmentDTO;
import com.efitops.basesetup.dto.EngineeringChangeRecordDTO;
import com.efitops.basesetup.dto.EngineeringDeviationAttachmentDTO;
import com.efitops.basesetup.dto.EngineeringDeviationRequestDTO;
import com.efitops.basesetup.dto.ToolMasterComponentOutPutDetailsDTO;
import com.efitops.basesetup.dto.ToolMasterDTO;
import com.efitops.basesetup.dto.ToolMasterMachineHistoryDetailsDTO;
import com.efitops.basesetup.dto.ToolMasterSpareDetailsDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.EngineeringChangeRecordAttachmentVO;
import com.efitops.basesetup.entity.EngineeringChangeRecordVO;
import com.efitops.basesetup.entity.EngineeringDeviationAttachmentVO;
import com.efitops.basesetup.entity.EngineeringDeviationRequestVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.ListOfValuesDetailsVO;
import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.entity.ToolMasterAttachementVO;
import com.efitops.basesetup.entity.ToolMasterComponentOutPutDetailsVO;
import com.efitops.basesetup.entity.ToolMasterMachineHistoryDetailsVO;
import com.efitops.basesetup.entity.ToolMasterSpareDetailsVO;
import com.efitops.basesetup.entity.ToolMasterVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.DepartmentRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.EmployeeMasterRepo;
import com.efitops.basesetup.repository.EngineeringChangeRecordRepo;
import com.efitops.basesetup.repository.EngineeringDeviationRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.ListOfValuesDetailsRepo;
import com.efitops.basesetup.repository.LocationRepo;
import com.efitops.basesetup.repository.ToolMasterAttachmentsRepo;
import com.efitops.basesetup.repository.ToolMasterComponentOutputRepo;
import com.efitops.basesetup.repository.ToolMasterMachineHistoryDetailsRepo;
import com.efitops.basesetup.repository.ToolMasterRepo;
import com.efitops.basesetup.repository.ToolMasterSpareDetailsRepo;
import com.efitops.basesetup.repository.UnitMasterRepo;

@Service
public class ToolMasterServiceImpl implements ToolMasterService {

	@Autowired
	private ToolMasterRepo toolMasterRepo;

	@Autowired
	private ToolMasterSpareDetailsRepo toolMasterSpareDetailsRepo;

	@Autowired
	private ToolMasterComponentOutputRepo toolMasterComponentOutputRepo;

	@Autowired
	private ToolMasterMachineHistoryDetailsRepo toolMasterMachineHistoryDetailsRepo;

	@Autowired
	private ToolMasterAttachmentsRepo toolMasterAttachmentsRepo;

	@Autowired
	private BranchRepo branchRepo;

	@Autowired
	private DepartmentRepo departmentRepo;

	@Autowired
	private ListOfValuesDetailsRepo listOfValuesDetailsRepo;

	@Autowired
	private LocationRepo locationRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private EmployeeMasterRepo employeeMasterRepo;

	@Autowired
	private ItemMasterRepo itemRepo;

	@Autowired
	private UnitMasterRepo unitMasterRepo;

	@Autowired
	private EngineeringChangeRecordRepo engineeringChangeRecordRepo;

	@Autowired
	private DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	private EngineeringDeviationRepo engineeringDeviationRepo;

	@Value("${toolmaster.upload.path}")
	private String uploadPath;

	@Override
	@Transactional
	public Map<String, Object> updateCreateToolMaster(ToolMasterDTO toolMasterDTO, MultipartFile[] files)
			throws ApplicationException {

		ToolMasterVO toolMasterVO = new ToolMasterVO();

		String screenCode = "TM";
		String message;

		if (ObjectUtils.isEmpty(toolMasterDTO.getId())) {

			toolMasterVO.setCreatedBy(toolMasterDTO.getCreatedBy());
			toolMasterVO.setUpdatedBy(toolMasterDTO.getCreatedBy());

			message = "Tool Master Created Successfully";

		} else {

			toolMasterVO = toolMasterRepo.findById(toolMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Tool Master"));

			toolMasterVO.setUpdatedBy(toolMasterDTO.getCreatedBy());

			message = "Tool Master Updated Successfully";

		}

		createUpdateToolMasterVO(toolMasterDTO, toolMasterVO);

		ToolMasterVO savedVO = toolMasterRepo.save(toolMasterVO);

		if (files != null && files.length > 0) {
			saveAttachments(files, savedVO);
		}

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);
		response.put("toolMasterVO", toolMasterResponse(savedVO));

		return response;
	}

	private void createUpdateToolMasterVO(ToolMasterDTO dto, ToolMasterVO vo) throws ApplicationException {

		/*
		 * Branch
		 */
		if (dto.getBranch() != null) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			vo.setBranch(branch);
		}

		/*
		 * Type
		 */
		if (dto.getType() != null) {

			ListOfValuesDetailsVO type = listOfValuesDetailsRepo.findById(dto.getType())
					.orElseThrow(() -> new ApplicationException("Type Not Found"));

			vo.setType(type);
		}

		/*
		 * Department
		 */
		if (dto.getDepartment() != null) {

			DepartmentVO department = departmentRepo.findById(dto.getDepartment())
					.orElseThrow(() -> new ApplicationException("Department Not Found"));

			vo.setDepartment(department);
		}

		/*
		 * Location
		 */
		if (dto.getLocation() != null) {

			LocationVO location = locationRepo.findById(dto.getLocation())
					.orElseThrow(() -> new ApplicationException("Location Not Found"));

			vo.setLocation(location);
		}

		/*
		 * Made In
		 */
		if (dto.getMadeIn() != null) {

			ListOfValuesDetailsVO madeIn = listOfValuesDetailsRepo.findById(dto.getMadeIn())
					.orElseThrow(() -> new ApplicationException("Made In Not Found"));

			vo.setMadeIn(madeIn);
		}

		/*
		 * Purchase From
		 */
		if (dto.getPurchaseFrom() != null) {

			CustomerVO purchaseFrom = customerRepo.findById(dto.getPurchaseFrom())
					.orElseThrow(() -> new ApplicationException("Purchase From Not Found"));

			vo.setPurchaseFrom(purchaseFrom);
		}

		/*
		 * Mode Of Purchase
		 */
		if (dto.getModeOfPurchase() != null) {

			ListOfValuesDetailsVO modeOfPurchase = listOfValuesDetailsRepo.findById(dto.getModeOfPurchase())
					.orElseThrow(() -> new ApplicationException("Mode Of Purchase Not Found"));

			vo.setModeOfPurchase(modeOfPurchase);
		}

		/*
		 * Tool Incharge
		 */
		if (dto.getToolIncharge() != null) {

			EmployeeMasterVO toolIncharge = employeeMasterRepo.findById(dto.getToolIncharge())
					.orElseThrow(() -> new ApplicationException("Tool Incharge Not Found"));

			vo.setToolIncharge(toolIncharge);
		}

		/*
		 * Tool Ownership
		 */
		if (dto.getToolOwnership() != null) {

			CustomerVO toolOwnership = customerRepo.findById(dto.getToolOwnership())
					.orElseThrow(() -> new ApplicationException("Tool Ownership Not Found"));

			vo.setToolOwnership(toolOwnership);
		}

		/*
		 * Present Location
		 */
		if (dto.getPresentLocation() != null) {

			LocationVO presentLocation = locationRepo.findById(dto.getPresentLocation())
					.orElseThrow(() -> new ApplicationException("Present Location Not Found"));

			vo.setPresentLocation(presentLocation);
		}

		/*
		 * Basic Details
		 */
		vo.setToolNo(dto.getToolNo());
		vo.setToolDescription(dto.getToolDescription());
		vo.setPMChecklistNo(dto.getPMChecklistNo());
		vo.setToolCategory(dto.getToolCategory());
		vo.setDrawingNo(dto.getDrawingNo());
		vo.setSerialNo(dto.getSerialNo());
		vo.setManufacturedBy(dto.getManufacturedBy());
		vo.setSection(dto.getSection());
		vo.setStatus(dto.getStatus());
		vo.setToolUsedFor(dto.getToolUsedFor());
		vo.setToolCost(dto.getToolCost());
		vo.setCavityNumber(dto.getCavityNumber());
		vo.setRemarks(dto.getRemarks());
		vo.setToolName(dto.getToolName());
		vo.setImage(dto.getImage());
		vo.setOrgId(dto.getOrgId());
		vo.setFinancialYear(dto.getFinancialYear());
		vo.setActive(dto.isActive());
		vo.setCancelRemarks(dto.getCancelRemarks());

		/*
		 * Technical Information - Header Fields
		 */
		vo.setToolWeight(dto.getToolWeight());
		vo.setToolFixtureSize(dto.getToolFixtureSize());
		vo.setLifeOfTool(dto.getLifeOfTool());
		vo.setReconditionFreq(dto.getReconditionFreq());
		vo.setSetUpTimeInMinutes(dto.getSetUpTimeInMinutes());
		vo.setCompletedLifeCycle(dto.getCompletedLifeCycle());
		vo.setToolMadeOf(dto.getToolMadeOf());
		vo.setTechnicalSpecification(dto.getTechnicalSpecification());
		vo.setNoOfStokesCompleted(dto.getNoOfStokesCompleted());
		vo.setStrokesCompletedAfterReconditioning(dto.getStrokesCompletedAfterReconditioning());
		vo.setReconditionedDate(dto.getReconditionedDate());
		vo.setToolFixtureCost(dto.getToolFixtureCost());
		vo.setToolFixtureAmortizedRecovered(dto.getToolFixtureAmortizedRecovered());

		/*
		 * Unit
		 */
		if (dto.getUnit() != null) {

			UnitMasterVO unit = unitMasterRepo.findById(dto.getUnit())
					.orElseThrow(() -> new ApplicationException("Unit Not Found"));

			vo.setUnit(unit);
		}

		/*
		 * Life Type
		 */
		if (dto.getLifeType() != null) {

			ListOfValuesDetailsVO lifeType = listOfValuesDetailsRepo.findById(dto.getLifeType())
					.orElseThrow(() -> new ApplicationException("Life Type Not Found"));

			vo.setLifeType(lifeType);
		}

		/*
		 * Spare Details
		 */
		vo.getToolMasterSpareDetailsVO().clear();

		if (dto.getToolMasterSpareDetailsDTO() != null) {

			for (ToolMasterSpareDetailsDTO detailDTO : dto.getToolMasterSpareDetailsDTO()) {

				ToolMasterSpareDetailsVO detailVO = new ToolMasterSpareDetailsVO();

				if (detailDTO.getSparePartId() != null) {

					ItemMasterVO sparePart = itemRepo.findById(detailDTO.getSparePartId())
							.orElseThrow(() -> new ApplicationException("Spare Part Not Found"));

					detailVO.setSparePartId(sparePart);
				}

				detailVO.setModelNo(detailDTO.getModelNo());
				detailVO.setSerialNo(detailDTO.getSerialNo());
				detailVO.setManufacturer(detailDTO.getManufacturer());
				detailVO.setWarrantyTillDate(detailDTO.getWarrantyTillDate());
				detailVO.setCalibrationReq(detailDTO.getCalibrationReq());
				detailVO.setLastCalibDate(detailDTO.getLastCalibDate());
				detailVO.setNextCalibDate(detailDTO.getNextCalibDate());

				detailVO.setToolMasterVO(vo);

				vo.getToolMasterSpareDetailsVO().add(detailVO);
			}
		}

		/*
		 * Component Output Details
		 */
		vo.getToolMasterComponentOutPutDetailsVO().clear();

		if (dto.getToolMasterComponentOutPutDetailsDTO() != null) {

			for (ToolMasterComponentOutPutDetailsDTO detailDTO : dto.getToolMasterComponentOutPutDetailsDTO()) {

				ToolMasterComponentOutPutDetailsVO detailVO = new ToolMasterComponentOutPutDetailsVO();

				if (detailDTO.getItem() != null) {

					ItemMasterVO item = itemRepo.findById(detailDTO.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));

					detailVO.setItem(item);
				}

				detailVO.setToolMasterVO(vo);

				vo.getToolMasterComponentOutPutDetailsVO().add(detailVO);
			}
		}

		/*
		 * Machine History Details
		 */
		vo.getToolMasterMachineHistoryDetailsVO().clear();

		if (dto.getToolMasterMachineHistoryDetailsDTO() != null) {

			for (ToolMasterMachineHistoryDetailsDTO detailDTO : dto.getToolMasterMachineHistoryDetailsDTO()) {

				ToolMasterMachineHistoryDetailsVO detailVO = new ToolMasterMachineHistoryDetailsVO();

				detailVO.setDate(detailDTO.getDate());
				detailVO.setDescription(detailDTO.getDescription());
				detailVO.setChangedDate(detailDTO.getChangedDate());
				detailVO.setCost(detailDTO.getCost());
				detailVO.setPurpose(detailDTO.getPurpose());
				detailVO.setRemarks(detailDTO.getRemarks());

				detailVO.setToolMasterVO(vo);

				vo.getToolMasterMachineHistoryDetailsVO().add(detailVO);
			}
		}

	}

	@Value("${toolmaster.upload.path}")
	private String toolMasterUploadPath;

	private void saveAttachments(MultipartFile[] files, ToolMasterVO toolMasterVO) throws ApplicationException {

		if (files == null || files.length == 0) {
			return;
		}

		try {

			File directory = new File(toolMasterUploadPath);

			if (!directory.exists()) {
				directory.mkdirs();
			}

			for (MultipartFile file : files) {

				if (file.isEmpty()) {
					continue;
				}

				String originalName = file.getOriginalFilename();

				String extension = "";

				if (originalName != null && originalName.contains(".")) {
					extension = originalName.substring(originalName.lastIndexOf("."));
				}

				String fileName = UUID.randomUUID().toString() + extension;

				Path path = Paths.get(toolMasterUploadPath, fileName);

				try (InputStream inputStream = file.getInputStream()) {

					Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
				}

				ToolMasterAttachementVO attachmentVO = new ToolMasterAttachementVO();

				attachmentVO.setName(originalName);
				attachmentVO.setFileName(fileName);
				attachmentVO.setFilePath(path.toString());
				attachmentVO.setFileSize(file.getSize());
				attachmentVO.setUploadOn(LocalDateTime.now());
				attachmentVO.setToolMasterVO(toolMasterVO);

				toolMasterVO.getToolMasterAttachementVO().add(attachmentVO);
			}

			toolMasterRepo.save(toolMasterVO);

		} catch (IOException e) {

			throw new ApplicationException("Unable to Save Attachment");
		}
	}

	private ToolMasterResponseDTO toolMasterResponse(ToolMasterVO vo) {

		ToolMasterResponseDTO dto = new ToolMasterResponseDTO();

		dto.setId(vo.getId());

		/*
		 * Branch
		 */
		if (vo.getBranch() != null) {

			BranchResponseDTO branchDTO = new BranchResponseDTO();

			branchDTO.setId(vo.getBranch().getId());
			branchDTO.setBranchCode(vo.getBranch().getBranchCode());
			branchDTO.setBranchName(vo.getBranch().getBranchName());

			dto.setBranch(branchDTO);
		}

		/*
		 * Type
		 */
		if (vo.getType() != null) {

			ListOfValuesDetailsResponseDTO typeDTO = new ListOfValuesDetailsResponseDTO();

			typeDTO.setId(vo.getType().getId());
			typeDTO.setCode(vo.getType().getValueCode());
			typeDTO.setDescription(vo.getType().getValueDescription());

			dto.setType(typeDTO);
		}

		/*
		 * Department
		 */
		if (vo.getDepartment() != null) {

			DepartmentResponseDTO departmentDTO = new DepartmentResponseDTO();

			departmentDTO.setId(vo.getDepartment().getId());
			departmentDTO.setDepartmentCode(vo.getDepartment().getDepartmentCode());
			departmentDTO.setDepartmentName(vo.getDepartment().getDepartmentName());

			dto.setDepartment(departmentDTO);
		}

		/*
		 * Location
		 */
		if (vo.getLocation() != null) {

			LocationMasterResponseDTO locationDTO = new LocationMasterResponseDTO();

			locationDTO.setId(vo.getLocation().getId());
			locationDTO.setLocationName(vo.getLocation().getLocationName());

			dto.setLocation(locationDTO);
		}

		/*
		 * Basic Details
		 */
		dto.setToolNo(vo.getToolNo());
		dto.setToolDescription(vo.getToolDescription());
		dto.setPMChecklistNo(vo.getPMChecklistNo());
		dto.setToolCategory(vo.getToolCategory());
		dto.setDrawingNo(vo.getDrawingNo());
		dto.setSerialNo(vo.getSerialNo());
		dto.setManufacturedBy(vo.getManufacturedBy());
		dto.setSection(vo.getSection());
		dto.setStatus(vo.getStatus());

		/*
		 * Made In
		 */
		if (vo.getMadeIn() != null) {

			ListOfValuesDetailsResponseDTO madeInDTO = new ListOfValuesDetailsResponseDTO();

			madeInDTO.setId(vo.getMadeIn().getId());
			madeInDTO.setCode(vo.getMadeIn().getValueCode());
			madeInDTO.setDescription(vo.getMadeIn().getValueDescription());

			dto.setMadeIn(madeInDTO);
		}

		/*
		 * Purchase From
		 */
		if (vo.getPurchaseFrom() != null) {

			CustomerResponse1DTO customerDTO = new CustomerResponse1DTO();

			customerDTO.setId(vo.getPurchaseFrom().getId());
//			customerDTO.setCustomerCode(vo.getPurchaseFrom().getCustomerCode());
			customerDTO.setCustomerName(vo.getPurchaseFrom().getCustomerName());

			dto.setPurchaseFrom(customerDTO);
		}

		/*
		 * Mode Of Purchase
		 */
		if (vo.getModeOfPurchase() != null) {

			ListOfValuesDetailsResponseDTO modeDTO = new ListOfValuesDetailsResponseDTO();

			modeDTO.setId(vo.getModeOfPurchase().getId());
			modeDTO.setCode(vo.getModeOfPurchase().getValueCode());
			modeDTO.setDescription(vo.getModeOfPurchase().getValueDescription());

			dto.setModeOfPurchase(modeDTO);
		}

		/*
		 * Tool Incharge
		 */
		if (vo.getToolIncharge() != null) {

			EmployeeDropdownResponseDTO employeeDTO = new EmployeeDropdownResponseDTO();

			employeeDTO.setEmployeeId(vo.getToolIncharge().getId());
			employeeDTO.setEmployeeCode(vo.getToolIncharge().getEmployeeId());
			employeeDTO.setEmployeeName(vo.getToolIncharge().getEmployeeName());
			employeeDTO.setEmail(vo.getToolIncharge().getEmail());

			dto.setToolIncharge(employeeDTO);
		}

		dto.setToolUsedFor(vo.getToolUsedFor());

		/*
		 * Tool Ownership
		 */
		if (vo.getToolOwnership() != null) {

			CustomerResponse1DTO customerDTO = new CustomerResponse1DTO();

			customerDTO.setId(vo.getToolOwnership().getId());
//			customerDTO.setCustomerCode(vo.getToolOwnership().getCustomerCode());
			customerDTO.setCustomerName(vo.getToolOwnership().getCustomerName());

			dto.setToolOwnership(customerDTO);
		}

		/*
		 * Present Location
		 */
		if (vo.getPresentLocation() != null) {

			LocationMasterResponseDTO locationDTO = new LocationMasterResponseDTO();

			locationDTO.setId(vo.getPresentLocation().getId());
			locationDTO.setLocationName(vo.getPresentLocation().getLocationName());

			dto.setPresentLocation(locationDTO);
		}

		dto.setToolCost(vo.getToolCost());
		dto.setCavityNumber(vo.getCavityNumber());
		dto.setRemarks(vo.getRemarks());
		dto.setToolName(vo.getToolName());
		dto.setImage(vo.getImage());
		dto.setOrgId(vo.getOrgId());
		dto.setFinancialYear(vo.getFinancialYear());

		/*
		 * IMPORTANT: ToolMasterVO has custom getActive() returning String. Therefore
		 * use isActive() here.
		 */
		dto.setActive(vo.getActive());

		dto.setCreatedBy(vo.getCreatedBy());
		dto.setCancelRemarks(vo.getCancelRemarks());

		/*
		 * Technical Information - Header Fields
		 */

		dto.setToolWeight(vo.getToolWeight());
		dto.setToolFixtureSize(vo.getToolFixtureSize());
		dto.setLifeOfTool(vo.getLifeOfTool());
		dto.setReconditionFreq(vo.getReconditionFreq());
		dto.setSetUpTimeInMinutes(vo.getSetUpTimeInMinutes());
		dto.setCompletedLifeCycle(vo.getCompletedLifeCycle());
		dto.setToolMadeOf(vo.getToolMadeOf());
		dto.setTechnicalSpecification(vo.getTechnicalSpecification());
		dto.setNoOfStokesCompleted(vo.getNoOfStokesCompleted());
		dto.setStrokesCompletedAfterReconditioning(vo.getStrokesCompletedAfterReconditioning());
		dto.setReconditionedDate(vo.getReconditionedDate());
		dto.setToolFixtureCost(vo.getToolFixtureCost());
		dto.setToolFixtureAmortizedRecovered(vo.getToolFixtureAmortizedRecovered());

		/*
		 * Unit
		 */

		if (vo.getUnit() != null) {

			UnitMasterResponseDTO unitDTO = new UnitMasterResponseDTO();

			unitDTO.setId(vo.getUnit().getId());
			unitDTO.setUnitId(vo.getUnit().getUnitId());
			unitDTO.setUnitDescription(vo.getUnit().getDescription());

			dto.setUnit(unitDTO);
		}

		/*
		 * Life Type
		 */

		if (vo.getLifeType() != null) {

			ListOfValuesDetailsResponseDTO lifeTypeDTO = new ListOfValuesDetailsResponseDTO();

			lifeTypeDTO.setId(vo.getLifeType().getId());
			lifeTypeDTO.setCode(vo.getLifeType().getValueCode());
			lifeTypeDTO.setDescription(vo.getLifeType().getValueDescription());

			dto.setLifeType(lifeTypeDTO);
		}

		/*
		 * Spare Details Response
		 */
		List<ToolMasterSpareDetailsResponseDTO> spareList = new ArrayList<>();

		if (vo.getToolMasterSpareDetailsVO() != null) {

			for (ToolMasterSpareDetailsVO detailVO : vo.getToolMasterSpareDetailsVO()) {

				ToolMasterSpareDetailsResponseDTO detailDTO = new ToolMasterSpareDetailsResponseDTO();

				detailDTO.setId(detailVO.getId());
				detailDTO.setModelNo(detailVO.getModelNo());
				detailDTO.setSerialNo(detailVO.getSerialNo());
				detailDTO.setManufacturer(detailVO.getManufacturer());
				detailDTO.setWarrantyTillDate(detailVO.getWarrantyTillDate());
				detailDTO.setCalibrationReq(detailVO.getCalibrationReq());
				detailDTO.setLastCalibDate(detailVO.getLastCalibDate());
				detailDTO.setNextCalibDate(detailVO.getNextCalibDate());

				if (detailVO.getSparePartId() != null) {

					ItemMasterVO item = detailVO.getSparePartId();

					ItemResponse1DTO itemDTO = new ItemResponse1DTO();

					itemDTO.setId(item.getId());
					itemDTO.setItemCode(item.getItemCode());
					itemDTO.setItemDescription(item.getItemDescription());

					if (item.getPrimaryUnit() != null) {

						UnitMasterResponseDTO unitDTO = new UnitMasterResponseDTO();

						unitDTO.setId(item.getPrimaryUnit().getId());
						unitDTO.setUnitId(item.getPrimaryUnit().getUnitId());
						unitDTO.setUnitDescription(item.getPrimaryUnit().getDescription());

						itemDTO.setUnit(unitDTO);
					}

					detailDTO.setSparePartId(itemDTO);
				}

				spareList.add(detailDTO);
			}
		}

		dto.setToolMasterSpareDetailsDTO(spareList);

		/*
		 * Component Output Response
		 */
		List<ToolMasterComponentOutPutDetailsResponseDTO> componentList = new ArrayList<>();

		if (vo.getToolMasterComponentOutPutDetailsVO() != null) {

			for (ToolMasterComponentOutPutDetailsVO detailVO : vo.getToolMasterComponentOutPutDetailsVO()) {

				ToolMasterComponentOutPutDetailsResponseDTO detailDTO = new ToolMasterComponentOutPutDetailsResponseDTO();

				detailDTO.setId(detailVO.getId());

				if (detailVO.getItem() != null) {

					ItemMasterVO item = detailVO.getItem();

					ItemResponse1DTO itemDTO = new ItemResponse1DTO();

					itemDTO.setId(item.getId());
					itemDTO.setItemCode(item.getItemCode());
					itemDTO.setItemDescription(item.getItemDescription());

					if (item.getPrimaryUnit() != null) {

						UnitMasterResponseDTO unitDTO = new UnitMasterResponseDTO();

						unitDTO.setId(item.getPrimaryUnit().getId());
						unitDTO.setUnitId(item.getPrimaryUnit().getUnitId());
						unitDTO.setUnitDescription(item.getPrimaryUnit().getDescription());

						itemDTO.setUnit(unitDTO);
					}

					detailDTO.setItem(itemDTO);
				}

				componentList.add(detailDTO);
			}
		}

		dto.setToolMasterComponentOutPutDetailsDTO(componentList);

		/*
		 * Machine History Response
		 */
		List<ToolMasterMachineHistoryDetailsResponseDTO> historyList = new ArrayList<>();

		if (vo.getToolMasterMachineHistoryDetailsVO() != null) {

			for (ToolMasterMachineHistoryDetailsVO detailVO : vo.getToolMasterMachineHistoryDetailsVO()) {

				ToolMasterMachineHistoryDetailsResponseDTO detailDTO = new ToolMasterMachineHistoryDetailsResponseDTO();

				detailDTO.setId(detailVO.getId());
				detailDTO.setDate(detailVO.getDate());
				detailDTO.setDescription(detailVO.getDescription());
				detailDTO.setChangedDate(detailVO.getChangedDate());
				detailDTO.setCost(detailVO.getCost());
				detailDTO.setPurpose(detailVO.getPurpose());
				detailDTO.setRemarks(detailVO.getRemarks());

				historyList.add(detailDTO);
			}
		}

		dto.setToolMasterMachineHistoryDetailsDTO(historyList);

		/*
		 * Attachment Response
		 */
		List<ToolMasterAttachementResponseDTO> attachmentList = new ArrayList<>();

		if (vo.getToolMasterAttachementVO() != null) {

			for (ToolMasterAttachementVO attachmentVO : vo.getToolMasterAttachementVO()) {

				ToolMasterAttachementResponseDTO attachmentDTO = new ToolMasterAttachementResponseDTO();

				attachmentDTO.setId(attachmentVO.getId());
				attachmentDTO.setName(attachmentVO.getName());
				attachmentDTO.setFileName(attachmentVO.getFileName());
				attachmentDTO.setFilePath(attachmentVO.getFilePath());
				attachmentDTO.setFileSize(attachmentVO.getFileSize());
				attachmentDTO.setUploadOn(attachmentVO.getUploadOn());

				attachmentList.add(attachmentDTO);
			}
		}

		dto.setToolMasterAttachementDTO(attachmentList);

		return dto;
	}

	@Override
	public ToolMasterResponseDTO getToolMasterById(Long id) throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {
			throw new ApplicationException("Invalid Id");
		}

		ToolMasterVO toolMasterVO = toolMasterRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Tool Master Not Found"));

		return toolMasterResponse(toolMasterVO);
	}

	@Override
	public List<ToolMasterResponseDTO> getToolMasterByOrgId(Long orgId, Long branch) throws ApplicationException {

		List<ToolMasterVO> toolMasterList = toolMasterRepo.getToolMasterByOrgId(orgId, branch);

		if (toolMasterList.isEmpty()) {
			throw new ApplicationException("No Tool Master Details Found");
		}

		List<ToolMasterResponseDTO> responseList = new ArrayList<>();

		for (ToolMasterVO toolMasterVO : toolMasterList) {

			responseList.add(toolMasterResponse(toolMasterVO));
		}

		return responseList;
	}

//	dropdown for the location

	@Override
	public List<Map<String, Object>> getLocationForToolMaster(Long orgId, Long branch) throws ApplicationException {

		List<Object[]> locationList = toolMasterRepo.getLocationForToolMaster(orgId, branch);

		if (locationList.isEmpty()) {
			throw new ApplicationException("No Location Details Found");
		}

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : locationList) {

			Map<String, Object> map = new HashMap<>();

			map.put("locationId", obj[0]);

			map.put("locationName", obj[1]);

			responseList.add(map);
		}

		return responseList;
	}

//	Engineering Change Record

	@Value("${server.base-url}")
	private String serverBaseUrl;

	@Override
	@Transactional
	public Map<String, Object> updateCreateEngineeringChangeRecord(
			EngineeringChangeRecordDTO engineeringChangeRecordDTO, MultipartFile[] files) throws ApplicationException {

		EngineeringChangeRecordVO engineeringChangeRecordVO = new EngineeringChangeRecordVO();

		String screenCode = "ECR";
		String message;

		if (ObjectUtils.isEmpty(engineeringChangeRecordDTO.getId())) {

			// =========================
			// Generate Doc ID
			// =========================

			String docId = engineeringChangeRecordRepo.getEngineeringChangeRecordDocId(
					engineeringChangeRecordDTO.getOrgId(), engineeringChangeRecordDTO.getFinancialYear(), screenCode);

			if (StringUtils.isBlank(docId)) {

				throw new ApplicationException("Engineering Change Record DocId Not Found");
			}

			engineeringChangeRecordVO.setDocId(docId);

			// =========================
			// Document Mapping
			// =========================

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(engineeringChangeRecordDTO.getOrgId(),
							engineeringChangeRecordDTO.getFinancialYear(), screenCode);

			if (documentTypeMappingDetailsVO == null) {

				throw new ApplicationException("Document Type Mapping Details Not Found");
			}

			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			engineeringChangeRecordVO.setCreatedBy(engineeringChangeRecordDTO.getCreatedBy());

			engineeringChangeRecordVO.setUpdatedBy(engineeringChangeRecordDTO.getCreatedBy());

			message = "Engineering Change Record Created Successfully";

		} else {

			engineeringChangeRecordVO = engineeringChangeRecordRepo.findById(engineeringChangeRecordDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Engineering Change Record"));

			engineeringChangeRecordVO.setUpdatedBy(engineeringChangeRecordDTO.getCreatedBy());

			message = "Engineering Change Record Updated Successfully";
		}

		createUpdateEngineeringChangeRecordVO(engineeringChangeRecordDTO, engineeringChangeRecordVO);

		EngineeringChangeRecordVO savedVO = engineeringChangeRecordRepo.save(engineeringChangeRecordVO);

		if (files != null && files.length > 0) {

			saveAttachments(files, savedVO);
		}

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("engineeringChangeRecordVO", engineeringChangeRecordResponse(savedVO));

		return response;
	}

	private void createUpdateEngineeringChangeRecordVO(EngineeringChangeRecordDTO dto, EngineeringChangeRecordVO vo)
			throws ApplicationException {

		/*
		 * Branch
		 */

		if (dto.getBranch() != null) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			vo.setBranch(branch);
		}

		/*
		 * Requested By
		 */

		if (dto.getRequestedBy() != null) {

			EmployeeMasterVO requestedBy = employeeMasterRepo.findById(dto.getRequestedBy())
					.orElseThrow(() -> new ApplicationException("Requested By Not Found"));

			vo.setRequestedBy(requestedBy);
		}

		/*
		 * Approved By
		 */

		if (dto.getApprovedBy() != null) {

			EmployeeMasterVO approvedBy = employeeMasterRepo.findById(dto.getApprovedBy())
					.orElseThrow(() -> new ApplicationException("Approved By Not Found"));

			vo.setApprovedBy(approvedBy);
		}

		/*
		 * Basic Details
		 */

//		vo.setDocId(dto.getDocId());
//
//		vo.setDocDate(dto.getDocDate());

		vo.setFromDepartment(dto.getFromDepartment());

		vo.setCustomerName(dto.getCustomerName());

		vo.setReasonForChange(dto.getReasonForChange());

		vo.setProductDescription(dto.getProductDescription());

		vo.setEngineeringDrawingChange(dto.getEngineeringDrawingChange());

		vo.setBomChange(dto.getBomChange());

		/*
		 * Remarks
		 */

		vo.setAccepted(dto.getAccepted());

		vo.setRejected(dto.getRejected());

		vo.setApproved(dto.getApproved());

		/*
		 * Product No Details
		 */

		vo.setCustomerProductNo(dto.getCustomerProductNo());

		vo.setCompanyProductNo(dto.getCompanyProductNo());

		/*
		 * Part No Details
		 */

		vo.setPartNo(dto.getPartNo());

		vo.setPartDescription(dto.getPartDescription());

		/*
		 * TDC Department
		 */

		vo.setCustomerApproval(dto.getCustomerApproval());

		vo.setDrawingWhichRequiredChange(dto.getDrawingWhichRequiredChange());

		vo.setDocumentWhichRequiredChange(dto.getDocumentWhichRequiredChange());

		/*
		 * Common Details
		 */

		vo.setActive(dto.isActive());

		vo.setOrgId(dto.getOrgId());

		vo.setCancelRemarks(dto.getCancelRemarks());

//		/*
//		 * Attachment Details
//		 */
//
//		vo.getEngineeringChangeRecordAttachmentVO().clear();
//
//		if (dto.getEngineeringChangeRecordAttachmentDTO() != null) {
//
//			for (EngineeringChangeRecordAttachmentDTO detailDTO : dto.getEngineeringChangeRecordAttachmentDTO()) {
//
//				EngineeringChangeRecordAttachmentVO detailVO = new EngineeringChangeRecordAttachmentVO();
//
//				detailVO.setName(detailDTO.getName());
//
//				detailVO.setFileName(detailDTO.getFileName());
//
//				detailVO.setFilePath(detailDTO.getFilePath());
//
//				detailVO.setFileSize(detailDTO.getFileSize());
//
//				detailVO.setContentType(detailDTO.getContentType());
//
//				detailVO.setUploadOn(detailDTO.getUploadOn());
//
//				detailVO.setEngineeringChangeRecordVO(vo);
//
//				vo.getEngineeringChangeRecordAttachmentVO().add(detailVO);
//			}
//		}
	}

	@Value("${engineeringchangerecord.upload.path}")
	private String engineeringChangeRecordUploadPath;

	private void saveAttachments(MultipartFile[] files, EngineeringChangeRecordVO engineeringChangeRecordVO)
			throws ApplicationException {

		if (files == null || files.length == 0) {
			return;
		}

		try {

			File directory = new File(engineeringChangeRecordUploadPath);

			if (!directory.exists()) {
				directory.mkdirs();
			}

			for (MultipartFile file : files) {

				if (file.isEmpty()) {
					continue;
				}

				String originalName = file.getOriginalFilename();

				String extension = "";

				if (originalName != null && originalName.contains(".")) {

					extension = originalName.substring(originalName.lastIndexOf("."));
				}

				String fileName = UUID.randomUUID().toString() + extension;

				Path path = Paths.get(engineeringChangeRecordUploadPath, fileName);

				try (InputStream inputStream = file.getInputStream()) {

					Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
				}

				EngineeringChangeRecordAttachmentVO attachmentVO = new EngineeringChangeRecordAttachmentVO();

				attachmentVO.setName(originalName);

				attachmentVO.setFileName(fileName);

				attachmentVO.setFilePath(path.toString());

				attachmentVO.setFileSize(file.getSize());

				attachmentVO.setContentType(file.getContentType());

				attachmentVO.setUploadOn(LocalDateTime.now());

				attachmentVO.setEngineeringChangeRecordVO(engineeringChangeRecordVO);

				engineeringChangeRecordVO.getEngineeringChangeRecordAttachmentVO().add(attachmentVO);
			}

			engineeringChangeRecordRepo.save(engineeringChangeRecordVO);

		} catch (IOException e) {

			throw new ApplicationException("Unable to Save Attachment");
		}
	}

	private EngineeringChangeRecordResponseDTO engineeringChangeRecordResponse(EngineeringChangeRecordVO vo) {

		EngineeringChangeRecordResponseDTO dto = new EngineeringChangeRecordResponseDTO();

		dto.setId(vo.getId());

		/*
		 * Branch
		 */

		if (vo.getBranch() != null) {

			BranchResponseDTO branchDTO = new BranchResponseDTO();

			branchDTO.setId(vo.getBranch().getId());

			branchDTO.setBranchCode(vo.getBranch().getBranchCode());

			branchDTO.setBranchName(vo.getBranch().getBranchName());

			dto.setBranch(branchDTO);
		}

		/*
		 * Basic Details
		 */

		dto.setDocId(vo.getDocId());

		dto.setDocDate(vo.getDocDate());

		dto.setFromDepartment(vo.getFromDepartment());

		dto.setCustomerName(vo.getCustomerName());

		dto.setReasonForChange(vo.getReasonForChange());

		dto.setProductDescription(vo.getProductDescription());

		dto.setEngineeringDrawingChange(vo.getEngineeringDrawingChange());

		dto.setBomChange(vo.getBomChange());

		/*
		 * Requested By
		 */

		if (vo.getRequestedBy() != null) {

			EmployeeDropdownResponseDTO employeeDTO = new EmployeeDropdownResponseDTO();

			employeeDTO.setEmployeeId(vo.getRequestedBy().getId());

			employeeDTO.setEmployeeCode(vo.getRequestedBy().getEmployeeId());

			employeeDTO.setEmployeeName(vo.getRequestedBy().getEmployeeName());

			employeeDTO.setEmail(vo.getRequestedBy().getEmail());

			dto.setRequestedBy(employeeDTO);
		}

		/*
		 * Remarks
		 */

		dto.setAccepted(vo.getAccepted());

		dto.setRejected(vo.getRejected());

		/*
		 * Approved By
		 */

		if (vo.getApprovedBy() != null) {

			EmployeeDropdownResponseDTO employeeDTO = new EmployeeDropdownResponseDTO();

			employeeDTO.setEmployeeId(vo.getApprovedBy().getId());

			employeeDTO.setEmployeeCode(vo.getApprovedBy().getEmployeeId());

			employeeDTO.setEmployeeName(vo.getApprovedBy().getEmployeeName());

			employeeDTO.setEmail(vo.getApprovedBy().getEmail());

			dto.setApprovedBy(employeeDTO);
		}

		dto.setApproved(vo.getApproved());

		/*
		 * Product No Details
		 */

		dto.setCustomerProductNo(vo.getCustomerProductNo());

		dto.setCompanyProductNo(vo.getCompanyProductNo());

		/*
		 * Part No Details
		 */

		dto.setPartNo(vo.getPartNo());

		dto.setPartDescription(vo.getPartDescription());

		/*
		 * TDC Department
		 */

		dto.setCustomerApproval(vo.getCustomerApproval());

		dto.setDrawingWhichRequiredChange(vo.getDrawingWhichRequiredChange());

		dto.setDocumentWhichRequiredChange(vo.getDocumentWhichRequiredChange());

		/*
		 * Common Details
		 */

		dto.setActive(vo.getActiveStatus());

		dto.setOrgId(vo.getOrgId());

		dto.setCreatedBy(vo.getCreatedBy());

		dto.setCancelRemarks(vo.getCancelRemarks());

		/*
		 * Attachment Response
		 */

		List<EngineeringChangeRecordAttachmentDTO> attachmentList = new ArrayList<>();

		if (vo.getEngineeringChangeRecordAttachmentVO() != null) {

			for (EngineeringChangeRecordAttachmentVO attachmentVO : vo.getEngineeringChangeRecordAttachmentVO()) {

				EngineeringChangeRecordAttachmentDTO attachmentDTO = new EngineeringChangeRecordAttachmentDTO();

				attachmentDTO.setId(attachmentVO.getId());

				attachmentDTO.setName(attachmentVO.getName());

				attachmentDTO.setFileName(attachmentVO.getFileName());

				String urlPath = engineeringChangeRecordUploadPath.replace("C:/", "/").replace("\\\\", "/");

				attachmentDTO.setFilePath(serverBaseUrl + urlPath + attachmentVO.getFileName());

				attachmentDTO.setFileSize(attachmentVO.getFileSize());

				attachmentDTO.setContentType(attachmentVO.getContentType());

				attachmentDTO.setUploadOn(attachmentVO.getUploadOn());

				attachmentList.add(attachmentDTO);
			}
		}

		dto.setEngineeringChangeRecordAttachmentDTO(attachmentList);
		return dto;
	}

	@Override
	public Map<String, Object> getEngineeringChangeRecordById(Long id) throws ApplicationException {

		Map<String, Object> response = new HashMap<>();

		EngineeringChangeRecordVO engineeringChangeRecordVO = engineeringChangeRecordRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Engineering Change Record not found"));

		EngineeringChangeRecordResponseDTO responseDTO = engineeringChangeRecordResponse(engineeringChangeRecordVO);

		response.put("engineeringChangeRecordVO", responseDTO);

		return response;
	}

	@Override
	public Map<String, Object> getEngineeringChangeRecordByOrgId(Long orgId, Long branch) throws ApplicationException {

		Map<String, Object> response = new HashMap<>();

		List<EngineeringChangeRecordVO> engineeringChangeRecordVOList = engineeringChangeRecordRepo
				.getEngineeringChangeRecordByOrgId(orgId, branch);

		List<EngineeringChangeRecordResponseDTO> responseDTOList = new ArrayList<>();

		for (EngineeringChangeRecordVO engineeringChangeRecordVO : engineeringChangeRecordVOList) {

			EngineeringChangeRecordResponseDTO responseDTO = engineeringChangeRecordResponse(engineeringChangeRecordVO);

			responseDTOList.add(responseDTO);
		}

		response.put("engineeringChangeRecordVO", responseDTOList);

		return response;
	}

	@Override
	public String getEngineeringChangeRecordDocId(Long orgId, String financialYear) {

		String screenCode = "ECR";

		String result = engineeringChangeRecordRepo.getEngineeringChangeRecordDocId(orgId, financialYear, screenCode);

		return result;
	}

//	engineering deviation request
	
	@Value("${engineeringdeviation.upload.path}")
	private String engineeringDeviationUploadPath; 

	@Override
	@Transactional
	public Map<String, Object> updateCreateEngineeringDeviation(
			EngineeringDeviationRequestDTO engineeringDeviationRequestDTO, MultipartFile[] files) throws Exception {

		EngineeringDeviationRequestVO engineeringDeviationRequestVO = new EngineeringDeviationRequestVO();

		String message;

		// ========================================================
		// CREATE / UPDATE
		// ========================================================

		if (ObjectUtils.isEmpty(engineeringDeviationRequestDTO.getId())) {

			engineeringDeviationRequestVO.setDocDate(LocalDateTime.now().toLocalDate());

			message = "Engineering Deviation Request Created Successfully";

		} else {

			engineeringDeviationRequestVO = engineeringDeviationRepo.findById(engineeringDeviationRequestDTO.getId())
					.orElseThrow(() -> new Exception("Invalid Engineering Deviation Request"));

			message = "Engineering Deviation Request Updated Successfully";
		}

		// ========================================================
		// DTO -> VO
		// ========================================================

		createUpdateEngineeringDeviationVO(engineeringDeviationRequestDTO, engineeringDeviationRequestVO);

		// ========================================================
		// SAVE BASIC
		// ========================================================

		EngineeringDeviationRequestVO savedVO = engineeringDeviationRepo.save(engineeringDeviationRequestVO);

		// ========================================================
		// ATTACHMENTS
		// ========================================================

		if (files != null && files.length > 0) {
			saveAttachments(files, savedVO);
		}

		// ========================================================
		// RESPONSE
		// ========================================================

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("engineeringDeviationRequestVO", engineeringDeviationRequestResponse(savedVO));

		return response;
	}

	private void createUpdateEngineeringDeviationVO(EngineeringDeviationRequestDTO dto,
			EngineeringDeviationRequestVO vo) throws Exception {

		// ========================================================
		// DEPARTMENT - TO DEPARTMENT
		// ========================================================

		if (!ObjectUtils.isEmpty(dto.getToDepartment())) {

			DepartmentVO departmentVO = departmentRepo.findById(dto.getToDepartment())
					.orElseThrow(() -> new Exception("Invalid To Department"));

			vo.setToDepartment(departmentVO);

		} else {

			vo.setToDepartment(null);
		}

		// ========================================================
		// DEVIATION REQUESTED BY
		// ========================================================

		if (!ObjectUtils.isEmpty(dto.getDeviationRequestedBy())) {

			EmployeeMasterVO employeeVO = employeeMasterRepo.findById(dto.getDeviationRequestedBy())
					.orElseThrow(() -> new Exception("Invalid Deviation Requested By"));

			vo.setDeviationRequestedBy(employeeVO);

		} else {

			vo.setDeviationRequestedBy(null);
		}

		// ========================================================
		// BASIC DETAILS
		// ========================================================

		vo.setDocId(dto.getDocId());

		if (dto.getDocDate() != null) {
			vo.setDocDate(dto.getDocDate());
		}

		vo.setPartDescription(dto.getPartDescription());
		vo.setCustomerId(dto.getCustomerId());
		vo.setProductName(dto.getProductName());
		vo.setQuantityReceived(dto.getQuantityReceived());
		vo.setSupplier(dto.getSupplier());

		// ========================================================
		// APPROVED BY
		// ========================================================

		if (!ObjectUtils.isEmpty(dto.getDeviationRequistApprovedBy())) {

			EmployeeMasterVO employeeVO = employeeMasterRepo.findById(dto.getDeviationRequistApprovedBy())
					.orElseThrow(() -> new Exception("Invalid Deviation Request Approved By"));

			vo.setDeviationRequistApprovedBy(employeeVO);

		} else {

			vo.setDeviationRequistApprovedBy(null);
		}

		// ========================================================
		// PART DETAILS
		// ========================================================

		vo.setPartNo(dto.getPartNo());
		vo.setInvoiceNo(dto.getInvoiceNo());
		vo.setDescriptionOfNC(dto.getDescriptionOfNC());
		vo.setReasonForDeviationRequest(dto.getReasonForDeviationRequest());
		vo.setActionOnNC(dto.getActionOnNC());
		vo.setDeviationPeriod(dto.getDeviationPeriod());

		// ========================================================
		// RESPONSIBLE FOR
		// ========================================================

		if (!ObjectUtils.isEmpty(dto.getResponsibleForName())) {

			EmployeeMasterVO employeeVO = employeeMasterRepo.findById(dto.getResponsibleForName())
					.orElseThrow(() -> new Exception("Invalid Responsible Person"));

			vo.setResponsibleForName(employeeVO);

		} else {

			vo.setResponsibleForName(null);
		}

		// ========================================================
		// DEPARTMENT
		// ========================================================

		if (!ObjectUtils.isEmpty(dto.getDepartment())) {

			DepartmentVO departmentVO = departmentRepo.findById(dto.getDepartment())
					.orElseThrow(() -> new Exception("Invalid Department"));

			vo.setDepartment(departmentVO);

		} else {

			vo.setDepartment(null);
		}

		// ========================================================
		// NC AFFECT DETAILS
		// ========================================================

		vo.setWillTheNCAffectTheFit(dto.getWillTheNCAffectTheFit());

		vo.setWillTheNCAffectTheForm(dto.getWillTheNCAffectTheForm());

		vo.setWillTheNCAffectTheFunction(dto.getWillTheNCAffectTheFunction());

		vo.setWillTheNCAffectTheSafety(dto.getWillTheNCAffectTheSafety());

		// ========================================================
		// DEVIATION DETAILS
		// ========================================================

		vo.setNatureOfTheDeviationRequest(dto.getNatureOfTheDeviationRequest());

		vo.setToBeIntimatedToCustomerAndActionOnCustomerFeedBack(
				dto.getToBeIntimatedToCustomerAndActionOnCustomerFeedBack());

		vo.setNote(dto.getNote());

		// ========================================================
		// PRODUCTION MANAGER
		// ========================================================

		if (!ObjectUtils.isEmpty(dto.getProductionMgr())) {

			EmployeeMasterVO employeeVO = employeeMasterRepo.findById(dto.getProductionMgr())
					.orElseThrow(() -> new Exception("Invalid Production Manager"));

			vo.setProductionMgr(employeeVO);

		} else {

			vo.setProductionMgr(null);
		}

		vo.setProductionMgrDisposition(dto.getProductionMgrDisposition());

		// ========================================================
		// QUALITY MANAGER
		// ========================================================

		if (!ObjectUtils.isEmpty(dto.getQualityMgr())) {

			EmployeeMasterVO employeeVO = employeeMasterRepo.findById(dto.getQualityMgr())
					.orElseThrow(() -> new Exception("Invalid Quality Manager"));

			vo.setQualityMgr(employeeVO);

		} else {

			vo.setQualityMgr(null);
		}

		vo.setQualityMgrDisposition(dto.getQualityMgrDisposition());

		// ========================================================
		// TDC MANAGER
		// ========================================================

		if (!ObjectUtils.isEmpty(dto.getTDCMgr())) {

			EmployeeMasterVO employeeVO = employeeMasterRepo.findById(dto.getTDCMgr())
					.orElseThrow(() -> new Exception("Invalid TDC Manager"));

			vo.setTDCMgr(employeeVO);

		} else {

			vo.setTDCMgr(null);
		}

		vo.setTdcMgrDisposition(dto.getTdcMgrDisposition());

		// ========================================================
		// DIRECTOR TECHNICAL
		// ========================================================

		if (!ObjectUtils.isEmpty(dto.getDirectorTechnical())) {

			EmployeeMasterVO employeeVO = employeeMasterRepo.findById(dto.getDirectorTechnical())
					.orElseThrow(() -> new Exception("Invalid Director Technical"));

			vo.setDirectorTechnical(employeeVO);

		} else {

			vo.setDirectorTechnical(null);
		}

		vo.setDirectorTechnicalDisposition(dto.getDirectorTechnicalDisposition());

		// ========================================================
		// PURCHASE MANAGER
		// ========================================================

		if (!ObjectUtils.isEmpty(dto.getPurMgr())) {

			EmployeeMasterVO employeeVO = employeeMasterRepo.findById(dto.getPurMgr())
					.orElseThrow(() -> new Exception("Invalid Purchase Manager"));

			vo.setPurMgr(employeeVO);

		} else {

			vo.setPurMgr(null);
		}

		vo.setPurMgrDisposition(dto.getPurMgrDisposition());

		// ========================================================
		// CUSTOMER DETAILS
		// ========================================================

		vo.setCustomerIntimationModeAndReference(dto.getCustomerIntimationModeAndReference());

		vo.setCustomerFeedBack(dto.getCustomerFeedBack());

		vo.setCustomerFeedBackModeAndReference(dto.getCustomerFeedBackModeAndReference());

		vo.setDecision(dto.getDecision());
	}

	// ============================================================
	// SAVE ATTACHMENTS
	// ============================================================

	private void saveAttachments(MultipartFile[] files, EngineeringDeviationRequestVO savedVO) throws IOException {

		File directory = new File(engineeringDeviationUploadPath);

		if (!directory.exists()) {
			directory.mkdirs();
		}

		for (MultipartFile file : files) {

			if (file == null || file.isEmpty()) {
				continue;
			}

			String originalFileName = file.getOriginalFilename();

			String extension = "";

			if (originalFileName != null && originalFileName.contains(".")) {

				extension = originalFileName.substring(originalFileName.lastIndexOf("."));
			}

			String uniqueFileName = UUID.randomUUID().toString() + extension;

			Path filePath = Paths.get(engineeringDeviationUploadPath, uniqueFileName );

			Files.copy(file.getInputStream(), filePath);

			EngineeringDeviationAttachmentVO attachmentVO = new EngineeringDeviationAttachmentVO();

			attachmentVO.setName(originalFileName);
			attachmentVO.setFileName(uniqueFileName);
			attachmentVO.setFilePath(filePath.toString());
			attachmentVO.setFileSize(file.getSize());
			attachmentVO.setContentType(file.getContentType());
			attachmentVO.setUploadOn(LocalDateTime.now());

			// IMPORTANT
			attachmentVO.setEngineeringDeviationRequestVO(savedVO);

			savedVO.getEngineeringDeviationAttachmentVO().add(attachmentVO);
		}

		engineeringDeviationRepo.save(savedVO);
	}

	// ============================================================
	// RESPONSE MAPPING
	// ============================================================

	private EngineeringDeviationRequestResponseDTO engineeringDeviationRequestResponse(
			EngineeringDeviationRequestVO vo) {

		EngineeringDeviationRequestResponseDTO dto = new EngineeringDeviationRequestResponseDTO();

		dto.setId(vo.getId());
		dto.setDocId(vo.getDocId());
		dto.setDocDate(vo.getDocDate());

		// ========================================================
		// TO DEPARTMENT
		// ========================================================

		if (vo.getToDepartment() != null) {

		    DepartmentResponseDTO departmentDTO = new DepartmentResponseDTO();

		    departmentDTO.setId(vo.getToDepartment().getId());
		    departmentDTO.setDepartmentCode(vo.getToDepartment().getDepartmentCode());
		    departmentDTO.setDepartmentName(vo.getToDepartment().getDepartmentName());

		    dto.setToDepartment(departmentDTO);
		}

		// ========================================================
		// DEVIATION REQUESTED BY
		// ========================================================

		if (vo.getDeviationRequestedBy() != null) {

			EmployeeDropdownResponseDTO employeeDTO = new EmployeeDropdownResponseDTO();

			employeeDTO.setEmployeeId(vo.getDeviationRequestedBy().getId());

			employeeDTO.setEmployeeCode(vo.getDeviationRequestedBy().getEmployeeId());

			employeeDTO.setEmployeeName(vo.getDeviationRequestedBy().getEmployeeName());

			employeeDTO.setEmail(vo.getDeviationRequestedBy().getEmail());

			dto.setRequestedBy(employeeDTO);
		}

		// ========================================================
		// BASIC DETAILS
		// ========================================================

		dto.setPartDescription(vo.getPartDescription());

		dto.setCustomerId(vo.getCustomerId());

		dto.setProductName(vo.getProductName());

		dto.setQuantityReceived(vo.getQuantityReceived());

		dto.setSupplier(vo.getSupplier());

		// ========================================================
		// APPROVED BY
		// ========================================================

		if (vo.getDeviationRequistApprovedBy() != null) {

			EmployeeDropdownResponseDTO employeeDTO = new EmployeeDropdownResponseDTO();

			employeeDTO.setEmployeeId(vo.getDeviationRequistApprovedBy().getId());

			employeeDTO.setEmployeeCode(vo.getDeviationRequistApprovedBy().getEmployeeId());

			employeeDTO.setEmployeeName(vo.getDeviationRequistApprovedBy().getEmployeeName());

			employeeDTO.setEmail(vo.getDeviationRequistApprovedBy().getEmail());

			dto.setDeviationRequistApprovedBy(employeeDTO);
		}

		// ========================================================
		// PART DETAILS
		// ========================================================

		dto.setPartNo(vo.getPartNo());
		dto.setInvoiceNo(vo.getInvoiceNo());
		dto.setDescriptionOfTheNC(vo.getDescriptionOfNC());
		dto.setReasonForDeviationRequest(vo.getReasonForDeviationRequest());
		dto.setActionOnNC(vo.getActionOnNC());
		dto.setDeviationPeriod(vo.getDeviationPeriod());

		// ========================================================
		// RESPONSIBLE PERSON
		// ========================================================

		if (vo.getResponsibleForName() != null) {

			EmployeeDropdownResponseDTO employeeDTO = new EmployeeDropdownResponseDTO();

			employeeDTO.setEmployeeId(vo.getResponsibleForName().getId());

			employeeDTO.setEmployeeCode(vo.getResponsibleForName().getEmployeeId());

			employeeDTO.setEmployeeName(vo.getResponsibleForName().getEmployeeName());

			employeeDTO.setEmail(vo.getResponsibleForName().getEmail());

			dto.setResponsibleForName(employeeDTO);
		}

		// ========================================================
		// DEPARTMENT
		// ========================================================

		if (vo.getDepartment() != null) {

		    DepartmentResponseDTO departmentDTO = new DepartmentResponseDTO();

		    departmentDTO.setId(vo.getDepartment().getId());
		    departmentDTO.setDepartmentCode(vo.getDepartment().getDepartmentCode());
		    departmentDTO.setDepartmentName(vo.getDepartment().getDepartmentName());

		    dto.setDepartment(departmentDTO);
		}

		// ========================================================
		// NC AFFECT DETAILS
		// ========================================================

		dto.setWillTheNCAffectTheFit(vo.getWillTheNCAffectTheFit());

		dto.setWillTheNCAffectTheForm(vo.getWillTheNCAffectTheForm());

		dto.setWillTheNCAffectTheFunction(vo.getWillTheNCAffectTheFunction());

		dto.setWillTheNCAffectTheSafety(vo.getWillTheNCAffectTheSafety());

		// ========================================================
		// DEVIATION DETAILS
		// ========================================================

		dto.setNatureOfTheDeviationRequest(vo.getNatureOfTheDeviationRequest());

		dto.setToBeIntimatedToCustomerAndActionOnCustomerFeedBack(
				vo.getToBeIntimatedToCustomerAndActionOnCustomerFeedBack());

		dto.setNote(vo.getNote());

		// ========================================================
		// MANAGER DETAILS
		// ========================================================

		if (vo.getProductionMgr() != null) {

			EmployeeDropdownResponseDTO employeeDTO = new EmployeeDropdownResponseDTO();

			employeeDTO.setEmployeeId(vo.getProductionMgr().getId());

			employeeDTO.setEmployeeCode(vo.getProductionMgr().getEmployeeId());

			employeeDTO.setEmployeeName(vo.getProductionMgr().getEmployeeName());

			employeeDTO.setEmail(vo.getProductionMgr().getEmail());

			dto.setProductionMgr(employeeDTO);
		}

		dto.setProductionMgrDisposition(vo.getProductionMgrDisposition());

		if (vo.getQualityMgr() != null) {

			EmployeeDropdownResponseDTO employeeDTO = new EmployeeDropdownResponseDTO();

			employeeDTO.setEmployeeId(vo.getQualityMgr().getId());

			employeeDTO.setEmployeeCode(vo.getQualityMgr().getEmployeeId());

			employeeDTO.setEmployeeName(vo.getQualityMgr().getEmployeeName());

			employeeDTO.setEmail(vo.getQualityMgr().getEmail());

			dto.setQualityMgr(employeeDTO);
		}

		dto.setQualityMgrDisposition(vo.getQualityMgrDisposition());

		if (vo.getTDCMgr() != null) {

			EmployeeDropdownResponseDTO employeeDTO = new EmployeeDropdownResponseDTO();

			employeeDTO.setEmployeeId(vo.getTDCMgr().getId());

			employeeDTO.setEmployeeCode(vo.getTDCMgr().getEmployeeId());

			employeeDTO.setEmployeeName(vo.getTDCMgr().getEmployeeName());

			employeeDTO.setEmail(vo.getTDCMgr().getEmail());

			dto.setTDCMgr(employeeDTO);
		}

		dto.setTdcMgrDisposition(vo.getTdcMgrDisposition());

		if (vo.getDirectorTechnical() != null) {

			EmployeeDropdownResponseDTO employeeDTO = new EmployeeDropdownResponseDTO();

			employeeDTO.setEmployeeId(vo.getDirectorTechnical().getId());

			employeeDTO.setEmployeeCode(vo.getDirectorTechnical().getEmployeeId());

			employeeDTO.setEmployeeName(vo.getDirectorTechnical().getEmployeeName());

			employeeDTO.setEmail(vo.getDirectorTechnical().getEmail());

			dto.setDirectorTechnical(employeeDTO);
		}

		dto.setDirectorTechnicalDisposition(vo.getDirectorTechnicalDisposition());

		if (vo.getPurMgr() != null) {

			EmployeeDropdownResponseDTO employeeDTO = new EmployeeDropdownResponseDTO();

			employeeDTO.setEmployeeId(vo.getPurMgr().getId());

			employeeDTO.setEmployeeCode(vo.getPurMgr().getEmployeeId());

			employeeDTO.setEmployeeName(vo.getPurMgr().getEmployeeName());

			employeeDTO.setEmail(vo.getPurMgr().getEmail());

			dto.setPurMgr(employeeDTO);
		}

		dto.setPurMgrDisposition(vo.getPurMgrDisposition());

		// ========================================================
		// CUSTOMER DETAILS
		// ========================================================

		dto.setCustomerIntimationModeAndReference(vo.getCustomerIntimationModeAndReference());

		dto.setCustomerFeedBack(vo.getCustomerFeedBack());

		dto.setCustomerFeedBackModeAndReference(vo.getCustomerFeedBackModeAndReference());

		dto.setDecision(vo.getDecision());

		// ========================================================
		// ATTACHMENTS
		// ========================================================

		List<EngineeringDeviationAttachmentDTO> attachmentList = new ArrayList<>();

		if (vo.getEngineeringDeviationAttachmentVO() != null) {

			for (EngineeringDeviationAttachmentVO attachmentVO : vo.getEngineeringDeviationAttachmentVO()) {

				EngineeringDeviationAttachmentDTO attachmentDTO = new EngineeringDeviationAttachmentDTO();

				attachmentDTO.setId(attachmentVO.getId());

				attachmentDTO.setName(attachmentVO.getName());

				attachmentDTO.setFileName(attachmentVO.getFileName());

				String urlPath = engineeringDeviationUploadPath.replace("C:/", "/").replace("\\", "/");

				attachmentDTO.setFilePath(serverBaseUrl + urlPath + attachmentVO.getFileName());

				attachmentDTO.setFileSize(attachmentVO.getFileSize());

				attachmentDTO.setContentType(attachmentVO.getContentType());

				attachmentDTO.setUploadOn(attachmentVO.getUploadOn());

				attachmentList.add(attachmentDTO);
			}
		}

		dto.setEngineeringDeviationAttachmentDTO(attachmentList);

		return dto;
	}

}
