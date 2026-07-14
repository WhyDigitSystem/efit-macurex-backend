package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ItemWiseProcessDetailsVO;
import com.efitops.basesetup.entity.ItemWiseProcessMasterVO;

@Repository
public interface ItemWiseProcessDetailsRepo extends JpaRepository<ItemWiseProcessDetailsVO, Long>{

	List<ItemWiseProcessDetailsVO> findByItemWiseProcessMasterVO(ItemWiseProcessMasterVO itemWiseProcessMasterVO);



}
