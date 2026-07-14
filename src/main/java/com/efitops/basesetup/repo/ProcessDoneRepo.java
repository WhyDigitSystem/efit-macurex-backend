package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.ProcessDoneVO;

public interface ProcessDoneRepo extends JpaRepository<ProcessDoneVO, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM processdone WHERE orgid=?1")
	List<ProcessDoneVO> getAllProcessDoneByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "SELECT * FROM processdone WHERE processdoneid=?1")
	List<ProcessDoneVO> getAllProcessDoneById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getProcessDoneDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "select a.docid,a.fgpartname,a.fgpartdesc,a.fgqty from  routecardentry \r\n"
			+ "a WHERE a.orgid=?1 and a.customername=?2  and a.status='PENDING' group by  a.docid,a.fgpartname,a.fgpartdesc,a.fgqty")
	Set<Object[]> getRouteCardNo(Long orgId, String customerName);

	@Query(nativeQuery = true, value = "select a.docid from joborder a where a.orgid=?1 and a.routecardno=?2 ")
	Set<String> getJobCardNo(Long orgId, String routeCardNo);

	@Query(nativeQuery = true, value = "SELECT  A.departmentname FROM department A WHERE A.orgid =?1 ORDER BY A.departmentname;")
	Set<Object[]> getFrom(Long orgId);

	@Query(nativeQuery = true, value = "SELECT  A.departmentname  FROM department A WHERE A.orgid=?1 ORDER BY A.departmentname;")
	Set<Object[]> getTo(Long orgId);

	@Query(nativeQuery = true, value = "SELECT DISTINCT B.processname FROM itemwiseprocess A,itemwiseprocessdetails B WHERE A.itemwiseprocessid=B.itemwiseprocessid AND A.processtype='PRODUCTION' AND A.orgid=?1 AND A.item=?2")
	Set<Object[]> getProcessFromItemMaster(Long orgId, String fgPartNo);

	@Query(nativeQuery = true, value = "SELECT pd.processdoneid, pd.docid, pd.docdate,\r\n"
			+ "			 pd.customername, pd.routecardno, pd.joborderno, pd.fgpartname, pd.fgpartno,\r\n"
			+ "			 pd.fromfield, pd.tofield, pd.placinglocation, pd.qty, pdd.process,\r\n"
			+ "			 pdd.status, pdd.remarks FROM processdone pd  JOIN processdonedetails pdd ON pd.processdoneid = pdd.processdoneid\r\n"
			+ "			WHERE pd.orgid =?1 AND\r\n"
			+ "            (?2 is null or pd.docdate >= ?2 ) and \r\n"
			+ "            (?3 is null or pd.docdate <= ?3 ) \r\n"
			+ "            and  (pd.routecardno = ?4  or ?4='ALL' )")
	Set<Object[]> getProcessDoneReport(Long orgId, String fromDate, String toDate, String routeCardEntry);
}
