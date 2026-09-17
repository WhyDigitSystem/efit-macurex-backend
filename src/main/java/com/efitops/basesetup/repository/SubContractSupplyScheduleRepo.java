package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SubContractSupplyScheduleVO;

@Repository
public interface SubContractSupplyScheduleRepo extends JpaRepository<SubContractSupplyScheduleVO, Long> {

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getSubContractSupplyScheduleDocId(Long orgId, String financialYear, String screenCode1);

	@Query(value = """
	        SELECT *
	        FROM subcontract_supply_schedule
	        WHERE org_id = :orgId
	        AND branch = :branch and cancel=0
	        """, nativeQuery = true)
	List<SubContractSupplyScheduleVO> findByOrgIdAndBranch(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);
	
	@Query(value = """
	        SELECT
	            s.doc_id AS scheduleNo,
	            s.doc_date AS scheduleDate,
	            s.sch_start_date AS schStartDate,
	            s.sch_end_date AS schEndDate,

	            s.contract_no AS contractNo,

	            h.hsn_id AS hsnId,
	            h.hsn AS hsnCode,
	            h.description AS hsnDescription,

	            sam.serviceaccmaster_id AS serviceId,
	            sam.service_name AS serviceName,

	            grm.gstratemaster_id AS gstRateMasterId,
	            grm.rate AS gstRate,
	            grm.igst AS igstRate,
	            grm.cgst AS cgstRate,
	            grm.sgst AS sgstRate

	        FROM subcontract_supply_schedule s

	        LEFT JOIN supplier_rate_contract src
	            ON src.doc_id = s.contract_no
	            AND src.org_id = s.org_id
	            AND src.branch = s.branch
	            AND src.active = 1
	            AND src.cancel = 0

	        LEFT JOIN hsn h
	            ON h.hsn_id = src.hsn_sac_code
	            AND h.org_id = s.org_id
	            AND h.branch = s.branch
	            AND h.active = 1
	            AND h.cancel = 0

	        LEFT JOIN serviceaccmaster sam
	            ON sam.serviceaccmaster_id = src.service_name
	            AND sam.org_id = s.org_id
	            AND sam.branch = s.branch
	            AND sam.active = 1
	            AND sam.cancel = 0

	        LEFT JOIN gstratemaster grm
	            ON grm.hsn_sac_code = h.hsn_id
	            AND grm.org_id = s.org_id
	            AND grm.branch = s.branch
	            AND grm.active = 1
	            AND grm.cancel = 0

	        WHERE s.org_id = :orgId
	          AND s.branch = :branch
	          AND s.customer = :customer
	        """, nativeQuery = true)
	Set<Object[]> getSubcontractSupplyScheduleforSubContractingGRN(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch,
	        @Param("customer") Long customer);

	
	@Query(value = """
	        SELECT
	            i.item_id AS itemId,
	            i.item_code AS itemCode,
	            i.item_description AS itemDescription,

	            u.unitmaster_id AS unitId,
	            u.unit_id AS unitCode,
	            u.description AS unitDescription,

	            s.job_order_no AS jobOrderNo,

	            sid.qty AS jobOrderQty,
	            sid.rate AS jobOrderRate

	        FROM subcontract_supply_schedule s

	        INNER JOIN subcontract_supply_schedule_item_details sid
	            ON sid.subcontract_supply_schedule_id =
	               s.subcontract_supply_schedule_id

	        LEFT JOIN item i
	            ON i.item_id = sid.item

	        LEFT JOIN unitmaster u
	            ON u.unitmaster_id = sid.unit

	        WHERE s.doc_id = :scheduleNo
	          AND s.org_id = :orgId
	          AND s.branch = :branch
	          AND s.customer = :customer
	          AND s.active = 1
	          AND s.cancel = 0
	        """, nativeQuery = true)
	Set<Object[]> getItemDetailsForSubContractingGRN(
	        @Param("scheduleNo") String scheduleNo,
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch,
	        @Param("customer") Long customer);

}
