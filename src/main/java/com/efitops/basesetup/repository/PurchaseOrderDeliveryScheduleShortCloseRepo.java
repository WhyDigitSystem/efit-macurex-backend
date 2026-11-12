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

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
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
	
	@Query(nativeQuery = true, value = "select doc_id,doc_date,purchase_order_basic_id from purchase_order_basic where org_id=?1 and branch=?2 and supplier_code=?3\r\n"
			+ " and active=1 and cancel=0  group by doc_id,doc_date,purchase_order_basic_id\r\n"
			+ " union \r\n"
			+ "select doc_id,doc_date,purchase_delivery_schedule_basic_id from purchase_delivery_schedule_basic where org_id=?1 and branch=?2 and supplier=?3\r\n"
			+ " and active=1 and cancel=0 \r\n"
			+ " group by doc_id,doc_date,purchase_delivery_schedule_basic_id")
	Set<Object[]> getPurchaseOrderNobasedSchedule(Long orgId, Long branch, Long supplier);
	
	
	@Query(nativeQuery = true, value = "select p1.item,i.item_code,i.item_description,p1.uom,p1.po_qty orderqty,g1.accept_qty suppliedqty,sum(p1.po_qty-g1.accept_qty) as pendingqty,u.unit_id from purchase_order_basic p join purchase_order_import_details p1 on p.purchase_order_basic_id=p1.purchase_order_basic_id\r\n"
			+ "			left join item i on i.item_id=p1.item left join grn_basic g on g.po_no=p.doc_id and g.supplier_code=p.supplier_code  \r\n"
			+ "			 join grn_details g1 on g1.grn_basic_id=g.grn_basic_id left join unitmaster u on u.unitmaster_id=p1.uom\r\n"
			+ "			where   p.org_id=?1 and p.branch=?2 and p.supplier_code=?3\r\n"
			+ "			and p.doc_id=?4 and p.po_type=0\r\n"
			+ "			 group by p1.item,i.item_code,i.item_description,p1.uom,p1.po_qty,g1.accept_qty,u.unit_id\r\n"
			+ "			 union\r\n"
			+ "			select p1.item,i.item_code,i.item_description,p1.primary_unit,p1.qty_in_primary_unit orderqty,g1.accept_qty suppliedqty,sum(qty_in_primary_unit-g1.accept_qty) as pendingqty,u.unit_id from purchase_order_basic p join purchase_order_local_details p1 on p.purchase_order_basic_id=p1.purchase_order_basic_id\r\n"
			+ "			left join item i on i.item_id=p1.item left join grn_basic g on g.po_no=p.doc_id and g.supplier_code=p.supplier_code  \r\n"
			+ "			 join grn_details g1 on g1.grn_basic_id=g.grn_basic_id left join unitmaster u on u.unitmaster_id=p1.primary_unit\r\n"
			+ "			where   p.org_id=?1 and p.branch=?2 and p.supplier_code=?3\r\n"
			+ "			and p.doc_id=?4 and p.po_type=1\r\n"
			+ "			 group by p1.item,i.item_code,i.item_description,p1.primary_unit,p1.qty_in_primary_unit ,g1.accept_qty,u.unit_id\r\n"
			+ "			 union \r\n"
			+ "			select p1.item,i.item_code,i.item_description,p1.primary_unit,p1.tentative_qty orderqty,g1.accept_qty suppliedqty,sum(tentative_qty-g1.accept_qty) as pendingqty,u.unit_id from \r\n"
			+ "			purchase_delivery_schedule_basic p join purchase_delivery_schedule_details p1 on p.purchase_delivery_schedule_basic_id=p1.purchase_delivery_schedule_basic_id\r\n"
			+ "			left join item i on i.item_id=p1.item left join grn_basic g on g.po_no=p.doc_id and g.supplier_code=p.supplier \r\n"
			+ "			 join grn_details g1 on g1.grn_basic_id=g.grn_basic_id left join unitmaster u on u.unitmaster_id=p1.primary_unit\r\n"
			+ "			where   p.org_id=?1 and p.branch=?2 and p.supplier=?3\r\n"
			+ "			and p.doc_id=?4 \r\n"
			+ "			 group by p1.item,i.item_code,i.item_description,p1.primary_unit,p1.tentative_qty ,g1.accept_qty,p1.primary_unit")
	Set<Object[]> getPurchaseOrderNobasedScheduleDetails(Long orgId, Long branch, Long supplier,String purchaseOrderNo);

}
