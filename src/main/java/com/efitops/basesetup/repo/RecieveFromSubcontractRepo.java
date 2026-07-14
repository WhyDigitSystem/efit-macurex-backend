package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.RecieveFromSubcontractVO;

@Repository
public interface RecieveFromSubcontractRepo extends JpaRepository<RecieveFromSubcontractVO, Long> {

	@Query(nativeQuery = true, value = "select * from recievefromsubcontract where orgid=?1 and finyear=?2 and branchcode=?3")
	List<RecieveFromSubcontractVO> findRecieveFromSubcontractByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from recievefromsubcontract where recievefromsubcontractid=?1")
	List<RecieveFromSubcontractVO> getRecieveFromSubcontractById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getRecieveFromSubContractDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "SELECT \n"
			+ "    r.docid,\n"
			+ "    r.docdate,\n"
			+ "    r.routecardno,\n"
			+ "    r.issueno,\n"
			+ "    r.issuedate,\n"
			+ "    r.jobworkoutorder,\n"
			+ "    r.dcno,\n"
			+ "    r.department,\n"
			+ "    r.contractorid,\n"
			+ "    r.contractorname,\n"
			+ "    r.invoiceno,\n"
			+ "    r.testcertificate,\n"
			+ "    r.orgid,\n"
			+ "    CASE \n"
			+ "        WHEN i.docid IS NOT NULL THEN 'Completed'\n"
			+ "        ELSE 'Pending'\n"
			+ "    END AS status,\n"
			+ "   r.recievefromsubcontractid,i.issuetosubcontractorid \n"
			+ "FROM recievefromsubcontract r\n"
			+ "LEFT JOIN issuetosubcontractor i \n"
			+ "    ON r.issueno = i.docid\n"
			+ "WHERE r.orgid =?1\n"
			+ " and \n"
			+ " (?2 is null or r.docdate >= ?2)\n"
			+ "     and (?3 is null or r.docdate <= ?3)\n"
			+ "AND (\n"
			+ "        ?4 = 'ALL'\n"
			+ "     OR (?4 = 'Completed' AND i.docid IS NOT NULL)\n"
			+ "     OR (?4 = 'Pending'   AND i.docid IS NULL)\n"
			+ "    )  and (r.routecardno=?5 or ?5='ALL')")
	Set<Object[]> getRecieveFromSubContractDetails(Long orgId, String frodate , String todate,String status,String routeCardNo);
}
