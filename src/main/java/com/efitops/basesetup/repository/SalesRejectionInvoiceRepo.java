package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesRejectionInvoiceVO;

@Repository
public interface SalesRejectionInvoiceRepo extends JpaRepository<SalesRejectionInvoiceVO, Long>{

	@Query(value = "SELECT * " +
            "FROM sales_rejection_invoice_basic " +
            "WHERE org_id = :orgId " +
            "AND branch = :branch " +
            "AND active = 1 " +
            "AND cancel = 0 " +
            "ORDER BY sales_rejection_invoice_basic_id DESC",
    nativeQuery = true)
List<SalesRejectionInvoiceVO> getSalesRejectionInvoiceByOrgId(
     @Param("orgId") Long orgId,
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

}
