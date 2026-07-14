package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ItemInventoryVO;
import com.efitops.basesetup.entity.ItemVO;

@Repository
public interface ItemInventoryRepo extends JpaRepository<ItemInventoryVO, Long>{

	List<ItemInventoryVO> findByItemVO(ItemVO itemVO);

}
