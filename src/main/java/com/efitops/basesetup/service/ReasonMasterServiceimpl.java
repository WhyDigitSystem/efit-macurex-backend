package com.efitops.basesetup.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.efitops.basesetup.ResponseDTO.DepartmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.ListOfValuesDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.ReasonMasterResponseDTO;
import com.efitops.basesetup.dto.ControlPlanDTO;
import com.efitops.basesetup.dto.ControlPlanDetailDTO;
import com.efitops.basesetup.dto.ControlPlanMachineFixtureDTO;
import com.efitops.basesetup.dto.ControlPlanParameterDTO;
import com.efitops.basesetup.dto.ControlPlanSampleDTO;
import com.efitops.basesetup.dto.ReasonMasterDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.ControlPlanDetailVO;
import com.efitops.basesetup.entity.ControlPlanMachineFixtureVO;
import com.efitops.basesetup.entity.ControlPlanParameterVO;
import com.efitops.basesetup.entity.ControlPlanSampleVO;
import com.efitops.basesetup.entity.ControlPlanVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.GradeMasterVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.ListOfValuesDetailsVO;
import com.efitops.basesetup.entity.MachineMasterVO;
import com.efitops.basesetup.entity.ParameterMasterVO;
import com.efitops.basesetup.entity.ReasonMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.ControlPlanDetailRepo;
import com.efitops.basesetup.repository.ControlPlanMachineFixtureRepo;
import com.efitops.basesetup.repository.ControlPlanParameterRepo;
import com.efitops.basesetup.repository.ControlPlanRepo;
import com.efitops.basesetup.repository.ControlPlanSampleRepo;
import com.efitops.basesetup.repository.DepartmentRepo;
import com.efitops.basesetup.repository.EmployeeMasterRepo;
import com.efitops.basesetup.repository.GradeMasterRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.ListOfValuesDetailsRepo;
import com.efitops.basesetup.repository.MachineMasterRepo;
import com.efitops.basesetup.repository.ParameterMasterRepo;
import com.efitops.basesetup.repository.ReasonMasterRepo;


@Service
public class ReasonMasterServiceimpl implements ReasonMasterService {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(ReasonMasterServiceimpl.class);
	
	
	@Autowired
	 private ReasonMasterRepo reasonMasterRepo;
	
	@Autowired
	private DepartmentRepo departmentRepo;
	
	@Autowired
	private ListOfValuesDetailsRepo listOfValuesDetailsRepo;
	
	@Autowired
	private ControlPlanRepo controlPlanRepo;
	
	@Autowired
	private BranchRepo branchRepo;
	
	@Autowired
	private ItemMasterRepo itemMasterRepo;
	
	@Autowired
	private GradeMasterRepo gradeMasterRepo;
	
	@Autowired
	private EmployeeMasterRepo employeeMasterRepo;
	
	@Autowired
	private ControlPlanDetailRepo controlPlanDetailRepo;
	
	@Autowired
	private ControlPlanParameterRepo controlPlanParameterRepo;
	
	@Autowired
	private ControlPlanSampleRepo controlPlanSampleRepo;
	
	@Autowired
	private ControlPlanMachineFixtureRepo controlPlanMachineFixtureRepo;
	
	@Autowired
	private MachineMasterRepo machineMasterRepo;
	
	@Autowired
	private ParameterMasterRepo parameterMasterRepo;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//Reasonmaster
	
	
	@Override
	@Transactional
	public Map<String, Object> createUpdateReasonMaster(ReasonMasterDTO reasonMasterDTO)
	        throws ApplicationException {

	    ReasonMasterVO reasonMasterVO;
	    String message;

	    // =========================================================
	    // CREATE / UPDATE
	    // =========================================================

	    if (ObjectUtils.isNotEmpty(reasonMasterDTO.getId())) {

	        reasonMasterVO = reasonMasterRepo.findById(reasonMasterDTO.getId())
	                .orElseThrow(() ->
	                        new ApplicationException("Reason Master Not Found"));

	        reasonMasterVO.setUpdatedBy(reasonMasterDTO.getCreatedBy());

	        message = "Reason Master Updated Successfully";

	    } else {

	        reasonMasterVO = new ReasonMasterVO();

	        reasonMasterVO.setCreatedBy(reasonMasterDTO.getCreatedBy());
	        reasonMasterVO.setUpdatedBy(reasonMasterDTO.getCreatedBy());

	        message = "Reason Master Created Successfully";
	    }

	    // =========================================================
	    // HEADER MAPPING
	    // =========================================================

	    createUpdateReasonMasterVOByDTO(
	            reasonMasterDTO,
	            reasonMasterVO);

	    // =========================================================
	    // SAVE
	    // =========================================================

	    reasonMasterVO = reasonMasterRepo.save(reasonMasterVO);

	    // =========================================================
	    // RESPONSE
	    // =========================================================

	    ReasonMasterResponseDTO responseDTO =
	            buildReasonMasterResponse(reasonMasterVO);

	    Map<String, Object> response = new HashMap<>();

	    response.put("message", message);
	    response.put("reasonMasterVO", responseDTO);

	    return response;
	}
	
	
	
	private void createUpdateReasonMasterVOByDTO(
	        ReasonMasterDTO dto,
	        ReasonMasterVO vo) throws ApplicationException {

	    // =========================================================
	    // DEPARTMENT
	    // =========================================================

	    if (dto.getDepartment() != null && dto.getDepartment() > 0) {

	        DepartmentVO department = departmentRepo.findById(dto.getDepartment())
	                .orElseThrow(() ->
	                        new ApplicationException("Department Not Found"));

	        vo.setDepartment(department);
	    }

	    // =========================================================
	    // REASON
	    // =========================================================

	    if (dto.getReason() != null && dto.getReason() > 0) {

	        ListOfValuesDetailsVO reason =
	                listOfValuesDetailsRepo.findById(dto.getReason())
	                        .orElseThrow(() ->
	                                new ApplicationException("Reason Not Found"));

	        vo.setReason(reason);
	    }

	    // =========================================================
	    // REASON CODE
	    // =========================================================

	    vo.setReasonCode(dto.getReasonCode());

	    // =========================================================
	    // REASON DESCRIPTION
	    // =========================================================

	    vo.setReasonDescription(dto.getReasonDescription());

	    // =========================================================
	    // NARRATION
	    // =========================================================

	    vo.setNarration(dto.getNarration());

	    // =========================================================
	    // COMMON FIELDS
	    // =========================================================

	    vo.setActive(dto.isActive());

	    vo.setOrgId(dto.getOrgId());

	    vo.setCreatedBy(dto.getCreatedBy());

	    vo.setUpdatedBy(dto.getUpdatedBy());

	    vo.setCancel(dto.isCancel());

	    vo.setCancelRemarks(dto.getCancelRemarks());

	    // =========================================================
	    // SCREEN DETAILS
	    // =========================================================

	    vo.setScreenName("REASONMASTER");
	    vo.setScreenCode("RM");
	}
	
	
	private ReasonMasterResponseDTO buildReasonMasterResponse(
	        ReasonMasterVO reasonMasterVO) {

	    ReasonMasterResponseDTO responseDTO =
	            new ReasonMasterResponseDTO();

	    // =========================================================
	    // BASIC DETAILS
	    // =========================================================

	    responseDTO.setId(reasonMasterVO.getId());

	    responseDTO.setReasonCode(
	            reasonMasterVO.getReasonCode());

	    responseDTO.setReasonDescription(
	            reasonMasterVO.getReasonDescription());

	    responseDTO.setNarration(
	            reasonMasterVO.getNarration());

	    responseDTO.setActive(
	            reasonMasterVO.isActive());

	    responseDTO.setOrgId(
	            reasonMasterVO.getOrgId());

	    responseDTO.setCreatedBy(
	            reasonMasterVO.getCreatedBy());

	    responseDTO.setUpdatedBy(
	            reasonMasterVO.getUpdatedBy());

	    responseDTO.setCancel(
	            reasonMasterVO.isCancel());

	    responseDTO.setCancelRemarks(
	            reasonMasterVO.getCancelRemarks());

	    responseDTO.setScreenName(
	            reasonMasterVO.getScreenName());

	    responseDTO.setScreenCode(
	            reasonMasterVO.getScreenCode());

	    // =========================================================
	    // ACTIVE STATUS
	    // =========================================================

	    responseDTO.setActiveStatus(
	            reasonMasterVO.isActive()
	                    ? "Active"
	                    : "In-Active");

	    // =========================================================
	    // CANCEL STATUS
	    // =========================================================

	    responseDTO.setCancelStatus(
	            reasonMasterVO.isCancel()
	                    ? "T"
	                    : "F");

	    // =========================================================
	    // DEPARTMENT
	    // =========================================================

	    if (reasonMasterVO.getDepartment() != null) {

	        DepartmentResponseDTO departmentDTO =
	                new DepartmentResponseDTO();

	        departmentDTO.setId(
	                reasonMasterVO.getDepartment().getId());

	        departmentDTO.setDepartmentCode(
	                reasonMasterVO.getDepartment().getDepartmentCode());

	        departmentDTO.setDepartmentName(
	                reasonMasterVO.getDepartment().getDepartmentName());

	        responseDTO.setDepartment(departmentDTO);
	    }

	    // =========================================================
	    // REASON
	    // =========================================================

	    if (reasonMasterVO.getReason() != null) {

	        ListOfValuesDetailsResponseDTO reasonDTO =
	                new ListOfValuesDetailsResponseDTO();

	        reasonDTO.setId(
	                reasonMasterVO.getReason().getId());

	        reasonDTO.setCode(
	                reasonMasterVO.getReason().getValueCode());

	        reasonDTO.setDescription(
	                reasonMasterVO.getReason().getValueDescription());

	        responseDTO.setReason(reasonDTO);
	    }

	    return responseDTO;
	}
	
	
	@Override
	public ReasonMasterResponseDTO getReasonMasterById(Long id) throws ApplicationException {

	    ReasonMasterVO reasonMasterVO = reasonMasterRepo.findById(id).orElse(null);

	    if (reasonMasterVO == null) {
	        throw new ApplicationException("Reason Master Not Found");
	    }

	    return buildReasonMasterResponse(reasonMasterVO);
	}


	@Override
	public List<ReasonMasterResponseDTO> getReasonMasterByOrgId(Long orgId)
	        throws ApplicationException {

	    List<ReasonMasterVO> reasonMasterList =
	            reasonMasterRepo.findByOrgId(orgId);

	    if (reasonMasterList == null || reasonMasterList.isEmpty()) {
	        throw new ApplicationException("Reason Master Not Found");
	    }

	    List<ReasonMasterResponseDTO> responseList = new ArrayList<>();

	    for (ReasonMasterVO reasonMasterVO : reasonMasterList) {

	        responseList.add(
	                buildReasonMasterResponse(reasonMasterVO)
	        );
	    }

	    return responseList;
	}
	
	
	//CONTROL PLAN
	
	@Override
	@Transactional
	public Map<String, Object> updateCreateControlPlan(ControlPlanDTO controlPlanDTO)
	        throws ApplicationException {

	    ControlPlanVO controlPlanVO = new ControlPlanVO();
	    String message;

	    // =========================
	    // Update
	    // =========================
	    if (ObjectUtils.isNotEmpty(controlPlanDTO.getId())) {

	        controlPlanVO = controlPlanRepo.findById(controlPlanDTO.getId())
	                .orElseThrow(() -> new ApplicationException("Invalid Control Plan Details"));

	        controlPlanVO.setUpdatedBy(controlPlanDTO.getCreatedBy());

	        message = "Control Plan Updated Successfully";

	    } else {

	        // =========================
	        // Create
	        // =========================
	        controlPlanVO = new ControlPlanVO();

	        controlPlanVO.setCreatedBy(controlPlanDTO.getCreatedBy());
	        controlPlanVO.setUpdatedBy(controlPlanDTO.getCreatedBy());
	        controlPlanVO.setDocDate(LocalDate.now());

	        message = "Control Plan Created Successfully";
	    }

	    // =========================
	    // Basic Mapping
	    // =========================
	    createUpdateControlPlanVO(controlPlanDTO, controlPlanVO);

	    // =========================
	    // Save Parent
	    // =========================
	    ControlPlanVO savedVO = controlPlanRepo.save(controlPlanVO);

	    // =========================
	    // Response
	    // =========================
	    Map<String, Object> response = new HashMap<>();

	    response.put("message", message);
//	    response.put("controlPlanVO", controlPlanResponse(savedVO));

	    return response;
	}
	
	private void createUpdateControlPlanVO(
	        ControlPlanDTO dto,
	        ControlPlanVO controlPlanVO) throws ApplicationException {

	    // =========================
	    // Basic Fields
	    // =========================

	    controlPlanVO.setRevisionDate(dto.getRevisionDate());
	    controlPlanVO.setPlanNo(dto.getPlanNo());
	    controlPlanVO.setItemDescription(dto.getItemDescription());
	    controlPlanVO.setItemSize(dto.getItemSize());
	    controlPlanVO.setProcessSheetNo(dto.getProcessSheetNo());
	    controlPlanVO.setApproved(dto.isApproved());
	    controlPlanVO.setActive(dto.isActive());
	    controlPlanVO.setOrgId(dto.getOrgId());
	    controlPlanVO.setCancel(dto.isCancel());
	    controlPlanVO.setCancelRemarks(dto.getCancelRemarks());

	    // =========================
	    // Branch
	    // =========================

	    if (dto.getBranch() != null && dto.getBranch() != 0) {

	        BranchVO branch = branchRepo.findById(dto.getBranch())
	                .orElseThrow(() -> new ApplicationException("Branch Not Found"));

	        controlPlanVO.setBranch(branch);
	    }

	    // =========================
	    // Control Plan Type
	    // =========================

	    if (dto.getControlPlanType() != null && dto.getControlPlanType() != 0) {

	        ListOfValuesDetailsVO controlPlanType =
	                listOfValuesDetailsRepo.findById(dto.getControlPlanType())
	                .orElseThrow(() -> new ApplicationException("Control Plan Type Not Found"));

	        controlPlanVO.setControlPlanType(controlPlanType);
	    }

	    // =========================
	    // FG Item Code
	    // =========================

	    if (dto.getFgItemCode() != null && dto.getFgItemCode() != 0) {

	        ItemMasterVO item = itemMasterRepo.findById(dto.getFgItemCode())
	                .orElseThrow(() -> new ApplicationException("FG Item Not Found"));

	        controlPlanVO.setFgItemCode(item);
	    }

	    // =========================
	    // Item Grade
	    // =========================

	    if (dto.getItemGrade() != null && dto.getItemGrade() != 0) {

	        GradeMasterVO grade = gradeMasterRepo.findById(dto.getItemGrade())
	                .orElseThrow(() -> new ApplicationException("Item Grade Not Found"));

	        controlPlanVO.setItemGrade(grade);
	    }

	    // =========================
	    // Prepared By
	    // =========================

	    if (dto.getPreparedBy() != null && dto.getPreparedBy() != 0) {

	        EmployeeMasterVO preparedBy = employeeMasterRepo.findById(dto.getPreparedBy())
	                .orElseThrow(() -> new ApplicationException("Prepared By Not Found"));

	        controlPlanVO.setPreparedBy(preparedBy);
	    }

	    // =========================
	    // Checked By
	    // =========================

	    if (dto.getCheckedBy() != null && dto.getCheckedBy() != 0) {

	        EmployeeMasterVO checkedBy = employeeMasterRepo.findById(dto.getCheckedBy())
	                .orElseThrow(() -> new ApplicationException("Checked By Not Found"));

	        controlPlanVO.setCheckedBy(checkedBy);
	    }

	    // ============================================================
	    // UPDATE - DELETE EXISTING CHILD GRIDS
	    // ============================================================

	    if (dto.getId() != null) {

	        // Detail
	        if (controlPlanVO.getControlPlanDetailVO() != null
	                && !controlPlanVO.getControlPlanDetailVO().isEmpty()) {

	            controlPlanDetailRepo.deleteAll(
	                    controlPlanVO.getControlPlanDetailVO());
	        }

	        // Parameter
	        if (controlPlanVO.getControlPlanParameterVO() != null
	                && !controlPlanVO.getControlPlanParameterVO().isEmpty()) {

	            controlPlanParameterRepo.deleteAll(
	                    controlPlanVO.getControlPlanParameterVO());
	        }

	        // Sample
	        if (controlPlanVO.getControlPlansampleVO() != null
	                && !controlPlanVO.getControlPlansampleVO().isEmpty()) {

	            controlPlanSampleRepo.deleteAll(
	                    controlPlanVO.getControlPlansampleVO());
	        }

	        // Machine / Fixture
	        if (controlPlanVO.getControlPlanMachineFixtureVO() != null
	                && !controlPlanVO.getControlPlanMachineFixtureVO().isEmpty()) {

	            controlPlanMachineFixtureRepo.deleteAll(
	                    controlPlanVO.getControlPlanMachineFixtureVO());
	        }

	        // Clear parent collections
	        controlPlanVO.getControlPlanDetailVO().clear();
	        controlPlanVO.getControlPlanParameterVO().clear();
	        controlPlanVO.getControlPlansampleVO().clear();
	        controlPlanVO.getControlPlanMachineFixtureVO().clear();
	    }

	    // ============================================================
	    // DETAIL GRID
	    // ============================================================

	    List<ControlPlanDetailVO> detailList = new ArrayList<>();

	    if (dto.getControlPlanDetailDTO() != null
	            && !dto.getControlPlanDetailDTO().isEmpty()) {

	        for (ControlPlanDetailDTO detailDTO :
	                dto.getControlPlanDetailDTO()) {

	            ControlPlanDetailVO detailVO = new ControlPlanDetailVO();

	            // =========================
	            // Basic Detail Fields
	            // =========================

	            detailVO.setOperationNo(detailDTO.getOperationNo());
	            detailVO.setProcess(detailDTO.getProcess());
	            detailVO.setSpecification(detailDTO.getSpecification());
	            detailVO.setRiskClassSpecialCharacter(
	                    detailDTO.getRiskClassSpecialCharacter());
	            detailVO.setEvaluationTechnique(
	                    detailDTO.getEvaluationTechnique());
	            detailVO.setReactionPlan(detailDTO.getReactionPlan());
	            detailVO.setRecord(detailDTO.getRecord());

	            // =========================
	            // Machine Device
	            // =========================

	            if (detailDTO.getMachineDevice() != null
	                    && detailDTO.getMachineDevice() != 0) {

	                MachineMasterVO machineDevice =
	                        machineMasterRepo.findById(detailDTO.getMachineDevice())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Machine Device Not Found"));

	                detailVO.setMachineDevice(machineDevice);
	            }

	            // =========================
	            // Control Method
	            // =========================

	            if (detailDTO.getControlMethod() != null
	                    && detailDTO.getControlMethod() != 0) {

	                ListOfValuesDetailsVO controlMethod =
	                        listOfValuesDetailsRepo.findById(
	                                detailDTO.getControlMethod())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Control Method Not Found"));

	                detailVO.setControlMethod(controlMethod);
	            }

	            // =========================
	            // Parent Mapping
	            // =========================

	            detailVO.setControlPlanVO(controlPlanVO);

	            detailList.add(detailVO);
	        }
	    }

	    controlPlanVO.setControlPlanDetailVO(detailList);


	 // ============================================================
	 // PARAMETER GRID
	 // ============================================================

	 List<ControlPlanParameterVO> parameterList = new ArrayList<>();

	 if (dto.getControlPlanParameterDTO() != null
	         && !dto.getControlPlanParameterDTO().isEmpty()) {

	     for (ControlPlanParameterDTO parameterDTO :
	             dto.getControlPlanParameterDTO()) {

	         ControlPlanParameterVO parameterVO =
	                 new ControlPlanParameterVO();

	         if (parameterDTO.getParameter() != null
	                 && parameterDTO.getParameter() != 0) {

	             ParameterMasterVO parameter =
	                     parameterMasterRepo.findById(
	                             parameterDTO.getParameter())
	                     .orElseThrow(() ->
	                             new ApplicationException(
	                                     "Parameter Not Found"));

	             parameterVO.setParameter(parameter);

	             if (parameter.getParameterType() != null) {

	                 parameterVO.setParameterType(
	                         parameter.getParameterType()
	                                 .getValueDescription());
	             }
	         }

	         parameterVO.setTol(parameterDTO.getTol());

	         parameterVO.setControlPlanVO(controlPlanVO);

	         parameterList.add(parameterVO);
	     }
	 }

	 controlPlanVO.setControlPlanParameterVO(parameterList);


	 // ============================================================
	 // SAMPLE GRID
	 // ============================================================

	 List<ControlPlanSampleVO> sampleList = new ArrayList<>();

	 if (dto.getControlPlanSampleDTO() != null
	         && !dto.getControlPlanSampleDTO().isEmpty()) {

	     for (ControlPlanSampleDTO sampleDTO :
	             dto.getControlPlanSampleDTO()) {

	         ControlPlanSampleVO sampleVO =
	                 new ControlPlanSampleVO();

	         sampleVO.setSampleFrequency(
	                 sampleDTO.getSampleFrequency());

	         sampleVO.setSize(
	                 sampleDTO.getSize());

	         sampleVO.setControlPlanVO(controlPlanVO);

	         sampleList.add(sampleVO);
	     }
	 }

	 controlPlanVO.setControlPlansampleVO(sampleList);


	 // ============================================================
	 // MACHINE / FIXTURE GRID
	 // ============================================================

	 List<ControlPlanMachineFixtureVO> machineFixtureList =
	         new ArrayList<>();

	 if (dto.getControlPlanMachineFixtureDTO() != null
	         && !dto.getControlPlanMachineFixtureDTO().isEmpty()) {

	     for (ControlPlanMachineFixtureDTO machineFixtureDTO :
	             dto.getControlPlanMachineFixtureDTO()) {

	         ControlPlanMachineFixtureVO machineFixtureVO =
	                 new ControlPlanMachineFixtureVO();

	         if (machineFixtureDTO.getMachineFixture() != null
	                 && machineFixtureDTO.getMachineFixture() != 0) {

	             MachineMasterVO machineFixture =
	                     machineMasterRepo.findById(
	                             machineFixtureDTO.getMachineFixture())
	                     .orElseThrow(() ->
	                             new ApplicationException(
	                                     "Machine Fixture Not Found"));

	             machineFixtureVO.setMachineFixture(machineFixture);
	         }

	         machineFixtureVO.setMachineFixtureName(
	                 machineFixtureDTO.getMachineFixtureName());

	         machineFixtureVO.setControlPlanVO(controlPlanVO);

	         machineFixtureList.add(machineFixtureVO);
	     }
	 }

	 controlPlanVO.setControlPlanMachineFixtureVO(
	         machineFixtureList);

	 }
}
