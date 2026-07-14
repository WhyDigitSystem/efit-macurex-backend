package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.ImageResponseDTO;
import com.efitops.basesetup.dto.ToolIssueEntryDTO;
import com.efitops.basesetup.dto.ToolIssueEntryImageResponseDTO;
import com.efitops.basesetup.dto.ToolRecieveFromCalibrationDTO;
import com.efitops.basesetup.dto.ToolRecieveFromCalibrationDetailsDTO;
import com.efitops.basesetup.dto.ToolsIssueToCalibrationDTO;
import com.efitops.basesetup.entity.ToolIssueEntryVO;
import com.efitops.basesetup.entity.ToolRecieveFromCalibrationDetailsVO;
import com.efitops.basesetup.entity.ToolRecieveFromCalibrationVO;
import com.efitops.basesetup.entity.ToolsIssueToCalibrationVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface ToolIssueEntryService {

	List<ToolIssueEntryVO> getToolIssueEntryByOrgId(Long orgId, String finYear, String branchCode);

	List<ToolIssueEntryVO> getToolIssueEntryById(Long id);

	Map<String, Object> updateCreateToolIssueEntry(ToolIssueEntryDTO toolIssueEntryDTO) throws ApplicationException;

	List<Map<String, Object>> getInstrumentforTollIssueForEntry(Long orgId, String finYear, String branchCode);

	String getToolIssueEntryDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getlastcountforTollIssueForEntry(Long orgId);

	// ToolIssueToCalibration

	List<ToolsIssueToCalibrationVO> getToolsIssueToCalibrationByOrgId(Long orgId, String finYear, String branchCode);

	ToolsIssueToCalibrationVO getToolsIssueToCalibrationById(Long id);

	Map<String, Object> updateCreateToolsIssueToCalibration(ToolsIssueToCalibrationDTO toolsIssueToCalibrationDTO)
			throws ApplicationException;

	String getToolsIssueToCalibrationDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getInstrumentdetforToolIssueForcalibration(Long orgId, String finYear, String branchCode);

	// Tool Recieve From Calibration

	List<ToolRecieveFromCalibrationVO> getToolsRecieveFromCalibrationByOrgId(Long orgId, String finYear,
			String branchCode);

	ToolRecieveFromCalibrationVO getToolsRecieveFromCalibrationById(Long id);

	String getToolsRecieveFromCalibrationDocId(Long orgId, String finYear, String branchCode);

	Map<String, Object> updateCreateToolsRecieveFromCalibration(
			ToolRecieveFromCalibrationDTO toolRecieveFromCalibrationDTO) throws ApplicationException;

	List<Map<String, Object>> getIssueDetailsforToolIssueNoForRecieveFormCalibration(Long orgId, String finyear,
			String branchCode);

	List<Map<String, Object>> getInstrumentdetforToolIssueNoForinstrumentRecieveFormCalibration(Long orgId,
			String finYear, String branchCode, String issueNo);

//	ToolRecieveFromCalibrationDetailsVO uploadFileForToolReciveFromcalibcertification(MultipartFile file, Long id)
//			throws IOException;

	List<Map<String, Object>> getPartyMasterDetailsforToolIssueForcalibration(Long orgId, String finYear,
			String branchCode);

	List<Map<String, Object>> getCustomerNameforTollIssueForEntry(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getToolsIssueEntryInstrumentCodeDesc(Long orgId);

	Map<String, Object> uploadFilesForCalibrationDetails(Long toolRecieveFromCalibrationId, Long detailsId,
			ToolRecieveFromCalibrationDetailsDTO dto) throws IOException;

	List<Map<String, Object>> getToolIssueToCalibrationReport(Long orgId, String fromdate, String todate,
			String issuepartyname);

	List<Map<String, Object>> getItemNameAndDesc(Long orgId);

	List<Map<String, Object>> getToolRecieveFromCalibration(Long orgId, String fromdate, String todate);

	List<Map<String, Object>> getInstrumentCodeAndName(Long orgId);

	List<Map<String, Object>> getTollIssueForEntryReport(Long orgId, String fromDate, String toDate,
			String instrumentCodeAndName);

	byte[] viewToolRecieveFromCalibrationImage(Long imageId) throws IOException;

	String getImageFileType(Long id) throws IOException;

	Map<String, Object> uploadToolRecieveFromCalibrationImages(Long toolRecieveId, List<MultipartFile> files)
			throws IOException;

	Map<String, Object> createUpdateToolsIssueToCalibration(MultipartFile[] files, String docId, String screenName,
			String module) throws ApplicationException, IOException;

	ResponseEntity<byte[]> ViewToolIssueToCalibration(HttpServletRequest request) throws IOException, IOException;

	Map<String, Object> createUpdateToolIssueEntry(MultipartFile[] files, String docId, String screenName,
			String module) throws ApplicationException, IOException;

	ResponseEntity<byte[]> ViewToolsIssueEntry(HttpServletRequest request) throws IOException, IOException;

	ResponseEntity<byte[]> viewFileTools(HttpServletRequest request) throws IOException, IOException;

	Map<String, Object> createUpdateToolRecieveFromCalibration(MultipartFile[] files, String docId, String screenName,
			String module) throws ApplicationException, IOException;

	List<ToolIssueEntryImageResponseDTO> getToolIssueEntryImages(Long id) throws Exception;

//	List<ToolIssueEntryImageResponseDTO> getRouteCardEntryImages(Long id);
	List<ImageResponseDTO> ToolsIssueToCalibrationImage(Long id) throws Exception;

}
