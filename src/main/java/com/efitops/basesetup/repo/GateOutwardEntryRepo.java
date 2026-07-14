package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.GateOutwardEntryVO;

@Repository
public interface GateOutwardEntryRepo extends JpaRepository<GateOutwardEntryVO, Long> {

	@Query(nativeQuery = true, value = "select * from gateoutwardentry where orgid=?1 and finyear=?2 and branchcode=?3")
	List<GateOutwardEntryVO> getAllGateOutwardEntryByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from gateoutwardentry where gateoutwardentryid=?1")
	GateOutwardEntryVO getGateOutwardEntryById(Long id);

	@Query(nativeQuery = true, value = "select  a.partyname,a.partycode from partymaster a where  a.partytype='CUSTOMER' and  a.orgid=?1 and active=1 and cancel=0 group by \r\n"
			+ " a.partyname,a.partycode order by  a.partyname")
	Set<Object[]> findCustomerNameAndCodeFromGateOutwardEntry(Long orgId);

	@Query(nativeQuery = true, value = "SELECT docid, docdate FROM (SELECT a.docid,  a.docdate FROM  dcforsubcontract a  "
			+ "JOIN partymaster b  ON b.partyname = a.customername   "
			+ "WHERE  a.orgid=?1 and a.customername =?2 AND 'Sub Contract' =?3"
			+ " UNION  SELECT  a.docid,  a.docdate FROM   deliverychalanforfg a  "
			+ "JOIN  partymaster b  ON b.partyname = a.customername   WHERE   a.orgid=?1 "
			+ "and a.customername =?2 AND 'Finished Goods' =?3 ) AS combinedORDER BY docid")
	Set<Object[]> findDeliveryChallanNoForGateOutwardEntry(Long orgId, String customerName, String type);

	@Query(nativeQuery = true, value = "select a.docid,a.docdate , wono from \r\n"
			+ "(select a.docid,a.docdate, ' ' as wono\r\n" + "from subcontractinvoice a \r\n"
			+ "join dcforsubcontract b on b.docid = a.dcno\r\n" + "where  'Sub-Contract' = ?2 and a.dcno = ?1 \r\n"
			+ "union \r\n" + "select a.docid,a.docdate,wono\r\n" + "from salesinvoicelocal a \r\n"
			+ "join partymaster b on b.partyname = a.customername \r\n"
			+ "join deliverychalanforfg c on c.customername = b.partyname \r\n"
			+ "where c.docid = ?1 and 'Finished Goods' = ?2\r\n" + "union \r\n" + "select a.docid,a.docdate,wono\r\n"
			+ "from salesinvoiceexport a  \r\n" + "join partymaster b on b.partyname = a.customername \r\n"
			+ "join deliverychalanforfg c on c.customername = b.partyname  \r\n"
			+ "where c.docid = ?1 and 'Finished Goods' = ?2   \r\n" + ") a\r\n" + "order by docid ")
	Set<Object[]> findInvoiceNoForGateOutwardEntry(Long orgId, String dcNo, String type);

	@Query(nativeQuery = true, value = "select c.itemname,c.itemdesc,a.quantitynos ,c.primaryunit  from subcontractinvoicedetails a, subcontractinvoice b,item c\r\n"
			+ "where b.subcontractinvoiceid =a.subcontractinvoiceid \r\n" + "and c.itemname =a.partno \r\n"
			+ "and b.orgid=?1 and b.docid=?2\r\n" + "union\r\n"
			+ "select c.itemname,c.itemdesc,a.qty,c.primaryunit from salesinvoicelocaldetails a, salesinvoicelocal b ,item c\r\n"
			+ "where b.salesinvoicelocalid =a.salesinvoicelocalid \r\n" + "and c.itemname =a.item\r\n"
			+ "and b.orgid=?1 and b.docid=?2\r\n" + "union\r\n"
			+ "select c.itemname,c.itemdesc,a.qty,c.primaryunit from salesinvoiceexportdetails a, salesinvoiceexport b,item c\r\n"
			+ "where a.salesinvoiceexportid =b.salesinvoiceexportid \r\n" + "and a.item =c.itemname \r\n"
			+ "and b.orgid=?1 and b.docid=?2 ")
	Set<Object[]> findItemDetailsForGateOutwardEntry(Long orgId, String invNo);
	
	
	

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getGateOutwardEntryDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select employeename,employeecode from employeemaster where orgid=?1 and branchcode=?2 and active=1 and cancel=0")
	Set<Object[]> getEmployeeNameDetails(Long orgId, String branchCode);
	
	@Query(nativeQuery = true, value = "select d.docid,d.docdate from dcforsubcontract d where d.orgid=?1  and d.branchcode=?2 and ?3='SUBCONTRACT'  and not exists  (select g.deliverychallanno from gateoutwardentry g where g.deliverychallanno=d.docid)  and d.active=1  and d.cancel=0\n"
			+ "union \n"
			+ "select s.docid,s.docdate from sales s where s.orgid=?1  and s.branchcode=?2 and ?3='SALES'   and not exists  (select g.deliverychallanno from gateoutwardentry g where g.deliverychallanno=s.docid)  and active=1  and cancel=0")
	Set<Object[]> getDeliveryChallanDetails(Long orgId, String branchCode,String type);
	
	@Query(nativeQuery = true, value = "select s.docid,s.docdate,s1.quantitynos,s1.partno,s1.partdes,s1.units from subcontractinvoice s join subcontractinvoicedetails s1\n"
			+ "			 on s.subcontractinvoiceid=s1.subcontractinvoiceid where s.orgid=?1  and s.branchcode=?2 and  s.dcno=?3\n"
			+ "			 union\n"
			+ "			select s.docid,s.docdate,s1.qty,s1.item,s1.itemdesc,s1.units from salesinvoicelocal s join salesinvoicelocaldetails s1\n"
			+ "			 on s.salesinvoicelocalid=s1.salesinvoicelocalid where s.orgid=?1 and s.branchcode=?2  and  s.salesorderno=?3")
	Set<Object[]> getInvoiceDetails(Long orgId, String branchCode,String deliveryChallanNo);

	@Query(nativeQuery = true, value = "SELECT\r\n"
			+ "    gateoutwardentryid,\r\n"
			+ "    customerno,\r\n"
			+ "    deliverychallandate,\r\n"
			+ "    deliverychallanno,\r\n"
			+ "    invoicedate,\r\n"
			+ "    invoiceno,\r\n"
			+ "    modeofshipment,\r\n"
			+ "    narration,\r\n"
			+ "    orgid,\r\n"
			+ "    type,\r\n"
			+ "    vehicleno,\r\n"
			+ "    branch,\r\n"
			+ "    branchcode,\r\n"
			+ "    finyear,\r\n"
			+ "    docdate,\r\n"
			+ "    docid,\r\n"
			+ "    customername\r\n"
			+ "FROM gateoutwardentry\r\n"
			+ "WHERE orgid = ?1\r\n"
			+ "  AND docdate BETWEEN ?2 AND ?3\r\n"
			+ "  AND active = 1\r\n"
			+ "  AND cancel = 0")
	Set<Object[]> getGateOutwardEntryReport(Long orgId, String fromDate, String toDate);
}
