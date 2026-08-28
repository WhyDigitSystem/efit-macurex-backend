package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.OpenStockEntryVO;


public interface OpenStockEntryRepo extends JpaRepository<OpenStockEntryVO, Long>{
	

	List<OpenStockEntryVO> findByOrgIdAndBranch(Long orgId,BranchVO branch);
	
	
	@Query(value = """
	        SELECT
	            i.item_code AS itemCode,
	            i.item_description AS itemDescription,
	            u.unit_id AS unitId,
	            i.item_id AS id
	        FROM item i
	        LEFT JOIN unitmaster u
	            ON i.primary_unit = u.unitmaster_id
	        WHERE i.cancel = 0
	          AND i.org_id = :orgId
	          AND i.branch = :branch
	        ORDER BY i.item_description
	        """, nativeQuery = true)
	List<Object[]> getOpenStockEntryItemCodeDropdown(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);
	
	
	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and screen_code=?3")
	String getOpenStockEntryDocId(Long orgId, String financialYear, String screenCode);
	
	
}