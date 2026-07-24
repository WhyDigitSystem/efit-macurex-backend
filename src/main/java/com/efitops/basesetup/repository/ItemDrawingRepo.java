package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ItemDrawingVO;
import com.efitops.basesetup.entity.ItemMasterVO;

@Repository
public interface ItemDrawingRepo extends JpaRepository<ItemDrawingVO, Long> {

	List<ItemDrawingVO> findByItemMasterVO(ItemMasterVO itemMasterVO);

}
