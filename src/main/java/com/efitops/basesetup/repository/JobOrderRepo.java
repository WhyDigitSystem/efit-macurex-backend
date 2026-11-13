package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.JobOrderVO;

@Repository
public interface JobOrderRepo extends JpaRepository<JobOrderVO, Long> {

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getJobOrderDocId(Long orgId, String financialYear, String screenCode);

	@Query(value = """
			SELECT *
			FROM job_order_basic
			WHERE org_id = :orgId
			  AND branch = :branch
			""", nativeQuery = true)
	List<JobOrderVO> findByOrgIdAndBranch(@Param("orgId") Long orgId, @Param("branch") Long branch);

	@Query(value = """
			SELECT
			    jo.job_order_basic_id AS id,
			    jo.doc_id AS jobOrderNo,
			    jo.doc_date AS jobOrderDate
			FROM job_order_basic jo
			WHERE jo.branch = :branch
			  AND jo.org_id = :orgId
			  AND jo.vendor = :customer
			  AND jo.active = 1
			  AND jo.cancel = 0
			ORDER BY jo.job_order_basic_id DESC
			""", nativeQuery = true)
	Set<Object[]> getJobOrderNoAndDateForJobOrderAmd(@Param("branch") Long branch, @Param("orgId") Long orgId,
			@Param("customer") Long customer);

	@Query(value = """
			        SELECT
			            jod.job_order_details_id AS id,

			            jod.incoming_item AS item,
			            i.item_code AS itemCode,
			            i.item_description AS itemDescription,

			            jod.unit AS unit,
			            u.description AS unitDescription,

			            jod.rate AS rate,

			            jod.bom AS bom,
			            job.delivery_date AS deliveryDate,
						jod.order_qty AS order_qty
			        FROM job_order_basic job

			        JOIN job_order_details jod
			            ON jod.job_order_basic_id = job.job_order_basic_id

			        LEFT JOIN item i
			            ON i.item_id = jod.incoming_item

			        LEFT JOIN unitmaster u
			            ON u.unitmaster_id = jod.unit

			        WHERE job.doc_id = :jobOrderNo
			          AND job.branch = :branch
			          AND job.org_id = :orgId
			          AND job.vendor = :customer
			          AND job.active = 1
			          AND job.cancel = 0
			        """, nativeQuery = true)
	Set<Object[]> getJobOrderItemDetailsForJobOrderAmd(@Param("jobOrderNo") String jobOrderNo,
			@Param("branch") Long branch, @Param("orgId") Long orgId, @Param("customer") Long customer);

	@Query(value = """
			SELECT
			    job.job_order_basic_id AS id,
			    job.job_order_for AS jobOrderFor,
			    job.contract_no AS contractNo,
			    jod.incoming_item AS outgoingItem,
			    i.item_code AS itemCode,
			    i.item_description AS itemDescription,
			    jod.unit AS unitId,
			    u.unit_id As unit,
			    u.description AS unitDescription,
			    jod.rate AS rate
			FROM job_order_basic job
			JOIN job_order_details jod
			    ON jod.job_order_basic_id = job.job_order_basic_id
			LEFT JOIN item i
			    ON i.item_id = jod.incoming_item
			LEFT JOIN unitmaster u
			    ON u.unitmaster_id = jod.unit
			WHERE job.doc_id = :jobOrderNo
			  AND job.branch = :branch
			  AND job.org_id = :orgId
			  AND job.vendor = :vendor
			  AND job.active = 1
			  AND job.cancel = 0
			ORDER BY jod.job_order_details_id
			""", nativeQuery = true)
	Set<Object[]> getItemDetailsforDeliveryChallanSubContract(@Param("jobOrderNo") String jobOrderNo,
			@Param("branch") Long branch, @Param("orgId") Long orgId, @Param("vendor") Long vendor);

	@Query(value = """
			SELECT
			    jo.job_order_basic_id AS id,
			    jo.doc_id AS jobOrderNo,
			    jo.doc_date AS jobOrderDate
			FROM job_order_basic jo
			WHERE jo.branch = :branch
			  AND jo.org_id = :orgId
			  AND jo.contract_no = :contractNo
			  AND jo.active = 1
			  AND jo.cancel = 0
			ORDER BY jo.job_order_basic_id DESC
			""", nativeQuery = true)
	Set<Object[]> getJobOrderNoAndDateForSubContractSupplySch(@Param("branch") Long branch, @Param("orgId") Long orgId,
			@Param("contractNo") String contractNo);

	@Query(value = """
	        SELECT
	            j.job_order_basic_id AS wkorderbasicid,
	            j.doc_id AS WONO,
	            j.doc_date AS WODT,
	            CONCAT(c.customer_code, '-', c.customer_name) AS PARTYNAME,
	            src.doc_id AS contno,
	            i.item_code AS INCOMINGID,
	            u.unit_id AS unitid,
	            d.issue_qty AS grnrecqty,
	            jd.order_qty AS jobqty,
	            (jd.order_qty - COALESCE(d.issue_qty, 0)) AS PendQty,
	            c.customer_code AS partyid
	        FROM job_order_basic j
	        INNER JOIN customer_header c
	            ON c.customer_id = j.vendor
	        LEFT JOIN supplier_rate_contract src
	            ON src.doc_id = j.contract_no
	        INNER JOIN job_order_details jd
	            ON jd.job_order_basic_id = j.job_order_basic_id
	        INNER JOIN item i
	            ON i.item_id = jd.incoming_item
	        LEFT JOIN unitmaster u
	            ON u.unitmaster_id = jd.unit
	        LEFT JOIN deliverychallan_subcontracting_details d
	            ON d.contract_no = j.doc_id
	           AND d.outgoing_item = i.item_id
	        WHERE j.branch = :branch
	          AND j.doc_date BETWEEN :fromDate AND :toDate
	          AND j.cancel = FALSE
	        ORDER BY j.doc_id, PARTYNAME, i.item_code
	        """, nativeQuery = true)
	List<Object[]> getJobOrderStatusReport(
	        @Param("branch") Long branch,
	        @Param("fromDate") String fromDate,
	        @Param("toDate") String toDate
	);
	
	@Query(value = """
		    SELECT
		        a.doc_id AS WONO,
		        a.doc_date AS WODT,
		        d.customer_name AS PARTYNAME,
		        i1.item_code AS INCOMINGID,
		        u1.unit_id AS unitid,
		        d.customer_code AS partyid,
		        i2.item_code AS itemid,
		        i2.item_description AS itemdesc
		    FROM job_order_basic a
		    LEFT JOIN customer_header d
		        ON d.customer_id = a.vendor
		    INNER JOIN job_order_details wp
		        ON wp.job_order_basic_id = a.job_order_basic_id
		    LEFT JOIN item i1
		        ON i1.item_id = wp.incoming_item
		    LEFT JOIN unitmaster u1
		        ON u1.unitmaster_id = wp.unit
		    LEFT JOIN deliverychallan_subcontracting c
		        ON c.job_order_no = a.doc_id
		    LEFT JOIN item i2
		        ON i2.item_id = c.incoming_item
		    WHERE a.doc_date BETWEEN :fromdt AND :todate
		      AND a.branch = :branch
		      AND a.org_id = :orgId
		    ORDER BY
		        a.doc_id,
		        d.customer_name,
		        i1.item_code
		    """, nativeQuery = true)
		List<Object[]> getJobOrderdetailsReport(
		        @Param("fromdt") String fromdt,
		        @Param("todate") String todate,
		        @Param("branch") Long branch,
		        @Param("orgId") Long orgId
		);
}
