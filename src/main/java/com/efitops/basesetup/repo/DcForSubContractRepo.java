package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DcForSubContractVO;

@Repository
public interface DcForSubContractRepo extends JpaRepository<DcForSubContractVO, Long> {

	@Query(nativeQuery = true, value = "select * from dcforsubcontract where orgid=?1 and finyear=?2 and branchcode=?3")
	List<DcForSubContractVO> findDcforSCByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select*from dcforsubcontract where dcforsubcontractid=?1")
	List<DcForSubContractVO> getDcforSCById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3  and screencode=?4")
	String getdcForSubcontractDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "SELECT a.docid,a.docdate,a.routecardno,a.customername,b.gstin,b.currency FROM efit_ops.issuetosubcontractor a,efit_ops.partymaster b "
			+ "where a.customername =b.partyname and a.orgid=?1  and a.active=1 and a.cancel = 0")
	Set<Object[]> findIssueSCNoDetails(Long orgId);

	@Query(nativeQuery = true, value = "select concat(a.addressline1,' ',a.addressline2,' ',a.addressline3,' ',a.city,' ',a.pincode,' ',a.state) as address from \r\n"
			+ "partyaddress a, partymaster b where a.partymasterid = b.partymasterid and b.cancel = 0 \r\n"
			+ "    and b.active = 1  and b.orgid =?1  and b.partyname =?2")
	Set<Object[]> findAddressDetails(Long orgId, String customerName);

	@Query(nativeQuery = true, value = "select  a1.partyname,a1.partycode,concat(a.addressline1,' ',a.addressline2,' ',a.addressline3,' ',a.city,' ',a.pincode,' ',a.state) as primaryaddress from\r\n"
			+ " partymaster a1, partyaddress a where a.partymasterid=a1.partymasterid and  \r\n"
			+ "	 a1.partytype='SUB-CONTRACTOR' and \r\n"
			+ "  a1.orgid=?1 and a1.active=1 group by  a1.partyname,a1.partycode,primaryaddress order by  a1.partyname")
	Set<Object[]> getSubContractorName(Long orgId);

	@Query(nativeQuery = true, value = "select a1.item,a1.itemdescription,a1.process,a1.quantity,b.primaryunit from issuetosubcontractor a,\r\n"
			+ "	  issuetosubcontractdetails a1,item b where a1.item=b.itemname and a1.issuetosubcontractorid=a.issuetosubcontractorid and \r\n"
			+ "	  a.orgid=?1 and a.docid=?2 group by a1.item,a1.itemdescription,a1.process,a1.quantity,b.primaryunit order by\r\n"
			+ "	  a1.item")
	Set<Object[]> getItenNameAndDescFromIssue(Long orgId, String scIssueNo);

	@Query(nativeQuery = true, value = "SELECT d.docid , routecardno, customername,customeraddress\r\n"
			+ "FROM dcforsubcontract d\r\n"
			+ "WHERE d.orgid = ?1 \r\n"
			+ "  AND d.finyear = ?2 \r\n"
			+ "  AND d.branchcode = ?3 \r\n"
			+ "  And d.active=1\r\n"
			+ "  AND NOT EXISTS (\r\n"
			+ "      SELECT 1\r\n"
			+ "      FROM jobworkout j\r\n"
			+ "      WHERE j.orgid = d.orgid\r\n"
			+ "        AND j.finyear = d.finyear\r\n"
			+ "        AND j.branchcode = d.branchcode\r\n"
			+ "        AND j.dcno = d.docid\r\n"
			+ "  );")
	Set<Object[]> getDcSubContractorDocIdForJobWorkOutOrder(Long orgId, String finYear, String branchCode);

}
