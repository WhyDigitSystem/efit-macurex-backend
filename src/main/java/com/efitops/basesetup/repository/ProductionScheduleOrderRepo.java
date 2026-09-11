package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ProductionScheduleOrderVO;

@Repository
public interface ProductionScheduleOrderRepo extends JpaRepository<ProductionScheduleOrderVO, Long> {

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getProductionScheduleOrderDocId(Long orgId, String financialYear, String screenCode);

	@Query(nativeQuery = true, value = "select * from production_schedule_order_basic where production_schedule_order_basic_id=?1 and active=1 and cancel=0")
	ProductionScheduleOrderVO getProductionScheduleOrderById(Long id);

	@Query(nativeQuery = true, value = "select * from production_schedule_order_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
	List<ProductionScheduleOrderVO> getProductionScheduleOrderByOrgId(Long orgId, Long branch);

	
	@Query(nativeQuery = true, value = "select i.item_id,i.item_code,i.item_description from item i left join listofvaluesdetails l1\r\n"
			+ "    on l1.listofvaluesdetails_id=i.item_type where i.org_id=?1 and \r\n"
			+ "    i.branch=?2 and l1.value_description in ('FG','SFG')")
	Set<Object[]> getFgAndSfgItemDetailsFromProduction(Long orgId, Long branch);
	
	@Query(nativeQuery = true, value = "select b1.item,i.item_code,i.item_description,l.value_description from bill_of_material b \r\n"
			+ "join bill_of_material_details b1 on b.bill_of_material_id=b1.bill_of_material_id left join\r\n"
			+ "item i on i.item_id=b1.item left join listofvaluesdetails l on l.listofvaluesdetails_id=i.item_type where b.org_id=?1 and \r\n"
			+ "b.branch=?2 and b.bill_of_material_id=?3")
	Set<Object[]> getFgAndSfgItemDetailsFromProductionDetails(Long orgId, Long branch,Long bom);



}
