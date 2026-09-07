package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SubContractSupplyScheduleVO;

@Repository
public interface SubContractSupplyScheduleRepo extends JpaRepository<SubContractSupplyScheduleVO, Long> {

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getSubContractSupplyScheduleDocId(Long orgId, String financialYear, String screenCode1);

	@Query(value = """
	        SELECT *
	        FROM subcontract_supply_schedule
	        WHERE org_id = :orgId
	        AND branch = :branch and cancel=0
	        """, nativeQuery = true)
	List<SubContractSupplyScheduleVO> findByOrgIdAndBranch(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);

}
