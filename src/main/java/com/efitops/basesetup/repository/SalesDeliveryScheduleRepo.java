package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.SalesDeliveryScheduleVO;

public interface SalesDeliveryScheduleRepo extends JpaRepository<SalesDeliveryScheduleVO, Long> {

	@Query(value = """
			SELECT *
			FROM sdvbasic
			WHERE org_id = :orgId
			  AND branch = :branch
			  AND cancel = 0
			  AND active = 1
			ORDER BY sdvbasic_id
			""", nativeQuery = true)
	List<SalesDeliveryScheduleVO> findByOrgIdAndBranch(@Param("orgId") Long orgId, @Param("branch") Long branch);

//    boolean existsByDlvNoAndOrgIdAndBranch_Id(
//            String dlvNo,
//            Long orgId,
//            Long branchId);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getSalesDeliveryScheduleDocId(Long orgId, String financialYear, String screenCode);

	@Query(value = """
	        SELECT month_year
	        FROM sdvbasic
	        WHERE doc_id = ?1
	          AND branch = ?2
	          AND org_id = ?3
	          AND active = 1
	          AND cancel = 0
	        """, nativeQuery = true)
	Set<Object[]> getMonthYearForSalesRejectionInv(
	        String docId,
	        Long branch,
	        Long orgId);

}