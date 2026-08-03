package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.CustomerVO;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerVO, Long>{

	boolean existsByCustomerNameAndOrgId(String string, Long orgId);

	boolean existsByGstNoAndOrgId(String gstNo, Long orgId);

	@Query(value = """
	        SELECT *
	        FROM customer_header
	        WHERE org_id = :orgId
	          AND branch = :branch
	          AND active = 1
	          AND cancel = 0
	        ORDER BY customer_id DESC
	        """, nativeQuery = true)
	List<CustomerVO> findByOrgIdAndBranch(@Param("orgId") Long orgId,
	                                      @Param("branch") Long branch);
	
	//dropdown for mapping party to account
	
	@Query(value = "SELECT customer_id AS partyId, customer_name AS partyName " +
	        "FROM customer_header " +
	        "WHERE active = 1 " +
	        "AND cancel = 0 " +
	        "AND org_id = ?2 " +
	        "AND branch = ?3 " +
	        "AND (customer_category = ?1 " +
	        "OR customer_category1 = ?1 " +
	        "OR customer_category2 = ?1) " +
	        "ORDER BY customer_name ASC",
	        nativeQuery = true)
	List<PartyProjection> getParty(Long category,
	                                       Long orgId,
	                                       Long branch);

	// dropdown for Customer in customer complaint entry
	@Query(value = "SELECT customer_id, customer_name " +
            "FROM customer_header " +
            "WHERE cancel = 0 " +
            "ORDER BY customer_id",
    nativeQuery = true)
List<Object[]> getCustomer();

@Query(value = "SELECT customer_id, customer_name " +
        "FROM customer_header " +
        "WHERE customer_id = ?1 " +
        "AND cancel = 0",
nativeQuery = true)
List<Object[]> getCustomerDetails(String customerId);


	@Query(value = """
			SELECT
			    c.customer_id,
			    c.customer_code,
			    c.customer_name,
			    c.address AS add1,
			    g.state_name AS GSTState,
			    c.gst_no,
			    c.is_gst_applicable,
			    c.gst_type
			FROM customer_header c
			INNER JOIN quotation q
			    ON q.party_id = c.customer_id
			INNER JOIN gststatemaster g
			    ON g.gststatemaster_id = c.gst_state
			WHERE c.cancel = 0
			  AND c.active = 1
			  AND ?1 = 'Flow'
			  AND q.org_id = ?2
			  AND q.branch = ?3
			  AND NOT EXISTS (
			        SELECT 1
			        FROM sales_contract sc
			        WHERE sc.customer = c.customer_id
			          AND sc.quotation_no = q.doc_id
			    )

			UNION

			SELECT
			    c.customer_id,
			    c.customer_code,
			    c.customer_name,
			    c.address AS add1,
			    g.state_name AS GSTState,
			    c.gst_no,
			    c.is_gst_applicable,
			    c.gst_type
			FROM customer_header c
			INNER JOIN gststatemaster g
			    ON g.gststatemaster_id = c.gst_state
			WHERE c.cancel = 0
			  AND c.active = 1
			  AND ?1 = 'Direct'
			  AND c.org_id = ?2
			  AND c.branch = ?3
			  AND UPPER(c.customer_type) = 'CUSTOMER'
			ORDER BY customer_code
			""", nativeQuery = true)
			List<Object[]> getCustomerDropdown(String ctype, Long orgId, Long branch);	
}



