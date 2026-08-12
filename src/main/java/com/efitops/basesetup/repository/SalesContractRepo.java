package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesContractVO;

@Repository
public interface SalesContractRepo extends JpaRepository<SalesContractVO, Long> {

	@Query(value = """
			SELECT
			    q.quotation_id,
			    q.doc_id AS quotation_no,
			    q.doc_date
			FROM quotation_header q
			INNER JOIN customer_header c
			    ON c.customer_id = q.customer
			WHERE q.cancel = 0
			  AND c.cancel = 0
			  AND c.active = 1
			  AND c.customer_code = ?1
			  AND ?2 = 'Flow'
			  AND q.org_id = ?3
			  AND q.branch = ?4
			  AND NOT EXISTS (
			        SELECT 1
			        FROM sales_contract_basic sc
			        WHERE sc.quotation_no = q.doc_id
			          AND sc.customer = c.customer_id
			          AND sc.org_id = ?3
			          AND sc.branch = ?4
			          AND sc.cancel = 0
			    )

			UNION

			SELECT
			    q.quotation_id,
			    q.doc_id AS quotation_no,
			    q.doc_date
			FROM quotation_header q
			WHERE q.cancel = 0
			  AND q.doc_id = ?5
			  AND ?6 > 0
			  AND q.org_id = ?3
			  AND q.branch = ?4

			ORDER BY quotation_no
			""", nativeQuery = true)
	List<Object[]> getQuotationDropdown(String customerCode, String ctype, Long orgId, Long branch,
			String oldQuotationNo, Long recId);

	@Query(value = """
											SELECT
			    i.item_id,
			    i.item_code,
			    i.item_description,
			    h.hsn,
			    i.customer_part_no,
			    gr.rate,
			    gr.cgst,
			    gr.sgst,
			    gr.igst,
			    u.unitmaster_id,
			    u.unit_id,
			    gr.gstratemaster_id
			FROM quotation_header q
			INNER JOIN quotation_detail qd
			    ON q.quotation_id = qd.quotation_id
			INNER JOIN item i
			    ON i.item_id = qd.item
			INNER JOIN unitmaster u
			    ON u.unitmaster_id = i.primary_unit
			INNER JOIN hsn h
			    ON h.hsn_id = i.hsn_code
			LEFT JOIN gstratemaster gr
			    ON gr.hsn_sac_code = h.hsn_id
			    AND gr.active = 1
			    AND gr.cancel = 0
			    AND gr.org_id = q.org_id
			    AND gr.branch = q.branch
			WHERE q.doc_id = ?1
			  AND q.org_id = ?2
			  AND q.branch = ?3
			  AND q.cancel = 0
			ORDER BY i.item_code;
			""", nativeQuery = true)
	List<Object[]> getQuotationItemDropdown(String quotationNo, Long orgId, Long branch);

//	@Query(value = """
//			SELECT *
//			FROM sales_contract_basic
//			WHERE cancel = 0
//			  AND active = 1
//			ORDER BY customer_contract_no
//			""", nativeQuery = true)
//	List<SalesContractVO> getContractNo();
	
	@Query(value = """
		    SELECT
		        salescontract_id AS id,
		        doc_id AS contractNo,
		        customer_purchase_order_no AS custPoNo,
		        customer_purchase_order_date AS custPoDate,
		        doc_date AS contractDate 
		    FROM sales_contract_basic
		    WHERE cancel = 0
		      AND active = 1
		      AND org_id = :orgId
		      AND branch = :branch
		    ORDER BY customer_contract_no
		    """, nativeQuery = true)
		List<Object[]> getSalesContractAmdContractNoDropdown(@Param("orgId") Long orgId,
		                                        @Param("branch") Long branch);
	
	@Query(value = "SELECT doc_id AS docId, invoice_type AS invoiceType " +
            "FROM sales_contract_basic " +
            "WHERE cancel = 0 " +
            "AND active = 1 " +
            "AND org_id = :orgId " +
            "AND branch = :branch " +
            "UNION ALL " +
            "SELECT doc_id AS docId, so_type AS invoiceType " +
            "FROM order_acceptance_basic " +
            "WHERE cancel = 0 " +
            "AND active = 1 " +
            "AND org_id = :orgId " +
            "AND branch = :branch " +
            "ORDER BY docId",
    nativeQuery = true)
List<Map<String, Object>> getDocIdAndInvoiceType(
     @Param("orgId") Long orgId,
     @Param("branch") Long branch);
	

	@Query(value = """
			SELECT *
			FROM sales_contract_basic
			WHERE org_id = :orgId
			  AND branch = :branch
			  AND cancel = 0 and active=1
			ORDER BY salescontract_id DESC
			""", nativeQuery = true)
	List<SalesContractVO> findByOrgIdAndBranch(@Param("orgId") Long orgId, @Param("branch") Long branch);


	@Query(value = """
		    SELECT
		        i.item_id,
		        i.item_code,
		        i.item_description,
		        scad.new_rate
		    FROM sales_contract_basic scb
		    INNER JOIN sales_contract_detail scd
		        ON scb.salescontract_id = scd.salescontract_id
		    INNER JOIN item i
		        ON i.item_id = scd.item
		    LEFT JOIN sales_contract_amendment_basic scab
		        ON scab.contract_no = scb.customer_contract_no
		    LEFT JOIN sales_contract_amendment_detail scad
		        ON scad.sales_contract_amendment_basic_id = scab.sales_contract_amendment_basic_id
		       AND scad.item = scd.item
		    WHERE scb.doc_id = ?1
		      AND scb.org_id = ?2
		      AND scb.branch = ?3
		      AND scb.active = 1
		      AND scb.cancel = 0
		    ORDER BY i.item_description
		    """, nativeQuery = true)
		List<Object[]> getSalesContractAmdItemDropdown(String salesContractNo,
		                               Long orgId,
		                               Long branch);

		@Query(value = """
			    SELECT COALESCE(MAX(CAST(scab.revision_no AS UNSIGNED)), 0) + 1
			    FROM sales_contract_amendment_basic scab
			    INNER JOIN sales_contract_amendment_detail scad
			        ON scab.sales_contract_amendment_basic_id = scad.sales_contract_amendment_basic_id
			    WHERE scab.contract_no = ?1
			      AND scad.item = ?2
			      AND scab.org_id = ?3
			      AND scab.branch = ?4
			      AND scab.active = 1
			      AND scab.cancel = 0
			    """, nativeQuery = true)
			Integer getSalesContractAmdRevisionNo(String salesContractNo,
			                      Long item,
			                      Long orgId,
			                      Long branch);

	
	
	 @Query(value = """
	            SELECT
	                salescontract_id,
	                doc_id,
	                contract_date,
	                customer_purchase_order_no
	            FROM sales_contract_basic
	            WHERE active = true
	              AND cancel = false
	              AND org_id = :orgId
	              AND branch = :branch
	            ORDER BY doc_id
	            """, nativeQuery = true)
	    List<Object[]> getSalesContractDropdown(
	            @Param("orgId") Long orgId,
	            @Param("branch") Long branch);
	    
	    
	    

}
