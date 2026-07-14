package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SubContractGrnVO;

@Repository
public interface SubContractGrnRepo extends JpaRepository<SubContractGrnVO, Long> {

	@Query(nativeQuery = true, value = "select*from subcontractgrn where orgid=?1 and finyear=?2 and branchcode=?3 ")
	List<SubContractGrnVO> getAllSubContractGrnByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select*from subcontractgrn where subcontractgrnid=?1")
     SubContractGrnVO getSubContractGrnById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getSubContractGrnDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select j.jobworkorderno,j.contractorname,j.contractorcode,j.dcno,j.dispatchedthrough,j.pono,j.routecardno,j.taxtype,d.scissueno,d.gstno,d.subcontractoraddress from jobworkout j \r\n"
			+ "left join dcforsubcontract d on j.dcno=d.docid where j.orgid=?1 and j.branchcode=?2 and j.jobworkorderno=?3")
	Set<Object[]> getJobWorkOutOrderFromSubContractDetails(Long orgId,String branchCode, String jobWorkOutOrderNumber);

	@Query(nativeQuery = true, value = "select d1.part,d1.partdesc,d1.process,d1.quantitynos,d2.unit,d1.rate\r\n"
			+ "			from jobworkout d join jobworkoutdetails d1 on d.jobworkoutid=d1.jobworkoutid\r\n"
			+ "            left join dcforsubcontractdetail d2 on d2.item=d1.part where \r\n"
			+ "			d.orgid=?1 and d.branchcode=?2 and d.jobworkorderno=?3 and d.active=1 and d.cancel=0")
	Set<Object[]> getJobWorkOutOrderFromSubContractItemDetails(Long orgId, String branchCode, String jobWorkOutOrderNumber);

	@Query(nativeQuery = true, value = "select jobworkorderno\r\n"
			+ " from jobworkout where orgid=?1  and active=1 and cancel=0")
	Set<Object[]> getJobWorkOutOrderDocId(Long orgId);
	
	@Query(nativeQuery = true, value = "SELECT\r\n"
			+ "    a.dcno,\r\n"
			+ "    a.docid,\r\n"
			+ "    a.docdate,\r\n"
			+ "    a.pono,\r\n"
			+ "    a.gstno,\r\n"
			+ "    a.gsttype,\r\n"
			+ "    a.jobworkoutorderdocid,\r\n"
			+ "    a.currency,\r\n"
			+ "    a.routecardno,\r\n"
			+ "    a.subcontractoraddress,\r\n"
			+ "    a.subcontractorcode,\r\n"
			+ "	a.subcontractorname,\r\n"
			+ "    b.itemcode,\r\n"
			+ "    b.itemdesc,\r\n"
			+ "    b.primaryunit,\r\n"
			+ "    b.qty,\r\n"
			+ "    b.porate,\r\n"
			+ "    b.taxtype,\r\n"
			+ "    b.taxvalue,\r\n"
			+ "    b.amount,\r\n"
			+ "    b.amount + b.taxvalue AS totalAmount\r\n"
			+ "FROM\r\n"
			+ "    subcontractgrn a,\r\n"
			+ "    subcontractgrndetails b\r\n"
			+ "WHERE\r\n"
			+ "    a.subcontractgrnid = b.subcontractgrnid\r\n"
			+ "     AND a.orgid = ?1\r\n"
			+ "    AND (a.subcontractorname = ?2 OR ?2 = 'ALL')\r\n"
			+ "    AND (?3 IS NULL OR a.docdate >= ?3)\r\n"
			+ "    AND (?4 IS NULL OR a.docdate <= ?4)\r\n"
			+ "    AND (a.branchcode = ?5 OR ?5 = 'ALL')\r\n"
			+ "ORDER BY\r\n"
			+ "    a.createdon DESC")
	Set<Object[]> getSubContractGrnDetails(Long orgId, String subContractName,String fromDate,String toDate,String branchCode);
	
	@Query(nativeQuery = true, value = "SELECT\r\n"
			+ "    a.dcno,\r\n"
			+ "    a.docid,\r\n"
			+ "    a.docdate,\r\n"
//			+ "    a.pono,\r\n"
			+ "    a.gstno,\r\n"
			+ "    a.gsttype,\r\n"
			+ "    a.jobworkoutorderdocid,\r\n"
			+ "    a.pono,\r\n"
			+ "    a.routecardno,\r\n"
			+ "    a.subcontractoraddress,\r\n"
			+ "    a.subcontractorcode,\r\n"
			+ "	a.subcontractorname,\r\n"
			+ "    a.grossamount,\r\n"
			+ "    a.totalamounttax,\r\n"
			+ "    a.netamount,\r\n"
			+ "    CAST(\r\n"
			+ "        CASE\r\n"
			+ "            WHEN a.gsttype = 'INTER' THEN a.totalamounttax\r\n"
			+ "            ELSE 0\r\n"
			+ "        END AS DECIMAL(18,2)\r\n"
			+ "    ) AS igst,\r\n"
			+ "    CAST(\r\n"
			+ "        CASE\r\n"
			+ "            WHEN a.gsttype = 'INTRA' THEN a.totalamounttax / 2\r\n"
			+ "            ELSE 0\r\n"
			+ "        END AS DECIMAL(18,2)\r\n"
			+ "    ) AS cgst,\r\n"
			+ "    CAST(\r\n"
			+ "        CASE\r\n"
			+ "            WHEN a.gsttype = 'INTRA' THEN a.totalamounttax / 2\r\n"
			+ "            ELSE 0\r\n"
			+ "        END AS DECIMAL(18,2)\r\n"
			+ "    ) AS sgst\r\n"
			+ "FROM\r\n"
			+ "    subcontractgrn a\r\n"
			+ "WHERE\r\n"
			+ "    a.orgid = ?1\r\n"
			+ "    AND (a.subcontractorname = ?2 OR ?2 = 'ALL')\r\n"
			+ "    AND (?3 IS NULL OR a.docdate >= ?3)\r\n"
			+ "    AND (?4 IS NULL OR a.docdate <= ?4)\r\n"
			+ "    AND (a.branchcode = ?5 OR ?5 = 'ALL')\r\n"
			+ "ORDER BY\r\n"
			+ "    a.createdon DESC")
	Set<Object[]> getSubContractGrnSummaryDetails(Long orgId, String subContractName,String fromDate,String toDate,String branchCode);
	
	@Query(nativeQuery = true, value = "SELECT docid,customername,customeraddress\r\n"
			+ "FROM dcforsubcontract\r\n"
			+ "WHERE active = 1 \r\n"
			+ "  AND cancel = 0 and orgid=?1;")
	Set<Object[]> getdcnumber(Long orgId);
	
	@Query(nativeQuery = true, value = "SELECT g.item ,g.itemdesc, g.qty\r\n"
			+ "FROM dcforsubcontract a\r\n"
			+ "join  dcforsubcontractdetail g\r\n"
			+ "    ON g.dcforsubcontractid = a.dcforsubcontractid\r\n"
			+ "WHERE a.active = 1 \r\n"
			+ "  AND a.cancel = 0 and a.orgid=?1 and a.docid=?2 ;")
	Set<Object[]> getdcnumberdetails(Long orgId);
}
