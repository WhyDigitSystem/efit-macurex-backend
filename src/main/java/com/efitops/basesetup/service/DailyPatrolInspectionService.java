package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.DailyPatrolInspectionResponseDTO;
import com.efitops.basesetup.dto.DailyPatrolImageResponseDTO;
import com.efitops.basesetup.dto.DailyPatrolInspectionDTO;
import com.efitops.basesetup.dto.ImageResponseDTO;
import com.efitops.basesetup.entity.DailyPatrolInspectionVO;
import com.efitops.basesetup.exception.ApplicationException;

@Repository
public interface DailyPatrolInspectionService {

//	Map<String, Object> updateCreateDailyPatrolInspection(@Valid DailyPatrolInspectionDTO dailyPatrolInspectionDTO)
//			throws ApplicationException;

	String getDailyPatrolInspectionDocId(Long orgId, String finYear, String branchCode);

	Optional<DailyPatrolInspectionVO> getDailyPatrolInspectionById(Long id);

	List<DailyPatrolInspectionVO> getAllDailyPatrolInspection(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getRouteCardNoForDailyPatrollInspection(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getMachineDetailsForDailyPatrolInspection(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getShiftDetails(Long orgId);

//	List<Map<String, Object>> getJobOrderNo();

	List<Map<String, Object>> getDrawingMasterNoForDailyPatrolInspection(Long orgId, String finYear, String branchCode,
			String partNo);

	List<Map<String, Object>> getJobOrderNoForDailyPatrolInspection(Long orgId, String finYear, String branchCode,
			String routeCardNo);

	List<Map<String, Object>> getDailyPatrolInspectionDetails(Long orgId, String fromdate, String todate);

	List<Map<String, Object>> getEmployeeNameBasedOnDepartment(Long orgId, String branchCode);

	DailyPatrolInspectionResponseDTO previewDailyPatrolInspectionExcel(MultipartFile file) throws Exception;

	Map<String, Object> updateCreateDailyPatrolInspection(DailyPatrolInspectionDTO dailyPatrolInspectionDTO,
			List<MultipartFile> files) throws ApplicationException, IOException;

	List<Map<String, Object>> getInspectionByInchargeName(Long orgId, String branchCode);

	ResponseEntity<byte[]> view(HttpServletRequest request) throws IOException, IOException;

	Map<String, Object> createUpdateDailyPatrolInspection(MultipartFile[] files, String docId, String screenName,
			String module) throws ApplicationException, IOException;

	List<ImageResponseDTO> getAllImages(Long id) throws Exception;

	List<DailyPatrolImageResponseDTO> getDailyPatrolInsImages(Long id) throws Exception;


}