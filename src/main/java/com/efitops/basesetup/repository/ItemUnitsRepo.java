package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.ItemUnitsVO;

@Repository
public interface ItemUnitsRepo extends JpaRepository<ItemUnitsVO, Long> {

	List<ItemUnitsVO> findByItemMasterVO(ItemMasterVO itemMasterVO);

}
