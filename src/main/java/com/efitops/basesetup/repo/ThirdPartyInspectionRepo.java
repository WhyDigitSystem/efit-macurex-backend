package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.efitops.basesetup.entity.ThirdPartyInspectionVO;

@Repository
public interface ThirdPartyInspectionRepo extends JpaRepository<ThirdPartyInspectionVO, Long> {
	@Query(nativeQuery = true, value = "select * from  thirdPartyInspection  where orgid=?1 and finyear=?2 and branchcode=?3")
	List<ThirdPartyInspectionVO> findThirdPartyInspectionOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from thirdPartyInspection where thirdPartyInspectionid=?1")
	List<ThirdPartyInspectionVO> getThirdPartyInspectionById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getThirdPartyInspectionDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select a.grnno,a.inwardno,a1.customername,a.suppliername,a1.workorderno,a.pono from\r\n"
			+ " grn a, purchaseorder a1 where a.orgid=?1 and a.orgid=a1.orgid and a.pono=a1.docid group by \r\n"
			+ " a.grnno,a.inwardno,a1.customername,a.suppliername,a1.workorderno,a.pono order by a.grnno")
	Set<Object[]> findGRNForThirdPartyInspDetails(Long orgId);

	@Query(nativeQuery = true, value = "SELECT b.partyname, " + "CONCAT( " + " COALESCE(a.addressline1,''), ', ', "
			+ " COALESCE(a.addressline2,''), ', ', " + " COALESCE(a.addressline3,''), ', ', "
			+ " COALESCE(a.city,''), ', ', " + " COALESCE(a.pincode,''), ', ', " + " COALESCE(a.state,'') "
			+ ") AS full_address, " + "a.stategstin, " + "a.state, " + "a.pincode, " + "a.city "
			+ "FROM efit_ops.partyaddress a " + "JOIN efit_ops.partymaster b "
			+ "  ON a.partymasterid = b.partymasterid " + "WHERE b.cancel = 0 " + "AND b.active = 1 "
			+ "AND b.partytype = 'THIRD-PARTY' " + "AND UPPER(a.addresstype) = 'BILLING' " + "AND b.orgid = ?1")
	Set<Object[]> findgetThirdPartyDetailsForThirdPartyInsp(Long orgId);

	@Query(nativeQuery = true, value = "SELECT\r\n"
			+ "    t.thirdpartyinspectionid,\r\n"
			+ "    t.docid,\r\n"
			+ "    t.docdate,\r\n"
			+ "    t.grnno,\r\n"
			+ "    t.workorderno,\r\n"
			+ "    t.pono,\r\n"
			+ "    t.customername,\r\n"
			+ "    t.suppliername,\r\n"
			+ "    pm.partyname,\r\n"
			+ "    t1.itemid,\r\n"
			+ "    t1.itemdesc,\r\n"
			+ "    t1.inspectiontype,\r\n"
			+ "    t1.certificateno,\r\n"
			+ "    t1.status,\r\n"
			+ "    t1.remarks\r\n"
			+ "FROM thirdpartyinspection t\r\n"
			+ "join thirdpartyinspectiondetails t1\r\n"
			+ "    ON t.thirdpartyinspectionid = t1.thirdpartyinspectionid\r\n"
			+ "LEFT JOIN partymaster pm\r\n"
			+ "    ON pm.partyname = t.thirdpartydetails\r\n"
			+ "WHERE t.orgid = ?1\r\n"
			+ "  AND ( ?2 is null or t.docdate >= ?2)  and (?3 is null or t.docdate <= ?3) and (pm.partyname=?4 or ?4='ALL')")
	Set<Object[]> getThirdPartyInspectionReport(Long orgId, String fromDate, String toDate,String partyName);

	
	@Query(nativeQuery = true, value = "select partyname from partymaster where orgid=?1 and partytype='THIRD-PARTY' and active=1 and cancel=0")
	Set<Object[]> getThirdPartyNamesFromPartyMaster(Long orgId);

	ThirdPartyInspectionVO findByDocId(String docId);

	ThirdPartyInspectionVO getAllThirdPartyInspectionById(Long id);
}
