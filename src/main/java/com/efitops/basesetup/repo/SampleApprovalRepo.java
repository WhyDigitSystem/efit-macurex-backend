package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SampleApprovalVO;

@Repository
public interface SampleApprovalRepo extends JpaRepository<SampleApprovalVO, Long> {

	@Query(nativeQuery = true, value = "select * from  sampleapproval where orgid=?1 and finyear=?2 and branchcode=?3 and active=1")
	List<SampleApprovalVO> getAllSampleApprovalByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from  sampleapproval where sampleapprovalid=?1")
	SampleApprovalVO getSampleApprovalById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getSampleApprovalDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select a.docid,a.fgpartname,a.fgpartdesc,b.operationdesc from routecardentry a join routecardentrydetails b on a.routecardentryid=b.routecardentryid  where a.orgid=?1 and a.finyear=?2 and a.branchcode=?3  and a.status='PENDING' and active=1 order by a.docid desc")
	Set<Object[]> findRouteCardDetailsForSampleApproval(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select a.drawingno from drawingmaster a where a.orgid=?1 and finyear=?2 and branchcode=?3  and a.fgpartno=?4 and active=1 order by a.drawingno desc")
	Set<Object[]> findDrawingMasterNoForSampleApproval(Long orgId, String finYear, String branchCode, String partNo);

	@Query(nativeQuery = true, value = "select a.machineno,a.machinename from machinemaster a where a.orgid=?1  and a.finyear=?2 and branchcode=?3 and active=1 order by a.machineno desc")
	Set<Object[]> findMachineNoForSampleApproval(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select a.docid from joborder a where a.orgid=?1 and finyear=?2 and branchcode=?3 and a.routecardno=?4 and a.operationname=?5 and active=1 order by a.docid desc")
	Set<Object[]> findJobOrderNoForSampleApproval(Long orgId, String finYear, String branchCode, String routeCardNo,
			String operation);

	@Query(value = "SELECT s.docdate, s.docid, s.drgno, s.joborderno, s.machinename, s.machineno, "
			+ "s.operation, s.operatorname, s.orgid, s.partname, s.partno, "
			+ "s.qualityname, s.routecardno, s.sampleqty, s.shiftdate, s.sfift, s.shifttime, "
			+ "s1.characteristics, s1.lsl, s1.operator1, s1.operator2, s1.operator3, s1.specification,s.sampleapprovalid "
			+ "FROM sampleapproval s " + "JOIN sampleapprovaldetails s1 ON s.sampleapprovalid = s1.sampleapprovalid "
			+ "WHERE s.orgid = ?1 " + "AND (?2 IS NULL OR s.docdate >= ?2) "
			+ "AND (?3 IS NULL OR s.docdate <= ?3) and (s.routecardno=?4 or ?4='ALL') ", nativeQuery = true)
	Set<Object[]> getSampleApprovalDetails(Long orgId, String fromDate, String toDate,String routeCardNo);

	SampleApprovalVO findByDocId(String docId);

	SampleApprovalVO getAllSampleApprovalImagesById(Long id);

}
