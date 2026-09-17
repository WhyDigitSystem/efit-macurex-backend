package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ConsumptionEntryVO;

@Repository
public interface ConsumptionEntryRepo extends JpaRepository<ConsumptionEntryVO, Long> {

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and screen_code=?3")
	String getConsumptionEntryDocId(Long orgId, String financialYear, String screenCode);

	@Query(nativeQuery = true, value = "select * from consumption_entry_basic where consumption_entry_basic_id=?1 and active=1 and cancel=0")
	ConsumptionEntryVO getConsumptionEntryById(Long id);

	@Query(nativeQuery = true, value = "select * from consumption_entry_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
	List<ConsumptionEntryVO> getConsumptionEntryByOrgId(Long orgId, Long branch);
	
	@Query(nativeQuery = true, value = "select i.item_id,i.item_code,i.item_description,u.unitmaster_id,u.unit_id from item i left join listofvaluesdetails l1\r\n"
			+ "			                        on l1.listofvaluesdetails_id=i.item_type left join unitmaster u on u.unitmaster_id=i.primary_unit where i.org_id=?1 and \r\n"
			+ "			                        i.branch=?2 and l1.value_description in ('FG','SFG')")
	Set<Object[]> getFgAndSfgItemDetailsConsumptionEntry(Long orgId, Long branch);
	
	
	@Query(nativeQuery = true, value = "select b1.item,i.item_code,i.item_description,b1.qty,b1.scrap_qty,b1.uom,u.unit_id from bill_of_material b\r\n"
			+ " join bill_of_material_details b1 on b.bill_of_material_id=b1.bill_of_material_id \r\n"
			+ " left join item i on i.item_id=b1.item left join unitmaster u on u.unitmaster_id=b1.uom where b.org_id=?1 and b.branch=?2\r\n"
			+ " and b.fg_item=?3  group by b1.item,i.item_code,i.item_description,b1.qty,b1.scrap_qty,b1.uom,u.unit_id")
	Set<Object[]> getRawMaterialConsumptionEntry(Long orgId, Long branch,Long fgItem);
}