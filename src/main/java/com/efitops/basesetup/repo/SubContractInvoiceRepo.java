package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SubContractInvoiceVO;

@Repository
public interface SubContractInvoiceRepo extends JpaRepository<SubContractInvoiceVO, Long> {
	@Query(nativeQuery = true, value = "select * from  subcontractinvoice where orgid=?1 and finyear=?2 and branchcode=?3")
	List<SubContractInvoiceVO> getAllSubContractInvoiceByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from subcontractinvoice where subcontractinvoiceid=?1")
	List<SubContractInvoiceVO> getSubContractInvoiceById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getSubContractInvoiceDocId(Long orgId, String finYear, String branchode, String screenCode);

	@Query(nativeQuery = true, value = "SELECT \r\n"
			+ "    a.jobworkorderno,\r\n"
			+ "    a.dcno,\r\n"
			+ "    a.jobworkorderdate,\r\n"
			+ "    a.dispatchedthrough,\r\n"
			+ "    a.routecardno,\r\n"
			+ "    a.contractorcode,\r\n"
			+ "    a.contractorname,\r\n"
			+ "    a.destination\r\n"
			+ "FROM jobworkout a \r\n"
			+ " WHERE a.orgid = ?1 \r\n"
			+ "  AND a.active = 1\r\n"
			+ "  AND NOT EXISTS (\r\n"
			+ "      SELECT 1\r\n"
			+ "      FROM subcontractinvoice b\r\n"
			+ "      WHERE b.orgid = a.orgid\r\n"
			+ "        AND b.active = 1\r\n"
			+ "        AND b.jobworkorderno = a.jobworkorderno\r\n"
			+ "  )\r\n"
			+ "ORDER BY a.jobworkorderno")
	Set<Object[]> getJobWorkOutOrderNo(Long orgId);    

	@Query(nativeQuery = true, value = "select a.part,a.partdesc,a.process,a.quantitynos,a.rate,a.grossamt,a.cgst,a.sgst,a.amount,a1.totalgrossamt,a1.totaltax,a1.totalamount,a1.amountinwords from\r\n"
			+ " jobworkout a1,jobworkoutdetails a where a1.orgid=?1 and a1.jobworkorderno=?2 and \r\n"
			+ "		             a.jobworkoutid=a1.jobworkoutid and a1.active=true  group by\r\n"
			+ "		   a.part,a.partdesc,a.process,a.quantitynos,a.rate,a.grossamt,a.cgst,a.sgst,a.amount,a1.totalgrossamt,a1.totaltax,a1.totalamount,a1.amountinwords \r\n"
			+ "            order by a.part")
	Set<Object[]> getJobWorkOutOrderFromPartNoAndDesc(Long orgId, String docId);
}
