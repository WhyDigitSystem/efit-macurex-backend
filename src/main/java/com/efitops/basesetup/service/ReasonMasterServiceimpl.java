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
	
//	@Autowired
//	private ControlPlanDetailRepo controlPlanDetailRepo;
//	
//	@Autowired
//	private ControlPlanParameterRepo controlPlanParameterRepo;
//	
//	@Autowired
//	private ControlPlanSampleRepo controlPlanSampleRepo;
//	
//	@Autowired
//	private ControlPlanMachineFixtureRepo controlPlanMachineFixtureRepo;
//	
//	@Autowired
//	private MachineMasterRepo machineMasterRepo;
//	
//	@Autowired
//	private ParameterMasterRepo parameterMasterRepo;
//		
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
	
	
}