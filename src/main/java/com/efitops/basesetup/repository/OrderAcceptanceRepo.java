package com.efitops.basesetup.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.OrderAcceptanceVO;
import com.efitops.basesetup.entity.QuotationVO;

@Repository
public interface OrderAcceptanceRepo extends JpaRepository<OrderAcceptanceVO, Long> {

	@Query(nativeQuery = true, value = "select * from order_acceptance_basic where order_acceptance_basic_id=?1 and active=1 and cancel=0")
	OrderAcceptanceVO getOrderAcceptanceById(Long id);

	QuotationVO findByDocId(String docId);

	@Query(nativeQuery = true, value = "select * from order_acceptance_basic where org_id=?1  and branch=?2 and active=1 and cancel=0")
	List<OrderAcceptanceVO> getOrderAcceptanceByOrgId(Long orgId, Long branchId);

	@Query(value = "SELECT\r\n" + "		    i.item_id,\r\n" + "		    i.item_code,\r\n"
			+ "		    i.item_description,\r\n" + "		    u.unit_id,\r\n" + "		    i.min_sell_price,\r\n"
			+ "		    h.hsn,\r\n" + "		    gr.rate,\r\n" + "		    gr.cgst,\r\n" + "		    gr.sgst,\r\n"
			+ "		    gr.igst,u.unitmaster_id,gr.gstratemaster_id\r\n" + "		FROM item i\r\n"
			+ "		INNER JOIN unitmaster u\r\n" + "		    ON u.unitmaster_id = i.primary_unit\r\n"
			+ "		INNER JOIN hsn h\r\n" + "		    ON h.hsn_id = i.hsn_code\r\n"
			+ "		LEFT JOIN gstratemaster gr\r\n" + "		    ON gr.hsn_sac_code = h.hsn_id\r\n"
			+ "		    AND gr.active = 1\r\n" + "		    AND gr.cancel = 0\r\n"
			+ "		    AND gr.org_id = i.org_id\r\n" + "		    AND gr.branch = i.branch\r\n"
			+ "		WHERE i.cancel = 0\r\n" + "		  AND i.org_id = ?1\r\n" + "		  AND i.branch =?2 \r\n"
			+ "		ORDER BY i.item_code", nativeQuery = true)
	List<Object[]> getOrderAcceptanceItemDetails(Long orgId, Long branch);

	@Query(value = """
			    SELECT
			        d.item,
			        i.item_code,
			        i.item_description,
			        soa.new_qty,
			        soa.new_delivery_date,
			        soa.new_rate
			    FROM order_acceptance_detail d
			    INNER JOIN item i
			        ON i.item_id = d.item
			    INNER JOIN order_acceptance_basic o
			        ON o.order_acceptance_basic_id = d.order_acceptance_basic_id
			    LEFT JOIN sales_order_amendment_basic soab
			ON soab.salesorder_no = o.doc_id
			       AND soab.org_id = o.org_id
			       AND soab.branch = o.branch
			       AND soab.active = true
			       AND soab.cancel = false
			    LEFT JOIN sales_order_amendment_detail soa
			        ON soa.sales_order_amendment_id = soab.sales_order_amendment_id
			       AND soa.item = d.item
			    WHERE o.doc_id = :docId
			      AND o.org_id = :orgId
			      AND o.branch = :branch
			      AND o.cancel = false
			      AND o.active = true
			    ORDER BY i.item_code
			    """, nativeQuery = true)
	List<Object[]> getOrderAcceptanceItemsWithAmendment(@Param("docId") String docId, @Param("orgId") Long orgId,
			@Param("branch") Long branch);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getOrderAcceptanceDocId(Long orgId, String financialYear, String screenCode);

//	 order acceptance approval report

	@Query(value = """
			SELECT
			    ob.doc_id AS docId,
			    ob.doc_date AS docDate,
			    p.customer_id AS customerId,
			    p.customer_name AS customerName,
			    ob.approved AS approved,
			    ob.order_acceptance_basic_id AS orderAcceptanceBasicId,
			    ob.note AS note
			FROM order_acceptance_basic ob
			INNER JOIN customer_header p
			    ON ob.customer = p.customer_id
			WHERE LOWER(ob.gst_approval) = 'no'
			  AND ob.doc_date BETWEEN :fromDate AND :toDate
			  AND ob.org_id = :orgId
			  AND ob.branch = :branch
			""", nativeQuery = true)
	List<Object[]> getOrderAcceptanceForGstApproval(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("orgId") Long orgId, @Param("branch") Long branch);

//		sales order report

	@Query(value = """
			SELECT
			    ob.order_acceptance_basic_id AS orderAcceptanceBasicId,
			    ob.doc_date AS docDate,
			    ob.doc_id AS docId,
			    ob.so_type AS soType,
			    ob.customer_purchase_order_no AS customerPurchaseOrderNo,
			    ob.customer_purchase_order_date AS customerPurchaseOrderDate,
			    od.customer_part_no AS customerPartNo,
			    od.item AS item,
			    od.quantity AS quantity,
			    od.quantity_rate AS quantityRate,
			    od.amount AS amount,
			    od.last_invoice_date AS lastInvoiceDate,
			    q.doc_id AS quotationDocId,
			    q.doc_date AS quotationDocDate,
			    i.item_code AS itemCode,
			    i.item_description AS itemDescription,
			    i.hsn_code AS hsnCode,
			    c.customer_code AS customerCode,
			    c.customer_name AS customerName
			FROM order_acceptance_basic ob
			INNER JOIN order_acceptance_detail od
			    ON od.order_acceptance_basic_id =
			       ob.order_acceptance_basic_id
			INNER JOIN quotation_header q
			    ON q.doc_id = ob.quotation_no
			INNER JOIN item i
			    ON i.item_id = od.item
			INNER JOIN branch b
			    ON b.branch_id = ob.branch
			INNER JOIN customer_header c
			    ON c.customer_id = ob.customer
			WHERE ob.doc_date BETWEEN :fromDate AND :toDate
			  AND ob.branch = :branch
			  AND ob.org_id = :orgId
			  AND c.customer_name = :customerName
			""", nativeQuery = true)
	List<Object[]> getSalesOrderReport(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("branch") Long branch, @Param("orgId") Long orgId, @Param("customerName") String customerName);
	
//	sales order pending itemwise
	
	@Query(value = """
	        SELECT 
	            b.branch_id,
	            o.order_acceptance_basic_id,
	            o.doc_id,
	            o.doc_date,
	            o.customer_purchase_order_no,
	            o.customer_purchase_order_date,
	            c.customer_id,
	            c.customer_name,
	            i.item_id,
	            i.item_description,
	            d.quantity,
	            SUM(srd.despatch_qty) AS sqty,
	            (d.quantity - SUM(srd.despatch_qty)) AS pqty,
	            d.amount,
	            o.specification
	        FROM order_acceptance_basic o
	        INNER JOIN branch b
	            ON b.branch_id = o.branch
	        INNER JOIN customer_header c
	            ON c.customer_id = o.customer
	        INNER JOIN order_acceptance_detail d
	            ON d.order_acceptance_basic_id =
	               o.order_acceptance_basic_id
	        INNER JOIN item i
	            ON i.item_id = d.item
	        INNER JOIN sales_rejection_invoice_basic sr
	            ON sr.purchase_order = o.customer_purchase_order_no
	        INNER JOIN sales_rejection_invoice_detail srd
	            ON srd.sales_rejection_invoice_basic_id =
	               sr.sales_rejection_invoice_basic_id
	            AND srd.item = d.item
	        WHERE o.cancel = FALSE
	          AND b.branch_id = :plant
	          AND c.belongs_to = :division
	          AND o.doc_date <= :asondt
	        GROUP BY
	            b.branch_id,
	            o.order_acceptance_basic_id,
	            o.doc_id,
	            o.doc_date,
	            o.customer_purchase_order_no,
	            o.customer_purchase_order_date,
	            c.customer_id,
	            c.customer_name,
	            i.item_id,
	            i.item_description,
	            d.quantity,
	            d.amount,
	            o.specification
	        HAVING (d.quantity - SUM(srd.despatch_qty)) > 0

	        UNION

	        SELECT 
	            b.branch_id,
	            o.order_acceptance_basic_id,
	            o.doc_id,
	            o.doc_date,
	            o.customer_purchase_order_no,
	            o.customer_purchase_order_date,
	            c.customer_id,
	            c.customer_name,
	            i.item_id,
	            i.item_description,
	            d.quantity,
	            0 AS sqty,
	            d.quantity AS pqty,
	            d.amount,
	            o.specification
	        FROM branch b
	        INNER JOIN order_acceptance_basic o
	            ON b.branch_id = o.branch
	        INNER JOIN customer_header c
	            ON c.customer_id = o.customer
	        INNER JOIN order_acceptance_detail d
	            ON d.order_acceptance_basic_id =
	               o.order_acceptance_basic_id
	        INNER JOIN item i
	            ON i.item_id = d.item
	        WHERE o.cancel = FALSE
	          AND b.branch_id = :plant
	          AND o.doc_date <= :asondt
	          AND c.belongs_to = :division
	          AND NOT EXISTS (
	              SELECT 1
	              FROM sales_rejection_invoice_basic sr
	              INNER JOIN sales_rejection_invoice_detail srd
	                  ON srd.sales_rejection_invoice_basic_id =
	                     sr.sales_rejection_invoice_basic_id
	              WHERE sr.purchase_order =
	                    o.customer_purchase_order_no
	                AND srd.item = d.item
	                AND sr.cancel = FALSE
	          )
	        GROUP BY
	            b.branch_id,
	            o.order_acceptance_basic_id,
	            o.doc_id,
	            o.doc_date,
	            o.customer_purchase_order_no,
	            o.customer_purchase_order_date,
	            c.customer_id,
	            c.customer_name,
	            i.item_id,
	            i.item_description,
	            d.quantity,
	            d.amount,
	            o.specification
	        HAVING d.quantity > 0

	        ORDER BY doc_id, customer_name
	        """, nativeQuery = true)
	List<Object[]> getSalesOrderPendingItemWiseReport(
	        @Param("plant") Long plant,
	        @Param("division") Long division,
	        @Param("asondt") String asondt);

}
