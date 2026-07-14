package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesInvoiceExportVO;

@Repository
public interface SalesInvoiceExportRepo extends JpaRepository<SalesInvoiceExportVO, Long> {

	@Query(nativeQuery = true, value = "select * from salesinvoiceexport  where salesinvoiceexportid=?1")
	SalesInvoiceExportVO getSalesInvoiceExportById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getSalesInvoiceExportDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select * from salesinvoiceexport where orgid=?1 and finyear=?2 and branchcode=?3 ")
	List<SalesInvoiceExportVO> getAllSalesInvoiceExport(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select a.partyname,a.currency,a1.city,\n"
			+ "						concat(a1.addressline1,' ',a1.addressline2,' ',a1.addressline3,' ',a1.city,' ',a1.pincode)as address,d.sellingexrate from \n"
			+ "						partymaster a join partyaddress a1 on a.partymasterid=a1.partymasterid   join partycurrencymapping a2 on a2.partymasterid=a.partymasterid join dailymonthlyexratesdtl d on d.currency=a2.transcurrency  where   a.active = 1 and \n"
			+ "						a1.addresstype='BILLING' and a.partytype='CUSTOMER' and a.country not in ('INDIA') and  a.orgid=?1\n"
			+ "                        and a.partyname=?2 group by \n"
			+ "						a.partyname,a.currency,a1.city, address,d.sellingexrate order by a.partyname")
	Set<Object[]> findByCustomerNameFromPartyMasterSalesInvoiceExport(Long orgId,String partyName);

	@Query(nativeQuery = true, value = "select concat(a1.addressline1,' ',a1.addressline2,' ',a1.addressline3,' ',a1.city,' ',a1.pincode)as address from \r\n"
			+ "partymaster a,partyaddress a1 where a.partymasterid=a1.partymasterid and a.active = 1 and a.country not in ('INDIA') and \r\n"
			+ "	a1.addresstype='SHIPPING' and a.partytype='CUSTOMER' and a.orgid=?1 and a.partyname=?2 group by address")
	Set<Object[]> findByShippingFromPartySalesInvoiceExp(Long orgId, String customerName);

	@Query(nativeQuery = true, value = "select a.docid from sales a where a.orgid=?1 and a.customername=?2 group by a.docid")
	Set<Object[]> getSalesOrderNumber(Long orgId, String customerName);

	@Query(nativeQuery = true, value = "select a.docid from exportpackinglist a  join salesinvoiceexport b on a.docid=b.exportpackingno\r\n"
			+ "where a.orgid=?1 and a.customername=?2 and\r\n"
			+ "a.salesorderno=?3 and a.status='PENDING'\r\n"
			+ " and not exists(\r\n"
			+ " select 1\r\n"
			+ " FROM salesinvoiceexport b\r\n"
			+ "        WHERE b.exportpackingno = a.docid\r\n"
			+ "        )\r\n"
			+ "group by a.docid;\r\n")
	Set<Object[]> getexportpackinglistNumber(Long orgId, String customerName, String salesOrderNo);

	@Query(nativeQuery = true, value = "select a1.partno,a1.partdesc,a1.unit,a1.quantity,a1.price from\r\n"
			+ " exportpackinglist a,exportpackinglistdetails a1 where a.exportpackinglistid=a1.exportpackinglistid and \r\n"
			+ " a.orgid=?1 and a.salesorderno=?2 and a.docid=?3 and a.status='PENDING' group by  a1.partno,a1.partdesc,\r\n"
			+ " a1.unit,a1.quantity,a1.price order by a1.partno")
	Set<Object[]> getPartNoFromexportpackinglist(Long orgId, String salesOrderNo, String exportPackingListNo);

	@Query(nativeQuery = true, value = "SELECT\n"
			+ "a.salesinvoiceexportid,\n"
			+ "    a.docid,\n"
			+ "    a.docdate,\n"
			+ "    a.billingaddress,\n"
			+ "    a.currency,\n"
			+ "    a.customername,\n"
			+ "    a.exchangerate,\n"
			+ "    a.location,\n"
			+ "    a.salesorderno,\n"
			+ "    a.exportpackingno,\n"
			+ "    b.item,\n"
			+ "    b.itemdesc,\n"
			+ "    b.rate,\n"
			+ "    b.qty,\n"
			+ "    b.grossamount,\n"
			+ "    b.discount,\n"
			+ "    b.discountamount,\n"
			+ "    b.netamount\n"
			+ "FROM salesinvoiceexport a,\n"
			+ "     salesinvoiceexportdetails b\n"
			+ "WHERE a.salesinvoiceexportid = b.salesinvoiceexportid\n"
			+ "  AND a.orgid = ?1\n"
			+ "  AND (a.customername = ?2 OR ?2 = 'ALL')\n"
			+ "  AND (?3 IS NULL OR a.docdate >= ?3)\n"
			+ "  AND (?4 IS NULL OR a.docdate <= ?4)\n"
			+ "  AND (a.branchcode = ?5 OR ?5 = 'ALL')\n"
			+ "ORDER BY a.createdon DESC")
	Set<Object[]> getSalesInvoiceExportDetails(Long orgId, String customerName,String fromDate,String toDate,String branchCode);
	
	@Query(nativeQuery = true, value = "SELECT\n"
			+ "a.salesinvoiceexportid,\n"
			+ "    a.docid,\n"
			+ "    a.docdate,\n"
			+ "    a.billingaddress,\n"
			+ "    a.currency,\n"
			+ "    a.exchangerate,\n"
			+ "    a.customername,\n"
			+ "    a.exportpackingno,\n"
			+ "    a.location,\n"
			+ "    a.salesorderno,\n"
			+ "    a.totalqty,\n"
			+ "    a.totalgrossamount,\n"
			+ "    a.totaldiscountamount,\n"
			+ "    a.totalamount\n"
			+ "FROM salesinvoiceexport a \n"
			+ "WHERE a.orgid = ?1\n"
			+ "  AND (a.customername = ?2 OR ?2 = 'ALL')\n"
			+ "  AND (?3 IS NULL OR a.docdate >= ?3)\n"
			+ "  AND (?4 IS NULL OR a.docdate <= ?4)\n"
			+ "  AND (a.branchcode = ?5 OR ?5 = 'ALL')\n"
			+ "ORDER BY a.createdon DESC")
	Set<Object[]> getSalesInvoiceExportSummaryDetails(Long orgId, String customerName,String fromDate,String toDate,String branchCode);

}
