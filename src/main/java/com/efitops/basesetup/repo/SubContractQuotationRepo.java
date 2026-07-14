package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SubContractQuotationVO;

@Repository
public interface SubContractQuotationRepo extends JpaRepository<SubContractQuotationVO, Long> {

	@Query(nativeQuery = true, value = "select * from  subcontractquotation where orgid=?1 and finYear=?2 and branchCode=?3")
	List<SubContractQuotationVO> getAllSubContractQuotationByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from subcontractquotation  where subcontractquotationid=?1")
	List<SubContractQuotationVO> getSubContractQuotationById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchCode=?3 and  screencode=?4")
	String getSubContractQuotationDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select docid,docdate from subcontractenquiry where orgid=?1 order by docid")

	Set<Object[]> getEnquiryNoFromSubContractEnquiry(Long orgId);

	@Query(nativeQuery = true, value = "select a1.subcontractorrefno,a1.subcontractorrefdate,a1.subcontractorname,a1.routecardno,a1.scissueno,a.gstin from \r\n"
			+ "subcontractenquiry a1 ,partymaster a where a.orgid=a1.orgid and a.partyname=a1.subcontractorname and\r\n"
			+ "a.partytype='SUB-CONTRACTOR' and a1.orgid=?1 and a1.docid=?2 group by a1.subcontractorrefno,a1.subcontractorrefdate,\r\n"
			+ "a1.subcontractorname,a1.routecardno,a1.scissueno,a.gstin")
	Set<Object[]> getDocDateFromSubEnquiry(Long orgId, String docId);

	@Query(nativeQuery = true, value = "select a1.part,a1.partdescription,a1.qty,a1.process from\r\n"
			+ "    subcontractenquiry a,subcontractenquirydetails a1 where \r\n"
			+ "    a.subcontractenquiryid=a1.subcontractenquiryid and a.orgid=?1 and a.docid=?2 group by\r\n"
			+ "    a1.part,a1.partdescription,a1.qty,a1.process order by a1.part")
	Set<Object[]> getPartNoPartDescFromSubEnquiry(Long orgId, String docId);

	@Query(nativeQuery = true, value = "SELECT \r\n" + "    s.docid,\r\n" + "    s.subcontractorname,\r\n"
			+ "    s.subcontractorid,\r\n"
			+ "   concat(ec.addressline1 ,ec.addressline2 , ec.addressline3) as address\r\n"
			+ "FROM subcontractquotation s\r\n" + "JOIN partymaster e\r\n"
			+ "    ON e.partycode = s.subcontractorid\r\n" + "   AND e.orgid = s.orgid\r\n"
			+ "LEFT JOIN partyaddress ec\r\n"
			+ "    ON ec.partymasterid = e.partymasterid and ec.addresstype='SHIPPING' \r\n" + "WHERE s.orgid = ?1 \r\n"
			+ "  AND s.finyear = ?2 \r\n" + "  AND s.branchcode = ?3 \r\n" + "  AND s.routecardno = ?4 \r\n"
			+ "  AND s.active = 1   ")
	Set<Object[]> getSubContractQuotationDocIdForJobWorkOutOrder(Long orgId, String finYear, String branchCode,
			String routeCardNo);

	@Query(nativeQuery = true, value = "select concat(o.docid, '-SCQ',q.count) from subcontractenquiry o join subcontractquotation q  on\n"
			+ "    q.enquiryno=o.docid  where o.orgid=?1 and o.subcontractorname=?2  and q.subcontractquotationid=?3")
	String getSubContractEnquiryNameId(Long orgId, String customerName, Long purchaseQuotationId);

	@Query(nativeQuery = true, value = "select count from subcontractquotation q where q.orgid=?1 and q.subcontractorname=?2  and  q.subcontractquotationid=?3")
	int getCount(Long orgId, String customerName, Long purchaseQuotationId);

	@Query(nativeQuery = true, value = "select concat(o.docid, '-SCQ',1) from subcontractenquiry o   where o.orgid=?1 and o.subcontractorname=?2 and o.docid=?3")
	String getSubContractEnquiryIdIteration(Long orgId, String customerName, String enquiryNo);

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
			+ "    q.count,\n"
			+ "    q.status\n"
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
			+ "      AND (customername = ?3 OR ?3 = 'ALL')\n"
			+ "      AND sourcescreencode = 'SCQ'\n"
			+ "      AND (?4 IS NULL OR docdate >= ?4)\n"
			+ "      AND (?5 IS NULL OR docdate <= ?5)\n"
			+ "    GROUP BY docid\n"
			+ ") latest\n"
			+ "  ON latest.docid = q.docid\n"
			+ " AND STR_TO_DATE(q.modifiedon, '%d-%m-%Y %h:%i:%s %p') = latest.max_modifiedon\n"
			+ "WHERE q.orgid = ?1\n"
			+ "  AND q.branchcode = ?2\n"
			+ "  AND (q.customername =?3 OR  ?3= 'ALL')\n"
			+ "  AND q.sourcescreencode = 'SCQ'\n"
			+ "  AND (?4 IS NULL OR q.docdate >= ?4)\n"
			+ "  AND (?5 IS NULL OR q.docdate <= ?5)\n"
			+ "ORDER BY q.docid DESC")
	Set<Object[]> getSubContractQuotationDetailsReport(Long orgId, String branchCode,String subContractName,String fromDate,String toDate);

	SubContractQuotationVO findByDocId(String docId);

	SubContractQuotationVO getAllSubContractQuotationById(Long id);
	
}
