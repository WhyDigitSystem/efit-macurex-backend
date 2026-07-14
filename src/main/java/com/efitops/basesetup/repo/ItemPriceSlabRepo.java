package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ItemPriceSlabVO;
import com.efitops.basesetup.entity.ItemVO;

@Repository
public interface ItemPriceSlabRepo extends JpaRepository<ItemPriceSlabVO, Long>{

	List<ItemPriceSlabVO> findByItemVO(ItemVO itemVO);

}
