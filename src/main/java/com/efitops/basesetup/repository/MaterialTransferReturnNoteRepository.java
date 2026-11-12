package com.efitops.basesetup.repository;


import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.MaterialTransferReturnNoteVO;

@Repository
public interface MaterialTransferReturnNoteRepository extends JpaRepository<MaterialTransferReturnNoteVO, Long> {

    @Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and screen_code=?3")
    String getMaterialTransferReturnNoteDocId(Long orgId, String financialYear, String screenCode);

    @Query(nativeQuery = true, value = "select * from material_transfer_return_note_basic where material_transfer_return_note_basic_id=?1 and active=1 and cancel=0")
    MaterialTransferReturnNoteVO getMaterialTransferReturnNoteById(Long id);

    @Query(nativeQuery = true, value = "select * from material_transfer_return_note_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
    List<MaterialTransferReturnNoteVO> getMaterialTransferReturnNoteByOrgId(Long orgId, Long branch);
    
	@Query(nativeQuery = true, value = "select i.item_id,i.item_code,i.item_description from item i left join listofvaluesdetails l1\r\n"
			+ "					                        on l1.listofvaluesdetails_id=i.item_type  where i.org_id=?1 and \r\n"
			+ "					                        i.branch=?2 and l1.value_description in ('FG','SFG')")
	Set<Object[]> getFgAndSfgFromMaterialTransferReturnNote(Long orgId, Long branch);
	
	@Query(nativeQuery = true, value = " select doc_id,doc_date from production_schedule_order_basic  where org_id=?1 and\r\n"
			+ "			 branch=?2 and active=1 and cancel=0")
	Set<Object[]> getSchNoFromMaterialTransferReturnNote(Long orgId, Long branch);
	
	
	@Query(nativeQuery = true, value = "select p1.item,i.item_code,i.item_description,u.unitmaster_id,u.unit_id,p1.qty_required from production_schedule_order_basic p join\r\n"
			+ "             production_schedule_order_details p1 on\r\n"
			+ "             p.production_schedule_order_basic_id=p1.production_schedule_order_basic_id\r\n"
			+ "           left join item i on i.item_id=p1.item left join unitmaster u on u.unitmaster_id=i.primary_unit  where p.org_id=?1 and p.branch=?2 and p.doc_id=?3")
	Set<Object[]> etSchNoItemDetailsFromMaterialTransferReturnNote(Long orgId, Long branch,String schNo);

}
