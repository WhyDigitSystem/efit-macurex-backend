package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ItemOthersVO;

@Repository
public interface ItemOthersRepo extends JpaRepository<ItemOthersVO, Long> {

//	List<ItemOthersVO> findByItemMasterVO(ItemMasterVO itemMasterVO);

}
