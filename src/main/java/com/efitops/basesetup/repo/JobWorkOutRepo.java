package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.JobWorkOutVO;

@Repository
public interface JobWorkOutRepo extends JpaRepository<JobWorkOutVO, Long>{

	@Query(nativeQuery = true, value = "select * from  jobworkout  where orgid=?1 and finyear=?2 and branchCode=?3")
	List<JobWorkOutVO> getAllJobWorkOutByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from jobworkout where jobworkoutid=?1")
	List<JobWorkOutVO> getAllJobWorkOutById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getJobWorkOutDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select a.docid,a.routecardno,a.customername,a.subcontractorname,a.subcontractorid,a.customeraddress,\r\n"
			+ "a.dispatchthrough,b1.taxtype from partymaster b,partyaddress b1,dcforsubcontract a,routecardentry c where \r\n"
			+ "a.orgid=?1 and b.partymasterid=b1.partymasterid and b.partyname=a.customername and \r\n"
			+ "a.routecardno=c.docid group by a.docid,a.routecardno,a.customername,a.subcontractorname,a.subcontractorid,a.customeraddress,\r\n"
			+ "a.dispatchthrough,b1.taxtype order by a.docid")
	Set<Object[]> getDCNumberFromDcForSubContract(Long orgId);
	
	@Query(nativeQuery = true, value = "select a.docid from purchaseorder a,routecardentry a1 where a.workorderno=a1.wono and \r\n"
			+ "  a.orgid=?1 and a1.docid=?2 order by  a.docid")
	Set<Object[]> getPoNumberFromPurchase(Long orgId, String routeCardNo);

	@Query(nativeQuery = true, value = "select a.docid from subcontractquotation a where a.orgid=?1 and a.routecardno=?2 order by \r\n"
			+ "  a.docid")
	Set<Object[]> getQuotationNumberFromSubContract(Long orgId, String routeCardNo);
	
	@Query(nativeQuery = true, value = "select a1.item,itemdesc,a1.process,a.duedate,a1.qty from dcforsubcontract a join dcforsubcontractdetail a1\r\n"
			+ " on a.dcforsubcontractid=a1.dcforsubcontractid where a.orgid=?1 and a.docid=?2 and a.routecardno =?3 group by\r\n"
			+ "a1.item,itemdesc,a1.process,a.duedate,a1.qty order by a1.item")
	Set<Object[]> getItemAndItemDescFromDcForSubContract(Long orgId, String dcNumber,String routeCardNo);

	@Query(nativeQuery = true, value = "select p.docid from purchaseorder p join routecardentry r on p.workorderno=r.wono where r.orgid=?1 and r.finyear=?2 and r.branchcode=?3 and r.docid=?4 order by p.docid \r\n"
			+ "")
	Set<Object[]> getPurchaseOrderDocIdForJobWorkOutOrder(Long orgId, String finYear, String branchCode, String routeCardNo);
	
	@Query(nativeQuery = true, value = "select s1.rate from subcontractquotation s join subcontractquotationdetails s1 on s.subcontractquotationid=s1.subcontractquotationid\n"
			+ "            where s.orgid=?1 and s.finyear=?2 and  s.branchcode=?3 and  s.docid=?4 and s.routecardno=?5 and s1.part=?6")
	Set<Object[]> getRateFromSubContractQuotation(Long orgId, String finYear, String branchCode, String subContractQuotationDocId,String routeCardNo,String part);
	
	@Query(nativeQuery = true, value = "SELECT\n"
			+ "    a.jobworkorderno,\n"
			+ "    a.jobworkorderdate,\n"
			+ "    a.dcno,\n"
			+ "    a.pono,\n"
			+ "    a.quotationno,\n"
			+ "    a.routecardno,\n"
			+ "    a.contractorcode,\n"
			+ "    a.contractorname,\n"
			+ "    a.dispatchedthrough,\n"
			+ "    a.durationofprocess,\n"
			+ "    a.taxtype,\n"
			+ "    b.part,\n"
			+ "    b.partdesc,\n"
			+ "    b.process,\n"
			+ "    b.quantitynos,\n"
			+ "    b.rate,\n"
			+ "    b.taxcode,\n"
			+ "    b.taxamt,\n"
			+ "    b.discount,\n"
			+ "    b.grossamt,\n"
			+ "    b.netamount,\n"
			+ "    b.discountamount,\n"
			+ "    b.grossamt + b.taxamt -b.discountamount AS totalAmount\n"
			+ "FROM\n"
			+ "    jobworkout a,\n"
			+ "    jobworkoutdetails b\n"
			+ "WHERE\n"
			+ "    a.jobworkoutid = b.jobworkoutid  \n"
			+ "    AND a.orgid = ?1\n"
			+ "    AND (a.contractorname = ?2 OR ?2 = 'ALL')\n"
			+ "    AND (?3 IS NULL OR a.jobworkorderdate >= ?3)\n"
			+ "    AND (?4 IS NULL OR a.jobworkorderdate <= ?4)\n"
			+ "    AND (a.branchcode = ?5 OR ?5 = 'ALL')\n"
			+ "ORDER BY\n"
			+ "    a.createdon DESC")
	Set<Object[]> getJobWorkOutDetails(Long orgId, String contractorName,String fromDate,String toDate,String branchCode);
	
	@Query(nativeQuery = true, value = "SELECT\n"
			+ "    a.jobworkorderno,\n"
			+ "    a.jobworkorderdate,\n"
			+ "    a.dcno,\n"
			+ "    a.pono,\n"
			+ "    a.quotationno,\n"
			+ "    a.routecardno,\n"
			+ "    a.contractorcode,\n"
			+ "    a.contractorname,\n"
			+ "    a.dispatchedthrough,\n"
			+ "    a.durationofprocess,\n"
			+ "       a.taxtype,\n"
			+ "    a.totalgrossamt,\n"
			+ "    a.totaltax,\n"
			+ "    a.totalamount,\n"
			+ "    CAST(\n"
			+ "        CASE\n"
			+ "            WHEN a.taxtype = 'INTER' THEN a.totaltax\n"
			+ "            ELSE 0\n"
			+ "        END AS DECIMAL(18,2)\n"
			+ "    ) AS igst,\n"
			+ "    CAST(\n"
			+ "        CASE\n"
			+ "            WHEN a.taxtype = 'INTRA' THEN a.totaltax / 2\n"
			+ "            ELSE 0\n"
			+ "        END AS DECIMAL(18,2)\n"
			+ "    ) AS cgst,\n"
			+ "    CAST(\n"
			+ "        CASE\n"
			+ "            WHEN a.taxtype = 'INTRA' THEN a.totaltax / 2\n"
			+ "            ELSE 0\n"
			+ "        END AS DECIMAL(18,2)\n"
			+ "    ) AS sgst\n"
			+ "FROM\n"
			+ "    jobworkout a\n"
			+ " WHERE\n"
			+ "    a.orgid = ?1\n"
			+ "    AND (a.contractorname = ?2 OR ?2 = 'ALL')\n"
			+ "    AND (?3 IS NULL OR a.jobworkorderdate >= ?3)\n"
			+ "    AND (?4 IS NULL OR a.jobworkorderdate <= ?4)\n"
			+ "    AND (a.branchcode = ?5 OR ?5 = 'ALL')\n"
			+ "ORDER BY\n"
			+ "    a.createdon DESC")
	Set<Object[]> getJobWorkOutSummaryDetails(Long orgId, String contractorName,String fromDate,String toDate,String branchCode);
}
