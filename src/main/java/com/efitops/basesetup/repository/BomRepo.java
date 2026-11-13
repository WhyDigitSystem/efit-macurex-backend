package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.BomVO;

@Repository
public interface BomRepo extends JpaRepository<BomVO, Long>{
	
	@Query(nativeQuery = true,value="select * from  bom where orgid=?1 and branchcode=?2 ")
	List<BomVO> getAllBomByOrgId(Long orgId, String branchCode);
	
	@Query(nativeQuery = true,value="select * from bom where bomid=?1")
	List<BomVO> getBomById(Long id);
	
	
	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getBomDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select itemname,itemdesc,primaryunit from item where orgid=?1 and itemtype=?2 and cancel =0 and active = 1")
	Set<Object[]> findFGSFGPartDetails(Long orgId,String productType);

	@Query(nativeQuery = true, value = "select  itemname,itemdesc,primaryunit,itemtype from item where orgid=?1 and itemtype in ('Raw Material','SFG')and cancel =0 and active = 1")
	Set<Object[]> findSFGItemDetails(Long orgId);

//	@Query(value = """
//	        SELECT 
//	            b.bomid,
//	            b.productcode,
//	            b.productname,
//	            b.producttype,
//	            b.qty,
//	            b.uom
//	        FROM bom b
//	        WHERE b.productcode = :productCode
//	          AND b.orgid = :orgId
//	          AND b.branch = :branch
//	          AND b.active = 1
//	          AND b.cancel = 0
//	        ORDER BY b.docdate DESC, b.bomid DESC
//	        LIMIT 1
//	        """, nativeQuery = true)
//	List<Object[]> getLatestBomDropdown(
//	        @Param("productCode") String productCode,
//	        @Param("orgId") Long orgId,
//	        @Param("branch") String branch);

//	@Query(value = """
//	        SELECT
//	            bd.bomdetails,
//	            bd.itemcode,
//	            bd.itemdesc,
//	            bd.itemtype,
//	            bd.qty,
//	            bd.uom,
//	            bd.bomid
//	        FROM bom_details bd
//	        INNER JOIN bom b
//	            ON b.bomid = bd.bomid
//	        WHERE b.productcode = :productCode
//	          AND b.orgid = :orgId
//	          AND b.branch = :branch
//	          AND b.active = 1
//	          AND b.cancel = 0
//	          AND bd.bomid = (
//	              SELECT b1.bomid
//	              FROM bom b1
//	              WHERE b1.productcode = :productCode
//	                AND b1.orgid = :orgId
//	                AND b1.branch = :branch
//	                AND b1.active = 1
//	                AND b1.cancel = 0
//	              ORDER BY b1.docdate DESC, b1.bomid DESC
//	              LIMIT 1
//	          )
//	        """, nativeQuery = true)
//	List<Object[]> getLatestBomDetailsByProductCode(
//	        @Param("productCode") String productCode,
//	        @Param("orgId") Long orgId,
//	        @Param("branch") String branch);
	
	
	
	// BOM Tree Report - Live

	@Query(value = """
	    SELECT
	        a.bomid AS bombasicid,
	        i.item_id AS parentid,
	        i.item_code AS pitemid,
	        i.item_description AS pitemdesc,
	        i.item_group_type AS ptype,
	        r.item_id AS rmid,
	        r.item_code AS itemid,
	        r.manufactured_or_boughtout AS ManB,
	        b.qty AS qty,
	        i.item_id AS first_parent_id,
	        i.item_code AS first_parent_name,
	        i.item_description AS first_parent_item,
	        1 AS levelno,
	        ROW_NUMBER() OVER (ORDER BY a.bomid, b.bomdetails) AS orderno,
	        CONCAT(i.item_code, '>', r.item_code) AS itemtree,
	        r.item_description AS itemdesc,
	        r.item_group_type AS itemtype,
	        u.unit_id AS unitid,
	        i.customer_part_no AS cpartno,
	        CASE
	            WHEN i.item_group_type = 'FG' THEN a.docid
	            ELSE NULL
	        END AS bomid,
	        CASE
	            WHEN i.item_group_type = 'FG' THEN a.docdate
	            ELSE NULL
	        END AS wef
	    FROM bom a
	    INNER JOIN bomdetails b
	        ON a.bomid = b.bomid
	    INNER JOIN item i
	        ON a.productcode = i.item_code
	    INNER JOIN item r
	        ON b.itemcode = r.item_code
	    INNER JOIN unitmaster u
	        ON r.primary_unit = u.unitmaster_id
	    WHERE COALESCE(a.cancel, 0) = 0
	      AND a.branch = :branch
	      AND a.orgid = :orgId
	      AND a.docdate = (
	          SELECT MAX(a2.docdate)
	          FROM bom a2
	          WHERE a2.productcode = a.productcode
	            AND COALESCE(a2.cancel, 0) = 0
	            AND a2.docdate <= CURRENT_DATE()
	            AND a2.branch = :branch
	            AND a2.orgid = :orgId
	      )
	      AND i.item_code = :itemCode
	    ORDER BY a.bomid, b.bomdetails
	    """, nativeQuery = true)
	List<Object[]> getBomTreeReportLive(
	        @Param("itemCode") String itemCode,
	        @Param("branch") String branch,
	        @Param("orgId") Long orgId);
	
	
	@Query(value = """
		    SELECT
		        a.bomid AS bombasicid,
		        i.item_id AS parentid,
		        i.item_code AS pitemid,
		        i.item_description AS pitemdesc,
		        i.item_group_type AS ptype,
		        r.item_id AS rmid,
		        r.item_code AS itemid,
		        r.manufactured_or_boughtout AS ManB,
		        b.qty AS qty,
		        i.item_id AS first_parent_id,
		        i.item_code AS first_parent_name,
		        i.item_description AS first_parent_item,
		        1 AS levelno,
		        ROW_NUMBER() OVER (ORDER BY a.bomid, b.bomdetails) AS orderno,
		        CONCAT(i.item_code, '>', r.item_code) AS itemtree,
		        r.item_description AS itemdesc,
		        r.item_group_type AS itemtype,
		        u.unit_id AS unitid,
		        i.customer_part_no AS cpartno,
		        a.docdate AS wef,
		        a.revision AS revno
		    FROM bom a
		    INNER JOIN bomdetails b
		        ON a.bomid = b.bomid
		    INNER JOIN item i
		        ON a.productcode = i.item_code
		    INNER JOIN item r
		        ON b.itemcode = r.item_code
		    INNER JOIN unitmaster u
		        ON r.primary_unit = u.unitmaster_id
		    WHERE COALESCE(a.cancel, 0) = 0
		      AND a.branch = :branch
		      AND a.orgid = :orgId
		      AND a.docdate = (
		          SELECT MAX(a2.docdate)
		          FROM bom a2
		          WHERE a2.productcode = a.productcode
		            AND COALESCE(a2.cancel, 0) = 0
		            AND a2.docdate >= a.docdate
		            AND a2.branch = :branch
		            AND a2.orgid = :orgId
		      )
		      AND i.item_code = :itemCode
		    ORDER BY a.bomid, b.bomdetails
		    """, nativeQuery = true)
		List<Object[]> getBomTreeAfterCorrectionReport(
		        @Param("itemCode") String itemCode,
		        @Param("branch") String branch,
		        @Param("orgId") Long orgId);
}


