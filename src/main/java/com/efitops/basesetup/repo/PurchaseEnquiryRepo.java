package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseEnquiryVO;

@Repository
public interface PurchaseEnquiryRepo extends JpaRepository<PurchaseEnquiryVO, Long> {

	@Query(nativeQuery = true, value = "select * from purchaseenquiry where orgid=?1 and finyear=?2 and branchCode=?3")
	List<PurchaseEnquiryVO> getPurchaseEnquiry(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from purchaseenquiry where purchaseenquiryid=?1")
	Optional<PurchaseEnquiryVO> getPurchaseEnquiryById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getPurchaseEnquiryByDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select distinct partyname,partycode from partymaster where partytype = 'SUPPLIER' and orgid=?1 and active=1 order by 1")
	Set<Object[]> findSupplierNameForPurchaseEnquiry(Long orgId);

	@Query(nativeQuery = true, value = "select distinct b.contactperson,p1.contact from partymaster a\r\n"
			+ " join partystate b ON a.partymasterid= b.partymasterid\r\n"
			+ " join partyaddress p1 on a.partymasterid=p1.partymasterid where\r\n"
			+ "  a.partytype = 'SUPPLIER' and a.orgid=?1 and a.partycode=?2  and active=1 order by 1")
	Set<Object[]> findContactPersonDetailsForPurchaseEnquiry(Long orgId, String supplierCode);

	@Query(nativeQuery = true, value = "SELECT a.docid FROM purchaseindent a  WHERE a.orgid = ?1 AND a.customercode = ?2 and workorderno=?3 AND a.active = 1 AND a.docid NOT IN (SELECT c.purchaseindentno FROM purchaseenquiry c WHERE c.orgid = ?1) ORDER BY a.docid")
	Set<Object[]> findPurchaseIndentNoForPurchaseEnquiry(Long orgId, String customerCode, String workOrderNo);

	@Query(nativeQuery = true, value = "SELECT DISTINCT b.item, b.itemdesc, b.uom, b.reqqty, b.indentqty "
			+ "FROM purchaseindent a " + "JOIN purchaseindentdetails b ON a.purchaseindentid = b.purchaseindentid "
			+ "WHERE a.orgid = ?1 AND a.docid = ?2 AND a.active = 1 " +

			"UNION " +

			"(SELECT d.itemcode, d.itemdesc, d.uom, 0 AS reqqty, 0 AS indentqty " + "FROM bom c "
			+ "JOIN bomdetails d ON c.bomid = d.bomid " + "WHERE c.orgid = ?1 AND ?2 = 'Null' AND c.productcode = ?3) "
			+

			"ORDER BY 1")
	Set<Object[]> findItemDetailsForPurchaseEnquiry(Long orgId, String purchaseIndentNo, String fgItem);

	@Query(nativeQuery = true, value = "SELECT DISTINCT a.docid ,b.partno, b.partname, b.requiredqty, a.customerpono FROM workorder a JOIN workorderdetails b ON a.workorderid = b.workorderid  WHERE a.orgid = ?1 and a.status='PENDING'  AND a.customercode = ?2 AND a.active = 1  ORDER BY a.docid")
	Set<Object[]> findWorkOrderNoForPurchaseEnquiry(Long orgId, String customerCode);

	@Query(nativeQuery = true, value = "select p.docid,p.docdate,p.customername,p.customercode,p.customerpono,p.fgpartname,p.fgpartdesc,p.suppliername,p.suppliercode,p.workorderno,\n"
			+ "			 p.contactno,p.contactperson,p1.item,p1.itemdesc,p1.qtyrequired, p.purchaseenquiryid,case \n"
			+ "        when p2.docid IS NOT NULL THEN 'Completed'\n"
			+ "        ELSE 'Pending'\n"
			+ "    END AS status,p.enquiryduedate,p2.purchasequotationid 	  from purchaseenquiry p join purchaseenquirydetails p1 \n"
			+ "			 on p.purchaseenquiryid=p1.purchaseenquiryid left join purchasequotation p2 on p.docid=p2.enquiryno \n"
			+ "             where p.orgid=?1\n"
			+ "             and p.branchcode=?2\n"
			+ "			 and  (p.suppliername=?3 or ?3='ALL')\n"
			+ "             AND (\n"
			+ "        ?4 = 'ALL'\n"
			+ "     OR (?4 = 'Completed' AND p2.docid IS NOT NULL)\n"
			+ "     OR (?4 = 'Pending'   AND p2.docid IS NULL)\n"
			+ "    )\n"
			+ "             and   (?5 IS NULL OR p.docdate >= ?5)\n"
			+ "			    AND (?6 IS NULL OR p.docdate <= ?6)\n"
			+ "    group by p.docid,p.docdate,p.customername,p.customercode,p.customerpono,p.fgpartname,p.fgpartdesc,p.suppliername,p.suppliercode,p.workorderno,\n"
			+ "			 p.contactno,p.contactperson,p1.item,p1.itemdesc,p1.qtyrequired,p.purchaseenquiryid,status,p.enquiryduedate,p2.purchasequotationid")
	Set<Object[]> getPurchaseEnquiryReport(Long orgId, String branchCode,String supplierName,String status,String fromDate,String toDate);

//	@Query(nativeQuery=true,value ="SELECT DISTINCT  b.partno, b.partname, b.requiredqty, a.customerpono FROM workorder a JOIN workorderdetails b ON a.workorderid = b.workorderid WHERE a.orgid = ?1 AND a.docid = ?2 AND a.active = 1  ORDER BY b.partno" )
//	Set<Object[]> findWorkOrderDetailsForPurchaseEnquiry(Long orgId, String workOrderNo);

}
