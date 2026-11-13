package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.AdvForStoresVO;

@Repository
public interface AdvForStoresRepo  extends JpaRepository<AdvForStoresVO, Long>{

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getAdvForStoresDocId(Long orgId, String financialYear, String screenCode);

	@Query(value = """
	        SELECT *
	        FROM adv_for_stores
	        WHERE org_id = :orgId
	          AND branch = :branch and cancel=0
	        ORDER BY adv_for_stores_id DESC
	        """, nativeQuery = true)
	List<AdvForStoresVO> findByOrgIdAndBranch(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);
	
	// Stores Advice Report

	@Query(value = """
	    SELECT
	        d.doc_id AS docno,
	        d.doc_date AS docdt,
	        p.customer_code AS partyid,
	        p.customer_name AS partyname,
	        i.item_code AS itemid,
	        i.item_description AS itemdesc,
	        e.issue_qty AS issqty,
	        d.time AS TIM
	    FROM adv_for_stores d
	    INNER JOIN customer_header p
	        ON p.customer_id = d.customer
	    INNER JOIN item i
	        ON i.item_id = d.incoming_part_no
	    INNER JOIN adv_for_stores_details e
	        ON d.adv_for_stores_id = e.adv_for_stores_id
	    INNER JOIN item i1
	        ON i1.item_id = e.item
	    WHERE d.doc_date BETWEEN :fromDate AND :toDate
	      AND d.branch = :branch
	      AND d.org_id = :orgId
	    ORDER BY d.doc_id
	    """, nativeQuery = true)
	List<Object[]> getStoresAdviceReport(
	        @Param("fromDate") String fromDate,
	        @Param("toDate") String toDate,
	        @Param("branch") Long branch,
	        @Param("orgId") Long orgId
	);
}
