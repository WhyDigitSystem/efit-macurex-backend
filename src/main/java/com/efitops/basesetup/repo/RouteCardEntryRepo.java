package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.RouteCardEntryVO;

@Repository
public interface RouteCardEntryRepo extends JpaRepository<RouteCardEntryVO, Long> {

	@Query(nativeQuery = true, value = "select * from routecardentry where orgid=?1 and finyear=?2 and branchcode=?3")
	List<RouteCardEntryVO> findRouteCardEntryByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from routecardentry where routecardentryid=?1")
	List<RouteCardEntryVO> findRouteCardEntryById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getRouteCardEntryDocId(Long orgId, String finYear, String branchCode, String screenCode);

	// Dropdown api
	@Query(nativeQuery = true, value = "select  a.partyname,a.partycode from partymaster a where  a.partytype='CUSTOMER' and  a.orgid=?1 and active=1 and cancel=0 group by \r\n"
			+ " a.partyname,a.partycode order by  a.partyname")
	Set<Object[]> findCustomerNameAndCodeFromRouteCardEntry(Long orgId);

	@Query(nativeQuery = true, value = "select  a.docid from workorder a where a.orgid=?1 and a.customercode=?2 and active=1 and cancel=0 group by \r\n"
			+ " a.docid order by  a.docid")
	Set<Object[]> findWorkOrderNoFromRouteCardEntry(Long orgId, String customerCode);

	@Query(nativeQuery = true, value = "select  b.partno,b.partname,b.ordqty from workorder a JOIN workorderdetails b ON a.workorderid = b.workorderid where a.orgid=?1 and a.docid=?2 and active=1 and cancel=0 group by \r\n"
			+ " b.partno,b.partname,b.ordqty order by   b.partno")
	Set<Object[]> findFgPartNameAndDescAndQtyFromRouteCardEntry(Long orgId, String workOrderNo);

	@Query(nativeQuery = true, value = "select e.employeename from employeemaster e join employeedetails ed on e.employeemasterid=ed.employeemasterid where e.orgid = ?1 and ed.designation='OPERATOR' and e.active=1 and e.cancel=0 group by\r\n"
			+ "			 e.employeename order by  e.employeename")
	Set<Object[]> findOptrSignFromRouteCardEntry(Long orgId);

	@Query(nativeQuery = true, value = "select e.employeename from employeemaster e join employeedetails ed on e.employeemasterid=ed.employeemasterid where e.orgid = ?1 and ed.designation='TEAM LEADER' and e.active=1 and e.cancel=0 group by\r\n"
			+ "			 e.employeename order by  e.employeename")
	Set<Object[]> findPreparedByFromRouteCardEntry(Long orgId);

	@Query(nativeQuery = true, value = "select e.employeename from employeemaster e join employeedetails ed on e.employeemasterid=ed.employeemasterid where e.orgid = ?1 and ed.designation='PRODUCTION MANAGER' and e.active=1 and e.cancel=0 group by\r\n"
			+ "			 e.employeename order by  e.employeename")
	Set<Object[]> findApprovedByFromRouteCardEntry(Long orgId);

	@Query(nativeQuery = true, value = "select e.employeename from employeemaster e join employeedetails ed on e.employeemasterid=ed.employeemasterid where e.orgid = ?1 and ed.designation='QUALITY MANAGER' and e.active=1 and e.cancel=0 group by\r\n"
			+ "			 e.employeename order by  e.employeename")
	Set<Object[]> findQAManagerSignFromRouteCardEntry(Long orgId);

	@Query(nativeQuery = true, value = "select e.employeename from employeemaster e join employeedetails ed on e.employeemasterid=ed.employeemasterid where e.orgid = ?1 and ed.designation='PLANT MANAGER' and e.active=1 and e.cancel=0 group by\r\n"
			+ "			 e.employeename order by  e.employeename")
	Set<Object[]> findPlantManagerSignFromRouteCardEntry(Long orgId);

	@Query(nativeQuery = true, value = "SELECT " + " rce.routecardentryid, " + " rce.docid, " + " rce.docdate, "
			+ " rce.customername, " + " rce.wono, "

			+ " rce.fgpartname, " + " rce.fgpartdesc, " + " rce.fgqty, " + " rce.batchqty, "

			+ " rce.rmtype, " + " rce.rmsize, " + " rce.rmbatchno, " + " rce.rmqty, "

			+ " rce.status " + "FROM efit_ops.routecardentry rce " + "WHERE rce.orgid = ?1 " + "AND rce.status = ?2")
	Set<Object[]> getRouteCardEntryReport(Long orgId, String status);

	RouteCardEntryVO findByDocId(String docId);

	RouteCardEntryVO getAllRouteCardEntryById(Long id);

}
