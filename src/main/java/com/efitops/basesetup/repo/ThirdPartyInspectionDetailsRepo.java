package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ThirdPartyInspectionDetailsVO;
import com.efitops.basesetup.entity.ThirdPartyInspectionVO;

@Repository
public interface ThirdPartyInspectionDetailsRepo extends JpaRepository<ThirdPartyInspectionDetailsVO, Long> 
{

	List<ThirdPartyInspectionDetailsVO> findByThirdPartyInspectionVO(ThirdPartyInspectionVO thirdPartyInspectionVO);

}
