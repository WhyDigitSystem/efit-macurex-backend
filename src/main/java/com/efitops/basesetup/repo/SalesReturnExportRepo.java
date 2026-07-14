package com.efitops.basesetup.repo;

import java.util.List;

import java.util.Set;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesReturnExportVO;

@Repository
public interface SalesReturnExportRepo extends JpaRepository<SalesReturnExportVO, Long> {

	@Query(nativeQuery = true, value = "select * from  salesreturnexport where orgid=?1 and finyear=?2 and branchcode=?3")
	List<SalesReturnExportVO> getAllSalesReturnExportByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from salesreturnexport  where salesreturnexportid=?1")
	SalesReturnExportVO getSalesReturnExportById(Long id);
	
	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getSalesReturnExportDocId(Long orgId, String finYear, String branchCode, String screenCode);


	@Query(nativeQuery = true, value = "select a.partyname from partymaster a where\r\n"
			+ "  a.orgid=?1 and upper(a.partytype='CUSTOMER') and a.active = 1 and a.country  not in  ('INDIA')\r\n"
			+ "  group by a.partyname order by a.partyname")
	Set<Object[]> getCustomerNameFromPartyMasterhExport(Long orgId);

	@Query(nativeQuery = true, value = "select a.docid,a.docdate,a.salesorderno,a.exportpackingno,a.currency,\r\n"
			+ "  a.exchangerate,a.location,a.billingaddress from salesinvoiceexport a \r\n"
			+ "  where a.orgid=?1 and a.customername=?2 group by a.docid,a.docdate,a.salesorderno,a.exportpackingno,a.currency,\r\n"
			+ "  a.exchangerate,a.location,a.billingaddress order by a.docid")
	Set<Object[]> getDocIdFromSalesInvoiceExport(Long orgId, String customerName);

	@Query(nativeQuery = true, value = "select a1.item,a1.itemdesc,a1.units,a1.qty,a1.rate from\r\n"
			+ "  salesinvoiceexport a, salesinvoiceexportdetails a1 where a.salesinvoiceexportid=a1.salesinvoiceexportid and \r\n"
			+ "  a.orgid=?1 and a.customername=?2 and a.docid=?3 group by a1.item,a1.itemdesc,a1.units,a1.qty,a1.rate\r\n"
			+ "  order by a1.item")
	Set<Object[]> getItemFromSalesInvoiceExport(Long orgId, String customerName, String salesInvoiceExportNo);


}
