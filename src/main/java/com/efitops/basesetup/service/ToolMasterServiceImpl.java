package com.efitops.basesetup.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.CustomerResponse1DTO;
import com.efitops.basesetup.ResponseDTO.DepartmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.EmployeeDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.ItemResponse1DTO;
import com.efitops.basesetup.ResponseDTO.ListOfValuesDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.LocationMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.ToolMasterAttachementResponseDTO;
import com.efitops.basesetup.ResponseDTO.ToolMasterComponentOutPutDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.ToolMasterMachineHistoryDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.ToolMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.ToolMasterSpareDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.ToolMasterTechnicalInfoDetailsResponseDTO;
import com.efitops.basesetup.dto.ToolMasterComponentOutPutDetailsDTO;
import com.efitops.basesetup.dto.ToolMasterDTO;
import com.efitops.basesetup.dto.ToolMasterMachineHistoryDetailsDTO;
import com.efitops.basesetup.dto.ToolMasterSpareDetailsDTO;
import com.efitops.basesetup.dto.ToolMasterTechnicalInfoDetailsDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.ToolMasterAttachementDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.ListOfValuesDetailsVO;
import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.entity.ToolMasterAttachementVO;
import com.efitops.basesetup.entity.ToolMasterComponentOutPutDetailsVO;
import com.efitops.basesetup.entity.ToolMasterMachineHistoryDetailsVO;
import com.efitops.basesetup.entity.ToolMasterSpareDetailsVO;
import com.efitops.basesetup.entity.ToolMasterTechnicalInfoDetailsVO;
import com.efitops.basesetup.entity.ToolMasterVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.DepartmentRepo;
import com.efitops.basesetup.repository.EmployeeMasterRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.ListOfValuesDetailsRepo;
import com.efitops.basesetup.repository.LocationRepo;
import com.efitops.basesetup.repository.ToolMasterAttachmentsRepo;
import com.efitops.basesetup.repository.ToolMasterComponentOutputRepo;
import com.efitops.basesetup.repository.ToolMasterMachineHistoryDetailsRepo;
import com.efitops.basesetup.repository.ToolMasterRepo;
import com.efitops.basesetup.repository.ToolMasterSpareDetailsRepo;
import com.efitops.basesetup.repository.ToolMasterTechnicalInfoDetailsRepo;
import com.efitops.basesetup.repository.UnitMasterRepo;
import com.efitops.basesetup.service.ToolMasterService;

@Service
public class ToolMasterServiceImpl implements ToolMasterService {

	@Autowired
	private ToolMasterRepo toolMasterRepo;

	@Autowired
	private ToolMasterTechnicalInfoDetailsRepo toolMasterTechnicalInfoDetailsRepo;

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

			vo.setLocatrion(location);
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
		 * Technical Information Details
		 */
		vo.getToolMasterTechnicalInfoDetailsVO().clear();

		if (dto.getToolMasterTechnicalInfoDetailsDTO() != null) {

			for (ToolMasterTechnicalInfoDetailsDTO detailDTO : dto.getToolMasterTechnicalInfoDetailsDTO()) {

				ToolMasterTechnicalInfoDetailsVO detailVO = new ToolMasterTechnicalInfoDetailsVO();

				if (detailDTO.getUnit() != null) {

					UnitMasterVO unit = unitMasterRepo.findById(detailDTO.getUnit())
							.orElseThrow(() -> new ApplicationException("Unit Not Found"));

					detailVO.setUnit(unit);
				}

				if (detailDTO.getLifeType() != null) {

					ListOfValuesDetailsVO lifeType = listOfValuesDetailsRepo.findById(detailDTO.getLifeType())
							.orElseThrow(() -> new ApplicationException("Life Type Not Found"));

					detailVO.setLifeType(lifeType);
				}

				detailVO.setToolWeight(detailDTO.getToolWeight());
				detailVO.setToolFixtureSize(detailDTO.getToolFixtureSize());
				detailVO.setLifeOfTool(detailDTO.getLifeOfTool());
				detailVO.setReconditionFreq(detailDTO.getReconditionFreq());
				detailVO.setSetUpTimeInMinutes(detailDTO.getSetUpTimeInMinutes());
				detailVO.setCompletedLifeCycle(detailDTO.getCompletedLifeCycle());
				detailVO.setToolMadeOf(detailDTO.getToolMadeOf());
				detailVO.setTechnicalSpecification(detailDTO.getTechnicalSpecification());
				detailVO.setNoOfStokesCompleted(detailDTO.getNoOfStokesCompleted());
				detailVO.setStrokesCompletedAfterReconditioning(detailDTO.getStrokesCompletedAfterReconditioning());
				detailVO.setReconditionedDate(detailDTO.getReconditionedDate());
				detailVO.setToolFixtureCost(detailDTO.getToolFixtureCost());
				detailVO.setToolFixtureAmortizedRecovered(detailDTO.getToolFixtureAmortizedRecovered());

				detailVO.setToolMasterVO(vo);

				vo.getToolMasterTechnicalInfoDetailsVO().add(detailVO);
			}
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

		/*
		 * Attachments from DTO
		 */
		vo.getToolMasterAttachementVO().clear();

		if (dto.getToolMasterAttachementDTO() != null) {

			for (ToolMasterAttachementDTO attachmentDTO : dto.getToolMasterAttachementDTO()) {

				ToolMasterAttachementVO attachmentVO = new ToolMasterAttachementVO();

				attachmentVO.setName(attachmentDTO.getName());
				attachmentVO.setFileName(attachmentDTO.getFileName());

				attachmentVO.setToolMasterVO(vo);

				vo.getToolMasterAttachementVO().add(attachmentVO);
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
		if (vo.getLocatrion() != null) {

			LocationMasterResponseDTO locationDTO = new LocationMasterResponseDTO();

			locationDTO.setId(vo.getLocatrion().getId());

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
		 * Technical Information Response
		 */
		List<ToolMasterTechnicalInfoDetailsResponseDTO> technicalList = new ArrayList<>();

		if (vo.getToolMasterTechnicalInfoDetailsVO() != null) {

			for (ToolMasterTechnicalInfoDetailsVO detailVO : vo.getToolMasterTechnicalInfoDetailsVO()) {

				ToolMasterTechnicalInfoDetailsResponseDTO detailDTO = new ToolMasterTechnicalInfoDetailsResponseDTO();

				detailDTO.setId(detailVO.getId());
				detailDTO.setToolWeight(detailVO.getToolWeight());
				detailDTO.setToolFixtureSize(detailVO.getToolFixtureSize());
				detailDTO.setLifeOfTool(detailVO.getLifeOfTool());
				detailDTO.setReconditionFreq(detailVO.getReconditionFreq());
				detailDTO.setSetUpTimeInMinutes(detailVO.getSetUpTimeInMinutes());
				detailDTO.setCompletedLifeCycle(detailVO.getCompletedLifeCycle());
				detailDTO.setToolMadeOf(detailVO.getToolMadeOf());
				detailDTO.setTechnicalSpecification(detailVO.getTechnicalSpecification());
				detailDTO.setNoOfStokesCompleted(detailVO.getNoOfStokesCompleted());
				detailDTO.setStrokesCompletedAfterReconditioning(detailVO.getStrokesCompletedAfterReconditioning());
				detailDTO.setReconditionedDate(detailVO.getReconditionedDate());
				detailDTO.setToolFixtureCost(detailVO.getToolFixtureCost());
				detailDTO.setToolFixtureAmortizedRecovered(detailVO.getToolFixtureAmortizedRecovered());

				if (detailVO.getUnit() != null) {

					UnitMasterResponseDTO unitDTO = new UnitMasterResponseDTO();

					unitDTO.setId(detailVO.getUnit().getId() );

					detailDTO.setUnit(unitDTO);
				}

				if (detailVO.getLifeType() != null) {

					ListOfValuesDetailsResponseDTO lifeTypeDTO = new ListOfValuesDetailsResponseDTO();

					lifeTypeDTO.setId(detailVO.getLifeType().getId());

					detailDTO.setLifeType(lifeTypeDTO);
				}

				technicalList.add(detailDTO);
			}
		}

		dto.setToolMasterTechnicalInfoDetailsDTO(technicalList);

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

					ItemResponse1DTO itemDTO = new ItemResponse1DTO();

					itemDTO.setId(detailVO.getSparePartId().getId());
					itemDTO.setItemCode(detailVO.getSparePartId().getItemCode());
					itemDTO.setItemDescription(detailVO.getSparePartId().getItemDescription());
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

					ItemResponse1DTO itemDTO = new ItemResponse1DTO();

					itemDTO.setId(detailVO.getItem().getId());

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
}
