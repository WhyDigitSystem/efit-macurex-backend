package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesRejectionInvoiceVO;

@Repository
public interface SalesRejectionInvoiceRepo extends JpaRepository<SalesRejectionInvoiceVO, Long> {

	@Query(value = "SELECT * " + "FROM sales_rejection_invoice_basic " + "WHERE org_id = :orgId "
			+ "AND branch = :branch " + "AND active = 1 " + "AND cancel = 0 "
			+ "ORDER BY sales_rejection_invoice_basic_id DESC", nativeQuery = true)
	List<SalesRejectionInvoiceVO> getSalesRejectionInvoiceByOrgId(@Param("orgId") Long orgId,
			@Param("branch") Long branch);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getSalesRejectionInvoiceDocId(Long orgId, String financialYear, String screenCode);
	
	
	@Query(value = """
	        SELECT
	            c.customer_code AS partyid,
	            c.customer_name AS partyname,
	            SUM(COALESCE(d.amount_in_rs, 0)) AS basic,
	            SUM(COALESCE(d.igst_amount, 0)) AS IGST,
	            SUM(COALESCE(d.sgst_amount, 0)) AS SGST,
	            SUM(COALESCE(d.cgst_amount, 0)) AS CGST
	        FROM sales_rejection_invoice_basic h
	        INNER JOIN sales_rejection_invoice_detail d
	            ON d.sales_rejection_invoice_basic_id =
	               h.sales_rejection_invoice_basic_id
	        INNER JOIN customer_header c
	            ON c.customer_id = h.customer
	        LEFT JOIN item i
	            ON i.item_id = d.item
	        LEFT JOIN unitmaster u
	            ON u.unitmaster_id = d.unit
	        WHERE COALESCE(h.cancel, 0) = 0
	          AND h.doc_date BETWEEN STR_TO_DATE(?1, '%Y-%m-%d')
	          AND h.belongs_to = ?3
	        GROUP BY
	            c.customer_code,
	            c.customer_name
	        ORDER BY
	            c.customer_name,
	            c.customer_code
	        """, nativeQuery = true)
	List<Object[]> getRegularSaleCustomerWiseCumlReport(
	        String fromdate,
	        String todate,
	        Long orgId);

	@Query(value = """
			SELECT
			    sr.doc_id,
			    sr.doc_date,
			    sd.despatch_qty,
			    c.customer_id,
			    c.customer_code,
			    c.customer_name,
			    i.item_code,
			    i.item_description,
			    o.doc_id AS order_acceptance_no,
			    u.unitmaster_id,
			    sr.purchase_order,
			    sr.purchase_order_date,
			    c.city,
			    sr.net_amount,
			    sr.total_ass_val
			FROM sales_rejection_invoice_basic sr

			INNER JOIN sales_rejection_invoice_detail sd
			    ON sd.sales_rejection_invoice_basic_id =
			       sr.sales_rejection_invoice_basic_id

			INNER JOIN order_acceptance_basic o
			    ON o.customer_purchase_order_no =
			       sr.purchase_order

			INNER JOIN order_acceptance_detail od
			    ON od.order_acceptance_basic_id =
			       o.order_acceptance_basic_id
			   AND od.item = sd.item

			INNER JOIN customer_header c
			    ON c.customer_id = sr.customer

			INNER JOIN item i
			    ON i.item_id = sd.item

			INNER JOIN unitmaster u
			    ON u.unitmaster_id = i.primary_unit

			WHERE c.customer_name = :customerName
			  AND i.item_code = :itemCode
			   AND sr.doc_type = 'INVOICE'
			  AND sr.doc_date BETWEEN :fromDate AND :toDate
			  AND sr.branch = :branch
			  AND sr.org_id = :orgId
			""", nativeQuery = true)
	List<Object[]> getSalesRegisterProductWiseReport(@Param("customerName") String customerName,
			@Param("itemCode") String itemCode, @Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("branch") Long branch, @Param("orgId") Long orgId);

//	Sales register location wise

	@Query(value = """
			     SELECT
			         sr.doc_id,
			         sr.doc_date,
			         sd.despatch_qty,
			         c.customer_id,
			         c.customer_code,
			         c.customer_name,
			         i.item_code,
			         i.item_description,
			         o.doc_id AS order_acceptance_no,
			         u.unit_id,
			         cc.city,
			         sr.total_ass_val,
			         sr.net_amount,
			         CASE
			    WHEN sd.new_rate = 0
			        THEN sd.rate_in_selected_currency
			    ELSE sd.new_rate
			END AS rate
			     FROM sales_rejection_invoice_basic sr

			     INNER JOIN sales_rejection_invoice_detail sd
			         ON sd.sales_rejection_invoice_basic_id =
			            sr.sales_rejection_invoice_basic_id

			     INNER JOIN order_acceptance_basic o
			         ON o.customer_purchase_order_no =
			            sr.purchase_order

			     INNER JOIN order_acceptance_detail od
			         ON od.order_acceptance_basic_id =
			            o.order_acceptance_basic_id
			        AND od.item = sd.item

			     INNER JOIN customer_header c
			         ON c.customer_id = sr.customer

			     INNER JOIN item i
			         ON i.item_id = sd.item

			     INNER JOIN unitmaster u
			         ON u.unitmaster_id = i.primary_unit
			    inner join city cc on cc.city_id = c.city


			     WHERE (cc.city = :location OR :location = 'ALL')
			       AND i.item_code = :itemCode
			       AND sr.doc_type = 'INVOICE'
			       AND sr.doc_date BETWEEN :fromDate AND :toDate
			       AND sr.branch = :branch
			       AND sr.org_id = :orgId
			     """, nativeQuery = true)
	List<Object[]> getSalesRegisterLocationWiseReport(@Param("location") String location,
			@Param("itemCode") String itemCode, @Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("branch") Long branch, @Param("orgId") Long orgId);

//	sales customer/part no -c

	@Query(value = """
			SELECT
			    sr.doc_id,
			    sr.doc_date,
			    c.customer_code,
			    c.customer_name,
			    i.item_code,
			    i.item_description,
			    sd.customer_part_no,
			    u.unit_id,
			    sd.despatch_qty,
			    sr.total_ass_val,
			    SUM(sd.igst_amount) AS igst,
			    SUM(sd.cgst_amount) AS cgst,
			    SUM(sd.sgst_amount) AS sgst
			FROM sales_rejection_invoice_basic sr
			INNER JOIN sales_rejection_invoice_detail sd
			    ON sd.sales_rejection_invoice_basic_id =
			       sr.sales_rejection_invoice_basic_id
			INNER JOIN customer_header c
			    ON c.customer_id = sr.customer
			INNER JOIN item i
			    ON i.item_id = sd.item
			INNER JOIN unitmaster u
			    ON u.unitmaster_id = sd.unit
			WHERE sr.branch = :branch
			ANd sr.org_id = :orgId
			  AND sr.doc_date BETWEEN :fromDate AND :toDate
			  AND c.customer_name = :customerName
			  AND (sr.doc_type IS NULL OR sr.doc_type = 'INVOICE')
			GROUP BY
			    sr.doc_id,
			    sr.doc_date,
			    c.customer_code,
			    c.customer_name,
			    i.item_code,
			    i.item_description,
			    sd.customer_part_no,
			    u.unit_id,
			    sd.despatch_qty,
			    sr.total_ass_val
			""", nativeQuery = true)
	List<Object[]> getSalesCustomerPartNoCumlReport(@Param("branch") Long branch, Long orgId,
			@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("customerName") String customerName);

//	sales register customerwise

	@Query(value = """
			SELECT
			    srb.doc_id,
			    srb.doc_date,
			    srb.cancel,
			    srb.cancel_remarks,
			    srb.purchase_order,
			    c.customer_name,
			    c.customer_code,
			    i.item_code,
			    i.item_description,
			    srd.customer_part_no,
			    u.unit_id,

			    CASE
			        WHEN srb.cancel = FALSE
			        THEN srd.despatch_qty
			        ELSE 0
			    END AS qty,

			    CASE
			        WHEN srd.new_rate = 0
			        THEN srd.rate_in_selected_currency
			        ELSE srd.new_rate
			    END AS rate,

			    CASE
			        WHEN srb.cancel = FALSE
			        THEN srb.total_ass_val
			        ELSE 0
			    END AS amtInRs,

			    CASE
			        WHEN srb.cancel = FALSE
			        THEN srb.net_amount
			        ELSE 0
			    END AS vatVal,

			    SUM(srd.sgst_amount) AS sgst,
			    SUM(srd.cgst_amount) AS cgst,
			    SUM(srd.igst_amount) AS igst

			FROM sales_rejection_invoice_basic srb

			INNER JOIN customer_header c
			    ON c.customer_id = srb.customer

			INNER JOIN sales_rejection_invoice_detail srd
			    ON srd.sales_rejection_invoice_basic_id =
			       srb.sales_rejection_invoice_basic_id

			INNER JOIN item i
			    ON i.item_id = srd.item

			INNER JOIN unitmaster u
			    ON u.unitmaster_id = i.primary_unit

			WHERE srb.belongs_to = :belongsTo
			  AND srb.doc_date BETWEEN :fromDate AND :toDate
			  AND c.customer_name = :customerName
			  AND srb.branch = :branch
			  AND srb.org_id = :orgId

			GROUP BY
			    srb.doc_id,
			    srb.doc_date,
			    srb.cancel,
			    srb.cancel_remarks,
			    srb.purchase_order,
			    c.customer_name,
			    c.customer_code,
			    i.item_code,
			    i.item_description,
			    srd.customer_part_no,
			    u.unit_id,
			    srb.total_ass_val,
			    srb.net_amount,
			    srd.despatch_qty,
			    srd.new_rate,
			    srd.rate_in_selected_currency
			""", nativeQuery = true)
	List<Object[]> getSalesRegisterCustomerWiseReport(@Param("belongsTo") Long belongsTo, @Param("fromDate") String fromDate,
			@Param("toDate") String toDate, @Param("customerName") String customerName, @Param("branch") Long branch,
			@Param("orgId") Long orgId);
	
	
	  @Query(value = """
	            SELECT
	                h.doc_id AS DOCID,
	                h.doc_date AS DOCDT,
	                d.sales_order_contract_no AS OANO,
	                c.customer_code AS PARTYID,
	                c.customer_name AS PARTYNAME,
	                i.item_id AS ITEMID,
	                i.item_description AS ITEMDESC,
	                d.customer_part_no AS CPART,
	                u.unit_id AS UNITID,
	                d.despatch_qty AS QTY,

	                CASE
	                    WHEN COALESCE(d.new_rate, 0) = 0
	                        THEN d.rate_in_selected_currency
	                    ELSE d.new_rate
	                END AS RATE,

	                h.total_ass_val AS AMTINRS,
	                h.net_amount AS VATVAL,

	                SUM(COALESCE(d.igst_amount, 0)) AS IGST,
	                SUM(COALESCE(d.sgst_amount, 0)) AS SGST,
	                SUM(COALESCE(d.cgst_amount, 0)) AS CGST

	            FROM sales_rejection_invoice_basic h

	            INNER JOIN sales_rejection_invoice_detail d
	                ON h.sales_rejection_invoice_basic_id =
	                   d.sales_rejection_invoice_basic_id

	            INNER JOIN customer_header c
	                ON c.customer_id = h.customer

	            INNER JOIN item i
	                ON i.item_id = d.item

	            LEFT JOIN unitmaster u
	                ON u.unitmaster_id = d.unit

	            WHERE h.doc_date BETWEEN STR_TO_DATE(:fromdate, '%Y-%m-%d')
	                                  AND STR_TO_DATE(:todate, '%Y-%m-%d')

	              AND h.org_id = :orgId
	              AND h.branch = :branch

	              AND c.customer_name NOT LIKE 'BOSCH%'
	              AND c.customer_name NOT LIKE 'FOUNDATION%'
	              AND c.customer_name NOT LIKE 'ROBERT%'
	              AND c.customer_name NOT LIKE 'SARASWATHI%'
	              AND c.customer_name NOT LIKE 'ENGINEERING%'

	              AND (h.doc_type IS NULL OR h.doc_type = 'INVOICE')

	            GROUP BY
	                h.doc_id,
	                h.doc_date,
	                d.sales_order_contract_no,
	                c.customer_code,
	                c.customer_name,
	                i.item_id,
	                i.item_description,
	                d.customer_part_no,
	                u.unit_id,
	                d.despatch_qty,
	                d.new_rate,
	                d.rate_in_selected_currency,
	                h.total_ass_val,
	                h.net_amount

	            ORDER BY
	                c.customer_code,
	                c.customer_name,
	                h.doc_id
	            """, nativeQuery = true)
	    List<Object[]> getSalesRegisterAppliancesReport(
	            @Param("fromdate") String fromdate,
	            @Param("todate") String todate,
	            @Param("orgId") Long orgId,
	            @Param("branch") Long branch);
	    
	    //getSalesRegisterAppliancesReportMultiselectquery
	    
	    @Query(value = """
	            SELECT
	                p.customer_id AS id,
	                p.customer_name AS name
	            FROM customer_header p
	            INNER JOIN listofvaluesdetails lov
	                ON lov.listofvaluesdetails_id = p.customer_category
	            WHERE lov.value_description = 'Customer'
	              AND p.org_id = :orgId
	              AND p.branch = :branch
	              AND p.customer_name NOT LIKE 'BOSCH%'
	              AND p.customer_name NOT LIKE 'MOTOR%'
	              AND p.customer_name NOT LIKE 'SHASH%'
	              AND p.customer_name NOT LIKE 'ENGINEE%'
	              AND p.customer_name NOT LIKE 'J.L%'
	            ORDER BY p.customer_name
	            """, nativeQuery = true)
	    List<Object[]> getCustomerSalesRegisterAppliancesReport(
	            @Param("orgId") Long orgId,
	            @Param("branch") Long branch);
	    
	    
	  //salesRegisterCustomerBosch
	    
	    @Query(value = """
	    	    SELECT
	    	        IVB.doc_id AS DOCID,
	    	        IVB.doc_date AS DOCDT,
	    	        IVD.sales_order_contract_no AS OANO,
	    	        PM.customer_code AS PARTYID,
	    	        PM.customer_name AS PARTYNAME,
	    	        IM.item_code AS ITEMID,
	    	        IM.item_description AS ITEMDESC,
	    	        UM.unit_id AS UNITID,
	    	        IVD.despatch_qty AS QTY,
	    	        IF(
	    	            COALESCE(IVD.new_rate, 0) = 0,
	    	            IVD.rate_in_selected_currency,
	    	            IVD.new_rate
	    	        ) AS RATE,
	    	        IVD.amount_in_rs AS amtinrs,
	    	        IVD.cgst_amount AS CGSTVAL,
	    	        IVD.sgst_amount AS SGSTVAL,
	    	        IVD.igst_amount AS IGSTVAL
	    	    FROM sales_rejection_invoice_basic IVB
	    	    JOIN sales_rejection_invoice_detail IVD
	    	        ON IVB.sales_rejection_invoice_basic_id =
	    	           IVD.sales_rejection_invoice_basic_id
	    	    LEFT JOIN customer_header PM
	    	        ON PM.customer_id = IVB.customer
	    	    LEFT JOIN item IM
	    	        ON IM.item_id = IVD.item
	    	    LEFT JOIN unitmaster UM
	    	        ON UM.unitmaster_id = IVD.unit
	    	    WHERE IVB.cancel = b'0'
	    	      AND IVB.doc_date BETWEEN :fromdate AND :todate
	    	      AND LOWER(IVB.narration) = 'sales rejection invoice'
	    	    ORDER BY PM.customer_name, IM.item_description
	    	    """, nativeQuery = true)
	    	List<Object[]> getsalesRegisterCustomerBosch(
	    	    @Param("fromdate") String fromdate,
	    	    @Param("todate") String todate);

}
