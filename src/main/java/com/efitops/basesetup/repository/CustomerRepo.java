package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

@Query(value = "SELECT customer_id, customer_name " +
        "FROM customer_header " +
        "WHERE org_id = ?1 and branch=?2  " +
        "AND cancel = 0 and active=1",
nativeQuery = true)
List<Object[]> getCustomerDetails(Long orgId, Long branch);	


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
			INNER JOIN quotation_header q
			    ON q.customer = c.customer_id
			INNER JOIN gststatemaster g
			    ON g.gststatemaster_id = c.gst_state
			WHERE c.cancel = 0
			  AND c.active = 1
			  AND ?1 = 'Flow'
			  AND q.org_id = ?2
			  AND q.branch = ?3
			  AND NOT EXISTS (
			        SELECT 1
			        FROM sales_contract_basic sc
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
		
			//getstocktransfercustomer
			@Query(value =
					"SELECT " +
					"c.customer_id, " +
					"c.customer_code, " +
					"c.customer_name, " +
					"c.account_name, " +
					"g.gststatemaster_id, " +
					"g.state_code, " +
					"g.state_name, " +
					"g.gst_state_id, " +
					"c.is_gst_applicable, " +
					"c.gst_no " +
					"FROM customer_header c " +
					"INNER JOIN gststatemaster g ON c.gst_state = g.gststatemaster_id " +
					"WHERE c.cancel = false " +
					"AND c.active = true " +
					"AND LOWER(c.customer_type)='customer' " +
					"AND c.branch = ?1 " +
					"AND c.org_id = ?2 " +
					"ORDER BY c.customer_code",
					nativeQuery = true)
					List<Object[]> getCustomerForStockTransferChallan(Long branch,Long orgId);
					
					//despatch instruction customer dropdown
					@Query(value = """
						    SELECT
						        c.customer_id,
						        c.customer_code,
						        c.customer_name,
						        c.account_name
						    FROM customer_header c

						    LEFT JOIN listofvaluesdetails a
						        ON c.customer_category = a.listofvaluesdetails_id

						    LEFT JOIN listofvaluesdetails b
						        ON c.customer_category1 = b.listofvaluesdetails_id

						    LEFT JOIN listofvaluesdetails cc
						        ON c.customer_category2 = cc.listofvaluesdetails_id

						    WHERE c.cancel = FALSE
						      AND c.active = TRUE
						      AND c.branch = :branch
						      AND c.org_id = :orgId
						      AND (
						            a.value_code = 'CUSTOMER'
						         OR b.value_code = 'CUSTOMER'
						         OR cc.value_code = 'CUSTOMER'
						      )

						    ORDER BY c.customer_code
						    """, nativeQuery = true)
						List<Object[]> getCustomerDropdownForDespatchInstructions(@Param("branch") Long branch,
						                                   @Param("orgId") Long orgId);

			@Query(value = """
			        SELECT
			            customer_id AS customerId,
			            customer_name AS customerName,
			            customer_code AS customerCode
			        FROM customer_header
			        WHERE org_id = ?1
			          AND branch = ?2 and customer_type="CUSTOMER"
			          AND active = 1
			          AND cancel = 0
			        ORDER BY customer_name
			        """, nativeQuery = true)
			List<Map<String, Object>> getAllCustomerDetails(
			        @Param("orgId") Long orgId,
			        @Param("branch") Long branch);

			
			
//		Customerdropdown for the purchasedelivaryschedule 
			
			@Query(value = "SELECT\r\n"
					+ "			    c.customer_id,\r\n"
					+ "			    c.customer_code,\r\n"
					+ "			    c.customer_name\r\n"
					+ "			FROM customer_header c\r\n"
					+ "			LEFT JOIN listofvaluesdetails a\r\n"
					+ "			    ON c.customer_category = a.listofvaluesdetails_id\r\n"
					+ "			LEFT JOIN listofvaluesdetails b\r\n"
					+ "			    ON c.customer_category1 = b.listofvaluesdetails_id\r\n"
					+ "			LEFT JOIN listofvaluesdetails cc\r\n"
					+ "			    ON c.customer_category2 = cc.listofvaluesdetails_id\r\n"
					+ "			WHERE c.cancel = FALSE\r\n"
					+ "			  AND c.active = TRUE\r\n"
					+ "			  AND c.branch = :branch\r\n"
					+ "			  AND c.org_id = :orgId\r\n"
					+ "			  AND (a.value_description = 'Supplier'\r\n"
					+ "			        OR b.value_description = 'Supplier'\r\n"
					+ "			        OR cc.value_description = 'Supplier'\r\n"
					+ "			      )\r\n"
					+ "			ORDER BY c.customer_code", nativeQuery = true)
			List<Object[]> getSupplierDropdownForPurchaseDeliverySchedule(@Param("branch") Long branch,
			                                   @Param("orgId") Long orgId);

			
			@Query(value = """
			        SELECT
			            c.customer_id AS customerId,
			            c.customer_code AS customerCode,
			            c.customer_name AS customerName,
			            gs.state_name AS gstState,
			            c.gst_no AS gstNo,
			            c.is_gst_applicable AS igstApplicable,
			            c.gst_type AS gstType,
			            cs.shipping_address AS shippingAddress,
			            city.city AS shippingCity,
			            cs.shipping_pincode AS shippingPincode
			        FROM customer_header c

			        LEFT JOIN gststatemaster gs
			            ON gs.gststatemaster_id = c.gst_state
			            AND gs.active = 1
			            AND gs.cancel = 0

			        LEFT JOIN customer_shipping_details cs
			            ON cs.customer_id = c.customer_id

			        LEFT JOIN city city
			            ON city.city_id = cs.shipping_city

			        WHERE c.org_id = ?1
			          AND c.branch = ?2
			          AND c.customer_type = ?3
			          AND c.active = 1
			          AND c.cancel = 0

			        ORDER BY c.customer_name
			        """, nativeQuery = true)
			Set<Object[]> getCustomerDetailsforSalesRejectionInvoice(
			        Long orgId,
			        Long branch,
			        String customerType);

}





