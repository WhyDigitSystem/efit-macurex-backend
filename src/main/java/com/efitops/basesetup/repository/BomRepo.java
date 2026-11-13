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
	
}


