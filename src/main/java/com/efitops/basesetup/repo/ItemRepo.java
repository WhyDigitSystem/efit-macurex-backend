package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ItemVO;

@Repository
public interface ItemRepo extends JpaRepository<ItemVO, Long> {

	@Query(nativeQuery = true, value = "select * from item a where a.orgid=?1 order by a.itemid desc")
	List<ItemVO> findItemByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from item where itemid=?1")
	List<ItemVO> findItemById(Long id);

	@Query(nativeQuery = true, value = "select uomcode from uommast where orgid=?1 and active=1 ")
	Set<Object[]> findPrimaryCodeFromUomMaster(Long orgId);

	@Query(nativeQuery = true, value = "select locationcode from stocklocation where orgid=? and active=1")
	Set<Object[]> findStockLocationForItemMaster(Long orgId);

	@Query(nativeQuery = true, value = "select gstslab from gst where orgid=?1 and active=1")
	Set<Object[]> findTaxSlabFromGst(Long orgId);

	@Query(nativeQuery = true, value = "select itemgroup from materialtype where orgid=?1 and materialtype=?2 and active=1")
	Set<Object[]> findMaterialGroupFromMaterialType(Long orgId, String materialType);

	@Query(nativeQuery = true, value = "select a1.itemsubgroup from materialtype a join materialtypedetails a1 where a.orgid=?1 and a.materialtype=?2 and \r\n"
			+ "a.itemgroup=?3  and a.materialtypeid=a1.materialtypeid and active=1")
	Set<Object[]> findMaterialSubGroupFromMaterialType(Long orgId, String materialType, String materialGroup);

	@Query(nativeQuery = true, value = "select materialtype from  materialtype where orgid=?1 and active=1")
	Set<Object[]> findMaterialTypeForItemMaster(Long orgId);

	boolean existsByItemNameAndOrgId(String itemName, Long orgId);

//	@Query(nativeQuery =true,value ="select distinct(Itemtype) from m_item")
//	Set<Object[]> findItemDetails();

//	@Query(nativeQuery =true,value ="select a.itemid, a.itemdesc,a.primaryunit from m_item a  where a.active=1 and itemtype='identtype' and itemname=?1 order by a.itemid")
//	Set<Object[]> getItemDetails(String itemName);

}
