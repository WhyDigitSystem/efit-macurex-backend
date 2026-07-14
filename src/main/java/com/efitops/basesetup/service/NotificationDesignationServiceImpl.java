package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.NotificationDesignationDTO;
import com.efitops.basesetup.dto.NotificationDesignationDetailsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.NotificationDesignationDetailsVO;
import com.efitops.basesetup.entity.NotificationDesignationVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.NotificationDesignationDetailsRepo;
import com.efitops.basesetup.repo.NotificationDesignationRepo;

@Service
public class NotificationDesignationServiceImpl implements NotificationDesignationService {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(ToolIssueEntryServiceImpl.class);
	@Autowired
	NotificationDesignationRepo notificationDesignationRepo;
	
	@Autowired
	NotificationDesignationDetailsRepo notificationDesignationDetailsRepo;
	
	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;
	
	@Override
	public Map<String, Object> createUpdateNotificationDesignation(NotificationDesignationDTO notificationDesignationDTO) throws ApplicationException, IOException {
		String message;
		String screenCode = "ND";
		NotificationDesignationVO notificationDesignationVO = new NotificationDesignationVO();

		if (notificationDesignationDTO.getId() != null) {
			// Fetch existing ItemVO for update
			notificationDesignationVO = notificationDesignationRepo.findById(notificationDesignationDTO.getId())
					.orElseThrow(() -> new ApplicationException("NotificationDesignation not found"));
			notificationDesignationVO.setUpdatedBy(notificationDesignationDTO.getCreatedBy());
			createUpdateNotificationDesignationVOByNotificationDesignationDTO(notificationDesignationDTO, notificationDesignationVO);
			message = "NotificationDesignation Updated Successfully";

			List<NotificationDesignationDetailsVO> notificationDesignationDetailsVO = notificationDesignationDetailsRepo
					.findByNotificationDesignationVO(notificationDesignationVO);
			notificationDesignationDetailsRepo.deleteAll(notificationDesignationDetailsVO);
			
			
		} else {

			String docId = notificationDesignationRepo.getNotificationDesignationDocId(notificationDesignationDTO.getOrgId(),
					notificationDesignationDTO.getFinYear(), notificationDesignationDTO.getBranchCode(), screenCode);
			notificationDesignationVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(notificationDesignationDTO.getOrgId(),
							notificationDesignationDTO.getFinYear(), notificationDesignationDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			// Create new ItemVO
			notificationDesignationVO.setCreatedBy(notificationDesignationDTO.getCreatedBy());
			notificationDesignationVO.setUpdatedBy(notificationDesignationDTO.getCreatedBy());
			createUpdateNotificationDesignationVOByNotificationDesignationDTO(notificationDesignationDTO, notificationDesignationVO);
			message = "NotificationDesignation Created Successfully";
		}

		// Save the ItemVO
		notificationDesignationRepo.save(notificationDesignationVO);

		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("notificationDesignationVO", notificationDesignationVO);
		response.put("message", message);
		return response;
	}

	

	private void createUpdateNotificationDesignationVOByNotificationDesignationDTO(
	        NotificationDesignationDTO dto,
	        NotificationDesignationVO vo) {

	    vo.setBranch(dto.getBranch());
	    vo.setBranchCode(dto.getBranchCode());
	    vo.setOrgId(dto.getOrgId());
	    vo.setFinYear(dto.getFinYear());

	    // ✅ Convert List → Comma Separated
	    if (dto.getDesignationCode() != null && dto.getDesignationName() != null) {

	        List<String> codes = dto.getDesignationCode();
	        List<String> names = dto.getDesignationName();

	        // 🔥 Validation (must)
	        if (codes.size() != names.size()) {
	            throw new IllegalArgumentException("DesignationCode and DesignationName size mismatch");
	        }

	        vo.setDesignationcode(String.join(",", codes));
	        vo.setDesignationname(String.join(",", names));
	    }

	    // ✅ Child Mapping (Screen Details)
	    List<NotificationDesignationDetailsVO> detailsList = new ArrayList<>();

	    if (dto.getNotificationDesignationDetailsDTO() != null) {
	        for (NotificationDesignationDetailsDTO d : dto.getNotificationDesignationDetailsDTO()) {

	            NotificationDesignationDetailsVO child = new NotificationDesignationDetailsVO();
	            child.setScreenCode(d.getScreenCode());
	            child.setScreenName(d.getScreenName());
	            child.setCreateMessage(d.getCreateMessage());
	            child.setUpdateMessage(d.getUpdateMessage());
	            child.setEntityName(d.getEntityName());
	            if (d.getUpdateFields() != null && !d.getUpdateFields().isEmpty()) {
	                child.setUpdateFields(String.join(",", d.getUpdateFields()));
	            }
	            if (d.getCreateFields() != null && !d.getCreateFields().isEmpty()) {
	                child.setCreateFields(String.join(",", d.getCreateFields()));
	            }
	            // 🔥 VERY IMPORTANT (relationship)
	            child.setNotificationDesignationVO(vo);

	            detailsList.add(child);
	        }
	    }

	    vo.setNotificationDesignationDetailsVO(detailsList);
	}
	
	@Override
	public List<NotificationDesignationVO> getAllNotificationDesignationByOrgId1(Long orgId, String finYear,
			String branchCode) {

		return notificationDesignationRepo.getAllNotificationDesignationByOrgId1(orgId, finYear, branchCode);
	}

	@Override
	public NotificationDesignationVO getNotificationDesignationById1(Long id) {

		return notificationDesignationRepo.getNotificationDesignationById1(id);
	}

	@Override
	public String getNotificationDesignationDocId1(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "ND";
		String result = notificationDesignationRepo.getNotificationDesignationDocId(orgId, finYear, branchCode,
				ScreenCode);
		return result;
	}

}


