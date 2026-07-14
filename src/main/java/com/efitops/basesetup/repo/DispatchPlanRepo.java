package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DispatchPlanVO;

@Repository
public interface DispatchPlanRepo extends JpaRepository<DispatchPlanVO, Long> {

	@Query(nativeQuery = true, value = "select * from dispatchplan d where orgid=?1 order by d.docid desc ")
	List<DispatchPlanVO> getDispatchPlanByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from dispatchplan where dispatchplanid=?1")
	DispatchPlanVO getDispatchPlanById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and  screencode=?2")
	String getDispatchPlanByDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "select a.docid,a.customercode,a.customername,a.wono from routecardentry a where a.orgid=?1 and a.status='PENDING' and active=1 order by 1")
	Set<Object[]> findRouteCardDetailsForDispatchPlan(Long orgId);

	@Query(nativeQuery = true, value = "select b.partno,b.partname,b.uom,b.ordqty from workorder a join workorderdetails b ON a.workorderid=b.workorderid where a.orgid=?1 and a.docid=?2  and active=1 order by 1")
	Set<Object[]> findItemDetailsForDispatchPlan(Long orgId, String routeCardNo);

	@Query(nativeQuery = true, value = "SELECT\r\n"
			+ "    dp.dispatchplanid,\r\n"
			+ "    dp.docid,\r\n"
			+ "    dp.docdate,\r\n"
			+ "    dp.routecardentry,\r\n"
			+ "    dp.customercode,\r\n"
			+ "    dp.customername,\r\n"
			+ "    dp.workorderno,\r\n"
			+ "    dp.scheduledispatchdate,\r\n"
			+ "    dp.dispatchtype,\r\n"
			+ "    dp.narration,\r\n"
			+ "    dpd.item,\r\n"
			+ "    dpd.itemdesc,\r\n"
			+ "    dpd.unit,\r\n"
			+ "    dpd.orderqty,\r\n"
			+ "    dpd.deliveryqty,\r\n"
			+ "    dpd.remarks\r\n"
			+ "FROM dispatchplan dp\r\n"
			+ "join dispatchplandetails dpd\r\n"
			+ "    ON dp.dispatchplanid = dpd.dispatchplanid\r\n"
			+ "WHERE dp.orgid = ?1\r\n"
			+ "  AND ( ?2 is null or dp.docdate >= ?2 ) and   ( ?3 is null or dp.docdate <= ?3 )\r\n"
			+ "  AND (dp.routecardentry = ?4 or ?4='ALL')")
	Set<Object[]> getDispatchPlanReport(Long orgId, String fromDate, String toDate, String routeCardEntry);

}
