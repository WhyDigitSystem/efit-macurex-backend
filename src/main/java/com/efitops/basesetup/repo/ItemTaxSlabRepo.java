package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ItemTaxSlabVO;
import com.efitops.basesetup.entity.ItemVO;

@Repository
public interface ItemTaxSlabRepo extends JpaRepository<ItemTaxSlabVO, Long>{

	List<ItemTaxSlabVO> findByItemVO(ItemVO itemVO);

}
