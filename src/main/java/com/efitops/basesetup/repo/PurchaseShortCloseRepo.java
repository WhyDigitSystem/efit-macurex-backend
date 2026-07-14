package com.efitops.basesetup.repo;

import java.util.List;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseShortCloseVO;

@Repository
public interface PurchaseShortCloseRepo extends JpaRepository<PurchaseShortCloseVO, Long> {

	@Query(nativeQuery = true, value = "select * from purchaseshortclose where orgid=?1 and finyear=?2 and branchcode=?3")
	List<PurchaseShortCloseVO> getPurchaseShortCloseByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select*from purchaseshortclose where purchaseshortcloseid=?1")
	PurchaseShortCloseVO getPurchaseShortCloseById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getPurchaseShortCloseDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select docid,podate,customername,customercode,suppliername,suppliercode,country,address,contactperson,city,state\r\n"
			+ " from purchaseorder where orgid=?1 and docid=?2  and active=1 and cancel=0")
	Set<Object[]> getPurchaseOrderDetails(Long orgId, String poNo);

	@Query(nativeQuery = true, value = "select p1.item,p1.itemdesc,p1.uom,p1.qty,p1.price,sum(g1.acceptqty) as acceptqty  from \r\n"
			+ "			purchaseorder p join purchaseorderdetails p1 on p.purchaseorderid=p1.purchaseorderid\r\n"
			+ "            left join grn g on g.pono=p.docid join grndetails g1 on g.grnid=g1.grnid and g1.itemcode=p1.item\r\n"
			+ "            where p.orgid=?1\r\n"
			+ "            and p.branchcode=?2 and p.docid=?3 and p.active=1 and p.cancel=0\r\n"
			+ "             group by  p1.item,p1.itemdesc,p1.uom,p1.qty,p1.price")
	Set<Object[]> getItemDetailsFromPurchaseOrderDetails(Long orgId, String branchCode, String poNo);

	PurchaseShortCloseVO findByOrgIdAndIdAndDocId(Long orgId, Long id, String docId);

	@Query(nativeQuery = true, value = "select docid\r\n"
			+ " from purchaseorder where orgid=?1  and active=1 and cancel=0")
	Set<Object[]> getPurchaseOrderDocId(Long orgId);
	
	@Query(nativeQuery = true, value = "select p.address,p.contactperson,p.country,p.customername,p.customercode,p.docid,p.docdate,p.ponumber,p.purchasecloseddate,p.suppliercode,p.suppliername, \r\n"
			+ "p1.item,p1.itemdesc,p1.qty,p1.rate,p1.receivedqty,p1.shortageqty,p1.uom,p1.amount from purchaseshortclose p join purchaseshortclosedetails p1 on p.purchaseshortcloseid=p1.purchaseshortcloseid where\r\n"
			+ "p.orgid=?1 and p.branchcode=?2  and \r\n"
			+ " (?3 IS NULL OR p.docdate >= ?3)  and  (?4 IS NULL OR p.docdate <= ?4)\r\n"
			+ "group by  p.address,p.contactperson,p.country,p.customername,p.customercode,p.docid,p.docdate,p.ponumber,p.purchasecloseddate,p.suppliercode,p.suppliername, \r\n"
			+ "p1.item,p1.itemdesc,p1.qty,p1.rate,p1.receivedqty,p1.shortageqty,p1.uom,p1.amount")
	Set<Object[]> getPurchaseShortCloseReport(Long orgId,String branchCode,String fromDate,String toDate);
	
}
