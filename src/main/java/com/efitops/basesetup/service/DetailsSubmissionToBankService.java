package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.DetailsSubmissionToBankDTO;
import com.efitops.basesetup.entity.DetailsSubmissionToBankDetailsVO;
import com.efitops.basesetup.entity.DetailsSubmissionToBankVO;
import com.efitops.basesetup.exception.ApplicationException;

public interface DetailsSubmissionToBankService {

	List<DetailsSubmissionToBankVO> getAllDetailsSubmissionToBankByOrgId(Long orgId,String finYear, String branchCode);

	List<DetailsSubmissionToBankVO> getDetailsSubmissionToBankById(Long detailsSubmissionToBankId);

	Map<String, Object> createUpdateDetailsSubmissionToBank(DetailsSubmissionToBankDTO detailsSubmissionToBankDTO)
			throws ApplicationException;

	String getDetailsSubmissionToBankDocId(Long orgId, String finYear, String branchCode);

	List<DetailsSubmissionToBankDetailsVO> uploadAttachmentsInBloob(List<MultipartFile> files, List<Long> id)
			throws IOException;

	List<Map<String, Object>> getDetailsSubmissionToBankReport(Long orgId, String fromDate, String toDate);
}