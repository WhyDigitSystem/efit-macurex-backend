package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.EnquiryVO;
import com.efitops.basesetup.entity.PurchaseOrderAmendmentVO;

public interface PurchaseOrderAmendmentRepo extends JpaRepository<PurchaseOrderAmendmentVO, Long> {

	Optional<PurchaseOrderAmendmentVO> findByIdAndOrgId(Long id, Long orgId);

	List<PurchaseOrderAmendmentVO> findByOrgIdAndCancelFalse(Long orgId);
	
	
	@Query(value = """
	        SELECT
	            po.doc_id,
	            po.doc_date,
	            po.belongs_to,
	            po.purchase_order_basic_id AS id
	        FROM purchase_order_basic po
	        INNER JOIN customer_header c
	            ON po.supplier_code = c.customer_id
	        WHERE po.cancel = FALSE
	          AND c.customer_id = :customer
	          AND po.branch = :branch
	          AND po.org_id = :orgId
	        """, nativeQuery = true)
	List<Object[]> getPurchaseOrderAmendmentforCustomer(
	        @Param("customer") Long customer,
	        @Param("branch") Long branch,
	        @Param("orgId") Long orgId);
	
	
	
	@Query(value = """
	        SELECT COALESCE(MAX(revision_no), 0)
	        FROM purchaseorder_amendment_basic
	        WHERE purchase_order_number = :purchaseOrderNumber
	          AND org_id = :orgId
	          AND branch = :branch
	          AND cancel = FALSE
	        """, nativeQuery = true)
	Integer getPurchaseOrderAmendmentRevisionNo(
	        @Param("purchaseOrderNumber") String purchaseOrderNumber,
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);
	
	
	
	@Query(value = """
	        SELECT i.item_id AS id,
	               i.item_code AS itemCode,
	               i.HSN_CODE AS hsnSacCode
	        FROM item i
	        INNER JOIN purchase_order_local_details b
	                ON i.item_id = b.item
	        INNER JOIN
	        N purchase_order_basic a
	                ON a.purchase_order_basic_id = b.purchase_order_basic_id
	        WHERE a.CANCEL = 0
	          AND a.doc_id = :docId
	          AND a.org_id = :orgId
	          AND a.branch = :branch

	        UNION

	        SELECT i.item_id AS id,
	               i.item_code AS itemCode,
	               i.HSN_CODE AS hsnSacCode
	        FROM item i
	        INNER JOIN purchase_order_import_details b
	                ON i.item_id = b.item
	        INNER JOIN purchase_order_basic a
	                ON a.purchase_order_basic_id = b.purchase_order_basic_id
	        WHERE a.CANCEL = 0
	          AND a.doc_id = :docId
	          AND a.org_id = :orgId
	          AND a.branch = :branch
	        """, nativeQuery = true)
	List<Object[]> getPurchaseOrderAmendmentItemCodeDropdown(
	        @Param("docId") String docId,
	        @Param("branch") Long branch,
	        @Param("orgId") Long orgId);
	
	
	@Query(value = """
	        SELECT
	            c.currency_id AS currencyId,
	            c.currency AS currency,
	            der.selling_ex_rate AS exchangeRate,
	            der.buying_ex_rate AS buyingExRate
	        FROM customer_header cust
	        JOIN currency c
	            ON c.currency_id = cust.primary_currency
	        LEFT JOIN dailyexchangerate der
	            ON der.currency = c.currency_id
	            AND der.org_id = c.org_id
	            AND der.branch = :branch
	            AND der.active = 1
	            AND der.cancel = 0
	        WHERE cust.customer_id = :customer
	            AND cust.org_id = :orgId
	            AND c.active = 1
	            AND c.cancel = 0
	        """, nativeQuery = true)
	Set<Object[]> getCurrencyExchangeRateforPurchaseOrderAmendment(
	        @Param("customer") Long customer,
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);
}