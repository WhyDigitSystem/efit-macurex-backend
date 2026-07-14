package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.GrnDTO;
import com.efitops.basesetup.dto.ImageResponseDTO;
import com.efitops.basesetup.dto.SubContractGrnDTO;
import com.efitops.basesetup.dto.ThirdPartyAttachmentDTO;
import com.efitops.basesetup.dto.ThirdPartyInspectionDTO;
import com.efitops.basesetup.entity.GrnVO;
import com.efitops.basesetup.entity.SubContractGrnVO;
import com.efitops.basesetup.entity.ThirdPartyInspectionVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface GrnService {

	List<GrnVO> getGrnByOrgId(Long orgId, String finYear, String branchCode);

	List<GrnVO> getGrnById(Long id);

	Map<String, Object> updateCreateGrn(GrnDTO grndto) throws ApplicationException;

	String getGrnDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getInwardNoForGRN(Long orgId);

	List<Map<String, Object>> getGrnavlstock(Long orgId, String itemCode);

	List<Map<String, Object>> getItemForGRN(Long orgId, String InwardNo);

	List<Map<String, Object>> getSupplierAddressForGRN(Long orgId, String supplierName);

	List<Map<String, Object>> getSGSTandCGSTForGRN(Long orgId, String taxType, String gstType);

	List<Map<String, Object>> getIGSTForGRN(Long orgId, String taxType, String gstType);

	// third party inspection
	List<ThirdPartyInspectionVO> getThirdPartyInspByOrgId(Long orgId, String finYear, String branchCode);

	List<ThirdPartyInspectionVO> getThirdPartyInspById(Long id);

	List<Map<String, Object>> findGRNForThirdPartyInspDetails(Long orgId);

	List<Map<String, Object>> getThirdPartyDetailsForThirdPartyInsp(Long orgId);

	Map<String, Object> updateCreateThirdPartyInsp(ThirdPartyInspectionDTO thirdPartyInspectionDTO)
			throws ApplicationException;

	String getThirdPartyInspectionDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getAvailableStock(Long orgId, String branchCode, String location, String itemCode);

	List<Map<String, Object>> getGrnItemDetails(Long orgId, String grnNo);
//
//	Map<String, Object> uploadFileForThirdPartyInspection(Long thirdPartyId, String itemId, List<MultipartFile> files)
//			throws IOException;

//	Map<String, Object> uploadFileForThirdPartyInspection(Long thirdPartyId, ThirdPartyAttachmentDTO attachmentDTO)
//			throws IOException;

	List<Map<String, Object>> getRemainingBalanceQty(Long orgId, String branchCode, String purchaseOrderNo,
			String itemCode);

	List<Map<String, Object>> getAllShowsAvalibaleqty(Long orgId, String branchCode, String location, String itemCode);

	List<Map<String, Object>> getGrnDetails(Long orgId, String supplierName, String fromDate, String toDate,
			String branchCode);

	List<Map<String, Object>> getGrnSummaryDetails(Long orgId, String supplierName, String fromDate, String toDate,
			String branchCode);

	List<Map<String, Object>> getSupplierName(Long orgId);

	// SubGrn

	List<SubContractGrnVO> getAllSubContractGrnByOrgId(Long orgId, String finYear, String branchCode);

	SubContractGrnVO getSubContractGrnById(Long id);

	Map<String, Object> updateCreateSubContractGrn(SubContractGrnDTO subContractGrnDTO) throws ApplicationException;

	String getSubContractGrnDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getJobWorkOutOrderFromSubContractDetails(Long orgId, String branchCode,
			String jobWorkOutOrderNumber);

	List<Map<String, Object>> getJobWorkOutOrderFromSubContractItemDetails(Long orgId, String branchCode,
			String jobWorkOutOrderNumber);

	List<Map<String, Object>> getJobWorkOutOrderDocId(Long orgId);

	List<Map<String, Object>> getThirdPartyInspectionReport(Long orgId, String fromDate, String toDate,
			String partyName);

	List<Map<String, Object>> getThirdPartyNamesFromPartyMaster(Long orgId);

	List<Map<String, Object>> getSubContractGrnSummaryDetails(Long orgId, String subContractName, String fromDate,
			String toDate, String branchCode);

	List<Map<String, Object>> getSubContractGrnDetails(Long orgId, String subContractName, String fromDate,
			String toDate, String branchCode);

//	byte[] viewThirdPartyImage(Long imageId) throws IOException;
//
//	String getImageFileType(Long id) throws IOException;

//	Map<String, Object> createUpdateThirdPartyImages(MultipartFile[] files, String docId, String screenName,
//			String module) throws ApplicationException, IOException;

	ResponseEntity<byte[]> viewFileThirdPartyImages(HttpServletRequest request) throws IOException, IOException;

	Map<String, Object> createUpdateThirdPartyImages(MultipartFile[] files, String docId, String screenName,
			String module, List<String> itemId) throws ApplicationException, IOException;

	List<ImageResponseDTO> getThirdPartyReportDetailsImages(Long id) throws Exception;

//	String uploadFileForThirdPartyInspection(List<MultipartFile> files, Long thirdPartyId, List<Long> detailsId)
//			throws IOException;

//	Map<String, Object> uploadFileForThirdPartyInspection(Long thirdPartyId, List<MultipartFile> files)
//			throws IOException;

}
