package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseOrderDeliveryScheduleShortCloseVO;

@Repository
public interface PurchaseOrderDeliveryScheduleShortCloseRepo
		extends JpaRepository<PurchaseOrderDeliveryScheduleShortCloseVO, Long> {
	
	@Query(nativeQuery = true, value = "select * from proforma_order_delivery_schedule_shortclose_basic where proforma_order_delivery_schedule_shortclose_basic_id=?1 and active=1 and cancel=0")
	PurchaseOrderDeliveryScheduleShortCloseVO getPurchaseOrderDeliveryScheduleShortCloseById(Long id);

	@Query(nativeQuery = true, value = "select * from proforma_order_delivery_schedule_shortclose_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
	List<PurchaseOrderDeliveryScheduleShortCloseVO> getPurchaseOrderDeliveryScheduleShortCloseByOrgId(Long orgId,
			Long branch);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,4,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getPurchaseOrderDeliveryScheduleShortCloseDocId(Long orgId, String financialYear, String screenCode);

	@Query(nativeQuery = true, value = "SELECT    c.customer_id,    c.customer_name,\r\n"
			+ "			    c.customer_code   FROM customer_header c\r\n"
			+ "			LEFT JOIN listofvaluesdetails l1    ON c.customer_category = l1.listofvaluesdetails_id\r\n"
			+ "			LEFT JOIN listofvaluesdetails l2    ON c.customer_category1 = l2.listofvaluesdetails_id\r\n"
			+ "			LEFT JOIN listofvaluesdetails l3    ON c.customer_category2 = l3.listofvaluesdetails_id\r\n"
			+ "			WHERE c.org_id =?1  AND c.branch = ?2  AND c.active = 1\r\n"
			+ "			  AND c.cancel = 0  AND (  l1.value_description = 'Supplier'\r\n"
			+ "			        OR l2.value_description = 'Supplier'     OR l3.value_description = 'Supplier') ORDER BY c.customer_code")
	Set<Object[]> getSupplierDetailsShortClose(Long orgId, Long branch);

}
