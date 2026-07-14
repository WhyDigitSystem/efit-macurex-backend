package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseQuotationVO;

@Repository
public interface PurchaseQuotationRepo extends JpaRepository<PurchaseQuotationVO, Long>{

	@Query(nativeQuery = true, value = "SELECT * FROM purchasequotation where orgid=?1 and finyear=?2 and branchcode=?3")
	List<PurchaseQuotationVO> getAllPurchaseQuotationByOrgId(Long orgId,String finYear,String branchCode);

	@Query(nativeQuery = true, value = "SELECT * FROM purchasequotation where purchasequotationid=?1")
	Optional<PurchaseQuotationVO> getAllPurchaseQuotationById(Long id);
	
	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and  finyear=?2 and branchcode=?3 and screencode=?4")
	String getPurchaseQuotationByDocId(Long orgId,String finYear,String branchCode, String screenCode);

	@Query(nativeQuery=true,value ="SELECT a.docid,a.docdate,a.suppliername,a.suppliercode FROM purchaseenquiry a  WHERE a.orgid = ?1 AND a.customercode = ?2 and a.workorderno=?3 AND a.active = 1 AND a.docid NOT IN (SELECT c.enquiryno FROM purchasequotation c WHERE c.orgid = ?1) ORDER BY a.docid" )
	Set<Object[]> findPurchaseEnquiryNoForPurchaseQuotation(Long orgId, String customerCode, String workOrderNo);

	@Query(nativeQuery=true,value ="select distinct b.item,b.itemdesc,b.unit,b.qtyrequired from purchaseenquiry a join purchaseenquirydetails b \r\n"
			+ "ON a.purchaseenquiryid=b.purchaseenquiryid where  a.orgid=?1 and a.docid=?2  and active=1 order by 1" )
	Set<Object[]> findItemDetailsForPurchaseQuotation(Long orgId, String purchaseEnquiryNo);

	@Query(nativeQuery=true,value ="SELECT DISTINCT a.docid FROM workorder a  WHERE a.orgid = ?1 AND a.customercode = ?2 and a.status='PENDING'  AND a.active = 1 ORDER BY a.docid" )
	Set<Object[]> findWorkOrderNoForPurchaseQuotation(Long orgId, String customerCode);

	@Query(nativeQuery=true,value ="SELECT DISTINCT b.partno, b.partname, b.requiredqty, a.customerpono FROM workorder a JOIN itemparticulars b ON a.workorderid = b.workorderid WHERE a.orgid = ?1 AND a.docid = ?2 AND a.active = 1 ORDER BY b.partno" )
	Set<Object[]> findWorkOrderDetailsForPurchaseQuotation(Long orgId, String customerCode);

	@Query(nativeQuery=true,value ="SELECT \r\n"
			+ "    CASE \r\n"
			+ "        WHEN p.country = 'INDIA' THEN\r\n"
			+ "            CASE \r\n"
			+ "                WHEN p1.state = b.state THEN 'SGST'\r\n"
			+ "                ELSE 'IGST'\r\n"
			+ "            END\r\n"
			+ "        ELSE 'NIL'\r\n"
			+ "    END AS TaxType \r\n"
			+ "FROM partymaster p \r\n"
			+ "JOIN partyaddress p1 \r\n"
			+ "    ON p.partymasterid = p1.partymasterid \r\n"
			+ "JOIN branch b \r\n"
			+ "    ON b.orgid = p.orgid \r\n"
			+ "   AND b.branchcode = ?2 \r\n"
			+ "WHERE p.orgid =?1 \r\n"
			+ "  AND p.partycode =?3 and p.partytype=?4 " )
	Set<Object[]> findByTaxCode(Long orgId, String branchCode, String supplierCode, String partyType);

	@Query(nativeQuery=true,value ="SELECT \r\n"
			+ "    i.itemname,\r\n"
			+ "    ip.price\r\n"
			+ "FROM item i\r\n"
			+ "JOIN itempriceslab ip \r\n"
			+ "    ON i.itemid = ip.itemid\r\n"
			+ "WHERE i.orgid = ?1 \r\n"
			+ "  AND i.itemname = ?2 \r\n"
			+ "  AND ip.priceeffectivefrom = (\r\n"
			+ "        SELECT MAX(ip2.priceeffectivefrom)\r\n"
			+ "        FROM itempriceslab ip2\r\n"
			+ "        WHERE ip2.itemid = i.itemid\r\n"
			+ "    ) \r\n"
			+ " " )
	Set<Object[]> findByUnitForPurchaseQuatation(Long orgId, String itemName);

	@Query(nativeQuery=true,value ="SELECT igstpercentage, 0 AS cgstpercentage, 0 AS sgstpercentage\r\n"
			+ "FROM gst\r\n"
			+ "WHERE orgid=?1 and  gstslab = ?2 \r\n"
			+ "  AND 'IGST' = ?3 \r\n"
			+ "\r\n"
			+ "UNION ALL\r\n"
			+ "\r\n"
			+ "SELECT 0 AS igstpercentage, cgstpercentage, sgstpercentage\r\n"
			+ "FROM gst\r\n"
			+ "WHERE orgid=?1 and gstslab = ?2 \r\n"
			+ "  AND 'SGST' = ?3 \r\n"
			+ "" )
	Set<Object[]> findByIgstAndSgstPercentageForPurchaseQrder(Long orgId, String taxType, String taxCode);
	
	@Query(nativeQuery = true, value = "select concat(o.docid, '-PQ',q.count) from purchaseenquiry o join purchasequotation q  on\n"
			+ "    q.enquiryno=o.docid  where o.orgid=?1 and o.customername=?2  and q.purchasequotationid=?3")
	String getPurchaseEnquiryNameId(Long orgId,String customerName,Long purchaseQuotationId);
	
	@Query(nativeQuery = true, value = "select count from purchasequotation q where q.orgid=?1 and q.customername=?2  and  q.purchasequotationid=?3")
	int getCount(Long orgId, String customerName,Long purchaseQuotationId);
	
	@Query(nativeQuery = true, value = "select concat(o.docid, '-PQ',1) from purchaseenquiry o   where o.orgid=?1 and o.customername=?2 and o.docid=?3")
	String getPurchaseEnquiryIdIteration(Long orgId,String customerName, String enquiryNo);

	@Query(nativeQuery = true, value = "SELECT\n"
			+ "    q.docid,\n"
			+ "    q.docdate,\n"
			+ "    q.iterations,\n"
			+ "    q.customername,\n"
			+ "    q.sourcedocid,\n"
			+ "    q.sourcedocdate,\n"
			+ "    q.kindattention,\n"
			+ "    q.sourceid,\n"
			+ "    q.partno,\n"
			+ "    q.partdesc,\n"
			+ "    q.qty,\n"
			+ "    q.sellingprice,\n"
			+ "    q.price,\n"
			+ "    q.discount,\n"
			+ "    q.discountamount,\n"
			+ "    q.amount,\n"
			+ "    q.contactno,\n"
			+ "    q.count,q.status,q.suppliername,q.suppliercode\n"
			+ "FROM quoterevision q\n"
			+ "JOIN (\n"
			+ "    SELECT\n"
			+ "        docid,\n"
			+ "        MAX(\n"
			+ "            STR_TO_DATE(modifiedon, '%d-%m-%Y %h:%i:%s %p')\n"
			+ "        ) AS max_modifiedon\n"
			+ "    FROM quoterevision\n"
			+ "    WHERE orgid = ?1\n"
			+ "      AND branchcode = ?2\n"
			+ "      and (suppliername = ?3 or ?3='ALL')\n"
			+ "      AND sourcescreencode = 'PQ'\n"
			+ "      AND (?4 IS NULL OR docdate >= ?4)\n"
			+ "      AND (?5 IS NULL OR docdate <= ?5 )\n"
			+ "    GROUP BY docid\n"
			+ ") latest\n"
			+ "  ON latest.docid = q.docid\n"
			+ " AND STR_TO_DATE(q.modifiedon, '%d-%m-%Y %h:%i:%s %p') = latest.max_modifiedon\n"
			+ "WHERE q.orgid =?1\n"
			+ "  AND q.branchcode =?2\n"
			+ "   and (q.suppliername =?3 or ?3='ALL')\n"
			+ "  AND q.sourcescreencode = 'PQ'\n"
			+ "  AND (?4 IS NULL OR q.docdate >= ?4 )\n"
			+ "  AND (?5 IS NULL OR q.docdate <= ?5 )\n"
			+ "ORDER BY q.docid DESC")
	Set<Object[]> getPurchaseQuotationDetailsReport(Long orgId, String branchCode,String supplierName,String fromDate,String toDate);

	PurchaseQuotationVO findByDocId(String docId);
}
