package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.BillOfMaterialVO;

@Repository
public interface BillOfMaterialRepo extends JpaRepository<BillOfMaterialVO, Long> {

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getBillOfMaterialDocId(Long orgId, String financialYear, String screenCode);

	@Query(nativeQuery = true, value = "select * from bill_of_material where bill_of_material_id=?1 and active=1 and cancel=0")
	BillOfMaterialVO getBillOfMaterialById(Long id);

	@Query(nativeQuery = true, value = "select * from bill_of_material where org_id=?1 and branch=?2 and active=1 and cancel=0")
	List<BillOfMaterialVO> getBillOfMaterialByOrgId(Long orgId, Long branch);

}
