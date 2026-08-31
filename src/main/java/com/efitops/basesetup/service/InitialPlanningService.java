package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import com.efitops.basesetup.ResponseDTO.InitialPlanningResponseDTO;
import com.efitops.basesetup.dto.InitialPlanningDTO;
import com.efitops.basesetup.dto.ProblemSolvingEntryDTO;
import com.efitops.basesetup.exception.ApplicationException;

public interface InitialPlanningService {

	Map<String, Object> updateCreateInitialPlanning(InitialPlanningDTO initialPlanningDTO) throws ApplicationException;

	List<InitialPlanningResponseDTO> getInitialPlanningByOrgId(Long orgId) throws ApplicationException;

	InitialPlanningResponseDTO getInitialPlanningById(Long id) throws ApplicationException;

	String getInitialPlanningDocId(Long orgId, String financialYear);

	Map<String, Object> getItemDropdownForInitialPlanning(Long itemType, Long orgId) throws ApplicationException;

	Map<String, Object> getParameterDropdownForInitialPlanning(Long orgId) throws ApplicationException;

//	problem Solving entry
	Map<String, Object> updateCreateProblemSolvingEntry(ProblemSolvingEntryDTO problemSolvingEntryDTO)
			throws ApplicationException;

	String getProblemSolvingEntryDocId(Long orgId, String financialYear);

	Map<String, Object> getTeamMemberDropdownForProblemSolvingEntry(Long branch, Long department, Long orgId)
			throws ApplicationException;

}
