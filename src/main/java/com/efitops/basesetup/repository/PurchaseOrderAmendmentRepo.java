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
	        SELECT
	            COALESCE(MAX(CAST(revision_no AS UNSIGNED)), 0) + 1
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
	        select
	            i.item_id, i.item_code, pdl.hsn_code, pdl.primary_unit unit,
	            pdl.qty_in_primary_unit qty, pdl.rate_in_inr rate, pdl.delivery_date,

	            (
	                select coalesce(new_rate, 0) new_rate
	                from purchaseorder_amendment_basic pb
	                inner join purchaseorder_amendment_detail pd
	                    on pb.purchaseorder_amendment_basic_id =
	                       pd.purchaseorder_amendment_basic_id
	                where purchase_order_number = po.doc_id
	                and created_on =
	                    (select max(created_on)
	                     from purchaseorder_amendment_basic pb1
	                     where pb1.purchase_order_number = pb.purchase_order_number)
	            ) old_rate,

	            (
	                select coalesce(pd.new_qty, 0) new_qty
	                from purchaseorder_amendment_basic pb
	                inner join purchaseorder_amendment_detail pd
	                    on pb.purchaseorder_amendment_basic_id =
	                       pd.purchaseorder_amendment_basic_id
	                where purchase_order_number = po.doc_id
	                and created_on =
	                    (select max(created_on)
	                     from purchaseorder_amendment_basic pb1
	                     where pb1.purchase_order_number = pb.purchase_order_number)
	            ) old_qty,

	            (
	                select coalesce(pd.olddelivery_date, 0) olddelivery_date
	                from purchaseorder_amendment_basic pb
	                inner join purchaseorder_amendment_detail pd
	                    on pb.purchaseorder_amendment_basic_id =
	                       pd.purchaseorder_amendment_basic_id
	                where purchase_order_number = po.doc_id
	                and created_on =
	                    (select max(created_on)
	                     from purchaseorder_amendment_basic pb1
	                     where pb1.purchase_order_number = pb.purchase_order_number)
	            ) olddelivery_date

	        from purchase_order_basic po
	        inner join purchase_order_local_details pdl
	            on po.purchase_order_basic_id = pdl.purchase_order_basic_id
	        inner join item i
	            on i.item_id = pdl.item
	        where po.doc_id = :purchaseordernumber
	        AND po.org_id = :orgId
	        AND po.branch = :branch

	        union

	        select
	            i.item_id, i.item_code, pdl.hsn_code, pdl.uom unit,
	            pdl.po_qty qty, pdl.order_rate rate, pdl.indent_date delivery_date,

	            (
	                select coalesce(new_rate, 0) new_rate
	                from purchaseorder_amendment_basic pb
	                inner join purchaseorder_amendment_detail pd
	                    on pb.purchaseorder_amendment_basic_id =
	                       pd.purchaseorder_amendment_basic_id
	                where purchase_order_number = po.doc_id
	                and created_on =
	                    (select max(created_on)
	                     from purchaseorder_amendment_basic pb1
	                     where pb1.purchase_order_number = pb.purchase_order_number)
	            ) old_rate,

	            (
	                select coalesce(pd.new_qty, 0) new_qty
	                from purchaseorder_amendment_basic pb
	                inner join purchaseorder_amendment_detail pd
	                    on pb.purchaseorder_amendment_basic_id =
	                       pd.purchaseorder_amendment_basic_id
	                where purchase_order_number = po.doc_id
	                and created_on =
	                    (select max(created_on)
	                     from purchaseorder_amendment_basic pb1
	                     where pb1.purchase_order_number = pb.purchase_order_number)
	            ) old_qty,

	            (
	                select coalesce(pd.olddelivery_date, 0) olddelivery_date
	                from purchaseorder_amendment_basic pb
	                inner join purchaseorder_amendment_detail pd
	                    on pb.purchaseorder_amendment_basic_id =
	                       pd.purchaseorder_amendment_basic_id
	                where purchase_order_number = po.doc_id
	                and created_on =
	                    (select max(created_on)
	                     from purchaseorder_amendment_basic pb1
	                     where pb1.purchase_order_number = pb.purchase_order_number)
	            ) olddelivery_date

	        from purchase_order_basic po
	        inner join purchase_order_import_details pdl
	            on po.purchase_order_basic_id = pdl.purchase_order_basic_id
	        inner join item i
	            on i.item_id = pdl.item
	        where po.doc_id = :purchaseordernumber
	        AND po.org_id = :orgId
	        AND po.branch = :branch
	        """, nativeQuery = true)
	List<Object[]> getPurchaseOrderAmendmentItemCodeDropdown(
	        @Param("purchaseordernumber") String purchaseordernumber,
	        @Param("branch") Long branch,
	        @Param("orgId") Long orgId
	        );
	
	
	
	
	
	@Query(value = """
	        SELECT
	            c.currency_id AS currencyId,
	            c.currency AS currency,
	            der.selling_ex_rate AS exchangeRate,
	            der.buying_ex_rate AS buyingExRate
	        FROM purchase_order_basic pob
	        INNER JOIN customer_header cust
	            ON cust.customer_id = pob.supplier_code
	        INNER JOIN currency c
	            ON c.currency_id = cust.primary_currency
	        INNER JOIN (
	            SELECT
	                d.org_id,
	                d.branch,
	                d.currency,
	                d.selling_ex_rate,
	                d.buying_ex_rate
	            FROM dailyexchangerate d
	            INNER JOIN currency c1
	                ON d.currency = c1.currency_id
	            WHERE d.effective_from = (
	                SELECT MAX(d1.effective_from)
	                FROM dailyexchangerate d1
	                WHERE d1.currency = c1.currency_id
	            )
	        ) der
	            ON der.currency = c.currency_id
	            AND der.branch = pob.branch
	        WHERE pob.doc_id = :purchaseOrderNumber
	          AND pob.org_id = :orgId
	          AND pob.branch = :branch
	          AND pob.cancel = FALSE
	          AND c.active = TRUE
	          AND c.cancel = FALSE
	        """, nativeQuery = true)
	List<Object[]> getCurrencyExchangeRateforPurchaseOrderAmendment(
	        @Param("purchaseOrderNumber") String purchaseOrderNumber,
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);
	
	

	@Query(
		    nativeQuery = true,
		    value = "select concat(prefix, lpad(last_no, 5, 0)) AS docid " +
		            "from documenttypemapping_details " +
		            "where org_id=?1 and fin_year=?2 and screen_code=?3"
		)
		String getPurchaseOrderAmendmentDocId(
		        Long orgId,
		        String financialYear,
		        String screenCode);

}