package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SubContractEnquiryVO;

@Repository
public interface SubContractEnquiryRepo extends JpaRepository<SubContractEnquiryVO, Long> {
	@Query(nativeQuery = true, value = "select * from  subcontractenquiry  where orgid=?1 and finyear=?2 and branchcode=?3")
	List<SubContractEnquiryVO> getAllSubContractEnquiryByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from subcontractenquiry where subcontractenquiryid=?1")
	List<SubContractEnquiryVO> getSubContractEnquiryById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getSubContractEnquiryDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select  a.partyname,a.partycode,a.gstin from partymaster a where \r\n"
			+ "    a.partytype='SUB-CONTRACTOR' and \r\n"
			+ "    a.orgid=?1 and a.active=1 group by  a.partyname,a.partycode,a.gstin order by  a.partyname")
	Set<Object[]> getSubContractCustomerNameAndCode(Long orgId);

	@Query(nativeQuery = true, value = "select a.contactperson,a.contactphoneno from partystate a,partymaster a1 where\r\n"
			+ " a.partymasterid=a1.partymasterid and a1.orgid=?1 and a1.partyname=?2 and a1.partytype='SUB-CONTRACTOR' and\r\n"
			+ " a1.active = 1 group by a.contactperson,a.contactphoneno order by a.contactperson")
	Set<Object[]> getSubContractContactNameAndNo(Long orgId, String subContractorName);

	@Query(nativeQuery = true, value = "select a.item,a.itemdescription,a.process,a.quantity from \r\n"
			+ "		 issuetosubcontractdetails a,issuetosubcontractor a1  where a1.orgid=?1 and\r\n"
			+ "         a.issuetosubcontractorid=a1.issuetosubcontractorid and \r\n"
			+ "	a1.docid=?2 and a1.active = 1  group by a.item,a.itemdescription,a.process,a.quantity order by a.item")
	Set<Object[]> getSubContractPartNoAndDescription(Long orgId, String scIssueNo);

	@Query(nativeQuery = true, value = "select docid from routecardentry where orgid=?1 and active=true group by docid")
	Set<Object[]> getSubRouteCardNo(Long orgId);

	@Query(nativeQuery = true, value = "select a.docid,a.docdate,a.department from\r\n"
			+ "    issuetosubcontractor a where a.orgid=?1 and a.routecardno=?2 and  a.active=true \r\n"
			+ "    group by a.docid,a.docdate,a.department")
	Set<Object[]> getScIssueNoFormSubContract(Long orgId, String routeCardNo);

	@Query(nativeQuery = true, value = "SELECT s.orgid, s.subcontractenquiryid,s.docid,s.docdate,s.enquiryduedate,s.subcontractorname,s.routecardno,s1.part,s1.partdescription,s1.process \r\n"
			+ "FROM subcontractenquiry s\r\n"
			+ "join subcontractenquirydetails s1 on s.subcontractenquiryid = s1.subcontractenquiryid "
			+ "where s.orgid=?1  and (?2 is null or s.docdate >= ?2)  and (?3 is null or s.docdate <= ?3) and (s.subcontractorname=?4 or ?4='ALL')")
	Set<Object[]> getSubContractEnquiryDetails(Long orgId, String frodate, String todate,String subContractorName);
	
	
}
