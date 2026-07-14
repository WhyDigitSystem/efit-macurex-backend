package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.NotificationDesignationDTO;
import com.efitops.basesetup.entity.NotificationDesignationVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface NotificationDesignationService  {

	Map<String, Object> createUpdateNotificationDesignation(NotificationDesignationDTO notificationDesignationDTO)
			throws ApplicationException, IOException;

	List<NotificationDesignationVO> getAllNotificationDesignationByOrgId1(Long orgId, String finYear,
			String branchCode);

	NotificationDesignationVO getNotificationDesignationById1(Long id);

	String getNotificationDesignationDocId1(Long orgId, String finYear, String branchCode);


	
	


}
