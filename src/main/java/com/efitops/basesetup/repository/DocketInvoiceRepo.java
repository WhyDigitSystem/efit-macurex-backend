package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.DocketInvoiceVO;
import com.efitops.basesetup.entity.TransportMasterVO;

public interface DocketInvoiceRepo extends JpaRepository<DocketInvoiceVO, Long>{
	
	@Query(value = """
	        SELECT *
	        FROM docket_invoice_basic
	        WHERE org_id = :orgId
	          AND branch = :branch
	          AND cancel = false and active = 1
	        ORDER BY docket_invoice_basic_id
	        """, nativeQuery = true)
	List<DocketInvoiceVO> getDocketInvoiceByOrgId(@Param("orgId") Long orgId,
	                                             @Param("branch") Long branch);

	 @Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getDocketInvoiceDocId(Long orgId, String financialYear, String screenCode);

//	 invoice/docket details
	 
	 @Query(value = """
		        SELECT
		            t.transport_id AS transmastid,
		            t.transport_name AS transid,
		            d.doc_id AS dono,
		            d.doc_date AS docdate,
		            d.bill_no AS billno,
		            d.bill_date AS billdt,
		            d.total_amount AS totamt
		        FROM transport t
		        INNER JOIN docket_invoice_basic d
		            ON t.transport_id = d.transport_id
		        WHERE d.cancel = FALSE
		          AND d.doc_date BETWEEN :fromDate AND :toDate
		          and d.branch = :branch
                  and d.org_id = :orgId
		        GROUP BY
		            t.transport_id,
		            t.transport_name,
		            d.doc_id,
		            d.doc_date,
		            d.bill_no,
		            d.bill_date,
		            d.total_amount
		            
		        ORDER BY transid
		        """, nativeQuery = true)
		List<Object[]> getInvoiceDocketDetailsReport(
		        @Param("fromDate") String fromDate,
		        @Param("toDate") String toDate,
		        @Param("branch") Long branch,
		        @Param("orgId") Long orgId
		);
}
