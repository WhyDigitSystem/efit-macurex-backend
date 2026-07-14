package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.StockReConcilationVO;

@Repository
public interface StockReConcilationRepo extends JpaRepository<StockReConcilationVO, Long> {
	@Query(nativeQuery = true, value = "select * from stockreconcilation  where  orgid=?1 and finyear=?2 and branchcode=?3")
	List<StockReConcilationVO> getAllStockReConcilationByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from stockreconcilation  where  stockreconcilationid=?1")
	StockReConcilationVO getStockReConcilationById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getStockReConcilationDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(value = "SELECT \r\n" + "    i.itemname,\r\n" + "    i.itemdesc,\r\n" + "    i1.price,\r\n"
			+ "    i.primaryunit\r\n" + "FROM item i\r\n" + "JOIN itempriceslab i1 \r\n"
			+ "    ON i.itemid = i1.itemid\r\n" + "WHERE i.orgid = ?1\r\n" + "  AND i.cancel = 0\r\n"
			+ "  AND i.active = 1\r\n" + "  AND i1.priceeffectivefrom = (\r\n"
			+ "        SELECT MAX(i2.priceeffectivefrom)\r\n" + "        FROM itempriceslab i2\r\n"
			+ "        WHERE i2.itemid = i.itemid\r\n" + "  )", nativeQuery = true)
	Set<Object[]> getItemNameAndDesc(Long orgId);
}
