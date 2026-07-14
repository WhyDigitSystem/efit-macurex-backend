package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.NotificationDesignationVO;

@Repository
public interface NotificationDesignationRepo extends JpaRepository<NotificationDesignationVO, Long> {
	
	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getNotificationDesignationDocId(Long orgId, String finYear, String branchCode, String screenCode);
	
	@Query(nativeQuery = true, value = "select * from  notificationdesignation  where orgid=?1 and finyear=?2 and branchcode=?3")
	List<NotificationDesignationVO> getAllNotificationDesignationByOrgId1(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from notificationdesignation where notificationdesignationid=?1")
	NotificationDesignationVO getNotificationDesignationById1(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getNotificationDesignationDocId1(Long orgId, String finYear, String branchCode, String screenCode);

//	String getnotificationDesignationDocId(Long orgId, String finYear, String branchCode, String screenCode);
	

}
