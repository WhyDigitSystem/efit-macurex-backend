package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.FgTransferSlipVO;

@Repository
public interface FgTransferSlipRepo extends JpaRepository<FgTransferSlipVO, Long> {

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and screen_code=?3")
	String getFgTransferSlipDocId(Long orgId, String financialYear, String screenCode);

	@Query(nativeQuery = true, value = "select * from fg_transfer_slip_basic where fg_transfer_slip_basic_id=?1 and active=1 and cancel=0")
	FgTransferSlipVO getFgTransferSlipById(Long id);

	@Query(nativeQuery = true, value = "select * from fg_transfer_slip_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
	List<FgTransferSlipVO> getFgTransferSlipByOrgId(Long orgId, Long branch);

	
	@Query(nativeQuery = true, value = "select bill_of_material_id,doc_id,doc_date from bill_of_material  where org_id=?1 and branch=?2 and\r\n"
			+ " type_of_item='FG' and fg_item=?3")
	Set<Object[]> getBomFromFgTransferSlip(Long orgId, Long branch,Long fgItem);
	
	@Query(nativeQuery = true, value = "select doc_id,doc_date,total_qty from production_schedule_order_basic  where org_id=?1 and\r\n"
			+ " branch=?2 and active=1 and cancel=0")
	Set<Object[]> getSchNoFromFgTransferSlip(Long orgId, Long branch);
	
	@Query(nativeQuery = true, value = "SELECT     c.customer_id,     c.customer_name,\r\n"
			+ "						    c.customer_code FROM customer_header c\r\n"
			+ "						LEFT JOIN listofvaluesdetails l1     ON c.customer_category = l1.listofvaluesdetails_id\r\n"
			+ "						LEFT JOIN listofvaluesdetails l2     ON c.customer_category1 = l2.listofvaluesdetails_id\r\n"
			+ "						LEFT JOIN listofvaluesdetails l3     ON c.customer_category2 = l3.listofvaluesdetails_id\r\n"
			+ "						left JOIN gststatemaster g     ON g.gststatemaster_id = c.gst_state\r\n"
			+ "						WHERE c.org_id =?1  AND c.branch =?2   AND c.active = 1\r\n"
			+ "						  AND c.cancel = 0   AND (         l1.value_description = 'Customers'\r\n"
			+ "						        OR l2.value_description = 'Customers'         OR l3.value_description = 'Customers'\r\n"
			+ "						      ) ORDER BY c.customer_code")
	Set<Object[]> getCustomersDetailsFromTransferSlip(Long orgId, Long branch);
	
	@Query(nativeQuery = true, value = "select b1.item,i.item_code,i.item_description,b1.qty,u.unitmaster_id,u.unit_id from bill_of_material b join bill_of_material_details b1 on b.bill_of_material_id=b1.bill_of_material_id \r\n"
			+ "left join item i on i.item_id=b1.item left join unitmaster u on u.unitmaster_id=i.primary_unit\r\n"
			+ " where b.org_id=?1 and b.branch=?2 and b.bill_of_material_id=?3")
	Set<Object[]> getBomDetailsFromFgTransferSlip(Long orgId, Long branch,Long bom);
}
