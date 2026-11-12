package com.efitops.basesetup.repository;


import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.efitops.basesetup.entity.ScrapNoteVO;

@Repository
public interface ScrapNoteRepo extends JpaRepository<ScrapNoteVO, Long> {

    @Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and screen_code=?3")
    String getScrapNoteDocId(Long orgId, String financialYear, String screenCode);

    @Query(nativeQuery = true, value = "select * from scrap_note_basic where scrap_note_basic_id=?1 and active=1 and cancel=0")
    ScrapNoteVO getScrapNoteById(Long id);

    @Query(nativeQuery = true, value = "select * from scrap_note_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
    List<ScrapNoteVO> getScrapNoteByOrgId(Long orgId, Long branch);
    
	@Query(nativeQuery = true, value = "select doc_id,doc_date from production_schedule_order_basic where org_id=?1 and branch=?2")
	Set<Object[]> getSchNoFromScrapNote(Long orgId, Long branch);
	
	@Query(nativeQuery = true, value = "select doc_id,doc_date,bill_of_material_id from bill_of_material where org_id=?1 and branch=?2")
	Set<Object[]> getBomNoFromScrapNote(Long orgId, Long branch);
	
	@Query(nativeQuery = true, value = "select i.item_id,i.item_code,i.item_description from item i left join listofvaluesdetails l1\r\n"
			+ "						                        on l1.listofvaluesdetails_id=i.item_type  where i.org_id=?1 and \r\n"
			+ "					                       i.branch=?2 and l1.value_description  in ('SCRAP')")
	Set<Object[]> getScrapPartNo(Long orgId, Long branch);
	
	@Query(nativeQuery = true, value = "select b1.item,i.item_code,i.item_description from bill_of_material b join bill_of_material_details b1 on \r\n"
			+ "b.bill_of_material_id=b1.bill_of_material_id left join item i on i.item_id=b1.item where b.org_id=?1 and\r\n"
			+ "b.branch=?2 and b.bill_of_material_id=?3  group by  b1.item,i.item_code,i.item_description")
	Set<Object[]> getScrapNoteItemDetails(Long orgId, Long branch,Long bom);
}