package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseReturnVO;

@Repository
public interface PurchaseReturnRepo extends JpaRepository<PurchaseReturnVO, Long> {

	@Query(nativeQuery = true, value = "select * from purchase_return_basic where purchase_return_basic_id=?1 and active=1 and cancel=0")
	PurchaseReturnVO getPurchaseReturnById(Long id);

	@Query(nativeQuery = true, value = "select * from purchase_return_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
	List<PurchaseReturnVO> getPurchaseReturnByOrgId(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getPurchaseReturnDocId(Long orgId, String financialYear, String screenCode);

	@Query(nativeQuery = true, value = "select doc_id,doc_date,po_no from grn_basic where org_id=?1 and branch=?2 and supplier_code=?3 and\r\n"
			+ " active=1 and cancel=0")
	Set<Object[]> getGrnDetails(Long orgId, Long branch, Long supplierCode);

	@Query(nativeQuery = true, value = "select doc_id,doc_date from purchase_bill_basic where org_id=?1 and branch=?2 and supplier=?3  and grn_no=?4 and active=1 and cancel=0")
	Set<Object[]> getPurchaseBill(Long orgId, Long branch, Long supplierCode, String grnNo);
	
//	select p1.item from purchase_bill_basic p join purchase_bill_details p1 on p.purchase_bill_basic_id=p1.purchase_bill_basic_id where p.org_id=1000000017 and p.branch=1000000016 and 
//			p.supplier=1000000008    and p.grn_no='GNR101' and p.doc_id='' and p.active=1 and p.cancel=0;
}
