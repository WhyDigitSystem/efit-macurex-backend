package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.HolidayMasterVO;
import com.efitops.basesetup.entity.TransportMasterVO;

public interface HolidayMasterRepo extends JpaRepository<HolidayMasterVO, Long> {

	@Query(value = """
	        SELECT *
	        FROM holiday
	        WHERE org_id = :orgId
	          AND branch = :branch
	          AND cancel = false and active = 1
	        ORDER BY holiday_id
	        """, nativeQuery = true)
	List<HolidayMasterVO> getHolidayMasterByOrgId(@Param("orgId") Long orgId,
	                                             @Param("branch") Long branch);

	HolidayMasterVO getHolidayMasterById(Long id);

	
}
