package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.EnquiryVO;
import com.efitops.basesetup.entity.QuotationVO;

@Repository
public interface QuotationRepo extends JpaRepository<QuotationVO, Long> {

	@Query(nativeQuery = true, value = "select * from quotation  where  orgid=?1 and finyear=?2 and branchcode=?3")
	List<QuotationVO> getAllQuotationByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from quotation  where  quotationid=?1")
	QuotationVO getQuotationById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getQuotationDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "SELECT docid,docdate,contactname,contactno FROM enquiry\r\n"
			+ "WHERE orgid = ? AND customercode = ? AND status = ? AND active = 1 AND docid NOT IN (\r\n"
			+ "SELECT enquiryno \r\n"
			+ "FROM quotation\r\n"
			+ ")\r\n"
			+ "ORDER BY docid;")
	Set<Object[]> getEnquiryNoAndDate(Long orgId, String customerCode);

	@Query(nativeQuery = true, value = "select a.employee from employee a where a.orgid=?1 and a.active=1 order by a.employee")
	Set<Object[]> getProductionManager(Long orgId);

	@Query(nativeQuery = true, value = "select a.partcode,a.partdescription,a.drawingno,a.revisionno,a.unit,a.requireqty,i1.price from enquirydetails\n"
			+ "			 a,enquiry a1,item i,itempriceslab i1  where a1.enquiryid=a.enquiryid  and i.itemid=i1.itemid and i.itemname=a.partcode and\n"
			+ "					  a1.orgid=?1 and a1.docid=?2 and a1.customercode=?3 and  a1.active = 1 group by \n"
			+ "							a.partcode,a.partdescription,a.drawingno,a.revisionno,a.unit,a.requireqty,i1.price  order by  a.partcode")
	Set<Object[]> getPartNoAndPartDesBasedOnEnquiryNo(Long orgId, String docId, String customerCode);
	
	@Query(nativeQuery =true,value ="select q.quotationid,q.docid, max(q.docdate),q.customername,q.enquiryno,q.kindattention  from quotation q\n"
			+ "			where q.docid NOT IN (select w.quotationno from workorder w where w.orgid =?1 )  and q.orgid =?2\n"
			+ "			group by q.quotationid")
	Set<Object[]> findQutationsByOrgId(Long orgId);
	
	@Query(nativeQuery = true, value = "select concat(o.docid, '-QOT',q.count) from enquiry o join quotation q  on\n"
			+ "    q.enquiryno=o.docid  where o.orgid=?1 and o.customer=?2  and q.quotationid=?3")
	String getEnquiryNameId(Long orgId,String customerName,Long quotationId);
	
	@Query(nativeQuery = true, value = "select count from quotation q where q.orgid=?1 and q.customername=?2  and  q.quotationid=?3")
	int getCount(Long orgId, String customerName,Long quotationId);
	
	@Query(nativeQuery = true, value = "select concat(o.docid, '-QOT',1) from enquiry o   where o.orgid=?1 and o.customer=?2 and o.docid=?3")
	String getEnquiryIdIteration(Long orgId,String customerName, String enquiryNo);

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
			+ "    q.producationmanager,\n"
			+ "    q.count,\n"
			+ "    q.validtill,\n"
			+ "    CASE\n"
			+ "        WHEN w.quotationno IS NOT NULL THEN 'Completed'\n"
			+ "        ELSE 'Pending'\n"
			+ "    END AS status\n"
			+ "FROM quoterevision q\n"
			+ "LEFT JOIN workorder w \n"
			+ "       ON q.docid = w.quotationno\n"
			+ "JOIN (\n"
			+ "    SELECT\n"
			+ "        docid,\n"
			+ "        MAX(\n"
			+ "            STR_TO_DATE(modifiedon, '%d-%m-%Y %h:%i:%s %p')\n"
			+ "        ) AS max_modifiedon\n"
			+ "    FROM quoterevision\n"
			+ "    WHERE orgid = ?1\n"
			+ "      AND branchcode = ?2\n"
			+ "      AND (customername = ?3 OR ?3 = 'ALL')\n"
			+ "      AND sourcescreencode = 'QOT'\n"
			+ "      AND (?4 IS NULL OR docdate >= ?4)\n"
			+ "      AND (?5 IS NULL OR docdate <= ?5)\n"
			+ "    GROUP BY docid\n"
			+ ") latest\n"
			+ "  ON latest.docid = q.docid\n"
			+ " AND STR_TO_DATE(q.modifiedon, '%d-%m-%Y %h:%i:%s %p') = latest.max_modifiedon\n"
			+ "WHERE q.orgid = ?1\n"
			+ "  AND q.branchcode = ?2\n"
			+ "  AND (q.customername = ?3 OR ?3 = 'ALL')\n"
			+ "  AND q.sourcescreencode = 'QOT'\n"
			+ "  AND (?4 IS NULL OR q.docdate >= ?4)\n"
			+ "  AND (?5 IS NULL OR q.docdate <= ?5)\n"
			+ "  AND (\n"
			+ "        ?6 = 'ALL'\n"
			+ "        OR (?6 = 'Completed' AND w.quotationno IS NOT NULL)\n"
			+ "        OR (?6 = 'Pending'   AND w.quotationno IS NULL)\n"
			+ "      )\n"
			+ "ORDER BY q.docid DESC")
	Set<Object[]> getQuotationDetailsReport(Long orgId, String branchCode,String customerName,String fromDate,String toDate,String status);

	QuotationVO findByDocId(String docId);
}
