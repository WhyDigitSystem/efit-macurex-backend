package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.NpdVO;

@Repository
public interface NpdRepo extends JpaRepository<NpdVO, Long> {

	@Query(nativeQuery = true, value = "select * from npd  where  orgid=?1 and finyear=?2 and branchcode=?3")
	List<NpdVO> getNpdByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from npd  where  npdid=?1")
	NpdVO getNpdById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getNpdDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select employeename from employeemaster where orgid=?1 and active=1 and cancel=0")
	Set<Object[]> getEmployeeName(Long orgId);

	@Query(nativeQuery = true, value = "select n.npdid,n.orgid ,n.documentformateno, n1.customer,n1.documentrefno,n1.partname,n1.partno,n1.currentdate,n1.approvedby,n1.remarks,n1.revision  from npd n\r\n"
			+ "join  npddetails n1 on  n.npdid = n1.npdid\r\n" + "where n.orgid= ?1 \r\n"
			+ " and (?2 IS NULL OR n.docdate >= ?2) \n" + " and (?3 IS NULL OR n.docdate <= ?3) ")
	Set<Object[]> getNpdReport(Long orgId, String fromdate, String todate);

	NpdVO findByDocId(String docId);

	NpdVO getAllNPDImagesById(Long id);

	@Query(nativeQuery = true, value = "select partyname ,partycode from partymaster where partytype='CUSTOMER' and orgid=?1 and branchcode=?2 and active=1")
	Set<Object[]> getNPDdetails(Long orgId,String branchCode);

}
