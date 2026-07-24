package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.ItemOthersVO;

@Repository
public interface ItemOthersRepo extends JpaRepository<ItemOthersVO, Long> {

	List<ItemOthersVO> findByItemMasterVO(ItemMasterVO itemMasterVO);

}
