package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.SalesReturnVO;

public interface SalesReturnRepo extends JpaRepository<SalesReturnVO, Long> {

    @Query(value = """
            SELECT *
            FROM sales_return_basic
            WHERE org_id = :orgId
              AND branch_id = :branchId
              AND cancel = false
              AND active = true
            ORDER BY sales_return_basicid DESC
            """, nativeQuery = true)
    List<SalesReturnVO> findByOrgIdAndBranch(
            @Param("orgId") Long orgId,
            @Param("branchId") Long branchId);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getSalesReturnDocId(Long orgId, String financialYear, String screenCode);

//    boolean existsByDocNoAndOrgIdAndBranch_Id(
//            String docNo,
//            Long orgId,
//            Long branchId);

}