package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ProductionTransferSlipVO;

@Repository
public interface ProductionTransferSlipRepo extends JpaRepository<ProductionTransferSlipVO, Long> {

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and screen_code=?3")
	String getProductionTransferSlipDocId(Long orgId, String financialYear, String screenCode);

	@Query(nativeQuery = true, value = "select * from production_transfer_slip_basic where production_transfer_slip_basicid=?1 and active=1 and cancel=0")
	ProductionTransferSlipVO getProductionTransferSlipById(Long id);

	@Query(nativeQuery = true, value = "select * from production_transfer_slip_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
	List<ProductionTransferSlipVO> getProductionTransferSlipByOrgId(Long orgId, Long branch);
	
	@Query(nativeQuery = true, value = "select i.item_id,i.item_code,i.item_description from item i left join listofvaluesdetails l1\r\n"
			+ "					                        on l1.listofvaluesdetails_id=i.item_type  where i.org_id=?1 and \r\n"
			+ "					                       i.branch=?2 and l1.value_description  in ('FG')")
	Set<Object[]> getFgPartNoDetails(Long orgId, Long branch);
	
	@Query(nativeQuery = true, value = "select i.item_id,i.item_code,i.item_description from item i left join listofvaluesdetails l1\r\n"
			+ "					                        on l1.listofvaluesdetails_id=i.item_type  where i.org_id=?1 and \r\n"
			+ "					                       i.branch=?2 and l1.value_description  in ('SFG')")
	Set<Object[]> getSfgPartNoDetails(Long orgId, Long branch);
	
	@Query(nativeQuery = true, value = "select doc_id,doc_date from production_schedule_order_basic where org_id=?1 and branch=?2 and fg_item=?3 group by doc_id,doc_date\r\n"
			+ "union \r\n"
			+ "select doc_id,doc_date from production_schedule_order_basic where org_id=?1 and branch=?2 and fg_item=?4 \r\n"
			+ "group by doc_id,doc_date")
	Set<Object[]> getSchNoFromTransferSlip(Long orgId, Long branch,Long fgItem,Long sfgItem);
	
	@Query(nativeQuery = true, value = "select doc_id,doc_date,bill_of_material_id from bill_of_material where org_id=?1 and branch=?2 and fg_item=?3 group by doc_id,doc_date,bill_of_material_id\r\n"
			+ "union \r\n"
			+ "select doc_id,doc_date,bill_of_material_id from bill_of_material where org_id=?1 and branch=?2 and fg_item=?3 \r\n"
			+ "group by doc_id,doc_date,bill_of_material_id")
	Set<Object[]> getBomNoFromTransferSlip(Long orgId, Long branch,Long fgItem,Long sfgItem);
	
	@Query(nativeQuery = true, value = "select b1.item,i.item_code,i.item_description,l.value_description,b1.qty from bill_of_material b join bill_of_material_details b1 on b.bill_of_material_id=b1.bill_of_material_id\r\n"
			+ " left join item i on i.item_id=b1.item left join listofvaluesdetails l on l.listofvaluesdetails_id=i.item_type where \r\n"
			+ " b.org_id=?1 and b.branch=?2 and b.bill_of_material_id=?3 \r\n"
			+ " group by b1.item,i.item_code,i.item_description,l.value_description,b1.qty")
	Set<Object[]> getBomNoFromTransferSlipDetails(Long orgId, Long branch,Long bom);

}