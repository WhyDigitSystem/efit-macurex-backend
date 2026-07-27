package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ItemMasterVO;

@Repository
public interface ItemMasterRepo extends JpaRepository<ItemMasterVO, Long> {

	@Query(nativeQuery = true, value = "select * from itemmaster where itemmaster_id=?1 and active=1 and cancel=0")
	ItemMasterVO getItemMasterById(Long id);

	boolean existsByItemCodeAndOrg(String itemCode, Long org);

}
