package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.ResponseDTO.CustomerResponse1DTO;
import com.efitops.basesetup.ResponseDTO.DepartmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.EmployeeDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.InitialPlanningDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.InitialPlanningInstrumentDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.InitialPlanningResponseDTO;
import com.efitops.basesetup.ResponseDTO.ItemResponse1DTO;
import com.efitops.basesetup.ResponseDTO.ListOfValuesDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.MachineInstrumentResponseDTO;
import com.efitops.basesetup.ResponseDTO.OperationMasterConsumablesDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.OperationMasterMachineDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.OperationMasterMachineResponseDTO;
import com.efitops.basesetup.ResponseDTO.OperationMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.OperationMasterToolDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.OperationMasterToolResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProblemSolvingActionDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProblemSolvingEntryResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProblemSolvingOtherDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProblemSolvingRootDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.UnitResponseDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.GradeMasterResponseDTO;
import com.efitops.basesetup.dto.InitialPlanningDTO;
import com.efitops.basesetup.dto.InitialPlanningDetailsDTO;
import com.efitops.basesetup.dto.InitialPlanningInstrumentDetailsDTO;
import com.efitops.basesetup.dto.OperationMasterConsumableDetailsDTO;
import com.efitops.basesetup.dto.OperationMasterDTO;
import com.efitops.basesetup.dto.OperationMasterMachineDetailsDTO;
import com.efitops.basesetup.dto.OperationMasterToolDetailsDTO;
import com.efitops.basesetup.dto.ProblemSolvingActionDetailsDTO;
import com.efitops.basesetup.dto.ProblemSolvingEntryDTO;
import com.efitops.basesetup.dto.ProblemSolvingOtherDetailsDTO;
import com.efitops.basesetup.dto.ProblemSolvingRootDetailsDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.GradeMasterVO;
import com.efitops.basesetup.entity.InitialPlanningDetailsVO;
import com.efitops.basesetup.entity.InitialPlanningInstrumentDetailsVO;
import com.efitops.basesetup.entity.InitialPlanningVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.ListOfValuesDetailsVO;
import com.efitops.basesetup.entity.MachineMasterVO;
import com.efitops.basesetup.entity.OperationMasterConsumableDetailsVO;
import com.efitops.basesetup.entity.OperationMasterMachineDetailsVO;
import com.efitops.basesetup.entity.OperationMasterToolDetailsVO;
import com.efitops.basesetup.entity.OperationMasterVO;
import com.efitops.basesetup.entity.ProblemSolvingActionDetailsVO;
import com.efitops.basesetup.entity.ProblemSolvingEntryVO;
import com.efitops.basesetup.entity.ProblemSolvingOtherDetailsVO;
import com.efitops.basesetup.entity.ProblemSolvingRootDetailsVO;
import com.efitops.basesetup.entity.ToolMasterVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.DepartmentRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.EmployeeMasterRepo;
import com.efitops.basesetup.repository.GradeMasterRepo;
import com.efitops.basesetup.repository.InitialPlanningDetailsRepo;
import com.efitops.basesetup.repository.InitialPlanningInstrumentDetailsRepo;
import com.efitops.basesetup.repository.InitialPlanningRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.ListOfValuesDetailsRepo;
import com.efitops.basesetup.repository.MachineMasterRepo;
import com.efitops.basesetup.repository.OperationMasterConsumablesDetailsRepo;
import com.efitops.basesetup.repository.OperationMasterMachineDetailsRepo;
import com.efitops.basesetup.repository.OperationMasterRepo;
import com.efitops.basesetup.repository.OperationMasterToolDetailsRepo;
import com.efitops.basesetup.repository.ProblemSolvingActionDetailsRepo;
import com.efitops.basesetup.repository.ProblemSolvingEntryRepo;
import com.efitops.basesetup.repository.ProblemSolvingOtherDetailsRepo;
import com.efitops.basesetup.repository.ProblemSolvingRootDetailsRepo;
import com.efitops.basesetup.repository.ToolMasterRepo;
import com.efitops.basesetup.repository.UnitMasterRepo;

@Service
public class InitialPlanningServiceImpl implements InitialPlanningService {
	@Autowired
	private InitialPlanningRepo initialPlanningRepo;

	@Autowired
	private InitialPlanningDetailsRepo initialPlanningDetailsRepo;

	@Autowired
	private InitialPlanningInstrumentDetailsRepo initialPlanningInstrumentDetailsRepo;

	@Autowired
	private ListOfValuesDetailsRepo listOfValuesDetailsRepo;

	@Autowired
	private ItemMasterRepo itemRepo;

	@Autowired
	private GradeMasterRepo gradeMasterRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private EmployeeMasterRepo employeeRepo;

	@Autowired
	private UnitMasterRepo unitMasterRepo;

	@Autowired
	private DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	private ProblemSolvingEntryRepo problemSolvingEntryRepo;

	@Autowired
	private ProblemSolvingRootDetailsRepo problemSolvingRootDetailsRepo;

	@Autowired
	private ProblemSolvingActionDetailsRepo problemSolvingActionDetailsRepo;

	@Autowired
	private ProblemSolvingOtherDetailsRepo problemSolvingOtherDetailsRepo;

	@Autowired
	private BranchRepo branchRepo;

	@Autowired
	private DepartmentRepo departmentRepo;

	@Autowired
	private OperationMasterRepo operationMasterRepo;

	@Autowired
	private OperationMasterToolDetailsRepo operationMasterToolDetailsRepo;

	@Autowired
	private OperationMasterConsumablesDetailsRepo operationMasterConsumablesDetailsRepo;

	@Autowired
	private OperationMasterMachineDetailsRepo operationMasterMachineDetailsRepo;

	@Autowired
	private MachineMasterRepo machineMasterRepo;

	@Autowired
	private ToolMasterRepo toolMasterRepo;

	@Autowired
	private ItemMasterRepo itemMasterRepo;

	@Override
	@Transactional
	public Map<String, Object> updateCreateInitialPlanning(InitialPlanningDTO initialPlanningDTO)
			throws ApplicationException {

		String screenCode = "INIP";

		InitialPlanningVO initialPlanningVO = new InitialPlanningVO();

		String message;

		// =========================
		// Update
		// =========================

		if (ObjectUtils.isNotEmpty(initialPlanningDTO.getId())) {

			initialPlanningVO = initialPlanningRepo.findById(initialPlanningDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Initial Planning Details"));

			initialPlanningVO.setUpdatedBy(initialPlanningDTO.getCreatedBy());

			message = "Initial Planning Updated Successfully";

		} else {

			// =========================
			// Generate Doc ID
			// =========================

			String docId = initialPlanningRepo.getInitialPlanningDocId(initialPlanningDTO.getOrgId(),
					initialPlanningDTO.getFinancialYear(), screenCode);

			if (StringUtils.isBlank(docId)) {

				throw new ApplicationException("Initial Planning DocId Not Found");
			}

			initialPlanningVO.setDocId(docId);

			// =========================
			// Document Mapping
			// =========================

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(initialPlanningDTO.getOrgId(),
							initialPlanningDTO.getFinancialYear(), screenCode);

			if (documentTypeMappingDetailsVO == null) {

				throw new ApplicationException("Document Type Mapping Details Not Found");
			}

			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			initialPlanningVO.setCreatedBy(initialPlanningDTO.getCreatedBy());

			initialPlanningVO.setUpdatedBy(initialPlanningDTO.getCreatedBy());

			message = "Initial Planning Created Successfully";
		}

		// =========================
		// Basic Mapping
		// =========================

		createUpdateInitialPlanningVO(initialPlanningDTO, initialPlanningVO);

		// =========================
		// Save Basic
		// =========================

		InitialPlanningVO savedVO = initialPlanningRepo.save(initialPlanningVO);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("initialPlanningVO", initialPlanningResponse(savedVO));

		return response;
	}

	private void createUpdateInitialPlanningVO(InitialPlanningDTO dto, InitialPlanningVO initialPlanningVO)
			throws ApplicationException {

		// =========================
		// Basic Fields
		// =========================

//		initialPlanningVO.setDocDate(dto.getDocDate());

		initialPlanningVO.setDrawingNo(dto.getDrawingNo());

		initialPlanningVO.setMaterialCharacteristics(dto.getMaterialCharacteristics());

		initialPlanningVO.setSamplingPlan(dto.getSamplingPlan());

		initialPlanningVO.setProcess(dto.getProcess());

		initialPlanningVO.setAesthetics(dto.getAesthetics());

		initialPlanningVO.setPackingRequirements(dto.getPackingRequirements());

		initialPlanningVO.setOthers(dto.getOthers());

		initialPlanningVO.setApproved(dto.getApproved());

		initialPlanningVO.setOrgId(dto.getOrgId());

		initialPlanningVO.setFinancialYear(dto.getFinancialYear());

		initialPlanningVO.setActive(dto.isActive());

		initialPlanningVO.setCancelRemarks(dto.getCancelRemarks());

		// =========================
		// Item Type
		// =========================

		if (dto.getItemType() != null && dto.getItemType() != 0) {

			ListOfValuesDetailsVO itemType = listOfValuesDetailsRepo.findById(dto.getItemType())
					.orElseThrow(() -> new ApplicationException("Item Type Not Found"));

			initialPlanningVO.setItemType(itemType);
		}

		// =========================
		// Item
		// =========================

		if (dto.getItem() != null && dto.getItem() != 0) {

			ItemMasterVO item = itemRepo.findById(dto.getItem())
					.orElseThrow(() -> new ApplicationException("Item Not Found"));

			initialPlanningVO.setItem(item);
		}

		// =========================
		// Item Grade
		// =========================

		if (dto.getItem_grade() != null && dto.getItem_grade() != 0) {

			GradeMasterVO grade = gradeMasterRepo.findById(dto.getItem_grade())
					.orElseThrow(() -> new ApplicationException("Item Grade Not Found"));

			initialPlanningVO.setItem_grade(grade);
		}

		// =========================
		// Source
		// =========================

		if (dto.getSource() != null && dto.getSource() != 0) {

			CustomerVO source = customerRepo.findById(dto.getSource())
					.orElseThrow(() -> new ApplicationException("Source Not Found"));

			initialPlanningVO.setSource(source);
		}

		// =========================
		// Prepared By
		// =========================

		if (dto.getPreparedBy() != null && dto.getPreparedBy() != 0) {

			EmployeeMasterVO preparedBy = employeeRepo.findById(dto.getPreparedBy())
					.orElseThrow(() -> new ApplicationException("Prepared By Not Found"));

			initialPlanningVO.setPreparedBy(preparedBy);
		}

		// =========================
		// Approved By
		// =========================

		if (dto.getApprovedBy() != null && dto.getApprovedBy() != 0) {

			EmployeeMasterVO approvedBy = employeeRepo.findById(dto.getApprovedBy())
					.orElseThrow(() -> new ApplicationException("Approved By Not Found"));

			initialPlanningVO.setApprovedBy(approvedBy);
		}

		// =========================================
		// Delete Existing Grid During Update
		// =========================================

		if (dto.getId() != null) {

			List<InitialPlanningDetailsVO> oldDetails = initialPlanningDetailsRepo
					.findByInitialPlanningVO(initialPlanningVO);

			for (InitialPlanningDetailsVO oldDetail : oldDetails) {

				List<InitialPlanningInstrumentDetailsVO> oldInstruments = initialPlanningInstrumentDetailsRepo
						.findByInitialPlanningDetailsVO(oldDetail);

				if (!oldInstruments.isEmpty()) {

					initialPlanningInstrumentDetailsRepo.deleteAll(oldInstruments);
				}
			}

			if (!oldDetails.isEmpty()) {

				initialPlanningDetailsRepo.deleteAll(oldDetails);
			}
		}

		// =========================
		// Details Grid
		// =========================

		List<InitialPlanningDetailsVO> detailsList = new ArrayList<>();

		if (dto.getInitialPlanningDetailsDTO() != null && !dto.getInitialPlanningDetailsDTO().isEmpty()) {

			for (InitialPlanningDetailsDTO detailDTO : dto.getInitialPlanningDetailsDTO()) {

				InitialPlanningDetailsVO detailVO = new InitialPlanningDetailsVO();

				// =========================
				// Parameter
				// =========================

				detailVO.setParameter(detailDTO.getParameter());

				detailVO.setSpecification(detailDTO.getSpecification());

				detailVO.setAccCriteria(detailDTO.getAccCriteria());

				detailVO.setInspectionMethod(detailDTO.getInspectionMethod());

				detailVO.setNoOfInstrumentsUsed(detailDTO.getNoOfInstrumentsUsed());

				detailVO.setRemarks(detailDTO.getRemarks());

				// =========================
				// UOM Mapping
				// =========================

				if (detailDTO.getUom() != null && detailDTO.getUom() != 0) {

					UnitMasterVO uom = unitMasterRepo.findById(detailDTO.getUom())
							.orElseThrow(() -> new ApplicationException("UOM Not Found"));

					detailVO.setUom(uom);
				}

				// =========================
				// Parent Mapping
				// =========================

				detailVO.setInitialPlanningVO(initialPlanningVO);

				// =========================
				// Instrument Grid
				// =========================

				List<InitialPlanningInstrumentDetailsVO> instrumentList = new ArrayList<>();

				if (detailDTO.getInitialPlanningInstrumentDetailsDTO() != null
						&& !detailDTO.getInitialPlanningInstrumentDetailsDTO().isEmpty()) {

					for (InitialPlanningInstrumentDetailsDTO instrumentDTO : detailDTO
							.getInitialPlanningInstrumentDetailsDTO()) {

						InitialPlanningInstrumentDetailsVO instrumentVO = new InitialPlanningInstrumentDetailsVO();

//						instrumentVO.setInstrumentNo(instrumentDTO.getInstrumentNo());
//
//						instrumentVO.setInstrumentName(instrumentDTO.getInstrumentName());
//
						instrumentVO.setRange(instrumentDTO.getRange());

						// Parent mapping
						instrumentVO.setInitialPlanningDetailsVO(detailVO);

						instrumentList.add(instrumentVO);
					}
				}

				detailVO.setInitialPlanningInstrumentDetailsVO(instrumentList);

				detailsList.add(detailVO);
			}
		}

		initialPlanningVO.setInitialPlanningDetailsVO(detailsList);
	}

	private InitialPlanningResponseDTO initialPlanningResponse(InitialPlanningVO initialPlanningVO) {

		InitialPlanningResponseDTO responseDTO = new InitialPlanningResponseDTO();

		responseDTO.setId(initialPlanningVO.getId());

		responseDTO.setDocId(initialPlanningVO.getDocId());

		responseDTO.setDocDate(initialPlanningVO.getDocDate());

		responseDTO.setDrawingNo(initialPlanningVO.getDrawingNo());

		responseDTO.setMaterialCharacteristics(initialPlanningVO.getMaterialCharacteristics());

		responseDTO.setSamplingPlan(initialPlanningVO.getSamplingPlan());

		responseDTO.setProcess(initialPlanningVO.getProcess());

		responseDTO.setAesthetics(initialPlanningVO.getAesthetics());

		responseDTO.setPackingRequirements(initialPlanningVO.getPackingRequirements());

		responseDTO.setOthers(initialPlanningVO.getOthers());

		responseDTO.setApproved(initialPlanningVO.getApproved());

		responseDTO.setOrgId(initialPlanningVO.getOrgId());

		responseDTO.setFinancialYear(initialPlanningVO.getFinancialYear());

		responseDTO.setActive(initialPlanningVO.getActive());

		responseDTO.setCancelRemarks(initialPlanningVO.getCancelRemarks());

		responseDTO.setCreatedBy(initialPlanningVO.getCreatedBy());

		// =========================
		// Item Type Response
		// =========================

		if (initialPlanningVO.getItemType() != null) {

			ListOfValuesDetailsResponseDTO itemTypeDTO = new ListOfValuesDetailsResponseDTO();

			itemTypeDTO.setId(initialPlanningVO.getItemType().getId());

			itemTypeDTO.setCode(initialPlanningVO.getItemType().getValueCode());

			itemTypeDTO.setDescription(initialPlanningVO.getItemType().getValueDescription());

			responseDTO.setItemType(itemTypeDTO);
		}

		// =========================
		// Item Response
		// =========================

		if (initialPlanningVO.getItem() != null) {

			ItemResponse1DTO itemDTO = new ItemResponse1DTO();

			itemDTO.setId(initialPlanningVO.getItem().getId());

			itemDTO.setItemCode(initialPlanningVO.getItem().getItemCode());

			itemDTO.setItemDescription(initialPlanningVO.getItem().getItemDescription());

			responseDTO.setItem(itemDTO);
		}

		// =========================
		// Item Grade Response
		// =========================

		if (initialPlanningVO.getItem_grade() != null) {

			GradeMasterResponseDTO gradeDTO = new GradeMasterResponseDTO();

			gradeDTO.setId(initialPlanningVO.getItem_grade().getId());

			gradeDTO.setGradeCode(initialPlanningVO.getItem_grade().getGradeCode());

			gradeDTO.setGradeDescription(initialPlanningVO.getItem_grade().getGradeDescription());

			responseDTO.setItem_grade(gradeDTO);
		}

		// =========================
		// Source Response
		// =========================

		if (initialPlanningVO.getSource() != null) {

			CustomerResponse1DTO sourceDTO = new CustomerResponse1DTO();

			sourceDTO.setId(initialPlanningVO.getSource().getId());

			sourceDTO.setCustomerName(initialPlanningVO.getSource().getCustomerName());

			responseDTO.setSource(sourceDTO);
		}

		// =========================
		// Prepared By Response
		// =========================

		if (initialPlanningVO.getPreparedBy() != null) {

			EmployeeDropdownResponseDTO preparedByDTO = new EmployeeDropdownResponseDTO();

			preparedByDTO.setEmployeeId(initialPlanningVO.getPreparedBy().getId());

			preparedByDTO.setEmployeeName(initialPlanningVO.getPreparedBy().getEmployeeName());

			responseDTO.setPreparedBy(preparedByDTO);
		}

		// =========================
		// Approved By Response
		// =========================

		if (initialPlanningVO.getApprovedBy() != null) {

			EmployeeDropdownResponseDTO approvedByDTO = new EmployeeDropdownResponseDTO();

			approvedByDTO.setEmployeeId(initialPlanningVO.getApprovedBy().getId());

			approvedByDTO.setEmployeeName(initialPlanningVO.getApprovedBy().getEmployeeName());

			responseDTO.setApprovedBy(approvedByDTO);
		}

		// =========================
		// Details Response
		// =========================

		List<InitialPlanningDetailsResponseDTO> detailResponseList = new ArrayList<>();

		if (initialPlanningVO.getInitialPlanningDetailsVO() != null
				&& !initialPlanningVO.getInitialPlanningDetailsVO().isEmpty()) {

			for (InitialPlanningDetailsVO detailVO : initialPlanningVO.getInitialPlanningDetailsVO()) {

				InitialPlanningDetailsResponseDTO detailResponseDTO = new InitialPlanningDetailsResponseDTO();

				detailResponseDTO.setId(detailVO.getId());

				detailResponseDTO.setParameter(detailVO.getParameter());

				detailResponseDTO.setSpecification(detailVO.getSpecification());

				detailResponseDTO.setAccCriteria(detailVO.getAccCriteria());

				detailResponseDTO.setInspectionMethod(detailVO.getInspectionMethod());

				detailResponseDTO.setNoOfInstrumentsUsed(detailVO.getNoOfInstrumentsUsed());

				detailResponseDTO.setRemarks(detailVO.getRemarks());

				// =========================
				// UOM Response
				// =========================

				if (detailVO.getUom() != null) {

					UnitResponseDTO uomDTO = new UnitResponseDTO();

					uomDTO.setId(detailVO.getUom().getId());

					uomDTO.setUnitId(detailVO.getUom().getUnitId());

					detailResponseDTO.setUom(uomDTO);
				}

				// =========================
				// Instrument Grid Response
				// =========================

				List<InitialPlanningInstrumentDetailsResponseDTO> instrumentResponseList = new ArrayList<>();

				if (detailVO.getInitialPlanningInstrumentDetailsVO() != null
						&& !detailVO.getInitialPlanningInstrumentDetailsVO().isEmpty()) {

					for (InitialPlanningInstrumentDetailsVO instrumentVO : detailVO
							.getInitialPlanningInstrumentDetailsVO()) {

						InitialPlanningInstrumentDetailsResponseDTO instrumentResponseDTO = new InitialPlanningInstrumentDetailsResponseDTO();

						if (instrumentVO.getInstrumentNo() != null) {

							MachineInstrumentResponseDTO instrumentDTO = new MachineInstrumentResponseDTO();

							instrumentDTO.setId(instrumentVO.getInstrumentNo().getId());
							instrumentDTO
									.setMachineInstrumentNo(instrumentVO.getInstrumentNo().getMachineInstrumentNo());
							instrumentDTO.setMachineInstrumentName(
									instrumentVO.getInstrumentNo().getMachineInstrumentName());

							instrumentResponseDTO.setInstrumentNo(instrumentDTO);
						}

						instrumentResponseDTO.setRange(instrumentVO.getRange());

						instrumentResponseList.add(instrumentResponseDTO);
					}
				}

				detailResponseDTO.setInitialPlanningInstrumentDetailsResponseDTO(instrumentResponseList);

				detailResponseList.add(detailResponseDTO);
			}
		}

		responseDTO.setInitialPlanningDetailsResponseDTO(detailResponseList);

		return responseDTO;
	}

	@Override
	public InitialPlanningResponseDTO getInitialPlanningById(Long id) throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {

			throw new ApplicationException("Invalid Id");
		}

		InitialPlanningVO initialPlanningVO = initialPlanningRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Initial Planning Not Found"));

		return initialPlanningResponse(initialPlanningVO);
	}

	@Override
	public List<InitialPlanningResponseDTO> getInitialPlanningByOrgId(Long orgId) throws ApplicationException {

		List<InitialPlanningVO> initialPlanningList = initialPlanningRepo.getInitialPlanningByOrgId(orgId);

		if (initialPlanningList.isEmpty()) {

			throw new ApplicationException("No Initial Planning Details Found");
		}

		List<InitialPlanningResponseDTO> responseList = new ArrayList<>();

		for (InitialPlanningVO initialPlanningVO : initialPlanningList) {

			responseList.add(initialPlanningResponse(initialPlanningVO));
		}

		return responseList;
	}

	@Override
	public String getInitialPlanningDocId(Long orgId, String financialYear) {

		String screenCode = "INIP";

		String result = initialPlanningRepo.getInitialPlanningDocId(orgId, financialYear, screenCode);

		return result;
	}

	@Override
	public Map<String, Object> getItemDropdownForInitialPlanning(Long itemType, Long orgId)
			throws ApplicationException {

		List<Object[]> result = initialPlanningRepo.getItemDropdownForInitialPlanning(itemType, orgId);

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

			// =========================
			// Item Type Details
			// =========================

			itemMap.put("itemTypeId", obj[3] != null ? ((Number) obj[3]).longValue() : null);

			itemMap.put("itemTypeCode", obj[4] != null ? obj[4].toString() : null);

			itemMap.put("itemTypeDescription", obj[5] != null ? obj[5].toString() : null);

			// =========================
			// Grade Details
			// =========================

			itemMap.put("gradeId", obj[6] != null ? ((Number) obj[6]).longValue() : null);

			itemMap.put("gradeCode", obj[7] != null ? obj[7].toString() : null);

			itemMap.put("gradeDescription", obj[8] != null ? obj[8].toString() : null);

			itemList.add(itemMap);
		}

		Map<String, Object> response = new HashMap<>();

		response.put("itemList", itemList);

		return response;
	}

//	dropdown for parameter
	@Override
	public Map<String, Object> getParameterDropdownForInitialPlanning(Long orgId) throws ApplicationException {

		List<Object[]> result = initialPlanningRepo.getParameterDropdownForInitialPlanning(orgId);

		if (result.isEmpty()) {
			throw new ApplicationException("No Parameter Details Found");
		}

		List<Map<String, Object>> parameterList = new ArrayList<>();

		for (Object[] obj : result) {

			Map<String, Object> parameterMap = new HashMap<>();

			// =========================
			// Parameter Details
			// =========================

			parameterMap.put("id", obj[0] != null ? ((Number) obj[0]).longValue() : null);

			parameterMap.put("parameterCode", obj[1] != null ? obj[1].toString() : null);

			parameterMap.put("parameterDescription", obj[2] != null ? obj[2].toString() : null);

			// =========================
			// Parameter Type Details
			// =========================

			parameterMap.put("parameterTypeId", obj[3] != null ? ((Number) obj[3]).longValue() : null);

			parameterMap.put("parameterTypeCode", obj[4] != null ? obj[4].toString() : null);

			parameterMap.put("parameterTypeDescription", obj[5] != null ? obj[5].toString() : null);

			parameterList.add(parameterMap);
		}

		Map<String, Object> response = new HashMap<>();

		response.put("parameterList", parameterList);

		return response;
	}

//	problem solving entry
	@Override
	@Transactional
	public Map<String, Object> updateCreateProblemSolvingEntry(ProblemSolvingEntryDTO problemSolvingEntryDTO)
			throws ApplicationException {

		String screenCode = "PSE";

		ProblemSolvingEntryVO problemSolvingEntryVO = new ProblemSolvingEntryVO();

		String message;

		// =========================
		// Update
		// =========================

		if (ObjectUtils.isNotEmpty(problemSolvingEntryDTO.getId())) {

			problemSolvingEntryVO = problemSolvingEntryRepo.findById(problemSolvingEntryDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Problem Solving Entry Details"));

			problemSolvingEntryVO.setUpdatedBy(problemSolvingEntryDTO.getCreatedBy());

			message = "Problem Solving Entry Updated Successfully";

		} else {

			// =========================
			// Generate Doc ID
			// =========================

			String docId = problemSolvingEntryRepo.getProblemSolvingEntryDocId(problemSolvingEntryDTO.getOrgId(),
					problemSolvingEntryDTO.getFinancialYear(), screenCode);

			if (StringUtils.isBlank(docId)) {

				throw new ApplicationException("Problem Solving Entry DocId Not Found");
			}

			problemSolvingEntryVO.setDocId(docId);

			// =========================
			// Document Mapping
			// =========================

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(problemSolvingEntryDTO.getOrgId(),
							problemSolvingEntryDTO.getFinancialYear(), screenCode);

			if (documentTypeMappingDetailsVO == null) {

				throw new ApplicationException("Document Type Mapping Details Not Found");
			}

			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			problemSolvingEntryVO.setCreatedBy(problemSolvingEntryDTO.getCreatedBy());

			problemSolvingEntryVO.setUpdatedBy(problemSolvingEntryDTO.getCreatedBy());

			message = "Problem Solving Entry Created Successfully";
		}

		// =========================
		// Basic Mapping
		// =========================

		createUpdateProblemSolvingEntryVO(problemSolvingEntryDTO, problemSolvingEntryVO);

		// =========================
		// Save Basic
		// =========================

		ProblemSolvingEntryVO savedVO = problemSolvingEntryRepo.save(problemSolvingEntryVO);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("problemSolvingEntryVO", problemSolvingEntryResponse(savedVO));

		return response;
	}

	private void createUpdateProblemSolvingEntryVO(ProblemSolvingEntryDTO dto,
			ProblemSolvingEntryVO problemSolvingEntryVO) throws ApplicationException {

		// =========================
		// Basic Fields
		// =========================

		problemSolvingEntryVO.setDocDate(dto.getDocDate());

		problemSolvingEntryVO.setReference(dto.getReference());

		problemSolvingEntryVO.setMachineNo(dto.getMachineNo());

		problemSolvingEntryVO.setMfgDate(dto.getMfgDate());

		problemSolvingEntryVO.setDefectDesciption(dto.getDefectDesciption());

		problemSolvingEntryVO.setShortTeamAction(dto.getShortTeamAction());

		problemSolvingEntryVO.setCloseDate(dto.getCloseDate());

		problemSolvingEntryVO.setRecognizeTheTeam(dto.getRecognizeTheTeam());

		problemSolvingEntryVO.setOrgId(dto.getOrgId());

		problemSolvingEntryVO.setFinancialYear(dto.getFinancialYear());

		problemSolvingEntryVO.setActive("Active".equalsIgnoreCase(dto.getActive()));

		problemSolvingEntryVO.setCancelRemarks(dto.getCancelRemarks());

		// =========================
		// Branch
		// =========================

		if (dto.getBranch() != null && dto.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			problemSolvingEntryVO.setBranch(branch);
		}

		// =========================
		// Department
		// =========================

		if (dto.getDepartment() != null && dto.getDepartment() != 0) {

			DepartmentVO department = departmentRepo.findById(dto.getDepartment())
					.orElseThrow(() -> new ApplicationException("Department Not Found"));

			problemSolvingEntryVO.setDepartment(department);
		}

		// =========================
		// Customer
		// =========================

		if (dto.getCustomer() != null && dto.getCustomer() != 0) {

			CustomerVO customer = customerRepo.findById(dto.getCustomer())
					.orElseThrow(() -> new ApplicationException("Customer Not Found"));

			problemSolvingEntryVO.setCustomer(customer);
		}

		// =========================
		// Item
		// =========================

		if (dto.getItem() != null && dto.getItem() != 0) {

			ItemMasterVO item = itemRepo.findById(dto.getItem())
					.orElseThrow(() -> new ApplicationException("Item Not Found"));

			problemSolvingEntryVO.setItem(item);
		}

		// =========================
		// Team Member 1
		// =========================

		if (dto.getTeamMember1() != null && dto.getTeamMember1() != 0) {

			EmployeeMasterVO teamMember1 = employeeRepo.findById(dto.getTeamMember1())
					.orElseThrow(() -> new ApplicationException("Team Member 1 Not Found"));

			problemSolvingEntryVO.setTeamMember1(teamMember1);
		}

		// =========================
		// Team Member 2
		// =========================

		if (dto.getTeamMember2() != null && dto.getTeamMember2() != 0) {

			EmployeeMasterVO teamMember2 = employeeRepo.findById(dto.getTeamMember2())
					.orElseThrow(() -> new ApplicationException("Team Member 2 Not Found"));

			problemSolvingEntryVO.setTeamMember2(teamMember2);
		}

		// =========================
		// Prepared By
		// =========================

		if (dto.getPreparedBy() != null && dto.getPreparedBy() != 0) {

			EmployeeMasterVO preparedBy = employeeRepo.findById(dto.getPreparedBy())
					.orElseThrow(() -> new ApplicationException("Prepared By Not Found"));

			problemSolvingEntryVO.setPreparedBy(preparedBy);
		}
		// =========================================
		// Delete Existing Grid During Update
		// =========================================

		if (dto.getId() != null) {

			List<ProblemSolvingRootDetailsVO> oldRootDetails = problemSolvingRootDetailsRepo
					.findByProblemSolvingEntryVO(problemSolvingEntryVO);

			if (!oldRootDetails.isEmpty()) {

				problemSolvingRootDetailsRepo.deleteAll(oldRootDetails);
			}

			List<ProblemSolvingOtherDetailsVO> oldOtherDetails = problemSolvingOtherDetailsRepo
					.findByProblemSolvingEntryVO(problemSolvingEntryVO);

			if (!oldOtherDetails.isEmpty()) {

				problemSolvingOtherDetailsRepo.deleteAll(oldOtherDetails);
			}

			List<ProblemSolvingActionDetailsVO> oldActionDetails = problemSolvingActionDetailsRepo
					.findByProblemSolvingEntryVO(problemSolvingEntryVO);

			if (!oldActionDetails.isEmpty()) {

				problemSolvingActionDetailsRepo.deleteAll(oldActionDetails);
			}
		}

		// =========================
		// Root Details Grid
		// =========================

		List<ProblemSolvingRootDetailsVO> rootDetailsList = new ArrayList<>();

		if (dto.getProblemSolvingRootDetailsDTO() != null && !dto.getProblemSolvingRootDetailsDTO().isEmpty()) {

			for (ProblemSolvingRootDetailsDTO detailDTO : dto.getProblemSolvingRootDetailsDTO()) {

				ProblemSolvingRootDetailsVO detailVO = new ProblemSolvingRootDetailsVO();

				// =========================
				// Root Cause
				// =========================

				detailVO.setRootCause(detailDTO.getRootCause());

				detailVO.setContributionPercentage(detailDTO.getContributionPercentage());

				// =========================
				// Parent Mapping
				// =========================

				detailVO.setProblemSolvingEntryVO(problemSolvingEntryVO);

				rootDetailsList.add(detailVO);
			}
		}

		problemSolvingEntryVO.setProblemSolvingRootDetailsVO(rootDetailsList);

		// =========================
		// Other Details Grid
		// =========================

		List<ProblemSolvingOtherDetailsVO> otherDetailsList = new ArrayList<>();

		if (dto.getProblemSolvingOtherDetailsDTO() != null && !dto.getProblemSolvingOtherDetailsDTO().isEmpty()) {

			for (ProblemSolvingOtherDetailsDTO detailDTO : dto.getProblemSolvingOtherDetailsDTO()) {

				ProblemSolvingOtherDetailsVO detailVO = new ProblemSolvingOtherDetailsVO();

				// =========================
				// Permanent Corrective Actions
				// =========================

				detailVO.setPermanentCorrectiveActions(detailDTO.getPermanentCorrectiveActions());

				detailVO.setEffectsPercentage(detailDTO.getEffectsPercentage());

				// =========================
				// Parent Mapping
				// =========================

				detailVO.setProblemSolvingEntryVO(problemSolvingEntryVO);

				otherDetailsList.add(detailVO);
			}
		}

		problemSolvingEntryVO.setProblemSolvingOtherDetailsVO(otherDetailsList);

		// =========================
		// Action Details Grid
		// =========================

		List<ProblemSolvingActionDetailsVO> actionDetailsList = new ArrayList<>();

		if (dto.getProblemSolvingActionDetailsDTO() != null && !dto.getProblemSolvingActionDetailsDTO().isEmpty()) {

			for (ProblemSolvingActionDetailsDTO detailDTO : dto.getProblemSolvingActionDetailsDTO()) {

				ProblemSolvingActionDetailsVO detailVO = new ProblemSolvingActionDetailsVO();

				// =========================
				// Action
				// =========================

				detailVO.setAction(detailDTO.getAction());

				detailVO.setDescription(detailDTO.getDescription());

				detailVO.setImplDate(detailDTO.getImplDate());

				// =========================
				// Responsible
				// =========================

				if (detailDTO.getResponsible() != null && detailDTO.getResponsible() != 0) {

					EmployeeMasterVO responsible = employeeRepo.findById(detailDTO.getResponsible())
							.orElseThrow(() -> new ApplicationException("Responsible Employee Not Found"));

					detailVO.setResponsible(responsible);
				}

				// =========================
				// Parent Mapping
				// =========================

				detailVO.setProblemSolvingEntryVO(problemSolvingEntryVO);

				actionDetailsList.add(detailVO);
			}
		}

		problemSolvingEntryVO.setProblemSolvingActionDetailsVO(actionDetailsList);
	}

	private ProblemSolvingEntryResponseDTO problemSolvingEntryResponse(ProblemSolvingEntryVO problemSolvingEntryVO) {

		ProblemSolvingEntryResponseDTO responseDTO = new ProblemSolvingEntryResponseDTO();

		// =========================
		// Basic Fields
		// =========================

		responseDTO.setId(problemSolvingEntryVO.getId());

		responseDTO.setDocId(problemSolvingEntryVO.getDocId());

		responseDTO.setDocDate(problemSolvingEntryVO.getDocDate());

		responseDTO.setReference(problemSolvingEntryVO.getReference());

		responseDTO.setMachineNo(problemSolvingEntryVO.getMachineNo());

		responseDTO.setMfgDate(problemSolvingEntryVO.getMfgDate());

		responseDTO.setDefectDesciption(problemSolvingEntryVO.getDefectDesciption());

		responseDTO.setShortTeamAction(problemSolvingEntryVO.getShortTeamAction());

		responseDTO.setCloseDate(problemSolvingEntryVO.getCloseDate());

		responseDTO.setRecognizeTheTeam(problemSolvingEntryVO.getRecognizeTheTeam());

		responseDTO.setOrgId(problemSolvingEntryVO.getOrgId());

		responseDTO.setFinancialYear(problemSolvingEntryVO.getFinancialYear());

		responseDTO.setActive(problemSolvingEntryVO.getActive());

		responseDTO.setCancelRemarks(problemSolvingEntryVO.getCancelRemarks());

		responseDTO.setCreatedBy(problemSolvingEntryVO.getCreatedBy());

		// =========================
		// Branch Response
		// =========================

		if (problemSolvingEntryVO.getBranch() != null) {

			BranchResponseDTO branchDTO = new BranchResponseDTO();

			branchDTO.setId(problemSolvingEntryVO.getBranch().getId());

			branchDTO.setBranchCode(problemSolvingEntryVO.getBranch().getBranchCode());

			branchDTO.setBranchName(problemSolvingEntryVO.getBranch().getBranchName());

			responseDTO.setBranch(branchDTO);
		}

		// =========================
		// Department Response
		// =========================

		if (problemSolvingEntryVO.getDepartment() != null) {

			DepartmentResponseDTO departmentDTO = new DepartmentResponseDTO();

			departmentDTO.setId(problemSolvingEntryVO.getDepartment().getId());

			departmentDTO.setDepartmentCode(problemSolvingEntryVO.getDepartment().getDepartmentCode());

			departmentDTO.setDepartmentName(problemSolvingEntryVO.getDepartment().getDepartmentName());

			responseDTO.setDepartment(departmentDTO);
		}
		// =========================
		// Customer Response
		// =========================

		if (problemSolvingEntryVO.getCustomer() != null) {

			CustomerResponse1DTO customerDTO = new CustomerResponse1DTO();

			customerDTO.setId(problemSolvingEntryVO.getCustomer().getId());

			customerDTO.setCustomerName(problemSolvingEntryVO.getCustomer().getCustomerName());

			responseDTO.setCustomer(customerDTO);
		}

		// =========================
		// Item Response
		// =========================

		if (problemSolvingEntryVO.getItem() != null) {

			ItemResponse1DTO itemDTO = new ItemResponse1DTO();

			itemDTO.setId(problemSolvingEntryVO.getItem().getId());

			itemDTO.setItemCode(problemSolvingEntryVO.getItem().getItemCode());

			itemDTO.setItemDescription(problemSolvingEntryVO.getItem().getItemDescription());

			responseDTO.setItem(itemDTO);
		}

		// =========================
		// Team Member 1 Response
		// =========================

		if (problemSolvingEntryVO.getTeamMember1() != null) {

			EmployeeDropdownResponseDTO teamMember1DTO = new EmployeeDropdownResponseDTO();

			teamMember1DTO.setEmployeeId(problemSolvingEntryVO.getTeamMember1().getId());

			teamMember1DTO.setEmployeeName(problemSolvingEntryVO.getTeamMember1().getEmployeeName());

			responseDTO.setTeamMember1(teamMember1DTO);
		}

		// =========================
		// Team Member 2 Response
		// =========================

		if (problemSolvingEntryVO.getTeamMember2() != null) {

			EmployeeDropdownResponseDTO teamMember2DTO = new EmployeeDropdownResponseDTO();

			teamMember2DTO.setEmployeeId(problemSolvingEntryVO.getTeamMember2().getId());

			teamMember2DTO.setEmployeeName(problemSolvingEntryVO.getTeamMember2().getEmployeeName());

			responseDTO.setTeamMember2(teamMember2DTO);
		}

		// =========================
		// Prepared By Response
		// =========================

		if (problemSolvingEntryVO.getPreparedBy() != null) {

			EmployeeDropdownResponseDTO preparedByDTO = new EmployeeDropdownResponseDTO();

			preparedByDTO.setEmployeeId(problemSolvingEntryVO.getPreparedBy().getId());

			preparedByDTO.setEmployeeName(problemSolvingEntryVO.getPreparedBy().getEmployeeName());

			responseDTO.setPreparedBy(preparedByDTO);
		}

		// =========================
		// Root Details Response
		// =========================

		List<ProblemSolvingRootDetailsResponseDTO> rootResponseList = new ArrayList<>();

		if (problemSolvingEntryVO.getProblemSolvingRootDetailsVO() != null
				&& !problemSolvingEntryVO.getProblemSolvingRootDetailsVO().isEmpty()) {

			for (ProblemSolvingRootDetailsVO detailVO : problemSolvingEntryVO.getProblemSolvingRootDetailsVO()) {

				ProblemSolvingRootDetailsResponseDTO detailResponseDTO = new ProblemSolvingRootDetailsResponseDTO();

				detailResponseDTO.setRootCause(detailVO.getRootCause());

				detailResponseDTO.setContributionPercentage(detailVO.getContributionPercentage());

				rootResponseList.add(detailResponseDTO);
			}
		}

		responseDTO.setProblemSolvingRootDetailsResponseDTO(rootResponseList);

		// =========================
		// Action Details Response
		// =========================

		List<ProblemSolvingActionDetailsResponseDTO> actionResponseList = new ArrayList<>();

		if (problemSolvingEntryVO.getProblemSolvingActionDetailsVO() != null
				&& !problemSolvingEntryVO.getProblemSolvingActionDetailsVO().isEmpty()) {

			for (ProblemSolvingActionDetailsVO detailVO : problemSolvingEntryVO.getProblemSolvingActionDetailsVO()) {

				ProblemSolvingActionDetailsResponseDTO detailResponseDTO = new ProblemSolvingActionDetailsResponseDTO();

				detailResponseDTO.setAction(detailVO.getAction());

				detailResponseDTO.setDescription(detailVO.getDescription());

				detailResponseDTO.setImplDate(detailVO.getImplDate());

				// =========================
				// Responsible Response
				// =========================

				if (detailVO.getResponsible() != null) {

					EmployeeDropdownResponseDTO responsibleDTO = new EmployeeDropdownResponseDTO();

					responsibleDTO.setEmployeeId(detailVO.getResponsible().getId());

					responsibleDTO.setEmployeeName(detailVO.getResponsible().getEmployeeName());

					detailResponseDTO.setResponsible(responsibleDTO);
				}

				actionResponseList.add(detailResponseDTO);
			}
		}

		responseDTO.setProblemSolvingActionDetailsResponseDTO(actionResponseList);

		// =========================
		// Other Details Response
		// =========================

		List<ProblemSolvingOtherDetailsResponseDTO> otherResponseList = new ArrayList<>();

		if (problemSolvingEntryVO.getProblemSolvingOtherDetailsVO() != null
				&& !problemSolvingEntryVO.getProblemSolvingOtherDetailsVO().isEmpty()) {

			for (ProblemSolvingOtherDetailsVO detailVO : problemSolvingEntryVO.getProblemSolvingOtherDetailsVO()) {

				ProblemSolvingOtherDetailsResponseDTO detailResponseDTO = new ProblemSolvingOtherDetailsResponseDTO();

				detailResponseDTO.setPermanentCorrectiveActions(detailVO.getPermanentCorrectiveActions());

				detailResponseDTO.setEffectsPercentage(detailVO.getEffectsPercentage());

				otherResponseList.add(detailResponseDTO);
			}
		}

		responseDTO.setProblemSolvingOtherDetailsResponseDTO(otherResponseList);

		return responseDTO;
	}

	@Override
	public String getProblemSolvingEntryDocId(Long orgId, String financialYear) {

		String screenCode = "PSE";

		String result = problemSolvingEntryRepo.getProblemSolvingEntryDocId(orgId, financialYear, screenCode);

		return result;
	}

//	dropdown for teammember1,teamember2 and prepared by and responsible
	@Override
	public Map<String, Object> getTeamMemberDropdownForProblemSolvingEntry(Long branch, Long department, Long orgId)
			throws ApplicationException {

		List<Object[]> employeeList = problemSolvingEntryRepo.getTeamMemberDropdownForProblemSolvingEntry(branch,
				department, orgId);

		if (employeeList.isEmpty()) {
			throw new ApplicationException("No Team Member Details Found");
		}

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : employeeList) {

			Map<String, Object> employeeMap = new HashMap<>();

			employeeMap.put("id", obj[0] != null ? ((Number) obj[0]).longValue() : null);

			employeeMap.put("employeeId", obj[1] != null ? obj[1].toString() : null);

			employeeMap.put("employeeName", obj[2] != null ? obj[2].toString() : null);

			responseList.add(employeeMap);
		}

		Map<String, Object> response = new HashMap<>();

		response.put("teamMemberList", responseList);

		return response;
	}

//	dropdown for instruments
	@Override
	public List<Map<String, Object>> getMachineInstrumentDropdownForInitialPlanning(Long orgId, Long branch)
			throws ApplicationException {

		List<Object[]> machineInstrumentList = initialPlanningRepo.getMachineInstrumentDropdownForInitialPlanning(orgId,
				branch);

		if (machineInstrumentList.isEmpty()) {
			throw new ApplicationException("No Machine Instrument Details Found");
		}

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : machineInstrumentList) {

			Map<String, Object> map = new HashMap<>();

			map.put("id", obj[0]);
			map.put("machineInstrumentNo", obj[1]);
			map.put("machineInstrumentName", obj[2]);

			responseList.add(map);
		}

		return responseList;
	}

//	operation master

	@Override
	@Transactional
	public Map<String, Object> updateCreateOperationMaster(OperationMasterDTO operationMasterDTO)
			throws ApplicationException {

		OperationMasterVO operationMasterVO = new OperationMasterVO();

		String message;

		if (ObjectUtils.isNotEmpty(operationMasterDTO.getId())) {

			operationMasterVO = operationMasterRepo.findById(operationMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Operation Master"));

			operationMasterVO.setUpdatedBy(operationMasterDTO.getCreatedBy());

			message = "Operation Master Updated Successfully";

		} else {

			operationMasterVO.setCreatedBy(operationMasterDTO.getCreatedBy());
			operationMasterVO.setUpdatedBy(operationMasterDTO.getCreatedBy());

			message = "Operation Master Created Successfully";
		}

		createUpdateOperationMasterVO(operationMasterDTO, operationMasterVO);

		OperationMasterVO savedVO = operationMasterRepo.save(operationMasterVO);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);
		response.put("operationMasterVO", operationMasterResponse(savedVO));

		return response;
	}

	private void createUpdateOperationMasterVO(OperationMasterDTO dto, OperationMasterVO vo)
			throws ApplicationException {

		vo.setOperationId(dto.getOperationId());
		vo.setDescription(dto.getDescription());
		vo.setActive(dto.isActive());
		vo.setOrgId(dto.getOrgId());
		vo.setCancelRemarks(dto.getCancelRemarks());

		// =========================
		// Tool Details
		// =========================

		vo.getOperationMasterToolDetailsVO().clear();

		if (dto.getOperationMasterToolDetailsDTO() != null) {

			for (OperationMasterToolDetailsDTO detailDTO : dto.getOperationMasterToolDetailsDTO()) {

				OperationMasterToolDetailsVO detailVO = new OperationMasterToolDetailsVO();

				if (detailDTO.getToolId() != null) {

					ToolMasterVO tool = toolMasterRepo.findById(detailDTO.getToolId())
							.orElseThrow(() -> new ApplicationException("Tool Not Found"));

					detailVO.setTool(tool);
				}

				detailVO.setOperationMasterVO(vo);

				vo.getOperationMasterToolDetailsVO().add(detailVO);
			}
		}

		// =========================
		// Machine Details
		// =========================

		vo.getOperationMasterMachineDetailsVO().clear();

		if (dto.getOperationMasterMachineDetailsDTO() != null) {

			for (OperationMasterMachineDetailsDTO detailDTO : dto.getOperationMasterMachineDetailsDTO()) {

				OperationMasterMachineDetailsVO detailVO = new OperationMasterMachineDetailsVO();

				if (detailDTO.getMachine() != null) {

					System.out.println("Machine ID = " + detailDTO.getMachine());

					Optional<MachineMasterVO> machineOptional = machineMasterRepo
							.findMachineById(detailDTO.getMachine());

					System.out.println("Machine found = " + machineOptional.isPresent());

					if (machineOptional.isEmpty()) {
						throw new ApplicationException("Machine Not Found for ID: " + detailDTO.getMachine());
					}

					detailVO.setMachine(machineOptional.get());
				}

				detailVO.setOperationMasterVO(vo);

				vo.getOperationMasterMachineDetailsVO().add(detailVO);
			}
		}

		// =========================
		// Consumable Details
		// =========================

		vo.getOperationMasterConsumableDetailsVO().clear();

		if (dto.getOperationMasterConsumableDetailsDTO() != null) {

			for (OperationMasterConsumableDetailsDTO detailDTO : dto.getOperationMasterConsumableDetailsDTO()) {

				OperationMasterConsumableDetailsVO detailVO = new OperationMasterConsumableDetailsVO();

				// Consumable Item
				if (detailDTO.getConsumables() != null) {

					ItemMasterVO item = itemMasterRepo.findById(detailDTO.getConsumables())
							.orElseThrow(() -> new ApplicationException("Consumable Item Not Found"));

					detailVO.setConsumables(item);
				}

				// Type
				if (detailDTO.getType() != null) {

					ListOfValuesDetailsVO type = listOfValuesDetailsRepo.findById(detailDTO.getType())
							.orElseThrow(() -> new ApplicationException("Consumable Type Not Found"));

					detailVO.setType(type);
				}

				detailVO.setQuantity(detailDTO.getQuantity());

				detailVO.setOperationMasterVO(vo);

				vo.getOperationMasterConsumableDetailsVO().add(detailVO);
			}
		}
	}

	private OperationMasterResponseDTO operationMasterResponse(OperationMasterVO vo) {

		OperationMasterResponseDTO dto = new OperationMasterResponseDTO();

		dto.setId(vo.getId());
		dto.setOperationId(vo.getOperationId());
		dto.setDescription(vo.getDescription());
		dto.setActive(vo.getActiveStatus());
		dto.setOrgId(vo.getOrgId());
		dto.setCreatedBy(vo.getCreatedBy());
		dto.setCancelRemarks(vo.getCancelRemarks());

		// =========================
		// Tool Details Response
		// =========================

		List<OperationMasterToolDetailsResponseDTO> toolResponseList = new ArrayList<>();

		if (vo.getOperationMasterToolDetailsVO() != null && !vo.getOperationMasterToolDetailsVO().isEmpty()) {

			for (OperationMasterToolDetailsVO toolVO : vo.getOperationMasterToolDetailsVO()) {

				OperationMasterToolDetailsResponseDTO toolResponseDTO = new OperationMasterToolDetailsResponseDTO();

				if (toolVO.getTool() != null) {

					OperationMasterToolResponseDTO toolDTO = new OperationMasterToolResponseDTO();

					toolDTO.setId(toolVO.getTool().getId());
					toolDTO.setToolNo(toolVO.getTool().getToolNo());
					toolDTO.setToolDescription(toolVO.getTool().getToolDescription());

					toolResponseDTO.setToolId(toolDTO);
				}

				toolResponseList.add(toolResponseDTO);
			}
		}

		dto.setOperationMasterToolDetailsResponseDTO(toolResponseList);

		// =========================
		// Machine Details Response
		// =========================

		List<OperationMasterMachineDetailsResponseDTO> machineResponseList = new ArrayList<>();

		if (vo.getOperationMasterMachineDetailsVO() != null && !vo.getOperationMasterMachineDetailsVO().isEmpty()) {

			for (OperationMasterMachineDetailsVO machineVO : vo.getOperationMasterMachineDetailsVO()) {

				OperationMasterMachineDetailsResponseDTO machineResponseDTO = new OperationMasterMachineDetailsResponseDTO();

				if (machineVO.getMachine() != null) {

					OperationMasterMachineResponseDTO machineDTO = new OperationMasterMachineResponseDTO();

					machineDTO.setId(machineVO.getMachine().getId());
					machineDTO.setMachineNo(machineVO.getMachine().getMachineInstrumentNo());

					machineDTO.setMachineName(machineVO.getMachine().getMachineInstrumentName());

					machineResponseDTO.setMachine(machineDTO);
				}

				machineResponseList.add(machineResponseDTO);
			}
		}

		dto.setOperationMasterMachineDetailsResponseDTO(machineResponseList);

		// =========================
		// Consumable Details Response
		// =========================

		List<OperationMasterConsumablesDetailsResponseDTO> consumableResponseList = new ArrayList<>();

		if (vo.getOperationMasterConsumableDetailsVO() != null
				&& !vo.getOperationMasterConsumableDetailsVO().isEmpty()) {

			for (OperationMasterConsumableDetailsVO consumableVO : vo.getOperationMasterConsumableDetailsVO()) {

				OperationMasterConsumablesDetailsResponseDTO consumableResponseDTO = new OperationMasterConsumablesDetailsResponseDTO();

				// =========================
				// Item Response
				// =========================

				if (consumableVO.getConsumables() != null) {

				    ItemResponse1DTO itemDTO = new ItemResponse1DTO();

				    itemDTO.setId(consumableVO.getConsumables().getId());
				    itemDTO.setItemCode(consumableVO.getConsumables().getItemCode());
				    itemDTO.setItemDescription(consumableVO.getConsumables().getItemDescription());

				    if (consumableVO.getConsumables().getPricingUnit() != null) {

				        UnitMasterResponseDTO unitDTO = new UnitMasterResponseDTO();

				        unitDTO.setId(consumableVO.getConsumables().getPricingUnit().getId());
				        unitDTO.setUnitId(consumableVO.getConsumables().getPricingUnit().getUnitId());
				        unitDTO.setUnitDescription(
				                consumableVO.getConsumables().getPricingUnit().getDescription());

				        itemDTO.setUnit(unitDTO);
				    }

				    consumableResponseDTO.setConsumables(itemDTO);
				}
				consumableResponseDTO.setQuantity(consumableVO.getQuantity());

				// =========================
				// Type Response
				// =========================

				if (consumableVO.getType() != null) {

					ListOfValuesDetailsResponseDTO typeDTO = new ListOfValuesDetailsResponseDTO();

					typeDTO.setId(consumableVO.getType().getId());
					typeDTO.setCode(consumableVO.getType().getValueCode());
					typeDTO.setDescription(consumableVO.getType().getValueDescription());

					consumableResponseDTO.setType(typeDTO);
				}

				consumableResponseList.add(consumableResponseDTO);
			}
		}

		dto.setOperationMasterConsumablesDetailsResponseDTO(consumableResponseList);

		return dto;
	}

	@Override
	public OperationMasterResponseDTO getOperationMasterById(Long id) throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {

			throw new ApplicationException("Invalid Id");
		}

		OperationMasterVO operationMasterVO = operationMasterRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Operation Master Not Found"));

		return operationMasterResponse(operationMasterVO);
	}

	@Override
	public List<OperationMasterResponseDTO> getOperationMasterByOrgId(Long orgId) throws ApplicationException {

		List<OperationMasterVO> operationMasterList = operationMasterRepo.getOperationMasterByOrgId(orgId);

		if (operationMasterList.isEmpty()) {

			throw new ApplicationException("No Operation Master Details Found");
		}

		List<OperationMasterResponseDTO> responseList = new ArrayList<>();

		for (OperationMasterVO operationMasterVO : operationMasterList) {

			responseList.add(operationMasterResponse(operationMasterVO));
		}

		return responseList;
	}
}
