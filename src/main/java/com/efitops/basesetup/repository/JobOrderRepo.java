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
	List<JobOrderVO> findByOrgIdAndBranch(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);

	
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
	Set<Object[]> getJobOrderNoAndDateForJobOrderAmd(
	        @Param("branch") Long branch,
	        @Param("orgId") Long orgId,
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


	            job.delivery_date AS deliveryDate

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
	Set<Object[]> getJobOrderItemDetailsForJobOrderAmd(
	        @Param("jobOrderNo") String jobOrderNo,
	        @Param("branch") Long branch,
	        @Param("orgId") Long orgId,
	        @Param("customer") Long customer);

	@Query(value = """
	        SELECT
	            job.job_order_basic_id AS id,
	            job.job_order_for AS jobOrderFor,
	            job.contract_no AS contractNo,
	            jod.incoming_item AS outgoingItem,
	            i.item_code AS itemCode,
	            i.item_description AS itemDescription,
	            jod.unit AS unit,
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
	Set<Object[]> getItemDetailsforDeliveryChallanSubContract(
	        @Param("jobOrderNo") String jobOrderNo,
	        @Param("branch") Long branch,
	        @Param("orgId") Long orgId,
	        @Param("vendor") Long vendor);
	
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
	Set<Object[]> getJobOrderNoAndDateForSubContractSupplySch(
	        @Param("branch") Long branch,
	        @Param("orgId") Long orgId,
	        @Param("contractNo") String contractNo);


}
