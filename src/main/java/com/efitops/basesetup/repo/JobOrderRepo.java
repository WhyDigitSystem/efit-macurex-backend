package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.JobOrderVO;

@Repository
public interface JobOrderRepo extends JpaRepository<JobOrderVO, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM joborder WHERE joborderid=?1")
	List<JobOrderVO> getAllJobOrderById(Long id);

	@Query(nativeQuery = true, value = "SELECT * FROM joborder j WHERE orgid=?1  order by j.docid desc")
	List<JobOrderVO> getAllJobOrderByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getJobOrderDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "SELECT A.shiftcode FROM shiftmast A WHERE A.orgid=?1")
	Set<Object[]> getShift(Long orgId);

	@Query(nativeQuery = true, value = "select b.process,b.totaltimetaken from productionplan a, productionplandetails b\n"
			+ "			 WHERE a.productionplanid=b.productionplanid AND a.orgid=?1  and a.routecardno=?2")
	Set<Object[]> getOperationName(Long orgId, String routeCardNo);

	@Query(nativeQuery = true, value = "select employee from employee where orgid=?1 and  partytype='MACHINE OPERATOR'")
	Set<Object[]> getOperatorName(Long orgId);

	@Query(nativeQuery = true, value = "SELECT b.timeinhours FROM shiftmast a join shiftdet b\r\n"
			+ " ON a.shiftmastid=b.shiftmastid  WHERE a.orgid=?1 and a.shiftcode=?2")
	Set<Object[]> getTimings(Long orgId, String shiftCode);

	@Query(nativeQuery = true, value = "SELECT primaryunit FROM item A WHERE A.orgid=?1 and A.itemname=?2")
	Set<Object[]> getUnitforJobOrder(Long orgId, String partNo);

	@Query(nativeQuery = true, value = "SELECT A.docid,a.wono, b.docdate as wonodate,A.customername,b.customerpono ,A.fgqty,fgpartname,fgpartdesc FROM routecardentry A, workorder B,partymaster D\r\n"
			+ "WHERE A.wono=B.docid and a.customername=b.customername\r\n"
			+ "AND a.orgid=?1 GROUP BY A.docid,a.wono, b.docdate,A.customername,b.customerpono ,A.fgqty,fgpartname,fgpartdesc,A.suppliername")
	Set<Object[]> getRouteCardNoAndDetailsforJobOrder(Long orgId);

	// Report Query

	@Query(nativeQuery = true, value = "SELECT \r\n"
			+ "    jo.joborderid,\r\n"
			+ "    jo.docid,\r\n"
			+ "    jo.docdate,\r\n"
			+ "    jo.customername,\r\n"
			+ "    jo.customerpono,\r\n"
			+ "    jo.workorderno,\r\n"
			+ "    jo.routecardno,\r\n"
			+ "    jo.partno,\r\n"
			+ "    jo.partname,\r\n"
			+ "    jo.shift,\r\n"
			+ "    jo.operationname,\r\n"
			+ "    jo.operatorname,\r\n"
			+ "    jo.cycletimeinsecs,\r\n"
			+ "    jo.normshr,\r\n"
			+ "    jo.productionqty,\r\n"
			+ "    jo.status,\r\n"
			+ "    jod.timeinhours,\r\n"
			+ "    jod.unit,\r\n"
			+ "    jod.hoursproduction,\r\n"
			+ "    jod.rework,\r\n"
			+ "    jod.reject,\r\n"
			+ "    jod.idealtime,\r\n"
			+ "    jod.cumulativetest,\r\n"
			+ "    jod.remarks\r\n"
			+ "FROM joborder jo\r\n"
			+ " JOIN joborderdetails jod \r\n"
			+ "    ON jo.joborderid = jod.joborderid\r\n"
			+ "WHERE jo.orgid = ?1\r\n"
			+ " and ( ?2 is null or jo.docdate >= ?2 ) and   ( ?3 is null or jo.docdate <= ?3 )\r\n"
			+ "  AND jo.status = ?4 and (jo.routecardno=?5 or ?5='ALL')")
	Set<Object[]> getJobOrderReport(Long orgId, String fromDate, String toDate, String status,String routeCardNo);

}
