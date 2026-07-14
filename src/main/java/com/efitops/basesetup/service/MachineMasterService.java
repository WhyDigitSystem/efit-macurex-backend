package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.DrawingMasterDTO;
import com.efitops.basesetup.dto.ImageResponseDTO;
import com.efitops.basesetup.dto.MachineMasterDTO;
import com.efitops.basesetup.dto.StockLocationDTO;
import com.efitops.basesetup.entity.DrawingMasterDetailsVO;
import com.efitops.basesetup.entity.DrawingMasterAttachmentsVO;
import com.efitops.basesetup.entity.DrawingMasterVO;
import com.efitops.basesetup.entity.MachineMasterVO;
import com.efitops.basesetup.entity.StockLocationVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface MachineMasterService {

	Map<String, Object> updateCreateMachineMaster(@Valid MachineMasterDTO machineMasterDTO) throws ApplicationException;

	List<MachineMasterVO> getAllMachineMasterByOrgId(Long orgId, String branchCode);

	Optional<MachineMasterVO> getAllMachineMasterById(Long id);

	MachineMasterVO uploadMachineAttachementsInBloob(MultipartFile file, Long id) throws IOException;

	String getMachineMasterDocId(Long orgId, String finYear, String branchCode);

	MachineMasterVO getMachineMasterByDocId(Long orgId, String docId);

	// STOCKLOCATION

	Map<String, Object> updateCreateStockLocation(@Valid StockLocationDTO stockLocationDTO) throws ApplicationException;

	List<StockLocationVO> getAllStockLocationByOrgId(Long orgId);

	Optional<StockLocationVO> getAllStockLocationById(Long id);

	List<Map<String, Object>> getCompanyForStockLocation(Long orgId);

	// DRAWING MASTER

	Map<String, Object> updateDrawingMaster(@Valid DrawingMasterDTO drawingMasterDTO) throws ApplicationException;

	List<DrawingMasterVO> getAllDrawingMasterByOrgId(Long orgId, String branchCode);

	Optional<DrawingMasterVO> getAllDrawingMasterById(Long id);

//	List<DrawingMaster1VO> uploadAttachementsInBloob(MultipartFile file, Long id) throws IOException;

	DrawingMasterAttachmentsVO uploadAttachementsInBloob1(MultipartFile file, Long id) throws IOException;

	List<Map<String, Object>> getFGSFGPartDetailsForDrawingMaster(Long orgId);

	String getDrawingMasterDocId(Long orgId, String finYear, String branchCode);

	List<DrawingMasterAttachmentsVO> uploadDrawingAttachmentsInBloob(Long headerId, List<MultipartFile> files)
			throws IOException;

	List<DrawingMasterDetailsVO> uploadDrawingDetailsAttachmentsInBloob(Long headerId, List<MultipartFile> files)
			throws IOException;

	Map<String, Object> createUpdateMachineMaster(MultipartFile[] files, String docId, String screenName, String module)
			throws ApplicationException, IOException;

	ResponseEntity<byte[]> viewFile(HttpServletRequest request) throws IOException, IOException;

	List<ImageResponseDTO> getMachineMasterImages(Long id) throws Exception;

	Map<String, Object> createUpdateDrawingMasterDocumentImage(MultipartFile[] files, String docId, String screenName,
			String module, List<String> fileNames) throws ApplicationException, IOException;

	ResponseEntity<byte[]> viewDocumets(HttpServletRequest request) throws IOException;

	ResponseEntity<byte[]> viewDocumetsSub(HttpServletRequest request) throws IOException;

	Map<String, Object> createUpdateDrawingMasterDocumentSubImage(MultipartFile[] files, String docId,
			String screenName, String module, List<String> fileNames) throws ApplicationException, IOException;

}
