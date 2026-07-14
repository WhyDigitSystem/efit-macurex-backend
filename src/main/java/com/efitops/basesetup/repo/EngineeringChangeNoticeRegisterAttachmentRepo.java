package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.EngineeringChangeNoticeRegisterAttachmentVO;
import com.efitops.basesetup.entity.EngineeringChangeNoticeRegisterVO;

@Repository
public interface EngineeringChangeNoticeRegisterAttachmentRepo
		extends JpaRepository<EngineeringChangeNoticeRegisterAttachmentVO, Long> {

	List<EngineeringChangeNoticeRegisterAttachmentVO> findByEngineeringChangeNoticeRegisterVO(
			EngineeringChangeNoticeRegisterVO engineeringChangeNoticeRegisterVO);

}
