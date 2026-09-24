package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.BillOfMaterialVO;

@Repository
public interface BillOfMaterialRepo extends JpaRepository<BillOfMaterialVO, Long> {

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getBillOfMaterialDocId(Long orgId, String financialYear, String screenCode);

	@Query(nativeQuery = true, value = "select * from bill_of_material where bill_of_material_id=?1 and active=1 and cancel=0")
	BillOfMaterialVO getBillOfMaterialById(Long id);

	@Query(nativeQuery = true, value = "select * from bill_of_material where org_id=?1 and branch=?2 and active=1 and cancel=0")
	List<BillOfMaterialVO> getBillOfMaterialByOrgId(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "select i.item_id,i.item_code,i.item_description,concat(i.item_code,' --- ',i.item_description) profit from item i left join listofvaluesdetails l1\r\n"
			+ "                        on l1.listofvaluesdetails_id=i.item_type where i.org_id=?1 and \r\n"
			+ "                        i.branch=?2 and l1.value_description=?3\r\n"
			+ "                        union \r\n"
			+ "                         select i.item_id,i.item_code,i.item_description,concat(i.item_code,' --- ',i.item_description) profit from item i left join listofvaluesdetails l1\r\n"
			+ "                        on l1.listofvaluesdetails_id=i.item_type where i.org_id=?1 and \r\n"
			+ "                        i.branch=?2 and l1.value_description=?3")
	Set<Object[]> getFgAndSfgItemDetails(Long orgId, Long branch,String type);


	@Query(nativeQuery = true, value = "select i.item_id,i.item_code,i.item_description,l1.value_description from item i left join listofvaluesdetails l1\r\n"
			+ "                        on l1.listofvaluesdetails_id=i.item_type where i.org_id=?1 and \r\n"
			+ "                        i.branch=?2 and l1.value_description not in ('FG')")
	Set<Object[]> getGridDetailsFromBom(Long orgId, Long branch);
	
	@Query(nativeQuery = true, value = "select i.item_id,i.item_code,i.item_description,unitmaster_id,u.unit_id from item i left join listofvaluesdetails l1\r\n"
			+ "			                        on l1.listofvaluesdetails_id=i.item_type left join unitmaster u on u.unitmaster_id=i.primary_unit where i.org_id=?1 and \r\n"
			+ "			                       i.branch=?2 and l1.value_description  in ('SCRAP')")
	Set<Object[]> getScrapDetailsItem(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "select b.doc_id,b.doc_date from bill_of_material b join bill_of_material_details b1 on\r\n"
			+ " b.bill_of_material_id=b1.bill_of_material_id and b.type_of_item=b1.item_type where b.org_id=?1 and b.branch=?2")
	Set<Object[]> getSfGDocIdAndDetails(Long orgId, Long branch);
	
	@Query(nativeQuery = true, value = "select  doc_id from  bill_of_material where org_id =?1 and branch = ?2  and fg_itme = ?3\r\n"
			+ "order by  created_on desc limit 1")
	Set<Object[]> getFillDetailsOf(Long orgId, Long branch,Long fgItem);

	@Query(value = """
	        SELECT
	            b.bill_of_material_id AS id,
	            b.doc_id AS docId,
	            b.doc_date AS docDate
	        FROM bill_of_material b
	        WHERE b.fg_item = :itemId
	          AND b.org_id = :orgId
	          AND b.branch = :branch
	          AND b.active = 1
	          AND b.cancel = 0
	        ORDER BY b.wef DESC, b.bill_of_material_id DESC
	        LIMIT 1
	        """, nativeQuery = true)
	List<Object[]> getLatestBomDropdown(
	        @Param("itemId") Long itemId,
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);

	@Query(value = """
	        SELECT
	            bd.bill_of_material_details_id AS bomDetailsId,

	            i.item_id AS itemId,
	            i.item_code AS itemCode,
	            i.item_description AS itemDescription,

	            bd.item_type AS itemType,
	            bd.manbou AS manbou,
	            bd.qty AS qty,

	            u.unitmaster_id AS unitId,
	            u.unit_id AS unitCode,
	            u.description AS unitDescription,

	            bd.bill_of_material_id AS bomId

	        FROM bill_of_material_details bd

	        INNER JOIN bill_of_material b
	            ON b.bill_of_material_id = bd.bill_of_material_id

	        LEFT JOIN item i
	            ON i.item_id = bd.item

	        LEFT JOIN unitmaster u
	            ON u.unitmaster_id = bd.uom

	        WHERE b.doc_id = :docId
	          AND b.org_id = :orgId
	          AND b.branch = :branch
	          AND b.active = 1
	          AND b.cancel = 0

	        ORDER BY bd.bill_of_material_details_id
	        """, nativeQuery = true)
	List<Object[]> getBomDetailsByDocId(
	        @Param("docId") String docId,
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);
	
	@Query(value = """
	        SELECT
	            b.bill_of_material_id AS bomId,

	            i.item_id AS itemId,
	            i.item_code AS itemCode,
	            i.item_description AS itemDescription,

	            u.unitmaster_id AS unitId,
	            u.unit_id AS unitCode,
	            u.description AS unitDescription,

	            bd.qty AS bomQty,
	            bd.scrap_item AS scrapItem,
	            bd.scrap_qty AS scrapQty,
	            b.doc_id

	        FROM bill_of_material b

	        INNER JOIN bill_of_material_details bd
	            ON bd.bill_of_material_id = b.bill_of_material_id

	        LEFT JOIN item i
	            ON i.item_id = bd.item

	        LEFT JOIN unitmaster u
	            ON u.unitmaster_id = bd.uom

	        WHERE b.org_id = :orgId
	          AND b.branch = :branch
	          AND b.fg_item = :itemId
	          AND b.active = 1
	          AND b.cancel = 0  

			AND b.wef = (
			      SELECT MAX(b2.wef)
			      FROM bill_of_material b2
			      WHERE b2.org_id = :orgId
			        AND b2.branch = :branch
			        AND b2.fg_item = :itemId
			        AND b2.active = 1
			        AND b2.cancel = 0
			  )

	        ORDER BY bd.bill_of_material_details_id
	        """, nativeQuery = true)
	Set<Object[]> getBomItemDetailsforSubContractingGRN(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch,
	        @Param("itemId") Long itemId);

	
}