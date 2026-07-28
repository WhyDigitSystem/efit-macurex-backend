package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ItemDrawingVO;

@Repository
public interface ItemDrawingRepo extends JpaRepository<ItemDrawingVO, Long> {

//	List<ItemDrawingVO> findByItemMasterVO(ItemMasterVO itemMasterVO);

}
