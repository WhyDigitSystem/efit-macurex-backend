package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.DetailsSubmissionToBankDTO;
import com.efitops.basesetup.dto.DetailsSubmissionToBankDetailsDTO;
import com.efitops.basesetup.entity.DetailsSubmissionToBankDetailsVO;
import com.efitops.basesetup.entity.DetailsSubmissionToBankVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DetailsSubmissionToBankDetailsRepo;
import com.efitops.basesetup.repo.DetailsSubmissionToBankRepo;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;

@Service
public class DetailsSubmissionToBankServiceImpl implements DetailsSubmissionToBankService {

	public static final Logger LOGGER = LoggerFactory.getLogger(DetailsSubmissionToBankServiceImpl.class);

	@Autowired
	DetailsSubmissionToBankRepo detailsSubmissionToBankRepo;

	@Autowired
	DetailsSubmissionToBankDetailsRepo detailsSubmissionToBankDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	private CommonNotificationService commonNotificationService;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<DetailsSubmissionToBankVO> getAllDetailsSubmissionToBankByOrgId(Long orgId, String finYear,
			String branchCode) {
		List<DetailsSubmissionToBankVO> bankVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  Bank Details BY OrgId : {}", orgId);
			bankVO = detailsSubmissionToBankRepo.getAllDetailsSubmissionToBankByOrgId(orgId, finYear, branchCode);
		}
		return bankVO;
	}

	@Override
	public List<DetailsSubmissionToBankVO> getDetailsSubmissionToBankById(Long id) {
		List<DetailsSubmissionToBankVO> bankVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received Bank Details BY Id : {}", id);
			bankVO = detailsSubmissionToBankRepo.getAllDetailsSubmissionToBankById(id);
		}
		return bankVO;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateDetailsSubmissionToBank(DetailsSubmissionToBankDTO bankDTO)
			throws ApplicationException {

		String screenCode = "DSTB";
		DetailsSubmissionToBankVO oldDetailsSubmissionToBank = null;

		DetailsSubmissionToBankVO bankVO;
		String message;

		/* ================= UPDATE ================= */
		if (ObjectUtils.isNotEmpty(bankDTO.getId())) {

			oldDetailsSubmissionToBank = detailsSubmissionToBankRepo.findById(bankDTO.getId())
					.orElseThrow(() -> new ApplicationException("detailsSubmissionToBank  not found"));

			oldDetailsSubmissionToBank.getDetailsSubmissionToBankDetailsVO().size();// load
			entityManager.detach(oldDetailsSubmissionToBank); // detach snapshot

			bankVO = detailsSubmissionToBankRepo.findById(bankDTO.getId())
					.orElseThrow(() -> new ApplicationException("Bank submission details not found"));

			bankVO.setModifiedBy(bankDTO.getCreatedBy());
			mapBankDTOToVO(bankDTO, bankVO);

			message = "Bank Details Updated Successfully";
		}
		/* ================= CREATE ================= */
		else {

			bankVO = new DetailsSubmissionToBankVO();

			String docId = detailsSubmissionToBankRepo.getBankDetailsDocId(bankDTO.getOrgId(), bankDTO.getFinYear(),
					bankDTO.getBranchCode(), screenCode);

			if (docId == null) {
				throw new ApplicationException("Failed to generate Bank Submission Doc ID");
			}

			bankVO.setDocId(docId);

			DocumentTypeMappingDetailsVO docMap = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(bankDTO.getOrgId(), bankDTO.getFinYear(),
							bankDTO.getBranchCode(), screenCode);

			if (docMap == null) {
				throw new ApplicationException("Document type mapping not found for DSB");
			}

			docMap.setLastno(docMap.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(docMap);

			bankVO.setCreatedBy(bankDTO.getCreatedBy());
			bankVO.setModifiedBy(bankDTO.getCreatedBy());

			mapBankDTOToVO(bankDTO, bankVO);

			message = "Bank Details Submitted Successfully";
		}

		detailsSubmissionToBankRepo.save(bankVO);
		commonNotificationService.generateNotification(bankVO.getScreenCode(), bankVO.getId(),
				oldDetailsSubmissionToBank, bankVO);

		Map<String, Object> response = new HashMap<>();
		response.put("detailsSubmissionToBankVO", bankVO);
		response.put("message", message);

		return response;
	}

	private void mapBankDTOToVO(DetailsSubmissionToBankDTO bankDTO, DetailsSubmissionToBankVO bankVO)
			throws ApplicationException {

		bankVO.setOrgId(bankDTO.getOrgId());
		bankVO.setBranch(bankDTO.getBranch());
		bankVO.setBranchCode(bankDTO.getBranchCode());
		bankVO.setFinYear(bankDTO.getFinYear());
		bankVO.setDocDate(bankDTO.getDocDate());
		bankVO.setInvoiceNo(bankDTO.getInvoiceNo());
		bankVO.setInvoiceDate(bankDTO.getInvoiceDate());
		bankVO.setNarration(bankDTO.getNarration());

		/* DELETE OLD DETAILS (UPDATE CASE) */
		if (ObjectUtils.isNotEmpty(bankVO.getId())) {
			List<DetailsSubmissionToBankDetailsVO> oldList = detailsSubmissionToBankDetailsRepo
					.findByDetailsSubmissionToBankVO(bankVO);
			detailsSubmissionToBankDetailsRepo.deleteAll(oldList);
		}

		if (CollectionUtils.isEmpty(bankDTO.getDetailsSubmissionToBankDetailsDTO())) {
			throw new ApplicationException("Bank document details cannot be empty");
		}

		List<DetailsSubmissionToBankDetailsVO> detailsList = new ArrayList<>();

		for (DetailsSubmissionToBankDetailsDTO d : bankDTO.getDetailsSubmissionToBankDetailsDTO()) {

			DetailsSubmissionToBankDetailsVO vo = new DetailsSubmissionToBankDetailsVO();

			vo.setDocumentName(d.getDocumentName());
			vo.setStatus(d.getStatus());
//	        vo.setAttachements(d.getAttachements());
			vo.setDetailsSubmissionToBankVO(bankVO);

			detailsList.add(vo);
		}

		bankVO.setDetailsSubmissionToBankDetailsVO(detailsList);
	}

	@Override
	public String getDetailsSubmissionToBankDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "DSTB";
		return detailsSubmissionToBankRepo.getBankDetailsDocId(orgId, finYear, branchCode, screenCode);
	}

	@Override
	public List<DetailsSubmissionToBankDetailsVO> uploadAttachmentsInBloob(List<MultipartFile> files, List<Long> ids)
			throws IOException {
		List<DetailsSubmissionToBankDetailsVO> updatedMasterList = new ArrayList<>();

		for (int i = 0; i < ids.size(); i++) {
			Long id = ids.get(i);
			MultipartFile file = files.get(i);

			// Find the entity by its ID
			DetailsSubmissionToBankDetailsVO masterDetailsVO = detailsSubmissionToBankDetailsRepo.findById(id)
					.orElseThrow(() -> new RuntimeException("DrawingMaster1VO not found with ID: " + id));

			// Process the file and set it as the attachment for this entity
			masterDetailsVO.setAttachements(file.getBytes());

			// Save the updated entity to the database
			DetailsSubmissionToBankDetailsVO updatedMaster = detailsSubmissionToBankDetailsRepo.save(masterDetailsVO);

			// Add the updated entity to the list
			updatedMasterList.add(updatedMaster);
		}

		// Return the list of updated entities
		return updatedMasterList;
	}

	// Report Service Method

	@Override
	public List<Map<String, Object>> getDetailsSubmissionToBankReport(Long orgId, String fromDate, String toDate) {

		Set<Object[]> reportData = detailsSubmissionToBankRepo.getDetailsSubmissionToBankReport(orgId, fromDate,
				toDate);

		return mapDetailsSubmissionToBankReport(reportData);
	}

	private List<Map<String, Object>> mapDetailsSubmissionToBankReport(Set<Object[]> reportData) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : reportData) {

			Map<String, Object> map = new HashMap<>();

			map.put("id", ch[0]);
			map.put("docId", ch[1]);
			map.put("docDate", ch[2]);
			map.put("invoiceNo", ch[3]);
			map.put("invoiceDate", ch[4]);
			map.put("branch", ch[5]);
			map.put("branchCode", ch[6]);
			map.put("finYear", ch[7]);
			map.put("narration", ch[8]);

			map.put("documentName", ch[9]);
			map.put("status", ch[10]);

			list.add(map);
		}

		return list;
	}

}