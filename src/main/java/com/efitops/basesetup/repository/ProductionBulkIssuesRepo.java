package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ProductionBulkIssuesVO;

@Repository
public interface ProductionBulkIssuesRepo extends JpaRepository<ProductionBulkIssuesVO, Long>{

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getProductionBulkIssuesDocId(Long orgId, String financialYear, String screenCode);

	@Query(value = """
	        SELECT *
	        FROM production_bulk_issues
	        WHERE org_id = :orgId
	          AND branch = :branch
	          AND cancel = 0
	        ORDER BY production_bulk_issues_id DESC
	        """, nativeQuery = true)
	List<ProductionBulkIssuesVO> findByOrgIdAndBranch(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);

	
	  @Query(value = """
	            SELECT
	                ib.indent_basic_id,
	                ib.doc_id,
	                ib.doc_date,

	                id.indent_detail_id,
	                id.purpose,
	                id.qtyinprimary_unit,
	                id.qtyinpurchase_unit,
	                id.required_date,
	                id.conversion_factor,

	                im.item_id AS item_id,
	                im.item_code,
	                im.item_description,

	                pu.unitmaster_id AS primary_unit_id,
	                pu.unit_id AS primary_unit_code,
	                pu.description AS primary_unit_description

	            FROM indent_basic ib

	            INNER JOIN indent_detail id
	                ON id.indent_basic_id = ib.indent_basic_id

	            INNER JOIN item im
	                ON im.item_id = id.item

	            LEFT JOIN unitmaster pu
	                ON pu.unitmaster_id = id.primary_unit

	            LEFT JOIN unitmaster pcu
	                ON pcu.unitmaster_id = id.purchase_unit

	            WHERE id.item = :itemId
	              AND ib.org_id = :orgId
	              AND ib.branch = :branch
	              AND ib.active = 1
	              AND ib.cancel = 0
	            """, nativeQuery = true)
	    List<Object[]> getIndentByItemForProductionBulkIssues(
	            @Param("itemId") Long itemId,
	            @Param("orgId") Long orgId,
	            @Param("branch") Long branch);


}
