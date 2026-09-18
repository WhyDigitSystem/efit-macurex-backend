package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DeliveryChallanCumGatePassVO;

@Repository
public interface DeliveryChallanCumGatePassRepo  extends JpaRepository<DeliveryChallanCumGatePassVO, Long>{

	@Query(value = """
	        SELECT
	            jod.incoming_item AS item,
	            i.item_code AS itemCode,
	            i.item_description AS itemDescription,
	            job.hsn_sac_code AS hsnSacCode,
	            jod.unit AS unit,
	            u.description AS unitDescription,

	            CASE
	                WHEN dcg.delivery_challan_cum_gate_pass_id IS NULL
	                    THEN jod.order_qty
	                ELSE dcgd.qty
	            END AS qty,

	            CASE
	                WHEN dcg.delivery_challan_cum_gate_pass_id IS NULL
	                    THEN jod.rate
	                ELSE dcgd.rate
	            END AS rate

	        FROM job_order_basic job

	        JOIN job_order_details jod
	            ON jod.job_order_basic_id = job.job_order_basic_id

	        LEFT JOIN item i
	            ON i.item_id = jod.incoming_item

	        LEFT JOIN unitmaster u
	            ON u.unitmaster_id = jod.unit

	        LEFT JOIN delivery_challan_cum_gate_pass dcg
	            ON dcg.work_order_no = job.doc_id
	            AND dcg.active = 1
	            AND dcg.cancel = 0

	        LEFT JOIN delivery_challan_cum_gate_pass_details dcgd
	            ON dcgd.delivery_challan_cum_gate_pass_id =
	               dcg.delivery_challan_cum_gate_pass_id
	            AND dcgd.item = jod.incoming_item

	        WHERE job.doc_id = :jobOrderNo
	          AND job.branch = :branch
	          AND job.org_id = :orgId
	          AND job.vendor = :customer
	          AND job.active = 1
	          AND job.cancel = 0
	        """, nativeQuery = true)
	Set<Object[]> getDeliveryChallanCumGatePassDetails(
	        @Param("jobOrderNo") String jobOrderNo,
	        @Param("branch") Long branch,
	        @Param("orgId") Long orgId,
	        @Param("customer") Long customer);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getDeliveryChallanCumGatePassDocId(Long orgId, String financialYear, String screenCode);

	List<DeliveryChallanCumGatePassVO> findByOrgIdAndBranch(Long orgId, Long branch);
}
