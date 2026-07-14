package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesReturnLocalVO;

@Repository
public interface SalesReturnLocalRepo extends JpaRepository<SalesReturnLocalVO, Long> {

	@Query(nativeQuery = true, value = "select * from  salesreturnlocal where orgid=?1 and finyear=?2 and branchcode=?3")
	List<SalesReturnLocalVO> getAllSalesReturnLocalByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from salesreturnlocal  where salesreturnlocalid=?1")
	SalesReturnLocalVO getSalesReturnLocalById(Long id);
	
	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getSalesReturnLocalDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select a.docid,a.docdate,a.billingaddress,a.currency,\r\n"
			+ "	a.customername,a.exchangerate,\r\n"
			+ "	a.location,a.salesorderno,a.taxtype,a.packinglistno from\r\n"
			+ "	 salesinvoicelocal a where a.orgid=?1 and \r\n"
			+ "	a.customername=?2  group by a.docid,a.docdate,a.billingaddress,a.currency,\r\n"
			+ "	a.customername,a.exchangerate,\r\n"
			+ "	a.location,a.salesorderno,a.taxtype,a.packinglistno order by a.docid")
	Set<Object[]> getSalesInvoiceNoFromSalesInvoice(Long orgId, String customerName);
	
	@Query(nativeQuery = true, value = "select a1.item,a1.itemdesc,a1.rate,a1.qty,a1.taxcode,a1.units from\r\n"
			+ "	 salesinvoicelocal a,salesinvoicelocaldetails a1 where a.orgid=?1  and a.customername=?2 and \r\n"
			+ "	 a.docid=?3 and a.salesinvoicelocalid=a1.salesinvoicelocalid group by \r\n"
			+ "	 a1.item,a1.itemdesc,a1.rate,a1.qty,a1.taxcode,a1.units order by a1.item")
	Set<Object[]> getItemFromSalesInvoice(Long orgId, String customerName,String salesInvoiceLocalNo);
	
	
	
}
