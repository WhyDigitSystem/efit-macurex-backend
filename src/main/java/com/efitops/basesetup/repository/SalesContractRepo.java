package com.efitops.basesetup.repository;

import java.util.List;

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

	@Query(value = """
			SELECT *
			FROM sales_contract_basic
			WHERE cancel = 0
			  AND active = 1
			ORDER BY customer_contract_no
			""", nativeQuery = true)
	List<SalesContractVO> getContractNo();

	@Query(value = """
			SELECT *
			FROM sales_contract_basic
			WHERE org_id = :orgId
			  AND branch = :branch
			  AND cancel = 0 and active=1
			ORDER BY salescontract_id DESC
			""", nativeQuery = true)
	List<SalesContractVO> findByOrgIdAndBranch(@Param("orgId") Long orgId, @Param("branch") Long branch);
}
