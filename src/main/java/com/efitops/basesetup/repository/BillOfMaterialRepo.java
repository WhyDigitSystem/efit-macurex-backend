package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

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

	@Query(nativeQuery = true, value = "select i.item_id,i.item_code,i.item_description,concat(i.item_code,' --- ',i.item_description) profit from item i left join listofvaluesdetails l1\r\n"
			+ "                        on l1.listofvaluesdetails_id=i.item_type where i.org_id=?1 and \r\n"
			+ "                        i.branch=?2 and l1.value_description=?3\r\n"
			+ "                        union \r\n"
			+ "                         select i.item_id,i.item_code,i.item_description,concat(i.item_code,' --- ',i.item_description) profit from item i left join listofvaluesdetails l1\r\n"
			+ "                        on l1.listofvaluesdetails_id=i.item_type where i.org_id=?1 and \r\n"
			+ "                        i.branch=?2 and l1.value_description=?3")
	Set<Object[]> getFgAndSfgItemDetails(Long orgId, Long branch,String type);


	@Query(nativeQuery = true, value = "select i.item_id,i.item_code,i.item_description,l1.value_description from item i left join listofvaluesdetails l1\r\n"
			+ "                        on l1.listofvaluesdetails_id=i.item_type where i.org_id=?1 and \r\n"
			+ "                        i.branch=?1 and l1.value_description not in ('FG')")
	Set<Object[]> getGridDetailsFromBom(Long orgId, Long branch);

	
	@Query(nativeQuery = true, value = "select  doc_id from  bill_of_material where org_id =?1 and branch = ?2  and fg_itme = ?3\r\n"
			+ "order by  created_on desc limit 1")
	Set<Object[]> getFillDetailsOf(Long orgId, Long branch,Long fgItem);
}
