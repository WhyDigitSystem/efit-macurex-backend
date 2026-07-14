package com.efitops.basesetup.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.SettingApprovalResponseDTO;
import com.efitops.basesetup.dto.SampleApprovalDTO;
import com.efitops.basesetup.dto.SampleImageResponseDTO;
import com.efitops.basesetup.dto.SettingApprovalDTO;
import com.efitops.basesetup.dto.ThirdPartyAttachmentDTO;
import com.efitops.basesetup.entity.SampleApprovalVO;
import com.efitops.basesetup.entity.SettingApprovalVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface QualityApprovalServive {

	List<SettingApprovalVO> getAllSettingApprovalByOrgId(Long orgId, String finYear, String branchCode);

	SettingApprovalVO getSettingApprovalById(Long id);

	String getSettingApprovalDocId(Long orgId, String finYear, String branchCode);

//	Map<String, Object> createUpdateSettingApproval(SettingApprovalDTO settingApprovalDTO, List<MultipartFile> files)
//			throws ApplicationException, IOException;

	List<Map<String, Object>> getRouteCardDetailsForSettingApproval(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getDrawingNoForSettingApproval(Long orgId, String finYear, String branchCode,
			String partNo);

	List<Map<String, Object>> getMachineNoForSettingApproval(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getOperatorNameForSettingApproval(Long orgId, String branchCode);

	List<Map<String, Object>> getSetterNameForSettingApproval(Long orgId, String branchCode);

	List<Map<String, Object>> getQualityNameForSettingApproval(Long orgId, String branchCode);

	List<Map<String, Object>> getShiftInChargeForSettingApproval(Long orgId, String branchCode);

	// sampleApproval

	List<SampleApprovalVO> getAllSampleApprovalByOrgId(Long orgId, String finYear, String branchCode);

	SampleApprovalVO getSampleApprovalById(Long id);

	String getSampleApprovalDocId(Long orgId, String finYear, String branchCode);

	Map<String, Object> createUpdateSampleApproval(SampleApprovalDTO sampleApprovalDTO) throws ApplicationException;

	List<Map<String, Object>> getRouteCardDetailsForSampleApproval(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getDrawingMasterNoForSampleApproval(Long orgId, String partNo, String branchCode,
			String partNo2);

	List<Map<String, Object>> getMachineNoForSampleApproval(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getJobOrderNoForSampleApproval(Long orgId, String finYear, String branchCode,
			String routeCardNo, String operation);

	List<Map<String, Object>> getSettingApprovalReport(Long orgId, String branchCode, String fromDate, String toDate,
			String routeCardNo);

	List<Map<String, Object>> getSampleApprovalDetails(Long orgId, String fromdate, String todate, String routeCardNo);

	List<Map<String, Object>> getEmployeeNameBasedOnDesgnation(Long orgId, String branchCode);

	Map<String, Object> createUpdateSettingApproval(MultipartFile[] files, String docId, String screenName,
			String module) throws ApplicationException, IOException;

	ResponseEntity<byte[]> viewFileSample(HttpServletRequest request) throws IOException, IOException;

	ResponseEntity<byte[]> viewFileSetting(HttpServletRequest request) throws IOException, IOException;

	Map<String, Object> createUpdateSampleApproval(MultipartFile[] files, String docId, String screenName,
			String module) throws ApplicationException, IOException;

	Map<String, Object> createUpdateSettingApproval(SettingApprovalDTO settingApprovalDTO)
			throws ApplicationException, IOException;

	List<SampleImageResponseDTO> getSampleApprovalImages(Long id) throws Exception;

}
