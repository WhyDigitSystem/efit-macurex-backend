package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.InwardInspectionVO;

@Repository
public interface InwardInspectionRepo extends JpaRepository<InwardInspectionVO, Long> {
	
	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getInwardInspectionDocId(Long orgId, String financialYear, String screenCode);

	@Query(nativeQuery = true, value = "select * from inward_inspection_basic where inward_inspection_basic_id=?1 and active=1 and cancel=0")
	InwardInspectionVO getInwardInspectionById(Long id);

	@Query(nativeQuery = true, value = "select * from inward_inspection_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
	List<InwardInspectionVO> getInwardInspectionByOrgId(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "select doc_id,doc_date,grn_clear_time,po_no,schedule_no,party_dc_no,supplier_dc_date from grn_basic where org_id=?1 and \r\n"
			+ "branch=?2 and supplier_code=?3 and active=1 and cancel=0")
	Set<Object[]> getMirnGrnNo(Long orgId, Long branch, Long supplierCode);

	
	@Query(nativeQuery = true, value = "select g1.item,i.item_code,i.item_description,i.drawing_no,i.inspection,l.value_description,i.primary_unit,i.purchase_unit,u.unit_id,g1.po_qty,g1.received_qty,g1.accept_qty from grn_basic g join grn_details g1 on g.grn_basic_id=g1.grn_basic_id left join item i on i.item_id=g1.item\r\n"
			+ "left join listofvaluesdetails l on l.listofvaluesdetails_id=i.inspection left join unitmaster u on u.unitmaster_id=i.primary_unit and \r\n"
			+ "u.unitmaster_id=i.purchase_unit  where g.org_id=?1 and \r\n"
			+ "g.branch=?2 and g.supplier_code=?3 and g.po_no=?4 and g.active=1 and g.cancel=0 group by\r\n"
			+ "g1.item,i.item_code,i.item_description,i.drawing_no,i.inspection,l.value_description,\r\n"
			+ "i.primary_unit,i.purchase_unit,u.unit_id,g1.po_qty,g1.received_qty,g1.accept_qty")
	Set<Object[]> getMirnGrnNoItemDetails(Long orgId, Long branch, Long supplierCode,String purchaseOrderNo);

}
