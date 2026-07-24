package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.ItemSalesVO;

@Repository
public interface ItemSalesRepo extends JpaRepository<ItemSalesVO, Long> {

	List<ItemSalesVO> findByItemMasterVO(ItemMasterVO itemMasterVO);

}
