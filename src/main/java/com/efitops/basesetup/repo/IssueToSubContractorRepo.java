package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.IssueToSubContractorVO;

@Repository
public interface IssueToSubContractorRepo extends JpaRepository<IssueToSubContractorVO, Long> {

	@Query(nativeQuery = true, value = "select * from  issuetosubcontractor  where orgid=?1")
	List<IssueToSubContractorVO> getAllIssueToSubContractorByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from issuetosubcontractor where issuetosubcontractorid=?1 ")
	List<IssueToSubContractorVO> getIssueToSubContractorById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getIssueToSubContractorDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select a.docid,a.customername,a.fgpartname,a.fgpartdesc,a.fgqty,a.wono from routecardentry a where a.orgid=?1  and a.status in('PENDING','IN-COMPLETE') group by \r\n"
			+ "			a.docid,a.customername,a.fgpartname,a.fgpartdesc,a.fgqty,a.wono\r\n"
			+ "			 order by  a.docid")
	Set<Object[]> getRouteCardNoAndItemNo(Long orgId);

	@Query(nativeQuery = true, value = "select departmentname from department where orgid=?1 and active=true group by departmentname")
	Set<Object[]> getDepartmentName(Long orgId);

	@Query(nativeQuery = true, value = "select a1.processname from itemwiseprocess a,itemwiseprocessdetails a1 where a.orgid=?1 and a.item=?2\r\n"
			+ "		 and a.processtype='SUB-CONTRACT' and \r\n"
			+ "		a.itemwiseprocessid=a1.itemwiseprocessid group by a1.processname order by a1.processname")
	Set<Object[]> getProcessNameFormItemWiseProcess(Long orgId, String item);

	@Query(nativeQuery = true, value = "select docid,docdate,department from issuetosubcontractor where orgid=?1 and finyear=?2 and branchcode=?3 and routecardno=?4 and  active=1")
	Set<Object[]> getIssueNoForReceiveFromSubContractor(Long orgId, String finYear, String branchCode,
			String routeCardNo);

	@Query(nativeQuery = true, value = "Select jobworkorderno, dcno,contractorcode,contractorname from jobworkout  where orgid=?1 and finyear=?2 and branchcode= ?3 and routecardno= ?4 and  active=1")
	Set<Object[]> getJobWorkOutOrderNoForReceiveFromSubContractor(Long orgId, String finYear, String branchCode,
			String routeCardNo);

	@Query(nativeQuery = true, value = "SELECT b.item, b.itemdescription, b.quantity FROM issuetosubcontractor a  \r\n"
			+ "JOIN issuetosubcontractdetails b ON a.issuetosubcontractorid = b.issuetosubcontractorid  \r\n"
			+ "LEFT JOIN recievefromsubcontract r ON r.issueno = a.docid  \r\n"
			+ "LEFT JOIN recievefromsubcontractdetails r1 ON r.recievefromsubcontractid = r1.recievefromsubcontractid  \r\n"
			+ "WHERE a.orgid =?1 AND a.finyear =?2 AND a.branchcode = ?3 AND a.routecardno = ?4  \r\n"
			+ "AND a.docid = ?5 AND a.active = 1  \r\n"
			+ "GROUP BY b.item, b.itemdescription, b.quantity  \r\n"
			+ "HAVING b.quantity - SUM(IFNULL(r1.receviedqty, 0)) > 0;")
	
	Set<Object[]> getPartNameAndPartDescForReceiveFromSubContractor(Long orgId, String finYear, String branchCode,
			String routeCardNo, String issueNo);

	@Query(nativeQuery = true, value = "SELECT\r\n"
			+ "    dcs.dcforsubcontractid,\r\n"
			+ "    dcs.docid,\r\n"
			+ "    dcs.docdate,\r\n"
			+ "    dcs.scissueno,\r\n"
			+ "    dcs.customername,\r\n"
			+ "    dcs.subcontractorname,\r\n"
			+ "    dcs.routecardno,\r\n"
			+ "    dcs.vehicleno,\r\n"
			+ "    dcs.duedate,\r\n"
			+ "    dcd.item,\r\n"
			+ "    dcd.itemdesc,\r\n"
			+ "    dcd.process,\r\n"
			+ "    dcd.qty,\r\n"
			+ "    dcd.unit,\r\n"
			+ "    dcd.weight,\r\n"
			+ "    dcd.remarks\r\n"
			+ "FROM dcforsubcontract dcs\r\n"
			+ " JOIN dcforsubcontractdetail dcd\r\n"
			+ "    ON dcs.dcforsubcontractid = dcd.dcforsubcontractid\r\n"
			+ "WHERE dcs.orgid = ?1\r\n"
			+ "AND  ( ?2 is null or dcs.docdate >= ?2 ) and \r\n"
			+ "  ( ?3 is null or dcs.docdate <= ?3 ) and (dcs.routecardno=?4 or ?4='ALL')")
	Set<Object[]> getDeliveryChallanSubContractorReport(Long orgId, String fromDate, String toDate,String routeCardNo);

	@Query(nativeQuery = true, value = "SELECT\r\n"
			+ "    dcs.dcforsubcontractid,\r\n"
			+ "    dcs.docid,\r\n"
			+ "    dcs.docdate,\r\n"
			+ "    dcs.scissueno,\r\n"
			+ "    dcs.routecardno,\r\n"
			+ "    dcs.customername,\r\n"
			+ "    dcs.subcontractorname,\r\n"
			+ "    dcs.subcontractoraddress,\r\n"
			+ "    dcs.vehicleno,\r\n"
			+ "    dcs.dispatchthrough,\r\n"
			+ "    dcd.item,\r\n"
			+ "    dcd.itemdesc,\r\n"
			+ "    dcd.process,\r\n"
			+ "    dcd.qty,\r\n"
			+ "    dcd.unit,\r\n"
			+ "    dcd.weight,\r\n"
			+ "    dcd.remarks\r\n"
			+ "FROM dcforsubcontract dcs\r\n"
			+ " JOIN  dcforsubcontractdetail dcd\r\n"
			+ "    ON dcs.dcforsubcontractid = dcd.dcforsubcontractid\r\n"
			+ "WHERE dcs.orgid = ?1\r\n"
			+ "  AND ( ?2 is null or dcs.docdate >= ?2 ) and   ( ?3 is null or dcs.docdate <= ?3 ) and (dcs.routecardno=?4 or ?4='ALL')")
	Set<Object[]> getSubContractorInvoiceReport(Long orgId, String fromDate, String toDate,String routeCardNo);

	@Query(nativeQuery = true, value = "select i.orgid,i.docid,i.docdate,i.routecardno,i.customername,i.department,i.status,i1.item,i1.itemdescription,i1.process,i1.quantity,i.issuetosubcontractorid \n"
			+ "from issuetosubcontractor i\n"
			+ " JOIN issuetosubcontractdetails i1 on i.issuetosubcontractorid = i1.issuetosubcontractorid \n"
			+ "where i.orgid=?1 and i.status =?4  and (?2 is null or i.docdate >= ?2)  and (?3 is null or i.docdate <= ?3)   and (i.routecardno=?5 or ?5='ALL')")
	Set<Object[]> getIssueToSubContractorDetails(Long orgId, String fromdate, String todate, String status,String routeCardNo);
}
