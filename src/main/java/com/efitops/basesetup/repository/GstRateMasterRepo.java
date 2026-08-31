package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.GSTRateMasterVO;

public interface GstRateMasterRepo extends JpaRepository<GSTRateMasterVO, Long> {



	@Query(nativeQuery = true, value = "select * from gstratemaster where org_id=?1  and cancel=0")
	List<GSTRateMasterVO> getGSTRateByOrgId(Long orgId);

	boolean existsByCategoryIdAndOrgId(Long long1, Long orgId);
	
	

}
