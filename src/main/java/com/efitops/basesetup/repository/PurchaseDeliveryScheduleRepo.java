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

	@Query(nativeQuery = true, value = "select * from purchase_delivery_schedule where org_id=?1 and branch_id=?2 and active=1 and cancel=0")
	List<PurchaseDeliveryScheduleVO> getPurchaseDeliveryScheduleByOrgId(Long orgId, Long branchId);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,'0')) AS docid "
			+ "from documenttypemapping_details where org_id=?1 and screen_code=?2")
	String getPurchaseDeliveryScheduleDocId(Long orgId, String screenCode);

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
			    i.primary_unit,
			    i.purchase_unit
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
	List<Object[]> getPurchaseOrderNumberForPurchaseDeliverySchedule(
	        @Param("customer") Long customer,
	        @Param("docdt") LocalDate docdt,
	        @Param("branch") Long branch,
	@Param("orgId") Long orgId) ;
}