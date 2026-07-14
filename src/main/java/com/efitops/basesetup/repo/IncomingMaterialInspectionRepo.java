package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.IncomingMaterialInspectionVO;

@Repository
public interface IncomingMaterialInspectionRepo extends JpaRepository<IncomingMaterialInspectionVO, Long> {

	@Query(nativeQuery = true, value = "select * from  incomingmaterialinspection where orgid=?1 and finyear=?2 and branchcode=?3 ")
	List<IncomingMaterialInspectionVO> getAllIncomingMaterialInspectionByOrgId(Long orgId, String finYear,
			String branchCode);

	@Query(nativeQuery = true, value = "select * from incomingmaterialinspection  where incomingmaterialinspectionid=?1")
	IncomingMaterialInspectionVO getIncomingMaterialInspectionById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getIncomingMaterialInspectionDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select  a.grnno,a.pono,a.suppliername,a.invdcno from grn a where a.orgid=?1  and a.grnno=?2 and  a.active = 1 and \r\n"
			+ " a.cancel= 0  group by a.grnno,a.pono,a.suppliername,a.invdcno \r\n"
			+ " union \r\n"
			+ " select  a.docid,a.pono,a.subcontractorname,a.dcno from subcontractgrn a where a.orgid=?1  and a.docid=?2 and  a.active = 1 and \r\n"
			+ " a.cancel= 0  group by a.docid,a.pono,a.subcontractorname,a.dcno")
	Set<Object[]> getGrnAndSubContractGrnDetails(Long orgId, String grnNo);

		@Query(nativeQuery = true, value = "select a1.itemcode,a1.itemdesc,a1.recievedqty from grn a join grndetails a1 on \r\n"
				+ "a.grnid =  a1.grnid  where a.orgid=?1  and a.grnno=?2 and   a.active = 1 and a.cancel = 0  group by \r\n"
				+ "a1.itemcode,a1.itemdesc,a1.recievedqty \r\n"
				+ " union\r\n"
				+ " select a1.itemcode,a1.itemdesc,a1.acceptQty from subcontractgrn a join subcontractgrndetails a1 on \r\n"
				+ "a.subcontractgrnid =  a1.subcontractgrnid  where a.orgid=?1  and a.docid=?2 and   a.active = 1 and a.cancel = 0  group by \r\n"
				+ "a1.itemcode,a1.itemdesc,a1.acceptQty")
	Set<Object[]> getItemNoFromGrn(Long orgId,String grnNo);

	@Query(nativeQuery = true, value = "select acceptedqty,dcinvno,docid,documentformatno,grnno,itemno,material,materialtype,pono,qtyreceived,suppliername from incomingmaterialinspection where orgid=?1 and (grnno=?2 or ?2='ALL')  \r\n"
			+ "and (suppliername=?3 or ?3='ALL') and materialtype=?4 and active=1 and cancel=0")
	Set<Object[]> getIncomingMaterialInspectionReport(Long orgId, String grnNo, String supplierName, String type);

	@Query(value = "SELECT DISTINCT suppliername " + "FROM incomingmaterialinspection " + "WHERE orgid = ?1 "
			+ "AND TRIM(UPPER(branchcode)) = TRIM(UPPER(?2)) " + "AND suppliername IS NOT NULL", nativeQuery = true)
	Set<String> getSupplierNameForIncomingMaterialInspectionReport(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "select grnno from  incomingmaterialinspection where orgid=?1 and branchcode=?2 and ( suppliername=?3 or ?3 ='ALL' ) ")
	Set<String> getGrnNoForIncomingMaterialInspectionReport(Long orgId, String branchCode, String supplierName);

	IncomingMaterialInspectionVO findByDocId(String docId);
	
	@Query(nativeQuery = true, value = "select grnno from grn where orgid=?1 and  ?2='Raw Material' and active=1 and cancel=0 group by grnno\r\n"
			+ "union \r\n"
			+ "select docid from subcontractgrn where orgid=?1 and ?2='FG/SFG' and active=1 and cancel=0 group by docid")
	Set<Object[]> getGrnNoAndSubContractGrnNo(Long orgId, String type);
	
	@Query(nativeQuery = true, value = "select grnno,suppliername from  incomingmaterialinspection where orgid=?1  and  materialtype=?2")
	Set<Object[]> getListOfGrnNumbers(Long orgId, String type);

	IncomingMaterialInspectionVO getAllIncomingMaterialInspectionById(Long id);

	IncomingMaterialInspectionVO getAllIncomingMaterialInspectionImagesById(Long id);


}
