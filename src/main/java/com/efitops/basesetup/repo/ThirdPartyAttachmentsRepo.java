package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ThirdPartyAttachmentsVO;
import com.efitops.basesetup.entity.ThirdPartyInspectionVO;

@Repository
public interface ThirdPartyAttachmentsRepo extends JpaRepository<ThirdPartyAttachmentsVO, Long> {

	List<ThirdPartyAttachmentsVO> findByThirdPartyInspectionVO(ThirdPartyInspectionVO thirdPartyInspectionVO);

}
