package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ItemInventoryVO;

@Repository
public interface ItemInventoryRepo extends JpaRepository<ItemInventoryVO, Long> {

//	List<ItemInventoryVO> findByItemMasterVO(ItemMasterVO itemMasterVO);

}
