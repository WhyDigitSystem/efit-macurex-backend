package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DailyExchangeRateVO;
import com.efitops.basesetup.entity.TransportMasterVO;

@Repository
public interface DailyExchangeRateRepo extends JpaRepository<DailyExchangeRateVO, Long>{

	@Query(value = """
	        SELECT *
	        FROM dailyexchangerate
	        WHERE org_id = :orgId
	          AND branch = :branch
	          AND cancel = false and active = 1
	        ORDER BY dailyexchangerate_id
	        """, nativeQuery = true)
	List<DailyExchangeRateVO> findByOrgIdAndBranch(@Param("orgId") Long orgId,
	                                             @Param("branch") Long branch);
}
