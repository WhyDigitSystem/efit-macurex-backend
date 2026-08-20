package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseOrderVO;

@Repository
public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrderVO, Long> {

	@Query(nativeQuery = true, value = "select * from purchase_order_basic where purchase_order_basic_id=?1 and active=1 and cancel=0 and po_type=?2")
	PurchaseOrderVO getPurchaseOrderById(Long id, Integer type);

	@Query(nativeQuery = true, value = "select * from purchase_order_basic where org_id=?1  and branch=?2 and active=1 and cancel=0")
	List<PurchaseOrderVO> getPurchaseOrderByOrgId(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "select multiplication_factor from uomconversion where org_id=?1 and from_unit=?2 and to_unit=?3")
	Set<Object[]> getMutipleFactorAmount(Long orgId, Long primaryUnit, Long purchaseUnit);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getPurchaseOrderImportDocId(Long orgId, String financialYear, String screenCode);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getPurchaseOrderLocalDocId(Long orgId, String financialYear, String screenCode);

	@Query(nativeQuery = true, value = "SELECT i.item_id,\r\n" + "    		                i.item_code,\r\n"
			+ "    		                i.item_description,\r\n" + "    		                u.unit_id,\r\n"
			+ "    		                h.hsn,\r\n" + "                            i.customer_part_no,\r\n"
			+ "    		                i.primary_unit,\r\n" + "                            i.purchase_unit\r\n"
			+ "    		            FROM item i\r\n" + "    		            INNER JOIN unitmaster u\r\n"
			+ "    		                ON u.unitmaster_id = i.primary_unit  and u.unitmaster_id=i.purchase_unit\r\n"
			+ "    		            INNER JOIN hsn h\r\n" + "    		                ON h.hsn_id = i.hsn_code\r\n"
			+ "    		            WHERE i.cancel = 0	\r\n" + "    		              AND i.org_id = ?1\r\n"
			+ "    		              AND i.branch = ?2 group by  i.item_id,\r\n"
			+ "    		                i.item_code,\r\n" + "    		                i.item_description,\r\n"
			+ "    		                u.unit_id,\r\n" + "    		                h.hsn,\r\n"
			+ "    		                i.customer_part_no order by i.item_id")
	Set<Object[]> getItemDetailsResponsePurchaseLocal(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "SELECT i.item_id,\r\n" + "    		                i.item_code,\r\n"
			+ "    		                i.item_description,\r\n" + "    		                u.unit_id,\r\n"
			+ "    		                h.hsn,\r\n" + "                            i.customer_part_no,\r\n"
			+ "                            u.unitmaster_id\r\n" + "\r\n" + "    		            FROM item i\r\n"
			+ "    		            INNER JOIN unitmaster u\r\n"
			+ "    		                ON u.unitmaster_id = i.primary_unit \r\n"
			+ "    		            INNER JOIN hsn h\r\n" + "    		                ON h.hsn_id = i.hsn_code\r\n"
			+ "    		            WHERE i.cancel = 0	\r\n" + "    		              AND i.org_id = ?1\r\n"
			+ "    		              AND i.branch = ?2 group by  i.item_id,\r\n"
			+ "    		                i.item_code,\r\n" + "    		                i.item_description,\r\n"
			+ "    		                u.unit_id,\r\n" + "    		                h.hsn,\r\n"
			+ "    		                i.customer_part_no order by i.item_id")
	Set<Object[]> getItemDetailsResponsePurchaseImport(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "SELECT\r\n"
			+ "    c.customer_id,\r\n"
			+ "    c.customer_name,\r\n"
			+ "    c.customer_code,\r\n"
			+ "    c.address,\r\n"
			+ "    c.pincode,\r\n"
			+ "    c.gst_no,\r\n"
			+ "    g.state_name,\r\n"
			+ "    c.is_registered\r\n"
			+ "FROM customer_header c\r\n"
			+ "LEFT JOIN listofvaluesdetails l1\r\n"
			+ "    ON c.customer_category = l1.listofvaluesdetails_id\r\n"
			+ "LEFT JOIN listofvaluesdetails l2\r\n"
			+ "    ON c.customer_category1 = l2.listofvaluesdetails_id\r\n"
			+ "LEFT JOIN listofvaluesdetails l3\r\n"
			+ "    ON c.customer_category2 = l3.listofvaluesdetails_id\r\n"
			+ "left JOIN gststatemaster g\r\n"
			+ "    ON g.gststatemaster_id = c.gst_state\r\n"
			+ "WHERE c.org_id = ?1\r\n"
			+ "  AND c.branch = ?2\r\n"
			+ "  AND c.active = 1\r\n"
			+ "  AND c.cancel = 0\r\n"
			+ "  AND (\r\n"
			+ "        l1.value_description = 'Supplier'\r\n"
			+ "        OR l2.value_description = 'Supplier'\r\n"
			+ "        OR l3.value_description = 'Supplier'\r\n"
			+ "      )\r\n"
			+ "ORDER BY c.customer_code")
	Set<Object[]> getSupplierDetails(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "select d.selling_ex_rate from currency c join dailyexchangerate d on c.currency_id=d.currency  where d.org_id=?1\r\n"
			+ " and d.branch=?2 and d.active=1 and d.cancel=0 and d.currency=?3")
	Set<Object[]> getExchangeRateDetails(Long orgId, Long branch, Long currency);

}
