package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.CustomerVO;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerVO, Long>{

	boolean existsByCustomerNameAndOrgId(String customerName, Long orgId);

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

}



