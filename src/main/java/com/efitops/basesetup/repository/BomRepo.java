package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.BomVO;

@Repository
public interface BomRepo extends JpaRepository<BomVO, Long>{
	
	@Query(nativeQuery = true,value="select * from  bom where orgid=?1 and branchcode=?2 ")
	List<BomVO> getAllBomByOrgId(Long orgId, String branchCode);
	
	@Query(nativeQuery = true,value="select * from bom where bomid=?1")
	List<BomVO> getBomById(Long id);
	
	
	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getBomDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select itemname,itemdesc,primaryunit from item where orgid=?1 and itemtype=?2 and cancel =0 and active = 1")
	Set<Object[]> findFGSFGPartDetails(Long orgId,String productType);

	@Query(nativeQuery = true, value = "select  itemname,itemdesc,primaryunit,itemtype from item where orgid=?1 and itemtype in ('Raw Material','SFG')and cancel =0 and active = 1")
	Set<Object[]> findSFGItemDetails(Long orgId);

}


