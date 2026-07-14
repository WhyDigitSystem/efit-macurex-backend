package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.NotificationDesignationDetailsVO;
import com.efitops.basesetup.entity.NotificationDesignationVO;

@Repository

public interface NotificationDesignationDetailsRepo extends JpaRepository<NotificationDesignationDetailsVO, Long> {



	NotificationDesignationDetailsVO findByScreenCode(String screenCode);

	List<NotificationDesignationDetailsVO> findByNotificationDesignationVO(
			NotificationDesignationVO notificationDesignationVO);

}
