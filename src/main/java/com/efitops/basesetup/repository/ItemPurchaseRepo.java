package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.ItemPurchaseVO;

@Repository
public interface ItemPurchaseRepo extends JpaRepository<ItemPurchaseVO, Long> {

//	List<ItemPurchaseVO> findByItemMasterVO(ItemMasterVO itemMasterVO);

}
