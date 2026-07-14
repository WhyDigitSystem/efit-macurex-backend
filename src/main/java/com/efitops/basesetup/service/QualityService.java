package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.EncryptedDocumentException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.DailyPatrolResponseDTO;
import com.efitops.basesetup.dto.DocumentNumberChangeDTO;
import com.efitops.basesetup.dto.EcnApprovalRecordDTO;
import com.efitops.basesetup.dto.EngineeringChangeNoticeRegisterDTO;
import com.efitops.basesetup.dto.FinalInspectionImageResponseDTO;
import com.efitops.basesetup.dto.FinalInspectionReportDTO;
import com.efitops.basesetup.dto.FinalInspectionResponseDTO;
import com.efitops.basesetup.dto.ImageResponseDTO;
import com.efitops.basesetup.dto.IncomingImageResponseDTO;
import com.efitops.basesetup.dto.IncomingMaterialInspectionDTO;
import com.efitops.basesetup.dto.IncomingMaterialResposeDTO;
import com.efitops.basesetup.dto.InprocessImageResponseDTO;
import com.efitops.basesetup.dto.InprocessInspectionDTO;
import com.efitops.basesetup.dto.InprocessResponseDTO;
import com.efitops.basesetup.dto.NPDImageResponseDTO;
import com.efitops.basesetup.dto.NcProductRegisterDTO;
import com.efitops.basesetup.dto.NpdDTO;
import com.efitops.basesetup.dto.ProcessNonConformanceReportDTO;
import com.efitops.basesetup.dto.QADRegisterDTO;
import com.efitops.basesetup.dto.QualityDocumentChangeRecordDTO;
import com.efitops.basesetup.dto.SampleResponseDTO;
import com.efitops.basesetup.dto.SettingResposeDTO;
import com.efitops.basesetup.entity.DocumentNumberChangeVO;
import com.efitops.basesetup.entity.EcnApprovalRecordVO;
import com.efitops.basesetup.entity.EngineeringChangeNoticeRegisterVO;
import com.efitops.basesetup.entity.FinalInspectionReportVO;
import com.efitops.basesetup.entity.IncomingMaterialInspectionVO;
import com.efitops.basesetup.entity.InprocessInspectionVO;
import com.efitops.basesetup.entity.NcProductRegisterVO;
import com.efitops.basesetup.entity.NpdVO;
import com.efitops.basesetup.entity.ProcessNonConformanceReportVO;
import com.efitops.basesetup.entity.QADRegisterVO;
import com.efitops.basesetup.entity.QualityDocumentChangeRecordVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface QualityService {

	// IncomingMaterialInspection

	Map<String, Object> createUpdateIncomingMaterialInspection(
			IncomingMaterialInspectionDTO incomingMaterialInspectionDTO) throws ApplicationException;

	List<IncomingMaterialInspectionVO> getAllIncomingMaterialInspectionByOrgId(Long orgId, String finYear,
			String branchCode);

	IncomingMaterialInspectionVO getIncomingMaterialInspectionById(Long id);

	String getIncomingMaterialInspectionDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getGrnAndSubContractGrnDetails(Long orgId, String grnNo);

	List<Map<String, Object>> getItemNoFromGrn(Long orgId, String grnNo);

	// InprocesInspection

	Map<String, Object> createUpdateInprocessInspection(InprocessInspectionDTO inprocessInspectionDTO)
			throws ApplicationException;

	List<InprocessInspectionVO> getAllInprocessInspectionByOrgId(Long orgId, String finYear, String branchCode);

	InprocessInspectionVO getInprocessInspectionById(Long id);

	String getInprocessInspectionDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getDocIdFromRouteCardNumber(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getDrawingNumberForInProcessInspection(Long orgId, String finYear, String branchCode,
			String fgPartno);

	List<Map<String, Object>> getEmployeeNameFromEmployeeMaster(Long orgId, String branchCode);

	// FinalInspectionReport

	Map<String, Object> createUpdateFinalInspectionReport(FinalInspectionReportDTO finalInspectionReportDTO)
			throws ApplicationException;

	List<FinalInspectionReportVO> getAllFinalInspectionReportByOrgId(Long orgId, String finYear, String branchCode);

	FinalInspectionReportVO getFinalInspectionReportById(Long id);

	String getFinalInspectionReportDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getPartNameForFinalInspectionReport(Long orgId, String routeCardNumber, String branchCode,
			String routeCardNo);

	List<Map<String, Object>> getRouteCardNumberForFinalInspectionReport(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getIncomingMaterialInspectionReport(Long orgId, String grnNo, String supplierName,
			String type);

	List<Map<String, Object>> getInProcessInspectionReport(Long orgId, String branchCode, String fromDate,
			String toDate, String routeCardNo);

	List<Map<String, Object>> getSupplierNameForIncomingMaterialInspectionReport(Long orgId, String branchCode);

	List<Map<String, Object>> getGrnNoForIncomingMaterialInspectionReport(Long orgId, String branchCode,
			String supplierName);

	List<Map<String, Object>> getFinalInspectionReportDetails(Long orgId, String fromdate, String todate);

	List<EngineeringChangeNoticeRegisterVO> getEngineeringChangeNoticeRegisterByOrgId(Long orgId, String finYear,
			String branchCode);

	EngineeringChangeNoticeRegisterVO getEngineeringChangeNoticeRegisterById(Long id);

	String getEngineeringChangeNoticeRegisterDocId(Long orgId, String finYear, String branchCode);

	Map<String, Object> createUpdateEngineeringChangeNoticeRegister(
			EngineeringChangeNoticeRegisterDTO engineeringChangeNoticeRegisterDTO) throws ApplicationException;

	List<NpdVO> getNpdByOrgId(Long orgId, String finYear, String branchCode);

	NpdVO getNpdById(Long id);

	String getNpdDocId(Long orgId, String finYear, String branchCode);

	Map<String, Object> createUpdateNpd(NpdDTO npdDTO) throws ApplicationException;

	List<Map<String, Object>> getCustomerNameFormPartyMaster(Long orgId);

	List<Map<String, Object>> getPartNameFormPartyMaster(Long orgId);

	List<Map<String, Object>> getEmployeeName(Long orgId);

	// Process

	String getProcessNonConformanceReportDocId(Long orgId, String finYear, String branchCode);

	ProcessNonConformanceReportVO getProcessNonConformanceReportById(Long id);

	List<ProcessNonConformanceReportVO> getAllProcessNonConformanceReportByOrgId(Long orgId, String finYear,
			String branchCode);

	Map<String, Object> createUpdateProcessNonConformanceReport(
			ProcessNonConformanceReportDTO processNonConformanceReportDTO) throws ApplicationException;

	List<QualityDocumentChangeRecordVO> getAllQualityDocumentChangeRecordByOrgId(Long orgId, String finYear,
			String branchCode);

	QualityDocumentChangeRecordVO getAllQualityDocumentChangeRecordById(Long id);

	String getQualityDocumentChangeRecordDocId(Long orgId, String finYear, String branchCode);

	Map<String, Object> createUpdateQualityDocumentChangeRecord(
			QualityDocumentChangeRecordDTO qualityDocumentChangeRecordDTO) throws ApplicationException;

	List<Map<String, Object>> getEmployeeNameAndDesignation(Long orgId);

	// ECN

	List<EcnApprovalRecordVO> getAllEcnApprovalRecordByOrgId(Long orgId, String finYear, String branchCode);

	EcnApprovalRecordVO getAllEcnApprovalRecordById(Long id);

	String getEcnApprovalRecordDocId(Long orgId, String finYear, String branchCode);

	Map<String, Object> createUpdateEcnApprovalRecord(EcnApprovalRecordDTO ecnApprovalRecordDTO)
			throws ApplicationException;

	List<Map<String, Object>> getQualityDocumentChangeRecordReport(Long orgId, String branchCode, String fromDate,
			String toDate);

	List<Map<String, Object>> getEcnApprovalRecordReport(Long orgId, String branchCode, String fromDate, String toDate);

	Map<String, Object> updateCreateQADRegister(QADRegisterDTO qadRegisterDTO) throws ApplicationException;

	List<QADRegisterVO> getAllQADRegisterByOrgId(Long orgId, String finYear, String branchCode);

	QADRegisterVO getQADRegisterById(Long id);

	List<Map<String, Object>> getNpdReport(Long orgId, String fromdate, String todate);

	Map<String, Object> updateCreateNCProductRegister(NcProductRegisterDTO ncProductRegisterDTO)
			throws ApplicationException;

	NcProductRegisterVO getNCProductRegisterById(Long id);

	List<NcProductRegisterVO> getNCProductRegisterOrgId(Long orgId, String branchCode, String finYear);

	String getNcProductRegisterDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getEngineeringChangeNoticeRegisterReport(Long orgId, String fromdate, String todate);

//	List<QADRegisterVO> getAllQADRegisterByOrgId(Long orgId, String finYear, String branchCode);

	List<IncomingMaterialResposeDTO> getIncomingMaterialRespose(MultipartFile files)
			throws EncryptedDocumentException, IOException, ApplicationException;

	List<InprocessResponseDTO> getInprocessResponse(MultipartFile file)
			throws EncryptedDocumentException, IOException, ApplicationException;

	List<SettingResposeDTO> getSettingResponse(MultipartFile file)
			throws EncryptedDocumentException, IOException, ApplicationException;

	List<SampleResponseDTO> getSampleResponse(MultipartFile file)
			throws EncryptedDocumentException, IOException, ApplicationException;

	List<DailyPatrolResponseDTO> getDailyPatrolResponse(MultipartFile file)
			throws EncryptedDocumentException, IOException, ApplicationException;

	List<FinalInspectionResponseDTO> getFinalInspectionResponse(MultipartFile file)
			throws EncryptedDocumentException, IOException, ApplicationException;

	List<Map<String, Object>> getQADRegisterReport(Long orgId, String docName);

	List<Map<String, Object>> getNCProductRegisterReport(Long orgId, String fromdate, String todate, String partNo);

	Map<String, Object> createUpdateIncomingMaterialInspection(MultipartFile[] files, String docId, String screenName,
			String module) throws ApplicationException, IOException;

	ResponseEntity<byte[]> viewFile(HttpServletRequest request) throws IOException, IOException;

	List<Map<String, Object>> getDrawingNo(Long orgId, String partNo);

	List<Map<String, Object>> getDrawingOldRevNo(Long orgId, String drawingNo);

	List<Map<String, Object>> getProcessNonConformanceReport(Long orgId, String fromdate, String todate, String partNo);

	List<DocumentNumberChangeVO> getDocumentNumberChangeByOrgId(Long orgId, String finYear, String branchCode);

	DocumentNumberChangeVO getDocumentNumberChangeById(Long id);

	String getDocumentNumberChangeDocId(Long orgId, String finYear, String branchCode);

	Map<String, Object> createUpdateDocumentNumberChange(DocumentNumberChangeDTO documentNumberChangeDTO)
			throws ApplicationException;

	Map<String, Object> createUpdateNpd(MultipartFile[] files, String docId, String screenName, String module)
			throws ApplicationException, IOException;

	List<Map<String, Object>> getDocumentFormateNumber(Long orgId, String screenName);

	Map<String, Object> createUpdateEcn(MultipartFile[] files, String docId, String screenName, String module)
			throws ApplicationException, IOException;

	List<Map<String, Object>> getGrnNoAndSubContractGrnNo(Long orgId, String type);

	List<Map<String, Object>> getListOfGrnNumbers(Long orgId, String type);

	ResponseEntity<byte[]> viewFilesFinal(HttpServletRequest request) throws IOException, IOException;

	Map<String, Object> createUpdateFinalInspection(MultipartFile[] files, String docId, String screenName,
			String module) throws ApplicationException, IOException;

	Map<String, Object> createUpdateProcessNonConformanceReport(MultipartFile[] files, String docId, String screenName,
			String module) throws ApplicationException, IOException;

	ResponseEntity<byte[]> viewFileProcessNon(HttpServletRequest request) throws IOException, IOException;

	Map<String, Object> createUpdateEngineeringChangeNoticeRegister(MultipartFile[] files, String docId,
			String screenName, String module) throws ApplicationException, IOException;

	ResponseEntity<byte[]> viewFileEngineering(HttpServletRequest request) throws IOException, IOException;

	Map<String, Object> createUpdateQualityDocumentChangeRecord(MultipartFile[] files, String docId, String screenName,
			String module) throws ApplicationException, IOException;

	ResponseEntity<byte[]> viewFileDocument(HttpServletRequest request) throws IOException, IOException;

	ResponseEntity<byte[]> viewFileNcProduct(HttpServletRequest request) throws IOException, IOException;

	Map<String, Object> createUpdateNCProductRegister(MultipartFile[] files, String docId, String screenName,
			String module) throws ApplicationException, IOException;

	ResponseEntity<byte[]> viewFileInprocess(HttpServletRequest request) throws IOException, IOException;

	Map<String, Object> createUpdateInprocessInspection(MultipartFile[] files, String docId, String screenName,
			String module) throws ApplicationException, IOException;

	List<ImageResponseDTO> getAllImages(Long id) throws Exception;

	List<ImageResponseDTO> getEngineeringChangeNoticeRegisterImages(Long id) throws Exception;

	String getQADRegisterByDocId(Long orgId, String finYear, String branchCode);

	List<ImageResponseDTO> getNCProductRegisterImages(Long id) throws Exception;

	List<ImageResponseDTO> getProcessNonConformanceReportImages(Long id) throws Exception;
  
//	List<ImageResponseDTO> getIncomingMaterialInspectionImages(Long id) throws Exception;
  
//	List<IncomingImageResponseDTO> getIncomingMaterialInspectionImages(Long id) throws Exception;

	List<InprocessImageResponseDTO> getInprocessInspectionImages(Long id) throws Exception;

	List<FinalInspectionImageResponseDTO> getFinalInspectionReportImages(Long id) throws Exception;

	List<NPDImageResponseDTO> getNPDImages(Long id) throws Exception;

	List<ImageResponseDTO> getIncomingMaterialInspectionImages(Long id) throws Exception;

//	List<DailyPatrolImageResponseDTO> getDailyPatrolInspectionImages(Long id);

	List<ImageResponseDTO> toolRecieveFromCalibrationVO(Long id) throws Exception;

	List<Map<String, Object>> getNPDdetails(Long orgId, String branchCode);

}
