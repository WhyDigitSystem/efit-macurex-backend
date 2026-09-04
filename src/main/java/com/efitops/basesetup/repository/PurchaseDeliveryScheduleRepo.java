package com.efitops.basesetup.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseDeliveryScheduleVO;

@Repository
public interface PurchaseDeliveryScheduleRepo extends JpaRepository<PurchaseDeliveryScheduleVO, Long> {

	@Query(nativeQuery = true, value = "select * from purchase_delivery_schedule where purchasedeliveryschedule_id=?1")
	PurchaseDeliveryScheduleVO getPurchaseDeliveryScheduleById(Long id);

	@Query(nativeQuery = true, value = "select * from purchase_delivery_schedule_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
	List<PurchaseDeliveryScheduleVO> getPurchaseDeliveryScheduleByOrgId(Long orgId, Long branchId);

	@Query(value = """
			SELECT
			    u.unitmaster_id,
			    u.unit_id
			FROM unitmaster u
			INNER JOIN item i
			    ON u.unitmaster_id = i.purchase_unit
			WHERE u.cancel = FALSE
			  AND i.item_id = :item
			  AND i.branch = :branch
			  AND i.org_id = :orgId
			ORDER BY u.unit_id
			""", nativeQuery = true)
	List<Object[]> getPurchaseUnitForPurchaseDeliverySchedule(@Param("item") Long itemId, @Param("branch") Long branch,
			@Param("orgId") Long orgId);

//    item dropdown for the purchase delivery schedule
	@Query(value = """
			SELECT
			    CONCAT(i.item_id, '----', i.item_description) AS item,
			    sb.supplier,
			u.unit_id,
   i.item_code,i.item_description,i.item_id
			FROM item i
			JOIN purchase_contract_details sd
			    ON sd.item = i.item_id
			JOIN purchase_contract_basic sb
			    ON sb.purchase_contract_basic_id = sd.purchase_contract_basic_id
			JOIN customer_header p
			    ON p.customer_id = sb.supplier
			JOIN unitmaster u
			    ON u.unitmaster_id = i.primary_unit
			WHERE i.cancel = false
			  AND sb.cancel = false
			  AND sb.doc_id = :purchasecontractnumber
			  AND p.customer_id = :customer
			  AND i.branch = :branch
			  AND i.org_id = :orgId
			""", nativeQuery = true)
	List<Object[]> getItemsForPurchaseDeliverySchedule(@Param("purchasecontractnumber") String purchasecontractnumber,
			@Param("customer") Long customer, @Param("branch") Long branch, @Param("orgId") Long orgId);

	@Query(value = """
			SELECT
			    o.purchase_contract_basic_id AS id,
			    o.doc_id AS pono,
			    o.doc_date,
			    o.supplier
			FROM purchase_contract_basic o
			JOIN customer_header p
			    ON p.customer_id = o.supplier
			WHERE o.cancel = false
			  AND p.customer_id = :customer
			  AND :docdt BETWEEN o.valid_from AND o.valid_to
			  AND o.branch = :branch
			  AND o.org_id = :orgId
			""", nativeQuery = true)
	List<Object[]> getPurchaseOrderNumberForPurchaseDeliverySchedule(@Param("customer") Long customer,
			@Param("docdt") LocalDate docdt, @Param("branch") Long branch, @Param("orgId") Long orgId);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getPurchaseDeliveryScheduleDocId(Long orgId, String financialYear, String screenCode);
	
//	dropdown for purchase order amendment repo
	
	@Query(value = """
	        SELECT
	            pob.purchase_order_basic_id AS id,
	            pob.doc_id AS docId
	        FROM purchase_order_basic pob
	        INNER JOIN customer_header ch
	            ON ch.customer_id = pob.supplier_code
	        WHERE pob.active = 1
	          AND (pob.cancel = 0 OR pob.cancel IS NULL)
	          AND ch.customer_id = :customerId
	          AND pob.branch = :branch
	          AND pob.org_id = :orgId
	        ORDER BY pob.doc_date DESC
	        """, nativeQuery = true)
	List<Object[]> getPurchaseOrderDropdownForPurchaseOrderAmendment(
	        @Param("customerId") Long customerId,
	        @Param("branch") Long branch,
	        @Param("orgId") Long orgId);

}